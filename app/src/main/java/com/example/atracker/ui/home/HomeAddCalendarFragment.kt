package com.example.atracker.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.atracker.R
import com.example.atracker.databinding.Example3CalendarDayBinding
import com.example.atracker.databinding.Example3CalendarHeaderBinding
import com.example.atracker.databinding.FragmentHomeAddCalendarBinding
import com.example.atracker.model.dto.CalendarEvent
import com.example.atracker.ui.MainActivity
import com.example.atracker.ui.calendar.*
import com.example.atracker.utils.*
import com.example.atracker.utils.setTextColorRes
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.*

class HomeAddCalendarFragment : Fragment(), CalendarEventOnclickListener {

    private val homeViewModel: HomeViewModel by activityViewModels()
    private var _binding: FragmentHomeAddCalendarBinding? = null

    private val parentActivity by lazy {
        activity as MainActivity
    }
    private val binding get() = _binding!!
    private val lazyContext by lazy {
        requireContext()
    }

    //private val homeAddCalendarEventsAdapter = HomeAddCalendarEventsAdapter(this, homeViewModel)

    private val inputDialog by lazy {
        EventCreateFragment()
//        val editText = AppCompatEditText(requireContext())
//        val layout = LinearLayout(requireContext()).apply {
//            // Setting the padding on the EditText only pads the input area
//            // not the entire EditText so we wrap it in a FrameLayout.
//            val padding = dpToPx(20, requireContext())
//            setPadding(padding, padding, padding, padding)
//            addView(editText, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT))
//        }
//
//
//        AlertDialog.Builder(requireContext())
//            .setTitle(getString(R.string.example_3_input_dialog_title))
//            .setView(layout)
//            .setPositiveButton(R.string.save) { _, _ ->
//                saveEvent(editText.text.toString())
//                // Prepare EditText for reuse.
//                editText.setText("")
//
//                binding.calendarView.notifyCalendarChanged()
//
//            }
//            .setNegativeButton(R.string.close, null)
//            .create()
//            .apply {
//                setOnShowListener {
//                    // Show the keyboard
//                    editText.requestFocus()
//                    context.inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
//                }
//                setOnDismissListener {
//                    // Hide the keyboard
//                    context.inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
//                }
//            }
    }


    private var selectedDate: LocalDate? = null
    private val today = LocalDate.now()
    private val titleSameYearFormatter = DateTimeFormatter.ofPattern("MMMM")
    private val titleFormatter = DateTimeFormatter.ofPattern("MMM yyyy")
    //private val selectionFormatter = DateTimeFormatter.ofPattern("d MMM yyyy")
    private val selectionHeaderFormatter = DateTimeFormatter.ofPattern("yyyy MM")
    private val selectionDateFormatter = DateTimeFormatter.ofPattern("M월 d일 E요일", Locale.KOREAN)


    private val events = mutableMapOf<LocalDate, List<CalendarEvent>>()



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentHomeAddCalendarBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textNotifications
//        calendarViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })




        return root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //_binding = FragmentCalendarDisplayBinding.bind(view)



//        homeViewModel.eventChangeFlag.observe(viewLifecycleOwner, Observer {
//            if (it) {
//                updateAdapterForDate(selectedDate!!)
//                binding.calendarView.notifyDateChanged(homeViewModel.zonedDateTime.value!!.toLocalDate())
//            }
//
//        })

        val homeAddCalendarEventsAdapter = HomeAddCalendarEventsAdapter(this, homeViewModel, lazyContext)


        binding.homeAddCalendarBackButton.setOnClickListener { view ->
            val navController = view.findNavController()
            navController.popBackStack()
        }

        binding.homeAddCalendarBottomCL.setOnClickListener { view ->
            homeViewModel.postApply()

            homeViewModel.postApplyFlag.observe(viewLifecycleOwner, Observer {
                homeViewModel.clearHomeAddText()
                homeViewModel.getApplyDisplay(applyIds = null, includeContent = false)

                view.findNavController().navigate(R.id.action_navigation_home_add_calendar_to_navigation_home)
                parentActivity.mainBottomNavigationAppear()
            })
        }


