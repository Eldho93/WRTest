package com.eldho.whiterabbitsample.network_models

data class EmployeeDetailsItem(
    val address: Address,
    val company: Company,
    val email: String,
    val id: Int,
    val name: String,
    val phone: String,
    val profile_image: String,
    val username: String,
    val website: String
)