/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.bsc.element;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.bsc.BSC;
import org.semanticwb.bsc.accessory.Period;

/**
 *
 * @author carlos.ramos
 */
public class NearestMeasurementPeriod {
    private String modelId = "DAC";
    private int c = 10;
    
    public NearestMeasurementPeriod() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        String base = SWBUtils.getApplicationPath();
        SWBPlatform plat = SWBPlatform.createInstance();
        plat.setPersistenceType(SWBPlatform.PRESIST_TYPE_SWBTRIPLESTOREEXT);
        SWBPlatform.getSemanticMgr().initializeDB();
        SWBPlatform.getSemanticMgr().addBaseOntology(base + "/../../../build/web/WEB-INF/owl/swb.owl");
        SWBPlatform.getSemanticMgr().addBaseOntology(base + "/../../../build/web/WEB-INF/owl/swb_rep.owl");
        SWBPlatform.getSemanticMgr().addBaseOntology(base + "/../../../build/web/WEB-INF/owl/ext/bscom.owl");
        SWBPlatform.getSemanticMgr().loadBaseVocabulary();
        SWBPlatform.getSemanticMgr().loadDBModels();
        SWBPlatform.getSemanticMgr().getOntology().rebind();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Test
    public void test1() {
        if(BSC.ClassMgr.hasBSC("DAC"))
        {
            BSC model = BSC.ClassMgr.getBSC(modelId);            
            System.out.println("model: "+model);
            Iterator<Indicator> indicators = Indicator.ClassMgr.listIndicators(model);
            try {
                Indicator indicator = indicators.next();
System.out.println("indicator="+indicator.getTitle());
                GregorianCalendar current = new GregorianCalendar();   
System.out.println("hoy="+current.getTime());

                //Iterator<Period> periods = model.listPeriods();
                try {
                    Iterator<Period> periods = indicator.listMeasurablesPeriods(true);
                    while(periods.hasNext()) {
                        Period period = periods.next();
                        if(!period.isValid()) {                            
                            continue;
                        }                        
System.out.println("-------------- "+period.getTitle());
                        if(isInMeasurementTime(period)) {
                            System.out.println("Estamos en tiempo de medicion!!!!!!");
                        }
                    }
                }catch(Exception e){
System.out.println("CATCH...."+e.getMessage());
                }
            }catch(NoSuchElementException nsee){}
        }else {
System.out.println("no existe model con id "+modelId);
        }
    }
    
    public boolean isInMeasurementTime(final Period period) {
        GregorianCalendar current = new GregorianCalendar();
        Date end = period.getEnd();
        if(end==null) {
            return false;
        }
        GregorianCalendar left = new GregorianCalendar();
        left.setTime(end);
        left.add(Calendar.DATE, -c);
        GregorianCalendar right = new GregorianCalendar();
        right.setTime(end);
        right.add(Calendar.DATE, c);
        return current.compareTo(left)>0 && current.compareTo(right)<0;
    }
}
