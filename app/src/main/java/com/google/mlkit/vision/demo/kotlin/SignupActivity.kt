package com.google.mlkit.vision.demo.kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.mlkit.vision.demo.R
//import com.google.mlkit.vision.demo.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        var f1: FirebaseAuth
        f1 = FirebaseAuth.getInstance()

        val signupbtn:Button=findViewById(R.id.sign_up_buton)
        val uname:EditText=findViewById(R.id.user_name)
        val emaile:EditText=findViewById(R.id.editTextTextEmailAddress)
        val passe:EditText=findViewById(R.id.editTextTextPassword)

        signupbtn.setOnClickListener(View.OnClickListener {
            var name:String = uname.text.toString()
            var email:String = emaile.text.toString()
            var pass:String = passe.text.toString()

            if(name.isEmpty() || name.length < 4) {
                uname.setError("Enter a valid 4 character name")
                return@OnClickListener
            }
            if (email.isEmpty()) {
                emaile.setError("Enter valid Email")
                return@OnClickListener
            }
            if (pass.isEmpty() || pass.length < 6) {
                passe.setError("Enter password, 6 characters min")
                return@OnClickListener
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