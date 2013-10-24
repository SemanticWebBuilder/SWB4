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
//import org.semanticwb.nlp.translation.SWBCssToXmlTranslator;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBResourceException;

import org.semanticwb.portal.admin.admresources.util.*;



// TODO: Auto-generated Javadoc
/**
 * The Class Styler.
 */
public class Styler extends GenericResource {
//    
//    /** The log. */
//    private static Logger log = SWBUtils.getLogger(Styler.class);
//
//    /** The mm. */
//    private HashMap mm;
//
//    /**
//     * Instantiates a new styler.
//     */
//    public Styler() {
//        mm = new HashMap();
//    }
//
//    /* (non-Javadoc)
//     * @see org.semanticwb.portal.api.GenericResource#doView(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
//     */
//    @Override
//    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
//        Resource base = getResourceBase();
//        StringBuilder script = new StringBuilder();
//        //StyleHandler handler = null;
//        StylerDomParser handler = null;
//        try {
//            //InputStream is = getClass().getResourceAsStream("/org/semanticwb/portal/admin/admresources/util/style.xml");
//            //handler= new StyleHandler(is, base);
//            String css = ".title {\n" +
//                    "background-color:white;\n" +
//                    "border: 1px solid blue;\n" +
//                    "}\n" +
//                    ".header {\n" +
//                    "background-image:url('mi/imagen.jpg');\n" +
//                    "}\n" +
//                    ".cuerpo .parrafo {\n" +
//                    "text-decoration:none;\n" +
//                    "display:block;\n" +
//                    "}";
//            
//            SWBCssToXmlTranslator csst = new SWBCssToXmlTranslator();            
//            handler = new StylerDomParser(csst.translateCSS(css), base);
//
//
//            SWBResourceURL url = paramRequest.getRenderUrl().setCallMethod(paramRequest.Call_DIRECT).setMode("fillStyle");
//            script.append("<script type=\"text/javascript\">\n");
//            script.append(" dojo.require(\"dijit.form.Button\");\n");
//
//            script.append("function sendData(tab, styleName, styleValue) {\n");
//            script.append("    var xhrArgs = {\n");
//            script.append("        url:'"+url+"'+'?stel='+tab+'@'+styleName+'@'+styleValue, \n");
//            script.append("        handleAs : 'text',\n");
//            script.append("        load: function(response){},\n");
//            script.append("        error: function(error){alert('hubo un error con código '+error);}\n");
//            script.append("    };\n");
//            script.append("    var deferred = dojo.xhrPost(xhrArgs);\n");
//            script.append("}\n");
//
//            script.append("</script>\n");
//            script.append("<div class=\"soria\" style=\"width:750px;\" style=\"float:left;\">\n");
//            url = paramRequest.getActionUrl();
//            script.append("<form id=\"frmResource\" name=\"frmResource\" method=\"post\" action=\""+ url+"\"> ");
//            //script.append(handler.getScript());
//            script.append(handler.parse());
//            script.append("\n<table width=\"100%\" align=\"center\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\"> ");
//            script.append("\n <tr><td>");
//            script.append("\n <button dojoType=\"dijit.form.Button\" type=\"submit\">"+paramRequest.getLocaleString("usrmsg_Styler_doView_submit")+"</button>");
//            script.append("\n <button dojoType=\"dijit.form.Button\" type=\"reset\">"+paramRequest.getLocaleString("usrmsg_Styler_doView_reset")+"</button>");
//            script.append("\n </td></tr>");
//            script.append("\n</table> ");
//            script.append("</form> ");
//            script.append("</div>");
//
//            mm.put(base.getId(), handler.getTabs());
//        }catch(Exception e) {
//            //e.printStackTrace();
//            System.out.println("*******************error\n\n"+e);
//        }
//        PrintWriter out = response.getWriter();
//        out.println(script.toString());
//        out.flush();
//    }
//
//    /* (non-Javadoc)
//     * @see org.semanticwb.portal.api.GenericResource#processAction(javax.servlet.http.HttpServletRequest, org.semanticwb.portal.api.SWBActionResponse)
//     */
//    @Override
//    public void processAction(javax.servlet.http.HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
//        System.out.println("\n\n********* processAction ****************");
//        /*Resource base = response.getResourceBase();
//        
//        base.setAttribute("", request.getParameter("txt"));
//        try{
//            base.updateAttributesToDB();
//        }catch(Exception e){
//            log.error("Error al guardar atributos del InlineTextArea. ",e);
//        }*/
//    }
//
//    /* (non-Javadoc)
//     * @see org.semanticwb.portal.api.GenericResource#processRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
//     */
//    @Override
//    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
//        if(paramsRequest.getMode().equalsIgnoreCase("fillStyle")) {
//            doEditStyle(request,response,paramsRequest);
//        }else {
//            super.processRequest(request, response, paramsRequest);
//        }
//    }
//
//    /**
//     * Do edit style.
//     * 
//     * @param request the request
//     * @param response the response
//     * @param paramRequest the param request
//     * @throws SWBResourceException the sWB resource exception
//     * @throws IOException Signals that an I/O exception has occurred.
//     */
//    public void doEditStyle(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
//        System.out.println("\n************ doEditStyle ************");
//        Resource base = getResourceBase();
//        String stel = request.getParameter("stel");
//        System.out.println("stel="+stel);
//        String[] tkns = stel.split("@",3);
//
//        HashMap matriz = (HashMap)mm.get(base.getId());
//        if(matriz != null) {
//            try {
//                System.out.println("tkns[0]="+tkns[0]);
//                System.out.println("tkns[1]="+tkns[1]);
//                System.out.println("tkns[2]="+tkns[2]);
//
//                HashMap h = (HashMap)matriz.get(tkns[0]);
//                h.put(tkns[1], tkns[2]+";");
//                System.out.println("\n\n");
//                printMatriz();
//            }catch(IndexOutOfBoundsException iobe) {
//                System.out.println("\n error... "+iobe);
//            }
//        }else {
//            System.out.println("matriz es nulo");
//        }
//    }
//
//    /**
//     * Prints the matriz.
//     */
//    private void printMatriz() {
//        Resource base = getResourceBase();
//        HashMap matriz = (HashMap)mm.get(base.getId());
//        Iterator<String> it1 = matriz.keySet().iterator();
//        while(it1.hasNext()) {
//            String k = it1.next();
//            System.out.println("k="+k);
//            HashMap hm = (HashMap)matriz.get(k);
//            Iterator<String> it2 = hm.keySet().iterator();
//            while(it2.hasNext()) {
//                String l = it2.next();
//                System.out.println("key="+l+", value="+hm.get(l));
//            }
//
//
//            
//        }
//    }


}
