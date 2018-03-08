package com.example.tomek.exchanger2.FirebaseHelpers

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.tomek.exchanger2.Activities.LoginActivity
import com.example.tomek.exchanger2.ProjectValues
import com.example.tomek.exchanger2.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

/**
 *  Simple class  operating on Firebase database
 */
class FireBaseHelper(private val userValues: AppUser) {



     fun updateUserInfo(context: Context){

         val user:FirebaseUser? = FirebaseAuth.getInstance().currentUser


         if(user == null){
             context.startActivity(Intent(context, LoginActivity::class.java))
         }else {

             val id: String = user.uid

             val fireBaseDb: DatabaseReference = FirebaseDatabase.getInstance().reference




             fireBaseDb.child(ProjectValues.USERS_NODE_TAG).child(id).setValue(userValues).addOnCompleteListener { task ->
                 if (task.isSuccessful) {

                     Toast.makeText(context, context.getString(R.string.profile_info_updated), Toast.LENGTH_SHORT).show()



                 } else {
                     Toast.makeText(context, context.getString(R.string.error_occured_pls_try_later), Toast.LENGTH_SHORT).show()

                 }
             }

         }

    }


    //TODO implement method to fetch data from FireBase to show nick & other information



}