package com.example.esame2

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class DialView: View {
    private val SELECTION_COUNT = 2
    private var mActiveSelection = 0
    private var mRectOnColor = Color.GREEN
    private var mRectOffColor = Color.RED

    private var mTextPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mDialPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mOffOnTextPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mLinePaint = Paint(Paint.ANTI_ALIAS_FLAG)

    constructor(context: Context?) : super(context){
        init(null)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
        init(attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){
        init(attrs)
    }

    private fun init(attrs: AttributeSet?){
        //get the custom attributes (btnOnColor and btnOffColor) if available
        if(attrs != null) {
            val typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.DialView, 0, 0)

            //Set the fan on and fan off colors from the attribute values
            mRectOnColor = typedArray.getColor(R.styleable.DialView_btnOnColor, mRectOnColor);
            mRectOffColor = typedArray.getColor(R.styleable.DialView_btnOffColor, mRectOffColor);
            typedArray.recycle()
        }

        //paint per la scritta ON e OFF
        val textColor = getResources().getColor(R.color.orange)
        mTextPaint.setColor(textColor)
        mTextPaint.setTextAlign(Paint.Align.CENTER)
        mTextPaint.style = Paint.Style.FILL_AND_STROKE

        //paint per disegnare rettangolo base
        mDialPaint.color=mRectOffColor

        //paint per disegnare cerchio OFF e linea ON
        mOffOnTextPaint.setColor(Color.WHITE)

        //paint per la linea divisoria
        val lineColor = getResources().getColor(R.color.darkgrey)
        mLinePaint.setColor(lineColor)
        mLinePaint.style = Paint.Style.STROKE
        mLinePaint.strokeWidth = 25f

        setOnClickListener {
            mActiveSelection = (mActiveSelection + 1) % SELECTION_COUNT

            if (mActiveSelection >= 1) {
                mDialPaint.color = mRectOnColor
            } else {
                mDialPaint.color = mRectOffColor
            }
            invalidate()        //serve per ridisegnare tutta la view
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        //attributi per corretto ridmimensionamento
        mOffOnTextPaint.textSize = height/5.toFloat()
        mTextPaint.textSize = height/8.toFloat()


        //disegno rettangolo base
        val rLeft = width/9
        val rTop = height/9
        val rRight = width - rLeft
        val rBottom = height - rTop

        //creo rettangolo e lo disegno sulla bitmap
        val rect = RectF(rLeft.toFloat(), rTop.toFloat(), rRight.toFloat(), rBottom.toFloat())
        canvas.drawRect(rect, mDialPaint)

        //Creo linea separatrice
        val lineX = width/9
        val lineY = height/2
        val lineSX = width - rLeft
        val lineSY = height/2

        // disegno linea sulla bitmap
        canvas.drawLine(lineX.toFloat(), lineY.toFloat(), lineSX.toFloat(), lineSY.toFloat(), mLinePaint)

        //creo parte superiore con OFF
        val offX = (width/2) - rLeft
        val offY = height/3.5

        // disegno O sulla bitmap
        canvas.drawText("O", offX.toFloat(), offY.toFloat(), mOffOnTextPaint)

        //creo parte inferiore con ON
        val onX = (width/2)  - (rLeft/2)
        val onY = rBottom - (height/20)

        //disegno I sulla bitmap
        canvas.drawText("I", onX.toFloat(), onY.toFloat(), mOffOnTextPaint)

        //creo scritta ON/OFF in alto
        val OnOffX = width/2
        val OnOffY = height/10

        //disegno scritta sulla bitmpa
        if (mActiveSelection >= 1) {
            canvas.drawText("ON", OnOffX.toFloat(), OnOffY.toFloat(), mTextPaint)
        } else {
            canvas.drawText("OFF", OnOffX.toFloat(), OnOffY.toFloat(), mTextPaint)
        }

    }
}