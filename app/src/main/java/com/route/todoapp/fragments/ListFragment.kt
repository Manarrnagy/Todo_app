package com.route.todoapp.fragments

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.provider.CalendarContract.Colors
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
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
import kotlin.math.log

class ListFragment : Fragment(){
    lateinit var calendarView : MaterialCalendarView
    lateinit var todoRecycler: RecyclerView
    var adapter = TodoAdapter( listOf())
    var selectedDay: Calendar = Calendar.getInstance()
     lateinit var deletedTodo : Todo
     lateinit var selectedTodo : Todo



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
        adapter.onItemClick = object :TodoAdapter.OnItemClick{
            override fun onTaskClick(
                todo: Todo
            ) {
                selectedTodo = todo
                Log.e("TODO INFO ##### ",""+ selectedTodo.id)

               // var bundle =  Bundle()
               // bundle.putInt("id",selectedTodo.id)
//                bundle.putString("title",selectedTodo.title)
//                bundle.putString("description",selectedTodo.description)
//                bundle.putLong("date",selectedTodo.date)
//                bundle.putBoolean("isDone",selectedTodo.isDone)
               // val bundle2 = bundleOf("id" to selectedTodo.id,)

               // val fragment2 = EditTaskFragment()
//                fragment2.arguments = bundle
//                getFragmentManager()?.beginTransaction()
//                    ?.replace(R.id.fragmentContainer, EditTaskFragment() )
//                    ?.commit();



            }

        }
        adapter.onItemDelete = object : TodoAdapter.OnItemDelete {
            override fun RoomItemDelete(todo:Todo) {
                deletedTodo = todo
                refreshTodos()
            }

        }
        adapter.onItemDone = object : TodoAdapter.OnItemDone{
            override fun updateItemDone(todo: Todo, isCheck:View, verticalLine: View, taskTitle: TextView,doneTv: TextView) {
                verticalLine.setBackgroundColor(getResources().getColor(R.color.green))
                taskTitle.setTextColor(getResources().getColor(R.color.green))
                doneTv.setTextColor(getResources().getColor(R.color.green))
                isCheck.setBackgroundColor(getResources().getColor(R.color.transparent))
                todo.isDone =true
            //    MyDatabase.database?.getTodoDao()?.updateTodo(todo)

                Log.e("ON ITEM DONE", "")
                refreshTodos()
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
               // Log.e("ALL TODOS",""+date.day +" "+date.month + date.year)
                refreshTodos()
            }
        })
    }
    fun refreshTodos(){
        selectedDay.clear(Calendar.HOUR)
        selectedDay.clear(Calendar.MINUTE)
        selectedDay.clear(Calendar.SECOND)
        selectedDay.clear(Calendar.MILLISECOND)
       val Todos = MyDatabase.getInstance(requireContext()).getTodoDao().getTodosByDate(
           selectedDay.time.time
       )
        val AllTodos = MyDatabase.getInstance(requireContext()).getTodoDao().getTodos()
       // Log.e("ALL TODOS", ""+AllTodos)
        todoRecycler.post(Runnable {

            //adapter.notifyDataSetChanged()

            adapter.changeData(Todos)
        })

    }
}