package org.semanticwb.portal.community;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.http.*;
import org.semanticwb.model.WebPage;
import org.semanticwb.portal.api.*;

public class Videos extends org.semanticwb.portal.community.base.VideosBase 
{

    public Videos()
    {
    }

    public Videos(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

//    @Override
//    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
//    {
//        User user=paramRequest.getUser();
//        WebPage page=paramRequest.getWebPage();
//        PrintWriter out=response.getWriter();
//        out.println("Hello Videos...");
//
//        //Para ver, editar, agragr, eliminar
//        int userLevel=CommUtils.getUerLevel(user,page);
//
//        Iterator<Video> it=Video.listVideosByWebPage(page.getWebSite(),page);
//        while(it.hasNext())
//        {
//            Video video=it.next();
//            int vis=video.getVisibility();
//            //if user puede ver un elemento de comunidad
//                out.println("Titulo:"+video.getTitle());
//                out.println("Descrip:"+video.getDescription());
//        }
//
//    }
//
//    public void add(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
//    {
//        WebPage page=paramRequest.getWebPage();
//
//        Video video=Video.createVideo(page.getWebSite());
//        video.setWebPage(page);
//        video.setVisibility(visibility);
//
//    }

}
