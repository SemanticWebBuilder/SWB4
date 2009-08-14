/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.community;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.SWBPlatform;
import org.semanticwb.model.Resource;
import org.semanticwb.model.ResourceSubType;
import org.semanticwb.model.ResourceType;
import org.semanticwb.portal.api.*;

/**
 *
 * @author victor.lorenzana
 */
public class MostrarPanorama extends GenericResource
{

    private static final String NL = "\r\n";
    //String webWorkPath = "/work";

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        ResourceType promo = ResourceType.getResourceType("Promo", paramRequest.getWebPage().getWebSite());
        Iterator<ResourceSubType> subtypes = ResourceSubType.listResourceSubTypeByType(promo, paramRequest.getWebPage().getWebSite());
        HashSet<Promo> promos = new HashSet<Promo>();
        while (subtypes.hasNext())
        {
            ResourceSubType subresource = subtypes.next();
            if (subresource.getId().equals("promohome"))
            {


                Iterator<Resource> resources = Resource.listResourceByResourceSubType(subresource);
                while (resources.hasNext())
                {
                    Resource resource = resources.next();
                    Promo o_promo = new Promo();
                    o_promo.title = resource.getAttribute("title");
                    o_promo.imgfile = resource.getAttribute("imgfile");
                    String webWorkPath = (String) SWBPlatform.getWebWorkPath() + resource.getWorkPath();
                    o_promo.imgfile = webWorkPath + "/" + o_promo.imgfile;
                    o_promo.text = resource.getAttribute("text");
                    o_promo.url = resource.getAttribute("url");
                    if ("1".equalsIgnoreCase(resource.getAttribute("target", "0").trim()))
                    {
                        o_promo.target = "_blank";
                    }
                    promos.add(o_promo);
                }
            }
        }

        if (!promos.isEmpty())
        {
            PrintWriter out = response.getWriter();
            out.write("<div id=\"panorama\">" + NL);
            out.write("<h2 class=\"tituloPrincipal\">Panorama del mes</h2>" + NL);
            int i_parte = 1;
            {
                int i = 1;
                for (Promo o_promo : promos)
                {
                    if (i == 1)
                    {
                        if (i_parte == 1)
                        {
                            out.write("<div id=\"parte" + i_parte + "\">" + NL);
                        }
                        else
                        {
                            out.write("<div id=\"parte" + i_parte + "\" style=\"display:none;\">" + NL);
                        }
                    }

                    out.write("<div class=\"panoramaEnrty\">" + NL);
                    out.write("<p><img border=\"0\" src=\"" + o_promo.imgfile + "\" alt=\"" + o_promo.title + "\" width=\"222\" height=\"149\"></p>" + NL);
                    out.write("<h3 class=\"titulo\">" + o_promo.title + "</h3>" + NL);
                    out.write("<p>" + o_promo.text + "</p>" + NL);
                    String target = "";
                    if (o_promo.target != null)
                    {
                        target = "target=\"" + o_promo.target + "\"";
                    }
                    out.write("<p class=\"vermas\"><a " + target + " href=\"" + o_promo.url + "\">Ver m&aacute;s</a></p>" + NL);
                    out.write("</div>" + NL);
                    out.write("</div>" + NL);
                    i++;
                    if (i == 3)
                    {
                        i_parte++;
                        i = 0;
                        out.write("</div>" + NL);
                    }
                }
            }

            if (i_parte > 1)
            {
                out.write("<div id=\"paginador\">" + NL);
                out.write("<div id=\"backIttems\"><p><a href=\"#\" onClick=\"SWB_previous()\"><img border=\"0\" src=\"images/anteriorBTN.jpg\" alt=\"Anterior\" width=\"19\" height=\"19\"></a> Anterior</p></div>" + NL);
                out.write("<div id=\"noPaginas\">" + NL);
                out.write("<table width=\"100\" border=\"0\" cellspacing=\"5\" cellpadding=\"0\">" + NL);
                out.write("<tr>" + NL);
                for (int i_td = 1; i_td <= i_parte; i_td++)
                {
                    if (i_td == 0)
                    {
                        out.write("<td id=\"page" + i_td + "\" class=\"activePage\"><a onClick=\"SWB_gotoPage(" + (i_td - 1) + ")\" href=\"#\">1</a></td>" + NL);
                    }
                    else
                    {
                        out.write("<td id=\"page" + i_td + "\"><a onClick=\"SWB_gotoPage(" + (i_td - 1) + ")\" href=\"#\">1</a></td>" + NL);
                    }
                }


                out.write("</tr>" + NL);
                out.write("</table>" + NL);
                out.write("</div>" + NL);
                out.write("<div id=\"nextIttems\"><p>Siguiente <a href=\"#\" onClick=\"SWB_next()\"><img border=\"0\" src=\"images/siguienteBTN.jpg\" alt=\"Siguiente\" width=\"19\" height=\"19\"></a></p></div>" + NL);
                out.write("</div>" + NL);
            }
            out.write("</div>" + NL);

            out.write("<script type=\"text/javascript\" >" + NL);
            out.write("var numpages=" + i_parte + ";" + NL);
            out.write("var current=0;" + NL);
            out.write("<!--" + NL);
            out.write("function SWB_next() { " + NL);
            out.write("if(current<(numpages-1))" + NL);
            out.write("{" + NL);
            out.write("SWB_gotoPage(current+1);" + NL);
            out.write("}" + NL);
            out.write("}" + NL);
            out.write("function SWB_previous() { " + NL);
            out.write("if(current>=1)" + NL);
            out.write("{" + NL);
            out.write("SWB_gotoPage(current+-1);" + NL);
            out.write("}" + NL);
            out.write("}");
            out.write("function SWB_gotoPage(page)" + NL);
            out.write("{" + NL);
            out.write("var i=0;" + NL);
            out.write("if(page< numpages && page>-1)" + NL);
            out.write("{" + NL);
            out.write("current=page;" + NL);
            out.write("}" + NL);
            out.write("for(i=0;i<numpages;i++)" + NL);
            out.write("{" + NL);
            out.write("var iddiv='parte'+(i+1);" + NL);
            out.write("var style='hide';" + NL);
            out.write("if(current==i)" + NL);
            out.write("{" + NL);
            out.write("style='show'" + NL);
            out.write("}" + NL);
            out.write("with (document)" + NL);
            out.write("{" + NL);
            out.write("if (getElementById && ((obj=getElementById(iddiv))!=null)) " + NL);
            out.write("{" + NL);
            out.write("v=style;" + NL);
            out.write("if (obj.style)" + NL);
            out.write("{ " + NL);
            out.write("obj=obj.style; v=(v=='show')?'block':(v=='hide')?'none':v; }" + NL);
            out.write("obj.display=v;" + NL);
            out.write("}" + NL);
            out.write("}" + NL);
            out.write("var tdpage='page'+(i+1);" + NL);
            out.write("var obj2=document.getElementById(tdpage);" + NL);
            out.write("if (obj2!=null) " + NL);
            out.write("{" + NL);
            out.write("obj2.setAttribute('class','');" + NL);
            out.write("if (current==i)" + NL);
            out.write("{ " + NL);
            out.write("obj2.setAttribute('class','activePage');" + NL);
            out.write("}" + NL);
            out.write("}" + NL);
            out.write("}" + NL);
            out.write("}" + NL);
            out.write("//-->" + NL);
            out.write("</script>" + NL);
            out.close();
        }
    }
}

class Promo
{

    public String title, imgfile, text, url, target;
}
