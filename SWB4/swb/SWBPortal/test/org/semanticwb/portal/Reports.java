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
package org.semanticwb.portal;

import org.junit.Test;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.Iterator;

import org.semanticwb.SWBUtils;
import org.semanticwb.model.Device;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.WebSite;


public class Reports {

    @Test
    public void fillDataSource() {
        /*SectionReport("sec1");
        SectionReport("sec11");
        SectionReport("sec11");
        SectionReport("sec112");
        SectionReport("sec1121");*/
        /*GlobalReport();*/
        /*DeviceReport("1");
        DeviceReport("2");
        DeviceReport("3");
        DeviceReport("4");
        DeviceReport("5");
        DeviceReport("6");
        DeviceReport("7");
        DeviceReport("8");
        DeviceReport("9");
        DeviceReport("10");
        DeviceReport("11");
        DeviceReport("12");
        DeviceReport("13");
        DeviceReport("14");
        DeviceReport("15");*/
        /*DeviceReport("-");*/
        LanguageReport("es");
        LanguageReport("en");
        LanguageReport("pt");
        System.out.println("FIN.");
    }

    public void GlobalReport() {
        StringBuilder query = new StringBuilder();
        Connection con = SWBUtils.DB.getDefaultConnection();
        Timestamp fecha = null;
        GregorianCalendar date;
        int hits;
        try {
            query.append("insert into swb_reshits(hits_date,hits_modelid,hits_objid,hits_type,hits) values(?,?,?,?,?)");
            PreparedStatement st = con.prepareStatement(query.toString());
            con.setAutoCommit(true);
            for(int year=2006; year<2009; year++) {
                for(int month=0; month<12; month++) {
                    for(int day=1; day<28; day++) {
                        date = new GregorianCalendar(year, month, day, 10,10,10);
                        fecha = new Timestamp(date.getTimeInMillis());
                        hits = (int)(Math.random()*100)+1;
                        st.setTimestamp(1, fecha);
                        st.setString(2, "x");
                        st.setString(3, "_");
                        st.setInt(4, 0);
                        st.setInt(5, hits);
                        st.executeUpdate();
                    }
                }
            }
            System.out.println("Global Report FIN");
        }catch(SQLException e) {
            System.out.println("error... "+e);
        }
    }

    public void DeviceReport(String deviceId) {
        StringBuilder query = new StringBuilder();
        Connection con = SWBUtils.DB.getDefaultConnection();
        Timestamp fecha = null;
        GregorianCalendar date;
        int hits;
        try {
            query.append("insert into swb_reshits(hits_date,hits_modelid,hits_objid,hits_type,hits) values(?,?,?,?,?)");
            PreparedStatement st = con.prepareStatement(query.toString());
            con.setAutoCommit(true);
            for(int year=2006; year<2009; year++) {
                for(int month=0; month<12; month++) {
                    for(int day=1; day<28; day++) {
                        date = new GregorianCalendar(year, month, day, 10,10,10);
                        fecha = new Timestamp(date.getTimeInMillis());
                        hits = (int)(Math.random()*100)+1;
                        st.setTimestamp(1, fecha);
                        st.setString(2, "x");
                        st.setString(3, deviceId);
                        st.setInt(4, 1);
                        st.setInt(5, hits);
                        st.executeUpdate();
                    }
                }
            }
            System.out.println("Device Report FIN");
        }catch(SQLException e) {
            System.out.println("error... "+e);
        }
    }

    public void LanguageReport(String lang) {
        StringBuilder query = new StringBuilder();
        Connection con = SWBUtils.DB.getDefaultConnection();
        Timestamp fecha = null;
        GregorianCalendar date;
        int hits;
        try {
            query.append("insert into swb_reshits(hits_date,hits_modelid,hits_objid,hits_type,hits) values(?,?,?,?,?)");
            PreparedStatement st = con.prepareStatement(query.toString());
            con.setAutoCommit(true);
            for(int year=2006; year<2009; year++) {
                for(int month=0; month<12; month++) {
                    for(int day=1; day<28; day++) {
                        date = new GregorianCalendar(year, month, day, 10,10,10);
                        fecha = new Timestamp(date.getTimeInMillis());
                        hits = (int)(Math.random()*100)+1;
                        st.setTimestamp(1, fecha);
                        st.setString(2, "x_x");
                        st.setString(3, lang);
                        st.setInt(4, 2);
                        st.setInt(5, hits);
                        st.executeUpdate();
                    }
                }
            }
            System.out.println("Language Report FIN");
        }catch(SQLException e) {
            System.out.println("error... "+e);
        }
    }

    public void SectionReport(String section) {
        StringBuilder query = new StringBuilder();
        Connection con = SWBUtils.DB.getDefaultConnection();
        Timestamp fecha = null;
        GregorianCalendar date;
        int hits;
        try {
            query.append("insert into swb_reshits(hits_date,hits_modelid,hits_objid,hits_type,hits) values(?,?,?,?,?)");
            PreparedStatement st = con.prepareStatement(query.toString());
            con.setAutoCommit(true);
            for(int year=2006; year<2009; year++) {
                for(int month=0; month<12; month++) {
                    for(int day=1; day<28; day++) {
                        date = new GregorianCalendar(year, month, day, 10,10,10);
                        fecha = new Timestamp(date.getTimeInMillis());
                        hits = (int)(Math.random()*100)+1;
                        st.setTimestamp(1, fecha);
                        st.setString(2, "x");
                        st.setString(3, section);
                        st.setInt(4, 3);
                        st.setInt(5, hits);
                        st.executeUpdate();
                    }
                }
            }
            System.out.println("Section Report FIN");
        }catch(SQLException e) {
            System.out.println("error... "+e);
        }
    }

}