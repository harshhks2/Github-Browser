package com.example.githubbrowser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class AddRepo : AppCompatActivity() {

    private lateinit var etOwner: EditText
    private lateinit var tvOwner: TextView
    private lateinit var etRepoName: EditText
    private lateinit var btAdd: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_repo)
        etOwner = findViewById(R.id.etOwner)
        tvOwner = findViewById(R.id.tvOwner)
        etRepoName = findViewById(R.id.etRepoName)
        btAdd = findViewById(R.id.btAddRepo)
        val owner = etOwner.text
        val name = etRepoName.text


        btAdd.setOnClickListener(){

            val queue = Volley.newRequestQueue(this)
            val url = "https://api.github.com/repos/${owner}/${name}"
            //Toast.makeText(this, url, Toast.LENGTH_LONG).show()
            //val url = "https://api.github.com/repos/harshhks2/RoutineMaker"
            //val url = "https://www.metaweather.com//api/${owner}/${repo}/"

            val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
                { response ->
                    var description = response.getString("description").toString()
                    if (etOwner.text.toString().isNotEmpty() &&
                            etRepoName.text.toString().isNotEmpty()
                    ) {
                        if(description=="null") description = "Description Not Available"
                        val repo = DataRepository(etOwner.text.toString(), etRepoName.text.toString(), description)
                        //val repon = Repository("hello","there")
                        val db = DatabaseHelper(this)
                        db.insertData(repo)
                        //Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        //Toast.makeText(this, "Please Fill All Data", Toast.LENGTH_SHORT).show()
                        showAlertDialog("Please Fill All The Fields")
                    }
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)

                },
                { error ->
                    //Toast.makeText(this, "Repository Not Found", Toast.LENGTH_LONG).show()
                    showAlertDialog("Repository Not Found")

                }

            )

            queue.add(jsonObjectRequest)

        }

    }
    fun showAlertDialog(message: String){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage(message)
        builder.setPositiveButton("Ok") { dialog, which ->

        }
        val dialog: AlertDialog = builder.create()
        dialog.show()

    }

}