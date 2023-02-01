package com.route.todoapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.route.todoapp.R
import com.route.todoapp.database_model.MyDatabase
import com.route.todoapp.home.HomeActivity


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed(object: Runnable{
            override fun run() {
                startHomeScreen()
            }

        },2000)

    }

    private fun startHomeScreen() {
        val intent = Intent(this,HomeActivity::class.java )
        startActivity(intent)

    }

}