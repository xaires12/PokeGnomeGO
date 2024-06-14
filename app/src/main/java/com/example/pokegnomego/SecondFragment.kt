package com.example.pokegnomego

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import com.example.pokegnomego.R
import com.example.pokegnomego.databinding.FragmentSecondBinding


class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        val buttonShake: Button = binding.buttonSecond

        // Set an OnClickListener on the button
        buttonShake.setOnClickListener {

        }
        return binding.root

    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}