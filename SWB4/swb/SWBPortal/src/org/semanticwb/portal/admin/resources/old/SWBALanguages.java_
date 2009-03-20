/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.Language;
import org.semanticwb.model.Resource;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;


/**
 *
 * @author juan.fernandez
 */
public class SWBALanguages extends GenericResource{
    
    private Logger log=SWBUtils.getLogger(SWBALanguages.class);
    public String strRscType=SWBALanguages.class.getName();
    Resource base=null;

    
    /** Método que despliega la parte pública del recurso
     * @param request Objeto HttpServletRequest
     * @param response Objeto HttpServletResponse
     * @param paramRequest Objeto WBActionResponse
     * @throws SWBResourceException Excepción del Aplication Framework
     * @throws IOException Excepción de Entrada/Salida
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
    
        PrintWriter out = response.getWriter();
        String act = request.getParameter("act");
        String strConfirm=request.getParameter("confirm");
        String strIdioma=request.getParameter("id");
        String strTm=request.getParameter("tm");
        String URL=paramRequest.getAdminTopic().getWebSite().getWebPage("WBAd_sysi_LasnguagesInfo").getUrl();
        if (strConfirm!=null && strConfirm.equals("removed")) {
            try {
                out.println("<script>");
                out.println("top.frames['4'].document.write('"+paramRequest.getLocaleString("jsLangRemoved")+"')");
                out.println("wbTree_remove();");
                out.println("</script>");

            } catch (SWBResourceException e) {
                log.error("Error on method getForma() resource" + " " + strRscType + " " + "with id" + " " + getResourceBase().getId(), e);
            }
            return;
        }
        if (strConfirm!=null && strConfirm.equals("updated")) {
            try {
                out.println("<script>");
                out.println("top.frames['4'].document.write('"+paramRequest.getLocaleString("jsLangUpdated")+"')");
                out.println("wbTree_executeAction('gotopath."+strTm+".languages');");
                out.println("wbTree_reload();");
                out.println("wbTree_executeAction('gotopath."+strTm+".languages."+strIdioma+"');");
                out.println("</script>");
            } catch (SWBResourceException e) {
                log.error("Error on method getForma() resource" + " " + strRscType + " " + "with id" + " " + getResourceBase().getId(), e);
            }
            return;
        }
        if (strConfirm!=null && strConfirm.equals("added")) {
            try {
                out.println("<script>");
                out.println("top.frames['4'].document.write('"+paramRequest.getLocaleString("jsLangAdded")+"')");
                out.println("wbTree_executeAction('gotopath."+strTm+".languages');");
                out.println("wbTree_reload();");
                out.println("wbTree_executeAction('gotopath."+strTm+".languages."+strIdioma+"');");
                out.println("</script>");
            } catch (SWBResourceException e) {
                log.error( "Error on method getForma() resource" + " " + strRscType + " " + "with id" + " " + getResourceBase().getId(), e);
            }
            return;
        }
        if (strConfirm!=null && strConfirm.equals("notadded")) {
            return;
        }
        if(act==null) act="";
        if(act.equals(""))
            getInfo(request, paramRequest, response);
        if (act.equals("add") || act.equals("edit"))
            getForma(request, paramRequest, response);
        if (act.equals("remove")) {
            String strAssType=hasAssociations(strTm,strIdioma,paramRequest.getUser().getId());
            if (strAssType!=null && request.getParameter("ass")==null) {
                out.println("<script>");
                out.println("top.frames['work'].location='"+URL+"?act=associations&tm="+strTm+"&id="+strIdioma+"&ass="+strAssType+"';");
                out.println("</script>");
            }
            else {
                out.println("<form name=\"Idioma\" method=\"post\" action=\"\"> ");
                try {
                    getJavaScript(paramRequest, response);
                }
                catch (SWBResourceException e) {
                log.error( "Error on method getForma() resource" + " " + strRscType + " " + "with id" + " " + getResourceBase().getId(), e);
                }
                out.println("<input type=hidden name=id value=\""+strIdioma+"\"> \n");
                out.println("<input type=hidden name=tm value=\""+strTm+"\"> \n");
                out.println("<input type=hidden name=act2 value=\"remove\"> \n");
                out.println("<script language=\"JavaScript\" type=\"text/JavaScript\"> \n");
                out.println("send(\"save\",\"remove\");");
                out.println("</script> \n");
                out.println("</form> ");
            }

        }
        if (act.equals("associations"))
            out.println(getLanguageAssociations(request, response, paramRequest));
    }

    
    
    
    /** Método en donde se procesan las diferentes acciones del recurso
     * @param request Objeto HttpServletRequest
     * @param response Objeto WBActionResponse
     * @throws SWBResourceException Excepción del Aplication Framework
     * @throws IOException Excepción de Entrada/Salida
     */
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String strWBAction=request.getParameter("act2");
        String strTm=request.getParameter("tm");
        String strLang=request.getParameter("id");
        String strLangName=request.getParameter("nom_idioma");
        User user = response.getUser();
        Language recLang=null;
        String iId="0";
        if (strWBAction!=null && strWBAction.equals("add")) {
            recLang = SWBContext.getWebSite(strTm).getLanguage(strLang);
            if (recLang==null) {
                WebSite ws = SWBContext.getWebSite(strTm);
                recLang = ws.createLanguage(strLang);
                recLang.setCreator(user);
                recLang.setTitle(strLangName);
                response.setRenderParameter("confirm","added");
                iId=recLang.getId();
                response.setRenderParameter("id",iId);
            }
            else {
                response.setRenderParameter("confirm","notadded");
                iId=recLang.getId();
                response.setRenderParameter("id",iId);
            }
        }
        else if (strWBAction!=null && strWBAction.equals("edit")) {
            try{
                WebSite ws = SWBContext.getWebSite(strTm);
                Language lang = ws.getLanguage(strLang);
                lang.setTitle(strLangName);
                lang.setModifiedBy(user);
                iId=lang.getId();
                response.setRenderParameter("id",iId);
                response.setRenderParameter("confirm","updated");
            }
            catch(Exception e)
            {
                log.error("Error al tratar de actualiar el lenguaje.",e);
            }
        }
        else if (strWBAction!=null && strWBAction.equals("remove")) {
            recLang=SWBContext.getWebSite(strTm).getLanguage(strLang);
            boolean isRemoved=false;
            try{
                WebSite ws = SWBContext.getWebSite(strTm);
                ws.removeLanguage(recLang.getId());
                ws.setModifiedBy(user);
                isRemoved=true;
            }
            catch(Exception e)
            {
                isRemoved=false;
            }
            if (isRemoved) {
                response.setRenderParameter("confirm","removed");
                response.setRenderParameter("id",iId);
            }
            else {
                response.setRenderParameter("lang",strLang);
                response.setRenderParameter("tm",strTm);
                response.setRenderParameter("confirm","notremoved");
            }
        }
        response.setRenderParameter("tm",strTm);
        response.setAction("view");
        response.setMode(response.Mode_VIEW);
    }

    private String hasAssociations(String strTm,String strLang,String strUser) {
        String strAss=null;
        Language recLang=SWBContext.getWebSite(strTm).getLanguage(strLang);
        
        //TODO: Falta implementar asociaciones de lenguajes
        //HashMap hLangAss=langSrv.checkLanguageAssociations(strTm,recLang.getId(),strUser);
        Iterator itLangAss=recLang.listRelatedObjects();
        //HashMap hLangAss=new HashMap();
        //
        if (itLangAss.hasNext()) {
            //Iterator itLangAss=hLangAss.keySet().iterator();
            while(itLangAss.hasNext()){
                GenericObject obj = (GenericObject)itLangAss.next();
                strAss=obj.getId();
            }
        }
        return strAss;
    }

    private String getLanguageAssociations (HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException{
        PrintWriter out=response.getWriter();
        StringBuffer strBuff=new StringBuffer();
        String strTm=request.getParameter("tm");
        String strLang=request.getParameter("id");
        String strUser=paramRequest.getUser().getId();
        String strAssType=request.getParameter("ass");
        Language recLang=SWBContext.getWebSite(strTm).getLanguage(strLang);
        //TODO: Falta implementar asociaciones de lenguajes
        //HashMap hLangAss=langSrv.checkLanguageAssociations(strTm,recLang.getId(),strUser);
        HashMap hLangAss=new HashMap();
        //Iterator itLangAss=hLangAss.keySet().iterator();
        Iterator itLangAss=recLang.listRelatedObjects();
        //TODO: falta implementar
        out.println("<form name=\"Idioma\" method=\"post\" action=\"\" target=4> ");
        out.println("<div class=box>");
        out.println("<table width=\"100%\"  border=\"0\" cellpadding=\"10\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
        while(itLangAss.hasNext()){
            String strAss=(String)itLangAss.next();
            out.println("<tr>");
            out.println("<td class=\"valores\">");
            if(strAss.equals("Tm")) {// El sitio esta utilizando el lenguaje
                out.println(paramRequest.getLocaleString("frmLanguageinSite")+" "+hLangAss.get(strAss)+"<br>");
            }else if(strAss.startsWith("Rule")){ // Reglas estan utilizando el lenguaje
                out.println(paramRequest.getLocaleString("frmLanguageinRule")+" "+hLangAss.get(strAss)+"<br>");
            }else if(strAss.equals("Topics")){ //Esta siendo utilizado en títulos de topicos
                Iterator itTopics=((ArrayList)hLangAss.get(strAss)).iterator();
                while(itTopics.hasNext()){
                    String strTopic=(String)itTopics.next();
                    if (strTopic.indexOf("IDM")>0)
                        out.println(paramRequest.getLocaleString("frmLanguageinLanguage")+" "+strTopic+"<br>");
                    else
                        out.println(paramRequest.getLocaleString("frmLanguageinTopic")+" "+strTopic+"<br>");
                }
            }else if(strAss.equals("TopicOccs")){ //Esta siendo utilizado en Descripciones de topicos
                Iterator itTopicsDescr=((ArrayList)hLangAss.get(strAss)).iterator();
                while(itTopicsDescr.hasNext()){
                    String strTopicDescrOcc=(String)itTopicsDescr.next();
                    out.println(paramRequest.getLocaleString("frmLanguageinDesc")+" "+strTopicDescrOcc+"<br>");
                }
            }
            out.println("</td>");
            out.println("</tr>");
        }
        if (strAssType!=null && !strAssType.equals("Tm") && !strAssType.startsWith("Rule")) {
            try {
                getJavaScript(paramRequest, response);
            }
            catch (SWBResourceException e) {
            log.error( "Error on method getForma() resource" + " " + strRscType + " " + "with id" + " " + getResourceBase().getId(),e);
            }
            out.println("<tr>");
            out.println("<td class=\"valores\">");
            out.println("<input type=hidden name=id value=\""+strLang+"\"> \n");
            out.println("<input type=hidden name=tm value=\""+strTm+"\"> \n");
            out.println("<input type=hidden name=act2 value=\"remove\"> \n");
            out.println("<script language=\"JavaScript\" type=\"text/JavaScript\"> \n");
            String URL=paramRequest.getAdminTopic().getWebSite().getWebPage("WBAd_sysi_LasnguagesInfo").getUrl();
            out.println("function remove (){");
            out.println("top.frames['3'].location='"+URL+"?act=remove&tm="+strTm+"&id="+strLang+"&ass="+strAssType+"';");
            out.println("}");
            out.println("</script> \n");
            out.println("<input type=hidden name=id value=\""+strLang+"\"> \n");
            out.println("<input type=hidden name=tm value=\""+strTm+"\"> \n");
            out.println("<input type=hidden name=act value=\"remove\"> \n");
            out.println("<input type=hidden name=ass value=\""+strAssType+"\"> \n");
            out.println("<input type=button  class=\"boton\" name=\""+paramRequest.getLocaleString("btnRemove")+"\" onClick='javascript:remove();' value=\""+paramRequest.getLocaleString("btnRemove")+"\"> ");
            out.println("</td>");
            out.println("</tr>");
        }
        out.println("</table>");
        out.println("</div>");
        out.println("</form>");
        return strBuff.toString();
    }

    private void getJavaScript(SWBParamRequest paramsRequest, HttpServletResponse response) throws IOException, SWBResourceException{
        PrintWriter out=response.getWriter();
        out.println("<script language=\"JavaScript\" type=\"text/JavaScript\"> ");
        out.println("function send(acc) {");
        SWBResourceURL urlResAct=paramsRequest.getActionUrl();
        out.println("    document.Idioma.action.value = acc;");
        out.println("    if (acc=='save') {");
        out.println("        if (!validateForm(document.Idioma,document.Idioma.id.value))");
        out.println("        return;");
        out.println("        document.Idioma.action='"+urlResAct+"';");
        out.println("    }");
        out.println("    document.Idioma.submit();");
        out.println("}");

        out.println("function completeFields() {");
        out.println("    var aux = document.Idioma.txtIdioma.options[document.Idioma.txtIdioma.selectedIndex].value;");
        out.println("    var aux2 = document.Idioma.txtIdioma.options[document.Idioma.txtIdioma.selectedIndex].text;");
        out.println("    document.Idioma.id.value = aux;");
        out.println("    document.Idioma.nom_idioma.value = aux2;");
        out.println("}");

        out.println(" function validateForm(_f,id) {");
        out.println("    var _regExp=/select/i;");
        out.println("    for (i=0; i<_f.elements.length; i++ ) { ");
        out.println("        if(_f.elements[i]!=undefined) {");
        // Valida los objetos de la forma tipo text o textarea
        out.println("            if(_f.elements[i].type==\"text\" || _f.elements[i].type==\"textarea\") {");
        out.println("                if (_f.elements[i].value=='') {");
        out.println("                    alert ('"+paramsRequest.getLocaleString("jsEmptyField")+"');");
        out.println("                    _f.elements[i].focus();");
        out.println("                    return false;");
        out.println("                }");
        out.println("            }");
        // Valida los objetos de la forma tipo select
        out.println("            if(!id=='create') {");
        out.println("                if(_f.elements[i].type.match(_regExp)) {");
        out.println("                    alert (_f.elements[i].name);");
        out.println("                    if (_f.elements[i].value=='') {");
        out.println("                        _f.elements[i].focus();");
        out.println("                        return false;");
        out.println("                    }");
        out.println("                }");
        out.println("            }");
        out.println("        }");
        out.println("    }");
        out.println("    return true;");
        out.println(" }");
        out.println("</script>");
    }

    private void getForma(HttpServletRequest request, SWBParamRequest paramsRequest, HttpServletResponse response) throws IOException, SWBResourceException {
        PrintWriter out=response.getWriter();
        String wbAction=request.getParameter("act");
        String strTm=request.getParameter("tm");
        WebSite tm = SWBContext.getWebSite(strTm);
        if(wbAction==null)
            wbAction="edit";
        String strDisable="";
        Iterator<Language> htIdioma=tm.listLanguages();
        String strIdioma=request.getParameter("id");
        String iIdioma="0";
        if (strIdioma!=null && !strIdioma.equals(""))
            iIdioma=strIdioma;
        String strCadena="";
        String strNombre="";
        String strTmId=null;
        out.println("<form name=\"Idioma\" method=\"post\" action=\"\"> ");
        try {
            getJavaScript(paramsRequest, response);
        }
        catch (SWBResourceException e) {
            log.error("Error on method getForma() resource" + " " + strRscType + " " + "with id" + " " + getResourceBase().getId(), e);
        }

        if (wbAction!=null && !wbAction.equals("remove")) {
            out.println("<div class=box>");
            out.println("<table width=\"100%\"  border=\"0\" cellpadding=\"10\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
            out.println("<tr>");
            out.println("<td class=\"valores\">");
            if (strIdioma!=null) {
                try {
                    Language rec=SWBContext.getWebSite(strTm).getLanguage(iIdioma);
                    strCadena=rec.getId();
                    strNombre=rec.getTitle();
                    strTmId=rec.getWebSite().getId();
                }
                catch (Exception e){
                    log.error("Error on method getForma() resource" + " " + strRscType + " " + "with id" + " " + getResourceBase().getId(), e);
                    strIdioma=null;
                }
            }
            if (strIdioma==null && !wbAction.equals("add")){
                out.println("<tr align=\"center\" bgcolor=\"\"> ");
                out.println("<td colspan=\"2\"> ");
                
                out.println("<select name=\"txtIdioma\" class=\"combo1\" onChange=\"completeFields()\"> ");
                while (htIdioma.hasNext()) {
                    Language rec=htIdioma.next();
                    out.println(" <option value=\""+rec.getId()+"\">"+rec.getTitle()+"</option> ");
                }
                out.println("</select> ");
                out.println("</td> ");
                out.println("</tr> ");
            }
            if (strIdioma==null && wbAction.equals("add")){
                Locale[] locales=Locale.getAvailableLocales();
                java.util.TreeMap map=new java.util.TreeMap(java.text.Collator.getInstance(new Locale(paramsRequest.getUser().getLanguage())));
                for (int i=0; i < locales.length; i++ ) {
                    Locale lc=locales[i];
                    if(lc.getCountry().length()==0)
                        map.put(lc.getDisplayName(new Locale(paramsRequest.getUser().getLanguage())), lc);
                }
                out.println("<tr align=\"center\" bgcolor=\"\"> ");
                out.println("<td align=\"right\" class=\"datos\">"+paramsRequest.getLocaleString("msgListLang")+":</td> ");
                out.println("<td align=\"left\"> ");
                out.println("<select name=\"txtIdioma\" class=\"combo1\" onChange=\"completeFields()\" size=\"11\"> ");
                Iterator it=map.values().iterator();
                while(it.hasNext())
                {
                    Locale lc=(Locale)it.next();
                    if(lc.getCountry().length()==0)
                        out.println(" <option value=\""+lc.getLanguage()+"\">"+lc.getDisplayName(new Locale(paramsRequest.getUser().getLanguage()))+"</option> ");
                }
                out.println("</select>");
                out.println("</td> ");
                out.println("</tr> ");
            }
            // Genera la lista de TopicMaps

            try {
                if (strTm==null && strTm.equals("")) {
                    Iterator<WebSite> enumTopicMaps = SWBContext.listWebSites();
                    out.println("<tr align=\"center\" bgcolor=\"\"> ");
                    out.println("<td align=\"right\" class=\"txtcomb\">"+paramsRequest.getLocaleString("frmSite")+":</td> ");
                    out.println("<td align=\"left\" class=\"txtcomb\"> ");
                    out.println("<select name=\"tm\" class=\"combo1\" >");
                    while (enumTopicMaps.hasNext()) {
                        WebSite recMap = enumTopicMaps.next();
                        if (recMap.isActive() && !recMap.getId().equals("WBAdmin")) {
                            if (strTmId!=null && strTmId.equals(recMap.getId()))
                                out.println(" <option value=\""+recMap.getId()+"\" selected>"+recMap.getTitle()+"</option> ");
                            else
                                out.println(" <option value=\""+recMap.getId()+"\">"+recMap.getTitle()+"</option> ");
                        }
                    }
                    out.println("</select>");
                    out.println("</td> ");
                    out.println("</tr> ");
                }
                else {
                    out.println("<input type=hidden name=tm value=\""+strTm+"\">");
                }
                if (wbAction.equals("edit"))
                    strDisable="readonly";
                out.println("<tr bgcolor=\"\"> ");
                out.println("<td align=\"right\" class=\"datos\">"+paramsRequest.getLocaleString("frmName")+":</td> ");
                out.println("<td><input name=\"nom_idioma\" type=\"text\" class=\"combo1\" size=\"50\" maxlength=\"100\" value=\""+strNombre+"\" "+strDisable+"></td> ");
                out.println("</tr> ");
                out.println("<tr bgcolor=\"\"> ");
                out.println("<td align=\"right\" class=\"datos\">"+paramsRequest.getLocaleString("frmShortName")+":</td> ");
                out.println("<td><input name=\"id\" type=\"text\" class=\"combo1\" size=\"50\" maxlength=\"40\" value=\""+strCadena+"\" "+strDisable+"></td> ");
                out.println("</tr> ");
                out.println("<tr align=\"center\" bgcolor=\"\"> ");
                out.println("<td colspan=\"2\"> ");
                out.println("<input type=button  class=\"boton\" name=\""+paramsRequest.getLocaleString("btnAccept")+"\" onClick='javascript:send(\"save\");' value=Guardar> ");
                out.println("<input type=hidden name=act2 value=\""+wbAction+"\"> ");
                out.println("</td> ");
                out.println("</tr> ");
                if (strIdioma==null && !wbAction.equals("add")){
                    out.println("<script language=\"JavaScript\" type=\"text/JavaScript\"> ");
                    out.println("completeFields();");
                    out.println("</script>");
                }
            } catch (SWBResourceException e) {
                log.error( "Error on method getForma() resource" + " " + strRscType + " " + "with id" + " " + getResourceBase().getId(),e);
            }
            out.println("</table>");
            out.println("</div>");
        }
        out.println("</form>");
    }

    private void getInfo(HttpServletRequest request, SWBParamRequest paramsRequest, HttpServletResponse response) throws IOException, SWBResourceException {
        PrintWriter out=response.getWriter();
        String strTm=request.getParameter("tm");
        String strIdioma = request.getParameter("id");
        String strCadena="";
        String strNombre="";
        String strTmId="";
        String strLastUpdate="";
        out.println("<div class=box>");
        out.println("<table width=\"100%\"  border=\"0\" cellpadding=\"10\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
        if (strIdioma!=null) {
            try {
                String iIdioma = strIdioma;
                Language rec=SWBContext.getWebSite(strTm).getLanguage(iIdioma);
                strLastUpdate = rec.getUpdated().toString();
                strCadena=rec.getId();
                strNombre=rec.getTitle();
                strTmId=rec.getWebSite().getId();
            }
            catch (Exception e){
                log.error("Error on method getInfo() resource" + " " + strRscType + " " + "with id" + " " + getResourceBase().getId(),e);
                strIdioma=null;
            }
        }
        out.println("<tr > ");
        out.println("<td align=\"right\" class=\"datos\" width=150>"+paramsRequest.getLocaleString("frmSite")+":</td> ");
        out.println("<td class=\"valores\"> ");
        out.println(SWBContext.getWebSite(strTmId).getTitle());
        out.println("</td> ");
        out.println("</tr> ");
        out.println("<tr bgcolor=\"\"> ");
        out.println("<td align=\"right\" class=\"datos\" width=150>"+paramsRequest.getLocaleString("frmName")+":</td> ");
        out.println("<td class=\"valores\">"+strNombre+"</td> ");
        out.println("</tr> ");
        out.println("<tr bgcolor=\"\"> ");
        out.println("<td align=\"right\" class=\"datos\" width=150>"+paramsRequest.getLocaleString("frmShortName")+":</td> ");
        out.println("<td class=\"valores\">"+strCadena+"</td> ");
        out.println("</tr> ");
        out.println("<tr bgcolor=\"\"> ");
        out.println("<td align=\"right\" class=\"datos\" width=150>"+paramsRequest.getLocaleString("frmLastUpdate")+":</td> ");
        out.println("<td class=\"valores\">"+strLastUpdate+"</td> ");
        out.println("</tr> ");
        out.println("</table></div>");
    }


}
