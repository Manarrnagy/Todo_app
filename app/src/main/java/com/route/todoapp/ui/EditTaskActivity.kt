package com.route.todoapp.ui

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.textfield.TextInputLayout
import com.route.todoapp.R
import com.route.todoapp.database_model.MyDatabase
import com.route.todoapp.database_model.Todo
import com.route.todoapp.home.HomeActivity
import java.text.SimpleDateFormat
import java.util.*

class EditTaskActivity : AppCompatActivity() {
    lateinit var dateTv : TextView //selectDateTv
    lateinit var titleTextInput : TextInputLayout
    var selectedDate : Calendar = Calendar.getInstance()
    lateinit var descriptionTextInput : TextInputLayout
    lateinit var updateTodo : Button
    //var selectedDay: Calendar = Calendar.getInstance()
    lateinit var updatedTodo : Todo
    lateinit var updatedTitle :String
    lateinit var updatedDescription :String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_task)
        initView()
        initListeners(this)
    }

    fun initView(){
        dateTv = findViewById(R.id.editDateTv)
        dateTv.setText(convertLongToTime(intent.getLongExtra("date",0)))
        titleTextInput = findViewById(R.id.editTitleInputText)
        titleTextInput.editText?.setText(intent.getStringExtra("title"))
        descriptionTextInput = findViewById(R.id.editDescriptionTextInput)
        descriptionTextInput.editText?.setText(intent.getStringExtra("description"))
        updateTodo =findViewById(R.id.updateTodoBtn)

    }
    fun initListeners(context: Context){
        dateTv.setOnClickListener {
            val dialog = DatePickerDialog( context , object : DatePickerDialog.OnDateSetListener{
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    selectedDate.set(year,month,dayOfMonth)
                    updateDateTextView()
                }
            }, selectedDate.get(Calendar.YEAR), selectedDate.get(Calendar.MONTH), selectedDate.get(Calendar.DAY_OF_MONTH))
            dialog.show()
        }
        updateTodo.setOnClickListener{
            if (titleTextInput.editText?.text.toString().equals("")){
               // updatedTodo.title = intent.getStringExtra("title")!!
                updatedTitle= intent.getStringExtra("title")!!
            }else{
              //  updatedTodo.title = titleTextInput.editText!!.text.toString()
                updatedTitle= titleTextInput.editText!!.text.toString()
            }
            if (descriptionTextInput.editText?.text.toString().equals("")){
               // updatedTodo.description = intent.getStringExtra("description")!!
                updatedDescription = intent.getStringExtra("description")!!
            }else{
                updatedDescription = descriptionTextInput.editText!!.text.toString()
            }

            selectedDate.clear(Calendar.HOUR)
            selectedDate.clear(Calendar.MINUTE)
            selectedDate.clear(Calendar.SECOND)
            selectedDate.clear(Calendar.MILLISECOND)
            updatedTodo=Todo(id=intent.getIntExtra("id",0),title = updatedTitle, description = updatedDescription, date = selectedDate.time.time, isDone =  intent.getBooleanExtra("isDone",false))
            MyDatabase.getInstance(context).getTodoDao().updateTodo(updatedTodo)
            Log.e("ALL TODOS  ", ""+MyDatabase.getInstance(context).getTodoDao().getTodos())
            //finish()
            intent = Intent(context,HomeActivity::class.java)
            startActivity(intent)
        }
    }
    fun updateDateTextView(){
        dateTv.text = "${ selectedDate.get(Calendar.DAY_OF_MONTH)} / ${ selectedDate.get(Calendar.MONTH)+1} / ${ selectedDate.get(Calendar.YEAR)}"
    }

    private fun convertLongToTime(date: Long?): String {
        val date = Date(date!!)
        val format = SimpleDateFormat("dd/MM/yyyy")
        return format.format(date)
    }


}