package com.example.capstoneproject

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.capstoneproject.data.pref.UserPreference
import com.example.capstoneproject.data.pref.dataStore
import com.example.capstoneproject.databinding.ActivityMainBinding
import com.example.capstoneproject.ui.login.LoginActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userPreference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        userPreference = UserPreference.getInstance(applicationContext.dataStore)
        checkSession()

        val wisataId = intent.getIntExtra("wisataId", -1)

        val navView: BottomNavigationView = binding.navView

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_plan,
                R.id.navigation_profile,
                R.id.navigation_change_name,
                R.id.navigation_change_email,
                R.id.navigation_change_password,
                R.id.navigation_explore
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        if (wisataId != -1) {
            val bundle = Bundle()
            bundle.putInt("wisataId", wisataId)
            navController.navigate(R.id.action_homeFragment_to_detailWisataFragment, bundle)
        }
    }

    private fun checkSession() {
        lifecycleScope.launch {
            userPreference.getSession().collect { userModel ->
                if (!userModel.isLogin) {
                    startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                    finish()
                }
            }
        }
    }
}

