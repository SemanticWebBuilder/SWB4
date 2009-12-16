/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.community;

import java.util.HashMap;
import org.semanticwb.SWBPortal;
import org.semanticwb.model.Searchable;
import org.semanticwb.model.WebPage;
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
        return ((DirectoryObject)gen).getWebPage().getUrl() + "?act=detail&uri=" +gen.getSemanticObject().getEncodedURI();
    }

    @Override
    public String getPath(Searchable gen, String lang) {
        String ret = null;

        HashMap arg = new HashMap();
        arg.put("separator", " | ");
        arg.put("links", "false");
        arg.put("language", lang);
        WebPage page=((DirectoryObject)gen).getWebPage();
        ret=page.getPath(arg);

        return ret;
    }

    @Override
    public String getIndexDescription(Searchable gen) {
        return ((DirectoryObject)gen).getDescription();
    }

    @Override
    public String getIndexTags(Searchable gen) {
        return ((DirectoryObject)gen).getTags();
    }

    @Override
    public String getImage(Searchable gen) {
        return SWBPortal.getWebWorkPath() + "/" + 
                gen.getSemanticObject().getWorkPath() + "/" +
                gen.getSemanticObject().getProperty(DirectoryObject.swbcomm_dirPhoto);
    }
}
