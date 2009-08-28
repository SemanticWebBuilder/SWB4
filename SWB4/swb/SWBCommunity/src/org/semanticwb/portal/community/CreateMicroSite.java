/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.community;


import java.io.IOException;

import java.util.Iterator;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;

import org.semanticwb.SWBUtils;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 *
 * @author javier.solis
 */
public class CreateMicroSite extends GenericResource
{
    private static Logger log=SWBUtils.getLogger(CreateMicroSite.class);

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String act=request.getParameter("act");
        if(act==null)act="view";
        String path="/swbadmin/jsp/microsite/CreateMicroSite/view.jsp";
        if(act.equals("add"))path="/swbadmin/jsp/microsite/CreateMicroSite/add.jsp";
        if(act.equals("edit"))path="/swbadmin/jsp/microsite/CreateMicroSite/edit.jsp";
        if(act.equals("detail"))path="/swbadmin/jsp/microsite/CreateMicroSite/detail.jsp";

        RequestDispatcher dis=request.getRequestDispatcher(path);
        try
        {
            request.setAttribute("paramRequest", paramRequest);
            dis.include(request, response);
        }catch(Exception e){log.error(e);}
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException 
    {
        WebPage page=response.getWebPage();
        WebSite model = page.getWebSite();
        User user = response.getUser();
        String action=request.getParameter("act");

        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();

        //System.out.println("act:"+action);
        if("add".equals(action))
        {
            String title=request.getParameter("title");
            String id=request.getParameter("id");
            String description=request.getParameter("description");
            String tags=request.getParameter("tags");
            String type=request.getParameter("type");
            boolean bprivate=request.getParameter("private")!=null?true:false;
            boolean bmoderate=request.getParameter("moderate")!=null?true:false;
            String utils[]=request.getParameterValues("hasMicroSiteUtil");
            //System.out.println("title:"+title);
            //System.out.println("description:"+description);
            //System.out.println("utils:"+utils+", size:"+utils.length);

            MicroSite ms = MicroSite.createMicroSite(id,model);
            ms.setParent(page);
            ms.setTitle(title);
            ms.setDescription(description);
            ms.setTags(tags);
            ms.setActive(Boolean.TRUE);

            SemanticObject sotype = ont.getSemanticObject(type);
            MicroSiteType mstype = null;
            if(sotype.getGenericInstance() instanceof MicroSiteType)
            {
               mstype = (MicroSiteType)sotype.getGenericInstance();
               ms.setType(mstype);
            }

            ms.setPrivate(bprivate);
            ms.setModerate(bmoderate);

            if(null!=utils&&utils.length>0)
            {

                for(int i=0;i<utils.length;i++)
                {
                    //System.out.println("i:"+i+", wputil:"+utils[i]);
                    GenericObject sowpu = ont.getGenericObject(utils[i]);

                    //System.out.println("tipo: "+sowpu.getSemanticObject().getSemanticClass());


                    if(sowpu!=null && sowpu instanceof MicroSiteUtil )
                    {
                        MicroSiteUtil msu = (MicroSiteUtil)sowpu;
                        MicroSiteWebPageUtil mswpu = MicroSiteWebPageUtil.createMicroSiteWebPageUtil(ms.getId()+"_"+msu.getId(), model);
                        
                        mswpu.setTitle(msu.getTitle());
                        
                        
                        mswpu.setMicroSite(ms);
                        mswpu.setMicroSiteUtil(msu);

                        mswpu.setParent(ms);
                        mswpu.setActive(Boolean.TRUE);

                        //System.out.println("MicroSiteUtil:"+utils[i]);
                    }
                }
            }

            // Suscribo al creador de la nueva comunidad a esta.

            Member member=Member.createMember(page.getWebSite());
            member.setAccessLevel(Member.LEVEL_OWNER);
            member.setUser(user);
            member.setMicroSite(ms);

        }
        else super.processAction(request, response);
    }


}
