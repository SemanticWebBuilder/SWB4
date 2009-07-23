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
                //if(!key.equals("stype"))
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

	@Override
	public boolean isReduceTokenStack() {
		return true;
	}

	@Override
	public String getParents() {
		return Configuration.SPECIAL_BLOCK_TAGS;
	}
}
