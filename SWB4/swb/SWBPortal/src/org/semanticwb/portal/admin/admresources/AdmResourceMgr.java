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
package org.semanticwb.portal.admin.admresources;

import javax.servlet.http.HttpServletRequest;
import org.semanticwb.portal.admin.admresources.ImgFE;
import org.semanticwb.portal.admin.admresources.MapFE;
import org.semanticwb.portal.admin.admresources.OptionSelectFE;
import org.semanticwb.portal.admin.admresources.ParamAppletFE;
import org.semanticwb.portal.admin.admresources.PasswordFE;
import org.semanticwb.portal.admin.admresources.RadioFE;
import org.semanticwb.portal.admin.admresources.ResetFE;
import org.semanticwb.portal.admin.admresources.SelectFE;
import org.semanticwb.portal.admin.admresources.SubmitFE;
import org.semanticwb.portal.admin.admresources.TextAreaFE;
import org.semanticwb.portal.admin.admresources.TextFE;
import org.semanticwb.portal.admin.admresources.lib.WBContainerFE;
import org.semanticwb.Logger;
import org.semanticwb.portal.admin.admresources.db.AdmDBConnMgr;
import org.semanticwb.model.Resource;
import org.semanticwb.model.User;
import org.w3c.dom.*;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;


// TODO: Auto-generated Javadoc
/**
 * Objeto que administra elementos html de una forma.
 * <p>
 * Object that administers html elements in a form
 * @author  Jorge Alberto Jim�nez
 */

public class AdmResourceMgr extends WBContainerFE 
{
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(AdmResourceMgr.class);
    
    /** The user. */
    private User user;
    
    /** The isedit. */
    private boolean isedit;
    
    /** The dom. */
    private Document dom;
    
    /** The xml. */
    private String xml;
    
    /** The isdefconn. */
    private boolean isdefconn;
    
    /** The base. */
    private Resource base;
    
    /** The str res. */
    String strRes;
    
    /** The redirect. */
    String redirect;
    
    /** The request. */
    HttpServletRequest request=null;
    
    
    /**
     * Inicializa el objeto.
     */    
    public AdmResourceMgr() {
        user=null;
        isedit = false;
        dom = null;
        xml = null;
        isdefconn = true;
        base = null;
        redirect=null;
        //webpath = SWBUtils.getInstance().getWebPath();
        strRes = SWBPlatform.getEnv("wb/admresource");
    }
    
    /**
     * Inicializa el objeto.
     * 
     * @param user Objeto wbuser del usuario que realiza la acci�n
     */    
    public AdmResourceMgr(User user) {
        this();
        this.user=user;
    }    
    
    /*
    public void setDom(Document dom)
    {
        this.dom = dom;
    }*/
    
    /**
     * Agrega el xml de la definici�n de admin al objeto.
     * 
     * @param xml Inserta el xml al objeto
     * @param base Objeto base del recurso
     * @param redirect Ruta de redireccionamiento para el submit de la forma
     */    
    public void setXml(String xml, Resource base,String redirect) {
        this.xml = xml;
        this.base = base;
        this.redirect=redirect;
        createHtmlObjs();
    }

    /**
     * Sets the request.
     * 
     * @param request the new request
     */
    public void setRequest(HttpServletRequest request){
        this.request=request;
    }
    
    
   /**
    * Crea objetos html de acuerdo a tags del xml de la administraci�n de los recursos
    * Creates html objects according with the tags of xml admin resources.
    */ 
    private void createHtmlObjs() {
        dom = SWBUtils.XML.xmlToDom(xml);
        NodeList ndllevel1 = dom.getChildNodes();
        if(ndllevel1.getLength() > 0) {
            NodeList ndllevel2 = ndllevel1.item(0).getChildNodes();
            if(ndllevel2.getLength() > 0) {
                //int cont = 0;
                for(int i = 0; i < ndllevel2.getLength(); i++) {
                    //if(ndllevel2.item(i).getNodeName().startsWith("#text") || ++cont < 2)
                    if(ndllevel2.item(i).getNodeName().startsWith("#text"))
                        continue;
                    
                    if(ndllevel2.item(i).getNodeName().equalsIgnoreCase("form")){ //para formas y elementos de la misma
                        FormFE forma = null;
                        forma = createObj(ndllevel2.item(i), forma);
                        
                        //pongo admdbconnmgr por defecto en la forma
                        if(forma != null){
                            try{
                                Document domtmp=SWBUtils.XML.getNewDocument();
                                Element edbconmgr = domtmp.createElement("admdbconnmgr");
                                AdmDBConnMgr admdbconnmgr = new AdmDBConnMgr(forma,(Node)edbconmgr, base);
                                forma.setAdmDBConnMgr(admdbconnmgr);
                            }catch(Exception e){
                                log.error("Error while creating default conection",e);
                            }
                        }
                        //
                        NodeList ndllevel3 = ndllevel2.item(i).getChildNodes();
                        if(ndllevel3.getLength() > 0) {
                            for(int j = 0; j < ndllevel3.getLength(); j++)
                                if(!ndllevel3.item(j).getNodeName().startsWith("#text"))
                                    forma = createObj(ndllevel3.item(j), forma);
                            
                        }
                        add(forma);
                    }else if(ndllevel2.item(i).getNodeName().equalsIgnoreCase("statictext") || ndllevel2.item(i).getNodeName().equalsIgnoreCase("script")){ //para statictext al mismo nivel de formas
                        HtmlFE htmlfe=new HtmlFE(ndllevel2.item(i));
                        add(htmlfe);
                    }
                }
            }
        }
    }
    
