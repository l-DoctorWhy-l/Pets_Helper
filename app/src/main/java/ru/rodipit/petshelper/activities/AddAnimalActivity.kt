package ru.rodipit.petshelper.activities

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import ru.rodipit.petshelper.R
import ru.rodipit.petshelper.databinding.ActivityAddAnimalBinding
import ru.rodipit.petshelper.models.Animal
import ru.rodipit.petshelper.viewModels.AddAnimalViewModel

class AddAnimalActivity : AppCompatActivity() {

    private val binding: ActivityAddAnimalBinding by lazy { ActivityAddAnimalBinding.inflate(layoutInflater) }
    private lateinit var viewModel: AddAnimalViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[AddAnimalViewModel::class.java]

        val spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.pets_types, android.R.layout.simple_spinner_item)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.animalTypeSpinner.adapter = spinnerAdapter

        createListeners()

        createObservers()


    }

    private fun createObservers() {
        viewModel.success.observe(this){
            if(it){
                setResult(RESULT_OK)
                finish()
            }
        }
    }


    private fun createListeners(){

        binding.nameEt.addTextChangedListener{
            viewModel.changeName(it.toString())
        }

        binding.fullnameEt.addTextChangedListener{
            viewModel.changeFullname(it.toString())
        }

        binding.birthDateEt.addTextChangedListener{
            viewModel.changeBirthDate(it.toString())
        }

        binding.breedEt.addTextChangedListener{
            viewModel.changeBreed(it.toString())
        }

        binding.confirmButton.setOnClickListener {
            viewModel.createNewAnimal()
        }

        binding.animalTypeSpinner.onItemSelectedListener = object: OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val item = p0?.getItemAtPosition(p2).toString()
                viewModel.changeAnimalType(Animal.castAnimalType(item))
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }
    }


    override fun onResume() {
        super.onResume()
        with(binding){
            nameEt.setText(viewModel.name.value)
            fullnameEt.setText(viewModel.fullname.value)
            breedEt.setText(viewModel.breed.value)
            birthDateEt.setText(viewModel.birthDate.value)
        }
    }


}