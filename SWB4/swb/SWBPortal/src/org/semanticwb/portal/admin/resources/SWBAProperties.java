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
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.util.SWBProperties;
import org.semanticwb.model.*;
import org.semanticwb.platform.SemanticMgr;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.portal.api.*;

// TODO: Auto-generated Javadoc
/**
 * The Class SWBAProperties.
 * 
 * @author juan.fernandez
 */
public class SWBAProperties extends GenericResource {

    /** The log. */
    private Logger log = SWBUtils.getLogger(SWBAProperties.class);
    
    /** The properties. */
    ArrayList properties;
    
    /** The prop. */
    SWBProperties prop;
    
    /** The base. */
    Resource base;
    
    /**
     * Creates a new instance of WBAProperties.
     */
    public SWBAProperties() {
    }
    
    /**
     * Init the resource.
     * 
     * @throws SWBResourceException the sWB resource exception
     */    
    @Override
    public void init() throws SWBResourceException {
        properties=new ArrayList();
        properties.add("db.properties");
        //properties.add("license.properties");
        properties.add("startup.properties");
        //properties.add("user.properties");
        properties.add("web.properties");
        properties.add("logging.properties");
        properties.add("System.properties");

    }
    
    /**
     * Init the resource base configuration.
     * 
     * @param base a resource in the data base
     * @throws SWBResourceException the sWB resource exception
     */    
    @Override
    public void setResourceBase(Resource base) throws SWBResourceException
    {
        super.setResourceBase(base);

        String fileSelected = base.getProperty("uptPropFile");
        //fileSelected ="web.properties";  //quitar, es solo para pruebas
        if(fileSelected==null)return;
        if(fileSelected.equals("web.properties"))
        {
            prop = (SWBProperties) SWBPortal.getWebProperties();
//        }else if(fileSelected.equals("workflow.properties"))
//        {
//            prop = com.infotec.workflow.manager.ProcessManagerConfig.getInstance().getProperties();
        }else if(fileSelected.equals("System.properties"))
        {
            prop = new SWBProperties(System.getProperties());
            prop.setReadOnly(true);
        }else
        {
            InputStream is = getClass().getResourceAsStream("/"+fileSelected);  
            prop = new SWBProperties();     
            try
            {
                prop.load(is);
            } catch (Exception e)
            {
                log.error("Can't read the properties file. " +
                               "Make sure "+fileSelected+" is in the CLASSPATH",e);
            }            
        }     
    } 

     /**
      * Requested action of the WBAProperties resource.
      * 
      * @param request input parameters
      * @param response an answer to the request and a list of objects (topic, user, action, ...)
      * @throws IOException an IO Exception
      * @throws SWBResourceException the sWB resource exception
      */  
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        base = getResourceBase();

        String fileSelected = base.getProperty("uptPropFile");
       
        String accion = response.getAction();
        if(accion==null) accion = request.getParameter("act");
        String keyID = request.getParameter("id");
        if(keyID!=null&&keyID.trim().length()==0) keyID=null;
        String comentario = request.getParameter("comentario");
        String valor = request.getParameter("valor");
        String newKey = request.getParameter("llave");
        
