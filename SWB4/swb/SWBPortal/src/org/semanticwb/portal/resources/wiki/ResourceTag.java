/*
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
import org.semanticwb.model.ResourceSubType;
import org.semanticwb.model.ResourceType;
import org.semanticwb.model.WebPage;
import org.semanticwb.portal.TemplateImp;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBParamRequestImp;
import org.semanticwb.portal.api.SWBResource;
import org.semanticwb.portal.lib.SWBResponse;
import org.semanticwb.servlet.SWBHttpServletRequestWrapper;
import org.semanticwb.servlet.internal.DistributorParams;

// TODO: Auto-generated Javadoc
/**
 * Permite incorporar Un recurso dentro del Wiki.
 * 
 * @author Javier Solis Gonzalez
 */
public class ResourceTag extends HTMLTag implements INoBodyParsingTag 
{
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(ResourceTag.class);

	/**
	 * Instantiates a new resource tag.
	 */
	public ResourceTag() {
		super("resource");
                addAllowedAttribute("stype");
                addAllowedAttribute("topic");
                addAllowedAttribute("method");
                addAllowedAttribute("language");
	}
        
        /**
         * Filter attributes.
         * 
         * @param attributes the attributes
         * @return the hash map
         */
        HashMap<String, String> filterAttributes(Map<String, String> attributes)
        {
            HashMap map=new HashMap();
            Iterator it=attributes.keySet().iterator();
            while(it.hasNext())
            {
                String key=(String)it.next();
                //if(!key.equals("stype"))
                {
                    map.put(key,attributes.get(key));
                    //System.out.println("Attribute:"+key+"-"+attributes.get(key));
                }
            }
            return map;
        }

	/* (non-Javadoc)
	 * @see info.bliki.wiki.tags.HTMLTag#renderHTML(info.bliki.wiki.filter.ITextConverter, java.lang.Appendable, info.bliki.wiki.model.IWikiModel)
	 */
	/**
	 * Render html.
	 * 
	 * @param converter the converter
	 * @param writer the writer
	 * @param model the model
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
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
                            TemplateImp tpl=(TemplateImp)SWBPortal.getTemplateMgr().getTemplate(paramRequest.getUser(),paramRequest.getWebPage());
                            HashMap map=filterAttributes(attributes);
                            ResourceType rt=tpl.getWebSite().getResourceType(type);
                            ResourceSubType pst=tpl.getSubType(type,stype);
                            if(stype!=null)map.put("stype",pst);
                            Iterator it=SWBPortal.getResourceMgr().getResources(rt,pst,paramRequest.getUser(),paramRequest.getWebPage(),map,tpl);
                            
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
                                    SWBResponse res=new SWBResponse();
                                    javax.servlet.http.HttpServletRequest req=null;
                                    if(distparams!=null)
                                    {
                                        req=distparams.getInternalRequest(request,rid);
                                    }else
                                    {
                                        req=new SWBHttpServletRequestWrapper(request);
                                    }                                       
                                    
                                    SWBParamRequestImp resParams = new SWBParamRequestImp(request,wbres.getResourceBase(),paramRequest.getWebPage(),paramRequest.getUser());
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

	/* (non-Javadoc)
	 * @see org.htmlcleaner.TagToken#isReduceTokenStack()
	 */
	/**
	 * Checks if is reduce token stack.
	 * 
	 * @return true, if is reduce token stack
	 */
	@Override
	public boolean isReduceTokenStack() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.htmlcleaner.TagNode#getParents()
	 */
	/**
	 * Gets the parents.
	 * 
	 * @return the parents
	 */
	@Override
	public String getParents() {
		return Configuration.SPECIAL_BLOCK_TAGS;
	}
}
