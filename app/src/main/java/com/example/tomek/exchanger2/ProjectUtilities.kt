package com.example.tomek.exchanger2

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.tomek.exchanger2.Activities.ActivityVerifyEmail
import com.example.tomek.exchanger2.Activities.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

/**
 *  extension functions for usage in Project -> used instead of Java's utility classes
 */



// method that checks if the user is logged in and activated and
// if those conditions are true it proceeds to MainActivity
fun FirebaseAuth.checkIfLoggedAndProceedToMainActiv( activity: AppCompatActivity){
    val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
    if(user != null && user.isEmailVerified ){
        activity.finish()
        activity.startActivity(Intent(activity, MainActivity::class.java))
    }
}



// function responsible for sending activation email to accurate user
fun FirebaseAuth.projectSendActivationEmail( activity: AppCompatActivity, isRegisterActiv: Boolean){
    val user : FirebaseUser? = FirebaseAuth.getInstance().currentUser

    // kotlin (not only Kotlin -> Swift!) great null - safe call
    user?.sendEmailVerification()?.addOnCompleteListener {
        task ->
            if(task.isSuccessful){
                Toast.makeText(activity,"Email sent!", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(activity,"Problem while sending email - please try later", Toast.LENGTH_SHORT).show()
                if(isRegisterActiv){
                    activity.finish()
                    activity.startActivity(Intent(activity, ActivityVerifyEmail::class.java))
                }
            }
    }
}










