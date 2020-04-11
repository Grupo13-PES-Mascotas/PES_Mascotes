package org.pesmypetcare.mypetcare.activities.views.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.activities.views.chart.statisticdata.StatisticData;
import org.pesmypetcare.mypetcare.activities.views.chart.statisticdata.StubStatisticData;
import org.pesmypetcare.mypetcare.features.pets.Pet;

import java.util.List;

public class BarChart extends View {
    private static final int BORDER_X = 125;
    private static final int BORDER_Y = 75;
    private static final int X_COORD = 0;
    private static final int Y_COORD = 1;
    private static final int CHART_SIZE = 275;
    private static final int X_AXIS_DIVISIONS = 4;
    private static final int Y_AXIS_DIVISIONS = 5;
    private static final int BAR_PROPORTION = 4;
    private static final int TEXT_SIZE = 24;
    private static final int TEXT_SEPARATOR = 40;
    private static final int TEN = 10;

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
    private Paint textPaint;
    private int selectedStatistic;
    private int dataRegion;
    private StatisticData[] statisticData;

    public BarChart(Context context, @Nullable AttributeSet attrs, Pet pet) {
        super(context, attrs);
        initDrawComponents();

        statisticData = new StatisticData[] {
            new StubStatisticData()
        };
    }

    private void initDrawComponents() {
        axisPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        axisPaint.setColor(Color.BLACK);
        axisPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        barPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        barPaint.setColor(getResources().getColor(R.color.colorPrimary));
        barPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.BLACK);
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint.setTextSize(TEXT_SIZE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawAxis(canvas);
        drawBars(canvas);
    }

    private void drawBars(Canvas canvas) {
        List<String> xValues = statisticData[selectedStatistic].getxAxisValues();
        List<Double> yValues = statisticData[selectedStatistic].getyAxisValues();
        float barDrawingFactor = (float) (xDivisionFactor / BAR_PROPORTION);

        int next = dataRegion * X_AXIS_DIVISIONS;
        int count = 0;

        while (hasValuesRemaining(yValues, next, count)) {
            float xPoint = (float) (originPoint[X_COORD] + (count + 1) * xDivisionFactor);
            float yProportion = (float) (yValues.get(next) * Y_AXIS_DIVISIONS / nextTenMultiple);
            float yPoint = (float) (originPoint[Y_COORD] - yProportion * yDivisionFactor);
            canvas.drawRect(xPoint - barDrawingFactor, yPoint, xPoint + barDrawingFactor, (float) originPoint[Y_COORD],
                barPaint);

            float textWidth = textPaint.measureText(xValues.get(count));
            canvas.drawText(xValues.get(count), xPoint - textWidth / 2, originPoint[Y_COORD] + TEXT_SEPARATOR,
                textPaint);

            ++count;
            ++next;
        }
    }

    private boolean hasValuesRemaining(List<Double> yValues, int next, int count) {
        return count < X_AXIS_DIVISIONS && next < yValues.size();
    }

    private void drawYaxis(Canvas canvas) {
        canvas.drawLine(yAxisMaxPoint[X_COORD], yAxisMaxPoint[Y_COORD], originPoint[X_COORD], originPoint[Y_COORD],
            axisPaint);

        maxValue = statisticData[selectedStatistic].getyMaxValue();
        nextTenMultiple = calculateNextTenMultiple();
        yDivisionFactor = calculateYDivisionFactor();

        for (int next = 1; next <= Y_AXIS_DIVISIONS; ++next) {
            float yPoint = drawYmark(canvas, next);
            drawYmarkText(canvas, next, yPoint);
        }

        String unit = statisticData[selectedStatistic].getUnit();
        float textWith = textPaint.measureText(unit);
        canvas.drawText(unit, yAxisMaxPoint[X_COORD] - textWith - TEXT_SEPARATOR,
            yAxisMaxPoint[Y_COORD] - TEXT_SEPARATOR, textPaint);
    }

    private void drawYmarkText(Canvas canvas, int next, float yPoint) {
        int markValue = (int)(next * nextTenMultiple / Y_AXIS_DIVISIONS);
        String markText = String.valueOf(markValue);
        float textWidth = textPaint.measureText(markText);
        Rect textBounds = new Rect();
        textPaint.getTextBounds(markText, 0, markText.length(), textBounds);
        float height = Math.abs(textBounds.top - textBounds.bottom);
        canvas.drawText(markText, originPoint[X_COORD] - textWidth - TEXT_SEPARATOR, yPoint + height / 2, textPaint);
    }

    private float drawYmark(Canvas canvas, int next) {
        float yPoint = (float) (originPoint[Y_COORD] - next * yDivisionFactor);
        canvas.drawLine(originPoint[X_COORD], (int) yPoint, originPoint[X_COORD] - TEN, (int) yPoint, axisPaint);
        return yPoint;
    }

    private double calculateYDivisionFactor() {
        return (double)(originPoint[Y_COORD] - yAxisMaxPoint[Y_COORD]) / Y_AXIS_DIVISIONS;
    }

    private int calculateNextTenMultiple() {
        return TEN * (int)(maxValue / TEN + 1);
    }

    private void drawXaxis(Canvas canvas) {
        canvas.drawLine(originPoint[X_COORD], originPoint[Y_COORD], xAxisMaxPoint[X_COORD], xAxisMaxPoint[Y_COORD],
            axisPaint);

        xDivisionFactor = calculateXDivisionFactor();

        for (int next = 1; next <= X_AXIS_DIVISIONS; ++next) {
            double xPoint = originPoint[X_COORD] + next * xDivisionFactor;
            canvas.drawLine((int) xPoint, originPoint[Y_COORD], (int) xPoint, originPoint[Y_COORD] + TEN, axisPaint);
        }
    }

    private double calculateXDivisionFactor() {
        return ((double)(getWidth() - 2 * BORDER_Y)) / (X_AXIS_DIVISIONS + 2);
    }

    private void drawAxis(Canvas canvas) {
        calculateAxisPoints();
        drawXaxis(canvas);
        drawYaxis(canvas);
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
        selectedStatistic = statisticId;
        dataRegion = 0;
        invalidate();
    }

    public void nextRegion() {
        if (X_AXIS_DIVISIONS * (dataRegion + 1) < statisticData[selectedStatistic].getyAxisValues().size()) {
            ++dataRegion;
        }

        invalidate();
    }

    public void previousRegion() {
        if (dataRegion > 0) {
            --dataRegion;
        }

        invalidate();
    }
}
