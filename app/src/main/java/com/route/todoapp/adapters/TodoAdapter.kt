package com.route.todoapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.route.todoapp.R
import com.route.todoapp.database_model.Todo

class TodoAdapter(var items : List<Todo>) : RecyclerView.Adapter<TodoAdapter.ViewHolder>(){

    class ViewHolder (val itemView: View): androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView){
        val card = itemView.findViewById<CardView>(R.id.itemTodoCard)
        val taskTitle = itemView.findViewById<TextView>(R.id.taskTitle)
        val taskDescription = itemView.findViewById<TextView>(R.id.taskDescription)
        val icCheck = itemView.findViewById<ImageView>(R.id.icCheck)
        val verticalLine = itemView.findViewById<View>(R.id.verticalLine)
        val doneTv = itemView.findViewById<TextView>(R.id.doneTv)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_todo,parent,false)
        return  ViewHolder(view)
    }

    fun  changeData(newList: List<Todo>){
        items = newList
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       var todo = items.get(position)
        holder.taskTitle.text = todo.title
        holder.taskDescription.text = todo.description
        holder.card.setOnClickListener {
            onItemClick?.onTaskClick(items.get(position))
        }
        onItemDelete?.RoomItemDelete(items.get(position))
        holder.icCheck.setOnClickListener{
            onItemDone?.updateItemDone(items.get(position),holder.icCheck,holder.verticalLine,holder.taskTitle, holder.doneTv)
        }

    }
    var onItemClick : OnItemClick?= null
    interface OnItemClick{
        fun onTaskClick(todo:Todo)
    }

    var onItemDelete : OnItemDelete? = null
    interface OnItemDelete {
        fun RoomItemDelete(todo: Todo)
    }

    var onItemDone : OnItemDone? = null
    interface  OnItemDone{
        fun updateItemDone(todo: Todo, isCheck:View, verticalLine: View, taskTitle:TextView , doneTv: TextView)

    }
}


