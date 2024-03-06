package com.nothing.societyuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.nothing.societyuser.databinding.ActivityLoginBinding
import com.nothing.societyuser.easyWork.EaseWorkActivity

class LoginActivity : AppCompatActivity() {


    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Login button Intent
        binding.loginBtn.setOnClickListener {
            loginUsser()

        }
        //back button intent
        binding.loginBackBtn.setOnClickListener{
            intentFun(WelcomeSignUpLogin::class.java)
        }
        //Text Intent to registation
        binding.registationTextBtn.setOnClickListener {
            intentFun(RegistrationActivity::class.java)
        }



    }

    private fun loginUsser() {
        val email = binding.loginEmailEdt.text.toString()
        val password = binding.loginPasswordEdt.text.toString()
        var isValidated:Boolean = validateRegistration(email,password)
        if (!isValidated) {
            return
        }

        loginAccountInFirebase(email,password)
    }

    private fun loginAccountInFirebase(email: String, password: String) {
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(){
            changeProgress(false)
            if (it.isSuccessful){
                //login is success
                var intent = Intent(this, BottomActivity::class.java)
                startActivity(intent)

            }else{
                toastFun("enter right password and email")
            }
        }

    }

    //progressBar on button
    fun changeProgress(inProgress: Boolean) {
        if (inProgress) {
            binding.progressBar.visibility = View.VISIBLE
            binding.loginBtn.visibility = View.GONE
        }else{
            binding.progressBar.visibility = View.GONE
            binding.loginBtn.visibility = View.VISIBLE
        }
    }

    //validation
    fun validateRegistration(userEmail: String, userPassword: String, ): Boolean {
        // empty format validate
        if (userEmail.isEmpty()) {
            binding.loginEmailEdt.error = "Email is required"
            return false
        }

        if (userPassword.isEmpty()) {
            binding.loginPasswordEdt.error = "Password is required"
            return false
        }

        // format Validate
        val emailRegex = Regex("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")
        if (!emailRegex.matches(userEmail)) {
            binding.loginEmailEdt.error = "Invalid email address"
            return false
        }
        if (userPassword.length < 8) {
            binding.loginPasswordEdt.error = "Password should be at least 8 characters long"
            return false
        }
        return true

    }


    //helper function
    //intent
    fun intentFun(destination : Class<*>){
        var intent = Intent(this, destination)
        startActivity(intent)

    }
    //toast
    fun toastFun(message : String){
        var toast = Toast.makeText(this, message, Toast.LENGTH_LONG).show()

    }

}
