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
            try{
                byte[] contents = new byte[1024];
                int bytesRead=0;
                InputStream is = getClass().getResourceAsStream(path);
                BufferedInputStream bin = new BufferedInputStream(is);
                while( (bytesRead=bin.read(contents)) != -1 )
                    c.append(new String(contents, 0, bytesRead));
            }catch(IOException ioe) {
                log.error("Error while getting css resource attribute: "+base.getId() +"-"+ base.getTitle(), ioe);
                c.delete(0, c.length());
                c.append(".title{}");
                c.append(".content{}");
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
        script.append(handler.parse());
        
        mm.put(base.getId(), handler.getTabs());
        return script.toString();
    }

    public void printMatriz(String key) {
        HashMap matriz = (HashMap)mm.get(key);
        if(matriz!=null) {
            Iterator<String> it1 = matriz.keySet().iterator();
            while(it1.hasNext()) {
                String k = it1.next();
                HashMap hm = (HashMap)matriz.get(k);
                Iterator<String> it2 = hm.keySet().iterator();
                while(it2.hasNext()) {
                    String l = it2.next();
                }
            }
        }
    }

    public HashMap getMm(String key) {
        return (HashMap)mm.get(key);
    }


}