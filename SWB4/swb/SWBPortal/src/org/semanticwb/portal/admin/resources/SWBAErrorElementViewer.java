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
 * SWBAErrorElementViewer.java
 *
 * Created on 9 de febrero de 2009, 05:15 PM
 */

package org.semanticwb.portal.admin.resources;


import java.util.*;
import java.io.*;
import java.net.InetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.util.ErrorElement;
import org.semanticwb.base.util.SWBMail;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
/** Muestra la lista de errores generados en WebBuilder. Permite ver a detalle del
 * error seleccionando con la posibilidad de enviar el error por correo
 * electronico.
 *
 * It shows the error list generated on WebBuilder. It show the detail of the error
 * selected with the posibility to send an email of the error.
 * @author Juan Antonio Fernandez Arias
 */
public class SWBAErrorElementViewer extends GenericResource{
    private static Logger log = SWBUtils.getLogger(SWBAErrorElementViewer.class);
    /** Creates a new instance of SWBAErrorElementViewer */
    public SWBAErrorElementViewer() {
    }

    /** User View of the SWBAErrorElementViewer Resource; it shows an error list generated from the resources
     * in the WebBuilder application
     * @param request
     * @param response
     * @param paramRequest
     * @throws AFException
     * @throws IOException
     */    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        PrintWriter out = response.getWriter();
        String act = request.getParameter("act");

        ErrorElement ee=null;
        if(act==null) act="";

        Iterator ite_err = SWBUtils.ERROR.getErrorElements();
        Vector hmerr = new Vector();
        while(ite_err.hasNext())
        {
            ErrorElement eel = (ErrorElement) ite_err.next();
            hmerr.add(eel);
        }
        
