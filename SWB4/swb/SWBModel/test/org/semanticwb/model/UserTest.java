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
import org.semanticwb.SWBInstance;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.SWBContext;
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
        SWBInstance.createInstance(null);
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
        SWBContext contexto = SWBContext.createInstance();
        UserRepository repository = null;
        try {
        repository = contexto.getUserRepository("swb_users");
        } catch (Exception ignore){} //TODO Revisar NPE cuando se le solicita un repositorio inexistente
        if (null==repository) repository = contexto.createUserRepository("swb_users", "http://www.infotec.com.mx");
        System.out.println("getName "+repository);
        
        User instance = repository.createUser("serch");
        instance.setCreated(new Date());
        instance.setStatus(1);
        instance.setUsrEmail("serch@infotec.com.mx");
        instance.setUsrFirstName("Sergio");
        instance.setUsrLastName("Martínez");
        String expResult = "serch";
        String result = instance.getName();
        instance.setUsrPassword("serch08");
        
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
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