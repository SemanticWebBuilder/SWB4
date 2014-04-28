/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.bsc.admin.resources;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.api.SWBParamRequest;
import static javax.swing.JOptionPane.showInputDialog;

/**
 *
 * @author ana.garcias
 */
public class EmailLogResourceTest {

    public EmailLogResourceTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        String base = SWBUtils.getApplicationPath();
        SWBPlatform plat = SWBPlatform.createInstance();
        plat.setPersistenceType(SWBPlatform.PRESIST_TYPE_SWBTRIPLESTOREEXT);
        SWBPlatform.getSemanticMgr().initializeDB();
        SWBPlatform.getSemanticMgr().addBaseOntology("C:/Users/ana.garcias/Documents/Proyectos5/SWBBSCom/build/web/WEB-INF/owl/swb.owl");
        SWBPlatform.getSemanticMgr().addBaseOntology("C:/Users/ana.garcias/Documents/Proyectos5/SWBBSCom/build/web/WEB-INF/owl/swb_rep.owl");
        SWBPlatform.getSemanticMgr().addBaseOntology("C:/Users/ana.garcias/Documents/Proyectos5/SWBBSCom/build/web/WEB-INF/owl/ext/bscom.owl");
        SWBPlatform.getSemanticMgr().loadBaseVocabulary();
        SWBPlatform.getSemanticMgr().loadDBModels();
        SWBPlatform.getSemanticMgr().getOntology().rebind();
    }

    @Test
    public void valiadteDates() throws Exception {
        DateFormat f = DateFormat.getDateInstance(DateFormat.SHORT);
        String str1 = "01/03/14"; // Formato “dd/mm/aa”
        Date date1 = f.parse(str1);
        String str2 = "31/03/14" ;// Formato “dd/mm/aa”
        Date date2 = f.parse(str2);
        Calendar cal1 = new GregorianCalendar();
        cal1.setTime(date1);
        Calendar cal2 = new GregorianCalendar();
        cal2.setTime(date2);
        String str3 = "03/04/14"; // Formato “dd/mm/aa”
        Date date3 = f.parse(str3);
        Calendar cal3 = new GregorianCalendar();
        cal3.setTime(date3);

        if (cal3.after(cal1) && cal3.before(cal2)) {
            System.out.println("La fecha "+f.format(cal3.getTime()) +" esta dentro del rango");
           // assertEquals("La fecha esta dentro del rango",  f.format(cal3.getTime()), cal3);
        } else {
             System.out.println("La fecha "+f.format(cal3.getTime()) +" esta fuera del rango");
           // assertEquals("La fecha esta fuera del rango",  f.format(cal3.getTime()), cal3);
        }
    }

}