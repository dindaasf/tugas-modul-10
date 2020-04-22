package com.example.biodata.Biodata

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BioDao {

    @Insert
    fun insert(bio: Bio)

    @Update
    fun update(bio: Bio)

    @Delete
    fun delete(bio: Bio)

    @Query("DELETE FROM bio_table")
    fun deleteAllBio()

    @Query("SELECT * FROM bio_table ORDER BY priority DESC")
    fun getAllBio(): LiveData<List<Bio>>
}