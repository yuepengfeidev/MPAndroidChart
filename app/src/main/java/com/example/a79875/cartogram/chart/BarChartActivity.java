package com.example.a79875.cartogram.chart;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.a79875.cartogram.R;
import com.example.a79875.cartogram.util.BarChartUtil;
import com.example.a79875.cartogram.util.RadarChartUtil;
import com.example.a79875.cartogram.view.CustomDialog;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.UUID;


public class BarChartActivity extends AppCompatActivity {
    private BarChart mBarChart;
    private Menu menu;
    private TextView mBarChartName;

    private MenuItem yDataItem;
    private MenuItem onlyAudiItem;// 只看奥迪菜单项
    private MenuItem onlyBenzItem;// 只看奔驰菜单项

    // 奥迪和奔驰两个条形
    private BarDataSet audiDataSet;
    private BarDataSet benzDataSet;

    private ArrayList<Integer> inputAudiSet;// 输入奥迪销量
    private ArrayList<Integer> inputBenzSet;//

    private BarData barData;// 条形图数据

    private YAxis yAxis;// y轴

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);

        initView();
    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Pie Chart");
        }

        mBarChartName = findViewById(R.id.tv_chart_name);
        mBarChart = findViewById(R.id.bar_chart);
        yAxis = mBarChart.getAxisLeft();
        BarChartUtil.drawBarChart(this,mBarChart);
        setBarChartData();
    }

    // 设置条形图数据
    private void setBarChartData() {
        if ((mBarChart.getData() != null) && (mBarChart.getData().getDataSetCount() > 0)) {
            // 获取数据集
            audiDataSet = (BarDataSet) mBarChart.getData().getDataSetByIndex(0);
            benzDataSet = (BarDataSet) mBarChart.getData().getDataSetByIndex(1);
            // 设置值
            audiDataSet.setValues(BarChartUtil.getYValues(true,null));
            benzDataSet.setValues(BarChartUtil.getYValues(false,null));
            // 通过数据更新
            mBarChart.getData().notifyDataChanged();
            mBarChart.notifyDataSetChanged();
        } else {
            audiDataSet = BarChartUtil.setBarChartData(this, "奥迪",
                    R.color.blueAudi, (ArrayList<BarEntry>) BarChartUtil.getYValues(true,null));
            benzDataSet = BarChartUtil.setBarChartData(this, "奔驰",
                    R.color.pinkBenz, (ArrayList<BarEntry>) BarChartUtil.getYValues(false,null));

            BarData barData = new BarData(audiDataSet, benzDataSet);
            // 不显示数据
            barData.setDrawValues(false);
            // 设置分柱
            float groupSpace = 0.3f; //柱状图组之间的间距
            float barWidth = 0.35f;
            float barSpace = 0f;
            barData.setBarWidth(barWidth);
            //(起始点、柱状图组间距、柱状图之间间距)
            barData.groupBars(2009f, groupSpace, barSpace);

            // 设置数据
            mBarChart.setData(barData);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        /* 加载菜单栏布局*/
        getMenuInflater().inflate(R.menu.menu_bar_chart, menu);
        return true;
    }

    // 菜单选项

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.show_y_data_bar:// 显示Y轴值
                if (yDataItem == null) {
                    yDataItem = menu.findItem(R.id.show_y_data_bar);// 加载显示y轴数据菜单项布局
                }
                audiDataSet.setDrawValues(!audiDataSet.isDrawValuesEnabled());// 设置奥迪数据是否可见
                benzDataSet.setDrawValues(!benzDataSet.isDrawValuesEnabled());
                if (audiDataSet.isDrawValuesEnabled()) {
                    yDataItem.setTitle("隐藏Y轴数据");
                } else {
                    yDataItem.setTitle("显示Y轴数据");
                }
                mBarChart.invalidate(); // 重绘条形图
                break;
            case R.id.only_see_audi_bar:// 只看奥迪
                if (onlyAudiItem == null) {
                    onlyAudiItem = menu.findItem(R.id.only_see_audi_bar);
                }
                audiDataSet.setVisible(true);
                benzDataSet.setVisible(false);
                mBarChart.invalidate();
                break;
            case R.id.only_see_benz_bar:// 只看奔驰
                if (onlyBenzItem == null) {
                    onlyBenzItem = menu.findItem(R.id.only_see_benz_bar);
                }
                audiDataSet.setVisible(false);
                benzDataSet.setVisible(true);
                mBarChart.invalidate();
                break;
            case R.id.see_all_bar:// 显示全部数据
                audiDataSet.setVisible(true);
                benzDataSet.setVisible(true);
                mBarChart.invalidate();
                break;
            case R.id.dynamic_data_bar:// 动态数据
                showSetDataDialog();
                break;
            case R.id.save_to_local_bar:// 保存到本地
                save();
                break;
            case android.R.id.home:// 显示左上角返回键
                onBackPressed();
                break;
            default:
                break;
        }
        return true;
    }

    // 显示自定义数据对话框
    private void showSetDataDialog() {
        final CustomDialog dialog = new CustomDialog(BarChartActivity.this, R.style.CustomDialog);
        dialog.setCancelable(false);
        dialog.setOnPositiveClickListener(v -> {
            inputAudiSet = dialog.getAudiData();// 获取自定义对话框的奥迪输入值
            inputBenzSet = dialog.getBenzData();
            if (inputAudiSet.size() < 10 || inputBenzSet.size() < 10) {
                // 震动权限
                Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                assert vibrator != null;// 断言： 等于null则退出，不等于则继续执行
                vibrator.vibrate(240);
                Toast.makeText(BarChartActivity.this, "请输入完整数据", Toast.LENGTH_SHORT).show();
            } else {
                setDynamicData();
                Toast.makeText(BarChartActivity.this, "数据更新完成", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        }).setOnNegativeClickListener(v -> dialog.dismiss()).show();
    }

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
        //获取自定义数据线条
        audiDataSet = BarChartUtil.setBarChartData(this,"奥迪",
                R.color.blueAudi, (ArrayList<BarEntry>) BarChartUtil.getYValues(true,audiValues));
        benzDataSet = BarChartUtil.setBarChartData(this,"奔驰",
                R.color.pinkBenz, (ArrayList<BarEntry>) BarChartUtil.getYValues(false,benzValues));
        barData = new BarData(audiDataSet,benzDataSet);
        barData.setDrawValues(false);// 不显示Y轴数据

        // 设置分柱
        float groupSpace = 0.3f; //柱状图组之间的间距
        float barWidth = 0.35f;
        float barSpace = 0f;
        barData.setBarWidth(barWidth);
        //(起始点、柱状图组间距、柱状图之间间距)
        barData.groupBars(2009, groupSpace, barSpace);

        mBarChart.setData(barData);// 给条形图添加自定义数据
        BarChartUtil.getTotalValues(audiValues,benzValues,yAxis);// 获取数据中最大值来修改Y轴坐标值
        // notifyDataSetChanged方法通过一个外部的方法控制如果适配器的内容改变时需要强制调用getView来刷新每个Item的内容。
        mBarChart.notifyDataSetChanged();// 少了则会只增加y轴的长度
        mBarChart.invalidate();// 重新绘制界面
        mBarChart.animateXY(1200,1200);// 设置动画
        // 自定义数据后重设菜单选项
        if (onlyAudiItem != null) {
            onlyAudiItem.setTitle("只看奥迪");
        }
        if (onlyBenzItem != null) {
            onlyBenzItem.setTitle("只看奔驰");
        }
        if (yDataItem != null){
            yDataItem.setTitle("显示Y轴值");
        }
    }

    // 自定义数组列表转换为数组
    private int[] arraylistToArray(ArrayList<Integer> list) {
        int[] toInt = new int[list.size()];
        for (int i = 0; i < toInt.length; i++) {
            toInt[i] = list.get(i);
        }
        return toInt;
    }

    private void save() {
        if (ContextCompat.checkSelfPermission(BarChartActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // 获取权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
        } else {
            if (mBarChart.saveToGallery(mBarChartName.getText().toString() + UUID.randomUUID(), 100)) {
                Toast.makeText(BarChartActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(BarChartActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (mBarChart.saveToGallery(mBarChartName.getText().toString() + UUID.randomUUID(), 100)) {
                        Toast.makeText(BarChartActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(BarChartActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(BarChartActivity.this, "没有获取权限", Toast.LENGTH_SHORT).show();
                }
        }
    }
}
