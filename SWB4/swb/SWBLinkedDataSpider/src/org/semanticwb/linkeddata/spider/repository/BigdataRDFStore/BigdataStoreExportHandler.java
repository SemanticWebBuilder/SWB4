/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.linkeddata.spider.repository.BigdataRDFStore;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.rio.RDFHandler;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.ntriples.NTriplesWriter;
import org.openrdf.rio.rdfxml.RDFXMLWriter;

/**
 *
 * @author karen.najera
 */
public class BigdataStoreExportHandler {

    private Repository repository;
    // Export all statements in the context to System.out, in RDF/XML format

    public BigdataStoreExportHandler(Repository repository) {
        this.repository = repository;
    }

    public void exportToRDFXML(String filepath) throws FileNotFoundException {
        try {
            OutputStream outputStream = new FileOutputStream(filepath);
            RepositoryConnection con = repository.getConnection();
            RDFHandler rdfxmlWriter = new RDFXMLWriter(outputStream);
            try {
                con.export(rdfxmlWriter);
            } catch (RDFHandlerException ex) {
            } finally {
                con.close();
            }
        } catch (RepositoryException ex) {
        }
    }

    public void exportToNTriples(String filepath) throws FileNotFoundException {
        try {
            OutputStream outputStream = new FileOutputStream(filepath);
            RepositoryConnection con = repository.getConnection();
            NTriplesWriter ntwriter = new NTriplesWriter(outputStream);
            try {
                con.export(ntwriter);
            } catch (RDFHandlerException ex) {
            } finally {
                con.close();
            }
        } catch (RepositoryException ex) {
        }
    }
}
