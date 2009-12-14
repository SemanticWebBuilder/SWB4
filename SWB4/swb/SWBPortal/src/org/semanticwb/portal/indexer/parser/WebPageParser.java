/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.indexer.parser;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Searchable;
import org.semanticwb.model.WebPage;

/**
 *
 * @author javier.solis
 */
public class WebPageParser extends GenericParser
{
    private static Logger log=SWBUtils.getLogger(WebPageParser.class);

    @Override
    public boolean canIndex(Searchable gen)
    {
        boolean ret=gen.isValid();
        if(ret)
        {
            WebPage page=(WebPage)gen;
            if(page.isIndexable())
            {
                ret=true;
            }
        }
        return ret;
    }

    @Override
    public String getSummary(Searchable gen, String lang)
    {
        String ret=null;
        WebPage page=(WebPage)gen;
        if(page!=null)
        {
            try
            {
                ret=SWBUtils.TEXT.parseHTML(page.getDisplayDescription(lang));
            }catch(Exception e){log.error(e);}
        }
        return ret;
    }

    public String getType(Searchable gen)
    {
        return "WebPage";
    }
}
