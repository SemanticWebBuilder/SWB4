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

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.w3c.dom.*;
import org.semanticwb.portal.admin.admresources.db.*;
import org.semanticwb.portal.admin.admresources.lib.WBAdmResource;
import org.semanticwb.portal.admin.admresources.lib.WBContainerFE;
import org.semanticwb.portal.admin.admresources.lib.WBJsInputFE;
import org.semanticwb.portal.admin.admresources.lib.WBJsValidationsFE;

// TODO: Auto-generated Javadoc
/**
 * Objeto que administra un elemento de tipo Form de html.
 * <p>
 * Object that administers an html form element
 * @author  Jorge Alberto Jim?nez
 */

public class FormFE extends WBContainerFE
{
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(FormFE.class);

    /** The locale. */
    private Locale locale=null;
    
    /** The action. */
    private String action=null;
    
    /** The method. */
    private String method=null;
    
    /** The enctype. */
    private String enctype="multipart/form-data";
    
    /** The acceptcharset. */
    private String acceptcharset=null;
    
    /** The accept. */
    private String accept=null;
    
    /** The id. */
    private String id=null;
    
    /** The redirect. */
    private String redirect=null;
    
    /** The ajsfe. */
    private ArrayList ajsfe=new ArrayList();
    
    /** The tag. */
    private Node tag=null;
    
    /** The base. */
    private Resource base=null;
    
    /** The request. */
    HttpServletRequest request=null;

    /** The jsframework. */
    private String jsframework="dojo";


    //ArrayList formelements=new ArrayList();

    /**
     * Creates a new instance of FormFE.
     */
    public FormFE() {
    }

    /**
     * Creates a new instance of FormFE with default values.
     * 
     * @param name the name
     * @param action the action
     */
    public FormFE(String name,String action) {
        this.name=name;
        this.action=action;
    }

    /**
     * Creates a new instance of FormFE with default values.
     * 
     * @param tag the tag
     * @param base the base
     * @param redirect the redirect
     */
    public FormFE(Node tag, Resource base,String redirect) {
        this.tag=tag;
        this.base=base;
        this.redirect=redirect;
        setAttributes();
    }

