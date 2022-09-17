package com.example.week1

import Database.GlobalVar
import Model.Hewan
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.week1.databinding.ActivityInputBinding

class InputActivity : AppCompatActivity() {

    private lateinit var hewan:Hewan
    private lateinit var binding:ActivityInputBinding
    private var position = -1
    var tempUri:String =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        GetIntent()
        listener()
    }

    private fun listener(){
        binding.addHewanButton.setOnClickListener {
            val nama = binding.nameInputlayout.editText?.text.toString().trim()
            val jenis = binding.jenisInputlayout.editText?.text.toString().trim()
            val usia = binding.usiaInputlayout.editText?.text.toString().trim()

            hewan =Hewan(nama, jenis, usia)
            checker()
        }

        binding.backtohomeImageButton.setOnClickListener {
            val myIntent = Intent(this, MainActivity::class.java)
            startActivity(myIntent)
        }

        binding.posterinputImageView.setOnClickListener{
            val myIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            myIntent.type="image/*"
            GetResult.launch(myIntent)
        }

    }

    private val GetResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == Activity.RESULT_OK){
            val uri = it.data?.data
            binding.posterinputImageView.setImageURI(uri)
            if(uri!=null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    baseContext.getContentResolver().takePersistableUriPermission(
                        uri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                    )
                }
                tempUri = uri.toString()
            }
        }
    }

    private fun GetIntent(){
        position = intent.getIntExtra("position",-1)
        if(position!=-1){
            binding.addhewanTextView.setText("Edit Hewan")
            binding.addHewanButton.setText("Save")
            val tempMov = GlobalVar.listDataHewan[position]
            Display(tempMov)
        }
    }

    private fun Display(movie:Hewan?){
        binding.nameInputlayout.editText?.setText((movie?.nama))
        binding.jenisInputlayout.editText?.setText((movie?.jenis))
        binding.usiaInputlayout.editText?.setText((movie?.usia))
        binding.posterinputImageView.setImageURI(Uri.parse(movie?.imageUri))

    }

    private fun checker(){
        var isCompleted:Boolean = true
        if(hewan.nama!!.isEmpty()){
            binding.nameInputlayout.error = "Title harus diisi"
            isCompleted = false
        }else{
            binding.nameInputlayout.error = ""
        }

        if(hewan.usia!!.isEmpty()){
            binding.usiaInputlayout.error = "Rating harus diisi 1-10"
            isCompleted=false
        }else if(hewan.usia!!.contains(".*[A-Z].*".toRegex())){
            binding.usiaInputlayout.error = "Rating tidak boleh ada huruf"
            isCompleted=false
        }else if(hewan.usia!!.contains(".*[a-z].*".toRegex())){
            binding.usiaInputlayout.error = "Rating tidak boleh ada huruf"
            isCompleted=false
        }

        if(hewan.jenis!!.isEmpty()){
            binding.jenisInputlayout.error = "Genre harus diisi"
            isCompleted=false
        }else{
            binding.jenisInputlayout.error = ""
        }

        if(isCompleted){
            if(position==-1){
                hewan.imageUri= tempUri
                GlobalVar.listDataHewan.add(hewan)
                Toast.makeText(this, "Hewan Telah Berhasil Ditambahkan", Toast.LENGTH_SHORT).show()
                val myIntent = Intent(this,MainActivity::class.java)
                startActivity(myIntent)
            }
            else{
                if(tempUri== GlobalVar.listDataHewan[position].imageUri){
                    hewan.imageUri = GlobalVar.listDataHewan[position].imageUri
                }else if(tempUri==""){
                    hewan.imageUri = GlobalVar.listDataHewan[position].imageUri
                }else{
                    hewan.imageUri = tempUri
                }
                GlobalVar.listDataHewan[position]=hewan
                Toast.makeText(this, "Edit Success", Toast.LENGTH_SHORT).show()
                val myIntent = Intent(this,MainActivity::class.java)
                startActivity(myIntent)
            }
            finish()
        }
    }
}