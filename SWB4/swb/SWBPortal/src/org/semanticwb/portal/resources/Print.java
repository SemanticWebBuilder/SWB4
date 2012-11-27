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
package org.semanticwb.portal.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.Template;
import org.semanticwb.portal.TemplateImp;
import org.semanticwb.portal.admin.admresources.util.WBAdmResourceUtils;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.api.SWBResourceURLImp;
import org.semanticwb.portal.util.FileUpload;

// TODO: Auto-generated Javadoc
/** Objeto que se encarga de desplegar y administrar una interfaz parar imprimir topicos bajo ciertos
 * criterios(configuraci?n de recurso).
 *
 * Object that is in charge to unfold and to administer an interface to print topics under certain
 * criteria (resource configuration).
 *
 * @author : Vanessa Arredondo N??ez
 * @Modified: Jorge Jiménez
 * @version 1.0
 */
public class Print extends GenericResource
{

    /** The log. */
    private static Logger log = SWBUtils.getLogger(Print.class);

    /** The work path. */
    String workPath = "";

    /** The web work path. */
    String webWorkPath = "/work";

    /** The windowconf. */
    String windowconf="";

    /** The adm res utils. */
    WBAdmResourceUtils admResUtils=new WBAdmResourceUtils();

    /**
     * Creates a new instance of Print.
     */
    public Print() {
    }

    /**
     * Sets the resource base.
     *
     * @param base the new resource base
     */
    @Override
    public void setResourceBase(Resource base)
    {
        try
        {
            super.setResourceBase(base);
            workPath = (String)SWBPortal.getWorkPath() +  base.getWorkPath();
            webWorkPath = (String) SWBPortal.getWebWorkPath() +  base.getWorkPath();
        }
        catch(Exception e) { log.error("Error while setting resource base: "+base.getId() +"-"+ base.getTitle(), e);  }
    }


    /**
     * Gets the params.
     *
     * @param request the request
     * @param url the url
     * @return the params
     */
    public void getParams(javax.servlet.http.HttpServletRequest request, SWBResourceURLImp url)
    {
        if(request.getParameterMap().size()>0)
        {
            Map q=request.getParameterMap();
            if(q!=null)
            {
                Set s=q.keySet();
                if(s!=null)
                {
                    Iterator it=s.iterator();
                    while(it.hasNext())
                    {
                        String key=(String)it.next();
                        //if(!key.equals("page")) //Comentado ya que segun yo debe estar afectando el recurso content en su impresión cuando tiene páginación (Jorge Jiménez-3/Mayo/2010)
                        //{
                            url.setParameter(key,(String[])q.get(key));
                        //}

                    }
                }
            }
        }
    }

