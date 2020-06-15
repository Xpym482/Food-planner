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
        GetProducts()
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

    private fun GetProducts(): String {
        val bundle = intent.extras
        val obj: ArrayList<Product> = intent.getSerializableExtra("products") as ArrayList<Product>
        return "test"
    }
}