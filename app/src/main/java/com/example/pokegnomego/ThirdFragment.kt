package com.example.pokegnomego

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils.replace
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.navigation.fragment.findNavController
import com.example.pokegnomego.databinding.FragmentThirdBinding



class ThirdFragment : Fragment() {

    private var _binding: FragmentThirdBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentThirdBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageviewFirst.setOnClickListener {
            val comFragment = CommentsFragment()
            parentFragmentManager.commit {
                replace(R.id.whatever, comFragment)
                addToBackStack(null)
            }
        }
        binding.imageviewSecond.setOnClickListener {
            val comFragment = CommentsGolebnikFragment()
            parentFragmentManager.commit {
                replace(R.id.whatever, comFragment)
                addToBackStack(null)
            }
        }
        binding.imageviewThird.setOnClickListener {
            val comFragment = CommentsSpiochFragment()
            parentFragmentManager.commit {
                replace(R.id.whatever, comFragment)
                addToBackStack(null)
            }
        }
        binding.imageviewFourth.setOnClickListener {
            val comFragment = CommentsBibliofilFragment()
            parentFragmentManager.commit {
                replace(R.id.whatever, comFragment)
                addToBackStack(null)
            }
        }
        binding.imageviewFifth.setOnClickListener {
            val comFragment = CommentsGrunwaldekFragment()
            parentFragmentManager.commit {
                replace(R.id.whatever, comFragment)
                addToBackStack(null)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}