package com.example.shipmentsystem

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.shipmentsystem.db.MyDatabase
import com.example.shipmentsystem.db.OrderItem
import java.text.SimpleDateFormat


class OrderVm(application: Application) : AndroidViewModel(application) {
    private var db: MyDatabase = MyDatabase.getInstance(application)

//    suspend fun createOrder() {
//        var dateInString = "2020-05-02"
//
//        val date = SimpleDateFormat("dd-MM-yyyy").parse(dateInString)
//        val item = OrderItem("adf",123, date)
//        db.dao.insertOrder(item)
//    }
}