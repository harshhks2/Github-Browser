package com.example.githubbrowser


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import java.lang.Exception


class RVABranches(private val dataSet: ArrayList<DataBranches>) :
        RecyclerView.Adapter<RVABranches.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
    {
        val tvBranchName: TextView
        val cvBranches: CardView


        init {
            // Define click listener for the ViewHolder's View.
            tvBranchName = view.findViewById(R.id.tvBranchName)
            cvBranches = view.findViewById(R.id.cvBranches)

        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.branch_item, viewGroup, false)


        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder:ViewHolder, position: Int) {
        if(!dataSet.isNullOrEmpty()) {
            viewHolder.tvBranchName.text = dataSet[position].branchName
            val context = viewHolder.tvBranchName.context

            try{
            viewHolder.cvBranches.setOnClickListener() {

                var intent = Intent(context, Commits::class.java)
                intent.putExtra("sha", dataSet[position].sha)
                intent.putExtra("owner", dataSet[position].owner)
                intent.putExtra("name", dataSet[position].name)
                context.startActivity(intent)
            }}
            catch(e: Exception) {
                val builder = AlertDialog.Builder(context)
                builder.setTitle("Error")
                builder.setMessage("Some error occurred while loading lhe content of commits of this branch. Please try again later.")
                builder.setPositiveButton("Ok") { dialog, which ->

                }
                val dialog: AlertDialog = builder.create()
                dialog.show()
            }
        }
    }

    override fun getItemCount() = dataSet.size
}