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
package org.semanticwb.model;

import javax.servlet.http.HttpServletRequest;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;


   /**
   * TreeSelect 
   */
public class TreeSelect extends org.semanticwb.model.base.TreeSelectBase 
{
    protected static final String pathImages = SWBPlatform.getContextPath() + "/swbadmin/icons/";
    private static Logger log = SWBUtils.getLogger(TreeSelect.class);

    public TreeSelect(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void process(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String propName) {
        if (prop.getDisplayProperty() == null) {
            return;
        }
        String value = request.getParameter("section" + "_" + obj.getShortURI() + "_" + propName);
        String delPages = request.getParameter("deletePages" + "_" + obj.getShortURI() + "_" + propName);
        if ((value != null) && (value.length() > 0)) {
            try {
                obj.setProperty(prop, value);
            } catch (Exception e) {
                log.error("Can't save the value's property in TreeSelect, " + e);
            }
        }
        if(delPages != null) {
            obj.removeProperty(prop);
        }
    }

    @Override
    public String renderElement(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String propName, String type, String mode, String lang) {
        StringBuffer ret = new StringBuffer(256);
        String style = "";
        StringBuilder params = new StringBuilder("");

        String li = obj.getProperty(prop);
        WebSite site = SWBContext.getWebSite(getModel().getName());
        WebPage home = site.getHomePage();
        String url = SWBPlatform.getContextPath() + "/treeSelect/" + obj.getModel().getModelObject().getId();
        WebPage lip = null;
        String dataProp = obj.getShortURI() + "_" + propName;

        if(li != null && li.length() > 0) {
            ret.append("<input type=\"checkbox\" name=\"deletePages" + "_" + obj.getShortURI() + "_" + propName + "\" value=\"deletePages\">Deseleccionar p&aacute;gina web");
            lip = site.getWebPage(li);
            if(lip != null) {
                ret.append("<br><span style=\"font-style:italic; font-weight:bold; padding-top:7px; padding-left: 20px; padding-bottom: 7px; padding-right: 20px; color:#336699;\">" + lip.getDisplayName() + "</span>");
                ret.append("<a href=\"javascript:getHtml('" + url + "?site=" + home.getWebSiteId() + "&reptp=" + home.getId() + params + "&dataProp=" + dataProp + "&li=" + li + "&lang=" + lang + "&edit=true','tree_'+'" + site.getId() + "_" + obj.getShortURI() + "_" + propName + "')\">Mostrar en el &aacute;rbol</a>");
                if(lip.isDeleted()) {
                    ret.append("<span style=\"font-style:italic; font-weight:bold; padding-top:7px; padding-left: 20px; padding-bottom: 5px; padding-right: 20px; color:#FF0000;\">(En papelera de reciclaje, recup&eacute;rala o selecciona otra)</span>");
                }
            } else {
                //Si la página web almacenada se eliminó del sitio, la propiedad almacenada se eliminará.
                obj.removeProperty(prop);
            }
        }

        ret.append("<div id=\"slave\">");
        ret.append("<div class=\"sitesectiontree\" id=\"tree_" + site.getId() + "_" + obj.getShortURI() + "_" + propName + "\">");

        String styleActual = "";
        if(lip == null) {
            styleActual = "style=\"display:inline\"";
        } else {
            styleActual = "style=\"display:none\"";
        }

        ret.append("<ul class=\"treeres\">");
        ret.append("<li "+ styleActual+">"); //style=\"border-color:#6699FF; max-width:400px; min-width:200px;\" -----

        params.append("&" + home.getId() + "=0");
        ret.append("<img src=\"" + pathImages + "/plus.gif\" alt=\"abrir nodo\"  onclick=\"getHtml('" + url + "?site=" + home.getWebSiteId() + "&reptp=" + home.getId() + params + "&dataProp=" + dataProp + "&li=" + li + "&lang=" + lang + "','tree_'+'" + site.getId() + "_" + obj.getShortURI() + "_" + propName + "')\" />");

        if((li != null && li.length() > 0) && li.equalsIgnoreCase(home.getId())) {
            style = " style=\"font-weight:bold;\"";
        }

        ret.append("<a class=\"treeres\" onclick=\"getHtml('" + url + "?site=" + home.getWebSiteId() + "&reptp=" + home.getId() + params + "&dataProp=" + dataProp + "&li=" + li + "&lang=" + lang + "','tree_'+'" + site.getId() + "_" + obj.getShortURI() + "_" + propName + "')\" " + style + ">");
        if(home.isActive()) { //mostrar icono del home(Activo/Desactivo)
            ret.append("<img src=\"" + pathImages + "/icon_homeac.png\" alt=\"seleccionar inicio\" />");
        } else {
            ret.append("<img src=\"" + pathImages + "/icon_homein.png\" alt=\"seleccionar inicio\" />");
        }

        try { //Agregar el título del nodo actual
            ret.append("<span style=\"padding-left:5px\">" + SWBUtils.TEXT.encode(home.getDisplayTitle(lang), SWBUtils.TEXT.CHARSET_UTF8) + "</span>");
        } catch(Exception e) {
            log.error("Can't save the value's property in TreeSelect, " + e);
        }

        ret.append("</a>");
        ret.append("</li>");
        ret.append("</ul>");
        ret.append("</div>");
        ret.append("</div>");
        return ret.toString();
    }
}
