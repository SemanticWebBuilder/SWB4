/*
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
 */
package org.semanticwb.portal;


import org.semanticwb.model.*;
import java.io.PrintStream;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.semanticwb.SWBException;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.SWBContext;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.platform.SemanticProperty;
import static org.junit.Assert.*;
/**
 *
 * @author serch
 */
public class TestClass {

    static PrintStream out;

    @BeforeClass
    public static void setUpClass() throws Exception {
        String base=SWBUtils.getApplicationPath();
        SWBPlatform.createInstance();
        SWBPlatform.getSemanticMgr().initializeDB();
        SWBPlatform.getSemanticMgr().addBaseOntology(base+"../../../web/WEB-INF/owl/swb.owl");
        SWBPlatform.getSemanticMgr().addBaseOntology(base+"../../../web/WEB-INF/owl/swb_rep.owl");
        SWBPlatform.getSemanticMgr().addBaseOntology(base+"../../../web/WEB-INF/owl/office.owl");
        SWBPlatform.getSemanticMgr().loadBaseVocabulary();
        SWBPlatform.getSemanticMgr().loadDBModels();
        SWBPlatform.getSemanticMgr().getOntology().rebind();
        out = System.out;
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getName method, of class User.
     */
    
    public void testClase() {
        SemanticOntology ont=SWBPlatform.getSemanticMgr().getOntology();
    //SemanticClass clase=SWBPlatform.getSemanticMgr().getModel(smodel).createSemanticClass(suri);
    //SemanticClass clase=ont.createSemanticClass(suri);
    //SemanticClass clase = SWBContext.getDefaultRepository().getUserType("estudiante");

    SemanticClass clase = SWBContext.getDefaultRepository().getUserType("publico");
    //clas2.addSuperClass(clase);
    //clase = SWBContext.getVocabulary().swb_User;
    //clase.getOntClass().addSuperClass(SWBContext.getVocabulary().swb_User.getOntClass().getRDFType());
    //clase.addSuperClass(SWBContext.getVocabulary().swb_User);
   // clase = clas2;
    //SWBContext.getDefaultRepository().createBooleanExtendedAttribute("inscrito", "publico");
    out.println(clase.getURI());
    Iterator <SemanticClass> sclsit = clase.listSuperClasses();

    out.println("<pre>");
    while(sclsit.hasNext()) out.println("-"+sclsit.next());

    out.println();
    sclsit = clase.listSubClasses();
    while(sclsit.hasNext()) out.println("-"+sclsit.next());
    out.println();

    Iterator<SemanticProperty>itsp = clase.listProperties();
    while(itsp.hasNext()){
        out.println(itsp.next());
    }
    out.println("</pre>");
    SWBFormMgr frm=new SWBFormMgr(clase, null, null);
     //   frm.processForm(request, response);
        frm.setAction("/swb/swbadmin/jsp/SemClassEditor.jsp");
        out.println(frm.renderForm(null));
    }


    @Test
    public void doTest(){
/*
        out.println("<pre>");
        SemanticClass clase=SWBPlatform.getSemanticMgr().getModel("urswb").createSemanticClass("urswb/clsUserType#publico");
        Iterator<SemanticProperty>itsp = clase.listProperties();
    while(itsp.hasNext()){
        out.println(itsp.next());
    }
        out.println("-----");
        SemanticOntology ont=SWBPlatform.getSemanticMgr().getOntology();
        clase=ont.createSemanticClass("urswb/clsUserType#publico");
        itsp = clase.listProperties();
    while(itsp.hasNext()){
        out.println(itsp.next());
    }
        SWBContext.getDefaultRepository().createDateTimeExtendedAttribute("fechaRegistro", "publico");
        clase=ont.createSemanticClass("urswb/clsUserType#publico");
        itsp = clase.listProperties();
          out.println("-----");
            out.println("-----");
    while(itsp.hasNext()){
        out.println(itsp.next());
    }
 */
    }

    @Test
    public void testClassRoot()
    {
        System.out.println("WebPage:"+WebPage.sclass.getRootClass());
        System.out.println("MenuItem:"+MenuItem.sclass.getRootClass());
        WebSite site=SWBContext.getWebSite("sep");
        TemplateRef ref=site.createTemplateRef();
        System.out.println(ref.getPriority());
        ref.setPriority(3);
        int ret=ref.getPriority();
        System.out.println(ret);
        ref.setActive(true);
        System.out.println(ref.getPriority());
        ref.remove();
    }
}
