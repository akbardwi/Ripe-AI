package com.dicoding.ripeai.ui.history

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.ripeai.R
import com.dicoding.ripeai.adapter.ListHistoryAdapter
import com.dicoding.ripeai.databinding.ActivityHistoryBinding
import com.dicoding.ripeai.datastore.api.ApiConfig
import com.dicoding.ripeai.datastore.response.DataItem
import com.dicoding.ripeai.datastore.response.HistoryResponse
import com.dicoding.ripeai.ui.UserViewModelFactory
import com.dicoding.ripeai.ui.about.AboutActivity
import com.dicoding.ripeai.ui.main.MainActivity
import com.dicoding.ripeai.ui.Result
import com.dicoding.ripeai.ui.profile.ProfileActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList


@Suppress("UNCHECKED_CAST")
class HistoryActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHistoryBinding
    private lateinit var tvText: TextView
    private lateinit var navigation: BottomNavigationView
    private lateinit var historyViewModel: HistoryViewModel

    private var list = ArrayList<DataItem>()

    private val adapter: ListHistoryAdapter by lazy {
        ListHistoryAdapter(list)
    }

    companion object {
        private const val TAG = "HistoryActivity"
        private const val EMAIL = "john@email.com"
        const val EXTRA_TITLE = "extra_title"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        findHistory()

        init()
        navigationListener()


    }

    private fun findHistory() {
        binding.rvHistory.setHasFixedSize(true)
        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.rvHistory.layoutManager = GridLayoutManager(this, 2)
        } else {
            binding.rvHistory.layoutManager = LinearLayoutManager(this)
        }
        val factory: UserViewModelFactory = UserViewModelFactory.getInstance(this)
        historyViewModel = ViewModelProvider(
            this,
            factory
        )[HistoryViewModel::class.java]

        historyViewModel.getToken().observe(this){ token ->
            historyViewModel.getHistory(token).observe(this){
                if(it != null){
                    when(it){
                        is Result.Success->{
                            val history = it.data
                            adapter.addDataToList(history as ArrayList<DataItem>)
                            list.addAll(history)
                            binding.rvHistory.adapter = adapter
                            Log.i(this@HistoryActivity.toString(), "Success  $Result")
                        }
                        is Result.Error -> {
                            Log.e(this@HistoryActivity.toString(), "onFailure dimana:  $Result")
                        }
                    }
                }
            }
        }

    }


    private fun init() {
        tvText = findViewById(R.id.textView)
        navigation = findViewById(R.id.navigation)
        navigation.selectedItemId = R.id.button_history
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.top_menu, menu)

        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                historyViewModel.logout()
                true
            }
            R.id.settings -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                true
            }
            else -> true
        }
    }
}