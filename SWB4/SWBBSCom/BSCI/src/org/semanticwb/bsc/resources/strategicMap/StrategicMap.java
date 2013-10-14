/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.bsc.resources.strategicMap;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.SWBUtils;
import org.semanticwb.bsc.BSC;
import org.semanticwb.bsc.accessory.DifferentiatorGroup;
import org.semanticwb.bsc.accessory.Period;
import org.semanticwb.bsc.element.Perspective;
import org.semanticwb.bsc.element.Theme;
import org.semanticwb.model.Resource;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

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
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest)
            throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        WebSite ws = paramRequest.getWebPage().getWebSite();
        if (ws instanceof BSC) {
            Period period = getPeriod(request, ws);
            if ((ws != null) && (period != null)) {
                Resource base = paramRequest.getResourceBase();
                BSC bsc = (BSC) ws;
                CausalMap map = new CausalMap();
                CausalArrows arrows = new CausalArrows(map);
                out.println(arrows.draw(bsc, period, base));
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


        sb.append("<script type=\"text/javascript\">\n");
        sb.append("  dojo.require('dijit.form.Form');\n");
        sb.append("</script>\n");
        sb.append("\n<script type=\"text/javascript\">\n");
        sb.append("<!--\n");
        sb.append("var swOk=0, optionObj;");
        sb.append("\nfunction jsValida()");
        sb.append("{");
        sb.append("   var ele=document.getElementById(\"frmAdm" + id + "\");");
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
        sb.append("\n<form type=\"dijit.form.Form\" class=\"swbform\" id=\"frmAdm" + id);
        sb.append("\" onsubmit=\"return jsValida()\" name=\"frmAdm" + id + "\" action=\"" + url);
        sb.append("\" method=\"post\" >");
        sb.append("\n<input type=\"hidden\" name=\"suri\" value=\"" + id + "\">");
        sb.append("\n<fieldset>");
        sb.append("\n<legend>Configuración Perspectivas</legend>");
        sb.append("\n<ul class=\"swbform-ul\">");

        sb.append("\n<li class=\"swbform-li\"><label for=\"amountPerspective\" class=\"swbform-label\">");
        sb.append("Cant maxima de texto a mostrar: </label>");
        sb.append("\n<input id=\"amountPerspective\" name=\"amountPerspective\" type=\"text\" regExp=\"\\d+\"");
        sb.append("value=\"" + amountPerspective + "\" dojoType=\"dijit.form.ValidationTextBox\" ");
        sb.append("promptMessage=\"Captura cantidad.\" invalidMessage=\"El valor proporcionado debe ser númerico.\">");
        sb.append("\n</li>");
        sb.append("\n</ul>");
        Iterator<Perspective> itPers = Perspective.ClassMgr.listPerspectives(ws);
        while (itPers.hasNext()) {
            Perspective perspective = itPers.next();
            String showTitlePerspective = base.getData("show_" + perspective.getTitle()
                    + perspective.getId()) == null ? "" : base.getData("show_" + perspective.getTitle()
                    + perspective.getId());
            String colorTextPerspective = base.getData("ty_" + perspective.getTitle() + perspective.getId())
                    == null ? "" : base.getData("ty_" + perspective.getTitle() + perspective.getId());
            String viewPerspective = base.getData("perspective" + id + perspective.getId()) == null ? ""
                    : base.getData("perspective" + id + perspective.getId());

            String select = showTitlePerspective.equals("") ? "" : "checked";
            sb.append("\n<ul>");
            sb.append("\n<li>" + perspective.getTitle() + ": </li>");

            sb.append("\n<li class=\"swbform-li\"><input id=\"show_" + perspective.getTitle() + perspective.getId());
            sb.append("\" name=\"show_" + perspective.getTitle() + perspective.getId() + "\" ");
            sb.append("type=\"checkbox\" value=\"show_" + perspective.getTitle() + perspective.getId() + "\" ");
            sb.append(" data-dojo-type=\"dijit.form.CheckBox\" " + select + " class=\"swbform-label\">");
            sb.append("\n<label for=\"show_" + perspective.getTitle() + perspective.getId() + "\">");
            sb.append("Mostra titulo horizontal:</label></li>");
            sb.append("\n</li>");

            select = viewPerspective.equals("") ? "" : "checked";
            sb.append("\n<li class=\"swbform-li\"><input id=\"perspective" + id + perspective.getId());
            sb.append("\" name=\"perspective" + id + perspective.getId());
            sb.append("\" type=\"checkbox\" value=\"" + perspective.getId() + "\" ");
            sb.append(" data-dojo-type=\"dijit.form.CheckBox\" " + select + " class=\"swbform-label\">");
            sb.append("\n<label for=\"perspective" + id + perspective.getId() + "\">");
            sb.append("\nMostrar vista en forma horizontal</label></li>");
            sb.append("\n</ul>");

            String strPers = "ty_" + perspective.getTitle() + perspective.getId();
            sb.append("\n<li class=\"swbform-li\"><label for=\"" + strPers + "\" class=\"swbform-label\">");
            sb.append("Color de letra titulo: </label>");
            sb.append("\n<input id=\"" + strPers + "\" name=\"" + strPers + "\" type=\"text\" ");
            sb.append("value=\"" + colorTextPerspective + "\" dojoType=\"dijit.form.ValidationTextBox\">");
            sb.append("\n</li>");

        }
        //fin de recorrer perspectivas

        sb.append("\n</fieldset>");

        sb.append("\n<div id=\"configcol/" + id + "\" dojoType=\"dijit.TitlePane\" title=\"Configurar Temas\"  ");
        sb.append("open=\"false\" duration=\"150\" minSize_=\"20\" splitter_=\"true\" region=\"bottom\">");
        sb.append("\n<fieldset>");
        sb.append("\n<legend>Configurar Temas</legend>");
        sb.append("\n<ul class=\"swbform-ul\">");

        sb.append("\n<li class=\"swbform-li\"><label for=\"amountTheme\" class=\"swbform-label\">");
        sb.append("Cant maxima de texto a mostrar: </label>");
        sb.append("\n<input id=\"amountTheme\" name=\"amountTheme\" type=\"text\" regExp=\"\\d+\"");
        sb.append("value=\"" + amountTheme + "\" dojoType=\"dijit.form.ValidationTextBox\" ");
        sb.append("promptMessage=\"Captura cantidad.\" invalidMessage=\"El valor proporcionado debe ser númerico.\">");
        sb.append("\n</li>");
        sb.append("\n</ul>");

        Iterator<Theme> itTheme = Theme.ClassMgr.listThemes(ws);
        while (itTheme.hasNext()) {
            Theme theme = itTheme.next();
            String colorBgTheme = base.getData("bg_" + theme.getTitle() + "_" + theme.getId()) == null ? ""
                    : base.getData("bg_" + theme.getTitle() + "_" + theme.getId());
            String colorTxtTheme = base.getData("ty_" + theme.getTitle() + "_" + theme.getId()) == null ? ""
                    : base.getData("ty_" + theme.getTitle() + "_" + theme.getId());
            sb.append("\n<ul>");
            sb.append("\n<li>" + theme.getTitle() + ": </li>");

            String strTheme = "ty_" + theme.getTitle() + "_" + theme.getId();
            sb.append("\n<li class=\"swbform-li\"><label for=\"" + strTheme + "\" class=\"swbform-label\">");
            sb.append("Color de letra para titulo: </label>");
            sb.append("\n<input id=\"" + strTheme + "\" name=\"" + strTheme + "\" type=\"text\" ");
            sb.append("value=\"" + colorBgTheme + "\" dojoType=\"dijit.form.ValidationTextBox\">");
            sb.append("\n</li>");

            String strTheme1 = "bg_" + theme.getTitle() + "_" + theme.getId();
            sb.append("\n<li class=\"swbform-li\"><label for=\"" + strTheme1 + "\" class=\"swbform-label\">");
            sb.append("Color de Fondo: </label>");
            sb.append("\n<input id=\"" + strTheme1 + "\" name=\"" + strTheme1 + "\" type=\"text\" ");
            sb.append("value=\"" + colorTxtTheme + "\" dojoType=\"dijit.form.ValidationTextBox\">");
            sb.append("\n</li>");

            sb.append("\n</ul>");

        }
        sb.append("\n</fieldset>");
        sb.append("\n</div>");


        sb.append("\n<div id=\"configObj/" + id + "\" dojoType=\"dijit.TitlePane\" title=\"Configurar Objectivos\"  ");
        sb.append("open=\"false\" duration=\"150\" minSize_=\"20\" splitter_=\"true\" region=\"bottom\">");
        sb.append("\n<fieldset>");
        sb.append("\n<legend>Configurar Objectivos</legend>");
        sb.append("\n<ul class=\"swbform-ul\">");

        sb.append("\n<li class=\"swbform-li\"><label for=\"widthHorizontalObjective\" class=\"swbform-label\">");
        sb.append("Alto objetivo forma horizontal: </label>");
        sb.append("\n<input id=\"widthHorizontalObjective\" name=\"widthHorizontalObjective\" type=\"text\" ");
        sb.append("regExp=\"\\d+\" value=\"" + widthHorizontalObjective + "\" ");
        sb.append("dojoType=\"dijit.form.ValidationTextBox\" promptMessage=\"Captura cantidad.\"");
        sb.append(" invalidMessage=\"El valor proporcionado debe ser númerico.\">");
        sb.append("\n</li>");

        sb.append("\n<li class=\"swbform-li\"><label for=\"widthVerticalObjective\" class=\"swbform-label\">");
        sb.append("Alto objetivo forma vertical: </label>");
        sb.append("\n<input id=\"widthVerticalObjective\" name=\"widthVerticalObjective\" type=\"text\" ");
        sb.append("regExp=\"\\d+\" value=\"" + widthVerticalObjective + "\"");
        sb.append(" dojoType=\"dijit.form.ValidationTextBox\" promptMessage=\"Captura cantidad.\"");
        sb.append(" invalidMessage=\"El valor proporcionado debe ser númerico.\">");
        sb.append("\n</li>");

        sb.append("\n<li class=\"swbform-li\"><label for=\"amountObjective\" class=\"swbform-label\">");
        sb.append("Cant maxima de texto a mostrar: </label>");
        sb.append("\n<input id=\"amountObjective\" name=\"amountObjective\" type=\"text\" regExp=\"\\d+\"");
        sb.append("value=\"" + amountObjective + "\" dojoType=\"dijit.form.ValidationTextBox\" ");
        sb.append("promptMessage=\"Captura cantidad.\" invalidMessage=\"El valor proporcionado debe ser númerico.\">");
        sb.append("\n</li>");

        sb.append("\n</fieldset>");
        sb.append("\n</div>");


        sb.append("\n<div id=\"configGDiffere/" + id + "\" dojoType=\"dijit.TitlePane\" title=\"Grupos de diferenciadores\"  ");
        sb.append("open=\"false\" duration=\"150\" minSize_=\"20\" splitter_=\"true\" region=\"bottom\">");
        sb.append("\n<fieldset>");
        sb.append("\n<legend>Configurar Grupos de diferenciadores</legend>");
        sb.append("\n<ul class=\"swbform-ul\">");

        sb.append("\n<li class=\"swbform-li\"><label for=\"amountDifferentiatorGroup\" class=\"swbform-label\">");
        sb.append("Cant maxima de texto a mostrar: </label>");
        sb.append("\n<input id=\"amountDifferentiatorGroup\" name=\"amountDifferentiatorGroup\" type=\"text\" ");
        sb.append("regExp=\"\\d+\" value=\"" + amountDifferentiatorGroup + "\"");
        sb.append(" dojoType=\"dijit.form.ValidationTextBox\" promptMessage=\"Captura cantidad.\"");
        sb.append(" invalidMessage=\"El valor proporcionado debe ser númerico.\">");
        sb.append("\n</li>");
        sb.append("\n</ul>");
        Iterator<DifferentiatorGroup> itDiffeG = DifferentiatorGroup.ClassMgr.listDifferentiatorGroups(ws);
        while (itDiffeG.hasNext()) {
            DifferentiatorGroup diffG = itDiffeG.next();
            String colorBgTheme = base.getData("bg_" + diffG.getTitle() + "_" + diffG.getId()) == null ? ""
                    : base.getData("bg_" + diffG.getTitle() + "_" + diffG.getId());
            String colorTxtTheme = base.getData("ty_" + diffG.getTitle() + "_" + diffG.getId()) == null ? ""
                    : base.getData("ty_" + diffG.getTitle() + "_" + diffG.getId());
            sb.append("\n<ul>");
            sb.append("\n<li>" + diffG.getTitle() + ": </li>");

            String strTheme = "ty_" + diffG.getTitle() + "_" + diffG.getId();
            sb.append("\n<li class=\"swbform-li\"><label for=\"" + strTheme + "\" class=\"swbform-label\">");
            sb.append("Color de letra para titulo: </label>");
            sb.append("\n<input id=\"" + strTheme + "\" name=\"" + strTheme + "\" type=\"text\" ");
            sb.append("value=\"" + colorBgTheme + "\" dojoType=\"dijit.form.ValidationTextBox\">");
            sb.append("\n</li>");

            String strTheme1 = "bg_" + diffG.getTitle() + "_" + diffG.getId();
            sb.append("\n<li class=\"swbform-li\"><label for=\"" + strTheme1 + "\" class=\"swbform-label\">");
            sb.append("Color de Fondo: </label>");
            sb.append("\n<input id=\"" + strTheme1 + "\" name=\"" + strTheme1 + "\" type=\"text\" ");
            sb.append("value=\"" + colorTxtTheme + "\" dojoType=\"dijit.form.ValidationTextBox\">");
            sb.append("\n</li>");

            sb.append("\n</ul>");

        }
        sb.append("\n</fieldset>");
        sb.append("\n</div>");


        sb.append("\n<div id=\"configDife/" + id + "\" dojoType=\"dijit.TitlePane\" title=\"Configurar Diferenciadores\"  ");
        sb.append("open=\"false\" duration=\"150\" minSize_=\"20\" splitter_=\"true\" region=\"bottom\">");
        sb.append("\n<fieldset>");
        sb.append("\n<legend>Configurar Diferenciadores</legend>");
        sb.append("\n<ul class=\"swbform-ul\">");

        sb.append("\n<li class=\"swbform-li\">");
        sb.append("\n<label for=\"widthHorizontalDifferentiator\" class=\"swbform-label\">");
        sb.append("Alto objetivo forma horizontal: </label>");
        sb.append("\n<input id=\"widthHorizontalDifferentiator\" name=\"widthHorizontalDifferentiator\" ");
        sb.append("type=\"text\" regExp=\"\\d+\" ");
        sb.append("value=\"" + widthHorizontalDifferentiator + "\" dojoType=\"dijit.form.ValidationTextBox\" ");
        sb.append("promptMessage=\"Captura cantidad.\" invalidMessage=\"El valor proporcionado debe ser númerico.\">");
        sb.append("\n</li>");

        sb.append("\n<li class=\"swbform-li\">");
        sb.append("\n<label for=\"amountDifferentiator\" class=\"swbform-label\">");
        sb.append("Cant maxima de texto a mostrar: </label>");
        sb.append("\n<input id=\"amountDifferentiator\" name=\"amountDifferentiator\" type=\"text\" ");
        sb.append("regExp=\"\\d+\" value=\"" + amountDifferentiator + "\" dojoType=\"dijit.form.ValidationTextBox\" ");
        sb.append("promptMessage=\"Captura cantidad.\" invalidMessage=\"El valor proporcionado debe ser númerico.\">");
        sb.append("\n</li>");
        sb.append("\n</ul>");
        sb.append("\n</fieldset>");
        sb.append("\n</div>");

        sb.append("\n<div id=\"configCausal/" + id + "\" dojoType=\"dijit.TitlePane\" title=\"Causa Efecto\"  ");
        sb.append("open=\"false\" duration=\"150\" minSize_=\"20\" splitter_=\"true\" region=\"bottom\">");
        sb.append("\n<fieldset>");
        sb.append("\n<legend>Configurar Relaciones Causa Efecto</legend>");
        sb.append("\n<ul class=\"swbform-ul\">");

        sb.append("\n<li class=\"swbform-li\">");
        sb.append("\n<label for=\"colorRelOO\" class=\"swbform-label\">");
        sb.append("Clase CSS Causa efecto Obj a Obj: </label>");
        sb.append("\n<input id=\"colorRelOO\" name=\"colorRelOO\" type=\"text\" ");
        sb.append("value=\"" + colorRelOO + "\" dojoType=\"dijit.form.ValidationTextBox\">");
        sb.append("\n</li>");

        sb.append("\n<li class=\"swbform-li\">");
        sb.append("\n<label for=\"colorRelOT\" class=\"swbform-label\">");
        sb.append("Clase CSS Causa efecto Obj a Tema: </label>");
        sb.append("\n<input id=\"colorRelOT\" name=\"colorRelOT\" type=\"text\" ");
        sb.append("value=\"" + colorRelOT + "\" dojoType=\"dijit.form.ValidationTextBox\">");
        sb.append("\n</li>");

        sb.append("\n<li class=\"swbform-li\">");
        sb.append("\n<label for=\"colorRelTO\" class=\"swbform-label\">");
        sb.append("Clase CSS Causa efecto Tema a Obj: </label>");
        sb.append("\n<input id=\"colorRelTO\" name=\"colorRelTO\" type=\"text\" ");
        sb.append("value=\"" + colorRelTO + "\" dojoType=\"dijit.form.ValidationTextBox\">");
        sb.append("\n</li>");

        sb.append("\n<li class=\"swbform-li\">");
        sb.append("\n<label for=\"colorRelTT\" class=\"swbform-label\">");
        sb.append("Clase CSS Causa efecto Tema a Tema: </label>");
        sb.append("\n<input id=\"colorRelTT\" name=\"colorRelTT\" type=\"text\" ");
        sb.append("value=\"" + colorRelTT + "\" dojoType=\"dijit.form.ValidationTextBox\">");
        sb.append("\n</li>");
        sb.append("\n</ul>");

        sb.append("\n</fieldset>");
        sb.append("\n</div>");

        sb.append("\n<fieldset>");
        sb.append("\n <button dojoType=\"dijit.form.Button\" type=\"submit\" value=\"Submit\" >Guardar");
        sb.append("</button>&nbsp;");
        sb.append("\n <button dojoType=\"dijit.form.Button\" type=\"reset\" value=\"Reset\">Cancelar</button>");
        sb.append("\n</fieldset>");

        sb.append("\n</form>");
        sb.append("\n</div>");
        if ((request.getParameter("statusMsg")) != null
                && (!request.getParameter("statusMsg").isEmpty())) {
            sb.append("\n<script type=\"text/javascript\" language=\"JavaScript\">");
            sb.append("\n   alert('" + request.getParameter("statusMsg") + "');");
            sb.append("\n   window.location.href='" + paramRequest.getRenderUrl().setAction("edit") + "';");
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
    public void processAction(HttpServletRequest request, SWBActionResponse response)
            throws SWBResourceException, IOException {
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
                base.setData(showTitlePerspective,null);
                base.setData(viewPerspective,null);
            }
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
    private Period getPeriod(HttpServletRequest request, WebSite ws) {
        Period period = null;
        if (request.getSession().getAttribute("period") != null) {
            String dataPeriod = (String) request.getSession().getAttribute("period");
            period = Period.ClassMgr.getPeriod(dataPeriod, ws);
        }
        //if(period == null) {
        period = Period.ClassMgr.getPeriod("6", ws);
        //}
        return period;
    }
}
