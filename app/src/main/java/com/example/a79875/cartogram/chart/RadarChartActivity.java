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
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.example.a79875.cartogram.R;
import com.example.a79875.cartogram.util.RadarChartUtil;
import com.example.a79875.cartogram.view.CustomDialog;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;

import java.util.ArrayList;
import java.util.UUID;

import static com.example.a79875.cartogram.util.RadarChartUtil.drawRadarChart;
import static com.example.a79875.cartogram.util.RadarChartUtil.getYValues;

public class RadarChartActivity extends AppCompatActivity {
    private RadarChart mRadarChart;

    private Menu mMenu;

    private TextView mRadarChartName;

    private MenuItem onlyAudiItem;// 只看奥迪菜单项
    private MenuItem onlyBenzItem;// 只看奔驰菜单项
    private MenuItem fillColorItem;// 显示填充色菜单项
    private MenuItem yCoordinateDataItem;// 显示Y轴坐标菜单项
    private MenuItem xCoordinateDataItem;// 显示X轴坐标菜单项
    private MenuItem yDataItem;// 显示Y轴数据菜单项
<<<<<<< HEAD
    private MenuItem seeAllItem;// aa

    // 两条环形蜘蛛线（奥迪销量，奔驰销量)
=======
    private MenuItem seeAllItem;// 显示全部数据菜单项

    // 两条环形蜘蛛线（奥迪销量，奔驰销量
>>>>>>> 5d947c7d67b93c7c278c4f39ceae03f100d0fee4
    private RadarDataSet audiDataSet;
    private RadarDataSet benzDataSet;

    private RadarData radarData;// 雷达图数据

    private ArrayList<Integer> inputAudiSet;// 输入奥迪销量
    private ArrayList<Integer> inputBenzSet;// 输入奔驰销量

