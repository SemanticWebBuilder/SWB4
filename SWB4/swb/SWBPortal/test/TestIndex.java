
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.query.larq.IndexBuilderString;
import com.hp.hpl.jena.query.larq.IndexBuilderSubject;
import com.hp.hpl.jena.query.larq.IndexLARQ;
import com.hp.hpl.jena.query.larq.LARQ;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.sparql.util.StringUtils;
import java.io.FileOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import org.junit.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Dns;
import org.semanticwb.model.Language;
import org.semanticwb.model.ResourceType;
import org.semanticwb.model.Resource;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.Template;
import org.semanticwb.model.TemplateGroup;
import org.semanticwb.model.TemplateRef;
import org.semanticwb.model.UserRepository;
import org.semanticwb.model.User;
import org.semanticwb.model.VersionInfo;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;


/**
 *
 * @author Jei
 */
public class TestIndex {
    

    public TestIndex()
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
    public void Test1()
    {
        Model model=SWBPlatform.getSemanticMgr().getOntology().getRDFOntModel();

        // -- Read and index all literal strings.
        IndexBuilderString indexBuilder = new IndexBuilderString();
        // -- Create an index based on existing statements
        indexBuilder.indexStatements(model.listStatements()) ;
        // -- Finish indexing
        indexBuilder.closeWriter() ;


        // -- Create the access index
        IndexLARQ index = indexBuilder.getIndex() ;

//        NodeIterator nIter = index.searchModelByIndex("+javier");
//        for ( ; nIter.hasNext() ; )
//        {
//            // if it's an index storing literals ...
//            RDFNode node = nIter.nextNode() ;
//            System.out.println("Node:"+node);
//        }

        String searchString = "+sección" ;

        String queryString = StringUtils.join("\n", new String[]{
            "PREFIX xsd:    <http://www.w3.org/2001/XMLSchema#>" ,
            "PREFIX :       <http://example/>" ,
            "PREFIX pf:     <http://jena.hpl.hp.com/ARQ/property#>",
            "PREFIX swb:     <http://www.semanticwebbuilder.org/swb4/ontology#>",
            "SELECT * {" ,
            "    (?lit ?score ) pf:textMatch '"+searchString+"'.",
            "    ?doc ?p ?lit",
            "}"
        }) ;

        // Make globally available
        //LARQ.setDefaultIndex(index) ;

        Query query = QueryFactory.create(queryString) ;
        query.serialize(System.out) ;
        System.out.println();

        QueryExecution qExec = QueryExecutionFactory.create(query, model) ;
        LARQ.setDefaultIndex(qExec.getContext(), index) ;
        ResultSetFormatter.out(System.out, qExec.execSelect(), query) ;
        qExec.close() ;
        

        index.close();

    }

}
