package com.example.food_planner_project

import android.R.layout.simple_list_item_1
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuItemCompat
//import com.google.firebase.database.*
import kotlin.collections.ArrayList
import kotlinx.android.synthetic.main.home_page.*
import java.util.*
//import com.firebase.client.ValueEventListener
//import com.google.firebase.database.ValueEventListener



//class SearchPage: Observable() {
//
//    private var m_valueDataListener: ValueEventListener? = null     // The data listener that gets the data from the database
//    private var m_foodsList: ArrayList<foods>? = ArrayList()    // Person cache
//
//    //gets the database location reference for later repeated use
//    private fun getDatabaseRef(): DatabaseReference? {
//        return FirebaseDatabase.getInstance().reference.child("foods")
//    }
//
//    //called on object initialisation
//    init {
//        if (m_valueDataListener != null) {
//
//            getDatabaseRef()?.removeEventListener(m_valueDataListener)
//        }
//        m_valueDataListener = null
//        Log.i("SearchPage", "dataInit line 27")
//
//
//        m_valueDataListener = object: ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot?) {
//                try {
//                    Log.i("PersonModel", "data updated line 28")
//                    val data: ArrayList<foods> = ArrayList()
//                    if (dataSnapshot != null) {
//                        for (snapshot: DataSnapshot in dataSnapshot.children) {
//                            try {
//                                data.add(foods(snapshot))
//                            } catch (e: Exception) {
//                                e.printStackTrace()
//                            }
//                        }
//                        m_foodsList = data
//                        Log.i("SearchPage","data updated there are " + m_foodsList!!.size + " foods in the list")
//                        setChanged()
//                        notifyObservers()
//                    } else {
//                        throw Exception("data snapshot is null line 31")
//                    }
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                }
//            }
//
//            override fun onCancelled(p0: DatabaseError?) {
//                if (p0 != null) {
//                    Log.i("SearchPage", "line 33 Data update cancelled, err = ${p0.message}, detail = ${p0.details}")
//                }
//            }
//        }
//        getDatabaseRef()?.addValueEventListener(m_valueDataListener)
//    }
//
//    fun getData(): ArrayList<Person>? {
//        return m_foodsList
//    }
//}

class SearchPage : AppCompatActivity() {
    var listView: ListView? = null
    var list: ArrayList<String>? = null
    var adapter: ArrayAdapter<String>? = null
    var textView: TextView? = null
    var productsList = ArrayList<Product>()
    var id = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_page)
        val product1 = Product(id++, "Grechka", 65,1, 24,200)
        val product2 = Product(id++, "Rice", 15,100, 25,100)
        productsList.add(product1)
        productsList.add(product2)

        listView = findViewById<View>(R.id.listView) as ListView
        list = ArrayList()
        list!!.add("Apple")
        list!!.add("Banana")
        list!!.add("Pineapple")
        list!!.add("Orange")
        list!!.add("Lychee")
        list!!.add("Gavava")
        list!!.add("Peech")
        list!!.add("Melon")
        list!!.add("Watermelon")
        list!!.add("Papaya")
        adapter = ArrayAdapter(this, simple_list_item_1, list!!)
        listView!!.adapter = adapter

        listView!!.setOnItemClickListener { adapterView, view, i, l ->
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

        bottomNavBarListenerSetup(productsList!!);
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

