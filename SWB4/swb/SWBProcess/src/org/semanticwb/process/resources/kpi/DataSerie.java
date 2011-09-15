/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.process.resources.kpi;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author hasdai
 */
public class DataSerie {
    ArrayList<Long> serie = null;
    ArrayList<String> dataLabels = null;
    ArrayList<String> xAxisLabels = null;
    ArrayList<String> yAxisLabels = null;
    String serieLabel = "";
    String id = "";

    public DataSerie(String sid, String label) {
        serie = new ArrayList<Long>();
        dataLabels = new ArrayList<String>();
        xAxisLabels = new ArrayList<String>();
        yAxisLabels = new ArrayList<String>();
        serieLabel = label;
        id = sid;
    }

    public void addData(long data, String tooltip) {
        serie.add(data);
        dataLabels.add(tooltip);
    }
    
    public void addXAxisLabel(String label) {
        xAxisLabels.add(label);
    }
    
    public void addYAxisLabel(String label) {
        yAxisLabels.add(label);
    }
    
    public String getId() {
        return id;
    }
    
    public String getSerieLabel() {
        return serieLabel;
    }

    public String getDataArrayString() {
        String ret = "";
        Iterator<Long> it = serie.iterator();
        while(it.hasNext()) {
            Long d = it.next();
            ret += String.valueOf(d);
            if (it.hasNext()) ret += ",";
        }
        return ret;
    }

    public String getLabelsArrayString() {
        String ret = "";
        Iterator<String> it = dataLabels.iterator();
        while(it.hasNext()) {
            String d = it.next();
            ret += d;
            if (it.hasNext()) ret += ",";
        }
        return ret;
    }
    
    public Iterator<String> listDataLabels() {
        return dataLabels.iterator();
    }
    
    public Iterator<Long> listData() {
        return serie.iterator();
    }
    
    public Iterator<String> listxAxisLabels() {
        return xAxisLabels.iterator();
    }
    
    public Iterator<String> listyAxisLabels() {
        return yAxisLabels.iterator();
    }
}
