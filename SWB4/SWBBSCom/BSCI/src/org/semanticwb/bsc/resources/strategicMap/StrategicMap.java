/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.bsc.resources.strategicMap;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
import org.semanticwb.model.User;
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
        User user = paramRequest.getUser();

        String fontSizePerspective = base.getData("fontSizePerspective") == null ? ""
                : base.getData("fontSizePerspective");
        String amountObjective = base.getData("amountObjective") == null ? ""
                : base.getData("amountObjective");
        String amountDistinctive = base.getData("amountDistinctive") == null ? ""
                : base.getData("amountDistinctive");
        String amountPerspective = base.getData("amountPerspective") == null ? ""
                : base.getData("amountPerspective");
        String widthHorizontalObjective = base.getData("widthHorizontalObjective") == null ? ""
                : base.getData("widthHorizontalObjective");
        String widthVerticalObjective = base.getData("widthVerticalObjective") == null ? ""
                : base.getData("widthVerticalObjective");
        String perspectives = base.getData("perspectives") == null ? ""
                : base.getData("perspectives");
//        String[] arrayValues = {"Center", "Right", "Left"};

        ArrayList arrayPerspectives = new ArrayList();
        if (perspectives.length() > 1) {
            String[] dataPerspectives = perspectives.split(",");

            for (String string : dataPerspectives) {
                arrayPerspectives.add(string);
            }
        }

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
        sb.append("   var ele3=document.getElementById(\"fontSizeObjective\");");
        sb.append("   var ele4=document.getElementById(\"fontSizeDistinctive\");");
        sb.append("   var ele5=document.getElementById(\"fontSizePerspective\");");
        sb.append("   var ele6=document.getElementById(\"amountObjective\");");
        sb.append("   var ele7=document.getElementById(\"amountDistinctive\");");
        sb.append("   var ele8=document.getElementById(\"amountPerspective\");");
        sb.append("   if(!isNumber(ele1)) return false;");
        sb.append("   if(!isNumber(ele2)) return false;");
        sb.append("   if(!isNumber(ele3)) return false;");
        sb.append("   if(!isNumber(ele4)) return false;");
        sb.append("   if(!isNumber(ele5)) return false;");
        sb.append("   if(!isNumber(ele6)) return false;");
        sb.append("   if(!isNumber(ele7)) return false;");
        sb.append("   if(!isNumber(ele8)) return false;");
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
        sb.append("<legend>Configuración básica</legend>");
        sb.append("<ul class=\"swbform-ul\">");
        
        sb.append("<li class=\"swbform-li\"><label for=\"widthHorizontalObjective\" class=\"swbform-label\">Alto en px para objetivos mostrados horizontalmente: </label>"
                + "<input id=\"widthHorizontalObjective\" name=\"widthHorizontalObjective\" type=\"text\" regExp=\"\\d+\""
                + "value=\"" + widthHorizontalObjective + "\" dojoType=\"dijit.form.ValidationTextBox\" promptMessage=\"Captura Alto.\" invalidMessage=\"El valor proporcionado debe ser númerico.\">");
        sb.append("</li>");
        
        sb.append("<li class=\"swbform-li\"><label for=\"widthVerticalObjective\" class=\"swbform-label\">Alto en px para objetivos mostrados verticalmente: </label>"
                + "<input id=\"widthVerticalObjective\" name=\"widthVerticalObjective\" type=\"text\" regExp=\"\\d+\""
                + "value=\"" + widthVerticalObjective + "\" dojoType=\"dijit.form.ValidationTextBox\" promptMessage=\"Captura Alto.\" invalidMessage=\"El valor proporcionado debe ser númerico.\">");
        sb.append("</li>");
        
        sb.append("<li class=\"swbform-li\"><label for=\"fontSizePerspective\" class=\"swbform-label\">Tamaño de letra para perspectivas: </label>"
                + "<input id=\"fontSizePerspective\" name=\"fontSizePerspective\" type=\"text\" regExp=\"\\d+\""
                + "value=\"" + fontSizePerspective + "\" dojoType=\"dijit.form.ValidationTextBox\" promptMessage=\"Captura Tamaño.\" invalidMessage=\"El valor proporcionado debe ser númerico.\">");
        sb.append("</li>");
        sb.append("</ul>");
        
        sb.append("<label>Longitud máxima para el texto de:</label>");
        sb.append("<ul class=\"swbform-ul\">");
        sb.append("<li class=\"swbform-li\"><label for=\"amountObjective\" class=\"swbform-label\">Objetivos: </label>"
                + "<input id=\"amountObjective\" name=\"amountObjective\" type=\"text\" regExp=\"\\d+\""
                + "value=\"" + amountObjective + "\" dojoType=\"dijit.form.ValidationTextBox\" promptMessage=\"Captura longitud máxima.\" invalidMessage=\"El valor proporcionado debe ser númerico.\">");
        sb.append("</li>");

        sb.append("<li class=\"swbform-li\"><label for=\"amountDistinctive\" class=\"swbform-label\">Diferenciadores: </label>"
                + "<input id=\"amountDistinctive\" name=\"amountDistinctive\" type=\"text\" regExp=\"\\d+\""
                + "value=\"" + amountDistinctive + "\" dojoType=\"dijit.form.ValidationTextBox\" promptMessage=\"Captura longitud máxima.\" invalidMessage=\"El valor proporcionado debe ser númerico.\">");
        sb.append("</li>");
        
        sb.append("<li class=\"swbform-li\"><label for=\"amountPerspective\" class=\"swbform-label\">Perspectivas: </label>"
                + "<input id=\"amountPerspective\" name=\"amountPerspective\" type=\"text\" regExp=\"\\d+\""
                + "value=\"" + amountPerspective + "\" dojoType=\"dijit.form.ValidationTextBox\" promptMessage=\"Captura longitud máxima.\" invalidMessage=\"El valor proporcionado debe ser númerico.\">");
        sb.append("</li>");
        sb.append("</ul>");
        sb.append("</fieldset>");
        
        sb.append("<div id=\"configgral/" + id + "\" dojoType=\"dijit.TitlePane\" title=\"Configuración de vista para Perspectivas\" class=\"admViewProperties\" open=\"true\" duration=\"150\" minSize_=\"20\" splitter_=\"true\" region=\"bottom\">");
        sb.append("<fieldset>");

        sb.append("<label>Selecciona las perspectivas que quieras mostrar horizontalmente: </label>");
        Iterator<Perspective> itPerspectives = Perspective.ClassMgr.listPerspectives(ws);
        boolean containPerspectives = itPerspectives.hasNext();
        if (containPerspectives) {
            sb.append("<ul class=\"swbform-ul\">");

            while (itPerspectives.hasNext()) {
                Perspective perspective = itPerspectives.next();

                String title = perspective.getTitle(user.getLanguage()) == null ? perspective.getTitle()
                        : perspective.getTitle(user.getLanguage());

                String select = "";
                if (!arrayPerspectives.isEmpty() && arrayPerspectives.contains("perspective" + id + perspective.getId())) {
                    select = "checked";
                }

                sb.append("<li class=\"swbform-li\"><input id=\"perspective" + id + perspective.getId() + "\" name=\"perspective" + id + perspective.getId()
                        + "\" type=\"checkbox\" value=\"" + perspective.getId() + "\" "
                        + " data-dojo-type=\"dijit.form.CheckBox\" " + select + " class=\"swbform-label\">"
                        + "<label for=\"" + perspective.getId() + "\">" + title
                        + "</label></li>");
            }
            sb.append("</ul>");
        }


        sb.append("</fieldset>");
        sb.append("</div>");

        sb.append("<div id=\"configcol/" + id + "\" dojoType=\"dijit.TitlePane\" title=\"Estilo\"  open=\"false\" duration=\"150\" minSize_=\"20\" splitter_=\"true\" region=\"bottom\">");
        sb.append("<fieldset>");
        sb.append("<legend>Configurar Despliegue</legend>");


        itPerspectives = Perspective.ClassMgr.listPerspectives(ws);
        containPerspectives = itPerspectives.hasNext();
        if (containPerspectives) {
            sb.append("<ul class=\"swbform-ul\">");

            while (itPerspectives.hasNext()) {
                Perspective perspective = itPerspectives.next();

                Iterator<Theme> itListThemes = perspective.listThemes();
                String title = perspective.getTitle(user.getLanguage()) == null ? perspective.getTitle()
                        : perspective.getTitle(user.getLanguage());
                sb.append("<label>Datos de la perpectiva " + title + "</label>");
                if (itListThemes.hasNext()) {

                    sb.append("<ul class=\"swbform-ul\">");
                    while (itListThemes.hasNext()) {
                        Theme theme = itListThemes.next();
                        String strTheme = "bg_" + theme.getTitle() + "_" + theme.getId();
                        String valueTheme = base.getData(strTheme) == null ? ""
                                : base.getData(strTheme);
                        String strFontTheme = "ty_" + theme.getTitle() + "_" + theme.getId();
                        String valueFontTheme = base.getData(strFontTheme) == null ? ""
                                : base.getData(strFontTheme);
                        sb.append("<li class=\"swbform-li\"><label for=\"" + strTheme + "\" class=\"swbform-label\">Color de fondo para el tema " + theme.getTitle() + ": </label>"
                                + "<input id=\"" + strTheme + "\" name=\"" + strTheme + "\" type=\"text\" "
                                + "value=\"" + valueTheme + "\" dojoType=\"dijit.form.ValidationTextBox\">");
                        sb.append("</li>");

                        //este es para el color de letra
                        sb.append("<li class=\"swbform-li\"><label for=\"" + strFontTheme + "\" class=\"swbform-label\">Color de letra para el tema" + theme.getTitle() + ": </label>"
                                + "<input id=\"" + strFontTheme + "\" name=\"" + strFontTheme + "\" type=\"text\" "
                                + "value=\"" + valueFontTheme + "\" dojoType=\"dijit.form.ValidationTextBox\">");
                        sb.append("</li>");
                    }
                    sb.append("</ul>");
                }
                Iterator<DifferentiatorGroup> itDistinti = perspective.listDifferentiatorGroups();
                if (itDistinti.hasNext()) {
                    sb.append("<ul class=\"swbform-ul\">");
                    while (itDistinti.hasNext()) {
                        DifferentiatorGroup distinctive = itDistinti.next();

                        String strDistin = "bgd_" + distinctive.getTitle() + "_" + distinctive.getId();
                        String valueDistin = base.getData(strDistin) == null ? ""
                                : base.getData(strDistin);
                        String strFontDistin = "tyd_" + distinctive.getTitle() + "_" + distinctive.getId();
                        String valueFontDistin = base.getData(strFontDistin) == null ? ""
                                : base.getData(strFontDistin);
                        sb.append("<li class=\"swbform-li\"><label for=\"" + strDistin + "\" class=\"swbform-label\">Color de fondo para el diferenciador " + distinctive.getTitle() + ": </label>"
                                + "<input id=\"" + strDistin + "\" name=\"" + strDistin + "\" type=\"text\" "
                                + "value=\"" + valueDistin + "\" dojoType=\"dijit.form.ValidationTextBox\">");
                        sb.append("</li>");

                        //este es para el color de letra
                        sb.append("<li class=\"swbform-li\"><label for=\"" + strFontDistin + "\" class=\"swbform-label\">Color de letra para el diferenciador" + distinctive.getTitle() + ": </label>"
                                + "<input id=\"" + strFontDistin + "\" name=\"" + strFontDistin + "\" type=\"text\" "
                                + "value=\"" + valueFontDistin + "\" dojoType=\"dijit.form.ValidationTextBox\">");
                        sb.append("</li>");
                    }
                    sb.append("</ul>");
                }
            }
            sb.append("</ul>");
        }




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
    /*Carga la forma con los siguientes parámetros de configuración:
     1 Forma de mostrarse para cada perspectiva -- cambia
     * 
     2 FontSize para cada perspectiva -- para todas
     * 
     3 Color de letra para cada tema -- cambia
     4 BackgroundColor para cada tema -- cambia
     5 Color de letra para cada grupo de diferenciador -- cambia
     6 BackgroundColor para cada diferenciador -- cambia
     * 
     7 Cantidad máxima de letras a mostrar del título del objetivo -- para todas
     8 Cantidad máxima de letras a mostrar del título del diferenciador -- para todas
     9 Cantidad máxima de letras a mostrar del título del tema -- para todas
     10 Cantidad máxima de letras a mostrar del título del grupo de diferenciadores -- para todas
     11 Cantidad máxima de letras a mostrar del título de la perspectiva -- para todas
     
     * 12 Mostrar título horizontal para cada perspectiva -- cambia
     13 Color del texto para el título de la perspectiva -- cambia
     
     * 14 Alto de cada objetivo en forma horizontal -- para todas
     15 Alto de cada objetivo en forma vertical -- para todas
     16 Alto de cada diferenciador -- para todas
     17 Color relación objetivo a objetivo -- para todas
     18 Color relación objetivo a tema-- para todas
     19 Color relación tema a tema -- para todas
     20 Color relación tema a objetivo -- para todas
     */

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
