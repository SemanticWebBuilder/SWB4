
import com.hp.hpl.jena.graph.Graph;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import java.util.Iterator;
import org.junit.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import org.semanticwb.model.WebSite;
import org.semanticwb.rdf.RemoteGraph;

/**
 *
 * @author Jei
 */
public class TestDBPedia2 {

    public TestDBPedia2() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        //SWBPlatform.createInstance(null);
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

    //@Test
    public void QueryJena() {
        //try {

       	String queryString =
    		"PREFIX dc:      <http://purl.org/dc/elements/1.1/> " +
    		"CONSTRUCT { $book $prop $title } " +
    		"WHERE  " +
      		"{ $book $prop $title } ";
       	//String url = "http://dbpedia.org/";
        String url = "http://www.sparql.org/books";
       	Query query = QueryFactory.create(queryString);
    	QueryExecution remote = QueryExecutionFactory.sparqlService(url,query);
        Model model=remote.execConstruct();
        model.write(System.out);
    }

    //@Test
    public void QueryDBPediaConstruct() {
       	String queryString =
            "PREFIX dbpedia:      <http://dbpedia.org/resource/> " +
            "CONSTRUCT { dbpedia:Mexico $prop $obj }" +
    		"WHERE  " +
            "{dbpedia:Mexico ?prop ?obj}";
       	String url = "http://dbpedia.org/sparql";
       	Query query = QueryFactory.create(queryString);
    	QueryExecution qexec = QueryExecutionFactory.sparqlService(url,query);
        Model model=qexec.execConstruct();
        model.write(System.out);
    }

    @Test
    public void QueryDBPediaDescribe() {
       	String queryString =
            "PREFIX dbpedia:      <http://dbpedia.org/resource/> " +
            "PREFIX rdf:      <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
            //"DESCRIBE  ?x " +
            "DESCRIBE  dbpedia:Mexico ";
    		//"WHERE  " +
            //"{?x rdf:type <http://dbpedia.org/class/yago/OECDMemberEconomies>}";
       	String url = "http://dbpedia.org/sparql";
       	Query query = QueryFactory.create(queryString);
    	QueryExecution qexec = QueryExecutionFactory.sparqlService(url,query);
        Model model=qexec.execDescribe();
        model.write(System.out);
    }

    //@Test
    public void QueryDBPediaQuery() {
       	String queryString =
//            "PREFIX dbpedia:      <http://dbpedia.org/resource/> " +
//            "SELECT $prop $obj " +
//    		"WHERE  " +
//            "{dbpedia:Mexico ?prop ?obj}";
            "PREFIX  rdfs:    <http://www.w3.org/2000/01/rdf-schema#> " +
            "PREFIX  rdf:     <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
            "PREFIX  dbp-ont: <http://dbpedia.org/ontology/> " +
            "PREFIX  dbp-pro: <http://dbpedia.org/property/> " +
            "select ?pais ?presidente " +
            "where { " +
            "?x rdf:type dbp-ont:Country . " +
            "?x dbp-pro:nativeName ?pais . " +
            "?x dbp-pro:leaderName ?presidente " +
            "} ";

       	String url = "http://dbpedia.org/sparql";
       	Query query = QueryFactory.create(queryString);

        //QueryExecution qexec = QueryExecutionFactory.create(query, model) ;
    	QueryExecution qexec = QueryExecutionFactory.sparqlService(url,query);

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
                    if(x.isLiteral())System.out.print(x.asNode().getLiteralLanguage());
                }
                System.out.println();

            }
        }
        finally
        {
            // QueryExecution objects should be closed to free any system resources
            qexec.close() ;
        }

    }

    //@Test
    public void testNewGraph()
    {
       	String uri = "http://dbpedia.org/sparql";
        //String uri = "http://www.sparql.org/books";
        Graph graph=new RemoteGraph(uri);
        Model model=ModelFactory.createModelForGraph(graph);
        //Iterator<Statement> it=model.listStatements(null, model.getProperty("http://purl.org/dc/elements/1.1/title"),(String)null);
        //System.out.println(model.contains(model.getResource("http://example.org/book/book5"),null));
        Resource res=model.getResource("http://dbpedia.org/resource/Mexico");

        printStatements(model.listStatements(res,null,(String)null));

        Property lat=model.getProperty("http://www.w3.org/2003/01/geo/wgs84_pos#lat");
        Property lon=model.getProperty("http://www.w3.org/2003/01/geo/wgs84_pos#long");
        Property type=model.getProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
        Statement st=res.getProperty(lat);
        System.out.println("Latitud:"+st.getString());
        st=res.getProperty(lon);
        System.out.println("Longitud:"+st.getString());


        //printStatements(model.listStatements(res,type,(String)null));

        //printStatements(model.listStatements(null,type,model.getResource("http://www.w3.org/2002/07/owl#Ontology")));

        System.out.println("Paises");
        Resource country=model.getResource("http://dbpedia.org/ontology/Country");
        printStatements(model.listStatements(null,type,country));

    }

    public void printStatements(Iterator<Statement> it)
    {
        while(it.hasNext())
        {
            Statement st=it.next();
            System.out.println("ret:"+st.getSubject()+" "+st.getPredicate()+" "+st.getObject());
        }
    }

}

