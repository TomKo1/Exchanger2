package com.example.tomek.exchanger2

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
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

        if(fireBaseAuthe.currentUser != null){
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }

        registerButton.setOnClickListener(mainListener)


        progressDialog  = ProgressDialog(this)
        textSignIn.setOnClickListener(mainListener)

    }


    private val mainListener = View.OnClickListener { view->
        when(view.id){
            R.id.registerButton -> registerUser()
            R.id.textSignIn -> startActivity(Intent(this,LoginActivity::class.java))
        }
    }

    private fun registerUser(){
        var email:String=etEmail.text.toString().trim()
        var password:String = etPassword.text.toString().trim()

        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(this,"Password or E-mail is empty",Toast.LENGTH_SHORT).show()
            return
        }

        progressDialog.setMessage("Registering...")
        progressDialog.show()


        fireBaseAuthe.createUserWithEmailAndPassword(email, password).
                addOnCompleteListener(this, OnCompleteListener<AuthResult> {
                    task ->
                    progressDialog.dismiss()
                    if(task.isSuccessful){

                        Toast.makeText(this,"Registered Successfully",Toast.LENGTH_SHORT).show()
                        finish()
                        startActivity(Intent(this, MainActivity::class.java))

                    }
                }).addOnFailureListener({
                            e: Exception ->
                                        Toast.makeText(this,e.message,Toast.LENGTH_SHORT).show()

                    })










    }

}
