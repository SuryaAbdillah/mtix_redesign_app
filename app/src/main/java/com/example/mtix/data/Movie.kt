package com.example.mtix.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.mtix.R

data class Movie(
    @DrawableRes val imagePosterId: Int,
    @StringRes val title: Int,
    @StringRes val age: Int,
    @StringRes val rate: Int
)

val movies = listOf(
    Movie(R.drawable.poster, R.string.title, R.string.rate, R.string.age),
    Movie(R.drawable.poster, R.string.title, R.string.rate, R.string.age),
    Movie(R.drawable.poster, R.string.title, R.string.rate, R.string.age),
    Movie(R.drawable.poster, R.string.title, R.string.rate, R.string.age),
    Movie(R.drawable.poster, R.string.title, R.string.rate, R.string.age),
    Movie(R.drawable.poster, R.string.title, R.string.rate, R.string.age),
    Movie(R.drawable.poster, R.string.title, R.string.rate, R.string.age),
    Movie(R.drawable.poster, R.string.title, R.string.rate, R.string.age),
    Movie(R.drawable.poster, R.string.title, R.string.rate, R.string.age),
    Movie(R.drawable.poster, R.string.title, R.string.rate, R.string.age)
)