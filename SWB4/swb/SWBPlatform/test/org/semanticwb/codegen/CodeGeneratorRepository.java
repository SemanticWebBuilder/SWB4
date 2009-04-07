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
public class CodeGeneratorRepository
{

    public CodeGeneratorRepository()
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

    @Test
    public void generateRepository()
    {
        try
        {
            String path = getClass().getResource("/").getPath().replaceAll("%20", " ");
            File dir = new File(path + "../../../SWBModel/src");
            String sPakage = "org.semanticwb.repository";
            CodeGenerator codeGeneration = new CodeGenerator(dir, sPakage);
            codeGeneration.generateCode("nt", false);
            System.out.println("Generación de clases completa");
        }
        catch (CodeGeneratorException cge)
        {
            fail(cge.getMessage());
        }
    }
}