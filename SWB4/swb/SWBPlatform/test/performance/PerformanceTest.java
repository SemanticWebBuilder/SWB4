/**  
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
**/ 
 
package performance;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Iterator;
import java.util.Map;
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
        String base=SWBUtils.getApplicationPath();
        SWBPlatform.createInstance();
        SWBPlatform.getSemanticMgr().initializeDB();
        SWBPlatform.getSemanticMgr().addBaseOntology(base+"/../../../web/WEB-INF/owl/swb.owl");
        SWBPlatform.getSemanticMgr().addBaseOntology(base+"/../../../web/WEB-INF/owl/swb_rep.owl");
        SWBPlatform.getSemanticMgr().addBaseOntology(base+"/../../../web/WEB-INF/owl/office.owl");
        SWBPlatform.getSemanticMgr().loadBaseVocabulary();
        SWBPlatform.getSemanticMgr().loadDBModels();
        SWBPlatform.getSemanticMgr().rebind();
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
        
        //SemanticOntology ontology=SWBPlatform.getSemanticMgr().getSchema();
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
        
        SemanticModel model=SWBPlatform.getSemanticMgr().getModel("demo");
        
        System.out.println(model);
        
        Iterator<SemanticClass> itcls=voc.listSemanticClasses();
        while(itcls.hasNext())
        {
            SemanticClass cls=itcls.next();
            System.out.println("cls:"+cls.getName()+"-->"+cls.getURI());
            Iterator<SemanticProperty> itprop=cls.listProperties();
            while(itprop.hasNext())
            {
                SemanticProperty prop=itprop.next();
                System.out.println("  -->prop:"+prop.getName());
            }
            
            //cls.lis
            
        }
        
        System.out.println("Time:"+(System.currentTimeMillis()-time));
        
        SemanticClass cls=voc.getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebSite");
        System.out.println(cls);
        Iterator<SemanticClass> it=cls.listSubClasses();
        while (it.hasNext())
        {
            SemanticClass semanticClass = it.next();
            System.out.println(semanticClass);
        }
        
        Iterator<Map.Entry<String,SemanticModel>> it2=SWBPlatform.getSemanticMgr().getModels().iterator();
        while (it2.hasNext())
        {
            Map.Entry<String, SemanticModel> entry = it2.next();
            System.out.println(entry.getKey());
        }
        
    }
}
