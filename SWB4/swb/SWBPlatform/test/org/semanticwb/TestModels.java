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
 
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntDocumentManager;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;
import java.util.Iterator;
import org.junit.*;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.platform.SemanticVocabulary;

/**
 *
 * @author Jei
 */
public class TestModels {
    private Logger log=SWBUtils.getLogger(TestModels.class);

    static public final String NL = System.getProperty("line.separator") ; 

    public TestModels()
    {
    }

    @BeforeClass
    public static void setUpClass() throws Exception
    {
        String base=SWBUtils.getApplicationPath();
        SWBPlatform.createInstance();
        SWBPlatform.getSemanticMgr().initializeDB();
        //SWBPlatform.getSemanticMgr().addBaseOntology(base+"../../../web/WEB-INF/owl/swb.owl");
        //SWBPlatform.getSemanticMgr().addBaseOntology(base+"../../../web/WEB-INF/owl/swb_rep.owl");
        //SWBPlatform.getSemanticMgr().addBaseOntology(base+"../../../web/WEB-INF/owl/office.owl");
        SWBPlatform.getSemanticMgr().loadBaseVocabulary();
        SWBPlatform.getSemanticMgr().loadDBModels();
        SWBPlatform.getSemanticMgr().getOntology().rebind();
        SemanticModel model=SWBPlatform.getSemanticMgr().createDBModel("class", "htp://www.class.com#",true);
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
    public void test1()
    {
        long time=System.currentTimeMillis();
        //System.out.println("path:"+SWBUtils.getApplicationPath()+"../../../web/WEB-INF/owl/swb.owl");

        OntDocumentManager mgr=com.hp.hpl.jena.ontology.OntDocumentManager.getInstance();
        //Model m=SWBPlatform.getSemanticMgr().loadRDFFileModel("file:"+SWBUtils.getApplicationPath()+"/WEB-INF/owl/swb.owl");
        //mgr.addModel("http://www.semanticwebbuilder.org/swb4/ontology", m);
        System.out.println("Time Mgr:"+(System.currentTimeMillis()-time));

        mgr.addAltEntry("http://www.semanticwebbuilder.org/swb4/ontology", SWBUtils.getApplicationPath()+"../../../web/WEB-INF/owl/swb.owl");
        mgr.addAltEntry("http://www.semanticwebbuilder.org/swb4/community", SWBUtils.getApplicationPath()+"../../../web/WEB-INF/owl/community.owl");
        mgr.addAltEntry("http://www.semanticwebbuilder.org/swb4/process", SWBUtils.getApplicationPath()+"../../../web/WEB-INF/owl/swp.owl");
        System.out.println("Time Refs:"+(System.currentTimeMillis()-time));

        SemanticModel m=SWBPlatform.getSemanticMgr().getModel("class");
        mgr.addModel("http://www.semanticwb.org/SWBAdmin", m.getRDFModel());
        
        //sont=new SemanticOntology("", com.hp.hpl.jena.rdf.model.ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_RDFS_INF,base.getRDFModel()));
        //sont=new SemanticOntology("", mgr.getOntology("http://www.semanticwb.org/catalogs", OntModelSpec.OWL_MEM_RDFS_INF));
        //sont=new SemanticOntology("", mgr.getOntology("http://www.semanticwebbuilder.org/swb4/ontology", OntModelSpec.OWL_MEM_RDFS_INF));

        //SemanticOntology sont=new SemanticOntology("", mgr.getOntology("http://www.semanticwebbuilder.org/swb4/community", OntModelSpec.OWL_MEM_TRANS_INF));

        SemanticOntology sont = new SemanticOntology("SWBSquema",ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_TRANS_INF));
        mgr.loadImport(sont.getRDFOntModel(), "http://www.semanticwebbuilder.org/swb4/ontology");
        mgr.loadImport(sont.getRDFOntModel(), "http://www.semanticwebbuilder.org/swb4/community");
        mgr.loadImport(sont.getRDFOntModel(), "http://www.semanticwebbuilder.org/swb4/process");
        //mgr.loadImport(sont.getRDFOntModel(), "htp://www.class.com");

        //sont.getRDFOntModel().addSubModel(mgr.getModel("http://www.semanticwebbuilder.org/swb4/ontology"), false);
        //sont.getRDFOntModel().addSubModel(mgr.getModel("http://www.semanticwebbuilder.org/swb4/community"), false);
        //sont.getRDFOntModel().rebind();

        System.out.println("Time Create:"+(System.currentTimeMillis()-time));

/*
        swbowl="file:"+SWBUtils.getApplicationPath()+"/WEB-INF/owl/swb.owl";
        owlf=new java.io.File(swbowl);
        SemanticModel model=SWBPlatform.getSemanticMgr().readRDFFile(owlf.getName(),swbowl);
        sont.addSubModel(model, false);
*/
        //System.out.println("Ont:"+sont.getRDFOntModel().size());

        //OntClass cls=sont.getRDFOntModel().getOntClass("http://www.semanticwebbuilder.org/swb4/ontology#Topic");

        int x=0;
        Iterator<OntClass> it=sont.getRDFOntModel().listClasses();
        while (it.hasNext()) {
            OntClass cls = it.next();
            //System.out.println("cls:"+cls);
            x++;
        }
        System.out.println("Time Cls1:"+x+" "+(System.currentTimeMillis()-time));

        Iterator<OntProperty> itp=sont.getRDFOntModel().listAllOntProperties();
        x=0;
        while (itp.hasNext()) {
            OntProperty prop = itp.next();
            //System.out.println("cls:"+cls);
            x++;
        }
        System.out.println("Time Prop1:"+x+" "+(System.currentTimeMillis()-time));

        mgr.unloadImport(sont.getRDFOntModel(), "http://www.semanticwebbuilder.org/swb4/process");

        it=sont.getRDFOntModel().listClasses();
        x=0;
        while (it.hasNext()) {
            OntClass cls = it.next();
            //System.out.println("cls:"+cls);
            x++;
        }
        System.out.println("Time Cls2:"+x+" "+(System.currentTimeMillis()-time));

        itp=sont.getRDFOntModel().listAllOntProperties();
        x=0;
        while (itp.hasNext()) {
            OntProperty prop = itp.next();
            //System.out.println("cls:"+cls);
            x++;
        }
        System.out.println("Time Prop2:"+x+" "+(System.currentTimeMillis()-time));
    }