        if(act.equals("")){
            out.println("<div class=\"swbform\">");
            out.println("<fieldset>");
            out.println("<table  width=\"98%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\">");
            out.println("<tr >");
            out.println(" <td align=\"center\">"+paramRequest.getLocaleString("msgAction")+"</td>");
            out.println(" <td align=\"center\">"+paramRequest.getLocaleString("msgDescription")+"</td>");
            out.println(" <td align=\"center\">"+paramRequest.getLocaleString("msgDate")+"</td>");
            out.println("</tr>");
            Iterator iteErr = SWBUtils.ERROR.getErrorElements();
            String rowColor="";
            boolean cambiaColor = true;
            //int num = 0;
            while(iteErr.hasNext()){
                rowColor="#EFEDEC";
                if(!cambiaColor) rowColor="#FFFFFF";
                cambiaColor = !(cambiaColor);
                ErrorElement  e = (ErrorElement) iteErr.next();
                //num++;
                SWBResourceURL urlDetail = paramRequest.getRenderUrl();
                urlDetail.setParameter("act","detail");
                urlDetail.setParameter("idErr",Long.toString(e.getId()));
                out.println("<tr bgcolor=\""+rowColor+"\">");
                out.println(" <td><a href=\""+urlDetail.toString()+"\" >"+e.getId()+"</a></td>");
                out.println(" <td>"+SWBUtils.XML.replaceXMLChars(e.getMessage())+"</td>");
                out.println(" <td>"+e.getDate()+"</td>");
                out.println("</tr>");
                urlDetail=null;
            }
            out.println("</table>");
            out.println("</fieldset>");
            out.println("</div>");
        }
        if(act.equals("send")){
            //String idErr = request.getParameter("idErr");
            String mensaje = request.getParameter("mensaje");
            String fecha = request.getParameter("fecha");
            String stacktrace = request.getParameter("stacktrace");
            Properties sysInfo = System.getProperties();
            StringBuffer ret = new StringBuffer();
            ret.append("\n"+paramRequest.getLocaleString("msgErroInfoGenerated")+":\n");
            InetAddress iAddr = InetAddress.getLocalHost();
            StringBuffer addrStr = new StringBuffer();
            byte[] bytes = iAddr.getAddress();
            for(int cnt = 0; cnt < bytes.length; cnt++){
                int uByte = bytes[cnt] < 0 ? bytes[cnt] + 256 : bytes[cnt];
                addrStr.append(uByte);
                if(cnt < 3)
                    addrStr.append('.');
            }
            ret.append("\n"+paramRequest.getLocaleString("msgSite")+":  "+request.getServerName());
            ret.append("\n"+paramRequest.getLocaleString("msgPort")+":  "+request.getServerPort());
            ret.append("\n"+paramRequest.getLocaleString("msgIP")+":  "+ addrStr.toString());
            ret.append("\n"+paramRequest.getLocaleString("msgLanguage")+":  "+request.getLocale().toString());
            ret.append("\n"+paramRequest.getLocaleString("msgTotalMemory")+":  "+Runtime.getRuntime().totalMemory()+ " bytes");
            ret.append("\n"+paramRequest.getLocaleString("msgMaxMemory")+":  "+Runtime.getRuntime().maxMemory()+ " bytes");
            ret.append("\n"+paramRequest.getLocaleString("msgAvailableMemory")+":  "+Runtime.getRuntime().freeMemory()+ " bytes");
            ret.append("\n"+paramRequest.getLocaleString("msgDate")+":  "+fecha);
            ret.append("\n\n"+paramRequest.getLocaleString("msgErrorMsg")+":\n\n  "+mensaje);
            ret.append("\n\n"+paramRequest.getLocaleString("msgStackTrace")+": \n\n"+stacktrace+"\n\n");
            ret.append("\n\n ------------------------------------------------------------------------------------ \n\n");
            if(sysInfo!=null){
                ret.append("\n"+paramRequest.getLocaleString("msgSysInfo")+":\n\n");
                Enumeration enuProp = sysInfo.propertyNames();
                while(enuProp.hasMoreElements()){
                    String key = (String) enuProp.nextElement();
                    ret.append("\n"+key+": "+sysInfo.getProperty(key,paramRequest.getLocaleString("msgUnAvailable")));
                }
            }

            try{
                SWBMail mail = new SWBMail();
                mail.setFromEmail("webbuilder@infotec.com.mx");
                mail.setSubject(paramRequest.getLocaleString("msgErrorGeneratedWB3"));
                mail.setData(ret.toString());

                SWBUtils.EMAIL.sendBGEmail(mail);
                act = "detail";

            }
            catch(Exception e)
            {
                log.error("Error al mandar Error Element a webbuilder@infotec.com.mx",e);
            }

            
        }
        if(act.equals("detail")){
            long id =  -1;
            //long idN = -1;
            //long idP = -1;
            String mensaje = "";
            String identificador = "";
            String fecha = "";
            String stacktrace = "";
            if(request.getParameter("idErr")!=null){
                id= Long.parseLong(request.getParameter("idErr"));
            }
            
            
            
            
            out.println("<div class=\"swbform\">");
            out.println("<fieldset>");
            out.println("<table  width=\"98%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\" >");
            
            if(id!=-1) {
                Iterator iter = SWBUtils.ERROR.getErrorElements();
                //int pos = getErrPos(hmerr, id);
                
                long pid = 0;
                long nid = 0;
                int mpos =0;
                boolean show_p=false;
                boolean show_n = false;
                //ErrorElement ee_tmp = null;
                if(null!=hmerr&&!hmerr.isEmpty())
                {
                    mpos = getErrPos(hmerr, id);
                    if(mpos==1)
                    {
                        show_p=false;
                    }
                    if(mpos>1)
                    {
                        show_p=true;
                        pid=getErrID(hmerr,mpos-1);

                    }
                    if(mpos==1&&mpos==hmerr.size())
                    {
                        show_n=false;
                    }
                    if((mpos>0||mpos==0)&&mpos<hmerr.size())
                    {
                        show_n=true;
                        nid=getErrID(hmerr,mpos+1);
                    }
                }
                out.println("<tr ><td colspan=\"2\"><table border=\"0\" width=\"98%\" cellpadding=\"5\" cellspacing=\"0\"><tr><td align=\"right\" width=\"50%\">&nbsp;");
                if(show_n)
                {
                    SWBResourceURL urlDetailn = paramRequest.getRenderUrl();
                    urlDetailn.setParameter("act","detail");
                    urlDetailn.setParameter("idErr",Long.toString(nid));
                    //out.println("<button dojoType=\"dijit.form.Button\" onclick=\"submitUrl('" + urlDetailn + "',this.domNode); return false;\">" + paramRequest.getLocaleString("btnprevious")+ "</button>");
                    out.println("<input type=\"button\" value=\""+paramRequest.getLocaleString("btnprevious")+"\" onclick=\"window.location='"+urlDetailn+"'\">");
                }
                
                out.println(" </td><td align=\"left\" width=\"50%\">");
                if(show_p)
                {
                    SWBResourceURL urlDetailp = paramRequest.getRenderUrl();
                    urlDetailp.setParameter("act","detail");
                    urlDetailp.setParameter("idErr",Long.toString(pid));
                    //out.println("<button dojoType=\"dijit.form.Button\" onclick=\"submitUrl('" + urlDetailp + "',this.domNode); return false;\">" + paramRequest.getLocaleString("btnnext")+ "</button>");
                    out.println(" <input type=\"button\" value=\""+paramRequest.getLocaleString("btnnext")+"\" onclick=\"window.location='"+urlDetailp+"'\">");
                }
                out.println("&nbsp;</td></tr></table></td></tr>");
                out.println("<tr>");
            out.println(" <td colspan=\"2\">"+paramRequest.getLocaleString("msgErrorElementDetail")+"</td>");
            out.println("</tr>");
                boolean find = false;
                while(iter.hasNext()){
                    ee = (ErrorElement) iter.next();
                    if(ee.getId()==id){
                        find=true;
                        mensaje = SWBUtils.XML.replaceXMLChars(ee.getMessage());
                        identificador = Long.toString(ee.getId());
                        fecha = ee.getDate().toString();
                        stacktrace = SWBUtils.XML.replaceXMLChars(ee.getStackTrace());
                        break;  
                    }
                    ee=null;
                }
                if(find){
                    out.println("<tr>");
                    out.println(" <td width=\"150\" align=\"right\">"+paramRequest.getLocaleString("msgId")+"</td>");
                    out.println(" <td >"+identificador+"</td>");
                    out.println("</tr>");
                    out.println("<tr>");
                    out.println(" <td width=\"150\" align=\"right\">"+paramRequest.getLocaleString("msgMessage")+"</td>");
                    out.println(" <td >"+mensaje+"</td>");
                    out.println("</tr>");
                    out.println("<tr>");
                    out.println(" <td width=\"150\" align=\"right\">"+paramRequest.getLocaleString("msgDate")+"</td>");
                    out.println(" <td >"+fecha+"</td>");
                    out.println("</tr>");
                    out.println("<tr>");
                    out.println(" <td width=\"150\" align=\"right\">"+paramRequest.getLocaleString("msgStackTrace")+"</td>");
                    out.println(" <td ><pre >"+stacktrace+"</pre></td>");
                    out.println("</tr>");        
                }
                else{
                    out.println("<tr><td colspan=\"2\" ><b>"+paramRequest.getLocaleString("msgErrorNotFoundErrorElement")+"</b></td></tr>");
                }
            }
            else{
                out.println("<tr><td colspan=\"2\" ><b>Error en los parametros.</b></td></tr>");
            }
            SWBResourceURL urlBack = paramRequest.getRenderUrl();
            SWBResourceURL urlSend = paramRequest.getRenderUrl();
            out.println("<tr >");
            out.println(" <td colspan=\"2\">");
            out.println("<form id=\""+getResourceBase().getId()+"/ErrorEV\" method=\"post\" action=\""+urlSend.toString()+"\">");
            out.println("<input type=\"hidden\" name=\"act\" value=\"send\">");
            out.println("<input type=\"hidden\" name=\"idErr\" value=\""+identificador+"\">");
            out.println("<input type=\"hidden\" name=\"mensaje\" value=\""+mensaje+"\">");
            out.println("<input type=\"hidden\" name=\"fecha\" value=\""+fecha+"\">");
            out.println("<input type=\"hidden\" name=\"stacktrace\" value=\""+stacktrace+"\">");

            //out.println("<button dojoType=\"dijit.form.Button\" onclick=\"submitForm('"+getResourceBase().getId()+"/ErrorEV'); return false;\">" + paramRequest.getLocaleString("msgBtnSendError")+ "</button>");
            //out.println("<button dojoType=\"dijit.form.Button\" onclick=\"submitUrl('" + urlBack + "',this.domNode); return false;\">" + paramRequest.getLocaleString("msgBtnBack")+ "</button>");
            out.println("<input type=submit value=\""+paramRequest.getLocaleString("msgBtnSendError")+"\" >&nbsp;<input type=button value=\""+paramRequest.getLocaleString("msgBtnBack")+"\" onclick=\"window.location='"+urlBack.toString()+"';\">");
            out.println("</form>");
            out.println("</td>");
            out.println("</tr>");
            out.println("</table>");
            out.println("</fieldset>");
            out.println("</div>");
        }   
    }
    
    public int getErrPos(Vector hmErr, long err_id)
    {
        int pos=0;
        if(null!=hmErr&&!hmErr.isEmpty())
        {
            Iterator itepos = hmErr.iterator();
            while(itepos.hasNext())
            {
                pos++;
                ErrorElement key = (ErrorElement) itepos.next();
                if(key.getId()==err_id)
                {
                    break;
                }
            }
        }
        return pos;
    }
    
    public long getErrID(Vector hmErr, int pos)
    {
        long ret=0;
        int cont = 0;
        if(null!=hmErr&&!hmErr.isEmpty())
        {
            Iterator itepos = hmErr.iterator();
            while(itepos.hasNext())
            {
                cont++;
                ErrorElement key = (ErrorElement) itepos.next();
                if(pos==cont)
                {
                    ret = key.getId();
                    break;
                }
            }
        }
        return ret;
    }
}
