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


package org.semanticwb.portal.admin.admresources.db;

import org.w3c.dom.*;
import java.util.*;
import javax.servlet.http.*;
import org.semanticwb.portal.admin.admresources.*;
import java.sql.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.*;
import org.semanticwb.portal.admin.admresources.util.WBAdmResourceUtils;
import org.semanticwb.portal.util.WBFileUpload;

/**
 * Objeto que administra conecci�n o connecciones de bd de la api de administraci�n de recursos
 * <p>
 * Object that administers bd conections in the resources administration api
 * @author  Jorge Alberto Jim�nez
 */

public class AdmDBConnMgr 
{
    private static Logger log = SWBUtils.getLogger(AdmDBConnMgr.class);
    
    private boolean defconn=true;
    private Portlet base=null;
    private Document dom=null;
    private HttpServletRequest req=null;
    private User user=null;
    private StringBuffer strbxmlp=null;
    private String imgapplet="";
    private Node tag=null;
    private FormFE formfe=null;
    private String conname=null;
    private String tablename=null;
    private String fieldname=null;
    private String resID=null;
    private String resIDTM=null;
    private TreeMap connparams=null;
    private TreeMap connparamsInsert=null;
    private boolean existRecord=false;
    WBAdmResourceUtils admResUtils=new WBAdmResourceUtils();
    
    /** Creates a new instance of AdmDBConnMgr */
    public AdmDBConnMgr() {
    }
    
    /** Creates a new instance of AdmDBConnMgr when is the dafult db*/
    public AdmDBConnMgr(Portlet base) {
        this.base=base;
        this.defconn=true;
        this.conname=SWBPlatform.getEnv("wb/db/nameconn","wb"); //conexion por defecto
        init();
    }
    
    /** Creates a new instance of AdmDBConnMgr when is the dafult db*/
    public AdmDBConnMgr(String conname,String tablename,String fieldname,String resID,String resIDTM,Portlet base) {
        this.defconn=false;
        this.conname=conname;
        this.tablename=tablename;
        this.fieldname=fieldname;
        this.resID=resID;
        this.resIDTM=resIDTM;
        this.base=base;
        init();
    }
    
    
    /** Creates a new instance of AdmDBConnMgr */
    public AdmDBConnMgr(HttpServletRequest req,Portlet base) {
        this.req=req;
        this.base=base;
        createXmlAdmRes(req);
    }
    
    /** Creates a new instance of AdmDBConnMgr */
    public AdmDBConnMgr(HttpServletRequest req,String conname,String tablename,String fieldname,String resID,String resIDTM,Portlet base) {
        this.defconn=false;
        this.req=req;
        this.conname=conname;
        this.tablename=tablename;
        this.fieldname=fieldname;
        this.resID=resID;
        this.resIDTM=resIDTM;
        this.base=base;
        createXmlAdmRes(req);
    }
    
    
    /** Creates a new instance of AdmDBConnMgr when is the dafult db*/
    public AdmDBConnMgr(FormFE formfe,Node tag,Portlet base) {
        this.tag=tag;
        this.base=base;
        this.formfe=formfe;
        this.conname=SWBPlatform.getEnv("wb/db/nameconn","wb"); //conexion por defecto
        setAttrConn();
    }
    
    public void setRequest(HttpServletRequest req){
        this.req=req;
        createXmlAdmRes(req);
    }
    
    public void init(){
        if(defconn){
            dom=SWBUtils.XML.xmlToDom(base.getXml());
        }
        else{ //saca datos del campo,tabla y db especificados(no de defecto),
            try{
                if(loadXmlRes()!=null && !loadXmlRes().equals("")){
                    dom=SWBUtils.XML.xmlToDom(loadXmlRes());
                }
            }catch(Exception e){log.error(e);}
        }
    }
    
    //regresa el valor de un atributo
    public String getAttribute(String attribute){
        //return base.getAttribute(attribute);
        if(dom!=null){
            NodeList ndl=dom.getElementsByTagName(attribute);
            if(ndl.getLength()>0){
                for(int i=0;i<ndl.getLength();i++){
                    if(ndl.item(i)!=null && ndl.item(i).getChildNodes()!=null && ndl.item(i).getChildNodes().item(0)!=null && ndl.item(i).getChildNodes().item(0).getNodeValue()!=null) {
                        return ndl.item(i).getChildNodes().item(0).getNodeValue();
                    }
                }
            }
        }
        return null;
    }
    
