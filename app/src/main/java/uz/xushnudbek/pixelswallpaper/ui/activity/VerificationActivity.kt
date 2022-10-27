package uz.xushnudbek.pixelswallpaper.ui.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import uz.xushnudbek.pixelswallpaper.databinding.ActivityVerificationBinding
import java.util.concurrent.TimeUnit

class VerificationActivity : AppCompatActivity() {
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private var number = ""
    private var id = ""
    private lateinit var mAuth:FirebaseAuth

    private lateinit var bn:ActivityVerificationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bn = ActivityVerificationBinding.inflate(layoutInflater)
        setContentView(bn.root)

        mAuth = FirebaseAuth.getInstance()
        number = intent.getStringExtra("number").toString()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }

         callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                Log.d("TAG", "onVerificationCompleted:$credential")
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Log.w("TAG", "onVerificationFailed", e)
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                    id = verificationId
            }
        }

        sendVerificationCode()
        bn.apply {
            btnSubmit.setOnClickListener {
                if (etOtp.text.toString().isEmpty()) {
                    Toast.makeText(this@VerificationActivity, "Please Enter OTP", Toast.LENGTH_SHORT).show()
                } else if (etOtp.text.toString().replace(" ", "").length != 6) {
                    Toast.makeText(this@VerificationActivity, "Please enter a valid OTP", Toast.LENGTH_SHORT).show()
                } else {
                    progressbar.visibility = View.VISIBLE
                    val credential = PhoneAuthProvider.getCredential(id, etOtp.text.toString().replace(" ", ""))
                    signInWithPhoneAuthCredential(credential)
                }
            }

            tvResendCode.setOnClickListener {
                sendVerificationCode()
            }
        }

    }

    private fun sendVerificationCode() {

        object: CountDownTimer(60000, 100){
            override fun onTick(p0: Long) {
                bn.tvResendCode.setText(""+1/1000)
                bn.tvResendCode.isEnabled = false
            }
            override fun onFinish() {
                bn.tvResendCode.setText("Resend")
                bn.tvResendCode.isEnabled = true
            }
        }.start()

        val options = PhoneAuthOptions.newBuilder(mAuth)
            .setPhoneNumber(number)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                bn.progressbar.visibility = View.GONE
                if (task.isSuccessful) {
                    Log.d("TAG", "signInWithCredential:success")

                    val mainIntent = Intent(this, MainActivity::class.java)
                    startActivity(mainIntent)
                    finish()

                    val user = task.result?.user
                } else {
                    // Sign in failed, display a message and update the UI
                    Toast.makeText(this, "Verification failed! Try again", Toast.LENGTH_SHORT).show()
                    Log.w("TAG", "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    // Update UI
                }
            }
    }

}