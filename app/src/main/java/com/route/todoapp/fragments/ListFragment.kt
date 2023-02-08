package com.route.todoapp.fragments

import android.content.Intent
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import com.route.todoapp.R
import com.route.todoapp.SwipeToDeleteCallback
import com.route.todoapp.adapters.TodoAdapter
import com.route.todoapp.database_model.MyDatabase
import com.route.todoapp.database_model.Todo
import com.route.todoapp.database_model.dao.TodoDao
import java.util.*

class ListFragment : Fragment(){
    lateinit var calendarView : MaterialCalendarView
    lateinit var todoRecycler: RecyclerView
    var adapter = TodoAdapter( listOf())
    var selectedDay: Calendar = Calendar.getInstance()
     lateinit var deletedTodo : Todo

//    lateinit var itemTodo: CardView
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
        adapter.onItemDelete = object : TodoAdapter.OnItemDelete {
            override fun RoomItemDelete(todo:Todo) {
                deletedTodo = todo
            }

        }
        val swipeToDeleteCallback = object :SwipeToDeleteCallback(){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
//                todoRecycler.adapter?.notifyItemRemoved(position)
                MyDatabase.database?.getTodoDao()?.deleteTodo(deletedTodo)
                refreshTodos()
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(todoRecycler)
    }
    fun initListeners(){
        calendarView.setOnDateChangedListener(object: OnDateSelectedListener {
            override fun onDateSelected(
                widget: MaterialCalendarView,
                date: CalendarDay,
                selected: Boolean
            ) {
                selectedDay.set(date.year, date.month-1, date.day)
                refreshTodos()
            }
        })
//        itemTodo.setOnClickListener {
//            var intent = Intent(requireContext(),EditTaskFragment::class.java)
//            startActivity(intent)
//        }
    }
    fun refreshTodos(){
        selectedDay.clear(Calendar.HOUR)
        selectedDay.clear(Calendar.MINUTE)
        selectedDay.clear(Calendar.SECOND)
        selectedDay.clear(Calendar.MILLISECOND)
       val Todos = MyDatabase.getInstance(requireContext()).getTodoDao().getTodosByDate(
           selectedDay.time.time
       )
        adapter.changeData(Todos)
    }
}