        binding.exThreeRv.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = homeAddCalendarEventsAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
        }

        homeViewModel.homeAddDateSelectFlag.observe(viewLifecycleOwner, Observer {
            homeAddCalendarEventsAdapter.notifyDataSetChanged()
        })


        val daysOfWeek = daysOfWeekFromLocale()
        val currentMonth = YearMonth.now()
        binding.calendarView.apply {
            setup(currentMonth.minusMonths(10), currentMonth.plusMonths(10), daysOfWeek.first())
            scrollToMonth(currentMonth)
        }

        if (savedInstanceState == null) {
            binding.calendarView.post {
                // Show today's events initially.
                selectDate(today)
            }
        }

        class DayViewContainer(view: View) : ViewContainer(view) {
            lateinit var day: CalendarDay // Will be set when this container is bound.
            val binding = Example3CalendarDayBinding.bind(view)

            init {
                view.setOnClickListener {
                    if (day.owner == DayOwner.THIS_MONTH) {
                        selectDate(day.date)
                    }
                }
            }
        }
        binding.calendarView.dayBinder = object : DayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)
            override fun bind(container: DayViewContainer, day: CalendarDay) {
                container.day = day
                val textView = container.binding.exThreeDayText

                val dotView1 = container.binding.exThreeDotView1
                val dotView2 = container.binding.exThreeDotView2
                val dotView3 = container.binding.exThreeDotView3

                textView.text = day.date.dayOfMonth.toString()

                if (day.owner == DayOwner.THIS_MONTH) {
                    textView.makeVisible()

                    //Log.d("events", "${events[day.date]}")
                    //Log.d("events", "${homeViewModel.events.value!![day.date]}")

                    when (day.date) {
                        today -> {
                            textView.setTextColorRes(R.color.atracker_white)
                            textView.setBackgroundResource(R.drawable.example_3_today_bg)

                            //eventDotSetVisibiity(homeViewModel.events.value!![day.date], dotView1, dotView2, dotView3)

//                            dotView.makeInVisible()
//
//                            dotView1.makeInVisible()
//                            dotView2.makeInVisible()
//                            dotView3.makeInVisible()

                        }
                        selectedDate -> {
                            textView.setTextColorRes(R.color.progress_color_7)
                            //textView.setBackgroundResource(R.drawable.example_3_selected_bg)

                            //eventDotSetVisibiity(homeViewModel.events.value!![day.date], dotView1, dotView2, dotView3)

//                            dotView.makeInVisible()
//
//                            dotView1.makeInVisible()
//                            dotView2.makeInVisible()
//                            dotView3.makeInVisible()
                        }
                        else -> {
                            textView.setTextColorRes(R.color.white)
                            textView.background = null
                            //dotViewMore.isVisible = events[day.date].orEmpty().isNotEmpty()

                            //eventDotSetVisibiity(homeViewModel.events.value!![day.date], dotView1, dotView2, dotView3)

                        }
                    }
                } else {
                    textView.setTextColorRes(R.color.atracker_gray_3)

                    dotView1.makeInVisible()
                    dotView2.makeInVisible()
                    dotView3.makeInVisible()
                }
            }
        }

        binding.calendarView.monthScrollListener = {
//            homeActivityToolbar.title = if (it.year == today.year) {
//                titleSameYearFormatter.format(it.yearMonth)
//            } else {
//                titleFormatter.format(it.yearMonth)
//            }

            // Select the first day of the month when
            // we scroll to a new month.
            selectDate(it.yearMonth.atDay(1))
        }

        class MonthViewContainer(view: View) : ViewContainer(view) {
            val legendLayout = Example3CalendarHeaderBinding.bind(view).legendLayout.root
        }


        binding.calendarView.monthHeaderBinder = object :
            MonthHeaderFooterBinder<MonthViewContainer> {
            override fun create(view: View) = MonthViewContainer(view)
            override fun bind(container: MonthViewContainer, month: CalendarMonth) {
                // Setup each header day text if we have not done that already.
                if (container.legendLayout.tag == null) {
                    container.legendLayout.tag = month.yearMonth
                    container.legendLayout.children.map { it as TextView }.forEachIndexed { index, tv ->
                        var dayNameKorean = ""
                        when (daysOfWeek[index].name) {
                            "SUNDAY" -> dayNameKorean = "일"
                            "MONDAY" -> dayNameKorean = "월"
                            "TUESDAY" -> dayNameKorean = "화"
                            "WEDNESDAY" -> dayNameKorean = "수"
                            "THURSDAY" -> dayNameKorean = "목"
                            "FRIDAY" -> dayNameKorean = "금"
                            "SATURDAY" -> dayNameKorean = "토"
                        }

                        tv.text = dayNameKorean
                        tv.setTextColorRes(R.color.atracker_white)
                    }
                }
            }
        }

