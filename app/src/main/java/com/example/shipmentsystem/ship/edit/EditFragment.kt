package com.example.shipmentsystem.ship.edit

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shipmentsystem.R
import com.example.shipmentsystem.databinding.FragmentEditBinding
import com.example.shipmentsystem.db.Product
import com.example.shipmentsystem.order.OrderListVm
import com.example.shipmentsystem.product.ProductVm
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class EditFragment : Fragment() {
    private var editBinding: FragmentEditBinding? = null
    private val binding get() = editBinding!!

    private val editVm: EditVm by activityViewModels()
    private val productVm: ProductVm by activityViewModels()
    private val orderListVm: OrderListVm by activityViewModels()

    private lateinit var rvEditAdapter: RvEditAdapter

    private var orderProduct = ""
    private var orderProductPrice = 0


    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        editBinding = FragmentEditBinding.inflate(inflater,container,false)

        //get processingItem from ViewModel,no need safeargs//
//        val args = EditFragmentArgs.fromBundle(requireArguments())
//        Timber.d("${args.processingItem.name}")

        rvEditAdapter = RvEditAdapter()

        binding.rvEdit.adapter = rvEditAdapter.apply {

        }
        binding.rvEdit.layoutManager = LinearLayoutManager(requireActivity())

        editVm.processingItem.observe(viewLifecycleOwner, Observer { processingItem ->
            binding.edCustomerName.setText(processingItem.name)
            binding.tvDate.text = SimpleDateFormat("yyyy/MM/dd").format(processingItem.date)
            rvEditAdapter.updateList(processingItem.orderList)
        })

        getProductListToSpinner()

        setDatePicker()

        return binding.root
    }

    private fun getProductListToSpinner() {
        productVm.productList.observe(viewLifecycleOwner, Observer {
            setProductSpinner(it)
        })
    }

    private fun setProductSpinner(productList: List<Product>) {
        val spinnerList = mutableListOf<String>()
        productList.forEach {
            spinnerList.add(it.name)
        }
        val adapter = ArrayAdapter(
            requireActivity(),      //context
            R.layout.spinner_item,  //spinner text view style
            spinnerList             //spinner option list
        )
        binding.spinnerProduct.adapter = adapter
        binding.spinnerProduct.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    orderProduct = spinnerList[position]
                    orderProductPrice = productList[position].price
                }

            }
        //when click an item , will notify spinner to display selected item name
        orderListVm.selectedItem.observe(viewLifecycleOwner, Observer {
            it?.let { binding.spinnerProduct.setSelection(adapter.getPosition(it.name)) }
                ?: let { binding.spinnerProduct.setSelection(0) }
        })
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    private fun setDatePicker() {
        binding.tvDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            DatePickerDialog(requireActivity(), { _, year, month, day ->
                run {
                    binding.tvDate.text = "$year/${month + 1}/$day"
                }
            }, year, month, day).show()
        }
    }


}