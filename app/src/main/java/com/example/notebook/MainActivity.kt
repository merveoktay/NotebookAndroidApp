package com.example.notebook

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.notebook.adapter.CustomAdapter
import com.example.notebook.database.DB
import com.example.notebook.models.Note
import java.util.Calendar


class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var note: Note
    }

    lateinit var editTextTitle : EditText
    lateinit var editTextDetail :EditText
    lateinit var buttonCalender :Button
    lateinit var buttonSave:Button
    lateinit var listViewNote:ListView
    lateinit var selectDate:String

    val db= DB(this)
    lateinit var customAdapter:ArrayAdapter<Note>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextTitle=findViewById(R.id.editTextTitle)
        editTextDetail=findViewById(R.id.editTextDetail)

        buttonCalender=findViewById(R.id.buttonCalender)
        buttonCalender.setOnClickListener(buttonCalenderOnClickListener)

        buttonSave=findViewById(R.id.buttonSave)
        buttonSave.setOnClickListener(buttonSaveOnClickListener)

        listViewNote=findViewById(R.id.listViewNote)
        listViewNote.setOnItemClickListener(listViewNoteOnClickListener)

    }

    override fun onStart() {
        super.onStart()
        customAdapter= CustomAdapter(this,db.allNote())
        listViewNote.adapter = customAdapter
    }

   val buttonCalenderOnClickListener= View.OnClickListener{
       val calender=Calendar.getInstance()
       val datePickerDialog = DatePickerDialog(
           this,
           DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->

               if(i2+1<10){
                   selectDate = "$i3.0${i2 + 1}.$i"
               }else {
                   selectDate = "$i3.${i2 + 1}.$i"
               }
           },
           calender.get(Calendar.YEAR),
           calender.get(Calendar.MONTH),
           calender.get(Calendar.DAY_OF_MONTH),
       )
       datePickerDialog.show()
    }

    val buttonSaveOnClickListener=View.OnClickListener{


        if ( selectDate != "" ) {
            customAdapter.notifyDataSetChanged()
            var status = db.addNote(editTextTitle.text.toString(), editTextDetail.text.toString(), selectDate)

            selectDate = ""
        }else {
            Toast.makeText(this, "Plase Select Date!", Toast.LENGTH_LONG).show()
        }
        customAdapter= CustomAdapter(this,db.allNote())
        listViewNote.adapter = customAdapter
        editTextDetail.setText("")
        editTextTitle.setText("")



        closeKeyboard()
    }

    val listViewNoteOnClickListener =AdapterView.OnItemClickListener { parent, view, position, id ->
        note=db.allNote().get(position)
        val intent = Intent(this, DetailActivity::class.java)
        startActivity(intent)
    }

    private fun closeKeyboard() {

        val view = this.currentFocus

        if (view != null) {
            val manager = getSystemService(
                INPUT_METHOD_SERVICE
            ) as InputMethodManager
            manager
                .hideSoftInputFromWindow(
                    view.windowToken, 0
                )
        }
    }

}