package com.example.food_planner_project

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class Product (val id: Long = 0, val title : String = "", var Rasvad : Int = 0, var Susivesikud : Int = 0, var Valgud : Int = 0, var Kalorid : Int = 0) : Serializable

