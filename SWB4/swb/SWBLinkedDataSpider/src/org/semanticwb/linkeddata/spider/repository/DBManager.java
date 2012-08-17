/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.linkeddata.spider.repository;

import org.semanticwb.linkeddata.spider.repository.BigdataRDFStore.BigdataStoreManager;
import org.semanticwb.linkeddata.spider.repository.mysqldatabase.MysqldatabaseManager;

/**
 *
 * @author karen.najera
 */
public class DBManager {

    public BigdataStoreManager bigdatastoremanager;
    public MysqldatabaseManager MySQLStoreManager;
    public String RDFStore;
    public String database;
    

    public DBManager(String database, String RDFStore) {
        this.RDFStore = RDFStore;
        this.database = database;
    }

    public void createDatabase() {
        if (RDFStore.equalsIgnoreCase("MySQL")) {
            MySQLStoreManager = new MysqldatabaseManager(database);
            MySQLStoreManager.dbConnection();
        } else if (RDFStore.equalsIgnoreCase("Bigdata")) {
            bigdatastoremanager = new BigdataStoreManager(database);
            bigdatastoremanager.initializeRepository();
        }
    }
    
   

    
}
