package com.example.tomek.exchanger2

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


// Progress Bar to show the progress of registration process
 private  var  progressDialog : ProgressDialog = ProgressDialog(this)
// TextViews:
    //etEmail ;  etPassword
    // registerButton ; textSignIn

    // var representing authentication object
    private var fireBaseAuthe : FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        // setting onClickListener
        registerButton.setOnClickListener(btnListener);
    }

    // btnListner with a special lambda
    private val btnListener = View.OnClickListener { view->
       // when is like Java's switch
        when(view.id){
            R.id.registerButton -> registerUser()
        }
    }

    // I know Unit is reduntant -> but this are (almost) my first lines in Kotlin
    private fun registerUser():Unit{
        var email:String=etEmail.text.toString().trim()
        var password:String = etPassword.text.toString().trim()
        if(email.isEmpty() || password.isEmpty()){
            // user cannot go futher without filling this fields
            Toast.makeText(this,"Password or E-mail is empty",Toast.LENGTH_SHORT).show()
            return
        }

        // set message in Kotlin???
        progressDialog.setMessage("Registering...")
        progressDialog.show()

        // we create the user
        // we attach listener to see when the registration is done
        fireBaseAuthe.createUserWithEmailAndPassword(email, password).
                addOnCompleteListener(this, OnCompleteListener {
                    view ->
                    if(view.isSuccessful){
                        // user is successfully registered and loggwed in
                        // we will start the profile activity here
                        Toast.makeText(this,"Registered Successfully",Toast.LENGTH_SHORT).show()

                    }else{
                        Toast.makeText(this," Failed to register",Toast.LENGTH_SHORT).show()

                    }

                })










    }

}
