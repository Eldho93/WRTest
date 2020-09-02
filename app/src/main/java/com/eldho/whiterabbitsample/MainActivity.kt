package com.eldho.whiterabbitsample

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.eldho.whiterabbitsample.network_models.EmployeeDetailsItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity(),EmployeeTapped {
    lateinit var progressBar: ProgressBar
    lateinit var recycleView: RecyclerView
    lateinit var empSearch: SearchView

    lateinit var empDetails:List<EmployeeDetailsItem>
    lateinit var adapter: EmployeesAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        recycleView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar)
        empSearch = findViewById(R.id.empSearch)

        sendRequest()

        empSearch.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }

        })
    }



    private fun sendRequest() {
        progressBar.showOrGone(true)


        val URL = "http://www.mocky.io/v2/5d565297300000680030a986"

        val request = StringRequest(
                URL,
                Response.Listener<String> { response ->
                    progressBar.showOrGone(false)

                    val listType = object : TypeToken<List<EmployeeDetailsItem>>() { }.type
                    val employeeModel = Gson().fromJson<List<EmployeeDetailsItem>>(response, listType)

                    empDetails = employeeModel!!

                    setAdapter(empDetails)

                },
                Response.ErrorListener { error ->
                    error.printStackTrace()
                    loadToast(error.message)
                    progressBar.showOrGone(false)


                })
        VolleySingleton.requestQueque.add(request)
    }


    private fun setAdapter(
            empList: List<EmployeeDetailsItem>
    ) {
        adapter = EmployeesAdapter(this,this,empList as ArrayList<EmployeeDetailsItem>)
        val linear = LinearLayoutManager(this)
        recycleView.layoutManager = linear
        recycleView.adapter = adapter
        adapter.notifyDataSetChanged()
    }



    private fun loadToast(content: String?) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show()
    }

    private fun showSnackbar(
            mainTextStringId: Int, actionStringId: Int,
            listener: View.OnClickListener
    ) {

        Toast.makeText(this@MainActivity, getString(mainTextStringId), Toast.LENGTH_LONG).show()
    }


    private fun View.showOrGone(show: Boolean) {
        visibility = if (show) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    override fun onEmployeeTapped(empItems: EmployeeDetailsItem) {
        val dialogFragment = ProfileFragment()
        dialogFragment.show(supportFragmentManager, "ProfileFragment")
    }
}