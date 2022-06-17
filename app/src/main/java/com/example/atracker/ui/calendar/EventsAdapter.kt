package com.example.atracker.ui.calendar

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.atracker.databinding.Example3EventItemViewBinding
import com.example.atracker.model.dto.CalendarEvent
import com.example.atracker.utils.layoutInflater

class EventsAdapter(calendarEventOnclickListener: CalendarEventOnclickListener) :
    RecyclerView.Adapter<EventsAdapter.EventsViewHolder>() {

    val selectedEvents = mutableListOf<CalendarEvent>()


    private var calendarEventOnclickListener: CalendarEventOnclickListener? = null

    init {
        this.calendarEventOnclickListener = calendarEventOnclickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        return EventsViewHolder(
            Example3EventItemViewBinding.inflate(parent.context.layoutInflater, parent, false), this.calendarEventOnclickListener!!
        )
    }

    override fun onBindViewHolder(viewHolder: EventsViewHolder, position: Int) {
        viewHolder.bind(selectedEvents[position])
    }

    override fun getItemCount(): Int = selectedEvents.size

    inner class EventsViewHolder(private val binding: Example3EventItemViewBinding, calendarEventOnclickListener: CalendarEventOnclickListener
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        private var calendarEventOnclickListener: CalendarEventOnclickListener? = null

        init {
            binding.itemEventMainCL.setOnClickListener(this)
            this.calendarEventOnclickListener = calendarEventOnclickListener
        }

//        init {
//            itemView.setOnClickListener {
//                onClick(events[bindingAdapterPosition])
//            }
//        }

        fun bind(calendarEvent: CalendarEvent) {
            binding.itemEventText.text = calendarEvent.eventTitle
            binding.itemEventDate.text = calendarEvent.zonedDateTime.toLocalTime().toString()
        }

        override fun onClick(view: View?) {
            this.calendarEventOnclickListener?.onClickContainerView(view = view!!, position = bindingAdapterPosition, viewTag = "tmpTag", calendarEvent = selectedEvents[bindingAdapterPosition])

        }
    }
}