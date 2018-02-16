package com.example.tomek.exchanger2

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


// Progress Bar to show the progress of registration process
 private lateinit var  progressBar : ProgressBar
// TextViews:
    //etEmail ;  etPassword
    // registerButton ; textSignIn

    // var representing authentication object
    private lateinit var fireBaseAuthe : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fireBaseAuthe  = FirebaseAuth.getInstance()
        if(fireBaseAuthe.currentUser != null){
            // start the profile activity
            finish()
            startActivity(Intent(this,ProfileActivity::class.java))
        }
        // setting onClickListener
        registerButton.setOnClickListener(btnListener)


        progressBar  = ProgressBar(this)

    }

    // btnListner with a special lambda
    private val btnListener = View.OnClickListener { view->
       // when is like Java's switch
        when(view.id){
            R.id.registerButton -> registerUser()
            R.id.textSignIn -> startActivity(Intent(this,LoginActivity::class.java))
        }
    }

    // I know Unit is reduntant
    private fun registerUser():Unit{
        var email:String=etEmail.text.toString().trim()
        var password:String = etPassword.text.toString().trim()
        if(email.isEmpty() || password.isEmpty()){
            // user cannot go futher without filling this fields
            Toast.makeText(this,"Password or E-mail is empty",Toast.LENGTH_SHORT).show()
            return
        }

        // set message in Kotlin???
        //progressDialog.setMessage("Registering...")
        //progressDialog.show()
        progressBar.setVisibility(View.VISIBLE);  //To show ProgressBar

        // we create the user
        // we attach listener to see when the registration is done
        fireBaseAuthe.createUserWithEmailAndPassword(email, password).
                addOnCompleteListener(this, OnCompleteListener<AuthResult> {
                    task ->
                    progressBar.setVisibility(View.INVISIBLE);  //To show ProgressBar
                    if(task.isSuccessful){
                        Toast.makeText(this,"Registered Successfully",Toast.LENGTH_SHORT).show()
                        // the registration is successful -> we start the profile activity
                        finish()
                        startActivity(Intent(this,ProfileActivity::class.java))

                    }else{
                        Toast.makeText(this," Failed to register",Toast.LENGTH_SHORT).show()

                    }

                })










    }

}
