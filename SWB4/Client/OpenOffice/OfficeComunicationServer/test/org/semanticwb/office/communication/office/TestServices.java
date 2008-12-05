/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.office.communication.office;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.semanticwb.SWBPlatform;
import org.semanticwb.model.SWBContext;
import org.semanticwb.office.comunication.OfficeApplication;

/**
 *
 * @author victor.lorenzana
 */
public class TestServices
{
    private static final String workspaceid="defaultWorkspace@swb";
    public TestServices()
    {
    }

    @BeforeClass
    public static void setUpClass() throws Exception
    {
        System.setProperty("swb.web."+"swb/repositoryManager","org.semanticwb.repository.SWBRepositoryManager");
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
    public void getCategoriesTest()
    {
        OfficeApplication office=new OfficeApplication();
        try
        {
            office.createCategory(workspaceid, "demo", "Categoria demo");
            office.getCategories(workspaceid);
        }
        catch(Exception e)
        {
            Assert.fail(e.getMessage());
        }
    }
}