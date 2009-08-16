package org.semanticwb.portal.community;


import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.WebPage;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.*;

public class NewsResource extends org.semanticwb.portal.community.base.NewsResourceBase 
{
    private static Logger log = SWBUtils.getLogger(NewsResource.class);
    public NewsResource()
    {
    }

    public NewsResource(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String action = request.getParameter("act");
        action = (action == null ? "view" : action);

        String path = "/swbadmin/jsp/microsite/NewsResource/newsView.jsp";
        if (action.equals("add")) {
            path = "/swbadmin/jsp/microsite/NewsResource/newsAdd.jsp";
        } else if (action.equals("edit")) {
            path = "/swbadmin/jsp/microsite/NewsResource/newsEdit.jsp";
        } else if (action.equals("detail")) {
            path = "/swbadmin/jsp/microsite/NewsResource/newsDetail.jsp";
        }

        RequestDispatcher dis = request.getRequestDispatcher(path);
        try {
            request.setAttribute("paramRequest", paramRequest);
            dis.include(request, response);
        } catch (Exception e) {
            log.error(e);
        }
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String action = request.getParameter("act");
        WebPage page = response.getWebPage();
        Member mem = Member.getMember(response.getUser(), page);
        if (!mem.canView()) {
            return;                                       //si el usuario no pertenece a la red sale;
        }

        //Gather news data
        String title = request.getParameter("new_title");
        if (title==null) title="";
        String image = request.getParameter("new_image");
        if (image==null) image="";
        String author = request.getParameter("new_author");
        if(author==null) author="";
        String abstr = request.getParameter("new_abstr");
        if(abstr==null) abstr="";
        String fulltext = request.getParameter("new_fulltext");
        if(fulltext==null) fulltext="";
        String tags = request.getParameter("new_tags");
        tags = (tags==null?"":tags);

        if (action.equals("add") && mem.canAdd()) {
            //Create news object
            NewsElement rec = NewsElement.createNewsElement(getResourceBase().getWebSite());

            //Set news properties
            rec.setTitle(title);
            rec.setNewsPicture(image);
            rec.setAuthor(author);
            rec.setAbstr(abstr);
            rec.setTags(tags);
            rec.setFullText(fulltext);
            rec.setTags(tags);
            
            //Set render parameters
            try {
                response.setRenderParameter("act", "edit");
                response.setRenderParameter("uri", rec.getURI());
            } catch (Exception e) {
                log.error(e);
                response.setRenderParameter("act", "add");               //regresa a agregar codigo
                response.setRenderParameter("err", "true");              //envia parametro de error
            }
        } else if (action.equals("edit")) {
            //Get event object
            String uri = request.getParameter("uri");
            NewsElement rec = (NewsElement) SemanticObject.createSemanticObject(uri).createGenericInstance();

            if (rec != null && rec.canModify(mem)) {
                //Set new news properties
                //Set event properties
                rec.setTitle(title);
                rec.setNewsPicture(image);
                rec.setAuthor(author);
                rec.setAbstr(abstr);
                rec.setTags(tags);
                rec.setFullText(fulltext);
                rec.setTags(tags);
                rec.setVisibility(Integer.parseInt(request.getParameter("level")));   //hace convercion a int en automatico

                if (page instanceof MicroSiteWebPageUtil) {
                    ((MicroSiteWebPageUtil) page).sendNotification(rec);
                }
            }
        } else if (action.equals("remove")) {
            //Get event object
            String uri = request.getParameter("uri");
            NewsElement rec = (NewsElement) SemanticObject.createSemanticObject(uri).createGenericInstance();

            //Remove event object
            if (rec != null && rec.canModify(mem)) {
                rec.remove();                                       //elimina el registro
            }        
        } else {
            super.processAction(request, response);
        }
    }
}
