/*
 * INFOTEC WebBuilder es una herramienta para el desarrollo de portales de conocimiento, colaboraci�n e integraci�n para Internet,
 * la cual, es una creaci�n original del Fondo de Informaci�n y Documentaci�n para la Industria INFOTEC, misma que se encuentra
 * debidamente registrada ante el Registro P�blico del Derecho de Autor de los Estados Unidos Mexicanos con el
 * No. 03-2002-052312015400-14, para la versi�n 1; No. 03-2003-012112473900 para la versi�n 2, y No. 03-2006-012012004000-01
 * para la versi�n 3, respectivamente.
 *
 * INFOTEC pone a su disposici�n la herramienta INFOTEC WebBuilder a trav�s de su licenciamiento abierto al p�blico (�open source�),
 * en virtud del cual, usted podr� usarlo en las mismas condiciones con que INFOTEC lo ha dise�ado y puesto a su disposici�n;
 * aprender de �l; distribuirlo a terceros; acceder a su c�digo fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los t�rminos y condiciones de la LICENCIA ABIERTA AL P�BLICO que otorga INFOTEC para la utilizaci�n
 * de INFOTEC WebBuilder 3.2.
 *
 * INFOTEC no otorga garant�a sobre INFOTEC WebBuilder, de ninguna especie y naturaleza, ni impl�cita ni expl�cita,
 * siendo usted completamente responsable de la utilizaci�n que le d� y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre INFOTEC WebBuilder, INFOTEC pone a su disposici�n la siguiente
 * direcci�n electr�nica:
 *
 *                                          http://www.webbuilder.org.mx
 */


package org.semanticwb.portal.admin.admresources;

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
import org.semanticwb.model.Portlet;
import org.semanticwb.model.User;
import org.w3c.dom.*;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;


/**
 * Objeto que administra elementos html de una forma.
 * <p>
 * Object that administers html elements in a form
 * @author  Jorge Alberto Jim�nez
 */

public class AdmResourceMgr extends WBContainerFE 
{
    private static Logger log = SWBUtils.getLogger(AdmResourceMgr.class);
    
