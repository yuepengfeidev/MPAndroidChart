package com.example.a79875.cartogram.util;

import android.content.Context;
import android.graphics.Color;

import com.example.a79875.cartogram.R;
import com.example.a79875.cartogram.view.CustomMarkerView;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class PieChartUtil {

    /* 模拟奥迪销量*/
    public static int[] audiValues = {34, 20, 18, 36, 11, 24, 12, 38, 30, 27};

    /* 模拟奔驰销量*/
    public static int[] benzValues = {10, 15, 28, 16, 17, 34, 21, 15, 24, 32};

    // 获得设置进饼图的数据
    public static ArrayList<PieEntry> getData(int year, int[] audiInput, int[] benzInput){
        ArrayList<PieEntry> entries = new ArrayList<>();
        if (audiInput == null && benzInput == null){// 输入为空则返回默认模拟值
            PieEntry audi = new PieEntry(audiValues[year - 2009], "奥迪");// PieEntry中的label使饼块上显示奥迪标签
            PieEntry benz = new PieEntry(benzValues[year - 2009], "奔驰");
            entries.add(audi);
            entries.add(benz);
        }else {
            PieEntry audi = new PieEntry(audiInput[year - 2009], "奥迪");
            PieEntry benz = new PieEntry(benzInput[year - 2009], "奔驰");
            entries.add(audi);
            entries.add(benz);
        }
        return entries;
    }


    // 设置饼图数据
    public static PieDataSet setPieChartData(ArrayList<Integer> pieColorID,
                                            String label, ArrayList<PieEntry> entries){
        PieDataSet pieDataSet = new PieDataSet(entries,label);
        // 设置饼图之间的间隙
        pieDataSet.setSliceSpace(1f);
        // 设置饼图中各块的颜色
        pieDataSet.setColors(pieColorID);
        // 显示百分比样式
        pieDataSet.setDrawValues(true);
        // 设置饼块选中后偏离饼图中心的距离
        pieDataSet.setSelectionShift(5f);

        return pieDataSet;
    }

    // 绘制饼图
    public static void drawPieChart(Context mContext, PieChart pieChart, String title){
        //设置高光效果
        pieChart.needsHighlight(50);
        //设置内部圆的半径
        pieChart.setHoleRadius(50f);
        //设置图标文本字体大小
        pieChart.setEntryLabelTextSize(18f);
        // 设置使用百分比
        pieChart.setUsePercentValues(true);
        // 禁止描述
        pieChart.getDescription().setEnabled(false);
        // 设置边距
        /*pieChart.setExtraOffsets(25,10,25,25);*/
        // 设置摩擦系数（值越小摩擦系数越大）
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        // 设置环中文字
        pieChart.setCenterText(title);
        // 设置是否可以旋转
        pieChart.setRotationEnabled(true);
        // 设置点击放大
        pieChart.setHighlightPerTapEnabled(true);
        // 设置环中文字的大小
        pieChart.setCenterTextSize(24f);
        // 设置绘制环中文字
        pieChart.setDrawCenterText(true);
        // 设置旋转角度
        pieChart.setRotationAngle(120f);
        // 设置半透明内环
        pieChart.setTransparentCircleRadius(61f);
        // 这个方法为true就是环形图，为false就是饼图
        pieChart.setDrawHoleEnabled(true);
        // 设置环形中空白颜色时白色
        pieChart.setHoleColor(Color.parseColor("#ffffff"));
        // 设置半透明圆环的颜色
        pieChart.setTransparentCircleColor(Color.parseColor("#ffffff"));
        // 设置半透明圆环的透明度
        pieChart.setTransparentCircleAlpha(110);
        // 设置点击一个点显示一个值的对话框
        pieChart.setMarker(new CustomMarkerView(mContext, R.layout.custom_marker_view));
        // 设置动画
        pieChart.animateXY(1200,1200);

        Legend legend = pieChart.getLegend();
        // 设置图例
        legend.setEnabled(true);
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
