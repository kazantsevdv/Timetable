package com.example.timetable.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.timetable.App
import com.example.timetable.R
import com.example.timetable.databinding.FragmentHomeBinding
import com.example.timetable.repo.image.IImageLoader
import com.example.timetable.ui.classes.ClassesViewModel
import javax.inject.Inject
import javax.inject.Provider

class HomeFragment : Fragment() {

    @Inject
    lateinit var viewModeProvider: Provider<HomeViewModel.Factory>

    @Inject
    lateinit var imageLoader: IImageLoader<ImageView>

    private val viewModel: HomeViewModel by viewModels { viewModeProvider.get() }

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val navigation by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        App.component.inject(this)
//        homeViewModel =
//            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        //val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner, Observer {
//            //textView.text = it
//        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}