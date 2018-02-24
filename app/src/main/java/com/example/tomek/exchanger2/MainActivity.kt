package com.example.tomek.exchanger2

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBar
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Gravity
import android.view.MenuItem
import android.widget.Toast
import android.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var fireBaseAuthe : FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // emergency check if the user is logged
        fireBaseAuthe = FirebaseAuth.getInstance()
        if(fireBaseAuthe.currentUser == null){
            finish()
            startActivity(Intent(this, RegisterActivity::class.java))
            return
        }

        configDrawer()



        // temporary code to detect which user is logged
        var currentUsrEmail:String=fireBaseAuthe.currentUser!!.email.toString()
        Toast.makeText(this,"Welcome "+currentUsrEmail,Toast.LENGTH_LONG).show()




        }
//fireBaseAuthe.signOut()

    private fun configDrawer(){
        val toggle = ActionBarDrawerToggle(this, drawer_layout,R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        val actionBar = supportActionBar

        if(actionBar !=null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeButtonEnabled(true)
        }

        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        // public boolean onNavigationItemSelected(MenuItem item);
        nav_view.setNavigationItemSelectedListener {
            item ->
                when(item.itemId){

                }
                drawer_layout.closeDrawer(Gravity.START)

                true
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            android.R.id.home -> toggle()
        }
        return true
    }




    // detects the state of drawer and closes or opens it
    private fun toggle(){
        if(drawer_layout.isDrawerOpen(Gravity.START)){
            drawer_layout.closeDrawer(Gravity.START)
        }else{
            drawer_layout.openDrawer(Gravity.START)
        }
    }



}
