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


import org.semanticwb.portal.admin.admresources.lib.*;
import java.util.*;
import org.semanticwb.portal.admin.admresources.db.*;
import org.w3c.dom.*;
import java.sql.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

// TODO: Auto-generated Javadoc
/**
 * Objeto que administra un elemento html de tipo Select.
 * <p>
 * Object that administers an element Select in a html form
 * @author  Jorge Alberto Jim�nez
 */

public class SelectFE extends WBContainerFE
{
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(SelectFE.class);
    
    /** The accesskey. */
    private String accesskey=null;
    
    /** The align. */
    private String align=null;
    
    /** The isdisabled. */
    private boolean isdisabled=false;
    
    /** The ismultiple. */
    private boolean ismultiple=false;
    
    /** The size. */
    private int size=-1;
    
    /** The width. */
    private int width=-1;
    
    /** The isselected. */
    private boolean isselected=false;
    
    /** The xmltag. */
    private String xmltag=null;
    
    /** The tag. */
    protected Node tag=null;
    
    /** The query. */
    private String query=null;
    
    /** The dbconnload. */
    private String dbconnload=null;
    
    /** The keyload. */
    private String keyload=null;
    
    /** The titleload. */
    private String titleload=null;
    
    /** The fromload. */
    private String fromload=null;
    
    /** The whereload. */
    private String whereload=null;
    
    /** The onchange. */
    private String onchange=null;

    /** The invalid message. */
    protected String invalidMessage=null;
   
    /**
     * Creates a new instance of SelectFE.
     * 
     * @param name the name
     */
    public SelectFE(String name) {
        this.name=name;
    }
    
    /**
     * Creates a new instwance with the default parameters.
     * 
     * @param tag the tag
     */
    public SelectFE(Node tag){
        this.tag=tag;
        setAttributes();
    }
    
    //sets
    /**
     * Sets the access key.
     * 
     * @param accesskey the new access key
     */
    public void setAccessKey(String accesskey){
        this.accesskey=accesskey;
    }
   
    /**
     * Sets the align.
     * 
     * @param align the new align
     */
    public void setAlign(String align){
        this.align=align;
    }
    
    /**
     * Sets the disabled.
     * 
     * @param isdisabled the new disabled
     */
    public void setDisabled(boolean isdisabled){
        this.isdisabled=isdisabled;
    }
    
    /**
     * Sets the multiple.
     * 
     * @param ismultiple the new multiple
     */
    public void setMultiple(boolean ismultiple){
        this.ismultiple=ismultiple;
    }
    
    /**
     * Sets the size.
     * 
     * @param size the new size
     */
    public void setSize(int size){
        this.size=size;
    }
    
    /**
     * Sets the width.
     * 
     * @param width the new width
     */
    public void setWidth(int width){
        this.width=width;
    }
    
