package com.example.contacts.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.contacts.ContactViewModel
import com.example.contacts.R
import com.example.contacts.databinding.AddEditContactBinding

class EditContactFragment: Fragment(R.layout.add_edit_contact) {
    private var _binding: AddEditContactBinding? = null
    private val binding get() = _binding!!

    private lateinit var btnCancel: Button
    private lateinit var btnConfirm: Button

    private lateinit var etFirstName: EditText
    private lateinit var etLastName: EditText
    private lateinit var etNumber: EditText
    private lateinit var etNotes: EditText

    private val sharedViewModel: ContactViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AddEditContactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = sharedViewModel

        btnCancel = binding.btnCancel
        btnConfirm = binding.btnConfirm

        etFirstName = binding.etFirstName
        etLastName = binding.etLastName
        etNumber = binding.etNumber
        etNotes = binding.etNotes

        btnCancel.setOnClickListener {
            goToMainFragment()
        }

        btnConfirm.setOnClickListener {
            confirmEdit()
            goToMainFragment()
        }
    }

    private fun confirmEdit(){

    }

    private fun goToMainFragment(){
        findNavController().navigate(R.id.action_editContactFragment_to_mainFragment)
    }
}