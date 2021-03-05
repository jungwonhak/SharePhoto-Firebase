package com.example.sharephoto.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sharephoto.Post
import com.example.sharephoto.R
import com.example.sharephoto.SharePhotoActivity
import com.example.sharephoto.adapter.RecyclerAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_feed.*

class FeedActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database:FirebaseFirestore
    private lateinit var recyclerViewAdapter:RecyclerAdapter

    var postList=ArrayList<Post>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        auth=FirebaseAuth.getInstance()
        database= FirebaseFirestore.getInstance()
        getData()

        var layoutManager=LinearLayoutManager(this)
        recyclerView.layoutManager=layoutManager
        recyclerViewAdapter= RecyclerAdapter(postList)
        recyclerView.adapter=recyclerViewAdapter
    }

    fun getData(){
        database.collection("Post").orderBy("date",Query.Direction.DESCENDING).addSnapshotListener { value, error ->
            if(error!=null){
                Toast.makeText(this,error.localizedMessage,Toast.LENGTH_LONG).show()
            }else{
                if(value!=null){
                    if(!value.isEmpty){
                        val documents=value.documents

                        postList.clear()

                        for(document in documents){
                            val userEmail=document.get("useremail") as String
                            val userComment=document.get("usercomment") as String
                            val imageUrl=document.get("imageurl") as String

                            val downloadedPost= Post(userEmail, userComment, imageUrl)
                            postList.add(downloadedPost)
                        }
                        recyclerViewAdapter.notifyDataSetChanged()
                    }

                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater=menuInflater
        menuInflater.inflate(R.menu.options_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId== R.id.share_photo){
            val intent= Intent(this, SharePhotoActivity::class.java)
            startActivity(intent)
        }else if(item.itemId== R.id.log_out){
            auth.signOut()
            val intent=Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}