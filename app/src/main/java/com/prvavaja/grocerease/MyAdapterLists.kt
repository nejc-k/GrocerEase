package com.prvavaja.grocerease

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import net.nicbell.materiallists.ListItem

class MyAdapterLists(val app: MyApplication) :
    RecyclerView.Adapter<MyAdapterLists.MyViewHolder>() {
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val listItem: ListItem
        init{
            listItem = itemView.findViewById(R.id.listItemId)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.lists_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val current = app.listOfgrocerylists.getAllLists()[position]
        holder.listItem.headline.text = current.listName
        val text = "Number of items: ${current.items.size.toString()}\nCreated: ${current.date}"
        holder.listItem.supportText.text = text

        holder.itemView.setOnLongClickListener {
            showDeleteConfirmationDialog(holder.itemView.context, position)
            true
        }
        holder.itemView.setOnClickListener {
            showGroceryList(holder.itemView.context, position)
        }
    }

    private fun showGroceryList(context: Context, position: Int) {
        val intent = Intent(context, SingleListActivity::class.java)
        app.currentList = app.listOfgrocerylists.getAllLists()[position]
        context.startActivity(intent)
    }

    private fun showDeleteConfirmationDialog(context: Context, position: Int) {
        if(app.listOfgrocerylists.getAllLists()[position].items.size >= 1){
            Toast.makeText(context, "You can't delete lists that have items in it", Toast.LENGTH_SHORT).show()
            return
        }
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setTitle("Delete Item")
        alertDialogBuilder.setMessage("Are you sure you want to delete this item?")

        alertDialogBuilder.setPositiveButton("Yes") { _, _ ->
            val serialization = Serialization(context)
            serialization.delete(app.listOfgrocerylists.getAllLists()[position].uuid)
            app.listOfgrocerylists.getAllLists().removeAt(position)

            notifyItemRemoved(position)
        }

        alertDialogBuilder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }

        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    override fun getItemCount() = app.listOfgrocerylists.getAllLists().size
}