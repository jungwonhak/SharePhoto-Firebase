package com.example.sharephoto.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sharephoto.Post
import com.example.sharephoto.R
import kotlinx.android.synthetic.main.activity_feed.view.*
import kotlinx.android.synthetic.main.recycler_row.view.*

class RecyclerAdapter(val postList: ArrayList<Post>):RecyclerView.Adapter<RecyclerAdapter.PostHolder>() {
    class PostHolder(itemView: View):RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val inflater=LayoutInflater.from(parent.context)
        val view=inflater.inflate(R.layout.recycler_row,parent,false)
        return PostHolder(view)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        holder.itemView.recyclerViewUserEmail.text=postList[position].userEmail
        holder.itemView.recyclerViewUserComment.text=postList[position].userComment
    }
}