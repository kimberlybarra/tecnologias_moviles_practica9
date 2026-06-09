package com.kimberly.marketplaceucsm


import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText

    private lateinit var btnRegister: Button
    private lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)

        btnRegister = findViewById(R.id.btnRegister)
        btnLogin = findViewById(R.id.btnLogin)

        btnRegister.setOnClickListener {
            registerUser()
        }

        btnLogin.setOnClickListener {
            loginUser()
        }
    }

    private fun validateInputs(): Boolean {

        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()

        if (email.isEmpty()) {

            etEmail.error = "Ingrese correo"
            etEmail.requestFocus()
            return false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

            etEmail.error = "Correo inválido"
            etEmail.requestFocus()
            return false
        }

        if (password.isEmpty()) {

            etPassword.error = "Ingrese contraseña"
            etPassword.requestFocus()
            return false
        }

        if (password.length < 6) {

            etPassword.error = "Mínimo 6 caracteres"
            etPassword.requestFocus()
            return false
        }

        return true
    }

    private fun registerUser() {

        if (!validateInputs()) return

        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()

        auth.createUserWithEmailAndPassword(
            email,
            password
        ).addOnCompleteListener {

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

    private fun loginUser() {

        if (!validateInputs()) return

        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()

        auth.signInWithEmailAndPassword(
            email,
            password
        ).addOnCompleteListener {

            if (it.isSuccessful) {

                startActivity(
                    Intent(
                        this,
                        HomeActivity::class.java
                    )
                )

                finish()

            } else {

                Toast.makeText(
                    this,
                    "Credenciales incorrectas",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}