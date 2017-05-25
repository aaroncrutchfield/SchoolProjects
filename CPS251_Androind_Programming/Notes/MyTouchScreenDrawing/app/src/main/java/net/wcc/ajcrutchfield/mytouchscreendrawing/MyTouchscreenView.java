package net.wcc.ajcrutchfield.mytouchscreendrawing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import java.util.ArrayList;

/**
 * Created by AaronC on 10/8/2015.
 */
public class MyTouchscreenView extends View implements OnTouchListener {
    Paint currPaint, workingPaint, backGroundPaint;
    int red, green, blue, background;
    int drawingType;

    ArrayList<Shape> shapeArr = new ArrayList<Shape>();
    Shape inProgress = null;

    public void update(int resource) {
        switch (resource) {
            case R.id.red:
                currPaint.setColor(red);
                break;
            case R.id.green:
                currPaint.setColor(green);
                break;
            case R.id.blue:
                currPaint.setColor(blue);
                break;
            case R.id.rectangle:
                drawingType = R.id.rectangle;
                break;
            case R.id.oval:
                drawingType = R.id.oval;
                break;
            case R.id.line:
                drawingType = R.id.line;
                break;
            case R.id.clear:
                shapeArr.clear();
                invalidate();
                break;
            case R.id.undo:
                if (shapeArr.size() > 0)
                    shapeArr.remove(shapeArr.size() - 1);
                break;
        }
    }

    private void init() {
        currPaint = new Paint();
        workingPaint = new Paint();

        currPaint.setStyle(Paint.Style.STROKE);
        workingPaint.setStyle(Paint.Style.STROKE);

        currPaint.setStrokeWidth(2);
        workingPaint.setStrokeWidth(2);

        red = getResources().getColor(R.color.redPen);
        green = getResources().getColor(R.color.greenPen);
        blue = getResources().getColor(R.color.bluePen);
        background = getResources().getColor(R.color.background);

        backGroundPaint = new Paint();
        backGroundPaint.setColor(background);
    }

    public MyTouchscreenView(Context context) {
        super(context);
        init();
    }

    public MyTouchscreenView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyTouchscreenView(Context context, AttributeSet attrs, int defaultStyle) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(background);
        for (int i = 0; i < shapeArr.size(); i++) {
            Shape s = shapeArr.get(i);
            s.draw(canvas, workingPaint);
        }

        if (inProgress != null) {
            inProgress.draw(canvas, workingPaint);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
//        Handle touch events here...
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:           //primary pointer down
            case MotionEvent.ACTION_POINTER_DOWN:   //non-primary pointer (i.e. finger) down

                switch (drawingType) {
                    case R.id.rectangle:
                        inProgress = new Rectangle(x, y, currPaint);
                        break;
                    case R.id.oval:
                        inProgress = new Oval(x, y, currPaint);
                        break;
                    case R.id.line:
                        inProgress = new Line(x, y, currPaint);
                        break;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                if (inProgress != null) {
                    inProgress.subsequentPoint(x, y);
                    shapeArr.add(inProgress);
                    inProgress = null;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (inProgress != null)
                    inProgress.subsequentPoint(x, y);
                break;
        }
        invalidate(); //repaint screen
        return true; //indicate event was handled
    }
}