    /**
     * Do view.
     *
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws SWBResourceException the sWB resource exception
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String action = null != request.getParameter("imp_act") && !"".equals(request.getParameter("imp_act").trim()) ? request.getParameter("imp_act").trim() : "imp_step1";
        setWindowConf();
        if("imp_step2".equals(action))  {
            printByPage(request, response, paramRequest); // Imprimir por p?gina o documento completo
        }
        else if("imp_step3".equals(action))  {
            printContent(request, response, paramRequest); // Nueva ventana con el contenido a imprimir
        }
        else
        {
            // Objeto(imagen/bot?n) para invocar la nueva ventana
            StringBuilder ret = new StringBuilder("");
            Resource base=getResourceBase();

            SWBResourceURLImp url=new SWBResourceURLImp(request, base, paramRequest.getWebPage(),SWBResourceURL.UrlType_RENDER);
            url.setResourceBase(base);
            url.setMode(url.Mode_VIEW);
            url.setWindowState(url.WinState_MAXIMIZED);
            url.setTopic(paramRequest.getWebPage());
            url.setCallMethod(paramRequest.Call_DIRECT);
            if(request.getParameter("page")!=null && !request.getParameter("page").trim().equals(""))
            {
                url.setParameter("imp_act","imp_step2");
                url.setParameter("page", request.getParameter("page"));
            }
            else
            {
                url.setParameter("imp_act","imp_step3");
                //url.setParameter("page", "0");
            }
            getParams(request, url);
            String onclick="javascript:window.open('" + url.toString().replace('\'', '-') +"','_newimp','" + getWindowConf() + "'); return false;";

            synchronized (ret)
            {
                if (!"".equals(base.getAttribute("img", "").trim()))
                {
                    ret = ret.append("\n<a href=\"");
                    ret = ret.append(onclick);
                    ret = ret.append("\">");
                    ret = ret.append("<img onClick=\"");
                    ret = ret.append(onclick);
                    ret = ret.append("\"");
                    ret = ret.append(" src=\"");
                    ret = ret.append(webWorkPath);
                    ret = ret.append("/");
                    ret = ret.append(base.getAttribute("img").trim());
                    ret = ret.append("\"");
                    if (!"".equals(base.getAttribute("alt","").trim())) {
                        ret = ret.append(" alt=\"");
                        ret = ret.append(base.getAttribute("alt").trim().replaceAll("\"", "&#34;"));
                        ret = ret.append("\"");
                    }
                    ret = ret.append(" border=0></a>");
                }
                else if (!"".equals(base.getAttribute("btntexto", "").trim()))
                {
                    ret = ret.append("\n<form name=\"frmImprimir\" action=\"\">");
                    ret = ret.append("\n<input type=button name=btnImprimir onClick=\"");
                    ret = ret.append(onclick);
                    ret = ret.append("\" value=");
                    ret = ret.append("\"");
                    ret = ret.append(base.getAttribute("btntexto").trim().replaceAll("\"", "&#34;"));
                    ret = ret.append("\"");
                    if (!"".equals(base.getAttribute("blnstyle", "").trim())) {
                        ret = ret.append(" style=\"");
                        ret = ret.append(base.getAttribute("blnstyle").trim().replaceAll("\"", "&#34;"));
                        ret = ret.append("\"");
                    }
                    ret = ret.append("\n></form>");
                }
                else
                {
                    ret = ret.append("\n<a href=\"");
                    ret = ret.append(onclick);
                    ret = ret.append("\" onClick=\"");
                    ret = ret.append(onclick);
                    ret = ret.append("\"");
                    if (!"".equals(base.getAttribute("blnstyle", "").trim())) {
                        ret = ret.append(" style=\"" );
                        ret = ret.append(base.getAttribute("blnstyle").trim().replaceAll("\"", "&#34;"));
                        ret = ret.append("\"");
                    }
                    ret = ret.append(">");
                    if (!"".equals(base.getAttribute("lnktexto", "").trim())) {
                        ret = ret.append(base.getAttribute("lnktexto").trim());
                    }
                    else {
                        ret = ret.append(paramRequest.getLocaleString("msgPrint"));
                    }
                    ret = ret.append("</a>");
                }
            }
            PrintWriter out = response.getWriter();
            out.println(ret.toString());
        }
    }

    /**
     * Prints the by page.
     *
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws SWBResourceException the sWB resource exception
     */
    public void printByPage(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        StringBuilder ret = new StringBuilder("");
        SWBResourceURLImp url=new SWBResourceURLImp(request, getResourceBase(), paramRequest.getWebPage(),SWBResourceURL.UrlType_RENDER);
        url.setResourceBase(getResourceBase());
        url.setMode(url.Mode_VIEW);
        url.setWindowState(url.WinState_MAXIMIZED);
        url.setTopic(paramRequest.getWebPage());
        url.setCallMethod(paramRequest.Call_DIRECT);

        getParams(request, url);

        url.setParameter("imp_act","imp_step3");

        ret = ret.append("\n<script language=\"JavaScript\">");
        ret = ret.append("\nfunction wbPrint(frm)");
        ret = ret.append("\n{");
        ret = ret.append("\n   var pOk=0;");
        ret = ret.append("\n   for(var i=0; i < frm.page.length ;i++)");
        ret = ret.append("\n   {");
        ret = ret.append("\n       if(frm.page[i].checked) pOk=frm.page[i].value;");
        ret = ret.append("\n   }");
        ret = ret.append("\n   window.open('");
        ret = ret.append(url.toString());
        ret = ret.append("&amp;page='+pOk");
        ret = ret.append(",'_preview','");
        ret = ret.append(getWindowConf());
        ret = ret.append("');");
        ret = ret.append("\n   window.self.close();");
        ret = ret.append("\n   return true;");
        ret = ret.append("\n}");
        ret = ret.append("\n</script>");
        ret = ret.append("\n<form name=frmPrint>");
        ret = ret.append("\n<table width=\"100%\" height=\"100%\" border=\"1\"><tr><td align=\"center\" valign=\"center\">");
        ret = ret.append("\n<table border=\"0\" cellspacing=\"5\" cellpadding=\"5\">");
        ret = ret.append("\n<tr><td>");
        ret = ret.append("\n<input type=radio  name=page value=\"0\" checked> " );
        ret = ret.append(paramRequest.getLocaleString("msgAllDocument"));
        ret = ret.append("\n</td></tr><tr><td>");
        ret = ret.append("\n<input type=radio  name=page value=\"");
        if(request.getParameter("page")!=null && !request.getParameter("page").trim().equals("")) {
            ret = ret.append(request.getParameter("page"));
        }
        else{
            ret = ret.append("1");
        }
        ret = ret.append("\"> ");
        ret = ret.append(paramRequest.getLocaleString("msgCurrentPage"));
        ret = ret.append("\n</td></tr><tr><td align=center>");
        ret = ret.append("\n<input type=button name=btnPrint onClick=\"wbPrint(this.form)\" value=\"");
        ret = ret.append(paramRequest.getLocaleString("btnPrint"));
        ret = ret.append("\">");
        ret = ret.append("\n</td></tr>");
        ret = ret.append("\n</table>");
        ret = ret.append("\n</td></tr></table>");
        ret = ret.append("\n</form>");
        response.getWriter().print(ret.toString());

//        response.setContentType("text/html");
//         base=getResourceBase();
//        if (!"".equals(base.getAttribute("template", "").trim()))
//        {
//            try
//            {
//                Template tpl = TemplateMgr.getInstance().getTemplate(base.getTopicMapId(), Integer.parseInt(base.getAttribute("template").trim()));
//                if(tpl!=null) tpl.build(request, response, response.getWriter(), reqParams.getUser(), reqParams.getWebPage(),true,ret.toString());
//            }
//            catch(Exception e) { AFUtils.log(e,"Error while getting topic to print: "+base.getId() +"-"+ base.getTitle(),true); }
//        }
    }

