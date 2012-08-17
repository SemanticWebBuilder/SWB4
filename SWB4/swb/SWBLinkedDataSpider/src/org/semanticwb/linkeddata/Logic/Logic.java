/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.linkeddata.Logic;

import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.semanticwb.linkeddata.spider.repository.DBManager;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.semanticwb.linkeddata.spider.repository.DocumentTriples;
import org.semanticwb.linkeddata.spider.repository.Triple;
import org.semanticwb.linkeddata.spider.DocumentInfo;
import org.semanticwb.linkeddata.spider.TYPE;
import org.semanticwb.linkeddata.spider.repository.ProvenanceInformation;

/**
 *
 * @author karen.najera
 */
public class Logic {

    Model model = ModelFactory.createDefaultModel();
    DocumentInfo docInfo;
    DocumentTriples documents = new DocumentTriples();
    Set<String> urlVisited = Collections.synchronizedSet(new HashSet<String>());
    private DBManager dbcreator;
    private String RDFStore;
    private int daysforcheck = 3;
    LoadVocabularies lv;
    ProvenanceInformation provInf = new ProvenanceInformation();
    LoadProvenanceInformation loadprovInf;

    public Logic(String database, String RDFStore) {
        dbcreator = new DBManager(database, RDFStore);
        dbcreator.createDatabase();
        lv = new LoadVocabularies(dbcreator);
        loadprovInf = new LoadProvenanceInformation(dbcreator);
        this.RDFStore = RDFStore;

    }

    public void init() {
        loadvisitedURLs();
        //The http://www.w3.org/2000/01/rdf-schema# namespace is associated (by convention) with the rdfs: namespace prefix 
        //The http://www.w3.org/1999/02/22-rdf-syntax-ns# namespace is associated (by convention) with the rdf: namespace prefix 
        //owl: <http://www.w3.org/2002/07/owl#>        
        //dcterms: <http://purl.org/dc/terms/> -> <http://dublincore.org/2012/06/14/dcterms.rdf>
        //skos: <http://www.w3.org/2004/02/skos/core> -> <http://www.w3.org/2004/02/skos/core.rdf>
        //foaf: <http://xmlns.com/foaf/0.1/> -> <http://xmlns.com/foaf/spec/index.rdf>

        String url = "http://www.w3.org/2000/01/rdf-schema";
        if (!urlVisited.contains(url)) {
            urlVisited.add(url);
            loadOnVisitDate(url);
            lv.loadRDFSchema();
            System.out.println("URL added RDFSchema: " + url);

        } else {
            String lastAccess = loadprovInf.getLastAcess(url);
            System.err.println("lastAccess: " + lastAccess);
            if (lastAccess != null && toUpdate(lastAccess)) {
                System.err.println("Actualizar vocabulario... " + url);
                lv.loadRDFSchema();
                updateOnVisitDate(url);
            }
        }


        url = "http://www.w3.org/1999/02/22-rdf-syntax-ns";
        if (!urlVisited.contains(url)) {
            urlVisited.add(url);
            loadOnVisitDate(url);
            lv.loadRDFSyntaxis();
            System.out.println("URL added RDFSyntax: " + url);
        } else {
            String lastAccess = loadprovInf.getLastAcess(url);
            if (lastAccess != null && toUpdate(lastAccess)) {
                System.err.println("Actualizar vocabulario... " + url);
                lv.loadRDFSchema();
                updateOnVisitDate(url);
            }
        }

        url = "http://www.w3.org/2002/07/owl";
        if (!urlVisited.contains(url)) {
            urlVisited.add(url);
            loadOnVisitDate(url);
            //lv.loadOWL();
            System.out.println("URL added OWL: " + url);
        } else {
            String lastAccess = loadprovInf.getLastAcess(url);
            if (lastAccess != null && toUpdate(lastAccess)) {
                System.err.println("Actualizar vocabulario... " + url);
                lv.loadRDFSchema();
                updateOnVisitDate(url);
            }
        }

        url = "http://xmlns.com/foaf/0.1/";
        if (!urlVisited.contains(url)) {
            urlVisited.add(url);
            loadOnVisitDate(url);
            lv.loadFoaf();
            System.out.println("URL added foaf: " + url);
        } else {
            String lastAccess = loadprovInf.getLastAcess(url);
            if (lastAccess != null && toUpdate(lastAccess)) {
                System.err.println("Actualizar vocabulario... " + url);
                lv.loadRDFSchema();
                updateOnVisitDate(url);
            }
        }

        url = "http://purl.org/dc/terms/";
        if (!urlVisited.contains(url)) {
            urlVisited.add(url);
            loadOnVisitDate(url);
            lv.loaddcTerms();
            System.out.println("URL added dcTerms: " + url);
        }
        url = "http://www.w3.org/2004/02/skos/core";
        if (!urlVisited.contains(url)) {
            urlVisited.add(url);
            loadOnVisitDate(url);
            lv.loadSkos();
            System.out.println("URL added skos: " + url);
        } else {
            String lastAccess = loadprovInf.getLastAcess(url);
            if (lastAccess != null && toUpdate(lastAccess)) {
                System.err.println("Actualizar vocabulario... " + url);
                lv.loadRDFSchema();
                updateOnVisitDate(url);
            }
        }
    }

