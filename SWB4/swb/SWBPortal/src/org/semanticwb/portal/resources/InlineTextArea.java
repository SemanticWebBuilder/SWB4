
package org.semanticwb.portal.resources;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.User;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBResourceException;

/**
 *
 * @author carlos.ramos
 */
public class InlineTextArea extends GenericAdmResource {
    private static Logger log = SWBUtils.getLogger(Banner.class);

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        Resource base = getResourceBase();
        try {
            SWBResourceURL url=paramRequest.getActionUrl();

            out.println("<script type=\"text/javascript\">");
            out.println("dojo.require(\"dijit.InlineEditBox\");");
            out.println("dojo.require(\"dijit.form.Textarea\");");

            User user = paramRequest.getUser();
            if(user.isSigned()) {
            }

            String style = base.getAttribute("style")==null?"width:400px;":base.getAttribute("style");

            out.println("var iledit_"+base.getId()+";");
            out.println("dojo.addOnLoad( function() {");
            out.println("  iledit_"+base.getId()+" = new dijit.InlineEditBox({");
            out.println("    id: \"inline_"+base.getId()+"\",");
            out.println("    autoSave: false,");
            out.println("    editor: \"dijit.form.Textarea\",");
            out.println("    style: \""+style+"\",");
            out.println("    onChange: function(value){");
            out.println("        postHtml('"+url+"?txt='+value,'inline_"+base.getId()+"');");
            out.println("      }");
            out.println("    }, 'tb_"+base.getId()+"');");
            out.println("  }");
            out.println(");");
            out.println("</script>");

            String cssClass = base.getAttribute("cssClass")==null?"":" class=\""+base.getAttribute("cssClass")+"\" ";
            out.println("<div id=\"tb_"+base.getId()+"\""+cssClass+">");
            out.println(base.getAttribute("text", ""));
            out.println("</div>");

        }catch (Exception e) {
            log.error("Error in resource Banner while bringing HTML", e);
        }
    }

    @Override
    public void processAction(javax.servlet.http.HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        Resource base = response.getResourceBase();
        base.setAttribute("text", request.getParameter("txt"));
        try{
            base.updateAttributesToDB();
        }catch(Exception e){
            System.out.println(e);
        }
    }

}
