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
}
