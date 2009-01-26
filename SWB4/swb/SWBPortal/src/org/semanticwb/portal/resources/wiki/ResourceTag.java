/*
 * INFOTEC WebBuilder es una herramienta para el desarrollo de portales de conocimiento, colaboraci�n e integraci�n para Internet,
 * la cual, es una creaci�n original del Fondo de Informaci�n y Documentaci�n para la Industria INFOTEC, misma que se encuentra
 * debidamente registrada ante el Registro P�blico del Derecho de Autor de los Estados Unidos Mexicanos con el
 * No. 03-2002-052312015400-14, para la versi�n 1; No. 03-2003-012112473900 para la versi�n 2, y No. 03-2006-012012004000-01
 * para la versi�n 3, respectivamente.
 *
 * INFOTEC pone a su disposici�n la herramienta INFOTEC WebBuilder a trav�s de su licenciamiento abierto al p�blico (�open source�),
 * en virtud del cual, usted podr� usarlo en las mismas condiciones con que INFOTEC lo ha dise�ado y puesto a su disposici�n;
 * aprender de �l; distribuirlo a terceros; acceder a su c�digo fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los t�rminos y condiciones de la LICENCIA ABIERTA AL P�BLICO que otorga INFOTEC para la utilizaci�n
 * de INFOTEC WebBuilder 3.2.
 *
 * INFOTEC no otorga garant�a sobre INFOTEC WebBuilder, de ninguna especie y naturaleza, ni impl�cita ni expl�cita,
 * siendo usted completamente responsable de la utilizaci�n que le d� y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre INFOTEC WebBuilder, INFOTEC pone a su disposici�n la siguiente
 * direcci�n electr�nica:
 *
 *                                          http://www.webbuilder.org.mx
 */

/*
 * ResourceTag.java
 *
 * Created on 29 de mayo de 2008, 03:25 AM
 *
 */

package org.semanticwb.portal.resources.wiki;


import info.bliki.wiki.filter.ITextConverter;
import info.bliki.wiki.model.Configuration;
import info.bliki.wiki.model.IWikiModel;
import info.bliki.wiki.tags.HTMLTag;
import info.bliki.wiki.tags.util.INoBodyParsingTag;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.PortletSubType;
import org.semanticwb.model.Template;
import org.semanticwb.model.WebPage;
import org.semanticwb.portal.TemplateImp;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBParamRequestImp;
import org.semanticwb.portal.api.SWBResource;
import org.semanticwb.portal.lib.WBResponse;
import org.semanticwb.servlet.SWBHttpServletRequestWrapper;
import org.semanticwb.servlet.internal.DistributorParams;

/**
 * Permite incorporar Un recurso dentro del Wiki
 * @author Javier Solis Gonzalez
 */
public class ResourceTag extends HTMLTag implements INoBodyParsingTag 
{
    private static Logger log = SWBUtils.getLogger(ResourceTag.class);

	public ResourceTag() {
		super("resource");
                addAllowedAttribute("stype");
                addAllowedAttribute("topic");
                addAllowedAttribute("method");
                addAllowedAttribute("language");
	}
        
        HashMap<String, String> filterAttributes(Map<String, String> attributes)
        {
            HashMap map=new HashMap();
            Iterator it=attributes.keySet().iterator();
            while(it.hasNext())
            {
                String key=(String)it.next();
                if(!key.equals("stype"))
                {
                    map.put(key,attributes.get(key));
                    //System.out.println("Attribute:"+key+"-"+attributes.get(key));
                }
            }
            return map;
        }

	@Override
	public void renderHTML(ITextConverter converter, Appendable writer, IWikiModel model) throws IOException 
        {
            SWBParamRequest paramRequest=((WikiModel)model).getParamsRequest();
            HttpServletRequest request=((WikiModel)model).getRequest();
            
            String content = getBodyString();
            //if (content != null && content.length() > 0) 
            {
                    Map<String, String> attributes = getAttributes();
                    String type = attributes.get("type");
                    String stype = attributes.get("stype");
                    //System.out.println("type:"+type);
                    //System.out.println("stype:"+stype);
                    if(type!=null)
                    {
                        try
                        {
                            TemplateImp tpl=(TemplateImp)SWBPortal.getTemplateMgr().getTemplate(paramRequest.getUser(),paramRequest.getTopic());
                            HashMap map=filterAttributes(attributes);
                            PortletSubType pst=tpl.getSubType(type,stype);
                            if(stype!=null)map.put("stype",pst);
                            Iterator it=SWBPortal.getResourceMgr().getResources(pst.getType(),pst,paramRequest.getUser(),paramRequest.getTopic(),map,tpl);
                            
                            if(it.hasNext())
                            {
                                SWBResource wbres=(SWBResource)it.next();
                                if(wbres!=null)
                                {
                                    DistributorParams distparams=new DistributorParams(request);
                                    
                                    String rid=wbres.getResourceBase().getId();
                                    String mdo=null;
                                    String wst=null;
                                    String act=null;
                                    WebPage vtopic=null;
                                    String extParams=null;
                                    if(distparams!=null)
                                    {
                                        vtopic=distparams.getVirtWebPage();
                                        HashMap resp=distparams.getResourceURI(rid);
                                        if(resp!=null && distparams.getResourceTMID(rid).equals(wbres.getResourceBase().getWebSiteId()))
                                        {
                                            mdo=(String)resp.get(distparams.URLP_MODE);
                                            wst=(String)resp.get(distparams.URLP_WINSTATE);
                                            act=(String)resp.get(distparams.URLP_ACTION);
                                        }
                                        extParams=distparams.getNotAccResourceURI(rid);
                                    }
                                    
                                    //out.print(ResourceMgr.getInstance().getResourceTraceMgr().getHtmlTraced(wbres, request, response, user, topic, args));
                                    WBResponse res=new WBResponse();
                                    javax.servlet.http.HttpServletRequest req=null;
                                    if(distparams!=null)
                                    {
                                        req=distparams.getInternalRequest(request,rid);
                                    }else
                                    {
                                        req=new SWBHttpServletRequestWrapper(request);
                                    }                                       
                                    
                                    SWBParamRequestImp resParams = new SWBParamRequestImp(request,wbres.getResourceBase(),paramRequest.getTopic(),paramRequest.getUser());
                                    //resParams.setArguments(args);
                                    resParams.setExtURIParams(extParams);
                                    resParams.setCallMethod(resParams.Call_STRATEGY);
                                    resParams.setArguments(map);
                                    if(act!=null)resParams.setAction(act);
                                    //resParams.setCallMethod(mto);
                                    if(mdo!=null)resParams.setMode(mdo);
                                    if(wst!=null)resParams.setWindowState(wst);                                        
                                    if(vtopic!=null)
                                    {
                                        resParams.setVirtualTopic(vtopic);
                                    }
                                    
                                    SWBPortal.getResourceMgr().getResourceTraceMgr().renderTraced(wbres, req, res, resParams);
                                    writer.append(res.toString());
                                    //System.out.println("out:"+res.toString());
                                }                            
                            }
                        }catch(Exception e){log.error(e);}
                    }
            }
	}

	@Override
	public boolean isReduceTokenStack() {
		return true;
	}

	@Override
	public String getParents() {
		return Configuration.SPECIAL_BLOCK_TAGS;
	}
}
