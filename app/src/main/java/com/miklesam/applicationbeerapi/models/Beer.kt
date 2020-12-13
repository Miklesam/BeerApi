package com.miklesam.applicationbeerapi.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Beer(
    val name: String,
    val description: String,
    val image_url: String,
    val tagline: String,
    val id: String,
    val first_brewed: String
) : Parcelable
