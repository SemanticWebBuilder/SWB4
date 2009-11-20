/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.indexer;

import org.semanticwb.platform.SemanticObject;

/**
 *
 * @author javier.solis
 */
public class SWBIndexParserImp implements SWBIndexParser
{
    SemanticObject m_obj=null;
    
    public SWBIndexParserImp(SemanticObject obj)
    {
        m_obj=obj;
    }

    public boolean canSearchIndex() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getSearchCategories() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getSearchTitle() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getSearchDisplayTitle(String lang) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getSearchTags() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getSearchURL() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getSearchData() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getSearchDisplaySummary(String lang) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getSearchDisplayImage() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

/*  WebPage
    public String getSearchCategories()
    {
        WebPage aux=this;
        StringBuffer ret=new StringBuffer();
        ret.append(aux.getId());
        while(aux.getParent()!=null)
        {
            aux=aux.getParent();
            ret.insert(0,aux.getId()+" ");
        }

        String level=""+getLevel();
        int l=level.length();
        for(int x=0;x<4-l;x++)
        {
            level="0"+level;
        }

        ret.insert(0,level+" ");
        return ret.toString();
    }

    public String getSearchURL() {
        return getUrl();
    }

    public String getSearchData()
    {
        return getSemanticObject().getPropertyIndexData(swb_description);
    }

    public String getSearchDisplaySummary(String lang) {
        return getDisplayDescription(lang);
    }

    public boolean canSearchIndex() {
        return isIndexable();
    }

    public String getSearchTitle()
    {
        return getSemanticObject().getPropertyIndexData(swb_title);
    }

    public String getSearchDisplayTitle(String lang)
    {
        return getDisplayTitle(lang);
    }

    public String getSearchTags() {
        return getTags();
    }

    public String getSearchDisplayImage()
    {
        return null;
    }
 */


    /*  User
     *
    public boolean canSearchIndex() {
        return false;
    }

    public String getSearchCategories() {
        return null;
    }

    public String getSearchTitle() {
        return getFullName();
    }

    public String getSearchDisplayTitle(String lang) {
        return getFullName();
    }

    public String getSearchTags() {
        return null;
    }

    public String getSearchURL() {
        return "{SWBUserURL}"+getId();
    }

    public String getSearchData()
    {
        return null;
    }

    public String getSearchDisplaySummary(String lang)
    {
        return null;
    }

    public String getSearchDisplayImage()
    {
        return "{SWBUserImage}"+getId();
    }
*/

    /* resource
    public boolean canSearchIndex()
    {
        boolean ret=false;
        GenericObject obj=getResourceable();
        if(obj!=null && obj instanceof WebPage && isIndexable()==true)
        {
            ret=true;
        }
        return ret;
    }

    public String getSearchCategories()
    {
        String ret=null;
        GenericObject obj=getResourceable();
        if(obj!=null && obj instanceof WebPage)
        {
            WebPage page=(WebPage)obj;
            ret=page.getSearchCategories();
        }
        return ret;
    }

    public String getSearchTitle() {
        return getSemanticObject().getPropertyIndexData(swb_title);
    }

    public String getSearchDisplayTitle(String lang) {
        return getDisplayTitle(lang);
    }

    public String getSearchTags() {
        return getTags();
    }

    public String getSearchURL() {
        String ret=null;
        GenericObject obj=getResourceable();
        if(obj!=null && obj instanceof WebPage)
        {
            WebPage page=(WebPage)obj;
            ret=page.getUrl();
        }
        return ret;

    }

    public String getSearchData() {
        return null;
    }

    public String getSearchDisplaySummary(String lang) {
        return getDisplayDescription(lang);
    }

    public String getSearchDisplayImage() {
        return null;
    }
    */
}
