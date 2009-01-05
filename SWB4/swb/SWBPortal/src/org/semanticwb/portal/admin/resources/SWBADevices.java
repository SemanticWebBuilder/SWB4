/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Device;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.portal.api.GenericResource;

import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

/**
 *
 * @author juan.fernandez
 */
public class SWBADevices extends GenericResource {
    
    private Logger log=SWBUtils.getLogger(SWBADevices.class);
    /** Nombre del recurso */    
    public String strRscType=SWBADevices.class.getName();
    
  

    /** Método que despliega la parte pública del recurso
     * @param request Objeto HttpServletRequest
     * @param response Objeto HttpServletResponse
     * @param paramsRequest Objeto WBParamRequest
     * @throws AFException Excepción del Aplication Framework
     * @throws IOException Excepción de Entrada/Salida
     */    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        getForma(request, paramRequest, response);
    }

    /** Método en donde se procesan las diferentes acciones del recurso
     * @param request Objeto HttpServletRequest
     * @param response Objeto WBActionResponse
     * @throws AFException Excepción del Aplication Framework
     * @throws IOException Excepción de Entrada/Salida
     */    
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String act=request.getParameter("acc2");
        String iDev_Id="0";
        Device dev = null;
        User user = response.getUser();
        if (act!=null && !act.equals("add"))
            iDev_Id=request.getParameter("txtDevice");
        String strName=request.getParameter("nom_dev");
        String strDesc=request.getParameter("desc_dev");
        String strMatch=request.getParameter("cadena_dev");
        if (act !=null && act.equals("edit")) {
            dev = SWBContext.getGlobalWebSite().getDevice(iDev_Id);
            dev.setTitle(strName);
            dev.setDescription(strDesc);
            dev.setModifiedBy(user);
            dev.setUserAgent(strMatch);
            response.setRenderParameter("confirm","updated");
        }
        if (act !=null && act.equals("add")) {
            dev = SWBContext.getGlobalWebSite().createDevice();
            dev.setTitle(strName);
            dev.setDescription(strDesc);
            dev.setCreator(user);
            dev.setUserAgent(strMatch);
            iDev_Id=dev.getId();
            response.setRenderParameter("confirm","added");
        }
        if (act !=null && act.equals("remove")) {
            SWBContext.getGlobalWebSite().removeDevice(iDev_Id);
            response.setRenderParameter("confirm","removed");
            response.setRenderParameter("status","true");            
        }
        response.setRenderParameter("id",iDev_Id);
        response.setAction("view");
        response.setMode(response.Mode_VIEW);
    }

    /** Método en donde se ejecuta el Java Script
     * @param paramsRequest Objeto SWBParamRequest
     * @param response Objeto HttpServletResponse
     * @throws IOException Excepción de Entrada/Salida
     */    
    private void getJavaScript(SWBParamRequest paramsRequest, HttpServletResponse response) throws IOException{
        PrintWriter out=response.getWriter();
        SWBResourceURL urlResAct=paramsRequest.getActionUrl();
        out.println("<script language=\"JavaScript\" type=\"text/JavaScript\"> \n");
        out.println("\nfunction send(acc,acc2) {");
        out.println("\n    if (acc=='save') {");
        out.println("\n        document.Device.acc2.value=acc2;");
        out.println("\n        if (!validateForm(document.Device,document.Device.id.value))");
        out.println("\n            return;");
        out.println("\n        document.Device.action='"+urlResAct+"';");
        out.println("\n        document.Device.submit();");
        out.println("\n    }");
        out.println("\n}");

        out.println("\nfunction completeFields() {");
        out.println("\n   var aux = document.Device.txtDevice.options[document.Device.txtDevice.selectedIndex].title;");
        out.println("\n   var aux2 = document.Device.txtDevice.options[document.Device.txtDevice.selectedIndex].text;");
        out.println("\n   var arregloDev = aux.split(\"|\")");
        out.println("\n   var idDev = arregloDev[0]");
        out.println("\n   var descDev = arregloDev[1]");
        out.println("\n   document.Device.nom_dev.value = aux2");
        out.println("\n   document.Device.cadena_dev.value = idDev");
        out.println("\n   document.Device.desc_dev.value = descDev");
        out.println("\n}");
        out.println("\n function validateForm(_f,id) {");
        out.println("\n    var _regExp=/select/i;");
        out.println("\n    for (i=0; i<_f.elements.length; i++ ) { ");
        out.println("\n        if(_f.elements[i]!=undefined) {");
        // Valida los objetos de la forma tipo text o textarea
        out.println("\n            if(_f.elements[i].type==\"text\" || _f.elements[i].type==\"textarea\") {");
        out.println("\n                if (_f.elements[i].value=='') {");
        try {
            out.println("\n                    alert ('"+paramsRequest.getLocaleString("jsEmptyField")+"');");
        }
        catch (Exception e) {
            log.error("Error on method getJavaScript() resource" + " " + strRscType + " " + "with id" + " " + getResourceBase().getId(),e);    
        }
        out.println("\n                    _f.elements[i].focus();");
        out.println("\n                    return false;");
        out.println("\n                }");
        out.println("\n            }");
        // Valida los objetos de la forma tipo select
        out.println("\n            if(_f.elements[i].type.match(_regExp)) {");
        out.println("\n                if (_f.elements[i].value=='') {");
        out.println("\n                    _f.elements[i].focus();");
        out.println("\n                    return false;");
        out.println("\n                }");
        out.println("\n            }");
        out.println("\n        }");
        out.println("\n    }");
        out.println("\n    return true;");
        out.println("\n }");
        out.println("\n</script>");
    }
    
    /** Método que genera la forma de vista al usuario
     * @param request Objeto HttpServletRequest
     * @param response Objeto HttpServletResponse
     * @param paramsRequest Objeto SWBParamRequest
     * @throws IOException Excepción de Entrada/Salida
     */    

    private void getForma(HttpServletRequest request, SWBParamRequest paramsRequest, HttpServletResponse response) throws IOException{
        PrintWriter out=response.getWriter();
        String devId="0";
        try {
            Device recDev=null;
            String strDevName="";
            String strDevDesc="";
            String strDevChain="";
            String strDisable="";
            String strConfirm=request.getParameter("confirm");
            String act=request.getParameter("act");
            if(act==null)
                act="";
                //act="edit";
            if (request.getParameter("id")!=null)
                devId=request.getParameter("id");
            if (act!=null && !act.equals("remove")) {
                if (strConfirm!=null && strConfirm.equals("removed")) {
                    out.println("<script>wbTree_remove();</script>");
                    //out.println("<font face=\"Verdana\" size=\"1\">");
                    out.println(paramsRequest.getLocaleString("frmDeviceRemoved"));
                    //out.println("</font>");
                    return;
                }
                
                out.println("<form name=\"Device\" method=\"post\" action=\"\"> \n");
                getJavaScript(paramsRequest, response);
                
                if (strConfirm!=null && strConfirm.equals("updated")) {
                    out.println("<script>");
                    out.println("wbTree_executeAction('gotopath.WBAGlobal.devices."+devId+"');");
                    out.println("wbTree_reload();");
                    out.println("</script>");
                }
                else if (strConfirm!=null && strConfirm.equals("added")) {
                    out.println("<script>");
                    out.println("wbTree_executeAction('gotopath.WBAGlobal.devices');");
                    out.println("wbTree_reload();");
                    out.println("wbTree_executeAction('gotopath.WBAGlobal.devices."+devId+"');");
                    out.println("</script>");

                }
                out.println("<table width=\"95%\"  border=\"0\" cellpadding=\"0\" cellspacing=\"1\" bgcolor=\"#CCCCCC\">");
                out.println("<tr>");
                out.println("<td>");
                out.println("<table width=\"100%\"  border=\"0\" cellpadding=\"10\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
                out.println("<tr>");
                out.println("<td class=\"valores\">");
                out.println("<table width=\"75%\" border=\"0\" cellpadding=\"4\" cellspacing=\"1\" bgcolor=\"\"> \n");
                out.println("<tr align=\"center\" bgcolor=\"\"> \n");
                out.println("</tr> \n");
                if (!devId.equals("0")) {
                    recDev = SWBContext.getGlobalWebSite().getDevice(devId);
                    strDevName=recDev.getTitle();
                    strDevDesc=recDev.getDescription();
                    strDevChain=recDev.getUserAgent(); //String Match
                    out.println("<input type=hidden name=txtDevice value=\""+devId+"\"> \n");
                }
                out.println("<tr bgcolor=\"\"> \n");
                out.println("<td align=\"right\" class=\"datos\">"+paramsRequest.getLocaleString("frmDeviceName")+":</td> \n");
                if (act!=null && act.equals(""))
                    out.println("<td align=\"right\" class=\"datos\">"+strDevName+"</td> \n");

                else
                    out.println("<td><input name=\"nom_dev\" type=\"text\" class=\"valores\" size=\"50\" maxlength=\"100\" value=\""+strDevName+"\" "+strDisable+"></td> \n");
                out.println("</tr> \n");
                out.println("<tr bgcolor=\"\"> \n");
                out.println("<td align=\"right\" class=\"datos\">"+paramsRequest.getLocaleString("frmDeviceDesc")+":</td> \n");
                if (act!=null && act.equals(""))
                    out.println("<td align=\"right\" class=\"datos\">"+strDevDesc+"</td> \n");
                else
                    out.println("<td><textarea name=\"desc_dev\" cols=\"38\" rows=\"8\" wrap=\"VIRTUAL\" class=\"valores\" "+strDisable+">"+strDevDesc+"</textarea></td> \n");
                out.println("</tr> \n");
                out.println("<tr bgcolor=\"\"> \n");
                out.println("<td align=\"right\" class=\"datos\">"+paramsRequest.getLocaleString("frmDeviceString")+":</td> \n");
                if (act!=null && act.equals(""))
                    out.println("<td align=\"right\" class=\"datos\">"+strDevChain+"</td> \n");
                else
                    out.println("<td><input name=\"cadena_dev\" type=\"text\" class=\"valores\" size=\"50\" maxlength=\"40\" value=\""+strDevChain+"\" "+strDisable+"></td> \n");
                out.println("</tr> \n");
                out.println("<tr align=\"center\" bgcolor=\"\"> \n");
                out.println("<td colspan=\"2\"> \n");
                if (act!=null && !act.equals("")) {
                    if (act.equals("add"))
                        out.println("\n <input type=button  class=\"botones\" name=Aceptar onClick='javascript:send(\"save\",\"add\");' value="+paramsRequest.getLocaleString("frmSave")+">");
                    if (act.equals("edit"))
                        out.println("\n <input type=button  class=\"botones\" name=Aceptar onClick='javascript:send(\"save\",\"edit\");' value="+paramsRequest.getLocaleString("frmSave")+">");
                }
                out.println("</td> \n");
                out.println("</tr> \n");
                out.println("</table> \n");
                if ((act.equals("edit"))&& devId.equals("0")) {
                    out.println("<script language=\"JavaScript\" type=\"text/JavaScript\"> \n");
                    out.println("completeFields(); \n");
                    out.println("</script> \n");
                }
                out.println("<input type=hidden name=acc2 value=\"\"> \n");
                out.println("</td>");
                out.println("</tr>");
                out.println("</table>");
                out.println("</td>");
                out.println("</tr>");
                out.println("</table>");
                out.println("</form> \n");
            }
            else {
                out.println("<form name=\"Device\" method=\"post\" action=\"\"> \n");
                getJavaScript(paramsRequest, response);
                out.println("<input type=hidden name=txtDevice value=\""+devId+"\"> \n");
                out.println("<input type=hidden name=acc2 value=\"\"> \n");
                out.println("<script language=\"JavaScript\" type=\"text/JavaScript\"> \n");
                out.println("send(\"save\",\"remove\");");
                out.println("</script> \n");
                out.println("</form> \n");
            }
        }
        catch (Exception e) {
            log.error( "Error on method getForma() resource" + " " + strRscType + " " + "with id" + " " + getResourceBase().getId(), e);
        }
    }
    

}
