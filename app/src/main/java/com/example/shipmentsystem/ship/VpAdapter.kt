package com.example.shipmentsystem.ship

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.shipmentsystem.ship.complete.CompleteFragment
import com.example.shipmentsystem.ship.processing.ProcessingFragment

class VpAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle)  {

    var fragments:ArrayList<Fragment> = arrayListOf(
        ProcessingFragment(),
        CompleteFragment()
    )

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}