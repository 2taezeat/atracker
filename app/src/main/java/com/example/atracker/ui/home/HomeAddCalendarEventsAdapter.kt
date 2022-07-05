package com.example.atracker.ui.home

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.atracker.databinding.Example3EventItemViewBinding
import com.example.atracker.databinding.HomeAddCalendarEventItemViewBinding
import com.example.atracker.model.dto.CalendarEvent
import com.example.atracker.ui.calendar.CalendarEventOnclickListener
import com.example.atracker.utils.layoutInflater
import java.time.format.DateTimeFormatter
import java.util.*

class HomeAddCalendarEventsAdapter(calendarEventOnclickListener: CalendarEventOnclickListener, homeViewModel: HomeViewModel) :
    RecyclerView.Adapter<HomeAddCalendarEventsAdapter.EventsViewHolder>() {

    val selectedEvents = mutableListOf<CalendarEvent>()

    val selectedChips = homeViewModel.homeAddSelectedChipName.value


    private val selectionDateFormatter = DateTimeFormatter.ofPattern("a  hh : mm", Locale.KOREAN)


    private var calendarEventOnclickListener: CalendarEventOnclickListener? = null

    init {
        this.calendarEventOnclickListener = calendarEventOnclickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        return EventsViewHolder(
            HomeAddCalendarEventItemViewBinding.inflate(parent.context.layoutInflater, parent, false), this.calendarEventOnclickListener!!
        )
    }

    override fun onBindViewHolder(viewHolder: EventsViewHolder, position: Int) {
        viewHolder.bind(selectedChips!![position]!!)
    }

    override fun getItemCount(): Int = selectedChips!!.size

    inner class EventsViewHolder(private val binding: HomeAddCalendarEventItemViewBinding, calendarEventOnclickListener: CalendarEventOnclickListener
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        private var calendarEventOnclickListener: CalendarEventOnclickListener? = null
        //private val selectionDateFormatter = DateTimeFormatter.ofPattern("a hh:mm", Locale.KOREAN)


        init {
            binding.itemEventMainCL.setOnClickListener(this)
            this.calendarEventOnclickListener = calendarEventOnclickListener
        }

//        init {
//            itemView.setOnClickListener {
//                onClick(events[bindingAdapterPosition])
//            }
//        }

        fun bind(chipProgressName: String) {
            //binding.itemEventCompanyNameText.text = calendarEvent.eventTitle
            //binding.itemEventDate.text = calendarEvent.zonedDateTime.toLocalTime().toString()
            //binding.itemEventDate.text = selectionDateFormatter.format(calendarEvent.zonedDateTime.toLocalTime())
            //selectionHeaderFormatter.format(date)


            binding.itemEventProgressText.text = chipProgressName
        }

        override fun onClick(view: View?) {
            this.calendarEventOnclickListener?.onClickContainerView(view = view!!, position = bindingAdapterPosition, viewTag = "tmpTag", calendarEvent = selectedEvents[bindingAdapterPosition])

        }
    }
}