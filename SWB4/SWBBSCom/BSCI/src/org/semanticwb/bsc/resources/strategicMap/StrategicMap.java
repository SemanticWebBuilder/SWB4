
package org.semanticwb.bsc.resources.strategicMap;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.apache.batik.dom.svg.SVGDOMImplementation;
import org.semanticwb.SWBUtils;
import org.semanticwb.bsc.BSC;
import org.semanticwb.bsc.accessory.DifferentiatorGroup;
import org.semanticwb.bsc.accessory.Period;
import org.semanticwb.bsc.element.Objective;
import org.semanticwb.bsc.element.Perspective;
import org.semanticwb.bsc.element.Theme;
import org.semanticwb.model.Resource;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/**
 * Recurso Mapa Estrat&eacute;gico. Permite generar un recurso para SWB que se
 * encarga de visualizar Perspectivas, Temas, Grupo de diferenciadores,
 * Objectivos y Diferenciadores.
 *
 * @author Martha Elvia Jim&eacute;nez Salgado
 * @version %I%, %G%
 * @since 1.0
 */
public class StrategicMap extends GenericResource {

    /**
     * Genera la vista del mapa Estrat&eacute;gico.
     *
     * @param request la petici&oacute;n enviada por el cliente
     * @param response la respuesta generada a la petici&oacute;n recibida
     * @param paramRequest un objeto de la plataforma de SWB con datos
     * adicionales de la petici&oacute;n
     * @throws SWBResourceException si durante la ejecuci&oacute;n no se cuenta
     * con los recursos necesarios para atender la petici&oacute;n
     * @throws IOException si durante la ejecuci&oacute;n ocurre alg&uacute;n
     * problema con la generaci&oacute;n o escritura de la respuesta
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        
        Resource base = getResourceBase();
        WebSite webSite = base.getWebSite();
        PrintWriter out = response.getWriter();
        
        if (webSite instanceof BSC) {
            Period period = getPeriod(request);
            if(period != null) {
                //Resource base = paramRequest.getResourceBase();
                BSC bsc = (BSC)webSite;
                StringBuilder svg = new StringBuilder();
                svg.append("\n<div id=\"emap_"+bsc.getId()+"\">\n");
                try {
                    Document dom = getDom(request);    
                    svg.append(getSvg(dom));
                }catch(XPathException xpe) {
                }
                svg.append("</div>\n");
//                CausalMap map = new CausalMap();
//                CausalArrows arrows = new CausalArrows(map);
//                out.println(arrows.draw(bsc, period, base));
            } else {
                out.println("<p>" + paramRequest.getLocaleString("errorPeriod") + "</p>");
            }
        }
    }

    /**
     * Permite capturar y almacenar la informaci&oacute;n para configurar un
     * mapa estrat&eacute;gico
     *
     * @param request la petici&oacute;n enviada por el cliente
     * @param response la respuesta generada a la petici&oacute;n recibida
     * @param paramRequest un objeto de la plataforma de SWB con datos
     * adicionales de la petici&oacute;n
     * @throws SWBResourceException si durante la ejecuci&oacute;n no se cuenta
     * con los recursos necesarios para atender la petici&oacute;n
     * @throws IOException si durante la ejecuci&oacute;n ocurre alg&uacute;n
     * problema con la generaci&oacute;n o escritura de la respuesta
     */
    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest)
            throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        StringBuffer sb = new StringBuffer();
        String id = paramRequest.getResourceBase().getId();
        SWBResourceURL url = paramRequest.getActionUrl().setAction("update");
        Resource base = paramRequest.getResourceBase();
        WebSite ws = paramRequest.getWebPage().getWebSite();

        String amountPerspective = base.getData("amountPerspective") == null ? ""
                : base.getData("amountPerspective");
        String amountTheme = base.getData("amountTheme") == null ? ""
                : base.getData("amountTheme");
        String amountObjective = base.getData("amountObjective") == null ? ""
                : base.getData("amountObjective");
        String widthHorizontalObjective = base.getData("widthHorizontalObjective") == null ? ""
                : base.getData("widthHorizontalObjective");
        String widthVerticalObjective = base.getData("widthVerticalObjective") == null ? ""
                : base.getData("widthVerticalObjective");
        String amountDifferentiatorGroup = base.getData("amountDifferentiatorGroup") == null ? ""
                : base.getData("amountDifferentiatorGroup");
        String amountDifferentiator = base.getData("amountDifferentiator") == null ? ""
                : base.getData("amountDifferentiator");
        String widthHorizontalDifferentiator = base.getData("widthHorizontalDifferentiator") == null ? ""
                : base.getData("widthHorizontalDifferentiator");
        String colorRelOO = base.getData("colorRelOO") == null ? "" : base.getData("colorRelOO");
        String colorRelOT = base.getData("colorRelOT") == null ? "" : base.getData("colorRelOT");
        String colorRelTO = base.getData("colorRelTO") == null ? "" : base.getData("colorRelTO");
        String colorRelTT = base.getData("colorRelTT") == null ? "" : base.getData("colorRelTT");
        String colorRelPP = base.getData("colorRelPP") == null ? "" : base.getData("colorRelPP");
        String ty_vision = base.getData("ty_vision") == null ? "" : base.getData("ty_vision");
        String bg_vision = base.getData("bg_vision") == null ? "" : base.getData("bg_vision");
        String ty_mision = base.getData("ty_mision") == null ? "" : base.getData("ty_mision");
        String bg_mision = base.getData("bg_mision") == null ? "" : base.getData("bg_mision");

        sb.append("<script type=\"text/javascript\">\n");
        sb.append("  dojo.require('dijit.form.Form');\n");
        sb.append("</script>\n");
        sb.append("\n<script type=\"text/javascript\">\n");
        sb.append("<!--\n");
        sb.append("var swOk=0, optionObj;");
        sb.append("\nfunction jsValida()");
        sb.append("{");
        sb.append("   var ele=document.getElementById(\"frmAdm");
        sb.append(id);
        sb.append("\");");
        sb.append("   var ele1=document.getElementById(\"widthHorizontalObjective\");");
        sb.append("   var ele2=document.getElementById(\"widthVerticalObjective\");");
        sb.append("   var ele3=document.getElementById(\"widthHorizontalDifferentiator\");");

        sb.append("   var ele5=document.getElementById(\"amountDifferentiator\");");
        sb.append("   var ele6=document.getElementById(\"amountObjective\");");
        sb.append("   var ele7=document.getElementById(\"amountDifferentiator\");");
        sb.append("   var ele8=document.getElementById(\"amountPerspective\");");
        sb.append("   var ele9=document.getElementById(\"amountTheme\");");
        sb.append("   var ele10=document.getElementById(\"amountDifferentiatorGroup\");");
        sb.append("   if(!isNumber(ele1)) return false;");
        sb.append("   if(!isNumber(ele2)) return false;");
        sb.append("   if(!isNumber(ele3)) return false;");
        sb.append("   if(!isNumber(ele5)) return false;");
        sb.append("   if(!isNumber(ele6)) return false;");
        sb.append("   if(!isNumber(ele7)) return false;");
        sb.append("   if(!isNumber(ele8)) return false;");
        sb.append("   if(!isNumber(ele9)) return false;");
        sb.append("   if(!isNumber(ele10)) return false;");
        sb.append("   return true;");
        sb.append("}");

        sb.append("\nfunction isNumber(pIn)");
        sb.append("\n{");
        sb.append("\n   pCaracter=pIn.value;");
        sb.append("\n   for (var i=0;i<pCaracter.length;i++)");
        sb.append("\n   {");
        sb.append("\n       var sByte=pCaracter.substring(i,i+1);");
        sb.append("\n       if (sByte<\"0\" || sByte>\"9\")");
        sb.append("\n       {");
        sb.append("\n           pIn.focus();");
        sb.append("\n           alert('" + SWBUtils.TEXT.getLocaleString("locale_swb_util",
                "usrmsg_WBResource_loadIsNumber_msg") + ".');");
        sb.append("\n           return false;");
        sb.append("\n       }");
        sb.append("\n   }");
        sb.append("\n   return true;");
        sb.append("\n}");

        sb.append("\n-->");
        sb.append("\n</script>");

        sb.append("\n<div class=\"swbform\">");
        sb.append("\n<form type=\"dijit.form.Form\" class=\"swbform\" id=\"frmAdm");
        sb.append(id);
        sb.append("\" onsubmit=\"return jsValida()\" name=\"frmAdm");
        sb.append(id);
        sb.append("\" action=\"");
        sb.append(url);
        sb.append("\" method=\"post\" >");
        sb.append("\n<input type=\"hidden\" name=\"suri\" value=\"");
        sb.append(id);
        sb.append("\">");
        sb.append("\n<fieldset>");
        sb.append("\n<legend>");
        sb.append(paramRequest.getLocaleString("configPerspec"));
        sb.append("</legend>");
        sb.append("\n<ul class=\"swbform-ul\">");

        sb.append("\n<li class=\"swbform-li\"><label for=\"amountPerspective\" class=\"swbform-label\">");
        sb.append(paramRequest.getLocaleString("amountText"));
        sb.append(": </label>");
        sb.append("\n<input id=\"amountPerspective\" name=\"amountPerspective\" type=\"text\" ");
        sb.append(" regExp=\"\\d+\" value=\"");
        sb.append(amountPerspective);
        sb.append("\" dojoType=\"dijit.form.ValidationTextBox\" ");
        sb.append("promptMessage=\"Captura cantidad.\" invalidMessage=\"El valor proporcionado debe");
        sb.append(" ser númerico.\">");
        sb.append("\n</li>");
        sb.append("\n</ul>");
        Iterator<Perspective> itPers = Perspective.ClassMgr.listPerspectives(ws);
        while (itPers.hasNext()) {
            Perspective perspective = itPers.next();
            String colorTextPerspective = base.getData("ty_perspective" + perspective.getId())
                    == null ? "" : base.getData("ty_perspective" + perspective.getId());
            String viewPerspective = base.getData("perspective" + id + perspective.getId()) == null ? ""
                    : base.getData("perspective" + id + perspective.getId());

            sb.append("\n<ul class=\"swbform-ul\">");
            sb.append("\n<li class=\"swbform-li\">");
            sb.append(perspective.getTitle());
            sb.append(": </li>");

            String select = viewPerspective.equals("") ? "" : "checked";
            sb.append("\n<li class=\"swbform-li\"><input id=\"perspective");
            sb.append(id);
            sb.append(perspective.getId());
            sb.append("\" name=\"perspective");
            sb.append(id);
            sb.append(perspective.getId());
            sb.append("\" type=\"checkbox\" value=\"");
            sb.append(perspective.getId());
            sb.append("\" ");
            sb.append(" data-dojo-type=\"dijit.form.CheckBox\" ");
            sb.append(select);
            sb.append(" class=\"swbform-label\">");
            sb.append("\n<label for=\"perspective");
            sb.append(id);
            sb.append(perspective.getId());
            sb.append("\">");
            sb.append(paramRequest.getLocaleString("showPerspective"));
            sb.append("\n</label></li>");
            sb.append("\n</ul>");

            String strPers = "ty_perspective" + perspective.getId();
            sb.append("\n<li class=\"swbform-li\"><label for=\"");
            sb.append(strPers);
            sb.append("\" class=\"swbform-label\">");
            sb.append(paramRequest.getLocaleString("letterColor"));
            sb.append(": </label>");
            sb.append("\n<input id=\"");
            sb.append(strPers);
            sb.append("\" name=\"");
            sb.append(strPers);
            sb.append("\" type=\"text\" ");
            sb.append("value=\"");
            sb.append(colorTextPerspective);
            sb.append("\" dojoType=\"dijit.form.ValidationTextBox\">");
            sb.append("\n</li>");
        }

        sb.append("\n</fieldset>");
        sb.append("\n<div id=\"configcol\\/");
        sb.append(id);
        sb.append("\" dojoType=\"dijit.TitlePane\" title=\"");
        sb.append(paramRequest.getLocaleString("configThemes"));
        sb.append("\"  ");
        sb.append("open=\"false\" duration=\"150\" minSize_=\"20\" splitter_=\"true\" region=\"bottom\">");
        sb.append("\n<fieldset>");
        sb.append("\n<legend>");
        sb.append(paramRequest.getLocaleString("configThemes"));
        sb.append("</legend>");
        sb.append("\n<ul class=\"swbform-ul\">");

        sb.append("\n<li class=\"swbform-li\"><label for=\"amountTheme\" class=\"swbform-label\">");
        sb.append(paramRequest.getLocaleString("amountText"));
        sb.append(": </label>");
        sb.append("\n<input id=\"amountTheme\" name=\"amountTheme\" type=\"text\" regExp=\"\\d+\"");
        sb.append(" value=\"");
        sb.append(amountTheme);
        sb.append("\" dojoType=\"dijit.form.ValidationTextBox\" ");
        sb.append("promptMessage=\"Captura cantidad.\" invalidMessage=\"El valor proporcionado debe ");
        sb.append("ser númerico.\">");
        sb.append("\n</li>");
        sb.append("\n</ul>");

        Iterator<Theme> itTheme = Theme.ClassMgr.listThemes(ws);
        while (itTheme.hasNext()) {
            Theme theme = itTheme.next();
            String colorBgTheme = base.getData("bg_theme_" + theme.getId()) == null ? ""
                    : base.getData("bg_theme_" + theme.getId());
            String colorTxtTheme = base.getData("ty_theme_" + theme.getId()) == null ? ""
                    : base.getData("ty_theme_" + theme.getId());
            sb.append("\n<ul class=\"swbform-ul\">");
            sb.append("\n<li class=\"swbform-li\">");
            sb.append(theme.getTitle());
            sb.append(": </li>");

            String strTheme = "ty_theme_" + theme.getId();
            sb.append("\n<li class=\"swbform-li\"><label for=\"");
            sb.append(strTheme);
            sb.append("\" class=\"swbform-label\">");
            sb.append(paramRequest.getLocaleString("letterColor"));
            sb.append(": </label>");
            sb.append("\n<input id=\"");
            sb.append(strTheme);
            sb.append("\" name=\"");
            sb.append(strTheme);
            sb.append("\" type=\"text\" ");
            sb.append("value=\"");
            sb.append(colorTxtTheme);
            sb.append("\" dojoType=\"dijit.form.ValidationTextBox\">");
            sb.append("\n</li>");

            String strTheme1 = "bg_theme_" + theme.getId();
            sb.append("\n<li class=\"swbform-li\"><label for=\"");
            sb.append(strTheme1);
            sb.append("\" class=\"swbform-label\">");
            sb.append(paramRequest.getLocaleString("bgColor"));
            sb.append(": </label>");
            sb.append("\n<input id=\"");
            sb.append(strTheme1);
            sb.append("\" name=\"");
            sb.append(strTheme1);
            sb.append("\" type=\"text\" ");
            sb.append("value=\"");
            sb.append(colorBgTheme);
            sb.append("\" dojoType=\"dijit.form.ValidationTextBox\">");
            sb.append("\n</li>");
            sb.append("\n</ul>");
        }
        sb.append("\n</fieldset>");
        sb.append("\n</div>");

        sb.append("\n<div id=\"configObj\\/");
        sb.append(id);
        sb.append("\" dojoType=\"dijit.TitlePane\" title=\"");
        sb.append(paramRequest.getLocaleString("configObjec"));
        sb.append("\"  ");
        sb.append("open=\"false\" duration=\"150\" minSize_=\"20\" splitter_=\"true\" region=\"bottom\">");
        sb.append("\n<fieldset>");
        sb.append("\n<legend>");
        sb.append(paramRequest.getLocaleString("configObjec"));
        sb.append("</legend>");
        sb.append("\n<ul class=\"swbform-ul\">");

        sb.append("\n<li class=\"swbform-li\"><label for=\"widthHorizontalObjective\" class=\"swbform-label\">");
        sb.append(paramRequest.getLocaleString("heightHoriz"));
        sb.append(": </label>");
        sb.append("\n<input id=\"widthHorizontalObjective\" name=\"widthHorizontalObjective\" type=\"text\" ");
        sb.append("regExp=\"\\d+\" value=\"");
        sb.append(widthHorizontalObjective);
        sb.append("\" ");
        sb.append("dojoType=\"dijit.form.ValidationTextBox\" promptMessage=\"Captura cantidad.\"");
        sb.append(" invalidMessage=\"El valor proporcionado debe ser númerico.\">");
        sb.append("\n</li>");

        sb.append("\n<li class=\"swbform-li\"><label for=\"widthVerticalObjective\" class=\"swbform-label\">");
        sb.append(paramRequest.getLocaleString("heightVert"));
        sb.append(": </label>");
        sb.append("\n<input id=\"widthVerticalObjective\" name=\"widthVerticalObjective\" type=\"text\" ");
        sb.append("regExp=\"\\d+\" value=\"");
        sb.append(widthVerticalObjective);
        sb.append("\"");
        sb.append(" dojoType=\"dijit.form.ValidationTextBox\" promptMessage=\"Captura cantidad.\"");
        sb.append(" invalidMessage=\"El valor proporcionado debe ser númerico.\">");
        sb.append("\n</li>");

        sb.append("\n<li class=\"swbform-li\"><label for=\"amountObjective\" class=\"swbform-label\">");
        sb.append(paramRequest.getLocaleString("amountText"));
        sb.append(": </label>");
        sb.append("\n<input id=\"amountObjective\" name=\"amountObjective\" type=\"text\" regExp=\"\\d+\"");
        sb.append(" value=\"");
        sb.append(amountObjective);
        sb.append("\" dojoType=\"dijit.form.ValidationTextBox\" ");
        sb.append("promptMessage=\"Captura cantidad.\" invalidMessage=\"El valor proporcionado debe ");
        sb.append("ser númerico.\">");
        sb.append("\n</li>");
        sb.append("\n</ul>");

        sb.append("\n</fieldset>");
        sb.append("\n</div>");


        sb.append("\n<div id=\"configGDiffere\\/");
        sb.append(id);
        sb.append("\" dojoType=\"dijit.TitlePane\" title=\"");
        sb.append(paramRequest.getLocaleString("configGDiff"));
        sb.append("\"  ");
        sb.append("open=\"false\" duration=\"150\" minSize_=\"20\" splitter_=\"true\" region=\"bottom\">");
        sb.append("\n<fieldset>");
        sb.append("\n<legend>");
        sb.append(paramRequest.getLocaleString("configGDiff"));
        sb.append("</legend>");
        sb.append("\n<ul class=\"swbform-ul\">");

        sb.append("\n<li class=\"swbform-li\"><label for=\"amountDifferentiatorGroup\" class=\"swbform-label\">");
        sb.append(paramRequest.getLocaleString("amountText"));
        sb.append(": </label>");
        sb.append("\n<input id=\"amountDifferentiatorGroup\" name=\"amountDifferentiatorGroup\" type=\"text\" ");
        sb.append("regExp=\"\\d+\" value=\"");
        sb.append(amountDifferentiatorGroup);
        sb.append("\"");
        sb.append(" dojoType=\"dijit.form.ValidationTextBox\" promptMessage=\"Captura cantidad.\"");
        sb.append(" invalidMessage=\"El valor proporcionado debe ser númerico.\">");
        sb.append("\n</li>");
        sb.append("\n</ul>");
        Iterator<DifferentiatorGroup> itDiffeG = DifferentiatorGroup.ClassMgr.listDifferentiatorGroups(ws);
        while (itDiffeG.hasNext()) {
            DifferentiatorGroup diffG = itDiffeG.next();
            String colorBgTheme = base.getData("bg_diffG_" + diffG.getId()) == null ? ""
                    : base.getData("bg_diffG_" + diffG.getId());
            String colorTxtTheme = base.getData("ty_diffG_" + diffG.getId()) == null ? ""
                    : base.getData("ty_diffG_" + diffG.getId());
            sb.append("\n<ul class=\"swbform-ul\">");
            sb.append("\n<li class=\"swbform-li\">");
            sb.append(diffG.getTitle());
            sb.append(": </li>");

            String strTheme = "ty_diffG_" + diffG.getId();
            sb.append("\n<li class=\"swbform-li\"><label for=\"");
            sb.append(strTheme);
            sb.append("\" class=\"swbform-label\">");
            sb.append(paramRequest.getLocaleString("letterColor"));
            sb.append(": </label>");
            sb.append("\n<input id=\"");
            sb.append(strTheme);
            sb.append("\" name=\"");
            sb.append(strTheme);
            sb.append("\" type=\"text\" ");
            sb.append("value=\"");
            sb.append(colorTxtTheme);
            sb.append("\" dojoType=\"dijit.form.ValidationTextBox\">");
            sb.append("\n</li>");

            String strTheme1 = "bg_diffG_" + diffG.getId();
            sb.append("\n<li class=\"swbform-li\"><label for=\"");
            sb.append(strTheme1);
            sb.append("\" class=\"swbform-label\">");
            sb.append(paramRequest.getLocaleString("bgColor"));
            sb.append(": </label>");
            sb.append("\n<input id=\"");
            sb.append(strTheme1);
            sb.append("\" name=\"");
            sb.append(strTheme1);
            sb.append("\" type=\"text\" ");
            sb.append("value=\"");
            sb.append(colorBgTheme);
            sb.append("\" dojoType=\"dijit.form.ValidationTextBox\">");
            sb.append("\n</li>");
            sb.append("\n</ul>");

        }
        sb.append("\n</fieldset>");
        sb.append("\n</div>");


        sb.append("\n<div id=\"configDife\\/");
        sb.append(id);
        sb.append("\" dojoType=\"dijit.TitlePane\" title=\"");
        sb.append(paramRequest.getLocaleString("configDiff"));
        sb.append("\"  ");
        sb.append("open=\"false\" duration=\"150\" minSize_=\"20\" splitter_=\"true\" region=\"bottom\">");
        sb.append("\n<fieldset>");
        sb.append("\n<legend>");
        sb.append(paramRequest.getLocaleString("configDiff"));
        sb.append("</legend>");
        sb.append("\n<ul class=\"swbform-ul\">");

        sb.append("\n<li class=\"swbform-li\">");
        sb.append("\n<label for=\"widthHorizontalDifferentiator\" class=\"swbform-label\">");
        sb.append(paramRequest.getLocaleString("heightHoriz"));
        sb.append(": </label>");
        sb.append("\n<input id=\"widthHorizontalDifferentiator\" name=\"widthHorizontalDifferentiator\" ");
        sb.append("type=\"text\" regExp=\"\\d+\" ");
        sb.append("value=\"");
        sb.append(widthHorizontalDifferentiator);
        sb.append("\" dojoType=\"dijit.form.ValidationTextBox\" ");
        sb.append("promptMessage=\"Captura cantidad.\" invalidMessage=\"El valor proporcionado ");
        sb.append("debe ser númerico.\">");
        sb.append("\n</li>");

        sb.append("\n<li class=\"swbform-li\">");
        sb.append("\n<label for=\"amountDifferentiator\" class=\"swbform-label\">");
        sb.append(paramRequest.getLocaleString("amountText"));
        sb.append(": </label>");
        sb.append("\n<input id=\"amountDifferentiator\" name=\"amountDifferentiator\" type=\"text\" ");
        sb.append("regExp=\"\\d+\" value=\"");
        sb.append(amountDifferentiator);
        sb.append("\" dojoType=\"dijit.form.ValidationTextBox\" ");
        sb.append("promptMessage=\"Captura cantidad.\" invalidMessage=\"El valor proporcionado debe ");
        sb.append("ser númerico.\">");
        sb.append("\n</li>");
        sb.append("\n</ul>");
        sb.append("\n</fieldset>");
        sb.append("\n</div>");

        sb.append("\n<div id=\"configCausal\\/");
        sb.append(id);
        sb.append("\" dojoType=\"dijit.TitlePane\" title=\"");
        sb.append(paramRequest.getLocaleString("confCausal"));
        sb.append("\"  ");
        sb.append("open=\"false\" duration=\"150\" minSize_=\"20\" splitter_=\"true\" region=\"bottom\">");
        sb.append("\n<fieldset>");
        sb.append("\n<legend>");
        sb.append(paramRequest.getLocaleString("confCausal"));
        sb.append("</legend>");
        sb.append("\n<ul class=\"swbform-ul\">");

        sb.append("\n<li class=\"swbform-li\">");
        sb.append("\n<label for=\"colorRelOO\" class=\"swbform-label\">");
        sb.append(paramRequest.getLocaleString("cssOO"));
        sb.append(": </label>");
        sb.append("\n<input id=\"colorRelOO\" name=\"colorRelOO\" type=\"text\" ");
        sb.append("value=\"");
        sb.append(colorRelOO);
        sb.append( "\" dojoType=\"dijit.form.ValidationTextBox\">");
        sb.append("\n</li>");

        sb.append("\n<li class=\"swbform-li\">");
        sb.append("\n<label for=\"colorRelOT\" class=\"swbform-label\">");
        sb.append(paramRequest.getLocaleString("cssOT"));
        sb.append(": </label>");
        sb.append("\n<input id=\"colorRelOT\" name=\"colorRelOT\" type=\"text\" ");
        sb.append("value=\"");
        sb.append(colorRelOT);
        sb.append("\" dojoType=\"dijit.form.ValidationTextBox\">");
        sb.append("\n</li>");

        sb.append("\n<li class=\"swbform-li\">");
        sb.append("\n<label for=\"colorRelTO\" class=\"swbform-label\">");
        sb.append(paramRequest.getLocaleString("cssTO"));
        sb.append(": </label>");
        sb.append("\n<input id=\"colorRelTO\" name=\"colorRelTO\" type=\"text\" ");
        sb.append("value=\"");
        sb.append(colorRelTO);
        sb.append("\" dojoType=\"dijit.form.ValidationTextBox\">");
        sb.append("\n</li>");

        sb.append("\n<li class=\"swbform-li\">");
        sb.append("\n<label for=\"colorRelTT\" class=\"swbform-label\">");
        sb.append(paramRequest.getLocaleString("cssTT"));
        sb.append(": </label>");
        sb.append("\n<input id=\"colorRelTT\" name=\"colorRelTT\" type=\"text\" ");
        sb.append("value=\"");
        sb.append(colorRelTT);
        sb.append("\" dojoType=\"dijit.form.ValidationTextBox\">");
        sb.append("\n</li>");
        
        sb.append("\n<li class=\"swbform-li\">");
        sb.append("\n<label for=\"colorRelPP\" class=\"swbform-label\">");
        sb.append(paramRequest.getLocaleString("cssPP"));
        sb.append(": </label>");
        sb.append("\n<input id=\"colorRelPP\" name=\"colorRelPP\" type=\"text\" ");
        sb.append("value=\"");
        sb.append(colorRelPP);
        sb.append("\" dojoType=\"dijit.form.ValidationTextBox\">");
        sb.append("\n</li>");        
        
        String select = base.getData("margins") == null ? "" : "checked";
        sb.append("\n<li class=\"swbform-li\"><input id=\"margins");
        sb.append("\" name=\"margins");
        sb.append("\" type=\"checkbox\" value=\"margins\" ");
        sb.append(" data-dojo-type=\"dijit.form.CheckBox\" ");
        sb.append(select);
        sb.append(" class=\"swbform-label\">");
        sb.append("\n<label for=\"margins\">");
        sb.append(paramRequest.getLocaleString("addMargin"));
        sb.append("\n</label></li>");

        sb.append("\n</ul>");

        sb.append("\n</fieldset>");
        sb.append("\n</div>");

        sb.append("\n<div id=\"configHeaders\\/");
        sb.append(id);
        sb.append("\" dojoType=\"dijit.TitlePane\" title=\"");
        sb.append(paramRequest.getLocaleString("configVM"));
        sb.append("\"  ");
        sb.append("open=\"false\" duration=\"150\" minSize_=\"20\" splitter_=\"true\" region=\"bottom\">");
        sb.append("\n<fieldset>");
        sb.append("\n<legend>");
        sb.append(paramRequest.getLocaleString("configVM"));
        sb.append("</legend>");
        sb.append("\n<ul class=\"swbform-ul\">");

        String strVision = "ty_vision";
        sb.append("\n<li class=\"swbform-li\"><label for=\"");
        sb.append(strVision);
        sb.append("\" class=\"swbform-label\">");
        sb.append(paramRequest.getLocaleString("letterColorV"));
        sb.append(": </label>");
        sb.append("\n<input id=\"");
        sb.append(strVision);
        sb.append("\" name=\"");
        sb.append(strVision);
        sb.append("\" type=\"text\" ");
        sb.append("value=\"");
        sb.append(ty_vision);
        sb.append( "\" dojoType=\"dijit.form.ValidationTextBox\">");
        sb.append("\n</li>");

        String strVision1 = "bg_vision";
        sb.append("\n<li class=\"swbform-li\"><label for=\"");
        sb.append(strVision1);
        sb.append("\" class=\"swbform-label\">");
        sb.append(paramRequest.getLocaleString("bgColorV"));
        sb.append(": </label>");
        sb.append("\n<input id=\"");
        sb.append(strVision1);
        sb.append( "\" name=\"");
        sb.append(strVision1);
        sb.append("\" type=\"text\" ");
        sb.append("value=\"");
        sb.append(bg_vision);
        sb.append("\" dojoType=\"dijit.form.ValidationTextBox\">");
        sb.append("\n</li>");

        String strMision = "ty_mision";
        sb.append("\n<li class=\"swbform-li\"><label for=\"");
        sb.append(strMision);
        sb.append("\" class=\"swbform-label\">");
        sb.append(paramRequest.getLocaleString("letterColorM"));
        sb.append(": </label>");
        sb.append("\n<input id=\"");
        sb.append(strMision);
        sb.append("\" name=\"");
        sb.append(strMision);
        sb.append("\" type=\"text\" ");
        sb.append("value=\"");
        sb.append(ty_mision);
        sb.append("\" dojoType=\"dijit.form.ValidationTextBox\">");
        sb.append("\n</li>");

        String strMision1 = "bg_mision";
        sb.append("\n<li class=\"swbform-li\"><label for=\"");
        sb.append(strMision1);
        sb.append("\" class=\"swbform-label\">");
        sb.append(paramRequest.getLocaleString("bgColorM"));
        sb.append(": </label>");
        sb.append("\n<input id=\"");
        sb.append(strMision1);
        sb.append("\" name=\"");
        sb.append(strMision1);
        sb.append("\" type=\"text\" ");
        sb.append("value=\"");
        sb.append(bg_mision);
        sb.append("\" dojoType=\"dijit.form.ValidationTextBox\">");
        sb.append("\n</li>");
        sb.append("\n</ul>");

        sb.append("\n</fieldset>");
        sb.append("\n</div>");

        sb.append("\n<fieldset>");
        sb.append("\n <button dojoType=\"dijit.form.Button\" type=\"submit\" value=\"Submit\" >");
        sb.append(paramRequest.getLocaleString("save"));
        sb.append("</button>&nbsp;");
        sb.append("\n <button dojoType=\"dijit.form.Button\" type=\"reset\" value=\"Reset\">");
        sb.append(paramRequest.getLocaleString("cancel"));
        sb.append("</button>");
        sb.append("\n</fieldset>");

        sb.append("\n</form>");
        sb.append("\n</div>");
        if ((request.getParameter("statusMsg")) != null
                && (!request.getParameter("statusMsg").isEmpty())) {
            sb.append("\n<script type=\"text/javascript\" language=\"JavaScript\">");
            sb.append("\n   alert('");
            sb.append(request.getParameter("statusMsg"));
            sb.append("');");
            sb.append("\n   window.location.href='");
            sb.append(paramRequest.getRenderUrl().setAction("edit"));
            sb.append("';");
            sb.append("\n</script>");
        }
        out.println(sb.toString());
    }

    /**
     * Realiza las operaciones de almacenamiento de la configuraci&oacute;n para
     * la visualizaci&oacute;n del mapa estrat&eacute;gico.
     *
     * @param request la petici&oacute;n enviada por el cliente
     * @param response la respuesta generada a la petici&oacute;n recibida
     * @throws SWBResourceException si durante la ejecuci&oacute;n no se cuenta
     * con los recursos necesarios para atender la petici&oacute;n
     * @throws IOException si durante la ejecuci&oacute;n ocurre alg&uacute;n
     * problema con la generaci&oacute;n o escritura de la respuesta
     */
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException
    {
        String action = response.getAction();
        Resource base = response.getResourceBase();
        WebSite ws = response.getWebPage().getWebSite();
        String id = base.getId();
        if ("update".equals(action)) {
            Enumeration enumAttri = request.getParameterNames();

            Iterator<Perspective> itPers = Perspective.ClassMgr.listPerspectives(ws);
            while (itPers.hasNext()) {
                Perspective perspective = itPers.next();
                String showTitlePerspective = "show_" + perspective.getTitle() + perspective.getId();
                String viewPerspective = "perspective" + id + perspective.getId();
                base.setData(showTitlePerspective, null);
                base.setData(viewPerspective, null);
            }
            base.setData("margins", null);

            while (enumAttri.hasMoreElements()) {
                Object elem = enumAttri.nextElement();
                String data = (String) request.getParameter(elem.toString());
                base.setData(elem.toString(), data);
            }
        }
        response.setRenderParameter("statusMsg", "Actualización exitosa");
        response.setAction(SWBResourceURL.Mode_ADMIN);
    }

    /**
     * Obtiene el periodo seleccionado actual
     *
     * @param request la petici&oacute;n enviada por el cliente
     * @param ws sitio web
     * @return un objeto {@code Periodo} que representa el Periodo actual
     * seleccionado
     */
    private Period getPeriod(HttpServletRequest request)
    {
        String wsid = getResourceBase().getWebSiteId();
        WebSite model = getResourceBase().getWebSite();
        Period period = null;
        
        HttpSession session = request.getSession(true);
        final String pid = (String)session.getAttribute(wsid);
        if(Period.ClassMgr.hasPeriod(pid, model)) {
            period = Period.ClassMgr.getPeriod(pid, model);
        }
        
//        if (request.getSession(true).getAttribute(id) != null) {
//            String pid = (String) request.getSession(true).getAttribute(id);
//            if(Period.ClassMgr.hasPeriod(pid, ws)) {
//                period = Period.ClassMgr.getPeriod(pid, ws);
//            }
//        } 
//        if(period == null) {
//            BSC bsc = (BSC) ws;
//            period = bsc.listValidPeriods().iterator().next();
//        }
        return period;
    }
    
    private String urlBase = null;
    
    @Override
    public void setResourceBase(Resource base) throws SWBResourceException
    {
        super.setResourceBase(base);
        WebPage wp = base.getWebSite().getWebPage(Objective.class.getSimpleName());
        urlBase = wp.getUrl() + "?suri=";
    }
    
    public static final String HEADER_PREFIX = "head_";
    public static final int MARGEN_LEFT = 10; // Especifica el margen izquierdo del rectángulo de una perspectiva
    public static final int MARGEN_RIGHT = 10; // Especifica el margen derecho del rectángulo de una perspectiva
    public static final int MARGEN_TOP = 10; // Especifica el margen superior del rectángulo de una perspectiva
    public static final int MARGEN_BOTTOM = 10; // Especifica el margen inferior del rectángulo de una perspectiva
    
    public static final int HEADER = 150; 
    public static final int HEADER_TITLE = 18; // header title
    
    public static final int BOX_SPACING = 4; // Especifica el espacio entre rectángulos internos de una perspectiva
    public static final int BIND_TOP_SPACING = 2; // Especifica el espacio libre arriba entre rectángulos para pintar las ligas
    public static final int BIND_LEFT_SPACING = 2; // Especifica el espacio libre a la izquieerda entre rectángulos para pintar las ligas
    public static final int BIND_RIGHT_SPACING = 2; // Especifica el espacio libre a la derecha entre rectángulos para pintar las ligas
    public static final String SVG_NS_URI = "http://www.w3.org/2000/svg";
    public static final String XLNK_NS_URI = "http://www.w3.org/1999/xlink";
    
    public Document getDom(HttpServletRequest request) throws XPathExpressionException, NumberFormatException
    {
        Resource base = getResourceBase();
        final BSC scorecard = (BSC)base.getWebSite();
        final int width = Integer.parseInt(base.getAttribute("width", "800"));
        final int height = Integer.parseInt(base.getAttribute("height", "600"));
        final Period period = getPeriod(request);
        Document documentBSC = scorecard.getDom(period);
        
        //root: period, width, height
        Element root = documentBSC.getDocumentElement();        
        root.setAttribute("width", Integer.toString(width));
        root.setAttribute("height", Integer.toString(height));
                
        XPath xPath = XPathFactory.newInstance().newXPath();
        
        //header
        Element header = documentBSC.createElement("header");
        header.setAttribute("id", HEADER_PREFIX+scorecard.getId());
        header.setAttribute("width", Integer.toString(width));
        header.setAttribute("height", Integer.toString(HEADER));
        header.setAttribute("x", "0");
        header.setAttribute("y", "0");
        
        String expression = "/bsc/title";
        Node node = (Node) xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODE);
        header.appendChild(node);
        expression = "/bsc/mission";
        node = (Node) xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODE);
        header.appendChild(node);
        expression = "/bsc/vision";
        node = (Node) xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODE);
        header.appendChild(node);
        expression = "/bsc/logo";
        node = (Node) xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODE);
        header.appendChild(node);
        
        expression = "/bsc/perspective[1]";
        Node p1 = (Node) xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODE);
        root.insertBefore(header, p1);
        
        
        final int px;
        int py;
        final int pw, ph;
        final int perspCount;
        String uri;
        Boolean hiddenTheme;
        
        //para cada perspectiva: width, height, x, y
        expression = "/bsc/perspective";
        NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODESET);
        perspCount = nodeList.getLength();
        pw = width-MARGEN_LEFT-MARGEN_RIGHT;
        ph = (height-HEADER)/perspCount -  MARGEN_TOP - MARGEN_BOTTOM;
        px = MARGEN_LEFT;
        //lista de perspectivas
        for(int j=0; j<perspCount; j++) {
            int h_ = ph;
            Node nodep = nodeList.item(j);
            if(nodep.getNodeType()==Node.ELEMENT_NODE)
            {
                //perspectiva
                Element p = (Element)nodep;
                uri = p.getAttribute("id");
                py = MARGEN_TOP + j*(ph+MARGEN_BOTTOM+MARGEN_TOP);
                
                //diferenciadores de la perspectiva
                expression = "/bsc/perspective[@id='"+uri+"']/diffgroup[1]/diff";
                NodeList nlDiffs = (NodeList)xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODESET);
                final int nlDiffsCount = nlDiffs.getLength();
                final boolean hasDifferentiators = nlDiffsCount>0;
                if(hasDifferentiators) {
                    final int dw = pw/nlDiffsCount;  
                    //int dx = px;
                    for(int k=0; k<nlDiffsCount; k++) {
                        Node noded = nlDiffs.item(k);
                        Element d = (Element)noded;
                        d.setAttribute("width", Integer.toString(dw-BOX_SPACING));
                        d.setAttribute("height", "14");
                        d.setAttribute("x", Integer.toString(px + k*dw));
                        d.setAttribute("y", Integer.toString(py));
                    }
                    py = py + 14 + BOX_SPACING;
                }
                
                //perspectiva
                p.setAttribute("width", Integer.toString(pw));
                if(hasDifferentiators) {
                    h_ = ph - 14 - BOX_SPACING;
                }
                p.setAttribute("height",Integer.toString(h_));
                p.setAttribute("x", Integer.toString(px));
                p.setAttribute("y", Integer.toString(py));
                
                //lista de temas por perspectiva                
                expression = "/bsc/perspective[@id='"+uri+"']/themes/theme";
                NodeList nlThms = (NodeList)xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODESET);
                final int nlThmsCount = nlThms.getLength();
                final  boolean hasThemes = nlThmsCount>0;
                if(hasThemes)
                {
                    final int tw = pw/nlThmsCount;
                    int tx;
                    for(int k=0; k<nlThmsCount; k++)
                    {
                        Node nodet = nlThms.item(k);
                        int ty = py+BIND_TOP_SPACING;
                        if(nodet!=null && nodet.getNodeType()==Node.ELEMENT_NODE) {
                            Element t = (Element)nodet;
                            uri = t.getAttribute("id");
                            hiddenTheme = Boolean.parseBoolean(t.getAttribute("hidden"));
                            t.setAttribute("width", Integer.toString(tw-BOX_SPACING));
                            tx = px + k*tw + BOX_SPACING;
                            t.setAttribute("x", Integer.toString(tx));
                            t.setAttribute("y", Integer.toString(ty));
                            
                            //lista de objetivos
                            expression = "//theme[@id='"+uri+"']/obj";
                            NodeList nlObjs = (NodeList)xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODESET);
                            final int nlObjsCount = nlObjs.getLength();
                            if(nlObjsCount>0)
                            {
                                int hObj;
                                if(hiddenTheme) {
                                    hObj = h_/(nlObjsCount);
                                    t.setAttribute("height","0");
                                }else {
                                    hObj = h_/(nlObjsCount+1);
                                    t.setAttribute("height",Integer.toString(hObj));
                                }
                                for(int l=0; l<nlObjsCount; l++)
                                {
                                    Node nodeo = nlObjs.item(l);
                                    int ox = tx;
                                    int oy = hiddenTheme ? 0 : hObj;
                                    if(nodeo.getNodeType()==Node.ELEMENT_NODE) {
                                        Element o = (Element)nodeo;
                                        uri = o.getAttribute("id");                                
                                        o.setAttribute("width", Integer.toString(tw-BIND_RIGHT_SPACING));
                                        o.setAttribute("height", Integer.toString(hObj-BIND_TOP_SPACING));
                                        o.setAttribute("x", Integer.toString(ox));  
                                        oy += ty + l*hObj + BIND_TOP_SPACING;
                                        o.setAttribute("y", Integer.toString(oy));
                                        o.setAttribute("href",  urlBase+uri);

                                        //relaciones con este objetivo
                                        expression = "//rel[@to='"+uri+"']";
                                        NodeList nlRels = (NodeList)xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODESET);
                                        for(int m=0; m<nlRels.getLength(); m++) {
                                            Node noder = nlRels.item(m);
                                            if(noder.getNodeType()==Node.ELEMENT_NODE) {
                                                Element rel = (Element)noder;
                                                rel.setAttribute("rx", Integer.toString(ox+tw/2));
                                                rel.setAttribute("ry", Integer.toString(oy));
                                            }
                                        }
                                    }
                                }//lista de objetivos por tema
                            }
                        }
                    }//lista de temas
                }
            }
        }//lista de perspectivas
        
        // TODO imagen logo
        // corregir los 0?1:
        // atributo height de los diferenciadores. Esto depende del numero de tema+objetivos
        // atributo href de los objetivos
        // corregir coordenadas x,y
        return documentBSC;
    }
    
    public String getSvg(Document documentBSC) throws XPathExpressionException
    {
        Resource base = getResourceBase();
        String id, expression;
        final int width, height;
        int w, h, w_, h_;
        int x, y, x_, y_;
        StringBuilder SVGjs = new StringBuilder();
        final String emapId = base.getWebSiteId();
        
        
        Element rootBSC = documentBSC.getDocumentElement();
        width = assertValue(rootBSC.getAttribute("width"));
        height = assertValue(rootBSC.getAttribute("height"));
//        SVGjs.append("<svg xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" width=\""+w_+"\" height=\""+h_+"\" viewBox=\"0,0,"+w_+","+h+"\" version=\"1.0\" id=\""+emapId+"\">");
//        SVGjs.append("</svg>");
        
SVGjs.append(" ").append("\n");
SVGjs.append(" ").append("\n");
SVGjs.append(" ").append("\n");
SVGjs.append(" ").append("\n");
SVGjs.append(" ").append("\n");
SVGjs.append(" ").append("\n");
SVGjs.append(" ").append("\n");
SVGjs.append(" ").append("\n");
SVGjs.append(" ").append("\n");
SVGjs.append(" ").append("\n");        
SVGjs.append(" ").append("\n");
SVGjs.append("<script type=\"text/javascript\">");
SVGjs.append(" var SVG_ = '"+SVG_NS_URI+"';").append("\n");
SVGjs.append(" var svg = document.createElementNS(SVG_, 'svg'); ").append("\n");
SVGjs.append(" svg.setAttributeNS(null,'xmlns:xlink', '"+XLNK_NS_URI+"');").append("\n");
SVGjs.append(" svg.setAttributeNS(null,'id','"+emapId+"');").append("\n");
SVGjs.append(" svg.setAttributeNS(null,'width','"+width+"');").append("\n");
SVGjs.append(" svg.setAttributeNS(null,'height','"+height+"');").append("\n");
SVGjs.append(" svg.setAttributeNS(null,'viewBox','0,0,"+width+","+height+"');").append("\n");
SVGjs.append(" svg.setAttributeNS(null,'version','1.0');").append("\n");
        
SVGjs.append(" var g;").append("\n");
SVGjs.append(" var txt;").append("\n");
SVGjs.append(" var rect;").append("\n");

        
XPath xPath = XPathFactory.newInstance().newXPath();
expression = "/bsc/header";
Node node = (Node) xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODE);
if(node!=null && node instanceof Element) {
    NamedNodeMap attrs = node.getAttributes();
    id = attrs.getNamedItem("id").getNodeValue();
    w = assertValue(attrs.getNamedItem("width").getNodeValue());
    h = assertValue(attrs.getNamedItem("height").getNodeValue());
    x = assertValue(attrs.getNamedItem("x").getNodeValue());
    y = assertValue(attrs.getNamedItem("y").getNodeValue());
    
    SVGjs.append(" g = document.createElementNS(SVG_,'g');").append("\n");
    SVGjs.append(" g.setAttributeNS(null,'id','"+id+"');").append("\n");
    SVGjs.append(" svg.appendChild(g);").append("\n");
    
    // título mapa
    x_ = x;
    y_ = y + HEADER_TITLE;
    w_ = w;
    h_ = HEADER_TITLE;
    
    expression = "/bsc/header/title";
    node = (Node) xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODE);
    if(node!=null && node.getNodeType()==Node.ELEMENT_NODE) {
        SVGjs.append(" txt = document.createElementNS(SVG_,'text');").append("\n");
        SVGjs.append(" txt.setAttributeNS(null,'id','"+id+"_title"+"');").append("\n");
        SVGjs.append(" txt.setAttributeNS(null,'x','"+x_+"');").append("\n");
        SVGjs.append(" txt.setAttributeNS(null,'y','"+y_+"');").append("\n");
        SVGjs.append(" txt.setAttributeNS(null,'font-size','14');").append("\n");
        SVGjs.append(" txt.setAttributeNS(null,'font-family','Verdana');").append("\n");
        SVGjs.append(" txt.textContent='"+node.getFirstChild().getNodeValue()+"';").append("\n");
        SVGjs.append(" g.appendChild(txt);").append("\n");
        SVGjs.append(" fixParagraphAtBounding(txt,"+w_+","+h_+","+x_+","+y_+");").append("\n");
        SVGjs.append(" rect = getBBoxAsRectElement(txt);").append("\n");
        SVGjs.append(" framingRect(rect,"+w_+","+h_+","+x_+","+y_+");").append("\n");
        SVGjs.append(" g.appendChild(rect);").append("\n");
    }
    
    // misión
    y_ = y + HEADER_TITLE + 12;
    w_ = w/3;
    h_ = h - HEADER_TITLE;
    expression = "/bsc/header/mission";
    node = (Node) xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODE);
    if(node!=null && node.getNodeType()==Node.ELEMENT_NODE) {
        SVGjs.append(" txt = document.createElementNS(SVG_,'text');").append("\n");
        SVGjs.append(" txt.setAttributeNS(null,'id','"+id+"_mt"+"');").append("\n");
        SVGjs.append(" txt.setAttributeNS(null,'x','"+x_+"');").append("\n");
        SVGjs.append(" txt.setAttributeNS(null,'y','"+y_+"');").append("\n");
        SVGjs.append(" txt.setAttributeNS(null,'font-size','12');").append("\n");
        SVGjs.append(" txt.setAttributeNS(null,'font-family','Verdana');").append("\n");
        SVGjs.append(" txt.textContent='"+node.getFirstChild().getNodeValue()+"';").append("\n");
        SVGjs.append(" g.appendChild(txt);").append("\n");
        SVGjs.append(" fixParagraphAtBounding(txt,"+w_+","+h_+","+x_+","+y_+");").append("\n");
        SVGjs.append(" rect = getBBoxAsRectElement(txt);").append("\n");
        SVGjs.append(" framingRect(rect,"+w_+","+h_+","+x_+","+y_+");").append("\n");
        SVGjs.append(" g.appendChild(rect);").append("\n");
    }
    
    // logo
    x_ = x_ + w_;
    expression = "/bsc/header/logo";
    node = (Node) xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODE);
    if(node!=null && node.getNodeType()==Node.ELEMENT_NODE) {
        SVGjs.append(" rect = document.createElementNS(SVG_,'rect');").append("\n");
        SVGjs.append(" rect.setAttributeNS(null,'id','"+id+"_lg"+"');").append("\n");
        SVGjs.append(" rect.setAttributeNS(null,'width','"+w_+"');").append("\n");
        SVGjs.append(" rect.setAttributeNS(null,'height','"+h_+"');").append("\n");
        SVGjs.append(" rect.setAttributeNS(null,'x','"+x_+"');").append("\n");
        SVGjs.append(" rect.setAttributeNS(null,'y','"+y_+"');").append("\n");
        SVGjs.append(" rect.setAttributeNS(null,'fill','none');").append("\n");
        SVGjs.append(" rect.setAttributeNS(null,'stroke','black');").append("\n");
        SVGjs.append(" rect.setAttributeNS(null, 'stroke-width',1);").append("\n");
        SVGjs.append(" g.appendChild(rect);").append("\n");
    }
    
    // vision
    x_ = x_ + w_;
    expression = "/bsc/header/vision";
    node = (Node) xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODE);
    if(node!=null && node.getNodeType()==Node.ELEMENT_NODE) {
        SVGjs.append(" txt = document.createElementNS(SVG_,'text');").append("\n");
        SVGjs.append(" txt.setAttributeNS(null,'id','"+id+"_vs"+"');").append("\n");
        SVGjs.append(" txt.setAttributeNS(null,'x','"+x_+"');").append("\n");
        SVGjs.append(" txt.setAttributeNS(null,'y','"+y_+"');").append("\n");
        SVGjs.append(" txt.setAttributeNS(null,'font-size','12');").append("\n");
        SVGjs.append(" txt.setAttributeNS(null,'font-family','Verdana');").append("\n");
        SVGjs.append(" txt.textContent='"+node.getFirstChild().getNodeValue()+"';").append("\n");
        SVGjs.append(" g.appendChild(txt);").append("\n");
        SVGjs.append(" fixParagraphAtBounding(txt,"+w_+","+h_+","+x_+","+y_+");").append("\n");
        SVGjs.append(" rect = getBBoxAsRectElement(txt);").append("\n");
        SVGjs.append(" framingRect(rect,"+w_+","+h_+","+x_+","+y_+");").append("\n");
        SVGjs.append(" g.appendChild(rect);").append("\n");
    }
}

