
package org.semanticwb.portal.admin.resources;

/**
 *
 * @author carlos.ramos
 */

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.nlp.translation.SWBCssToXmlTranslator;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBResourceException;

import org.semanticwb.portal.admin.admresources.util.*;

public class StyleInner {
    private static Logger log = SWBUtils.getLogger(Styler.class);

    private HashMap mm;
    //private Resource base;

    public StyleInner(Resource base) {
        mm = new HashMap();
        //this.base = base;
    }

    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        Resource base = paramRequest.getResourceBase();
        StringBuilder script = new StringBuilder();
        //StyleHandler handler = null;
        StylerDomParser handler = null;
        try {
            //InputStream is = getClass().getResourceAsStream("/org/semanticwb/portal/admin/admresources/util/style.xml");
            //handler= new StyleHandler(is, base);
            String css = ".title {\n" +
                    "background-color:white;\n" +
                    "border: 1px solid blue;\n" +
                    "}\n" +
                    ".header {\n" +
                    "background-image:url('mi/imagen.jpg');\n" +
                    "}\n" +
                    ".cuerpo .parrafo {\n" +
                    "text-decoration:none;\n" +
                    "display:block;\n" +
                    "}";
            SWBCssToXmlTranslator csst = new SWBCssToXmlTranslator();
            handler = new StylerDomParser(csst.translateCSS(css), base);


            SWBResourceURL url = paramRequest.getRenderUrl().setCallMethod(paramRequest.Call_DIRECT).setMode("fillStyle");
            script.append("<script type=\"text/javascript\">\n");
            script.append(" dojo.require(\"dijit.form.Button\");\n");

            script.append("function sendData(tab, styleName, styleValue) {\n");
            script.append("    var xhrArgs = {\n");
            script.append("        url:'"+url+"'+'?stel='+tab+'@'+styleName+'@'+styleValue, \n");
            script.append("        handleAs : 'text',\n");
            script.append("        load: function(response){},\n");
            script.append("        error: function(error){alert('hubo un error con código '+error);}\n");
            script.append("    };\n");
            script.append("    var deferred = dojo.xhrPost(xhrArgs);\n");
            script.append("}\n");

            script.append("</script>\n");
            script.append("<div class=\"soria\" style=\"width:99%;\" style=\"float:left;\">\n");
            url = paramRequest.getActionUrl();
            script.append("<form id=\"frmResource\" name=\"frmResource\" method=\"post\" action=\""+ url+"\"> ");
            //script.append(handler.getScript());
            script.append(handler.parse());
            script.append("\n<table width=\"100%\" align=\"center\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\"> ");
            script.append("\n <tr><td>");
            script.append("\n <button dojoType=\"dijit.form.Button\" type=\"submit\">Guardar</button>");
            script.append("\n <button dojoType=\"dijit.form.Button\" type=\"reset\">Restablecer</button>");
            script.append("\n </td></tr>");
            script.append("\n</table> ");
            script.append("</form> ");
            script.append("</div>");

            mm.put(base.getId(), handler.getTabs());
        }catch(Exception e) {
            //e.printStackTrace();
            System.out.println("*******************error\n\n"+e);
        }
        PrintWriter out = response.getWriter();
        out.println(script.toString());
        out.flush();
    }

    
    /*public void processAction(javax.servlet.http.HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        System.out.println("\n\n********* processAction ****************");
        Resource base = response.getResourceBase();

        base.setAttribute("", request.getParameter("txt"));
        try{
            base.updateAttributesToDB();
        }catch(Exception e){
            log.error("Error al guardar atributos del InlineTextArea. ",e);
        }
    }*/

    
    /*public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        if(paramsRequest.getMode().equalsIgnoreCase("fillStyle")) {
            doEditStyle(request,response,paramsRequest);
        }else {
            super.processRequest(request, response, paramsRequest);
        }
    }*/

    /*public void doEditStyle(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest, Resource base) throws SWBResourceException, IOException {
        System.out.println("\n************ doEditStyle ************");

        String stel = request.getParameter("stel");
        System.out.println("stel="+stel);
        String[] tkns = stel.split("@",3);

        HashMap matriz = (HashMap)mm.get(base.getId());
        if(matriz != null) {
            try {
                System.out.println("tkns[0]="+tkns[0]);
                System.out.println("tkns[1]="+tkns[1]);
                System.out.println("tkns[2]="+tkns[2]);

                HashMap h = (HashMap)matriz.get(tkns[0]);
                h.put(tkns[1], tkns[2]+";");
                System.out.println("\n\n");
                printMatriz(base);
            }catch(IndexOutOfBoundsException iobe) {
                System.out.println("\n error... "+iobe);
            }
        }else {
            System.out.println("matriz es nulo");
        }
    }*/

    public void printMatriz(String key) {
        HashMap matriz = (HashMap)mm.get(key);
        if(matriz!=null) {
            Iterator<String> it1 = matriz.keySet().iterator();
            while(it1.hasNext()) {
                String k = it1.next();
                System.out.println("k="+k);
                HashMap hm = (HashMap)matriz.get(k);
                Iterator<String> it2 = hm.keySet().iterator();
                while(it2.hasNext()) {
                    String l = it2.next();
                    System.out.println("key="+l+", value="+hm.get(l));
                }
            }
        }
    }

    public HashMap getMm() {
        return mm;
    }


}
