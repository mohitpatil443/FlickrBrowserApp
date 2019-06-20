package com.example.flickrbrowserapp


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.element.view.*


//takes in two args list and listener which is a lambda function from main activity

class flickrAdapter(private var list:List<photo>,private val listener:(photo)->Unit):RecyclerView.Adapter<flickrAdapter.ViewHolder>()
{
    //Implements recyclerView.Adapter
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): flickrAdapter.ViewHolder {

        val view=LayoutInflater.from(p0.context).inflate(R.layout.element,p0,false)
       return ViewHolder(view)
        //Create the element


    }

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)
    {
        //in order to set an itemClick listener ton the itemView
        fun bind(ele:photo,listener: (photo) -> Unit)
        {

            Picasso.get().load(ele.image)
            .error(R.drawable.placeholder)
            .placeholder(R.drawable.placeholder)
            .into(itemView.thumbnail)

            itemView.title.text=ele.title

            itemView.setOnClickListener{listener(ele)}

        }

    }

    override fun getItemCount(): Int {
        if(list.isNotEmpty())
            return list.size

        else
            return 1

    }

    override fun onBindViewHolder(p0: flickrAdapter.ViewHolder, p1: Int) {

        if(list.isEmpty())
        {
            p0.itemView.title.setText(R.string.NoImage)
            p0.itemView.thumbnail.setImageResource(R.drawable.placeholder)
        }
        else {
            p0.bind(list[p1], listener)
        }


    }
    fun loadNewData(new:List<photo>){
        //load new data
        list=new
        notifyDataSetChanged()

    }
}