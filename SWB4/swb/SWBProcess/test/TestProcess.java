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
 



import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.semanticwb.SWBPlatform;
import org.semanticwb.process.Activity;
import org.semanticwb.process.ProcessInstance;
import org.semanticwb.process.ProcessSite;
import org.semanticwb.process.Task;

/**
 *
 * 
 * @author serch
 */
public class TestProcess {

    @BeforeClass
    public static void setUpClass() throws Exception
    {
        //SWBPlatform.setUseDB(true);
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

    @Test
    public void createProcess()
    {
        ProcessSite site=ProcessSite.getProcessSite("oqp");


        org.semanticwb.process.Process process=site.getProcess("1");
        if(process==null)
        {
            process=site.createProcess();
            process.setTitle("Plan Estrategico");

            Activity act=site.createActivity();
            process.addActivity(act);
            process.addProcessClass(SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/oqp#PlanEstrategico").getSemanticObject());
            act.setTitle("Plan Estrategico");

            Task mision=site.createTask();
            act.addTask(mision);
            mision.setTitle("Misión");

            Task vision=site.createTask();
            act.addTask(vision);
            vision.setTitle("Visión");

            Task valores=site.createTask();
            act.addTask(valores);
            valores.setTitle("Valores");

            Task objetives=site.createTask();
            act.addTask(objetives);
            objetives.setTitle("Valores");
            objetives.addDependence(mision);
            objetives.addDependence(vision);
            objetives.addDependence(valores);
        }
        
    }


}

