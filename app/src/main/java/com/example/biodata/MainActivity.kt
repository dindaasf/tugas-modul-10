package com.example.biodata

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.biodata.Biodata.Bio

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        const val ADD_BIO_REQUEST = 1
        const val EDIT_BIO_REQUEST = 2
    }
    private lateinit var bioViewModel: BioViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        floatingActionButton.setOnClickListener {
            startActivityForResult(
                Intent(this, AddEditBioActivity::class.java),
                ADD_BIO_REQUEST
            )
        }
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)
        val adapter = BioAdapter()
        recycler_view.adapter = adapter
        bioViewModel = ViewModelProviders.of(this).get(BioViewModel::class.java)
        bioViewModel.getAllBio().observe(this, Observer<List<Bio>> {
            adapter.submitList(it)
        })
        ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                bioViewModel.delete(adapter.getBioAt(viewHolder.adapterPosition))
                Toast.makeText(baseContext, "Catatan dihapus!", Toast.LENGTH_SHORT).show()
            }
        }
        ).attachToRecyclerView(recycler_view)
        adapter.setOnItemClickListener(object : BioAdapter.OnItemClickListener {
            override fun onItemClick(bio: Bio) {
                val intent = Intent(baseContext, AddEditBioActivity::class.java)
                intent.putExtra(AddEditBioActivity.EXTRA_ID, bio.id)
                intent.putExtra(AddEditBioActivity.EXTRA_NAMA, bio.nama)
                intent.putExtra(AddEditBioActivity.EXTRA_ALAMAT, bio.alamat)
                intent.putExtra(AddEditBioActivity.EXTRA_NOMER, bio.nomer)
                intent.putExtra(AddEditBioActivity.EXTRA_PRIORITAS, bio.priority)
                startActivityForResult(intent, EDIT_BIO_REQUEST)
            }
        })
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.delete_all_bio  -> {
                bioViewModel.deleteAllBio()
                Toast.makeText(this, "Semua sudah dihapus!", Toast.LENGTH_SHORT).show()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_BIO_REQUEST && resultCode == Activity.RESULT_OK) {
            val newBio = Bio(
                data!!.getStringExtra(AddEditBioActivity.EXTRA_NAMA),
                data.getStringExtra(AddEditBioActivity.EXTRA_ALAMAT),
                data.getStringExtra(AddEditBioActivity.EXTRA_NOMER),
                data.getIntExtra(AddEditBioActivity.EXTRA_PRIORITAS, 1)
            )
            bioViewModel.insert(newBio)
            Toast.makeText(this, "Catatan disimpan!", Toast.LENGTH_SHORT).show()
        } else if (requestCode == EDIT_BIO_REQUEST && resultCode == Activity.RESULT_OK) {
            val id = data?.getIntExtra(AddEditBioActivity.EXTRA_ID, -1)
            if (id == -1) {
                Toast.makeText(this, "Pembaharuan gagal!", Toast.LENGTH_SHORT).show()
            }
            val updateBio  = Bio(
                data!!.getStringExtra(AddEditBioActivity.EXTRA_NAMA),
                data.getStringExtra(AddEditBioActivity.EXTRA_ALAMAT),
                data.getStringExtra(AddEditBioActivity.EXTRA_NOMER),
                data.getIntExtra(AddEditBioActivity.EXTRA_PRIORITAS, 1)
            )
            updateBio.id = data.getIntExtra(AddEditBioActivity.EXTRA_ID, -1)
            bioViewModel.update(updateBio)
        } else {
            Toast.makeText(this, "Catatan tidak disimpan!", Toast.LENGTH_SHORT).show()
        }
    }
}