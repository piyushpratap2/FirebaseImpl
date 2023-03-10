package com.example.firebaseimpl

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.firebaseimpl.databinding.ActivityMainBinding
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*

import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var sendVerificationId:String
    private lateinit var dialog: AlertDialog
    private lateinit var credential:PhoneAuthCredential
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        val builder = AlertDialog.Builder(this)
        builder.setMessage("Please wait...")
        builder.setTitle("Loading")
        builder.setCancelable(false)
        dialog = builder.create()
        if(auth.currentUser!=null){
            startActivity(Intent(applicationContext,MainActivity2::class.java))
            finish()
        }
        binding.btn1.setOnClickListener {
            var data = binding.edit1.text
            if(data.isBlank() || data.length<10 || data.length>10){
                Toast.makeText(applicationContext,"Please enter the valid number",Toast.LENGTH_LONG).show()
            }
            else{
                var phone = "+91" + data
                sendVerificationCode(phone)
            }
        }
        binding.btn2.setOnClickListener{

            val otp = binding.edit2.text
            if(otp!!.isBlank()){
                Toast.makeText(applicationContext,"Please enter the OTP",Toast.LENGTH_LONG).show()
            }
            else{
                verifyCode(otp.toString())
            }
        }


    }



    /*private fun signUp(){
        var email = binding.edit1.text.toString()
        var pass = binding.edit2.text.toString()

        if(email.isBlank() || pass.isBlank() ){
            Toast.makeText(applicationContext,"Please enter all the fields",Toast.LENGTH_LONG).show()
            return
        }
        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    Toast.makeText(baseContext, "Signup successfully.",Toast.LENGTH_SHORT).show()
                } else {

                    Toast.makeText(baseContext, "Signup failed.",Toast.LENGTH_SHORT).show()
                }
            }
    }*/

    private fun sendVerificationCode(number:String){
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(number)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }


    private val callbacks = object: PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
        override fun onVerificationCompleted(p0: PhoneAuthCredential) {

        }

        override fun onVerificationFailed(p0: FirebaseException) {
            Toast.makeText(applicationContext,p0.message,Toast.LENGTH_LONG).show()
        }

        override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
            super.onCodeSent(p0, p1)
            sendVerificationId = p0
        }


    }

    private fun verifyCode(code:String){
           credential = PhoneAuthProvider.getCredential(sendVerificationId!!,code)
          signIn(credential)
    }

    private fun signIn(credential: PhoneAuthCredential){
        auth.signInWithCredential(credential).addOnCompleteListener{
            if(it.isSuccessful){
                startActivity(Intent(applicationContext,MainActivity2::class.java))
                finish()
            }
            else{
                Toast.makeText(applicationContext,"failed to sign in",Toast.LENGTH_LONG).show()
            }
        }
    }

}