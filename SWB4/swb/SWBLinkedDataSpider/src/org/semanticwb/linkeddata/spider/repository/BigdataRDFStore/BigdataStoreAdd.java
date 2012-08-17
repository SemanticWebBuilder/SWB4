/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.linkeddata.spider.repository.BigdataRDFStore;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import org.openrdf.model.Resource;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.model.impl.LiteralImpl;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.rio.RDFFormat;

/**
 *
 * @author karen.najera
 */
public class BigdataStoreAdd {

    BigdataStoreRemove remove = new BigdataStoreRemove();
    Repository repository;
    int repeated;

    /**
     *
     * Constructor
     */
    public BigdataStoreAdd() {
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    /**
     * Returns the quantity of repeated statements in the repository
     * @return repeated The quantity of repeated statements in the repository
     */
    public int getRepeated() {
        return repeated;
    }

    /**
     * Add a triple to repository
     * @param s_suj URI subject
     * @param s_pred URI predicate
     * @param s_obj URI object
     */
    public void addTriple(String s_suj, String s_pred, String s_obj, String s_context) {
        try {
            RepositoryConnection con = repository.getConnection();
            con.setAutoCommit(false);
            Resource suj, context;
            URI pred;
            Value obj;
            CharSequence sec = "http://";

            if (s_suj != null) {
                suj = new URIImpl(s_suj);
            } else {
                suj = null;
            }
            if (s_pred != null) {
                pred = new URIImpl(s_pred);
            } else {
                pred = null;
            }
            if (s_obj != null) {
                if (s_obj.contains(sec)) {
                    obj = new URIImpl(s_obj);
                } else {
                    obj = new LiteralImpl(s_obj);
                }
            } else {
                obj = null;
            }
            if (s_context != null) {
                context = new URIImpl(s_context);
            } else {
                context = null;
            }

            try {
                if (con.hasStatement(suj, pred, obj, false, context)) {
                    repeated++;
                    System.out.println("Repeated " + repeated + ". " + "<" + context + "> <" + suj + "> <" + pred + "> <" + obj + ">");
                } else {
                    con.add(suj, pred, obj, context);
                    con.commit();
                    System.out.println("Loaded      " + "<" + context + "> <" + suj + "> <" + pred + "> <" + obj + ">");
                }

            } catch (Exception ex) {
                con.rollback();
                System.err.print("rollback ... statement: " + suj + " " + pred + " " + obj);
            } finally {
                con.close();
            }
        } catch (Exception ex) {
        }
    }

    /**
     * Add a triple to repository
     * @param s_suj URI subject
     * @param s_pred URI predicate
     * @param s_obj String object (literal)
     */
    /*
    public void addDatawithContext_Literal(String s_suj, String s_pred, String s_obj, String s_context) {
    try {
    RepositoryConnection con = repository.getConnection();
    con.setAutoCommit(false);
    URI context;
    Resource suj = new URIImpl(s_suj);
    URI pred = new URIImpl(s_pred);
    Value obj = new LiteralImpl(s_obj);
    try {
    if (s_context != null) {
    context = new URIImpl(s_context);
    if (con.hasStatement(suj, pred, obj, false, context)) {
    repeated++;
    System.out.println("Repeated " + repeated + ". " + "< " + context + " > < " + suj + " > < " + pred + " > < " + obj + " >");
    } else {
    con.add(suj, pred, obj, context);
    System.out.println("Loaded (URI) " + "<" + context + "> <" + suj + "> <" + pred + "> <" + obj + ">");
    }
    } else {
    if (con.hasStatement(suj, pred, obj, false)) {
    repeated++;
    System.out.println("Repeated " + repeated + ". " + "<" + suj + "> <" + pred + "> <" + obj + ">");
    } else {
    con.add(suj, pred, obj);
    System.out.println("statement cargado");
    }
    }
    con.commit();
    } catch (Exception ex) {
    con.rollback();
    System.err.print("rollback ... statement: " + suj + " " + pred + " " + obj);
    } finally {
    con.close();
    }
    } catch (Exception ex) {
    }
    }
     */
    /**
     * Add triples from document
     * @param path document path
     * @param baseURI namespace
     */
    public void addDataFromFile(String path, String baseURI, String context) {
        try {
            RepositoryConnection con = repository.getConnection();

            con.setAutoCommit(false);
            try {
                Resource URLcontext = new URIImpl(context);
                InputStream is = getClass().getResourceAsStream(path);
                if (is == null && new File(path).exists()) {
                    is = new FileInputStream(path);
                }
                if (is == null) {
                    throw new Exception("Could not locate resource: " + path);
                }
                Reader reader = new InputStreamReader(new BufferedInputStream(is));
                con.add(reader, baseURI, RDFFormat.RDFXML, URLcontext);
                con.commit();
            } catch (Exception ex) {
                con.rollback();
                System.err.println("Error: " + ex);
            } finally {
                con.close();
            }
        } catch (RepositoryException ex) {
            System.err.println("Error: " + ex);
        }
    }
    
        /**
     * Add triples from document
     * @param path document path
     * @param baseURI namespace
     */
    public void addDataFromReader(Reader reader, String baseURI, String context) {
        try {
            RepositoryConnection con = repository.getConnection();

            con.setAutoCommit(false);
            try {
                Resource URLcontext = new URIImpl(context);
                                
                con.add(reader, baseURI, RDFFormat.RDFXML, URLcontext);
                con.commit();
            } catch (Exception ex) {
                con.rollback();
                System.err.println("Error: " + ex);
            } finally {
                con.close();
            }
        } catch (RepositoryException ex) {
            System.err.println("Error: " + ex);
        }
    }
    

    /**     
     * Add provenance information
     * @param s_suj URL context
     * @param s_pred URI predicate
     * @param s_obj String object 
     */
    public void addProvenanceInformation(String s_context, String s_pred, String s_obj) {
        Resource context;
        URI pred;
        Value obj;
        CharSequence sec = "http://";
        try {
            RepositoryConnection con = repository.getConnection();
            con.setAutoCommit(false);

            if (s_context != null) {
                context = new URIImpl(s_context);
            } else {
                context = null;
            }
            if (s_pred != null) {
                pred = new URIImpl(s_pred);
            } else {
                pred = null;
            }
            if (s_obj != null) {
                if (s_obj.contains(sec)) {
                    obj = new URIImpl(s_obj);
                } else {
                    obj = new LiteralImpl(s_obj);
                }
            } else {
                obj = null;
            }
            try {
                con.add(context, pred, obj);
                con.commit();
            } catch (Exception ex) {
                System.err.println("Error: " + ex);
                con.rollback();
            } finally {
                con.close();
            }
        } catch (RepositoryException ex) {
            System.err.println("Error: " + ex);
        }
    }

    /**     
     * Update provenance information
     * @param s_suj URL context
     * @param s_pred URI predicate
     * @param s_obj String object 
     */
    public void updateProvenanceInformation(String s_context, String s_pred, String s_obj) {
        Resource context;
        URI pred;
        Value obj;
        CharSequence sec = "http://";
        try {
            if (s_context != null) {
                context = new URIImpl(s_context);
            } else {
                context = null;
            }
            if (s_pred != null) {
                pred = new URIImpl(s_pred);
            } else {
                pred = null;
            }
            if (s_obj != null) {
                if (s_obj.contains(sec)) {
                    obj = new URIImpl(s_obj);
                } else {
                    obj = new LiteralImpl(s_obj);
                }
            } else {
                obj = null;
            }
            remove.setRepository(repository);
            remove.removeTriple(context, pred, obj);
            
            RepositoryConnection con = repository.getConnection();
            con.setAutoCommit(false);
            try {
                con.add(context, pred, obj);
                System.out.println("Added: " + context+ " ... "+ pred+ " ... "+ obj);    
                con.commit();
            } catch (Exception ex) {
                con.rollback();
            } finally {
                con.close();
            }
        } catch (RepositoryException ex) {
            System.err.println("Error updateProvenanceInformation: " + ex);
        }
    }
}
