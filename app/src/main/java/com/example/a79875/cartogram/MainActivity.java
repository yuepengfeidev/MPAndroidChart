package com.example.a79875.cartogram;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.a79875.cartogram.adapter.ChartAdapter;
import com.example.a79875.cartogram.entity.Chart;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    private Chart[] charts = {
            new Chart(R.drawable.ic_line_chart_icon,
                    "Line Chart",
            "一个简单展示两款汽车销售量的折线图")
            ,new Chart(R.drawable.ic_radar_chart,
            "Radar Chart",
            "一个简单展示两款汽车销售量的雷达图")
            ,new Chart(R.drawable.ic_bar_chart_icon,
            "Bar Chart",
            "一个简单展示两款汽车销售量的条形图")
            ,new Chart(R.drawable.ic_pie_chart_icon,
            "Pie Chart",
            "一个简单展示两款汽车销售量的饼状图")
    };

    private ArrayList<Chart> chartArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initData();
    }

    /*初始化数据*/
    private void initData() {
        chartArrayList.addAll(Arrays.asList(charts));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ChartAdapter mChartAdapter = new ChartAdapter(this,chartArrayList);
        mRecyclerView.setAdapter(mChartAdapter);

    }

    /*初始化视图*/
    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mRecyclerView = findViewById(R.id.recycler_view);
    }
}
