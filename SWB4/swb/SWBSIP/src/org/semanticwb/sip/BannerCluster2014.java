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
package org.semanticwb.sip;

import java.io.*;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.*;
import org.semanticwb.model.*;
import org.semanticwb.portal.api.*;

public class BannerCluster2014 extends GenericAdmResource
{

    class BannerSortComparator implements Comparator
    {

        @Override
        public int compare(Object o1, Object o2)
        {
            Resource r1 = (Resource) o1;
            Resource r2 = (Resource) o2;
            int i1 = Integer.parseInt(r1.getAttribute("index"));
            int i2 = Integer.parseInt(r2.getAttribute("index"));
            if (i2 > i1)
            {
                return -1;
            } else
            {
                return 1;
            }
        }

//    @Override
//    public boolean equals(Object o)   {
//        return false;
//    }
    }
    private static Logger log = SWBUtils.getLogger(BannerCluster2014.class);
    private static final String webWorkPath = SWBPortal.getWebWorkPath();

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out = response.getWriter();
        Resource base = paramRequest.getResourceBase();
        User user = paramRequest.getUser();
        String lang = user.getLanguage();

        StringBuilder b = new StringBuilder();
        int i = 0;

        String width = base.getAttribute("width");
        int w;
        try
        {
            w = Integer.parseInt(width.replaceAll("\\D", ""));
            //w = Integer.parseInt(base.getAttribute("width", "143"));
        } catch (Exception e)
        {
            w = 143;
            width = Integer.toString(w);
        }
        String height = base.getAttribute("height");
        int h;
        try
        {
            h = Integer.parseInt(height.replaceAll("\\D", ""));
            //h = Integer.parseInt(base.getAttribute("height", "208 "));
        } catch (Exception e)
        {
            h = 208;
            height = Integer.toString(h);
        }

        int theight;
        try
        {
            theight = Integer.parseInt(base.getAttribute("theight", "40"));
        } catch (Exception e)
        {
            theight = 40;
        }
        /*<div id="carrusel_2" style="overflow: hidden;">  
         <button id="boton_izq" class="ant">&lt;</button>
  
         <div id="elementos_c2">
         <ul style="width: 1520px; left: 0px;">
    
         <li>
         <img src="img/banner-regalos-nov.jpg" alt="Gracias Sí Regalos No" width="144" height="209">
         <span class="c2_titulo marron">
         <a href="#" title="Primer Informe de Gobierno" target="_blank">Durante diciembre y todo el año gracias, Sí</a></span> <span class="c2_sinopsis">
         <a href="#" title="Primer Informe de Gobierno" target="_blank">Regalos, No</a></span>
         </li>
      
         <li><img src="img/Informe.jpg" alt="1er. Informe de Gobierno" width="144" height="209"> <span class="c2_titulo marron"><a href="http://www.presidencia.gob.mx/informe/" title="Primer Informe de Gobierno" target="_blank">Primer Informe de Gobierno</a></span> <span class="c2_sinopsis">2012 - 2013</span></li>
      
         <li><img src="img/RHacendaria.jpg" alt="Reforma Hacendaria" width="144" height="209"> <span class="c2_titulo marron"><a href="http://www.reformahacendaria.gob.mx/" title="Reforma Hacendaria" target="_blank">Reforma Hacendaria</a></span> <span class="c2_sinopsis"><a href="http://www.reformahacendaria.gob.mx/" target="_blank"></a></span></li>
      
      
         <li><img src="img/REnergetica.jpg" alt="Reforma Energetica" width="144" height="209"> <span class="c2_titulo marron"><a href="http://presidencia.gob.mx/reformaenergetica/" title="Reforma Energética" target="_blank">Reforma Energética</a></span> <span class="c2_sinopsis"><a href="http://presidencia.gob.mx/reformaenergetica/" target="_blank"></a></span></li>
         <li><img src="img/autoridad_nacional.jpg" alt="SEGOB" width="144" height="209"> <span class="c2_titulo marron" style="margin-top: -4em;"><a href="http://www.autoridadnacional.gob.mx/inicio.htm" title="Autoridad Nacional" target="_blank">Portal Autoridad Nacional</a></span> <span class="c2_sinopsis">La no proliferación de armas de destrucción en masa constituye una prioridad en la política exterior de México.</span></li>
         <li><img src="img/esisen.jpg" alt="Escuela de Inteligencia" width="144" height="209"> <span class="c2_titulo marron" style="margin-top: -4em;"><a href="http://www.cisen.gob.mx/Esisen.html" title="ESISEN">ESISEN</a></span> <span class="c2_sinopsis">Escuela de Inteligencia para la Seguridad Nacional</span></li>
         <li><img src="img/visita_virtual.jpg" alt="visita virtual" width="144" height="209"> <span class="c2_titulo marron" style="margin-top: -4em;"> <a href="http://www.cisen.gob.mx/visitaVirtual.html" title="Visita Virtual">Visita Virtual</a> </span> <span class="c2_sinopsis">Recorrido virtual de 360°del CISEN</span></li>
         <li><img src="img/transp_focal.jpg" alt="transparencia focalizada" width="144" height="209"> <span class="c2_titulo marron" style="margin-top: -4em;"><a href="http://www.cisen.gob.mx/tranpFocalizada.html" title="Transparencia Focalizada">Transparencia Focalizada</a></span> <span class="c2_sinopsis">Información identificada por la institución como socialmente útil o focalizada.</span></li>
         <li><img src="img/maagtic-si.jpg" alt="Maagtic-si" width="144" height="209"> <span class="c2_titulo marron" style="margin-top: -4em;"><a href="http://www.cisen.gob.mx/pdfs/maagticsi/MAAGTICSI_SFP.pdf" title="MAAGTIG-SI" target="_blank">MAAGTIC-SI</a></span> <span class="c2_sinopsis">Manual Administrativo de Aplicación General en Materia de Tecnologías de la Información y Comunicaciones y Seguridad de la Información.</span></li>
         <li><img src="img/sitios_interes.jpg" alt="sitios de interés" width="144" height="209"> <span class="c2_titulo marron" style="margin-top: -4em;"><a href="http://www.cisen.gob.mx/sitiosInteres.html" title="Sitios de Interés">Sitios de Interés</a></span> <span class="c2_sinopsis">Sitios de las dependencias <br>
         del Gobierno Federal</span></li>
         </ul>
         </div>
         <button id="boton_der" class="sig">&gt;</button>
         </div>*/
        

//        out.println("<script type=\"text/javascript\">");
//        out.println("<!--");
//        out.println("    dojo.require('dojox.fx');");
//        out.println("    function expande(domId) {");
//        out.println((new StringBuilder()).append(" var a=dojox.fx.wipeTo( {node:domId, duration:200, height:").append(h - 5).append("} );").toString());
//        out.println("     a.play();");
//        out.println("   }");
//        out.println("    function collapse(domId) {");        
//        out.println("      var a=dojox.fx.wipeTo( {node:domId, duration:200, height:" + theight + "} );");
//        out.println("      a.play();");
//        out.println("    }");
//        out.println("-->");
//        out.println("</script>");
//        out.println("<h2 class=\"tituloBloque\">" + base.getDisplayTitle(lang) + " <span class=\"span_tituloBloque\">" + base.getDisplayDescription(lang) + "</span></h2>");
//        out.println("<div class=\"swb-banner-cluster\">");
        
