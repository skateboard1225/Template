package com.skateboard.couplet.ui.widget

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.CountDownTimer
import android.util.AttributeSet
import android.widget.Button
import com.skateboard.couplet.R

/**
 * Created by skateboard on 2018/3/8.
 */
class RecipButton(context: Context, attrs: AttributeSet?, style: Int) : Button(context, attrs, style)
{
    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)

    constructor(context: Context) : this(context, null, 0)

    private var millisInFuture = 0L

    private var countDownInterval = 0L

    private var normalText: String = ""

    private var countDownText: String = ""

    private var countDownTimer: CountDownTimer? = null

    private var countDownBg: Drawable? = null

    private var normalBg: Drawable? = null

    private var normalTextColor: Int = Color.BLACK

    private var countDownTextColor: Int = Color.WHITE

    init
    {
        initAttrs(attrs)
        setNormalState()
    }

    private fun initAttrs(attrs: AttributeSet?)
    {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.RecipButton)
        millisInFuture = typedArray.getInteger(R.styleable.RecipButton_millisInFuture, 0).toLong()
        countDownInterval = typedArray.getInteger(R.styleable.RecipButton_countDownInterval, 0).toLong()
        normalText = typedArray.getString(R.styleable.RecipButton_normal_text)
        countDownText = typedArray.getString(R.styleable.RecipButton_countdown_text)
        countDownBg = typedArray.getDrawable(R.styleable.RecipButton_countDownBg)
        normalBg = typedArray.getDrawable(R.styleable.RecipButton_normalBg)
        countDownTextColor = typedArray.getColor(R.styleable.RecipButton_countDownTextColor, Color.WHITE)
        normalTextColor = typedArray.getColor(R.styleable.RecipButton_normalTextColor, Color.BLACK)
        typedArray.recycle()
    }

    private fun setNormalState()
    {
        isClickable = true
        isEnabled = true
        setTextColor(normalTextColor)
        text = normalText
        background = normalBg
    }


    fun startCountDown()
    {
        countDownTimer = object : CountDownTimer(millisInFuture+50, countDownInterval)
        {
            override fun onFinish()
            {
                setNormalState()
            }

            override fun onTick(millisUntilFinished: Long)
            {

                setTickState(millisUntilFinished)
            }

        }
        countDownTimer?.start()
    }

    private fun setTickState(millisUntilFinished: Long)
    {
        isEnabled = false
        setTextColor(countDownTextColor)
        background = countDownBg
        text = countDownText + "(${millisUntilFinished / 1000 - 1})"
    }

    fun cancelCountDown()
    {
        countDownTimer?.cancel()
    }

    override fun onDetachedFromWindow()
    {
        super.onDetachedFromWindow()
        cancelCountDown()
    }
}