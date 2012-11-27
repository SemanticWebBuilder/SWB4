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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import org.semanticwb.portal.admin.admresources.lib.*;
import org.w3c.dom.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;

// TODO: Auto-generated Javadoc
/**
 * The Class FieldSet.
 * 
 * @author jorge.jimenez
 */
public class FieldSet extends WBContainerFE {

    /** The log. */
    private static Logger log = SWBUtils.getLogger(FieldSet.class);
    
    /** The legend. */
    private String legend = null;
    
    /** The tag. */
    protected Node tag = null;
    
    /** The base. */
    protected Resource base = null;
    
    /** The form. */
    FormFE form=null;
    
    /** The ajsfe. */
    private ArrayList ajsfe=new ArrayList();
    
    /** The locale. */
    private Locale locale=null;

    /**
     * Instantiates a new field set.
     * 
     * @param id the id
     */
    public FieldSet(String id) {
        this.id = id;
    }

    /**
     * Creates a new instwance with the default parameters.
     * 
     * @param tag the tag
     * @param base the base
     * @param form the form
     */
    public FieldSet(Node tag, Resource base, FormFE form) {
        this.tag = tag;
        setAttributes();
        this.base=base;
        this.form=form;
        createObjs();
    }

    /**
     * Sets the legend.
     * 
     * @param legend the new legend
     */
    public void setLegend(String legend) {
        this.legend = legend;
    }

    /**
     * Sets the legend.
     * 
     * @return the string
     */
    public String setLegend() {
        return legend;
    }