    public void loadvisitedURLs() {
        if (RDFStore.equalsIgnoreCase("Bigdata")) {
            urlVisited = dbcreator.bigdatastoremanager.loadVisitedURLs();
            //  Print urlVisited
            /*  
            Iterator<String> iter = urlVisited.iterator();
            while (iter.hasNext()) {
            System.out.println("URLs: " + iter.next());
            }
            //    */
        } else if (RDFStore.equalsIgnoreCase("MySQL")) {
            urlVisited = dbcreator.MySQLStoreManager.loadVisitedURLs();

            Iterator<String> iter = urlVisited.iterator();
            while (iter.hasNext()) {
                System.out.println("URLs: " + iter.next());
            }
            //    */
        }
    }

    private DocumentTriples loadTriplesfromURL(URL url) {
        DocumentTriples document = new DocumentTriples();
        try {
            String s_url;
            int pos = url.toString().indexOf("#");
            if (pos != -1) {
                s_url = url.toString().substring(0, pos);
            } else if (url.toString().contains("http://xmlns.com/foaf/0.1/")) {
                s_url = "http://xmlns.com/foaf/0.1/";
            } else if (url.toString().contains("http://purl.org/dc/terms/")) {
                s_url = "http://purl.org/dc/terms/";
            } else {
                s_url = url.toString();
            }


            if (urlVisited.contains(s_url)) {
                if (RDFStore.equalsIgnoreCase("Bigdata")) {

                    document = dbcreator.bigdatastoremanager.queryRepository(null, null, null, s_url);

                    /* 
                    Set<Triple> triples = document.getTriples(url.toString());
                    Iterator<Triple> iter = triples.iterator();
                    while (iter.hasNext()) {
                    Triple triple = iter.next();
                    if (triple.exist) {
                    System.out.println("URLs: " + triple.toString() + " " + triple.exist);
                    }
                    }
                     */
                } else if (RDFStore.equalsIgnoreCase("mySQL")) {
                document = dbcreator.MySQLStoreManager.LoadTriplesfromURL(s_url);
            }
                
                
            }
        } catch (Exception ex) {
            System.err.println("Error loadTriplesfromURL: " + ex);

        } finally {
            return document;
        }
    }

    private void updateTriplesfromDocument(String s_url) {
        Set<Triple> triples = documents.getTriples(s_url);
        Iterator<Triple> iter = triples.iterator();
        while (iter.hasNext()) {
            Triple triple = iter.next();
            if (triple.isOnPage && !triple.isOnDatabase) {
                System.out.println("OnEnd AGREGA tripla: <" + s_url + "> <" + triple.suj + "> <" + triple.pred + "> <" + triple.obj + ">");
                if (RDFStore.equalsIgnoreCase("Bigdata")) {
                    dbcreator.bigdatastoremanager.addTriple(triple.suj, triple.pred, triple.obj, s_url);
                } else if (RDFStore.equalsIgnoreCase("mysql")) {
                    dbcreator.MySQLStoreManager.addTriple(triple.suj, triple.pred, triple.obj, s_url);
                }
            } else if (!triple.isOnPage && triple.isOnDatabase) {
                //remove
                System.out.println("OnEnd BORRA tripla: <" + s_url + "> <" + triple.suj + "> <" + triple.pred + "> <" + triple.obj + ">");
                if (RDFStore.equalsIgnoreCase("Bigdata")) {
                    dbcreator.bigdatastoremanager.removefromRepository(triple.suj, triple.pred, triple.obj, s_url);
                } else if (RDFStore.equalsIgnoreCase("mysql")) {
                    dbcreator.MySQLStoreManager.removeTriple(triple.suj, triple.pred, triple.obj, s_url);
                }

            }

        }
        documents.remove(s_url);
    }

