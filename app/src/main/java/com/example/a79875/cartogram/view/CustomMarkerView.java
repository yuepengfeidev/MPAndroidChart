package com.example.a79875.cartogram.view;

import android.content.Context;
import android.widget.TextView;

import com.example.a79875.cartogram.R;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

// 点击显示数据对话框
public class CustomMarkerView extends MarkerView {
    private TextView mMarkTextView;

    private MPPointF mOffset;

    /* 默认UI对话框*/
    public CustomMarkerView(Context context){
        super(context,R.layout.default_marker_view);
        mMarkTextView = findViewById(R.id.tv_chart_name);
    }

    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     * 自定义对话框样式
     *
     * @param layoutResource the layout resource to use for the MarkerView
     */
    public CustomMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
        mMarkTextView = findViewById(R.id.tv_marker_view);
    }

    /*  添加判断，当点击获取的点在平滑曲线上也就是真实值时才会显示*/
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        if (e instanceof CandleEntry){
            CandleEntry ce = (CandleEntry) e;
            mMarkTextView.setText(String.valueOf((int) ce.getHigh()));
        }else {
            mMarkTextView.setText(String.valueOf((int) e.getY()));
        }
        super.refreshContent(e, highlight);// 执行必要的布局
    }

    public MPPointF getOffset(){
        if (mOffset == null){
            mOffset = new MPPointF(-(getWidth() / 2), -getHeight());// 水平和垂直方向的标记
        }
        return mOffset;
    }


}
