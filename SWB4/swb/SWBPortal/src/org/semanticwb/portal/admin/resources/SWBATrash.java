/**  
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
**/ 
 
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.admin.resources;

import java.io.*;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.*;
import org.semanticwb.model.*;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.portal.api.*;

// TODO: Auto-generated Javadoc
/**
 * The Class SWBATrash.
 * 
 * @author juan.fernandez
 */
public class SWBATrash extends GenericResource {

    /** The log. */
    private Logger log=SWBUtils.getLogger(SWBATrash.class);
    
    /** The debugvar. */
    private boolean debugvar=true;
    private Resource base=null;
    
    /**
     * Creates a new instance of SWBATrash.
     */
    public SWBATrash() { }

    /**
     * Admin view of a WBATrash resource.
     * 
     * @param request parameters
     * @param response answer to the request
     * @param paramRequest list of objects
     * @throws IOException an IO Exception
     * @throws SWBResourceException the sWB resource exception
     */
    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        
        base=getResourceBase();
        String  trashtype = base.getAttribute("trash","site");
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        String id = request.getParameter("suri");
        PrintWriter out = response.getWriter();
        SWBResourceURL urlact = paramRequest.getActionUrl();
        urlact.setAction("updcfg");
        out.println("<div class=\"swbform\">");
        out.println("<form id=\"" + id + "/trashAdmin\" action=\""+urlact+"\" method=\"post\" onsubmit=\"submitForm('" + id + "/trashAdmin'); return false;\">");
        out.println("<input type=\"hidden\" name=\"suri\" value=\""+id+"\">");
        out.println("<fieldset>");
        out.println("<legend>");
        out.println(paramRequest.getLocaleString("msgResourceConfig"));
        out.println("</legend>");
        out.println("<ul style=\"list-style:none;\">");
        out.println("<li>");
        out.println("<input type=\"radio\" id=\""+id+"/trashtypesite\" name=\"trashtype\" value=\"site\" "+(trashtype.equals("site")?"checked":"")+"><label for=\""+id+"/trashtypesite\">manejo de papelera para sitios.</label>");
        out.println("</li>");
        out.println("<li>");
        out.println("<input type=\"radio\" id=\""+id+"/trashtypeelements\" name=\"trashtype\" value=\"elements\" "+(trashtype.equals("elements")?"checked":"")+"><label for=\""+id+"/trashtypeelements\">manejo de papelera para elementos del sitio.</label>");
        out.println("</li>");
        out.println("</ul>");
        out.println("</fieldset>");

