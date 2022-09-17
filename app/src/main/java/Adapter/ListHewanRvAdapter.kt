package Adapter

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
//            itemView.setOnClickListener{
//                cardlistener1.onCardClick(adapterPosition)
//            }
            binding.editButton.setOnClickListener {
                cardlistener1.onCardClick(false,adapterPosition)
            }
            binding.deleteButton.setOnClickListener {
                cardlistener1.onCardClick(true,adapterPosition)
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
    }

    override fun getItemCount(): Int {
        return listHewan.size
    }

}