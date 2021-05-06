package com.google.mlkit.vision.demo.kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.mlkit.vision.demo.R
import com.google.mlkit.vision.demo.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
         var f1: FirebaseAuth
         f1 = FirebaseAuth.getInstance()

        binding.signupnew.setOnClickListener(View.OnClickListener {
            gotoSignup()
        })

        binding.loginBtn.setOnClickListener(View.OnClickListener {
            var email: String = binding.loginEmail.text.toString()
            var pass: String = binding.loginPassword.text.toString()
            if(email.isEmpty()) binding.loginEmail.setError("Enter your email")
            if(pass.isEmpty() || pass.length < 6) binding.loginPassword.setError("At least 6 characters")

          f1.signInWithEmailAndPassword(email,pass).addOnCompleteListener(OnCompleteListener {
              if(it.isSuccessful){
                  Toast.makeText(this,"Signed In", Toast.LENGTH_SHORT).show()
                  gotoPoseCam()
              }
              else Toast.makeText(this,"Error Signing in", Toast.LENGTH_SHORT).show()
          })
        })

    }
    fun gotoSignup(){
        var intent = Intent(this,SignupActivity::class.java)
        startActivity(intent)
    }
    fun gotoPoseCam(){
        var intent1 = Intent(this,LivePreviewActivity::class.java)
        startActivity(intent1)
    }
}