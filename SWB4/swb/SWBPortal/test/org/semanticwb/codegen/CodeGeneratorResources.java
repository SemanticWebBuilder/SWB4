/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.codegen;

import com.hp.hpl.jena.rdf.model.NsIterator;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.platform.SemanticModel;
import static org.junit.Assert.*;

/**
 *
 * @author victor.lorenzana
 */
public class CodeGeneratorResources
{

    public CodeGeneratorResources()
    {
    }

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

    public Iterator<SemanticModel> getModels(String sPakage, String models[], File basepath)
    {
        ArrayList arr=new ArrayList();
        for(int i=0;i<models.length;i++)
        {
            String m=models[i];
            File mf=null;
            if(m.startsWith("/"))
            {
                mf=new File(basepath,m);
            }else
            {
                mf=new File(basepath,SWBUtils.TEXT.replaceAll(sPakage, ".", "/")+"/"+m);
            }
            System.out.println("OWL File:"+mf.toString());
            SemanticModel model=SWBPlatform.getSemanticMgr().readRDFFile(m, mf.toString());
            if(model==null)System.out.println("File Not Found:"+mf.toString());
            if(model!=null)arr.add(model);
        }
        return arr.iterator();
    }

    @Test
    public void generateCP()
    {
        try
        {
            String sPakage = "org.semanticwb.portal.resources.sem";
            String models[]={"SWBBanner.owl"};
            String path=getClass().getResource("/").getPath().replaceAll("%20", " ");
            //System.out.println("path:"+path);
            File dir = new File(path+"../../src");
            //System.out.println("dir:"+dir);

            Iterator<SemanticModel> it=getModels(sPakage, models, dir);
            while(it.hasNext())
            {
                SemanticModel model=it.next();
                NsIterator it2=model.getRDFModel().listNameSpaces();
                while(it2.hasNext())
                {
                    String ns=it2.nextNs();
                    System.out.println("Ns:"+ns);
                }
            }


//            CodeGenerator codeGeneration = new CodeGenerator(dir, sPakage);
//            codeGeneration.generateCode("swbres",false);
            System.out.println("Generación de clases completa");
        }
        catch ( Exception cge )
        {
            fail(cge.getMessage());
        }
    }

}
