package com.example.githubbrowser


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView


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

            viewHolder.cvBranches.setOnClickListener() {

                var intent = Intent(context, Commits::class.java)
                intent.putExtra("sha", dataSet[position].sha)
                intent.putExtra("owner", dataSet[position].owner)
                intent.putExtra("name", dataSet[position].name)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount() = dataSet.size
}