package com.route.todoapp.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.os.PersistableBundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.google.android.material.textfield.TextInputLayout
import com.route.todoapp.R
import com.route.todoapp.database_model.MyDatabase
import com.route.todoapp.database_model.Todo
import java.util.*

class EditTaskFragment : AppCompatActivity() {


    lateinit var dateTv : TextView //selectDateTv
    lateinit var titleTextInput : TextInputLayout
    var selectedDate : Calendar = Calendar.getInstance()
    lateinit var descriptionTextInput : TextInputLayout
    lateinit var updateTodo : Button
    var onSaveClicked: AddBottomSheetFragment.OnAddClicked? = null
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_edit_task, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        initViews(view)
//        initListeners()
//
//    }
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        initViews()
    initListeners()
    }
    fun updateDateTextView(){
        dateTv.text = "${ selectedDate.get(Calendar.DAY_OF_MONTH)} / ${ selectedDate.get(Calendar.MONTH)+1} / ${ selectedDate.get(Calendar.YEAR)}"
    }
    fun initViews(){
        dateTv = findViewById(R.id.editDateTv)
        titleTextInput = findViewById(R.id.editTitleInputText)
        descriptionTextInput = findViewById(R.id.editDescriptionTextInput)
        updateTodo =findViewById(R.id.updateTodoBtn)
       // updateDateTextView()
       var savedTodo= Todo(intent.getIntExtra("id",5),intent.getStringExtra("title").toString(),intent.getStringExtra("description").toString(),intent.getStringExtra("date")!!.toLong(),intent.getBooleanExtra("isDone",false))
        dateTv.text = savedTodo.date.toString()



    }
    fun initListeners(){
        dateTv.setOnClickListener {
            val dialog = DatePickerDialog( this, object : DatePickerDialog.OnDateSetListener{
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    selectedDate.set(year,month,dayOfMonth)
                    updateDateTextView()
                }
            }, selectedDate.get(Calendar.YEAR), selectedDate.get(Calendar.MONTH), selectedDate.get(Calendar.DAY_OF_MONTH))
            dialog.show()
        }
        updateTodo.setOnClickListener {
            if(!dataChanged()) FragmentManager.POP_BACK_STACK_INCLUSIVE
            selectedDate.clear(Calendar.HOUR)
            selectedDate.clear(Calendar.MINUTE)
            selectedDate.clear(Calendar.SECOND)
            selectedDate.clear(Calendar.MILLISECOND)

            val todo = Todo(title = titleTextInput.editText!!.text.toString(),
                description = descriptionTextInput.editText!!.text.toString(),
                isDone = false, date = selectedDate.time.time)
            MyDatabase.getInstance(this).getTodoDao().updateTodo(todo)
            onSaveClicked?.onClick()
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        }

    }

    interface OnSaveClicked{
        fun onClick()
    }
    fun dataChanged():Boolean{
        var isChanged = true
        if (titleTextInput.editText!!.text.isEmpty()){
            titleTextInput.error="Please write todo title"
            isChanged= false
        }else{
            titleTextInput.error=null
        }
        if(descriptionTextInput.editText!!.text.isEmpty()){
            descriptionTextInput.error="Please write todo description"
            isChanged= false
        }else{
            descriptionTextInput.error=null
        }
        return isChanged
    }

}