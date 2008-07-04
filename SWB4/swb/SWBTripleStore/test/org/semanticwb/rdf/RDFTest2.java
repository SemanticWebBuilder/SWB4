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
public class RDFTest2 {

    public RDFTest2() {
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
        String givenName    = "John";
        String familyName   = "Smith";     
        String fullName     = givenName + " " + familyName;
       
       // create an empty model
       ModelFactory fact=new ModelFactory(); 
       Model model = fact.createDefaultModel();
       Assert.assertNotNull(model);

        // create the resource
        //   and add the properties cascading style
        Resource johnSmith = model.createResource(personURI)
                 .addProperty(new PropertyImp(VCARD.FN), fullName)
                 .addProperty(new PropertyImp(VCARD.N), 
                              model.createResource()
                                   .addProperty(new PropertyImp(VCARD.Given), givenName)
                                   .addProperty(new PropertyImp(VCARD.Family), familyName));

        Assert.assertNotNull(johnSmith);
        
        // list the statements in the graph
        StmtIterator iter = model.listStatements();
        Assert.assertNotNull(iter);        
        
        // print out the predicate, subject and object of each statement
        while (iter.hasNext()) {
            Statement stmt      = iter.nextStatement();         // get next statement
            Resource  subject   = stmt.getSubject();   // get the subject
            Property  predicate = stmt.getPredicate(); // get the predicate
            RDFNode   object    = stmt.getObject();    // get the object
            
            Assert.assertNotNull(subject);        
            Assert.assertNotNull(predicate);        
            Assert.assertNotNull(object);        
        
            System.out.print(subject.toString());
            System.out.print(" " + predicate.toString() + " ");
            if (object instanceof Resource) {
                System.out.print(object.toString());
            } else {
                // object is a literal
                System.out.print(" \"" + object.toString() + "\"");
            }
            System.out.println(" .");
        }       
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}

}