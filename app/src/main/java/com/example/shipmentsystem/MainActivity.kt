package com.example.shipmentsystem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.shipmentsystem.product.OrderViewModelFactory
import com.example.shipmentsystem.product.ProductVM
import com.example.shipmentsystem.product.ProductViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        if (savedInstanceState == null) {  //判斷前一fragment是否由系統自動recreate
//            val itemFragment = ItemFragment()
//            supportFragmentManager.beginTransaction().apply {
//                setReorderingAllowed(true)
//                replace(R.id.main_Layout, itemFragment, itemFragment::class.java.name)
//                commit()
//            }
//        }

        val navController = getNavController()

        setupActionBar(navController)

        setupBottomNav(navController)

    }

    private fun getNavController(): NavController {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        return navHostFragment.navController

    }//set NavHostFragment, a container for fragments

    private fun setupActionBar(navController: NavController) {
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.orderListFragment,
                R.id.itemFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

    }//set ActionBar fragment's label with nav_graph.xml

    private fun setupBottomNav(navController: NavController) {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_Navigation)
        bottomNav.setupWithNavController(navController)

        // if current fragment == destination , do not create the same fragment
        bottomNav.setOnNavigationItemReselectedListener {}

    }//set BottomNavigationView with NavController

}