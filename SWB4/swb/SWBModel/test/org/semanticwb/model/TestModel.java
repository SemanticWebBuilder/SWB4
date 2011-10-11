/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.model;

import java.util.Iterator;
import java.util.Properties;
import org.junit.*;

import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;

/**
 *
 * @author javier.solis.g
 */
public class TestModel 
{
    private Logger log = SWBUtils.getLogger(TestCreate.class);

    public TestModel() {}

    @BeforeClass
    public static void setUpClass() throws Exception 
    {
        
        String base = SWBUtils.getApplicationPath();
        //System.out.println(base);
        Properties props = SWBUtils.TEXT.getPropertyFile("/web.properties");  
        //System.out.println(props);
        SWBPlatform.createInstance().setProperties(props);
        
        SWBPlatform.getSemanticMgr().initializeDB();
        SWBPlatform.getSemanticMgr().addBaseOntology(base + "../../../web/WEB-INF/owl/swb.owl");
        SWBPlatform.getSemanticMgr().addBaseOntology(base + "../../../web/WEB-INF/owl/swb_rep.owl");
        SWBPlatform.getSemanticMgr().addBaseOntology(base + "../../../web/WEB-INF/owl/office.owl");
        SWBPlatform.getSemanticMgr().loadBaseVocabulary();
        SWBPlatform.getSemanticMgr().loadDBModels();
        SWBPlatform.getSemanticMgr().getOntology().rebind();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {}

    @Before
    public void setUp() {}

    @After
    public void tearDown() {}

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void test() {

        Iterator<WebSite> it=SWBContext.listWebSites();
        while (it.hasNext()) {
            WebSite webSite = it.next();
            System.out.println();
        }
        
    }    
}
