package com.example.shipmentsystem.ship.complete

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.shipmentsystem.R
import com.example.shipmentsystem.databinding.FragmentCompleteBinding
import com.example.shipmentsystem.databinding.FragmentProcessingBinding
import timber.log.Timber

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CompleteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CompleteFragment : Fragment() {
    private var processingBinding: FragmentCompleteBinding? = null
    private val binding get() = processingBinding!!

    init {
        Timber.d("complete Vm create $this")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        processingBinding = FragmentCompleteBinding.inflate(inflater,container,false)

        binding.button.setOnClickListener {
            getNavController().navigate(R.id.action_shipFragment_to_editFragment)
        }

     return binding.root
    }


    private fun getNavController(): NavController {
        val navHostFragment = requireActivity().supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        return navHostFragment.navController

    }//set NavHostFragment, a container for fragments

}