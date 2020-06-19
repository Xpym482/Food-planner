package com.example.food_planner_project

import android.R.layout.simple_list_item_1
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuItemCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlin.collections.ArrayList
import kotlinx.android.synthetic.main.home_page.*


class SearchPage : AppCompatActivity() {
    var listView: ListView? = null
    var list: ArrayList<String>? = null
    var listGramms = ArrayList<Int>()
    var adapter: ArrayAdapter<String>? = null
    var productsList = ArrayList<String>()
    var productsFromDB = ArrayList<Product>()
    var transferProducts = ArrayList<Product>()

    private lateinit var postKey: String
    private lateinit var database: DatabaseReference
    private lateinit var postReference: DatabaseReference
    private var postListener: ValueEventListener? = null


    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_page)

        list = ArrayList()

        val database = Firebase.database
        val myRef = database.getReference("Product")

        // Read from the database
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for(h in dataSnapshot.children){
                    val product = h.getValue(Product::class.java)
                    productsFromDB.add(product!!)
                    list!!.add(product.title)
                }
                listView = findViewById<View>(R.id.listView) as ListView
                adapter = ArrayAdapter(applicationContext, simple_list_item_1, list!!)
                listView!!.adapter = adapter
                inputGramms(listView)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("loadPost:onCancelled", databaseError.toException())
            }

        }
        myRef.addValueEventListener(postListener)
    }

    private fun inputGramms(productsView: ListView? ){
        productsView!!.setOnItemClickListener { adapterView, view, i, l ->
            var position = i
            val builder = AlertDialog.Builder(this);
            // Get the layout inflater
            val inflater = layoutInflater;
            val dialogLayout = inflater.inflate(R.layout.input_box, null)
            val editText  = dialogLayout.findViewById<EditText>(R.id.gramms)
            builder.setView(dialogLayout)
            builder.setPositiveButton("OK") { dialogInterface, i ->
                if(editText.text.toString().isNullOrEmpty() == false) {
                    Toast.makeText(
                        applicationContext,
                        "You chose " + list!![position],
                        Toast.LENGTH_SHORT
                    ).show()
                    transferProducts.add(productsFromDB[position])
                    productsList.add(editText.text.toString())
                    listGramms.add(Integer.parseInt(editText.text.toString()))
                }
            }
            builder.show()
        }
        bottomNavBarListenerSetup(transferProducts, listGramms);
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_button, menu)
        val searchViewItem = menu.findItem(R.id.app_bar_search)
        val searchView =
            MenuItemCompat.getActionView(searchViewItem) as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchView.clearFocus()
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                adapter!!.filter.filter(newText)
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    private fun bottomNavBarListenerSetup(listProducts: ArrayList<Product>? = null, productsGramms: ArrayList<Int>? = null) {
        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {//like switch statement
                R.id.navigation_search -> {
                    true
                }
                R.id.navigation_home -> {
                   val intent = Intent(this, HomePage::class.java)
                    intent.putExtra("products", listProducts)
                    intent.putExtra("gramms", productsGramms)
                    startActivity(intent);
                    true
                }
                else -> true
            }
        }
    }
}

