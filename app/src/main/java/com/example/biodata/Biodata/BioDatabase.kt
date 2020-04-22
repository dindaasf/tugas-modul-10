package com.example.biodata.Biodata

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Bio::class], version = 1)
abstract class BioDatabase : RoomDatabase() {

    abstract fun bioDao(): BioDao

    companion object {
        private var instance: BioDatabase? = null

        fun getInstance(context: Context): BioDatabase? {
            if (instance == null) {
                synchronized(BioDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        BioDatabase::class.java, "bio_database"
                    )
                        .fallbackToDestructiveMigration()
                        .addCallback(roomCallback)
                        .build()
                }
            }
            return instance
        }

        fun destroyInstance() {
            instance = null
        }
        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDbAsyncTask(instance)
                    .execute()
            }
        }
    }
    class PopulateDbAsyncTask(db: BioDatabase?) : AsyncTask<Unit, Unit, Unit>() {
        private val bioDao = db?.bioDao()
        override fun doInBackground(vararg p0: Unit?) {
            bioDao?.insert(Bio("who", "where", "nomer", 1))
        }
    }
}