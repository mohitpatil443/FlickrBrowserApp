package com.example.flickrbrowserapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity()
    ,rawData.OnDownloadComplete
    ,parseJson.OnDataAvailable

{

    val TAG="MainActivity"
    private val flickrAdapter=flickrAdapter(ArrayList(),{Item:photo->ItemClicked(Item)})
    //lambda function for each itemClickListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       setSupportActionBar(toolbar)
        //setting the toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        //for the back arrow on toolbar

        recycler.layoutManager=LinearLayoutManager(this)



        //adding onitemtouchlistener to each view
        recycler.adapter=flickrAdapter




    }


    override fun onError(exception: Exception) {
        Log.d(TAG,"onError : Called")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
     //if the toolabar menu is clicked
        return when (item.itemId) {
            R.id.action_search -> {
                startActivity(Intent(this,SearchActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

//    companion object {
//        private const val TAG="MainActivity"    //Acts as static declaration for the class Only one copy is created
//
//    }
    override fun onDownloadComplete(result:String,status:DownloadStatus)
{
    if(status==DownloadStatus.OK)
    {
        Log.d(TAG,"onDownloadComplete : called")

        val parseJson=parseJson(this)
        parseJson.execute(result)


    }else
    {
        Log.d(TAG,"Error occured")
    }
}

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        //inflate the menu options
        menuInflater.inflate(R.menu.menu_main,menu)
        return true
    }

    override fun onDataAvailable(data: ArrayList<photo>) {
        //where the parsed json data list is returned and displayed on screen
        Log.d(TAG,"onDataAvailable : called")
        flickrAdapter.loadNewData(data)

    }


    private fun buildUri(base:String,searchCriteria:String,lang:String,matchAll:Boolean):String{
        //build the uri according to our needs
        Log.d(TAG,"URI builder called")
        return Uri.parse(base).buildUpon()
            .appendQueryParameter("tags",searchCriteria)
            .appendQueryParameter("tagmode",if(matchAll) "ALL" else "ANY")
            .appendQueryParameter("lang",lang)
            .appendQueryParameter("format","json")
            .appendQueryParameter("nojsoncallback","1")
            .build().toString()
    }



    fun ItemClicked(ele:photo)
    {
        //lambda function where each item click is responded
        val intent=Intent(this,photodetails::class.java)
        intent.putExtra("link",ele.link)
        intent.putExtra("author",ele.author)
        intent.putExtra("title",ele.title)
        intent.putExtra("tags",ele.tags)

        //put the data in order to extract and display the image in photoDetails



        startActivity(intent)
    }

    override fun onResume() {
        //implemented for navigating from searchActivity but not starting an intent
        Log.d("MainActivity","OnResume: called")
        super.onResume()
        //shared prefrences  implemented for storing key value pairs for passing search keywords

        val sharedPref=PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val queryResult=sharedPref.getString(FLICKR_QUERY,"")

        if(queryResult.isNotEmpty())
        {
            //download new data according to the search criteria
            val uri=buildUri("https://api.flickr.com/services/feeds/photos_public.gne",queryResult,"en-us",true)
            val RawData=rawData(this)

            RawData.execute(uri)

        }
    }



}
