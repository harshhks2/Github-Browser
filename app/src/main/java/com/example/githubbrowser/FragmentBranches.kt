package com.example.githubbrowser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FragmentBranches(private val branchDetailsList: ArrayList<DataBranches>) : Fragment() {


    private lateinit var rvBranches: RecyclerView
    //private var branchDetailsList = ArrayList<DataBranches>()
    private lateinit var hi: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_branches, container, false)
        rvBranches = view.findViewById(R.id.rvBranches)
        rvBranches.adapter = RVABranches(branchDetailsList)
        rvBranches.layoutManager = LinearLayoutManager(rvBranches.context)

        return view


    }

    companion object {
        fun newInstance(branchDetailsList: ArrayList<DataBranches>) = FragmentBranches(branchDetailsList)

    }
}