package org.semanticwb.model;

import java.util.Iterator;
import org.semanticwb.SWBPlatform;
import org.semanticwb.model.base.*;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;

public class WebSite extends WebSiteBase 
{
    public WebSite(SemanticObject base)
    {
        super(base);
    }
    
    @Override
    public Dns createDns(String uri)
    {
        return (Dns)getSemanticObject().getModel().createGenericObject(uri, Dns.swb_Dns);
    }    

    @Override
    public Dns getDns(String id)
    {
        return (Dns)getSemanticObject().getModel().getGenericObject(id,Dns.swb_Dns);
    }
    
    public Dns getDefaultDns()
    {
        Dns dns=null;
        Iterator<Dns> it=listDnss();
        while(it.hasNext())
        {
            Dns d=it.next();
            if(d.isDefault())
            {
                dns=d;
                break;
            }
        }
        return dns;
    }
    
//    @Override
//    public PortletType createPortletType(String id) {
//        return super.createPortletType(id.toLowerCase());
//    }
//
//    @Override
//    public PortletType getPortletType(String id) {
//        return super.getPortletType(id.toLowerCase());
//    }

//    @Override
//    public WebPage getWebPage(String id)
//    {
//        WebPage ret=null;
//        SemanticClass cls=swb_WebPage;
//        int i=id.indexOf(':');
//        if(i>0)
//        {
//            String clsid=id.substring(0,i);
//            cls=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClassById(clsid);
//            id=id.substring(i+1);
//        }
//        if(cls!=null)
//        {
//            ret=(org.semanticwb.model.WebPage)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,cls),cls);
//        }
//        return ret;
//    }

//    @Override
//    public Portlet getPortlet(String id)
//    {
//        Portlet ret=super.getPortlet(id);
//        //System.out.println("id:"+id+" ret:"+ret);
//        Portlet ret=null;
//        SemanticClass cls=swb_Portlet;
//        int i=id.indexOf(':');
//        if(i>0)
//        {
//            String clsid=id.substring(0,i);
//            cls=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClassById(clsid);
//            id=id.substring(i+1);
//        }
//        if(cls!=null)
//        {
//            ret=(Portlet)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,cls),cls);
//        }
//        return ret;
//    }

    public String getNameSpace()
    {
        return getSemanticObject().getModel().getNameSpace();
    }
    
}
