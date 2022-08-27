package com.iceapk.data.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.iceapk.data.dao.converters.Converters
import com.iceapk.data.dao.entities.Cart
import com.iceapk.data.dao.entities.Product

@Database(entities = [Product::class, Cart::class], version =3, exportSchema = false)
@TypeConverters(Converters::class)
abstract class Database: RoomDatabase() {

    abstract fun productsDao(): ProductsDao
    abstract  fun cartsDao(): CartsDao

    companion object{
        @Volatile
        private var INSTANCE: com.iceapk.data.dao.Database? = null
        @Synchronized
        fun getDatabase(context: Context):  com.iceapk.data.dao.Database{
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context,
                    com.iceapk.data.dao.Database::class.java,
                    "ICE")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE!!
        }
    }
}