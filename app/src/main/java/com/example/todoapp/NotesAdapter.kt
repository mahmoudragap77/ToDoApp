package com.example.todoapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter(private var notes :List<Note> ,context: Context):RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private  val db :NoteDataBaseHelper = NoteDataBaseHelper(context)
    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView :TextView =itemView.findViewById(R.id.titleTextView)
        val contentTExtView:TextView =itemView.findViewById(R.id.contentTextView)
        val updateButotn:ImageView =itemView.findViewById(R.id.updateButton)
        val deleteButotn:ImageView =itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view =LayoutInflater.from(parent.context).inflate(R.layout.note_item,parent,false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int= notes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.apply {
            titleTextView.text=note.title
            contentTExtView.text =note.content
            updateButotn.setOnClickListener{
                val intent =Intent(itemView.context,UpdateNoteActivity::class.java).apply {
                    putExtra("note_id",note.id)
                }
                    itemView.context.startActivity(intent)
            }

            deleteButotn.setOnClickListener{
                db.deleteNote(note.id)
                refreshData(db.getAllNotes())
                Toast.makeText(itemView.context,"Note Deleted",Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun refreshData (newNotes : List<Note>){
        notes =newNotes
        notifyDataSetChanged()
    }
}