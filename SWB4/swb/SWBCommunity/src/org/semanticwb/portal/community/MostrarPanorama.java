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
package org.semanticwb.portal.community;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.model.Resource;
import org.semanticwb.model.ResourceSubType;
import org.semanticwb.model.ResourceType;
import org.semanticwb.portal.api.*;

/**
 *
 * @author victor.lorenzana
 */
public class MostrarPanorama extends GenericAdmResource
{

    private static final String NL = "\r\n";
    //String webWorkPath = "/work";

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String webWorkPath = null;
        String type = this.getResourceBase().getAttribute("type", "Promo");
        String subtype = this.getResourceBase().getAttribute("subtype", "promohome");
        ResourceType promo = ResourceType.ClassMgr.getResourceType(type, paramRequest.getWebPage().getWebSite());
        ResourceSubType subresource = ResourceSubType.ClassMgr.getResourceSubType(subtype, paramRequest.getWebPage().getWebSite());

        if (subresource != null && promo != null)
        {
            HashSet<Promo> promos = new HashSet<Promo>();
            Iterator  resources=SWBPortal.getResourceMgr().getResources(promo, subresource, paramRequest.getUser(), paramRequest.getWebPage(), new HashMap(), null);
            while (resources.hasNext())
            {
                SWBResource sWBResource = (SWBResource)resources.next();                
                Resource resource=sWBResource.getResourceBase();
                webWorkPath = (String) SWBPortal.getWebWorkPath() + resource.getWorkPath();
                if (resource.isActive())
                {
                    Promo o_promo = new Promo();
                    o_promo.title = resource.getAttribute("title");
                    o_promo.imgfile = resource.getAttribute("imgfile");
                    o_promo.imgfile = webWorkPath + "/" + o_promo.imgfile;
                    o_promo.text = resource.getAttribute("text");
                    o_promo.url = resource.getAttribute("url");
                    o_promo.created=resource.getCreated();
                    if ("1".equalsIgnoreCase(resource.getAttribute("target", "0").trim()))
                    {
                        o_promo.target = "_blank";
                    }
                    promos.add(o_promo);
                }
            }
            if (!promos.isEmpty())
            {
                int i_partes = promos.size() / 3;
                if (promos.size() % 3 != 0 && promos.size() > 0)
                {
                    i_partes++;
                }
                PrintWriter out = response.getWriter();
                out.write("<div id=\"panorama\">" + NL);
                out.write("<h2 class=\"tituloPrincipal\">Panorama del mes</h2>" + NL);
                for (int i_parte = 0; i_parte < i_partes; i_parte++)
                {
                    if (i_parte == 0)
                    {
                        out.write("<div id=\"parte" + (i_parte + 1) + "\">" + NL);
                    }
                    else
                    {
                        out.write("<div id=\"parte" + (i_parte + 1) + "\" style=\"display:none;\">" + NL);
                    }
                    Promo[] arrayPromos = promos.toArray(new Promo[promos.size()]);
                    Arrays.sort(arrayPromos, new PromoComparator());
                    for (int i_element = 0; i_element < 3; i_element++)
                    {
                        int element = (i_parte * 3) + i_element;
                        if (element < arrayPromos.length)
                        {
                            Promo o_promo = arrayPromos[element];
                            if (o_promo != null)
                            {
                                out.write("<div class=\"panoramaEnrty\">" + NL);
                                if (o_promo.title == null)
                                {
                                    o_promo.title = "";
                                }
                                if (o_promo.text == null)
                                {
                                    o_promo.text = "";
                                }


                                out.write("<p><img border=\"0\" src=\"" + o_promo.imgfile + "\" alt=\"" + o_promo.title + "\" width=\"222\" height=\"149\"></p>" + NL);
                                out.write("<h3 class=\"titulo\">" + o_promo.title + "</h3>" + NL);
                                if (o_promo.text.length() > 300)
                                {
                                    o_promo.text = o_promo.text.substring(0, 297) + "...";
                                }
                                out.write("<div style=\"height:150px;\">" + NL);
                                out.write("<p>" + o_promo.text + "</p>" + NL);
                                out.write("</div>" + NL);
                                if (o_promo.url != null)
                                {
                                    if (o_promo.target != null)
                                    {
                                        String target = "target=\"" + o_promo.target + "\"";
                                        out.write("<div style=\"height:30px;\">" + NL);
                                        out.write("<p class=\"vermas\"><a " + target + " href=\"" + o_promo.url + "\">Ver m&aacute;s</a></p>" + NL);
                                        out.write("</div>" + NL);
                                    }
                                    else
                                    {
                                        out.write("<div style=\"height:30px;\">" + NL);
                                        out.write("<p class=\"vermas\"><a href=\"" + o_promo.url + "\">Ver m&aacute;s</a></p>" + NL);
                                        out.write("</div>" + NL);
                                    }
                                }
                                else
                                {
                                    out.write("<div style=\"height:30px;\">" + NL);
                                    out.write("<p>&nbsp;</p>" + NL);
                                    out.write("</div>" + NL);
                                }
                                out.write("</div>" + NL);
                            }
                        }
                    }
                    out.write("</div>" + NL);
                }
                if (i_partes > 0)
                {
                    webWorkPath = (String) SWBPortal.getWebWorkPath() + this.getResourceBase().getWorkPath();
                    out.write("<div id=\"paginador\">" + NL);
                    String siguiente = this.getResourceBase().getAttribute("siguiente");
                    String anterior = this.getResourceBase().getAttribute("anterior");
                    if (siguiente != null && anterior != null)
                    {
                        anterior = webWorkPath + "/" + anterior;
                        out.write("<div id=\"backIttems\"><p><a href=\"#\" onClick=\"SWB_previous()\"><img border=\"0\" src=\"" + anterior + "\" alt=\"Anterior\" width=\"19\" height=\"19\"></a> Anterior</p></div>" + NL);
                    }
                    else
                    {
                        out.write("<div id=\"backIttems\"><p><a href=\"#\" onClick=\"SWB_previous()\">Anterior</a> </p></div>" + NL);
                    }
                    out.write("<div id=\"noPaginas\">" + NL);
                    out.write("<table width=\"100\" border=\"0\" cellspacing=\"5\" cellpadding=\"0\">" + NL);
                    out.write("<tr>" + NL);
                    for (int i_td = 1; i_td <= i_partes; i_td++)
                    {
                        if (i_td == 1)
                        {
                            out.write("<td id=\"page" + i_td + "\" class=\"activePage\"><a onClick=\"SWB_gotoPage(" + (i_td - 1) + ")\" href=\"#\">" + i_td + "</a></td>" + NL);
                        }
                        else
                        {
                            out.write("<td id=\"page" + i_td + "\"><a onClick=\"SWB_gotoPage(" + (i_td - 1) + ")\" href=\"#\">" + i_td + "</a></td>" + NL);
                        }
                    }


                    out.write("</tr>" + NL);
                    out.write("</table>" + NL);
                    out.write("</div>" + NL);

                    if (siguiente != null && anterior != null)
                    {
                        siguiente = webWorkPath + "/" + siguiente;
                        out.write("<div id=\"nextIttems\"><p>Siguiente <a href=\"#\" onClick=\"SWB_next()\"><img border=\"0\" src=\"" + siguiente + "\" alt=\"Siguiente\" width=\"19\" height=\"19\"></a></p></div>" + NL);
                    }
                    else
                    {
                        out.write("<div id=\"nextIttems\"><p><a href=\"#\" onClick=\"SWB_next()\">Siguiente</a></p></div>" + NL);
                    }
                    out.write("</div>" + NL);
                }
                out.write("</div>" + NL);

                out.write("<script type=\"text/javascript\" >" + NL);
                out.write("var numpages=" + i_partes + ";" + NL);
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
                out.write("SWB_gotoPage(current-1);" + NL);
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
}

class Promo
{

    public String title, imgfile, text, url, target;
    public Date created;
}
class PromoComparator implements Comparator<Promo>
{

    public int compare(Promo o1, Promo o2)
    {
        return o1.created.compareTo(o2.created);
    }

}