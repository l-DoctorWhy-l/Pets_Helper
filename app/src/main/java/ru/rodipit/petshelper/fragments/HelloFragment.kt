package ru.rodipit.petshelper.fragments

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.rodipit.petshelper.R
import ru.rodipit.petshelper.databinding.FragmentHelloBinding
import ru.rodipit.petshelper.viewModels.HelloViewModel


class HelloFragment : Fragment() {

    interface OnEventListener {
        fun userCreated()
    }


    private lateinit var someEventListener: OnEventListener

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        someEventListener = try {
            activity as OnEventListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$activity must implement onEventListener")
        }
    }

    private var _binding: FragmentHelloBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HelloViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(requireActivity())[HelloViewModel::class.java]
        _binding = FragmentHelloBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.regIsComplete.observe(requireActivity()){
            if(it){
                replaceFragment(MainFragment())
                someEventListener.userCreated()
            }
        }

        binding.nameEt.addTextChangedListener{
            viewModel.updateUserName(binding.nameEt.text.toString())
        }

        binding.confirmButton.setOnClickListener {
            binding.nameEt.isEnabled = false
            viewModel.createUser(viewModel.userName.value.toString())
        }
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    private fun updateUI(){
        binding.nameEt.setText(viewModel.userName.value)
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}