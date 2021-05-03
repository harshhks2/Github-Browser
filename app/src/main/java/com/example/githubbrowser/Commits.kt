package com.example.githubbrowser

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AbsListView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley


class Commits : AppCompatActivity() {

    private lateinit var rvCommits: RecyclerView
    private var commitDetailsList = ArrayList<DataCommits>()
    private lateinit var owner: String
    private lateinit var name: String
    private lateinit var sha: String
    var isScrolling = false
    var currentItems = 0
    var totalItems = 0
    var scrollOutItems = 0
    val manager = LinearLayoutManager(this)
    private lateinit var pBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_commits)

        rvCommits = findViewById(R.id.rvCommits)
        pBar = findViewById(R.id.commitsProgressBar)
         owner = intent.getStringExtra("owner")
         name = intent.getStringExtra("name")
         sha = intent.getStringExtra("sha")
        var pageNumber = 1
        rvCommits.adapter = RVACommits(commitDetailsList)
        rvCommits.layoutManager = manager
        callVolleyforCommit(pageNumber)

        rvCommits.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                currentItems = manager.getChildCount()
                totalItems = manager.getItemCount()
                scrollOutItems = manager.findFirstVisibleItemPosition()
                if (isScrolling && currentItems + scrollOutItems == totalItems) {
                    isScrolling = false
                    pBar.visibility = View.VISIBLE
                    callVolleyforCommit(pageNumber+1)
                }
            }
        })


    }

    fun callVolleyforCommit(pageNumber: Int) {
        val queue = Volley.newRequestQueue(this)
        val url = "https://api.github.com/repos/${owner}/${name}/commits?${sha}=main&page=${pageNumber}"
        var text = "Not Available"


        val jsonArrayRequest2 = JsonArrayRequest(Request.Method.GET, url, null,
                { response ->
                    for (i in 0..response.length() - 1) {
                        val responseObj = response.getJSONObject(i)
                        //Log.d("Tarzan",responseObj.toString())
                        val commitObj = responseObj.getJSONObject("commit")
                        //Log.d("Tarzan",commitObj.toString())
                        val authorObj = commitObj.getJSONObject("author")
                        //Log.d("Tarzan",authorObj.toString())
                        val authorImageObj = responseObj.getJSONObject("author")
                        //Log.d("Tarzan",authorImageObj.toString())
                        val commit = DataCommits(authorObj.getString("date"), commitObj.getString("message"),
                                authorObj.getString("name"), authorImageObj.getString("avatar_url"),responseObj.getString("sha"))
                        commitDetailsList.add(commit)
                    }
//                    rvCommits.adapter = RVACommits(commitDetailsList)
//                    rvCommits.layoutManager = manager
                    rvCommits.adapter?.notifyDataSetChanged()
                    pBar.visibility = View.GONE

                },
                { error ->
                    Toast.makeText(this, "No commits Found", Toast.LENGTH_LONG).show()

                }
        )
        queue.add(jsonArrayRequest2)



    }
}