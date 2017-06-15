package net.wcc.ajcrutchfield.jh3_ajcrutchfield;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by AaronC on 10/4/2015.
 */
public class MyDrawHangmanView extends View {
    Context context;
    int drawingCount = 0;

    Paint translucentRedPen, bluePen;
    int backgroundColor;

    private void init(){
        Log.d("Mine", "init called in MyDrawHangmanView");
        backgroundColor = getResources().getColor(R.color.backgroundColor);
        translucentRedPen = new Paint();
        translucentRedPen.setColor(getResources().getColor(R.color.translucentRedPen));
        bluePen = new Paint();
        bluePen.setColor(getResources().getColor(R.color.bluePen));
        bluePen.setStyle(Paint.Style.STROKE);
        bluePen.setStrokeWidth(2);
    }

    public MyDrawHangmanView(Context context){
        super(context);
        init();
        this.context = context;
    }

    public MyDrawHangmanView(Context context, AttributeSet attrs){
        super(context, attrs);
        init();
        this.context = context;
    }

    public void increment(){
        drawingCount += 1;
        invalidate();
    }

    public void reset(){
        drawingCount = 0;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas){
        float width = getWidth();
        float height = getHeight();

        Log.d("Mine", "start onDraw width=" + width + " height=" + height);
        canvas.save();
        canvas.scale(width / 600, height / 650);
        canvas.drawColor(backgroundColor);





        for (int i = 0; i <= drawingCount; i++) {
            Log.d("Mine", "drawingCount=" + drawingCount);
            switch (i){
                case 1:
                    bluePen.setStyle(Paint.Style.FILL);
                    canvas.drawRect(150, 600, 550, 650, bluePen); //base
                    canvas.drawLine(350, 200, 350, 600, bluePen); //post
                    break;
                case 2:
                    canvas.drawLine(200, 200, 350, 200, bluePen); //top horizontal
                    break;
                case 3:
                    canvas.drawLine(200, 200, 200, 250, bluePen); //hang
                    break;
                case 4:
                    canvas.drawCircle(200, 260, 20, bluePen); //head
                    break;
                case 5:
                    canvas.drawLine(200, 270, 200, 375, bluePen); //body
                    break;
                case 6:
                    canvas.drawLine(200, 300, 175, 350, bluePen); //left arm
                    break;
                case 7:
                    canvas.drawLine(200, 300, 225, 350, bluePen); //right arm
                    break;
                case 8:
                    canvas.drawLine(200, 375, 175, 450, bluePen); //left leg
                    break;
                case 9:
                    canvas.drawLine(200, 375, 225, 450, bluePen); //right leg
                    break;
                case 10:
                    translucentRedPen.setStyle(Paint.Style.FILL);
                    canvas.drawCircle(300, 600, 300, translucentRedPen); //sunset
                    break;

            }
        }

        canvas.restore();
    }
}
