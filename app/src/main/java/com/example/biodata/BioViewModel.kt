package com.example.biodata

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.biodata.Biodata.Bio
import com.example.biodata.Biodata.BioRepository


class BioViewModel(application: Application) : AndroidViewModel(application)
{
    private var repository: BioRepository =
        BioRepository(application)
    private var allBio: LiveData<List<Bio>> = repository.getAllBio()
    fun insert(bio: Bio) {
        repository.insert(bio)
    }
    fun update(bio: Bio) {
        repository.update(bio)
    }
    fun delete(bio: Bio) {
        repository.delete(bio)
    }
    fun deleteAllBio() {
        repository.deleteAllBio()
    }
    fun getAllBio(): LiveData<List<Bio>> {
        return allBio
    }
}