        out.println("<div id=\"carrusel_2\" style=\"overflow: hidden;\">");
        out.println("<button id=\"boton_izq\" class=\"ant\">&lt;</button>");
        out.println("<div id=\"elementos_c2\">");
        out.println("<ul style=\"width: 1520px; left: 0px;\">");
        String cluster = base.getAttribute("cluster", "carrusel");

        TreeSet<Resource> banners = new TreeSet<Resource>(new BannerSortComparator());

        ResourceType rt = ResourceType.ClassMgr.getResourceType("Banner", paramRequest.getWebPage().getWebSite());

        Iterator<ResourceSubType> itResSubTypes = rt.listSubTypes();
        while (itResSubTypes.hasNext())
        {
            ResourceSubType st = itResSubTypes.next();
            if (st.getId().equalsIgnoreCase(cluster))
            {
                Iterator<Resource> itRes = st.listResources();
                while (itRes.hasNext())
                {
                    Resource r = itRes.next();
                    if (r.isActive() && r.isValid() && user.haveAccess(r))
                    {
                        try
                        {
                            Integer.parseInt(r.getAttribute("index"));
                        } catch (NumberFormatException nfe)
                        {
                            System.out.println("error  **** " + nfe);
                            r.setAttribute("index", "0");
                            try
                            {
                                r.updateAttributesToDB();
                            } catch (SWBException swbe)
                            {
                                continue;
                            }
                        }
                        banners.add(r);
                    }
                }
                break;
            }
        }
        

        Iterator<Resource> it = banners.iterator();
        while (it.hasNext())
        {
            Resource r = it.next();
            String title = r.getDisplayTitle(lang) == null ? "" : r.getDisplayTitle(lang);
            String desc = r.getDisplayDescription(lang) == null ? "" : r.getDisplayDescription(lang);
            String url = r.getAttribute("url", "#");
            String img = (new StringBuilder()).append(webWorkPath).append(r.getWorkPath()).append("/").append(r.getAttribute("img")).toString();
            String alt = r.getAttribute("alt", title);
            b.append("<li><img src=\"").append(img).append("\" alt=\"").append(alt).append("\" width=\"").append(width).append("\" height=\"").append(height).append("\"> <span class=\"c2_titulo marron\"><a href=\"").append(url).append("\" title=\"").append(title).append("\" target=\"_blank\">").append(title).append("</a></span> <span class=\"c2_sinopsis\">").append(desc).append("</span></li>");
            /*b.append("<div class=\"swb-banner-cluster-ci\">");
             b.append("  <div class=\"swb-cluster-img\">");
             b.append("    <a href=\"" + url + "\">");
             b.append("      <img src=\"" + img + "\" alt=\"" + alt + "\" width=\"" + width + "\" height=\"" + height + "\" />");
             b.append("    </a>");
             b.append("  </div>");
             b.append("  <div class=\"swb-cluster-despliega\" id=\"r" + base.getId() + "_" + (i++) + "\" onmouseover=\"expande(this.id)\" onmouseout=\"collapse(this.id)\">");
             b.append("    <p class=\"swb-cluster-titulo\"><a href=\"" + url + "\">" + title + "</a></p>");
             b.append("    <p>&nbsp;</p>");
             b.append("    <p class=\"swb-cluster-desc\"><a href=\"" + url + "\">" + desc + "</a></p>");
             b.append("  </div>");
             b.append("</div>");*/

        }

        int wi = (w * i) + (i * 13);
        //out.println("<div class=\"banner-cluster-hldr\" style=\"width:" + wi + "px; height:" + h + "px;\">");
        out.println(b.toString());
        out.println("</ul></div><button id=\"boton_der\" class=\"sig\">&gt;</button></div>");
        //out.println("</div>\n");
        //out.println("</div>\n");
        out.flush();
        out.close();
    }
}
