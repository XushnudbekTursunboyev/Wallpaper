package uz.xushnudbek.pixelswallpaper.ui.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import uz.xushnudbek.pixelswallpaper.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }

        binding.apply {
            cpp.registerCarrierNumberEditText(etPhoneNumber)
            btnNext.setOnClickListener {
                if (etPhoneNumber.text.toString().isEmpty()) {
                    Toast.makeText(this@LoginActivity, "Please enter your phone number", Toast.LENGTH_SHORT).show()
                } else if (etPhoneNumber.text.toString().replace(" ", "").length != 9) {
                    Toast.makeText(this@LoginActivity, "Please enter a valid phone number", Toast.LENGTH_SHORT).show()
                } else {
                    val mainIntent = Intent(this@LoginActivity, VerificationActivity::class.java)
                    mainIntent.putExtra("number", cpp.fullNumberWithPlus.replace(" ", ""))
                    startActivity(mainIntent)
                    finish()
                }
            }
        }

    }
}