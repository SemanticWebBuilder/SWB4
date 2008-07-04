/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.rdf;

import com.hp.hpl.jena.vocabulary.VCARD;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.semanticwb.rdf.imp.PropertyImp;

/**
 *
 * @author Jei
 */
public class RDFTest1 {

    public RDFTest1() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
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
    
    @Test
    public void test()
    {
        
       String personURI    = "http://somewhere/JohnSmith";
       String fullName     = "John Smith";        
       
       // create an empty model
       ModelFactory fact=new ModelFactory(); 
       Model model = fact.createDefaultModel();
       Assert.assertNotNull(model);

       // create the resource
       Resource johnSmith = model.createResource(personURI);
       Assert.assertNotNull(johnSmith);

       // add the property
       johnSmith.addProperty(new PropertyImp(VCARD.FN), fullName);   
       
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}

}