    //Regresa una lista de valores de un atributo dado
    public ArrayList getAttributeValues(String attribute){
        ArrayList attrvalues=new ArrayList();
        if(dom!=null){
            NodeList ndl=dom.getElementsByTagName(attribute);
            for(int i=0;i<ndl.getLength();i++){
                if(ndl.item(i).getChildNodes().item(0).getNodeValue()!=null) {
                    attrvalues.add(ndl.item(i).getChildNodes().item(0).getNodeValue());
                }
            }
        }
        return attrvalues;
    }
    
    /** Crea el xml de la administraci�n de un recurso,
     * esto para cuando se edite el recurso ya tenga valores por defecto.
     */
    private void createXmlAdmRes(HttpServletRequest req) {
        this.req=req;
        strbxmlp=new StringBuffer();
        try {
            Document dom=SWBUtils.XML.getNewDocument();
            if(dom!=null) {
                Element root = dom.createElement("resource");
                dom.appendChild(root);
                Element child=null;
                if(req.getHeader("content-type")!=null && (req.getHeader("content-type").indexOf("multipart/form-data"))>-1) { // utilizar fileupload
                    HashMap hparams=new HashMap();
                    HashMap afiles=new HashMap();
                    connparams=new TreeMap();
                    connparamsInsert=new TreeMap();
                    WBFileUpload fUpload=new WBFileUpload();
                    fUpload.getFiles(req);
                    Iterator params=fUpload.getParamNames().iterator();
                    while(params.hasNext()) {
                        String paramN=(String)params.next();
                        if(fUpload.getFileName(paramN)==null) { //Para parametros que no son de tipo file
                            Iterator paramval=fUpload.getValue(paramN).iterator();
                            while(paramval.hasNext()) {
                                String value=(String)paramval.next();
                                if(!paramN.equalsIgnoreCase("conname") && !paramN.equalsIgnoreCase("tablename") 
                                    && !paramN.equalsIgnoreCase("fieldname") && !paramN.equalsIgnoreCase("wbi_resID") && !paramN.equalsIgnoreCase("wbi_resIDTM")
                                    && !paramN.startsWith("wbpconn_") && !paramN.startsWith("wbpconnI_"))
                                { //agregar a hashmap de parametros
                                    hparams.put(paramN, value);
                                }
                                else if(paramN.equalsIgnoreCase("conname")) {
                                    if(value.trim().equalsIgnoreCase("true")) {
                                        defconn=true;
                                        this.conname=SWBPlatform.getEnv("wb/db/nameconn","wb"); //conexion por defecto
                                        init();
                                    }
                                    else {  //Es otra conexi�n
                                        defconn=false;
                                        this.conname=value.trim();
                                    }
                                }else if(paramN.equalsIgnoreCase("tablename")) {
                                    this.tablename=value.trim();
                                }else if(paramN.equalsIgnoreCase("fieldname")) {
                                    this.fieldname=value.trim();
                                }else if(paramN.equalsIgnoreCase("wbi_resID")) {
                                    this.resID=value.trim();
                                }else if(paramN.equalsIgnoreCase("wbi_resIDTM")) {
                                    this.resIDTM=value.trim();
                                }else if(paramN.startsWith("wbpconn_")){
                                    connparams.put(paramN.substring(8), value.trim());
                                }else if(paramN.startsWith("wbpconnI_")){
                                    connparamsInsert.put(paramN.substring(9), value.trim());
                                }
                            }
                        }
                        else { //Para parametros tipo file tiene que parsear y subir imagenes..
                            //System.out.println("entra a subir archivo con uploadFileParsed");
                            String filename=fUpload.getFileName(paramN);
                            int i = filename.lastIndexOf("\\");
                            if(i != -1) {
                                filename = filename.substring(i + 1);
                            }
                            i = filename.lastIndexOf("/");
                            if(i != -1) {
                                filename = filename.substring(i + 1);
                            }
                            
                            child = dom.createElement(paramN);
                            child.appendChild(dom.createTextNode(filename.trim()));
                            root.appendChild(child);
                            
                            afiles.put(filename.trim(),"1");
                            String tmp=admResUtils.uploadFileParsed(base,fUpload,paramN,req.getSession().getId());
                            if(tmp!=null) imgapplet+=tmp;
                        }
                    }
                    Iterator itparams=hparams.keySet().iterator();
                    while(itparams.hasNext()) {
                        String pname=(String)itparams.next();
                        String value=(String)hparams.get(pname);
                        if(value.trim().equals("")) {
                            if(((String)hparams.get("wbfile_"+pname))!=null && hparams.get("wbNoFile_"+pname)==null) {
                                child = dom.createElement(pname);
                                child.appendChild(dom.createTextNode(((String)hparams.get("wbfile_"+pname)).trim()));
                                root.appendChild(child);
                            }
                            else {
                                child = dom.createElement(pname);
                                child.appendChild(dom.createTextNode(value.trim()));
                                root.appendChild(child);
                            }
                            if(hparams.get("wbNoFile_"+pname)!=null && hparams.get("wbfile_"+pname)!=null) {
                                admResUtils.removeFileFromFS(SWBPlatform.getWorkPath()+base.getWorkPath()+"/"+hparams.get("wbfile_"+pname));
                            }
                        }
                        else if(!pname.startsWith("wbfile_") && !pname.startsWith("wbNoFile_") && !pname.startsWith("wbReplacefile_")) {
                            child = dom.createElement(pname);
                            child.appendChild(dom.createTextNode(value.trim()));
                            root.appendChild(child);
                        }
                        else if(pname.startsWith("wbReplacefile_")) { //remover de filesystem archivo anterior
                            String hiddenFile=(String)hparams.get(pname);
                            if(afiles.get(hiddenFile)==null) { //si no existe en el hash de archivos , eliminalo de filesystem
                                admResUtils.removeFileFromFS(SWBPlatform.getWorkPath()+base.getWorkPath()+"/"+hiddenFile);
                            }
                        }
                    }
                }
                else { //utilizar request normal
                    Enumeration en=req.getParameterNames();
                    boolean flag=true;
                    while(en.hasMoreElements()) {
                        String paramN=en.nextElement().toString();
                        String[] paramval=req.getParameterValues(paramN);
                        for(int i=0;i<paramval.length;i++) {
                            child = dom.createElement(paramN);
                            child.appendChild(dom.createTextNode(paramval[i].trim()));
                            root.appendChild(child);
                        }
                    }
                }
                //strbxmlp.append(com.infotec.appfw.util.AFUtils.getInstance().DomtoXml(dom));
                strbxmlp.append(SWBUtils.XML.domToXml(dom, "ISO-8859-1", true));
            }
        }
        catch(Exception e) { log.error(e);}
    }
    
