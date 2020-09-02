package com.eldho.whiterabbitsample
import com.eldho.whiterabbitsample.network_models.EmployeeDetailsItem

interface EmployeeTapped {
    fun onEmployeeTapped(empItems: EmployeeDetailsItem)
}