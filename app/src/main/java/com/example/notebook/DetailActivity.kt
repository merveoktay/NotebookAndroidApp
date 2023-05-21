package com.example.notebook

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.notebook.MainActivity.Companion.note
import com.example.notebook.database.DB
import com.example.notebook.models.Note

class DetailActivity : AppCompatActivity() {


    lateinit var textDateDetail:EditText
    lateinit var buttonDelete:Button
    lateinit var buttonUpdate:Button
    lateinit var editTextUpdateTitle:EditText
    lateinit var editTextUpdateDetail:EditText

    val db= DB(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val note: Note =note

        editTextUpdateTitle=findViewById(R.id.editTextUpdateTitle)
        editTextUpdateDetail=findViewById(R.id.editTextUpdateDetail)
        textDateDetail=findViewById(R.id.textDateDetail)
        buttonDelete=findViewById(R.id.buttonDelete)
        buttonUpdate=findViewById(R.id.buttonUpdate)

        editTextUpdateTitle.setText(note.title)
        editTextUpdateDetail.setText(note.detail)
        textDateDetail.setText(note.date)
        buttonDelete.setOnClickListener(buttonDeleteOnClickListener)
        buttonUpdate.setOnClickListener(buttonUpdateOnClickListener)
    }

    val buttonDeleteOnClickListener=View.OnClickListener {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("DELETE NOTE ")
        builder.setMessage("Are You Sure, You Want to Delete ?")
        builder.setIcon(android.R.drawable.ic_dialog_alert)


        builder.setPositiveButton("Yes"){dialogInterface, which ->
            Toast.makeText(applicationContext,"Clicked Yes",Toast.LENGTH_LONG).show()
            val status= db.deleteNote(note.nid)
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
        }



        builder.setNegativeButton("No"){dialogInterface, which ->
            Toast.makeText(applicationContext,"Clicked No \n Operation Cancel",Toast.LENGTH_LONG).show()
        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()

    }
    val buttonUpdateOnClickListener=View.OnClickListener {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("UPDATE NOTE ")
        builder.setMessage("Are You Sure, You Want to Update ?")
        builder.setIcon(android.R.drawable.ic_dialog_alert)


        builder.setPositiveButton("Yes"){dialogInterface, which ->
            Toast.makeText(applicationContext,"Clicked Yes",Toast.LENGTH_LONG).show()
            val status= db.updateNote(editTextUpdateTitle.text.toString(),editTextUpdateDetail.text.toString(),textDateDetail.text.toString(),note.nid)
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        builder.setNegativeButton("No"){dialogInterface, which ->
            Toast.makeText(applicationContext,"Clicked No \n Operation Cancel",Toast.LENGTH_LONG).show()
        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
}