//        binding.calendarDisplayPlusIV.setOnClickListener {
//            inputDialog.show(parentFragmentManager, EventCreateFragment.TAG)
//        }
    }


    /////////////
    private fun selectDate(date: LocalDate) {
        if (selectedDate != date) {
            val oldDate = selectedDate
            selectedDate = date
            oldDate?.let { binding.calendarView.notifyDateChanged(it) }
            binding.calendarView.notifyDateChanged(date)
            updateAdapterForDate(date)
        }
    }

//    private fun saveEvent(text: String) {
//        if (text.isBlank()) {
//            Toast.makeText(requireContext(), R.string.example_3_empty_input_text, Toast.LENGTH_LONG).show()
//        } else {
//            selectedDate?.let {
//                events[it] = events[it].orEmpty().plus(CalendarEvent(UUID.randomUUID().toString(), text, it))
//                updateAdapterForDate(it)
//            }
//        }
//    }
//
//    private fun deleteEvent(calendarEvent: CalendarEvent) {
//        val date = calendarEvent.date
//        events[date] = events[date].orEmpty().minus(calendarEvent)
//        updateAdapterForDate(date)
//    }

    private fun updateAdapterForDate(date: LocalDate) {
//        homeAddCalendarEventsAdapter.apply {
//            selectedEvents.clear()
//            //selectedEvents.addAll(homeViewModel.events.value!![date].orEmpty())
//            notifyDataSetChanged()
//        }


        binding.exThreeSelectedDateHeaderText.text = selectionHeaderFormatter.format(date)
        //binding.exThreeSelectedDateText.text = selectionDateFormatter.format(date)


    }


    private fun eventDotSetVisibiity(currentDataCalendarEvent : List<CalendarEvent>?, view1: View, view2: View, view3: View ){
        if (currentDataCalendarEvent == null) {
            view1.makeInVisible()
            view2.makeInVisible()
            view3.makeInVisible()
        } else {
            when (currentDataCalendarEvent.size) {
                0 -> {
                    view1.makeInVisible()
                    view2.makeInVisible()
                    view3.makeInVisible()
                }
                1 -> {
                    view1.makeVisible()
                    view2.makeInVisible()
                    view3.makeInVisible()
                }
                2 -> {
                    view1.makeVisible()
                    view2.makeVisible()
                    view3.makeInVisible()
                }
                3 -> {
                    view1.makeVisible()
                    view2.makeVisible()
                    view3.makeVisible()
                }
                else -> {
                    view1.makeVisible()
                    view2.makeVisible()
                    view3.makeVisible()
                }
            }
        }



    }

    override fun onClickContainerView(view: View, position: Int, viewTag: String, calendarEvent: CalendarEvent?) {
        HomeAddDateFragment(position).show(parentFragmentManager, HomeAddDateFragment.TAG)

    }

}