package org.semanticwb.model;

import org.semanticwb.SWBPlatform;
import org.semanticwb.model.base.*;
import org.semanticwb.platform.SemanticObject;

public class WebPage extends WebPageBase 
{
    private String siteid=null;
     
    public WebPage(SemanticObject base)
    {
        super(base);
    }
    
    public String getWebSiteId()
    {
        if(siteid==null)
        {
            siteid=getWebSite().getId();
        }
        return siteid;
    }     
    
    /**  Regresa el Url de la pagina
     *  Ejemplo: /wb2/jei/home
     * @return String
     */
    public String getRealUrl()
    {
        return SWBPlatform.getContextPath() + SWBPlatform.getEnv("swb/distributor","swb") + "/" + getWebSite().getId() + "/" + getId();
    }    
    
    /**  Regresa el Url del topico
     *  Ejemplo: /wb2/jei/home
     * @return String
     */
    public String getUrl()
    {
//        if(this.getSubjectIdentity()!=null)
//        {
//            String url=this.getSubjectIdentity().getResourceRef();
//            if(url!=null)
//            {
//                if(url.startsWith(WBVirtualHostMgr.PREF_FURL))
//                {
//                    url=url.substring(WBVirtualHostMgr.PREF_FURL.length());
//                    if(url.startsWith("/"))url=url.substring(1);
//                    url=WBUtils.getInstance().getWebPath()+url;
//                }else
//                {
//                    Iterator it=paths.keySet().iterator();
//                    while(it.hasNext())
//                    {
//                        String key=(String)it.next();
//                        int i=url.indexOf(key);
//                        if(i>=0)
//                        {
//                            url=url.substring(0,i)+paths.get(key)+url.substring(i+key.length());
//                        }
//                    }
//                }
//                return url;
//            }
//        }
        return getRealUrl();
    }    
    
    
}
