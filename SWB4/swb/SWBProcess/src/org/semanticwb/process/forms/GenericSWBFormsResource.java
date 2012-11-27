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
package org.semanticwb.process.forms;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.SWBClass;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.admin.admresources.util.XmlBundle;
import org.semanticwb.process.model.FlowNodeInstance;
import org.semanticwb.process.model.ItemAwareReference;

/**
 *
 * @author jorge.jimenez
 */
public class GenericSWBFormsResource extends GenericResource{

    private static Logger log = SWBUtils.getLogger(GenericSWBFormsResource.class);

    XmlBundle bundle=null;
    static Hashtable bundles=new Hashtable();
    String xml=null;
    private final String saveOK="Save_OK";
    private final String removeOK="Remove_OK";
    private final String saveError="Save_Error";


    /**
     * Sets the resource base.
     *
     * @param base the new resource base
     * @throws SWBResourceException the sWB resource exception
     */
    @Override
    public void setResourceBase(Resource base) throws SWBResourceException
    {
        super.setResourceBase(base);
        String name=getClass().getName();
        bundle=(XmlBundle)bundles.get(name);
        if(bundle==null)
        {
            bundle= new XmlBundle(name,getClass().getName());
            bundles.put(name, bundle);
        }
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out=response.getWriter();
        User user=paramRequest.getUser();
        StringBuilder ret = new StringBuilder("");
        String action =  request.getParameter("act");
        if(action==null || (action!=null && action.trim().equals(""))) action=paramRequest.getAction();

        if(request.getParameter("msg")!=null){
            String msg=request.getParameter("msg");

            if(msg.equals(saveOK)) msg="Procesado...";
            else if(msg.equals(removeOK)) msg="Eliminado Ok en el lenguaje del usuario";
            ret.append(
                    "<script language=\"JavaScript\">\n"+
                    "   alert('"+msg+"');\n"+
                    "</script>\n");
        }

        String suri=request.getParameter("suri");
        //System.out.println("suri en GenericSWBFormsResource:"+suri);

        //out.println("<a href=\""+paramRequest.getRenderUrl().setMode(SWBResourceURL.Mode_EDIT).setParameter("suri", suri)+"\">[editar]</a>");

        SWBResourceURL urlAction = paramRequest.getActionUrl();
        if(action.equals("add") || action.equals("edit"))
        {
            urlAction.setAction("update");
            urlAction.setParameter("suri", suri);
            xml=bundle.getBundle(getClass().getName(), new java.util.Locale(user.getLanguage()));
            //System.out.println("xml en GenericSWBFormsResource:"+xml);
            if(xml!=null && xml.trim().length()>0) {
                SWBFormMgrLayer swbFormMgrLayer=new SWBFormMgrLayer(xml, paramRequest, request);
                String html=swbFormMgrLayer.getHtml();
                //System.out.println("html final:"+html);
                ret.append(html);
            }
        }
        out.println(ret.toString());
    }


    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String action=response.getAction();
        Resource base=getResourceBase();
        User user=response.getUser();

         String suri=request.getParameter("suri");
        if (suri == null && !action.equalsIgnoreCase("saveXMLFile"))
        {
            return;
        }
        FlowNodeInstance foi = (FlowNodeInstance)SWBPlatform.getSemanticMgr().getOntology().getGenericObject(suri);

