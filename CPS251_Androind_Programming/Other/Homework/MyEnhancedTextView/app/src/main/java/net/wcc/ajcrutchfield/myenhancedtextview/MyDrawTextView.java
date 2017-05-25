package net.wcc.ajcrutchfield.myenhancedtextview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Aaron on 10/10/2015.
 */
public class MyDrawTextView extends TextView{

    private Paint marginPaint;
    private Paint linePaint;
    private  int paperColor;
    private float margin;


    public MyDrawTextView(Context context) {
        super(context);
        init();
    }

    public MyDrawTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyDrawTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        //get a reference to out resource table
        Resources myResources = getResources();

        //create the paint brushes we will use in the onDraw method
        marginPaint = new Paint();
        marginPaint.setColor(myResources.getColor(R.color.notepad_margin));

        linePaint = new Paint();
        linePaint.setColor(myResources.getColor(R.color.notepad_lines));
        linePaint.setStrokeWidth(20f);

        //get the paper background color and the margin width
        paperColor = myResources.getColor(R.color.notepad_paper);
        margin = myResources.getDimension(R.dimen.notepad_margin);


    }

    @Override
     public void onDraw(Canvas canvas){
        //color as paper
        canvas.drawColor(paperColor);
        float width = getWidth();
        float height = getHeight();

        //top and bottom thick lines drawn
        canvas.drawLine(0, 0, width, 0, linePaint);
        canvas.drawLine(0,height, width, height, linePaint);

        //draw margin (vertical skinny line)
        canvas.drawLine(margin, 0, margin, getHeight(), marginPaint);

        //move the text across from the margin
        canvas.save();
        canvas.translate(margin, 0);

        //use the TextView to render the text
        super.onDraw(canvas);
        canvas.restore();
    }
}
