package com.ismaildev.rawg_perqara

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ismaildev.rawg_perqara.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val navView: BottomNavigationView = findViewById(R.id.bottomNavigation)
        navView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener{_,destination,_->
            if (destination.id ==R.id.homeFragment||destination.id==R.id.favoriteFragment){
                binding.bottomNavigation.visibility = View.VISIBLE
            }else{
                binding.bottomNavigation.visibility = View.GONE
            }
        }

    }
}