package com.avanade.mobilet1.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import com.avanade.mobilet1.R
import com.avanade.mobilet1.extensions.Extensions.toast
import com.avanade.mobilet1.utils.FirebaseUtils
import com.avanade.mobilet1.utils.FirebaseUtils.database
import com.avanade.mobilet1.utils.FirebaseUtils.firebaseAuth
import com.avanade.mobilet1.viewmodels.AuthViewModel
import com.avanade.mobilet1.views.fragments.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private val homeFragment = HomeFragment()
    private val addMovieFragment = AddMovieFragment()
    private val myMoviesFragment = MyMoviesFragment()
    private val profileFragment = ProfileFragment()
    private val searchFragemnt = SearchFragment()

    lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        authViewModel = AuthViewModel(this.application)


        val refUsuario = database.getReference("usuarios")
        val refFilme = database.getReference("filmes")

        replaceFragment(homeFragment)

        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> replaceFragment(homeFragment)
                R.id.search_movies -> replaceFragment(searchFragemnt)
                R.id.menu_profile -> replaceFragment(profileFragment)
            }
            true
        }

    }

    override fun onStart() {
        super.onStart()

        authViewModel.userOff()
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }
}