//lista de perspectivas
expression = "/bsc/perspective";
NodeList nlPersp = (NodeList) xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODESET);
for(int j=0; j<nlPersp.getLength(); j++) {
    node = nlPersp.item(j);
    if(node!=null && node.getNodeType()==Node.ELEMENT_NODE) {
        NamedNodeMap attrs = node.getAttributes();
        String pid = attrs.getNamedItem("id").getNodeValue();
        int pw = assertValue(attrs.getNamedItem("width").getNodeValue());
        int ph = assertValue(attrs.getNamedItem("height").getNodeValue());
        int px = assertValue(attrs.getNamedItem("x").getNodeValue());
        int py = assertValue(attrs.getNamedItem("y").getNodeValue());
        
        // título de la perspectiva
        expression = "/bsc/perspective[@id='"+pid+"']/title";
        String title = (String)xPath.compile(expression).evaluate(documentBSC, XPathConstants.STRING);
        // prefijo de la perspectiva
        expression = "/bsc/perspective[@id='"+pid+"']/prefix";
        String prefix = (String)xPath.compile(expression).evaluate(documentBSC, XPathConstants.STRING);
        
SVGjs.append(" g = document.createElementNS(SVG_,'g');").append("\n");
SVGjs.append(" g.setAttributeNS(null,'id','"+pid+"');").append("\n");
SVGjs.append(" svg.appendChild(g);").append("\n");

SVGjs.append(" rect = document.createElementNS(SVG_,'rect');").append("\n");
SVGjs.append(" rect.setAttributeNS(null,'id','"+pid+"_p"+"');").append("\n");
SVGjs.append(" rect.setAttributeNS(null,'width','"+pw+"');").append("\n");
SVGjs.append(" rect.setAttributeNS(null,'height','"+ph+"');").append("\n");
SVGjs.append(" rect.setAttributeNS(null,'x','"+px+"');").append("\n");
SVGjs.append(" rect.setAttributeNS(null,'y','"+py+"');").append("\n");
SVGjs.append(" rect.setAttributeNS(null,'fill','none');").append("\n");
SVGjs.append(" rect.setAttributeNS(null,'stroke','black');").append("\n");
SVGjs.append(" rect.setAttributeNS(null, 'stroke-width',1);").append("\n");
SVGjs.append(" g.appendChild(rect);").append("\n");
        
        
        //diferenciadores de la perspectiva
        expression = "/bsc/perspective[@id='"+pid+"']/diffgroup[1]/diff";
        NodeList nlDiffs = (NodeList)xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODESET);
        for(int k=0; k<nlDiffs.getLength(); k++) {
            Node nodeD = nlDiffs.item(k);
            if(nodeD!=null && nodeD.getNodeType()==Node.ELEMENT_NODE) {
                attrs = nodeD.getAttributes();
                String did = attrs.getNamedItem("id").getNodeValue();
                w_ = assertValue(attrs.getNamedItem("width").getNodeValue());
                h_ = assertValue(attrs.getNamedItem("height").getNodeValue());
                x_ = assertValue(attrs.getNamedItem("x").getNodeValue());
                y_ = assertValue(attrs.getNamedItem("y").getNodeValue());
                
                expression = "/bsc/perspective[@id='"+pid+"']/diffgroup[1]/diff/title";
                title = (String)xPath.compile(expression).evaluate(documentBSC, XPathConstants.STRING);

                
SVGjs.append(" txt = document.createElementNS(SVG_,'text');").append("\n");
SVGjs.append(" txt.setAttributeNS(null,'id','"+did+"');").append("\n");
SVGjs.append(" txt.setAttributeNS(null,'x','"+x_+"');").append("\n");
SVGjs.append(" txt.setAttributeNS(null,'y','"+y_+"');").append("\n");
SVGjs.append(" txt.setAttributeNS(null,'font-size','12');").append("\n");
SVGjs.append(" txt.setAttributeNS(null,'font-family','Verdana');").append("\n");
SVGjs.append(" txt.textContent='"+title+"';").append("\n");
SVGjs.append(" g.appendChild(txt);").append("\n");
SVGjs.append(" fixParagraphAtBounding(txt,"+w_+","+h_+","+x_+","+y_+");").append("\n");
SVGjs.append(" rect = getBBoxAsRectElement(txt);").append("\n");
SVGjs.append(" framingRect(rect,"+w_+","+h_+","+x_+","+y_+");").append("\n");
SVGjs.append(" g.appendChild(rect);").append("\n");                
            }
        }
        
        // temas de la perspectiva
        expression = "/bsc/perspective[@id='"+pid+"']/themes/theme";
        NodeList nlThms = (NodeList)xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODESET);
        for(int l=0; l<nlThms.getLength(); l++) {
            Node nodeT = nlThms.item(l);
            if(nodeT!=null && nodeT.getNodeType()==Node.ELEMENT_NODE) {
                attrs = nodeT.getAttributes();
                String tid = attrs.getNamedItem("id").getNodeValue();
                w_ = assertValue(attrs.getNamedItem("width").getNodeValue());
                h_ = assertValue(attrs.getNamedItem("height").getNodeValue());
                x_ = assertValue(attrs.getNamedItem("x").getNodeValue());
                y_ = assertValue(attrs.getNamedItem("y").getNodeValue());

                expression = "/bsc/perspective[@id='"+pid+"']/themes/theme[@id='"+tid+"']/title";
                title = (String)xPath.compile(expression).evaluate(documentBSC, XPathConstants.STRING);
                
SVGjs.append(" txt = document.createElementNS(SVG_,'text');").append("\n");
SVGjs.append(" txt.setAttributeNS(null,'id','"+tid+"');").append("\n");
SVGjs.append(" txt.setAttributeNS(null,'x','"+x_+"');").append("\n");
SVGjs.append(" txt.setAttributeNS(null,'y','"+y_+"');").append("\n");
SVGjs.append(" txt.setAttributeNS(null,'font-size','12');").append("\n");
SVGjs.append(" txt.setAttributeNS(null,'font-family','Verdana');").append("\n");
SVGjs.append(" txt.textContent='"+title+"';").append("\n");
SVGjs.append(" g.appendChild(txt);").append("\n");
SVGjs.append(" fixParagraphAtBounding(txt,"+w_+","+h_+","+x_+","+y_+");").append("\n");
SVGjs.append(" rect = getBBoxAsRectElement(txt);").append("\n");
SVGjs.append(" framingRect(rect,"+w_+","+h_+","+x_+","+y_+");").append("\n");
SVGjs.append(" g.appendChild(rect);").append("\n"); 
                
                
                //lista de objetivos
                expression = "//theme[@id='"+tid+"']/obj";
                NodeList nlObjs = (NodeList)xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODESET);
                for(int m=0; m<nlObjs.getLength(); m++) {
                    Node nodeO = nlObjs.item(m);
                    if(nodeO!=null && nodeO.getNodeType()==Node.ELEMENT_NODE) {
                        attrs = nodeO.getAttributes();
                        String oid = attrs.getNamedItem("id").getNodeValue();
                        w_ = assertValue(attrs.getNamedItem("width").getNodeValue());
                        h_ = assertValue(attrs.getNamedItem("height").getNodeValue());
                        x_ = assertValue(attrs.getNamedItem("x").getNodeValue());
                        y_ = assertValue(attrs.getNamedItem("y").getNodeValue());

                        expression = "//theme[@id='"+tid+"']/obj[@id='"+oid+"']/title";
                        title = (String)xPath.compile(expression).evaluate(documentBSC, XPathConstants.STRING);
SVGjs.append(" txt = document.createElementNS(SVG_,'text');").append("\n");
SVGjs.append(" txt.setAttributeNS(null,'id','"+oid+"');").append("\n");
SVGjs.append(" txt.setAttributeNS(null,'x','"+x_+"');").append("\n");
SVGjs.append(" txt.setAttributeNS(null,'y','"+y_+"');").append("\n");
SVGjs.append(" txt.setAttributeNS(null,'font-size','12');").append("\n");
SVGjs.append(" txt.setAttributeNS(null,'font-family','Verdana');").append("\n");
SVGjs.append(" txt.textContent='"+title+"';").append("\n");
SVGjs.append(" g.appendChild(txt);").append("\n");
SVGjs.append(" fixParagraphAtBounding(txt,"+w_+","+h_+","+x_+","+y_+");").append("\n");
SVGjs.append(" rect = getBBoxAsRectElement(txt);").append("\n");
SVGjs.append(" framingRect(rect,"+w_+","+h_+","+x_+","+y_+");").append("\n");
SVGjs.append(" g.appendChild(rect);").append("\n"); 
                    }
                }
            }
        }
    }
}






