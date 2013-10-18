package org.semanticwb.bsc.catalogs;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Iterator;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.bsc.BSC;
import java.util.Collection;
import org.junit.runners.Parameterized.Parameters;
import java.util.Arrays;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 *
 * @author carlos.ramos
 */
@RunWith(value=Parameterized.class)
public class EvaluateMeasuresTest {
    private String operId;
    private double value1;
    private double value2;
    
    public static final String bscId = "DAC";
    /*
     * Los valores de operationId se han tomado de las instancias reales de Operation en el scorecard DAC
     * en mi ambiente local de desarrollo.
     * Para replicar las pruebas, es necesario instanciar y configurar las operaciones y sustituir
     * los valores de los identificadores por los correspondientes de propio ambiente de trabajo.
     */
    @Parameters
    public static Collection<Object[]> getTestParameters() {
        return Arrays.asList(new Object[][] {
            //operationId, value1, value2
            {"14",1.23999,1.23999},
            {"15",1.0000001,1.90},
            {"16",3.0001,3.0001},
            {"17",1.0000001,0.99990},
            {"18",9.99999999,9.0000},
            {"19",1.0000001,5.0},
            {"20",1.0,1.0000001}
            //{"21",2.0222, 1.0000001,3.0}
        });
    }
    
    public EvaluateMeasuresTest(Object operId, Object value1, Object value2) {
        this.operId = (String)operId;
        this.value1 = (Double)value1;
        this.value2 = (Double)value2;
    }
    
    @BeforeClass
    public static void setUpClass() throws Exception
    {
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
    public void testRhino1() {
        BSC bsc = null;
        if(BSC.ClassMgr.hasBSC(bscId)) {
            bsc = BSC.ClassMgr.getBSC(bscId);
            if(Operation.ClassMgr.hasOperation(operId, bsc)) {
                Operation operation = Operation.ClassMgr.getOperation(operId, bsc);
                System.out.println("operation="+operation);
                try{
                    assertTrue("prueba bien", operation.evaluate(value1,value2));
                }catch(Exception e) {
                    fail(e.getMessage());
                }
            }else {
                fail("No existen operacion con id "+operId);
            }
        }else {
            fail("No existe un BSC con id "+bscId);
        }
    }
    
}
