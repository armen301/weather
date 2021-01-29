package com.e.weatherapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.e.weatherapp.BuildConfig
import com.e.weatherapp.R
import com.e.weatherapp.data.DataResult
import com.e.weatherapp.data.local.entities.DailyWeather
import com.e.weatherapp.databinding.MainFragmentBinding
import com.e.weatherapp.databinding.ViewItemWeekForecastBinding
import com.e.weatherapp.provider.ConnectionProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class MainFragment : Fragment() {

    @Inject
    lateinit var connectionProvider: ConnectionProvider

    private var fragmentBinding: MainFragmentBinding? = null

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = MainFragmentBinding.bind(view)
        fragmentBinding = binding

        binding.recyclerView.apply {
            setHasFixedSize(true)
            adapter = ForecastAdapter()
        }

        viewModel.loadWeatherData(40.177200, 44.503490)

        viewModel.currentWeather.observe(viewLifecycleOwner) { resource ->
            resource?.let {
                when (it) {
                    is DataResult.Loading -> binding.progressBar.show()
                    is Error -> {
                        binding.progressBar.hide()
                    }
                    is DataResult.Success -> {
                        if (it.data == null) {
                            if (!connectionProvider.isConnected()) {
                                context?.let {
                                    MaterialAlertDialogBuilder(it)
                                        .setMessage(getString(R.string.connection_error_text))
                                        .setPositiveButton(getString(R.string.ok), null).show()
                                }
                                binding.progressBar.hide()
                            }
                            return@let
                        }
                        Glide.with(this@MainFragment).load(prepareIconUrl(it.data.iconUrl))
                            .centerCrop()
                            .into(binding.currentWeatherMainInfoIcon)
                        binding.currentWeatherDateTextView.text =
                            prepareDay(it.data.time?.times(1000) ?: System.currentTimeMillis())
                        binding.currentWeatherFeelsLikeTextView.text =
                            getString(R.string.feels_like, it.data.feelsLike)
                        binding.currentWeatherMainInfoTextView.text = it.data.temp.toString()

                        binding.progressBar.hide()

                    }
                }
            }
        }

        viewModel.dailyWeather.observe(viewLifecycleOwner) { resource ->
            resource?.let {
                when (it) {
                    is DataResult.Loading -> binding.progressBar.show()
                    is Error -> {
                        binding.progressBar.hide()
                    }
                    is DataResult.Success -> {
                        (binding.recyclerView.adapter as ForecastAdapter).submit(it.data)
                    }
                }
            }

        }
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }

    private class ForecastAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        private val data = ArrayList<DailyWeather>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            return Holder(inflater.inflate(R.layout.view_item_week_forecast, parent, false))
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            if (holder !is Holder) {
                return
            }

            val data = data[position]

            holder.dayText.text = prepareDay(data.time?.times(1000) ?: System.currentTimeMillis())

            Glide.with(holder.dayIcon).load(prepareIconUrl(data.iconUrl)).into(holder.dayIcon)
            Glide.with(holder.nightIcon).load(prepareIconUrl(data.iconUrl)).into(holder.nightIcon)

            holder.dayTemperature.text = data.day.toString()
            holder.nightTemperature.text = data.night.toString()
        }

        override fun getItemCount(): Int = data.size

        fun submit(newData: List<DailyWeather>) {
            data.clear()
            data.addAll(newData)
            notifyDataSetChanged()
        }

        private class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val binding = ViewItemWeekForecastBinding.bind(itemView)

            val dayText = binding.itemForecastDayTextView

            val dayIcon = binding.itemForecastDayIcon
            val dayTemperature = binding.itemForecastDayTemperature

            val nightIcon = binding.itemForecastNightIcon
            val nightTemperature = binding.itemForecastNightTemperature

        }
    }

    companion object {
        @JvmStatic
        fun prepareDay(time: Long): String {
            val dateFormat =
                SimpleDateFormat("E, dd MMM", Locale.getDefault())
            return dateFormat.format(Date(time))
        }

        @JvmStatic
        fun prepareIconUrl(icon: String?): String? {
            icon ?: return null
            return "${BuildConfig.ICON_BASE_URL}$icon@2x.png"
        }
    }

}

fun View.hide() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}