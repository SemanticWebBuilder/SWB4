package performance;

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
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.platform.SemanticVocabulary;

/**
 *
 * @author Jei
 */
public class PerformanceTest 
{
    private Logger log=SWBUtils.getLogger(PerformanceTest.class);

    public PerformanceTest()
    {
    }

    @BeforeClass
    public static void setUpClass() throws Exception
    {
        SWBPlatform.createInstance(null);
    }

    @AfterClass
    public static void tearDownClass() throws Exception
    {
    }

    @Before
    public void setUp()
    {
    }

    @After
    public void tearDown()
    {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void test()
    {
        long time=System.currentTimeMillis();
        
        SemanticModel model=SWBPlatform.getSemanticMgr().getSystemModel();
        SemanticOntology ontology=SWBPlatform.getSemanticMgr().getOntology();
        SemanticVocabulary voc=SWBPlatform.getSemanticMgr().getVocabulary();
        System.out.println("Time:"+(System.currentTimeMillis()-time));
        time=System.currentTimeMillis();
//        for(int x=0;x<30;x++)
//        {
//            //model.createSemanticObject("name"+x, Vocabulary.WebPage);
//            SemanticObject obj=ontology.getSemanticObject("name"+x);
//            System.out.println("obj:"+obj.getClass());
//            //page.setStatus(1);
//            //int stat=obj.getIntProperty(voc.getSemanticProperty(voc.URI+"status"));
//            //log.debug(stat);
//            //page.setDescription("Description");
//            //SemanticObject obj=ontology.getSemanticObject("name"+x);
//            //System.out.println("x:"+stat);
//        }
        
//        Iterator<SemanticObject> it=Vocabulary.WebPage.listInstances();
//        while(it.hasNext())
//        {
//            SemanticObject obj=it.next();
//            log.debug(obj.toString());
//        }
        
        Iterator<SemanticClass> itcls=voc.listSemanticClasses();
        while(itcls.hasNext())
        {
            SemanticClass cls=itcls.next();
            System.out.println("cls:"+cls.getName());
            Iterator<SemanticProperty> itprop=cls.listProperties();
            while(itprop.hasNext())
            {
                SemanticProperty prop=itprop.next();
                System.out.println("  -->prop:"+prop.getName());
            }
            
        }
        
        System.out.println("Time:"+(System.currentTimeMillis()-time));
    }
}
