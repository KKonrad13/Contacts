package com.example.contacts.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.contacts.ContactViewModel
import com.example.contacts.R
import com.example.contacts.databinding.ContactInfoBinding

class ContactInfoFragment: Fragment(R.layout.contact_info) {
    private var _binding: ContactInfoBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: ContactViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ContactInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            viewModel = sharedViewModel
            infoFragment = this@ContactInfoFragment
        }
    }

    fun goToMainFragment(){
        sharedViewModel.setCurrentContact(null)
        findNavController().navigate(R.id.action_contactInfoFragment_to_mainFragment)
    }

    fun goToEditContact(){
        findNavController().navigate(R.id.action_contactInfoFragment_to_editContactFragment)
    }
}