package com.example.loginpage.database

import android.app.Application
import androidx.databinding.adapters.Converters
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.loginpage.model.User

@Database(entities = [
    User::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao() : UserDao


    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Application) : AppDatabase {
            return INSTANCE?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "loginpage_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .createFromAsset("loginpage_database.db")
                    .build()
                INSTANCE = instance

                instance
            }
        }

    }
}