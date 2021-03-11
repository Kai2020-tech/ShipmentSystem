package com.example.shipmentsystem.order

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shipmentsystem.*
import com.example.shipmentsystem.databinding.FragmentOrderBinding
import com.example.shipmentsystem.db.OrderItem
import com.example.shipmentsystem.db.Product
import com.example.shipmentsystem.product.ProductVM
import java.text.SimpleDateFormat
import java.util.*


class OrderFragment : Fragment() {
    private var orderBinding: FragmentOrderBinding? = null
    private val binding get() = orderBinding!!

    private lateinit var productVM: ProductVM
    private lateinit var orderVM: OrderVm

    private lateinit var orderRvAdapter: RvOrderAdapter

    private lateinit var customerName: EditText
    private lateinit var productAmount: EditText
    private lateinit var orderDate: TextView
    private lateinit var orderProduct: String
    private var orderProductPrice = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        orderBinding = FragmentOrderBinding.inflate(inflater, container, false)

        customerName = binding.edCustomerName
        productAmount = binding.edAmount
        orderDate = binding.tvDate


        productVM = getProductViewModel()

        orderVM = getOrderViewModel()



        orderVM.getAllOrders()

        initOrderRecyclerView()

        getProductListToSpinner()

        setDatePicker()

        onCrud()

        return binding.root
    }

    @SuppressLint("SimpleDateFormat")
    private fun onCrud() {
        /** Create */
        binding.btnCreate.setOnClickListener {
            val amount: Int = productAmount.text.toString().toInt()
            val sumPrice = amount * orderProductPrice
            val name = customerName.text.toString()
            val date = SimpleDateFormat("yyyy/MM/dd").parse(orderDate.text.toString())
            if (customerName.text.isNotBlank() && amount != 0) {

                orderVM.onInsertOrder(name, orderProduct, sumPrice, date)
                toast(getString(R.string.created, name))
//                clearEditText()
            }
        }
    }

    private fun getProductListToSpinner() {
        productVM.getAllProduct()   //trigger ProductList
//        val productList = mutableListOf<String>()
        val productList = mutableListOf<Product>()
        productVM.productList.observe(viewLifecycleOwner, Observer {
//            productList.clear()
//            it.forEach { product ->
//                productList.add(product.name)
//            }
//            productList.addAll(it)

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
//                    Toast.makeText(requireActivity(), list[position], Toast.LENGTH_SHORT).show()
                    orderProduct = spinnerList[position]
                    orderProductPrice = productList[position].price
                }

            }

    }

    private fun initOrderRecyclerView() {
        orderRvAdapter = RvOrderAdapter()
        binding.rvOrder.adapter = orderRvAdapter.apply {
            itemClickListener = {
//                orderVM.onSelectProduct(it)
            }

            changeBackgroundListener = { currentItem, holder ->
                setSelectedItemColor(currentItem, holder)
            }
        }
        binding.rvOrder.layoutManager = LinearLayoutManager(requireActivity())
        orderVM.orderList.observe(viewLifecycleOwner, Observer {
            orderRvAdapter.update(it)
        })
    }

    private fun setSelectedItemColor(currentItem: OrderItem, holder: RvOrderAdapter.MyHolder) {
        val selectedColor = getString(R.string.selectedColor)
        val defaultColor = getString(R.string.defaultColor)
//        orderVM.selectedProduct.observe(viewLifecycleOwner, Observer {
//            //do not use "it" in here, cause it might be null
//            if (currentItem.id == orderVM.selectedProduct.value?.id) {
//                holder.name.setTextColor(Color.BLACK)
//                holder.price.setTextColor(Color.BLACK)
//                holder.itemView.setBackgroundColor(Color.parseColor(selectedColor))
//            } else {
//                holder.name.setTextColor(Color.WHITE)
//                holder.price.setTextColor(Color.WHITE)
//                holder.itemView.setBackgroundColor(Color.parseColor(defaultColor))
//            }
//        })
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    private fun setDatePicker() {
        orderDate.text = SimpleDateFormat("yyyy/MM/dd").format(System.currentTimeMillis())
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