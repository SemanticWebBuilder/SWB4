/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.bsc.element;

import java.util.Iterator;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.bsc.BSC;
import org.semanticwb.bsc.accessory.Period;
import org.semanticwb.bsc.accessory.State;
import org.semanticwb.bsc.tracing.Measure;
import org.semanticwb.bsc.tracing.Series;

/**
 *
 * @author carlos.ramos
 */
public class IndicatorDataTableTest {
    private String modelId = "DADT";
    private String periodId = "16";
    
    public IndicatorDataTableTest() {
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
    public void test2() {
        System.out.println("test 1...");
        if(BSC.ClassMgr.hasBSC(modelId))
        {
            BSC model = BSC.ClassMgr.getBSC(modelId);
            
            System.out.println("model: "+model);
        }
    }
    
    
    //@Test
    public void test1() {
        System.out.println("test 1...");
        if(BSC.ClassMgr.hasBSC(modelId))
        {
            BSC model = BSC.ClassMgr.getBSC(modelId);
            
            System.out.println("model: "+model);
//            System.out.println("period: "+period);
            Iterator<Indicator> indicators = model.listIndicators();
            while(indicators.hasNext()) {
                Indicator indicator = indicators.next();
                if( !indicator.isValid() || indicator.getStar()==null ) {
                    continue;
                }
                Iterator<Period> periods = indicator.listPeriods(true);
                while(periods.hasNext()) {
                    Period period = periods.next();
                    if( indicator.getStar().getMeasure(period)==null || indicator.getStar().getMeasure(period).getEvaluation()==null || indicator.getStar().getMeasure(period).getEvaluation().getStatus()==null) {
                        continue;
                    }
                    System.out.println("\n\n--------------------");
                    System.out.println("Indicador:"+indicator.getTitle());
                    System.out.println("Periodo "+period.getTitle());

                    State state = indicator.getStar().getMeasure(period).getEvaluation().getStatus();
                    System.out.println("Estado "+state.getTitle());
                    Iterator<Series> serieses = indicator.listSerieses();
                    while(serieses.hasNext()) {
                        Series series = serieses.next();
                        if(!series.isValid()) {
                            continue;
                        }
                        Measure measure = series.getMeasure(period);
                        if(measure==null) {
                            continue;
                        }
                        System.out.print(series.getTitle()+":"+measure.getValue()+"; ");
                    }
                }
                break;
            }
        }
        else
        {
            fail("No existen operacion con id "+modelId);
        }
    }
}
