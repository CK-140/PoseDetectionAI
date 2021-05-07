package com.google.mlkit.vision.demo.kotlin

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import com.google.firebase.auth.FirebaseAuth
import com.google.mlkit.vision.demo.R
//import com.google.mlkit.vision.demo.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        var f1: FirebaseAuth
        f1 = FirebaseAuth.getInstance()
        val lowkeyUseful: TextView = findViewById(R.id.useless_textview2)
        val loginbutton:MaterialButton= findViewById(R.id.login_btn)
        val signupyo:MaterialTextView=findViewById(R.id.signupnew)
        val emailtxt:TextInputEditText=findViewById(R.id.login_email)
        val passtxt:TextInputEditText=findViewById(R.id.login_password)

        signupyo.setOnClickListener(View.OnClickListener {
            gotoSignup()
        })

        lowkeyUseful.setOnTouchListener(object : View.OnTouchListener {
            var handler: Handler = Handler()
            var numberOfTaps = 0
            var lastTapTimeMs: Long = 0
            var touchDownMs: Long = 0
            override fun onTouch(v: View?, event: MotionEvent): Boolean {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> touchDownMs = System.currentTimeMillis()
                    MotionEvent.ACTION_UP -> {
                        // Handle the numberOfTaps
                        handler.removeCallbacksAndMessages(null)
                        if (System.currentTimeMillis() - touchDownMs
                            > ViewConfiguration.getTapTimeout()) {
                            //it was not a tap
                            numberOfTaps = 0
                            lastTapTimeMs = 0
                        }
                        if (numberOfTaps > 0
                            && System.currentTimeMillis() - lastTapTimeMs
                            < ViewConfiguration.getDoubleTapTimeout()
                        ) {
                            // if the view was clicked once
                            numberOfTaps += 1
                        } else {
                            // if the view was never clicked
                            numberOfTaps = 1
                        }
                        lastTapTimeMs = System.currentTimeMillis()

                        // Triple Tap
                        if (numberOfTaps == 4) {
                            gotoPoseCam()
                            // Double tap
                        } else if (numberOfTaps == 2) {
                            handler.postDelayed(Runnable {
                                Toast.makeText(applicationContext, "Stop",
                                    Toast.LENGTH_SHORT)
                                    .show()
                            }, ViewConfiguration.getDoubleTapTimeout().toLong())
                        }
                    }
                }
                return true
            }
        })

        loginbutton.setOnClickListener(View.OnClickListener {
            var email: String = emailtxt.text.toString()
            var pass: String = passtxt.text.toString()

            if(email.isEmpty()){ emailtxt.setError("Enter your email")
            return@OnClickListener
            }
            if(pass.isEmpty()){
                passtxt.setError("Enter A password")
                return@OnClickListener
            }


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
