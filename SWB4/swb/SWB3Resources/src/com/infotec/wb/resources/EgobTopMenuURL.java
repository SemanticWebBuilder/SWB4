/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.infotec.wb.resources;

import com.infotec.wb.util.WBUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.WebPage;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.w3c.dom.Document;

/**
 *
 * @author jorge.jimenez
 */
public class EgobTopMenuURL extends EgobTopMenu
{
    /**
     * Crea un nuevo objeto EgobTopMenuURL.
     */
    String strSeparator = " I ";


    private static Logger log = SWBUtils.getLogger(EgobTopMenuURL.class);


    public String doView() throws SWBResourceException, IOException
    {
        Document dom = base.getDom();
        if (dom == null) throw new SWBResourceException("Dom nulo");
        return menu;
    }



    /**
     * Obtiene la versión html del recurso.
     *
     * @param     request   El servlet container crea un objeto HttpServletRequest y
     *                      se pasa como argumento al método del servlet.
     * @param     response  El servlet container crea un objeto HttpServletResponse y
     *                      se pasa como argumento al método del servlet.
     * @param     user      El usuario concurrente que solicita el tópico.
     * @param     topic     El tópico que solicita el recurso.
     * @param     arguments Argumentos del recurso en el template.
     * @return    Regresa un nuevo String que contiene el código html del recurso.
     * @exception com.infotec.appfw.exception.AFException
     *              Si se origina cualquier error en el recurso al traer el html.
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out=response.getWriter();
        WebPage wpage=paramRequest.getWebPage();
        StringBuffer html = new StringBuffer();
        if (paramRequest.getArgument("separator") != null) strSeparator = (String) paramRequest.getArgument("separator");
        if (paramRequest.getArgument("frame") != null)
        {
            SWBResourceURL urlBaseCallDirect=paramRequest.getRenderUrl();
            urlBaseCallDirect.setCallMethod(paramRequest.Call_DIRECT);
            html.append("<FRAME SRC=\"");
            html.append(urlBaseCallDirect.toString());
            html.append("\" NAME=\"top\" SCROLLING=\"No\" noresize BORDER=0 frameborder=\"NO\">");
        } else
        {
            html.append("<html>");
            html.append("<head>");
            html.append("<link rel=\"stylesheet\" href=\"" + SWBPortal.getWebWorkPath() + base.getWorkPath() + "/css/styles.html\" type=\"text/css\">");
            html.append("</head>");
            html.append("<body bgcolor=\"#FFFFFF\" text=\"#000000\" topmargin=0 leftmargin=20 marginwidth=20 marginheight=0>");
            html.append("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
            html.append(doView());
            html.append("\r\n<tr>");
            html.append("\r\n<td colspan=2>");
            html.append("<font face=\"Arial\" color=\"565656\" size=\"1\">");
            HashMap map = new HashMap();
            map.put("separator", strSeparator);
            map.put("cssclass", "ligasruta");
            map.put("selectcolor", "#CC3300");
            map.put("target", "_top");
            html.append(wpage.getPath(map));
            html.append("</font>");
            html.append("\r\n</td>");
            html.append("\r\n</tr>");
            html.append("\r\n<tr>");
            html.append("\r\n<td colspan=2>");
            html.append("<font face=\"Arial\" color=\"565656\" size=\"1\">");
            html.append("<b>Esta información es responsabilidad de la fuente original</b>");
            html.append("</font>");
            html.append("\r\n</td>");
            html.append("\r\n</tr>");
            html.append("</table>");
            html.append("</body>");
            html.append("</html>");
        }
        out.println(html.toString());
    }


}