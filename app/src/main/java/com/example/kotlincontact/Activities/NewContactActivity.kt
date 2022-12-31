package com.example.kotlincontact.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.kotlincontact.Models.Contact
import com.example.kotlincontact.R
import com.google.android.material.textfield.TextInputEditText

class NewContactActivity : AppCompatActivity() {

    private var toolbar:Toolbar? = null
    private var photoIndex:Int = 0
    private var imgPhoto:ImageView? = null
    private val photos = arrayOf(R.drawable.foto_01, R.drawable.foto_02, R.drawable.foto_04, R.drawable.foto_05, R.drawable.foto_06)
    private var index:Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_contact)

        setToolbar()

        imgPhoto = findViewById(R.id.imgPhoto)
        imgPhoto?.setOnClickListener {
            selectPhoto()
        }

        if (intent.hasExtra("ID")){
            index = intent.getStringExtra("ID")!!.toInt()
            fillData(index)

        }

    }

    private fun fillData(index: Int) {
        val contact = HomeActivity.getContact(index)

        //Referenciar los campos de nuevos contacto
        val imgPhoto = findViewById<ImageView>(R.id.imgPhoto)
        val name = findViewById<TextInputEditText>(R.id.etName)
        val lastName = findViewById<TextInputEditText>(R.id.etLastName)
        val company = findViewById<TextInputEditText>(R.id.etCompany)
        val age = findViewById<TextInputEditText>(R.id.etAge)
        val weight = findViewById<TextInputEditText>(R.id.etWeight)
        val phone = findViewById<TextInputEditText>(R.id.etPhone)
        val email = findViewById<TextInputEditText>(R.id.etEmail)
        val address = findViewById<TextInputEditText>(R.id.etAddress)


        name.setText(contact.name, TextView.BufferType.EDITABLE)
        lastName.setText(contact.lastName, TextView.BufferType.EDITABLE)
        company.setText(contact.company, TextView.BufferType.EDITABLE)
        age.setText( contact.age.toString(), TextView.BufferType.EDITABLE)
        weight.setText(contact.weight.toString(), TextView.BufferType.EDITABLE)
        phone.setText(contact.phone, TextView.BufferType.EDITABLE)
        email.setText(contact.email, TextView.BufferType.EDITABLE)
        address.setText(contact.address,TextView.BufferType.EDITABLE)

        Glide.with(applicationContext)
            .load(contact.photo)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .circleCrop()
            .into(imgPhoto)

        var position = 0
        for (photo in photos){
            if (contact.photo == photo){
                photoIndex = position
            }
            position++
        }


    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.new_contact_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){

            android.R.id.home -> {
                finish()
                return true
            }

            R.id.add_contact -> {
                //Referenciar los campos de nuevos contacto
                val name = findViewById<TextInputEditText>(R.id.etName)
                val lastName = findViewById<TextInputEditText>(R.id.etLastName)
                val company = findViewById<TextInputEditText>(R.id.etCompany)
                val age = findViewById<TextInputEditText>(R.id.etAge)
                val weight = findViewById<TextInputEditText>(R.id.etWeight)
                val phone = findViewById<TextInputEditText>(R.id.etPhone)
                val email = findViewById<TextInputEditText>(R.id.etEmail)
                val address = findViewById<TextInputEditText>(R.id.etAddress)

                //Validar campos
                val fields = ArrayList<String>()
                fields.add(name.text.toString())
                fields.add(lastName.text.toString())
                fields.add(company.text.toString())
                fields.add(age.text.toString())
                fields.add(weight.text.toString())
                fields.add(phone.text.toString())
                fields.add(email.text.toString())
                fields.add(address.text.toString())

                var float = 0
                for (field in fields){
                    if (field.isEmpty())
                        float++
                }
                if (float > 0){
                    Toast.makeText(this, "Please fill in all the fields",Toast.LENGTH_LONG).show()

                }else {
                    if (index > -1){
                        HomeActivity.updateContact(index,Contact(
                            fields[0],
                            fields[1],
                            fields[2],
                            fields[3].toInt(),
                            fields[4].toDouble(),
                            fields[5],
                            fields[6],
                            fields[7],
                            getPhoto(photoIndex)))
                        finish()
                    }else {
                        HomeActivity.addContact(Contact(
                            fields[0],
                            fields[1],
                            fields[2],
                            fields[3].toInt(),
                            fields[4].toDouble(),
                            fields[5],
                            fields[6],
                            fields[7],
                            getPhoto(photoIndex)))
                        finish()
                    }

                    finish()
                    Log.d("Elements", HomeActivity.contacts?.count().toString())

                }

                return true

            }  else ->{
            return super.onOptionsItemSelected(item)
        }

        }
    }

        fun selectPhoto(){
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Select a profile picture")

            val adapterDialog = ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item)
            adapterDialog.add("Foto 01")
            adapterDialog.add("Foto 02")
            adapterDialog.add("Foto 03")
            adapterDialog.add("Foto 04")
            adapterDialog.add("Foto 05")

            builder.setAdapter(adapterDialog){
                    _, wich ->

                photoIndex = wich

                imgPhoto?.setImageResource(getPhoto(photoIndex))
            }

            builder.setNegativeButton("Cancel"){
                    dialog, _ ->
                dialog.dismiss()
            }
            builder.show()

        }
    //Metodo para obtener las fotos
    fun getPhoto(index: Int): Int{
        return photos.get(index)
    }

    //Metodo para configurar la toolbar
    private fun setToolbar(){
        toolbar = findViewById(R.id.toolbar)
        toolbar?.title = getString(R.string.add_contact_message)
        setSupportActionBar(toolbar)

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }


}