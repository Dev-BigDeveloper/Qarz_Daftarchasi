package com.example.qarzdaftarchasi.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.qarzdaftarchasi.databaseRoom.entities.Contact
import com.example.qarzdaftarchasi.databinding.UserItemBinding
import com.squareup.picasso.Picasso

class UsersAdapter(var list: List<Contact>) : RecyclerView.Adapter<UsersAdapter.Vh>() {
    inner class Vh(private val binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(contact: Contact,context:Context) {
            Glide.with(context).load(contact.image).into(binding.imageView);
            binding.name.text = contact.name
            //Picasso.get().load(contact.image).into(binding.imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position],holder.itemView.context)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}