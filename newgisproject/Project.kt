package com.example.newgisproject

data class Project(
    val name: String,
    val type: String,
    val district: String,
    val location: String,
    val beneficiaries: Int,
    val progress: Int,  // Percentage
    val status: String
)