SVGjs.append("function framingRect(rect,width, height, x, y) {").append("\n");
SVGjs.append("    rect.x.baseVal.value = x;").append("\n");
SVGjs.append("    rect.y.baseVal.value = y;").append("\n");
SVGjs.append("    rect.width.baseVal.value = width;").append("\n");
SVGjs.append("    rect.height.baseVal.value = height;").append("\n");
SVGjs.append("    rect.setAttributeNS(null,'fill','none');").append("\n");
SVGjs.append("    rect.setAttributeNS(null,'stroke','black');").append("\n");
SVGjs.append("    rect.setAttributeNS(null, 'stroke-width',1);").append("\n");
SVGjs.append("}").append("\n");
        
SVGjs.append("function fixParagraphAtBounding(text_element, width, height, x, y) {").append("\n");
SVGjs.append("    var dy = getFontSize(text_element);").append("\n");
SVGjs.append("    var text = text_element.textContent;").append("\n");
SVGjs.append("    var words = text.split(' ');").append("\n");
SVGjs.append("    var tspan_element = document.createElementNS(SVG_, 'tspan');").append("\n");
SVGjs.append("    var text_node = document.createTextNode(words[0]);").append("\n");

SVGjs.append("    text_element.textContent='';").append("\n");
SVGjs.append("    tspan_element.appendChild(text_node);").append("\n");
SVGjs.append("    text_element.appendChild(tspan_element);").append("\n");