    @Test
    public void test2()
    {
        OntDocumentManager mgr=com.hp.hpl.jena.ontology.OntDocumentManager.getInstance();
        mgr.reset();

        long time=System.currentTimeMillis();

        Model m1=SWBPlatform.getSemanticMgr().loadRDFFileModel(SWBUtils.getApplicationPath()+"../../../web/WEB-INF/owl/swb.owl");
        Model m2=SWBPlatform.getSemanticMgr().loadRDFFileModel(SWBUtils.getApplicationPath()+"../../../web/WEB-INF/owl/community.owl");
        Model m3=SWBPlatform.getSemanticMgr().loadRDFFileModel(SWBUtils.getApplicationPath()+"../../../web/WEB-INF/owl/swp.owl");
        System.out.println("Time Refs:"+(System.currentTimeMillis()-time));

        SemanticModel m=SWBPlatform.getSemanticMgr().getModel("class");

        SemanticOntology sont = new SemanticOntology("SWBSquema",ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_TRANS_INF));
        sont.getRDFOntModel().addSubModel(m1, false);
        sont.getRDFOntModel().addSubModel(m2, false);
        sont.getRDFOntModel().addSubModel(m3, false);
        sont.getRDFOntModel().addSubModel(m.getRDFModel(), false);

        System.out.println("Time Create:"+(System.currentTimeMillis()-time));

        int x=0;
        Iterator<OntClass> it=sont.getRDFOntModel().listClasses();
        while (it.hasNext()) {
            OntClass cls = it.next();
            //System.out.println("cls:"+cls);
            x++;
        }
        System.out.println("Time Cls1:"+x+" "+(System.currentTimeMillis()-time));

        Iterator<OntProperty> itp=sont.getRDFOntModel().listAllOntProperties();
        x=0;
        while (itp.hasNext()) {
            OntProperty prop = itp.next();
            //System.out.println("cls:"+cls);
            x++;
        }
        System.out.println("Time Prop1:"+x+" "+(System.currentTimeMillis()-time));

