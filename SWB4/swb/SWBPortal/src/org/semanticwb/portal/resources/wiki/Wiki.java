/*
 * INFOTEC WebBuilder es una herramienta para el desarrollo de portales de conocimiento, colaboraci�n e integraci�n para Internet,
 * la cual, es una creaci�n original del Fondo de Informaci�n y Documentaci�n para la Industria INFOTEC, misma que se encuentra
 * debidamente registrada ante el Registro P�blico del Derecho de Autor de los Estados Unidos Mexicanos con el
 * No. 03-2002-052312015400-14, para la versi�n 1; No. 03-2003-012112473900 para la versi�n 2, y No. 03-2006-012012004000-01
 * para la versi�n 3, respectivamente.
 *
 * INFOTEC pone a su disposici�n la herramienta INFOTEC WebBuilder a trav�s de su licenciamiento abierto al p�blico (�open source�),
 * en virtud del cual, usted podr� usarlo en las mismas condiciones con que INFOTEC lo ha dise�ado y puesto a su disposici�n;
 * aprender de �l; distribuirlo a terceros; acceder a su c�digo fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los t�rminos y condiciones de la LICENCIA ABIERTA AL P�BLICO que otorga INFOTEC para la utilizaci�n
 * de INFOTEC WebBuilder 3.2.
 *
 * INFOTEC no otorga garant�a sobre INFOTEC WebBuilder, de ninguna especie y naturaleza, ni impl�cita ni expl�cita,
 * siendo usted completamente responsable de la utilizaci�n que le d� y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre INFOTEC WebBuilder, INFOTEC pone a su disposici�n la siguiente
 * direcci�n electr�nica:
 *
 *                                          http://www.webbuilder.org.mx
 */

/*
 * Wiki.java
 *
 * Created on 18 de abril de 2008, 01:52 AM
 *
 */

package org.semanticwb.portal.resources.wiki;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;


/**
 *
 * @author Javier Solis Gonzalez
 */
public class Wiki extends GenericResource
{
    private static Logger log = SWBUtils.getLogger(Wiki.class);

    WikiParser parser;
    SimpleDateFormat df=new SimpleDateFormat("dd-MM-yy_H-mm-ss-SSS");
    
    /** Creates a new instance of Wiki */
    public Wiki()
    {
    }
    
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException
    {
        PrintWriter out=response.getWriter();
        String content=getContent(readFile(paramsRequest));
        String lang=paramsRequest.getUser().getLanguage();
        
        out.println("<div aling=\"rigth\"><a href=\""+paramsRequest.getRenderUrl().setMode(SWBParamRequest.Mode_EDIT)+"\">editar</a></div>");
        String pre="<div class=\"wikiContent\">";
        String post="</div>";
        if(content!=null)
        {
            out.print(pre+parser.parse(content,request, paramsRequest)+post);
        }
    }

    /*
    public void processAction(HttpServletRequest request, WBActionResponse response) throws AFException, IOException
    {
        if(request.getParameter("save")!=null)
        {
            String content=request.getParameter("WBWikiTextbox");
            writeContent(response,content);
            response.setMode(response.Mode_VIEW);
        }else if(request.getParameter("cancel")!=null)
        {
            response.setMode(response.Mode_VIEW);
        }
        ResourceMgr.getInstance().getResourceCacheMgr().removeResource(response.getResourceBase().getRecResource());
    }
    */

    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException
    {
        String WBWikiTextbox=request.getParameter("WBWikiTextbox");
        String summary=request.getParameter("summary");
        String lang=paramsRequest.getUser().getLanguage();
        
        String preview="";
        if(request.getParameter("save")!=null)
        {
            String header=backUpContent(paramsRequest,summary);
            writeContent(paramsRequest,header+WBWikiTextbox);
            response.sendRedirect(paramsRequest.getRenderUrl().setMode(paramsRequest.Mode_VIEW).toString());
            return;
        }else if(request.getParameter("cancel")!=null)
        {
            response.sendRedirect(paramsRequest.getRenderUrl().setMode(paramsRequest.Mode_VIEW).toString());
            return;
        }else if(request.getParameter("preview")!=null)
        {
            preview=parser.parse(WBWikiTextbox,request, paramsRequest);
        }
        
        PrintWriter out=response.getWriter();
        SWBResourceURL url=paramsRequest.getRenderUrl();
        String content=getContent(readFile(paramsRequest));
        if(content!=null)
        {
            content=SWBUtils.XML.replaceXMLChars(content);
        }else content="";
        String adminpath=SWBPlatform.getContextPath()+"/swbadmin/";
        
        out.println("  <script type=\"text/javascript\">var WBAdmPath='"+adminpath+"';</script>");
        out.println("  <script type=\"text/javascript\" src=\""+adminpath+"js/wiki/wiki.js\"></script>");
        out.println("  <div id=\"WBWikiPreview\">"+preview+"</div>");
        out.println("  <form name=\"WBWikiEditForm\" id=\"WBWikiEditForm\" method=\"post\" action=\""+url+"\">");
        out.println("    <div id=\"WBWikiToolBar\"></div>");
        out.println("    <textarea tabindex='1' accesskey=\",\" name=\"WBWikiTextbox\" id=\"WBWikiTextbox\" rows='25' cols='80' >"+content+"</textarea><br>");
        out.println("    <span>Comentario de los cambios</small></span>");
        out.println("    <br>");
        out.println("    <div class='editOptions'>");
        out.println("      <input tabindex='2' type='text' value=\"/* historia */ \" name='summary' id='summary' maxlength='200' size='60' /><br/>");
        out.println("      <div class='editButtons'>");
        out.println("        <input id=\"save\" name=\"save\" type=\"submit\" tabindex=\"3\" value=\"Guardar\" accesskey=\"s\" title=\"Guardar los cambios [s]\" />");
        out.println("        <input id=\"preview\" name=\"preview\" type=\"submit\" tabindex=\"4\" value=\"Vista previa\" accesskey=\"p\" title=\"Previsualizar los cambios [p]\" />");
        out.println("        <input id=\"diff\" name=\"diff\" type=\"submit\" tabindex=\"5\" value=\"Mostrar Cambios\" accesskey=\"v\" title=\"Muestra que cambios se an hecho [v]\" />");
        out.println("        <input id=\"cancel\" name=\"cancel\" type=\"submit\" tabindex=\"6\" value=\"Cancelar\" accesskey=\"c\" title=\"Cancelar [c]\"/>");
        out.println("      </div>");
        out.println("    </div>");
        out.println("  </form>");
    }
    
    
    public String getHeader(String data)
    {
        String pattern="\n#EndHeader\n";
        int i=data.indexOf(pattern);
        if(i>=0)
        {
            return data.substring(0,i+pattern.length());
        }
        return "#EndHeader\n";
    }

