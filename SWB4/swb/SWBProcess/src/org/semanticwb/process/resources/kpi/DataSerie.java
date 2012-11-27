/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
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
