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
import org.semanticwb.nlp.translation.SWBCssToXmlTranslator;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.api.SWBParamRequest;

import org.semanticwb.portal.admin.admresources.util.*;

public class StyleInner {
    private static Logger log = SWBUtils.getLogger(Styler.class);

    private HashMap mm;

    public StyleInner(Resource base) {
        mm = new HashMap();
    }

    public String render(SWBParamRequest paramRequest, String path) throws Exception {
        Resource base = paramRequest.getResourceBase();
        StringBuilder script = new StringBuilder();
        StylerDomParser handler = null;

        String css = base.getAttribute("css");        
        if(css==null) {
            StringBuilder c = new StringBuilder();
            InputStream is = getClass().getResourceAsStream(path);
            BufferedInputStream bin = new BufferedInputStream(is);
            byte[] contents = new byte[1024];
            int bytesRead=0;
            try{
                while( (bytesRead=bin.read(contents)) != -1 )
                    c.append(new String(contents, 0, bytesRead));
            }catch(IOException ioe) {
                c.delete(0, c.length());
                c.append(".title{}");
                c.append(".subtitle{}");
                c.append(".header{}");
                c.append(".content .parrafo{}");
                c.append(".extra{}");
                c.append(".footer{}");
            }
            css = c.toString();
        }

        SWBCssToXmlTranslator csst = new SWBCssToXmlTranslator();
        handler = new StylerDomParser(csst.translateCSS(css), base);

        SWBResourceURL url = paramRequest.getRenderUrl().setCallMethod(paramRequest.Call_DIRECT).setMode("fillStyle");
        script.append("<script type=\"text/javascript\">\n");
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

        //url = paramRequest.getActionUrl();
        script.append("<div class=\"soria\" style=\"float:left; padding:10px;\">\n");
        //script.append("<form id=\"frmResource_0999\" name=\"frmResource\" method=\"post\" action=\""+ url+"\"> ");
        script.append(handler.parse());
        /*script.append("<table width=\"100%\" align=\"left\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\">\n");
        script.append("  <tr><td>\n");
        script.append("  <button dojoType=\"dijit.form.Button\" type=\"submit\">Guardar</button>\n");
        script.append("  <button dojoType=\"dijit.form.Button\" type=\"reset\">Restablecer</button>\n");
        script.append("  </td></tr>\n");
        script.append("</table>\n");*/
        //script.append("</form>\n");
        script.append("</div>\n");

        mm.put(base.getId(), handler.getTabs());

        return script.toString();
    }

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

    public HashMap getMm(String key) {
        return (HashMap)mm.get(key);
    }


}