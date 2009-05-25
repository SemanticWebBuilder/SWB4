package org.semanticwb.portal.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.semanticwb.model.WebPage;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

public class Miselect extends GenericAdmResource {

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String mode = paramRequest.getMode();
        if(mode.equalsIgnoreCase("fillSelect")) {
            doRenderSelect(request,response, paramRequest);
        }else {
            super.processRequest(request, response, paramRequest);
        }
    }

    public void doRenderSelect(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html;charset=iso-8859-1");
        PrintWriter out = response.getWriter();

        String sectId = request.getParameter("ef");
        WebPage wp = paramRequest.getTopic().getWebSite().getWebPage(sectId);
        
        out.println("<select name=\"mun\" id=\"mun\" class=\"combos\">");
        if(wp!=null) {
            String lang = paramRequest.getUser().getLanguage();
            Iterator<WebPage> webpages = wp.listVisibleChilds(lang);
            while(webpages.hasNext()) {
                WebPage sect = webpages.next();
                out.println("<option value=\""+sect.getId()+"\">"+sect.getDisplayName(lang)+"</option>");
            }
        }else {
            out.println("<option value=\"\">Seleccione un Municipio</option>");
        }
        out.println("</select>");
    }


    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html;charset=iso-8859-1");
        PrintWriter out = response.getWriter();

        SWBResourceURL url = paramRequest.getRenderUrl();
        url.setCallMethod(paramRequest.Call_DIRECT).setMode("fillSelect").setParameter("c", Double.toString(Math.random()));
        out.println("<script type=\"text/javascript\">");
        //out.println("dojo.addOnLoad(function(){postHtml('"+url+"','munctnr')});");
        out.println("</script>");

        WebPage wp = paramRequest.getTopic();
        String lang = paramRequest.getUser().getLanguage();
        Iterator<WebPage> webpages = wp.listVisibleChilds(lang);

        out.println("<select name=\"entfed\" id=\"entfed\" onchange=\"postHtml('"+url+"'+'&ef='+this.value,'munctnr')\" class=\"combos\">");
        out.println("<option value=\"0\" selected=\"selected\">Seleccione un Estado</option>");
        while(webpages.hasNext()) {
        WebPage sect = webpages.next();
            out.println("<option value=\""+sect.getId()+"\">"+sect.getDisplayName(lang)+"</option>");
        }
        out.println("</select>");
        out.println("<div id=\"munctnr\">");
        out.println("<select name=\"mun\" id=\"mun\" class=\"combos\"><option value=\"\">Seleccione un Municipio</option></select>");
        out.println("</div>");
    }

}
