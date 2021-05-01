package com.example.githubbrowser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FragmentIssues(private val issueDetailsList: ArrayList<DataIssues>) : Fragment() {

    private lateinit var rvIssues: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_issues, container, false)
        rvIssues = view.findViewById(R.id.rvIssues)
        rvIssues.adapter = RVAIssues(issueDetailsList)
        rvIssues.layoutManager = LinearLayoutManager(rvIssues.context)

        return view



    }

    companion object {
        fun newInstance(issueDetailsList: ArrayList<DataIssues>) = FragmentIssues(issueDetailsList)
    }
}