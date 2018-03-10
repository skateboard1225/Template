package com.skateboard.couplet.ui.widget

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import com.skateboard.couplet.R

/**
 * Created by skateboard on 2018/3/1.
 */
class TransCircleButton(context: Context, attrs: AttributeSet?) : View(context, attrs)
{


    private var shrinkInterval = 0f

    private var rotateInterval = 30f

    private lateinit var paint: Paint

    private lateinit var textPaint: Paint

    private var bgColor:Int=Color.RED

    private var textColor = Color.BLACK

    private var textSize = 15f

    private var text: String = ""

    private var textRect:Rect= Rect()

    enum class STATE
    {
        NORMAL, SHRINKED, EXPANDING, SHRINKING
    }

    private var state: STATE = STATE.NORMAL

    init
    {
        initAttrs(attrs)
        initBgPaint()
        initTextPaint()
    }

    private fun initAttrs(attrs: AttributeSet?)
    {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TransCircleButton)
        textSize = typedArray.getDimension(R.styleable.TransCircleButton_textSize, 15f)
        textColor = typedArray.getColor(R.styleable.TransCircleButton_textColor, Color.BLACK)
        text = (typedArray.getText(R.styleable.TransCircleButton_text) ?: "").toString()
        shrinkInterval=typedArray.getDimension(R.styleable.TransCircleButton_shrinkInterval,20f)
        bgColor=typedArray.getColor(R.styleable.TransCircleButton_bgColor,Color.RED)
        typedArray.recycle()
    }

    private fun initBgPaint()
    {
        paint = Paint(Paint.ANTI_ALIAS_FLAG)

        paint.color = bgColor

    }

    private fun initTextPaint()
    {
        textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        textPaint.textSize = textSize
        textPaint.color = textColor
        textPaint.textAlign = Paint.Align.CENTER

    }


    fun setTypeface(typeface: Typeface)
    {
        textPaint.typeface = typeface
        invalidate()
    }

    fun shrink()
    {
        if (state == STATE.SHRINKED)
        {
            return
        }
        state = STATE.SHRINKING
        invalidate()
    }

    fun expand()
    {
        if (state == STATE.NORMAL)
        {
            return
        }
        state = STATE.EXPANDING
        invalidate()
    }

    fun getState(): STATE
    {
        return state
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int)
    {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        if (widthMode == MeasureSpec.AT_MOST && heightMeasureSpec == MeasureSpec.AT_MOST)
        {
            super.onMeasure(MeasureSpec.makeMeasureSpec((90 * resources.displayMetrics.density).toInt(), widthMode), MeasureSpec.makeMeasureSpec((30 * resources.displayMetrics.density)
                    .toInt(), heightMode))
        }
        else if (widthMode == MeasureSpec.AT_MOST)
        {
            super.onMeasure(MeasureSpec.makeMeasureSpec((90 * resources.displayMetrics.density).toInt(), widthMode), heightMeasureSpec)
        }
        else if (heightMode == MeasureSpec.AT_MOST)
        {
            super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec((30 * resources.displayMetrics.density)
                    .toInt(), heightMode))
        }
        else
        {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }

    override fun onDraw(canvas: Canvas?)
    {


        when (state)
        {
            STATE.SHRINKING ->
            {
                drawShrink(canvas)
            }

            STATE.SHRINKED ->
            {
                drawShrink(canvas)
            }

            STATE.EXPANDING ->
            {
                drawExpand(canvas)
            }

            else ->
            {
                drawNormal(canvas)
            }
        }


    }


    private fun drawNormal(canvas: Canvas?)
    {
        canvas?.drawRoundRect(RectF(0f, 0f, width.toFloat(), height.toFloat()), height/2.toFloat(), height/2.toFloat(), paint)
        drawText(canvas)
    }

    private fun drawText(canvas: Canvas?)
    {
        textPaint.style = Paint.Style.FILL
        textPaint.getTextBounds(text,0,text.length-1,textRect)
        canvas?.drawText(text, width / 2.toFloat(), height / 2.toFloat()+textRect.height()/2, textPaint)
    }

    private fun drawShrink(canvas: Canvas?)
    {
        calShrinkInterval()
        if ((right - shrinkInterval) - (left + shrinkInterval) <= bottom - top)
        {
            state = STATE.SHRINKED
            drawShrinked(canvas)
        }
        else
        {
            drawChanging(canvas)
        }
    }

    private fun drawShrinked(canvas: Canvas?)
    {
        val radius = ((right - shrinkInterval) - (left + shrinkInterval)) / 2.toFloat()
        canvas?.drawCircle(width / 2.toFloat(), height / 2.toFloat(), radius, paint)
        textPaint.style = Paint.Style.STROKE
        textPaint.strokeWidth = resources.displayMetrics.density * 2
        canvas?.drawArc(RectF(width / 2 - radius / 2f, height / 2 - radius / 2f, width / 2 + radius / 2f, height / 2 + radius / 2f), rotateInterval, 90f, false, textPaint)
        rotateInterval += 30
        postInvalidate()
    }

    private fun drawExpand(canvas: Canvas?)
    {
        calShrinkInterval()
        if (shrinkInterval == 0f)
        {
            state = STATE.NORMAL
            invalidate()
        }
        else
        {
            drawChanging(canvas)
        }
    }


    private fun calShrinkInterval()
    {
        if (state == STATE.SHRINKING)
        {
            shrinkInterval += shrinkInterval
            if ((width - shrinkInterval * 2) <= bottom - top)
            {
                shrinkInterval = (width - (bottom - top)) / 2.toFloat()
            }
        }
        else if (state == STATE.EXPANDING)
        {
            if (shrinkInterval <= 0)
            {
                shrinkInterval = 0f
            }
            else
            {
                shrinkInterval -= shrinkInterval
            }
        }
    }

    private fun drawChanging(canvas: Canvas?)
    {

        canvas?.drawRoundRect(RectF(shrinkInterval, 0f, width - shrinkInterval, height.toFloat()), height/2.toFloat(), height/2.toFloat(), paint)
        postInvalidate()
    }

}