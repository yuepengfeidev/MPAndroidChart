package com.example.a79875.cartogram.util;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.example.a79875.cartogram.R;
import com.example.a79875.cartogram.view.CustomMarkerView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;

import org.w3c.dom.Entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class LineChartUtil {

    /* 模拟奥迪销量*/
    private static int[] audiValues = {16, 20, 18, 25, 28, 24, 28, 27, 30, 27};

    /* 模拟奔驰销量*/
    private static int[] benzValues = {10, 15, 13, 16, 15, 19, 21, 23, 24, 27};

    /* 获取Y轴的值*/
    public static ArrayList<Entry> getYValues(boolean isAudi, int[] fromInput) {
        ArrayList<Entry> entries = new ArrayList<>();
        int index = 0;
        /* 默认数据*/
        if (fromInput == null) {
            if (isAudi) {
                for (int i = 2009; i <= 2018; i++) {
                    entries.add(new Entry(i, audiValues[index++]));
                }
            } else {
                for (int i = 2009; i <= 2018; i++) {
                    entries.add(new Entry(i, benzValues[index++]));
                }
            }
        } else {
            /* 手动更新数据*/
            for (int i = 2009; i <= 2018; i++) {
                entries.add(new Entry(i, fromInput[index++]));
            }
        }
        return entries;
    }

    /* 计算并获取总销量Y轴数据*/
    public static ArrayList<Entry> getTotalYValues(int[] fromInputAudi, int[] fromInputBenz, YAxis yAxis) {
        ArrayList<Entry> entries = new ArrayList<>();// 用来添加默认合计数据
        ArrayList<Integer> values = new ArrayList<>();// 用来记录自定义数据总销量，判断是否超过Y轴最大值
        int index = 0;
        /* 默认数据*/
        if (fromInputAudi == null && fromInputBenz == null) {
            for (int i = 2009; i <= 2018; i++) {
                entries.add(new Entry(i, audiValues[index] + benzValues[index]));
                index++;
            }
        } else {/* 手动添加数据*/
            for (int i = 2009; i <= 2018; i++) {
                assert fromInputAudi != null;//assert：断言  如果不为空继续执行，为空则结束程序
                entries.add(new Entry(i, fromInputAudi[index] + fromInputBenz[index]));
                values.add(fromInputAudi[index] + fromInputBenz[index]);
                index++;
            }

            /* 若总销量超过Y轴最大标准值60，则重新设置最大值*/
            int max = Collections.max(values);// 搜索集合中的最大元素
            if (max > 60) {
                yAxis.setAxisMaximum((max + 10) / 10 * 10);// 重新设置Y轴最大值
            }
        }
        return entries;
    }

    /* 设置折线图数据*/
    public static LineDataSet setLineChartData(Context context, String label,
                                          int lineColorId, ArrayList<Entry> entries) {
        LineDataSet lineDataSet = new LineDataSet(entries, label);
        /* 设置数据依赖左侧Y轴*/
        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        /* 设置折线颜色*/
        lineDataSet.setColor(ContextCompat.getColor(context, lineColorId));
        /* 不显示圆点*/
        lineDataSet.setDrawCircles(false);
        /* 设置折线宽度*/
        lineDataSet.setLineWidth(1.6f);
        /* 格式化字符串*/
   /* Lamda表达式添加compileOptions{
        sourceCompatibility 1.8
        targetCompatibility 1.8
    }*/
        lineDataSet.setValueFormatter((value, entry, dataSetIndex, viewPortHandler) -> String.valueOf((int) value));
        /* 设置值的文本大小*/
        lineDataSet.setValueTextSize(12);
        /* 禁止显示十字交叉纵横线*/
        lineDataSet.setDrawHighlightIndicators(false);

        return lineDataSet;
    }

    /* 绘制折线图*/
    public static void drawLineChart(Context mContext, LineChart mLineChart, YAxis yAxis){
        /* 设置比列缩放*/
        mLineChart.setPinchZoom(true);
        /* 设置无数据时显示文字*/
        mLineChart.setNoDataText("暂无数据");
        /* 禁止缩放*/
        mLineChart.setScaleEnabled(false);
        /* 禁止描述*/
        mLineChart.getDescription().setEnabled(false);
        /* 设置点击一个点显示一个值的对话框*/
        mLineChart.setMarker(new CustomMarkerView(mContext, R.layout.custom_marker_view));
        /* 设置动画*/
        mLineChart.animateXY(1200,1200);

        /* X轴坐标*/
        final XAxis xAxis  = mLineChart.getXAxis();
        // 设置X轴位置在底部
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        // X轴显示网格线
        xAxis.setDrawGridLines(true);
        // 设置X轴的最小间距
        xAxis.setGranularity(1f);
        //  设置X轴的最大、最小值（自动分配刻度显示）
        xAxis.setAxisMaximum(2018f);
        xAxis.setAxisMinimum(2009f);
        xAxis.setLabelCount(10);
        // 格式化X轴的值为字符串
        xAxis.setValueFormatter((value, axis) -> String.valueOf((int) value));

        // 显示Y轴网格线
        yAxis.setDrawGridLines(true);
        // 设置Y轴最大、最小值（自动分配刻度显示）
        yAxis.setAxisMaximum(60);
        yAxis.setAxisMinimum(0);
        // 禁止显示右侧Y轴
        mLineChart.getAxisRight().setEnabled(false);

        // 图例设置
        Legend legend = mLineChart.getLegend();
        // 设置水平居右对其
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        // 设置垂直居上对其
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        // 设置图例布局为垂直布局
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        // 设置图例在图表外部绘制
        legend.setDrawInside(false);
        // 设置图例的文本方向
        legend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
        // 设置图例的形式为水平线条
        legend.setForm(Legend.LegendForm.SQUARE);
        // 设置图例文本字体大小
        legend.setTextSize(12f);
    }


}
