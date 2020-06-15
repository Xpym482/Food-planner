package com.example.food_planner_project

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class Product (val id: Long, val title : String, var Rasvad : Int, var Susivesikud : Int, var Valgud : Int, var Kalorid : Int) : Serializable

