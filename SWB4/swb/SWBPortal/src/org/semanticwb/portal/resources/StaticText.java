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


package org.semanticwb.portal.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;


/** Objeto que se encarga de desplegar y administrar un texto est�tico, este texto
 * se agrega en la administraci�n del recurso, acepta tags de html para cambiar su
 * aspecto.
 *
 * Object that is in charge to unfold and to administer a static text, this text
 * it is added in the administration of the resource, accepts tags of HTML to change his
 * aspect.
 * @author Javier Solis Gonzalez
 */
public class StaticText extends GenericAdmResource {

    

    /** Obtiene la vista del recurso.
     *
     * @param request El servlet container crea un objeto HttpServletRequest y
     *                      se pasa como argumento al m�todo del servlet.
     * @param response El servlet container crea un objeto HttpServletResponse y
     *                      se pasa como argumento al m�todo del servlet.
     * @param paramsRequest Argumentos de la solicitud del recurso.
     * @throws IOException
     * @exception com.infotec.appfw.exception.AFException Si se origina cualquier error en el recurso al traer el html.
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        Resource base=getResourceBase();
        String staticText = replaceTags(base.getAttribute("text"), request, paramRequest);
        
        SWBResourceURL url=paramRequest.getActionUrl();
        url.setCallMethod(url.Call_DIRECT);
        url.setMode(url.Mode_EDIT);
        
        PrintWriter out = response.getWriter();
        
        out.println("<script type=\"text/javascript\">");

        out.println("  function editableTextOnChange(id, arg) {");
        out.println("    postHtml('"+url.toString()+"'+'?val='+arg, id);");
        out.println("  }");

        /*out.println("   var eb;");
        out.println("   dojo.addOnLoad(function(){");
        out.println("       eb = new dijit.InlineEditBox({");
        out.println("         id: 'eb',");
        out.println("         editor: 'dijit.form.Textarea',");
        out.println("         autoSave: true");
        out.println("       }, 'stext_"+base.getId()+"');");
        out.println("   dojo.connect(dijit.byId('eb'), 'onChange', editableTextOnChange);");
        out.println("   });");*/

        out.println("</script>");
        /*out.println("<script src=\"/swb/swbadmin/js/jquery/jquery.jeditable.js\" type=\"text/javascript\"></script>");*/
        /*out.println("<script type=\"text/javascript\">");
        out.println(" $(document).ready(function() {");
        //out.println("     $('.editable_textarea').editable('" + url.toString() + "', {");
        out.println("     $('#" + "statictext_"+base.getId()+ "').editable('" + url.toString() + "', {");
        out.println("         type:'textarea',");
        out.println("         cancel:'Cancelar',");
        out.println("         submit:'Aceptar',");
        //out.println("         indicator:'<img src=\"img/indicator.gif\">',");        
        out.println("         id:'inlineid_" + base.getId() + "',");
        out.println("         name:'value_" + base.getId() + "',");        
        out.println("         tooltip   : 'Click para editar...'");
        out.println("     });");
        out.println(" });");
        out.println(" </script>");*/

        /*out.println("<div class=\"editable_textarea\" id=\"statictext_" + base.getId() + "\">");*/
        //out.println("<div dojoType=\"dijit.InlineEditBox\" id=stext_"+base.getId()+" onChange=\"editableTextOnChange(this.id,arguments[0])\">");
        out.println("<div id=\"stext_"+base.getId()+" \" dojoType=\"dijit.InlineEditBox\" onChange=\"editableTextOnChange(this.id,arguments[0])\" autoSave=\"true\">");
        out.println(staticText);
        out.println("</div>");
        out.flush();
    }

    public String replaceTags(String str, HttpServletRequest request, SWBParamRequest paramRequest)
    {
        if(str==null || str.trim().length()==0)
            return "";
        
        str=str.trim();
        //TODO: codificar cualquier atributo o texto
       
        Iterator it=SWBUtils.TEXT.findInterStr(str, "{request.getParameter(\"", "\")}");
        while(it.hasNext())
        {
            String s=(String)it.next();
            str=SWBUtils.TEXT.replaceAll(str, "{request.getParameter(\""+s+"\")}", request.getParameter(replaceTags(s,request,paramRequest)));
        }
        
        it=SWBUtils.TEXT.findInterStr(str, "{session.getAttribute(\"", "\")}");
        while(it.hasNext())
        {
            String s=(String)it.next();
            str=SWBUtils.TEXT.replaceAll(str, "{session.getAttribute(\""+s+"\")}", (String)request.getSession().getAttribute(replaceTags(s,request,paramRequest)));
        }
        
        it=SWBUtils.TEXT.findInterStr(str, "{getEnv(\"", "\")}");
        while(it.hasNext())
        {
            String s=(String)it.next();
            str=SWBUtils.TEXT.replaceAll(str, "{getEnv(\""+s+"\")}", SWBPlatform.getEnv(replaceTags(s,request,paramRequest)));
        }
        
        str=SWBUtils.TEXT.replaceAll(str, "{user.login}", paramRequest.getUser().getUsrLogin());
        str=SWBUtils.TEXT.replaceAll(str, "{user.email}", paramRequest.getUser().getUsrEmail());
        str=SWBUtils.TEXT.replaceAll(str, "{user.language}", paramRequest.getUser().getLanguage());
        str=SWBUtils.TEXT.replaceAll(str, "{webpath}", SWBPlatform.getContextPath());
        str=SWBUtils.TEXT.replaceAll(str, "{distpath}", SWBPortal.getDistributorPath());
        str=SWBUtils.TEXT.replaceAll(str, "{webworkpath}", SWBPlatform.getWebWorkPath());
        str=SWBUtils.TEXT.replaceAll(str, "{workpath}", SWBPlatform.getWorkPath());
        return str;
    }
    
    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        Resource base=getResourceBase();
        String staticText = replaceTags(base.getAttribute("text"), request, paramRequest);
        PrintWriter out = response.getWriter();
        out.print(staticText);
        out.flush();
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        Resource base = response.getResourceBase();
        System.out.println("text="+request.getParameter("val"));
        //base.setAttribute("text", request.getParameter("value_"+base.getId()));
        base.setAttribute("text", request.getParameter("val"));
        try{
            base.updateAttributesToDB();
        }catch(Exception e){
            
        }
    }
    
}