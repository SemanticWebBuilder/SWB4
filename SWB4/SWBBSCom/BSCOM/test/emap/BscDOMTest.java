/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package emap;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.fail;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.bsc.BSC;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.w3c.dom.Document;

/**
 *
 * @author carlos.ramos
 */
public class BscDOMTest {
    final private String modelId = "DADT";
    final private String periodId = "16";
    
    public BscDOMTest() {
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
    
    public void setUpSessionUser(BSC model) {
        User user = model.getUserRepository().listUsers().next();
        SWBContext.setSessionUser(user);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Test
    public void dom1() {
        System.out.println("\n\n----- Obtener el dom de un scorecard");
        if(BSC.ClassMgr.hasBSC(modelId))
        {   
            BSC model = BSC.ClassMgr.getBSC(modelId);
            System.out.println("model: "+model);
            
            setUpSessionUser(model);            
            
            Document dom = model.getDom();
            System.out.println("xml:\n"+SWBUtils.XML.domToXml(dom));
        }
        else
        {
            fail("No existen operacion con id "+modelId);
        }
    }
}
