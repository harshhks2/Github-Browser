package com.example.githubbrowser

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley

class FragmentBranches(private val branchDetailsList: ArrayList<DataBranches>) : Fragment() {


    private lateinit var rvBranches: RecyclerView
    //private var branchDetailsList = ArrayList<DataBranches>()
//    private lateinit var owner: String
//    private lateinit var name: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_branches, container, false)
        rvBranches = view.findViewById(R.id.rvBranches)
        rvBranches.adapter = RVABranches(branchDetailsList)
        rvBranches.layoutManager = LinearLayoutManager(rvBranches.context)
//        if(branchDetailsList.isNotEmpty()){owner = branchDetailsList[0].owner
//        name = branchDetailsList[0].name}


        return view


    }
//    fun callVolleyforBranches(pageNumber: Int) {
//
//        val queue1 = Volley.newRequestQueue(context)
//        val url1 = "https://api.github.com/repos/${owner}/${name}/branches?page=${pageNumber}"
//        var text1 = "Not Available"
//
//
//        val jsonArrayRequest1 = JsonArrayRequest(Request.Method.GET, url1, null,
//                { response ->
//                    if(response.length() > 0){
//                        for (i in 0..response.length() - 1) {
//                            val responseObj = response.getJSONObject(i)
//                            val commitObj = responseObj.getJSONObject("commit")
//                            val branch = DataBranches(owner, name, responseObj.getString("name"), commitObj.getString("sha"))
//                            branchDetailsList.add(branch)
//                            callVolleyforBranches(pageNumber+1)
//                        }
//                    }
//                    else {
//                        Log.d("Tarzan","else branch executed")
//
//                    }
//
//                },
//                { error ->
//
//                }
//        )
//        queue1.add(jsonArrayRequest1)
////        val branch = DataBranches(owner,name,"name", "sha")
////        branchDetailsList.add(branch)
//    }

    companion object {
        fun newInstance(branchDetailsList: ArrayList<DataBranches>) = FragmentBranches(branchDetailsList)

    }
}