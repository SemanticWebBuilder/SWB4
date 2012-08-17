/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.linkeddata.spider.repository.BigdataRDFStore;

import org.semanticwb.linkeddata.spider.repository.Triple;
import org.semanticwb.linkeddata.spider.repository.DocumentTriples;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashSet;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.model.impl.LiteralImpl;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.RepositoryResult;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.ntriples.NTriplesWriter;

/**
 *
 * @author karen.najera
 */
public class BigdataStoreQuery {
Repository repository;

    public void setRepository(Repository repository) {
        this.repository = repository;
    }
    public BigdataStoreQuery() {
    }

    /**
     *
     * Query all data in the repository without context
     */
    public HashSet<Statement> queryRepository(String s_suj, String s_pred, String s_obj) throws Exception {
        Resource suj = null;
        URI pred;
        Value obj;
        HashSet<Statement> stms = new HashSet<Statement>();
        try {
            RepositoryConnection con = repository.getConnection();
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

            RepositoryResult<Statement> stmts = con.getStatements(suj, pred, obj, false);
            while (stmts.hasNext()) {
                Statement stmt = stmts.next();
                stms.add(stmt);
            }
            stmts.close();
            con.close();

        } catch (Exception ex) {
            System.err.println("Error queryRepository: " + ex);
        } finally {
            return stms;
        }
    }

    /**
     *
     * Query all data in the repository
     */
    public DocumentTriples queryRepository(String s_suj, String s_pred, String s_obj, String s_context) throws Exception {
        Resource suj, context = null;
        URI pred;
        Value obj;
        HashSet<Triple> triples = new HashSet<Triple>();
        DocumentTriples document = new DocumentTriples();
        try {
            RepositoryConnection con = repository.getConnection();
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
            int pos = 0;
            String object;
            RepositoryResult<Statement> stmts = con.getStatements(suj, pred, obj, false, context);
            while (stmts.hasNext()) {
                Statement stmt = stmts.next();
                pos = stmt.getObject().toString().indexOf("\"");
                if (pos != -1) {
                    object = stmt.getObject().toString().replace("\"", "");
                } else {
                    object = stmt.getObject().toString();
                }
                Triple triple = new Triple(stmt.getSubject().toString(), stmt.getPredicate().toString(), object, true, false);
                triples.add(triple);
            }
            stmts.close();
            con.close();

        } catch (Exception ex) {
            System.err.println("Error queryRepository: " + ex);
        } finally {
            if (context != null) {
                document.add(context.toString(), triples);
            }
            return document;
        }
    }

    public HashSet<String> queryNamedGraphs() {
        HashSet<String> ngs = new HashSet<String>();
        try {
            RepositoryConnection con = repository.getConnection();
            RepositoryResult<Resource> graphs = con.getContextIDs();

            while (graphs.hasNext()) {
                Resource g = graphs.next();
                if (g instanceof URI) {
                    ngs.add(g.toString());
                }
            }
            System.err.println("graphCount: " + ngs.size());
            con.close();
        } catch (RepositoryException ex) {
        } finally {
            return ngs;
        }
    }

    /*
    public void queryProvenanceInformation(Repository repository, String s_context) {
    try {
    RepositoryConnection con = repository.getConnection();
    Resource URLcontext = new URIImpl(s_context);
    
    RepositoryResult<Statement> result = con.getStatements(URLcontext, null, null, false);
    
    while (result.hasNext()) {
    Statement st = result.next();
    System.out.println(" - Valores de S: " + st.getSubject() + " ... P: " + st.getPredicate() + " ... O: " + st.getObject());
    }
    result.close();
    con.close();
    
    } catch (RepositoryException ex) {
    } finally {
    }
    }
     * 
     */
    public HashSet<String> queryProvenanceInformation(String s_context, String s_pred, String s_obj) {
        HashSet<String> provInf = new HashSet<String>();
        Resource context;
        URI pred;
        Value obj;
        CharSequence sec = "http://";
        try {

            RepositoryConnection con = repository.getConnection();

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

            //System.out.println("connection: " + con.toString());
            RepositoryResult<Statement> result = con.getStatements(context, pred, obj, false);
            while (result.hasNext()) {
                Statement st = result.next();
                //System.out.println("Query: " + st.getSubject()+ " ... "+ st.getPredicate()+ " ... "+ st.getObject());
                provInf.add(st.getObject().toString());
            }
            result.close();
            con.close();
        } catch (RepositoryException ex) {
            System.err.println("Error: " + ex);
        } finally {
            return provInf;
        }
    }

    public InputStream exportToMemoryNT() {
        InputStream is = null;
        try {
            RepositoryConnection con = repository.getConnection();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            NTriplesWriter ntwriter = new NTriplesWriter(out);



            try {
                con.export(ntwriter);
            } catch (RDFHandlerException ex) {
                System.out.println("Error exportToFileNTMemory.RDFHandlerException: " + ex);
            } finally {
                con.close();
            }
            is = new ByteArrayInputStream(out.toByteArray());

        } catch (RepositoryException ex) {
            System.out.println("Error exportToFileNTMemory.RepositoryException: " + ex);
        } finally {
            return is;
        }
    }
}
