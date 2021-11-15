package com.sriyank.ajirihiringapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.navigation.NavigationView

class SignInActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostSignFrag) as NavHostFragment
        navController = navHostFragment.navController
    }
}