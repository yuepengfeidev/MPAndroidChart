package com.example.a79875.cartogram.util;

import android.content.Context;
import android.service.autofill.FieldClassification;
import android.support.v4.content.ContextCompat;

import com.example.a79875.cartogram.R;
import com.example.a79875.cartogram.view.CustomMarkerView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.RadarEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BarChartUtil {
    /* 模拟奥迪销量*/
    private static int[] audiValues = {34, 20, 18, 36, 11, 24, 12, 38, 30, 27};

    /* 模拟奔驰销量*/
    private static int[] benzValues = {10, 15, 28, 16, 17, 34, 21, 15, 24, 32};

    // 获取Y轴的值
    public static List<BarEntry> getYValues(Boolean isAdui, int[] fromInput) {
        List<BarEntry> entries = new ArrayList<>();
        int index = 0;
        // 默认数据获取
        if (fromInput == null) {
            if (isAdui) {// 奥迪
                for (int i = 2009; i <= 2018; i++) {
                    entries.add(new BarEntry(i, audiValues[index++]));
                }
            } else {// 奔驰
                for (int i = 2009; i <= 2018; i++) {
                    entries.add(new BarEntry(i, benzValues[index++]));
                }
            }
        } else {// 输入数据获取
            for (int i = 2009; i <= 2018; i++) {
                entries.add(new BarEntry(i, fromInput[index++]));
            }
        }
        return entries;
    }
    public static BarDataSet setBarChartData(Context mContext, String label,
                                             int BarColorID, ArrayList<BarEntry> entries) {

        BarDataSet BarDataSet = new BarDataSet(entries, label);
        // 设置数据依赖左侧Y轴
        BarDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        // 设置柱子颜色
        BarDataSet.setColor(ContextCompat.getColor(mContext, BarColorID));
        // 格式化值为字符串
        BarDataSet.setValueFormatter((value, entry, dataSetIndex, viewPortHandler) -> String.valueOf((int) value));
        // 设置值的文本大小
        BarDataSet.setValueTextSize(12);

        return BarDataSet;
    }

    // 获取所有数据中的最大值
    public static void getTotalValues(int[] audiInputData, int[] benzInputData, YAxis yAxis) {
        ArrayList<Integer> values = new ArrayList<>();
        int index = 0;

        for (int i = 2009; i <= 2018; i++) {// 存入所有自定义数据
            values.add(audiInputData[index]);
            values.add(benzInputData[index]);
            index++;
        }
        int max = Collections.max(values);// 找出数据中的最大值
        yAxis.setAxisMaximum((max + 10) / 10 * 10);

    }

    public static void drawBarChart(Context mContext, BarChart mBarChart){
        // 按比例缩放
        mBarChart.setPinchZoom(true);
        // 无数据时显示文字
        mBarChart.setNoDataText("暂无数据");
        // 允许缩放
        mBarChart.setScaleEnabled(true);
        // 禁止描述
        mBarChart.getDescription().setEnabled(false);
        /* 设置点击一个点显示一个值的对话框*/
        mBarChart.setMarker(new CustomMarkerView(mContext, R.layout.custom_marker_view));

        // 加载动画
        mBarChart.animateXY(1200,1200);

        XAxis xAxis = mBarChart.getXAxis();
        // 设置x坐标值在底部
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        // 设置x轴最小间距
        xAxis.setGranularity(1f);
        // 设置x坐标值的最大值和最小值
        xAxis.setAxisMaximum(2009f);
        xAxis.setAxisMaximum(2018f);
        xAxis.setLabelCount(10,false);// 设置坐标值为10个
        // 格式化x轴值为字符串
        xAxis.setValueFormatter(((value, axis) -> String.valueOf((int)value)));

        // 左侧y轴
        YAxis yAxis = mBarChart.getAxisLeft();
        // 设置y轴的最大值和最小值
        yAxis.setAxisMaximum(40);
        yAxis.setAxisMinimum(0);
        // 禁止显示右侧y轴
        mBarChart.getAxisRight().setEnabled(false);

        Legend legend = mBarChart.getLegend();
        // 设置水平居左对其
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        // 设置水平居下对其
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        // 设置布局为水平布局
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        // 设置图例在图表外显示
        legend.setDrawInside(false);
        // 设置图例的文本方向
        legend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
        // 设置图例的形式为正方形
        legend.setForm(Legend.LegendForm.SQUARE);
        // 设置图标大小
        legend.setTextSize(12f);


    }
}
