package com.example.a79875.cartogram.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a79875.cartogram.R;
import com.example.a79875.cartogram.chart.BarChartActivity;
import com.example.a79875.cartogram.chart.LineChartActivity;
import com.example.a79875.cartogram.chart.PieChartActivity;
import com.example.a79875.cartogram.chart.RadarChartActivity;
import com.example.a79875.cartogram.entity.Chart;

import java.util.ArrayList;

/*图表RecyclerView的适配器类*/
public class ChartAdapter extends RecyclerView.Adapter<ChartAdapter.ViewHolder> {

    private Context mContext;

    private ArrayList<Chart> charts;

    public ChartAdapter(Context mContext, ArrayList<Chart> charts) {
        this.mContext = mContext;
        this.charts = charts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.item_recycler_view, viewGroup, false);
        final ViewHolder holder = new ViewHolder(rootView);
        holder.mCardView.setOnClickListener(view -> {
            switch (holder.getAdapterPosition()) {
                case 0://打开折线图
                    Intent intent = new Intent(mContext, LineChartActivity.class);
                    mContext.startActivity(intent);
                    break;
                case 1://打开雷达图：
                    Intent intent1 = new Intent(mContext, RadarChartActivity.class);
                    mContext.startActivity(intent1);
                    break;
                case 2://打开条形图
                    Intent intent2 = new Intent(mContext,BarChartActivity.class);
                    mContext.startActivity(intent2);
                    break;
                case 3:// 打开饼状图
                    Intent intent3 = new Intent(mContext,PieChartActivity.class);
                    mContext.startActivity(intent3);
                    break;
                default:
                    break;
            }

        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Chart chart = charts.get(i);
        Glide.with(mContext).load(chart.getImageID()).into(viewHolder.chartImage);
        viewHolder.chartName.setText(chart.getChartName());
        viewHolder.chartDescribe.setText(chart.getChartDescribe());

    }

    @Override
    public int getItemCount() {
        return charts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CardView mCardView;
        ImageView chartImage;
        TextView chartName;
        TextView chartDescribe;

        ViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView;
            chartImage = mCardView.findViewById(R.id.iv_chart_icon);
            chartName = mCardView.findViewById(R.id.tv_chart_name);
            chartDescribe = mCardView.findViewById(R.id.tv_chart_description);
        }
    }
}
