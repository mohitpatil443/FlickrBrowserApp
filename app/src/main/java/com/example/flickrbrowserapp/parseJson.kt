package com.example.flickrbrowserapp

import android.os.AsyncTask
import android.util.Log
import org.json.JSONException
import org.json.JSONObject
//Async task to start the activity on different thread
//listener refers to the main activity object
class parseJson(private val listener:OnDataAvailable) :AsyncTask<String,Void,ArrayList<photo>>(){
    val TAG="parseJson"

   private var photoArray=ArrayList<photo>()
//interface main activity should implement so that the control is transferred
    interface OnDataAvailable
    {
        fun onDataAvailable(data:ArrayList<photo>)
        fun onError(exception:Exception)
    }


    override fun onPostExecute(result: ArrayList<photo>) {
        super.onPostExecute(result)
        listener.onDataAvailable(result)
        //pass the main data llist to main activity
    }

    override fun doInBackground(vararg p0: String?): ArrayList<photo> {
        try{
            //creating a new object of json and then parsing
       val jsonObj=JSONObject(p0[0])
        val jsonArray=jsonObj.getJSONArray("items")   //Items is the array name in json

        for(i in 0 until jsonArray.length()) {
            val item = jsonArray.getJSONObject(i)    //these are single strings
            val title = item.getString("title")
            val author = item.getString("author")
            val authorId = item.getString("author_id")
            val tags = item.getString("tags")

            val mediaObj = item.getJSONObject("media") //media is an object with curly braces i.e obj within obj
            val image = mediaObj.getString("m") //"media": {
            val link = image.replaceFirst(
                "_m.jpg",
                "_b.jpg"
            ) //link refers to onClick // "m": "https://farm8.staticflickr.com/7837/47048290591_3849a68302_m.jpg"                            // },

            val photoObj = photo(title, author, authorId, link, tags, image)

            photoArray.add(photoObj)
            //add the object to the array



        }

        }catch(e:JSONException)
        {
            e.printStackTrace()
            Log.d(TAG,"Error parsing Json ${e.message}")
            listener.onError(e)
        }

        return photoArray

        //return to on post execute

    }

}