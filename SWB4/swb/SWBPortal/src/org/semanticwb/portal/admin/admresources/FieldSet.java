/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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

/**
 *
 * @author jorge.jimenez
 */
public class FieldSet extends WBContainerFE {

    private static Logger log = SWBUtils.getLogger(FieldSet.class);
    private String legend = null;
    protected Node tag = null;
    protected Resource base = null;
    FormFE form=null;
    private ArrayList ajsfe=new ArrayList();
    private Locale locale=null;

    public FieldSet(String id) {
        this.id = id;
    }

    /** Creates a new instwance with the default parameters */
    public FieldSet(Node tag, Resource base, FormFE form) {
        this.tag = tag;
        setAttributes();
        this.base=base;
        this.form=form;
        createObjs();
    }

    public void setLegend(String legend) {
        this.legend = legend;
    }

    public String setLegend() {
        return legend;
    }


    /**
    * Obtiene el html(xml) final del elemento para mostrar en la admin del recurso
    * obtains the final xml element to show in the resource admin
    */
    @Override
    public String getHtml(){
        StringBuffer ret=new StringBuffer("");
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
                    xml=xml.substring(xml.indexOf("<fieldset"), xml.indexOf("/>", xml.indexOf("<fieldset"))) + ">";
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

    public Iterator getJscripsFE(){
        return ajsfe.iterator();
    }

    public int getSizeJsFE(){
        return ajsfe.size();
    }

    //Sets
    public void setLocale(Locale locale){
        this.locale=locale;
    }

    //gets
    /** agrega el action del elemento forma */
    public Locale getLocale(){
        return this.locale;
    }

     public String getJsFE(){
        StringBuffer strb=new StringBuffer();
        Iterator ijsfeObj=ajsfe.iterator();
        while(ijsfeObj.hasNext()){
            WBJsValidationsFE js_valfe=(WBJsValidationsFE)ijsfeObj.next();
            strb.append(js_valfe.getHtml(getLocale()));
        }
        return strb.toString();
    }

     public void add(Object obj){
       super.add(obj);
       addJSFormFE(obj);
    }

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

    @Override
    public void setAttributes() {
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
     * Creates html objects according with the tags of xml admin resources
     */
    private void createObjs() {
        if (tag != null) {
            NodeList ndlchilds = tag.getChildNodes();
            for (int i = 0; i < ndlchilds.getLength(); i++)
            {
                Node tag=ndlchilds.item(i);
                if (tag.getNodeName().equalsIgnoreCase("INPUT")) {
                    String type = findType(tag);
                    if (type != null) {
                        if (type.equalsIgnoreCase("TEXT")) {
                            TextFE textfe = new TextFE(tag);
                            textfe.setFormFE(form);
                            this.add(textfe);
                        } else if (type.equalsIgnoreCase("PASSWORD")) {
                            PasswordFE passwordfe = new PasswordFE(tag);
                            passwordfe.setFormFE(form);
                            this.add(passwordfe);
                        } else if (type.equalsIgnoreCase("FILE")) {
                            FileFE filefe = new FileFE(tag);
                            filefe.setFormFE(form);
                            this.add(filefe);
                        } else if (type.equalsIgnoreCase("CHECKBOX")) {
                            CheckBoxFE checkboxfe = new CheckBoxFE(tag);
                            checkboxfe.setFormFE(form);
                            this.add(checkboxfe);
                        } else if (type.equalsIgnoreCase("RADIO")) {
                            RadioFE radiofe = new RadioFE(tag);
                            radiofe.setFormFE(form);
                            this.add(radiofe);
                        } else if (type.equalsIgnoreCase("SUBMIT")) {
                            SubmitFE submitfe = new SubmitFE(tag, form);
                            submitfe.setFormFE(form);
                            this.add(submitfe);
                        } else if (type.equalsIgnoreCase("RESET")) {
                            ResetFE resetfe = new ResetFE(tag);
                            resetfe.setFormFE(form);
                            this.add(resetfe);
                        } else if (type.equalsIgnoreCase("HIDDEN")) {
                            HiddenFE hiddenfe = new HiddenFE(tag);
                            hiddenfe.setFormFE(form);
                            this.add(hiddenfe);
                        } else if (type.equalsIgnoreCase("BUTTON")) {
                            ButtonFE buttonfe = new ButtonFE(tag);
                            buttonfe.setFormFE(form);
                            this.add(buttonfe);
                        }
                    }
                } else {
                    if (tag.getNodeName().equalsIgnoreCase("SELECT")) {
                        SelectFE selectfe = new SelectFE(tag);
                        selectfe = (SelectFE) addChildsFE(tag, selectfe);
                        selectfe.setFormFE(form);
                        this.add(selectfe);
                    } else if (tag.getNodeName().equalsIgnoreCase("TEXTAREA")) {
                        TextAreaFE textareafe = new TextAreaFE(tag);
                        textareafe.setFormFE(form);
                        this.add(textareafe);
                    } else if (tag.getNodeName().equalsIgnoreCase("IMG")) {
                        ImgFE imgfe = new ImgFE(tag);
                        imgfe.setFormFE(form);
                        this.add(imgfe);
                    } else if (tag.getNodeName().equalsIgnoreCase("MAP")) {
                        MapFE mapfe = new MapFE(tag);
                        mapfe.setAdmDBConnMgr(this.getAdmDBConnMgr());
                        mapfe = (MapFE) addChildsMapFE(tag, mapfe);
                        mapfe.setAdmDBConnMgr(form.getAdmDBConnMgr());
                        this.add(mapfe);
                    } else if (tag.getNodeName().equalsIgnoreCase("APPLET")) {
                        AppletFE appletfe = new AppletFE(tag);
                        appletfe.setAdmDBConnMgr(this.getAdmDBConnMgr());
                        appletfe = (AppletFE) addChildsAppletFE(tag, appletfe);
                        appletfe.setAdmDBConnMgr(form.getAdmDBConnMgr());
                        this.add(appletfe);
                    } else if (tag.getNodeName().equalsIgnoreCase("CALENDAR")) {
                        CalendarFE calendarfe = new CalendarFE(tag);
                        calendarfe.setAdmDBConnMgr(form.getAdmDBConnMgr());
                        this.add(calendarfe);
                    } else if ((tag.getNodeName().equalsIgnoreCase("statictext") || tag.getNodeName().equalsIgnoreCase("script"))) {
                        //HtmlFE htmlfe = new HtmlFE(tag);
                        //TODO:check if the base needs to be passed to other tags, if yes it need to be declared in the WBAdmResource interface
                        HtmlFE htmlfe = new HtmlFE(tag, base);
                        htmlfe.setFormFE(form);
                        this.add(htmlfe);
                    } else if (tag.getNodeName().equalsIgnoreCase("fieldset")) {
                        //HtmlFE htmlfe = new HtmlFE(tag);
                        //TODO:check if the base needs to be passed to other tags, if yes it need to be declared in the WBAdmResource interface
                        FieldSet fieldset = new FieldSet(tag, base, form);
                        fieldset.setAdmDBConnMgr(form.getAdmDBConnMgr());
                        this.add(fieldset);
                    }
                }
            }
        }
    }

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