    private YAxis yAxis;// y轴

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radar_chart);

        initView();
    }

    private void initView() {
<<<<<<< HEAD
        Toolbar toolbar = findViewById(R.id.toolbar2);
=======
        Toolbar toolbar = findViewById(R.id.toolbar);
>>>>>>> 5d947c7d67b93c7c278c4f39ceae03f100d0fee4
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // 显示返回键
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Radar Chart");
        }

        mRadarChartName = findViewById(R.id.tv_chart_name);
        mRadarChart = findViewById(R.id.radar_chart);
        yAxis = mRadarChart.getYAxis();

        drawRadarChart(this, mRadarChart,yAxis);// 绘制雷达图
        setRadarChartData();
    }

    // 设置雷达图数据
    private void setRadarChartData() {
        // 如果图表中有值，则添加数据线并设置默认数据
        if (mRadarChart.getData() != null && mRadarChart.getData().getEntryCount() > 0) {
            audiDataSet = (RadarDataSet) mRadarChart.getData().getDataSetByIndex(0);// 获取奥迪的数据线
            benzDataSet = (RadarDataSet) mRadarChart.getData().getDataSetByIndex(1);

            // 设置两条数据线的默认值
            audiDataSet.setValues(RadarChartUtil.getYValues(true, null));
            benzDataSet.setValues(RadarChartUtil.getYValues(false, null));
            // 更新数据
            mRadarChart.getData().notifyDataChanged();
            mRadarChart.notifyDataSetChanged();
        } else {// 如果图表中没有值，则直接获取设置了默认值的数据线
            audiDataSet = RadarChartUtil.setRadarChart(this, "奥迪",
                    R.color.blueAudi, (ArrayList<RadarEntry>) getYValues(true, null));
            benzDataSet = RadarChartUtil.setRadarChart(this, "奔驰",
                    R.color.pinkBenz, (ArrayList<RadarEntry>) getYValues(false, null));
            audiDataSet.setDrawFilled(true);// 打开时填充颜色
            benzDataSet.setDrawFilled(true);

            radarData = new RadarData(audiDataSet,benzDataSet);
            radarData.setDrawValues(false);//不显示Y轴值
            mRadarChart.setData(radarData);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.mMenu = menu;
        /* 加载菜单栏布局*/
        getMenuInflater().inflate(R.menu.menu_radar_chart, menu);
        return true;
    }

    // 菜单选项
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.show_y_data:// 显示Y轴数据
                if (yDataItem == null) {
                    yDataItem = mMenu.findItem(R.id.show_y_data);// 加载显示y轴数据菜单项布局
                }
                audiDataSet.setDrawValues(!audiDataSet.isDrawValuesEnabled());// 设置奥迪数据是否可见
                benzDataSet.setDrawValues(!benzDataSet.isDrawValuesEnabled());
                if (audiDataSet.isDrawValuesEnabled()) {
                    yDataItem.setTitle("隐藏Y轴数据");
                } else {
                    yDataItem.setTitle("显示Y轴数据");
                }
                mRadarChart.invalidate();// 重绘雷达图
                break;
            case R.id.show_fill_color:// 显示填充色
                if (fillColorItem == null) {
                    fillColorItem = mMenu.findItem(R.id.show_fill_color);
                }
                audiDataSet.setDrawFilled(!audiDataSet.isDrawFilledEnabled());// 设置奥迪实心填充色
                benzDataSet.setDrawFilled(!benzDataSet.isDrawFilledEnabled());
                if (audiDataSet.isDrawFilledEnabled()) {
                    fillColorItem.setTitle("隐藏填充色");
                } else {
                    fillColorItem.setTitle("显示填充色");
                }
                mRadarChart.invalidate();
                break;
            case R.id.show_y_coordinate_data:// 显示Y坐标值
                if (yCoordinateDataItem == null) {
                    yCoordinateDataItem = mMenu.findItem(R.id.show_y_coordinate_data);
                }
                mRadarChart.getYAxis().setEnabled(!mRadarChart.getYAxis().isEnabled());
                if (mRadarChart.getYAxis().isEnabled()) {
                    yCoordinateDataItem.setTitle("隐藏Y坐标值");
                } else {
                    yCoordinateDataItem.setTitle("显示Y坐标值");
                }
                mRadarChart.invalidate();
                break;
            case R.id.show_x_coordinate_data:// 显示X坐标值
                if (xCoordinateDataItem == null) {
                    xCoordinateDataItem = mMenu.findItem(R.id.show_x_coordinate_data);
                }
                mRadarChart.getXAxis().setEnabled(!mRadarChart.getXAxis().isEnabled());
                if (mRadarChart.getXAxis().isEnabled()) {
                    xCoordinateDataItem.setTitle("隐藏X坐标值");
                } else {
                    xCoordinateDataItem.setTitle("显示X坐标值");
                }
                mRadarChart.invalidate();
                break;
            case R.id.dynamic_data:// 动态数据
                showSetDataDialog();
                break;
            case R.id.only_see_audi:// 只看奥迪菜单选项
                if (onlyAudiItem == null) {
                    onlyAudiItem = mMenu.findItem(R.id.only_see_audi);
                }
                audiDataSet.setVisible(true);
                benzDataSet.setVisible(false);
                mRadarChart.invalidate();
                break;
            case R.id.only_see_benz:// 只看奔驰菜单选项
                if (onlyBenzItem == null) {
                    onlyBenzItem = mMenu.findItem(R.id.only_see_benz);
                }
                audiDataSet.setVisible(false);
                benzDataSet.setVisible(true);
                mRadarChart.invalidate();
                break;
            case R.id.see_all:// 显示所有数据
                audiDataSet.setVisible(true);
                benzDataSet.setVisible(true);
                mRadarChart.invalidate();
                break;
            case R.id.save_to_local:// 图片保存到本地
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
        final CustomDialog dialog = new CustomDialog(RadarChartActivity.this, R.style.CustomDialog);
        dialog.setCancelable(false);
        dialog.setOnPositiveClickListener(v -> {
            inputAudiSet = dialog.getAudiData();// 获取自定义对话框的奥迪输入值
            inputBenzSet = dialog.getBenzData();
            if (inputAudiSet.size() < 10 || inputBenzSet.size() < 10) {
                // 震动权限
                Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                assert vibrator != null;// 断言： 等于null则退出，不等于则继续执行
                vibrator.vibrate(240);
                Toast.makeText(RadarChartActivity.this, "请输入完整数据", Toast.LENGTH_SHORT).show();
            } else {
                setDynamicData();
                Toast.makeText(RadarChartActivity.this, "数据更新完成", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        }).setOnNegativeClickListener(v -> dialog.dismiss()).show();
    }

    // 自定义数据
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
        audiDataSet = RadarChartUtil.setRadarChart(this,"奥迪",
                R.color.blueAudi, (ArrayList<RadarEntry>) getYValues(true,audiValues));
        benzDataSet = RadarChartUtil.setRadarChart(this,"奔驰",
                R.color.pinkBenz, (ArrayList<RadarEntry>) getYValues(false,benzValues));
        audiDataSet.setDrawFilled(true);// 自定义数据后打开时填充颜色
        benzDataSet.setDrawFilled(true);
        radarData = new RadarData(audiDataSet,benzDataSet);
        radarData.setDrawValues(false);// 不显示y轴值
        mRadarChart.setData(radarData);// 给雷达图添加自定义数据
        RadarChartUtil.getTotalValues(audiValues,benzValues,yAxis);// 获取数据中最大值来修改Y轴坐标值
        // notifyDataSetChanged方法通过一个外部的方法控制如果适配器的内容改变时需要强制调用getView来刷新每个Item的内容。
        mRadarChart.notifyDataSetChanged();// 少了则会只增加y轴的长度
        mRadarChart.invalidate();// 重新绘制界面
        mRadarChart.animateXY(1200,1200);// 设置动画
        // 自定义数据后重设菜单选项
        if (onlyAudiItem != null) {
            onlyAudiItem.setTitle("只看奥迪");
        }
        if (onlyBenzItem != null) {
            onlyBenzItem.setTitle("只看奔驰");
        }
        if (seeAllItem != null){
            seeAllItem.setTitle("显示全数据");
        }
        if (yDataItem != null){
            yDataItem.setTitle("显示Y轴值");
        }
        if (xCoordinateDataItem != null){
            xCoordinateDataItem.setTitle("显示X坐标");
        }
        if (yCoordinateDataItem != null){
            yCoordinateDataItem.setTitle("显示Y坐标");
        }
        if (fillColorItem != null){
            fillColorItem.setTitle("隐藏填充色");
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
        if (ContextCompat.checkSelfPermission(RadarChartActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // 获取权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
        } else {
            if (mRadarChart.saveToGallery(mRadarChartName.getText().toString() + UUID.randomUUID(), 100)) {
                Toast.makeText(RadarChartActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(RadarChartActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (mRadarChart.saveToGallery(mRadarChartName.getText().toString() + UUID.randomUUID(), 100)) {
                        Toast.makeText(RadarChartActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RadarChartActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RadarChartActivity.this, "没有获取权限", Toast.LENGTH_SHORT).show();
                }
        }
    }
}
