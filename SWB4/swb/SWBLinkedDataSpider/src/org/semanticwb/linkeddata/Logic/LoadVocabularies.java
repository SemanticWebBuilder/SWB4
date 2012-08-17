/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.linkeddata.Logic;

import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Statement;
import org.semanticwb.linkeddata.spider.repository.DBManager;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.Iterator;

/**
 *
 * @author karen.najera
 */
public class LoadVocabularies {

    private DBManager dbcreator;
    private String RDFStore;
    private String urlRedirect;
    private Model model;

    public LoadVocabularies(DBManager dbcreator) {
        model = ModelFactory.createDefaultModel();
        this.dbcreator = dbcreator;
        this.RDFStore = dbcreator.RDFStore;
    }

    private Reader getRDFSchema() {
        urlRedirect = "http://www.w3.org/2000/01/rdf-schema";
        
        Reader myreader = null;
        try {
            URL page = new URL(urlRedirect);
            myreader = new BufferedReader(new InputStreamReader(page.openStream()));

        } catch (Exception e) {
            System.err.println("Error page: " + e);
        } finally {
            return myreader;
        }
    }

    private Reader getRDFSyntax() {
        urlRedirect = "http://www.w3.org/1999/02/22-rdf-syntax-ns";

        Reader myreader = null;
        try {
            URL page = new URL(urlRedirect);
            myreader = new BufferedReader(new InputStreamReader(page.openStream()));

        } catch (Exception e) {
            System.err.println("Error page: " + e);
        } finally {
            return myreader;
        }
    }

    private Reader getOWL() {
        urlRedirect = "http://www.w3.org/2002/07/owl";

        Reader myreader = null;
        try {
            URL page = new URL(urlRedirect);
            myreader = new BufferedReader(new InputStreamReader(page.openStream()));

        } catch (Exception e) {
            System.err.println("Error page: " + e);
        } finally {
            return myreader;
        }
    }

    private Reader getFoaf() {
        urlRedirect = "http://xmlns.com/foaf/spec/index.rdf";
        Reader myreader = null;
        try {
            URL page = new URL(urlRedirect);
            myreader = new BufferedReader(new InputStreamReader(page.openStream()));

        } catch (Exception e) {
            System.err.println("Error page: " + e);
        } finally {
            urlRedirect = "http://xmlns.com/foaf/0.1/";
            return myreader;
        }

    }

    private Reader getdcTerms() {
        urlRedirect = "http://dublincore.org/2012/06/14/dcterms.rdf";

        Reader myreader = null;
        try {
            URL page = new URL(urlRedirect);
            myreader = new BufferedReader(new InputStreamReader(page.openStream()));

        } catch (Exception e) {
            System.err.println("Error page: " + e);
        } finally {
            urlRedirect = "http://purl.org/dc/terms/";
            return myreader;
        }

    }

    private Reader getSkos() {
        urlRedirect = "http://www.w3.org/2004/02/skos/core.rdf";

        Reader myreader = null;
        try {
            URL page = new URL(urlRedirect);
            myreader = new BufferedReader(new InputStreamReader(page.openStream()));

        } catch (Exception e) {
            System.err.println("Error page: " + e);
        } finally {
            urlRedirect = "http://www.w3.org/2004/02/skos/core";
            return myreader;
        }
    }

    public void loadRDFSchema() {
        Reader reader = getRDFSchema();
        if (reader != null) {
            loadVocabulary(reader);
        }
    }

    public void loadRDFSyntaxis() {
        Reader reader = getRDFSyntax();
        if (reader != null) {
            loadVocabulary(reader);
        }
    }

    public void loadOWL() {
        Reader reader = getOWL();
        if (reader != null) {
            loadVocabulary(reader);
        }
    }

    public void loadFoaf() {
        Reader reader = getFoaf();
        if (reader != null) {
            loadVocabulary(reader);
        }
    }

    public void loaddcTerms() {
        Reader reader = getdcTerms();
        if (reader != null) {
            loadVocabulary(reader);
        }
    }

    public void loadSkos() {
        Reader reader = getSkos();
        if (reader != null) {
            loadVocabulary(reader);
        }
    }

    public void loadVocabularies() {
        //dcterms: <http://purl.org/dc/terms/> -> <http://dublincore.org/2012/06/14/dcterms.rdf>
        //skos: <http://www.w3.org/2004/02/skos/core> -> <http://www.w3.org/2004/02/skos/core.rdf>
        //foaf: <http://xmlns.com/foaf/0.1/> -> <http://xmlns.com/foaf/spec/index.rdf>
        //owl: <http://www.w3.org/2002/07/owl#>
        //The http://www.w3.org/2000/01/rdf-schema# namespace is associated (by convention) with the rdfs: namespace prefix 
        //The http://www.w3.org/1999/02/22-rdf-syntax-ns# namespace is associated (by convention) with the rdf: namespace prefix 

        Reader reader = getRDFSchema();
        loadVocabulary(reader);

        reader = getRDFSyntax();
        loadVocabulary(reader);

        reader = getOWL();
        loadVocabulary(reader);

        reader = getFoaf();
        loadVocabulary(reader);

        reader = getdcTerms();
        loadVocabulary(reader);

        reader = getSkos();
        loadVocabulary(reader);
    }

    private void loadVocabulary(Reader reader) {
        if (RDFStore.equalsIgnoreCase("Bigdata")) {
            dbcreator.bigdatastoremanager.addDataFromReader(reader, urlRedirect, urlRedirect);
        } else if (RDFStore.equalsIgnoreCase("mysql")) {

            try {
                //dbcreator.db.insertURLandDate(urlRedirect, "12/09/2012");
                model.read(reader, null);
            } catch (Exception e) {
                System.out.println("Error model: " + e);
            }
            Iterator<Statement> stmts = model.listStatements();
            while (stmts.hasNext()) {
                Statement st = stmts.next();
                dbcreator.MySQLStoreManager.addTriple(st.getSubject().toString(), st.getPredicate().toString(), st.getObject().toString(), urlRedirect);
            }
            clearModel();
        }
    }

    private void clearModel() {
        model.removeAll();
    }

    /*
    public static void main(String[] arg) {
        //String database = "/repository/datavocabularies.jnl";
        //String RDFStore = "Bigdata";
        String database = "ldstore";
        String RDFStore = "mysql";
        DBManager db = new DBManager(database, RDFStore);
        db.createDatabase();
        LoadVocabularies lv = new LoadVocabularies(db);
        //lv.loadVocabularies();
        lv.loadRDFSchema();
    }
     * 
     */
}
