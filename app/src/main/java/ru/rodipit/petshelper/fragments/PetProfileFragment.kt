package ru.rodipit.petshelper.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.rodipit.petshelper.R
import ru.rodipit.petshelper.databinding.FragmentPetProfileBinding
import ru.rodipit.petshelper.viewModels.PetProfileViewModel
@AndroidEntryPoint
class PetProfileFragment : Fragment() {


    private val viewModel: PetProfileViewModel by viewModels()

    private var _binding: FragmentPetProfileBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPetProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}