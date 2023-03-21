package com.example.contacts.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.contacts.R
import com.example.contacts.databinding.MainLayoutBinding

class MainFragment: Fragment(R.layout.main_layout) {

    private var _binding: MainLayoutBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainLayoutBinding.inflate(inflater, container, false)

        return binding.root
    }

    fun goToAddContact(){
        findNavController().navigate(R.id.action_mainFragment_to_addContactFragment)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mainFragment = this
    }
}