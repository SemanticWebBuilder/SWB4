/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.openoffice;

import java.io.File;
import java.util.List;

/**
 *
 * @author victor.lorenzana
 */
public abstract class OfficeApplication
{
    /**
     * Opens a document in a file path
     * @param file Path for the file
     * @return OfficeDocument opened
     * @throws org.semanticwb.WBException If the document can not be opened
     */
    public abstract OfficeDocument open(File file) throws WBException;
    /**
     * Returns all documents opened
     * @return List of documents
     * @throws org.semanticwb.WBException In case that there was an error
     */
    public abstract List<OfficeDocument> getDocuments() throws WBException;
}