     /**
      * Crea la forma con todos sus elementos de html
      * Creates the form with the html tag elements as objects.
      * 
      * @param tag the tag
      * @param forma the forma
      * @return the form fe
      */ 
    private FormFE createObj(Node tag, FormFE forma) {
        if(tag.getNodeName().equalsIgnoreCase("FORM")) {
            forma = new FormFE(tag, base,redirect,request);
            if(user!=null) forma.setLocale(new java.util.Locale(user.getLanguage()));
            else forma.setLocale(SWBUtils.TEXT.getLocale());
        }else if(tag.getNodeName().equalsIgnoreCase("INPUT") && forma != null) {
            String type = findType(tag);
            if(type != null)
            if(type.equalsIgnoreCase("TEXT")) {
                TextFE textfe = new TextFE(tag);
                forma.add(textfe);
            }else if(type.equalsIgnoreCase("PASSWORD")) {
                PasswordFE passwordfe = new PasswordFE(tag);
                forma.add(passwordfe);
            }else if(type.equalsIgnoreCase("FILE")) {
                FileFE filefe = new FileFE(tag);
                forma.add(filefe);
            }else if(type.equalsIgnoreCase("CHECKBOX")) {
                CheckBoxFE checkboxfe = new CheckBoxFE(tag);
                forma.add(checkboxfe);
            }else if(type.equalsIgnoreCase("RADIO")) {
                RadioFE radiofe = new RadioFE(tag);
                forma.add(radiofe);
            }else if(type.equalsIgnoreCase("SUBMIT")) {
                SubmitFE submitfe = new SubmitFE(tag,forma);
                forma.add(submitfe);
            }else if(type.equalsIgnoreCase("RESET")) {
                ResetFE resetfe = new ResetFE(tag);
                forma.add(resetfe);
            }else if(type.equalsIgnoreCase("HIDDEN")) {
                HiddenFE hiddenfe = new HiddenFE(tag);
                forma.add(hiddenfe);
            }else if(type.equalsIgnoreCase("BUTTON")) {
                ButtonFE buttonfe = new ButtonFE(tag);
                forma.add(buttonfe);
            }
        }else {
            if(tag.getNodeName().equalsIgnoreCase("SELECT") && forma != null) {
                SelectFE selectfe = new SelectFE(tag);
                selectfe = (SelectFE)addChildsFE(tag, selectfe);
                forma.add(selectfe);
            }else if(tag.getNodeName().equalsIgnoreCase("TEXTAREA") && forma != null) {
                TextAreaFE textareafe = new TextAreaFE(tag);
                forma.add(textareafe);
            }else if(tag.getNodeName().equalsIgnoreCase("IMG") && forma != null) {
                ImgFE imgfe = new ImgFE(tag);
                forma.add(imgfe);
            }else if(tag.getNodeName().equalsIgnoreCase("MAP") && forma != null) {
                MapFE mapfe = new MapFE(tag);
                mapfe.setAdmDBConnMgr(forma.getAdmDBConnMgr());
                mapfe = (MapFE)addChildsMapFE(tag, mapfe);
                forma.add(mapfe);
            }else if(tag.getNodeName().equalsIgnoreCase("APPLET") && forma != null) {
                AppletFE appletfe = new AppletFE(tag);
                appletfe.setAdmDBConnMgr(forma.getAdmDBConnMgr());
                appletfe = (AppletFE)addChildsAppletFE(tag, appletfe);
                forma.add(appletfe);
            }else if(tag.getNodeName().equalsIgnoreCase("CALENDAR") && forma != null) {
                CalendarFE calendarfe = new CalendarFE(tag);
                forma.add(calendarfe);
            }else if(tag.getNodeName().equalsIgnoreCase("admdbconnmgr") && forma != null) {
                AdmDBConnMgr admdbconnmgr = new AdmDBConnMgr(forma, tag, base);
                forma.setAdmDBConnMgr(admdbconnmgr);
            }else if((tag.getNodeName().equalsIgnoreCase("statictext") || tag.getNodeName().equalsIgnoreCase("script")) && forma != null) {
                HtmlFE htmlfe = new HtmlFE(tag, base);
                forma.add(htmlfe);
            }else if(tag.getNodeName().equalsIgnoreCase("fieldset") && forma != null) {
                FieldSet fieldset = new FieldSet(tag, base, forma);
                if(user!=null)
                    fieldset.setLocale(new java.util.Locale(user.getLanguage()));
                else
                    fieldset.setLocale(SWBUtils.TEXT.getLocale());
                fieldset.setName(forma.getName());
                forma.add(fieldset);
            }else if(tag.getNodeName().equalsIgnoreCase("div")) {
                Div div = new Div(tag, base, forma);
                if(user!=null)
                    div.setLocale(new java.util.Locale(user.getLanguage()));
                else
                    div.setLocale(SWBUtils.TEXT.getLocale());
                div.setName(forma.getName());
                forma.add(div);
            }
        }
        return forma;
    }
    
