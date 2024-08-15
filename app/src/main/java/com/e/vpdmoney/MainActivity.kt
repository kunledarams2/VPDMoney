package com.e.vpdmoney

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.e.vpdmoney.databinding.ActivityMainBinding
import com.e.vpdmoney.util.callback.FragmentListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), FragmentListener {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_dashboard_activity)

        navView.setupWithNavController(navController)
    }

    override fun onBackPressed() {

    }

    override fun hideBNV() {
        hideBottomNav()
    }

    override fun showBNV() {
        showBottomNav()
    }

     fun hideBottomNav() {
        binding.navView.visibility = View.GONE
    }

    fun showBottomNav(){
        binding.navView.visibility = View.VISIBLE
    }

}