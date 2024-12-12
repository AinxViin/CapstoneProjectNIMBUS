package com.example.capstoneproject.ui.loading

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.capstoneproject.R
import com.example.capstoneproject.data.pref.UserPreference
import com.example.capstoneproject.data.pref.dataStore
import com.example.capstoneproject.ui.login.LoginActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoadingActivity : AppCompatActivity() {
    private lateinit var userPreference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        userPreference = UserPreference.getInstance(dataStore)
        checkSession()
    }

    private fun checkSession() {
        lifecycleScope.launch {
            delay(1000)
            userPreference.getSession().collect { userModel ->
                if (userModel.isLogin) {
                    startActivity(Intent(this@LoadingActivity, MainActivity::class.java))
                } else {
                    startActivity(Intent(this@LoadingActivity, LoginActivity::class.java))
                }
                finish()
            }
        }
    }
}