        out.println("<fieldset>");
        out.println("<button dojoType=\"dijit.form.Button\" type=\"submit\" >" + paramRequest.getLocaleString("btnSave") + "</button>"); //
        out.println("</fieldset>");
        out.println("</form>");
        out.println("</div>");
    }

    /**
     * User view of WBATrash resource.
     * 
     * @param request parameters
     * @param response answer to the request
     * @param paramRequest list of objects
     * @throws IOException an IO Exception
     * @throws SWBResourceException the sWB resource exception
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {

        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        base=getResourceBase();
        String  trashtype = base.getAttribute("trash","site");
        String id = request.getParameter("suri");
        String page = request.getParameter("page");
        PrintWriter out = response.getWriter();

        WebPage wp = paramRequest.getWebPage();
        User user = paramRequest.getUser();


        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        SemanticObject so = ont.getSemanticObject(id);

        SWBResourceURL urlact = paramRequest.getActionUrl();
        out.println("<div class=\"swbform\">");
        out.println("<form id=\"" + id + "/trash\" action=\""+urlact+"\" method=\"post\" onsubmit=\"submitForm('" + id + "/trash'); return false;\">");
        out.println("<input type=\"hidden\" name=\"suri\" value=\""+id+"\">");
        out.println("<fieldset>");
        out.println("<legend>");
        out.println(paramRequest.getLocaleString("msgDeletedElements"));
        out.println("</legend>");
        SWBResourceURL urlfilter = paramRequest.getRenderUrl();
        urlfilter.setParameter("suri", id);
        out.println("<ul style=\"list-style:none;\">");
        out.println("<li>");
        String pfilter = request.getParameter("filtersel");
        if(pfilter==null) pfilter="";
        out.println("<label for=\""+id+"/filtertrash\">"+paramRequest.getLocaleString("msgFilterElements")+":</label>");
        out.println("<select id=\""+id+"/filtertrash\" name=\"filtersel\" >");
        out.println("<option value=\"\" "+(pfilter.equals("")?"selected":"")+" > </option>");
        SemanticClass scls = Trashable.swb_Trashable;
        Iterator<SemanticClass> itsc = scls.listSubClasses(true);
        while (itsc.hasNext()) {
            SemanticClass semClass = itsc.next();
            out.println("<option value=\""+semClass.getClassId()+"\" "+(pfilter.equals(semClass.getClassId())?"selected":"")+">"+semClass.getDisplayName(user.getLanguage())+"</option>");
        }
        out.println("</selected>");
        out.println("</li>");
        out.println("</ul>");
        out.println("</fieldset>");
        
        out.println("<fieldset>");
//        out.println("<legend>");
//        out.println(paramRequest.getLocaleString("msgDeletedElements"));
//        out.println("</legend>");
        out.println("<table width=\"100%\">");
        out.println("<thead>");

        SemanticProperty sptemp=null;

        out.println("<tr>");
        out.println("<th>");
        out.println(paramRequest.getLocaleString("th_action"));
        out.println("</th>");
        out.println("<th>");
        out.println(paramRequest.getLocaleString("th_title"));
        out.println("</th>");
        out.println("<th>");
        out.println(paramRequest.getLocaleString("th_type"));
        out.println("</th>");
        sptemp = Traceable.swb_created;
        String propname = sptemp.getName();
        try {
            propname = sptemp.getDisplayName(user.getLanguage());
        } catch (Exception e) {
        }
        out.println("<th>");
        out.println(propname);
        out.println("</th>");
        sptemp = Traceable.swb_updated;
        propname = sptemp.getName();
        try {
            propname = sptemp.getDisplayName(user.getLanguage());
        } catch (Exception e) {
        }
        out.println("<th>");
        out.println(propname);
        out.println("</th>");
        sptemp = Traceable.swb_modifiedBy;
        propname = sptemp.getName();
        try {
            propname = sptemp.getDisplayName(user.getLanguage());
        } catch (Exception e) {
        }
        out.println("<th>");
        out.println(propname);
        out.println("</th>");
        sptemp = Activeable.swb_active;
        propname = sptemp.getName();
        try {
            propname = sptemp.getDisplayName(user.getLanguage());
        } catch (Exception e) {
        }
        out.println("<th>");
        out.println(propname);
        out.println("</th>");
        
        out.println("</thead>");

        out.println("<tbody>");

        //Muestra los elementos borrados
        Iterator<SemanticObject> itso = so.getModel().listSubjects(Trashable.swb_deleted, Boolean.TRUE);
        
        HashMap<String, SemanticObject> hmnum = new HashMap();
        while (itso.hasNext()) {
            SemanticObject semanticObject = itso.next();
            hmnum.put(semanticObject.getURI(), semanticObject);
        }

        int ps=20;
        int l=hmnum.size();
        int p=0;
        if(page!=null)p=Integer.parseInt(page);
        int x=0;
        
        itso = hmnum.values().iterator();
        while (itso.hasNext()) {
            SemanticObject semObj = itso.next();

            //PAGINACION ////////////////////
            if(x<p*ps)
            {
                x++;
                continue;
            }
            if(x==(p*ps+ps) || x==l)break;
            x++;
            /////////////////////////////////


            out.println("<tr>");
            out.println("<td>");

            out.println("<input type=\"hidden\" name=\"sval\" value=\""+semObj.getURI()+"\">");

            SWBResourceURL urlrem = paramRequest.getActionUrl();
            urlrem.setAction("remove");
            urlrem.setParameter("suri",id);
            urlrem.setParameter("sval", semObj.getURI());

            out.println("<a href=\"#\" onclick=\"if(confirm('"+paramRequest.getLocaleString("msgConfirmEliminar")+"?')){submitUrl('" + urlrem + "',this);} return false;\" title=\""+paramRequest.getLocaleString("msg_removeSO")+"\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/delete.gif\" border=\"0\" alt=\"" + paramRequest.getLocaleString("msg_removeSO") + "\"></a>");

            SWBResourceURL urlrec = paramRequest.getActionUrl();
            urlrec.setAction("recover");
            urlrec.setParameter("suri",id);
            urlrec.setParameter("sval", semObj.getURI()); //

            out.println("<a href=\"#\" onclick=\"if(confirm('"+paramRequest.getLocaleString("msgConfirmRecover")+"?')){submitUrl('" + urlrec + "',this);} return false;\" title=\""+paramRequest.getLocaleString("msg_recoverSO")+"\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/delete.gif\" border=\"0\" alt=\"" + paramRequest.getLocaleString("msg_recoverSO") + "\"></a>");
            out.println("</td>");

            out.println("<td>");
            out.println("<a href=\"#\" onclick=\"addNewTab('" + semObj.getURI() + "','" + SWBPlatform.getContextPath() + "/swbadmin/jsp/objectTab.jsp" + "','" + semObj.getDisplayName() + "');return false;\" title=\""+paramRequest.getLocaleString("msg_SODetail")+"\">"+semObj.getDisplayName(user.getLanguage())+"</a>");
            out.println("</td>");
            out.println("<td>");
            out.println(semObj.getSemanticClass().getName());
            out.println("</td>");
            out.println("<td>");
            if (semObj.getProperty(Traceable.swb_created) != null) {
                sptemp = Traceable.swb_created;
                out.print(getValueSemProp(semObj, sptemp));
            }
            else out.println(" --- ");
            out.println("</td>");
            out.println("<td>");
            if (semObj.getProperty(Traceable.swb_updated) != null) {
                sptemp = Traceable.swb_updated;
                out.print(getValueSemProp(semObj, sptemp));
            }
            else out.println(" --- ");
            out.println("</td>");
            out.println("<td>");
            if (semObj.getObjectProperty(Traceable.swb_modifiedBy) != null)
            {
                sptemp = Traceable.swb_modifiedBy;
                semObj = semObj.getObjectProperty(sptemp);

                if (null != semObj) {
                    log.debug("MODIFIEDBY-------" + semObj.getURI());
                    out.println("<a href=\"#\"  onclick=\"addNewTab('" + semObj.getURI() + "','" + SWBPlatform.getContextPath() + "/swbadmin/jsp/objectTab.jsp" + "','" + semObj.getDisplayName() + "');return false;\" >");
                    out.println(semObj.getProperty(User.swb_usrLogin));
                    out.println("</a>");
                }
            }
            else out.println(" --- ");
            out.println("</td>");
            out.println("<td>");
            if (semObj.getProperty(Activeable.swb_active) != null)
            {
                boolean activo = false;
                if (semObj.getBooleanProperty(Activeable.swb_active))
                {
                    activo = true;
                }
                SWBResourceURL urlu = paramRequest.getActionUrl();
                urlu.setParameter("suri", id);
                urlu.setParameter("sval", semObj.getURI());
                urlu.setAction("updstatus");
                out.println("<input name=\"" + Activeable.swb_active.getName() + semObj.getURI() + "\" type=\"checkbox\" value=\"1\" onclick=\"return false;\"  " + (activo ? "checked='checked'" : "") + "/>");
            }
            else out.println(" --- ");
            out.println("</td>");
        }

        out.println("</tbody>");
        out.println("<tfooter>");
        out.println("</tfooter>");
        out.println("</table>");
        out.println("</fieldset>");

        if(p>0 || x<l) //Requiere paginacion
        {
            out.println("<fieldset>");
            out.println("<center>");
            int pages=(int)(l+ps/2)/ps;
            for(int z=0;z<pages;z++)
            {
                SWBResourceURL urlNew = paramRequest.getRenderUrl();
                urlNew.setParameter("suri", id);
                urlNew.setParameter("page", ""+z);
                urlNew.setParameter("act", "stpBusqueda");
//                urlNew.setParameter("search",busqueda);
//                urlNew.setParameter("clsuri", sccol.getURI());

                if(z!=p)
                {
                    out.println("<a href=\"#\" onclick=\"submitUrl('" + urlNew + "',this); return false;\">"+(z+1)+"</a> ");
                }else
                {
                    out.println((z+1)+" ");
                }
            }
            out.println("</center>");
            out.println("</fieldset>");
        }

        out.println("<fieldset>");
        out.println("<button dojoType=\"dijit.form.Button\" name=\""+id+"/btndelete\" id=\""+id+"/btndelete\">" + paramRequest.getLocaleString("btnDeleteAll")); //onclick=\"if(confirm('"+paramRequest.getLocaleString("msgConfirmEliminarAll")+"')){submitForm('" + id + "/trash');}return false;\"
        
        out.println("<script type=\"dojo/method\" event=\"onClick\">");
        out.println(" if(confirm('"+paramRequest.getLocaleString("msgConfirmEliminarAll")+"?')){");
        out.println(" var myform=document.getElementById('" + id + "/trash');   ");
        SWBResourceURL urldellall = paramRequest.getActionUrl();
        urldellall.setAction("remove");
        out.println(" myform.action='"+urldellall.toString()+"'; ");
        out.println(" submitForm('" + id + "/trash');");
        out.println(" }");
        out.println(" return false; ");
        out.println("</script>");

        out.println("</button>"); //



        out.println("<button dojoType=\"dijit.form.Button\" name=\""+id+"/btnrecover\" id=\""+id+"/btnrecover\">" + paramRequest.getLocaleString("btnRecoverAll")); //onclick=\"if(confirm('"+paramRequest.getLocaleString("msgConfirmRecoverAll")+"')){submitForm('" + id + "/trash');}return false;\"

        out.println("<script type=\"dojo/method\" event=\"onClick\">");
        out.println(" if(confirm('"+paramRequest.getLocaleString("msgConfirmRecoverAll")+"?')){");
        out.println(" var myform=document.getElementById('" + id + "/trash');   ");
        SWBResourceURL urlrecall = paramRequest.getActionUrl();
        urlrecall.setAction("recover");
        out.println(" myform.action='"+urlrecall.toString()+"'; ");
        out.println(" submitForm('" + id + "/trash');");
        out.println(" }");
        out.println(" return false; ");
        out.println("</script>");

        out.println("</button>");


        out.println("</fieldset>");
        out.println("</form>");
        out.println("</div>");

        
    }

    /**
     * Recover or Eliminate an WB objects like topic, topicmpas, contents, resources.
     * 
     * @param request parameters
     * @param response answer to the request
     * @throws IOException an IO Exception
     * @throws SWBResourceException the sWB resource exception
     */
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        
        
        base=getResourceBase();
        String view=base.getAttribute("trash","site");
        
        String id=request.getParameter("suri");
        User user = response.getUser();
        WebPage wp = response.getWebPage();

        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        SemanticObject so = ont.getSemanticObject(id);

        String accion = response.getAction();

        //System.out.println("processAction("+accion+")");

        if(accion!=null && accion.equals("updcfg")){
            try{
                if(request.getParameter("trashtype")!=null){
                    System.out.println("tipo papelera: "+request.getParameter("trashtype"));
                    base.setAttribute("trash", request.getParameter("trashtype"));
                    base.updateAttributesToDB();
                }
            }
            catch(Exception e){log.error(e);}
        }
        else if(accion!=null && accion.equals("remove"))
        {
            String[] sval=request.getParameterValues("sval");
            if(null!=sval&&sval.length>0)
            {
                for(int i=0;i<sval.length;i++)
                {
                    System.out.println("remove ..."+sval[i]);
                    SemanticObject sorem = ont.getSemanticObject(sval[i]);
                    if(null!=sorem)
                    {
                        sorem.remove();
                    }
                }
            }
        }
        else if(accion!=null && accion.equals("recover"))
        {
            String[] sval=request.getParameterValues("sval");
            if(null!=sval&&sval.length>0)
            {
                for(int i=0;i<sval.length;i++)
                {
                    System.out.println("recover... "+sval[i]);
                    SemanticObject sorec = ont.getSemanticObject(sval[i]);
                    if(null!=sorec)
                    {
                        sorec.setBooleanProperty(Trashable.swb_deleted, false);
                    }
                }
            }
        }
        if(id!=null) response.setRenderParameter("suri",id);
    }

    /**
     * Gets the value sem prop.
     *
     * @param obj the obj
     * @param prop the prop
     * @return the value sem prop
     * @return
     */
    public String getValueSemProp(SemanticObject obj, SemanticProperty prop) {
        String ret = "";
        try {
            if (prop.isDataTypeProperty()) {
                log.debug("getValueSemProp(" + prop.getName() + ")" + obj.getProperty(prop));
                if (prop.isBoolean()) {
                    ret = "" + obj.getBooleanProperty(prop);
                }
                if (prop.isInt() || prop.isFloat()) {
                    ret = "" + obj.getLongProperty(prop);
                }
                if (prop.isString()) {
                    ret = obj.getProperty(prop);
                }
                if (prop.isDateTime()) {
                    ret = "" + obj.getDateProperty(prop);
                }
            } else if (prop.isObjectProperty()) {
                ret = obj.getObjectProperty(prop).getURI();
            }
        } catch (Exception e) {
            ret = "Not set";
        }
        return ret;
    }
}
