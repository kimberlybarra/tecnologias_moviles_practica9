package com.kimberly.marketplaceucsm

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging

class HomeActivity : AppCompatActivity() {

    private lateinit var txtUser: TextView
    private lateinit var btnLogout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        txtUser = findViewById(R.id.txtUser)
        btnLogout = findViewById(R.id.btnLogout)

        val user = FirebaseAuth.getInstance().currentUser

        txtUser.text =
            "Bienvenido\n${user?.email}"

        btnLogout.setOnClickListener {

            FirebaseAuth.getInstance().signOut()

            startActivity(
                Intent(
                    this,
                    MainActivity::class.java
                )
            )

            finish()
        }
        FirebaseMessaging.getInstance().token
            .addOnSuccessListener {

                Log.d(
                    "TOKEN_FCM",
                    it
                )
            }
    }
}