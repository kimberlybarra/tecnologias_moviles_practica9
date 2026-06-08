package com.kimberly.firebaseauthapp

import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        val email = findViewById<EditText>(R.id.etEmail)
        val password = findViewById<EditText>(R.id.etPassword)
        val button = findViewById<Button>(R.id.btnRegister)

        FirebaseMessaging.getInstance().token
            .addOnSuccessListener {

                Log.d(
                    "TOKEN_FCM",
                    it
                )
            }
        button.setOnClickListener {

            val mail = email.text.toString().trim()
            val pass = password.text.toString().trim()

            if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
                email.error = "Correo inválido"
                return@setOnClickListener
            }

            if (pass.length < 6) {
                password.error = "Mínimo 6 caracteres"
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(mail, pass)
                .addOnCompleteListener {

                    if (it.isSuccessful) {

                        Toast.makeText(
                            this,
                            "Usuario registrado",
                            Toast.LENGTH_LONG
                        ).show()

                    } else {

                        Toast.makeText(
                            this,
                            it.exception?.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }

    }
}