    /**
     * Obtiene el html(xml) final del elemento para mostrar en la admin del recurso
     * obtains the final xml element to show in the resource admin.
     * 
     * @return the html
     */
    @Override
    public String getHtml(){
        StringBuilder ret=new StringBuilder("");
        String xml="";
        try
        {
            Document dom=SWBUtils.XML.getNewDocument();
            if(dom!=null)
            {
                Element child=dom.createElement("fieldset");
                if(name!=null) {
                    child.setAttribute("name",name);
                }
                if(id!=null) {
                    child.setAttribute("id",id);
                }
                if(style!=null) {
                    child.setAttribute("style",style);
                }
                if(styleclass!=null) {
                    child.setAttribute("class",styleclass);
                }
                if(moreattr!=null) {
                    child.setAttribute("moreattr",moreattr);
                }
                if(legend!=null) {
                    child.setAttribute("legend",legend);
                }
                dom.appendChild(child);

                xml=SWBUtils.XML.domToXml(dom, "ISO-8859-1", true);
                if(xml!=null && !"".equals(xml.trim())) {
//                    System.out.println("\n\n.....................fieldset");
                    xml=xml.substring(xml.indexOf("<fieldset"), xml.indexOf("/>", xml.indexOf("<fieldset"))) + ">";
//                    System.out.println(xml);
                }
                else {
                    xml="";
                }
            }
        }
        catch(Exception e) { log.error(e); }
        ret.append(xml);
        ret.append(show());
        ret.append("\n</fieldset>");
        return ret.toString();
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.admin.admresources.lib.WBContainerFE#getJscripsFE()
     */
    @Override
    public Iterator getJscripsFE(){
        return ajsfe.iterator();
    }

    /**
     * Gets the size js fe.
     * 
     * @return the size js fe
     */
    public int getSizeJsFE(){
        return ajsfe.size();
    }

    //Sets
    /**
     * Sets the locale.
     * 
     * @param locale the new locale
     */
    public void setLocale(Locale locale){
        this.locale=locale;
    }

    //gets
    /**
     * agrega el action del elemento forma.
     * 
     * @return the locale
     */
    public Locale getLocale(){
        return this.locale;
    }

     /**
      * Gets the js fe.
      * 
      * @return the js fe
      */
     public String getJsFE(){
        StringBuilder strb=new StringBuilder();
        Iterator ijsfeObj=ajsfe.iterator();
        while(ijsfeObj.hasNext()){
            WBJsValidationsFE js_valfe=(WBJsValidationsFE)ijsfeObj.next();
            strb.append(js_valfe.getHtml(getLocale()));
        }
        return strb.toString();
    }

     /* (non-Javadoc)
      * @see org.semanticwb.portal.admin.admresources.lib.WBContainerFE#add(java.lang.Object)
      */
    @Override
     public void add(Object obj){
       super.add(obj);
       addJSFormFE(obj);
    }

     /**
      * Adds the js form fe.
      * 
      * @param obj the obj
      */
     private void addJSFormFE(Object obj){
         if(obj instanceof WBJsInputFE){
           WBJsInputFE objInJs=(WBJsInputFE)obj;
           Object[] js_Objs=objInJs.getJsValObj();
           for(int i=0;i<js_Objs.length;i++){
               if(js_Objs[i] instanceof WBJsValidationsFE){
                   WBJsValidationsFE js_valfe=(WBJsValidationsFE)js_Objs[i];
                   js_valfe.setFormFEName(getName());
                   ajsfe.add(js_valfe);
               }
           }
         }
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.admin.admresources.lib.WBContainerFE#setAttributes()
     */
    @Override
    public final void setAttributes() {
        if (tag != null) {
            NamedNodeMap nnodemap = tag.getAttributes();
            if (nnodemap.getLength() > 0) {
                for (int i = 0; i < nnodemap.getLength(); i++) {
                    String attrName = nnodemap.item(i).getNodeName();
                    String attrValue = nnodemap.item(i).getNodeValue();
                    if (attrValue != null && !attrValue.equals("")) {
                        //defecto
                        if (attrName.equalsIgnoreCase("id")) {
                            id = attrValue;
                        } else if (attrName.equalsIgnoreCase("style")) {
                            style = attrValue;
                        } else if (attrName.equalsIgnoreCase("class")) {
                            styleclass = attrValue;
                        } else if (attrName.equalsIgnoreCase("moreattr")) {
                            moreattr = attrValue;
                        } //propios
                        else if (attrName.equalsIgnoreCase("legend")) {
                            legend = attrValue;
                        }
                    }
                }
            }
        }
    }

    /**
     * Crea objetos html de acuerdo a tags del xml de la administraciï¿½n de los recursos
     * Creates html objects according with the tags of xml admin resources.
     */
    private void createObjs() {
        if (tag != null) {
            NodeList ndlchilds = tag.getChildNodes();
            for (int i = 0; i < ndlchilds.getLength(); i++)
            {
                Node node=ndlchilds.item(i);
                if (node.getNodeName().equalsIgnoreCase("INPUT")) {
                    String type = findType(node);
                    if (type != null) {
                        if (type.equalsIgnoreCase("TEXT")) {
                            TextFE textfe = new TextFE(node);
                            textfe.setFormFE(form);
                            this.add(textfe);
                        } else if (type.equalsIgnoreCase("PASSWORD")) {
                            PasswordFE passwordfe = new PasswordFE(node);
                            passwordfe.setFormFE(form);
                            this.add(passwordfe);
                        } else if (type.equalsIgnoreCase("FILE")) {
                            FileFE filefe = new FileFE(node);
                            filefe.setFormFE(form);
                            this.add(filefe);
                        } else if (type.equalsIgnoreCase("CHECKBOX")) {
                            CheckBoxFE checkboxfe = new CheckBoxFE(node);
                            checkboxfe.setFormFE(form);
                            this.add(checkboxfe);
                        } else if (type.equalsIgnoreCase("RADIO")) {
                            RadioFE radiofe = new RadioFE(node);
                            radiofe.setFormFE(form);
                            this.add(radiofe);
                        } else if (type.equalsIgnoreCase("SUBMIT")) {
                            SubmitFE submitfe = new SubmitFE(node, form);
                            submitfe.setFormFE(form);
                            this.add(submitfe);
                        } else if (type.equalsIgnoreCase("RESET")) {
                            ResetFE resetfe = new ResetFE(node);
                            resetfe.setFormFE(form);
                            this.add(resetfe);
                        } else if (type.equalsIgnoreCase("HIDDEN")) {
                            HiddenFE hiddenfe = new HiddenFE(node);
                            hiddenfe.setFormFE(form);
                            this.add(hiddenfe);
                        } else if (type.equalsIgnoreCase("BUTTON")) {
                            ButtonFE buttonfe = new ButtonFE(node);
                            buttonfe.setFormFE(form);
                            this.add(buttonfe);
                        }
                    }
                } else {
                    if (node.getNodeName().equalsIgnoreCase("SELECT")) {
                        SelectFE selectfe = new SelectFE(node);
                        selectfe = (SelectFE) addChildsFE(node, selectfe);
                        selectfe.setAdmDBConnMgr(form.getAdmDBConnMgr());
                        selectfe.setFormFE(form);
                        this.add(selectfe);
                    } else if (node.getNodeName().equalsIgnoreCase("TEXTAREA")) {
                        TextAreaFE textareafe = new TextAreaFE(node);
                        textareafe.setFormFE(form);
                        this.add(textareafe);
                    } else if (node.getNodeName().equalsIgnoreCase("IMG")) {
                        ImgFE imgfe = new ImgFE(node);
                        imgfe.setFormFE(form);
                        this.add(imgfe);
                    } else if (node.getNodeName().equalsIgnoreCase("MAP")) {
                        MapFE mapfe = new MapFE(node);
                        mapfe.setAdmDBConnMgr(this.getAdmDBConnMgr());
                        mapfe = (MapFE) addChildsMapFE(node, mapfe);
                        mapfe.setAdmDBConnMgr(form.getAdmDBConnMgr());
                        this.add(mapfe);
                    } else if (node.getNodeName().equalsIgnoreCase("APPLET")) {
                        AppletFE appletfe = new AppletFE(node);
                        appletfe.setAdmDBConnMgr(this.getAdmDBConnMgr());
                        appletfe = (AppletFE) addChildsAppletFE(node, appletfe);
                        appletfe.setAdmDBConnMgr(form.getAdmDBConnMgr());
                        this.add(appletfe);
                    } else if (node.getNodeName().equalsIgnoreCase("CALENDAR")) {
                        CalendarFE calendarfe = new CalendarFE(node);
                        calendarfe.setAdmDBConnMgr(form.getAdmDBConnMgr());
                        this.add(calendarfe);
                    } else if ((node.getNodeName().equalsIgnoreCase("statictext") || node.getNodeName().equalsIgnoreCase("script"))) {
                        //HtmlFE htmlfe = new HtmlFE(tag);
                        //TODO:check if the base needs to be passed to other tags, if yes it need to be declared in the WBAdmResource interface
                        HtmlFE htmlfe = new HtmlFE(node, base);
                        htmlfe.setFormFE(form);
                        this.add(htmlfe);
                    } else if (node.getNodeName().equalsIgnoreCase("fieldset")) {
                        //HtmlFE htmlfe = new HtmlFE(tag);
                        //TODO:check if the base needs to be passed to other tags, if yes it need to be declared in the WBAdmResource interface
                        FieldSet fieldset = new FieldSet(node, base, form);
                        fieldset.setAdmDBConnMgr(form.getAdmDBConnMgr());
                        this.add(fieldset);
                    }
                }
            }
        }
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
        for (int i = 0; i < nnodemap.getLength(); i++) {
            if (nnodemap.item(i).getNodeName().equalsIgnoreCase("type")) {
                type = nnodemap.item(i).getNodeValue();
            }
        }
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
                if(ndlchilds.item(i).getNodeName().startsWith("#text") || !(obj instanceof SelectFE)){
                    continue;
                }
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
                {continue;}
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
                {continue;}
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
