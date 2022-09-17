package Adapter

import Database.GlobalVar
import Interface.CardListener
import Model.Hewan
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.week1.InputActivity
import com.example.week1.MainActivity
import com.example.week1.R
import com.example.week1.databinding.CardviewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListHewanRvAdapter(val listHewan:ArrayList<Hewan>, val cardListener: CardListener):
    RecyclerView.Adapter<ListHewanRvAdapter.viewHolder>() {

    class viewHolder(itemView: View, val cardlistener1: CardListener):RecyclerView.ViewHolder(itemView){
        val binding = CardviewBinding.bind(itemView)

        fun setdata(data:Hewan){
            binding.nameTextview.text=data.nama
            binding.typeCard.text=data.jenis
            binding.ageCard.text=data.usia
            if (data.imageUri.isNotEmpty()){
                binding.imageViewHewan.setImageURI(Uri.parse(data.imageUri))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.cardview, parent, false)
        return viewHolder(view, cardListener)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.setdata(listHewan[position])
        holder.binding.deleteButton.setOnClickListener {
            val alertDialog = AlertDialog.Builder(it.context)
            alertDialog.apply {
                setTitle("Konfirmasi")
                setMessage("Apakah anda yakin untuk mendelete hewan ini?")
                setNegativeButton("No", { dialogInterface, i -> dialogInterface.dismiss() })
                setPositiveButton("Yes", { dialogInterface, i -> dialogInterface.dismiss()
                    GlobalVar.listDataHewan.removeAt(position)
                    notifyDataSetChanged()
                    Toast.makeText(it.context, "Hewan Berhasil Di Hapus", Toast.LENGTH_SHORT).show()
                })
                alertDialog.show()
            }
        }
        holder.binding.editButton.setOnClickListener {
            val intent = Intent(it.context, InputActivity::class.java).putExtra("position",position)
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return listHewan.size
    }

}