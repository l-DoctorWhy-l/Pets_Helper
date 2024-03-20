package ru.rodipit.petshelper.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.rodipit.petshelper.adapters.AnimalsAdapter
import ru.rodipit.petshelper.LoadingStates
import ru.rodipit.petshelper.R
import ru.rodipit.petshelper.databinding.ActivityMainBinding
import ru.rodipit.petshelper.fragments.EatingFragment
import ru.rodipit.petshelper.fragments.ExpensesFragment
import ru.rodipit.petshelper.fragments.HelloFragment
import ru.rodipit.petshelper.fragments.MainFragment
import ru.rodipit.petshelper.fragments.MedicineFragment
import ru.rodipit.petshelper.viewModels.MainViewModel


class MainActivity : AppCompatActivity(), HelloFragment.OnEventListener {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var viewModel : MainViewModel
    private val onItemClickListener = AnimalsAdapter.OnItemClickListener{ context ->
        addAnimalActivityLauncher.launch(Intent(context, AddAnimalActivity::class.java))
    }
    private var adapter: AnimalsAdapter = AnimalsAdapter(onItemClickListener)

    private val addAnimalActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){result ->
        if (result.resultCode == Activity.RESULT_OK){
            viewModel.startLoadingAnimals()
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        createObservers()


        val layoutManager = object: LinearLayoutManager(this, RecyclerView.HORIZONTAL, false){
            override fun canScrollHorizontally(): Boolean {
                return false
            }
        }

        binding.animalsRecycler.adapter = adapter
        binding.animalsRecycler.layoutManager = layoutManager






        binding.bottomMenu.setOnItemSelectedListener {
            changeFragment(it.itemId)
            true
        }

        binding.leftArrowBtn.setOnClickListener {
            viewModel.previousAnimal()
        }

        binding.rightArrowBtn.setOnClickListener {
            viewModel.nextAnimal()
        }

    }


    private fun createObservers(){
        viewModel.state.observe(this){state ->
            when(state){
                "registration" -> startRegistration()
                "using" -> {
                    startUsing()
                }
                else -> {
                }
            }
        }

        viewModel.loadingStates.observe(this){
            if (it["animals"] == LoadingStates.LOADED){
                it["animals"] = LoadingStates.SHOWING
            }
        }

        viewModel.currentAnimalPosition.observe(this){
            println("Current animal position is $it")
            binding.animalsRecycler.scrollToPosition(it)
            if(it == adapter.itemCount - 1) {
                binding.rightArrowBtn.visibility = View.INVISIBLE
            } else{
                binding.rightArrowBtn.visibility = View.VISIBLE
            }

            if(it == 0) {
                binding.leftArrowBtn.visibility = View.INVISIBLE
            } else{
                binding.leftArrowBtn.visibility = View.VISIBLE
            }
            changeFragment(binding.bottomMenu.selectedItemId)
        }

        viewModel.animals.observe(this){
            Log.wtf("animals", it.toString())
            adapter.animals = it
            Log.wtf("animals", adapter.animals.toString())
        }
    }


   private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        println("start ${fragment.javaClass.name}")
        fragmentTransaction.commit()
    }

    private fun changeFragment(fragmentId: Int){
        when(fragmentId){
            R.id.eating -> replaceFragment(EatingFragment.getNewInstance(requireNotNull(viewModel.currentAnimal?.id)))
            R.id.expenses -> replaceFragment(ExpensesFragment.getNewInstance(requireNotNull(viewModel.currentAnimal?.id)))
            R.id.pet -> replaceFragment(MainFragment.getNewInstance(requireNotNull(viewModel.currentAnimal?.id)))
            R.id.medicine -> replaceFragment(MedicineFragment.getNewInstance(requireNotNull(viewModel.currentAnimal?.id)))

            else -> {

            }
        }
    }

    private fun startRegistration(){
        println("startRegistration")
        with(binding){
            bottomMenu.visibility = View.GONE
            header.visibility = View.GONE
            fragmentContainer.layoutParams.height = MATCH_PARENT
        }
        replaceFragment(HelloFragment())

    }

    private fun startUsing(){
        println("startUsing")
        with(binding){
            bottomMenu.visibility = View.VISIBLE
            header.visibility = View.VISIBLE
            fragmentContainer.layoutParams.height = WRAP_CONTENT
        }
        replaceFragment(MainFragment.getNewInstance(requireNotNull(viewModel.currentAnimal?.id)))
    }

    override fun userCreated() {
        viewModel.startLoading()
    }



}