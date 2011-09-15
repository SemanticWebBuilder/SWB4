/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.process.resources.kpi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author hasdai
 */
public class Series {
    HashMap<String, DataSerie> seriesMap;

    public Series() {
        seriesMap = new HashMap<String, DataSerie>();
    }
    
    public void addSerie(DataSerie serie) {
        seriesMap.put(serie.getId(), serie);
    }
    
    public void addSeries(ArrayList<DataSerie> series) {
        Iterator<DataSerie> it_ser = series.iterator();
        while (it_ser.hasNext()) {
            DataSerie serie = it_ser.next();
            seriesMap.put(serie.getId(), serie);
        }
    }
    
    public DataSerie getSerie(String id) {
        return seriesMap.get(id);
    }
}