    /**
     * Creates a new instance of FormFE with default values.
     * 
     * @param tag the tag
     * @param base the base
     * @param redirect the redirect
     * @param request the request
     */
    public FormFE(Node tag, Resource base,String redirect, HttpServletRequest request) {
        this.tag=tag;
        this.base=base;
        this.redirect=redirect;
        setAttributes();
        this.request=request;
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


    /**
     * agrega el action del elemento forma.
     * 
     * @param action the new action
     */
    public void setAction(String action){
        this.action=action;
    }

    /**
     * agrega el metodo del elemento forma.
     * 
     * @param method the new method
     */
    public void setMethod(String method){
        this.method=method;
    }

    /**
     * agrega el metodo del elemento forma.
     */
    public void setWidthOutEnctype(){
        this.enctype=null;
    }

    /**
     * agrega el metodo del elemento forma.
     * 
     * @param acceptcharset the new accept charset
     */
    public void setAcceptCharset(String acceptcharset){
        this.acceptcharset=acceptcharset;
    }

    /**
     * agrega el metodo del elemento forma.
     * 
     * @param accept the new accept
     */
    public void setAccept(String accept){
        this.accept=accept;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.admin.admresources.lib.WBAdmResourceAbs#setId(java.lang.String)
     */
    @Override
    public void setId(String id){
        this.id=id;
    }

    /**
     * Sets the js frame work.
     *
     * @param jsframework the new js frame work
     */
    public void setJsFrameWork(String jsframework){
        this.jsframework=jsframework;
    }

    /**
     * Sets the request.
     * 
     * @param request the new request
     */
    public void setRequest(HttpServletRequest request){
        this.request=request;
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
     * Gets the action.
     * 
     * @return the action
     */
    public String getAction(){
        return action;
    }

    /**
     * agrega el metodo del elemento forma.
     * 
     * @return the method
     */
    public String getMethod(){
        return method;
    }

    /**
     * agrega el metodo del elemento forma.
     * 
     * @return the enctype
     */
    public String getEnctype(){
        return enctype;
    }

    /**
     * agrega el metodo del elemento forma.
     * 
     * @return the accept charset
     */
    public String getAcceptCharset(){
        return acceptcharset;
    }

    /**
     * agrega el metodo del elemento forma.
     * 
     * @return the accept
     */
    public String getAccept(){
        return accept;
    }

    /**
     * Gets the size js fe.
     * 
     * @return the size js fe
     */
    public int getSizeJsFE(){
        return ajsfe.size();
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.admin.admresources.lib.WBAdmResourceAbs#getId()
     */
    @Override
    public String getId(){
        return id;
    }

    /**
     * Gets the js frame work.
     *
     * @return the js frame work
     */
    protected String getJsFrameWork(){
        return jsframework;
    }

    /**
     * Obtiene el html(xml) final del elemento para mostrar en la admin del recurso
     * obtains the final xml element to show in the resource admin.
     * 
     * @return the html
     */
    @Override
    public String getHtml()
    {
        StringBuffer strb=new StringBuffer("");
        String xml="";
        try
        {
            Document dom=SWBUtils.XML.getNewDocument();
            if(dom!=null)
            {
                Element child=dom.createElement("form");
                if(id!=null) child.setAttribute("id",id);
                if(name!=null) {
                    child.setAttribute("name",name);
                    if(id==null) {
                        id=name;
                        child.setAttribute("id",id);
                    }
                }
                if(action!=null)
                    child.setAttribute("action",action);
                else {
                    child.setAttribute("action",redirect);
                }
                if(method!=null) child.setAttribute("method",method);
                if(enctype!=null) child.setAttribute("enctype",enctype);
                if(acceptcharset!=null) child.setAttribute("accept-charset",acceptcharset);
                if(accept!=null) child.setAttribute("accept",accept);
                if(style!=null) child.setAttribute("style",style);
                if(styleclass!=null) child.setAttribute("class",styleclass);
                if(moreattr!=null) child.setAttribute("moreattr",moreattr);

                setJsFrameworkAttributes(child);

                dom.appendChild(child);

                xml=SWBUtils.XML.domToXml(dom, "ISO-8859-1", true);
                if( xml!=null && !"".equals(xml.trim()) )
                    xml=xml.substring(xml.indexOf("<form"), xml.indexOf("/>", xml.indexOf("<form"))) + ">";
                else
                    xml="";
            }
        }
        catch(Exception e) { log.error(e); }
        strb.append(xml);
        strb.append(show());
        strb.append("\n</form>");
        //para desplegar validaciones
        if(getSizeJsFE()>0) {
            strb.append(getValHtml());
        }
        return strb.toString();
    }

    /**
     * Gets the val html.
     * 
     * @return the val html
     */
    private String getValHtml() {
        StringBuffer strb=new StringBuffer();
        strb.append("\n<script type=\"text/javascript\">");
        //strb.append("\n<!--");
        strb.append("\n<![CDATA[");
        strb.append("\nfunction valida_"+getName()+"(forma) {");
        strb.append("\n     "+getJsFE());
        strb.append("\n     return true;");
        strb.append("\n}");
        strb.append("\n]]>");
        //strb.append("\n-->");
        strb.append("\n</script>\n");
        return strb.toString();
    }


     /**
      * Gets the js fe.
      * 
      * @return the js fe
      */
     public String getJsFE(){
        StringBuffer strb=new StringBuffer();
        Iterator ijsfeObj=ajsfe.iterator();
        while(ijsfeObj.hasNext()){
            WBJsValidationsFE js_valfe=(WBJsValidationsFE)ijsfeObj.next();
            strb.append(js_valfe.getHtml(getLocale()));
        }
        return strb.toString();
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.admin.admresources.lib.WBContainerFE#show()
     */
    public String show(){
        return getFormE();
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.admin.admresources.lib.WBContainerFE#add(java.lang.Object)
     */
    public void add(Object obj){
       super.add(obj);
       setDBConnFE(obj);
       addJSFormFE(obj);
    }

    /**
     * Sets the dB conn fe.
     * 
     * @param obj the new dB conn fe
     */
    private void setDBConnFE(Object obj){
       if(obj instanceof WBAdmResource){
           WBAdmResource objInJs=(WBAdmResource)obj;
           objInJs.setAdmDBConnMgr(dbconnmgr);
           objInJs.setFormFE(this);
         }
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.admin.admresources.lib.WBAdmResourceAbs#setAdmDBConnMgr(org.semanticwb.portal.admin.admresources.db.AdmDBConnMgr)
     */
    public void setAdmDBConnMgr(AdmDBConnMgr dbconnmgr){
       this.dbconnmgr=dbconnmgr;
       if(!dbconnmgr.getIsDefConn()){
           this.add(new HiddenFE("conname",dbconnmgr.getConName()));
           this.add(new HiddenFE("tablename",dbconnmgr.getTableName()));
           this.add(new HiddenFE("fieldname",dbconnmgr.getFieldName()));
           this.add(new HiddenFE("wbi_resID",dbconnmgr.getResID()));
           this.add(new HiddenFE("wbi_resIDTM",dbconnmgr.getResIDTM()));

           //Parametros de conexion dinamicos
            TreeMap connParams=dbconnmgr.getConnparams();
            Iterator itConnParams=connParams.keySet().iterator();
            while(itConnParams.hasNext()){
                String pcname=(String)itConnParams.next();
                String pcvalue=(String)connParams.get(pcname);
                this.add(new HiddenFE("wbpconn_"+pcname,pcvalue));
            }

            //Parametros de conexion de inserci?n dinamicos
            TreeMap connIParams=dbconnmgr.getConnparamsInsert();
            Iterator itConnIParams=connIParams.keySet().iterator();
            while(itConnIParams.hasNext()){
                String pcname=(String)itConnIParams.next();
                String pcvalue=(String)connParams.get(pcname);
                this.add(new HiddenFE("wbpconnI_"+pcname,pcvalue));
            }
       }
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
         }else if(obj instanceof WBContainerFE){
           WBContainerFE objInJs=(WBContainerFE)obj;
           Iterator itJs=objInJs.getJscripsFE();
           while(itJs.hasNext()){
               Object objJs=(Object)itJs.next();
               if(objJs instanceof WBJsValidationsFE){
                   WBJsValidationsFE js_valfe=(WBJsValidationsFE)objJs;
                   js_valfe.setFormFEName(getName());
                   ajsfe.add(js_valfe);
               }
           }
         }
    }


    /*
    private void addJSFormFE(Object obj){
         if(obj instanceof WBJsInputFE){
           WBJsInputFE objInJs=(WBJsInputFE)obj;
           Object js_Obj=(Object)objInJs.getJsValObj();
           if(js_Obj instanceof WBJsValidationsFE){
               WBJsValidationsFE js_valfe=(WBJsValidationsFE)js_Obj;
               js_valfe.setFormFEName(getName());
               ajsfe.add(js_valfe);
           }
         }
    }
    */

    /**
     * Set attributes to class according with the xml tag element.
     */
    @Override
    public void setAttributes(){
        if(tag!=null){
            NamedNodeMap nnodemap=tag.getAttributes();
            if(nnodemap.getLength()>0){
                for(int i=0;i<nnodemap.getLength();i++){
                    String attrName=nnodemap.item(i).getNodeName();
                    String attrValue=nnodemap.item(i).getNodeValue();
                    if(attrValue!=null && !attrValue.equals("")){                        
                        //defecto
                        if(attrName.equalsIgnoreCase("name")) name=attrValue;
                        if(attrName.equalsIgnoreCase("id")) id=attrValue;
                        else if(attrName.equalsIgnoreCase("style")) style=attrValue;
                        else if(attrName.equalsIgnoreCase("class")) styleclass=attrValue;
                        else if(attrName.equalsIgnoreCase("moreattr")) moreattr=attrValue;
                        //propios
                        else if(attrName.equalsIgnoreCase("action")){
                            action=attrValue;
                            if(action.equalsIgnoreCase("true")){
                                action=redirect;
                            }
                        }
                        else if(attrName.equalsIgnoreCase("method")) {
                            method=attrValue;
                        }
                        else if(attrName.equalsIgnoreCase("enctype")) enctype=attrValue;
                        else if(attrName.equalsIgnoreCase("accept-charset")) acceptcharset=attrValue;
                        else if(attrName.equalsIgnoreCase("accept")) accept=attrValue;
                        else if(attrName.equalsIgnoreCase("jsframework")) jsframework=attrValue;
                    }
                }
            }
        }
    }


    /**
     * Manejo de Frameworks de JavaScript.
     * 
     * @param child the new js framework attributes
     */
    private void setJsFrameworkAttributes(Element child) {
        String jsFramework=getJsFrameWork();
        if(jsFramework!=null){
            if(jsFramework.equalsIgnoreCase("dojo")){
                child.setAttribute("dojoType","dijit.form.Form");
            }
//            child.setAttribute("onsubmit","submitForm('"+id+"');return false;");
        }
    }
}
