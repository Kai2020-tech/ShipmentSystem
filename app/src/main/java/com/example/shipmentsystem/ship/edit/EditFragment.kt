package com.example.shipmentsystem.ship.edit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.shipmentsystem.databinding.FragmentEditBinding
import timber.log.Timber

class EditFragment : Fragment() {
    private var editBinding: FragmentEditBinding? = null
    private val binding get() = editBinding!!

    private val editVm: EditVm by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        editBinding = FragmentEditBinding.inflate(inflater,container,false)

//        val args = EditFragmentArgs.fromBundle(requireArguments())
//        Timber.d("${args.processingItem.name}")

        binding.rvEdit.adapter

        return binding.root
    }


}