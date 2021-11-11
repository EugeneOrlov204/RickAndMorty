package com.shpp.eorlov.rickandmorty.ui.start

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.shpp.eorlov.rickandmorty.R
import com.shpp.eorlov.rickandmorty.base.BaseFragment
import com.shpp.eorlov.rickandmorty.databinding.FragmentStartBinding
import com.shpp.eorlov.rickandmorty.ui.MainActivity
import javax.inject.Inject

class StartFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: StartViewModel
    private lateinit var binding: FragmentStartBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (activity as MainActivity).contactComponent.inject(this)

        viewModel =
            ViewModelProvider(this, viewModelFactory)[StartViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_start, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    override fun onResume() {
        super.onResume()
        printLog("On resume")
    }

    private fun setListeners() {
        binding.imageViewBackgroundImage.setOnClickListener {
            val action = StartFragmentDirections.actionStartFragmentToCharacterFragment()
            findNavController().navigate(action)
        }
    }
}