SVGjs.append("    var h;").append("\n");

SVGjs.append("    for(var i=1; i<words.length; i++)").append("\n");
SVGjs.append("    {").append("\n");
SVGjs.append("        h = getBoundingHeight(text_element);").append("\n");
SVGjs.append("        var len = tspan_element.firstChild.data.length;").append("\n");
SVGjs.append("        tspan_element.firstChild.data += ' ' + words[i];").append("\n");

SVGjs.append("        if (tspan_element.getComputedTextLength() > width)").append("\n");
SVGjs.append("        {").append("\n");
SVGjs.append("            dy = dy - (h/height);").append("\n");
SVGjs.append("            text_element.setAttributeNS(null, 'font-size', dy);").append("\n");
SVGjs.append("            var childElements = text_element.getElementsByTagName('tspan');").append("\n");
SVGjs.append("            for (var j=0; j<childElements.length; j++) {").append("\n");
SVGjs.append("                if(childElements[j].getAttribute('dy')) {").append("\n");
SVGjs.append("                    childElements[j].setAttributeNS(null,'dy',dy);").append("\n");
SVGjs.append("                }").append("\n");
SVGjs.append("            }").append("\n");
SVGjs.append("            h = getBoundingHeight(text_element);").append("\n");

SVGjs.append("            if (tspan_element.getComputedTextLength() > width)").append("\n");
SVGjs.append("            {").append("\n");
SVGjs.append("                tspan_element.firstChild.data = tspan_element.firstChild.data.slice(0, len);").append("\n");  // Remove added word

