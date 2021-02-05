package com.example.shipmentsystem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shipmentsystem.item.ItemFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val itemFragment = ItemFragment()
        supportFragmentManager.beginTransaction().apply {
            add(R.id.main_Layout, itemFragment , itemFragment ::class.java.name)
            commit()
        }
    }
}