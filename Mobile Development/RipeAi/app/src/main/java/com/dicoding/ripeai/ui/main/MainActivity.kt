package com.dicoding.ripeai.ui.main

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import com.dicoding.ripeai.R
import com.dicoding.ripeai.databinding.ActivityHistoryBinding
import com.dicoding.ripeai.databinding.ActivityMainBinding
import com.dicoding.ripeai.datastore.response.Data
import com.dicoding.ripeai.ui.UserViewModelFactory
import com.dicoding.ripeai.ui.about.AboutActivity
import com.dicoding.ripeai.ui.history.HistoryActivity
import com.dicoding.ripeai.ui.profile.ProfileActivity
import com.dicoding.ripeai.ui.upload.UploadActivity
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var tvText: TextView
    private lateinit var navigation: BottomNavigationView
    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val data = intent.getParcelableExtra<Data>(EXTRA_DATA)
        init()
        navigationListener()
        runViewModel()
        tvText.text = data?.email ?: ""
        binding.cardView.setOnClickListener {
            val intent = Intent(this, UploadActivity::class.java)
            intent.putExtra(UploadActivity.EXTRA_FRUIT, "Banana")
            startActivity(intent)
        }
        binding.cardViewApple.setOnClickListener {
            val intent = Intent(this, UploadActivity::class.java)
            intent.putExtra(UploadActivity.EXTRA_FRUIT, "Apple")
            startActivity(intent)
        }


    }
    private fun init() {
        tvText = findViewById(R.id.textView)
        navigation = findViewById(R.id.navigation)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.top_menu, menu)

        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                mainViewModel.logout()
                true
            }
            R.id.settings -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                true
            }
            else -> true
        }
    }
    private fun navigationListener() {
        navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.button_home -> {
                    tvText.text = item.title
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra(MainActivity.EXTRA_TITLE, item.title.toString())
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.button_about -> {
                    tvText.text = item.title
                    val intent = Intent(this, AboutActivity::class.java)
                    intent.putExtra(AboutActivity.EXTRA_TITLE, item.title.toString())
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.button_history -> {
                    tvText.text = item.title
                    val intent = Intent(this, HistoryActivity::class.java)
                    intent.putExtra(HistoryActivity.EXTRA_TITLE, item.title.toString())
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.button_profile -> {
                    tvText.text = item.title
                    val data = intent.getParcelableExtra<Data>(EXTRA_DATA)
                    val intent = Intent(this, ProfileActivity::class.java)
                    intent.putExtra(ProfileActivity.EXTRA_TITLE, item.title.toString())
                    intent.putExtra(ProfileActivity.EXTRA_DATA, data)
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }
    private fun runViewModel() {
        val factory: UserViewModelFactory = UserViewModelFactory.getInstance(this)
        mainViewModel = ViewModelProvider(
            this,
            factory
        )[MainViewModel::class.java]

    }
    companion object{
        const val EXTRA_FRUIT = "extra_fruit"
        const val EXTRA_TITLE = "extra_title"
        const val EXTRA_DATA = "extra_data"
    }
}