package com.gameconnect

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.gameconnect.R.layout.activity_login
import com.gameconnect.databinding.ActivityLoginBinding
import com.gameconnect.domain.model.AppAuthState
import com.gameconnect.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {



    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.registerTV.setOnClickListener {
            //Launch RegisterActivity
            Intent(this, RegisterActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.loginButton.setOnClickListener {
            viewModel.login(
                binding.emailET.text.toString(),
                binding.passwordET.text.toString(),
            )
        }

        viewModel.authStatus.observe(this){
            when (it) {
                is AppAuthState.Loading -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }

                is AppAuthState.Error -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }

                is AppAuthState.Success -> {
                    startActivity(
                        Intent(this, MainActivity::class.java)
                    )
                }
            }
        }
    }

}



