package com.example.atracker.ui.calendar

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.atracker.databinding.Example3EventItemViewBinding
import com.example.atracker.databinding.HomeProgressItemBinding
import com.example.atracker.model.dto.Event
import com.example.atracker.ui.home.HomeProgressAdapter
import com.example.atracker.ui.home.HomeProgressOnclickListener
import com.example.atracker.utils.layoutInflater

class EventsAdapter(calendarEventOnclickListener: CalendarEventOnclickListener) :
    RecyclerView.Adapter<EventsAdapter.EventsViewHolder>() {

    val events = mutableListOf<Event>()

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
        viewHolder.bind(events[position])
    }

    override fun getItemCount(): Int = events.size

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

        fun bind(event: Event) {
            binding.itemEventText.text = event.text
            binding.itemEventDate.text = event.date.toString()
        }

        override fun onClick(view: View?) {
            this.calendarEventOnclickListener?.onClickContainerView(view = view!!, position = bindingAdapterPosition, viewTag = "tmpTag")

        }
    }
}