package com.example.pokegnomego

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.pokegnomego.databinding.FragmentCommentsBinding



class CommentsFragment : Fragment() {

    private var _binding: FragmentCommentsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCommentsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.CommentButton.setOnClickListener {
            val commentText = binding.commentEditText.text.toString()
            if (commentText.isNotBlank()) {
                addComment(commentText)
                binding.commentEditText.text.clear()
            }
        }
    }

    private fun addComment(comment: String) {
        val commentTextView = TextView(requireContext()).apply {
            text = comment
            textSize = 16f
            setPadding(0, 8, 0, 8)
        }
        binding.commentsContainer.addView(commentTextView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}