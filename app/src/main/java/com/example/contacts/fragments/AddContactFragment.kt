package com.example.contacts.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.contacts.Contact
import com.example.contacts.ContactViewModel
import com.example.contacts.R
import com.example.contacts.databinding.AddEditContactBinding
import java.io.File
import java.io.FileOutputStream

class AddContactFragment: Fragment(R.layout.add_edit_contact) {

    private var _binding: AddEditContactBinding? = null
    private val binding get() = _binding!!

    private lateinit var btnCancel: Button
    private lateinit var btnConfirm: Button

    private lateinit var etFirstName: EditText
    private lateinit var etLastName: EditText
    private lateinit var etNumber: EditText
    private lateinit var etNotes: EditText
    private lateinit var ivAvatar: ImageView
    private val sharedViewModel: ContactViewModel by activityViewModels()

    private val activityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val uri: Uri? = data!!.data
            val imageView: ImageView = ivAvatar
            imageView.setImageURI(uri)
        }
    }

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
        ivAvatar = binding.ivAvatar

        handleConfirmButton()
        handleCancelButton()
        handleIvAvatar()
    }

    private fun handleIvAvatar() {
        ivAvatar.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
            activityResultLauncher.launch(intent)
        }
    }
    private fun handleCancelButton() {
        btnCancel.setOnClickListener {
            goToMainFragment()
        }
    }

    private fun handleConfirmButton() {
        btnConfirm.setOnClickListener {
            addContact()
        }
    }

    private fun addContact(){
        if((etFirstName.text.toString() != "" && etLastName.text.toString() != "" ) ||
            etNumber.text.toString() != "") {

            val drawable = ivAvatar.drawable as BitmapDrawable
            val bitmap = drawable.bitmap
            val file = File(requireActivity().filesDir,etFirstName.text.toString()
                .plus(etNumber.text.toString())
                .plus(".png"))

            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.close()

            sharedViewModel.setCurrentContact(
                Contact(
                    -1,
                    etFirstName.text.toString(),
                    etLastName.text.toString(),
                    etNumber.text.toString(),
                    etNotes.text.toString(),
                    etFirstName.text.toString()
                        .plus(etNumber.text.toString())
                        .plus(".png")
                )
            )

            goToMainFragment()
        }else{
            Toast.makeText(this.requireContext(),
                "Contact must have name and phone number",
                Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun goToMainFragment(){
        findNavController().navigate(R.id.action_addContactFragment_to_mainFragment)
    }
}