package com.example.shipmentsystem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.shipmentsystem.product.ProductVM
import com.example.shipmentsystem.product.ItemViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var productVM: ProductVM
    private lateinit var orderVM: OrderVm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createProductVm()

//        if (savedInstanceState == null) {  //判斷前一fragment是否由系統自動recreate
//            val itemFragment = ItemFragment()
//            supportFragmentManager.beginTransaction().apply {
//                setReorderingAllowed(true)
//                replace(R.id.main_Layout, itemFragment, itemFragment::class.java.name)
//                commit()
//            }
//        }

        val navController = getNavController()

        setupBottomNav(navController)

        setupActionBar(navController)
    }

    private fun setupActionBar(navController: NavController) {
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.orderListFragment,
                R.id.itemFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
    }//setup ActionBar fragment's label with nav_graph

    private fun setupBottomNav(navController: NavController) {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_Navigation)
        bottomNav.setupWithNavController(navController)
        // if current fragment == destination , do not create the same fragment
        bottomNav.setOnNavigationItemReselectedListener {}
    }

    private fun getNavController(): NavController {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        return navHostFragment.navController
    }

    private fun createProductVm() {
        val app = requireNotNull(this).application
        productVM =
            ViewModelProvider(this, ItemViewModelFactory(app)).get(ProductVM::class.java)
    }

    private fun createOrderVm() {
        val app = requireNotNull(this).application
        orderVM =
            ViewModelProvider(this, ItemViewModelFactory(app)).get(OrderVm::class.java)
    }
}