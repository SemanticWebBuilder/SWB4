/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.linkeddata.spider.repository.BigdataRDFStore;

import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.model.impl.LiteralImpl;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryResult;

/**
 *
 * @author karen.najera
 */
public class BigdataStoreRemove {

    private Repository repository;

    /*    public BigdataStoreRemove(Repository repository) {
    this.repository = repository;
    }
     */
    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    /**
     * Remove a triple from repository
     * @param s_suj URI subject
     * @param s_pred URI predicate
     * @param s_obj URI object
     * @param s_context URI context
     */
    public void removeTriple(String s_suj, String s_pred, String s_obj, String s_context) {
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
                    con.remove(suj, pred, obj, context);
                    System.err.println("Removed" + "<" + context + "> <" + suj + "> <" + pred + "> <" + obj + ">");
                } else {
                    System.err.print("The statement does not exist: " + suj + " " + pred + " " + obj);
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

    /**
     * Remove a triple from repository
     * @param suj URI subject
     * @param pred URI predicate
     * @param obj URI object
     */
    public void removeTriple(Resource suj, URI pred, Value obj) {
        try {
            RepositoryConnection con = repository.getConnection();
            con.setAutoCommit(false);

            try {
                if (con.hasStatement(suj, pred, null, false)) {
                    RepositoryResult<Statement> result = con.getStatements(suj, pred, null, false);
                    while (result.hasNext()) {
                        Statement st = result.next();
                        System.out.println("Removed: " + st.getSubject()+ " ... "+ st.getPredicate()+ " ... "+ st.getObject());             
                    }
                    con.remove(suj, pred, null);
                } else {
                    System.err.print("The statement does not exist: " + suj + " " + pred);
                }
                con.commit();
            } catch (Exception ex) {
                con.rollback();
                System.err.print("rollback ... statement: " + suj + " " + pred);
            } finally {
                con.close();
            }
        } catch (Exception ex) {
            System.err.println("Error removeTriple: " + ex);
        }
    }
}
