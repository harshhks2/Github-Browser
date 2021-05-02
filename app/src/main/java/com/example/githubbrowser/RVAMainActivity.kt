package com.example.githubbrowser

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley


class RVAMainActivity(private val dataSet: ArrayList<DataRepository>) :
    RecyclerView.Adapter<RVAMainActivity.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)//, View.OnClickListener
    {
        val tvRepoName: TextView
        val tvRepoDescripton: TextView
        val ivShareButton: ImageView
        val clRepoItem: ConstraintLayout

        init {
            // Define click listener for the ViewHolder's View.
            tvRepoName = view.findViewById(R.id.tvMainPageRepoName)
            tvRepoDescripton = view.findViewById(R.id.tvMainPageRepoDescription)
            ivShareButton = view.findViewById(R.id.ivShare)
            clRepoItem = view.findViewById(R.id.clRepoItem)
//            clRepoItem.setOnClickListener(this)


        }


//        override fun onClick(p0: View?) {
//            val context=clRepoItem.context
//            var intent = Intent(context, Details::class.java)
//            intent.putExtra("name",tvRepoName.text.toString())
//            intent.putExtra("owner","owner")
//            context.startActivity(intent)
//
//        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.repo_item, viewGroup, false)


        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        val name = dataSet[position].name
        val owner = dataSet[position].owner
        viewHolder.tvRepoName.text = name
        viewHolder.tvRepoDescripton.text = dataSet[position].description
        val context=viewHolder.clRepoItem.context
//        viewHolder.tvRepoDescripton.text = descriptionSet[position].subSequence(0, 6)
//        viewHolder.tvRepoDescripton.text = callVolley(context,dataSet[position].owner,dataSet[position].name)
        //callVolley(context,dataSet[position].owner,dataSet[position].name)
//        viewHolder.tvRepoDescripton.text = basicText

//        viewHolder.tvRepoDescripton.text = dataSet[position].name
        viewHolder.clRepoItem.setOnClickListener(){

            var intent = Intent(context, RepositoryDetails::class.java)
            intent.putExtra("name",name)
            intent.putExtra("owner",owner)
            intent.putExtra("description",dataSet[position].description)
            context.startActivity(intent)
        }
        viewHolder.ivShareButton.setOnClickListener(){
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            val message = "Name:${name}, Description:${dataSet[position].description}, URL: https://github.com/${owner}/${name}"
            intent.putExtra(Intent.EXTRA_TEXT,message)
            if(intent.resolveActivity(context.packageManager) != null)
            context.startActivity(intent)
            else Toast.makeText(context,"Error",Toast.LENGTH_LONG).show()
        }

    }

    override fun getItemCount() = dataSet.size


}