    private User user;
    private boolean isedit;
    private Document dom;
    private String xml;
    private boolean isdefconn;
    private Portlet base;
    String strRes;
    String redirect;
    
    
    /**
     * Inicializa el objeto
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
     * Inicializa el objeto
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
     * Agrega el xml de la definici�n de admin al objeto
     * @param xml Inserta el xml al objeto
     * @param base Objeto base del recurso
     * @param redirect Ruta de redireccionamiento para el submit de la forma
     */    
    public void setXml(String xml, Portlet base,String redirect) {
        this.xml = xml;
        this.base = base;
        this.redirect=redirect;
        createHtmlObjs();
    }
    
    
   /**
    * Crea objetos html de acuerdo a tags del xml de la administraci�n de los recursos
    * Creates html objects according with the tags of xml admin resources
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
    * Creates the form with the html tag elements as objects
    */ 
    private FormFE createObj(Node tag, FormFE forma) {
        if(tag.getNodeName().equalsIgnoreCase("FORM"))
        {
            forma = new FormFE(tag, base,redirect);
            if(user!=null) forma.setLocale(new java.util.Locale(user.getLanguage()));
            else forma.setLocale(SWBUtils.TEXT.getLocale());
        }
        else
            if(tag.getNodeName().equalsIgnoreCase("INPUT") && forma != null) {
                String type = findType(tag);
                if(type != null)
                    if(type.equalsIgnoreCase("TEXT")) {
                        TextFE textfe = new TextFE(tag);
                        forma.add(textfe);
                    } else
                        if(type.equalsIgnoreCase("PASSWORD")) {
                            PasswordFE passwordfe = new PasswordFE(tag);
                            forma.add(passwordfe);
                        } else
                            if(type.equalsIgnoreCase("FILE")) {
                                FileFE filefe = new FileFE(tag);
                                forma.add(filefe);
                            } else
                                if(type.equalsIgnoreCase("CHECKBOX")) {
                                    CheckBoxFE checkboxfe = new CheckBoxFE(tag);
                                    forma.add(checkboxfe);
                                } else
                                    if(type.equalsIgnoreCase("RADIO")) {
                                        RadioFE radiofe = new RadioFE(tag);
                                        forma.add(radiofe);
                                    } else
                                        if(type.equalsIgnoreCase("SUBMIT")) {
                                            SubmitFE submitfe = new SubmitFE(tag,forma);
                                            forma.add(submitfe);
                                        } else
                                            if(type.equalsIgnoreCase("RESET")) {
                                                ResetFE resetfe = new ResetFE(tag);
                                                forma.add(resetfe);
                                            } else
                                                if(type.equalsIgnoreCase("HIDDEN")) {
                                                    HiddenFE hiddenfe = new HiddenFE(tag);
                                                    forma.add(hiddenfe);
                                                } else
                                                    if(type.equalsIgnoreCase("BUTTON")) {
                                                        ButtonFE buttonfe = new ButtonFE(tag);
                                                        forma.add(buttonfe);
                                                    }
            } else {
                if(tag.getNodeName().equalsIgnoreCase("SELECT") && forma != null) {
                    SelectFE selectfe = new SelectFE(tag);
                    selectfe = (SelectFE)addChildsFE(tag, selectfe);
                    forma.add(selectfe);
                } else
                    if(tag.getNodeName().equalsIgnoreCase("TEXTAREA") && forma != null) {
                        TextAreaFE textareafe = new TextAreaFE(tag);
                        forma.add(textareafe);
                    } else
                        if(tag.getNodeName().equalsIgnoreCase("IMG") && forma != null) {
                            ImgFE imgfe = new ImgFE(tag);
                            forma.add(imgfe);
                        } else
                            if(tag.getNodeName().equalsIgnoreCase("MAP") && forma != null) {
                                MapFE mapfe = new MapFE(tag);
                                mapfe.setAdmDBConnMgr(forma.getAdmDBConnMgr());
                                mapfe = (MapFE)addChildsMapFE(tag, mapfe);
                                forma.add(mapfe);
                            } else                        
                                if(tag.getNodeName().equalsIgnoreCase("APPLET") && forma != null) {
                                    AppletFE appletfe = new AppletFE(tag);
                                    appletfe.setAdmDBConnMgr(forma.getAdmDBConnMgr());
                                    appletfe = (AppletFE)addChildsAppletFE(tag, appletfe);
                                    forma.add(appletfe);
                                } else
                                    if(tag.getNodeName().equalsIgnoreCase("CALENDAR") && forma != null) {
                                        CalendarFE calendarfe = new CalendarFE(tag);
                                        forma.add(calendarfe);
                                    } else
                                        if(tag.getNodeName().equalsIgnoreCase("admdbconnmgr") && forma != null) {
                                            AdmDBConnMgr admdbconnmgr = new AdmDBConnMgr(forma, tag, base);
                                            forma.setAdmDBConnMgr(admdbconnmgr);
                                        }else
                                            if((tag.getNodeName().equalsIgnoreCase("statictext") || tag.getNodeName().equalsIgnoreCase("script")) && forma != null) {
                                                //HtmlFE htmlfe = new HtmlFE(tag);
                                                //TODO:check if the base needs to be passed to other tags, if yes it need to be declared in the WBAdmResource interface
                                                HtmlFE htmlfe = new HtmlFE(tag, base);
                                                forma.add(htmlfe);
                                            }else if(tag.getNodeName().equalsIgnoreCase("fieldset") && forma != null) {
                                                //HtmlFE htmlfe = new HtmlFE(tag);
                                                //TODO:check if the base needs to be passed to other tags, if yes it need to be declared in the WBAdmResource interface
                                                FieldSet fieldset = new FieldSet(tag);
                                                forma.add(fieldset);
                                            }
            }
        return forma;
    }
    
    private String findType(Node tag) {
        String type = null;
        NamedNodeMap nnodemap = tag.getAttributes();
        for(int i = 0; i < nnodemap.getLength(); i++)
            if(nnodemap.item(i).getNodeName().equalsIgnoreCase("type"))
                type = nnodemap.item(i).getNodeValue();
        
        return type;
    }
    
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