    public String getContent(String data)
    {
        String ret="";
        if(data!=null)
        {
            String pattern="\n#EndHeader\n";
            int i=data.indexOf(pattern);
            if(i>=0)
            {
                ret=data.substring(i+pattern.length());
            }else ret=data;
        }
        return ret;
    }
    
    public String readFile(SWBParamRequest paramsRequest)
    {
        String topic=paramsRequest.getTopic().getId();
        String ret=null;
        try
        {
            ret=SWBPlatform.readFileFromWorkPath(paramsRequest.getResourceBase().getWorkPath()+"/"+topic);
        }catch(Exception noe){}
        
        return ret;
    }
    
    public String backUpContent(SWBParamRequest paramsRequest, String summary)
    {
        StringBuffer header=new StringBuffer();
        String topic=paramsRequest.getTopic().getId();
        String userid=paramsRequest.getUser().getId();
        try
        {
            String data=readFile(paramsRequest);
            if(data!=null)
            {
                header.append(getHeader(data));
                synchronized(this)
                {
                    String path=paramsRequest.getResourceBase().getWorkPath();
                    String backfile="/old/"+topic+"."+df.format(new Date());
                    SWBPlatform.writeFileToWorkPath(path+backfile,SWBUtils.IO.getStreamFromString(data),userid);

                    header.insert(0,"#previous:"+backfile+"\n");
                    header.insert(0,"#user:"+paramsRequest.getUser().getId()+"\n");
                    header.insert(0,"#summary:"+summary+"\n");
                }
            }
        }catch(Exception e){log.error(e);}
        return header.toString();
    }
    
    public void writeContent(SWBParamRequest paramsRequest, String content)
    {
        String topic=paramsRequest.getTopic().getId();
        String userid=paramsRequest.getUser().getId();
        try
        {
            SWBPortal.getResourceMgr().getResourceCacheMgr().removeResource(paramsRequest.getResourceBase());
            String path=paramsRequest.getResourceBase().getWorkPath()+"/"+topic;
            SWBPlatform.writeFileToWorkPath(path,SWBUtils.IO.getStreamFromString(content),userid);
        }catch(Exception noe){}
    }    

    @Override
    public void setResourceBase(Resource base) throws SWBResourceException
    {
        super.setResourceBase(base);
        parser=new WikiParser();
    }
    
    public String[] getModes(HttpServletRequest request, SWBParamRequest paramRequest) throws SWBResourceException, java.io.IOException
    {
        String arr[]=super.getModes(request,paramRequest);
        String ret[]=new String[arr.length+1];
        for(int i=0;i<arr.length;i++)
        {
            ret[i]=arr[i];
        }
        ret[arr.length]=paramRequest.Mode_EDIT;
        return ret;
    }

    public String getResourceCacheID(HttpServletRequest request, SWBParamRequest paramsRequest) throws SWBResourceException
    {
        String retValue = super.getResourceCacheID(request, paramsRequest);
        if(retValue!=null)retValue+="-"+paramsRequest.getTopic().getId()+"-"+paramsRequest.getUser().getLanguage();
        return retValue;
    }
    
}
