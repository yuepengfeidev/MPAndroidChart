package com.example.a79875.cartogram.util;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.example.a79875.cartogram.R;
import com.example.a79875.cartogram.view.CustomMarkerView;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RadarChartUtil {
    //用于存放X坐标值
    private static List<String> xCoordinateData = new ArrayList<>();

    /* 模拟奥迪销量*/
    private static int[] audiValues = {34, 20, 18, 36, 11, 24, 12, 38, 30, 27};

    /* 模拟奔驰销量*/
    private static int[] benzValues = {10, 15, 28, 16, 17, 34, 21, 15, 24, 32};

    // 获取Y轴的值
    public static List<RadarEntry> getYValues(Boolean isAdui, int[] fromInput) {
        List<RadarEntry> entries = new ArrayList<>();
        int index = 0;
        // 默认数据获取
        if (fromInput == null) {
            if (isAdui) {// 奥迪
                for (int i = 2009; i <= 2018; i++) {
                    entries.add(new RadarEntry(audiValues[index++], i));
                }
            } else {// 奔驰
                for (int i = 2009; i <= 2018; i++) {
                    entries.add(new RadarEntry(benzValues[index++], i));
                }
            }
        } else {// 输入数据获取
            for (int i = 2009; i <= 2018; i++) {
                entries.add(new RadarEntry(fromInput[index++], i));
            }
        }
        return entries;
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

    // 用于设置每条线的数据并返回
    public static RadarDataSet setRadarChart(Context mContext, String label, int radarColor, ArrayList<RadarEntry> entries) {
        RadarDataSet radarDataSet = new RadarDataSet(entries, label);

        // 设置数据线条宽度
        radarDataSet.setLineWidth(2f);
        // 设置线条颜色
        radarDataSet.setColor(ContextCompat.getColor(mContext, radarColor));
        // 设置每环数据线的填充色
        radarDataSet.setFillColor(ContextCompat.getColor(mContext, radarColor));
        // 设置每环数据线的填充色透明度
        radarDataSet.setFillAlpha(100);

        return radarDataSet;
    }

    // 绘制雷达图
    public static void drawRadarChart(Context mContext, RadarChart mRadarChart, YAxis yAxis) {
        // 禁止描述
        mRadarChart.getDescription().setEnabled(false);
        // 竖直网格线条的宽度
        mRadarChart.setWebLineWidth(1.5f);
        // 环形网格线条的宽度
        mRadarChart.setWebLineWidthInner(1.5f);
        // 所有线条的透明度
        mRadarChart.setWebAlpha(100);
        //点击显示数据对话框
        mRadarChart.setMarker(new CustomMarkerView(mContext, R.layout.custom_marker_view));
        // 设置动画
        mRadarChart.animateXY(1200, 1200);

        XAxis xAxis = mRadarChart.getXAxis();
        // X坐标值设置字体大小
        xAxis.setTextSize(12f);
        for (int i = 2009; i <= 2018; i++) {// x坐标值存放
            xCoordinateData.add(String.valueOf(i));
        }
        // 获得x轴的数据
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xCoordinateData.get((int) value % xCoordinateData.size());
            }
        });

        // 设置Y轴坐标个数为5个，
        yAxis.setLabelCount(5, true);// 为true时y轴最大值为自定义的值，false为默认最大值
        // 设置Y坐标字体大小
        yAxis.setTextSize(15f);
        // 设置Y坐标的最大值和最小值
       yAxis.setAxisMaximum(40f);
        yAxis.setAxisMinimum(0f);

        Legend legend = mRadarChart.getLegend();
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
        // 设置图例的形式为正方形
        legend.setForm(Legend.LegendForm.SQUARE);
        // 设置图例文本字体大小
        legend.setTextSize(12f);
    }
}
