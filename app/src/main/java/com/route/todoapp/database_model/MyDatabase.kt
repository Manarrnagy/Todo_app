package com.route.todoapp.database_model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.route.todoapp.database_model.dao.TodoDao


@Database(entities = [Todo::class], version = 1)
abstract class MyDatabase: RoomDatabase() {
 abstract fun getTodoDao(): TodoDao
 companion object{
     var database: MyDatabase? = null
     fun getInstance(context : Context): MyDatabase{
         if (database == null) {
              database =
                 Room.databaseBuilder(context, MyDatabase::class.java, "MyDatabase").allowMainThreadQueries().fallbackToDestructiveMigration().build()
         }
         return database!!
     }
 }
}