/*
<rdf:RDF
    xmlns:dbpedia-owl="http://dbpedia.org/ontology/"
    xmlns:foaf="http://xmlns.com/foaf/0.1/"
    xmlns:j.0="http://umbel.org/umbel/sc/"
    xmlns:georss="http://www.georss.org/georss/"
    xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
    xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#"
    xmlns:owl="http://www.w3.org/2002/07/owl#"
    xmlns:j.1="http://dbpedia.org/class/yago/"
    xmlns:geo="http://www.w3.org/2003/01/geo/wgs84_pos#"
    xmlns:skos="http://www.w3.org/2004/02/skos/core#"
    xmlns:dbpprop="http://dbpedia.org/property/"
    xmlns:j.2="http://sw.opencyc.org/2008/06/10/concept/" >
  <rdf:Description rdf:about="http://dbpedia.org/resource/Alejandro_Garc%C3%ADa">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Quiroga%2C_Michoac%C3%A1n">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Monarcas_Morelia">
    <dbpprop:ground rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Erick_Espinoza">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Carlos_Alberto_S%C3%A1nchez_Romero">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Sergio_Bernal">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Los_Temerarios">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jaibos_Tampico_Madero">
    <dbpprop:ground rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Luis_Hern%C3%A1ndez_%28footballer%29">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Buick_Century">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/1959_Mexico_hurricane">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Paseo_Reforma">
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Dino_Cazares">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:born rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Valentino_Lan%C3%BAs">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHAAA">
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Julio_C%C3%A9sar_Gonz%C3%A1lez">
    <dbpprop:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/General_Escobedo">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mexican_Hairless_Dog">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Horacio_Llamas">
    <dbpprop:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Aladia">
    <dbpedia-owl:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mexico_national_cricket_team">
    <dbpprop:countryName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Kat_Von_D">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Patricia_Navidad">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Football_%28soccer%29_at_the_2005_Maccabiah_Games_-_Men%27s_tournament/footballbox3">
    <dbpprop:team rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mart%C3%ADn_Galv%C3%A1n">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Oto-Manguean_languages">
    <dbpprop:region rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mariana_Torres">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Northern_Mexico">
    <dbpprop:redirect rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Cuitlatec_language">
    <dbpedia-owl:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/International_Maize_and_Wheat_Improvement_Center">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Meksiko">
    <dbpprop:redirect rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mountains_of_Mexico">
    <dbpprop:redirect rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mercedes-Benz_Vito">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/1971_Davis_Cup">
    <dbpprop:rd1t1Loc rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:rd3t1Loc rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:rd2t1Loc rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:venue rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEDRD-AM">
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Barrera_de_amor">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Andr%C3%A9s_Henestrosa">
    <dbpprop:office rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ignacio_L%C3%B3pez_Tarso">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Antonio_Osuna">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Zapotl%C3%A1n_el_Grande">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Spanish_Civil_War">
    <dbpprop:combatant rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEMCA">
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jacqueline_Bracamontes">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Rosenda_Monteros">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/The_Husband_That_Is_Necessary_To_Follow">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEFD">
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/La_Guerra_de_los_Chistes">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Black_Cat_%28wrestler%29">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Juan_Bautista_Ceballos">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Emilio_Portes_Gil">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mercedes-Benz_GL-Class">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Cadillac_Escalade">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Universidad_Aut%C3%B3noma_de_Nuevo_Le%C3%B3n">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jacob_Vargas">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mayan_sign_languages">
    <dbpprop:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Elisa_N%C3%A1jera">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tepehu%C3%A1n_language">
    <dbpprop:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/David_Zepeda">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Snowball_Express">
    <dbpprop:released rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/La_perla">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHPP">
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Christian_Vald%C3%A9z">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Kinky_%28band%29">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Estadio_Miguel_Aleman">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Plastilina_Mosh">
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/The_Black_Parade_Is_Dead%21/tracklist1">
    <dbpprop:headline rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mexican_Grand_Prix">
    <dbpprop:name rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Oscar_Robles">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Esperanza_Baur">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Adolfo_Dollero">
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ra%C3%BAl_Velasco">
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/A_Thousand_Clouds_of_Peace">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHRR">
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHTLAX-FM">
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/La_Madrastra">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jorge_Arce">
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:home rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Compa%C3%B1%C3%ADa_Nacional_de_Chocolates">
    <dbpedia-owl:areaServed rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:areaServed rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/San_Diego_and_Arizona_Eastern_Railway">
    <dbpprop:locale rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jacobo_Zabludovsky">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Lorena_Ochoa">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Avolar">
    <dbpedia-owl:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Area_code_442">
    <dbpprop:s rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jos%C3%A9_Mar%C3%ADa_Morelos">
    <dbpedia-owl:allegiance rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:allegiance rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/A%C3%A9reo_Servicio_Guerrero">
    <dbpprop:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Instituto_Tecnol%C3%B3gico_de_Saltillo">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Francisco_Javier_Torres">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Carlos_Vela">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/2007_U-20_World_Cup_CONCACAF_qualifying_tournament/footballbox2">
    <dbpprop:stadium rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Agust%C3%ADn_Jer%C3%B3nimo_de_Iturbide_y_Huarte">
    <dbpprop:throne rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Bristol_Boarhound">
    <dbpprop:primaryUser rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XECAV-AM">
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Estadio_Hidalgo">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/El_Chich%C3%B3n">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/CONCACAF_Champions%27_Cup_2008/footballbox14">
    <dbpprop:stadium rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Pilar_Cazares">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Interjet">
    <dbpprop:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Miguel_Caldera">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHVTH">
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Seri_language">
    <dbpprop:region rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:region rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Pierre_Colas">
    <dbpprop:residence rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Maksimilijan_Vanka">
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Sativa_Rose">
    <dbpedia-owl:ethnicity rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Aerounion_-_Aerotransporte_de_Carga_Union">
    <dbpprop:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Battle_of_Santa_Fe">
    <dbpprop:combatant rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tarahumara_language">
    <dbpedia-owl:region rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:region rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Fernando_Morales">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mosby_Parsons">
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Dodge_Spirit">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Elgabry_Rangel">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Rodrigo_Lopez_%28soccer%29">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jakaltek_language">
    <dbpprop:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Chihuahua_Institute_of_Technology">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/United_states_of_mexico">
    <dbpprop:redirect rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Battle_of_Huamantla">
    <dbpprop:combatant rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Uto-Aztecan_languages">
    <dbpprop:region rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Map_of_mexico">
    <dbpprop:redirect rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Cuca_%28band%29">
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mois%C3%A9s_Mu%C3%B1oz">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/El_Privilegio_de_Mandar">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Cuajimalpa">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/William_Maclure">
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Apango">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEBJ-AM">
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tropical_Storm_Lowell_%282008%29">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Pancho_Villa">
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:allegiance rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Roberto_Hernandez%2C_Jr.">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/1967_Davis_Cup">
    <dbpprop:rd1t2Loc rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:rd2t1Loc rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Rodrigo_de_la_Cadena">
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Edgar_de_Evia">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/NBC">
    <dbpprop:available rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Miguel_%C3%81ngel_Biaggio">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Chilapa_de_%C3%81lvarez">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Estadio_Nuevo_Laredo">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Anarchism_in_America_%28film%29">
    <dbpedia-owl:cinematography rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jorge_de_la_Rosa">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHCJE-TV">
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/1966_Davis_Cup">
    <dbpprop:rd2t1Loc rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Javier_Aguirre">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Salvador_Elizondo">
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Toronto_Blue_Jays_all-time_roster/player7">
    <dbpprop:playerProperty rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/James_Wilkinson">
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/El_B%C3%BAfalo_de_la_Noche_%28film%29">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Federico_Jordan">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Economy_of_Mexico">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Pina_Pellicer">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tecnol%C3%B3gico_de_Monterrey%2C_Campus_Cuernavaca">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Universidad_Panamericana_Sede_M%C3%A9xico">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Hurricane_Henri_%281979%29">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Hurricane_Juliette_%282001%29">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Felipe_Quintero">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Drones_%28album%29">
    <dbpedia-owl:recordplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:recorded rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/1954_Davis_Cup">
    <dbpprop:rd1t3Loc rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:rd3t1Loc rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Daewoo_Lacetti">
    <dbpedia-owl:predecessor rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Erasmo_Solorzano">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Sears_Roebuck_%28Mexico%29">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ang%C3%A9lica_Vale">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tlalpujahua">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Hurricane_Emily_%282005%29">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Omar_Trujillo">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Nissan_Hardbody_Truck">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Arturo_de_C%C3%B3rdova">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Querida_Enemiga">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Daniela_Luj%C3%A1n">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:born rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/El_Texano">
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Aut%C3%B3dromo_Hermanos_Rodr%C3%ADguez">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Unidos_Por_La_Paz">
    <dbpedia-owl:recordplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:recorded rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Daniel_M%C3%A1rquez">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/El_Universal_%28Mexico_City%29">
    <dbpprop:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Isela_Vega">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Armer%C3%ADa%2C_Colima">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/A_Day_Without_a_Mexican">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Francisco_Barn%C3%A9s_de_Castro">
    <dbpprop:residence rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Battle_of_Calder%C3%B3n_Bridge">
    <dbpedia-owl:place rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:place rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:combatant rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Rebelde">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Juan_Diego">
    <dbpprop:canonizedPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:canonized_place rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:majorShrine rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:majorShrine rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/V%C3%ADctor_Bravo_Ahuja">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Xalapa%2C_Veracruz">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Cuencam%C3%A9%2C_Durango">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ulises_Sol%C3%ADs">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Gabriel_Porras">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Federacion_Mexicana_de_Radio_Experimentadores">
    <dbpprop:regionServed rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Sonico.com">
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Dalia_Hern%C3%A1ndez">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Alejandro_Castro">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Chatino">
    <dbpprop:popplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ensenada_Center_for_Scientific_Research_and_Higher_Education">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Sharis_Cid">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Gabriel_Palmeros">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Hurricane_Barry_%281983%29">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Philippine_International_Convention_Center">
    <dbpprop:before rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Univision">
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEJPA-AM">
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Aerodavinci">
    <dbpedia-owl:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/San_Jos%C3%A9_Villa_de_Allende">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Rafael_Figueroa">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Gabriel_Pareyon">
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Karla_Mart%C3%ADnez">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Samuel_Morales">
    <dbpedia-owl:ethnicity rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Arte_para_aprender_la_lengua_mexicana">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Una_mujer_sin_amor">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Julio_Cesar_Chavez_vs._Meldrick_Taylor">
    <dbpprop:hometown rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Vocabulario_en_lengua_castellana_y_mexicana">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/M%C3%A9xico">
    <dbpprop:redirect rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mercedes-Benz_CLS-Class">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Marcario_Garcia">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/%C3%93scar_Larios">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEPF-AM">
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Alberto_Agnesi">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Aventuras_En_El_Tiempo_En_Vivo">
    <dbpedia-owl:recordplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Fernando_Allende">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Catalina_Cruz">
    <dbpedia-owl:ethnicity rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Lee_Kyung_Hae">
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/CONCACAF_Champions%27_Cup_2008/footballbox6">
    <dbpprop:stadium rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Sugi_Sito">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Zacazonapan%2C_Mexico_State">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Linda_Morgan">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Julio_C%C3%A9sar_Ch%C3%A1vez">
    <dbpprop:home rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Vocabulario_manual_de_las_lenguas_castellana_y_mexicana">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Luscious_Lopez">
    <dbpedia-owl:ethnicity rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Venustiano_Carranza%2C_D.F.">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/TL_Novelas">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:locationCountry rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Economy_of_the_United_States">
    <dbpprop:exportPartners rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:importPartners rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Darina">
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Itat%C3%AD_Cantoral">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Torre_Titanium">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Alfredo_Omar_Tena">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Silvia_Pinal">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Paquita_la_del_Barrio">
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Omar_Ni%C3%B1o_Romero">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Hurricane_Stan">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/En_Nombre_del_Amor">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Adolfo_de_la_Huerta">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Brad_Will">
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Johnny_%22J%22">
    <dbpprop:born rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ely_Guerra">
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Pilar_Montenegro">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Dami%C3%A1n_Delgado">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Autonomous_University_of_Campeche">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ismael_Valdez">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:dateOfBirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Second_Battle_of_Tabasco">
    <dbpprop:combatant rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/AK-7">
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/2007_U-20_World_Cup_CONCACAF_qualifying_tournament/footballbox3">
    <dbpprop:stadium rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Perez_Prado">
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:died rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEGLO-AM">
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Vivencias">
    <dbpprop:recorded rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:recordplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Par%C3%ADcutin">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Rafael_T._Caballero">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEJAM-AM">
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Juan_Carlos_Medina">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Gustavo_Baz">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Coahuilteco_language">
    <dbpprop:region rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Fernando_Delgadillo">
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tecnol%C3%B3gico_de_Monterrey%2C_Campus_Guadalajara">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Valent%C3%ADn_G%C3%B3mez_Far%C3%ADas">
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mercedes-Benz_G-Class">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Hurricane_Hernan_%282002%29">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/1992_Mexican_Grand_Prix">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Carlos_Villagr%C3%A1n">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mexicali_%28municipality%29">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Carlos_Santana">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:born rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Angelines_Fern%C3%A1ndez">
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Juan_Ram%C3%B3n_de_la_Fuente">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Volaris">
    <dbpedia-owl:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Gonzalo_Pineda">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Victor_Serge">
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Cytherea_%28porn_star%29">
    <dbpedia-owl:ethnicity rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Hurricane_Edith_%281971%29">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Amador_Salazar">
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jean_Van_Heijenoort">
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jos%C3%A9_Alberto_Hern%C3%A1ndez">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Sara_Ram%C3%ADrez">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Priscila_Perales">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ra%C3%BAl_Lara">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Alfonso_Arau">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Nevado_de_Toluca">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Hurricane_Gert_%281993%29">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Hurricane_Dean">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Panda_%28band%29">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Lu_%28duo%29">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/1972_Davis_Cup">
    <dbpprop:rd2t1Loc rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHRYS">
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Alfonso_Garc%C3%ADa_Robles">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:citizenship rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:residence rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:residence rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Santiago_Fern%C3%A1ndez">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Villano_V">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Pat_Paulsen">
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Pinal_de_Amoles">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Villahermosa_Institute_of_Technology">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Luis_Orozco">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Salvador_Toscano">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Gerardo_Torrado">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Grupo_mayan">
    <dbpprop:redirect rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Baby_Arizmendi">
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/11%279%2201_September_11">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEQS-AM">
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Wembley_Stadium/succession_box2">
    <dbpprop:before rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/The_Broken_Spears">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Pontiac_G5">
    <dbpedia-owl:bodyStyle rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/San_Juan_del_R%C3%ADo">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Esc%C3%A1rcega_%28municipality%29">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/La_Jornada">
    <dbpprop:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ervil_LeBaron">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Braulio_Luna">
    <dbpedia-owl:nationalteam rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:nationalteam rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Bank_of_Mexico">
    <dbpprop:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:bankOf rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEOJN-AM">
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Chedraui">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Allisson_Lozz">
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ever_Guzm%C3%A1n">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Miguel_%C3%81ngel_Fraga">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Chinantla">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/University_of_Guadalajara">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Revoluci%C3%B3n_de_Amor">
    <dbpprop:recorded rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:recordplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Laura_Z%C3%BA%C3%B1iga">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHPX">
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/H%C3%A9ctor_Jim%C3%A9nez">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Hurricane_Darby_%281992%29">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Chevrolet_HHR">
    <dbpedia-owl:predecessor rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Correcaminos_UAT">
    <dbpprop:ground rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEOQ">
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Manuel_de_la_Torre">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mercury_Lynx">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/University_of_Quintana_Roo">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Rebecca_de_Alba">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/John_Herrmann">
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/ALCO_S-6">
    <dbpprop:locale rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEWX-AM">
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:format rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Popocat%C3%A9petl">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Chrysler_Neon">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Hurricane_Madeline_%281998%29">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/2005_Gran_Premio_Telmex/Tecate">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Zacateco">
    <dbpprop:popplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Elan_%28Recording_Artist%29">
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mercury_Milan">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Pedro_Armend%C3%A1riz">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Blanca_Soto">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Meztitla_Scout_Camp_School">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Dodge_Lancer">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEASM-AM">
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Hector_Rebaque">
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ismael_Valadez">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ignacio_Baez">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Edwin_Borboa">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Milton_Aguilar">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ram%C3%B3n_Ram%C3%ADrez">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Suchiate_River">
    <dbpprop:basinCountries rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Cristian_Castro">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Juan_Luque_de_Serrallonga">
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofdeath rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Authentic_Labor_Front">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Leobardo_L%C3%B3pez">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Salvador_S%C3%A1nchez">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHMDR">
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/La_Fea_M%C3%A1s_Bella">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Aiden_Bay">
    <dbpprop:born rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Antonio_Castro_Leal">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEXO-AM">
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mercedes-Benz_SLK-Class">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Gabriel_Emilio_Hern%C3%A1ndez_Ibarz%C3%A1bal">
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Sara_Maldonado">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Pacific_coast_of_Mexico">
    <dbpprop:redirect rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHCAL-FM">
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mexican_Institute_of_Sound">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Two_Mules_for_Sister_Sara">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Luc%C3%ADa_M%C3%A9ndez">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Maya_Jupiter">
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/George_W._Romney">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Bruce_Anderson_%28Medal_of_Honor%29">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/San_Pedro_Garza_Garc%C3%ADa">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Angangueo">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jaime_Ruiz">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Henry_Watkins_Allen">
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jorge_Vaca">
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:home rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Delux">
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mazahua_language">
    <dbpprop:region rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:region rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Lucero">
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/EUM">
    <dbpprop:redirect rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Manuela_%C3%8Dmaz">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Bailando_por_un_Sue%C3%B1o">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Germ%C3%A1n_Larrea_Mota-Velasco">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/National_Polytechnic_Institute">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Pl%C3%A1cido_Rodriguez">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ingrid_Coronado">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Eduardo_Y%C3%A1%C3%B1ez">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jose_Manuel_Rivera">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Geography_of_Belize">
    <dbpprop:borders rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Manuel_Sol">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Dulce_Beat">
    <dbpprop:recorded rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:recordplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Football_%28soccer%29_at_the_2005_Maccabiah_Games_-_Men%27s_tournament/footballbox5">
    <dbpprop:team rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mar%C3%ADa_Antonieta_Collins">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jorge_Sol%C3%ADs">
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Grupo_Bimbo">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Martin_Vasquez">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/LARP_Alliance">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Kristoff_Presenta">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Coca-Cola_M5">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Maya_peoples">
    <dbpprop:popplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Renault_8">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/VivaAerobus">
    <dbpprop:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jonathan_Orozco">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Allison_%28band%29">
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mirsha_Serrano">
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofdeath rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Hurricane_Allen">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tonatico">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Presidio_of_San_Francisco">
    <dbpprop:architect rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Hurricane_Erika_%282003%29">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Brianne_Murphy">
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Judaeo-Spanish">
    <dbpedia-owl:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Caf%C3%A9_Tacuba">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ivan_Contreras">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Philippines_campaign_%281944%E2%80%9345%29">
    <dbpprop:combatant rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Classical_Nahuatl">
    <dbpprop:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Valent%C3%ADn_Elizalde">
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Enrique_Meza_Enriquez">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Iztacalco">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jacqueline_Andere">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Carlos_Ochoa">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Area_code_915">
    <dbpprop:s rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:w rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Hurricane_Ileana_%282006%29">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Art_Acord">
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Nigel_Bruce">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Miguel_Peraza">
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Stephanie_Salas">
    <dbpprop:born rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Oluta_Popoluca">
    <dbpprop:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Chicomuceltec">
    <dbpprop:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Texistepec_Popoluca">
    <dbpprop:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Nuevo_Estadio_Azul">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/San_%C3%81ngel">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHTIO">
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Isaac_Romo">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jos%C3%A9_Arturo_Rivas">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEYZ">
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:format rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Omar_Ch%C3%A1vez">
    <dbpprop:home rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Roberto_Pineda">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Anahuac_University">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Octavio_Valdez">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Rabia_Sorda">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Dolphin_Discovery">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tejupilco_%28municipality%29">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Arturo_Meza">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:born rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Universidad_Aut%C3%B3noma_de_San_Luis_Potos%C3%AD">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mexicans_of_Filipino_descent">
    <dbpprop:relatedC rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Juan_Carlos_Cacho">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/1956_Davis_Cup">
    <dbpprop:rd2t2Loc rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Kitten_Natividad">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Oaxtepec%2C_Mexico">
    <dbpprop:redirect rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEPAV">
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Grupo_Garza_Ponce">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHJCI-TV">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHRTA-FM">
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Bender_%28Futurama%29">
    <dbpprop:planet rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Nora_Salinas">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Amar_Sin_L%C3%ADmites">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Te_Anhelo">
    <dbpedia-owl:recordplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:recorded rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/California_Republic">
    <dbpprop:eventStart rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Francisco_V%C3%A1zquez">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Juan_Carlos_Mosqueda">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHUTX-FM">
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Lorena_Herrera">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Juan_Andreu_Almaz%C3%A1n">
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Luis_Fernando_Tena">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Matthew_Porter_%28baseball%29">
    <dbpprop:dateOfDeath rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jose_F._Jimenez">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Francisco_Javier_Rodr%C3%ADguez">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Dulce_Desaf%C3%ADo">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/La_Loba">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Salvador_Carmona">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Elpidia_Carrillo">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Luisa_Rioja">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:ethnicity rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:ethnicity rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Marco_Capetillo">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Temoaya_%28municipality%29">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Arturo_Albarr%C3%A1n_Arellano">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Belanova">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Sarah_Stewart_%28cancer_researcher%29">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Adolfo_R%C3%ADos">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jaime_Maussan">
    <dbpprop:blocation rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jes%C3%BAs_Morales">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Eduardo_N%C3%A1jera">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Oldsmobile_Cutlass_Ciera">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jes%C3%BAs_Arellano">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Sa%C3%BAl_Hern%C3%A1ndez">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Chevrolet_Tahoe">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHW">
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/The_Night_Buffalo">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Catedral_del_Esp%C3%ADritu_Santo">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Telvista">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Maude_Versini">
    <dbpprop:shortDescription rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mexican_Sign_Language">
    <dbpprop:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Araceli_Ard%C3%B3n">
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Battle_of_Carrizal">
    <dbpprop:place rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:place rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ang%C3%A9lica_Arag%C3%B3n">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Emilio_Fern%C3%A1ndez">
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Azul_Tequila/telenovela1">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XELA-AM">
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/United_State_of_Mexico">
    <dbpprop:redirect rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/El_Hijo_del_Santo">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Zoque_languages">
    <dbpprop:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Arturo_Mu%C3%B1oz">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Anastasio_Bustamante">
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XETUMI-AM">
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Las_pellizcadas_de_Margara">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tecnol%C3%B3gico_de_Monterrey%2C_Campus_Colima">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Plaza_S%C3%A9samo">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Juan_Zurita">
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mercedes-Benz_R-Class">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Our_Caba%C3%B1a">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Bernard_Francis_Law">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Paola_Rojas">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Torre_Bicentenario_II">
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Anacleto_Gonz%C3%A1lez_Flores">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:beatified_place rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:beatifiedPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Miguel_Espa%C3%B1a">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mar%C3%ADa_Antonieta_de_las_Nieves">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Aranza">
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/San_Miguel_Zinacantepec">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Universidad_de_Sonora">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Casa_de_los_Babys">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Chontal_Maya_people">
    <dbpprop:popplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Teocelo%2C_Veracruz">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Garc%C3%ADa%2C_Nuevo_Le%C3%B3n">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Battle_of_San_Jacinto">
    <dbpprop:combatant rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ana_B%C3%A1rbara">
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Cuidado_con_el_%C3%A1ngel">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Boston_Red_Sox_all-time_roster/player164">
    <dbpprop:playerProperty rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Luis_G%C3%B3mez_%28baseball%29">
    <dbpprop:dateOfBirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ilka_Chase">
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ana_de_la_Reguera">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Luis_de_la_Fuente">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Juan_Pablo_Rodr%C3%ADguez">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Guadalupe%2C_Nuevo_Le%C3%B3n">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Aerom%C3%A9xico_Travel">
    <dbpedia-owl:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEBAL-AM">
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:format rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Edron_Academy">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/TV_Azteca">
    <dbpedia-owl:locationCountry rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Chingo_Bling">
    <dbpprop:born rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Picacho_del_Diablo">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Magnicharters">
    <dbpedia-owl:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/The_Battle_of_Mexico_City">
    <dbpprop:recorded rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mr._%C3%81guila">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Diego_Rivera">
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/PCN_%28band%29">
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ixc%C3%A1n_River">
    <dbpprop:basinCountries rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Edy_Germ%C3%A1n_Brambila">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Marcelino_Serna">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Julio_D%C3%ADaz">
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Alex_Diego">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Rosalva_Luna">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Carlos_de_los_Cobos">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Cotija_cheese">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Hurricane_Iris">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Javier_Saavedra">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Gabino_Velasco">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Aar%C3%B3n_Galindo">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHGTO-FM">
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Grupo_Nacional_de_Chocolates">
    <dbpprop:areaServed rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:areaServed rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Timbiriche">
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Efrain_Ju%C3%A1rez">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Contra_viento_y_marea">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tuxtla_Statuette">
    <dbpprop:discovered rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Zelia_Nuttall">
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Honduras">
    <dbpprop:establishedEvent rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Javier_Rodriguez_Parra">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Sabine_Moussier">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Francisco_Fonseca">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/The_Black_Parade_Is_Dead%21/tracklist2">
    <dbpprop:headline rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Marco_Antonio_Regil">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Sweet_Electra">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jes%C3%BAs_Mendoza">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/San_Diego_and_Arizona_Railway">
    <dbpprop:locale rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Carlos_Hermosillo">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Adolfo_Bautista">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/CONCACAF_Champions%27_Cup_2005/footballbox8">
    <dbpprop:stadium rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mexican_War_of_Independence">
    <dbpedia-owl:place rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:place rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:combatant rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jes%C3%BAs_Armando_S%C3%A1nchez">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Hurricane_Isis_%281998%29">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Morris_Swadesh">
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Aeropuertos_y_Servicios_Auxiliares">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Battle_of_Molino_del_Rey">
    <dbpprop:combatant rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ricardo_Mauricio_Mart%C3%ADnez">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Enrique_Alfaro">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tropical_Storm_Bill_%282003%29">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Alejandro_Gonz%C3%A1lez_I%C3%B1%C3%A1rritu">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Oscar_Gonz%C3%A1lez_Guerrero">
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Adolfo_Ruiz_Cortines">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Xel-H%C3%A1_Water_Park">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/San_Juan_de_los_Lagos">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mayan_languages">
    <dbpprop:region rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Susana_Gonz%C3%A1lez">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jos%C3%A9_Mar%C3%ADa_Melo">
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Andrew_Jackson_Grayson">
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Lampros_Kontogiannis">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/David_Alfaro_Siqueiros">
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Click_Mexicana">
    <dbpprop:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Carquest">
    <dbpedia-owl:areaServed rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:areaServed rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Cunduac%C3%A1n">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/%C3%93scar_P%C3%A9rez_Rojas">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ra%C3%BAl_Alberto_Salinas">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ernesto_G%C3%B3mez_Cruz">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Lila_Deneken">
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Venetian_language">
    <dbpedia-owl:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Juan_Carlos_Ch%C3%A1vez">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHRYN">
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Sin_Bandera">
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tio_Chango">
    <dbpedia-owl:locationcountry rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:locationCountry rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mayr%C3%ADn_Villanueva">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ulises_Mendivil">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mujer%2C_Casos_de_la_Vida_Real">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tecnol%C3%B3gico_de_Monterrey%2C_Campus_San_Luis_Potos%C3%AD">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Central_Department_%28Mexico%29">
    <dbpprop:statusText rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XETU">
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Aguajito">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Katie_Barberi">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Effects_of_Hurricane_Dean_in_Mexico">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Julio_Alem%C3%A1n">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Monterrey_Institute_of_Technology_and_Higher_Education%2C_State_of_Mexico_Campus">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Selther">
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Hurricane_Paul_%281982%29">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Dean_Phoenix">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Rafael_M%C3%A1rquez_%28boxer%29">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jos%C3%A9_Antonio_Aguirre_%28boxer%29">
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Sergio_Arias">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Hurricane_Adolph_%282001%29">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Zapotec_peoples">
    <dbpprop:popplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tarimoro_%28municipality%29">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEZV-AM">
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Desafio_de_Estrellas">
    <dbpedia-owl:network rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Yahir">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/EMD_GP28">
    <dbpprop:locale rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Evoga">
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Meksika">
    <dbpprop:redirect rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEPM-TV">
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Arde_el_Cielo_%28song%29">
    <dbpedia-owl:recordplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:recorded rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jos%C3%A9_L%C3%B3pez_Portillo">
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Rosalinda">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Gilbert_Roland">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/2006_FIFA_World_Cup_qualification_%28CONCACAF%29/footballbox26">
    <dbpprop:stadium rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/%C3%8Dmuris">
    <dbpprop:subdivisionType rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/La_Jolla_de_Cort%C3%A9s">
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Edoardo_Isella">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:nationalteam rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:nationalteam rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Iv%C3%A1n_L%C3%B3pez">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/V%C3%ADctor_Burgos">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mar_de_Cort%C3%A9s_International_Airport">
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Eternal_Live">
    <dbpprop:recorded rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:recordplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/La_Paz%2C_Baja_California_Sur">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Azteca_%28horse%29">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Santo">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Wastek_language">
    <dbpprop:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Rodolfo_Nieto">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Rosa_Mar%C3%ADa_Ojeda">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/The_United_States_of_Mexicans">
    <dbpprop:redirect rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Rafael_Rangel_Sostmann">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Aeromar">
    <dbpprop:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tzeltal_language">
    <dbpprop:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Kid_Azteca">
    <dbpprop:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Volkswagen_Golf_Mk2">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Hector_King">
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tepehu%C3%A1n">
    <dbpprop:popplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mercury_Tracer">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:successor rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Plymouth_Duster">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Renault_12">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/1932_San_Ciprian_Hurricane">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Lupita_Tovar">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Siege_of_Los_Angeles">
    <dbpprop:combatant rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mexicana_de_Aviaci%C3%B3n">
    <dbpprop:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Alacranes_de_Durango">
    <dbpprop:ground rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Vecinos">
    <dbpedia-owl:network rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:network rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/2006-07_A1_Grand_Prix_of_Nations%2C_Mexico">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Irapuato%2C_Guanajuato">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XECTZ-AM">
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mexico_City_%28former_administrative_division%29">
    <dbpprop:statusText rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XESPN-AM">
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jos%C3%A9_Luis_Ram%C3%ADrez">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEWA-AM">
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEAR">
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tarz%C3%A1n_L%CF%8Cpez">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Culiac%C3%A1n">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Corazones_al_l%C3%ADmite">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Man%C3%A1">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mauricio_Ochmann">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Alejandra_Guzm%C3%A1n">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Omar_Monjaraz">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Volkswagen_Golf_Mk1">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Universidad_Popular_de_la_Chontalpa">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Francisco_Gamboa">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/The_Crystal_Frontier">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XENAC-AM">
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Fausto_Pinto">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/OK%21">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ramona_%282000_TV_series%29">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jos%C3%A9_Manuel_de_la_Torre">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mexico">
    <dbpprop:abstract xml:lang="nl">Mexico, officieel de Verenigde Mexicaanse Staten is een land in Noord-Amerika, grenzend aan de Verenigde Staten, Belize en Guatemala, de Grote Oceaan en de Golf van Mexico. Ook enkele eilanden behoren tot Mexico. Mexico is een federale republiek bestaande uit 31 staten en het Federaal District, oftewel de hoofdstad Mexico-stad, gelegen op 2240 meter boven de zeespiegel en een van de grootste steden ter wereld. Het land heeft 108,700,891 inwoners. De meest gesproken taal is het Spaans, waarmee Mexico het grootste Spaanssprekende land ter wereld is; Indiaanse talen worden door zo'n 10% van de bevolking gesproken. Felipe CalderÃ³n van de Nationale Actiepartij (PAN) is president sinds 2006. Gerekend naar het bruto binnenlands product in ppp-dollars is Mexico de twaalfde economie ter wereld.</dbpprop:abstract>
    <dbpprop:populationCensus rdf:datatype="http://www.w3.org/2001/XMLSchema#integer">103263388</dbpprop:populationCensus>
    <dbpprop:leaderName rdf:resource="http://dbpedia.org/resource/Felipe_Calder%C3%B3n"/>
    <dbpprop:callingCode rdf:datatype="http://www.w3.org/2001/XMLSchema#integer">52</dbpprop:callingCode>
    <dbpprop:leaderTitle rdf:resource="http://dbpedia.org/resource/President_of_Mexico"/>
    <dbpprop:last xml:lang="en">Mercene</dbpprop:last>
    <dbpprop:last xml:lang="en">Thompson</dbpprop:last>
    <dbpprop:timeZoneDst xml:lang="en">varies</dbpprop:timeZoneDst>
    <dbpprop:abstract xml:lang="fr">Le Mexique ou Ã‰tats unis mexicains est un pays dâ€™AmÃ©rique du Nord, situÃ© au sud des Ã‰tats-Unis (dont il est en partie sÃ©parÃ© par le RÃ­o Bravo del Norte, appelÃ© RÃ­o Grande aux Ã‰tats-Unis) et bordÃ© au sud par le Guatemala et le Belize. Avec prÃ¨s de 107 millions dâ€™habitants, dont 24 millions dans l'aire urbaine de Mexico, le Mexique est le plus peuplÃ© des pays d'expression espagnole. Il est le troisiÃ¨me par la taille et le second par sa population en AmÃ©rique latine.</dbpprop:abstract>
    <dbpprop:areaSqMi rdf:datatype="http://www.w3.org/2001/XMLSchema#integer">761606</dbpprop:areaSqMi>
    <dbpprop:title xml:lang="en">Conmemoran 100 aÃ±os de inmigraciÃ³n coreana</dbpprop:title>
    <dbpprop:title xml:lang="en">Rinde AMLO protesta como "presidente legÃ­timo"</dbpprop:title>
    <dbpprop:populationEstimateYear xml:lang="en">mid-2008</dbpprop:populationEstimateYear>
    <dbpprop:disambiguates rdf:resource="http://dbpedia.org/resource/Mexico%2C_Carroll_County%2C_Maryland"/>
    <dbpprop:abstract xml:lang="en">The United Mexican States (Spanish), or commonly Mexico, is a federal constitutional republic in North America. It is bordered on the north by the United States; on the south and west by the North Pacific Ocean; on the southeast by Guatemala, Belize, and the Caribbean Sea; and on the east by the Gulf of Mexico. The United Mexican States are a federation comprising thirty-one states and a federal district, the capital Mexico City, whose metropolitan area is one of the world's most populous. Covering almost 2 million square kilometres, Mexico is the fifth-largest country in the Americas by total area and the 14th largest independent nation in the world. With an estimated population of 109 million, it is the 11th most populous country and the most populous Spanish-speaking country in the world. As a regional power and the only Latin American member of the Organisation for Economic Co-operation and Development since 1994, Mexico is firmly established as an upper middle-income country. Mexico is a newly industrialized country and the 12th largest economy in the world by GDP by purchasing power parity. The economy is strongly linked to those of its North American Free Trade Agreement partners. Despite being considered an emerging world power, the uneven distribution of income and the increase in insecurity are issues of concern. Elections held in July 2000 marked the first time that an opposition party won the presidency from the Institutional Revolutionary Party (Partido Revolucionario Institucional, PRI) which had held it since 1929, culminating the political alternation at the federal level, which had begun at the local level during the 1980s.</dbpprop:abstract>
    <dbpprop:disambiguates rdf:resource="http://dbpedia.org/resource/Mexico_%28novel%29"/>
    <dbpprop:areaRank rdf:datatype="http://dbpedia.org/units/Rank">15</dbpprop:areaRank>
    <dbpprop:state xml:lang="en">collapsed</dbpprop:state>
    <rdfs:comment xml:lang="fr">Le Mexique ou Ã‰tats unis mexicains est un pays dâ€™AmÃ©rique du Nord, situÃ© au sud des Ã‰tats-Unis (dont il est en partie sÃ©parÃ© par le RÃ­o Bravo del Norte, appelÃ© RÃ­o Grande aux Ã‰tats-Unis) et bordÃ© au sud par le Guatemala et le Belize.</rdfs:comment>
    <dbpprop:populationCensusYear rdf:datatype="http://www.w3.org/2001/XMLSchema#integer">2005</dbpprop:populationCensusYear>
    <rdf:type rdf:resource="http://dbpedia.org/class/yago/FormerSpanishColonies"/>
    <rdfs:comment xml:lang="es">MÃ©xico es una RepÃºblica Federal integrada por 32 entidades federativas que ocupa la parte meridional de AmÃ©rica del Norte.</rdfs:comment>
    <dbpprop:first xml:lang="en">Floro L.</dbpprop:first>
    <dbpprop:first xml:lang="en">Francisco</dbpprop:first>
    <dbpprop:disambiguates rdf:resource="http://dbpedia.org/resource/Mexico%2C_Ohio"/>
    <rdf:type rdf:resource="http://sw.opencyc.org/2008/06/10/concept/Mx4r46fYpnaNEduAAAACs6hbjw"/>
    <dbpprop:sovereigntyType rdf:resource="http://dbpedia.org/resource/Mexican_War_of_Independence"/>
    <skos:subject rdf:resource="http://dbpedia.org/resource/Category:Spanish-speaking_countries"/>
    <dbpprop:disambiguates rdf:resource="http://dbpedia.org/resource/Mexico%2C_Montour_County%2C_Pennsylvania"/>
    <dbpprop:hdiRank rdf:datatype="http://dbpedia.org/units/Rank">52</dbpprop:hdiRank>
    <dbpprop:disambiguates rdf:resource="http://dbpedia.org/resource/Mexico%2C_Maryland"/>
    <dbpprop:date xml:lang="en">January-February 2006</dbpprop:date>
    <rdfs:label xml:lang="ja">ãƒ¡ã‚­ã‚·ã‚³</rdfs:label>
    <dbpprop:establishedDate rdf:datatype="http://www.w3.org/2001/XMLSchema#date">1810-09-16</dbpprop:establishedDate>
    <dbpprop:disambiguates rdf:resource="http://dbpedia.org/resource/Mexico_%28town%29%2C_New_York"/>
    <rdfs:comment xml:lang="zh">å¢¨è¥¿å“¥å�ˆä¼—å›½ä½�äºŽåŒ—ç¾Žæ´²ï¼ŒåŒ—éƒ¨ä¸Žç¾Žå›½æŽ¥å£¤ï¼Œä¸œå�—ä¸Žå�±åœ°é¦¬æ‹‰ä¸Žä¼¯åˆ©å…¹ç›¸é‚»ï¼Œè¥¿éƒ¨æ˜¯å¤ªå¹³æ´‹ï¼Œä¸œéƒ¨æœ‰å¢¨è¥¿å“¥æ¹¾ä¸Žï¿½ å‹’æ¯”æµ·çš„é˜»éš”ã€‚é¦–éƒ½å¢¨è¥¿å“¥åŸŽ(è¥¿ç�­ç‰™æ–‡ï¼šCiudad de MÃ©xico)ã€‚</rdfs:comment>
    <dbpprop:otherSymbolType rdf:resource="http://dbpedia.org/resource/Seal_%28device%29"/>
    <rdfs:label xml:lang="zh">å¢¨è¥¿å“¥</rdfs:label>
    <dbpprop:populationEstimateRank rdf:datatype="http://dbpedia.org/units/Rank">11</dbpprop:populationEstimateRank>
    <dbpprop:journal xml:lang="en">El Norte</dbpprop:journal>
    <dbpprop:last xml:lang="en">ResÃ©ndiz</dbpprop:last>
    <dbpprop:relatedInstance rdf:resource="http://dbpedia.org/resource/Mexico/wikia1"/>
    <dbpprop:abstract xml:lang="pl">Stany Zjednoczone Meksyku (hiszp. Estados Unidos Mexicanos; nahuatl MÄ“xihco) â€“ paÅ„stwo w Ameryce PÃ³Å‚nocnej, graniczÄ…ce od pÃ³Å‚nocy z USA a na poÅ‚udniu z GwatemalÄ… i Belize. Od zachodu jego terytorium ograniczone jest wodami Pacyfiku, a od wschodu Zatoki MeksykaÅ„skiej i Morza Karaibskiego. Trzeci co do wielkoÅ›ci kraj Ameryki Å�aciÅ„skiej i najwiÄ™kszy kraj Ameryki Åšrodkowej.</dbpprop:abstract>
    <dbpprop:gdpNominal xml:lang="en">$893.4 billion (short scale)</dbpprop:gdpNominal>
    <rdf:type rdf:resource="http://sw.opencyc.org/2008/06/10/concept/Mx4rvVjk55wpEbGdrcN5Y29ycA"/>
    <dbpprop:wikiPageUsesTemplate rdf:resource="http://dbpedia.org/resource/Template:audio-es"/>
    <dbpprop:currencyCode xml:lang="en">MXN</dbpprop:currencyCode>
    <dbpprop:wikiPageUsesTemplate rdf:resource="http://dbpedia.org/resource/Template:template_group"/>
    <foaf:depiction rdf:resource="http://upload.wikimedia.org/wikipedia/commons/thumb/9/90/Seal_of_the_Government_of_Mexico.svg/200px-Seal_of_the_Government_of_Mexico.svg.png"/>
    <dbpprop:abstract xml:lang="ja">ä¸–ç•Œ &amp;gt; åŒ—ã‚¢ãƒ¡ãƒªã‚« &amp;gt; ãƒ¡ã‚­ã‚·ã‚³'ãƒ¡ã‚­ã‚·ã‚³å�ˆè¡†å›½'ï¼ˆãƒ¡ã‚­ã‚·ã‚³ã�Œã�£ã�—ã‚…ã�†ã�“ã��ï¼‰ã€�é€šç§°ãƒ¡ã‚­ã‚·ã‚³ã�¯ã€�åŒ—ã‚¢ãƒ¡ãƒªã‚«å�—éƒ¨ã�«ä½�ç½®ã�™ã‚‹ãƒ©ãƒ†ãƒ³ã‚¢ãƒ¡ãƒªã‚«ã�®é€£é‚¦å…±å’Œåˆ¶å›½å®¶ã�§ã�‚ã‚‹ã€‚åŒ—ã�«ã‚¢ãƒ¡ãƒªã‚«å�ˆè¡†å›½ã�¨ã€�å�—æ�±ã�«ã‚°ã‚¢ãƒ†ãƒžãƒ©ã€�ãƒ™ãƒªãƒ¼ã‚ºã�¨å›½å¢ƒã‚’æŽ¥ã�—ã€�è¥¿ã�¯å¤ªå¹³æ´‹ã€�æ�±ã�¯å¤§è¥¿æ´‹ã�¨ã‚«ãƒªãƒ–æµ·ã�®ãƒ¡ã‚­ã‚·ã‚³æ¹¾ã�«é�¢ã�™ã‚‹ã€‚é¦–éƒ½ã�¯ãƒ¡ã‚­ã‚·ã‚³å¸‚ã€‚ãƒ©ãƒ†ãƒ³ã‚¢ãƒ¡ãƒªã‚«æœ€åŒ—ã�«ä½�ç½®ã�—ã€�ãƒ–ãƒ©ã‚¸ãƒ«ã€�ã‚¢ãƒ«ã‚¼ãƒ³ãƒ�ãƒ³ã�«æ¬¡ã�„ã�§é�¢ç©�ã�¯3ç•ªç›®ã�®å¤§ã��ã�•ã�§ã�‚ã‚‹ã€‚ã�¾ã�Ÿã€�ã�Šã‚ˆã��ä¸€å„„äººã�®ç·�äººå�£ã�¯ã€�ãƒ©ãƒ†ãƒ³ã‚¢ãƒ¡ãƒªã‚«ã�§ã�¯2ç•ªç›®ã�«å¤§ã��ã��ã€�ã‚¹ãƒšã‚¤ãƒ³èªžåœ�å…¨ä½“ã�§ã�¯æœ€å¤§ã‚’èª‡ã‚‹ã€‚</dbpprop:abstract>
    <dbpprop:abstract xml:lang="sv">Mexiko, alternativt Mexico,, Ã¤r en federal republik i sÃ¶dra Nordamerika. Landet grÃ¤nsar till Belize, Guatemala och USA och har kust mot bÃ¥de Atlanten och Stilla havet.</dbpprop:abstract>
    <dbpprop:disambiguates rdf:resource="http://dbpedia.org/resource/Mexico%2C_Pampanga"/>
    <dbpprop:disambiguates rdf:resource="http://dbpedia.org/resource/Mexico%2C_Maine"/>
    <dbpprop:title xml:lang="en">Baja pobreza en MÃ©xico de 24.2 a 17.6%: Banco Mundial</dbpprop:title>
    <owl:sameAs rdf:resource="http://rdf.freebase.com/ns/guid.9202a8c04000641f8000000000a483f7"/>
    <dbpprop:demonym rdf:resource="http://dbpedia.org/resource/Demographics_of_Mexico"/>
    <dbpprop:disambiguates rdf:resource="http://dbpedia.org/resource/Mexico_%28Bob_Moore_song%29"/>
    <rdfs:comment xml:lang="no">De forente meksikanske stater eller Mexico (pÃ¥ spansk Estados Unidos Mexicanos eller MÃ©xico, ogsÃ¥ skrevet MÃ©jico) er en nordamerikansk republikk som grenser mot USA i nord og Guatemala og Belize i sÃ¸rÃ¸st.</rdfs:comment>
    <dbpprop:languagesType rdf:resource="http://dbpedia.org/resource/National_language"/>
    <dbpprop:latns xml:lang="en">N</dbpprop:latns>
    <rdf:type rdf:resource="http://umbel.org/umbel/sc/AdministrativeDistrict"/>
    <dbpprop:seeAlsoProperty xml:lang="en">1970 FIFA World Cup</dbpprop:seeAlsoProperty>
    <dbpprop:gdpPpp rdf:datatype="http://dbpedia.org/units/Dollar">1346000000000</dbpprop:gdpPpp>
    <dbpedia-owl:governmenttype rdf:resource="http://dbpedia.org/resource/Federal_republic"/>
    <dbpprop:nativeName xml:lang="en">Estados Unidos Mexicanos</dbpprop:nativeName>
    <rdf:type rdf:resource="http://dbpedia.org/ontology/Resource"/>
    <dbpprop:year rdf:datatype="http://www.w3.org/2001/XMLSchema#integer">2005</dbpprop:year>
    <dbpprop:abstract xml:lang="da">Mexico eller Mexicos Forenede Stater (pÃ¥ spansk MÃ©xico eller Estados Unidos Mexicanos; den alternative stavemÃ¥de MÃ©jico er udbredt i Spanien, men anvendes nÃ¦sten ikke i resten af den spansk-talende verden) er et nordamerikansk land, der mod nord grÃ¦nser op til USA, mod syd til Guatemala og Belize, mod vest til Stillehavet og mod Ã¸st til den Mexicanske Golf og det Caribiske Hav.</dbpprop:abstract>
    <dbpprop:disambiguates rdf:resource="http://dbpedia.org/resource/Mexico%2C_Illinois"/>
    <dbpedia-owl:governmenttype rdf:resource="http://dbpedia.org/resource/Presidential_system"/>
    <dbpprop:disambiguates rdf:resource="http://dbpedia.org/resource/Mexico_%28Jefferson_Airplane_song%29"/>
    <rdfs:comment xml:lang="pl">Stany Zjednoczone Meksyku (hiszp. Estados Unidos Mexicanos; nahuatl MÄ“xihco) â€“ paÅ„stwo w Ameryce PÃ³Å‚nocnej, graniczÄ…ce od pÃ³Å‚nocy z USA a na poÅ‚udniu z GwatemalÄ… i Belize.</rdfs:comment>
    <dbpprop:populationDensityKm rdf:datatype="http://www.w3.org/2001/XMLSchema#integer">55</dbpprop:populationDensityKm>
    <dbpprop:disambiguates rdf:resource="http://dbpedia.org/resource/Mexico%2C_Allegany_County%2C_Maryland"/>
    <dbpprop:populationDensitySqMi rdf:datatype="http://www.w3.org/2001/XMLSchema#integer">142</dbpprop:populationDensitySqMi>
    <dbpprop:first xml:lang="en">Azucena</dbpprop:first>
    <dbpprop:date rdf:datatype="http://www.w3.org/2001/XMLSchema#date">2005-01-28</dbpprop:date>
    <foaf:name>Estados Unidos Mexicanos</foaf:name>
    <dbpprop:last xml:lang="en">Krauze</dbpprop:last>
    <foaf:page rdf:resource="http://en.wikipedia.org/wiki/Mexico"/>
    <dbpprop:disambiguates rdf:resource="http://dbpedia.org/resource/Mexico%2C_Missouri"/>
    <foaf:name>Mexico</foaf:name>
    <dbpprop:longew xml:lang="en">W</dbpprop:longew>
    <dbpprop:wikiPageUsesTemplate rdf:resource="http://dbpedia.org/resource/Template:web_cite"/>
    <rdfs:label xml:lang="pl">Meksyk</rdfs:label>
    <dbpprop:audioEsProperty xml:lang="en">MÃ©xico</dbpprop:audioEsProperty>
    <rdf:type rdf:resource="http://dbpedia.org/class/yago/WTOMemberEconomies"/>
    <dbpprop:journal xml:lang="en">Noticieros Televisa</dbpprop:journal>
    <dbpprop:abstract xml:lang="zh">å¢¨è¥¿å“¥å�ˆä¼—å›½ä½�äºŽåŒ—ç¾Žæ´²ï¼ŒåŒ—éƒ¨ä¸Žç¾Žå›½æŽ¥å£¤ï¼Œä¸œå�—ä¸Žå�±åœ°é¦¬æ‹‰ä¸Žä¼¯åˆ©å…¹ç›¸é‚»ï¼Œè¥¿éƒ¨æ˜¯å¤ªå¹³æ´‹ï¼Œä¸œéƒ¨æœ‰å¢¨è¥¿å“¥æ¹¾ä¸ŽåŠ å‹’æ¯”æµ·çš„é˜»éš”ã€‚é¦–éƒ½å¢¨è¥¿å“¥åŸŽ(è¥¿ç�­ç‰™æ–‡ï¼šCiudad de MÃ©xico)ã€‚</dbpprop:abstract>
    <dbpprop:currency rdf:resource="http://dbpedia.org/resource/Mexican_peso"/>
    <rdf:type rdf:resource="http://umbel.org/umbel/sc/ControlledLand"/>
    <dbpprop:sovereigntyNote xml:lang="en">from Spain</dbpprop:sovereigntyNote>
    <geo:long rdf:datatype="http://www.w3.org/2001/XMLSchema#float">-99.36666870117188</geo:long>
    <dbpprop:symbolType xml:lang="en">Coat of arms</dbpprop:symbolType>
    <rdfs:label xml:lang="fi">Meksiko</rdfs:label>
    <geo:lat rdf:datatype="http://www.w3.org/2001/XMLSchema#float">19.04999923706055</geo:lat>
    <dbpedia-owl:anthem rdf:resource="http://dbpedia.org/resource/Himno_Nacional_Mexicano"/>
    <dbpprop:wikiPageUsesTemplate rdf:resource="http://dbpedia.org/resource/Template:harvard_reference"/>
    <dbpprop:areaKm rdf:datatype="http://www.w3.org/2001/XMLSchema#integer">1972550</dbpprop:areaKm>
    <dbpedia-owl:latitutedegrees>19</dbpedia-owl:latitutedegrees>
    <dbpedia-owl:demonym>Mexican</dbpedia-owl:demonym>
    <dbpprop:gini xml:lang="en">â–¼ 46.1</dbpprop:gini>
    <dbpedia-owl:language rdf:resource="http://dbpedia.org/resource/Spanish_language"/>
    <dbpprop:latd rdf:datatype="http://www.w3.org/2001/XMLSchema#integer">19</dbpprop:latd>
    <dbpprop:abstract xml:lang="es">MÃ©xico ? es una RepÃºblica Federal integrada por 32 entidades federativas que ocupa la parte meridional de AmÃ©rica del Norte. De acuerdo con la constituciÃ³n mexicana vigente, el nombre oficial del paÃ­s es Estados Unidos Mexicanos, y la sede de los poderes de la federaciÃ³n es la Ciudad de MÃ©xico, cuyo territorio ha sido designado como Distrito Federal. Limita al norte con Estados Unidos; al este, con el Golfo de MÃ©xico y el Mar Caribe; al sureste, con Belice y Guatemala, y al oeste con el OcÃ©ano PacÃ­fico. La superficie mexicana ocupa una extensiÃ³n cercana a los 2 millones de kmÂ², que lo colocan en la decimocuarta posiciÃ³n entre los paÃ­ses del mundo ordenados por superficie. En este territorio habitan mÃ¡s de 109 millones de personas. Por ello, se trata de la naciÃ³n hispanohablante con mayor poblaciÃ³n. Por otra parte, el espaÃ±ol convive en MÃ©xico con numerosas lenguas indÃ­genas, reconocidas oficialmente como nacionales por el Estado mexicano. La historia de este territorio se remonta mÃ¡s de 30 mil aÃ±os hacia el pasado, tiempo en el que se sucedieron en ese mismo espacio numerosos pueblos, que incluyen tanto a culturas mesoamericanas agrÃ­colas como a los nÃ³madas de AridoamÃ©rica y los pueblos oasisamericanos. Tras la conquista espaÃ±ola, MÃ©xico iniciÃ³ la lucha por su independencia polÃ­tica en 1810. Posteriormente, durante cerca de un siglo el paÃ­s se vio envuelto en una serie de guerras internas e invasiones extranjeras que tuvieron repercusiones en todos los Ã¡mbitos de la vida de los mexicanos. Durante buena parte del siglo XX tuvo lugar un perÃ­odo de gran crecimiento econÃ³mico en el marco de una polÃ­tica dominada por un solo partido polÃ­tico. Por el volumen neto de su producto interno bruto, se considera a MÃ©xico la decimocuarta economÃ­a mundial â€”aunque hasta 2005 habÃ­a sido la novenaâ€”. Durante una buena parte del siglo XX, la principal fuente de divisas extranjeras del paÃ­s fue la venta de petrÃ³leo, aunque durante este siglo tuvo lugar un proceso de industrializaciÃ³n que permitiÃ³ al paÃ­s diversificar su economÃ­a. Las remesas de los trabajadores mexicanos en el exterior han venido creciendo aÃ±o con aÃ±o, hasta llegar a representar el 3% del PIB nacional y una de las principales fuentes de divisas extranjeras para el paÃ­s, precisamente al lado de los ingresos por exportaciones petroleras y el turismo. Otra gran problemÃ¡tica social es el aumento de los Ã­ndices de incidencia delictiva en el paÃ­s, especialmente la relativa al narcotrÃ¡fico.</dbpprop:abstract>
    <dbpprop:gdpNominalPerCapita rdf:datatype="http://dbpedia.org/units/Dollar">9717</dbpprop:gdpNominalPerCapita>
    <rdfs:comment xml:lang="it">Gli Stati Uniti del Messico sono uno stato federale dell'America settentrionale.</rdfs:comment>
    <dbpprop:url rdf:resource="http://busquedas.gruporeforma.com/utilerias/imdservicios3w.dll"/>
    <dbpprop:languages xml:lang="en"> Spanish, and 62  Indigenous Amerindian languagesThere is no official language stipulated in the constitution. However, the General Law of Linguistic Rights for the Indigenous Peoples recognizes all Amerindian minority languages, along with Spanish, as national languages and equally valid in territories where spoken. The government recognizes 62 indigenous languages, and more variants which are mutually unintelligible.</dbpprop:languages>
    <dbpedia-owl:areaMagnitude>1 E12</dbpedia-owl:areaMagnitude>
    <dbpprop:officialLanguages xml:lang="en">None at federal level. &lt;br&gt;  Spanish (de facto)</dbpprop:officialLanguages>
    <dbpprop:journal xml:lang="en">El Universal</dbpprop:journal>
    <skos:subject rdf:resource="http://dbpedia.org/resource/Category:Former_Spanish_colonies"/>
    <dbpprop:publisher xml:lang="en">Foreign Affairs</dbpprop:publisher>
    <dbpprop:disambiguates rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:disambiguates rdf:resource="http://dbpedia.org/resource/Mexico%2C_Wyandot_County%2C_Ohio"/>
    <rdfs:comment xml:lang="sv">Mexiko, alternativt Mexico,, Ã¤r en federal republik i sÃ¶dra Nordamerika.</rdfs:comment>
    <dbpprop:disambiguates rdf:resource="http://dbpedia.org/resource/Mexico_%28barque%29"/>
    <dbpprop:disambiguates rdf:resource="http://dbpedia.org/resource/Mexico%2C_Indiana"/>
    <dbpprop:hdiCategory xml:lang="en">high</dbpprop:hdiCategory>
    <dbpprop:abstract xml:lang="pt">O MÃ©xico (de nome oficial Estados Unidos Mexicanos) Ã© um paÃ­s localizado na AmÃ©rica do Norte delimitado ao norte pelos Estados Unidos da AmÃ©rica, a leste pelo Golfo do MÃ©xico e pelo Mar das CaraÃ­bas, atravÃ©s dos quais se aproxima de Cuba, a sul pela Guatemala e por Belize, e a oeste pelo Oceano PacÃ­fico. AlÃ©m do territÃ³rio continental e ilhas adjacentes Ã  costa, o MÃ©xico inclui tambÃ©m as Ilhas Revillagigedo, localizadas no Oceano PacÃ­fico, a mais de 400 km a sul do Cabo San Lucas, na Baja California Sur.</dbpprop:abstract>
    <dbpprop:gdpNominalPerCapitaRank rdf:datatype="http://dbpedia.org/units/Rank">54</dbpprop:gdpNominalPerCapitaRank>
    <dbpprop:abstract xml:lang="fi">Meksikon yhdysvallat eli Meksiko (esp. MÃ©xico tai joskus MÃ©jico, sekÃ¤ x ettÃ¤ j lausutaan ach-Ã¤Ã¤nteenÃ¤ [x]), on Pohjois-Amerikan etelÃ¤osassa sijaitseva yli sadan miljoonan asukkaan maa, jonka rajanaapureita ovat Yhdysvallat, Belize ja Guatemala. Maan pÃ¤Ã¤kaupunki on (Ciudad de) MÃ©xico, josta kÃ¤ytetÃ¤Ã¤n usein myÃ¶s englanninkielistÃ¤ versiota Mexico City. Meksiko on asukasluvultaan maailman suurin espanjankielinen maa.</dbpprop:abstract>
    <dbpprop:abstract xml:lang="no">De forente meksikanske stater eller Mexico (pÃ¥ spansk Estados Unidos Mexicanos eller MÃ©xico, ogsÃ¥ skrevet MÃ©jico) er en nordamerikansk republikk som grenser mot USA i nord og Guatemala og Belize i sÃ¸rÃ¸st. I vest ligger Stillehavet og i Ã¸st ligger Mexicogolfen og det Karibiske hav. Det hÃ¸yeste fjellet er Pico de Orizaba.</dbpprop:abstract>
    <dbpprop:audioEsProperty xml:lang="en">Mexico.ogg</dbpprop:audioEsProperty>
    <dbpprop:disambiguates rdf:resource="http://dbpedia.org/resource/Mexico%2C_Pennsylvania"/>
    <dbpprop:leaderName rdf:resource="http://dbpedia.org/resource/National_Action_Party_%28Mexico%29"/>
    <skos:subject rdf:resource="http://dbpedia.org/resource/Category:Liberal_democracies"/>
    <dbpprop:disambiguates rdf:resource="http://dbpedia.org/resource/Mexico%2C_Crawford_County%2C_Ohio"/>
    <rdf:type rdf:resource="http://dbpedia.org/class/yago/LiberalDemocracies"/>
    <dbpprop:hasPhotoCollection rdf:resource="http://www4.wiwiss.fu-berlin.de/flickrwrappr/photos/Mexico"/>
    <dbpprop:url rdf:resource="http://www.eluniversal.com.mx/notas/389114.html"/>
    <dbpedia-owl:longitudeseastorwest>W</dbpedia-owl:longitudeseastorwest>
    <dbpprop:capital rdf:resource="http://dbpedia.org/resource/Mexico_City"/>
    <rdfs:comment xml:lang="en">The United Mexican States (Spanish), or commonly Mexico, is a federal constitutional republic in North America. It is bordered on the north by the United States; on the south and west by the North Pacific Ocean; on the southeast by Guatemala, Belize, and the Caribbean Sea; and on the east by the Gulf of Mexico. The United Mexican States are a federation comprising thirty-one states and a federal district, the capital Mexico City, whose metropolitan area is one of the world's most populous. </rdfs:comment>
    <dbpprop:title xml:lang="en">Mexico, Economics: The US cast a long shadow</dbpprop:title>
    <skos:subject rdf:resource="http://dbpedia.org/resource/Category:Nahuatl_words_and_phrases"/>
    <dbpprop:mapWidth xml:lang="en">250px</dbpprop:mapWidth>
    <dbpprop:journal xml:lang="en">Arizona Republic</dbpprop:journal>
    <dbpprop:populationDensityRank rdf:datatype="http://dbpedia.org/units/Rank">142</dbpprop:populationDensityRank>
    <rdfs:label xml:lang="es">MÃ©xico</rdfs:label>
    <rdfs:label xml:lang="pt">MÃ©xico</rdfs:label>
    <dbpprop:disambiguates rdf:resource="http://dbpedia.org/resource/Mexico%2C_Texas"/>
    <dbpprop:utcOffset xml:lang="en">-8 to -6</dbpprop:utcOffset>
    <rdfs:label xml:lang="ru">ÐœÐµÐºÑ�Ð¸ÐºÐ°</rdfs:label>
    <dbpprop:imageMap rdf:resource="http://upload.wikimedia.org/wikipedia/commons/3/3c/Mexico_location.svg"/>
    <dbpprop:audioEsProperty xml:lang="en">Estados Unidos Mexicanos</dbpprop:audioEsProperty>
    <dbpprop:establishedEvent xml:lang="en">Recognized</dbpprop:establishedEvent>
    <dbpedia-owl:areaMetro rdf:datatype="http://dbpedia.org/ontology/squareMile">761606</dbpedia-owl:areaMetro>
    <dbpprop:cctld rdf:resource="http://dbpedia.org/resource/.mx"/>
    <dbpprop:gdpNominalYear rdf:datatype="http://www.w3.org/2001/XMLSchema#integer">2007</dbpprop:gdpNominalYear>
    <dbpprop:gdpPppYear rdf:datatype="http://www.w3.org/2001/XMLSchema#integer">2007</dbpprop:gdpPppYear>
    <rdf:type rdf:resource="http://umbel.org/umbel/sc/IndependentCountry"/>
    <dbpedia-owl:percentageOfAreaWater>2.5</dbpedia-owl:percentageOfAreaWater>
    <rdf:type rdf:resource="http://dbpedia.org/class/yago/OECDMemberEconomies"/>
    <dbpprop:disambiguates rdf:resource="http://dbpedia.org/resource/Mexico_%28game%29"/>
    <dbpprop:commonName xml:lang="en">Mexico</dbpprop:commonName>
    <dbpprop:abstract xml:lang="it">Gli Stati Uniti del Messico sono uno stato federale dell'America settentrionale. La superficie del territorio Ã¨ di 1. 972. 550 kmÂ², popolata da 108. 700. 891 abitanti (2007) con capitale federale CittÃ  del Messico. Confinano a nord con gli Stati Uniti d'America e a sud-est con il Guatemala e il Belize, si affaccia sull'Oceano Pacifico a ovest e sul Golfo del Messico e sul Mar dei Caraibi a est. Ãˆ il Paese piÃ¹ settentrionale dell'America Latina ed Ã¨ la Nazione con maggior numero di abitanti di lingua ispanofona del mondo.Ãˆ diffusa sui media l'abitudine di considerare il Messico uno stato appartenente all'America Centrale. Pur appartenendo all'America Latina il Messico Ã¨ geograficamente in America Settentrionale. Alcuni autori preferiscono la variante MÃ©jico al vero e corretto termine MÃ©xico . Entrambe le forme sono ritenute corrette dalla Real Academia de la Lengua EspaÃ±ola, che, comunque, suggerisce la forma MÃ©xico e l'uso della "x" in tutte le parole da essa derivate. Storicamente, la maggior parte dei Paesi di lingua spagnola hanno usato la forma contenente la "x".Il Messico Ã¨ una Repubblica federale costituita da 31 Stati . La capitale federale Ã¨ CittÃ  del Messico, la cui area metropolitana supera i 18 milioni di abitanti, di cui 8. 600. 000 vivono nel cosiddetto Distretto Federale (Distrito Federal, D.F.) propriamente detto.</dbpprop:abstract>
    <owl:sameAs rdf:resource="http://sw.opencyc.org/2008/06/10/concept/Mx4rvVkAiZwpEbGdrcN5Y29ycA"/>
    <dbpedia-owl:longitudeminutes>22</dbpedia-owl:longitudeminutes>
    <rdfs:comment xml:lang="fi">Meksikon yhdysvallat eli Meksiko (esp. MÃ©xico tai joskus MÃ©jico, sekÃ¤ x ettÃ¤ j lausutaan ach-Ã¤Ã¤nteenÃ¤ [x]), on Pohjois-Amerikan etelÃ¤osassa sijaitseva yli sadan miljoonan asukkaan maa, jonka rajanaapureita ovat Yhdysvallat, Belize ja Guatemala.</rdfs:comment>
    <dbpprop:conventionalLongName xml:lang="en">United Mexican States</dbpprop:conventionalLongName>
    <dbpprop:percentWater rdf:datatype="http://www.w3.org/2001/XMLSchema#double">2.5</dbpprop:percentWater>
    <dbpprop:otherSymbol rdf:resource="http://dbpedia.org/resource/Seal_of_the_United_Mexican_States"/>
    <dbpprop:otherSymbol rdf:resource="http://dbpedia.org/resource/Image:Seal_of_the_Government_of_Mexico.svg"/>
    <dbpprop:year rdf:datatype="http://www.w3.org/2001/XMLSchema#integer">2006</dbpprop:year>
    <rdf:type rdf:resource="http://dbpedia.org/class/yago/G15Nations"/>
    <dbpedia-owl:latitudenorthorsouth>N</dbpedia-owl:latitudenorthorsouth>
    <dbpprop:url rdf:resource="http://www.esmas.com/noticierostelevisa/mexico/443030.html"/>
    <dbpprop:disambiguates rdf:resource="http://dbpedia.org/resource/Mexico_%28Epcot%29"/>
    <dbpprop:timeZone xml:lang="en">U.S Central to Western</dbpprop:timeZone>
    <dbpprop:seeAlsoProperty xml:lang="en">1968 Summer Olympics</dbpprop:seeAlsoProperty>
    <dbpprop:disambiguates rdf:resource="http://dbpedia.org/resource/Mexico_State"/>
    <skos:subject rdf:resource="http://dbpedia.org/resource/Category:Federal_countries"/>
    <dbpprop:gdpPppPerCapitaRank rdf:datatype="http://dbpedia.org/units/Rank">54</dbpprop:gdpPppPerCapitaRank>
    <dbpprop:establishedDate rdf:datatype="http://www.w3.org/2001/XMLSchema#date">1821-09-27</dbpprop:establishedDate>
    <dbpedia-owl:currency>[[Mexican peso|Peso]]</dbpedia-owl:currency>
    <rdf:type rdf:resource="http://umbel.org/umbel/sc/DemocraticRepublicanIdeology"/>
    <dbpprop:governmentType rdf:resource="http://dbpedia.org/resource/Federal_republic"/>
    <dbpprop:title xml:lang="en">Filipinos in Mexican History</dbpprop:title>
    <rdf:type rdf:resource="http://sw.opencyc.org/2008/06/10/concept/Mx4r4qVlEJhnQdidKY2g0ysmcA"/>
    <dbpprop:last xml:lang="en">Mendoza</dbpprop:last>
    <rdf:type rdf:resource="http://dbpedia.org/ontology/PopulatedPlace"/>
    <dbpprop:disambiguates rdf:resource="http://dbpedia.org/resource/Mexico_%28Butthole_Surfers_song%29"/>
    <dbpprop:disambiguates rdf:resource="http://dbpedia.org/resource/Mexico%2C_South_Carolina"/>
    <dbpprop:hdi rdf:datatype="http://www.w3.org/2001/XMLSchema#double">0.829</dbpprop:hdi>
    <rdf:type rdf:resource="http://umbel.org/umbel/sc/GeopoliticalEntity"/>
    <dbpprop:first xml:lang="en">Enrique</dbpprop:first>
    <dbpprop:longm rdf:datatype="http://www.w3.org/2001/XMLSchema#integer">22</dbpprop:longm>
    <dbpprop:title rdf:resource="http://dbpedia.org/resource/Mexico/title/resize"/>
    <rdfs:comment xml:lang="nl">Mexico, officieel de Verenigde Mexicaanse Staten is een land in Noord-Amerika, grenzend aan de Verenigde Staten, Belize en Guatemala, de Grote Oceaan en de Golf van Mexico.</rdfs:comment>
    <dbpprop:disambiguates rdf:resource="http://dbpedia.org/resource/Mexico_City"/>
    <dbpprop:latm rdf:datatype="http://www.w3.org/2001/XMLSchema#integer">3</dbpprop:latm>
    <dbpprop:portalProperty rdf:resource="http://upload.wikimedia.org/wikipedia/commons/f/fc/Flag_of_Mexico.svg"/>
    <dbpedia-owl:languageType rdf:resource="http://dbpedia.org/resource/National_language"/>
    <dbpprop:accessdate rdf:datatype="http://www.w3.org/2001/XMLSchema#date">2007-08-20</dbpprop:accessdate>
    <dbpprop:giniCategory xml:lang="en">high</dbpprop:giniCategory>
    <rdfs:comment xml:lang="ru">ÐœÐµÌ�ÐºÑ�Ð¸ÐºÐ° (Ð¸Ñ�Ð¿. MÃ©xico), Ð¾Ñ„Ð¸Ñ†Ð¸Ð°Ð»ÑŒÐ½Ð¾Ðµ Ð½Ð°Ð·Ð²Ð°Ð½Ð¸Ðµ ÐœÐµÐºÑ�Ð¸ÐºÐ°Ì�Ð½Ñ�ÐºÐ¸Ðµ Ð¡Ð¾ÐµÐ´Ð¸Ð½Ñ‘Ð½Ð½Ñ‹Ðµ Ð¨Ñ‚Ð°Ì�Ñ‚Ñ‹ ï¿½ â€” Ð³Ð¾Ñ�ÑƒÐ´Ð°Ñ€Ñ�Ñ‚Ð²Ð¾ Ð² Ð¡ÐµÐ²ÐµÑ€Ð½Ð¾Ð¹ Ð�Ð¼ÐµÑ€Ð¸ÐºÐµ, Ð³Ñ€Ð°Ð½Ð¸Ñ‡Ð°Ñ‰ÐµÐµ Ð½Ð° Ñ�ÐµÐ²ÐµÑ€Ðµ Ñ� Ð¡Ð¨Ð�, Ð½Ð° ÑŽÐ³Ð¾-Ð²Ð¾Ñ�Ñ‚Ð¾ÐºÐµï¿½ â€” Ñ� Ð‘ÐµÐ»Ð¸Ð·Ð¾Ð¼ Ð¸ Ð“Ð²Ð°Ñ‚ÐµÐ¼Ð°Ð»Ð¾Ð¹, Ð½Ð° Ñ�ÐµÐ²ÐµÑ€Ð¾-Ð·Ð°Ð¿Ð°Ð´Ðµ Ð¾Ð¼Ñ‹Ð²Ð°ÐµÑ‚Ñ�Ñ� Ð²Ð¾Ð´Ð°Ð¼Ð¸ ÐšÐ°Ð»Ð¸Ñ„Ð¾Ñ€Ð½Ð¸Ð¹Ñ�ÐºÐ¾Ð³Ð¾ Ð·Ð°Ð»Ð¸Ð²Ð° Ð¸ Ð¢Ð¸Ñ…Ð¾Ð³Ð¾ Ð¾ÐºÐµÐ°Ð½Ð°, Ð½Ð° Ð²Ð¾Ñ�Ñ‚Ð¾ÐºÐµï¿½ â€” Ð²Ð¾Ð´Ð°Ð¼Ð¸ ÐœÐµÐºÑ�Ð¸ÐºÐ°Ð½Ñ�ÐºÐ¾Ð³Ð¾ Ð·Ð°Ð»Ð¸Ð²Ð° Ð¸ ÐšÐ°Ñ€Ð¸Ð±Ñ�ÐºÐ¾Ð³Ð¾ Ð¼Ð¾Ñ€Ñ�.</rdfs:comment>
    <dbpprop:imageCoat rdf:resource="http://upload.wikimedia.org/wikipedia/commons/2/2a/Coat_of_arms_of_Mexico.svg"/>
    <dbpprop:accessdate rdf:datatype="http://www.w3.org/2001/XMLSchema#date">2007-10-07</dbpprop:accessdate>
    <dbpprop:hdiYear rdf:datatype="http://www.w3.org/2001/XMLSchema#integer">2007</dbpprop:hdiYear>
    <dbpprop:gdpPppPerCapita rdf:datatype="http://dbpedia.org/units/Dollar">14120</dbpprop:gdpPppPerCapita>
    <dbpprop:url rdf:resource="http://www.foreignaffairs.org/20060101faessay85106/enrique-krauze/furthering-democracy-in-mexico.html"/>
    <rdfs:comment xml:lang="da">Mexico eller Mexicos Forenede Stater (pÃ¥ spansk MÃ©xico eller Estados Unidos Mexicanos; den alternative stavemÃ¥de MÃ©jico er udbredt i Spanien, men anvendes nÃ¦sten ikke i resten af den spansk-talende verden) er et nordamerikansk land, der mod nord grÃ¦nser op til USA, mod syd til Guatemala og Belize, mod vest til Stillehavet og mod Ã¸st til den Mexicanske Golf og det Caribiske Hav.</rdfs:comment>
    <georss:point>19.05 -99.3666666667</georss:point>
    <rdfs:comment xml:lang="pt">O MÃ©xico (de nome oficial Estados Unidos Mexicanos) Ã© um paÃ­s localizado na AmÃ©rica do Norte delimitado ao norte pelos Estados Unidos da AmÃ©rica, a leste pelo Golfo do MÃ©xico e pelo Mar das CaraÃ­bas, atravÃ©s dos quais se aproxima de Cuba, a sul pela Guatemala e por Belize, e a oeste pelo Oceano PacÃ­fico.</rdfs:comment>
    <rdf:type rdf:resource="http://umbel.org/umbel/sc/Place"/>
    <dbpprop:abstract xml:lang="de">Mexiko ist ein Staat in Nordamerika. Es grenzt im Norden an die Vereinigten Staaten von Amerika und im SÃ¼den an Belize und Guatemala. Westlich liegt der Pazifik, im Osten der Golf von Mexiko sowie das Karibische Meer, beides Randmeere des Atlantiks.</dbpprop:abstract>
    <dbpprop:url rdf:resource="http://estadis.eluniversal.com.mx/notas/301198.html"/>
    <dbpprop:url rdf:resource="http://www.ezilon.com/information/article_476.shtm"/>
    <owl:sameAs rdf:resource="http://www4.wiwiss.fu-berlin.de/factbook/resource/Mexico"/>
    <dbpprop:nationalAnthem xml:lang="en">"Himno Nacional Mexicano"&lt;br&gt;Mexican National Anthem</dbpprop:nationalAnthem>
    <skos:subject rdf:resource="http://dbpedia.org/resource/Category:Mexico"/>
    <dbpprop:audioEsProperty xml:lang="en">EUM.ogg</dbpprop:audioEsProperty>
    <dbpprop:longd rdf:datatype="http://www.w3.org/2001/XMLSchema#integer">99</dbpprop:longd>
    <dbpprop:title xml:lang="en">Furthering Democracy in Mexico</dbpprop:title>
    <dbpprop:wikiPageUsesTemplate rdf:resource="http://dbpedia.org/resource/Template:see_also"/>
    <dbpprop:imageFlag rdf:resource="http://upload.wikimedia.org/wikipedia/commons/f/fc/Flag_of_Mexico.svg"/>
    <dbpprop:journal xml:lang="en">Financial Times</dbpprop:journal>
    <rdfs:label xml:lang="no">Mexico</rdfs:label>
    <rdfs:label xml:lang="da">Mexico</rdfs:label>
    <rdfs:label xml:lang="en">Mexico</rdfs:label>
    <dbpprop:disambiguates rdf:resource="http://dbpedia.org/resource/Mexico_%28village%29%2C_New_York"/>
    <dbpprop:abstract xml:lang="ru">ÐœÐµÌ�ÐºÑ�Ð¸ÐºÐ° (Ð¸Ñ�Ð¿. MÃ©xico), Ð¾Ñ„Ð¸Ñ†Ð¸Ð°Ð»ÑŒÐ½Ð¾Ðµ Ð½Ð°Ð·Ð²Ð°Ð½Ð¸Ðµ ÐœÐµÐºÑ�Ð¸ÐºÐ°Ì�Ð½Ñ�ÐºÐ¸Ðµ Ð¡Ð¾ÐµÐ´Ð¸Ð½Ñ‘Ð½Ð½Ñ‹Ðµ Ð¨Ñ‚Ð°Ì�Ñ‚Ñ‹ Â â€” Ð³Ð¾Ñ�ÑƒÐ´Ð°Ñ€Ñ�Ñ‚Ð²Ð¾ Ð² Ð¡ÐµÐ²ÐµÑ€Ð½Ð¾Ð¹ Ð�Ð¼ÐµÑ€Ð¸ÐºÐµ, Ð³Ñ€Ð°Ð½Ð¸Ñ‡Ð°Ñ‰ÐµÐµ Ð½Ð° Ñ�ÐµÐ²ÐµÑ€Ðµ Ñ� Ð¡Ð¨Ð�, Ð½Ð° ÑŽÐ³Ð¾-Ð²Ð¾Ñ�Ñ‚Ð¾ÐºÐµÂ â€” Ñ� Ð‘ÐµÐ»Ð¸Ð·Ð¾Ð¼ Ð¸ Ð“Ð²Ð°Ñ‚ÐµÐ¼Ð°Ð»Ð¾Ð¹, Ð½Ð° Ñ�ÐµÐ²ÐµÑ€Ð¾-Ð·Ð°Ð¿Ð°Ð´Ðµ Ð¾Ð¼Ñ‹Ð²Ð°ÐµÑ‚Ñ�Ñ� Ð²Ð¾Ð´Ð°Ð¼Ð¸ ÐšÐ°Ð»Ð¸Ñ„Ð¾Ñ€Ð½Ð¸Ð¹Ñ�ÐºÐ¾Ð³Ð¾ Ð·Ð°Ð»Ð¸Ð²Ð° Ð¸ Ð¢Ð¸Ñ…Ð¾Ð³Ð¾ Ð¾ÐºÐµÐ°Ð½Ð°, Ð½Ð° Ð²Ð¾Ñ�Ñ‚Ð¾ÐºÐµÂ â€” Ð²Ð¾Ð´Ð°Ð¼Ð¸ ÐœÐµÐºÑ�Ð¸ÐºÐ°Ð½Ñ�ÐºÐ¾Ð³Ð¾ Ð·Ð°Ð»Ð¸Ð²Ð° Ð¸ ÐšÐ°Ñ€Ð¸Ð±Ñ�ÐºÐ¾Ð³Ð¾ Ð¼Ð¾Ñ€Ñ�. Â«Ð¡Ñ‚Ñ€Ð°Ð½Ð° Ð¼ÑƒÐ´Ñ€ÐµÐ½Ð°Ñ�Â»Â â€” Ñ�ÐºÐ°Ð·Ð°Ð» Ð¾ Ð½ÐµÐ¹ ÐœÐ°Ñ€Ðº Ð�Ð»Ð´Ð°Ð½Ð¾Ð². Ð¢Ð°Ð¼ Ð² Ð´Ñ€ÐµÐ²Ð½Ð¾Ñ�Ñ‚Ð¸ Ð¿Ñ€Ð¾Ñ†Ð²ÐµÑ‚Ð°Ð»Ð¸ Ñ†Ð¸Ð²Ð¸Ð»Ð¸Ð·Ð°Ñ†Ð¸Ð¸ Ð¼Ð°Ð¹Ñ� Ð¸ Ð°Ñ†Ñ‚ÐµÐºÐ¾Ð², Ð½Ð¾ Ð³Ð¾Ñ€Ñ�Ñ‚ÐºÐ° Ð¸Ñ�Ð¿Ð°Ð½Ñ�ÐºÐ¸Ñ… ÐºÐ¾Ð½ÐºÐ¸Ñ�Ñ‚Ð°Ð´Ð¾Ñ€Ð¾Ð² Ð²Ð¾ Ð³Ð»Ð°Ð²Ðµ Ñ� Ð­Ñ€Ð½Ð°Ð½Ð¾Ð¼ ÐšÐ¾Ñ€Ñ‚ÐµÑ�Ð¾Ð¼ Ð·Ð°Ñ�Ñ‚Ð°Ð»Ð° Ñ‚Ð°ÐºÑƒÑŽ Ð»ÑŽÑ‚ÑƒÑŽ Ð²Ñ€Ð°Ð¶Ð´Ñƒ Ð¼ÐµÐ¶Ð´Ñƒ Ð¿Ð»ÐµÐ¼ÐµÐ½Ð°Ð¼Ð¸, Ñ‡Ñ‚Ð¾ Ñ�ÑƒÐ¼ÐµÐ»Ð° Ð·Ð°Ñ…Ð²Ð°Ñ‚Ð¸Ñ‚ÑŒ Ð²Ñ�ÑŽ Ñ�Ñ‚Ñ€Ð°Ð½Ñƒ, Ð¸ Ð² 1535 ÐœÐµÐºÑ�Ð¸ÐºÐ° Ñ�Ñ‚Ð°Ð»Ð° Ð²Ð¸Ñ†Ðµ-ÐºÐ¾Ñ€Ð¾Ð»ÐµÐ²Ñ�Ñ‚Ð²Ð¾Ð¼ Ð�Ð¾Ð²Ð°Ñ� Ð˜Ñ�Ð¿Ð°Ð½Ð¸Ñ�. Ð—Ð° Ñ‚Ñ€Ð¸ Ð²ÐµÐºÐ° Ð¸Ð½Ð´ÐµÐ¹Ñ†Ñ‹ Ð°Ñ�Ñ�Ð¸Ð¼Ð¸Ð»Ð¸Ñ€Ð¾Ð²Ð°Ð»Ð¸ Ð±ÐµÐ»Ñ‹Ñ… Ð¸ Ð½ÐµÐ³Ñ€Ð¾Ð², Ñ�Ð°Ð¼Ð¸ ÑƒÑ�Ð²Ð¾Ð¸Ð»Ð¸ ÐºÐ°Ñ‚Ð¾Ð»Ð¸Ñ‡ÐµÑ�Ñ‚Ð²Ð¾ Ð¸ Ð¸Ñ�Ð¿Ð°Ð½Ñ�ÐºÐ¸Ð¹ Ñ�Ð·Ñ‹Ðº. Ð’ 1824 Ð¿Ñ€Ð¾Ð²Ð¾Ð·Ð³Ð»Ð°ÑˆÐµÐ½Ð° Ð½ÐµÐ·Ð°Ð²Ð¸Ñ�Ð¸Ð¼Ð°Ñ� Ñ„ÐµÐ´ÐµÑ€Ð°Ñ‚Ð¸Ð²Ð½Ð°Ñ� Ñ€ÐµÑ�Ð¿ÑƒÐ±Ð»Ð¸ÐºÐ°, Ð½Ð¾ Ð·ÐµÐ¼Ð»Ñ� Ð¾Ñ�Ñ‚Ð°Ð»Ð°Ñ�ÑŒ Ð² Ñ€ÑƒÐºÐ°Ñ… ÐºÑ€ÐµÐ¾Ð»Ð¾Ð², Ð¸ ÐµÑ‰Ðµ Ð²ÐµÐº Ñ�Ñ‚Ñ€Ð°Ð½Ñƒ Ñ‚ÐµÑ€Ð·Ð°Ð»Ð¸ Ð¿ÐµÑ€ÐµÐ²Ð¾Ñ€Ð¾Ñ‚Ñ‹, Ð´Ð¸ÐºÑ‚Ð°Ñ‚ÑƒÑ€Ñ‹ Ð¸ Ð³Ñ€Ð°Ð¶Ð´Ð°Ð½Ñ�ÐºÐ¸Ðµ Ð²Ð¾Ð¹Ð½Ñ‹. Ðš Ñ‚Ð¾Ð¼Ñƒ Ð¶Ðµ Ð² 1846â€”1848 Ð¡Ð¨Ð� Ð·Ð°Ñ…Ð²Ð°Ñ‚Ð¸Ð»Ð¸ Ð¿Ð¾Ð»Ð¾Ð²Ð¸Ð½Ñƒ Ð¼ÐµÐºÑ�Ð¸ÐºÐ°Ð½Ñ�ÐºÐ¾Ð¹ Ñ‚ÐµÑ€Ñ€Ð¸Ñ‚Ð¾Ñ€Ð¸Ð¸. Ð�Ð°Ð´ÐµÐ»Ð¸Ñ‚ÑŒ ÐºÑ€ÐµÑ�Ñ‚ÑŒÑ�Ð½ Ð·ÐµÐ¼Ð»ÐµÐ¹ Ð¿Ñ‹Ñ‚Ð°Ð»Ñ�Ñ� Ð¿Ñ€ÐµÐ·Ð¸Ð´ÐµÐ½Ñ‚ Ð‘ÐµÐ½Ð¸Ñ‚Ð¾ Ð¥ÑƒÐ°Ñ€ÐµÑ�, Ð½Ð¾ Ñ�Ð¼Ð¾Ð³Ð»Ð¸ Ñ�Ð´ÐµÐ»Ð°Ñ‚ÑŒ Ñ�Ñ‚Ð¾ Ñ‚Ð¾Ð»ÑŒÐºÐ¾ Ð²Ð¾Ð¶Ð´Ð¸ Ñ€ÐµÐ²Ð¾Ð»ÑŽÑ†Ð¸Ð¸ 1911â€”1918: Ð’ÐµÐ½ÑƒÑ�Ñ‚Ð¸Ð°Ð½Ð¾ ÐšÐ°Ñ€Ñ€Ð°Ð½Ñ�Ð° Ð¸ Ð­Ð¼Ð¸Ð»Ð¸Ð°Ð½Ð¾ Ð¡Ð°Ð¿Ð°Ñ‚Ð°. ÐŸÑ€Ð¸Ð½Ñ�Ñ‚Ð°Ñ� Ð² 1917 ÐšÐ¾Ð½Ñ�Ñ‚Ð¸Ñ‚ÑƒÑ†Ð¸Ñ� Ð´ÐµÐ¹Ñ�Ñ‚Ð²ÑƒÐµÑ‚ Ð´Ð¾ Ñ�Ð¸Ñ… Ð¿Ð¾Ñ€, Ð½Ð¾ Ð²Ð»Ð°Ñ�Ñ‚ÑŒ Ñ� 1929 Ð½Ð°Ñ…Ð¾Ð´Ð¸Ñ‚Ñ�Ñ� Ð² Ñ€ÑƒÐºÐ°Ñ… Ð¾Ð´Ð½Ð¾Ð¹ Ð¿Ð°Ñ€Ñ‚Ð¸Ð¸ . ÐœÐµÐºÑ�Ð¸ÐºÐ° Ñ�Ð²Ð»Ñ�ÐµÑ‚Ñ�Ñ� Ñ�Ð°Ð¼Ð¾Ð¹ Ñ�ÐµÐ²ÐµÑ€Ð½Ð¾Ð¹ Ð¸Ð· Ñ�Ñ‚Ñ€Ð°Ð½ Ð›Ð°Ñ‚Ð¸Ð½Ñ�ÐºÐ¾Ð¹ Ð�Ð¼ÐµÑ€Ð¸ÐºÐ¸ Ð¸ Ñ�Ð°Ð¼Ð¾Ð¹ Ð½Ð°Ñ�ÐµÐ»Ñ‘Ð½Ð½Ð¾Ð¹ Ð¸Ð· Ð¸Ñ�Ð¿Ð°Ð½Ð¾Ð³Ð¾Ð²Ð¾Ñ€Ñ�Ñ‰Ð¸Ñ… Ñ�Ñ‚Ñ€Ð°Ð½.</dbpprop:abstract>
    <foaf:name>United Mexican States</foaf:name>
    <dbpprop:largestCity xml:lang="en">capital</dbpprop:largestCity>
    <rdf:type rdf:resource="http://umbel.org/umbel/sc/EconomicSystem"/>
    <dbpprop:date rdf:datatype="http://www.w3.org/2001/XMLSchema#date">2005-08-24</dbpprop:date>
    <dbpprop:date rdf:datatype="http://www.w3.org/2001/XMLSchema#date">2005-05-02</dbpprop:date>
    <dbpprop:seeAlsoProperty xml:lang="en">1986 FIFA World Cup</dbpprop:seeAlsoProperty>
    <dbpedia-owl:populationDensity rdf:datatype="http://dbpedia.org/ontology/inhabitantsPerSquareMile">142</dbpedia-owl:populationDensity>
    <skos:subject rdf:resource="http://dbpedia.org/resource/Category:G15_nations"/>
    <dbpedia-owl:language rdf:resource="http://dbpedia.org/resource/Languages_of_Mexico"/>
    <foaf:img rdf:resource="http://upload.wikimedia.org/wikipedia/commons/9/90/Seal_of_the_Government_of_Mexico.svg"/>
    <dbpprop:portalProperty xml:lang="en">Mexico</dbpprop:portalProperty>
    <rdfs:label xml:lang="sv">Mexiko</rdfs:label>
    <rdfs:label xml:lang="de">Mexiko</rdfs:label>
    <rdf:type rdf:resource="http://dbpedia.org/ontology/Country"/>
    <rdfs:label xml:lang="it">Messico</rdfs:label>
    <dbpprop:first xml:lang="en">Adam</dbpprop:first>
    <dbpedia-owl:latitudeminutes>03</dbpedia-owl:latitudeminutes>
    <rdf:type rdf:resource="http://dbpedia.org/ontology/Place"/>
    <dbpprop:date rdf:datatype="http://www.w3.org/2001/XMLSchema#date">2001-07-10</dbpprop:date>
    <dbpprop:date rdf:datatype="http://www.w3.org/2001/XMLSchema#date">2006-06-20</dbpprop:date>
    <rdfs:label xml:lang="nl">Mexico (land)</rdfs:label>
    <dbpprop:gdpPppRank rdf:datatype="http://dbpedia.org/units/Rank">12</dbpprop:gdpPppRank>
    <dbpprop:areaMagnitude xml:lang="en">1 E12</dbpprop:areaMagnitude>
    <dbpedia-owl:longitudedegrees>99</dbpedia-owl:longitudedegrees>
    <dbpprop:wikiPageUsesTemplate rdf:resource="http://dbpedia.org/resource/Template:portal"/>
    <dbpedia-owl:capital rdf:resource="http://dbpedia.org/resource/Mexico_City"/>
    <dbpprop:populationEstimate rdf:datatype="http://www.w3.org/2001/XMLSchema#integer">106682500</dbpprop:populationEstimate>
    <rdf:type rdf:resource="http://sw.opencyc.org/2008/06/10/concept/Mx4rwNJMLpwpEbGdrcN5Y29ycA"/>
    <dbpprop:establishedEvent xml:lang="en">Declared</dbpprop:establishedEvent>
    <dbpprop:url rdf:resource="http://www.ft.com"/>
    <dbpprop:title xml:lang="en">Sobresale Nuevo LeÃ³n por su alto nivel de vida</dbpprop:title>
    <dbpprop:disambiguates rdf:resource="http://dbpedia.org/resource/Mexico%2C_Juniata_County%2C_Pennsylvania"/>
    <dbpprop:gdpNominalRank rdf:datatype="http://dbpedia.org/units/Rank">14</dbpprop:gdpNominalRank>
    <dbpprop:disambiguates rdf:resource="http://dbpedia.org/resource/Mexico%2C_Kentucky"/>
    <dbpprop:wikiPageUsesTemplate rdf:resource="http://dbpedia.org/resource/Template:infobox_country"/>
    <rdf:type rdf:resource="http://umbel.org/umbel/sc/GeographicalRegion"/>
    <rdfs:label xml:lang="fr">Mexique</rdfs:label>
    <dbpprop:governmentType rdf:resource="http://dbpedia.org/resource/Presidential_system"/>
    <rdfs:comment xml:lang="ja">ä¸–ç•Œ &amp;gt; åŒ—ã‚¢ãƒ¡ãƒªã‚« &amp;gt; ãƒ¡ã‚­ã‚·ã‚³'ãƒ¡ã‚­ã‚·ã‚³å�ˆè¡†å›½'ï¼ˆãƒ¡ã‚­ã‚·ã‚³ã�Œã�£ã�—ã‚…ã�†ã�“ã��ï¼‰ã€�é€šç§°ãƒ¡ã‚­ã‚·ã‚³ã�¯ã€�åŒ—ã‚¢ãƒ¡ãƒªã‚«å�—éƒ¨ã�«ä½�ç½®ã�™ã‚‹ãƒ©ãƒ†ãƒ³ã‚¢ãƒ¡ãƒªã‚«ã�®é€£é‚¦å…±å’Œåˆ¶å›½å®¶ã�§ã�‚ã‚‹ã€‚åŒ—ã�«ã‚¢ãƒ¡ãƒªã‚«å�ˆè¡†å›½ã�¨ã€�å�—æ�±ã�«ã‚°ã‚¢ãƒ†ãƒžãƒ©ã€�ãƒ™ãƒªãƒ¼ã‚ºã�¨å›½å¢ƒã‚’æŽ¥ã�—ã€�è¥¿ã�¯å¤ªå¹³æ´‹ã€�æ�±ã�¯å¤§è¥¿æ´‹ã�¨ã‚«ãƒªãƒ–æµ·ã�®ãƒ¡ã‚­ã‚·ã‚³æ¹¾ã�«é�¢ã�™ã‚‹ã€‚é¦–éƒ½ã�¯ãƒ¡ã‚­ã‚·ã‚³å¸‚ã€‚ãƒ©ãƒ†ãƒ³ã‚¢ãƒ¡ãƒªã‚«æœ€åŒ—ã�«ä½�ç½®ã�—ã€�ãƒ–ãƒ©ã‚¸ãƒ«ã€�ã‚¢ãƒ«ã‚¼ãƒ³ãƒ�ãƒ³ã�«æ¬¡ã�„ã�§é�¢ç©�ã�¯3ç•ªç›®ã�®å¤§ã��ã�•ã�§ã�‚ã‚‹ã€‚ã�¾ã�Ÿã€�ã�Šã‚ˆã��ä¸€å„„äººã�®ç·�äººå�£ã�¯ã€�ãƒ©ãƒ†ãƒ³ã‚¢ãƒ¡ãƒªã‚«ã�§ã�¯2ç•ªç›®ã�«å¤§ã��ã��ã€�ã‚¹ãƒšã‚¤ãƒ³èªžåœ�å…¨ä½“ã�§ã�¯æœ€å¤§ã‚’èª‡ã‚‹ã€‚</rdfs:comment>
    <dbpprop:giniYear rdf:datatype="http://www.w3.org/2001/XMLSchema#integer">2008</dbpprop:giniYear>
    <dbpprop:journal xml:lang="en">Ezilon Infobase</dbpprop:journal>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jos%C3%A9_Antonio_Roca">
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofdeath rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Julieta_Venegas">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Salamanca_F.C.">
    <dbpprop:ground rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Estadio_Tecnol%C3%B3gico">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/El_Paso_and_Southwestern_Railroad">
    <dbpprop:locale rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mercedes-Benz_B-Class">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/David_R._Flores">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tropical_Storm_Aletta_%282006%29">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jos%C3%A9_Alfredo_Jim%C3%A9nez">
    <dbpprop:born rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:died rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XECOPA-AM">
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Hecelchak%C3%A1n">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Diego_de_la_Torre">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Carmen_%28municipality%29">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Fuego_En_La_Sangre">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XETT-AM">
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Agust%C3%ADn_Enrique">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHJAL-TV">
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tropical_Storm_Beryl_%282000%29">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Efra%C3%ADn_Velarde">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Hurricane_Dolly_%281996%29">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Juan_Castro">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Advances_in_Applied_Clifford_Algebras">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Carlos_Reygadas">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Pontiac_Grand_Prix">
    <dbpedia-owl:successor rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Adrian_Zerme%C3%B1o">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Caesar_salad">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Prince_Hubertus_of_Hohenlohe-Langenburg">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ernesto_Zedillo">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Max_Wagner">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Alberto_Zeni">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEUBS">
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Lumi_Cavazos">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/H%C3%A9ctor_Reynoso">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:cityofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Volkswagen_Jetta">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tlapanec_language">
    <dbpedia-owl:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Cuilco_River">
    <dbpprop:basinCountries rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHUU-FM">
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Pontiac_Aztek">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mar%C3%ADa_Isabel_%28telenovela%29">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHMI-FM">
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHCAO">
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/List_of_NCIS_characters">
    <dbpprop:home rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Corporaci%C3%B3n_Andina_de_Fomento">
    <dbpprop:_percent_7B_percent_7Bflagicon_percent_23_percent_23_percent_23_percent_23mexico_percent_23_percent_23_percent_23_percent_23size rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Totonacan_languages">
    <dbpprop:region rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Penelope_Menchaca">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/David_Etherly">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Juan_Corona">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/National_Auditorium">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Como_Te_Extra%C3%B1o_Coraz%C3%B3n">
    <dbpprop:recorded rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:recordplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ford_Fusion_%28Americas%29">
    <dbpedia-owl:predecessor rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHEMZ-FM">
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHOH-FM">
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/AMC_Hornet">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ram%C3%B3n_Novarro">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Juan_Pablo_Santiago">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jes%C3%BAs_Zavala">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jorge_Campos">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/1939_International_Lawn_Tennis_Challenge">
    <dbpprop:rd1t2Loc rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Amigos_X_Siempre_%28soundtrack%29">
    <dbpedia-owl:recordplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tatiana_%28singer%29">
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mixe_languages">
    <dbpedia-owl:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Victoriano_Huerta">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Paseo_San_Pedro">
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jasmine_Byrne">
    <dbpedia-owl:ethnicity rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mario_Rodr%C3%ADguez_Cervantes">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Eduardo_Rergis">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Adri%C3%A1n_Ch%C3%A1vez">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/2007_U-20_World_Cup_CONCACAF_qualifying_tournament/footballbox4">
    <dbpprop:stadium rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ricardo_Osorio">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHRT">
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mexiconservaci%C3%B3n">
    <dbpedia-owl:foundationplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XERU">
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:format rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Christian_Armas">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Thomas_L._Harris">
    <dbpprop:battles rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Antonio_Salazar_%28footballer%29">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Battle_of_San_Pasqual">
    <dbpprop:combatant rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Cultural_Experiences_Abroad">
    <dbpprop:products rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:products rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Grupo_Continental">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Luis_Roberto_Alves">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Universidad_An%C3%A1huac_del_Sur">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHDGO-FM">
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mar%C3%ADa_Mercedes_%28telenovela%29">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEIC-AM">
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Oakie_Doke">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Area_code_575">
    <dbpprop:s rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Charles_Trowbridge">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Suburbia_%28department_store%29">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/NACER.org">
    <dbpprop:areaServed rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Luis_Omar_Hern%C3%A1ndez">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XELAC-AM">
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Matando_Cabos">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Quitupan">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Austin_TV">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Marcella_Grace_Eiler">
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/C%C3%A9sar_Costa">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/ALMA_de_M%C3%A9xico">
    <dbpprop:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Bill_Mel%C3%A9ndez">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Daniel_Tovar">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEERO">
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Iyari_Limon">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Revista_de_la_Sociedad_Qu%C3%ADmica_de_Mexico">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Patricio_Araujo">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Chevrolet_Avalanche">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Hurricane_Dolly_%282008%29">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Marvin_Cabrera">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Huapango">
    <dbpprop:culturalOrigins rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ford_Super_Duty">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Alborada">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tecnol%C3%B3gico_de_Monterrey%2C_Campus_Santa_Fe">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Everardo_Crist%C3%B3bal">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Barra_de_Navidad%2C_Jalisco">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/The_Palm_%28restaurant%29">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Esther_Orozco">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Control_Machete">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Blossoms_of_Fire">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jan_Carlo_DeFan">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/1997_Little_League_World_Series">
    <dbpprop:champion rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XETI">
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Kion_de_Mexico">
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Luigi_Raimondi/succession_box2">
    <dbpprop:title rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mexican_United_States">
    <dbpprop:redirect rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tlapa_de_Comonfort">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Hugo_Alcaraz-Cuellar">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/The_Mole_%28US_TV_series%29">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Hurricane_Hilda_%281955%29">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHZPL">
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Misi%C3%B3n_S.O.S">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHARZ">
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:format rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Canada_U-20_men%27s_national_soccer_team">
    <dbpprop:firstGame rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Zumpahuac%C3%A1n_%28municipality%29">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mi_peque%C3%B1a_Soledad">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Republicair">
    <dbpprop:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/H%C3%A9ctor_Vel%C3%A1zquez">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Undo_magazine">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Grupo_Lala">
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Daisy_Marie">
    <dbpedia-owl:ethnicity rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Discos_Konfort">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Manuel_Sandoval_Vallarta">
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:residence rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:award rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:residence rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Dodge_Shadow">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Galaxy_18">
    <dbpprop:coverage rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Miss_Universe_1993">
    <dbpprop:venue rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Miguel_Pro">
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jacqueline_Voltaire">
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/San_Pedro_River_%28Guatemala%29">
    <dbpprop:basinCountries rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ford_Focus_%28North_America%29">
    <dbpedia-owl:predecessor rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jehu_Chiapas">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Santiago_Ventura_Morales">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Poderoso">
    <dbpprop:recorded rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:recordplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ustedes_los_ricos">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHMR">
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:format rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Motel_%28band%29">
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/La_Lupita">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/1968_Summer_Olympics">
    <dbpprop:hostCity rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ana_Guevara">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Alejandro_Arg%C3%BCello">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEQIN-AM">
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Khotan_Fern%C3%A1ndez">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Rosario_Castellanos">
    <dbpprop:dateOfBirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Matamoros%2C_Coahuila">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Melchor_Cob_Castro">
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/International_Relations_Centre%2C_at_Political_and_Social_Sciences_School">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHMU">
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Seri">
    <dbpprop:popplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XERI">
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Maldita_Vecindad">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Isthmus_Zapotec">
    <dbpprop:region rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:region rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Javier_Hern%C3%A1ndez_%28footballer%29">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/San_Nicol%C3%A1s_de_los_Garza">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Nightmare_City">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Michi_Munoz">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Fando_y_Lis">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Miguel_Zacar%C3%ADas">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Instituto_Tecnol%C3%B3gico_de_Nogales">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Andrew_%22Carter%22_Brown">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Miguel_del_Toro">
    <dbpprop:dateOfDeath rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:dateOfBirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Miroslava_Stern">
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jorge_Bernal">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Roberto_Orci">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/La_Ch%C3%A8vre">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHBJ-TV">
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Dulce_Liquido">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Area_code_956">
    <dbpprop:w rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:s rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEXPUJ-AM">
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XESFT">
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Dafne_Molina">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Katalina_Verdin">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Israel_Castro">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ciro_Rodriguez">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Francisco_Palencia">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Wilhelm_von_Homburg">
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Rub%C3%AD">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Juan_Carlos_Silva">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Battle_of_Tecoac">
    <dbpedia-owl:place rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:place rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:combatant rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tecate_%28municipality%29">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEPUR-AM">
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jos%C3%A9_%C3%81ngel_Gurr%C3%ADa">
    <dbpprop:office rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Katy_Jurado">
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/De_Pel%C3%ADcula">
    <dbpedia-owl:locationCountry rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tlacotalpan_%28municipality%29">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Estados_Unidos_Mexicanos">
    <dbpprop:redirect rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEGH">
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jhonny_Gonz%C3%A1lez">
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Eduardo_Barraza">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Chetes">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:born rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Joe_Gray">
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Omar_Esparza">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Nayar_Town">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Charles_Mingus">
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:died rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHTPO">
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEDD-AM">
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Pame_language">
    <dbpedia-owl:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Saab_9-4X">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Macultepec">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/CONCACAF_Champions%27_Cup_2005/footballbox12">
    <dbpprop:stadium rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/1937_International_Lawn_Tennis_Challenge">
    <dbpprop:rd1t1Loc rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Xavier_Baez">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Otomi_language">
    <dbpedia-owl:region rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:region rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHAGT">
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:format rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Montserrat_Olivier">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Juan_D%C3%ADaz_%28Mexican_American_boxer%29">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Leanna_Foxxx">
    <dbpedia-owl:ethnicity rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Manuel_Negrete_Arias">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/M101_howitzer">
    <dbpprop:usedBy rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Blue_Demon">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Volkswagen_New_Beetle">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Zurdok">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/El_Colegio_de_M%C3%A9xico">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Fabi%C3%A1n_Villase%C3%B1or">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Henry_Eyring">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Aero_Cuahonte">
    <dbpprop:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Julieta_Rosen">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHOX-FM">
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Guarijio_language">
    <dbpprop:region rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:region rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Grupo_Aerom%C3%B3vil_de_Fuerzas_Especiales">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/1931_Belize_hurricane">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/De_Pel%C3%ADcula_Cl%C3%A1sico">
    <dbpedia-owl:locationCountry rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Heridas_de_Amor">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Marco_Antonio_Barrera">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XERP">
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jorge_Van_Rankin">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mariano_Riva_Palacio">
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Adolfo_%C3%81ngel">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Nina_Mercedez">
    <dbpedia-owl:ethnicity rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Julio_Nava">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Emmanuel_Cerda">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/George_J._Lewis">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tinieblas">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHSFJ-TV">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Juli%C3%A1n_Acu%C3%B1a_Gal%C3%A9">
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHUNL-FM">
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Raul_Julia-Levy">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Juan_Lazcano">
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Humberto_Hern%C3%A1ndez">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Marianela_de_la_Hoz">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Our_Lady_of_Guadalupe_Unfinished_Cathedral">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mariana_Seoane">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHPAZ">
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Juan_Calleros">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Gandikota_V._Rao">
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Juan_Bautista_Vargas_Arreola">
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:allegiance rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tecnol%C3%B3gico_de_Monterrey%2C_Campus_Chihuahua">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Villano_IV">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tropical_Storm_Bret_%282005%29">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Battle_of_Contreras">
    <dbpprop:combatant rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Nicol%C3%A1s_Bravo">
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Hundido_En_Un_Rinc%C3%B3n">
    <dbpedia-owl:recordplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Leonel_Olmedo">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHUAA-FM">
    <dbpedia-owl:format rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEPAB">
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Hurricane_Diana">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Calakmul_%28municipality%29">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Heriberto_Morales">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Texcaltitl%C3%A1n">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Battle_of_Natividad">
    <dbpprop:combatant rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ford_Contour">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Primer_amor..._a_mil_por_hora">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XECM">
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mexico_City_International_Airport">
    <dbpprop:cityServed rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Francisco_Mendoza">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Opel_Zafira">
    <dbpedia-owl:successor rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Adolfo_Su%C3%A1rez_Rivera">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Lupe_V%C3%A9lez">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Afro-Seminole_Creole">
    <dbpprop:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/United_States_occupation_of_Veracruz">
    <dbpprop:combatant rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jaime_Lozano">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/M%C3%A9rida%2C_Yucat%C3%A1n">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Perla_Beltr%C3%A1n">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XERT">
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/1964_Davis_Cup">
    <dbpprop:rd1t3Loc rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:rd2t2Loc rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tecnol%C3%B3gico_de_Monterrey%2C_Campus_Toluca">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Rodrigo_Gonz%C3%A1lez">
    <dbpprop:died rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:born rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Xcaret">
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Battle_of_Puebla">
    <dbpprop:place rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:place rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Yolanda_Andrade">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Laisha_Wilkins">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ana_Gabriel">
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Kate_del_Castillo">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/KBFM">
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Denisse_Guerrero">
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Adrian_Mart%C3%ADnez">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:cityofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Cerro_Potos%C3%AD">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tlaxcaltec">
    <dbpprop:popplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Violet-green_Swallow">
    <dbpedia-owl:binomial_authority rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Chevrolet_Monza">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEVFS-AM">
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Altair_Jarabo">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Zdravko_Rajkov">
    <dbpprop:countryofdeath rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Juan_Carlos_Valenzuela">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Isaac_Payne">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/1962_Davis_Cup">
    <dbpprop:venue rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:rd2t1Loc rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:rd1t2Loc rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:rd3t1Loc rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Huichol_language">
    <dbpprop:region rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:region rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/1988_Mexican_Grand_Prix">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHMLS">
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Israeli_Network">
    <dbpprop:available rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Lorena_Vel%C3%A1zquez">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Hector_Lizarraga">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Todd_Karns">
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Rafael_M%C3%A1rquez">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Orthodox_Church_in_America">
    <dbpprop:possessions rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Pablo_Larios">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Maria_Josepha_Sophia_de_Iturbide">
    <dbpprop:throne rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/2006_FIFA_World_Cup_qualification_-_CONCACAF_third_stage/footballbox12">
    <dbpprop:stadium rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Escuadr%C3%B3n_201">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Lake_Sagami">
    <dbpprop:after rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/1990_Mexican_Grand_Prix">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Evita_Mu%C3%B1oz_%22Chachita%22">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Carlos_Esquivel">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Maribel_Dom%C3%ADnguez">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Colegio_Nacional">
    <dbpprop:regionServed rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/OXXO">
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/2007_Davis_Cup_Americas_Zone">
    <dbpprop:_1rpt1Loc rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Keisha">
    <dbpprop:ethnicity rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHTIT-TV">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Aguascalientes%2C_Aguascalientes">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Bugambilia">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/El_Nuevo_Tesoro_de_la_Juventud">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Daniel_Alc%C3%A1ntar">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Juan_Pablo_Garc%C3%ADa">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Rodolfo_Neri_Vela">
    <dbpprop:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Isidoro_D%C3%ADaz">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ram%C3%B3n_de_la_Fuente_Mu%C3%B1iz">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Alejandro_Alcondez">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Luis_Ernesto_P%C3%A9rez">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Pedro_Fern%C3%A1ndez">
    <dbpprop:born rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/David_Cort%C3%A9s">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Estadio_Ol%C3%ADmpico_Benito_Ju%C3%A1rez">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Franklin%27s_Gull">
    <dbpedia-owl:binomial_authority rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Gael_Garc%C3%ADa_Bernal">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jos%C3%A9_Narro_Robles">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mexican_villa">
    <dbpedia-owl:products rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/%C3%89der_dos_Santos">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Dennis_Ziadie">
    <dbpprop:countryofdeath rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHFCE-FM">
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Highland_Prince_Academy_de_Mexico">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Economy_of_Canada">
    <dbpprop:importPartners rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Aut%C3%B3dromo_Miguel_E._Abed">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Enrique_Borja">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/La_Paz_%28municipality%29">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Miss_Universe_2007">
    <dbpprop:venue rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Gobierno_de_Estados_Unidos_Mexicanos">
    <dbpprop:redirect rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Octaviano_Ambrosio_Larrazolo">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEJ-TV">
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/A%C3%A9reo_Calafia">
    <dbpedia-owl:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mayo_language">
    <dbpprop:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Alfonso_Gomez">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHRW">
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/International_Circle_of_Faith">
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/2005_Tecate/Telmex_Grand_Prix_of_Monterrey">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jes%C3%BAs_Mar%C3%ADa%2C_Jalisco">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Roberto_Rivera">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mixtecan_languages">
    <dbpedia-owl:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEGW">
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Eugenio_Garza_Lag%C3%BCera">
    <dbpedia-owl:residence rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:residence rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Yosgart_Guti%C3%A9rrez">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Parque_la_Junta">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Diego_Gonz%C3%A1lez">
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Roberto_Nurse">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/1965_Davis_Cup">
    <dbpprop:rd2t1Loc rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Royal_and_Pontifical_University_of_Mexico">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Eduardo_Ver%C3%A1stegui">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Chinantec">
    <dbpprop:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Alfredo_Xeque">
    <dbpprop:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:home rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Highland_Totonac">
    <dbpprop:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tropical_Storm_Kiko_%282007%29">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/La_Otra">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/1931_International_Lawn_Tennis_Challenge">
    <dbpprop:rd1t1Loc rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/El_Se%C3%B1or_Presidente">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/%C3%89dgar_Hern%C3%A1ndez_Cabrera">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Margo_%28actress%29">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Matlalcueitl_%28volcano%29">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Gilberto_Rinc%C3%B3n_Gallardo">
    <dbpprop:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Eduardo_Vasconcelos_Stadium">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Matlatzinca_language">
    <dbpprop:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHITD-FM">
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Rebeca_Soriano">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Monterreal">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jorge_Hern%C3%A1ndez_%28footballer%29">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://mpii.de/yago/resource/Mexico">
    <owl:sameAs rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Estadio_Morelos">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Josu%C3%A9_Castillejos">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ignacio_Torres">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Chantal_Andere">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Monterrey_Institute_of_Technology_and_Higher_Education">
    <dbpedia-owl:campus rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHTO-FM">
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Cora_people">
    <dbpprop:popplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ariel_%28laundry%29">
    <dbpprop:markets rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Winston_H._Bostick">
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/WAOS">
    <dbpedia-owl:format rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Walmex">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Grupo_Sanborns">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Acaponeta_River">
    <dbpprop:basinCountries rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Carlos_E._Vela">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Geography_of_Cuba">
    <dbpprop:northwest rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jos%C3%A9_Antonio_Patl%C3%A1n">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Rafael_Medina">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Dodge_Aries">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Shantal_M%C3%A9ndez">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Sahuayo">
    <dbpprop:areaCode rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHNA">
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Emiliano_Zapata">
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Sexo%2C_pudor_y_l%C3%A1grimas">
    <dbpedia-owl:distributor rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ch%27ol_language">
    <dbpprop:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jorge_Iv%C3%A1n_Estrada">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Pan%27s_Labyrinth">
    <dbpedia-owl:distributor rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Claudio_Su%C3%A1rez">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/1950_Davis_Cup">
    <dbpprop:rd2t1Loc rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mercedes-Benz_CL-Class">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Victor_Espinoza">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/AMC_Concord">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XELE">
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ona_Grauer">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHRLM">
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Playas_de_Rosarito%2C_Baja_California">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Diego_Luna">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/United_States_of_Mexico">
    <dbpprop:redirect rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Francisco_Goitia">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XERLK-AM">
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Economy_of_Belize">
    <dbpprop:importPartners rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jos%C3%A9_Joaqu%C3%ADn_de_Herrera">
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHVLN">
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Instituto_Tecnol%C3%B3gico_de_Ciudad_Ju%C3%A1rez">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mario_J._Molina">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Orlando_Salido">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Amuzgo">
    <dbpprop:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Open_Student_Television_Network">
    <dbpprop:available rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tony_Zendejas">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHVIC">
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ana_Serradilla">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Pedro_P%C3%A1ramo">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Organizacion_Corona">
    <dbpedia-owl:areaServed rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:areaServed rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEWO-TV">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Hurricane_Greta-Olivia">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Martha_Higareda">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/The_Black_Pirates">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHUNI">
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/House_of_Terror_%28film%29">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Joakim_Soria">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHMX">
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Enrique_Gonz%C3%A1lez_Mart%C3%ADnez">
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Cantinflas">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Diego_Dreyfus">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Yaqui_language">
    <dbpprop:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Battle_of_Rio_San_Gabriel">
    <dbpprop:combatant rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Nortec_Collective">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ra%C3%BAl_Enr%C3%ADquez">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mexican_Drug_War">
    <dbpprop:combatant rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Casa_Noble">
    <dbpedia-owl:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XETUT">
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/The_WB_Television_Network">
    <dbpprop:available rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Manuel_Lapuente">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Maximilian_I_of_Mexico">
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:reason rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Chevrolet_Malibu">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Siege_of_Puebla">
    <dbpprop:combatant rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Club_Deportivo_U.A.N.L.">
    <dbpprop:ground rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/List_of_Punch-Out%21%21_characters/superpunchoutsnes3">
    <dbpprop:from rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Hurricane_Lorenzo_%282007%29">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Pemex">
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/InterLiga">
    <dbpprop:region rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mercedes-Benz_GLK-Class">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Enciende_Una_Luz">
    <dbpedia-owl:recordplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:recorded rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jes%C3%BAs_Olalde">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/%C3%81ngel_Maturino_Res%C3%A9ndiz">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Francisco_Javier_Gonzalez_%28Mexican_footballer%29">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Facultad_de_Ingenieria%2C_UNAM">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Silvestre_S._Herrera">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Francisco_Bojado">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/C%C3%B3mplices_Al_Rescate:_Silvana">
    <dbpedia-owl:recordplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Autonomous_University_of_Queretaro">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Cocopah_language">
    <dbpprop:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ricardo_Pel%C3%A1ez">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:cityofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/H%C3%A9ctor_Sober%C3%B3n">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Iztacc%C3%ADhuatl">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/ALFA_%28Mexico%29">
    <dbpedia-owl:locationcountry rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:locationCountry rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Horacio_Cervantes">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/C%C3%A9sar_Villaluz">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Frank_McEwen">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ciudad_Deportiva_%28Nuevo_Laredo%29">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Cenobita">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Carlos_Kasuga_Osaka">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Sergio_Mayer">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Alberto_Garc%C3%ADa_Aspe">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Y%C3%A9cora%2C_Sonora">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Paul_Rodriguez">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mar%C3%ADa_la_del_Barrio">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/La_Mojarra_Stela_1">
    <dbpprop:discovered rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ismael_%C3%8D%C3%B1iguez">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Manuel_Doblado">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Falta_Amor">
    <dbpprop:recorded rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:recordplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Lobos_de_la_BUAP">
    <dbpprop:ground rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Botellita_de_Jer%C3%A9z">
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Carmen_Barajas_Sandoval">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Gamesa">
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Batall%C3%B3n_de_San_Blas">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Haley_Paige">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Fey_%28singer%29">
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Byron_Alvarez">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Colorado_River">
    <dbpprop:basinCountries rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEWD">
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Joaqu%C3%ADn_Beltr%C3%A1n">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Chontal_Maya_language">
    <dbpprop:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Monterrey_Institute_of_Technology_and_Higher_Education%2C_Mexico_City_Campus">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHAGC">
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Carmen_Romano">
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/C%C3%A9sar_Gonz%C3%A1lez">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Agust%C3%ADn_de_Iturbide">
    <dbpprop:reason rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Am%C3%A9rica_M%C3%B3vil">
    <dbpprop:foundation rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:foundationplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Juan_Jos%C3%A9_Carrillo">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEEW">
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Santa_Catarina%2C_Nuevo_Le%C3%B3n">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Omar_Bravo">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tropical_Storm_Charley_%281998%29">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Monterrey">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Porter_%28band%29">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHFA">
    <dbpedia-owl:format rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Freddy_Sandoval">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEPOP-AM">
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ni%C3%B1a_Amada_M%C3%ADa_%28telenovela%29">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/1960_Davis_Cup">
    <dbpprop:rd2t1Loc rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Marcos_Escobedo">
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Alfonso_Vera">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jamapa_River">
    <dbpprop:basinCountries rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tijuana_%28municipality%29">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Gerardo_Espinoza">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Oribe_Peralta">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Adrian_Olivares">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHCGO">
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Pedro_Mar%C3%ADa_de_Anaya">
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Copa_Pachuca">
    <dbpprop:region rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ram%C3%B3n_Arellano_F%C3%A9lix">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/1928_International_Lawn_Tennis_Challenge">
    <dbpprop:rd1t2Loc rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/H%C3%A9ctor_Altamirano">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Toyota_Tacoma">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHTPI-FM">
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mercedes-Benz_SL-Class">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHGES">
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Infarto">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Rodrigo_%C3%8D%C3%B1igo">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mocho%27_language">
    <dbpedia-owl:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Rambler_American">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Casa_de_la_Cacica">
    <dbpprop:locationCountry rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Romualdo_Pacheco">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Alejandro_Vela">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ricky_Marvin">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Radio_Disney">
    <dbpprop:available rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jenaro_S%C3%A1nchez_Delgadillo">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Manuel_%C3%81lvarez_Bravo">
    <dbpprop:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Gerardo_Flores_Z%C3%BA%C3%B1iga">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ameripol">
    <dbpprop:col rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Accutone">
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jorge_Ramos">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Volkswagen_Golf_Mk3">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mexican%E2%80%93American_War">
    <dbpprop:combatant rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Cuauht%C3%A9moc%2C_D.F.">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Grupo_Mayan">
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/1858_San_Diego_hurricane">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Aero_California">
    <dbpprop:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Francisco_Bol%C3%ADvar_Zapata">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Texas_State_Highway_4">
    <dbpprop:from rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Luis_Garc%C3%ADa_Postigo">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/H%C3%A9ctor_Moreno_%28footballer%29">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Minatitl%C3%A1n%2C_Colima">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/2008_Davis_Cup_Americas_Zone">
    <dbpprop:_2rpt1Loc rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Gustavo_Napoles">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mission_San_Pedro_y_San_Pablo_del_Tubutama">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Alicia_Rio">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Salom%C3%A9_%28telenovela%29">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/M%C3%B3nica_Noguera">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XESRD-AM">
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Gruma">
    <dbpprop:foundation rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:foundationplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEEZ">
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jos%C3%A9_Retiz">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Bernardo_Tule">
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Norman_O._Brown">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHCJH-TV">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/L%C3%A1zaro_C%C3%A1rdenas">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Area_code_808">
    <dbpprop:e rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/The_CW_Television_Network">
    <dbpprop:available rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Miguel_de_Icaza">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Misantla_Totonac">
    <dbpedia-owl:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Eduardo_Santamarina">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Edgar_Due%C3%B1as">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Fox_language">
    <dbpprop:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:region rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/M%C3%A9rida_FC">
    <dbpprop:ground rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XES">
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Battle_of_Camar%C3%B3n">
    <dbpedia-owl:place rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/WPLO">
    <dbpedia-owl:format rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/La_Bataille_de_San_Sebastian">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Paco_Stanley">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Carlos_Gerardo_Rodr%C3%ADguez">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Agust%C3%ADn_V%C3%A1squez_Mendoza">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Enrique_Llanes">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Luis_Donaldo_Colosio">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Cosme_Rivera">
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Afro-Mexican">
    <dbpprop:popplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:related rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Benito_Ju%C3%A1rez%2C_D.F.">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Rina_%28TV_series%29/telenovela1">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Salma_Hayek">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Cerro_de_las_Mitras">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Reik">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Genesee_and_Wyoming_Inc">
    <dbpprop:areaServed rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:areaServed rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jumbo_%28band%29">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mexican_Republic">
    <dbpprop:redirect rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XESE-AM">
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:format rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Proyecto_AA">
    <dbpedia-owl:recordplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Estadio_Jalisco">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Rosemary_Barkett">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/The_United_State_of_Mexico">
    <dbpprop:redirect rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/B._R._Cohn_Winery">
    <dbpprop:distribution rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Messico">
    <dbpprop:redirect rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Dolores_del_R%C3%ADo">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ernesto_Enkerlin">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:residence rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:residence rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Rogelio_Ch%C3%A1vez">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Adriana_Barraza">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Fall_of_Tenochtitlan">
    <dbpprop:place rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:place rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/F.C._Atlas_A.C.">
    <dbpprop:ground rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tropical_Storm_Marco_%282008%29">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Edgar_Lugo">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tequixquiac">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Torre_Altus">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Christian_Berm%C3%BAdez">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mocorito%2C_Sinaloa">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Humberto_Soto">
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:home rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Chevrolet_Silverado">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/TecMilenio_University">
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Xico%2C_Mexico_State">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Soriana">
    <dbpedia-owl:foundationplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Solar_eclipse_of_April_8%2C_2024">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Divisi%C3%B3n_Min%C3%BAscula">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Renault_18">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Senya_Fleshin">
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Guillermo_del_Toro">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Galeana%2C_Nuevo_Le%C3%B3n">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XERRT">
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Papaloapan_River">
    <dbpprop:basinCountries rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Graham_G._Alexander">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/FX-05_Xiuhcoatl">
    <dbpprop:usedBy rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Luis_Ayala">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jos%C3%A9_Luis_Castillo">
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:home rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Opata_language">
    <dbpedia-owl:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Braulio_God%C3%ADnez">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Arturo_Albarran_Orellana">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Aar%C3%B3n_Padilla">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Autonomous_University_of_Aguascalientes">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Amigas_y_rivales">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mariano_Matamoros">
    <dbpedia-owl:allegiance rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:allegiance rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Rojo_amanecer">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ignacio_Calder%C3%B3n">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Clase_406">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Polymarchs">
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tormenta_en_el_para%C3%ADso">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Carlos_Slim_Hel%C3%BA">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Myriam_Montemayor_Cruz">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/M%C3%A9jico">
    <dbpprop:redirect rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Chocho_language">
    <dbpprop:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Battle_of_La_Mesa">
    <dbpprop:combatant rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Aerom%C3%A9xico_Connect">
    <dbpprop:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Play_%28Mexican_band%29">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jes%C3%BAs_Ram%C3%ADrez">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEMO">
    <dbpprop:callsignMeaning rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tropical_Storm_Emilia_%282006%29">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Javier_Torres_Maldonado">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Aerom%C3%A9xico">
    <dbpedia-owl:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Plaza_Fiesta_San_Agust%C3%ADn">
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Beto_Mendoza_y_los_Pajaros_Negros">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Avioquintana">
    <dbpedia-owl:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Meixcan">
    <dbpprop:redirect rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mirada_de_Mujer:_El_Regreso_%28telenovela%29">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Santa_Sangre">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHJT">
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tlalnepantla_de_Baz">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Plymouth_Sundance">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/1986_Pacific_hurricane_season">
    <dbpprop:where rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Cora_language">
    <dbpedia-owl:region rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:region rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Huave_language">
    <dbpprop:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mauricio_Aspe">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/El_Inmigrante">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tropical_Storm_Chantal_%282001%29">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Honda_CR-V">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jesse_%26_Joy">
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/University_of_Colima">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jonathan_Arias">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/United_States_of_Mexicans">
    <dbpprop:redirect rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHOE-FM">
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Andy_DeMize">
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Thal%C3%ADa">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/El_Encuentro">
    <dbpedia-owl:recordplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Hurricane_Henriette_%282007%29">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Omar_Avil%C3%A1n">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Leonardo_Carrera">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Volkswagen_Beetle">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Nahuatl_dialects">
    <dbpprop:region rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:region rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/The_Mangy_Parrot">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/No%C3%A9_Maya">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Rayo_de_Jalisco%2C_Sr.">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Siempre_tuya">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mar%C3%ADa_In%C3%A9s">
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ram%C3%B3n_Vald%C3%A9s">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Zapata:_El_sue%C3%B1o_de_un_h%C3%A9roe">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Joaqu%C3%ADn_Amaro">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Gerardo_Galindo">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/ESCE_Ecole_Sup%C3%A9rieure_de_Commerce_Ext%C3%A9rieur">
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Vinny_Castilla">
    <dbpprop:dateOfBirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ram%C3%B3n_Morales">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Cristina_Eustace">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Marion_G._Romney">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Carlos_M%C3%A9ndez_Villalobos">
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Aar%C3%B3n_D%C3%ADaz">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Villa_Guerrero%2C_Mexico_State">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/International_Airport_of_the_Sea_of_Cortes">
    <dbpprop:redirect rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Wuthering_Heights_%281954_film%29">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Lanix">
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Dav%C3%ADd_Garza_P%C3%A9rez">
    <dbpprop:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Hurricane_Alma_%281996%29">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/El_Reloj_Cuc%C3%BA">
    <dbpedia-owl:recordplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/The_United_States_of_Mexico">
    <dbpprop:redirect rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/2006_Tecate_Grand_Prix_of_Monterrey">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/1933_Atlantic_hurricane_season">
    <dbpprop:where rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Abraham_Laboriel">
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mehico">
    <dbpprop:redirect rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Alliant_International_University">
    <dbpprop:free rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Steve_Clemente">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Bibelot_Mansur">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jos%C3%A9_Rosas_Moreno">
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Silvia_Navarro">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Marco_Fabi%C3%A1n">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Espolon">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/David_Toledo">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Fernando_L%C3%B3pez_%28footballer%29">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Alvin_Mendoza">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Daewoo_Kalos">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/The_Orphanage_%282007_film%29">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Christopher_Uckermann">
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jared_Borgetti">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Patricia_Manterola">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XETAR-AM">
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Felipe_Colombo">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Carlos_Monsiv%C3%A1is">
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:citizenship rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:citizenship rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Zaide_Silvia_Guti%C3%A9rrez">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Kabah_%28band%29">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Cantarell_Field">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mateo_Correa_Magallanes">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ivan_Castillejos">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Manuel_de_la_Pe%C3%B1a_y_Pe%C3%B1a">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Francisco_%C3%81ngel_Santos">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:residence rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:residence rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/1933_International_Lawn_Tennis_Challenge">
    <dbpprop:rd1t1Loc rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Republic_of_Mexico">
    <dbpprop:redirect rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Kirsty_MacColl">
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/The_Death_of_Artemio_Cruz">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Plymouth_Acclaim">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEA-AM">
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:format rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jorge_Barrera">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Neal_Cassady">
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jos%C3%A9_Mar%C3%ADa_Robles_Hurtado">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Juan_Rulfo">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Grettell_Vald%C3%A9z">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jos%C3%A9_Joel_Gonz%C3%A1lez">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Estadio_de_Beisbol_Calimax">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/2010_Universal_Forum_of_Cultures">
    <dbpprop:last rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Chupacabra">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Robert_Cardenas">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Universidad_de_Monterrey">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Humberto_Ramos">
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mesa_del_Cobre">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Area_code_619">
    <dbpprop:s rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mexican_Restaurants%2C_Inc.">
    <dbpedia-owl:services rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Carlos_Salinas_de_Gortari">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Alegrijes_y_Rebujos">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Bodega_Aurrer%C3%A1">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Alicja_Bachleda">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Sierra_Negra">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Rub%C3%A9n_Aguirre">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mario_Van_Peebles">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/El_Gran_Silencio">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Huitzuco">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Gustavo_D%C3%ADaz_Ordaz">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Luis_%C3%81ngel_Land%C3%ADn">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Santa_Gertrudis_%28municipality%29">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Daniel_Ponce_de_Le%C3%B3n">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tepehua_language">
    <dbpprop:region rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:region rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Steve_McQueen">
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Manuel_Arvizu">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Alfonso_Cuar%C3%B3n">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Agust%C3%ADn_de_Iturbide_y_Green">
    <dbpprop:throne rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/%C3%89dgar_Adolfo_Hern%C3%A1ndez">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Hugo_S%C3%A1nchez">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Felipe_Calder%C3%B3n">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Dodge_Aspen">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/V%C3%ADctor_Hugo_Hern%C3%A1ndez">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Gerardo_Taracena">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHJK-TV">
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mar%C3%ADa_F%C3%A9lix">
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEHD-AM">
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Antonio_de_Nigris">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XECARH-AM">
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Camila_%28band%29">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHVIR">
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Club_Le%C3%B3n">
    <dbpprop:ground rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Nahua_peoples">
    <dbpprop:popplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Telemax_%28television_network%29">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:locationCountry rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jorge_Goeters">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Shrine_Auditorium/succession_box2">
    <dbpprop:after rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Dodge_Ram">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/1959_Davis_Cup">
    <dbpprop:rd1t2Loc rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Buick_Rendezvous">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Picnic_%28TV_show%29">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Marcela_Bovio">
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Andr%C3%A9s_Guardado">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jimena_%28singer%29">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Las_Limas_Monument_1">
    <dbpprop:discovered rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Yucatec_Maya_language">
    <dbpedia-owl:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Autonomous_University_of_Tamaulipas">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Southern_Mexico">
    <dbpprop:redirect rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Grupo_Aeroportuario_del_Pacifico">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Abelardo_L._Rodr%C3%ADguez">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Eliana_Alexander">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Chrysler_Sebring_%28convertible%29">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Guty_Espadas">
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Chevrolet_Cavalier">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:successor rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Grupo_Financiero_Banamex">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Quer%C3%A9taro%2C_Quer%C3%A9taro">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Gloria_Trevi">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:born rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Otomi_people">
    <dbpprop:popplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHMAO">
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ivan_Becerra">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Michele_Cantu">
    <dbpprop:residence rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:residence rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Nahuatl">
    <dbpedia-owl:region rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:region rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Vuelvo_A_Ti">
    <dbpedia-owl:recordplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/M%C3%ADstico">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Los_Mochis%2C_Sinaloa">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Multimedios_Television">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:locationCountry rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Cofre_de_Perote">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Milenio_Diario">
    <dbpedia-owl:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XETO">
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Hurricane_Lester_%281998%29">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Huichol">
    <dbpprop:popplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jhonny_Gonz%C3%A1lez/succession_box3">
    <dbpprop:title rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Antonio_Sancho">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Enrique_Camarena">
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Paul_Nicol%C3%A1s_Aguilar">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ambrose_Bierce">
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Chichimeca_Jonaz">
    <dbpprop:popplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Apostolic_Assembly_of_the_Faith_in_Christ_Jesus">
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Paulina_Rubio">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHDI">
    <dbpedia-owl:format rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mexico_City_Furia">
    <dbpprop:franchise rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Iv%C3%A1n_V%C3%A1zquez">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHUAD-FM">
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/2007_Universal_Forum_of_Cultures">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Juan_Garc%C3%ADa_Esquivel">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:born rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Foro_Sol">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Puebla_Cathedral">
    <dbpprop:locationCountry rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Instituto_Cultural_Tampico">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/GMC_Caballero">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mexican">
    <dbpprop:redirect rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Lawrence_Teeter">
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Roberto_G%C3%B3mez_Bola%C3%B1os">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Juan_P%C3%A9rez_%27Kichi%27">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Antonio_Caso_Andrade">
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHTA-FM">
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/PC_Magazine">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mexico_City">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHAVO">
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tampico_Affair">
    <dbpprop:place rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:place rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:combatant rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jair_Garcia">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Gerardo_Ruiz_Mateos">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Quiero_Club">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Chichimeca_Jonaz_language">
    <dbpprop:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Fernando_Espinoza">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Western_Airlines_Flight_2605">
    <dbpprop:site rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Rey_Mysterio%2C_Jr.">
    <dbpprop:billed rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XENKA-AM">
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Hurricane_Gilbert">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Coretta_Scott_King">
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Fernando_Garc%C3%ADa_Roel">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mindelight">
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Johnny_Weissmuller">
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Fernando_Olvera">
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Hurricane_Carlotta_%282000%29">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Israel_V%C3%A1zquez">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Aviacsa">
    <dbpprop:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ciudad_Ayala%2C_Morelos">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Eduardo_Lillingston">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Bacad%C3%A9huachi">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jonathan_Fraulin">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tampico_Bridge">
    <dbpprop:locale rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:locatedinarea rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tropical_Storm_Kirsten_%281966%29">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Amealco_de_Bonfil">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/InterContinental_Hotels_Group">
    <dbpprop:col rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/2007_U-20_World_Cup_CONCACAF_qualifying_tournament/footballbox6">
    <dbpprop:stadium rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Casas_Grandes%2C_Chihuahua">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Manuel_%C3%81vila_Camacho">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Autonomous_University_of_Chihuahua">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEMY">
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Johnny_Garc%C3%ADa">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Adela_Micha">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/P%C3%A9njamo">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/%C2%A1V%C3%A1monos_con_Pancho_Villa%21">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Deportivo_Toluca_F.C.">
    <dbpprop:ground rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Grass_Fight">
    <dbpprop:combatant rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Antonio_Margarito">
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:home rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Centro_Comercial_El_Paseo_Tehuacan">
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHUZ">
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Julio_Dom%C3%ADnguez">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tom%C3%A1s_Campos">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Edgar_Mej%C3%ADa">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Laura_Esquivel">
    <dbpprop:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Susana_L%C3%B3pez_Charreton">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:residence rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:residence rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Pastry_War">
    <dbpedia-owl:place rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:place rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:combatant rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEPET-AM">
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Venustiano_Carranza">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mariana_Ochoa">
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHDF-TV">
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/%C3%81lvaro_Obreg%C3%B3n%2C_D.F.">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XELTZ">
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Fantomas_contra_los_vampiros_multinacionales">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Gabriella_Hall">
    <dbpprop:ethnicity rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:ethnicity rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Battle_of_the_Alamo">
    <dbpprop:combatant rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jorge_Torres">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/TAESA">
    <dbpedia-owl:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tecamachalco">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Teddy_Stauffer">
    <dbpprop:died rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jos%C3%A9_Villagr%C3%A1n_Garc%C3%ADa">
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Porfirio_D%C3%ADaz">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jacobo_Arbenz_Guzm%C3%A1n">
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEHZ">
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/CBS">
    <dbpprop:available rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Clemente_Ovalle">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Cerralvo">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Rio_Grande">
    <dbpprop:basinCountries rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Joaqu%C3%ADn_Cos%C3%ADo">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Fernando_Platas">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Andr%C3%A9s_Roemer">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/House_of_Iturbide">
    <dbpprop:reason rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/The_Chasm">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mexicanos">
    <dbpprop:redirect rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Edgar_Gonz%C3%A1lez_%28architect%29">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/TRF1">
    <dbpprop:usedBy rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Vedanta_capital_group">
    <dbpprop:redirect rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Battle_of_Goliad">
    <dbpprop:combatant rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ricardo_Jim%C3%A9nez_de_Alba">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Max_Zendejas">
    <dbpprop:dateOfBirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ismael_Zambada_Garc%C3%ADa">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Plymouth_Savoy">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/The_American_School_Foundation">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Carlos_Gallardo">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHHF">
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Oscar_Gonz%C3%A1lez_Loyo">
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mexic%C3%B3">
    <dbpprop:redirect rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Miguel_Alem%C3%A1n_Vald%C3%A9s">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/SKY_Latin_America">
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/John_Candy">
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Latin_American_Integration_Association">
    <dbpprop:membership rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Hechicera">
    <dbpprop:recorded rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:recordplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/WiLDCOAST">
    <dbpprop:areaServed rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ricardo_Montalb%C3%A1n">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEHI">
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Pedro_Rodr%C3%ADguez_%28racing_driver%29">
    <dbpprop:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Pacific_Ring_of_Fire">
    <dbpprop:list rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Area_code_345">
    <dbpprop:w rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Softtek">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEMT">
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Battle_for_Mexico_City">
    <dbpprop:combatant rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/William_B._Ide">
    <dbpprop:predecessor rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Sayula_Popoluca">
    <dbpedia-owl:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/2006_FIFA_World_Cup_qualification_-_CONCACAF_Preliminary_Round/footballbox34">
    <dbpprop:stadium rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/1968_Davis_Cup">
    <dbpprop:rd1t2Loc rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Cuando_los_%C3%81ngeles_Lloran">
    <dbpedia-owl:recordplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jorge_Oropeza">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ignacio_Carrasco">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Volkswagen_Type_2">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mayan_Palace">
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Lupita_Jones">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Pajapan_Nahuatl">
    <dbpedia-owl:region rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:region rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/%C3%81nimas_Trujano_%28film%29">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Katty_Fuentes">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Gabriel_Soto">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Donald_Reed">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mephisto_%28wrestler%29">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Telehit_News">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEO">
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Battle_of_Palo_Alto">
    <dbpprop:combatant rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/International_reaction_to_the_2008_declaration_of_independence_by_Kosovo">
    <dbpprop:showProperty rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Xavier_Mart%C3%ADnez">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Banregio">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHPAL">
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/The_Uncomfortable_Dead">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEE-AM">
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:format rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEART-AM">
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tecnol%C3%B3gico_de_Monterrey%2C_Campus_Quer%C3%A9taro">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Carlos_Rivera">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Haggar_Clothing">
    <dbpprop:areaServed rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:areaServed rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHGA-TV">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Alejandro_Castillo">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Cavernario_Galindo">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Laura_Zapata">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Pasi%C3%B3n_%28telenovela%29">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/2007_U-20_World_Cup_CONCACAF_qualifying_tournament/footballbox1">
    <dbpprop:stadium rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Laura_Harring">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Central_America_Hurricane_of_1941">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Battle_of_Chapultepec">
    <dbpprop:combatant rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/2007-08_A1_Grand_Prix_of_Nations%2C_Mexico">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Alfredo_Tena">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Bachaj%C3%B3n_Tzeltal">
    <dbpedia-owl:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Usumacinta_River">
    <dbpprop:basinCountries rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mario_M%C3%A9ndez">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mexicanero">
    <dbpprop:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Hurricane_Rosa_%281994%29">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Area_code_432">
    <dbpprop:w rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:s rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Joseph_MacDonald">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Santa_Claus_%28film%29">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mexican_settlement_in_the_Philippines">
    <dbpprop:regions rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Alfonso_de_Nigris">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Fabi%C3%A1n_Pe%C3%B1a">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ruth_Zavaleta">
    <dbpprop:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Reyli">
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Emilio_Hassan">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Alejandro_Springall">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Osmar_Mares">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Battle_of_Resaca_de_la_Palma">
    <dbpprop:combatant rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Rio_Group">
    <dbpprop:col rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Siege_of_Veracruz">
    <dbpprop:combatant rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Guau%21">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Andr%C3%A9s_Eloy_Blanco">
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ger%C3%B3nimo_Gil">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Walker_%28film%29">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ignacio_Allende">
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Carlos_M%C3%A9rida">
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Alejandro_Fern%C3%A1ndez">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEV">
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tubar_language">
    <dbpedia-owl:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Agust%C3%ADn_Carstens">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Chapingo_Autonomous_University">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/The_Devil%27s_Backbone">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/%C3%93scar_Matur%C3%ADn">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Juan_Acevedo">
    <dbpprop:dateOfBirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Cactus">
    <dbpprop:imageCaption rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Eugenio_Siller">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Miriam_%28entertainer%29">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Brehon_B._Somervell">
    <dbpprop:battles rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:battles rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Pontiac_Sunbird">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Estadio_La_Corregidora">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/V%C3%ADctor_Gonz%C3%A1lez_%28actor%29">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Junior_Zarate">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Yuri_%28Mexican_singer%29">
    <dbpprop:residence rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Chevrolet_Suburban">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Chikita_Violenta">
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XERKS">
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Area_code_928">
    <dbpprop:s rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Linda_Christian">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ismael_de_Jes%C3%BAs_Rodr%C3%ADguez">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/2008_Pacific_hurricane_season">
    <dbpprop:where rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Battle_of_Monterrey">
    <dbpprop:combatant rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Dulce_Beat_Live">
    <dbpprop:recorded rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:recordplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Fernando_Salazar">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/En_El_Muelle_De_San_Bl%C3%A1s">
    <dbpprop:recorded rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:recordplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Adriana_Sage">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHXZ-FM">
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Diego_Alberto_Cervantes">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Area_code_520">
    <dbpprop:s rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Central_Mexico">
    <dbpprop:redirect rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Hurricane_Fern_%281971%29">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Carlos_Padilla_Maqueo">
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/1970_Davis_Cup">
    <dbpprop:rd1t1Loc rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/October_1999_Mexico_floods">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/2006_Gran_Premio_Telmex">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jos%C3%A9_Jonathan_Pi%C3%B1a_Guti%C3%A9rrez">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Liverpool_%28store%29">
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Samuel_Castel%C3%A1n_Marini">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/P%27urh%C3%A9pecha_language">
    <dbpedia-owl:region rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:region rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mondrag%C3%B3n_%28rifle%29">
    <dbpprop:usedBy rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:placeoforigin rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Amduscia">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Alberca_Ol%C3%ADmpica_Francisco_M%C3%A1rquez">
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Sierra_Popoluca">
    <dbpedia-owl:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Salvador_Carrasco">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Luis_Antonio_Vald%C3%A9z">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Eddy_Vilard">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Antonio_L%C3%B3pez_de_Santa_Anna">
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Area_code_830">
    <dbpprop:w rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Luis_Cruz">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Alejandra_Barros">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHNX-FM">
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Perro_Aguayo%2C_Jr.">
    <dbpprop:billed rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Salvatierra%2C_Guanajuato">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mayra_Suarez">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tropical_Storm_Barbara_%282007%29">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Coyoac%C3%A1n">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Marcial_Maciel">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Universidad_Tecnol%C3%B3gica_de_M%C3%A9xico">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/David_Villalpando">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHUAA-TV">
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/PopMart:_Live_from_Mexico_City">
    <dbpedia-owl:recordplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:recorded rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mejico">
    <dbpprop:redirect rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Battle_of_Santa_Cruz_de_Rosales">
    <dbpprop:combatant rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Carlos_Infante">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/P%C3%A1vel_Pardo">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Oro_%28wrestler%29">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jorge_Villalpando">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XECUA-AM">
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:format rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHUNES-FM">
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:format rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Druidas">
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Andr%C3%A9s_Aguirre">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Juan_Aldama">
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ignacio_Ambr%C3%ADz">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Gene_Gauntier">
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Durango_Institute_of_Technology">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Natalia_Lafourcade">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Autonomous_University_of_Baja_California_Sur">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Arath_de_la_Torre">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Universidad_Aut%C3%B3noma_Metropolitana">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/RBD">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Hurricane_Greg_%281999%29">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Sergio_Santana">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Opium">
    <dbpprop:producers rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Alejandro_Tommasi">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Octavio_Paz">
    <dbpprop:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jorge_Torres_Nilo">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Hocico">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/%C3%81ngel_Sosa">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Bloquera">
    <dbpprop:recorded rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:recordplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Facundo_G%C3%B3mez">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Elizabeth_%C3%81lvarez">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Quer%C3%A9taro_International_Airport">
    <dbpprop:cityServed rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jes%C3%BAs_Amezcua_Contreras">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Reforma">
    <dbpedia-owl:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Nery_Castillo">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Hurricane_Elida_%282002%29">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Hugo_Casillas">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHSAF">
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ferromex">
    <dbpprop:locale rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mariano_Trujillo">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Loosedemar">
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Esmeralda_%28telenovela%29">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Isthmus-Mecayapan_Nahuatl">
    <dbpprop:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Miguel_Hidalgo_y_Costilla">
    <dbpprop:allegiance rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:allegiance rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Odin_Pati%C3%B1o">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/National_Action_Party_%28Mexico%29">
    <dbpprop:redirect rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Marisol_%28telenovela%29">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHNY-FM">
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Aeromexpress">
    <dbpprop:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Roberto_Jordan">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Valent%C3%ADn_Canalizo">
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Amores_perros">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Sonora_Institute_of_Technology">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Rusty_Eye">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tarzan_Boy_%28wrestler%29">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ces%C3%A1reo_Victorino">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XERL-AM">
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Anagabriela_Espinoza">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Thomas_L._Hamer">
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mansions_of_the_World">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XETW">
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Alejandro_Alonso_%28musician%29">
    <dbpprop:born rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Arturo_Soto_Rangel">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Villano_III">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Alfonso_Loera">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mil_M%C3%A1scaras">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEYP">
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Marisol_Gonz%C3%A1lez">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/2003_Tecate_Telmex_Monterrey_Grand_Prix">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XETAM">
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Leon_Trotsky">
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/List_of_storms_in_the_2002_Atlantic_hurricane_season">
    <dbpprop:where rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Hurricane_Carlotta_%282006%29">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Dignity_tour">
    <dbpprop:countries rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Aerol%C3%ADneas_Internacionales">
    <dbpprop:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Esdras_Rangel">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEZON-AM">
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/El_Norte_%28newspaper%29">
    <dbpedia-owl:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Pontifical_University_of_Mexico">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Axtel">
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Chevrolet_El_Camino">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Once_TV">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:locationCountry rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mercedes-Benz_CLC-Class">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Marcelino_Bernal">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Hurricane_Tico_%281983%29">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Daniel_Zaragoza">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Dorados_de_Sinaloa">
    <dbpprop:ground rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Rodolfo_Salinas">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/List_of_storms_in_the_2001_Atlantic_hurricane_season">
    <dbpprop:where rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Hurricane_Inez">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Night_of_the_Bloody_Apes_%28film%29">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Brujeria_%28band%29">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHRYA">
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Rodolfo_Cota">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Rolando_Cantu">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Kuno_Becker">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Instituto_Tecnol%C3%B3gico_de_Ensenada">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Roman_Catholic_Diocese_of_La_Paz_en_la_Baja_California_Sur">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Sabritas">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Battle_of_Buena_Vista">
    <dbpprop:combatant rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Luis_Farell">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Antara_Polanco">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/First_Battle_of_Tabasco">
    <dbpprop:combatant rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEVAB-AM">
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Omar_Arellano">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/David_Cabrera_Pujol">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Enrique_Esqueda">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Salinas_Victoria">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Moises_Solana">
    <dbpprop:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jos%C3%A9_Antonio_Olvera">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Clavado_En_Un_Bar">
    <dbpedia-owl:recordplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:recorded rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Meteorological_history_of_Hurricane_Dean">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Sabinas%2C_Coahuila">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Aguascalientes_%28municipality%29">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHMTE">
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XESJ">
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Un_Gancho_al_Corazon">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Carlos_Turrubiates">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Yo_amo_a_Juan_Querend%C3%B3n">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Hurricane_Lester_%281992%29">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Cerro_de_la_Loma_Larga">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Queen_Sirikit_National_Convention_Center">
    <dbpprop:after rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Christian_Ch%C3%A1vez">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Chihuahua_%28dog%29">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Dodge_Ram_SRT-10">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Francis_Garc%C3%ADa">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Conquest_%281983_film%29">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Zo%C3%A9">
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tamara_de_Lempicka">
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jose_De_Jesus_Rodriguez">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Hurricane_Guillermo_%281997%29">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Merle_Gulick">
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Zapotec_languages">
    <dbpedia-owl:region rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:region rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Lincoln_Zephyr/MKZ">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Efra%C3%ADn_Flores">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mayakoba_Classic_at_Riviera_Maya">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Germ%C3%A1n_Dur%C3%A1n">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Rose_Zwi">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Wakal">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Casa_Velas_Hotel">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Hurricane_Caroline_%281975%29">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Navajeros">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Romancing_the_Stone">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Telchac_Pueblo">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Hurricane_Bret_%281999%29">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Marcos_Villasana">
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/2011_Pan_American_Games">
    <dbpprop:hostCity rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Pico_de_Orizaba">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Hector_Garcia-Molina">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Alfonso_Ant%C3%BAnez">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Javier_Alatorre">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/CONCACAF_Champions%27_Cup_2008/footballbox3">
    <dbpprop:stadium rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Miguel_Sabah">
    <dbpprop:cityofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/AMC_Matador">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tropical_Storm_Fay_%282002%29">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/1926_International_Lawn_Tennis_Challenge">
    <dbpprop:rd1t1Loc rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Grupo_Tampico">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Coatl%C3%A1n_Zapotec_language">
    <dbpedia-owl:region rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:region rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Pontiac_Sunfire">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/No_Ha_Parado_De_Llover">
    <dbpedia-owl:recordplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Hiromi_Hayakawa">
    <dbpprop:residence rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Laura_Flores">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/2004_Tecate/Telmex_Grand_Prix_of_Monterrey">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Aventuras_En_El_Tiempo_%28album%29">
    <dbpedia-owl:recordplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tijuana">
    <dbpprop:mapCaption rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/%C3%93scar_Rojas">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Comisi%C3%B3n_Federal_de_Electricidad">
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mapim%C3%AD_Silent_Zone">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Cynthia_Deyanira_Rodr%C3%ADguez_Ruiz">
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Saint_Patrick%27s_Battalion">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Battle_of_San_Patricio">
    <dbpprop:combatant rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEDU-AM">
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jonny_Magall%C3%B3n">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Edgar_Andrade">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Marimar_%28Mexican_TV_series%29">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Televisa">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:locationCountry rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Diego_Jim%C3%A9nez">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Freedom_from_Hunger">
    <dbpprop:areaServed rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:areaServed rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Trances/Drones">
    <dbpedia-owl:recordplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:recorded rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mercedes-Benz_CLK-Class">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jos%C3%A9_Dar%C3%ADo_Arg%C3%BCello">
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ricardo_Rodr%C3%ADguez_%28racing_driver%29">
    <dbpprop:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEBW">
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:format rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Geronimo">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Sergio_Rodr%C3%ADguez_%28footballer%29">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Vladimir_Garza">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Los_Monchis">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/The_Grand_Mayan">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Battle_of_Monterey">
    <dbpprop:combatant rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Santiago_Jamiltepec">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Party_of_the_Democratic_Revolution">
    <dbpprop:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Area_code_760">
    <dbpprop:s rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEOR">
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Giovani_Dos_Santos">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Dos_Caras">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/B-300">
    <dbpprop:usedBy rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Guillermo_Ochoa">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Marco_G%C3%B3mez">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Juan_%C3%81lvarez">
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Hugo_C%C3%A1zares">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/EMD_GP18">
    <dbpprop:locale rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Carlos_Adri%C3%A1n_Morales">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tropical_Storm_Jose_%282005%29">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/High_School_Musical:_El_desafio">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Plutarco_El%C3%ADas_Calles">
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tito_Larriva">
    <dbpprop:born rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Hermosillo">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tojolab%27al_language">
    <dbpedia-owl:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Nha_Trang">
    <dbpprop:before rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Opel_Corsa">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Battle_of_Cerro_Gordo">
    <dbpprop:combatant rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Banorte">
    <dbpedia-owl:type rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:companyType rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Columba_Bush">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Carlos_Arias_Ortiz">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:residence rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:residence rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Dulce_Mar%C3%ADa">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XERAC-AM">
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:format rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Marty_Feldman">
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Hurricane_Pauline_%281968%29">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEFW-AM">
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Emmet_Crawford">
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ensenada_%28municipality%29">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/T%C3%BA_y_Yo_%28album%29">
    <dbpprop:recorded rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:recordplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Horacio_Sandoval">
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Transmetal_%28band%29">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Daniel_Osorno">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Cuauht%C3%A9moc_Blanco">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Carol_Wayne">
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Nova_Air">
    <dbpprop:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Telmex">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Torre_Fundadores">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Como_Dueles_En_Los_Labios">
    <dbpprop:recorded rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:recordplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Allende_meteorite">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Gilberto_Hernandez_Guerrero">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Cartel_de_Santa">
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Pedro_Hern%C3%A1ndez">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/MasAir">
    <dbpprop:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Chevrolet_Celebrity">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jos%C3%A9_Antonio_Castro">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jos%C3%A9_Luis_Calva">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Alfredo_Aceves">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Gilberto_%C3%89rick_Mart%C3%ADnez">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Juan_Pablo_Alfaro">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/AMC_Spirit">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Malva_Flores">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Eastern_Huasteca_Nahuatl">
    <dbpprop:region rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:region rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Economy_of_Argentina">
    <dbpprop:importPartners rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Zapatista_Army_of_National_Liberation">
    <dbpedia-owl:place rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:place rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Aerom%C3%A9xico_Flight_498">
    <dbpprop:plane1Origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jos%C3%A9_Cuevas">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tropical_Storm_Larry_%282003%29">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XETEB-AM">
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:format rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Leticia_Murray">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Alberto_Rodr%C3%ADguez_%28Mexican_footballer%29">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Vicente_Fox">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/AMC_Gremlin">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Chatino_language">
    <dbpedia-owl:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/L%C3%ADneas_A%C3%A9reas_Azteca">
    <dbpedia-owl:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Luis_Enrique_Robles">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/ISO_3166-1:MX">
    <dbpprop:redirect rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Fresnillo_plc">
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Alfonso_Blanco">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHEM">
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Autonomous_University_of_Sinaloa">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Dave_Rudabaugh">
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tecnol%C3%B3gico_de_Monterrey%2C_Campus_Tampico">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Taco_Bueno">
    <dbpedia-owl:products rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Alvaro_Pineda">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHZZZ-FM">
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Pima_Bajo">
    <dbpprop:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tetelcingo_Nahuatl">
    <dbpedia-owl:region rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:region rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Apolo_Dant%C3%A9s">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Luis_Echeverr%C3%ADa">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XETNC-AM">
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mexique">
    <dbpprop:redirect rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Arcadio_Poveda">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tito_Gu%C3%ADzar">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Oswaldo_S%C3%A1nchez">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Kansas_City_Southern_de_M%C3%A9xico">
    <dbpprop:locale rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Sigma_Alimentos">
    <dbpprop:locationCountry rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:locationcountry rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/J._C._Quinn">
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jos%C3%A9_Guadalupe_Mart%C3%ADnez">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Apodaca">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Daniel_Guzm%C3%A1n">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Alan_Zamora">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ju%C3%A1rez%2C_Nuevo_Le%C3%B3n">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/MyNetworkTV">
    <dbpprop:available rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tropical_Storm_Dalila_%282007%29">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Benjam%C3%ADn_Galindo">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Sebasti%C3%A1n_Ligarde">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XETLA-AM">
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tlapanec_people">
    <dbpprop:popplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tepotzotl%C3%A1n">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Cirilo_Saucedo">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Battle_of_Churubusco">
    <dbpprop:combatant rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Renault_4">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Luis_Montes">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/General_Juan_N._%C3%81lvarez_International_Airport">
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Marco_Antonio_Firebaugh">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Edgar_Sosa_%28boxer%29">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Silvia_Salgado">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Aleks_Syntek">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/NASCAR_Mexico">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Amor_sin_Maquillaje">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mixe-Zoque_languages">
    <dbpprop:region rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Carlos_Fuentes">
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/El_callej%C3%B3n_de_los_milagros">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:distributor rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:editing rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Diego_Morales">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Sergio_Amaury_Ponce">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Adolfo_Ruiz_Cortines_Dam">
    <dbpprop:locale rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Francisco_V%C3%A1zquez_G%C3%B3mez">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Adri%C3%A1n_Varela">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Dodge_Super_Bee">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Adri%C3%A1n_Cort%C3%A9s_%28footballer%29">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Sue%C3%B1os_L%C3%ADquidos">
    <dbpprop:recorded rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:recordplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Palabra_de_Mujer">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mixe">
    <dbpprop:popplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Vicente_Fern%C3%A1ndez">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Rodolfo_Gato_Gonz%C3%A1lez">
    <dbpprop:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Grupo_CIE">
    <dbpprop:foundation rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:foundationplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Rafael_M%C3%A1rquez_Lugo">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Pedilanthus">
    <dbpprop:redirect rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Fernando_Montiel">
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XERDO">
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Alex_S%C3%A1nchez_%28author%29">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jes%C3%BAs_Ch%C3%A1vez">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Omar_Chaparro">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Alexander_Kirkland">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHMAE">
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Angel_Ochoa">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/San_Isidro_Buensuceso">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Antonio_Carbajal">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Aeropostal_Cargo_de_M%C3%A9xico">
    <dbpedia-owl:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHNS">
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Julio_C%C3%A9sar_Ch%C3%A1vez%2C_Jr.">
    <dbpprop:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:home rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Yahel_Castillo">
    <dbpprop:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Agust%C3%ADn_Caloca_Cort%C3%A9s">
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Cerro_de_la_Silla">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Adolfo_L%C3%B3pez_Mateos">
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Yovani_Gallardo">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Fernando_Arce">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Xonotla">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Cynthia_Klitbo">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/William_Paredes">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Carlos_Loret_de_Mola">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/C%C3%A1rdenas%2C_San_Luis_Potos%C3%AD">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ir%C3%A1n_Eory">
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/1934_Central_America_hurricane">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEBCS-AM">
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jos%C3%A9_Manuel_Abundis">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Alfonso_Herrera">
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tenabo_%28municipality%29">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Armando_Navarrete">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Battle_of_Refugio">
    <dbpprop:combatant rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mexichaun">
    <dbpprop:redirect rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Auditorio_Coca-Cola">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Luis_Bu%C3%B1uel">
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Salvador_Dur%C3%A1n">
    <dbpprop:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Corregidora%2C_Quer%C3%A9taro">
    <dbpprop:officialName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ninel_Conde">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Fernando_Colunga">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Alianza_Unetefan_AFC">
    <dbpprop:ground rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Autonomous_University_of_Zacatecas">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Hector_Guerrero">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Yagul">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Luis_Ernesto_Michel">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Frida_Kahlo">
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mercedes-Benz_Sprinter">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Melvin_Brown">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/M47_Dragon">
    <dbpprop:usedBy rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tampa_Bay_Rays_all-time_roster/player64">
    <dbpprop:playerProperty rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/1969_Davis_Cup">
    <dbpprop:rd2t1Loc rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/New_River_%28Mexico-United_States%29">
    <dbpprop:basinCountries rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/TeleHit">
    <dbpedia-owl:locationCountry rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHUNO">
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/%C3%89dgar_Gonz%C3%A1lez_%28pitcher%29">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Vanessa_Guzm%C3%A1n">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Claudia_Lizaldi">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Los_Kung-Fu_Monkeys">
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Gustavo_A._Madero%2C_D.F.">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Las_Tontas_No_Van_al_Cielo">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Chrysler_PT_Cruiser">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Eduardo_Capetillo">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Municipality_of_Huautla_de_Jim%C3%A9nez">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Gloomy_Lights">
    <dbpedia-owl:label rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Canto_General">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Alberto_Medina">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Pascual_Ortiz_Rubio">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Hurricane_Sergio_%282006%29">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Germ%C3%A1n_Villa">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Estadio_Maya">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XERMX-OC">
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Howard_Hughes">
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Pita_Amor">
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Akwid">
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Cristian_Mijares">
    <dbpprop:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Adam_Parada">
    <dbpprop:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Elsa_Aguirre">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Antonio_Aguilar_Barraza">
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/V%C3%ADctor_Trujillo">
    <dbpprop:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Lizmark%2C_Jr.">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Pontiac_G6">
    <dbpedia-owl:predecessor rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Miguel_Ostersen">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/So%2C_What%27s_your_price%3F">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEMS">
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Pante%C3%B3n_Rococ%C3%B3">
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/O%27odham_language">
    <dbpprop:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Teresa_Ruiz_%28actress%29">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tiburones_Rojos_de_Coatzacoalcos">
    <dbpprop:ground rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Renault_Dauphine">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Carmen_Scarpitta">
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHTNO-FM">
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Alfonso_Bedoya">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHRAW">
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHRPU-FM">
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHJUB-TV">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Adalberto_Palma">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Babel_%28film%29">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mexican_pop_music">
    <dbpprop:culturalOrigins rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Sears_Holdings_Corporation">
    <dbpedia-owl:areaServed rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:areaServed rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Guyana:_Crime_of_the_Century">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Laura_Le%C3%B3n">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Kathy_Acker">
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Esther_Fern%C3%A1ndez">
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/San_Luis_Potos%C3%AD%2C_San_Luis_Potos%C3%AD">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Super_Crazy">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:billed rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mexican_citizen">
    <dbpprop:redirect rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jorge_Campillo">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Miguel_Becerra">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XECK-AM">
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:format rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jos%C3%A9_%C3%81ngel_Llamas">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Albatross_Golf_Courses">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mexican_Institute_of_Petroleum">
    <dbpprop:headquarters rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mayan_Island">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Colegio_Arji">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mart%C3%ADn_Castillo">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Autonomous_University_of_Puebla">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jorge_Rodr%C3%ADguez_%28footballer%29">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Florinda_Meza">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Chango_Carmona">
    <dbpprop:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Zumpango%2C_Mexico_State">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Siege_of_Fort_Texas">
    <dbpprop:combatant rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Rufino_Tamayo">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Margarita_Zavala">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jes%C3%BAs_Silva_Herzog">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Nissan_Tiida">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/El_Mongol">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Battle_of_Lipantitl%C3%A1n">
    <dbpprop:combatant rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jaime_Dur%C3%A1n">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Hurricane_Naomi_%281968%29">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEAM">
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Atl%C3%A9tico_Mexiquense">
    <dbpprop:ground rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Cerro_del_Topo_Chico">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/David_Hackworth">
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ixcateopan_de_Cuauht%C3%A9moc_%28municipality%29">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHSOT">
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Alicia_Villarreal">
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Teoloyucan%2C_Mexico_State">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Silvia_Pasquel">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Battle_of_Agua_Dulce">
    <dbpprop:combatant rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Temporada_de_patos">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/EMD_SW1000">
    <dbpprop:locale rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Vaniity">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Karla_Carrillo">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Susana_Romero">
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Xalbal_River">
    <dbpprop:basinCountries rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XETOT">
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Spanish_Mexican">
    <dbpprop:popplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ana_Mar%C3%ADa_L%C3%B3pez_Colom%C3%A9">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Honda_Accord">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Rodolfo_Escalera">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Cemex">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:foundationplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:foundation rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mercedes-Benz_M-Class">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Sasha_Sokol">
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEHP">
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Joaqu%C3%ADn_Cordero">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHON">
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Chicos_de_Barrio">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Adri%C3%A1n_Aldrete">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Perisur">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Teotihuac%C3%A1n_de_Arista">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jaime_Camil">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Houston_Jim%C3%A9nez">
    <dbpprop:dateOfBirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Miguel_Calder%C3%B3n">
    <dbpprop:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Battle_of_Tuxpan">
    <dbpprop:combatant rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Juchit%C3%A1n%2C_Guerrero">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Alfredo_Am%C3%A9zaga">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Carlos_Salcido">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Arena_Indios">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ricardo_S%C3%A1nchez_%28footballer%29">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEJMN-AM">
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tecnol%C3%B3gico_de_Monterrey%2C_Campus_Morelia">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHDNG-FM">
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Los_10%2B_Pedidos">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Operation_Michoacan">
    <dbpprop:combatant rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:place rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:place rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jeff_Linton">
    <dbpprop:countryofdeath rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:countryofdeath rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/C%C3%B3mplices_Al_Rescate:_Mariana">
    <dbpedia-owl:recordplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Elsa_Ben%C3%ADtez">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHRV">
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Nissan_Sentra">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/HSBC_Mexico">
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mart%C3%ADn_Hern%C3%A1ndez">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Cesar_Millan">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Joel_Huiqui">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Fishman_%28wrestler%29">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Maurice_E._Curts">
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Siege_of_Bexar">
    <dbpprop:combatant rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Hurricane_Keith">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/El_Palacio_de_Hierro">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Duilio_Davino">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Playas_de_Rosarito_%28municipality%29">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Arturo_Ledesma">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/1935_International_Lawn_Tennis_Challenge">
    <dbpprop:rd2t1Loc rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:rd1t2Loc rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:rd1t1Loc rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tecnol%C3%B3gico_de_Monterrey%2C_Campus_Monterrey">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Autonomous_University_of_Nayarit">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEOLA">
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Edson_Rodriguez">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/American_Broadcasting_Company">
    <dbpprop:available rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mennonite">
    <dbpprop:popplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Grupo_Modelo">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mercedes-Benz_S-Class">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Maite_Perroni">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jos%C3%A9_Jos%C3%A9">
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Chuj_language">
    <dbpedia-owl:region rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEZD">
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Daniela_Castro">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Hugo_Ayala">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Anthony_Quinn">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jos%C3%A9_de_Jes%C3%BAs_Garc%C3%ADa_Ayala">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Bah%C3%ADa_de_los_%C3%81ngeles_Airport">
    <dbpprop:cityServed rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Daniela_Cosio">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tohono_O%27odham">
    <dbpprop:popplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Silent_Light">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Gimnasio_Multidisciplinario_Nuevo_Laredo">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/2007_Gran_Premio_Tecate">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Holden_Suburban">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Sergio_%C3%81vila">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Instituto_Tecnol%C3%B3gico_Superior_de_Irapuato">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Autonomous_University_of_Tlaxcala">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Instituto_Tecnol%C3%B3gico_de_La_Piedad">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Psicosis">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:resides rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:billed rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Rodrigo_L%C3%B3pez">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Javier_Iturriaga">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/1948_Davis_Cup">
    <dbpprop:rd2t1Loc rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEETCH-AM">
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jaime_Correa">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Hurricane_Debby_%281988%29">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Banobras">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Par%C3%A1cuaro">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/CONCACAF_Champions%27_Cup_2008/footballbox11">
    <dbpprop:stadium rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/BBVA_Bancomer">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mount_Tlaloc">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/World_Wrestling_Association">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/The_Pepsi_Bottling_Group">
    <dbpprop:areaServed rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:areaServed rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XECAM-AM">
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Indigenous_peoples_in_Mexico">
    <dbpprop:regions rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Latin_Lover_%28wrestler%29">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Rafael_Garc%C3%ADa_Torres">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/FFE_Transportation">
    <dbpedia-owl:areaServed rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:areaServed rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jacob_Bekenstein">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/National_Union_of_Mine_and_Metal_Workers_of_the_Mexican_Republic">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/United_Mexican_States">
    <dbpprop:redirect rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tropical_Storm_Norman_%282000%29">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tzotzil_language">
    <dbpedia-owl:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Elias_Hern%C3%A1ndez">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Cuitlatec">
    <dbpprop:popplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Instituto_Tecnol%C3%B3gico_Superior_de_Zacapoaxtla">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Selegu%C3%A1_River">
    <dbpprop:basinCountries rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Galilea_Montijo">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Mario_P%C3%A9rez_Z%C3%BA%C3%B1iga">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Santiago%2C_Nuevo_Le%C3%B3n">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Maximilian_von_G%C3%B6tzen-It%C3%BArbide">
    <dbpprop:throne rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Bibi_Gayt%C3%A1n">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tropical_Storm_Gert_%282005%29">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHMTO">
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Club_Tijuana">
    <dbpprop:ground rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jos%C3%A9_de_Jes%C3%BAs_Corona">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/2004_Gran_Premio_Telmex/Tecate">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Azcapotzalco">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHJUA-FM">
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Vedanta_Capital_Group">
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Camila_Sodi">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Michel_Rosales">
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Miguel_de_la_Madrid">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Sussie_4">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Hurricane_Rick_%281997%29">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Confederation_of_Mexican_Workers">
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/SS_Catalina">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Sonmate">
    <dbpprop:foundation rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:foundationplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/%C3%81lvaro_Obreg%C3%B3n">
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/2005-06_A1_Grand_Prix_of_Nations%2C_Mexico">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jorge_Orta">
    <dbpprop:dateOfBirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ricardo_Rinc%C3%B3n">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/1974_Davis_Cup_Americas_Zone">
    <dbpprop:rd2t1Loc rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Luis_Flores_%28footballer%29">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Rito_Romero">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Toribio_Romo_Gonz%C3%A1lez">
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Sierra_Vista%2C_Arizona">
    <dbpprop:twinTowns rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tigres_Reynosa">
    <dbpprop:ground rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Renault_9_%26_11">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Chilchota_Alimentos">
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Gila_River">
    <dbpprop:basinCountries rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/General_Anaya">
    <dbpprop:statusText rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Miguel_Zepeda">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/M%C5%93nia">
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XHTJB-TV">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Laura_Elizondo">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Alpek">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jos%C3%A9_Ladr%C3%B6nn">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ford_Escort_%28North_America%29">
    <dbpprop:assembly rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jos%C3%A9_Daniel_Guerrero">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tamaulipas_Institute_of_Higher_Education">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Armando_Flores">
    <dbpedia-owl:homeTown rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:origin rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Lagunillas_%28municipality%29">
    <dbpprop:subdivisionName rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Tecos_UAG">
    <dbpprop:ground rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/CONCACAF_Champions%27_Cup_2005/footballbox14">
    <dbpprop:stadium rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Alfonso_de_Anda">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Auditorium_Telmex">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Enrique_Ornelas">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEMTS">
    <dbpedia-owl:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:area rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Plautdietsch">
    <dbpprop:states rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Said_God%C3%ADnez">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Hurricane_Alice_%28June_1954%29">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Hurricane_Janet">
    <dbpprop:areas rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/XEANT-AM">
    <dbpedia-owl:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:city rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Jes%C3%BAs_Helguera">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Renato_L%C3%B3pez">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Hugo_S%C3%A1nchez_Guerrero">
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Manuel_P%C3%A9rez">
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:countryofbirth rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Made_In_Mexico">
    <dbpprop:redirect rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Arte_de_la_lengua_mexicana_%281754_book%29">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/French_intervention_in_Mexico">
    <dbpprop:place rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:place rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Enrique_de_la_Mora">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:significant_buildings rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:deathPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:deathplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ricardo_Lopez_%28boxer%29">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Ricardo_Arredondo">
    <dbpedia-owl:nationality rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Aracely_Ar%C3%A1mbula">
    <dbpprop:birthPlace rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:birthplace rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Sea_Garden">
    <dbpprop:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpedia-owl:location rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Asociaci%C3%B3n_de_Scouts_de_M%C3%A9xico%2C_A.C.">
    <dbpprop:country rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/2007_U-20_World_Cup_CONCACAF_qualifying_tournament/footballbox5">
    <dbpprop:stadium rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
  <rdf:Description rdf:about="http://dbpedia.org/resource/Football_%28soccer%29_at_the_2005_Maccabiah_Games_-_Men%27s_tournament">
    <dbpprop:round8WithThirdProperty14 rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:round8WithThirdProperty39 rdf:resource="http://dbpedia.org/resource/Mexico"/>
    <dbpprop:round8WithThirdProperty27 rdf:resource="http://dbpedia.org/resource/Mexico"/>
  </rdf:Description>
</rdf:RDF>
*/