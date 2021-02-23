package com.example.shipmentsystem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shipmentsystem.item.ItemFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {  //判斷前一fragment是否由系統自動recreate
            val itemFragment = ItemFragment()
            supportFragmentManager.beginTransaction().apply {
                setReorderingAllowed(true)
                replace(R.id.main_Layout, itemFragment, itemFragment::class.java.name)
                commit()
            }
        }
    }
}