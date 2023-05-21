package com.example.notebook.adapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.notebook.R
import com.example.notebook.models.Note

class CustomAdapter(private val context: Activity, private val list: List<Note>) : ArrayAdapter<Note>(context,    R.layout.custom_listview_item, list ) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rootView = context.layoutInflater.inflate(R.layout.custom_listview_item, null, true)
        val textTitle = rootView.findViewById<TextView>(R.id.textTitle)
        val textDate = rootView.findViewById<TextView>(R.id.textDate)
        val  note = list.get(position)

        textTitle.text=note.title
        textDate.text=note.date

        return  rootView
    }
}