package org.semanticwb.portal.admin.resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.IPFilter;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

/**
 *
 * @author serch
 */
public class SWBAIPFilter extends GenericResource
{

    private Logger log = SWBUtils.getLogger(SWBAIPFilter.class);
    private int pagezise = 10;

    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        response.setContentType("text/html;charset=ISO-8859-1");
        String val = request.getParameter("suri");
        WebSite ws = SWBContext.getWebSite(val);
        boolean empty = (ws == null ? true : false);
        IPFilter[] lista = null;
        if (!empty)
        {
            Iterator<IPFilter> itip = ws.listIPFilters();
            ArrayList<IPFilter> l1 = new ArrayList<IPFilter>();
            while (itip.hasNext())
            {
                l1.add(itip.next());
            }
            lista = l1.toArray(new IPFilter[0]);
        }

        int start = 0;
        int pag = pagezise;
        try
        {
            start = Integer.parseInt(request.getParameter("start"));
            pag = Integer.parseInt(request.getParameter("count"));
        } catch (Exception ne)
        {
        }
        int cant = 0;
        if (!empty && null != lista)
        {
            cant = lista.length;
        }
        JSONObject jobj = new JSONObject();
        JSONArray jarr = new JSONArray();
        try
        {
            //JSONObject tjson = new JSONObject();
            jobj.put("numRows", cant); //lista.length);

            jobj.put("items", jarr);
        } catch (JSONException njse)
        {
        }
    //    JSONObject obj = new JSONObject();
        try
        {
            if (!empty && cant > 0)
            {
                int end = start + pag;
                while (start < end && start < lista.length) {
                    JSONObject obj = new JSONObject();
                obj.put("@uri", "javascript:parent.addNewTab('" + lista[start] + "',null,'" + lista[start].getEncodedURI() + "')");
                obj.put("ipAddr", lista[start].getIpNumber());
                obj.put("desc", lista[start].getTitle());
                obj.put("rest", lista[start].getAction());
                obj.put("acct", lista[start].getUpdated());
                jarr.put(obj);
                start++;
                }
            }
        } catch (JSONException njsone)
        {
        }
        response.getOutputStream().println(jobj.toString());
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        StringBuffer ret = new StringBuffer("");
        SWBResourceURL url = paramRequest.getRenderUrl();
        url.setMode(SWBResourceURL.Mode_EDIT);
        url.setCallMethod(SWBResourceURL.Call_DIRECT);
        ret.append("<script type=\"text/javascript\">\n" +
                "           dojo.require(\"dojo.parser\");\n" +
                "                   dojo.require(\"dijit.layout.ContentPane\");\n" +
                "                   dojo.require(\"dijit.form.FilteringSelect\");\n" +
                "                   dojo.require(\"dijit.form.CheckBox\");\n" +
                "                   dojo.require(\"dojox.grid.DataGrid\");\n" +
                "                   dojo.require(\"dojox.data.QueryReadStore\");\n" +
                "                var Global_suri =''; \n" +
                "                var model =''; \n" +
                "        </script>\n");
        ret.append("<form id=\"" + IPFilter.swb_IPFilter.getClassName() + "/create\" dojoType=\"dijit.form.Form\" class=\"swbform\" ");
        ret.append("onSubmit=\"return false;\" method=\"POST\">");
        ret.append("\t<fieldset>\n\t<label for=\"Sitios\">Sitio</label>");
        Iterator<WebSite> itur = SWBContext.listWebSites();
        ret.append("\n\t\t\t\t<select dojoType=\"dijit.form.FilteringSelect\" autocomplete=\"false\" name=\"userRepository\" id=\"Sitios\"  >");
        ret.append("\n\t\t\t\t\t<option value=\"\"></option>"); //todo Add Language
        while (itur.hasNext())
        {
            WebSite ur = itur.next();
            ret.append("\n\t\t\t\t\t<option value=\"" + ur.getId() + "\">" + ur.getTitle() + "</option>"); //todo Add Language
        }
        ret.append("\n\n<script type=\"dojo/method\" event=\"onChange\" args=\"suri\">\n");
        ret.append("  Global_suri = suri;\n");
        ret.append("   model = new dojox.data.QueryReadStore({\n" +
                "				url:\"" + url + "?suri=\"+Global_suri,\n" +
                "		requestMethod:\"post\"});\n" +
                "       grid1.setStore(model);\n" );
        ret.append("</script> \n");
        ret.append("\n\t\t\t\t</select>");
        ret.append("\n\t\t\t</fieldset>\n</form>");

        ret.append("<script type=\"text/javascript\">\n" +
                "       // data grid layout: a grid view is a group of columns\n" +
                "       var page= 0;\n" +
                "       var start= 0;\n" +
                "       var batchSize=" + pagezise + ";                        \n" +
                "               // Data Grid layout\n" +
                "               // A grid view is a group of columns\n" +
                "       var view1 = [\n" +
                "                    {name: 'Dirección IP',width:'30%', field: \"ipAddr\"},\n" +
                "                    {name: 'Descripción',width:'30%', field: \"desc\"},\n" +
                "                    {name: 'Restricción',width:'20%',field: \"rest\"},\n " +
                "                    {name: 'Ultima actualización',width:'20%',field: \"acct\"},\n " +
                "            ]\n ;" +
                "       var layout = [ view1 ];\n" +
                //  "       model = new dojox.grid.data.Objects([{key: \"login\"}, {key: \"name\"},{key: \"papellid\"},{key: \"sapellid\"},{key: \"email\"}], null);\n" +

                "       \n" +
                "       dojo.addOnLoad(function(){\n" +
                "   	model = new dojox.data.QueryReadStore({\n" +
                "				url:\"" + url + "?suri=\"+Global_suri,\n" +
                "		requestMethod:\"post\"\n" +
                "	});\n\n" +
                "       grid1.setStore(model);\n" +
                "       grid1.setStructure(layout);\n" +
                "       });\n" +
                "       function openOther(evt){\n" +
                "           var row=evt.rowIndex;\n" +
                "           var curItem = grid1.getItem(row);\n" +
                "           var rowID=model.getValue(curItem,\"@uri\");\n" +
                "           eval(rowID);\n" +
                "           return false;\n" +
                "       }\n" +
                "           \n" +
                "        </script>\n");

