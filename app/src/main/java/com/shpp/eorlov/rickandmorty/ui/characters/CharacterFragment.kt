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
import android.widget.ImageView
import android.widget.LinearLayout
import com.shpp.eorlov.rickandmorty.R


class CharacterFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

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
    }

    override fun onResume() {
        super.onResume()
        printLog("On resume")
    }

    private fun addItem() {
        val btn7 = ImageView(context)
        btn7.setImageResource(R.drawable.rick_and_morty)
        btn7.scaleType = ImageView.ScaleType.FIT_XY
        val width = convertPxToDp(150f).toInt()
        val height = convertPxToDp(150f).toInt()
        val parms = LinearLayout.LayoutParams(width, height)
        btn7.layoutParams = parms
        binding.gridLayoutCharacters.addView(btn7)
    }

    private fun setListeners() {
        binding.gridLayoutCharacters.setOnClickListener {
            addItem()
        }
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