package com.example.shipmentsystem.ship.edit

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.*
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shipmentsystem.R
import com.example.shipmentsystem.databinding.FragmentEditBinding
import com.example.shipmentsystem.db.OrderItem
import com.example.shipmentsystem.db.Product
import com.example.shipmentsystem.getNavController
import com.example.shipmentsystem.order.OrderListVm
import com.example.shipmentsystem.product.ProductVm
import com.example.shipmentsystem.toast
import java.text.SimpleDateFormat
import java.util.*

class EditFragment : Fragment() {
    private var editBinding: FragmentEditBinding? = null
    private val binding get() = editBinding!!

    private val editVm: EditVm by activityViewModels()

    private val productVm: ProductVm by activityViewModels()
    private val orderListVm: OrderListVm by activityViewModels()

    private lateinit var rvEditAdapter: RvEditAdapter

    private lateinit var customerName: EditText
    private lateinit var productAmount: EditText
    private lateinit var orderDate: TextView

    private var orderProduct = ""
    private var orderProductPrice = 0


    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        editBinding = FragmentEditBinding.inflate(inflater, container, false)

        customerName = binding.edCustomerName
        productAmount = binding.edAmount
        orderDate = binding.tvDate

        //get processingItem from ViewModel,no need safeargs//
//        val args = EditFragmentArgs.fromBundle(requireArguments())
//        Timber.d("${args.processingItem.name}")

        initEditRecyclerView()

        setHasOptionsMenu(true)

        editVm.processingItem.observe(viewLifecycleOwner, Observer { processingItem ->
            binding.edCustomerName.setText(processingItem?.name)
            binding.tvDate.text = SimpleDateFormat("yyyy/MM/dd").format(processingItem?.date)
            binding.tvTotalPrice.text = processingItem?.totalPrice.toString()
            processingItem?.orderList?.let { rvEditAdapter.updateList(it.toMutableList()) }
        })

        editVm.orderList.observe(viewLifecycleOwner, Observer {
            rvEditAdapter.updateList(it)
        })

        editVm.selectedItem.observe(viewLifecycleOwner, Observer {
            it?.let { binding.edAmount.setText(it.amount.toString()) }
                ?: let { binding.edAmount.setText("") }
        })

        editVm.totalOrderPrice.observe(viewLifecycleOwner, Observer {
            binding.tvTotalPrice.text = it.toString()
        })

        editVm.updatedItem.observe(viewLifecycleOwner, Observer { updatedItem ->
            editVm.selectedPos.value?.let { position ->
                rvEditAdapter.updateItem(position, updatedItem)
            }
        })

        getProductListToSpinner()

        setDatePicker()
        /** Create */
        binding.btnCreate.setOnClickListener {
            val amount = if (productAmount.text.isBlank()) 1
            else productAmount.text.toString().toInt()
            if (orderProduct == "") {
                toast("Please create a product first")
            } else {
                val item = OrderItem(orderProduct, amount, amount * orderProductPrice)
                editVm.createOrderItem(item)
            }
        }
        /** Delete */
        binding.btnDelete.setOnClickListener {
            editVm.deleteOrderItem()
        }
        /** Update */
        binding.btnUpdate.setOnClickListener {
            if (editVm.selectedItem.value != null) {
                val amount = productAmount.text.toString().toInt() ?: 1
                val item = OrderItem(orderProduct, amount, amount * orderProductPrice)
                editVm.updateOrderItem(item)
            } else {
                toast(this.getString(R.string.please_select_an_item))
            }
        }
        /** Commit */
        binding.btnCommit.setOnClickListener {
            if (editVm.orderList.value?.size != 0 && binding.edCustomerName.text.isNotBlank()) {
                val name = binding.edCustomerName.text.toString()
                val date = SimpleDateFormat("yyyy/MM/dd").parse(binding.tvDate.text.toString())
                val list = rvEditAdapter.getCheckedList()
                editVm.updateProcessingItem(name, date, list)
            } else {
                toast(this.getString(R.string.please_enter_name_or_items))
            }
        }
        /** Clear all order items */
        binding.btnClear.setOnClickListener {
            editVm.onClear()
        }


        return binding.root
    }

    private fun initEditRecyclerView() {
        rvEditAdapter = RvEditAdapter()

        binding.rvEdit.adapter = rvEditAdapter.apply {
            itemClickListener = { item, pos ->
                editVm.onSelectedOrderItem(item, pos)
            }

            changeBackgroundListener = { currentItem, holder ->
                setSelectedItemColor(currentItem, holder)
            }

        }
        binding.rvEdit.layoutManager = LinearLayoutManager(requireActivity())
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

    private fun setSelectedItemColor(currentItem: OrderItem, holder: RvEditAdapter.MyHolder) {
        val selectedColor = getString(R.string.selectedOrderItemColor)
        val defaultColor = getString(R.string.defaultOrderItemColor)
        editVm.selectedItem.observe(viewLifecycleOwner, Observer {
            //use === compare objects , currentItem and vm's live data item
            if (currentItem === editVm.selectedItem.value) {
                holder.itemView.setBackgroundColor(Color.parseColor(selectedColor))
            } else {
                holder.itemView.setBackgroundColor(Color.parseColor(defaultColor))
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_item, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_itemListDel -> {
                AlertDialog.Builder(requireActivity())
                    .setMessage(getString(R.string.confirm_delete_processingOrder))
                    .setPositiveButton(getString(R.string.yes)) { _, _ ->
                        editVm.onDelete()
                        getNavController().navigate(R.id.action_editFragment_to_shipFragment)
                        toast(getString(R.string.clear_list))
                    }
                    .setNegativeButton(getString(R.string.no)) { _, _ -> }
                    .show()
//                editVm.onClear()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}