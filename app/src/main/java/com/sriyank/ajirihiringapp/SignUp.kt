package com.sriyank.ajirihiringapp

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUp: Fragment(){

    private lateinit var signUpEmail: TextInputLayout
    private lateinit var signUpPassword: TextInputLayout
    private lateinit var signUpButton: Button
    private lateinit var firebaseAuth: FirebaseAuth

    private var email = ""
    private var password = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.signup_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signUpEmail = view.findViewById(R.id.signup_email_edx)
        signUpPassword = view.findViewById(R.id.signup_pwd_edx)
        signUpButton = view.findViewById(R.id.signup_reg_btn)
        firebaseAuth = FirebaseAuth.getInstance()

        signUpButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                validateData()
            }
        }
    }

    private suspend fun validateData() {
        email = signUpEmail.editText?.text.toString()
        password = signUpPassword.editText?.text.toString()

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            //invalid email format
            signUpEmail.editText?.error = "Invalid email format"
        }else if(TextUtils.isEmpty(password)){
            //no password entered
            signUpPassword.editText?.error = "Please enter password"
        }else if(password.length < 6){
            signUpPassword.editText?.error = "Password must be at least 6 characters long"
        }else{
            firebaseSignUp()
        }
    }

    private suspend fun firebaseSignUp() {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val firebaseUser = firebaseAuth.currentUser
                Log.d("SignUpActivity", "The current user is: $firebaseUser")
                val email = firebaseUser?.email
                Toast.makeText(requireActivity(), "Account created with email$email", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_signUp_to_userNamePic)
            }
            .addOnFailureListener { e ->
                Toast.makeText(requireActivity(), "Logged in failed due to $e", Toast.LENGTH_LONG).show()
            }
    }


}