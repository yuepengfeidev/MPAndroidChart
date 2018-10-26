package com.example.a79875.cartogram.chart;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.a79875.cartogram.R;
import com.example.a79875.cartogram.util.PieChartUtil;
import com.example.a79875.cartogram.view.CustomDialog;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.UUID;

import javax.xml.transform.Source;

import static com.example.a79875.cartogram.util.PieChartUtil.drawPieChart;
import static com.example.a79875.cartogram.util.PieChartUtil.getData;

public class PieChartActivity extends AppCompatActivity {
    private MenuItem percentageItem;
    private Button lastYear;
    private Button nextYear;
    private Menu menu;
    private TextView mPieChartName;
    private ArrayList<Integer> inputAudiSet;// 输入奥迪销量
    private ArrayList<Integer> inputBenzSet;// 输入奔驰销量
    private PieChart mPieChart;
    private int year = 2009;

    private ArrayList<PieEntry> entries;
    private PieDataSet pieDataSet;
    private ArrayList<Integer> pieColorID = new ArrayList<>();
    private PieData pieData;

    private int[] audiValues;// 奥迪数据
    private int[] benzValues;// 奔驰数据

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);

        initView();
    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar4);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Pie Chart");
        }
        mPieChartName = findViewById(R.id.tv_chart_name);
        mPieChart = findViewById(R.id.pie_chart);
        lastYear = findViewById(R.id.last_year);
        nextYear = findViewById(R.id.next_year);

        pieColorID.add(ContextCompat.getColor(this,R.color.blueAudi));
        pieColorID.add(ContextCompat.getColor(this,R.color.pinkBenz));

        drawPieChart(this, mPieChart, "2008年");// 绘制饼状图
        setPieChartData();

        if (year == 2009) {
            lastYear.setVisibility(View.GONE);
        }else {
            lastYear.setVisibility(View.VISIBLE);
        }
        if (year == 2018){
            nextYear.setVisibility(View.GONE);
        }else {
            nextYear.setVisibility(View.VISIBLE);
        }

        lastYear.setOnClickListener(v -> shifting(2));

        nextYear.setOnClickListener(v -> shifting(1));
    }

    private void setPieChartData() {
        entries = new ArrayList<>();
        entries = getData(2009, null, null);
        pieDataSet = PieChartUtil.setPieChartData(pieColorID, "", entries);
        pieDataSet.setDrawValues(true);
        // 设置向往延伸的线来显示数据
        pieDataSet.setValueLinePart1OffsetPercentage(80.f);
        pieDataSet.setValueLinePart1Length(0.2f);
        pieDataSet.setValueLinePart2Length(0.5f);
        pieDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        pieData = new PieData(pieDataSet);
        pieData.setValueFormatter(new PercentFormatter());// 添加%格式
        pieData.setValueTextSize(15f);// 设置数据字体大小
        mPieChart.setData(pieData);
        mPieChart.setCenterText("2009年");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        /* 加载菜单栏布局*/
        getMenuInflater().inflate(R.menu.menu_pie_chart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.show_proportion:
                if (percentageItem == null) {
                    percentageItem = menu.findItem(R.id.show_proportion);
                }
                pieDataSet.setDrawValues(!pieDataSet.isDrawValuesEnabled());
                if (pieDataSet.isDrawValuesEnabled()) {
                    percentageItem.setTitle("隐藏百分比");
                } else {
                    percentageItem.setTitle("显示百分比");
                }
                mPieChart.invalidate();
                break;
            case R.id.dynamic_data_pie:
                showSetDataDialog();
                break;
            case R.id.save_to_local_pie:
                save();
                break;
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return true;
    }

    // 点击按钮切换图表
    private void shifting(int i) {
        if (i == 1) {// 为1时下一年，年份加1
            year++;
        } else {
            year--;
        }
        if (audiValues == null && benzValues == null) {
            entries.clear();
            entries = getData(year, null, null);
        } else {
            entries.clear();
            entries = getData(year, audiValues, benzValues);
        }
        pieDataSet = PieChartUtil.setPieChartData(pieColorID, "", entries);
        pieDataSet.setDrawValues(true);
        pieDataSet.setValueLinePart1OffsetPercentage(80.f);
        pieDataSet.setValueLinePart1Length(0.2f);
        pieDataSet.setValueLinePart2Length(0.5f);
        pieDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        pieData = new PieData(pieDataSet);
        pieData.setValueFormatter(new PercentFormatter());// 添加%格式
        pieData.setValueTextSize(15f);// 设置数据字体大小
        mPieChart.setData(pieData);
        mPieChart.setCenterText(year + "年");
        mPieChart.setUsePercentValues(true);// 显示百分比
        mPieChart.invalidate();
        mPieChart.animateXY(1200, 1200);
        if (percentageItem != null) {
            percentageItem.setTitle("隐藏百分比");
        }
        if (year > 2009){
            lastYear.setVisibility(View.VISIBLE);
        }else {
            lastYear.setVisibility(View.GONE);
        }
        if (year < 2018){
            nextYear.setVisibility(View.VISIBLE);
        }else {
            nextYear.setVisibility(View.GONE);
        }
    }

    // 显示自定义数据对话框
    private void showSetDataDialog() {
        final CustomDialog dialog = new CustomDialog(PieChartActivity.this, R.style.CustomDialog);
        dialog.setCancelable(false);
        dialog.setOnPositiveClickListener(v -> {
            inputAudiSet = dialog.getAudiData();// 获取自定义对话框的奥迪输入值
            inputBenzSet = dialog.getBenzData();
            if (inputAudiSet.size() < 10 || inputBenzSet.size() < 10) {
                // 震动权限
                Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                assert vibrator != null;// 断言： 等于null则退出，不等于则继续执行
                vibrator.vibrate(240);
                Toast.makeText(PieChartActivity.this, "请输入完整数据", Toast.LENGTH_SHORT).show();
            } else {
                setDynamicData();
                Toast.makeText(PieChartActivity.this, "数据更新完成", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        }).setOnNegativeClickListener(v -> dialog.dismiss()).show();
    }

    // 自定数据设置
    private void setDynamicData() {
        /* 判断API是否大于24，大于则调用自带的转换数组方法*/
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            audiValues = inputAudiSet.stream().mapToInt(Integer::intValue).toArray();
            benzValues = inputBenzSet.stream().mapToInt(Integer::intValue).toArray();// 输入的数据的数组列表转化为数组
        } else { // 否则调用自定义的转换数组方法
            audiValues = arraylistToArray(inputAudiSet);
            benzValues = arraylistToArray(inputBenzSet);
        }
        entries.clear();
        entries = getData(2009, audiValues, benzValues);
        pieDataSet = PieChartUtil.setPieChartData(pieColorID, "", entries);
        pieDataSet.setDrawValues(true);
        // 设置向往延伸的线来显示数据
        pieDataSet.setValueLinePart1OffsetPercentage(80.f);
        pieDataSet.setValueLinePart1Length(0.2f);
        pieDataSet.setValueLinePart2Length(0.5f);
        pieDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        pieData = new PieData(pieDataSet);
        pieData.setValueFormatter(new PercentFormatter());// 添加%格式
        pieData.setValueTextSize(15f);// 设置数据字体大小

        mPieChart.setData(pieData);
        mPieChart.setCenterText("2009年");
        year = 2009;
        mPieChart.notifyDataSetChanged();
        mPieChart.invalidate();
        mPieChart.animateXY(1200, 1200);
        if (percentageItem != null) {
            percentageItem.setTitle("隐藏百分比");
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
        if (ContextCompat.checkSelfPermission(PieChartActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // 获取权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
        } else {
            if (mPieChart.saveToGallery(mPieChartName.getText().toString() + UUID.randomUUID(), 100)) {
                Toast.makeText(PieChartActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(PieChartActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (mPieChart.saveToGallery(mPieChartName.getText().toString() + UUID.randomUUID(), 100)) {
                        Toast.makeText(PieChartActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(PieChartActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(PieChartActivity.this, "没有获取权限", Toast.LENGTH_SHORT).show();
                }
        }
    }

}
