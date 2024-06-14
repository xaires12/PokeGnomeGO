package com.example.pokegnomego

import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokegnomego.com.example.pokegnomego.api.RetrofitInstance
import com.example.pokegnomego.com.example.pokegnomego.api.User
import com.example.pokegnomego.databinding.FragmentRankingBinding
import com.example.pokegnomego.com.example.pokegnomego.api.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RankingFragment : Fragment() {

    private var _binding: FragmentRankingBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRankingBinding.inflate(inflater, container, false)
        loadRankingData()
        return inflater.inflate(R.layout.fragment_ranking, container, false)

    }

    private fun loadRankingData() {
        RetrofitInstance.api.getUsers().enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    val users = response.body() ?: emptyList()
                    updateRankingView(users)
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    private fun updateRankingView(users: List<User>) {
        val sortedUsers = users.sortedByDescending { it.visit_count }
        val rankingRecyclerView = binding.rankingRecyclerView
        rankingRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        rankingRecyclerView.adapter = RankingAdapter(sortedUsers)
    }

}