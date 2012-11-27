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

import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.process.model.FlowNodeInstance;
import org.semanticwb.process.model.Instance;
import org.semanticwb.process.model.SWBProcessFormMgr;
import com.arthurdo.parser.HtmlStreamTokenizer;
import com.arthurdo.parser.HtmlTag;
import java.io.ByteArrayInputStream;
import java.util.Enumeration;
import java.util.HashMap;
import org.semanticwb.model.*;
import org.semanticwb.portal.SWBForms;
import org.semanticwb.process.model.ItemAwareReference;

/**
 *
 * @author jorge.jimenez
 */
public class SWBFormMgrLayer {

    private String xml = null;
    HttpServletRequest request = null;
    ArrayList<SWBFormLayer> aProperties = null;
    
    SWBProcessFormMgr mgr = null;
    FlowNodeInstance foi=null;
    SWBParamRequest paramRequest = null;
    private String htmlType = "dojo";
    private static Logger log = SWBUtils.getLogger(SWBFormMgrLayer.class);
    HashMap<String,ArrayList<SemanticProperty>> hmapClasses=new HashMap();

    public SWBFormMgrLayer(String xml, SWBParamRequest paramRequest, HttpServletRequest request) {
        this.xml = xml;
        this.request = request;
        this.paramRequest = paramRequest;
        init();
        createObjs();
    }

    private void init() {
        aProperties = new ArrayList();

        String suri = request.getParameter("suri");
        foi = (FlowNodeInstance) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(suri);

        Iterator<ItemAwareReference> it = foi.listHeraquicalItemAwareReference().iterator();
        while (it.hasNext())
        {
            ItemAwareReference item=it.next();
            SWBClass obj = item.getProcessObject();
            //TODO: Revisar variables distintas de la misma clase
            SemanticClass cls = obj.getSemanticObject().getSemanticClass();
            //System.out.println("CLASE DE FOI:"+cls+", PREFIJO:"+cls.getPrefix());
            ArrayList<SemanticProperty> aListProps=new ArrayList();
            Iterator<SemanticProperty> itp = cls.listProperties();
            while (itp.hasNext()) {
                SemanticProperty prop = itp.next();
                aListProps.add(prop);
                //System.out.println("PROPIEDAD:"+prop+", PREFIJO:"+cls.getPrefix());
            }
            hmapClasses.put(item.getItemAware().getName(), aListProps);
        }

        mgr = new SWBProcessFormMgr(foi);    

    }

    private void createObjs() {
            HtmlTag tag = new HtmlTag();
            try
            {
                HtmlStreamTokenizer tok = new HtmlStreamTokenizer(new ByteArrayInputStream(xml.getBytes()));
                while (tok.nextToken() != HtmlStreamTokenizer.TT_EOF)
                {
                    int ttype = tok.getTokenType();
                    if (ttype == HtmlStreamTokenizer.TT_TAG)
                    {
                        tok.parseTag(tok.getStringValue(), tag);
                        if (tok.getRawString().toLowerCase().startsWith("<!--[if"))continue;
                        String tagName=tag.getTagString();
                        if(tagName.toLowerCase().equals("form")){
                            if(tag.getParam("htmlType")!=null) htmlType=tag.getParam("htmlType");
                        }else if(tagName.toLowerCase().equals("label"))
                        {
                            SWBLabelTag property = new SWBLabelTag(request, paramRequest, hmapClasses, getTagProperties(tok), mgr, tok, htmlType);
                            aProperties.add(property);
                        }else if(tagName.toLowerCase().equals("property"))
                        {
                            SWBPropertyTag property = new SWBPropertyTag(request, paramRequest, hmapClasses, getTagProperties(tok), mgr, tok, htmlType);
                            aProperties.add(property);
                        }else if(tagName.toLowerCase().equals("button"))
                        {
                            SWBButtonTag property = new SWBButtonTag(request, paramRequest, hmapClasses, getTagProperties(tok), mgr, tok, htmlType);
                            aProperties.add(property);
                        }
                    }
                }
            }catch (Exception e)
            {
                log.error(e);
            }
    }


