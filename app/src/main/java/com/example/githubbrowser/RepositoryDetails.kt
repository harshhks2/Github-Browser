package com.example.githubbrowser

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class RepositoryDetails : AppCompatActivity() {

    private lateinit var viewPager2: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var tvRepoName: TextView
    private lateinit var tvDescription: TextView
    private lateinit var owner: String
    private lateinit var name: String
    private lateinit var description: String
    private var branchDetailsList = ArrayList<DataBranches>()
    private var issueDetailsList = ArrayList<DataIssues>()
    private lateinit var queue1: RequestQueue


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        viewPager2 = findViewById(R.id.viewPager2)
        tabLayout = findViewById(R.id.tabLayout)
        tvRepoName = findViewById(R.id.tvDetailsPageRepoName)
        tvDescription = findViewById(R.id.tvDetailsPageRepoDescription)


        name = intent.getStringExtra("name").toString()
        owner = intent.getStringExtra("owner").toString()
        description = intent.getStringExtra("description").toString()

        tvRepoName.text = name
        tvDescription.text = description
        loading = ProgressDialog(this)
        loading.startProgressDialog()
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        queue1 = Volley.newRequestQueue(this)
        callVolleyforBranches(1)
        callVolleyforIssues(1)

    }

    fun callVolleyforBranches(pageNumber: Int) {

        val url1 = "https://api.github.com/repos/${owner}/${tvRepoName.text}/branches?page=${pageNumber}"
        var text1 = "Not Available"


        val jsonArrayRequest1 = JsonArrayRequest(Request.Method.GET, url1, null,
                { response ->
                    if(response.length() > 0){
                    for (i in 0..response.length() - 1) {
                        val responseObj = response.getJSONObject(i)
                        val commitObj = responseObj.getJSONObject("commit")
                        val branch = DataBranches(owner, name, responseObj.getString("name"), commitObj.getString("sha"))
                        branchDetailsList.add(branch)
                    }
                        callVolleyforBranches(pageNumber+1)
                    }
                    else {
                        //loading.dismissProgressDialog()
                        }

                },
                { error ->
                    Toast.makeText(this, "No Branches Found", Toast.LENGTH_LONG).show()
                    //loading.dismissProgressDialog()

                }
        )
        queue1.add(jsonArrayRequest1)

    }
    fun callVolleyforIssues(pageNumber: Int) {

        val url2 = "https://api.github.com/repos/${owner}/${name}/issues?page=${pageNumber}"
        var text2 = "Not Available"


        val jsonArrayRequest2 = JsonArrayRequest(Request.Method.GET, url2, null,
                { response ->
                    if(response.length() > 0) {
                        for (i in 0..response.length() - 1) {
                            val responseObj = response.getJSONObject(i)
                            val userObj = responseObj.getJSONObject("user")
                            val issue = DataIssues(owner, name,responseObj.getString("title"), userObj.getString("login"), userObj.getString("avatar_url"))
                            issueDetailsList.add(issue)

                        }
                        callVolleyforIssues(pageNumber+1)
                    }
                    else{
                        setAllViews()
                        loading.dismissProgressDialog()
                    }
                },
                { error ->
                    Toast.makeText(this,"No Issues Found",Toast.LENGTH_LONG).show()
                    //loading.dismissProgressDialog()

                }
        )
        queue1.add(jsonArrayRequest2)


    }
    fun setAllViews(){
        val fragmentList = arrayListOf(
                FragmentBranches.newInstance(branchDetailsList),
                FragmentIssues.newInstance(issueDetailsList),
        )

        viewPager2.adapter = ViewPagerAdapter(this, fragmentList)
        var tabTitles = arrayOf("Branches", "Issues(${issueDetailsList.size})")

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.text = tabTitles[position]
            viewPager2.setCurrentItem(tab.position, true)

        }.attach()


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_details,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.miDelete -> { val repo = DataRepository(owner,tvRepoName.text.toString(),"Delete")
                val db = DatabaseHelper(this)
                db.deleteData(repo)
                var intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            R.id.miBrowse -> {
                val url = "https://github.com/${owner}/${name}"
                val webpage: Uri = Uri.parse(url)
                val intent = Intent(Intent.ACTION_VIEW, webpage)
                if (intent.resolveActivity(packageManager) != null)
                    startActivity(intent)}
            android.R.id.home ->{
                onBackPressed()
            }

        }
        return true
    }
    companion object{
        lateinit var loading: ProgressDialog
    }

}