package com.example.githubbrowser

import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class RVACommits (private val dataSet: ArrayList<DataCommits>) : RecyclerView.Adapter<RVACommits.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
    {
        val tvCommitDate: TextView
        val tvCommitMessage: TextView
        val ivUserImage: ImageView
        val tvUserName: TextView

        init {
            // Define click listener for the ViewHolder's View.
            tvCommitDate= view.findViewById(R.id.tvCommitDate)
            tvCommitMessage = view.findViewById(R.id.tvCommitMessage)
            ivUserImage = view.findViewById(R.id.ivUserImage)
            tvUserName= view.findViewById(R.id.tvUserName)

        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
//        if(viewType == 1) {
            val view = LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.commit_item, viewGroup, false)
            return ViewHolder(view)
//        }
//        else {
//            val view = LayoutInflater.from(viewGroup.context)
//                    .inflate(R.layout.load_more, viewGroup, false)
//            return ViewHolder(view)
//        }




    }

    override fun onBindViewHolder(viewHolder:ViewHolder, position: Int) {
        if(!dataSet.isNullOrEmpty()) {
            viewHolder.tvCommitDate.text = dataSet[position].commitDate.subSequence(0,10)
            viewHolder.tvCommitMessage.text = dataSet[position].commitMessage
            viewHolder.tvUserName.text = dataSet[position].userName
            Glide.with(viewHolder.ivUserImage.context).load(dataSet[position].userImageURL).into(viewHolder.ivUserImage)

        }

    }

//    override fun getItemViewType(position: Int): Int {
//        if(position >= dataSet.size-1)
//        return 2
//        else return 1
//    }

    override fun getItemCount() = dataSet.size


}