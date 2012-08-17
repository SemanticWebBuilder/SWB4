/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.linkeddata.spider.repository.mysqldatabase;

import java.io.Reader;
import java.sql.SQLException;
import java.util.HashSet;
import org.semanticwb.linkeddata.spider.repository.DocumentTriples;


/**
 *
 * @author karen.najera
 */
public class MysqldatabaseManager {

    Mysqldatabase mysqldb;

    public MysqldatabaseManager(String databasename) {
        this.mysqldb = new Mysqldatabase(databasename);
    }

    public void dbConnection() {
        mysqldb.dbConnection();
    }


    public HashSet<String> loadVisitedURLs() {
        HashSet<String> urls = mysqldb.LoadVisitedURLs();
        return urls;
    }

    public void addProvenanceInformation(String URL, String lastAccess) {
        mysqldb.insertURLandLastAccess(URL, lastAccess);
    }

    public String queryProvenanceInformation(String s_context) {
        String provenanceInformation = mysqldb.getLastAccess(s_context);
        return provenanceInformation;
    }
    
        public void updateProvenanceInformation(String s_URLcontext, String lastAccess) {
        mysqldb.updateLastAccess(s_URLcontext, lastAccess);
    }

    public void addTriple(String suj, String prop, String obj, String url) {
        try {
            mysqldb.addTriple(suj, prop, obj, url);
        } catch (SQLException ex) {
            System.out.println("Error MysqldatabaseManager.addTriple " + ex);
        }
    }
    
    public void removeTriple(String suj, String prop, String obj, String url) {
        mysqldb.removeTriple(suj, prop, obj, url);
    }
    
    public DocumentTriples LoadTriplesfromURL(String url)
    {
        DocumentTriples document = mysqldb.LoadTriplesfromURL(url);
        return document;
    }
}
