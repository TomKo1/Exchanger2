package com.example.tomek.exchanger2.Activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Gravity
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import android.util.Log
import com.example.tomek.exchanger2.MainActivityFragments.FullProfileView
import com.example.tomek.exchanger2.MainActivityFragments.ProfileEdit
import com.example.tomek.exchanger2.ProjectValues
import com.example.tomek.exchanger2.R
import com.google.firebase.auth.FirebaseUser


class MainActivity : AppCompatActivity() {

    private lateinit var fireBaseAuthe : FirebaseAuth

    // AuthStateListener reacting for signOut operations
    private val mAuthStateListener: FirebaseAuth.AuthStateListener =
        FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            if (user != null) {
                Log.d(ProjectValues.MAINACTIVITY_TAG, "onAuthStateChanged: signed_in: " + user.uid)
            } else {
                Log.d(ProjectValues.MAINACTIVITY_TAG, "onAuthStateChanged: signed_out")
                Toast.makeText(this, getString(R.string.signed_out), Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                finish()
                startActivity(intent)
            }
        }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // emergency check if the user is logged
        fireBaseAuthe = FirebaseAuth.getInstance()

        val user: FirebaseUser? = fireBaseAuthe.currentUser
        if(user != null && !user.isEmailVerified ){
            finish()
            startActivity(Intent(this, LoginActivity::class.java))
        }


        configDrawer()

        cofigLogOutBtn()

        //TODO change it for the map with the nearest location of products
        makeTansaction(FullProfileView.newInstance())


        // temporary code to detect which user is logged
        var currentUsrEmail:String=fireBaseAuthe.currentUser!!.email.toString()
        Toast.makeText(this,getString(R.string.user_greetings_formatted,currentUsrEmail),Toast.LENGTH_LONG).show()




        }

    //configuration of Drawer and Hamburger
    private fun configDrawer(){
        val toggle = ActionBarDrawerToggle(this, drawer_layout, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
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
                    R.id.nav_profile ->makeTansaction(FullProfileView.newInstance())
                    R.id.nav_chat -> makeTansaction(FullProfileView.newInstance())
                    R.id.nav_search->makeTansaction(FullProfileView.newInstance())
                    R.id.nav_edit_profile->makeTansaction(ProfileEdit.newInstance())
                    R.id.nav_settings->makeTansaction(FullProfileView.newInstance())
                }

              toggle()

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


    private fun cofigLogOutBtn(){
        btnDrawLogOut.setOnClickListener{ view ->
            FirebaseAuth.getInstance().signOut()
        }
    }


    // here we add fragment programatically
    private fun makeTansaction( fragmentToAdd: Fragment){

        if(drawer_layout.isDrawerOpen(Gravity.START)){
            drawer_layout.closeDrawer(Gravity.START)
            Toast.makeText(this,"Closing Drawer",Toast.LENGTH_SHORT).show()
        }

        val fragmentManager: FragmentManager = supportFragmentManager

        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.mainActivFrag, fragmentToAdd)

        fragmentTransaction.addToBackStack(null)

        fragmentTransaction.commit()



    }


    override fun onStop() {
        super.onStop()
        FirebaseAuth.getInstance().removeAuthStateListener(mAuthStateListener)

    }

//TODO code it -> profile customization (profile pic, profile name, location ..., contact)
//TODO change deprecated progress dialog

    override fun onStart() {
        super.onStart()
        FirebaseAuth.getInstance().addAuthStateListener(mAuthStateListener)
    }



}
