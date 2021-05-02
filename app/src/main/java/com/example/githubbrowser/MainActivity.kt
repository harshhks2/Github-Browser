package com.example.githubbrowser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var btAddNow: Button
    private lateinit var rvRepositories: RecyclerView
    private lateinit var llMainScreen: LinearLayout
    private var repolist = ArrayList<DataRepository>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btAddNow = findViewById(R.id.btAddNow)
        rvRepositories = findViewById(R.id.rvRepositories)
        llMainScreen = findViewById(R.id.llMainScreen)
//        val loading = ProgressDialog(this)
//        loading.startProgressDialog()



        btAddNow.setOnClickListener(){
            var intent = Intent(this, AddRepo::class.java)
            startActivity(intent)
        }

        val db = DatabaseHelper(this)
        repolist = db.readData() as ArrayList<DataRepository>


        if(repolist.isEmpty()) {
            llMainScreen.visibility = View.VISIBLE
            rvRepositories.visibility = View.GONE
        }
        else {

            rvRepositories.adapter = RVAMainActivity(repolist)
            rvRepositories.layoutManager = LinearLayoutManager(this)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.miAdd -> {val intent = Intent(this, AddRepo::class.java)
                startActivity(intent)}

        }
        return true
    }


}