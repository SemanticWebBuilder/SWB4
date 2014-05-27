/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Iterator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.bsc.BSC;
import org.semanticwb.bsc.accessory.Period;
import org.semanticwb.bsc.element.Indicator;
import org.semanticwb.bsc.element.Initiative;
import org.semanticwb.bsc.element.Objective;

/**
 *
 * @author ana.garcias
 */
public class ExportSummaryViewTest {
    final private String modelId = "Pruebas";
    final private String periodId = "2";
    
    public ExportSummaryViewTest() {
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
   
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void validObjective() {
         BSC model = BSC.ClassMgr.getBSC(modelId);
        System.out.println("model.. " + model.getTitle());
        
        
        Period period = Period.ClassMgr.getPeriod(periodId, model);
        System.out.println("periodo.." + period.getTitle());

        Iterator<Objective> itObje = Objective.ClassMgr.listObjectiveByPeriod(period);
        while (itObje.hasNext()) {
            System.out.println("encontro objetivos!!");
            Objective ob = itObje.next();
            System.out.println("objetivo: " + ob.getTitle() + " su ID: " + ob.getId());
            assertEquals("El objetivo pertenece al mismo periodo!", period, ob.getPeriod());
        }
        
    }
    
      @Test
    public void validIndicator() {
        BSC model = BSC.ClassMgr.getBSC(modelId);
        System.out.println("model: " + model);
        Period period = Period.ClassMgr.getPeriod(periodId, model);
        Objective obj = Objective.ClassMgr.getObjective("1", model);
        Iterator<Indicator> itIndPeriod = Indicator.ClassMgr.listIndicatorByPeriod(period, model);
        while (itIndPeriod.hasNext()) {
            Indicator ind = itIndPeriod.next();
            assertEquals("El indicador pertenece al periodo", ind.getObjective(), obj);
        }
    }
      
    @Test
    public void validInitiative() {
        BSC model = BSC.ClassMgr.getBSC(modelId);
        System.out.println("model: " + model);
        Period period = Period.ClassMgr.getPeriod(periodId, model);
        Objective obj = Objective.ClassMgr.getObjective("1", model);
        Iterator<Initiative> itInitiativePeriod = Initiative.ClassMgr.listInitiatives(model);
        while (itInitiativePeriod.hasNext()) {
            Initiative ini = itInitiativePeriod.next();
            assertEquals("El indicador pertenece al periodo", ini.getInitiativeAssignable(), obj);
        }
    }
      
}