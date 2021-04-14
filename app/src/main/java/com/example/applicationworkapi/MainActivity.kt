package com.example.applicationworkapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telecom.Call
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.applicationworkapi.api.MyRetrofit
import com.example.applicationworkapi.model.Product
import retrofit2.Callback
import retrofit2.Response
import java.util.stream.Collectors.toList

class MainActivity : AppCompatActivity() {

    lateinit var recycleProdutcs: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycleProdutcs = findViewById(R.id.recycler_products)
        recycleProdutcs.layoutManager = LinearLayoutManager(this)

        getData()

    }

    private fun getData() {
        val call: retrofit2.Call<List<Product>> = MyRetrofit.instance?.productApi()?.getProductApi() as retrofit2.Call<List<Product>>
        call.enqueue(object: Callback<List<Product>>{
            override fun onResponse(
                call: retrofit2.Call<List<Product>>,
                response: Response<List<Product>>
            ) {
                val adapter = ProductAdapter(this@MainActivity, response.body()?.toList() as List<Product>)
                recycleProdutcs.adapter = adapter
            }

            override fun onFailure(call: retrofit2.Call<List<Product>>, t: Throwable) {
               Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_LONG).show()
            }

        })

    }
}