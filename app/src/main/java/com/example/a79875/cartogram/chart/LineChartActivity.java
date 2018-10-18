package com.example.a79875.cartogram.chart;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.a79875.cartogram.R;
import com.example.a79875.cartogram.util.LineChartUtil;
import com.example.a79875.cartogram.view.CustomDialog;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.UUID;

public class LineChartActivity extends AppCompatActivity {

    private LineChart mlineChart;

    private Menu menu;

    private TextView mLineChartName;// 折线图名称

    private MenuItem onlyAudiItem;// 只看奥迪菜单项
    private MenuItem onlyBenzItem;// 只看奔驰菜单项
    private MenuItem verticesItem;// 显示顶点值菜单项
    private MenuItem circleItem;// 显示圆点菜单项

    /* 三条折现（奥迪销量，奔驰销量，总销售量）*/
    private LineDataSet audiDataSet;
    private LineDataSet benzDataSet;
    private LineDataSet totalDataSet;

    private ArrayList<Integer> inputAudiSet;// 输入奥迪销量
    private ArrayList<Integer> inputBenzSet;// 输入奔驰销量

    private LineData lineData;// 折线图数据

    private YAxis yAxis;// Y轴



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);

        initView();
    }

    /* 初始化视图*/
    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            /* 显示返回键*/
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Line Chart");
        }

        mLineChartName = findViewById(R.id.tv_chart_name);
        mlineChart = findViewById(R.id.line_chart);

        yAxis = mlineChart.getAxisLeft();// 获取折线图的Y轴

        LineChartUtil.drawLineChart(this,mlineChart,yAxis);// 绘制雷达图

        setLineChartData();

    }

    /* 设置图表数据*/
    private void setLineChartData() {

        if ((mlineChart.getData() != null) && (mlineChart.getData().getDataSetCount() > 0)) {// 如果图表中有数据
            audiDataSet = (LineDataSet) mlineChart.getData().getDataSetByIndex(0);// 获取图表中奥迪这条折线
            benzDataSet = (LineDataSet) mlineChart.getData().getDataSetByIndex(1);
            /* 设置值*/
            audiDataSet.setValues(LineChartUtil.getYValues(true, null));// 为奥迪这条折线设置默认数据
            benzDataSet.setValues(LineChartUtil.getYValues(false, null));
            totalDataSet.setValues(LineChartUtil.getTotalYValues(null, null, null));
            /* 更新数据*/
            mlineChart.getData().notifyDataChanged();
            mlineChart.notifyDataSetChanged();
        } else {// 如果图表中没有数据，则直接获取加载了默认数据的折线进行绘制图表
            audiDataSet = LineChartUtil.setLineChartData(this, "奥迪",
                    R.color.blueAudi, LineChartUtil.getYValues(true, null));
            benzDataSet = LineChartUtil.setLineChartData(this, "奔驰",
                    R.color.pinkBenz, LineChartUtil.getYValues(false, null));
            totalDataSet = LineChartUtil.setLineChartData(this, "总销量",
                    R.color.colorPrimaryDark, LineChartUtil.getTotalYValues(null, null, null));

            lineData = new LineData(audiDataSet, benzDataSet, totalDataSet);
            /* 不显示顶点值*/
            lineData.setDrawValues(false);
            /* 设置数据*/
            mlineChart.setData(lineData);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        /* 加载菜单栏布局*/
        getMenuInflater().inflate(R.menu.menu_line_chart, menu);
        return true;
    }

    // 菜单选项
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.show_vertices_data:// 显示顶点值
                if (verticesItem == null) {// 获取item布局
                    verticesItem = menu.findItem(R.id.show_vertices_data);
                }
                audiDataSet.setDrawValues(!audiDataSet.isDrawValuesEnabled());
                benzDataSet.setDrawValues(!benzDataSet.isDrawValuesEnabled());
                totalDataSet.setDrawValues(!totalDataSet.isDrawValuesEnabled());
                /*  重新绘制统计图*/
                mlineChart.invalidate();

                if (audiDataSet.isDrawValuesEnabled()) {
                    verticesItem.setTitle(getString(R.string.hint_vertices_values));
                } else {
                    verticesItem.setTitle("显示顶点值");
                }
                break;
            case R.id.show_circle:// 显示圆点
                if (circleItem == null) {
                    circleItem = menu.findItem(R.id.show_circle);
                }
                audiDataSet.setDrawCircles(!audiDataSet.isDrawCirclesEnabled());
                benzDataSet.setDrawCircles(!audiDataSet.isDrawCirclesEnabled());
                totalDataSet.setDrawCircles(!totalDataSet.isDrawCirclesEnabled());
                mlineChart.invalidate();

                if (audiDataSet.isDrawCirclesEnabled()) {
                    circleItem.setTitle("隐藏圆点");
                } else {
                    circleItem.setTitle("显示圆点");
                }
                break;
            case R.id.dynamic_data:// 动态数据
                showSetDataDialog();
                break;
            case R.id.only_see_audi:// 只看奥迪
                if (onlyAudiItem == null) {
                    onlyAudiItem = menu.findItem(R.id.only_see_audi);
                }
                audiDataSet.setVisible(true);
                benzDataSet.setVisible(false);
                totalDataSet.setVisible(false);
                mlineChart.invalidate();
                break;
            case R.id.only_see_benz:// 只看奔驰
                if (onlyBenzItem == null) {
                    onlyBenzItem = menu.findItem(R.id.only_see_benz);
                }
                audiDataSet.setVisible(false);
                benzDataSet.setVisible(true);
                totalDataSet.setVisible(false);
                mlineChart.invalidate();
                break;
            case R.id.all_data:// 显示全数据
                audiDataSet.setVisible(true);
                benzDataSet.setVisible(true);
                totalDataSet.setVisible(true);
                mlineChart.invalidate();
                break;
            case R.id.save_to_local:// 保存到本地
                save();
                break;
            case android.R.id.home:
                onBackPressed();// 左上角返回按钮
                break;
            default:
                break;
        }
        return true;
    }

    /* 打开数据对话框*/
    private void showSetDataDialog() {
        final CustomDialog dialog = new CustomDialog(LineChartActivity.this, R.style.CustomDialog);
        dialog.setCancelable(false);
        dialog.setOnPositiveClickListener(v -> {
            inputAudiSet = dialog.getAudiData();// 获取数据
            inputBenzSet = dialog.getBenzData();
            /* 判断输入是否完整*/
            if (inputBenzSet.size() < 10 || inputAudiSet.size() < 10) {
                // 震动
                Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                assert vibrator != null;
                vibrator.vibrate(240);
                Toast.makeText(LineChartActivity.this, "请输入完整数据", Toast.LENGTH_SHORT).show();
            } else {
                setDynamicData();
                Toast.makeText(LineChartActivity.this, "更新数据完成", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        }).setOnNegativeClickListener(v -> dialog.dismiss()).show();
    }

    /*  设置动态数据*/
    private void setDynamicData() {
        int[] audiValues;// 奥迪数据
        int[] benzValues;// 奔驰数据

        /* 判断API是否大于24，大于则调用自带的转换数组方法*/
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            audiValues = inputAudiSet.stream().mapToInt(Integer::intValue).toArray();
            benzValues = inputBenzSet.stream().mapToInt(Integer::intValue).toArray();// 输入的数据的数组列表转化为数组
        } else { // 否则调用自定义的转换数组方法
            audiValues = arraylistToArray(inputAudiSet);
            benzValues = arraylistToArray(inputBenzSet);
        }
        audiDataSet = LineChartUtil.setLineChartData(this, "奥迪",
                R.color.blueAudi, LineChartUtil.getYValues(true, audiValues));
        benzDataSet = LineChartUtil.setLineChartData(this, "奔驰",
                R.color.pinkBenz, LineChartUtil.getYValues(false, benzValues));
        totalDataSet = LineChartUtil.setLineChartData(this, "总销量",
                R.color.colorPrimaryDark, LineChartUtil.getTotalYValues(audiValues, benzValues, yAxis));
        lineData = new LineData(audiDataSet, benzDataSet, totalDataSet);// 折线图数据
        lineData.setDrawValues(false);// 不显示数据
        /* setData(): 为图表设置一个新的数据对象。数据对象包含所有值*/
        mlineChart.setData(lineData);// 将数据加入折线图
        mlineChart.invalidate();// 重新绘制折线图
        /* 设置直线图动画效果，伸展宽（X）高（Y）的事件均为1200ms */
        mlineChart.animateXY(1200, 1200);// 设置动画效果
        /* 重新设置菜单标题栏*/
        if (onlyAudiItem != null) {
            onlyAudiItem.setTitle("只看奥迪");
        }
        if (onlyBenzItem != null) {
            onlyBenzItem.setTitle("只看奔驰");
        }
        if (verticesItem != null) {
            verticesItem.setTitle("显示顶点值");
        }
        if (circleItem != null) {
            circleItem.setTitle("显示圆点");
        }
    }

    private int[] arraylistToArray(ArrayList<Integer> list) {
        int[] toInt = new int[list.size()];
        for (int i = 0; i < toInt.length; i++) {
            toInt[i] = list.get(i);
        }
        return toInt;
    }

    /* 保存图片到本地*/
    private void save() {
        if (ContextCompat.checkSelfPermission(LineChartActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // 申请获取权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1
            );
        } else {
            if (mlineChart.saveToGallery(mLineChartName.getText().toString() + UUID.randomUUID(),
                    100)) {
                Toast.makeText(LineChartActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(LineChartActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /* 回调方法*/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (mlineChart.saveToGallery(mLineChartName.getText().toString() + UUID.randomUUID(),
                            100)) {
                        Toast.makeText(LineChartActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LineChartActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LineChartActivity.this, "无法获取读取权限", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                break;
        }
    }
}
