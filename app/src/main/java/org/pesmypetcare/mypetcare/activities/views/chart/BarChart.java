package org.pesmypetcare.mypetcare.activities.views.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;

import org.pesmypetcare.mypetcare.R;

import java.util.List;

public class BarChart extends View {
    private static final int BORDER_X = 125;
    private static final int BORDER_Y = 50;
    private static final int X_COORD = 0;
    private static final int Y_COORD = 1;
    private static final int CHART_SIZE = 275;
    private static final int X_AXIS_DIVISIONS = 4;
    private static final int Y_AXIS_DIVISIONS = 5;
    private static final int BAR_PROPORTION = 4;
    private static StatisticData[] statisticData = {
        new StubStatisticData()
    };

    private int width;
    private int height;
    private int[] yAxisMaxPoint;
    private int[] originPoint;
    private int[] xAxisMaxPoint;
    private double xDivisionFactor;
    private double yDivisionFactor;
    private double maxValue;
    private double nextTenMultiple;
    private Paint axisPaint;
    private Paint barPaint;
    private int selectedStatistic;
    private int dataRegion;

    public BarChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initDrawComponents();
    }

    private void initDrawComponents() {
        axisPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        axisPaint.setColor(Color.BLACK);
        axisPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        barPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        barPaint.setColor(getResources().getColor(R.color.colorPrimary));
        barPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawAxis(canvas);
        drawBars(canvas);
    }

    private void drawBars(Canvas canvas) {
        List<Double> yValues = statisticData[selectedStatistic].getyAxisValues();
        float barDrawingFactor = (float) (xDivisionFactor / BAR_PROPORTION);

        System.out.println("DATA REGION: " + dataRegion);
        int next = dataRegion * X_AXIS_DIVISIONS;
        int count = 0;

        while (count < X_AXIS_DIVISIONS && next < yValues.size()) {
            System.out.println("NEXT: " + next);
            float xPoint = (float) (originPoint[X_COORD] + (count + 1) * xDivisionFactor);
            float yProportion = (float) (yValues.get(next) * Y_AXIS_DIVISIONS / nextTenMultiple);
            float yPoint = (float) (originPoint[Y_COORD] - yProportion * yDivisionFactor);
            //canvas.drawLine((int) xPoint, originPoint[Y_COORD], (int) xPoint, (int) yPoint, axisPaint);
            canvas.drawRect(xPoint - barDrawingFactor, yPoint, xPoint + barDrawingFactor, (float) originPoint[Y_COORD],
                barPaint);

            ++count;
            ++next;
        }

        /*for (int next = dataRegion * X_AXIS_DIVISIONS; next < X_AXIS_DIVISIONS && next < yValues.size(); ++next) {
            System.out.println("NEXT: " + next);
            float xPoint = (float) (originPoint[X_COORD] + (next + 1) * xDivisionFactor);
            float yProportion = (float) (yValues.get(next) * Y_AXIS_DIVISIONS / nextTenMultiple);
            float yPoint = (float) (originPoint[Y_COORD] - yProportion * yDivisionFactor);
            //canvas.drawLine((int) xPoint, originPoint[Y_COORD], (int) xPoint, (int) yPoint, axisPaint);
            canvas.drawRect(xPoint - barDrawingFactor, yPoint, xPoint + barDrawingFactor, (float) originPoint[Y_COORD],
                barPaint);
        }*/
    }

    private void drawyAxisMarks(Canvas canvas) {
        maxValue = statisticData[selectedStatistic].getyMaxValue();
        nextTenMultiple = calculateNextTenMultiple();
        yDivisionFactor = calculateYDivisionFactor();

        for (int next = 1; next <= Y_AXIS_DIVISIONS; ++next) {
            double yPoint = originPoint[Y_COORD] - next * yDivisionFactor;
            canvas.drawLine(originPoint[X_COORD], (int) yPoint, originPoint[X_COORD] - 10, (int) yPoint, axisPaint);
        }
    }

    private double calculateYDivisionFactor() {
        return (double)(originPoint[Y_COORD] - yAxisMaxPoint[Y_COORD]) / Y_AXIS_DIVISIONS;
    }

    private int calculateNextTenMultiple() {
        return 10 * (int)(maxValue / 10 + 1);
    }

    private void drawxAxisMarks(Canvas canvas) {
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

        drawxAxisMarks(canvas);
        drawyAxisMarks(canvas);
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

    public void nextRegion() {
        if (X_AXIS_DIVISIONS * (dataRegion + 1) < statisticData[selectedStatistic].getyAxisValues().size()) {
            ++dataRegion;
        }

        invalidate();
    }

    public void resetRegion() {
    }

    public void previousRegion() {
        if (dataRegion > 0) {
            --dataRegion;
        }

        invalidate();
    }
}
