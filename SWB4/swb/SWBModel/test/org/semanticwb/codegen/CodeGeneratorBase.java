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
import org.junit.Ignore;
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
    @Test
    //@Ignore
    public void generateCodeSwb()
    {
        try
        {
            String path=getClass().getResource("/").getPath().replaceAll("%20", " ");
            File dir = new File(path+"../../src");            
            CodeGenerator codeGeneration = new CodeGenerator();
            codeGeneration.generateCode("swb",true,dir);
            System.out.println("Generación de clases completa");
        }
        catch ( CodeGeneratorException cge )
        {
            fail(cge.getMessage());
        }
    }

    @Test    
    public void generateCodeSwbxf()
    {
        try
        {
            String path=getClass().getResource("/").getPath().replaceAll("%20", " ");
            File dir = new File(path+"../../../SWBModel/src");            
            CodeGenerator codeGeneration = new CodeGenerator();
            codeGeneration.generateCode("swbxf",false,dir);
            System.out.println("Generación de clases completa");
        }
        catch ( CodeGeneratorException cge )
        {
            fail(cge.getMessage());
        }
    }

}
