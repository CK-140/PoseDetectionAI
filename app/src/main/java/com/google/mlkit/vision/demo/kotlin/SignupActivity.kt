package com.google.mlkit.vision.demo.kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.mlkit.vision.demo.R
import com.google.mlkit.vision.demo.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var f1: FirebaseAuth
        f1 = FirebaseAuth.getInstance()

        binding.signUpButon.setOnClickListener(View.OnClickListener {
            var name: String = binding.userName.text.toString()
            var email: String = binding.editTextTextEmailAddress.text.toString()
            var pass: String = binding.editTextTextPassword.text.toString()

            if (name.isEmpty() || name.length < 4) {
                binding.userName.setError("Enter a valid 4 character name")
            }
            if (email.isEmpty()) binding.editTextTextEmailAddress.setError("Enter valid Email")
            if (pass.isEmpty() || pass.length < 6) {
                binding.editTextTextPassword.setError("At least 6 characters")
            }
            f1.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(OnCompleteListener {
            if(it.isSuccessful){
                Toast.makeText(this,"Signed up successfully",Toast.LENGTH_SHORT).show()
                gotoposeCam()
            }
                else{
                    Toast.makeText(this,"Error, Try Again", Toast.LENGTH_SHORT).show()
            }
            })
        })


    }
    fun gotoposeCam(){
        val intent = Intent(this,LivePreviewActivity::class.java)
        startActivity(intent)
    }
}