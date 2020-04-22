package com.example.biodata.Biodata

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.room.Dao

class BioRepository(application: Application) {
    private var bioDao: BioDao
    private var allNotes: LiveData<List<Bio>>

    init {
        val database: BioDatabase = BioDatabase.getInstance(
            application.applicationContext
        )!!
        bioDao = database.bioDao()
        allNotes = bioDao.getAllBio()
    }

    fun insert(bio: Bio) {
        val insertNoteAsyncTask = InsertNoteAsyncTask(bioDao).execute(bio)
    }

    fun update(bio: Bio) {
        val updateNoteAsyncTask = UpdateNoteAsyncTask(bioDao).execute(bio)
    }

    fun delete(bio: Bio) {
        val deleteNoteAsyncTask = DeleteNoteAsyncTask(bioDao).execute(bio)
    }

    fun deleteAllBio() {
        val deleteAllNotesAsyncTask = DeleteAllNotesAsyncTask(
            bioDao
        ).execute()
    }

    fun getAllBio(): LiveData<List<Bio>> {
        return allNotes
    }

    companion object {
        private class InsertNoteAsyncTask(bioDao: BioDao) : AsyncTask<Bio, Unit, Unit>() {
            val bioDao = bioDao
            override fun doInBackground(vararg p0: Bio?) {
                bioDao.insert(p0[0]!!)
            }
        }

        private class UpdateNoteAsyncTask(bioDao: BioDao) : AsyncTask<Bio, Unit, Unit>() {
            val bioDao = bioDao
            override fun doInBackground(vararg p0: Bio?) {
                bioDao.update(p0[0]!!)
            }
        }

        private class DeleteNoteAsyncTask(bioDao: BioDao) : AsyncTask<Bio, Unit, Unit>() {
            val bioDao = bioDao
            override fun doInBackground(vararg p0: Bio?) {
                bioDao.delete(p0[0]!!)
            }
        }

        private class DeleteAllNotesAsyncTask(bioDao: BioDao) : AsyncTask<Unit, Unit, Unit>() {
            val bioDao = bioDao
            override fun doInBackground(vararg p0: Unit?) {
                bioDao.deleteAllBio()
            }
        }
    }
}