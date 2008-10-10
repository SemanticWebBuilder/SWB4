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

import java.util.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Portlet;
import org.w3c.dom.*;
import org.semanticwb.portal.admin.admresources.db.*;
import org.semanticwb.portal.admin.admresources.lib.WBAdmResource;
import org.semanticwb.portal.admin.admresources.lib.WBContainerFE;
import org.semanticwb.portal.admin.admresources.lib.WBJsInputFE;
import org.semanticwb.portal.admin.admresources.lib.WBJsValidationsFE;

/**
 * Objeto que administra un elemento de tipo Form de html.
 * <p>
 * Object that administers an html form element
 * @author  Jorge Alberto Jim�nez
 */

public class FormFE extends WBContainerFE
{
    private static Logger log = SWBUtils.getLogger(FormFE.class);
    
    private Locale locale=null;
    private String action=null;
    private String method=null;
    private String enctype="multipart/form-data";
    private String acceptcharset=null;
    private String accept=null;
    private String id=null;
    private String redirect=null;
    private ArrayList ajsfe=new ArrayList();
    private Node tag=null;
    private Portlet base=null;
    
    
    //ArrayList formelements=new ArrayList();
    
    /** Creates a new instance of FormFE */
    public FormFE() {
    }
    
    /** Creates a new instance of FormFE with default values*/
    public FormFE(String name,String action) {
        this.name=name;
        this.action=action;
    }
    
    /** Creates a new instance of FormFE with default values*/
    public FormFE(Node tag, Portlet base,String redirect) {
        this.tag=tag;
        this.base=base;
        this.redirect=redirect;
        setAttributes();
    }
    
    //Sets 
    public void setLocale(Locale locale){
        this.locale=locale;
    }
    
    
    /** agrega el action del elemento forma */
    public void setAction(String action){
        this.action=action;
    }
    
    /** agrega el metodo del elemento forma */
    public void setMethod(String method){
        this.method=method;
    }
    
    /** agrega el metodo del elemento forma */
    public void setWidthOutEnctype(){
        this.enctype=null;
    }
    
    /** agrega el metodo del elemento forma */
    public void setAcceptCharset(String acceptcharset){
        this.acceptcharset=acceptcharset;
    }
    
    /** agrega el metodo del elemento forma */
    public void setAccept(String accept){
        this.accept=accept;
    }
    
    public void setId(String id){
        this.id=id;
    }
    
    //gets
    /** agrega el action del elemento forma */
    public Locale getLocale(){
        return this.locale;
    }    
    
    public String getAction(){
        return action;
    }
    
    /** agrega el metodo del elemento forma */
    public String getMethod(){
        return method;
    }
    
    /** agrega el metodo del elemento forma */
    public String getEnctype(){
        return enctype;
    }
    
    /** agrega el metodo del elemento forma */
    public String getAcceptCharset(){
        return acceptcharset;
    }
    
    /** agrega el metodo del elemento forma */
    public String getAccept(){
        return accept;
    }
    
    public int getSizeJsFE(){
        return ajsfe.size();
    }
    
    public String getId(){
        return id;
    }
    
    /**
    * Obtiene el html(xml) final del elemento para mostrar en la admin del recurso
    * obtains the final xml element to show in the resource admin
    */  
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
                if(name!=null) child.setAttribute("name",name);
                if(action!=null) child.setAttribute("action",action);
                else {
                    //child.setAttribute("action","#");
                    child.setAttribute("action",redirect);
                }
                if(method!=null) child.setAttribute("method",method);
                if(enctype!=null) child.setAttribute("enctype",enctype);
                if(acceptcharset!=null) child.setAttribute("accept-charset",acceptcharset);
                if(accept!=null) child.setAttribute("accept",accept);
                if(style!=null) child.setAttribute("style",style);
                if(styleclass!=null) child.setAttribute("class",styleclass);
                if(moreattr!=null) child.setAttribute("moreattr",moreattr);
                dom.appendChild(child);

                xml=SWBUtils.XML.domToXml(dom, "ISO-8859-1", true);
                if(xml!=null && !"".equals(xml.trim())) xml=xml.substring(xml.indexOf("<form"), xml.indexOf("/>", xml.indexOf("<form"))) + ">";
                else xml="";                
            }
        } 
        catch(Exception e) { log.error(e); }
        strb.append(xml);
        strb.append(show());
        strb.append("\n</form>");
        
        //para desplegar valifaciones
        if(getSizeJsFE()>0) strb.append(getValHtml());
        return strb.toString();
    }
    
    private String getValHtml() {
        StringBuffer strb=new StringBuffer();
        strb.append("\n<script  language=\"JavaScript\">");
        strb.append("\n<![CDATA[");
        strb.append("\nfunction valida_"+getName()+"(forma) {");
        strb.append("\n     "+getJsFE());
        strb.append("\n     return true;");
        strb.append("\n}");
        strb.append("\n]]>");
        strb.append("\n</script> ");
        return strb.toString();
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
    
    public String show(){
        return getFormE();
    }
    
    public void add(Object obj){
       super.add(obj);
       setDBConnFE(obj);
       addJSFormFE(obj);
    }
    
    private void setDBConnFE(Object obj){
       if(obj instanceof WBAdmResource){
           WBAdmResource objInJs=(WBAdmResource)obj;
           objInJs.setAdmDBConnMgr(dbconnmgr);
         }
    }
    
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
            
            //Parametros de conexion de inserci�n dinamicos
            TreeMap connIParams=dbconnmgr.getConnparamsInsert();
            Iterator itConnIParams=connIParams.keySet().iterator();
            while(itConnIParams.hasNext()){
                String pcname=(String)itConnIParams.next();
                String pcvalue=(String)connParams.get(pcname);
                this.add(new HiddenFE("wbpconnI_"+pcname,pcvalue));
            }
       }
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
    * Set attributes to class according with the xml tag element
    */ 
    public void setAttributes(){
        if(tag!=null){
            NamedNodeMap nnodemap=tag.getAttributes();
            if(nnodemap.getLength()>0){
                for(int i=0;i<nnodemap.getLength();i++){
                    String attrName=nnodemap.item(i).getNodeName();
                    String attrValue=nnodemap.item(i).getNodeValue();
                    if(attrValue!=null && !attrValue.equals("")){
                        //defecto
                        if(attrName.equalsIgnoreCase("id")) id=attrValue;
                        if(attrName.equalsIgnoreCase("name")) name=attrValue;
                        else if(attrName.equalsIgnoreCase("style")) style=attrValue;
                        else if(attrName.equalsIgnoreCase("class")) styleclass=attrValue;
                        else if(attrName.equalsIgnoreCase("moreattr")) moreattr=attrValue;
                        //propios
                        else if(attrName.equalsIgnoreCase("action")){
                            action=attrValue;
                            if(action.equalsIgnoreCase("true")){
                               //action=WBUtils.getInstance().getWebPath()+AFUtils.getInstance().getEnv("wb/admresource")+"/"+base.getId()+"/update";
                                action=redirect;
                            }
                        }
                        else if(attrName.equalsIgnoreCase("method")) {
                            method=attrValue;
                        }
                        else if(attrName.equalsIgnoreCase("enctype")) enctype=attrValue;
                        else if(attrName.equalsIgnoreCase("accept-charset")) acceptcharset=attrValue;
                        else if(attrName.equalsIgnoreCase("accept")) accept=attrValue;
                    }
                }
            }
        }
    }
    
}