    public String getXmlParams(){
        String xmlparams=null;
        if(strbxmlp!=null) xmlparams=strbxmlp.toString();
        return xmlparams;
    }
    
    /**
     * Envia a actualizar a BD
     * send to update a DB
     */    
    public String update2DB(User user){
        this.user=user;
        try{
            if(getXmlParams()!=null && defconn==true){
                base.setXml(getXmlParams());
                //base.u(user.getId(), "resource width id:"+ base.getId()+",was updated succefully");
            }
            else { //No es base de datos defecto de recurso
                if(getXmlParams()!=null && defconn==false){
                    loadXmlRes();
                    if(existRecord){
                        if(update2NoDefDB()){
                            log.error("data was saved succefully");
                        }
                    }else{ //crea el registro en la bd que no es por defecto.
                        createRecord2NoDefDB();
                    }
                }
            }
            //System.out.println("grabando -update res...");
        }catch(SWBException e){log.error(e);}
     /*
     if(imgapplet!=null) {
          System.out.println("imgapplet:"+imgapplet);
         return strbxmlp.toString() +"\n"+ imgapplet;
     }
     else return strbxmlp.toString() +"\n";
      */
        return imgapplet;
    }
    
    public Portlet getBase(){
        return base;
    }
    
    /**
     * Coloca atributos de conecci�n a propiedades de la clase
     * insert connection attributes to class properties
     */    
    public void setAttrConn(){
        if(tag!=null){
            NamedNodeMap nnodemap=tag.getAttributes();
            if(nnodemap.getLength()>0){
                for(int i=0;i<nnodemap.getLength();i++){
                    String attrName=nnodemap.item(i).getNodeName().trim();
                    String attrValue=nnodemap.item(i).getNodeValue().trim();
                    if(attrValue!=null && !attrValue.equals("")){
                        if(attrName.equalsIgnoreCase("defconn")){
                            if(Boolean.valueOf(attrValue).booleanValue() && base!=null){ //es conecci�n por defecto
                                defconn=true;
                                init();
                                if(formfe!=null){
                                    HiddenFE hiddenfe=new HiddenFE("conname");
                                    hiddenfe.setValue("true");
                                    formfe.add(hiddenfe);
                                }
                            }else { // es otra conexi�n
                                defconn=false;
                            }
                        }else{
                                defconn=false;
                                if(attrName.equalsIgnoreCase("conname") && formfe!=null){
                                    this.conname=attrValue;
                                    createHiddenTag("conname",attrValue);
                                }else if(attrName.equalsIgnoreCase("tablename")){
                                    this.tablename=attrValue;
                                    createHiddenTag("tablename",attrValue);
                                }else if(attrName.equalsIgnoreCase("fieldname")){
                                    this.fieldname=attrValue;
                                    createHiddenTag("fieldname",attrValue);
                                }else if(attrName.equalsIgnoreCase("wbi_resID")){
                                    this.resID=attrValue;
                                    createHiddenTag("wbi_resID",attrValue);
                                }else if(attrName.equalsIgnoreCase("wbi_resIDTM")){
                                    this.resIDTM=attrValue;
                                    createHiddenTag("wbi_resIDTM",attrValue);
                                }else if(attrName.equalsIgnoreCase("params")){
                                    connparams=new TreeMap();
                                    StringTokenizer strParams=new StringTokenizer(attrValue,"|");
                                    while(strParams.hasMoreElements()){
                                        String param=strParams.nextToken();
                                        if(param.indexOf(",")>-1){
                                            String paramName=null;
                                            String paramValue=null;
                                            String pamamType=null;
                                            int cont=0;
                                            StringTokenizer strvalue=new StringTokenizer(param,",");
                                            while(strvalue.hasMoreElements()){
                                                String stoken=strvalue.nextToken();
                                                if (stoken == null) {
                                                   continue;
                                                }
                                                cont++;
                                                if(cont==1)paramName=stoken;
                                                else if(cont==2)paramValue=stoken;
                                                else if(cont==3)pamamType=stoken;
                                                else break;
                                            }
                                            if(paramName!=null && paramValue!=null && connparams.get(paramName)==null){
                                                if(pamamType==null) pamamType="String";
                                                connparams.put(paramName, paramValue+"|"+pamamType);
                                                createHiddenTag("wbpconn_"+paramName,paramValue+"|"+pamamType);
                                            }
                                        }
                                    }
                                }else if(attrName.equalsIgnoreCase("paramsInsert")){
                                    connparamsInsert=new TreeMap();
                                    StringTokenizer strParams=new StringTokenizer(attrValue,"|");
                                    while(strParams.hasMoreElements()){
                                        String param=strParams.nextToken();
                                        if(param.indexOf(",")>-1){
                                            String paramName=null;
                                            String paramValue=null;
                                            String pamamType=null;
                                            int cont=0;
                                            StringTokenizer strvalue=new StringTokenizer(param,",");
                                            while(strvalue.hasMoreElements()){
                                                String stoken=strvalue.nextToken();
                                                if (stoken == null) {
                                                   continue;
                                                }
                                                cont++;
                                                if(cont==1)paramName=stoken;
                                                else if(cont==2)paramValue=stoken;
                                                else if(cont==3)pamamType=stoken;
                                                else break;
                                            }
                                            if(paramName!=null && paramValue!=null && connparamsInsert.get(paramName)==null){
                                                if(pamamType==null) pamamType="String";
                                                connparamsInsert.put(paramName, paramValue+"|"+pamamType);
                                                createHiddenTag("wbpconnI_"+paramName,paramValue+"|"+pamamType);
                                            }
                                        }
                                    }
                                }
                        }
                    }
                }
                if(!defconn) init();
            }else {
                if(base!=null){ //si no se puso nada en el tag admdbconnmgr, crea conexi�n por defecto
                    defconn=true;
                    init();
                    if(formfe!=null){
                        HiddenFE hiddenfe=new HiddenFE("conname");
                        hiddenfe.setValue("true");
                        formfe.add(hiddenfe);
                    }
                }
            }
        }
    }
    
    
    private void createHiddenTag(String tagName,String TagVavue){
        if(formfe!=null){
            HiddenFE hiddenfe=new HiddenFE(tagName);
            hiddenfe.setValue(TagVavue);
            formfe.add(hiddenfe);
        }
    }
    
