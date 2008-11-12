/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.model;

import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.semanticwb.SWBPlatform;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.SWBContext;
import org.semanticwb.platform.SemanticClass;
import static org.junit.Assert.*;

/**
 *
 * @author serch
 */
public class UserTest {

    public UserTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        SWBPlatform.createInstance(null);
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
    @Test
    public void testGetName() {
        UserRepository repository = null;
        repository = SWBContext.getUserRepository("urswb");
        System.out.println("Repository:"+repository);
        System.out.println("Model:"+repository.getSemanticObject().getModel().getRDFModel());
        
        SemanticClass cls=SWBContext.getVocabulary().swb_Dns;
        Dns dns=(Dns)SWBPlatform.getSemanticMgr().getOntology().getGenericObject("localhost", cls);
        System.out.println("dns:"+dns);
        System.out.println("dns_model:"+dns.getSemanticObject().getModel().getRDFModel());
        
//        if (null==repository) 
//        {
//            repository = SWBContext.createUserRepository("swb_users", "http://www.infotec.com.mx");
//        }
//        System.out.println("Repository2;"+repository);
//        System.out.println("Rep_SemObj:"+repository.getSemanticObject());
//        System.out.println("Rep_SemObj_Mod:"+repository.getSemanticObject().getModel());
//        System.out.println("Rep_SemObj_Mod_NS:"+repository.getSemanticObject().getModel().getNameSpace());
//        
//        
//        User instance = repository.getUser("serch");
//        //User instance = repository.createUser("serch");
//        System.out.println("User;"+instance+" "+instance.getSemanticObject());
//        
//        instance.setCreated(new Date());
//        instance.setStatus(1);
//        instance.setUsrEmail("serch@infotec.com.mx");
//        instance.setUsrFirstName("Sergio");
//        instance.setUsrLastName("Martínez");
//        String expResult = "serch";
//        String result = instance.getName();
//        instance.setUsrPassword("serch08");
//        
//        instance = repository.getUser("serch");
//        System.out.println("UsrEmail:"+instance.getUsrEmail());
//        System.out.println("UsrLastName:"+instance.getUsrLastName());
//        System.out.println("Language:"+instance.getLanguage());
//        
//        // TODO review the generated test code and remove the default call to fail.
//        //fail("The test case is a prototype.");
    }

    /**
     * Test of setUsrPassword method, of class User.
     */
  /*  @Test
    public void testSetUsrPassword() {
        System.out.println("setUsrPassword");
        String password = "";
        User instance = null;
        instance.setUsrPassword(password);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/

}