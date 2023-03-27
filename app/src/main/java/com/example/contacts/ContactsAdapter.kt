package com.example.contacts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.contacts.databinding.SingleContactBinding

class ContactsAdapter(private val viewModel: ContactViewModel, private var contacts: MutableList<Contact>): RecyclerView.Adapter<ContactsAdapter.ContactViewHolder>() {
    inner class ContactViewHolder(binding: SingleContactBinding) : RecyclerView.ViewHolder(binding.root){
        val tvContactName = binding.tvContactName
        val cbContact = binding.cbContact
    }

    private var showCheckbox = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val contactBinding = SingleContactBinding.inflate(inflater, parent, false)
        return ContactViewHolder(contactBinding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contacts[position]

        holder.apply {
            var firstName = contact.firstName
            if(firstName != "")
                firstName = firstName.plus(" ")
            tvContactName.text = firstName.plus(contact.lastName)

            if (showCheckbox){
                cbContact.visibility = View.VISIBLE
            }else{
                cbContact.visibility = View.GONE
            }

            cbContact.setOnClickListener {
                contact.isChecked = !contact.isChecked
            }

            itemView.setOnClickListener {
                viewModel.setCurrentContact(contact)
                itemView.findNavController().navigate(R.id.action_mainFragment_to_contactInfoFragment)
            }
        }
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    fun changeCheckBoxVisibility(){
        showCheckbox = !showCheckbox
        contacts.map { it.isChecked = false }
        updateList()
    }

    fun deleteSelected(){
        contacts.removeAll { it.isChecked }
        updateList()
    }

    fun updateList(){
        notifyDataSetChanged()
    }

    fun setList(filteredContacts: MutableList<Contact>){
        contacts = filteredContacts
        updateList()
    }
}