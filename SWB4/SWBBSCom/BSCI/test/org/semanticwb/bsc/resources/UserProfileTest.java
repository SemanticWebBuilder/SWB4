/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.bsc.resources;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;

/**
 *
 * @author ana.garcias
 */
public class UserProfileTest {
    
    public UserProfileTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of doView method, of class UserProfile.
     */
    @Test
    public void testDoView() throws Exception {
        System.out.println("doView");
        HttpServletRequest request = null;
        HttpServletResponse response = null;
        SWBParamRequest paramRequest = null;
        UserProfile instance = new UserProfile();
        instance.doView(request, response, paramRequest);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of uploadPhoto method, of class UserProfile.
     */
    @Test
    public void testUploadPhoto() throws Exception {
        System.out.println("uploadPhoto");
        HttpServletRequest request = null;
        SemanticObject obj = null;
        HttpServletResponse response = null;
        SWBParamRequest paramRequest = null;
        UserProfile instance = new UserProfile();
        instance.uploadPhoto(request, obj, response, paramRequest);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of processRequest method, of class UserProfile.
     */
    @Test
    public void testProcessRequest() throws Exception {
        System.out.println("processRequest");
        HttpServletRequest request = null;
        HttpServletResponse response = null;
        SWBParamRequest paramRequest = null;
        UserProfile instance = new UserProfile();
        instance.processRequest(request, response, paramRequest);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of doChangePassword method, of class UserProfile.
     */
    @Test
    public void testDoChangePassword() throws Exception {
        System.out.println("doChangePassword");
        HttpServletRequest request = null;
        HttpServletResponse response = null;
        SWBParamRequest paramRequest = null;
        UserProfile instance = new UserProfile();
        instance.doChangePassword(request, response, paramRequest);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of processAction method, of class UserProfile.
     */
    @Test
    public void testProcessAction() throws Exception {
        System.out.println("processAction");
        HttpServletRequest request = null;
        SWBActionResponse response = null;
        UserProfile instance = new UserProfile();
        instance.processAction(request, response);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}