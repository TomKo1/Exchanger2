package com.example.tomek.exchanger2.Activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.tomek.exchanger2.R
import com.example.tomek.exchanger2.checkIfLoggedAndProceedToMainActiv
import com.example.tomek.exchanger2.projectSendActivationEmail
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_verify_email.*

class ActivityVerifyEmail : AppCompatActivity() {

    private lateinit var fireBaseAuthe : FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_email)

        configResendBtn()

        fireBaseAuthe = FirebaseAuth.getInstance()

        fireBaseAuthe.checkIfLoggedAndProceedToMainActiv(this)





    }


    override fun onResume() {
        super.onResume()

        proceedToAppIfActivInMeantime()

    }


    private fun proceedToAppIfActivInMeantime(){
        FirebaseAuth.getInstance().currentUser?.reload()?.addOnSuccessListener {
            void ->
            var  user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
                if( user != null && user.isEmailVerified){
                    finish()
                    startActivity(Intent(this, MainActivity::class.java))
                }
        }
    }




    private fun configResendBtn(){
        btnResendEmail.setOnClickListener {
            view ->
                fireBaseAuthe.projectSendActivationEmail(this,false)
        }

    }

}
