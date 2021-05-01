package com.example.githubbrowser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley

class Commits : AppCompatActivity() {

    private lateinit var rvCommits: RecyclerView
    private var commitDetailsList = ArrayList<DataCommits>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_commits)

        rvCommits = findViewById(R.id.rvCommits)
        val owner = intent.getStringExtra("owner")
        val name = intent.getStringExtra("name")
        val sha = intent.getStringExtra("sha")

        val queue = Volley.newRequestQueue(this)
        val url = "https://api.github.com/repos/${owner}/${name}/commits?${sha}=main"
        var text = "Not Available"


        val jsonArrayRequest2 = JsonArrayRequest(Request.Method.GET, url, null,
                { response ->
                    for(i in 0..response.length()-1) {
                        val responseObj = response.getJSONObject(i)
                        val commitObj = responseObj.getJSONObject("commit")
                        val authorObj = commitObj.getJSONObject("author")
                        val authorImageObj = responseObj.getJSONObject("author")
                        val commit = DataCommits(authorObj.getString("date"), commitObj.getString("message"),
                                authorObj.getString("name"), authorImageObj.getString("avatar_url"))
                        commitDetailsList.add(commit)
                    }
                    rvCommits.adapter = RVACommits(commitDetailsList)
                    rvCommits.layoutManager = LinearLayoutManager(this)

                },
                { error ->
                    Toast.makeText(this,"No commits Found",Toast.LENGTH_LONG).show()

                }
        )
        queue.add(jsonArrayRequest2)







    }
}