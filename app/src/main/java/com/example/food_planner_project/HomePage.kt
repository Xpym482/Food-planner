package com.example.food_planner_project

import androidx.appcompat.app.AppCompatActivity
import android.R.layout.simple_list_item_1
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuItemCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.AppBarConfiguration
import kotlinx.android.synthetic.main.home_page.*
import kotlin.collections.ArrayList

class HomePage : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_page)
        bottomNavBarListenerSetup();
        calcProducts(GetProducts())
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

    private fun GetProducts(): ArrayList<Product> {
        val products: ArrayList<Product> = intent.getSerializableExtra("products") as ArrayList<Product>
        var listView: ListView? = null
        var adapter: ArrayAdapter<String>? = null
        var list: ArrayList<String>? = null
        list = ArrayList()

        products.forEach { product ->
            list!!.add(product.title)
        }

        listView = findViewById<View>(R.id.products_list) as ListView
        adapter = ArrayAdapter(this, simple_list_item_1, list!!)
        listView!!.adapter = adapter

        return products
    }

    private fun calcProducts(productsList: ArrayList<Product>){
        var sumRasv : Int = 0
        var sumKal : Int = 0
        var sumSus : Int = 0
        var sumValg : Int = 0
        var sumView: TextView? = null

        productsList.forEach{product ->
            sumKal += product.Kalorid
            sumRasv += product.Rasvad
            sumSus += product.Susivesikud
            sumValg += product.Valgud
        }

        sumView = findViewById<TextView>(R.id.sum) as TextView
//        sumView.text = sumKal.toString()

    }
}