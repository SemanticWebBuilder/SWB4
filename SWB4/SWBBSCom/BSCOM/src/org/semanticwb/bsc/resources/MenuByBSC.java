package org.semanticwb.bsc.resources;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.bsc.BSC;
import org.semanticwb.model.WebPage;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;


/**
 * Genera el c&oacute;digo HTML que representa el men&uacute; de la aplicaci&oacute;n en la interface del usuario final
 * @author jose.jimenez
 */
public class MenuByBSC extends GenericResource {
    
    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        PrintWriter out = response.getWriter();
        StringBuilder output = new StringBuilder(512);
        BSC currentBsc = (BSC) paramsRequest.getWebPage().getWebSite();
        WebPage home = currentBsc.getHomePage();
        WebPage currentPage = paramsRequest.getWebPage();
        String lang = paramsRequest.getUser().getLanguage();
        Iterator<WebPage> children = home.listVisibleChilds(lang);
        MenuItemComparator comp = new MenuItemComparator();
        
        ArrayList<WebPage> childrenList = new ArrayList<>(16);
        while (children.hasNext()) {
            childrenList.add(children.next());
        }
        Collections.sort(childrenList, comp);
        children = childrenList.iterator();
        childrenList = null;
        
        if (children != null && children.hasNext()) {
            output.append("<ul class=\"nav navbar-nav\" role=\"menu\">\n");
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
                    output.append(option.getDisplayTitle(lang));
                    output.append("<span class=\"caret\"></span></a>\n");
                    output.append("    <ul class=\"dropdown-menu\" role=\"menu\">\n");
                    
                    ArrayList<WebPage> suboptionsList = new ArrayList<>(16);
                    while (suboptions.hasNext()) {
                        suboptionsList.add(suboptions.next());
                    }
                    Collections.sort(suboptionsList, comp);
                    suboptions = suboptionsList.iterator();
                    suboptionsList = null;
                    
                    while (suboptions.hasNext()) {
                        WebPage suboption = suboptions.next();
                        output.append("      <li><a href=\"");
                        output.append(suboption.getUrl(lang));
                        output.append("\">");
                        output.append(suboption.getDisplayTitle(lang));
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
                    output.append("</a></li>\n");
                }
            }
            output.append("</ul>\n");
        }
        out.println(output.toString());
    }
}

class MenuItemComparator implements Comparator {

    /**
     * Compara las nombres de ordenamiento de dos p&aacute;ginas web
     * @param prop1 una p&aacute;gina web a ordenar de acuerdo a su nombre de ordenamiento
     * @param prop2 una p&aacute;gina web a ordenar de acuerdo a su nombre de ordenamiento
     * @return un entero negativo, cero o un entero positivo de acuerdo a si el nombre de ordenamiento
     *         de la segunda p&aacute;gina web especificada se puede ordenar antes, en la 
     *         misma ubicacion o despues de aquel de la primera p&aacute;gina web.
     */
    @Override
    public int compare(Object obj1, Object obj2) {
        
        int comparacion;
        String s1 = new String();
        String s2 = new String();
        if (obj1 != null && obj1 instanceof WebPage) {
            WebPage page1 = (WebPage) obj1;
            if (page1.getSortName() != null) {
                s1 = page1.getSortName();
            }
        }
        if (obj2 != null && obj2 instanceof WebPage) {
            WebPage page2 = (WebPage) obj2;
            if (page2.getSortName() != null) {
                s2 = page2.getSortName();
            }
        }
        comparacion = s1.compareTo(s2);
        return comparacion;
    }
}