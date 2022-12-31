package com.example.kotlincontact.Adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.kotlincontact.Models.Contact
import com.example.kotlincontact.R
import java.util.*
import kotlin.collections.ArrayList

class HomeAdapter( items:ArrayList<Contact>):BaseAdapter() {
    // Almacena los elementos que se van a mostrar en el ReciclerView
    var items:ArrayList<Contact>? = null
    var copyItems:ArrayList<Contact>? = null

    init {
        this.items = ArrayList(items)
        this.copyItems = items
    }

    override fun getCount(): Int {
        //Regresar el numero de elementos de mi lista
        return this.items?.count()!!
    }

    override fun getItem(p0: Int): Any {
        return this.items?.get(p0)!!
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    @SuppressLint("SetTextI18n", "InflateParams")
    override fun getView(p0: Int, p1: View?, parent: ViewGroup?): View {
        val viewHolder:ViewHolder?
        var view:View? = p1

        if (view == null){
            view = LayoutInflater.from(parent?.context).inflate(R.layout.home_adapter, null)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder

        }else {
            viewHolder = view.tag as? ViewHolder
        }
        val item = getItem(p0) as Contact
        //Asignacion de valores a los elementos graficos
        viewHolder?.photo?.let {
            Glide.with(parent?.context!!)
                .load(item.photo)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .circleCrop()
                .into(it)
        }
        viewHolder?.photo?.setImageResource(item.photo)
        viewHolder?.name?.text = item.name + " " + item.lastName
        viewHolder?.company?.text = item.company

        return view!!

    }

    fun addItem(item:Contact){
        copyItems?.add(item)
        items = ArrayList(copyItems!!)
        notifyDataSetChanged()
    }

    fun removeItem(index:Int){
        copyItems?.removeAt(index)
        items = ArrayList(copyItems!!)
        notifyDataSetChanged()

    }

    fun updateItem(index: Int, newItem:Contact){
        copyItems?.set(index, newItem)
        items = ArrayList(copyItems!!)
        notifyDataSetChanged()
    }

    fun filter(str:String){
        items?.clear()
        if (str.isEmpty()){
            items = ArrayList(copyItems!!)
            notifyDataSetChanged()
            return
        }
        var search = str
        search = search.lowercase(Locale.getDefault())

        for (item in copyItems!!){
            val name = item.name.lowercase(Locale.getDefault())
            if (name.contains(search)){
                items?.add(item)
            }
            notifyDataSetChanged()

        }


    }

    private class ViewHolder(view: View){
        var photo:ImageView? = null
        var name:TextView? = null
        var company:TextView? = null

        init {
            photo = view.findViewById(R.id.imgPhoto)
            name = view.findViewById(R.id.tvName)
            company = view.findViewById(R.id.tvCompany)

        }
    }
}