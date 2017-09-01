package com.da08.sensor;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Da08 on 2017. 9. 1..
 */

public class Graph extends View {
    private int realWidth;
    private int realHeight;
    private final int X_DENSITY = 50, Y_DENSITY = 100;
    private int cellWidth,cellHeight;
    private Paint graphPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int lineWidth = 10;
    private Point zeroByzero = new Point();   // 시작점
    private List<Integer> points = new ArrayList<>();
    private float[] lines = new float[X_DENSITY*2];

    public Graph(Context context) {
        super(context,null);
    }

    public Graph(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.Graph);
        int graphColor = ta.getInt(R.styleable.Graph_graphColor,0);
        graphPaint.setColor((graphColor != 0)? graphColor: Color.RED);
        graphPaint.setStrokeWidth(lineWidth);  // 그래프에 그려질 선
        ta.recycle();  // db사용 후 close와 같은 의미
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        realWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        realHeight = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
        cellHeight = realHeight/Y_DENSITY;
        cellWidth = realWidth/X_DENSITY;
        zeroByzero.set(getPaddingLeft(),getPaddingTop()+realHeight/2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLines(lines,graphPaint);

    }

    public void setPoints(List<Integer> points) {
        if(points.size() < 2 ){
            throw new IllegalArgumentException("Require at least two points");
        }else if(points.size() > X_DENSITY){
            int deletaable = points.size() - Y_DENSITY;
            for(int i = 0 ; i < deletaable; i ++){
                points.remove(i);
            }
        }
        this.points = points;
        populateLinePoints();
        invalidate();
    }

    public void setPoint(Integer point){
        if(points.size() == Y_DENSITY) {
            points.remove(0);
        }else if(points.size() == 0){
            points.add(0);
        }
        points.add(point);
        populateLinePoints();
        invalidate();
    }
    private void populateLinePoints(){

        for(int i = 0; i < points.size(); i++){
            if( i % 2 == 1){
                float y = zeroByzero.y - (points.get(i/2)*cellHeight);
                lines[i] = y;  // y 값
            }else{
                float x = zeroByzero.x + (i/2)*cellWidth;
                lines[i] = x;  // x 값
            }
        }
    }
}
