package com.example.pokegnomego

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokegnomego.com.example.pokegnomego.api.User
import com.example.pokegnomego.com.example.pokegnomego.api.UserService
import com.example.pokegnomego.databinding.ItemUserBinding


class RankingAdapter(private val users: List<User>) : RecyclerView.Adapter<RankingAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        holder.bind(user)
    }

    override fun getItemCount() = users.size

    class UserViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.loginRankingTextView.text = user.login
            binding.pointsTextView.text = user.visit_count.toString()
        }
    }
}
