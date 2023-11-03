package com.delvin.uywalkywalker.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import com.delvin.uywalkywalker.databinding.ActivityRegisterBinding
import com.delvin.uywalkywalker.models.Walker
import com.delvin.uywalkywalker.providers.AuthProvider
import com.delvin.uywalkywalker.providers.WalkerProvider

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val authProvider = AuthProvider()
    private val walkerProvider = WalkerProvider()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)


        binding.btnGoToLogin.setOnClickListener{goToLogin()}
        binding.btnRegister.setOnClickListener {register()}
    }

    private fun register(){
        val name=binding.textFieldName.text.toString()
        val lastname=binding.textFieldLastname.text.toString()
        val email=binding.textFieldEmail.text.toString()
        val phone=binding.textFieldPhone.text.toString()
        val password=binding.textFieldPassword.text.toString()
        val confirmpassword=binding.textFieldConfirmPassword.text.toString()

        if (isValidForm(name,lastname, email, phone, password, confirmpassword)){
            authProvider.register(email,password).addOnCompleteListener {
                if (it.isSuccessful){
                    val walker = Walker(
                        id = authProvider.getId(),
                        name = name,
                        lastname = lastname,
                        phone = phone,
                        email = email
                    )

                    walkerProvider.create(walker).addOnCompleteListener {
                        if (it.isSuccessful){
                            Toast.makeText(this@RegisterActivity, "Registro exitoso", Toast.LENGTH_SHORT).show()
                            goToMap()
                        }
                        else{
                            Toast.makeText(this@RegisterActivity, "Hubo un error almacenando los datos del usuario ${it.exception.toString()}", Toast.LENGTH_SHORT).show()
                            Log.d("FIREBASE", "Error: ${it.exception.toString()}")
                        }
                    }
                } else {
                    Toast.makeText(this@RegisterActivity, "Registro fallido ${it.exception.toString()}", Toast.LENGTH_LONG).show()
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
    private fun isValidForm(
        name: String,
        lastname:String,
        email: String,
        phone:String,
        password:String,
        confirmpassword: String
    ): Boolean {
        if (name.isEmpty()){
            Toast.makeText(this, "Debes ingresar tu nombre", Toast.LENGTH_SHORT).show()
            return false
        }
        if (lastname.isEmpty()){
            Toast.makeText(this, "Debes ingresar tu apellido", Toast.LENGTH_SHORT).show()
            return false
        }
        if (email.isEmpty()){
            Toast.makeText(this, "Debes ingresar tu correo electronico", Toast.LENGTH_SHORT).show()
            return false
        }
        if (phone.isEmpty()){
            Toast.makeText(this, "Debes ingresar tu teléfono", Toast.LENGTH_SHORT).show()
            return false
        }
        if (password.isEmpty()){
            Toast.makeText(this, "Debes ingresar tu contraseña", Toast.LENGTH_SHORT).show()
            return false
        }
        if (password.isEmpty()){
            Toast.makeText(this, "Debes ingresar la confirmación de la contraseña", Toast.LENGTH_SHORT).show()
            return false
        }
        if (password!=confirmpassword){
            Toast.makeText(this, "Las contraseñas deben coincidir", Toast.LENGTH_SHORT).show()
            return false
        }
        if (password.length < 6){
            Toast.makeText(this, "Las contraseñas deben tener al menos 6 carácteres", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    private fun  goToLogin(){
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
    }
}