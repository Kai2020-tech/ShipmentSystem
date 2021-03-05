package com.example.shipmentsystem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.shipmentsystem.item.ItemFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_Navigation)

//        if (savedInstanceState == null) {  //判斷前一fragment是否由系統自動recreate
//            val itemFragment = ItemFragment()
//            supportFragmentManager.beginTransaction().apply {
//                setReorderingAllowed(true)
//                replace(R.id.main_Layout, itemFragment, itemFragment::class.java.name)
//                commit()
//            }
//        }

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
//        val navController = findNavController(R.id.nav_host_fragment)
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.orderListFragment,
//                R.id.fragment_item
//
//            )
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNav.setupWithNavController(navController)
//        NavigationUI.setupWithNavController(bottomNav,navController)
    }
}