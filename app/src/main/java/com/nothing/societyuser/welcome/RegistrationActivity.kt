package com.nothing.societyuser.welcome

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.nothing.societyuser.databinding.ActivityRegistrationBinding
import com.nothing.societyuser.fragment.BottomActivity

class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var societyNameAutoComplete: AutoCompleteTextView
    private lateinit var societyAdapter: ArrayAdapter<String>
    private var societyId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val societies: HashMap<String, String> = HashMap()
        val db = FirebaseFirestore.getInstance()

        db.collection("societies")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val societyName = document.data["name"].toString()
                    val id = document.id

                    societies[id] = societyName
                }

                Log.d("", societies.toString())
                initAutoCompleteTextView(societies)
            }
            .addOnFailureListener { exception ->
                Log.e("error", "Failed to fetch societies", exception)
            }

        //Intent Back Button
        binding.registraionBackBtn.setOnClickListener {
            intentFun(WelcomeSignUpLogin::class.java)
        }

        //Intent Registation Button
        binding.registraionBtn.setOnClickListener {
            createAccount()
        }

        //Text Intent to Login
        binding.loginTextBtn.setOnClickListener {
            intentFun(LoginActivity::class.java)
        }
    }

    // Initialize AutoCompleteTextView
    private fun initAutoCompleteTextView(societies: HashMap<String, String>) {
        societyNameAutoComplete = binding.registationSocietyNameAuto
        societyAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line,
            societies.values.toMutableList()
        )
        societyNameAutoComplete.setAdapter(societyAdapter)

        societyNameAutoComplete.setOnItemClickListener { _, _, position, _ ->
            val societyName = societyAdapter.getItem(position) ?: ""
            societyId = societies.entries.find { it.value == societyName }?.key ?: ""
            Toast.makeText(this, "Selected Society: $societyId", Toast.LENGTH_SHORT).show()
        }
    }

    // Create account
    private fun createAccount() {
        val userName = binding.registationUserNameEdt.text.toString()
        val userEmail = binding.registationEmailEdt.text.toString()
        val userPassword = binding.registationPasswordEdt.text.toString()
        val userConfirmPassword = binding.registationRePasswordEdt.text.toString()
        val userHouseNo = binding.registationHouseNoEdt.text.toString()

        if (!validateRegistration(
                userName,
                userEmail,
                userPassword,
                userConfirmPassword,
                societyId,
                userHouseNo
            )
        ) {
            return
        }
        if(societyId.isEmpty() || societyId.isNullOrEmpty() || societyId.isEmpty()){
            toastFun("Please Select Society Not only Search")
            return
        }

        changeProgress(true)

        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
            .addOnCompleteListener { task ->
                changeProgress(false)

                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser

                    if (user != null) {
                        // User registration successful, now add data to "member" collection
                        val db = FirebaseFirestore.getInstance()
                        val memberData = hashMapOf(
                            "userName" to userName,
                            "userEmail" to userEmail,
                            "societyId" to societyId,
                            "userHouseNo" to userHouseNo
                        )

                        db.collection("member")
                            .document(user.uid)
                            .set(memberData)
                            .addOnSuccessListener {
                                toastFun("Welcome")
                                intentFun(BottomActivity::class.java)
                                user.sendEmailVerification()
                            }
                            .addOnFailureListener { e ->
                                toastFun("Error adding data to member collection: ${e.message}")
                            }
                    } else {
                        toastFun("User is null.")
                    }
                } else {
                    toastFun("Error: ${task.exception?.localizedMessage}")
                }
            }
    }

    // Show toast
    private fun toastFun(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    // Change progress bar visibility
    private fun changeProgress(inProgress: Boolean) {
        binding.progressBar.visibility = if (inProgress) View.VISIBLE else View.GONE
        binding.registraionBtn.visibility = if (inProgress) View.GONE else View.VISIBLE
    }

    // Validate registration fields
    private fun validateRegistration(
        userName: String,
        userEmail: String,
        userPassword: String,
        userConfirmPassword: String,
        userSocietyName: String,
        userHouseNo: String
    ): Boolean {
        // Check if any field is empty
        if (userName.isEmpty()) {
            binding.registationUserNameEdt.error = "Username is required"
            return false
        }

        if (userEmail.isEmpty()) {
            binding.registationEmailEdt.error = "Email is required"
            return false
        }

        if (userPassword.isEmpty()) {
            binding.registationPasswordEdt.error = "Password is required"
            return false
        }

        if (userConfirmPassword.isEmpty()) {
            binding.registationRePasswordEdt.error = "Confirm Password is required"
            return false
        }

        if (userHouseNo.isEmpty()) {
            binding.registationHouseNoEdt.error = "House Number is required"
            return false
        }

        // Validate email format
        val emailRegex = Regex("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")
        if (!emailRegex.matches(userEmail)) {
            binding.registationEmailEdt.error = "Invalid email address"
            return false
        }

        // Validate password length
        if (userPassword.length < 8) {
            binding.registationPasswordEdt.error = "Password should be at least 8 characters long"
            return false
        }

        // Check if passwords match
        if (userPassword != userConfirmPassword) {
            binding.registationRePasswordEdt.error = "Passwords do not match"
            return false
        }

        // If all validations pass
        Log.d("RegistrationActivity", "Validation successful")
        return true
    }

    // Start intent
    private fun intentFun(destination: Class<*>) {
        val intent = Intent(this, destination)
        startActivity(intent)
    }
}
