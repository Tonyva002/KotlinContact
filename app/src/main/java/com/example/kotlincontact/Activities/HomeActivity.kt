package com.example.kotlincontact.Activities

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import com.example.kotlincontact.Adapters.HomeAdapter
import com.example.kotlincontact.Models.Contact
import com.example.kotlincontact.R

class HomeActivity : AppCompatActivity() {

    private var list:ListView? = null

    companion object {
        var contacts:ArrayList<Contact>? = null
        @SuppressLint("StaticFieldLeak")
        private var adapter:HomeAdapter? = null

        fun addContact(contact: Contact){
            adapter?.addItem(contact)
        }

        fun getContact(index:Int): Contact{
            return adapter?.getItem(index) as Contact
        }

        fun deleteContact(index: Int){
            adapter?.removeItem(index)
        }

        fun updateContact(index: Int, newContact:Contact){
            adapter?.updateItem(index, newContact)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setToolbar()

        contacts = ArrayList()
        contacts?.add(Contact("Luis", "Solano", "Hiberus", 26, 71.0, "C/p #3", "809-459-5623", "luis@hotmail.com", R.drawable.foto_01))
        contacts?.add(Contact("Omar", "Perez", "Oesia", 29, 73.5, "C/vega #34", "829-529-5229", "omar@hotmail.com", R.drawable.foto_02))
        contacts?.add(Contact("Ana", "Lopez", "NetTet", 23, 60.5, "C/m #23", "849-555-4683", "ana@hotmail.com", R.drawable.foto_04))
        contacts?.add(Contact("Maria", "Fernandez", "vmad", 24, 61.0, "C/b #43", "809-339-6421", "maria@hotmail.com", R.drawable.foto_05))

        list = findViewById(R.id.listView)
        adapter = HomeAdapter( contacts!!)
        list?.adapter = adapter

        list?.setOnItemClickListener { _, _, i, _ ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("ID", i.toString())
            startActivity(intent)

        }

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu,menu)

        val searchMessage = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu?.findItem(R.id.search)
        val searchView = searchItem?.actionView as SearchView

        searchView.setSearchableInfo(searchMessage.getSearchableInfo(componentName))
        searchView.queryHint = "Search contact..."

        searchView.setOnQueryTextFocusChangeListener { view, b ->
            //Preparar los datos

        }
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String?): Boolean {
                //Filtrar
                adapter?.filter(newText!!)
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
               //Filtrar
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.add ->{
                val intent = Intent(this, NewContactActivity::class.java)
                startActivity(intent)
                return true

            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        adapter?.notifyDataSetChanged()
    }

    private fun setToolbar(){
        val toolbar: Toolbar? = findViewById(R.id.toolbar)
        toolbar?.setTitle(R.string.app_name)
        setSupportActionBar(toolbar)
    }

}