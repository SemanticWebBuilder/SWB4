/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.linkeddata.spider.repository.BigdataRDFStore;

import org.semanticwb.linkeddata.spider.repository.DocumentTriples;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Reader;
import java.util.HashSet;
import org.openrdf.model.Statement;
import org.openrdf.repository.Repository;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

/**
 *
 * @author karen.najera
 */
public class BigdataStoreManager {

    private static Logger log = SWBUtils.getLogger(BigdataStoreManager.class);
    public Repository repository;
    BigdataStoreCreator bigdatastore = new BigdataStoreCreator();
    BigdataStoreAdd bigdatastoreadd = new BigdataStoreAdd();
    BigdataStoreQuery bigdatastorequery = new BigdataStoreQuery();
    BigdataStoreRemove bigdatastoreRemove = new BigdataStoreRemove();
    BigdataStoreExportHandler bigdatastoreexporthandler = new BigdataStoreExportHandler(repository);

    /**
     * Constructor
     * @param database The path for the journal file
     */
    public BigdataStoreManager(String database) {
        try {
            bigdatastore.createBigdataStore(database);
        } catch (Exception ex) {
        }
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    /**
     * initialize bigdata repository  
     */
    public void initializeRepository() {
        bigdatastore.initializeRepository();
        this.repository = bigdatastore.getRepository();
        bigdatastoreadd.setRepository(repository);
    }

    /**
     * Returns the quantity of statements in the repository
     * @return stmtCount The quantity of statements in the repository
     */
    public long size() {
        long stmtCount = bigdatastore.size();
        return stmtCount;
    }

    /**
     * Add a triple to repository
     * @param s_suj URI subject
     * @param s_pred URI predicate
     * @param s_obj URI object
     * @param s_context URI context
     */
    public void addTriple(String s_suj, String s_pred, String s_obj, String s_context) {
        //bigdatastoreadd.addDatawithContext_URI(s_suj, s_pred, s_obj, s_context);
        bigdatastoreadd.addTriple(s_suj, s_pred, s_obj, s_context);
    }

    /**
     * Add a triple to repository
     * @param s_suj URI subject
     * @param s_pred URI predicate
     * @param s_obj URI object
     */
    /*
    public void addDatawithContext_Literal(String s_suj, String s_pred, String s_obj, String s_context) {
    bigdatastoreadd.addDatawithContext_Literal(s_suj, s_pred, s_obj, s_context);
    }
     */
    /**
     * Returns the quantity of statements in the repository
     * @return stmtCount The quantity of statements in the repository
     */
    public int getRepeated() {
        int repeated = bigdatastoreadd.getRepeated();
        return repeated;
    }

    /**
     * Add triples from document
     * @param path document path
     * @param baseURI namespace
     */
    public void addDataFromFile(String path, String baseURI, String context) {
        bigdatastoreadd.addDataFromFile(path, baseURI, context);
    }

    /**
     * Add triples from document
     * @param path document path
     * @param baseURI namespace
     */
    public void addDataFromReader(Reader reader, String baseURI, String context) {
        bigdatastoreadd.addDataFromReader(reader, baseURI, context);
    }

    /**     
     * Add provenance information
     * @param s_URLcontext URL context
     * @param s_pred URI predicate
     * @param s_obj String object 
     */
    public void addProvenanceInformation(String s_URLcontext, String s_pred, String s_obj) {
        bigdatastoreadd.addProvenanceInformation(s_URLcontext, s_pred, s_obj);
    }

    /**     
     * Update provenance information
     * @param s_URLcontext URL context
     * @param s_pred URI predicate
     * @param s_obj String object 
     */
    public void updateProvenanceInformation(String s_URLcontext, String s_pred, String s_obj) {
        bigdatastoreadd.updateProvenanceInformation(s_URLcontext, s_pred, s_obj);
    }

    /**
     * Query all data in the repository
     * @param s_suj URI subject
     * @param s_pred URI predicate
     * @param s_obj URI object
     * @param s_context URI context
     * @throws Exception
     */
    public DocumentTriples queryRepository(String s_suj, String s_pred, String s_obj, String s_context) throws Exception {
        bigdatastorequery.setRepository(repository);
        DocumentTriples document = bigdatastorequery.queryRepository(s_suj, s_pred, s_obj, s_context);
        return document;
    }

    /**
     * Query all data in the repository
     * @param s_suj URI subject
     * @param s_pred URI predicate
     * @param s_obj URI object
     * @param s_context URI context
     * @throws Exception
     */
    public HashSet<Statement> queryRepository(String s_suj, String s_pred, String s_obj) throws Exception {
        bigdatastorequery.setRepository(repository);
        HashSet<Statement> stms = bigdatastorequery.queryRepository(s_suj, s_pred, s_obj);
        return stms;
    }

    /**
     * Query all NamedGraphs in the repository
     * @return HashSet<String> provenance Information
     */
    public HashSet<String> loadVisitedURLs() {
        bigdatastorequery.setRepository(repository);
        HashSet<String> urls = bigdatastorequery.queryNamedGraphs();
        return urls;
    }

    /**
     * Query provenance information in the repository
     * @param s_suj URI subject
     * @param s_pred URI predicate
     * @param s_obj URI object
     * @return HashSet<String> provenance Information
     */
    public HashSet<String> queryProvenanceInformation(String s_context, String s_pred, String s_obj) {
        bigdatastorequery.setRepository(repository);
        HashSet<String> provenanceInformation = bigdatastorequery.queryProvenanceInformation(s_context, s_pred, s_obj);
        return provenanceInformation;
    }

    /**
     * Query provenance information in the repository
     * @param s_suj URI subject
     * @param s_pred URI predicate
     * @param s_obj URI object
     * @return HashSet<String> provenance Information
     */
    public InputStream exportToMemoryNT() {
        bigdatastorequery.setRepository(repository);
        InputStream is = bigdatastorequery.exportToMemoryNT();
        if (is == null) {
            System.out.println("InputStream es null");
        }
        return is;
    }

    /**
     * Remove a triple from repository
     * @param s_suj URI subject
     * @param s_pred URI predicate
     * @param s_obj URI object
     * @param s_context URI context
     */
    public void removefromRepository(String s_suj, String s_pred, String s_obj, String s_context) {
        bigdatastoreRemove.removeTriple(s_suj, s_pred, s_obj, s_context);
    }

    /**
     * export to file in RDFXML format
     * @param filepath target file path 
     * @throws FileNotFoundException
     */
    public void exportToRDFXML(String filepath) throws FileNotFoundException {
        bigdatastoreexporthandler.exportToRDFXML(filepath);
    }

    /**
     * export to file in NTriples format
     * @param filepath target file path 
     * @throws FileNotFoundException     
     */
    public void exportToNTriples(String filepath) throws FileNotFoundException {
        bigdatastoreexporthandler.exportToNTriples(filepath);
    }
}
