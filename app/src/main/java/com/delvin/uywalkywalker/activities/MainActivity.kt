package com.delvin.uywalkywalker.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import com.delvin.uywalkywalker.databinding.ActivityMainBinding
import com.delvin.uywalkywalker.providers.AuthProvider


class MainActivity : AppCompatActivity() {

    private lateinit var biding:ActivityMainBinding
    val authProvider = AuthProvider()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        biding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(biding.root)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        biding.btnRegister.setOnClickListener{goToRegister()}
        biding.btnLogin.setOnClickListener{login()}

    }

    private fun login() {
        val email = biding.textFieldEmail.text.toString()
        val password = biding.textFieldPassword.text.toString()

        if (isValidFor(email,password)){
            authProvider.login(email,password).addOnCompleteListener {
                if (it.isSuccessful){
                    goToMap()
                }
                else {
                    Toast.makeText(this@MainActivity, "Error iniciando sesión", Toast.LENGTH_SHORT).show()
                    Log.d("FIREBASE", "Error: ${it.exception.toString()}")
                }
            }
        }

    }

    private fun goToMap(){
        val i = Intent(this, MapActivity::class.java)
        i.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(i)
    }

    private fun isValidFor(email: String, password:String): Boolean{

        if (email.isEmpty()){
            Toast.makeText(this, "Ingresa tu correo eléctronico", Toast.LENGTH_SHORT).show()
            return false
        }
        if (password.isEmpty()){
            Toast.makeText(this, "Ingresa tu contraseña", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun goToRegister(){
        val i = Intent(this, RegisterActivity::class.java)
        startActivity(i)
    }

    override fun onStart() {
        super.onStart()
        if (authProvider.existSession()){
            goToMap()
        }
    }
}