    /**
     * Find type.
     * 
     * @param tag the tag
     * @return the string
     */
    private String findType(Node tag) {
        String type = null;
        NamedNodeMap nnodemap = tag.getAttributes();
        for(int i = 0; i < nnodemap.getLength(); i++)
            if(nnodemap.item(i).getNodeName().equalsIgnoreCase("type"))
                type = nnodemap.item(i).getNodeValue();
        
        return type;
    }
    
    /**
     * Adds the childs fe.
     * 
     * @param tag the tag
     * @param obj the obj
     * @return the object
     */
    private Object addChildsFE(Node tag, Object obj) {
        NodeList ndlchilds = tag.getChildNodes();
        if(ndlchilds.getLength() > 0) {
            for(int i = 0; i < ndlchilds.getLength(); i++) {
                if(ndlchilds.item(i).getNodeName().startsWith("#text") || !(obj instanceof SelectFE))
                    continue;
                SelectFE selecFe = (SelectFE)obj;
                if(ndlchilds.item(i).getNodeName().equalsIgnoreCase("Option")) {
                    OptionSelectFE optionselfe = new OptionSelectFE(ndlchilds.item(i));
                    selecFe.add(optionselfe);
                    obj = selecFe;
                }
            }
            
        }
        return obj;
    }

    /**
     * Adds the childs applet fe.
     * 
     * @param tag the tag
     * @param obj the obj
     * @return the object
     */
    private Object addChildsAppletFE(Node tag, Object obj) {
        NodeList ndlchilds = tag.getChildNodes();
        if(ndlchilds.getLength() > 0) {
            for(int i = 0; i < ndlchilds.getLength(); i++) {
                if(ndlchilds.item(i).getNodeName().startsWith("#text") || !(obj instanceof AppletFE))
                    continue;
                AppletFE appletfe = (AppletFE)obj;
                if(ndlchilds.item(i).getNodeName().equalsIgnoreCase("Param")) {
                    ParamAppletFE paramappfe = new ParamAppletFE(ndlchilds.item(i));
                    paramappfe.setAdmDBConnMgr(appletfe.getAdmDBConnMgr());
                    appletfe.add(paramappfe);
                    obj = appletfe;
                }
            }
        }
        return obj;
    }
    
    /**
     * Adds the childs map fe.
     * 
     * @param tag the tag
     * @param obj the obj
     * @return the object
     */
    private Object addChildsMapFE(Node tag, Object obj) {
        NodeList ndlchilds = tag.getChildNodes();
        if(ndlchilds.getLength() > 0) {
            for(int i = 0; i < ndlchilds.getLength(); i++) {
                if(ndlchilds.item(i).getNodeName().startsWith("#text") || !(obj instanceof MapFE))
                    continue;
                MapFE mapfe = (MapFE)obj;
                if(ndlchilds.item(i).getNodeName().equalsIgnoreCase("Area")) {
                    AreaMapFE areamapfe = new AreaMapFE(ndlchilds.item(i));
                    areamapfe.setAdmDBConnMgr(mapfe.getAdmDBConnMgr());
                    mapfe.add(areamapfe);
                    obj = mapfe;
                }
            }
        }
        return obj;
    }    
}
