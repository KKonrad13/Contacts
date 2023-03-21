package com.example.contacts

data class Contact(
    var index: Int = -1,
    var firstName: String = "",
    var lastName: String = "",
    var phoneNumber: String = "",
    var notes: String = "",
    var imageName: String = ""
)