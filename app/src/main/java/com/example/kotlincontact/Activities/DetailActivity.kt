package com.example.kotlincontact.Activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.kotlincontact.R


class DetailActivity : AppCompatActivity() {

    private var toolbar:Toolbar? = null
    private var index:Int = 0

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setToolbar()

        index = intent.getStringExtra("ID")?.toInt()!!
        mapData()

    }

    private fun mapData(){
        val contact = index.let { HomeActivity.getContact(it) }

        val imgPhoto = findViewById<ImageView>(R.id.imgPhoto)
        val tvName = findViewById<TextView>(R.id.tvName)
        val tvCompany = findViewById<TextView>(R.id.tvCompany)
        val tvAge = findViewById<TextView>(R.id.tvAge2)
        val tvWeight = findViewById<TextView>(R.id.tvWeight2)
        val tvPhone = findViewById<TextView>(R.id.tvPhone2)
        val tvEmail = findViewById<TextView>(R.id.tvEmail2)
        val tvAddress = findViewById<TextView>(R.id.tvAddress2)

        Glide.with(applicationContext)
            .load(contact.photo)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .circleCrop()
            .into(imgPhoto)
        tvName.text = contact.name + " " + contact.lastName
        tvCompany.text = contact.company
        tvAge.text = contact.age.toString() + " años"
        tvWeight.text = contact.weight.toString() + " kg"
        tvPhone.text = contact.phone
        tvEmail.text = contact.email
        tvAddress.text = contact.address


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){

            android.R.id.home -> {
                finish()
                return true
            }

            R.id.edit -> {
                val intent = Intent(this, NewContactActivity::class.java)
                intent.putExtra("ID", index.toString())
                startActivity(intent)
                return true

            }

            R.id.delete -> {
                HomeActivity.deleteContact(index)
                finish()
                return true

            }else -> {
            return super.onOptionsItemSelected(item)
            }
        }

    }

    private fun setToolbar(){
        toolbar = findViewById(R.id.toolbar)
        toolbar?.title = getString(R.string.detail_message)
        setSupportActionBar(toolbar)

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onResume() {
        super.onResume()
        mapData()
    }
}