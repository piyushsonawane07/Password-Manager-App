package com.example.passwordmanager


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Adapter for the password list.
 */
class PasswordsAdapter(private val listener: ItemClicked ,var passList:ArrayList<DataModel>) : RecyclerView.Adapter<PasswordsAdapter.ViewHolder>() {

    inner class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
        val title:TextView = itemView.findViewById(R.id.title_tv)
        val username:TextView = itemView.findViewById(R.id.username_tv)
        val imageView:ImageView = itemView.findViewById(R.id.logo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view  = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        val viewHolder = ViewHolder(view)
        view.setOnClickListener {
            listener.onItemClicked(passList[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val logo  = Logo()
        val currentItem = passList[position]
        holder.title.text = currentItem.title
        holder.username.text = currentItem.username

        val title = currentItem.title
        logo.setLogo(holder,title!!)
    }


    override fun getItemCount(): Int {
        return passList.size
    }
}

interface ItemClicked{
    fun onItemClicked(item:DataModel)
}

