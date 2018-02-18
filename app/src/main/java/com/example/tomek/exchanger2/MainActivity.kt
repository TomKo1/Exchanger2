package com.example.tomek.exchanger2

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var fireBaseAuthe : FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fireBaseAuthe = FirebaseAuth.getInstance()
        if(fireBaseAuthe.currentUser == null){
            finish()
            startActivity(Intent(this, RegisterActivity::class.java))
            return
        }

        // temporary
        var currentUsrEmail:String=fireBaseAuthe.currentUser!!.email.toString()
        Toast.makeText(this,"Welcome "+currentUsrEmail,Toast.LENGTH_LONG).show()




        }
//fireBaseAuthe.signOut()









}
