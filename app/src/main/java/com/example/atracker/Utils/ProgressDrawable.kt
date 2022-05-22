package com.example.atracker.Utils

import android.content.Context
import android.graphics.*

import android.graphics.drawable.Drawable
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.atracker.R


internal class ProgressDrawable(val context: Context, private val numSegments : Int, private val progressInt : Int) : Drawable() {
    private val mPaint = Paint()
    private val mSegment = RectF()

    private val mSegmentMiniCircle = RectF()

    private val rectPath = Path()
    private val mBackground = ContextCompat.getColor(context, R.color.grey_progress)
    private val mForeground1 = ContextCompat.getColor(context, R.color.progress_color_1)
    private val mForeground2 = ContextCompat.getColor(context, R.color.progress_color_2)
    private val mForeground3 = ContextCompat.getColor(context, R.color.progress_color_3)
    private val mForeground4 = ContextCompat.getColor(context, R.color.progress_color_4)
    private val mForeground5 = ContextCompat.getColor(context, R.color.progress_color_5)

    private val mForegroundColorList = arrayListOf<Int>(mBackground, mForeground1, mForeground2, mForeground3, mForeground4, mForeground5 )

    companion object {
    }

    override fun onLevelChange(level: Int): Boolean {
        invalidateSelf()
        return true
    }


    override fun draw(canvas: Canvas) {
        Log.d("test123level", "${level}, ${bounds}")
        val level = level / 10000f
        val b = bounds

        val gapWidth = b.height() / 2f

        val segmentWidth = (b.width() - (numSegments - 1)).toFloat() / numSegments
        mSegment[0f, 0f, segmentWidth] = b.height().toFloat()
        mSegmentMiniCircle.left = mSegment.left
        mSegmentMiniCircle.right = mSegment.right / 5
        mSegmentMiniCircle.top = mSegment.top
        mSegmentMiniCircle.bottom = mSegment.bottom


        for (i in 1 until progressInt + 1) {
            mPaint.color = mForegroundColorList[i]
            if (i == 1) {
                if (i == progressInt) {

                } else {
                    canvas.drawRoundRect(mSegmentMiniCircle,50f,50f, mPaint)

                    mSegment.offset(mSegment.width() / 9, 0f)
                    mSegmentMiniCircle.offset(mSegment.width() / 9, 0f)

                    canvas.drawRect(mSegment,mPaint)
//                    mSegment.offset(mSegment.width() / 9, 0f)
//                    mSegmentMiniCircle.offset(mSegment.width() / 9, 0f)

                    //canvas.drawRoundRect(mSegmentMiniCircle,50f,50f, mPaint)

                    mSegment.offset(mSegment.width(), 0f)
                }


            } else if (i == progressInt) {
                //canvas.drawRoundRect(mSegmentMiniCircle,50f,50f, mPaint)
                //mSegment.offset(mSegment.width() / 9, 0f)
                canvas.drawRect(mSegment,mPaint)
                mSegment.offset(mSegment.width(), 0f)
                mSegmentMiniCircle.offset(mSegment.width(), 0f)
                mSegmentMiniCircle.offset(mSegment.width() - mSegment.width() / 9 , 0f)
                canvas.drawRoundRect(mSegmentMiniCircle,50f,50f, mPaint)

                //mSegment.offset(mSegment.width(), 0f)

            } else {
                canvas.drawRect(mSegment,mPaint)
                mSegment.offset(mSegment.width(), 0f)
                mSegmentMiniCircle.offset(mSegment.width(), 0f)
            }
        }



//        for (i in 0 until numSegments) {
//            val loLevel = i / numSegments.toFloat()
//            val hiLevel = (i + 1) / numSegments.toFloat()
//            Log.d("test123", "${loLevel}, ${hiLevel}, ${level}, ${segmentWidth}, ${mSegment}")
//            if (loLevel <= level && level <= hiLevel) {
////                val middle = mSegment.left + numSegments * segmentWidth * (level - loLevel)
////                Log.d("test123middle", "${middle}")
////                canvas.drawRoundRect(mSegment.left, mSegment.top, middle, mSegment.bottom,100f,100f, mPaint)
////                mPaint.color = mBackground
////                canvas.drawRoundRect(middle, mSegment.top, mSegment.right, mSegment.bottom,100f,100f, mPaint)
//            } else {
//                //canvas.drawRect(mSegment, mPaint)
//                canvas.drawRoundRect(mSegment,100f,100f,mPaint)
//                //canvas.drawArc(mSegment,270f,360f,false, mPaint)
//
//            }
//            mSegment.offset(mSegment.width(), 0f)
//        }


    }

    override fun setAlpha(alpha: Int) {}
    override fun setColorFilter(cf: ColorFilter?) {}
    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }


    fun typeCalculate(current: Int): ProgressBarType {
        when (current) {
            1 -> { // 처음
                if (current <= progressInt) { // 성공
                    return ProgressBarType.PROGRESS_TYPE0
                } else { // 실패
                    return ProgressBarType.PROGRESS_TYPE1
                }
            }
            numSegments -> { // 마지막
                if (current <= progressInt) { // 성공
                    return ProgressBarType.PROGRESS_TYPE4
                } else { // 실패
                    return ProgressBarType.PROGRESS_TYPE5
                }
            }
            else -> { // 중간에 끼인것들
                if (current <= progressInt) { // 성공
                    return ProgressBarType.PROGRESS_TYPE2
                } else { // 실패
                    return ProgressBarType.PROGRESS_TYPE3
                }
            }
        }
    }
}