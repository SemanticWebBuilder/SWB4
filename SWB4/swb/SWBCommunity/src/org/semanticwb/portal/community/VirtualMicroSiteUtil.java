/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.community;

import java.io.IOException;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.SWBPortal;
import org.semanticwb.model.Resource;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.WebPage;
import org.semanticwb.portal.api.*;

/**
 *
 * @author javier.solis
 */
public class VirtualMicroSiteUtil extends GenericResource
{

    @Override
    public void render(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException 
    {
        System.out.println("render");
        WebPage page=paramRequest.getWebPage();
        if(page instanceof MicroSiteWebPageUtil)
        {
            MicroSiteWebPageUtil wpu=(MicroSiteWebPageUtil)page;
            MicroSiteUtil util=wpu.getMicroSiteUtil();
            if(util!=null)
            {
                Iterator<Resource> it=SWBComparator.sortSortableObject(util.listResources());
                while(it.hasNext())
                {
                    Resource res=it.next();
                    System.out.println("res:"+res);
                    if(res.isValid() && paramRequest.getUser().haveAccess(res))
                    {
                        SWBResource swbres=SWBPortal.getResourceMgr().getResource(res);
                        System.out.println("swbres:"+swbres);
                        //SWBParamRequestImp pr=new SWBParamRequestImp(request,res,paramRequest.getWebPage(),paramRequest.getUser());
                        ((SWBParamRequestImp)paramRequest).setResourceBase(res);
                        ((SWBParamRequestImp)paramRequest).setVirtualResource(res);
                        swbres.render(request, response, paramRequest);
                    }
                }
            }
        }
    }


}
