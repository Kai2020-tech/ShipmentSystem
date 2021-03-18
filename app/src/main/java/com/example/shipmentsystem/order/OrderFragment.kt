package com.example.shipmentsystem.order

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.graphics.Color
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
import com.example.shipmentsystem.product.ProductVm
import java.text.SimpleDateFormat
import java.util.*


class OrderFragment : Fragment() {
    private var orderBinding: FragmentOrderBinding? = null
    private val binding get() = orderBinding!!

    private lateinit var productVm: ProductVm
    private lateinit var orderListVm: OrderListVm

    private lateinit var orderRvAdapter: RvOrderAdapter

    private lateinit var customerName: EditText
    private lateinit var productAmount: EditText
    private lateinit var orderDate: TextView

    private var orderProduct = ""
    private var orderProductPrice = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        orderBinding = FragmentOrderBinding.inflate(inflater, container, false)

        customerName = binding.edCustomerName
        productAmount = binding.edAmount
        orderDate = binding.tvDate

        productVm = getProductViewModel()

        orderListVm = getOrderListVm()

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
            val amount = if (productAmount.text.isBlank()) 1
            else productAmount.text.toString().toInt()
            if (orderProduct == "") {
                toast("Please create a product first")
            } else {
                val item = OrderItem(orderProduct, amount, amount * orderProductPrice)
                orderListVm.createOrderItem(item)
            }
        }
        /** Delete */
        binding.btnDelete.setOnClickListener {
            orderListVm.deleteOrderItem()
        }

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

    private fun initOrderRecyclerView() {
        orderRvAdapter = RvOrderAdapter()
        binding.rvOrder.adapter = orderRvAdapter.apply {
            itemClickListener = {
                orderListVm.onSelectedOrderItem(it)
            }

            changeBackgroundListener = { currentItem, holder ->
                setSelectedItemColor(currentItem, holder)
            }
        }
        binding.rvOrder.layoutManager = LinearLayoutManager(requireActivity())
        orderListVm.orderList.observe(viewLifecycleOwner, Observer {
            orderRvAdapter.update(it.toList())
        })
    }

    private fun setSelectedItemColor(currentItem: OrderItem, holder: RvOrderAdapter.MyHolder) {
        val selectedColor = getString(R.string.selectedOrderItemColor)
        val defaultColor = getString(R.string.defaultOrderItemColor)
        orderListVm.selectedItem.observe(viewLifecycleOwner, Observer {
            //use === compare objects , currentItem and vm's live data item
            if (currentItem === orderListVm.selectedItem.value) {
                holder.itemView.setBackgroundColor(Color.parseColor(selectedColor))
            } else {
                holder.itemView.setBackgroundColor(Color.parseColor(defaultColor))
            }
        })
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

    private fun clearEditText() {
        customerName.text.clear()
        productAmount.text.clear()
    }
}