    private HashMap getTagProperties(HtmlStreamTokenizer tok){
        HashMap hmap=new HashMap();
        try{
            HtmlTag tag = new HtmlTag();
            tok.parseTag(tok.getStringValue(), tag);
            Enumeration en = tag.getParamNames();
            while (en.hasMoreElements()) {
                String name = (String) en.nextElement();
                String value = tag.getParam(name);
                hmap.put(name, value);
            }
        }catch(Exception e){
            log.error(e);
        }
        return hmap;
    }


     public String getHtml() { 
        StringBuilder strb = new StringBuilder();
        int index = 0;
        int y = 0;
        String xmlTmp = "";
        SWBResourceURL actionUrl = paramRequest.getActionUrl();
        actionUrl.setAction("update");
        String sDojo = htmlType;
        if (sDojo!=null && sDojo.equalsIgnoreCase("dojo")) {
            sDojo = "dojoType=\"dijit.form.Form\"";
            strb.append(SWBForms.DOJO_REQUIRED);
            strb.append("<script type=\"text/javascript\">function validateForm"+foi.getId()+"(form) {if (form.validate()) {return true;} else {alert('Algunos de los datos no son válidos. Verifique la información proporcionada.'); return false;}}</script>");
        }
        int pos = -1;
        pos = xml.indexOf("<form");
        if (pos > -1) {
            int pos1 = xml.indexOf(">", pos);
            xml = xml.substring(0, pos) + xml.substring(pos1 + 1);
        }

        String lang = paramRequest.getUser().getLanguage();


        strb.append("<form name=\"" + mgr.getFormName() + "\" method=\"post\" action=\"" + actionUrl + "\" id=\"" + mgr.getFormName() + "\" " + sDojo + " class=\"swbform\" onSubmit=\"return validateForm"+foi.getId()+"(this);\">");

        Iterator<SWBFormLayer> itaProperties = aProperties.iterator();
        while (itaProperties.hasNext()) {
            SWBFormLayer swbProperty = itaProperties.next();
            String match = swbProperty.getTag();
            //System.out.println("match:" + match);
            String replace = swbProperty.getHtml();
            //System.out.println("replace by:" + replace);
            //Me barro todas las porpiedades del xml, pero voy guardando el indice para seguir barriendo el xml apartir de la ultima propiedad encontrada
            index = xml.indexOf(match, y);
            xmlTmp += xml.substring(y, index);
            xmlTmp += replace;
            y = index + match.length();
        }
        if (mgr.getFormHiddens() != null) {
            strb.append(mgr.getFormHiddens());//Agrego los hiddens que me regresa el FormMgr para cada Clase
        }
        if (xmlTmp.trim().length() > 0) {
            xmlTmp += xml.substring(y);
            xml = xmlTmp;
        }
        strb.append(xml);
        return strb.toString();
    }


    public static SemanticObject update2DB(HttpServletRequest request, SWBActionResponse response, FlowNodeInstance foi, String xml) { //En estos momentos solo funcionando para una sola clase
        try {
            String suri=request.getParameter("suri");
            if(suri==null) return null;
            SWBProcessFormMgr mgr = new SWBProcessFormMgr(foi);
            mgr.clearProperties();

            HashMap<String,ArrayList<SemanticProperty>> hmapClasses=new HashMap();
            foi = (FlowNodeInstance) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(suri);
            Iterator<ItemAwareReference> it = foi.listHeraquicalItemAwareReference().iterator();
            while (it.hasNext()) 
            {
                ItemAwareReference item=it.next();
                SWBClass obj = item.getProcessObject();
                SemanticClass cls = obj.getSemanticObject().getSemanticClass();
                ArrayList aListProps=new ArrayList();
                Iterator<SemanticProperty> itp = cls.listProperties();
                while (itp.hasNext()) {
                    SemanticProperty prop = itp.next();
                    aListProps.add(prop);
                }
                hmapClasses.put(item.getItemAware().getName(), aListProps);
            }

            mgr=addProperties2Mgr(mgr, hmapClasses, xml); //Agrega propiedades que seran actualizadas en BD (Persistidas).

            try{
                //System.out.println("Antes de procesar en update2DB");
                mgr.processForm(request);
                //System.out.println("Despues de procesar en update2DB");
            }catch(Exception e){
                log.error(e);
            }
            
            String url = foi.getProcessWebPage().getUrl();
            ResourceType rtype=ResourceType.ClassMgr.getResourceType("ProcessTaskInbox", foi.getProcessSite());

            if (rtype != null) {
                Resource res=rtype.getResource();
                if(res!=null)
                {
                    Resourceable resable=res.getResourceable();
                    if(resable instanceof WebPage)
                    {
                        url=((WebPage)resable).getUrl();
                    }
                }
            }
            
            if (request.getParameter("accept") != null) {
                foi.close(response.getUser(), Instance.ACTION_ACCEPT);
                response.sendRedirect(url);
            } else if (request.getParameter("reject") != null) {
                foi.close(response.getUser(), Instance.ACTION_REJECT);
                response.sendRedirect(url);
            } else if(request.getParameter("swp_action") != null) {            
                foi.close(response.getUser(), request.getParameter("swp_action"));
                response.sendRedirect(url);
            }
            response.setRenderParameter("suri", suri);
        } catch (Exception e) {
            log.error(e);
        }
        return null;
    }


