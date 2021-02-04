/*
 * 2020 MarvelDroid , Attribution Images belong to Marvel Entertainment
 * Source Timestamps exist under: https://github.com/PabloRosas17
 * Written by Jose Pablo Rosas  <pablo.rosas.0170@gmail.com>, January 2020
 */

package organization.fizzy.marveldroid.view.screens.ui

import android.content.Context
import android.graphics.*
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.util.AttributeSet
import android.view.View
import organization.fizzy.marveldroid.R

/**
 * @author, evolandlupiz
 * @date, 5/19/2020
 * @property, MarvelDroid
 * @purpose, define custom ui.
 */

/**
 * CurvesBL will create a custom ui element.
 * @param [context] contains a caller.
 * @param [attrs] contains a set of attributes.
 */
class CurvesUi(context: Context, attrs: AttributeSet) : View(context, attrs) {

    /**
     * show view attribute.
     */
    var show: Boolean = false

    /**
     * type view attribute.
     */
    var type: String = ""

    /**
     * initialization, apply custom attributes.
     */
    init {
        context.theme.obtainStyledAttributes(
            attrs, R.styleable.CurvesUi,
            0, 0).apply {
            try {
                show = getBoolean(R.styleable.CurvesUi_show, true)
                type = getString(R.styleable.CurvesUi_type).toString()
            } finally {
                recycle()
            }
        }
    }

    /**
     * onMeasure adjust the canvas measured specification.
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val wSize = paddingLeft + paddingRight + widthMeasureSpec
        val hSize = paddingTop + paddingBottom + heightMeasureSpec
        val w: Int = resolveSizeAndState(wSize, widthMeasureSpec, MeasureSpec.EXACTLY)
        val h: Int = resolveSizeAndState(hSize, heightMeasureSpec, MeasureSpec.EXACTLY)
        setMeasuredDimension(w, h)
    }

    /**
     * onDraw draw the elements.
     */
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.apply {
            drawBlob(this)
        }
    }

    /**
     * drawBlob define paint define path and draw the entity.
     */
    private fun drawBlob(canvas: Canvas) {
        val topLeft = PointF( width.toFloat()-measuredWidth , (height.toFloat()-measuredHeight) )
        val topRight = PointF( width.toFloat() , (height.toFloat()-measuredHeight) )
        val btmLeft = PointF( (width.toFloat()-measuredWidth) , height.toFloat() )
        val btmRight = PointF( width.toFloat() , height.toFloat() )
        val midCnr = PointF( (topLeft.x + topRight.x)/2 , (topRight.y + btmRight.y)/2 )

        if(type == "CurvesBR") {
            val blobPaint: Paint = Paint(ANTI_ALIAS_FLAG).apply {
                this.color = context.resources.getColor(R.color.colorLightSecondary, null)
                this.style = Paint.Style.FILL_AND_STROKE
            }
            canvas.apply {
                val blobPath = Path().apply {
                    moveTo(btmRight.x,btmRight.y)
                    quadTo(btmLeft.x,btmLeft.y,(midCnr.x+(midCnr.x*0.25)).toFloat(),midCnr.y)
                    cubicTo((midCnr.x-(midCnr.x*0.50)).toFloat()
                        ,topRight.y
                        ,(topLeft.x+(topLeft.x*0.85)).toFloat()
                        ,topLeft.y
                        ,topRight.x
                        ,topRight.y)
                }
                drawPath(blobPath,blobPaint)
            }
        }
        if(type == "CurvesBL") {
            val blobPaint: Paint = Paint(ANTI_ALIAS_FLAG).apply {
                this.color = context.resources.getColor(R.color.colorLightSecondary, null)
                this.style = Paint.Style.FILL_AND_STROKE
            }
            canvas.apply {
                val blobPath = Path().apply {
                    moveTo(btmLeft.x,btmLeft.y)
                    quadTo(btmRight.x,btmRight.y,(midCnr.x+(midCnr.x*0.25)).toFloat(),midCnr.y)
                    cubicTo((midCnr.x-(midCnr.x*0.50)).toFloat()
                        ,topLeft.y
                        ,(topRight.x+(topRight.x*0.85)).toFloat()
                        ,topRight.y
                        ,topLeft.x
                        ,topLeft.y)
                }
                drawPath(blobPath,blobPaint)
            }
        }
        if(type == "CurvesUL") {
            val blobPaint: Paint = Paint(ANTI_ALIAS_FLAG).apply {
                this.color = context.resources.getColor(R.color.colorLightSecondary, null)
                this.style = Paint.Style.FILL_AND_STROKE
            }
            canvas.apply {
                val blobPath = Path().apply {
                    moveTo(topLeft.x,topLeft.y)
                    quadTo(topRight.x,topRight.y,(midCnr.x+(midCnr.x*0.25)).toFloat(),midCnr.y)
                    cubicTo((midCnr.x-(midCnr.x*0.50)).toFloat()
                        ,btmLeft.y
                        ,(btmRight.x+(btmRight.x*0.25)).toFloat()
                        ,btmRight.y
                        ,btmLeft.x
                        ,btmLeft.y)
                }
                drawPath(blobPath,blobPaint)
            }
        }
        if(type == "CurvesUR") {
            val blobPaint: Paint = Paint(ANTI_ALIAS_FLAG).apply {
                this.color = context.resources.getColor(R.color.colorLightSecondary, null)
                this.style = Paint.Style.FILL_AND_STROKE
            }
            canvas.apply {
                val blobPath = Path().apply {
                    moveTo(topRight.x,topRight.y)
                    quadTo(topLeft.x,topLeft.y,(midCnr.x+(midCnr.x*0.25)).toFloat(),midCnr.y)
                    cubicTo((midCnr.x-(midCnr.x*0.50)).toFloat()
                        ,btmRight.y
                        ,(btmLeft.x+(btmLeft.x*0.25)).toFloat()
                        ,btmLeft.y
                        ,btmRight.x
                        ,btmRight.y)
                }
                drawPath(blobPath,blobPaint)
            }
        }
    }
}
