package com.example.tomek.exchanger2.Activities

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.tomek.exchanger2.R
import com.example.tomek.exchanger2.checkIfLoggedAndProceedToMainActiv
import com.example.tomek.exchanger2.projectSendActivationEmail
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*

/**
 *   The class representing
 *   @param mainListener is for registering button and text redirecting to Login activity
 *
 */


class RegisterActivity : AppCompatActivity() {



    private lateinit var  progressDialog:ProgressDialog


    private lateinit var fireBaseAuthe : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        fireBaseAuthe  = FirebaseAuth.getInstance()

        fireBaseAuthe.checkIfLoggedAndProceedToMainActiv(this)

        registerButton.setOnClickListener(mainListener)


        progressDialog  = ProgressDialog(this)
        textSignIn.setOnClickListener(mainListener)

    }


    private val mainListener = View.OnClickListener { view->
        when(view.id){
            R.id.registerButton -> registerUser()
            R.id.textSignIn -> startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun registerUser(){
        var email:String=etEmail.text.toString().trim()
        var password:String = etPassword.text.toString().trim()

        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(this,getString(R.string.password_or_email_empty),Toast.LENGTH_SHORT).show()
            return
        }

        progressDialog.setMessage(getString(R.string.registering_dots))
        progressDialog.show()


        fireBaseAuthe.createUserWithEmailAndPassword(email, password).
                addOnCompleteListener(this, OnCompleteListener<AuthResult> {
                    task ->
                    progressDialog.dismiss()
                    if(task.isSuccessful){
                        //configAndSendVerEmail()
                        fireBaseAuthe.projectSendActivationEmail(this,true)
                        Toast.makeText(this,getString(R.string.registered_success_please_confirm_email),Toast.LENGTH_SHORT).show()
                        finish()
                        startActivity(Intent(this,LoginActivity::class.java))
                    }
                }).addOnFailureListener({
                            e: Exception ->
                                        Toast.makeText(this,e.message,Toast.LENGTH_SHORT).show()

                    })

        }

}
