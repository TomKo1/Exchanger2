package com.example.tomek.exchanger2

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity()  {

    // var representing authentication object
    private lateinit var fireBaseAuthe : FirebaseAuth
    // Progress Bar to show the progress of registration process
    private  lateinit var progressDialog : ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        fireBaseAuthe = FirebaseAuth.getInstance()
        btnLogIn.setOnClickListener(clickListener)
        // user can be already logged in -> we should check it
        if(fireBaseAuthe.currentUser != null){
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }
        progressDialog  = ProgressDialog(this)
        textSignUp.setOnClickListener(clickListener)
    }

    private val clickListener = View.OnClickListener { view->
        when(view.id){
            R.id.btnLogIn -> logIn()
            R.id.textSignUp -> {
                finish()
                startActivity(Intent(this, RegisterActivity::class.java))
            }
        }
    }

    // logs in the user
    fun logIn(){
        var email:String=etLogEmail.text.toString().trim()
        var password:String = etLogPassword.text.toString().trim()
        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(this,"Password or E-mail is empty", Toast.LENGTH_SHORT).show()
            return
        }

        progressDialog.setMessage("Loging in...")
        progressDialog.show()


        fireBaseAuthe.signInWithEmailAndPassword(email, password).
                addOnCompleteListener(this, OnCompleteListener<AuthResult> {
                    task ->
                        progressDialog.dismiss()

                        if(task.isSuccessful){
                            finish()
                            startActivity(Intent(this, MainActivity::class.java))

                        }else{
                            Toast.makeText(this,"Failed to Log In",Toast.LENGTH_LONG).show()
                        }


                })





    }



}
