package com.example.flickrbrowserapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details.*




 class photodetails : AppCompatActivity()
{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)


        val bundle = intent.extras
        val link = bundle?.getString("link")
        val author = bundle?.getString("author")
        val tags=bundle?.getString("tags")
        val title=bundle?.getString("title")
        //using placceholders in strings
        //Receive the object values from main activity


            Picasso.get().load(link)
            .error(R.drawable.placeholder)
            .placeholder(R.drawable.placeholder)
            .into(imageView)

        photo_author.text=author

        photo_tags.text=getString(R.string.tags_photo,tags)

        photo_title.text=getString(R.string.title_photo,title)

        //set the new data

    }



}
