package com.gtappdevelopers.covidcount

data class StateModal(
    val state: String,
    val recovered: Int,
    val deaths: Int,
    val cases: Int
)