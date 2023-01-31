package com.route.todoapp.fragments


import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputLayout
import com.route.todoapp.R
import java.util.*


class AddBottomSheetFragment : BottomSheetDialogFragment() {

    lateinit var dateTv : TextView
    var selectedDate : Calendar= Calendar.getInstance()
    lateinit var titleTextInput : TextInputLayout
    lateinit var descriptionTextInput : TextInputLayout
    lateinit var addTodo : Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_bottom_sheet, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        initListeners()
    }
    fun updateDateTextView(){
        dateTv.text = "${ selectedDate.get(Calendar.DAY_OF_MONTH)} / ${ selectedDate.get(Calendar.MONTH)+1} / ${ selectedDate.get(Calendar.YEAR)}"
    }
    fun initViews(view: View){
        dateTv = view.findViewById(R.id.dateTv)
        titleTextInput = view.findViewById(R.id.titleInputText)
        descriptionTextInput = view.findViewById(R.id.descriptionTextInput)
        addTodo = view.findViewById(R.id.addTodoBtn)
        updateDateTextView()
    }
    fun initListeners(){
        dateTv.setOnClickListener {
            val dialog =DatePickerDialog( requireContext(), object :DatePickerDialog.OnDateSetListener{
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    selectedDate.set(year,month,dayOfMonth)
                    updateDateTextView()
                }
            }, selectedDate.get(Calendar.YEAR), selectedDate.get(Calendar.MONTH), selectedDate.get(Calendar.DAY_OF_MONTH))
            dialog.show()
        }
        addTodo.setOnClickListener {
            if(!validate()) return@setOnClickListener
        }

    }
    fun validate():Boolean{
        var isValid = true
        if (titleTextInput.editText!!.text.isEmpty()){
            titleTextInput.error="Please write todo title"
            isValid= false
        }else{
            titleTextInput.error=null
        }
        if(descriptionTextInput.editText!!.text.isEmpty()){
            descriptionTextInput.error="Please write todo description"
            isValid= false
        }else{
            descriptionTextInput.error=null
        }
        return isValid
    }

}