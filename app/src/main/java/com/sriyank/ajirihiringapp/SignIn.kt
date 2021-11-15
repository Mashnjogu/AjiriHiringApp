package com.sriyank.ajirihiringapp

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignIn : Fragment(){

    private lateinit var loginEmail: TextInputLayout
    private lateinit var loginPwd: TextInputLayout
    private lateinit var loginbtn: Button
    private lateinit var loginRegister: TextView
    private lateinit var auth: FirebaseAuth

    private var email = ""
    private var password = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        loginEmail = view.findViewById(R.id.login_email_edx)
        loginPwd = view.findViewById(R.id.login_pwd_edx)
        loginbtn = view.findViewById(R.id.login_btn)
        loginRegister = view.findViewById(R.id.login_rgs_txt)

//        checkUser()
        loginbtn.setOnClickListener {
            validateData()
        }

        CoroutineScope(Dispatchers.Default).launch {
            loginRegister.setOnClickListener {view ->
                view.findNavController().navigate(R.id.action_signIn_to_signUp)
            }
        }

    }

    private fun validateData() {
        email = loginEmail.editText?.text.toString()
        password = loginPwd.editText?.text.toString()

        //validate data
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            //invalid email format
            loginEmail.editText?.error = "Invalid email format"
        }else if(TextUtils.isEmpty(password)){
            //no password entered
            loginPwd.editText?.error = "Please enter password"
        }
        else{
            firebaseLogin()
        }
    }

    private fun firebaseLogin() {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val firebaseUser = auth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(requireActivity(), "Logged in as $email", Toast.LENGTH_LONG).show()
                startActivity(Intent(activity, MainActivity::class.java))
                requireActivity().finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(requireActivity(), "Logged in failed due to $e", Toast.LENGTH_LONG).show()
            }
    }

    private fun checkUser() {
        val currentUser = auth.currentUser
        if (currentUser != null){
            //move to next screen
            startActivity(Intent(activity, MainActivity::class.java))
            requireActivity().finish()
        }
    }
}