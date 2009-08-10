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
 * 
 * @author serch
 */
public class CodeGeneratorCommunity {

    @BeforeClass
    public static void setUpClass() throws Exception
    {
        SWBPlatform.setUseDB(false);
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

    @Test
    public void generateCatalogs()
    {
        try
        {
            String path = getClass().getResource("/").getPath().replaceAll("%20", " ");
            File dir = new File(path + "../../../SWBModel/src");
            CodeGenerator codeGeneration = new CodeGenerator();
            codeGeneration.generateCode("swbc", false, dir);
            System.out.println("Generación de clases completa");
        }
        catch (CodeGeneratorException cge)
        {
            fail(cge.getMessage());
        }
    }

    @Test
    public void generateComunity()
    {
        try
        {
            String path = getClass().getResource("/").getPath().replaceAll("%20", " ");
            File dir = new File(path+"../../../SWBCommunity/src");
            CodeGenerator codeGeneration = new CodeGenerator();
            codeGeneration.generateCode("swbcomm", false, dir);
            System.out.println("Generación de clases completa");
        }
        catch (CodeGeneratorException cge)
        {
            fail(cge.getMessage());
        }
    }


}

