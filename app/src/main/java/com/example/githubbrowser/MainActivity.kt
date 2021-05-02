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


//        repolist.add(Repository("yes","joker"))
//        repolist.add(Repository("no","joker"))
//        repolist.add(Repository("yes","hi"))
//        repolist.add(Repository("wow","wow"))
//        repolist.add(Repository("tda","jor"))
        val db = DatabaseHelper(this)
        repolist = db.readData() as ArrayList<DataRepository>
        //var descriptionList: MutableList<String> = ArrayList()

//        for(i in repolist) {
//            val queue = Volley.newRequestQueue(this)
//            val url = "https://api.github.com/repos/${i.owner}/${i.name}"
//            var text = "Not Available"
//
//            //Toast.makeText(this, url, Toast.LENGTH_LONG).show()
//            //val url = "https://api.github.com/repos/harshhks2/RoutineMaker"
//            //val url = "https://www.metaweather.com//api/${owner}/${repo}/"
//
//            val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
//                    { response ->
//                        text = response.getString("description").toString()
//                        if(text=="null") text = "Not Available"
//                        //Log.d("Tarzan",text)
//                        descriptionList.add(text)
//
//                    },
//                    { error ->
//                        descriptionList.add("Not Available")
//                        //Log.d("Tarzan","Not")
//                    }
//            )
//            //Log.d("Tarzan",text)
//
//            queue.add(jsonObjectRequest)
//        }
//        Thread.sleep(2000)
        //Log.d("Tarzan",descriptionList.toString())





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