SVGjs.append("                var tspan_element = document.createElementNS(SVG_, 'tspan');").append("\n");
SVGjs.append("                tspan_element.setAttributeNS(null, 'x', x);").append("\n");
SVGjs.append("                tspan_element.setAttributeNS(null, 'dy', dy);").append("\n");
SVGjs.append("                text_node = document.createTextNode(words[i]);").append("\n");
SVGjs.append("                tspan_element.appendChild(text_node);").append("\n");
SVGjs.append("                text_element.appendChild(tspan_element);").append("\n");
SVGjs.append("            }").append("\n");
SVGjs.append("        }").append("\n");
SVGjs.append("    }").append("\n");

SVGjs.append("    h = getBoundingHeight(text_element);").append("\n");
SVGjs.append("    while(h>height) {").append("\n");
SVGjs.append("        dy--;").append("\n");
SVGjs.append("        text_element.setAttributeNS(null, 'font-size', dy);").append("\n");

SVGjs.append("        var childElements = text_element.getElementsByTagName('tspan');").append("\n");
SVGjs.append("        for (var i=0; i < childElements.length; i++) {").append("\n");
SVGjs.append("            if(childElements[i].getAttribute('dy')) {").append("\n");
SVGjs.append("                childElements[i].setAttributeNS(null,'dy',dy-0.5);").append("\n");
SVGjs.append("            }").append("\n");
SVGjs.append("        }").append("\n");
SVGjs.append("        h = getBoundingHeight(text_element);").append("\n");
SVGjs.append("    }").append("\n");
SVGjs.append("}").append("\n");

