package com.example.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.todoapp.databinding.ActivityAddNoteAvtivityBinding

class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding:ActivityAddNoteAvtivityBinding
    private lateinit var db :NoteDataBaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteAvtivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NoteDataBaseHelper(this)


        binding.saveButton.setOnClickListener{
            val title =binding.titleEditText.text.toString()
            val content = binding.contentEditText.text.toString()
            val note =Note(0,title,content)
            db.insertNote(note)
            finish()

            Toast.makeText(this,"Note Saved Seccessfuly", Toast.LENGTH_SHORT).show()
        }

    }
}