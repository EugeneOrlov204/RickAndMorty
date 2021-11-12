package com.shpp.eorlov.rickandmorty.ui.characters

import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.shpp.eorlov.rickandmorty.R
import com.shpp.eorlov.rickandmorty.base.BaseFragment
import com.shpp.eorlov.rickandmorty.databinding.FragmentCharacterBinding
import com.shpp.eorlov.rickandmorty.model.CharacterModel
import com.shpp.eorlov.rickandmorty.ui.MainActivity
import com.shpp.eorlov.rickandmorty.ui.characters.adapter.CharactersGridAdapter
import com.shpp.eorlov.rickandmorty.ui.characters.adapter.listeners.CharacterClickListener
import com.shpp.eorlov.rickandmorty.ui.details.DetailFragmentArgs
import com.shpp.eorlov.rickandmorty.utils.Constants
import javax.inject.Inject


class CharacterFragment : BaseFragment(), CharacterClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val charactersGridAdapter: CharactersGridAdapter by lazy(LazyThreadSafetyMode.NONE) {
        CharactersGridAdapter(characterClickListener = this)
    }
    private val args: CharacterFragmentArgs by navArgs()

    private lateinit var viewModel: CharactersViewModel
    private lateinit var binding: FragmentCharacterBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (activity as MainActivity).contactComponent.inject(this)

        viewModel =
            ViewModelProvider(this, viewModelFactory)[CharactersViewModel::class.java]
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
        setObservers()
        setListeners()
        initRecycler()
        initData()
    }

    private fun initData() {
        if (viewModel.charactersListLiveData.value?.isEmpty() == true) {
            viewModel.setCharactersList(args.charactersArray.toMutableList())
        }
    }

    override fun onResume() {
        super.onResume()
        printLog("On resume")

        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                showAreYouSureDialog()
            }
        })

        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
    }


    private fun showAreYouSureDialog() {
        Snackbar.make(
            binding.root,
            getString(R.string.are_you_sure),
            Snackbar.LENGTH_LONG
        ).setAction("Yes") {
            requireActivity().finish()
        }.show()
    }

    override fun goToDetailView(characterModel: CharacterModel) {
        val action =
            CharacterFragmentDirections.actionCharacterFragmentToDetailFragment(characterModel)
        findNavController().navigate(action)
    }

    private fun setListeners() {
        binding.buttonSortCharacters.setOnClickListener {
            val sortedCharacters = viewModel.getSortedCharacters(charactersGridAdapter.currentList)
            viewModel.charactersListLiveData.value = sortedCharacters

            binding.buttonSortCharacters.visibility = View.GONE

            Toast.makeText(
                requireContext(),
                R.string.sorted,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun initRecycler() {

        val orientation = this.resources.configuration.orientation
        val spanCount = if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            Constants.SPAN_COUNT_PORTRAIT
        } else {
            Constants.SPAN_COUNT_LANDSCAPE
        }

        binding.recyclerViewMyContacts.apply {
            layoutManager = GridLayoutManager(
                requireContext(),
                spanCount
            )

            adapter = charactersGridAdapter
        }
    }

    private fun setObservers() {
        viewModel.charactersListLiveData.observe(viewLifecycleOwner) {
            charactersGridAdapter.submitList(it.toMutableList())
        }

        viewModel.sortedLiveData.observe(viewLifecycleOwner) { sorted ->
            if (sorted) binding.buttonSortCharacters.visibility = View.GONE
        }
    }
}