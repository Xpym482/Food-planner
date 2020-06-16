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
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlin.collections.ArrayList
import kotlinx.android.synthetic.main.home_page.*
import java.util.*


class SearchPage : AppCompatActivity() {
    var listView: ListView? = null
    var list: ArrayList<String>? = null
    var adapter: ArrayAdapter<String>? = null
    var textView: TextView? = null
    var productsList = ArrayList<Product>()
    var productsFromDB = ArrayList<Product>()
    var id = 0L

    private lateinit var postKey: String
    private lateinit var database: DatabaseReference
    private lateinit var postReference: DatabaseReference
    private var postListener: ValueEventListener? = null


    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_page)


        // Check if user is signed in (non-null) and update UI accordingly.

//        val product1 = Product(id++, "Grechka", 65,1, 24,200)
//        val product2 = Product(id++, "Rice", 15,100, 25,100)
//        productsList.add(product1)
//        productsList.add(product2)

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
                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException()) .
            }

        }
        myRef.addValueEventListener(postListener)
    }

    private fun inputGramms(productsView: ListView? ){
        productsView!!.setOnItemClickListener { adapterView, view, i, l ->
            val position = i

            val builder = AlertDialog.Builder(this);
            // Get the layout inflater
            val inflater = layoutInflater;
            val dialogLayout = inflater.inflate(R.layout.input_box, null)
            val editText  = dialogLayout.findViewById<EditText>(R.id.gramms)
            builder.setView(dialogLayout)
            builder.setPositiveButton("OK") { dialogInterface, i ->
                Toast.makeText(applicationContext, "You chose " + list!![0], Toast.LENGTH_SHORT).show()
            }
            builder.show()

        }

        bottomNavBarListenerSetup(productsFromDB!!);
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

    private fun bottomNavBarListenerSetup(listProducts: ArrayList<Product>) {
        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {//like switch statement
                R.id.navigation_search -> {
                    true
                }
                R.id.navigation_home -> {
                   val intent = Intent(this, HomePage::class.java)
                    intent.putExtra("products", listProducts)
                    startActivity(intent);
                    true
                }
                else -> true
            }
        }
    }
}

