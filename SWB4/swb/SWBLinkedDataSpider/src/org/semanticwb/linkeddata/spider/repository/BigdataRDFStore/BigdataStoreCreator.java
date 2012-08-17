/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.linkeddata.spider.repository.BigdataRDFStore;

import com.bigdata.rdf.sail.BigdataSail;
import com.bigdata.rdf.sail.BigdataSailRepository;
import java.io.File;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryException;

/**
 *
 * @author karen.najera
 */
public class BigdataStoreCreator {

    private Properties properties;
    private Repository repository;
    private BigdataSail sail;

    /**
     * Load properties for a bigdata repository
     * 
     */
    private Properties loadProperties(String database) {
        Properties prop = new Properties();
        File journal = new File(database);
        prop.setProperty(BigdataSail.Options.FILE, journal.getAbsolutePath());
        prop.setProperty("com.bigdata.journal.AbstractJournal.bufferMode", "DiskRW");
        prop.setProperty("com.bigdata.btree.writeRetentionQueue.capacity", "4000");
        prop.setProperty("com.bigdata.btree.BTree.branchingFactor", "128");

        prop.setProperty("com.bigdata.rdf.store.AbstractTripleStore.axiomsClass", "com.bigdata.rdf.axioms.NoAxioms");
        prop.setProperty("com.bigdata.rdf.store.AbstractTripleStore.quads", "true");
        prop.setProperty("com.bigdata.rdf.store.AbstractTripleStore.statementIdentifiers", "false");
        prop.setProperty("com.bigdata.rdf.sail.truthMaintenance", "false");
        return prop;
    }

    /**
     * Constructor     
     * 
     */
    public BigdataStoreCreator() {
    }

    public Repository getRepository() {
        return repository;
    }

    /**
     * Creates a bigdata repository  
     * @param database The path for the journal file
     * @return Repository  
     * 
     */
    public void createBigdataStore(String database) {
        properties = loadProperties(database);

        sail = new BigdataSail(properties);
        repository = new BigdataSailRepository(sail);
        System.err.println("Repository created");
    }

    /**
     * initialize bigdata repository  
     * 
     */
    public Repository initializeRepository() {
        try {
            repository.initialize();
            System.err.println("Repository initialized");
        } catch (RepositoryException ex) {
            Logger.getLogger(BigdataStoreCreator.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return repository;
        }
    }

    /**
     * Returns the quantity of statements in the repository
     * @return stmtCount The quantity of statements in the repository
     */
    public long size() {
        long stmtCount = sail.getDatabase().getStatementCount();
        System.err.println("Statement Count: " + stmtCount);
        return stmtCount;
    }
}
