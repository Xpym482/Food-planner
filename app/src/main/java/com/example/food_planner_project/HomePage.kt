package com.example.food_planner_project

import androidx.appcompat.app.AppCompatActivity
import android.R.layout.simple_list_item_1
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.home_page.*
import kotlin.collections.ArrayList

class HomePage : AppCompatActivity(){

    var productsFromSearch: ArrayList<Product>? = null
    var gramms: ArrayList<Int>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_page)
        bottomNavBarListenerSetup();
        productsFromSearch = GetProducts()
        if (productsFromSearch?.isEmpty() == false) calcProducts(productsFromSearch)
    }

    private fun bottomNavBarListenerSetup() {
        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {//like switch statement
                R.id.navigation_home -> {
                    true
                }
                R.id.navigation_search -> {
                    val intent = Intent(this, SearchPage::class.java)
                    startActivity(intent);
                    true
                }
                else -> true
            }
        }
    }

    private fun GetProducts(): ArrayList<Product>? {
        val products: ArrayList<Product>? = intent.getSerializableExtra("products") as ArrayList<Product>?

        var listView: ListView? = null
        var adapter: ArrayAdapter<String>? = null
        var list: ArrayList<String>? = null
        list = ArrayList()

        if (products != null) {
            products.forEach { product ->
                list!!.add(product.title)
            }
        }

        listView = findViewById<View>(R.id.products_list) as ListView
        adapter = ArrayAdapter(this, simple_list_item_1, list!!)
        listView!!.adapter = adapter

        return products
    }

    private fun calcProducts(productsList: ArrayList<Product>?){
        gramms = intent.getSerializableExtra("gramms") as ArrayList<Int>?
        var sumRasv : Double = 0.0
        var sumKal : Double = 0.0
        var sumSus : Double = 0.0
        var sumValg : Double = 0.0
        var sumView: TextView? = null
        var i : Int = 0

        productsList?.forEach{product ->
            sumKal += gramms!![i].toDouble() / 100 * product.Kalorid
            sumRasv += gramms!![i].toDouble() / 100 * product.Rasvad
            sumSus += gramms!![i].toDouble() / 100 * product.Susivesikud
            sumValg += gramms!![i].toDouble() / 100 * product.Valgud
            i++
        }
        // Kalorid
        sumView = findViewById<TextView>(R.id.kaloor_sum) as TextView
        sumView.text = sumKal.toString()
        // Valgud
        sumView = findViewById<TextView>(R.id.valgud_sum) as TextView
        sumView.text = sumValg.toString()
        //Sussivesikud
        sumView = findViewById<TextView>(R.id.susi_sum) as TextView
        sumView.text = sumSus.toString()
        //Rasvad
        sumView = findViewById<TextView>(R.id.rasvad_sum) as TextView
        sumView.text = sumRasv.toString()


    }
}