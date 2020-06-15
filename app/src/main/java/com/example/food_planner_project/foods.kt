//package com.example.food_planner_project
//
//import com.google.firebase.database.DataSnapshot
//
//class foods (snapshot: DataSnapshot){
//    lateinit var id: String
//    lateinit var name: String
//    lateinit var kalorid: String
//
//    init{
//        try{
//            val data: HashMap<String, Any> = snapshot.value as HashMap<String, Any>
//            id = snapshot.key ?: ""
//            name = data["name"] as String
//            kalorid = data["kalorid"] as String
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//
//}