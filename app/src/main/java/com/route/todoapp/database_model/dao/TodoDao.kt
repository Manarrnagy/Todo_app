package com.route.todoapp.database_model.dao

import androidx.room.*
import com.route.todoapp.database_model.Todo

@Dao
interface TodoDao {
    @Insert
    fun addTodo(todo: Todo)

    @Delete
    fun deleteTodo(todo: Todo)

    @Update
    fun updateTodo(todo: Todo)

    @Query ("select * from Todo")
    fun getTodos(): List<Todo>
}