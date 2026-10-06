package com.nishiket.note

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.nishiket.note.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        sharedPref = getSharedPreferences("Notes", Context.MODE_PRIVATE)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initNoteList()
        binding.btnAdd.setOnClickListener {
            saveNote()
            initNoteList()
        }
    }


    private fun saveNote(){
        val note = binding.edtNotes.text.toString().trim()
        val notes = sharedPref.getString("NOTE","")
        sharedPref.edit().putString("NOTE",notes + note + "/n").commit()
        binding.edtNotes.setText("")
    }

    private fun initNoteList(){
        val notes = sharedPref.getString("NOTE","")
        val data  = notes?.split("/n")
        binding.rvNotesList.adapter = NoteAdapter(data ?: emptyList())
        binding.rvNotesList.adapter?.notifyDataSetChanged()
    }

}