    /**
     * Prints the content.
     *
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws SWBResourceException the sWB resource exception
     */
    public void printContent(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        //PrintWriter out=response.getWriter();
        response.setContentType("text/html");
        Resource base=getResourceBase();
        if (!"".equals(base.getAttribute("template", "").trim()))
        {
            try
            {
                Template tpl =paramRequest.getWebPage().getWebSite().getTemplate(base.getAttribute("template").trim());
                if(request.getParameter("page")!=null) {
                    request.setAttribute("page",request.getParameter("page"));
                }
                if(tpl!=null) {
                       //out.println("<script language=\"JavaScript\">");
                       //out.println("window.self.print();");
                       //out.println("</script>");
                    ((TemplateImp)SWBPortal.getTemplateMgr().getTemplateImp(tpl)).build(request, response, paramRequest.getUser(), paramRequest.getWebPage());
                }
            }
            catch(Exception e) { log.error("Error while getting topic to print: "+base.getId() +"-"+ base.getTitle(), e); }
        }
    }

    /**
     * Sets the window conf.
     */
    private void setWindowConf()
    {
        Resource base=getResourceBase();
        this.windowconf  = "menubar="+ base.getAttribute("menubar", "no").trim();
        this.windowconf += ",toolbar="+ base.getAttribute("toolbar", "no").trim();
        this.windowconf += ",status="+ base.getAttribute("status", "no").trim();
        this.windowconf += ",location="+ base.getAttribute("location", "no").trim();
        this.windowconf += ",directories="+ base.getAttribute("directories", "no").trim();
        this.windowconf += ",scrollbars="+ base.getAttribute("scrollbars", "no").trim();
        this.windowconf += ",resizable="+ base.getAttribute("resizable", "no").trim();
        this.windowconf += ",width="+ base.getAttribute("width", "640").trim();
        this.windowconf += ",height="+ base.getAttribute("height", "480").trim();
        this.windowconf += ",top="+ base.getAttribute("top", "10").trim();
        this.windowconf += ",left="+ base.getAttribute("left", "10").trim();
    }

