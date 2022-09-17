package com.example.week1

import Adapter.ListHewanRvAdapter
import Database.GlobalVar
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.week1.databinding.ActivityMainBinding
import Database.GlobalVar.Companion.listDataHewan
import Interface.CardListener
import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.week1.databinding.CardviewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), CardListener {

    //private lateinit var adapter1: ListHewanRvAdapter
    private val adapter=ListHewanRvAdapter(listDataHewan,this)
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val binding =

        supportActionBar?.hide()
        setupRecyclerView()
        hidetext()
        listener()
    }

    private fun listener(){
        binding.addHewanFAB.setOnClickListener {
            val myIntent = Intent(this, InputActivity::class.java)
            startActivity(myIntent)
        }

    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }

    private fun hidetext(){
        if(listDataHewan.isEmpty()){
            binding.addTextview.isVisible = true
        } else {
            binding.addTextview.isInvisible = true
        }
    }

    private fun setupRecyclerView(){
        val layoutManager = LinearLayoutManager(baseContext)
        binding.listHewanRV.layoutManager= layoutManager //Set layout
        binding.listHewanRV.adapter=adapter //Set adapter
    }


}