package com.example.contacts

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.contacts.databinding.SingleContactBinding

class ContactsAdapter(private val activity: Activity, private var contacts: MutableList<Contact>): RecyclerView.Adapter<ContactsAdapter.ContactViewHolder>() {
    inner class ContactViewHolder(binding: SingleContactBinding) : RecyclerView.ViewHolder(binding.root){
        val tvContactName = binding.tvContactName
    }

    fun setList(filteredContacts: MutableList<Contact>){
        contacts = filteredContacts
        notifyDataSetChanged()
    }

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

            itemView.setOnClickListener {
                itemView.findNavController().navigate(R.id.action_mainFragment_to_contactInfoFragment)
            }
        }
    }

    override fun getItemCount(): Int {
        return contacts.size
    }
}