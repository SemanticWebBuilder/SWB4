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
 
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import junit.framework.TestCase;

import com.hp.hpl.jena.db.DBConnection;
import com.hp.hpl.jena.db.IDBConnection;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.ModelMaker;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.sparql.algebra.Algebra;
import com.hp.hpl.jena.sparql.algebra.Op;
import com.hp.hpl.jena.sparql.algebra.op.OpBGP;
import com.hp.hpl.jena.sparql.algebra.op.OpFilter;
import com.hp.hpl.jena.sparql.core.BasicPattern;
import com.hp.hpl.jena.sparql.core.Var;
import com.hp.hpl.jena.sparql.engine.QueryIterator;
import com.hp.hpl.jena.sparql.engine.http.QueryEngineHTTP;
import com.hp.hpl.jena.sparql.expr.E_LessThan;
import com.hp.hpl.jena.sparql.expr.Expr;
import com.hp.hpl.jena.sparql.expr.ExprVar;
import com.hp.hpl.jena.sparql.expr.NodeValue;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

/**
 * Unit test for the FreeMarker extension.
 *
 * @author Jerome Louvel (contact@noelios.com)
 */
public class TestDBPedia extends TestCase {
    public TestDBPedia(String n) {
		super(n);
		// TODO Auto-generated constructor stub
	}

	public void setUp() throws Exception {
	}

	private String BASE = "http://example/" ;

	public com.hp.hpl.jena.graph.Node getJenaNode(String id) {
		return com.hp.hpl.jena.graph.Node.createURI(BASE+id);
	}

	public void testQuery() throws IOException, ClassNotFoundException {

//   	 Create an empty in-memory model and populate it from the graph
    	Model model = ModelFactory.createMemModelMaker().createModel("test");


		BasicPattern bp = new BasicPattern() ;
		Var var_x = Var.alloc("x") ;

		Var var_z = Var.alloc("z") ;



		// ---- Build expression
		com.hp.hpl.jena.graph.Node uri =
			com.hp.hpl.jena.graph.Node.createURI(BASE+"p");
		bp.add(new Triple(var_x,  uri, var_z)) ;

		Op op = new OpBGP(bp) ;

		Expr expr = new E_LessThan(new ExprVar(var_z), NodeValue.makeNodeInteger(2)) ;

		//op = OpFilter.filter(expr, op) ;

		QueryIterator qi = Algebra.exec(op, model);


	   	//File file = new File("test_resources/caro.owl");
	   	File file = new File("test_resources/go-qname.rdf");
	   	//    	 Open the bloggers RDF graph from the filesystem
    	InputStream in = new FileInputStream(file);

    	System.out.println("reading");

    	model.read(in,null); // null base URI, since model URIs are absolute
    	in.close();
       	System.out.println("read");

//    	 Create a new query
    	String queryString =
    		"PREFIX foaf: <http://xmlns.com/foaf/0.1/> " +
    		"SELECT ?x ?r ?y " +
    		"WHERE {" +
    		"      ?x ?r ?y . " +
    		"      }";

    	Query query = QueryFactory.create(queryString);

//    	 Execute the query and obtain results
    	QueryExecution qe = QueryExecutionFactory.create(query, model);
    	ResultSet results = qe.execSelect();
    	// Model resultModel = qexec.execConstruct() ;
    	   for ( ; results.hasNext() ; )
    	    {
    	      QuerySolution soln = results.nextSolution() ;
      		   System.out.println("result="+soln);
      		   RDFNode x = soln.get("x") ;       // Get a result variable by name.
      		 RDFNode r = soln.getResource("r") ; // Get a result variable - must be a resource
      		RDFNode y = soln.get("y") ;   // Get a result variable - must be a literal
      		System.out.println(x+"  --< "+r+" >-- "+y);
    	    }


//    	 Output query results
//    	ResultSetFormatter.out(System.out, results, query);

//    	 Important - free up resources used running the query
    	qe.close();

       	System.out.println("creating ontModel");
       	OntModel ontModel = ModelFactory.createOntologyModel();

      	System.out.println("reading ontModel");
      	in = new FileInputStream(file);
      	ontModel.read(in,null);
      	in.close();
    	ExtendedIterator cl = ontModel.listClasses();
    	for ( ; cl.hasNext() ;) {
    		OntClass c = (OntClass) cl.next();
    		System.out.println(c+" "+c.getLabel(null));

    	}

    	assertTrue(true);

    	/*
       	queryString =
    		"PREFIX go: <http://purl.org/obo/owl/GO#> " +
    		"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
    		"SELECT ?class" +
    		"WHERE { ?class rdfs:subClassOf go:GO_0008150 } ";
    		*/
       	queryString =
    		"PREFIX go: <http://purl.org/obo/owl/GO#> " +
    		"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
    		"CONSTRUCT { go:GO_0008150 rdfs:label ?label } " +
    		"WHERE { go:GO_0008150 rdfs:label ?label } ";

       	String url = "http://hcls.deri.ie";
       	//String url = "http://dbpedia.org/";
       	query = QueryFactory.create(queryString);
    	QueryExecution remote = QueryExecutionFactory.sparqlService(url,
    			query);
    	//QueryEngineHTTP req = QueryExecutionFactory.createServiceRequest(url, query);
    	//req.execSelect();
    	//results = remote.execConstruct();
    	//model = remote.execConstruct();
    	// Model resultModel = qexec.execConstruct() ;
    	/*
    	   for ( ; results.hasNext() ; )
    	    {
    	      QuerySolution soln = results.nextSolution() ;
      		   System.out.println("result="+soln);
     	    }
    	 */

//    	 database URL
    	String M_DB_URL         = "jdbc:postgresql://localhost/jenatest";

//    	 User name

    	String M_DB_USER        = "cjm";

//    	 Password

    	String M_DB_PASSWD      = "";

//    	 Database engine name

    	String M_DB = "PostgreSQL";

//    	 JDBC driver

    	//String M_DBDRIVER_CLASS = "com.mysql.jdbc.Driver";
    	String M_DBDRIVER_CLASS = "org.postgresql.Driver";

//    	 load the the driver class
    	Class.forName(M_DBDRIVER_CLASS);


//    	 create a database connection
    	IDBConnection conn = new DBConnection(M_DB_URL, M_DB_USER, M_DB_PASSWD, M_DB);


//    	 create a model maker with the given connection parameters
    	ModelMaker maker = ModelFactory.createModelRDBMaker(conn);

//    	 create a default model
    	Model defModel = maker.createDefaultModel();

    	defModel.add(model);


//    	 Open existing default model

    	//Model defModel = maker.openModel(M_DBDRIVER_CLASS);



//    	 or create a named model
    	//Model nmModel = maker.createModel("MyNamedModel");



//    	 or open a previously created named model
    	//Model prvModel = maker.openModel("AnExistingModel");
    }

}
