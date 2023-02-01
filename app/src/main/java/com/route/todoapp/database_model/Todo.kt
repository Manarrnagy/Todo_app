package com.route.todoapp.database_model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todo (
    @PrimaryKey(autoGenerate = true)
    val id : Int=1,
    @ColumnInfo
    val title : String,
    @ColumnInfo
    val description : String,
    @ColumnInfo
    val date : Long,
    @ColumnInfo
    val isDone: Boolean
    )