/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.community;

import org.semanticwb.model.Searchable;
import org.semanticwb.portal.indexer.parser.GenericParser;

/**
 *
 * @author hasdai
 */
public class DirectoryObjectParser extends GenericParser {

    @Override
    public String getType(Searchable gen) {
        return "DirectoryObject";
    }

    @Override
    public String getSummary(Searchable gen, String lang) {
        return ((DirectoryObject)gen).getDescription();
    }

    @Override
    public String getUrl(Searchable gen) {
        return ((DirectoryObject)gen).getWebPage().getUrl() + "?act=detail&uri=" +gen.getURI();
    }


}
