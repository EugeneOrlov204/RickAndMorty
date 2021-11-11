package com.shpp.eorlov.rickandmorty.ui.details

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.shpp.eorlov.rickandmorty.R
import com.shpp.eorlov.rickandmorty.base.BaseFragment
import com.shpp.eorlov.rickandmorty.databinding.FragmentCharacterBinding
import com.shpp.eorlov.rickandmorty.databinding.FragmentDetailsBinding
import com.shpp.eorlov.rickandmorty.ui.MainActivity
import com.shpp.eorlov.rickandmorty.ui.characters.CharacterViewModel

import javax.inject.Inject

class DetailFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val args: DetailFragmentArgs by navArgs()

    private lateinit var viewModel: CharacterViewModel
    private lateinit var binding: FragmentDetailsBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (activity as MainActivity).contactComponent.inject(this)

        viewModel =
            ViewModelProvider(this, viewModelFactory)[CharacterViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCharacterData()
        setListeners()
        setObservers()
    }

    private fun initCharacterData() {
        binding.character = args.characterModel
        binding.simpleDraweeViewCharacterImage.setImageURI(args.characterModel.image)
    }

    private fun setObservers() {

    }

    private fun setListeners() {

    }

    override fun onResume() {
        super.onResume()
        printLog("On resume")
    }
}