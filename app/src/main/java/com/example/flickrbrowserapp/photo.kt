package com.example.flickrbrowserapp

class photo (val title:String ,val author : String, val authorId : String,val link : String,val tags:String, val image:String){

    override fun toString(): String {
        return "photo(title='$title', author='$author', authorId='$authorId', link='$link', tags='$tags', image='$image')"
    }

    //List considered as list of photos
    //blueprint of photos
}