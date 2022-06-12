package com.dicoding.ripeai.ui.about

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import com.dicoding.ripeai.R
import com.dicoding.ripeai.ui.history.HistoryActivity
import com.dicoding.ripeai.ui.main.MainActivity
import com.dicoding.ripeai.ui.profile.ProfileActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class AboutActivity : AppCompatActivity() {
    private lateinit var tvText: TextView
    private lateinit var navigation: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        init()
        navigationListener()
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.top_menu, menu)

        return true
    }
    private fun init() {
        tvText = findViewById(R.id.textView)
        navigation = findViewById(R.id.navigation)
        navigation.selectedItemId = R.id.button_about
        val title = intent.getStringExtra(EXTRA_TITLE)
        supportActionBar?.title = title
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
                    val intent = Intent(this, ProfileActivity::class.java)
                    intent.putExtra(ProfileActivity.EXTRA_TITLE, item.title.toString())
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }
    companion object {
        const val EXTRA_TITLE = "extra_title"
    }
}