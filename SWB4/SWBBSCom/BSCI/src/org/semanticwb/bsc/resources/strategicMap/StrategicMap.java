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
 *
 * @author Martha
 */
public class StrategicMap extends GenericResource {

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        WebSite ws = paramRequest.getWebPage().getWebSite();
        Period period = getPeriod(request, ws);
        Resource base = paramRequest.getResourceBase();
        if (ws instanceof BSC && period != null) {
            BSC bsc = (BSC) ws;
            CausalMap map = new CausalMap();
            CausalArrows arrows = new CausalArrows(map);
            out.println(arrows.draw(bsc, period, base));
        }
    }

    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
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
        String colorRelOO = base.getData("colorRelOO") == null ? ""
                : base.getData("colorRelOO");
        String colorRelOT = base.getData("colorRelOT") == null ? ""
                : base.getData("colorRelOT");
        String colorRelTO = base.getData("colorRelTO") == null ? ""
                : base.getData("colorRelTO");
        String colorRelTT = base.getData("colorRelTT") == null ? ""
                : base.getData("colorRelTT");


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
        //sb.append("   var ele4=document.getElementById(\"widthVerticalObjective\");");

        sb.append("   var ele5=document.getElementById(\"amountDifferentiator\");");
        sb.append("   var ele6=document.getElementById(\"amountObjective\");");
        sb.append("   var ele7=document.getElementById(\"amountDifferentiator\");");
        sb.append("   var ele8=document.getElementById(\"amountPerspective\");");
        sb.append("   var ele9=document.getElementById(\"amountTheme\");");
        sb.append("   var ele10=document.getElementById(\"amountDifferentiatorGroup\");");
        sb.append("   if(!isNumber(ele1)) return false;");
        sb.append("   if(!isNumber(ele2)) return false;");
        sb.append("   if(!isNumber(ele3)) return false;");
        //sb.append("   if(!isNumber(ele4)) return false;");
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
        sb.append("\n           alert('" + SWBUtils.TEXT.getLocaleString("locale_swb_util", "usrmsg_WBResource_loadIsNumber_msg") + ".');");
        sb.append("\n           return false;");
        sb.append("\n       }");
        sb.append("\n   }");
        sb.append("\n   return true;");
        sb.append("\n}");

        sb.append("\n-->");
        sb.append("\n</script>");

        sb.append("<div class=\"swbform\">");
        sb.append("<form type=\"dijit.form.Form\" class=\"swbform\" id=\"frmAdm" + id + "\" onsubmit=\"return jsValida()\" name=\"frmAdm" + id + "\" action=\"" + url + "\" method=\"post\" >"); //return false;onsubmit=\"submitForm('frmAdm" + id + "'); alert('ver');\" 
        sb.append("<input type=\"hidden\" name=\"suri\" value=\"" + id + "\">");
        sb.append("<fieldset>");
        sb.append("<legend>Configuración Perspectivas</legend>");
        sb.append("<ul class=\"swbform-ul\">");

        sb.append("<li class=\"swbform-li\"><label for=\"amountPerspective\" class=\"swbform-label\">Cant maxima de texto a mostrar: </label>"
                + "<input id=\"amountPerspective\" name=\"amountPerspective\" type=\"text\" regExp=\"\\d+\""
                + "value=\"" + amountPerspective + "\" dojoType=\"dijit.form.ValidationTextBox\" promptMessage=\"Captura cantidad.\" invalidMessage=\"El valor proporcionado debe ser númerico.\">");
        sb.append("</li>");

        Iterator<Perspective> itPers = Perspective.ClassMgr.listPerspectives(ws);
        while (itPers.hasNext()) {
            Perspective perspective = itPers.next();
            String showTitlePerspective = base.getData("show_" + perspective.getTitle() + perspective.getId()) == null ? ""
                    : base.getData("show_" + perspective.getTitle() + perspective.getId());
            String colorTextPerspective = base.getData("ty_" + perspective.getTitle() + perspective.getId()) == null ? ""
                    : base.getData("ty_" + perspective.getTitle() + perspective.getId());
            String viewPerspective = base.getData("perspective" + id + perspective.getId()) == null ? ""
                    : base.getData("perspective" + id + perspective.getId());

            String select = showTitlePerspective.equals("") ? "" : "checked";
            sb.append("<ul>");
            sb.append("<li>" + perspective.getTitle() + ": </li>");

            sb.append("<li class=\"swbform-li\"><input id=\"show_" + perspective.getTitle() + perspective.getId() + "\" ");
            sb.append("name=\"show_" + perspective.getTitle() + perspective.getId() + "\" ");
            sb.append("type=\"checkbox\" value=\"show_" + perspective.getTitle() + perspective.getId() + "\" ");
            sb.append(" data-dojo-type=\"dijit.form.CheckBox\" " + select + " class=\"swbform-label\">");
            sb.append("<label for=\"show_" + perspective.getTitle() + perspective.getId() + "\"> Mostra titulo horizontal:");
            sb.append("</label></li>");
            sb.append("</li>");

            String strPers = "ty_" + perspective.getTitle() + perspective.getId();
            sb.append("<li class=\"swbform-li\"><label for=\"" + strPers + "\" class=\"swbform-label\">Color de Color de letra titulo: </label>");
            sb.append("<input id=\"" + strPers + "\" name=\"" + strPers + "\" type=\"text\" ");
            sb.append("value=\"" + colorTextPerspective + "\" dojoType=\"dijit.form.ValidationTextBox\">");
            sb.append("</li>");

            select = viewPerspective.equals("") ? "" : "checked";
            sb.append("<li class=\"swbform-li\"><input id=\"perspective" + id + perspective.getId() + "\" name=\"perspective" + id + perspective.getId());
            sb.append("\" type=\"checkbox\" value=\"" + perspective.getId() + "\" ");
            sb.append(" data-dojo-type=\"dijit.form.CheckBox\" " + select + " class=\"swbform-label\">");
            sb.append("<label for=\"Mostrar vista en forma horizontal\">");
            sb.append("</label></li>");
            sb.append("</ul>");

        }
        //fin de recorrer perspectivas
        sb.append("</ul>");
        sb.append("</fieldset>");

        sb.append("<div id=\"configcol/" + id + "\" dojoType=\"dijit.TitlePane\" title=\"Estilo\"  open=\"false\" duration=\"150\" minSize_=\"20\" splitter_=\"true\" region=\"bottom\">");
        sb.append("<fieldset>");
        sb.append("<legend>Configurar Temas</legend>");
        sb.append("<ul class=\"swbform-ul\">");

        sb.append("<li class=\"swbform-li\"><label for=\"amountTheme\" class=\"swbform-label\">Cant maxima de texto a mostrar: </label>"
                + "<input id=\"amountTheme\" name=\"amountTheme\" type=\"text\" regExp=\"\\d+\""
                + "value=\"" + amountTheme + "\" dojoType=\"dijit.form.ValidationTextBox\" promptMessage=\"Captura cantidad.\" invalidMessage=\"El valor proporcionado debe ser númerico.\">");
        sb.append("</li>");

        Iterator<Theme> itTheme = Theme.ClassMgr.listThemes(ws);
        while (itTheme.hasNext()) {
            Theme theme = itTheme.next();
            String colorBgTheme = base.getData("bg_" + theme.getTitle() + "_" + theme.getId()) == null ? ""
                    : base.getData("bg_" + theme.getTitle() + "_" +  theme.getId());
            String colorTxtTheme = base.getData("ty_" + theme.getTitle() + "_" + theme.getId()) == null ? ""
                    : base.getData("ty_" + theme.getTitle() + "_" + theme.getId());
            sb.append("<ul>");
            sb.append("<li>" + theme.getTitle() + ": </li>");

            String strTheme = "ty_" + theme.getTitle() + "_" + theme.getId();
            sb.append("<li class=\"swbform-li\"><label for=\"" + strTheme + "\" class=\"swbform-label\">Color de letra para titulo: </label>");
            sb.append("<input id=\"" + strTheme + "\" name=\"" + strTheme + "\" type=\"text\" ");
            sb.append("value=\"" + colorBgTheme + "\" dojoType=\"dijit.form.ValidationTextBox\">");
            sb.append("</li>");

            String strTheme1 = "bg_" + theme.getTitle() + "_" + theme.getId();
            sb.append("<li class=\"swbform-li\"><label for=\"" + strTheme1 + "\" class=\"swbform-label\">Color de Fondo: </label>");
            sb.append("<input id=\"" + strTheme1 + "\" name=\"" + strTheme1 + "\" type=\"text\" ");
            sb.append("value=\"" + colorTxtTheme + "\" dojoType=\"dijit.form.ValidationTextBox\">");
            sb.append("</li>");

            sb.append("</ul>");

        }
        sb.append("</fieldset>");
        sb.append("</div>");


        sb.append("<div id=\"configObj/" + id + "\" dojoType=\"dijit.TitlePane\" title=\"Estilo\"  open=\"false\" duration=\"150\" minSize_=\"20\" splitter_=\"true\" region=\"bottom\">");
        sb.append("<fieldset>");
        sb.append("<legend>Configurar Objectivos</legend>");
        sb.append("<ul class=\"swbform-ul\">");

        sb.append("<li class=\"swbform-li\"><label for=\"widthHorizontalObjective\" class=\"swbform-label\">Alto objetivo forma horizontal: </label>");
        sb.append("<input id=\"widthHorizontalObjective\" name=\"widthHorizontalObjective\" type=\"text\" regExp=\"\\d+\"");
        sb.append("value=\"" + widthHorizontalObjective + "\" dojoType=\"dijit.form.ValidationTextBox\" promptMessage=\"Captura cantidad.\" invalidMessage=\"El valor proporcionado debe ser númerico.\">");
        sb.append("</li>");

        sb.append("<li class=\"swbform-li\"><label for=\"widthVerticalObjective\" class=\"swbform-label\">Alto objetivo forma vertical: </label>");
        sb.append("<input id=\"widthVerticalObjective\" name=\"widthVerticalObjective\" type=\"text\" regExp=\"\\d+\"");
        sb.append("value=\"" + widthVerticalObjective + "\" dojoType=\"dijit.form.ValidationTextBox\" promptMessage=\"Captura cantidad.\" invalidMessage=\"El valor proporcionado debe ser númerico.\">");
        sb.append("</li>");

        sb.append("<li class=\"swbform-li\"><label for=\"amountObjective\" class=\"swbform-label\">Cant maxima de texto a mostrar: </label>");
        sb.append("<input id=\"amountObjective\" name=\"amountObjective\" type=\"text\" regExp=\"\\d+\"");
        sb.append("value=\"" + amountObjective + "\" dojoType=\"dijit.form.ValidationTextBox\" promptMessage=\"Captura cantidad.\" invalidMessage=\"El valor proporcionado debe ser númerico.\">");
        sb.append("</li>");

        sb.append("</fieldset>");
        sb.append("</div>");

        
        sb.append("<div id=\"configGDiffere/" + id + "\" dojoType=\"dijit.TitlePane\" title=\"Estilo\"  open=\"false\" duration=\"150\" minSize_=\"20\" splitter_=\"true\" region=\"bottom\">");
        sb.append("<fieldset>");
        sb.append("<legend>Configurar Grupos de diferenciadores</legend>");
        sb.append("<ul class=\"swbform-ul\">");

        sb.append("<li class=\"swbform-li\"><label for=\"amountDifferentiatorGroup\" class=\"swbform-label\">Cant maxima de texto a mostrar: </label>"
                + "<input id=\"amountDifferentiatorGroup\" name=\"amountDifferentiatorGroup\" type=\"text\" regExp=\"\\d+\""
                + "value=\"" + amountDifferentiatorGroup + "\" dojoType=\"dijit.form.ValidationTextBox\" promptMessage=\"Captura cantidad.\" invalidMessage=\"El valor proporcionado debe ser númerico.\">");
        sb.append("</li>");

        Iterator<DifferentiatorGroup> itDiffeG = DifferentiatorGroup.ClassMgr.listDifferentiatorGroups(ws);
        while (itDiffeG.hasNext()) {
            DifferentiatorGroup diffG = itDiffeG.next();
            String colorBgTheme = base.getData("bg_" + diffG.getTitle() + "_" + diffG.getId()) == null ? ""
                    : base.getData("bg_" + diffG.getTitle() + "_" +  diffG.getId());
            String colorTxtTheme = base.getData("ty_" + diffG.getTitle() + "_" + diffG.getId()) == null ? ""
                    : base.getData("ty_" + diffG.getTitle() + "_" + diffG.getId());
            sb.append("<ul>");
            sb.append("<li>" + diffG.getTitle() + ": </li>");

            String strTheme = "ty_" + diffG.getTitle() + "_" + diffG.getId();
            sb.append("<li class=\"swbform-li\"><label for=\"" + strTheme + "\" class=\"swbform-label\">Color de letra para titulo: </label>");
            sb.append("<input id=\"" + strTheme + "\" name=\"" + strTheme + "\" type=\"text\" ");
            sb.append("value=\"" + colorBgTheme + "\" dojoType=\"dijit.form.ValidationTextBox\">");
            sb.append("</li>");

            String strTheme1 = "bg_" + diffG.getTitle() + "_" + diffG.getId();
            sb.append("<li class=\"swbform-li\"><label for=\"" + strTheme1 + "\" class=\"swbform-label\">Color de Fondo: </label>");
            sb.append("<input id=\"" + strTheme1 + "\" name=\"" + strTheme1 + "\" type=\"text\" ");
            sb.append("value=\"" + colorTxtTheme + "\" dojoType=\"dijit.form.ValidationTextBox\">");
            sb.append("</li>");

            sb.append("</ul>");

        }
        sb.append("</fieldset>");
        sb.append("</div>");

        
        sb.append("<div id=\"configDife/" + id + "\" dojoType=\"dijit.TitlePane\" title=\"Estilo\"  open=\"false\" duration=\"150\" minSize_=\"20\" splitter_=\"true\" region=\"bottom\">");
        sb.append("<fieldset>");
        sb.append("<legend>Configurar Objectivos</legend>");
        sb.append("<ul class=\"swbform-ul\">");

        sb.append("<li class=\"swbform-li\"><label for=\"widthHorizontalDifferentiator\" class=\"swbform-label\">Alto objetivo forma horizontal: </label>");
        sb.append("<input id=\"widthHorizontalDifferentiator\" name=\"widthHorizontalDifferentiator\" type=\"text\" regExp=\"\\d+\"");
        sb.append("value=\"" + widthHorizontalDifferentiator + "\" dojoType=\"dijit.form.ValidationTextBox\" promptMessage=\"Captura cantidad.\" invalidMessage=\"El valor proporcionado debe ser númerico.\">");
        sb.append("</li>");

        sb.append("<li class=\"swbform-li\"><label for=\"amountDifferentiator\" class=\"swbform-label\">Cant maxima de texto a mostrar: </label>");
        sb.append("<input id=\"amountDifferentiator\" name=\"amountDifferentiator\" type=\"text\" regExp=\"\\d+\"");
        sb.append("value=\"" + amountDifferentiator + "\" dojoType=\"dijit.form.ValidationTextBox\" promptMessage=\"Captura cantidad.\" invalidMessage=\"El valor proporcionado debe ser númerico.\">");
        sb.append("</li>");

        sb.append("</fieldset>");
        sb.append("</div>");
        
        sb.append("<div id=\"configCausal/" + id + "\" dojoType=\"dijit.TitlePane\" title=\"Estilo\"  open=\"false\" duration=\"150\" minSize_=\"20\" splitter_=\"true\" region=\"bottom\">");
        sb.append("<fieldset>");
        sb.append("<legend>Configurar Relaciones Causa Efecto</legend>");
        sb.append("<ul class=\"swbform-ul\">");

        sb.append("<li class=\"swbform-li\"><label for=\"colorRelOO\" class=\"swbform-label\">Color Causa efecto Obj a Obj: </label>");
        sb.append("<input id=\"colorRelOO\" name=\"colorRelOO\" type=\"text\" ");
        sb.append("value=\"" + colorRelOO + "\" dojoType=\"dijit.form.ValidationTextBox\">");        
        sb.append("</li>");

        sb.append("<li class=\"swbform-li\"><label for=\"colorRelOT\" class=\"swbform-label\">Color Causa efecto Obj a Tema: </label>");
        sb.append("<input id=\"colorRelOT\" name=\"colorRelOT\" type=\"text\" ");
        sb.append("value=\"" + colorRelOT + "\" dojoType=\"dijit.form.ValidationTextBox\">");        
        sb.append("</li>");

                sb.append("<li class=\"swbform-li\"><label for=\"colorRelTO\" class=\"swbform-label\">Color Causa efecto Tema a Obj: </label>");
        sb.append("<input id=\"colorRelTO\" name=\"colorRelTO\" type=\"text\" ");
        sb.append("value=\"" + colorRelTO + "\" dojoType=\"dijit.form.ValidationTextBox\">");        
        sb.append("</li>");

                sb.append("<li class=\"swbform-li\"><label for=\"colorRelTT\" class=\"swbform-label\">Color Causa efecto Tema a Tema: </label>");
        sb.append("<input id=\"colorRelTT\" name=\"colorRelTT\" type=\"text\" ");
        sb.append("value=\"" + colorRelTT + "\" dojoType=\"dijit.form.ValidationTextBox\">");        
        sb.append("</li>");

        sb.append("</fieldset>");
        sb.append("</div>");

        out.append("<fieldset>");
        out.append(" <button dojoType=\"dijit.form.Button\" type=\"submit\" value=\"Submit\" >Guardar</button>&nbsp;");
        out.append(" <button dojoType=\"dijit.form.Button\" type=\"reset\" value=\"Reset\">Cancelar</button>");
        out.append("</fieldset>");

        sb.append("</form>");
        sb.append("</div>");
        if (request.getParameter("statusMsg") != null
                && !request.getParameter("statusMsg").isEmpty()) {
            sb.append("<script type=\"text/javascript\" language=\"JavaScript\">");
            sb.append("   alert('" + request.getParameter("statusMsg") + "');");
            sb.append("   window.location.href='" + paramRequest.getRenderUrl().setAction("edit") + "';");
            sb.append("</script>");
        }

    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String action = response.getAction();
        Resource base = response.getResourceBase();
        if ("update".equals(action)) {
            Enumeration enumAttri = request.getParameterNames();
            StringBuffer elePerspective = new StringBuffer();
            elePerspective.append("");
            while (enumAttri.hasMoreElements()) {
                Object elem = enumAttri.nextElement();
                String data = (String) request.getParameter(elem.toString());
//                if (elem.toString().startsWith("perspective")) {
//                    if (elePerspective.length() > 0) {
//                        elePerspective.append(",");
//                    }
//                    elePerspective.append(elem);
//                } else {
                base.setData(elem.toString(), data);
//                }
            }
            base.setData("perspectives", elePerspective.toString());
        }
        response.setRenderParameter("statusMsg", "Actualización exitosa");//response.getLocaleString("statusMsg")
        response.setAction(SWBResourceURL.Mode_ADMIN);
    }

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
