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

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
//import org.semanticwb.nlp.translation.SWBCssToXmlTranslator;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.api.SWBParamRequest;

import org.semanticwb.portal.admin.admresources.util.*;

// TODO: Auto-generated Javadoc
/**
 * The Class StyleInner.
 */
public class StyleInner {
//    
//    /** The log. */
//    private static Logger log = SWBUtils.getLogger(Styler.class);
//
//    /** The mm. */
//    private HashMap<String,HashMap> mm;
//
//    /**
//     * Instantiates a new style inner.
//     * 
//     * @param base the base
//     */
//    public StyleInner(Resource base) {
//        mm = new HashMap();
//    }
//
//    /**
//     * Render.
//     * 
//     * @param paramRequest the param request
//     * @param path the path
//     * @return the string
//     * @throws Exception the exception
//     */
//    public String render(SWBParamRequest paramRequest, String path) throws Exception {
//        Resource base = paramRequest.getResourceBase();
//        StringBuilder script = new StringBuilder();
//        StylerDomParser handler = null;
//
//        String css = base.getAttribute("css");        
//        if(css==null) {
//            StringBuilder c = new StringBuilder();            
//            try{
//                byte[] contents = new byte[1024];
//                int bytesRead=0;
//                InputStream is = getClass().getResourceAsStream(path);
//                BufferedInputStream bin = new BufferedInputStream(is);
//                while( (bytesRead=bin.read(contents)) != -1 )
//                    c.append(new String(contents, 0, bytesRead));
//            }catch(IOException ioe) {
//                log.error("Error while getting css resource attribute: "+base.getId() +"-"+ base.getTitle(), ioe);
//                c.delete(0, c.length());
//                c.append(".title{}");
//                c.append(".content{}");
//                c.append(".footer{}");
//            }
//            css = c.toString();
//        }
//        SWBCssToXmlTranslator csst = new SWBCssToXmlTranslator();
//        handler = new StylerDomParser(csst.translateCSS(css), base);
//
//        SWBResourceURL url = paramRequest.getRenderUrl().setCallMethod(paramRequest.Call_DIRECT).setMode("fillStyle");
//        script.append("<script type=\"text/javascript\">\n");
//        script.append("function sendData(tab, styleName, styleValue) {\n");
//        script.append("    var xhrArgs = {\n");
//        script.append("        url:'"+url+"'+'?stel='+tab+'@'+styleName+'@'+styleValue, \n");
//        script.append("        handleAs : 'text',\n");
//        script.append("        load: function(response){},\n");
//        script.append("        error: function(error){alert('hubo un error con código '+error);}\n");
//        script.append("    };\n");
//        script.append("    var deferred = dojo.xhrPost(xhrArgs);\n");
//        script.append("}\n");
//        script.append("</script>\n");
//        script.append(handler.parse());
//        
//        mm.put(base.getId(), handler.getTabs());
//        return script.toString();
//    }
//
//    /**
//     * Prints the matriz.
//     * 
//     * @param key the key
//     */
//    public void printMatriz(String key) {
//        HashMap matriz = (HashMap)mm.get(key);
//        if(matriz!=null) {
//            Iterator<String> it1 = matriz.keySet().iterator();
//            while(it1.hasNext()) {
//                String k = it1.next();
//                HashMap hm = (HashMap)matriz.get(k);
//                Iterator<String> it2 = hm.keySet().iterator();
//                while(it2.hasNext()) {
//                    String l = it2.next();
//                }
//            }
//        }
//    }
//
//    /**
//     * Gets the mm.
//     * 
//     * @param key the key
//     * @return the mm
//     */
//    public HashMap getMm(String key) {
//        return mm.get(key);
//    }


}