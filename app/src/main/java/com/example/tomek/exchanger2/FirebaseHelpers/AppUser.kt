package com.example.tomek.exchanger2.FirebaseHelpers



data class AppUser(val editName: String,
              val editCountry: String,
              val editPhoneNumber:String,
              val editDescription: String,
              val editBirthDay:String) {



    // TODO maybe method checking if all fields are set ??

    // empty constructor to use for firebase
    constructor() : this("", "",
            "", "", "")


}
