package com.dicoding.ripeai.ui.profile

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.dicoding.ripeai.R
import com.dicoding.ripeai.databinding.ActivityProfileBinding
import com.dicoding.ripeai.datastore.api.ApiConfig
import com.dicoding.ripeai.datastore.response.*
import com.dicoding.ripeai.ui.UserViewModelFactory
import com.dicoding.ripeai.ui.about.AboutActivity
import com.dicoding.ripeai.ui.history.HistoryActivity
import com.dicoding.ripeai.ui.main.MainActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProfileActivity : AppCompatActivity() {
    private lateinit var tvText: TextView
    private lateinit var navigation: BottomNavigationView
    private lateinit var binding: ActivityProfileBinding
    private lateinit var profileViewModel: ProfileViewModel
    private var mQueue: RequestQueue? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mQueue = Volley.newRequestQueue(this);
        init()
        navigationListener()


        findUser()
        profilParse()

    }

    private fun profilParse() {
        profileViewModel.getToken().observe(this){
            if(it.isNotEmpty()){
                Log.d(TAG, "Token is $it")
                val url = "https://ripe-ai.et.r.appspot.com/api/user?email=$it"
                val request = JsonObjectRequest(
                    Request.Method.GET, url, null,
                    { response ->
                        try {
                            val jsonArray = response.getJSONArray("data")
                            for (i in 0 until jsonArray.length()) {
                                val employee = jsonArray.getJSONObject(i)
                                val firstName = employee.getString("firstname")
                                val lastname = employee.getString("lastname")
                                val phone = employee.getString("phone")
                                val mail = employee.getString("email")
                                binding.name.text = "Name : $firstName $lastname"
                                binding.phone.text = "Phone : $phone"
                                binding.email.text = "Email : $mail"
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }) { error -> error.printStackTrace() }
                mQueue?.add(request)
            }
        }
    }

    private fun findUser() {
        val factory: UserViewModelFactory = UserViewModelFactory.getInstance(this)
        profileViewModel = ViewModelProvider(
            this,
            factory
        )[ProfileViewModel::class.java]

    }

    private fun init() {
        tvText = findViewById(R.id.textView)
        navigation = findViewById(R.id.navigation)
        navigation.selectedItemId = R.id.button_profile
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
                    intent.putExtra(EXTRA_TITLE, item.title.toString())
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
                profileViewModel.logout()
                true
            }
            R.id.settings -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                true
            }
            else -> true
        }
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
        const val EXTRA_TITLE = "extra_title"
        const val TAG = "ProfileActivity"
    }
}