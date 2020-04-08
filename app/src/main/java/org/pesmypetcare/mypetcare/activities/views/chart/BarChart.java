package org.pesmypetcare.mypetcare.activities.views.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;

public class BarChart extends View {
    private static final int BORDER_X = 125;
    private static final int BORDER_Y = 50;
    private static final int X_COORD = 0;
    private static final int Y_COORD = 1;
    private static final int CHART_SIZE = 275;
    private static final int X_AXIS_DIVISIONS = 4;
    /*private static StatisticData[] {

    };*/

    private int width;
    private int height;
    private int[] yAxisMaxPoint;
    private int[] originPoint;
    private int[] xAxisMaxPoint;
    private double xDivisionFactor;
    private double yDivisionFactor;
    private Paint axisPaint;
    private int selectedStatistic;

    public BarChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initDrawComponents();
    }

    private void initDrawComponents() {
        axisPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        axisPaint.setColor(Color.BLACK);
        axisPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawAxis(canvas);
        drawxPoints(canvas);

        yDivisionFactor = calculateYDivisionFactor();
    }

    private double calculateYDivisionFactor() {
        return 0;
    }

    private void drawxPoints(Canvas canvas) {
        xDivisionFactor = calculateXDivisionFactor();

        for (int next = 1; next <= X_AXIS_DIVISIONS; ++next) {
            double xPoint = originPoint[X_COORD] + next * xDivisionFactor;
            canvas.drawLine((int) xPoint, originPoint[Y_COORD], (int) xPoint, originPoint[Y_COORD] + 10, axisPaint);
        }
    }

    private double calculateXDivisionFactor() {
        return ((double)(getWidth() - 2 * BORDER_Y)) / (X_AXIS_DIVISIONS + 2);
    }

    private void drawAxis(Canvas canvas) {
        calculateAxisPoints();
        canvas.drawLine(yAxisMaxPoint[X_COORD], yAxisMaxPoint[Y_COORD], originPoint[X_COORD], originPoint[Y_COORD],
            axisPaint);
        canvas.drawLine(originPoint[X_COORD], originPoint[Y_COORD], xAxisMaxPoint[X_COORD], xAxisMaxPoint[Y_COORD],
            axisPaint);
    }

    private void calculateAxisPoints() {
        float yAxisLastPixel = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, CHART_SIZE,
            getResources().getDisplayMetrics());
        yAxisMaxPoint = new int[] {BORDER_X, BORDER_Y};
        originPoint = new int[] {BORDER_X, (int) yAxisLastPixel};
        xAxisMaxPoint = new int[] {getWidth() - BORDER_X, (int) yAxisLastPixel};
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int minWidth = getPaddingLeft() + getPaddingRight() + getSuggestedMinimumWidth();
        width = resolveSizeAndState(minWidth, widthMeasureSpec, 1);

        int minHeight = getPaddingTop() + getPaddingBottom() + getSuggestedMinimumHeight();
        height = resolveSizeAndState(minHeight, heightMeasureSpec, 0);

        setMeasuredDimension(width, height);
    }

    public void changeStatistic(int statisticId) {

    }
}
