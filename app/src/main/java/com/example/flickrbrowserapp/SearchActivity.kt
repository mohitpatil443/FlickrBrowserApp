package com.example.flickrbrowserapp

import android.app.SearchManager
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.widget.SearchView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_search.*
internal const val FLICKR_QUERY = "FLICK_QUERY"

class SearchActivity : AppCompatActivity() {
    private var searchView:SearchView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

       setSupportActionBar(toolbarSearch)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        //Create a searchView widget
        menuInflater.inflate(R.menu.menu_search,menu)
        val searchManager=getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView=menu.findItem(R.id.action_search).actionView as SearchView
        val searchableInfo=searchManager.getSearchableInfo(componentName)
        searchView?.setSearchableInfo(searchableInfo)

        searchView?.isIconified=false

        searchView?.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {

                //After submit call the update recyclerview by downloading the data
                val sharedPref=PreferenceManager.getDefaultSharedPreferences(applicationContext)
                sharedPref.edit().putString(FLICKR_QUERY,query).apply()
                searchView?.clearFocus()




                finish()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
              return false
            }


        })
        searchView?.setOnCloseListener {
            finish()
            false
        }
        return true


    }
}