    private void loadOnVisitDate(String s_url) {
        if (urlVisited.contains(s_url)) {
            provInf.url = s_url;
            provInf.lastAcess = getTodayDate();
            loadprovInf.setProvInf(provInf);
            loadprovInf.loadProvenanceInformation();
            //System.err.println("loadOnVisitDate: " + s_url + " date: " + provInf.lastAcess);
        }
    }

    private void updateOnVisitDate(String s_url) {
        if (urlVisited.contains(s_url)) {
            provInf.url = s_url;
            provInf.lastAcess = getTodayDate();
            loadprovInf.setProvInf(provInf);
            loadprovInf.updateProvenanceInformation();
        }
    }

    public boolean onNewSubject(URI url, TYPE type) {
        boolean visit = false;
        if (type == type.PREDICATE) {
            System.out.println("predicate: " + url.toString());
            visit = false;
        } else {
            //System.out.println("onNewSubject: " + url.toString());
            try {
                String s_url;
                int pos = url.toString().indexOf("#");
                if (pos != -1) {
                    s_url = url.toString().substring(0, pos);
                } else if (url.toString().contains("http://xmlns.com/foaf/0.1/")) {
                    s_url = "http://xmlns.com/foaf/0.1/";
                } else if (url.toString().contains("http://purl.org/dc/terms/")) {
                    s_url = "http://purl.org/dc/terms/";
                } else {
                    s_url = url.toString();
                }

                if (!urlVisited.contains(s_url)) {
                    urlVisited.add(s_url);
                    visit = true;
                    //loadOnVisitDate(s_url);                
                    //System.out.println("logic.onNewSubject - nueva: " + url.toString());
                } else {
                    String lastAccess = loadprovInf.getLastAcess(s_url);
                    if (lastAccess != null && toUpdate(lastAccess)) {
                        System.err.println("logic.onNewSubject - Actualizar ... " + s_url);
                        visit = true;
                        //updateOnVisitDate(s_url);
                    } else {
                        visit = false;
                        //System.out.println("url already visited: " + s_url + " ... " + url.toString());
                    }
                }
            } catch (Exception ex) {
                System.err.println("Error toVisit: " + ex);
            }
        }
        return visit;
    }

    private boolean toUpdate(String s_lastAccess) {
        int days = 0;

        if (s_lastAccess != null) {
            Date lastAccess = stringtoDate(s_lastAccess);
            Date CurrentDate = new java.util.Date();
            days = compareDates(lastAccess, CurrentDate);
            //System.out.println("days of difference: " + days);
        }
        if (days >= daysforcheck) {
            return true;
        } else {
            return false;
        }
    }

    public void onStart(URL url, URL initialNode) {
        try {
            String s_url;
            int pos = url.toString().indexOf("#");
            if (pos != -1) {
                s_url = url.toString().substring(0, pos);
            } else if (url.toString().contains("http://xmlns.com/foaf/0.1/")) {
                s_url = "http://xmlns.com/foaf/0.1/";
            } else if (url.toString().contains("http://purl.org/dc/terms/")) {
                s_url = "http://purl.org/dc/terms/";
            } else {
                s_url = url.toString();
            }
            System.err.println(": Inicia ------------ URL :" + url.toString() + " ----------------------" + s_url);
            if (url == initialNode) {
                urlVisited.add(s_url);
            }
            if (urlVisited.contains(s_url)) {
                String lastAccess = loadprovInf.getLastAcess(url.toString());
                if (lastAccess == null) {
                    //provenance information               
                    loadOnVisitDate(url.toString());
                    //
                } else {
                    if (toUpdate(lastAccess)) {
                        System.out.println("onStart - si actualiza");
                        updateOnVisitDate(s_url);
                        DocumentTriples document = loadTriplesfromURL(url);
                        if (document.hasURL(url.toString())) {
                            Set<Triple> triples = document.getTriples(url.toString());
                            documents.add(url.toString(), triples);
                        }
                    }
                }
            } else {
                System.out.println("logic.onStart - nueva: " + url.toString());
            }
        } catch (Exception ex) {
            System.err.println("Error toVisit: " + ex);
        }
    }

    public void onEnd(URL url) {
        try {
            String s_url;
            int pos = url.toString().indexOf("#");
            if (pos != -1) {
                s_url = url.toString().substring(0, pos);
            } else if (url.toString().contains("http://xmlns.com/foaf/0.1/")) {
                s_url = "http://xmlns.com/foaf/0.1/";
            } else if (url.toString().contains("http://purl.org/dc/terms/")) {
                s_url = "http://purl.org/dc/terms/";
            } else {
                s_url = url.toString();
            }

            //System.err.println(": Termina ------------ " + url + " ----------------------" + s_url);
            if (urlVisited.contains(s_url)) {
                if (documents.hasURL(s_url)) {
                    updateTriplesfromDocument(s_url);
                    //System.out.println("logic.onEnd - Actualizada " + s_url);
                }
                //System.out.println("logic.onEnd - nueva " + s_url);
            }
        } catch (Exception ex) {
            System.err.println("Error toVisit: " + ex);
        }
    }

