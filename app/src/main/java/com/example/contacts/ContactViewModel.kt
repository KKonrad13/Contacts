package com.example.contacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ContactViewModel:  ViewModel() {
    private var _currentContact = MutableLiveData<Contact?>()

    val currentContact: LiveData<Contact?> = _currentContact

    private var _contacts = MutableLiveData<MutableList<Contact>>()
    val contacts: LiveData<MutableList<Contact>> = _contacts

    init{
        _currentContact.value = Contact()
        _contacts.value = mutableListOf()
    }

    private val compFirstName: CompFirstName = CompFirstName()
    private val compLastName: CompLastName = CompLastName()
    private val compPhoneNumber: CompPhoneNumber = CompPhoneNumber()

    fun setContacts(contacts: MutableList<Contact>){
        _contacts.value = contacts
    }

    fun setCurrentContact(contact: Contact?){
        _currentContact.value = contact
    }

    fun addContact(contact: Contact){
        _contacts.value?.add(contact)
        sortContacts("Default")
    }

    fun sortContacts(option: String){
        when(option){
            "First Name" -> _contacts.value?.sortWith(compFirstName)
            "Last Name" -> _contacts.value?.sortWith(compLastName)
            "Phone Number" -> _contacts.value?.sortWith(compPhoneNumber)
            else -> _contacts.value?.sortWith(compFirstName)
        }
        updateIndexes()
    }

    private fun updateIndexes(){
        for(i in 0 until _contacts.value?.size!!)
            _contacts.value!![i].index = i
    }


    inner class CompFirstName: Comparator<Contact>{
        override fun compare(o1: Contact?, o2: Contact?): Int {
            if(o1 == null || o2 == null) return 0
            if(o1.firstName == o2.firstName) return o1.lastName.compareTo(o2.lastName)
            return o1.firstName.compareTo(o2.firstName)
        }

    }

    inner class CompLastName: Comparator<Contact>{
        override fun compare(o1: Contact?, o2: Contact?): Int {
            if(o1 == null || o2 == null) return 0
            if(o1.lastName == "" && o2.lastName != "") return -1
            if(o1.lastName != "" && o2.lastName == "") return 1
            if(o1.lastName == o2.lastName) return o1.firstName.compareTo(o2.firstName)
            return o1.lastName.compareTo(o2.lastName)
        }

    }

    inner class CompPhoneNumber: Comparator<Contact>{
        override fun compare(o1: Contact?, o2: Contact?): Int {
            if(o1 == null || o2 == null) return 0
            if(o1.phoneNumber == o2.phoneNumber) return o1.firstName.compareTo(o2.firstName)
            return o1.phoneNumber.compareTo(o2.phoneNumber)
        }

    }
}