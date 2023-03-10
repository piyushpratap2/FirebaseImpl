package com.example.firebaseimpl

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.firebaseimpl.databinding.ActivityMain2Binding

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity2 : AppCompatActivity() {
    private lateinit var binding : ActivityMain2Binding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
/*
        auth = FirebaseAuth.getInstance()
        binding.btn1.setOnClickListener {
            LoginUser()
        }
       binding.tv1.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
           finish()
        }*/
        auth = FirebaseAuth.getInstance()
        binding.btn1.setOnClickListener {
            Firebase.auth.signOut()
            startActivity(Intent(applicationContext,MainActivity::class.java))
            finish()
        }
    }
   /* private fun LoginUser(){
        var email = binding.edit1.text.toString()
        var pass = binding.edit2.text.toString()
        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    Toast.makeText(baseContext, "login successfully.",Toast.LENGTH_SHORT).show()
                } else {

                    Toast.makeText(baseContext, "login failed.",Toast.LENGTH_SHORT).show()
                }
            }
    }*/
}