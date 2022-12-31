package com.example.kotlincontact.Models

class Contact(name:String, lastName:String, company:String, age:Int, weight:Double, address:String, phone:String, email:String, photo:Int) {
     var name:String = ""
     var lastName:String = ""
     var company:String = ""
     var age:Int = 0
     var weight:Double = 0.0
     var address:String = ""
     var phone:String = ""
     var email:String = ""
     var photo:Int = 0

    init {
        this.name = name
        this.lastName = lastName
        this.company = company
        this.age = age
        this.weight = weight
        this.address = address
        this.phone = phone
        this.email = email
        this.photo = photo
    }

}