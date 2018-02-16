package com.example.tomek.exchanger2

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_profile.*


class ProfileActivity : AppCompatActivity() {

    // var representing authentication object
    private var fireBaseAuthe : FirebaseAuth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        //just for make sure
        if(fireBaseAuthe.currentUser == null){
            // start the profile activity
            finish()
            startActivity(Intent(this,MainActivity::class.java))
            return
        }

        //"Welcome " + fireBaseAuthe.currentUser!!.email.toString()
        var currentUsrEmail:String=fireBaseAuthe.currentUser!!.email.toString()
        txtLoggedIn.text =String.format(resources.getString(R.string.logged_user_greeting),currentUsrEmail)

        btnLogOut.setOnClickListener(clickListener)




        }

        var clickListener = View.OnClickListener {
            view->
                when(view.id){
                    R.id.btnLogOut ->
                    {
                        fireBaseAuthe.signOut()
                        finish()
                        startActivity(Intent(this,MainActivity::class.java))
                    }
                }
        }








}