    public void onTriple(URI suj, URI pred, String obj, URL url) {
        String s_url;
        int pos = url.toString().indexOf("#");
        if (pos != -1) {
            s_url = url.toString().substring(0, pos);
        } else if (url.toString().contains("http://xmlns.com/foaf/0.1/")) {
            s_url = "http://xmlns.com/foaf/0.1/";
        } else if (url.toString().contains("http://purl.org/dc/terms/")) {
            s_url = "http://purl.org/dc/terms/";
        } else {
            s_url = url.toString();
        }

        if (documents.hasURL(s_url)) {
            //if (!documents.hasTriple(s_url, suj.toString(), pred.toString(), obj.toString(), false)) {
            if (!documents.hasTriple(s_url, suj.toString(), pred.toString(), obj.toString())) {
                Triple triple = new Triple(suj.toString(), pred.toString(), obj.toString(), false, true);
                documents.add(s_url, triple);
                System.out.println("Tripla nueva en: " + s_url + "tripla: " + triple.toString());
            }
        } else {
            //System.out.println("no es actualización de : " + s_url + " -se agrega Tripla nueva: " + suj.toString() + pred.toString() + obj);
            if (RDFStore.equalsIgnoreCase("Bigdata")) {
                dbcreator.bigdatastoremanager.addTriple(suj.toString(), pred.toString(), obj, s_url);
            } else if (RDFStore.equalsIgnoreCase("mysql")) {
                dbcreator.MySQLStoreManager.addTriple(suj.toString(), pred.toString(), obj, s_url);
            }
        }

    }

    public void onError(URL url, int error) {
        if (urlVisited.contains(url.toString())) {
            //urlVisited.remove(url.toString());
        }
        //System.err.println("Error en url: " + url + " code: " + error);
    }

    public void onError(URL url, Throwable e) {
        if (urlVisited.contains(url.toString())) {
            //urlVisited.remove(url.toString());
        }
        //System.err.println("Error en url: " + url + " Throwable: ");
    }

    public String getTodayDate() {
        Date date = new java.util.Date();
        String s_date = datetoString(date);
        return s_date;
    }

