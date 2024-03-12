package com.nothing.societyuser.welcome

import android.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.nothing.societyuser.databinding.ActivityRegistrationBinding
import com.nothing.societyuser.fragment.BottomActivity

class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var societyNameAutoComplete: AutoCompleteTextView
    private lateinit var societyAdapter: ArrayAdapter<String>
    private lateinit var  societyId:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // TODO: use societies
        var societies: HashMap<String, String> = HashMap<String, String>()
        val db = Firebase.firestore
        val user = Firebase.auth.currentUser

        db.collection("societies")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val societyName = document.data["name"].toString()
                    val id = document.id

                    societies[id] = societyName
                }

                Log.d("", societies.toString())
            }
            .addOnFailureListener { exception ->
                Log.e("error", "Failed to fetch societies")
            }

        //autotextview

        societyNameAutoComplete = binding.registationSocietyNameAuto
        societyAdapter = AutoTextSocName(
            this,
            android.R.layout.simple_dropdown_item_1line,
            societies.values.toMutableList(),
            societies
        )
        societyNameAutoComplete.setAdapter(societyAdapter)

        societyNameAutoComplete.setOnItemClickListener { _, _, position, _ ->
            val societyName = societyAdapter.getItem(position) ?: ""
            societyId = societies.entries.find { it.value == societyName }?.key ?: ""
            Toast.makeText(this, "Selected Society: $societyId", Toast.LENGTH_SHORT).show()

        }



        //Intent Back Button
        binding.registraionBackBtn.setOnClickListener(){
            intentFun(WelcomeSignUpLogin::class.java)
        }

        //Intent Registation Button
        binding.registraionBtn.setOnClickListener(){

            createAccounnt()
            //TODO:change home activity
            //TODO: data send To firebase

        }

        //Text Intent to Login
        binding.loginTextBtn.setOnClickListener(){


            intentFun(LoginActivity::class.java)

        }

    }

    //intent
    fun intentFun(destination : Class<*>){
        var intent = Intent(this, destination)
        startActivity(intent)
    }
    //toast
    fun toastFun(message : String) {
        var toast = Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    //create Account
    fun createAccounnt(){
        var userName = binding.registationUserNameEdt.text.toString()
        var userEmail = binding.registationEmailEdt.text.toString()
        var userPassword = binding.registationPasswordEdt.text.toString()
        var userConfirmPassword = binding.registationRePasswordEdt.text.toString()

        //TODO: userSociety name edt change to auto search edit textBox
        var userSocietyName = "surat"    //binding.registationSocietyNameEdt.text.toString()
        var userHouseNo = binding.registationHouseNoEdt.text.toString()

        var isValidated:Boolean = validateRegistration(userName,userEmail,userPassword,userConfirmPassword,userSocietyName,userHouseNo)
        if (!isValidated) {
            return
        }
        toastFun(societyId.toString())
        createAccounntInFirebase(userName, userEmail, userPassword, societyId, userHouseNo)
    }

    private fun createAccounntInFirebase(
        userName: String,
        userEmail: String,
        userPassword: String,
        userSocietyName: String,
        userHouseNo: String
    ) {
        changeProgress(true)
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(){
            changeProgress(false)
            if (it.isSuccessful){
                toastFun("Welcome")
                intentFun(BottomActivity::class.java)
                firebaseAuth.currentUser?.sendEmailVerification()
            }else{
                toastFun(""+it.exception!!.localizedMessage)
            }

        }
    }

    //progressBar on button
     fun changeProgress(inProgress: Boolean) {
         if (inProgress) {
             binding.progressBar.visibility = View.VISIBLE
             binding.registraionBtn.visibility = View.GONE
         }else{
             binding.progressBar.visibility = View.GONE
             binding.registraionBtn.visibility = View.VISIBLE
         }
    }

//validation
    fun validateRegistration(
        userName: String,
        userEmail: String,
        userPassword: String,
        userConfirmPassword: String,
        userSocietyName: String,
        userHouseNo: String
    ): Boolean {
        // Check if any field is empty
        if (userName.isEmpty()) {
            println()
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
        println("Validation successful")
        return true
    }


}