    private boolean createRecord2NoDefDB() {
        Connection con;
        try {
            long id=1;
            Timestamp lastupdate = new Timestamp(new java.util.Date().getTime());
            con = SWBUtils.DB.getConnection((String) SWBPlatform.getEnv("wb/db/nameconn"), "AdmDBConnMgr:createRecord2NoDefDB()");
            
            String query="SELECT max("+resID+") FROM "+tablename+" where "+resIDTM+"=?";
            
            //Si existen parametros para incluir en el query
            if(connparams!=null && connparams.size()>0){
                Iterator itParams=connparams.keySet().iterator();
                while(itParams.hasNext()){
                    String pname=(String)itParams.next();
                    String pvalue=(String)connparams.get(pname);
                    if(pname!=null && pvalue!=null){
                        int cont=0;
                        String svalue=null;
                        String stype=null;
                        StringTokenizer strParams=new StringTokenizer(pvalue,"|");
                        while(strParams.hasMoreElements()){
                            cont++;
                            if(cont==1) svalue=strParams.nextToken();
                            else if(cont==2) stype=strParams.nextToken();
                            else break;
                        }
                        if(svalue!=null && stype!=null){
                            query=query+" and "+ pname + "=?";
                        }
                    }
                }
            }
            
            PreparedStatement st = con.prepareStatement(query);
            
            st.setString(1, base.getWebSiteId());
            
            
            //Si existen parametros para incluir en el query
            if(connparams!=null && connparams.size()>0){
                Iterator itParams=connparams.keySet().iterator();
                int contParam=1;
                while(itParams.hasNext()){
                    String pname=(String)itParams.next();
                    String pvalue=(String)connparams.get(pname);
                    if(pname!=null && pvalue!=null){
                        int cont=0;
                        String svalue=null;
                        String stype=null;
                        StringTokenizer strParams=new StringTokenizer(pvalue,"|");
                        while(strParams.hasMoreElements()){
                            cont++;
                            if(cont==1) svalue=strParams.nextToken();
                            else if(cont==2) stype=strParams.nextToken();
                            else break;
                        }
                        contParam++;
                        if(svalue!=null && stype!=null){
                            if(stype.toLowerCase().equals("string")) st.setString(contParam,svalue);
                            else if(stype.toLowerCase().equals("int")) st.setInt(contParam,Integer.parseInt(svalue));
                            else if(stype.toLowerCase().equals("long")) st.setLong(contParam,Long.valueOf(svalue).longValue());
                        }
                    }
                }
            }
            
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                id = rs.getLong(1) + 1;
            }
            rs.close();
            st.close();
            
            ///****   TENGO QUE VER COMO LE ENVIO VALORES AL REGISTRO QUE VOY A CREAR, CUANDO LOS CAMPOS DE ESTE NO PERMITEN NULOS *******//
              
            query = "insert into "+ tablename+ "("+resID+","+resIDTM+","+fieldname;
            
            int contParams=3;
            //Si existen parametros para incluir en el query
            if(connparamsInsert!=null && connparamsInsert.size()>0){
                Iterator itParams=connparamsInsert.keySet().iterator();
                while(itParams.hasNext()){
                    String pname=(String)itParams.next();
                    String pvalue=(String)connparamsInsert.get(pname);
                    if(pname!=null && pvalue!=null){
                        int cont=0;
                        String svalue=null;
                        String stype=null;
                        StringTokenizer strParams=new StringTokenizer(pvalue,"|");
                        while(strParams.hasMoreElements()){
                            cont++;
                            if(cont==1) svalue=strParams.nextToken();
                            else if(cont==2) stype=strParams.nextToken();
                            else break;
                        }
                        if(svalue!=null && stype!=null){
                            query=query+","+ pname;
                            contParams++;
                        }
                    }
                }
            }
            query=query+")";
            
            query=query+" values (";
            for(int i=1;i<=contParams;i++){
                if(i>1)query=query+",";
                query=query+"?";
            }
            query=query+")";
            
            st = con.prepareStatement(query);
            st.setString(1, base.getId());
            st.setString(2, base.getWebSiteId());
            st.setString(3,getXmlParams()); 
           
            //Si existen parametros para incluir en el query
            if(connparams!=null && connparams.size()>0){
                Iterator itParams=connparams.keySet().iterator();
                int contParam=3;
                while(itParams.hasNext()){
                    String pname=(String)itParams.next();
                    String pvalue=(String)connparams.get(pname);
                    if(pname!=null && pvalue!=null){
                        int cont=0;
                        String svalue=null;
                        String stype=null;
                        StringTokenizer strParams=new StringTokenizer(pvalue,"|");
                        while(strParams.hasMoreElements()){
                            cont++;
                            if(cont==1) svalue=strParams.nextToken();
                            else if(cont==2) stype=strParams.nextToken();
                            else break;
                        }
                        contParam++;
                        if(svalue!=null && stype!=null){
                            if(stype.toLowerCase().equals("string")) st.setString(contParam,svalue);
                            else if(stype.toLowerCase().equals("int")) st.setInt(contParam,Integer.parseInt(svalue));
                            else if(stype.toLowerCase().equals("long")) st.setLong(contParam,Long.valueOf(svalue).longValue());
                            else if(stype.toLowerCase().equals("timestamp")) {
                                if(svalue.equals("$LASTUPDATE")) st.setTimestamp(contParam,lastupdate);
                                //new Timestamp()
                                //else st.setTimestamp(contParam,);
                            }
                        }
                    }
                }
            }
            
            st.executeUpdate();
            st.close();
            con.close();
        } catch (Exception e) {
            log.error(e);
        } 
        return true;
    }
    
    
    /**
     * Actualiza a una Bd que no es la de defecto de wb (tabla resource)
     * updates a DB that it is not the WebBuilder default (wbresource)
     */    
    public boolean update2NoDefDB() throws SWBException {
        boolean regresa=false;
        Connection con=null;
        PreparedStatement st=null;
        try {
            con=SWBUtils.DB.getConnection(conname);
            String query="update "+ tablename + " set "+ fieldname + " = ? where "+resID+"=? and "+resIDTM+"=?";
            
            //Si existen parametros para incluir en el query
            if(connparams!=null && connparams.size()>0){
                Iterator itParams=connparams.keySet().iterator();
                while(itParams.hasNext()){
                    String pname=(String)itParams.next();
                    String pvalue=(String)connparams.get(pname);
                    if(pname!=null && pvalue!=null){
                        int cont=0;
                        String svalue=null;
                        String stype=null;
                        StringTokenizer strParams=new StringTokenizer(pvalue,"|");
                        while(strParams.hasMoreElements()){
                            cont++;
                            if(cont==1) svalue=strParams.nextToken();
                            else if(cont==2) stype=strParams.nextToken();
                            else break;
                        }
                        if(svalue!=null && stype!=null){
                            query=query+" and "+ pname + "=?";
                        }
                    }
                }
            }
            st=con.prepareStatement(query);
            st.setString(1,getXmlParams());
            st.setString(2,base.getId());
            st.setString(3,base.getWebSiteId());
            
            //Si existen parametros para incluir en el query
            if(connparams!=null && connparams.size()>0){
                Iterator itParams=connparams.keySet().iterator();
                int contParam=3;
                while(itParams.hasNext()){
                    String pname=(String)itParams.next();
                    String pvalue=(String)connparams.get(pname);
                    if(pname!=null && pvalue!=null){
                        int cont=0;
                        String svalue=null;
                        String stype=null;
                        StringTokenizer strParams=new StringTokenizer(pvalue,"|");
                        while(strParams.hasMoreElements()){
                            cont++;
                            if(cont==1) svalue=strParams.nextToken();
                            else if(cont==2) stype=strParams.nextToken();
                            else break;
                        }
                        contParam++;
                        if(svalue!=null && stype!=null){
                            if(stype.toLowerCase().equals("string")) st.setString(contParam,svalue);
                            else if(stype.toLowerCase().equals("int")) st.setInt(contParam,Integer.parseInt(svalue));
                            else if(stype.toLowerCase().equals("long")) st.setLong(contParam,Long.valueOf(svalue).longValue());
                        }
                    }
                }
            }
            
            
            st.executeUpdate();
            regresa=true;
            st.close();
            con.close();
        }catch(Exception e) {
            log.error(e);
            regresa=false;
        }
        return regresa;
    }
    
    
    /**
     * Carga el xml del recurso en las propiedades de la clase
     * loads resource xml in the class properties
     */    
    public String loadXmlRes() throws SWBException {
        Connection con=null;
        //Statement st=null;
        ResultSet rs=null;
        String loadxml=null;
        try {
            con=SWBUtils.DB.getConnection(conname);
            
            String query="select " + fieldname + " from " + tablename + " where "+resID+"=? and "+resIDTM+"=?";
            
            //Si existen parametros para incluir en el query
            if(connparams!=null && connparams.size()>0){
                Iterator itParams=connparams.keySet().iterator();
                while(itParams.hasNext()){
                    String pname=(String)itParams.next();
                    String pvalue=(String)connparams.get(pname);
                    if(pname!=null && pvalue!=null){
                        int cont=0;
                        String svalue=null;
                        String stype=null;
                        StringTokenizer strParams=new StringTokenizer(pvalue,"|");
                        while(strParams.hasMoreElements()){
                            cont++;
                            if(cont==1) svalue=strParams.nextToken();
                            else if(cont==2) stype=strParams.nextToken();
                            else break;
                        }
                        if(svalue!=null && stype!=null){
                            query=query+" and "+ pname + "=?";
                        }
                    }
                }
            }
            System.out.println("query loadXmlRes:"+query);
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1,base.getId());
            st.setString(2,base.getWebSiteId());
            
            //Si existen parametros para incluir en el query
            if(connparams!=null && connparams.size()>0){
                Iterator itParams=connparams.keySet().iterator();
                int contParam=2;
                while(itParams.hasNext()){
                    String pname=(String)itParams.next();
                    String pvalue=(String)connparams.get(pname);
                    if(pname!=null && pvalue!=null){
                        int cont=0;
                        String svalue=null;
                        String stype=null;
                        StringTokenizer strParams=new StringTokenizer(pvalue,"|");
                        while(strParams.hasMoreElements()){
                            cont++;
                            if(cont==1) svalue=strParams.nextToken();
                            else if(cont==2) stype=strParams.nextToken();
                            else break;
                        }
                        contParam++;
                        if(svalue!=null && stype!=null){
                            if(stype.toLowerCase().equals("string")) st.setString(contParam,svalue);
                            else if(stype.toLowerCase().equals("int")) st.setInt(contParam,Integer.parseInt(svalue));
                            else if(stype.toLowerCase().equals("long")) st.setLong(contParam,Long.valueOf(svalue).longValue());
                        }
                    }
                }
            }
            
            rs=st.executeQuery();
            if(rs.next()) {
                existRecord=true;
                loadxml=rs.getString(fieldname);
            }
            rs.close();
            st.close();
            con.close();
        }catch(Exception e) {
            log.error(e);
        }
        System.out.println("loadxml:"+loadxml);
        return loadxml;
    }
    
    
    /**
     * Obtiene el nombre de la conecci�n
     */    
    public String getConName(){
        return conname;
    }
    
    public String getTableName(){
        return tablename;
    }
    
    public String getFieldName(){
        return fieldname;
    }
    
    public String getResID(){
        return resID;
    }
    
    public String getResIDTM(){
        return resIDTM;
    }
    
    public boolean getIsDefConn() {
        return defconn;
    }
    
    public TreeMap getConnparams(){
        return connparams;
    }
    
    public TreeMap getConnparamsInsert(){
        return connparamsInsert;
    }
    
}
