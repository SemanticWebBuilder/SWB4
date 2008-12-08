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
import org.junit.Ignore;
import org.junit.Test;
import org.semanticwb.SWBPlatform;
import org.semanticwb.office.comunication.OfficeApplication;
import org.semanticwb.office.interfaces.CategoryInfo;
import org.semanticwb.office.interfaces.ContentType;

/**
 *
 * @author victor.lorenzana
 */
public class TestServices
{

    private static final String workspaceid = "defaultWorkspace@swb";

    public TestServices()
    {
    }

    @BeforeClass
    public static void setUpClass() throws Exception
    {
        System.setProperty("swb.web." + "swb/repositoryManager", "org.semanticwb.repository.SWBRepositoryManager");
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
    @Ignore
    public void getCategoriesTest()
    {
        OfficeApplication office = new OfficeApplication();
        try
        {
            String id = office.createCategory(workspaceid, "demo", "Categoria demo");
            String id2 = office.createCategory(workspaceid, id, "demo 2", "demo 2");
            for (CategoryInfo category : office.getCategories(workspaceid))
            {
                getCategories(office, workspaceid, category.UDDI);
            }
        }
        catch (Exception e)
        {
            Assert.fail(e.getMessage());
        }
    }

    private void getCategories(OfficeApplication office, String workspaceid, String categoriId) throws Exception
    {

        for (CategoryInfo category : office.getCategories(workspaceid, categoriId))
        {
            getCategories(office, workspaceid, category.UDDI);
        }
    }

    @Test
    @Ignore
    public void deleteCategory()
    {
        OfficeApplication office = new OfficeApplication();
        try
        {
            for (CategoryInfo cat : office.getCategories(workspaceid))
            {
                if (office.canDeleteCategory(workspaceid, cat.UDDI))
                {
                    if (!office.deleteCategory(workspaceid, cat.UDDI))
                    {
                        System.out.println("No se puede borra la categoria " + cat.title);
                    }
                }
            }
        }
        catch (Exception e)
        {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    //@Ignore
    public void createCategory()
    {
        OfficeApplication office = new OfficeApplication();
        try
        {
            String id = office.createCategory(workspaceid, "demo", "demo descripcion");
            office.createCategory(workspaceid, id, "demo2", "demo2 descripcion");
        }
        catch (Exception e)
        {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    @Ignore
    public void getContentTypesTest()
    {
        OfficeApplication office = new OfficeApplication();
        try
        {
            for (ContentType type : office.getContentTypes(workspaceid))
            {
                System.out.println("type: "+type.id);
                System.out.println("title: "+type.title);
            }
        }
        catch (Exception e)
        {
            Assert.fail(e.getMessage());
        }
    }
}