        sont.getRDFOntModel().removeSubModel(m3);
        //SemanticClass cls2=m.createSemanticClass("htp://www.class.com#Prueba");
        //m.createSemanticProperty("htp://www.class.com#prop", cls2, SemanticVocabulary.OWL_DATATYPEPROPERTY, SemanticVocabulary.XMLS_INTEGER);

        it=sont.getRDFOntModel().listClasses();
        x=0;
        while (it.hasNext()) {
            OntClass cls = it.next();
            //System.out.println("cls:"+cls);
            x++;
        }
        System.out.println("Time Cls2:"+x+" "+(System.currentTimeMillis()-time));

        itp=sont.getRDFOntModel().listAllOntProperties();
        x=0;
        while (itp.hasNext()) {
            OntProperty prop = itp.next();
            //System.out.println("cls:"+cls);
            x++;
        }
        System.out.println("Time Prop2:"+x+" "+(System.currentTimeMillis()-time));
    }

    @Test
    public void test3()
    {
        long time=System.currentTimeMillis();
        //System.out.println("path:"+SWBUtils.getApplicationPath()+"../../../web/WEB-INF/owl/swb.owl");

        OntDocumentManager mgr=com.hp.hpl.jena.ontology.OntDocumentManager.getInstance();
        System.out.println("Time Mgr:"+(System.currentTimeMillis()-time));

        mgr.addAltEntry("http://www.semanticwebbuilder.org/swb4/ontology", SWBUtils.getApplicationPath()+"../../../web/WEB-INF/owl/swb.owl");
        mgr.addAltEntry("http://www.semanticwebbuilder.org/swb4/community", SWBUtils.getApplicationPath()+"../../../web/WEB-INF/owl/community.owl");
        mgr.addAltEntry("http://www.semanticwebbuilder.org/swb4/process", SWBUtils.getApplicationPath()+"../../../web/WEB-INF/owl/swp.owl");
        System.out.println("Time Refs:"+(System.currentTimeMillis()-time));

        SemanticModel m=SWBPlatform.getSemanticMgr().getModel("class");
        System.out.println(m.getNameSpace());
        mgr.addModel(m.getNameSpace(), m.getRDFModel());

        //SemanticOntology sont = new SemanticOntology("SWBSquema",ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_TRANS_INF,m.getRDFModel()));
        SemanticOntology sont = new SemanticOntology("SWBSquema",ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_TRANS_INF));
        mgr.loadImport(sont.getRDFOntModel(), "http://www.semanticwebbuilder.org/swb4/ontology");
        mgr.loadImport(sont.getRDFOntModel(), "http://www.semanticwebbuilder.org/swb4/community");
        mgr.loadImport(sont.getRDFOntModel(), "http://www.semanticwebbuilder.org/swb4/process");
        mgr.loadImport(sont.getRDFOntModel(), "htp://www.class.com#");

        System.out.println("Time Create:"+(System.currentTimeMillis()-time));

        int x=0;
        Iterator<OntClass> it=sont.getRDFOntModel().listClasses();
        while (it.hasNext()) {
            OntClass cls = it.next();
            //System.out.println("cls:"+cls);
            x++;
        }
        System.out.println("Time Cls1:"+x+" "+(System.currentTimeMillis()-time));

        Iterator<OntProperty> itp=sont.getRDFOntModel().listAllOntProperties();
        x=0;
        while (itp.hasNext()) {
            OntProperty prop = itp.next();
            //System.out.println("cls:"+cls);
            x++;
        }
        System.out.println("Time Prop1:"+x+" "+(System.currentTimeMillis()-time));

        mgr.unloadImport(sont.getRDFOntModel(), "http://www.semanticwebbuilder.org/swb4/process");

        it=sont.getRDFOntModel().listClasses();
        x=0;
        while (it.hasNext()) {
            OntClass cls = it.next();
            //System.out.println("cls:"+cls+" "+cls.getModel().hashCode());
            x++;
        }
        System.out.println("Time Cls2:"+x+" "+(System.currentTimeMillis()-time));

        itp=sont.getRDFOntModel().listAllOntProperties();
        x=0;
        while (itp.hasNext()) {
            OntProperty prop = itp.next();
            //System.out.println("cls:"+cls);
            x++;
        }
        System.out.println("Time Prop2:"+x+" "+(System.currentTimeMillis()-time));
    }

}
