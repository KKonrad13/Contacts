package com.example.contacts.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contacts.Contact
import com.example.contacts.ContactViewModel
import com.example.contacts.ContactsAdapter
import com.example.contacts.R
import com.example.contacts.databinding.MainLayoutBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainFragment: Fragment(R.layout.main_layout) {

    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var recyclerView: RecyclerView
    private lateinit var contactsAdapter: ContactsAdapter
    private lateinit var searchView: SearchView
    private lateinit var npChooseSort: NumberPicker
    private val displayedValues = arrayOf("Default", "First Name", "Last Name", "Phone Number")

    private var _binding: MainLayoutBinding? = null

    private val binding get() = _binding!!

    private val sharedViewModel: ContactViewModel by activityViewModels()
    private val sharedPrefFile = "y"
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

        npChooseSort = binding.npChooseSort

        npChooseSort.minValue = 0
        npChooseSort.maxValue = displayedValues.size - 1
        npChooseSort.displayedValues = displayedValues
        npChooseSort.value = 0

        val sharedPreferences = requireContext().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        val contactJson = sharedPreferences.getString("key", null)

        if(contactJson != null){
            val contactType = object : TypeToken<MutableList<Contact>>() {}.type
            sharedViewModel.setContacts(Gson().fromJson(contactJson, contactType))
        }else{
            sharedViewModel.setContacts(mutableListOf())
            addBasicContacts()//for testing
        }

        recyclerView = view.findViewById(R.id.recyclerView)
        contactsAdapter = ContactsAdapter(sharedViewModel, sharedViewModel.contacts.value!!)

        layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = contactsAdapter
    }

    override fun onStop() {
        super.onStop()
        val sharedPreferences = requireContext().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val contactJson = Gson().toJson(sharedViewModel.contacts.value!!)
        editor.putString("key", contactJson)
        editor.apply()
    }

    fun filterList(text: String){
        val filteredList: MutableList<Contact> = mutableListOf()
        val contacts = sharedViewModel.contacts.value!!
        for(i in 0 until contacts.size){
            if(    contacts[i].firstName.contains(text)
                || contacts[i].lastName.contains(text)
                || contacts[i].lastName.contains(text)
                || contacts[i].phoneNumber.contains(text)
                || contacts[i].notes.contains(text)){

                filteredList.add(contacts[i])
            }
        }
        contactsAdapter.setList(filteredList)
    }

    private fun addBasicContacts(){

        sharedViewModel.addContact(Contact(-1,"a", "abx", "192", ""))
        sharedViewModel.addContact(Contact(-1,"b", "adx", "119", ""))
        sharedViewModel.addContact(Contact(-1,"c", "ahds", "619", ""))
        sharedViewModel.addContact(Contact(-1,"d", "aa", "179", ""))
        sharedViewModel.addContact(Contact(-1,"e", "ag", "129", ""))
        sharedViewModel.addContact(Contact(-1,"f", "af", "1219", ""))
        sharedViewModel.addContact(Contact(-1,"af", "ga", "1349", ""))
    }
}