/**  
* SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración, 
* colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de 
* información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes 
* fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y 
* procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación 
* para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite. 
* 
* INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’), 
* en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición; 
* aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software, 
* todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización 
* del SemanticWebBuilder 4.0. 
* 
* INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita, 
* siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar 
* de la misma. 
* 
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente 
* dirección electrónica: 
*  http://www.semanticwebbuilder.org
**/ 
 
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb;

import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
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
        String base=SWBUtils.getApplicationPath();
        SWBPlatform.createInstance();
        SWBPlatform.getSemanticMgr().initializeDB();
        SWBPlatform.getSemanticMgr().addBaseOntology(base+"../../../web/WEB-INF/owl/swb.owl");
        SWBPlatform.getSemanticMgr().addBaseOntology(base+"../../../web/WEB-INF/owl/swb_rep.owl");
        SWBPlatform.getSemanticMgr().addBaseOntology(base+"../../../web/WEB-INF/owl/office.owl");
        SWBPlatform.getSemanticMgr().loadBaseVocabulary();
        SWBPlatform.getSemanticMgr().loadDBModels();
        SWBPlatform.getSemanticMgr().getOntology().rebind();
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
    //@Test
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
            "SELECT ?title ?desc WHERE {?x swb:title ?title. ?x rdf:type swb:WebPage. ?x swb:description ?desc}" ;
        
        Query query = QueryFactory.create(queryString) ;
        // Print with line numbers
        //query.serialize(new IndentedWriter(System.out,true));
        query.serialize(System.out) ;
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

    @Test
    public void testu()
    {
        long time=System.currentTimeMillis();

        Model model=SWBPlatform.getSemanticMgr().getOntology().getRDFOntModel();

        // First part or the query string
        String prolog = "PREFIX swb: <"+SemanticVocabulary.URI+">" ;
        prolog+= "PREFIX rdf: <"+SemanticVocabulary.RDF_URI+">" ;
        prolog+= "PREFIX rdfs: <"+SemanticVocabulary.RDFS_URI+">" ;

        // Query string.
        //"SELECT ?title ?class WHERE {?x swb:title ?title. ?x rdf:type swb:WebPage}"

        String queryString = prolog + NL +
            "SELECT ?x ?fname ?lname ?slname ?mail ?login WHERE {?x rdf:type swb:User. " +
            "?x swb:usrFirstName ?gfn .   FILTER regex(?gfn, \"\", \"i\"). " +
            "?x swb:usrLastName ?gln.   FILTER regex(?gln, \"\", \"i\"). " +
            "?x swb:usrSecondLastName ?gsln.   FILTER regex(?gsln, \"\", \"i\"). " +
            "?x swb:usrEmail ?gml.   FILTER regex(?gml, \"\", \"i\"). " +
            "?x swb:usrLastName ?lname. " +
            "?x swb:usrFirstName ?fname. " +
            "?x swb:usrSecondLastName ?slname. " +
            "?x swb:usrEmail ?mail. " +
            "?x swb:usrLogin ?login " +
            "}" ;

        Query query = QueryFactory.create(queryString) ;
        // Print with line numbers
        //query.serialize(new IndentedWriter(System.out,true)) ;
        query.serialize(System.out) ;
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