        if(accion!=null){
            if(accion.equals("update")){
                if(newKey!=null){
                    if(keyID!=null){
                        if(!keyID.equals(newKey)){
                            prop.remove(keyID);
                            prop.setProperty(newKey,valor,comentario);
                        }
                        else prop.setProperty(keyID,valor,comentario);
                    }
                    else prop.setProperty(newKey,valor,comentario);
                }
                response.setMode(response.Mode_VIEW);
                response.setRenderParameter("act","");
                //TODO:no se utiliza
                //SWBUtils.getInstance().refresh();
            }
            if(accion.equals("remove")){
                if(keyID!=null) prop.remove(keyID);
                response.setMode(response.Mode_VIEW);
            }
            if(accion.equals("rollback")){
                //cargar de nuevo el archivo properties a memoria
                InputStream fins = getClass().getResourceAsStream("/"+fileSelected);
                prop.load(fins);
                response.setMode(response.Mode_VIEW);
            }
            if(accion.equals("commit")){
                //guarda los cambio hechos al archivo properties
                FileOutputStream fouts = new FileOutputStream(SWBUtils.getApplicationPath()+"/WEB-INF/classes/" + fileSelected);
                prop.store(fouts,null);
                response.setMode(response.Mode_VIEW);
            }
            if(accion.equals("uptPropFile")){
                if(request.getParameter("propFile")!=null){
                    base.setProperty("uptPropFile", request.getParameter("propFile"));
                    //base.updateAttributesToDB();
                }
                response.setMode(response.Mode_ADMIN);
                //response.setMode(response.Mode_VIEW);
            }
            
        }
        
    }

    /**
     * User view of WBAProperties resource.
     * 
     * @param request input parameters
     * @param response an answer to the request
     * @param paramRequest a list of objects (topic, user, action, ...)
     * @throws IOException an IO Exception
     * @throws SWBResourceException the sWB resource exception
     */   
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        base = getResourceBase();
        String accion = request.getParameter("act");
        
        PrintWriter out = response.getWriter();
        String fileSelected = base.getProperty("uptPropFile");
        if(null==fileSelected) doAdmin(request, response, paramRequest);
        if(accion==null) accion="";
        if(accion.equals("")){

            out.println("<div class=\"swbform\">");
            out.println("<fieldset>");
            out.println("<table cellpadding=\"10\" cellspacing=\"0\" >");
            out.println("<thead>");
            out.println("<tr>");
            out.println("<th >"+paramRequest.getLocaleString("msgRemove")+"</th>");
            out.println("<th >"+paramRequest.getLocaleString("msgKey")+"</th>");
            out.println("<th >"+paramRequest.getLocaleString("msgValue")+"</th>");
            out.println("<th >"+paramRequest.getLocaleString("msgComment")+"</th>");
            out.println("</tr>");
            out.println("</thead>");
            out.println("<tbody>");
            String rowColor="";
            boolean cambiaColor = true;
            Enumeration ite = prop.propertyOrderedNames();
            while(ite.hasMoreElements()) {
                String key = (String) ite.nextElement();
                String comment = prop.getComment(key);
                if(comment==null)comment="";
                String value = prop.getProperty(key);
                if(value==null)value="";
                SWBResourceURL urlEdit = paramRequest.getRenderUrl();
                urlEdit.setMode(paramRequest.Mode_EDIT);
                urlEdit.setParameter("act","edit");
                urlEdit.setParameter("id",key);
                SWBResourceURL urlRemove = paramRequest.getActionUrl();
                urlRemove.setParameter("act","remove");
                urlRemove.setParameter("id",key);
//                rowColor="#EFEDEC";
//                if(!cambiaColor) rowColor="#FFFFFF";
//                cambiaColor = !(cambiaColor);
                out.println("<tr >"); //bgcolor=\""+rowColor+"\"
                out.println("<td >");
                if(!prop.isReadOnly())out.println("<a href=\""+urlRemove.toString()+"\" onclick=\"if(confirm('"+paramRequest.getLocaleString("msgAlertRemoveProperty")+"?')) { return(true);} else {return(false);}\"><img border=0 src=\""+SWBPlatform.getContextPath()+"/swbadmin/images/delete.gif\" alt=\""+paramRequest.getLocaleString("msgLinkRemove")+"\"></a>");
                out.println("</td>");
                out.println("<td >");
                if(!prop.isReadOnly())out.println("<a href=\""+urlEdit.toString()+"\">"+key+"</a>");
                else out.println(key);
                out.println("</td>");
                out.println("<td >"+value+"</td>");
                out.println("<td ><PRE >"+comment+"</PRE></td>");
                
                out.println("</tr>");
            }
            if (fileSelected.equals("System.properties"))
            {
                SemanticProperty sp = SWBPlatform.getSemanticMgr().getModel(SemanticMgr.SWBAdmin).getSemanticProperty(SemanticMgr.SWBAdminURI + "/PublicKey");
                String publicKey = SWBPlatform.getSemanticMgr().getModel(SemanticMgr.SWBAdmin).getModelObject().getProperty(sp);
                if (null != publicKey)
                {
                    out.println("<tr >");
                    out.println("<td >");
                    out.println("</td>");
                    out.println("<td >");
                    out.println("PKI - Public Key");
                    out.println("</td>");
                    out.println("<td >" + publicKey.substring(publicKey.indexOf("|") + 1) + "</td>");
                    out.println("<td ><PRE >System Public Key</PRE></td>");
                    out.println("</tr>");
                }
                out.println("</tbody>");
                out.println("</table>");
                out.println("</fieldset>");
                out.println("<fieldset>");
//            out.println("<tfoot>");
//            out.println("<tr>");
//            out.println("<td colspan=4 ><HR size=\"1\" noshade>");
//
            }
            if(prop.hasChangeIt()){
                SWBResourceURL urlRoll = paramRequest.getActionUrl();
                urlRoll.setParameter("act","rollback");
                
                SWBResourceURL urlCommit = paramRequest.getActionUrl();
                urlCommit.setParameter("act","commit");
                
                out.println("<input type=\"button\" name=\""+base.getId()+"/btnCommit\" onclick=\"if(confirm('"+paramRequest.getLocaleString("msgAlertSaveallChanges")+"?')) {window.location='" + urlCommit + "';}\" value=\""+paramRequest.getLocaleString("msgBtnCommit")+"\">");
                out.println("<input type=\"button\" name=\""+base.getId()+"/btnRollback\" onclick=\"if(confirm('"+paramRequest.getLocaleString("msgAlertRestoreAllValues")+"?')) {window.location='" + urlRoll + "';}\" value=\""+paramRequest.getLocaleString("msgBtnRollback")+"\">");
               // out.println("<input type=button class=\"boton\" name=btnRollback value=\""+paramRequest.getLocaleString("msgBtnRollback")+"\" onclick=\"if(confirm('"+paramRequest.getLocaleString("msgAlertRestoreAllValues")+"?')) {window.location='"+urlRoll.toString()+"';}\">");
            }
            
            SWBResourceURL urlAdd = paramRequest.getRenderUrl();
            urlAdd.setMode(paramRequest.Mode_EDIT);
            urlAdd.setParameter("act","add");
            if(!prop.isReadOnly())out.println("<input type=\"button\" name=\""+base.getId()+"/btnAdd\" onclick=\"window.location='"+urlAdd.toString()+"';\" value=\""+paramRequest.getLocaleString("msgBtnAdd")+"\">");
//            out.println("</td>");
//            out.println("</tr>");
//            out.println("</tfoot>");
//            out.println("</table>");
            out.println("</fieldset>");
            out.println("</div>");
        }
        
        
    }

     /**
      * Edit view of WBAProperties resource.
      * 
      * @param request input parameters
      * @param response an answer to the request
      * @param paramRequest a list of objects (topic, user, action, ...)
      * @throws IOException an IO Exception
      * @throws SWBResourceException the sWB resource exception
      */
    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        base = getResourceBase();
        String accion = request.getParameter("act");
        
        PrintWriter out = response.getWriter();
        String fileSelected = base.getProperty("uptPropFile");
        
        if(accion==null) accion="";
        if(accion.equals("edit")||accion.equals("add")) {
            String id=request.getParameter("id");
            String strKey="";
            String strValue="";
            String strComment="";
            String msgTitle = paramRequest.getLocaleString("msgKeyDefinition");
            if(accion.equals("edit")&&id!=null){
                msgTitle = paramRequest.getLocaleString("msgKeyEdition");
                strKey = id;
                strValue = prop.getProperty(id);
                strComment = prop.getComment(id);
            }
            
            out.println("<script>");
            out.println("   function valida(forma) {");
            out.println("       ");
            out.println("       ");
            out.println("       var tmp = forma.llave.value;     ");
            out.println("       trim(forma.llave);     ");
            out.println("       if(forma.llave.value=='')");
            out.println("       {   alert('"+paramRequest.getLocaleString("alertKeyMissing")+"');");
            out.println("           forma.llave.value=tmp;");
            out.println("           forma.llave.focus();     ");
            out.println("           forma.llave.select();     ");
            out.println("           return(false);");
            out.println("       }");
            out.println("       forma.llave.value=tmp;");
            out.println("       tmp = forma.valor.value;     ");
            out.println("       trim(forma.valor);     ");
            out.println("       if(forma.valor.value=='')");
            out.println("       {   alert('"+paramRequest.getLocaleString("alertValueMissing")+"');");
            out.println("           forma.valor.value=tmp;");
            out.println("           forma.valor.focus();     ");
            out.println("           forma.valor.select();     ");
            out.println("           return(false);");
            out.println("       }");
            out.println("       forma.valor.value=tmp;");
            out.println("       return (true);");
            out.println("   }");
            out.println("    function trim(field) {     ");
            out.println("         var retVal = '';     ");
            out.println("         var start = 0;     ");
            out.println("         while ((start < field.value.length) && (field.value.charAt(start) == ' ')) {     ");
            out.println("              ++start;     ");
            out.println("         }     ");
            out.println("         var end = field.value.length;     ");
            out.println("         while ((end > 0) && (field.value.charAt(end - 1) == ' ')) {     ");
            out.println("              --end;     ");
            out.println("         }     ");
            out.println("         retVal = field.value.substring(start, end);     ");
            out.println("         if (end == 0)     ");
            out.println("              field.value='';     ");
            out.println("         else     ");
            out.println("              field.value=retVal;          ");
            out.println("    }              ");
            out.println("</script>");

//            SWBResourceURL urla = paramRequest.getRenderUrl();
//            urla.setMode(SWBResourceURL.Mode_VIEW);
//
//            out.println("<a href=\"#\" onclick=\"window.location='"+urla+"'\">view</a>");
            out.println("<div class=\"swbform\">");
            //out.println("	<legend> Properties file edition. "+fileSelected+"</legend>");
            out.println("<form id=\""+id+"/fnewkey\" name=\""+id+"/fnewkey\" method=\"post\" action=\""+paramRequest.getActionUrl().setAction("update").toString()+"\" onsubmit=\"if(valida(frmAdd)){return true;} else return false; \" >");
            out.println("<fieldset>");
            out.println("<table cellpadding=\"10\" cellspacing=\"0\" width=\"98%\">");
            out.println("<tr>");
            out.println("<td colspan=\"2\" >"+msgTitle+"</td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td align=\"right\" width=\"150\">"+paramRequest.getLocaleString("msgKey")+": </td>");
            out.println("<td ><input type=\"text\" value=\""+strKey+"\" name=\"llave\" size=\"50\"><input type=\"hidden\" name=\"id\" value=\""+strKey+"\"></td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td align=\"right\" width=150>"+paramRequest.getLocaleString("msgValue")+": </td>");
            out.println("<td ><input type=\"text\" value=\""+strValue+"\" name=\"valor\" size=\"50\"></td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td align=\"right\" width=\"150\">"+paramRequest.getLocaleString("msgComment")+": </td>");
            out.println("<td ><textarea cols=\"50\" rows=\"10\" name=\"comentario\">"+strComment+"</textarea></td>");
            out.println("</tr>");
            out.println("</table>");
            out.println("</fieldset>");
            out.println("<fieldset>");
//            out.println("<tr>");
//            out.println("<td colspan=2 class=\"tabla\" align=\"right\"><HR size=\"1\" noshade>");
            
            SWBResourceURL urlBack = paramRequest.getRenderUrl();
            urlBack.setMode(paramRequest.Mode_VIEW);
            
            out.println("<input type=\"submit\" name=\""+base.getId()+"/btnSend\" value=\""+paramRequest.getLocaleString("msgBtnSend")+"\">");
            out.println("<input type=\"button\" name=\""+base.getId()+"/btnCancel\" onclick=\"window.location='"+urlBack.toString()+"';\" value=\""+paramRequest.getLocaleString("msgBtnCancel")+"\">");
//            out.println("</td>");
//            out.println("</tr>");
//            out.println("</table>");
            
            out.println("</fieldset>");
            out.println("</form>");
            out.println("</div>");
            
        }
    }

    /**
     * Admin view of WBAProperties resource.
     * 
     * @param request input parameters
     * @param response an answer to the request
     * @param paramRequest a list of objects (topic, user, action, ...)
     * @throws IOException an IO Exception
     * @throws SWBResourceException the sWB resource exception
     */   
    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        base = getResourceBase();
        PrintWriter out = response.getWriter();
        String fileSelect = "";
        if(base.getProperty("uptPropFile")!=null) fileSelect=base.getProperty("uptPropFile");

        SWBResourceURL urla = paramRequest.getActionUrl();
        urla.setAction("uptPropFile");

        out.println("<div class=\"swbform\">");
        out.println("<form id=\""+getResourceBase().getId()+"/doAdmin\" method=\"post\" action=\""+urla+"\" _onsubmit=\"submitForm('"+getResourceBase().getId()+"/doAdmin'); return false;\">");
        out.println("<fieldset>");
        //out.println("	<legend> Properties file edition. "+fileSelect+"</legend>");
        out.println("<table cellpadding=\"10\" cellspacing=\"0\" width=\"98%\">");
        out.println("<tr>");
        out.println("<td colspan=\"2\" >"+paramRequest.getLocaleString("msgSelectPropertyFile")+"</td>");
        out.println("</tr>");
        out.println("<tr>");
        out.println("<td align=\"right\" width=\"150\">"+paramRequest.getLocaleString("msgExistingFiles")+": </td>");
        out.println("<td >");
        out.println("<select name=\"propFile\" >");
        
        Iterator it=properties.iterator();
        while(it.hasNext())
        {
            String name=(String)it.next();
            String strSelect ="";
            if(fileSelect.trim().equals(name)) strSelect = "selected";
            out.println("<option value=\""+name+"\" "+strSelect+">"+name+"</option>");
        }

        out.println("</select>");
        out.println("</td>");
        out.println("</tr>");
        out.println("</table>");
        out.println("</fieldset>");
        out.println("<fieldset>");
//        out.println("<tr>");
//        out.println("<td colspan=2 class=\"tabla\" align=\"right\"><HR size=\"1\" noshade>");
        out.println("<input type=\"submit\" name=\"btnSend\" value=\""+paramRequest.getLocaleString("msgBtnSend")+"\">");
//        out.println("</td>");
//        out.println("</tr>");
        out.println("</fielset>");
        out.println("</form>");
        out.println("</div>");
       
    }
}
