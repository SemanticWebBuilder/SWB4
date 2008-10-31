/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb;

import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.sparql.util.IndentedWriter;
import java.util.Iterator;
import org.junit.*;
import org.semanticwb.platform.SemanticVocabulary;

/**
 *
 * @author Jei
 */
public class TestQuery {
    private Logger log=SWBUtils.getLogger(TestQuery.class);

    static public final String NL = System.getProperty("line.separator") ; 

    public TestQuery()
    {
    }

    @BeforeClass
    public static void setUpClass() throws Exception
    {
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
    public void test()
    {
        long time=System.currentTimeMillis();
        
        Model model=SWBPlatform.getSemanticMgr().getOntology().getRDFOntModel();
        
        // First part or the query string 
        String prolog = "PREFIX swb: <"+SemanticVocabulary.URI+">" ;
        prolog+= "PREFIX rdf: <"+SemanticVocabulary.RDF_URI+">" ;
        prolog+= "PREFIX rdfs: <"+SemanticVocabulary.RDFS_URI+">" ;
        
        // Query string.
        String queryString = prolog + NL +
            "SELECT ?title ?class WHERE {?x swb:title ?title. ?x rdf:type swb:WebPage}" ; 
        
        Query query = QueryFactory.create(queryString) ;
        // Print with line numbers
        query.serialize(new IndentedWriter(System.out,true)) ;
        System.out.println() ;
        
        // Create a single execution of this query, apply to a model
        // which is wrapped up as a Dataset
        
        QueryExecution qexec = QueryExecutionFactory.create(query, model) ;
        // Or QueryExecutionFactory.create(queryString, model) ;

        try {
            // Assumption: it's a SELECT query.
            ResultSet rs = qexec.execSelect() ;
            
            Iterator<String> it=rs.getResultVars().iterator();
            while(it.hasNext())
            {
                System.out.print(it.next()+"\t");
            }
            System.out.println();
            
            // The order of results is undefined. 
            for ( ; rs.hasNext() ; )
            {
                QuerySolution rb = rs.nextSolution() ;

                
                it=rs.getResultVars().iterator();
                while(it.hasNext())
                {
                    String name=it.next();
                    RDFNode x = rb.get(name) ;
                    System.out.print(x+"\t");
                }
                System.out.println();
                
                // Get title - variable names do not include the '?' (or '$')
//                RDFNode x = rb.get("x") ;
//                RDFNode title = rb.get("title") ;
//                
//                System.out.println("x:"+x+" title:"+title) ;
//                // Check the type of the result value
//                if ( x.isLiteral() )
//                {
//                    Literal titleStr = (Literal)x  ;
//                    System.out.println("    "+titleStr) ;
//                }
//                else
//                    System.out.println("Strange - not a literal: "+x) ;
                    
            }
        }
        finally
        {
            // QueryExecution objects should be closed to free any system resources 
            qexec.close() ;
        }
        
        
        System.out.println("Time:"+(System.currentTimeMillis()-time));
    }
}
