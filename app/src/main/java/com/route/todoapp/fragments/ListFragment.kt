package com.route.todoapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.route.todoapp.R
import com.route.todoapp.adapters.TodoAdapter
import com.route.todoapp.database_model.MyDatabase

class ListFragment : Fragment(){
    lateinit var calendarView : MaterialCalendarView
    lateinit var todoRecycler: RecyclerView
    var adapter = TodoAdapter( listOf())
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        initListeners()
        refreshTodos()
    }
    fun initViews(view: View){
        calendarView = view.findViewById(R.id.calenderView)
        calendarView.selectedDate = CalendarDay.today()
        todoRecycler = view.findViewById(R.id.listRecyclerView)
        todoRecycler.adapter =adapter
    }
    fun initListeners(){

    }
    fun refreshTodos(){
       val Todos = MyDatabase.getInstance(requireContext()).getTodoDao().getTodos()
        adapter.changeData(Todos)
    }
}