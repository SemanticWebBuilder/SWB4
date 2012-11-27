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
package org.semanticwb.portal.admin.resources;

import java.io.*;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.*;
import org.semanticwb.model.*;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticModel;
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
    
    /** The base. */
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
        if(null==id) id="";
        String page = request.getParameter("page");

        //System.out.println("filterselect="+request.getParameter("filtersel"));

        PrintWriter out = response.getWriter();

        WebPage wp = paramRequest.getWebPage();
        User user = paramRequest.getUser();


        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        SemanticObject so = null;

        String returi = request.getParameter("returi");

        
        if(null!=returi)
        {
            StringTokenizer stoken = new StringTokenizer(request.getParameter("returi"),",");
            boolean retreload = request.getParameter("reload")!=null&&request.getParameter("reload").equals("true")?true:false;
            boolean retclose = request.getParameter("closetab")!=null&&request.getParameter("closetab").equals("true")?true:false;
            out.println("<script type=\"text/javascript\">");
            while(stoken.hasMoreElements())
            {
                String token = stoken.nextToken();
                //System.out.println("returi:"+token);
                SemanticObject obj = ont.getSemanticObject(token);
                if(retreload&&null!=obj)
                {
                    SemanticObject parent=obj.getHerarquicalParent();
                    if(parent==null)parent=obj.getModel().getModelObject();
                    if(obj.instanceOf(WebSite.sclass))
                    {
                        //System.out.println("addItemByURI");
                        out.println("addItemByURI(mtreeStore, null, '" + obj.getURI() + "');");
                    }
                    else
                    {
                        //System.out.println("reloadTreeNodeByURI");
                        out.println("reloadTreeNodeByURI('"+parent.getURI()+"');");
                    }
                    out.println("reloadTab('"+obj.getURI()+"');");
                }
                else if (retclose)
                {
                    //System.out.println("cerrando tab...");
                    out.println("   closeTab('" + token + "');");
                }
            }
            out.println("</script>");
        }

        SWBResourceURL urlact = paramRequest.getActionUrl();
        out.println("<div class=\"swbform\">");
        out.println("<form id=\"" + id + "/trash\" action=\""+urlact+"\" method=\"post\" onsubmit=\"submitForm('" + id + "/trash'); return false;\">");
        out.println("<input type=\"hidden\" name=\"suri\" value=\""+id+"\">");
        
        out.println("<fieldset>");
        out.println("<legend>");
        out.println(paramRequest.getLocaleString("msgDeletedElements"));
        out.println("</legend>");
        String pfilter = request.getParameter("filtersel");
        if(pfilter==null) pfilter="";

        if(trashtype.equals("elements"))
        {
            so = ont.getSemanticObject(id);
            SWBResourceURL urlfilter = paramRequest.getRenderUrl();
            urlfilter.setParameter("suri", id);

            out.println("<p>");
            out.println("<label for=\""+id+"/filtertrash\">"+paramRequest.getLocaleString("msgFilterElements")+":</label>");
            out.println("<select dojoType=\"dijit.form.FilteringSelect\" autocomplete=\"true\" id=\""+id+"/filtertrash\" name=\"filtersel\" >");
            out.println("<option value=\"\" "+(pfilter.equals("")?"selected=\"selected\"":"")+" > </option>");
            SemanticClass scls = Trashable.swb_Trashable;
            Iterator<SemanticClass> itsc = scls.listSubClasses(true);
            while (itsc.hasNext()) {
                SemanticClass semClass = itsc.next();
                if(!semClass.equals(WebSite.swb_WebSite))
                {
                    out.println("<option value=\""+semClass.getClassId()+"\" "+(pfilter.equals(semClass.getClassId())?"selected=\"selected\" ":"")+">"+semClass.getDisplayName(user.getLanguage())+"</option>");
                }
            }
            out.println("<script type=\"dojo/method\" event=\"onChange\" >");

            out.println(" var urlfilter='" + urlfilter + "'+'&filtersel='+dijit.byId('"+id+"/filtertrash').attr('value');   ");

            //out.println(" alert(urlfilter); ");
            out.println(" submitUrl(urlfilter,this.domNode);");

            out.println(" return false; ");
            out.println("</script>");
            out.println("</select>");
            out.println("</p>");
//            out.println("</li>");
//            out.println("</ul>");
        }
        out.println("</fieldset>");
        
        
        out.println("<fieldset>");
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
        out.println("</tr>");
        
        out.println("</thead>");

        out.println("<tbody>");

        //Muestra los elementos borrados
        // Para listar elementos borrados
        Iterator<SemanticObject> itso = null;

        if(trashtype.equals("elements"))
        {
            itso = so.getModel().listSubjects(Trashable.swb_deleted, Boolean.TRUE);
        }
        //Si son sitios, para mostrar los sitios eliminados
        else if(trashtype.equals("site"))
        {
            HashMap<String, SemanticObject> hmdelsite = new HashMap();
            Iterator<SemanticModel> itsmodel = ont.listSubModels();
            while (itsmodel.hasNext()) {
                SemanticModel sm = itsmodel.next();
                SemanticObject smso = sm.getModelObject();
                if(smso!=null&&smso.instanceOf(Trashable.swb_Trashable))
                {
                    if(smso.getBooleanProperty(Trashable.swb_deleted))
                    {
                       hmdelsite.put(smso.getURI(), smso);
                    }
                }
            }
            itso = hmdelsite.values().iterator();
        }
        
        HashMap<String, SemanticObject> hmnum = new HashMap();
        while (itso.hasNext()) {
            SemanticObject semanticObject = itso.next();
            hmnum.put(semanticObject.getURI(), semanticObject);
        }

        HashMap<String, SemanticObject> hmfiltro = new HashMap();
        Iterator<SemanticObject> itfiltro = hmnum.values().iterator();
        if(pfilter!=null&&pfilter.trim().length()>0)
        {
            while (itfiltro.hasNext()) {
                SemanticObject semanticObject = itfiltro.next();

                SemanticClass sc = semanticObject.getSemanticClass();
                //System.out.println( "filtro: "+pfilter+" sc:"+(sc!=null?sc.getClassId():"null") );
                if(sc!=null&&pfilter.equals(sc.getClassId()))
                {
                    hmfiltro.put(semanticObject.getURI(), semanticObject);
                }
            }
        }
        else
        {
            hmfiltro = hmnum;
        }


        int ps=20;
        int l=hmfiltro.size();
        int p=0;
        if(page!=null)p=Integer.parseInt(page);
        int x=0;
        
        itso = hmfiltro.values().iterator();
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

            out.println("<a href=\"#\" onclick=\"if(confirm('"+paramRequest.getLocaleString("msgConfirmEliminar")+"?')){submitUrl('" + urlrem + "',this);} return false;\" title=\""+paramRequest.getLocaleString("msg_removeSO")+"\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/trash_vacio.gif\" border=\"0\" alt=\"" + paramRequest.getLocaleString("msg_removeSO") + "\"></a>");

            SWBResourceURL urlrec = paramRequest.getActionUrl();
            urlrec.setAction("recover");
            urlrec.setParameter("suri",id);
            urlrec.setParameter("sval", semObj.getURI()); //

            out.println("<a href=\"#\" onclick=\"if(confirm('"+paramRequest.getLocaleString("msgConfirmRecover")+"?')){submitUrl('" + urlrec + "',this);} return false;\" title=\""+paramRequest.getLocaleString("msg_recoverSO")+"\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/recover.gif\" border=\"0\" alt=\"" + paramRequest.getLocaleString("msg_recoverSO") + "\"></a>");
            out.println("</td>");

            out.println("<td>");
            out.println("<a href=\"#\" onclick=\"addNewTab('" + semObj.getURI() + "','" + SWBPlatform.getContextPath() + "/swbadmin/jsp/objectTab.jsp" + "','" + semObj.getDisplayName() + "');return false;\" title=\""+paramRequest.getLocaleString("msg_SODetail")+"\">"+semObj.getDisplayName(user.getLanguage()).replaceAll("'", "")+"</a>");
            out.println("</td>");
            out.println("<td>");
            out.println(semObj.getSemanticClass()!=null?semObj.getSemanticClass().getName():"--");
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
            SemanticObject soTmp =null;
            if (semObj.getObjectProperty(Traceable.swb_modifiedBy) != null)
            {
                sptemp = Traceable.swb_modifiedBy;
                soTmp = semObj.getObjectProperty(sptemp);

                if (null != soTmp) {
                    log.debug("MODIFIEDBY-------" + soTmp.getURI());
                    out.println("<a href=\"#\"  onclick=\"addNewTab('" + soTmp.getURI() + "','" + SWBPlatform.getContextPath() + "/swbadmin/jsp/objectTab.jsp" + "','" + soTmp.getDisplayName() + "');return false;\" >");
                    out.println(soTmp.getProperty(User.swb_usrLogin));
                    out.println("</a>");
                }
            }
            else out.println(" --- ");
            out.println("</td>");
            out.println("<td>");
            if (semObj.getProperty(Activeable.swb_active) != null)
            {
                boolean activo = semObj.getBooleanProperty(Activeable.swb_active);
                out.println("<input name=\"" + Activeable.swb_active.getName() + semObj.getURI() + "\" type=\"checkbox\" value=\"1\" id=\"" + Activeable.swb_active.getName() + semObj.getURI() + "\" onclick=\"return false;\" _disabled=\"true\"  " + (activo ? "checked" : "") + "/>");
            }
            else
            {
                out.println("<input name=\"" + Activeable.swb_active.getName() + semObj.getURI() + "\" type=\"checkbox\" value=\"1\" id=\"" + Activeable.swb_active.getName() + semObj.getURI() + "\" onclick=\"return false;\" _disabled=\"true\"  />");
            }
            out.println("</td>");
            out.println("</tr>");
        }

        out.println("</tbody>");
        out.println("</table>");
        out.println("</fieldset>");
        
        if(p>0 || x<l) //Requiere paginacion
        {
            out.println("<fieldset>");
            out.println("<center>");
            //int pages=(int)(l+ps/2)/ps;
            int pages=(int)(l/ps);
            if((l%ps)>0) pages++;
            for(int z=0;z<pages;z++)
            {
                SWBResourceURL urlNew = paramRequest.getRenderUrl();
                urlNew.setParameter("suri", id);
                urlNew.setParameter("page", ""+z);
                urlNew.setParameter("act", "stpBusqueda");

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
                    //System.out.println("tipo papelera: "+request.getParameter("trashtype"));
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
                String returi = "";
                for(int i=0;i<sval.length;i++)
                {
                    //System.out.println("remove ..."+sval[i]);
                    SemanticObject sorem = ont.getSemanticObject(sval[i]);
                    if(null!=sorem)
                    {
                        sorem.remove();
                    }
                    returi = returi + sval[i];
                    if(i<sval.length) returi = returi +",";
                }
                if(returi.length()>0) {
                    response.setRenderParameter("closetab", "true");
                    response.setRenderParameter("returi", returi);
                }

            }
        }
        else if(accion!=null && accion.equals("recover"))
        {
            String[] sval=request.getParameterValues("sval");
            if(null!=sval&&sval.length>0)
            {
                String returi = "";
                for(int i=0;i<sval.length;i++)
                {
                    //System.out.println("recover... "+sval[i]);
                    SemanticObject sorec = ont.getSemanticObject(sval[i]);
                    if(null!=sorec)
                    {
                        sorec.setBooleanProperty(Trashable.swb_deleted, false);
                    }
                    returi = returi + sval[i];
                    if(i<sval.length) returi = returi +",";
                }
                if(returi.length()>0) {
                    response.setRenderParameter("reload", "true");
                    response.setRenderParameter("returi", returi);
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
