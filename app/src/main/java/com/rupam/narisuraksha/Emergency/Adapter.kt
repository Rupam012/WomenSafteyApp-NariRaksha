package com.rupam.narisuraksha.Emergency

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rupam.narisuraksha.databinding.AddnumberBinding

class Adapter(private val list: List<emergengydata>) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    inner class ViewHolder(val binding: AddnumberBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AddnumberBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.binding.contactname.text = item.text

        holder.binding.imageView11.setOnClickListener {
            val phone = item.number ?: return@setOnClickListener
            val message = "🚨 Emergency! Please help me!"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://wa.me/$phone?text=${Uri.encode(message)}")
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}
