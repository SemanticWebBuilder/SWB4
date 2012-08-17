/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.linkeddata.Logic;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.semanticwb.linkeddata.spider.repository.DBManager;
import org.semanticwb.linkeddata.spider.repository.ProvenanceInformation;

/**
 *
 * @author karen.najera
 */
public class LoadProvenanceInformation {

    private DBManager dbcreator;
    private String RDFStore;
    ProvenanceInformation provInf;
    Set<String> provenanceInformation = Collections.synchronizedSet(new HashSet<String>());

    public void setProvInf(ProvenanceInformation provInf) {
        this.provInf = provInf;
    }

    public LoadProvenanceInformation(DBManager dbcreator) {
        this.dbcreator = dbcreator;
        this.RDFStore = dbcreator.RDFStore;
    }

    public void setDbcreator(DBManager dbcreator) {
        this.dbcreator = dbcreator;
    }

    private void loadLastAcess() {
        if (RDFStore.equalsIgnoreCase("Bigdata")) {
            String context = provInf.url;
            String pred = "http://purl.org/dc/terms/date";
            String obj = provInf.lastAcess;
            loadtoDatabase(context, pred, obj);
        } else if (RDFStore.equalsIgnoreCase("mysql")) {
            String URL = provInf.url;;
            String LastAccess = provInf.lastAcess;
            loadtoDatabase(URL, LastAccess);
        }
    }

    private void updateLastAcess() {
        if (RDFStore.equalsIgnoreCase("Bigdata")) {
            String context = provInf.url;
            String pred = "http://purl.org/dc/terms/date";
            String obj = provInf.lastAcess;
            updatetoDatabase(context, pred, obj);
        } else if (RDFStore.equalsIgnoreCase("mysql")) {
            String URL = provInf.url;;
            String LastAccess = provInf.lastAcess;
            updatetoDatabase(URL, LastAccess);
        }
    }

    public void loadProvenanceInformation() {
        loadLastAcess();
    }

    public void updateProvenanceInformation() {
        updateLastAcess();
    }

    public void getProvenanceInformation() {
    }

    public String getLastAcess(String context) {
        String lastAccess = null;
        if (RDFStore.equalsIgnoreCase("Bigdata")) {
            String s_pred = "http://purl.org/dc/terms/date";
            queryDatabase(context, s_pred, null);

            if (provenanceInformation.size() == 1) {
                Iterator iter = provenanceInformation.iterator();
                lastAccess = iter.next().toString();
            } else {
                Iterator iter = provenanceInformation.iterator();
                while (iter.hasNext()) {
                    lastAccess = iter.next().toString();
                }
            }
        } else if (RDFStore.equalsIgnoreCase("mysql")) {
            lastAccess = queryDatabase(context);
        }
        return lastAccess;
    }

    private void loadtoDatabase(String context, String pred, String obj) {
        dbcreator.bigdatastoremanager.addProvenanceInformation(context, pred, obj);
    }

    private void loadtoDatabase(String context, String lastAccess) {
        dbcreator.MySQLStoreManager.addProvenanceInformation(context, lastAccess);
    }

    private void updatetoDatabase(String context, String pred, String obj) {
        dbcreator.bigdatastoremanager.updateProvenanceInformation(context, pred, obj);
    }

    private void updatetoDatabase(String context, String lastAccess) {
        dbcreator.MySQLStoreManager.updateProvenanceInformation(context, lastAccess);
    }

    private void queryDatabase(String s_context, String s_pred, String s_obj) {
        provenanceInformation = dbcreator.bigdatastoremanager.queryProvenanceInformation(s_context, s_pred, s_obj);
    }

    private String queryDatabase(String s_context) {
        String lastAccess = dbcreator.MySQLStoreManager.queryProvenanceInformation(s_context);
        return lastAccess;
    }
}
