/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package FrontEnd;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartSeries;
 
@ManagedBean
public class ChartView implements Serializable {
 
    private LineChartModel animatedModel1;
    private BarChartModel animatedModel2;
 
    @PostConstruct
    public void init() {
        createAnimatedModels();
    }
 
    public LineChartModel getAnimatedModel1() {
        return animatedModel1;
    }
 
    public BarChartModel getAnimatedModel2() {
        return animatedModel2;
    }
 
    private void createAnimatedModels() {
        animatedModel1 = initLinearModel();
        animatedModel1.setTitle("Sales Performance(By Earning S$Million)");
        animatedModel1.setAnimate(true);
        animatedModel1.setLegendPosition("se");
        Axis yAxis = animatedModel1.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(10);
         
        animatedModel2 = initBarModel();
        animatedModel2.setTitle("Sales Performance (By Product Volume)");
        animatedModel2.setAnimate(true);
        animatedModel2.setLegendPosition("ne");
        yAxis = animatedModel2.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(200);
    }
     
    private BarChartModel initBarModel() {
        BarChartModel model = new BarChartModel();
 
       
        ChartSeries girls = new ChartSeries();
        girls.setLabel("Products");
        girls.set("2009", 52);
        girls.set("2010", 60);
        girls.set("2011", 110);
        girls.set("2012", 135);
        girls.set("2013", 120);
        girls.set("2014", 180);
 
        model.addSeries(girls);
         
        return model;
    }
     
    private LineChartModel initLinearModel() {
        LineChartModel model = new LineChartModel();
 
       
 
        LineChartSeries series2 = new LineChartSeries();
        series2.setLabel("Earnings");
 
        series2.set(1, 6);
        series2.set(2, 3);
        series2.set(3, 2);
        series2.set(4, 7);
        series2.set(5, 9);
 
       
        model.addSeries(series2);
         
        return model;
    }
    
}