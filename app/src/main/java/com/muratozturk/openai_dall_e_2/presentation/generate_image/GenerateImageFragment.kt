package com.muratozturk.openai_dall_e_2.presentation.generate_image


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.muratozturk.click_shrink_effect.applyClickShrink
import com.muratozturk.openai_dall_e_2.R
import com.muratozturk.openai_dall_e_2.common.Resource
import com.muratozturk.openai_dall_e_2.common.Sizes
import com.muratozturk.openai_dall_e_2.common.glideImage
import com.muratozturk.openai_dall_e_2.common.gone
import com.muratozturk.openai_dall_e_2.common.visible
import com.muratozturk.openai_dall_e_2.databinding.FragmentGenerateImageBinding
import com.muratozturk.openai_dall_e_2.network.ResultData
import com.muratozturk.openai_dall_e_2.presentation.viewmodel.MainViewModel
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

@AndroidEntryPoint
class GenerateImageFragment : Fragment(R.layout.fragment_generate_image) {

    private val viewModel: GenerateImageViewModel by viewModels()
    private val binding by viewBinding(FragmentGenerateImageBinding::bind)


    private val viewModelWeather by viewModels<MainViewModel>()
    private var edTextStr: String = ""
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewCollect()
    }

    private fun getTextFromEdittext() {
        edTextStr = binding.promptEditText.text.toString()
        viewModelWeather.getWeatherData(edTextStr).observe(requireActivity()) {
            when (it) {
                is ResultData.Success -> {
                    Log.i("harry", "" + it.toString())
                    it.data?.let { it1 -> Log.d("TAG", "onCreate: $it1") }

                    val locationStr = "Location : " + it.data?.location?.country.toString()
                    binding.location.text = locationStr
                    val countryStr = "Country : " + it.data?.location?.country.toString()
                    binding.country.text = countryStr

                    val tempStr = "Temp : " + it.data?.current?.temp_c.toString()
                    binding.temp.text = tempStr

                    val conditionStr = "Condition : " + it.data?.current?.condition?.text
                    binding.conditions.text
                    val promptStr: String =
                        "Create Image in $locationStr on weather of $conditionStr"
                    sendToAi(promptStr)
                }

                is ResultData.Failed -> {
                    Log.d("TAG", "onCreate: failed ${it.message}")
                }

                is ResultData.Loading -> {
                    Log.d("TAG", "onCreate: Loading")
                }
            }
        }
    }

    private fun sendToAi(promptTextFromWeather: String) {


        with(viewModel) {
            with(binding) {
                if (promptTextFromWeather.isEmpty().not()) {
                    generateImage(promptEditText.text.toString(), 1, Sizes.SIZE_256)
                } else {
                    promptInputLayout.error = getString(R.string.enter_prompt)
                }
            }
        }

    }

    private fun initViewCollect() {
        with(viewModel) {
            with(binding) {
                generateButton.setOnClickListener {
                    getTextFromEdittext()
                }
                generatedImageCard.applyClickShrink()

                viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                    state.collect { response ->
                        when (response) {
                            is Resource.Loading -> {
                                generateButton.startAnimation()
                                shimmerLayout.apply {
                                    startShimmer()
                                    visible()
                                }
                                generatedImagesGrid.gone()
                            }
                            is Resource.Success -> {
                                shimmerLayout.apply {
                                    stopShimmer()
                                    gone()
                                }
                                generatedImagesGrid.visible()

                                generateButton.revertAnimation {
                                    generateButton.setBackgroundResource(R.drawable.rounded_bg3)
                                }

                                generatedImageView.glideImage(response.data.data[0].url)


                                generatedImageCard.setOnClickListener {
                                    showImageFullPage(response.data.data[0].url)
                                }

                            }
                            is Resource.Error -> {
                                shimmerLayout.apply {
                                    stopShimmer()
                                    gone()
                                }
                                generatedImagesGrid.gone()

                                generateButton.revertAnimation {
                                    generateButton.setBackgroundResource(R.drawable.rounded_bg3)
                                }

                                MotionToast.createColorToast(
                                    requireActivity(),
                                    getString(R.string.error),
                                    response.throwable.localizedMessage ?: "Error",
                                    MotionToastStyle.ERROR,
                                    MotionToast.GRAVITY_TOP or MotionToast.GRAVITY_CENTER,
                                    MotionToast.LONG_DURATION,
                                    null
                                )

                                Log.e("Response", response.throwable.localizedMessage ?: "Error")
                            }
                            else -> {}
                        }
                    }
                }
            }

        }
    }

    private fun showImageFullPage(imageUrl: String) {
        findNavController().navigate(
            GenerateImageFragmentDirections.actionGenerateImageFragmentToImageDetailFragment(
                imageUrl
            )
        )
    }
}