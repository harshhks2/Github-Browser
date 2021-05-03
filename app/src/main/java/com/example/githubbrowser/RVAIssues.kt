package com.example.githubbrowser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class RVAIssues (private val dataSet: ArrayList<DataIssues>) :
        RecyclerView.Adapter<RVAIssues.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
    {
        val tvIssueTitle: TextView
        val tvIssueCreator: TextView
        val ivAvatar: ImageView


        init {
            // Define click listener for the ViewHolder's View.
            tvIssueTitle = view.findViewById(R.id.tvIssueTitle)
            tvIssueCreator = view.findViewById(R.id.tvIssueCreator)
            ivAvatar = view.findViewById(R.id.ivAvatar)

        }
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.issue_item, viewGroup, false)


        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder:ViewHolder, position: Int) {
        if(!dataSet.isNullOrEmpty()) {
            viewHolder.tvIssueTitle.text = dataSet[position].issueTitle
            viewHolder.tvIssueCreator.text = dataSet[position].issueCreator
            Glide.with(viewHolder.ivAvatar.context).load(dataSet[position].avatarURL).into(viewHolder.ivAvatar)
        }
       // RepositoryDetails.loading.dismissProgressDialog()

    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)


    }

    override fun getItemCount() = dataSet.size
}