package com.cmc.atracker.ui.home

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.cmc.atracker.R
import com.cmc.atracker.databinding.HomeAddCalendarEventItemViewBinding
import com.cmc.atracker.model.dto.CalendarEvent
import com.cmc.atracker.model.dto.HomeAddProgress
import com.cmc.atracker.ui.calendar.CalendarEventOnclickListener
import com.cmc.atracker.utils.layoutInflater
import java.time.format.DateTimeFormatter
import java.util.*

class HomeAddCalendarEventsAdapter(calendarEventOnclickListener: CalendarEventOnclickListener, homeViewModel: HomeViewModel, context : Context) :
    RecyclerView.Adapter<HomeAddCalendarEventsAdapter.HomeAddCalendarEventsViewHolder>() {

    val selectedEvents = mutableListOf<CalendarEvent>()

    private val homeAddSelectedProgress = homeViewModel.homeAddSelectedProgress.value


    private val mForegroundSuccess1 = ContextCompat.getDrawable(context, R.drawable.circle_type_1) as Drawable
    private val mForegroundSuccess2 = ContextCompat.getDrawable(context, R.drawable.circle_type_2) as Drawable
    private val mForegroundSuccess3 = ContextCompat.getDrawable(context, R.drawable.circle_type_3) as Drawable
    private val mForegroundSuccess4 = ContextCompat.getDrawable(context, R.drawable.circle_type_4) as Drawable
    private val mForegroundSuccess5 = ContextCompat.getDrawable(context, R.drawable.circle_type_5) as Drawable
    private val mForegroundSuccess6 = ContextCompat.getDrawable(context, R.drawable.circle_type_6) as Drawable
    private val mForegroundSuccess7 = ContextCompat.getDrawable(context, R.drawable.circle_type_7) as Drawable

    var mForegroundColorList : ArrayList<Drawable>? = null

    init {
        when (homeAddSelectedProgress!!.size) {
            1 -> mForegroundColorList = arrayListOf<Drawable>( mForegroundSuccess7 )
            2 -> mForegroundColorList = arrayListOf<Drawable>( mForegroundSuccess1, mForegroundSuccess7 )
            3 -> mForegroundColorList = arrayListOf<Drawable>( mForegroundSuccess1, mForegroundSuccess4, mForegroundSuccess7 )
            4 -> mForegroundColorList = arrayListOf<Drawable>( mForegroundSuccess1, mForegroundSuccess3, mForegroundSuccess5, mForegroundSuccess7 )
            5 -> mForegroundColorList = arrayListOf<Drawable>( mForegroundSuccess1, mForegroundSuccess2, mForegroundSuccess4, mForegroundSuccess5, mForegroundSuccess7 )
            6 -> mForegroundColorList = arrayListOf<Drawable>( mForegroundSuccess1, mForegroundSuccess2, mForegroundSuccess3, mForegroundSuccess4, mForegroundSuccess5, mForegroundSuccess7 )
            7 -> mForegroundColorList = arrayListOf<Drawable>( mForegroundSuccess1, mForegroundSuccess2, mForegroundSuccess3, mForegroundSuccess4, mForegroundSuccess5, mForegroundSuccess6, mForegroundSuccess7 )
        }
    }


    private val selectionTimeFormatter = DateTimeFormatter.ofPattern("a  hh : mm", Locale.KOREAN)
    private val selectionDateFormatter = DateTimeFormatter.ofPattern("yyyy MM dd")



    private var calendarEventOnclickListener: CalendarEventOnclickListener? = null

    init {
        this.calendarEventOnclickListener = calendarEventOnclickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAddCalendarEventsViewHolder {
        return HomeAddCalendarEventsViewHolder(
            HomeAddCalendarEventItemViewBinding.inflate(parent.context.layoutInflater,
                parent,
                false), this.calendarEventOnclickListener!!
        )
    }

    override fun onBindViewHolder(viewHolder: HomeAddCalendarEventsAdapter.HomeAddCalendarEventsViewHolder, position: Int) {
        viewHolder.bind(homeAddSelectedProgress!![position]!!)
    }

    override fun getItemCount(): Int = homeAddSelectedProgress!!.size

    inner class HomeAddCalendarEventsViewHolder(private val binding: HomeAddCalendarEventItemViewBinding, calendarEventOnclickListener: CalendarEventOnclickListener
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        private var calendarEventOnclickListener: CalendarEventOnclickListener? = null
        //private val selectionDateFormatter = DateTimeFormatter.ofPattern("a hh:mm", Locale.KOREAN)


        init {
            binding.itemEventMainCL.setOnClickListener(this)
            this.calendarEventOnclickListener = calendarEventOnclickListener
        }


        fun bind(addProgress: HomeAddProgress) {
            val zonedTime = addProgress.zonedDateTime

            binding.itemEventProgressText.text = addProgress.progressName
            binding.itemEventCircle.background = mForegroundColorList!![bindingAdapterPosition]

            Log.d("zonedTime","${zonedTime}")


            if (zonedTime == null) {
                binding.itemEventPlusTV.visibility = View.VISIBLE
            } else {
                binding.itemEventTime.text = selectionTimeFormatter.format(zonedTime.toLocalTime())
                binding.itemEventDate.text = selectionDateFormatter.format(zonedTime.toLocalDate())
                binding.itemEventPlusTV.visibility = View.INVISIBLE
            }

        }

        override fun onClick(view: View?) {
            this.calendarEventOnclickListener?.onClickContainerView(view = view!!, position = bindingAdapterPosition, viewTag = "tmpTag", calendarEvent = null)

        }
    }
}