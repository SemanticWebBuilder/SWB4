/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
        SWBPlatform.createInstance(null);
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
        out.println(frm.renderForm());
    }


    @Test
    public void doTest(){
        out.println("<pre>");
        SemanticClass clase=SWBPlatform.getSemanticMgr().getModel("urswb").createSemanticClass("urswb/clsUserType#publico");
        Iterator<SemanticProperty>itsp = clase.listProperties();
    while(itsp.hasNext()){
        out.println(itsp.next());
    }
    }
}
