package com.example.biodata

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.biodata.Biodata.Bio

import kotlinx.android.synthetic.main.bio_item.view.*

class BioAdapter : ListAdapter<Bio, BioAdapter.BioHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Bio>() {
            override fun areItemsTheSame(oldItem: Bio, newItem: Bio): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(oldItem: Bio, newItem: Bio): Boolean {
                return oldItem.nama == newItem.nama && oldItem.alamat == newItem.alamat
                        && oldItem.nomer == newItem.nomer && oldItem.priority == newItem.priority
            }
        }
    }
    private var listener: OnItemClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BioHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.bio_item, parent, false)
        return BioHolder(itemView)
    }
    override fun onBindViewHolder(holder: BioHolder, position: Int) {
        val currentNote: Bio = getItem(position)
        holder.textViewTitle.text = currentNote.nama
        holder.textViewPriority.text = currentNote.priority.toString()
        holder.textViewDescription.text = currentNote.alamat
        holder.textViewNomer.text = currentNote.nomer
    }
    fun getBioAt(position: Int): Bio {
        return getItem(position)
    }
    inner class BioHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(getItem(position))
                }
            }
        }
        var textViewTitle: TextView = itemView.text_view_title
        var textViewPriority: TextView = itemView.text_view_priority
        var textViewDescription: TextView = itemView.text_view_description
        var textViewNomer: TextView = itemView.nomerhape
    }
    interface OnItemClickListener {
        fun onItemClick(note: Bio)
    }
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}
