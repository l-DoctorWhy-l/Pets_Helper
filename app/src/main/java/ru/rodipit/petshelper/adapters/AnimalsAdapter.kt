package ru.rodipit.petshelper.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.rodipit.petshelper.R
import ru.rodipit.petshelper.data.entities.AnimalEntity
import ru.rodipit.petshelper.databinding.AddAnimalCardBinding
import ru.rodipit.petshelper.databinding.AnimalCardBinding

class AnimalsAdapter( private var onItemClickListener: OnItemClickListener): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var animals: MutableList<AnimalEntity> = mutableListOf()

    fun interface OnItemClickListener {
        fun onItemClick(context: Context)
    }


    class AnimalViewHolder(private val binding: AnimalCardBinding) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(animal: AnimalEntity){
            binding.name.text = animal.name
            val logoId = when(animal.type){
                AnimalEntity.DOG -> R.drawable.dog
                AnimalEntity.FISH -> R.drawable.fish
                AnimalEntity.HAMSTER -> R.drawable.hamster
                AnimalEntity.PARROT -> R.drawable.parrot
                AnimalEntity.TURTLE -> R.drawable.turtle
                AnimalEntity.CAT -> R.drawable.cat
                AnimalEntity.OTHER -> R.drawable.pow
                else -> R.drawable.pow
            }
            Picasso.get().load(logoId).fit().into(binding.logo)
        }
    }

    class AddViewHolder(private val binding: AddAnimalCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(listener: OnItemClickListener){
            binding.mainLayout.setOnClickListener {
                listener.onItemClick(binding.root.context)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val animalCardBinding = AnimalCardBinding.inflate(inflater, parent, false)
        val addAnimalCardBinding = AddAnimalCardBinding.inflate(inflater, parent, false)
        return when(viewType){
            ANIMAL_TYPE -> AnimalViewHolder(animalCardBinding)
            ADD_TYPE -> AddViewHolder(addAnimalCardBinding)
            else -> throw IllegalArgumentException("view Type is invalid")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(animals[position].id){
            -1 -> ADD_TYPE
            else -> ANIMAL_TYPE
        }
    }

    override fun getItemCount() = animals.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is AnimalViewHolder)
            holder.onBind(animals[position])
        else if (holder is AddViewHolder)
            holder.onBind(onItemClickListener)

    }

    companion object{
        private const val ANIMAL_TYPE = 1
        private const val ADD_TYPE = 2
    }
}