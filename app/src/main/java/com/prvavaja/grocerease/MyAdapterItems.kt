package com.prvavaja.grocerease

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.checkbox.MaterialCheckBox
import net.nicbell.materiallists.ListItem

class MyAdapterItems(val app: MyApplication) :

    RecyclerView.Adapter<MyAdapterItems.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val listItem: ListItem
        val checkBox: MaterialCheckBox
        init{
            listItem = itemView.findViewById(R.id.listItemId)
            checkBox = itemView.findViewById(R.id.checkBoxIV)
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val current = app.currentList.items[position]
        holder.listItem.headline.text = current.title
        val text = "Amount: ${current.amount}"
        holder.listItem.supportText.text = text
        holder.checkBox.isChecked = current.checked


        holder.checkBox.addOnCheckedStateChangedListener { _, _ ->
            changeCheckItem(holder, position)
        }

        holder.itemView.setOnClickListener {
            openEditActivity(holder.itemView.context, position)
        }
    }

    private fun openEditActivity(context: Context, position: Int) {
        val intent = Intent(context, AddEditItemActivity::class.java)
        app.currentItem = app.currentList.items[position]
        context.startActivity(intent)
    }

    private fun changeCheckItem(holder: MyViewHolder, position: Int) {
        val current = app.currentList.items[position]
        current.checked = !current.checked

        val serialization = Serialization(holder.itemView.context)
        serialization.updateInfo(app.currentList.uuid,app.currentList)
    }

    /*private fun showDeleteConfirmationDialog(context: Context, position: Int) {
        if(app.listOfgrocerylists.getAllLists()[position].items.size > 1){
            Toast.makeText(context, "You can't delete lists that have items in it", Toast.LENGTH_SHORT).show()
            return
        }
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setTitle("Delete Item")
        alertDialogBuilder.setMessage("Are you sure you want to delete this item?")

        alertDialogBuilder.setPositiveButton("Yes") { _, _ ->
            app.listOfgrocerylists.getAllLists().removeAt(position)
            notifyItemRemoved(position)
        }

        alertDialogBuilder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }

        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }*/

    override fun getItemCount() = app.currentList.getAllItems().size
}