package com.shpp.eorlov.rickandmorty.ui.characters

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.shpp.eorlov.rickandmorty.base.BaseFragment
import com.shpp.eorlov.rickandmorty.databinding.FragmentCharacterBinding
import com.shpp.eorlov.rickandmorty.ui.MainActivity
import javax.inject.Inject

import android.util.TypedValue
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.shpp.eorlov.rickandmorty.R
import com.shpp.eorlov.rickandmorty.ui.characters.adapter.CharactersGridAdapter
import com.shpp.eorlov.rickandmorty.utils.Results


class CharacterFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val charactersGridAdapter: CharactersGridAdapter by lazy(LazyThreadSafetyMode.NONE) {
        CharactersGridAdapter()
    }

    private lateinit var viewModel: CharacterViewModel
    private lateinit var binding: FragmentCharacterBinding

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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_character, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        setObservers()
        initRecycler()
    }

    override fun onResume() {
        super.onResume()
        printLog("On resume")
    }

    private fun initRecycler() {
        binding.recyclerViewMyContacts.apply {
            layoutManager = GridLayoutManager(
                requireContext(),
                2
            )

            adapter = charactersGridAdapter
        }
    }

    private fun setObservers() {
        viewModel.charactersFromCurrentPageLiveData.observe(viewLifecycleOwner) {
            if(it.info?.next != null) {
                viewModel.getAllCharacters()
            }
        }

        viewModel.charactersListLiveData.observe(viewLifecycleOwner) {
            charactersGridAdapter.submitList(it.toMutableList())
        }

        viewModel.loadEventLiveData.observe(viewLifecycleOwner) { event ->
            when (event) {
                Results.OK -> {
                    unlockUI()
                    binding.contentLoadingProgressBar.isVisible = false
                }
                Results.LOADING -> {
                    lockUI()
                    binding.contentLoadingProgressBar.isVisible = true
                }
                Results.INITIALIZE_DATA_ERROR -> {
                    unlockUI()
                    binding.contentLoadingProgressBar.isVisible = false
                    Toast.makeText(
                        requireContext(),
                        R.string.initialize_data_error,
                        Toast.LENGTH_LONG
                    ).show()
                }
                Results.INTERNET_ERROR -> {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.internet_error),
                        Toast.LENGTH_LONG
                    ).show()
                    unlockUI()
                    binding.contentLoadingProgressBar.isVisible = false
                }
                Results.UNEXPECTED_RESPONSE -> {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.unexpected_response),
                        Toast.LENGTH_LONG
                    ).show()
                    unlockUI()
                    binding.contentLoadingProgressBar.isVisible = false
                }
                Results.NOT_SUCCESSFUL_RESPONSE -> {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.not_successful_response),
                        Toast.LENGTH_LONG
                    ).show()
                    unlockUI()
                    binding.contentLoadingProgressBar.isVisible = false
                }
                else -> {
                }
            }
        }
    }

    private fun setListeners() {

    }

    private fun convertPxToDp(dip: Float): Float {
        val r: Resources = resources
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dip,
            r.displayMetrics
        )
    }
}