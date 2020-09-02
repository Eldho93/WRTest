package com.eldho.whiterabbitsample

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eldho.whiterabbitsample.network_models.EmployeeDetailsItem
import java.util.*
import kotlin.collections.ArrayList


class EmployeesAdapter( activity: Context,private val callbackInterface:EmployeeTapped,private var empItem: ArrayList<EmployeeDetailsItem>) :
        RecyclerView.Adapter<EmployeesAdapter.ViewHolder>(), Filterable {
    var act = activity
    var empFilterList = ArrayList<EmployeeDetailsItem>()

    init {
        empFilterList = empItem
    }

    override fun getItemCount(): Int {
        return empFilterList?.size!!
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtName: TextView? = itemView?.findViewById(R.id.textView2) as TextView?
        var txtCompanyName: TextView? = itemView?.findViewById(R.id.textView3) as TextView?
        var imgEmpProfile: ImageView? = itemView?.findViewById(R.id.imgEmpProfile) as ImageView?
        var card_list: CardView? = itemView?.findViewById(R.id.cardView) as CardView?




    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(act).inflate(R.layout.row_employees, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.txtName?.text = empFilterList!![position].name
        holder?.txtCompanyName?.text = empFilterList!![position].company?.name

        Glide.with(this!!.act!!)
                .load(empFilterList!![position].profile_image)
                .placeholder(R.mipmap.ic_placeholder)
                .into(holder?.imgEmpProfile!!)

        holder?.card_list?.setOnClickListener(View.OnClickListener {
            callbackInterface.onEmployeeTapped(empFilterList[position])
        })

    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    empFilterList = empItem
                } else {
                    val resultList = ArrayList<EmployeeDetailsItem>()
                    for (row in empItem) {
                        if (row.name.toLowerCase(Locale.ROOT).contains(charSearch.toLowerCase(Locale.ROOT))) {
                            resultList.add(row)
                        }
                    }
                    empFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = empFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                empFilterList = results?.values as ArrayList<EmployeeDetailsItem>
                notifyDataSetChanged()
            }

        }
    }

}