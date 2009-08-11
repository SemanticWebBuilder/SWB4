/**  
* SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración, 
* colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de 
* información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes 
* fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y 
* procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación 
* para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite. 
* 
* INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’), 
* en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición; 
* aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software, 
* todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización 
* del SemanticWebBuilder 4.0. 
* 
* INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita, 
* siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar 
* de la misma. 
* 
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente 
* dirección electrónica: 
*  http://www.semanticwebbuilder.org
**/ 
 
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
    
//    @Override
//    public Dns createDns(String uri)
//    {
//        return (Dns)getSemanticObject().getModel().createGenericObject(uri, Dns.swb_Dns);
//    }

//    @Override
//    public Dns getDns(String id)
//    {
//        return (Dns)getSemanticObject().getModel().getGenericObject(id,Dns.swb_Dns);
//    }

    public Dns getDns(String dns)
    {
        Dns ret = null;
        if (null != dns)
        {
            //TODO: cachar DNS
            Iterator<SemanticObject> it = getSemanticObject().getModel().listSubjects(Dns.swb_dns, dns);
            if (it.hasNext())
            {
                ret = (Dns) it.next().createGenericInstance();
            }
        }
        return ret;
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