SVGjs.append(" function getFontSize(text_element) {").append("\n");
SVGjs.append("  var fs_ = window.getComputedStyle(text_element, null).getPropertyValue('font-size');").append("\n");
SVGjs.append("  var fs = fs_.replace( /\\D+/g, '');").append("\n");
SVGjs.append("  return fs;").append("\n");
SVGjs.append(" }").append("\n");

SVGjs.append(" function getBoundingHeight(objNode) {").append("\n");
SVGjs.append("  if(!objNode.getBBox) {").append("\n");
SVGjs.append("      if(objNode.correspondingUseElement) {").append("\n");
SVGjs.append("          objNode = objNode.correspondingUseElement;").append("\n");
SVGjs.append("      }").append("\n");
SVGjs.append("  }").append("\n");
SVGjs.append("  var bbox = objNode.getBBox();").append("\n");
SVGjs.append("  return bbox.height;").append("\n");
SVGjs.append(" }").append("\n");

SVGjs.append(" function getBBoxAsRectElement(objNode) {").append("\n");
SVGjs.append("  if(!objNode.getBBox) {").append("\n");
SVGjs.append("    if(objNode.correspondingUseElement)").append("\n");
SVGjs.append("      objNode = objNode.correspondingUseElement;").append("\n");
SVGjs.append("  }").append("\n");
SVGjs.append("  var bbox = objNode.getBBox();").append("\n");
SVGjs.append("  var rect = document.createElementNS(SVG_, 'rect');").append("\n");
SVGjs.append("  rect.x.baseVal.value = bbox.x;").append("\n");
SVGjs.append("  rect.y.baseVal.value = bbox.y;").append("\n");
SVGjs.append("  rect.width.baseVal.value = bbox.width;").append("\n");
SVGjs.append("  rect.height.baseVal.value = bbox.height;").append("\n");
SVGjs.append("  return rect;").append("\n");
SVGjs.append(" }").append("\n");

        SVGjs.append("</script>");
        return SVGjs.toString();
    }
    
    public Document getSvg_(Document documentBSC) throws XPathExpressionException
    {
        Resource base = getResourceBase();
        String w_, h_, x_, y_;
        String id, expression;
        int w, h, x, y;
        Element t, e, r, s, use;
        
        // XML del BSC
        Element rootBSC = documentBSC.getDocumentElement();
        w_ = rootBSC.getAttribute("width");        
        h_ = rootBSC.getAttribute("height");
        
DOMImplementation impl = SVGDOMImplementation.getDOMImplementation();
final String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
Document documentSVG = impl.createDocument(svgNS, "svg", null);        
        
        Element rootSVG = documentSVG.getDocumentElement();
        w_ = rootBSC.getAttribute("width");
        rootSVG.setAttributeNS(null, "width", w_);
        h_ = rootBSC.getAttribute("height");
        rootSVG.setAttributeNS(null, "height", h_);
        rootSVG.setAttributeNS(null, "viewBox", "0,0,"+w_+","+h_);
        
        XPath xPath = XPathFactory.newInstance().newXPath();
        
        // <header>
        expression = "/bsc/header";
        Node node = (Node) xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODE);
        if(node!=null && node instanceof Element) {
            NamedNodeMap attrs = node.getAttributes();
            id = attrs.getNamedItem("id").getNodeValue();
            s = documentSVG.createElementNS(svgNS, "symbol");
            s.setAttributeNS(null, "id", id);
            rootSVG.appendChild(s);
            
            use = documentSVG.createElementNS(svgNS, "use");
            x = Integer.parseInt(attrs.getNamedItem("x").getNodeValue());
            y = Integer.parseInt(attrs.getNamedItem("y").getNodeName());
            w = Integer.parseInt(attrs.getNamedItem("width").getNodeName());
            h = Integer.parseInt(attrs.getNamedItem("height").getNodeValue());
            use.setAttributeNS(null, "x", Integer.toString(x));
            use.setAttributeNS(null, "y", Integer.toString(y));
            use.setAttributeNS(null, "width", Integer.toString(w));
            use.setAttributeNS(null, "height", Integer.toString(h));
            use.setAttributeNS(null, "xlink:href", attrs.getNamedItem("id").getNodeValue());
            
            NodeList nl = node.getChildNodes();
            if(nl.getLength()>0)
            {
                w = w/nl.getLength();
                h = Integer.parseInt(attrs.getNamedItem("height").getNodeValue());
                
                // <title>
                expression = "/bsc/header/title";
                Node n = (Node) xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODE);
                if(n!=null && node instanceof Element) {
                    Text title = documentSVG.createTextNode(n.getNodeValue());
                    t = documentSVG.createElementNS(svgNS, "text");
                    t.setAttributeNS(null, "x", Integer.toString(x));
                    t.setAttributeNS(null, "y", Integer.toString(y+12));
                    t.setAttributeNS(null, "font-size", "12");
                    t.appendChild(title);
                    s.appendChild(t);                 
                }
                
                // <mission>
                expression = "/bsc/header/mission";
                n = (Node) xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODE);
                if(n!=null && node instanceof Element) {
                    r = documentSVG.createElementNS(svgNS, "rect");
                    r.setAttributeNS(null, "x", Integer.toString(x+BIND_LEFT_SPACING));
                    r.setAttributeNS(null, "y", Integer.toString(y+BIND_TOP_SPACING));
                    r.setAttributeNS(null, "width", Integer.toString(w-BOX_SPACING));
                    r.setAttributeNS(null, "height", Integer.toString(h));
                    r.setAttributeNS(null, "style", "fill:none;stroke:black;stroke-width:1");
                    s.appendChild(r);
                }
                
                // <logo>
                x+=w;
                expression = "/bsc/header/logo";
                n = (Node) xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODE);
                if(n!=null && node instanceof Element) {
                    r = documentSVG.createElementNS(svgNS, "rect");
                    r.setAttributeNS(null, "x", Integer.toString(x+BIND_LEFT_SPACING));
                    r.setAttributeNS(null, "y", Integer.toString(y+BIND_TOP_SPACING));
                    r.setAttributeNS(null, "width", Integer.toString(w-BOX_SPACING));
                    r.setAttributeNS(null, "height", Integer.toString(h));
                    r.setAttributeNS(null, "style", "fill:none;stroke:black;stroke-width:1");
                    s.appendChild(r);
                }
                
                // <vision>
                x+=w;
                expression = "/bsc/header/vision";
                n = (Node) xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODE);
                if(n!=null && node instanceof Element) {
                    r = documentSVG.createElementNS(svgNS, "rect");
                    r.setAttributeNS(null, "x", Integer.toString(x+BIND_LEFT_SPACING));
                    r.setAttributeNS(null, "y", Integer.toString(y+BIND_TOP_SPACING));
                    r.setAttributeNS(null, "width", Integer.toString(w-BOX_SPACING));
                    r.setAttributeNS(null, "height", Integer.toString(h));
                    r.setAttributeNS(null, "style", "fill:none;stroke:black;stroke-width:1");
                    s.appendChild(r);
                }
            }
            rootSVG.appendChild(use);
        }
        
        
        
        return documentSVG;
    }
    
    private int assertValue(final String textVal)
    {
        int val;
        try {
            val = Integer.parseInt(textVal);
        }catch(NumberFormatException nfe) {
            val = 0;
        }catch(NullPointerException nulle) {
            val = 0;
        }
        return val;
    }
}