    /**
     * Gets the window conf.
     *
     * @return the window conf
     * @return
     */
    private String getWindowConf()
    {
        return this.windowconf;
    }

    /**
     * Do admin.
     *
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws SWBResourceException the sWB resource exception
     */
    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        Resource base=getResourceBase();
        StringBuilder ret = new StringBuilder("");
        String msg=paramRequest.getLocaleString("msgUndefinedOperation");
        String action = null != request.getParameter("act") && !"".equals(request.getParameter("act").trim()) ? request.getParameter("act").trim() : paramRequest.getAction();

        if(action.equals("add") || action.equals("edit"))
            ret = ret.append(getForm(request, paramRequest));
        else if(action.equals("update"))
        {
            FileUpload fup = new FileUpload();
            try
            {
                fup.getFiles(request, response);
                String value = null != fup.getValue("template") && !"".equals(fup.getValue("template").trim()) ? fup.getValue("template").trim() : null;
                if (value!=null)
                {
                    base.setAttribute("template", value);
                    value = null != fup.getValue("noimg") && !"".equals(fup.getValue("noimg").trim()) ? fup.getValue("noimg").trim() : "0";
                    if ("1".equals(value) && !"".equals(base.getAttribute("img", "").trim()))
                    {
                        SWBUtils.IO.removeDirectory(workPath + "/" + base.getAttribute("img").trim());
                        base.removeAttribute("img");
                    }
                    else
                    {
                        value = null != fup.getFileName("img") && !"".equals(fup.getFileName("img").trim()) ? fup.getFileName("img").trim() : null;
                        if (value!=null)
                        {
                            String file = admResUtils.getFileName(base, value);
                            if (file != null && !file.trim().equals(""))
                            {
                                if (!admResUtils.isFileType(file, "bmp|jpg|jpeg|gif|png")){
                                    msg=paramRequest.getLocaleString("msgErrFileType") +" <i>bmp, jpg, jpeg, gif, png</i>: " + file;
                                }
                                else
                                {
                                    if (admResUtils.uploadFile(base, fup, "img")){
                                        base.setAttribute("img", file);
                                    }
                                    else {
                                        msg=paramRequest.getLocaleString("msgErrUploadFile") +" <i>" + value + "</i>.";
                                    }
                                }
                            }
                            else {
                                msg=paramRequest.getLocaleString("msgErrUploadFile") +" <i>" + value + "</i>.";
                            }
                        }
                    }

                    setAttribute(base, fup, "alt");
                    setAttribute(base, fup, "btntexto");
                    setAttribute(base, fup, "lnktexto");
                    setAttribute(base, fup, "blnstyle");
                    setAttribute(base, fup, "menubar", "yes");
                    setAttribute(base, fup, "toolbar", "yes");
                    setAttribute(base, fup, "status", "yes");
                    setAttribute(base, fup, "location", "yes");
                    setAttribute(base, fup, "directories", "yes");
                    setAttribute(base, fup, "scrollbars", "yes");
                    setAttribute(base, fup, "resizable", "yes");
                    setAttribute(base, fup, "width");
                    setAttribute(base, fup, "height");
                    setAttribute(base, fup, "top");
                    setAttribute(base, fup, "left");

                    base.updateAttributesToDB();
                    msg=paramRequest.getLocaleString("msgOkUpdateResource") +" "+ base.getId();

                    ret = ret.append("<script language=\"JavaScript\">\n");
                    ret = ret.append("   alert('");
                    ret = ret.append(msg);
                    ret = ret.append("');\n");
                    ret = ret.append("   location='");
                    ret = ret.append(paramRequest.getRenderUrl().setAction("edit").toString());
                    ret = ret.append("';\n");
                    ret = ret.append("</script>\n");
                }
                else msg=paramRequest.getLocaleString("msgTemplateRequired");
            }
            catch(Exception e) { log.error(e); msg=paramRequest.getLocaleString("msgErrUpdateResource") +" "+ base.getId(); }
        }
        else if(action.equals("remove"))
        {
            msg=admResUtils.removeResource(base);

            String alert=   "<script language=\"JavaScript\">\n"+
                            "   alert('"+msg+"');\n"+
                            "</script>\n";
            ret = ret.append(alert);
        }
        response.getWriter().print(ret.toString());
    }

    /**
     * Sets the attribute.
     *
     * @param base the base
     * @param fup the fup
     * @param att the att
     */
    protected void setAttribute(Resource base, FileUpload fup, String att)
    {
        try
        {
            if(null != fup.getValue(att) && !"".equals(fup.getValue(att).trim())) {
                base.setAttribute(att, fup.getValue(att).trim());
            }
            else base.removeAttribute(att);
        }
        catch(Exception e) {  log.error("Error while setting resource attribute: "+att + ", "+base.getId() +"-"+ base.getTitle(), e); }
    }

    /**
     * Sets the attribute.
     *
     * @param base the base
     * @param fup the fup
     * @param att the att
     * @param value the value
     */
    protected void setAttribute(Resource base, FileUpload fup, String att, String value)
    {
        try
        {
            if(null != fup.getValue(att) && value.equals(fup.getValue(att).trim())) {
                base.setAttribute(att, fup.getValue(att).trim());
            }
            else {
                base.removeAttribute(att);
            }
        }
        catch(Exception e) {  log.error("Error while setting resource attribute: "+att + ", "+base.getId() +"-"+ base.getTitle(), e); }
    }

    /**
     * Gets the form.
     *
     * @param request the request
     * @param paramsRequest the params request
     * @return the form
     * @return
     */
    private String getForm(javax.servlet.http.HttpServletRequest request, SWBParamRequest paramsRequest)
    {
        Resource base=getResourceBase();
        StringBuilder ret=new StringBuilder("");
        try
        {
            SWBResourceURL url = paramsRequest.getRenderUrl().setAction("update");
            ret = ret.append("<div class=\"swbform\">");//div principal
            ret = ret.append("<form name=\"frmResource\" method=\"post\" enctype=\"multipart/form-data\" action=\"");
            ret = ret.append(url.toString());
            ret = ret.append("\"> \n");
            ret = ret.append("<fieldset>");
            ret = ret.append("<legend>");
            ret = ret.append(paramsRequest.getLocaleString("msgStep1"));
            ret = ret.append("</legend>");
            ret = ret.append("<ul class=\"swbform-ul\">");
            ret = ret.append("<li class=\"swbform-li\">");
            ret = ret.append("<label for=\"cssClass\" class=\"swbform-label\">");
            ret = ret.append(paramsRequest.getLocaleString("msgTemplate"));
            ret = ret.append(" (xsl, xslt)\n");
            ret = ret.append("</label>");
            ret = ret.append("\n<select name=template>");
            ret = ret.append("\n<option value=\"\">");
            ret = ret.append(paramsRequest.getLocaleString("msgOption"));
            ret = ret.append("</option>");
            Iterator <Template> itTpl=paramsRequest.getWebPage().getWebSite().listTemplates();
            while (itTpl.hasNext())
            {
                Template tpl = itTpl.next();
                ret = ret.append("\n<option value=\"");
                ret = ret.append(tpl.getId());
                ret = ret.append("\"");
                if (String.valueOf(tpl.getId()).equals(base.getAttribute("template", "").trim())) {
                    ret = ret.append(" selected");
                }
                ret = ret.append(">");
                ret = ret.append(tpl.getTitle());
                ret = ret.append("</option>");
            }
            ret = ret.append("\n</select>");
            ret = ret.append("</li> \n");

            ret = ret.append("<li class=\"swbform-li\">");
            ret = ret.append("<label for=\"img\" class=\"swbform-label\">");
            ret = ret.append(paramsRequest.getLocaleString("msgImage"));
            ret = ret.append(" (gif, jpg, jpeg, png)\n");
            ret = ret.append("</label>");
            ret = ret.append("<input type=\"file\" name=\"img\" size=\"40\" onClick=\"this.form.btntexto.value=''; this.form.lnktexto.value=''\" onChange=\"isFileType(this, 'jpg|jpeg|gif|png');\"/>");
            if (!"".equals(base.getAttribute("img", "").trim())) {
                ret = ret.append("<p>");
                ret = ret.append(admResUtils.displayImage(base, base.getAttribute("img").trim(), "img"));
                ret = ret.append("<input type=checkbox name=noimg value=1>");
                ret = ret.append(paramsRequest.getLocaleString("msgCutImage") );
                ret = ret.append(" ");
                ret = ret.append("<i>");
                ret = ret.append(base.getAttribute("img").trim());
                ret = ret.append("</i></p>");
            }
            ret = ret.append("</li> \n");         
            
            ret = ret.append("<li class=\"swbform-li\">");
            ret = ret.append("<label for=\"alt\" class=\"swbform-label\">");
            ret = ret.append(paramsRequest.getLocaleString("msgAlt"));
            ret = ret.append("</label>");
            ret = ret.append("<input type=text size=50 name=alt ");
            if (!"".equals(base.getAttribute("alt", "").trim()))  {
                ret = ret.append(" value=\"");
                ret = ret.append(base.getAttribute("alt").trim().replaceAll("\"", "&#34;"));
                ret = ret.append("\"");
            }
            ret = ret.append("></li> \n");
            
            ret = ret.append("<li class=\"swbform-li\">");
            ret = ret.append("<label for=\"btntexto\" class=\"swbform-label\">");
            ret = ret.append(paramsRequest.getLocaleString("msgButton"));
            ret = ret.append("</label>");
            ret = ret.append("<input type=text size=50 name=btntexto ");
            if (!"".equals(base.getAttribute("btntexto", "").trim()))  {
                ret = ret.append(" value=\"");
                ret = ret.append(base.getAttribute("btntexto").trim().replaceAll("\"", "&#34;"));
                ret = ret.append("\"");
            }
            ret = ret.append("></li> \n");

            ret = ret.append("<li class=\"swbform-li\">");
            ret = ret.append("<label for=\"lnktexto\" class=\"swbform-label\">");
            ret = ret.append(paramsRequest.getLocaleString("msgLink"));
            ret = ret.append("</label>");
            ret = ret.append("<input type=text size=50 name=lnktexto ");
            if (!"".equals(base.getAttribute("lnktexto", "").trim()))  {
                ret = ret.append(" value=\"");
                ret = ret.append(base.getAttribute("lnktexto").trim().replaceAll("\"", "&#34;"));
                ret = ret.append("\"");
            }
            ret = ret.append("></li> \n");

            ret = ret.append("<li class=\"swbform-li\">");
            ret = ret.append("<label for=\"blnstyle\" class=\"swbform-label\">");
            ret = ret.append(paramsRequest.getLocaleString("msgStyle"));
            ret = ret.append("</label>");
            ret = ret.append("<input type=text size=50 name=blnstyle ");
            if (!"".equals(base.getAttribute("blnstyle", "").trim()))  {
                ret = ret.append(" value=\"");
                ret = ret.append(base.getAttribute("blnstyle").trim().replaceAll("\"", "&#34;"));
                ret = ret.append("\"");
            }
            ret = ret.append("></li> \n");
            ret = ret.append("</ul>");
            ret = ret.append("</fieldset>");
            
            ret = ret.append("<div class=\"swbform\" dojoType=\"dijit.TitlePane\" open=\"false\" title=\"");
            ret = ret.append(paramsRequest.getLocaleString("msgStep2"));
            ret = ret.append("\"> \n");
            ret = ret.append("  <fieldset>");
            ret = ret.append("<ul class=\"swbform-ul\">");

            ret = ret.append(admResUtils.loadWindowConfiguration(base, paramsRequest));
            
            ret = ret.append("</ul>");
            ret = ret.append("</fieldset>");
            ret = ret.append("</div> \n");

            ret = ret.append("<fieldset>");
            ret = ret.append("<button id=\"botonEnviar\" dojoType=\"dijit.form.Button\" type=\"submit\" name=\"btnSave\" ");
            
            ret = ret.append(" onclick=\"if(jsValida(this.form)) return true; else return false;\">");
            ret = ret.append(paramsRequest.getLocaleString("btnSubmit"));
            ret = ret.append("</button>");
            ret = ret.append("<button id=\"botonReset\" dojoType=\"dijit.form.Button\" type=\"reset\" name=\"btnReset\" >");
            ret = ret.append(paramsRequest.getLocaleString("btnReset"));
            ret = ret.append("</button>\n");
            ret = ret.append("</fieldset>");
            ret = ret.append("</form> \n");
            ret = ret.append("</div>"); // cerramos el div principal
            ret = ret.append(getScript(request, paramsRequest));
        }
        catch(Exception e) {  log.error(e); }
        return ret.toString();
    }

    /**
     * Gets the script.
     *
     * @param request the request
     * @param paramsRequest the params request
     * @return the script
     * @return
     */
    private String getScript(javax.servlet.http.HttpServletRequest request, SWBParamRequest paramsRequest)
    {
        StringBuilder ret = new StringBuilder("");
        try
        {
            ret = ret.append("\n<script>");
            ret = ret.append("\nfunction jsValida(pForm)");
            ret = ret.append("\n{");
            ret = ret.append("\n   if(pForm.template.selectedIndex==0 || pForm.template.options[pForm.template.selectedIndex].value=='' || pForm.template.options[pForm.template.selectedIndex].value==' ')");
            ret = ret.append("\n   {");
            ret = ret.append("\n      alert('");
            ret = ret.append(paramsRequest.getLocaleString("msgTemplateRequired"));
            ret = ret.append("');");
            ret = ret.append("\n      pForm.template.focus();");
            ret = ret.append("\n      return false;");
            ret = ret.append("\n   }");
            ret = ret.append("\n   if(!isFileType(pForm.img, 'bmp|jpg|jpeg|gif|png')) return false;");
            ret = ret.append("\n   if(!isNumber(pForm.width)) return false;");
            ret = ret.append("\n   if(!isNumber(pForm.height)) return false;");
            ret = ret.append("\n   if(!isNumber(pForm.top)) return false;");
            ret = ret.append("\n   if(!isNumber(pForm.left)) return false;");
            ret = ret.append("\n   return true;");
            ret = ret.append("\n}");
            ret = ret.append(admResUtils.loadIsFileType());
            ret = ret.append(admResUtils.loadIsNumber());
            ret = ret.append("\n</script>");
        }
        catch(Exception e) {  log.error(e); }
        return ret.toString();
    }
 }