        ret.append("<div id=\"grid1\" jsid=\"grid1\" dojoType=\"dojox.grid.DataGrid\" model=\"model\" structure=\"layout\" onRowDblClick=\"openOther\" autoWidth_=\"true\" rowsPerPage=\"10\" >\n</div>");
        url.setMode(SWBResourceURL.Mode_HELP);
        ret.append("<fieldset><button dojoType=\"dijit.form.Button\" type=\"button\" onclick=\"parent.showDialog('"+url+"');\">Agregar</button></fieldset>");

        response.getWriter().write(ret.toString());
    }

    @Override
    public void doHelp(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
                StringBuffer ret = new StringBuffer(1000);
                 SWBResourceURL url = paramRequest.getActionUrl();
//                 ret.append("<script type=\"text/javascript\">\n"+
//        "           dojo.require(\"dojo.parser\");\n"+
//        "                   dojo.require(\"dijit.layout.ContentPane\");\n"+
//        "                   dojo.require(\"dijit.form.FilteringSelect\");\n"+
//        "                   dojo.require(\"dijit.form.CheckBox\");\n"+
//        "        </script>\n");
      //http://www.semanticwebbuilder.org/swb4/ontology#User
        ret.append("<form id=\""+IPFilter.swb_IPFilter.getClassName()+"/create\" dojoType=\"dijit.form.Form\" class=\"swbform\" ");
        ret.append("action=\""+url+"\" ");
        ret.append("onSubmit=\"submitForm('"+IPFilter.swb_IPFilter.getClassName()+"/create');return false;\" method=\"POST\">");
        ret.append("\t<fieldset>\n\t<table>\n\t\t<tr>\n\t\t\t<td width=\"200px\" align=\"right\">\n\t\t\t\t<label>Sitios</label>");
        ret.append("\n\t\t\t</td>\n\t\t\t<td>");
        Iterator<WebSite> itur = SWBContext.listWebSites();
        ret.append("\n\t\t\t\t<select dojoType=\"dijit.form.FilteringSelect\" autocomplete=\"false\" name=\"webSite\" id=\"webSite\" >");
        while (itur.hasNext())
        {
            WebSite ur = itur.next();
            ret.append("\n\t\t\t\t\t<option value=\"" + ur.getId() + "\">" + ur.getTitle() + "</option>"); //todo Add Language
        }
        ret.append("\n\t\t\t\t</select>\n\t\t\t</td>\n\t\t</tr>");
        ret.append("\n\t\t<tr>\n\t\t\t<td width=\"200px\" align=\"right\">\n\t\t\t\t<label>Título <em>*</em></label>\n\t\t\t</td>\n\t\t\t<td>");
        ret.append("<input type=\"text\" name=\"titulo\" dojoType=\"dijit.form.ValidationTextBox\" required=\"true\" " +
                "promptMessage=\"Asigna un nombre a este filtro.\" invalidMessage=\"Título del filtro es requerido.\" trim=\"true\" />");
        ret.append("\n\t\t\t</td>\n\t\t</tr>");
        ret.append("\n\t<tr>\n\t\t<td align=\"center\" colspan=\"2\">");
        ret.append("<button dojoType='dijit.form.Button' type=\"submit\">Guardar</button>\n");
        ret.append("<button dojoType='dijit.form.Button' onclick=\"dijit.byId('swbDialog').hide();\">Cancelar</button>\n");
        ret.append("\n\t\t\t</td>\n\t\t</tr>\n\t</table>\n\t</fieldset>\n</form>");
                 response.getWriter().write(ret.toString());
    }

    @Override
    public void doXML(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        StringBuffer ret = new StringBuffer();
        
        ret.append("<script type=\"text/javascript\">\n\ndojo.require(\"dojo.parser\");\ndijit.byId('swbDialog').hide();\nshowStatus('Filtro creado');\n");
        ret.append("addNewTab('"+request.getParameter("suri")+"','/"+SWBPlatform.getContextPath()+"swbadmin/jsp/objectTab.jsp','"+request.getParameter("label")+"');\n");
        ret.append("</script>");
        response.getWriter().write(ret.toString());
    }



    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException
    {
        String website = request.getParameter("webSite");
        String titulo = request.getParameter("titulo");
        if (null==website||website.length()==0||null==titulo||titulo.length()==0) {
            response.setMode(SWBResourceURL.Mode_HELP);
            return;
        }
        WebSite ws = SWBContext.getWebSite(website);
        response.setMode(SWBResourceURL.Mode_XML);
        IPFilter ipFilter = ws.createIPFilter();

        ipFilter.setTitle(titulo);
        response.setRenderParameter("suri", ipFilter.getURI());
        response.setRenderParameter("label", ipFilter.getTitle());
    }
}
