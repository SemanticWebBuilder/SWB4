/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.office.communication.office;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashSet;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.semanticwb.SWBPlatform;
import org.semanticwb.office.comunication.OfficeApplication;
import org.semanticwb.office.comunication.OfficeDocument;
import org.semanticwb.office.interfaces.CategoryInfo;
import org.semanticwb.office.interfaces.ContentType;
import org.semanticwb.office.interfaces.VersionInfo;
import org.semanticwb.xmlrpc.Part;

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
        //swbrep/repositoryManager=org.semanticwb.repository.SWBRepositoryManager
        //swbrep/numberOfVersions=2
        System.setProperty("swb.web." + "swbrep/repositoryManager", "org.semanticwb.repository.SWBRepositoryManager");
        System.setProperty("swb.web." + "swbrep/maxNumberOfVersions", "2");
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
    @Ignore
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
                System.out.println("type: " + type.id);
                System.out.println("title: " + type.title);
            }
        }
        catch (Exception e)
        {
            Assert.fail(e.getMessage());
        }
    }

    @Test    
    public void createContentTest()
    {
        OfficeDocument document = new OfficeDocument();
        OfficeApplication office = new OfficeApplication();
        try
        {
            String id = office.createCategory(workspaceid, "demo", "Categoria demo");
            String categoryid = id;
            String contentType = office.getContentTypes(workspaceid)[0].id;
            HashSet<Part> parts=new HashSet<Part>();
            File file=new File("C:\\temp\\demo.odt");
            FileInputStream in=new FileInputStream(file);
            byte[] buffer=new byte[2048];
            int read=in.read(buffer);
            ByteArrayOutputStream bin=new ByteArrayOutputStream();
            while(read!=-1)
            {
                bin.write(buffer, 0, read);
                read=in.read(buffer);
            }
            byte[] part=bin.toByteArray();
            parts.add(new Part(part,file.getName(),file.getName()));
            document.setParts(parts);
            String contentid=document.publish("contentido2", "contenido de prueba", workspaceid, categoryid, "WORD", contentType,file.getName());
            System.out.println("Contenido creado con id="+contentid);
            //document.clearParts();
            //document.setParts(parts);
            String versionName=document.updateContent(workspaceid,contentid,file.getName());

            System.out.println("Contenido actualizado con version "+versionName);
            versionName=document.updateContent(workspaceid,contentid,file.getName());
            System.out.println("Contenido actualizado con version "+versionName);
            for(VersionInfo info : document.getVersions(workspaceid, contentid))
            {
                System.out.println(info.nameOfVersion);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace(System.out);
            Assert.fail(e.getMessage());
        }
    }
}