/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.codegen;

import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.semanticwb.SWBPlatform;
import static org.junit.Assert.*;

/**
 *
 * @author victor.lorenzana
 */
public class CodeGeneratorBase
{

    public CodeGeneratorBase()
    {
    }

    @BeforeClass
    public static void setUpClass() throws Exception
    {
        //SWBPlatform.setUseDB(false);
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

    // The methods must be annotated with annotation @Test. For example:
    //
    //@Test
    public void generateCode()
    {
        try
        {
            String path=getClass().getResource("/").getPath().replaceAll("%20", " ");
            File dir = new File(path+"../../../SWBModel/src");
            String sPakage = "org.semanticwb.model";
            CodeGenerator codeGeneration = new CodeGenerator(dir, sPakage);
            codeGeneration.generateCode();
            System.out.println("Generación de clases completa");
        }
        catch ( CodeGeneratorException cge )
        {
            fail(cge.getMessage());
        }
    }

    // The methods must be annotated with annotation @Test. For example:
    //
    //@Test
    public void generateCodeSwb()
    {
        try
        {
            String path=getClass().getResource("/").getPath().replaceAll("%20", " ");
            File dir = new File(path+"../../../SWBModel/src");
            String sPakage = "org.semanticwb.model";
            CodeGenerator codeGeneration = new CodeGenerator(dir, sPakage);
            codeGeneration.generateCode("swb");
            System.out.println("Generación de clases completa");
        }
        catch ( CodeGeneratorException cge )
        {
            fail(cge.getMessage());
        }
    }

    //@Test
    public void generateCodeSwbxf()
    {
        try
        {
            String path=getClass().getResource("/").getPath().replaceAll("%20", " ");
            File dir = new File(path+"../../../SWBModel/src");
            String sPakage = "org.semanticwb.model";
            CodeGenerator codeGeneration = new CodeGenerator(dir, sPakage);
            codeGeneration.generateCode("swbxf");
            System.out.println("Generación de clases completa");
        }
        catch ( CodeGeneratorException cge )
        {
            fail(cge.getMessage());
        }
    }

    //@Test
    public void generateCodeForum()
    {
        try
        {
            String path=getClass().getResource("/").getPath().replaceAll("%20", " ");
            File dir = new File(path+"../../../SWBModel/src");
            String sPakage = "org.semanticwb.model";
            CodeGenerator codeGeneration = new CodeGenerator(dir, sPakage);
            codeGeneration.generateCode("frm");
            System.out.println("Generación de clases completa");
        }
        catch ( CodeGeneratorException cge )
        {
            fail(cge.getMessage());
        }
    }

    @Test
    public void generateOffice()
    {
        try
        {
            String path=getClass().getResource("/").getPath().replaceAll("%20", " ");
            File dir = new File(path+"../../../SWBOffice/src");
            String sPakage = "org.semanticwb.repository.office";
            CodeGenerator codeGeneration = new CodeGenerator(dir, sPakage);
            codeGeneration.generateCode("cm",false);
            System.out.println("Generación de clases completa");
        }
        catch ( CodeGeneratorException cge )
        {
            fail(cge.getMessage());
        }
    }

}