    /**
     * determines de xml tag name the form element will be added in a resource.
     * 
     * @param xmltag the new xml tag
     */
    public void setXmlTag(String xmltag){
        this.xmltag=xmltag;
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.portal.admin.admresources.lib.WBAdmResourceAbs#setAdmDBConnMgr(org.semanticwb.portal.admin.admresources.db.AdmDBConnMgr)
     */
    public void setAdmDBConnMgr(AdmDBConnMgr dbconnmgr){
       this.dbconnmgr=dbconnmgr;
       this.dbconnload=dbconnmgr.getConName();
       if(this.dbconnmgr!=null) setAdmDBConnMgr2Childs(dbconnmgr);
   }
    
    /**
     * Sets the adm db conn mgr2 childs.
     * 
     * @param dbconnmgr the new adm db conn mgr2 childs
     */
    private void setAdmDBConnMgr2Childs(AdmDBConnMgr dbconnmgr){
        Iterator iobj=formelements.iterator();
        while(iobj.hasNext()){
            Object obj=iobj.next();
            if(obj instanceof WBAdmResource){
               WBAdmResource wbadmres=(WBAdmResource)obj;
               wbadmres.setAdmDBConnMgr(dbconnmgr);
            }
        }
    }
    
    /**
     * Sets the dbconnload.
     * 
     * @param dbconnload the new dbconnload
     */
    public void setDbconnload(String dbconnload){
        this.dbconnload=dbconnload;
    }
  
    /**
     * Sets the keyload.
     * 
     * @param keyload the new keyload
     */
    public void setKeyload(String keyload){
        this.keyload=keyload;
    }
    
    /**
     * Sets the titleload.
     * 
     * @param titleload the new titleload
     */
    public void setTitleload(String titleload){
        this.titleload=titleload;
    }
    
    /**
     * Sets the fromload.
     * 
     * @param fromload the new fromload
     */
    public void setFromload(String fromload){
        this.fromload=fromload;
    }
    
    /**
     * Sets the whereload.
     * 
     * @param whereload the new whereload
     */
    public void setWhereload(String whereload){
        this.whereload=whereload;
    }
    
    
    //gets
    
    /**
     * Gets the access key.
     * 
     * @return the access key
     */
    public String getAccessKey(){
        return accesskey;
    }
    
    /**
     * Gets the align.
     * 
     * @return the align
     */
    public String getAlign(){
        return align;
    }
    
    /**
     * Gets the disabled.
     * 
     * @return the disabled
     */
    public boolean getDisabled(){
        return isdisabled;
    }
    
    /**
     * Gets the multiple.
     * 
     * @return the multiple
     */
    public boolean getMultiple(){
        return ismultiple;
    }
    
    /**
     * Gets the size.
     * 
     * @return the size
     */
    public int getSize(){
        return size;
    }
    
    /**
     * Gets the width.
     * 
     * @return the width
     */
    public int getWidth(){
        return width;
    }
    
     /**
      * determines de xml tag name the form element will be added in a resource.
      * 
      * @return the xml tag
      */
    public String getXmlTag(){
        return xmltag;
    }
    
    /**
     * Gets the dbconnload.
     * 
     * @return the dbconnload
     */
    public String getDbconnload(){
        return dbconnload;
    }
  
    /**
     * Gets the keyload.
     * 
     * @return the keyload
     */
    public String getKeyload(){
        return keyload;
    }
    
    /**
     * Gets the titleload.
     * 
     * @return the titleload
     */
    public String getTitleload(){
        return titleload;
    }
    
    /**
     * Gets the fromload.
     * 
     * @return the fromload
     */
    public String getFromload(){
        return fromload;
    }
    
    /**
     * Gets the whereload.
     * 
     * @return the whereload
     */
    public String getWhereload(){
        return whereload;
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.portal.admin.admresources.lib.WBContainerFE#add(java.lang.Object)
     */
    public void add(Object obj){
       super.add(obj);
       setSelectFEName(obj);
    }
    
    /**
     * Sets the select fe name.
     * 
     * @param obj the new select fe name
     */
    private void setSelectFEName(Object obj){
       if(obj instanceof OptionSelectFE){
           OptionSelectFE objselfe=(OptionSelectFE)obj;
           objselfe.setSelectFEObj(this);
         }
    }
    
   
    /**
     * Obtiene el html(xml) final del elemento para mostrar en la admin del recurso
     * obtains the final xml element to show in the resource admin.
     * 
     * @return the html
     */
    public String getHtml(){
        StringBuffer ret=new StringBuffer("");
        String xml="";
        try 
        {
            Document dom=SWBUtils.XML.getNewDocument(); 
            if(dom!=null)
            {
                Element child=dom.createElement("select");
                if(id!=null) child.setAttribute("id",id);
                if(name!=null) {
                    child.setAttribute("name",name);
                    if(id==null) child.setAttribute("id",name);
                }
                if(ismultiple) child.setAttribute("multiple","true");
                if(size!=-1) child.setAttribute("size",String.valueOf(size));
                if(width!=-1) child.setAttribute("width",String.valueOf(width));
                if(align!=null) child.setAttribute("align",align);
                if(isdisabled) child.setAttribute("disabled","true");
                if(onchange!=null) child.setAttribute("onchange",onchange); 
                if(accesskey!=null) child.setAttribute("accesskey",accesskey);
                if(style!=null) child.setAttribute("style",style);
                if(styleclass!=null) child.setAttribute("class",styleclass);
                if(moreattr!=null) child.setAttribute("moreattr",moreattr);

                setJsFrameworkAttributes(child);

                dom.appendChild(child);
                
                xml=SWBUtils.XML.domToXml(dom, "ISO-8859-1", true);
                if(xml!=null && !"".equals(xml.trim())) xml=xml.substring(xml.indexOf("<select"), xml.indexOf("/>", xml.indexOf("<select"))) + ">";
                else xml="";     
            }
        } 
        catch(Exception e) { log.error(e); }
        if(label!=null) ret.append("<label>"+label);
        ret.append(xml);
        if((dbconnload!=null || dbconnmgr!=null) && keyload!=null && titleload!=null && fromload!=null){ //agrega elementos de query dado
            getOptions();
        }
        ret.append(show());
        ret.append("\n</select>");
        if(label!=null) ret.append("</label>");
        return ret.toString();        
    }
    
    
    /**
     * Set attributes to class according with the xml tag element.
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
                        if(attrName.equalsIgnoreCase("name")) name=attrValue;
                        else if(attrName.equalsIgnoreCase("id")) id=attrValue;
                        else if(attrName.equalsIgnoreCase("style")) style=attrValue;
                        else if(attrName.equalsIgnoreCase("class")) styleclass=attrValue;
                        else if(attrName.equalsIgnoreCase("moreattr")) moreattr=attrValue;
                        //propios
                        else if(attrName.equalsIgnoreCase("accesskey")) accesskey=attrValue;
                        else if(attrName.equalsIgnoreCase("size")) size=Integer.parseInt(attrValue);
                        else if(attrName.equalsIgnoreCase("align")) align=attrValue;
                        else if(attrName.equalsIgnoreCase("width")) width=Integer.parseInt(attrValue);
                        else if(attrName.equalsIgnoreCase("disabled")) isdisabled=Boolean.valueOf(attrValue).booleanValue();
                        else if(attrName.equalsIgnoreCase("multiple")) ismultiple=Boolean.valueOf(attrValue).booleanValue();
                        else if(attrName.equalsIgnoreCase("label")) label=attrValue;
                        else if(attrName.equalsIgnoreCase("onchange")) onchange=attrValue;
                        else if(attrName.equalsIgnoreCase("dbconnload")) dbconnload=attrValue;
                        else if(attrName.equalsIgnoreCase("keyload")) keyload=attrValue;
                        else if(attrName.equalsIgnoreCase("titleload")) titleload=attrValue;
                        else if(attrName.equalsIgnoreCase("fromload")) fromload=attrValue;
                        else if(attrName.equalsIgnoreCase("whereload")) whereload=attrValue;
                        else if(attrName.equalsIgnoreCase("invalidMessage")) invalidMessage=attrValue;
                    }
                }
            }
        }
    }
    
    
       /**
        * Gets the options.
        * 
        * @return the options
        */
       private void getOptions(){
        Connection con=null;
        Statement st=null;
        ResultSet rs=null;
        String loadxml=null;
        try
        {
            if(dbconnload==null && dbconnmgr!=null) dbconnload=dbconnmgr.getConName();
            if(dbconnload!=null){
                con=SWBUtils.DB.getConnection(dbconnload);
                String query="select " +keyload+","+titleload+" from " + fromload;
                if(whereload!=null) query=query+" where "+whereload+";";
                //System.out.println("query:"+query);
                st=con.createStatement();
                rs=st.executeQuery(query);
                while(rs.next())
                {
                    OptionSelectFE optionselfe = new OptionSelectFE();
                    optionselfe.setAdmDBConnMgr(dbconnmgr);
                    Iterator valores=null;
                    //saca keyload y lo concatena en caso de ser mas de 1
                    if(keyload.indexOf(",")>-1){
                        int cont=0;
                        String cadena="";
                        valores=cadenaValues(keyload);
                        while(valores.hasNext()){
                            if(cont>0) cadena=cadena+"|";
                            String keyloadvalue=rs.getString((String)valores.next());
                            cadena=cadena+keyloadvalue;
                            cont++;
                        }
                        optionselfe.setValue(cadena);
                    }
                    else {
                        optionselfe.setValue(rs.getString(keyload));
                    }
                    //saca titleload y lo concatena en caso de ser mas de 1
                    if(titleload.indexOf(",")>-1){
                        int cont=0;
                        String cadena="";
                        valores=cadenaValues(titleload);
                        while(valores.hasNext()){
                            if(cont>0) cadena=cadena+"|";
                            String titlevalue=rs.getString((String)valores.next());
                            cadena=cadena+titlevalue;
                            cont++;
                        }
                        optionselfe.setLabel(cadena);
                    }
                    else {
                        optionselfe.setLabel(rs.getString(titleload));
                    }
                    this.add(optionselfe);
                }
            }
             if(rs!=null) rs.close();
             if(st!=null) st.close();
             if(con!=null) con.close();
        }catch(Exception e)
        {
            log.error("Error while loading options select-SelectFE:getOptions",e);
        }
    }
    
       
    /**
     * Cadena values.
     * 
     * @param cadena the cadena
     * @return the iterator
     */
    private Iterator cadenaValues(String cadena){
        ArrayList acoincide=new ArrayList();
        StringTokenizer st = new StringTokenizer(cadena,",");
        while (st.hasMoreElements()) {
            String token = st.nextToken();
            if (token == null) {
                continue;
            }
            acoincide.add(token);
       }
        return acoincide.iterator();
    }
    
    /**
     * Manejo de Frameworks de JavaScript.
     * 
     * @param child the new js framework attributes
     */
    private void setJsFrameworkAttributes(Element child){
            String jsFramework=getFormFE().getJsFrameWork();
            if(jsFramework!=null){
                if(jsFramework.equalsIgnoreCase("dojo")){
                    child.setAttribute("dojoType","dijit.form.FilteringSelect");
                    if(invalidMessage!=null){
                        child.setAttribute("invalidMessage",invalidMessage);
                    }                   
                }
            }
    }

    
}
