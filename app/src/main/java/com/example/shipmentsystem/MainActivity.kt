package com.example.shipmentsystem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
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

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
//                R.id.orderListFragment,
//                R.id.itemFragment,
//                R.id.shipFragment
            )
        )
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }// setup back arrow

    private fun getNavController(): NavController {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        return navHostFragment.navController

    }//set NavHostFragment, a container for fragments

    private fun setupActionBar(navController: NavController) {
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.orderListFragment,
                R.id.itemFragment,
                R.id.shipFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

    }//set ActionBar fragment's label with nav_graph.xml

    private fun setupBottomNav(navController: NavController) {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_Navigation)
        bottomNav.setupWithNavController(navController)

        // if current fragment == destination , do not create the same fragment
        bottomNav.setOnNavigationItemReselectedListener {}

        //decide bottomNav GONE or VISIBLE of current fragment
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
//            Log.e(TAG, "onDestinationChanged: "+destination.label);
            if (destination.label == getString(R.string.edit_fragment)) {
                bottomNav.visibility = View.GONE
            }else{
                bottomNav.visibility = View.VISIBLE
            }
        }
    }//set BottomNavigationView with NavController

}