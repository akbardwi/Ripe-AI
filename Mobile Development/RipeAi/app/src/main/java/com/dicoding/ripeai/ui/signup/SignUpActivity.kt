package com.dicoding.ripeai.ui.signup

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.load.engine.executor.GlideExecutor.UncaughtThrowableStrategy.LOG
import com.dicoding.ripeai.R
import com.dicoding.ripeai.databinding.ActivitySignupBinding
import com.dicoding.ripeai.ui.login.LoginActivity
import com.dicoding.ripeai.ui.Result
import com.dicoding.ripeai.ui.UserViewModelFactory


class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var signupViewModel: SignupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        setupAction()
        setupViewModel()
        playAnimation()
    }

    private fun playAnimation() {

        val title = ObjectAnimator.ofFloat(binding.tvSignup, View.ALPHA, 1f).setDuration(500)
        val tvFName = ObjectAnimator.ofFloat(binding.tvFirstName, View.ALPHA, 1f).setDuration(500)
        val edtFName = ObjectAnimator.ofFloat(binding.edtFirstName, View.ALPHA, 1f).setDuration(500)
        val tvName = ObjectAnimator.ofFloat(binding.tvName, View.ALPHA, 1f).setDuration(500)
        val edtName = ObjectAnimator.ofFloat(binding.edtName, View.ALPHA, 1f).setDuration(500)
        val tvPhone = ObjectAnimator.ofFloat(binding.tvPhone, View.ALPHA, 1f).setDuration(500)
        val edtPhone = ObjectAnimator.ofFloat(binding.edtPhone, View.ALPHA, 1f).setDuration(500)
        val tvEmail = ObjectAnimator.ofFloat(binding.tvEmail, View.ALPHA, 1f).setDuration(500)
        val edtEmail = ObjectAnimator.ofFloat(binding.edtEmail, View.ALPHA, 1f).setDuration(500)
        val tvPassword = ObjectAnimator.ofFloat(binding.tvPassword, View.ALPHA, 1f).setDuration(500)
        val edtPassword = ObjectAnimator.ofFloat(binding.edtPassword, View.ALPHA, 1f).setDuration(500)
        val btnSignup = ObjectAnimator.ofFloat(binding.btnSignup, View.ALPHA, 1f).setDuration(500)
        val message = ObjectAnimator.ofFloat(binding.tvKet, View.ALPHA, 1f).setDuration(500)
        val login = ObjectAnimator.ofFloat(binding.tvLogin, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(title,tvFName,edtFName,tvName,edtName,tvPhone,edtPhone, tvEmail, edtEmail, tvPassword, edtPassword, btnSignup, message, login)
            start()
        }
    }

    private fun setupViewModel() {
        val factory: UserViewModelFactory = UserViewModelFactory.getInstance(this)
        signupViewModel = ViewModelProvider(this, factory)[SignupViewModel::class.java]
    }

    private fun setupAction() {
        binding.btnSignup.setOnClickListener {
            val firstname = binding.edtFirstName.text.toString().trim()
            val lastname = binding.edtName.text.toString().trim()
            val phone = binding.edtPhone.text.toString().trim()
            val email = binding.edtEmail.text.toString().trim()
            val password = binding.edtPassword.text.toString().trim()
            when {
                firstname.isEmpty() -> {
                    binding.edtFirstName.error = resources.getString(R.string.message_validation, "first_name")
                }
                lastname.isEmpty() -> {
                    binding.edtName.error = resources.getString(R.string.message_validation, "name")
                }
                phone.isEmpty() -> {
                    binding.edtPhone.error = resources.getString(R.string.message_validation, "phone")
                }
                email.isEmpty() -> {
                    binding.edtEmail.error = resources.getString(R.string.message_validation, "email")
                }
                password.isEmpty() -> {
                    binding.edtPassword.error = resources.getString(R.string.message_validation, "password")
                }
                else -> {
                    signupViewModel.register(firstname, lastname, phone, email, password).observe(this){ result ->
                        if (result != null){
                            when(result) {
                                is Result.Loading -> {
                                    showLoading(true)
                                }
                                is Result.Success -> {
                                    showLoading(false)
                                    val user = result.data
                                    if (!user.success){
                                        Toast.makeText(this@SignUpActivity, "Register Failed", Toast.LENGTH_SHORT).show()
                                    }else{
                                        AlertDialog.Builder(this@SignUpActivity).apply {
                                            setTitle("Yeah!")
                                            setMessage("Your account successfully created!")
                                            setPositiveButton("Next") { _, _ ->
                                                finish()
                                            }
                                            create()
                                            show()
                                        }
                                    }
                                }
                                is Result.Error -> {
                                    showLoading(false)
                                    Toast.makeText(
                                        this,
                                        resources.getString(R.string.signup_error),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    Log.e(TAG,"Error Here")
                                }
                            }
                        }
                    }
                }
            }
        }

        binding.tvLogin.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            edtEmail.isEnabled = !isLoading
            edtPassword.isEnabled = !isLoading
            edtName.isEnabled = !isLoading
            btnSignup.isEnabled = !isLoading

            if (isLoading) {
                viewProgressbar.visibility = View.VISIBLE
            } else {
                viewProgressbar.visibility = View.GONE
            }
        }
    }

    companion object{
        const val TAG = "SignUpActivity"
    }
}