    private static SWBProcessFormMgr addProperties2Mgr(SWBProcessFormMgr mgr, HashMap<String,ArrayList<SemanticProperty>> hmapClasses, String xml){
        try{
            HtmlTag tag = new HtmlTag();
            HtmlStreamTokenizer tok = new HtmlStreamTokenizer(new ByteArrayInputStream(xml.getBytes()));
            while (tok.nextToken() != HtmlStreamTokenizer.TT_EOF)
            {
                int ttype = tok.getTokenType();
                if (ttype == HtmlStreamTokenizer.TT_TAG)
                {
                    tok.parseTag(tok.getStringValue(), tag);
                    if (tok.getRawString().toLowerCase().startsWith("<!--[if"))continue;
                    String tagName=tag.getTagString();
                    if(tagName.toLowerCase().equals("property"))
                    {
                        HashMap hmap=new HashMap();
                        tok.parseTag(tok.getStringValue(), tag);
                        Enumeration en = tag.getParamNames();
                        while (en.hasMoreElements()) {
                            String name = (String) en.nextElement();
                            String value = tag.getParam(name);
                            hmap.put(name, value);
                        }

                        String sTagClassComplete=null, sTagVarName=null, sTagProp=null, smode=null;
                        Iterator <String> itTagKeys=hmap.keySet().iterator();
                        while(itTagKeys.hasNext()){
                            String sTagKey=itTagKeys.next();
                            if(sTagKey.equalsIgnoreCase("name")){
                                sTagClassComplete=(String)hmap.get(sTagKey);
                                if(sTagClassComplete!=null){
                                    int pos=sTagClassComplete.indexOf(".");
                                    if(pos>-1){
                                        sTagVarName=sTagClassComplete.substring(0,pos);
                                        sTagProp=sTagClassComplete.substring(pos+1);
                                    }
                                }
                            }if(sTagKey.equalsIgnoreCase("mode"))
                            {
                                smode=(String)hmap.get(sTagKey);
                            }
                        }

                        //Saca clase y propiedad

                        Iterator <String> itClasses=hmapClasses.keySet().iterator();
                        while(itClasses.hasNext()){
                            String varName=itClasses.next();
                            if(sTagVarName.equalsIgnoreCase(varName))
                            {
                                Iterator <SemanticProperty> itClassProps=((ArrayList)hmapClasses.get(varName)).iterator();
                                while(itClassProps.hasNext()){
                                    SemanticProperty semProp=itClassProps.next();
                                    if(semProp.getName().endsWith(sTagProp)){
                                        //Manejo de modo
                                        String swbMode=mgr.MODE_EDIT;
                                        if(smode!=null && smode.length()>0) swbMode=smode;
                                        //System.out.println("AGREGA PROPIEDAD:"+semProp+", CON CLASE:"+cls);
                                        mgr.addProperty(semProp, varName, swbMode);
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }catch(Exception e){
            log.error(e);
        }
        return mgr;
    }

}