        if("savecnf".equals(response.getAction()))
        {
            String data="";
            Iterator<ItemAwareReference> it=foi.listHeraquicalItemAwareReference().iterator();
            while(it.hasNext())
            {
                ItemAwareReference item=it.next();
                SWBClass obj=item.getProcessObject();
                //TODO: Revisar variables distintas de la misma clase

                SemanticClass cls=obj.getSemanticObject().getSemanticClass();
                Iterator<SemanticProperty> itp=cls.listProperties();
                while(itp.hasNext())
                {
                    SemanticProperty prop=itp.next();
                    String name=cls.getClassId()+"|"+prop.getPropId();
                    if(request.getParameter(name)!=null)
                    {
                        data+=name;
                        data+="|"+request.getParameter(name+"|s");
                        data+="\n";
                    }
                }
            }
            response.getResourceBase().setData(response.getWebPage(), data);
            response.setMode(response.Mode_VIEW);
        }else if(action!=null && action.equals("update"))
        {
            if(action.equals("update"))
            {   //Add or update resource.
                try
                {
                     xml=bundle.getBundle(getClass().getName(), new java.util.Locale(user.getLanguage()));
                     if(xml!=null && xml.trim().length()>0) {
                       SWBFormMgrLayer.update2DB(request, response, foi, xml);
                     }
                    //if(newSemObj!=null){
                    //    base.setAttribute("objInst", newSemObj.getURI());
                    //    base.updateAttributesToDB();
                        //response.setRenderParameter("msg", saveOK);
                    //}else{
                    //    response.setRenderParameter("msg", saveError);
                    //}
                    response.setAction(response.Action_EDIT);
                }
                catch(Exception e) { log.error(e); }
            }
            else if(action.equals("remove"))
            {
                    response.setAction(response.Action_EDIT);
                    response.setRenderParameter("msg", saveOK);
            }
        } else if (action != null && action.equalsIgnoreCase("saveXMLFile")) {
            String basepath = SWBPortal.getWorkPath() + base.getWorkPath() + "/";
            File xmlFile = new File(basepath);
            if (xmlFile.exists()) {
                try {
                    String value = null;
                    if (request.getParameter("hiddencode") != null &&
                            !request.getParameter("hiddencode").equalsIgnoreCase("")) {
                        value = request.getParameter("hiddencode");
                    } else {
                        value = request.getParameter("resource" + base.getId());
                    }
                    xmlFile = new File(basepath + "code.xml");
                    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(xmlFile)));
                    out.print(value);
                    out.flush();
                    response.setRenderParameter("result", this.saveOK);
                } catch (Exception e) {
                    response.setRenderParameter("result", this.saveError);
                    log.error("Error saving file: " + xmlFile.getAbsolutePath(), e);
                }
            }
            response.setMode(SWBActionResponse.Mode_ADMIN);
        }
        response.setRenderParameter("suri", suri);
    }

    /*
    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out=response.getWriter();
        String lang=paramRequest.getUser().getLanguage();

        String suri=request.getParameter("suri");
        if(suri==null)
        {
            out.println("ParÃ¡metro no difinido...");
            return;
        }
        FlowNodeInstance foi = (FlowNodeInstance)SWBPlatform.getSemanticMgr().getOntology().getGenericObject(suri);

        out.println("<form action=\""+paramRequest.getActionUrl().setAction("savecnf")+"\" method=\"post\">");
        out.println("<input type=\"hidden\" name=\"suri\" value=\""+suri+"\">");
        Iterator<SWBClass> it=foi.listHeraquicalProcessObjects().iterator();
        while(it.hasNext())
        {
            SWBClass obj=it.next();
            SemanticClass cls=obj.getSemanticObject().getSemanticClass();
            out.println("<h3>"+cls.getDisplayName(lang)+"</h3>");
            Iterator<SemanticProperty> itp=cls.listProperties();
            while(itp.hasNext())
            {
                SemanticProperty prop=itp.next();
                String name=cls.getClassId()+"|"+prop.getPropId();
                out.print("<input type=\"checkbox\" name=\""+name+"\"");
                if(hasProperty(paramRequest, cls, prop))out.print(" checked");
                out.println(">");
                out.println("<select name=\""+name+"|s\">");
                out.println("<option value=\"edit\"");
                if(isEditProperty(paramRequest, cls, prop))out.print(" selected");
                out.println(">Edit</option>");
                out.println("<option value=\"view\"");
                if(isViewProperty(paramRequest, cls, prop))out.print(" selected");
                out.println(">View</option>");
                out.println("</select>");
                out.println(prop.getDisplayName(lang));
                out.println("<BR>");
            }
        }
        out.println("<input type=\"submit\" value=\"Guardar\">");
        out.println("<input type=\"button\" value=\"Cancelar\" onclick=\"window.location='"+paramRequest.getRenderUrl().setMode(paramRequest.Mode_VIEW)+"'\">");
        out.println("</form>");
    }

    public boolean hasProperty(SWBParameters paramRequest, SemanticClass cls, SemanticProperty prop)
    {
        boolean ret=false;
        String data=paramRequest.getResourceBase().getData(paramRequest.getWebPage());
        if(data!=null && data.indexOf(cls.getClassId()+"|"+prop.getPropId())>-1)return ret=true;
        return ret;
    }
     *
     */

    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramRequest) throws SWBResourceException, IOException {

        StringBuilder ret = new StringBuilder(400);
        StringBuilder value = new StringBuilder(200);
        Resource base = getResourceBase();
        String basepath = SWBPortal.getWorkPath() + base.getWorkPath() + "/";
        File xmlFile = new File(basepath);
        PrintWriter out = response.getWriter();
        SWBResourceURL url = paramRequest.getActionUrl().setAction("saveXMLFile");

        //ExtracciÃ³n del contenido del archivo para mostrarlo en el editor
        if (xmlFile.exists()) {
            xmlFile = new File(basepath + "code.xml");
            if (xmlFile.exists()) {
                BufferedReader in = new BufferedReader(new FileReader(xmlFile));
                String lineRead = in.readLine();
                while(lineRead != null) {
                    value.append(lineRead + "\n");
                    lineRead = in.readLine();
                }
            }
        } else {
            try {
                xmlFile.mkdirs();
            } catch (Exception e) {
                log.error("Unable to create directory " + xmlFile.getAbsolutePath(), e);
            }
        }

        //Despliegue del editor
        ret.append("<script type=\"text/javascript\" src=\"");
        ret.append(SWBPlatform.getContextPath());
        ret.append("/swbadmin/js/editarea/edit_area/edit_area_full.js\"></script>\n");
        ret.append("<script type=\"text/javascript\" charset=\"UTF-8\">\n");
        ret.append("editAreaLoader.init({\n");
        ret.append("    id : \"resource");
        ret.append(base.getId());
        ret.append("\"\n    ,language: \"");
        ret.append(paramRequest.getUser().getLanguage().toLowerCase());
        ret.append("\"\n    ,syntax: \"xml\"\n");
        ret.append("    ,start_highlight: true\n");
        ret.append("    ,toolbar: \"save, |, search, go_to_line,");
        ret.append(" |, undo, redo, |, select_font,|, change_smooth_selection,");
        ret.append(" highlight, reset_highlight, |, help\"\n");
        ret.append("    ,is_multi_files: false\n");
        ret.append("    ,save_callback: \"my_save\"\n");
        ret.append("    ,allow_toggle: false\n");
        ret.append("});\n");
        ret.append("\n");
        ret.append("  function my_save(id, content){\n");
        ret.append("    var elemento = document.getElementById(\"hiddencode\");\n");
        ret.append("    elemento.value = content;\n");
        ret.append("    document.xmledition.submit();\n");
        ret.append("  }\n");
        ret.append("</script>\n");
        ret.append("<form name=\"xmledition\" action=\"");
        ret.append(url.toString());
        ret.append("\" method=\"post\">\n");
        ret.append("<textarea id=\"resource");
        ret.append(base.getId());
        ret.append("\" name=\"resource");
        ret.append(base.getId());
        ret.append("\" rows=\"25");
        ret.append("\" cols=\"95\">");
        ret.append(value);
        ret.append("</textarea>\n");
        ret.append("<input type=\"hidden\" id=\"hiddencode\" name=\"hiddencode\" value=\"\"/>\n");
        //ret.append("<input type=\"button\" id=\"btnSend\" onclick=\"\" value=\"Guardar\"\n>");
        out.println(ret);
    }

}
