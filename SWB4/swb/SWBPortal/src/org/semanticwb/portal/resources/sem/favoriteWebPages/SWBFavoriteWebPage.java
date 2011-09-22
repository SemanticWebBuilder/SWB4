package org.semanticwb.portal.resources.sem.favoriteWebPages;

import java.util.Comparator;


public class SWBFavoriteWebPage extends org.semanticwb.portal.resources.sem.favoriteWebPages.base.SWBFavoriteWebPageBase implements Comparable 
{
    public SWBFavoriteWebPage(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    @Override
    public int compareTo(Object o) {
        if(o instanceof SWBFavoriteWebPage) {
            SWBFavoriteWebPage f = (SWBFavoriteWebPage)o;
            return this.getSubscription().compareTo( f.getSubscription() );
        }else {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}
