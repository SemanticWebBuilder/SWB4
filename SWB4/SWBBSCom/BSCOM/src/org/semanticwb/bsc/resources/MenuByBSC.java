package org.semanticwb.bsc.resources;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.bsc.BSC;
import org.semanticwb.model.WebPage;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;


/**
 * Genera el codigo HTML que representa el menu de la aplicacion en la interface del usuario final
 * @author jose.jimenez
 */
public class MenuByBSC extends GenericResource {
    
    
    /**
     * Realiza operaciones en la bitacora de eventos.
     */
    private static Logger log = SWBUtils.getLogger(MenuByBSC.class);
    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        
        PrintWriter out = response.getWriter();
        //Resource base = this.getResourceBase();
        StringBuilder output = new StringBuilder(512);
        BSC currentBsc = (BSC) paramsRequest.getWebPage().getWebSite();
        WebPage home = currentBsc.getHomePage();
        WebPage currentPage = paramsRequest.getWebPage();
        String lang = paramsRequest.getUser().getLanguage();
        Iterator<WebPage> children = home.listVisibleChilds(lang);
        
        if (children != null && children.hasNext()) {
            output.append("<ul class=\"nav navbar-nav\" role=\"menu\">");
            //TODO: ordenar children por nombre de ordenamiento
            while (children.hasNext()) {
                WebPage option = children.next();
                Iterator<WebPage> suboptions = option.listVisibleChilds(lang);
                boolean hasSuboptions = false;
                
                if (suboptions != null && suboptions.hasNext()) {
                    hasSuboptions = true;
                }
                output.append("  <li");
                if (hasSuboptions) {
                    output.append(" class=\"dropdown\">\n");
                    output.append("    <a class=\"dropdown-toggle\" data-toggle=\"dropdown\" href=\"#\">");
                    output.append(option.getTitle(lang));
                    output.append("<span class=\"caret\"></span></a>\n");
                    output.append("    <ul class=\"dropdown-menu\" role=\"menu\">");
                    while (suboptions.hasNext()) {
                        WebPage suboption = suboptions.next();
                        output.append("      <li><a href=\"");
                        output.append(suboption.getUrl(lang));
                        output.append("\">");
                        output.append(suboption.getTitle(lang));
                        output.append("</a></li>\n");
                    }
                    output.append("    </ul>\n");
                    output.append("  </li>\n");
                } else {
                    if (currentPage.getURI().equals(option.getURI())) {
                        output.append(" class=\"active\"");
                    }
                    output.append("><a href=\"");
                    output.append(option.getUrl(lang));
                    output.append("\">");
                    output.append(option.getTitle(lang));
                    output.append("</a></li>");
                }
//                output.append("");
//                output.append("");
//                output.append("");
//                output.append("");
//                output.append("");
//                output.append("");
//                output.append("");
//                output.append("");
//                output.append("");
            }
            output.append("</ul>");
        }
        out.println(output.toString());
    }
}