    public String datetoString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String s_date = null;
        try {
            s_date = format.format(date);
        } catch (Exception ex) {
            System.err.println("Error datetoString: " + ex);
        } finally {
            return s_date;
        }
    }

    public Date stringtoDate(String s_lastAccess) {
        String s_date = s_lastAccess;
        int pos = s_lastAccess.indexOf("\"");
        if (pos != -1) {
            s_date = s_lastAccess.replaceAll("\"", "");
        }
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;

        try {
            date = format.parse(s_date);
        } catch (Exception ex) {
            System.err.println("pos: " + pos + " -Error stringtoDate: " + ex + ". date: " + s_date);
        } finally {
            return date;
        }
    }

    public int compareDates(Date lastAcess, Date CurrentDate) {
        int i;
        int days, years, daysperyear;
        Calendar CDcalendar = Calendar.getInstance();
        CDcalendar.setTime(CurrentDate);
        Calendar LCcalendar = Calendar.getInstance();
        LCcalendar.setTime(lastAcess);

        int compDates = CurrentDate.compareTo(lastAcess);
        if (compDates > 0) {
            if (CDcalendar.get(CDcalendar.YEAR) == LCcalendar.get(LCcalendar.YEAR)) {
                days = CDcalendar.get(CDcalendar.DAY_OF_YEAR) - LCcalendar.get(LCcalendar.DAY_OF_YEAR);
            } else {
                Calendar calendar = Calendar.getInstance();
                Date date = stringtoDate("31/12/" + LCcalendar.get(LCcalendar.YEAR));
                calendar.setTime(date);
                daysperyear = calendar.get(calendar.DAY_OF_YEAR);

                if (CDcalendar.get(CDcalendar.MONTH) <= LCcalendar.get(LCcalendar.MONTH) && CDcalendar.get(CDcalendar.DAY_OF_YEAR) < LCcalendar.get(LCcalendar.DAY_OF_YEAR)) {
                    years = CDcalendar.get(CDcalendar.YEAR) - LCcalendar.get(LCcalendar.YEAR) - 1;
                    days = daysperyear - LCcalendar.get(LCcalendar.DAY_OF_YEAR) + CDcalendar.get(CDcalendar.DAY_OF_YEAR);
                    i = 0;
                    System.out.println("dias inicia: " + days);
                    while (i < years) {

                        date = stringtoDate("31/12/" + LCcalendar.get(LCcalendar.YEAR) + i);
                        calendar.setTime(date);
                        daysperyear = calendar.get(calendar.DAY_OF_YEAR);
                        days = days + daysperyear;
                        System.out.println("año: " + (LCcalendar.get(LCcalendar.YEAR) + i) + " Dias por año: " + "daysperyear: " + daysperyear + " days: " + days);
                        i++;
                    }
                } else {
                    years = CDcalendar.get(CDcalendar.YEAR) - LCcalendar.get(LCcalendar.YEAR);
                    days = CDcalendar.get(CDcalendar.DAY_OF_YEAR) - LCcalendar.get(LCcalendar.DAY_OF_YEAR);
                    i = 0;
                    System.out.println("dias inicia: " + days);
                    while (i < years) {
                        date = stringtoDate("31/12/" + LCcalendar.get(LCcalendar.YEAR) + i);
                        calendar.setTime(date);
                        daysperyear = calendar.get(calendar.DAY_OF_YEAR);
                        days = days + daysperyear;
                        System.out.println("año: " + (LCcalendar.get(LCcalendar.YEAR) + i) + " Dias por año: " + "daysperyear: " + daysperyear + " days: " + days);
                        i++;
                    }
                }
            }

        } else {
            days = 0;
        }
        return days;
    }

    public static void main(String[] arg) {
        //String database = "/repository/datavocabularies.jnl";
        //String RDFStore = "Bigdata";
        String database = "ldstore";
        String RDFStore = "MySQL";

        Logic l = new Logic(database, RDFStore);

        l.init();
        /*
        String s = "http://www.w3.org/2000/01/rdf-schema#domain";
        String p = "http://www.w3.org/2000/01/rdf-schema#isDefinedBy";
        String o = "http://www.w3.org/2000/01/rdf-schema#";
        
        String url = "http://www.w3.org/2000/01/rdf-schema";
        URL u_url;
        try {
        u_url = new URL(url);
        //l.loadTriplesfromURL(u_url);
        //l.onStart(u_url);
        if (!l.documents.hasTriple(url, s, p, o)) {
        //documents.add(url, null);
        }
        
        
        
        } catch (MalformedURLException ex) {
        Logger.getLogger(Logic.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
         * 
         */
        /*
        URI uri;
        try {
        String url = "http://www.w3.org/2000/01/rdf-schema";
        uri = new URI(url);
        l.onNewSubject(uri);
        String lastAccess = l.loadprovInf.getLastAcess(url);
        System.err.println(url + "lastAccess: " + lastAccess);
        url = "http://xmlns.com/foaf/0.1/";
        uri = new URI(url);
        l.onNewSubject(uri);
        lastAccess = l.loadprovInf.getLastAcess(url);
        System.err.println(url + "lastAccess: " + lastAccess);
        } catch (URISyntaxException ex) {
        System.err.println("Error: " + ex);
        }
         */

        /*
        String lastAccess = l.loadprovInf.getLastAcess(url);
        System.out.println(url + ": " + lastAccess);
        url = "http://www.w3.org/1999/02/22-rdf-syntax-ns";
        lastAccess = l.loadprovInf.getLastAcess(url);
        System.out.println(url + ": " + lastAccess);
        url = "http://www.w3.org/2002/07/owl";
        lastAccess = l.loadprovInf.getLastAcess(url);
        System.out.println(url + ": " + lastAccess);
        url = "http://xmlns.com/foaf/0.1/";
        lastAccess = l.loadprovInf.getLastAcess(url);
        System.out.println(url + ": " + lastAccess);
        url = "http://purl.org/dc/terms/";
        lastAccess = l.loadprovInf.getLastAcess(url);
        System.out.println(url + ": " + lastAccess);
        url = "http://www.w3.org/2004/02/skos/core";
        lastAccess = l.loadprovInf.getLastAcess(url);
        System.out.println(url + ": " + lastAccess);
         * 
         */
        // Date lastChecked = l.stringtoDate("20/02/2012");
        //  Date CurrentDate = l.stringtoDate("19/02/2014");
        // int days = l.compareDates(lastChecked, CurrentDate);
        // System.out.println("days: " + days);
    }
}
