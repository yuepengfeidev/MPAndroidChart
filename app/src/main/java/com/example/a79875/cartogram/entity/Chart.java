package com.example.a79875.cartogram.entity;

/*实体类Chart*/
public class Chart {
    /*图*/
    private int imageID;
    /*图名*/
    private String chartName;
    /*图描述*/
    private String chartDescribe;

    public Chart(int imageID, String chartName, String chartDescribe){
        this.imageID = imageID;
        this.chartName = chartName;
        this.chartDescribe = chartDescribe;
    }

    public int getImageID() {
        return imageID;
    }

    public String getChartName(){
        return chartName;
    }

    public String getChartDescribe(){
        return chartDescribe;
    }
}
