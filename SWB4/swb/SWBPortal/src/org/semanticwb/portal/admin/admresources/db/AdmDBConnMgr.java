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
package org.semanticwb.portal.admin.admresources.db;

import org.w3c.dom.*;
import java.util.*;
import javax.servlet.http.*;
import org.semanticwb.portal.admin.admresources.*;
import java.sql.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.*;
import org.semanticwb.portal.admin.admresources.util.WBAdmResourceUtils;
import org.semanticwb.portal.util.WBFileUpload;

// TODO: Auto-generated Javadoc
/**
 * Objeto que administra conecci�n o connecciones de bd de la api de administraci�n de recursos
 * <p>
 * Object that administers bd conections in the resources administration api.
 * 
 * @author  Jorge Alberto Jim�nez
 */
public class AdmDBConnMgr {

    /** The log. */
    private static Logger log = SWBUtils.getLogger(AdmDBConnMgr.class);
    
    /** The defconn. */
    private boolean defconn = true;
    
    /** The base. */
    private Resource base = null;
    
    /** The dom. */
    private Document dom = null;
    
    /** The req. */
    private HttpServletRequest req = null;
    
    /** The user. */
    private User user = null;
    
    /** The strbxmlp. */
    private StringBuffer strbxmlp = null;
    
    /** The imgapplet. */
    private String imgapplet = "";
    
    /** The tag. */
    private Node tag = null;
    
    /** The formfe. */
    private FormFE formfe = null;
    
    /** The conname. */
    private String conname = null;
    
    /** The tablename. */
    private String tablename = null;
    
    /** The fieldname. */
    private String fieldname = null;
    
    /** The res id. */
    private String resID = null;
    
    /** The res idtm. */
    private String resIDTM = null;
    
    /** The connparams. */
    private TreeMap connparams = null;
    
    /** The connparams insert. */
    private TreeMap connparamsInsert = null;
    
    /** The exist record. */
    private boolean existRecord = false;
    
    /** The adm res utils. */
    WBAdmResourceUtils admResUtils = new WBAdmResourceUtils();

    /**
     * Creates a new instance of AdmDBConnMgr.
     */
    public AdmDBConnMgr() {
    }

    /**
     * Creates a new instance of AdmDBConnMgr when is the dafult db.
     * 
     * @param base the base
     */
    public AdmDBConnMgr(Resource base) {
        this.base = base;
        this.defconn = true;
        this.conname = SWBPlatform.getEnv("wb/db/nameconn", "wb"); //conexion por defecto
        init();
    }

    /**
     * Creates a new instance of AdmDBConnMgr when is the dafult db.
     * 
     * @param conname the conname
     * @param tablename the tablename
     * @param fieldname the fieldname
     * @param resID the res id
     * @param resIDTM the res idtm
     * @param base the base
     */
    public AdmDBConnMgr(String conname, String tablename, String fieldname, String resID, String resIDTM, Resource base) {
        this.defconn = false;
        this.conname = conname;
        this.tablename = tablename;
        this.fieldname = fieldname;
        this.resID = resID;
        this.resIDTM = resIDTM;
        this.base = base;
        init();
    }

    /**
     * Creates a new instance of AdmDBConnMgr.
     * 
     * @param req the req
     * @param base the base
     */
    public AdmDBConnMgr(HttpServletRequest req, Resource base) {
        this.req = req;
        this.base = base;
        createXmlAdmRes(req);
    }

    /**
     * Creates a new instance of AdmDBConnMgr.
     * 
     * @param req the req
     * @param conname the conname
     * @param tablename the tablename
     * @param fieldname the fieldname
     * @param resID the res id
     * @param resIDTM the res idtm
     * @param base the base
     */
    public AdmDBConnMgr(HttpServletRequest req, String conname, String tablename, String fieldname, String resID, String resIDTM, Resource base) {
        this.defconn = false;
        this.req = req;
        this.conname = conname;
        this.tablename = tablename;
        this.fieldname = fieldname;
        this.resID = resID;
        this.resIDTM = resIDTM;
        this.base = base;
        createXmlAdmRes(req);
    }

    /**
     * Creates a new instance of AdmDBConnMgr when is the dafult db.
     * 
     * @param formfe the formfe
     * @param tag the tag
     * @param base the base
     */
    public AdmDBConnMgr(FormFE formfe, Node tag, Resource base) {
        this.tag = tag;
        this.base = base;
        this.formfe = formfe;
        this.conname = SWBPlatform.getEnv("wb/db/nameconn", "wb"); //conexion por defecto
        setAttrConn();
    }

    /**
     * Sets the request.
     * 
     * @param req the new request
     */
    public void setRequest(HttpServletRequest req) {
        this.req = req;
        createXmlAdmRes(req);
    }

    /**
     * Inits the.
     */
    public void init() {
        if (defconn) {
            dom = SWBUtils.XML.xmlToDom(base.getXml());
        } else { //saca datos del campo,tabla y db especificados(no de defecto),
            try {
                if (loadXmlRes() != null && !loadXmlRes().equals("")) {
                    dom = SWBUtils.XML.xmlToDom(loadXmlRes());
                }
            } catch (Exception e) {
                log.error(e);
            }
        }
    }
    //regresa el valor de un atributo
    /**
     * Gets the attribute.
     * 
     * @param attribute the attribute
     * @return the attribute
     */
    public String getAttribute(String attribute) {
        //return base.getAttribute(attribute);
        if (dom != null) {
            NodeList ndl = dom.getElementsByTagName(attribute);
            if (ndl.getLength() > 0) {
                for (int i = 0; i < ndl.getLength(); i++) {
                    if (ndl.item(i) != null && ndl.item(i).getChildNodes() != null && ndl.item(i).getChildNodes().item(0) != null && ndl.item(i).getChildNodes().item(0).getNodeValue() != null) {
                        return ndl.item(i).getChildNodes().item(0).getNodeValue();
                    }
                }
            }
        }
        return null;
    }
    //Regresa una lista de valores de un atributo dado
    /**
     * Gets the attribute values.
     * 
     * @param attribute the attribute
     * @return the attribute values
     */
    public ArrayList getAttributeValues(String attribute) {
        ArrayList attrvalues = new ArrayList();
        if (dom != null) {
            NodeList ndl = dom.getElementsByTagName(attribute);
            for (int i = 0; i < ndl.getLength(); i++) {
                if (ndl.item(i).getChildNodes().item(0).getNodeValue() != null) {
                    attrvalues.add(ndl.item(i).getChildNodes().item(0).getNodeValue());
                }
            }
        }
        return attrvalues;
    }

    /**
     * Crea el xml de la administraci�n de un recurso,
     * esto para cuando se edite el recurso ya tenga valores por defecto.
     * 
     * @param req the req
     */
    private void createXmlAdmRes(HttpServletRequest req) {
        this.req = req;
        strbxmlp = new StringBuffer();
        try {
            Document dom = SWBUtils.XML.getNewDocument();
            if (dom != null) {
                Element root = dom.createElement("resource");
                dom.appendChild(root);
                Element child = null;
                if (req.getHeader("content-type") != null && (req.getHeader("content-type").indexOf("multipart/form-data")) > -1) { // utilizar fileupload
                    HashMap hparams = new HashMap();
                    HashMap afiles = new HashMap();
                    connparams = new TreeMap();
                    connparamsInsert = new TreeMap();
                    WBFileUpload fUpload = new WBFileUpload();
                    fUpload.getFiles(req);
                    Iterator params = fUpload.getParamNames().iterator();
                    while (params.hasNext()) {
                        String paramN = (String) params.next();
                        if (fUpload.getFileName(paramN) == null) { //Para parametros que no son de tipo file
                            Iterator paramval = fUpload.getValue(paramN).iterator();
                            while (paramval.hasNext()) {
                                String value = (String) paramval.next();
                                if (!paramN.equalsIgnoreCase("conname") && !paramN.equalsIgnoreCase("tablename") && !paramN.equalsIgnoreCase("fieldname") && !paramN.equalsIgnoreCase("wbi_resID") && !paramN.equalsIgnoreCase("wbi_resIDTM") && !paramN.startsWith("wbpconn_") && !paramN.startsWith("wbpconnI_")) { //agregar a hashmap de parametros
                                    hparams.put(paramN, value);
                                } else if (paramN.equalsIgnoreCase("conname")) {
                                    if (value.trim().equalsIgnoreCase("true")) {
                                        defconn = true;
                                        this.conname = SWBPlatform.getEnv("wb/db/nameconn", "wb"); //conexion por defecto
                                        init();
                                    } else {  //Es otra conexi�n
                                        defconn = false;
                                        this.conname = value.trim();
                                    }
                                } else if (paramN.equalsIgnoreCase("tablename")) {
                                    this.tablename = value.trim();
                                } else if (paramN.equalsIgnoreCase("fieldname")) {
                                    this.fieldname = value.trim();
                                } else if (paramN.equalsIgnoreCase("wbi_resID")) {
                                    this.resID = value.trim();
                                } else if (paramN.equalsIgnoreCase("wbi_resIDTM")) {
                                    this.resIDTM = value.trim();
                                } else if (paramN.startsWith("wbpconn_")) {
                                    connparams.put(paramN.substring(8), value.trim());
                                } else if (paramN.startsWith("wbpconnI_")) {
                                    connparamsInsert.put(paramN.substring(9), value.trim());
                                }
                            }
                        } else { //Para parametros tipo file tiene que parsear y subir imagenes..
                            String filename = fUpload.getFileName(paramN);
                            int i = filename.lastIndexOf("\\");
                            if (i != -1) {
                                filename = filename.substring(i + 1);
                            }
                            i = filename.lastIndexOf("/");
                            if (i != -1) {
                                filename = filename.substring(i + 1);
                            }

                            child = dom.createElement(paramN);
                            child.appendChild(dom.createTextNode(filename.trim()));
                            root.appendChild(child);

                            afiles.put(paramN, filename.trim());

                            String tmp = admResUtils.uploadFileParsed(base, fUpload, paramN, req.getSession().getId());
                            if (tmp != null) {
                                imgapplet += tmp;
                            }
                        }
                    }
                    Iterator itparams = hparams.keySet().iterator();
                    while (itparams.hasNext()) {
                        String pname = (String) itparams.next();
                        String value = (String) hparams.get(pname);
                        //System.out.println("pname:"+pname+" value:"+value);
                        if (value.trim().equals("")) {
                            if (((String) hparams.get("wbfile_" + pname)) != null && hparams.get("wbNoFile_" + pname) == null) {
                                child = dom.createElement(pname);
                                child.appendChild(dom.createTextNode(((String) hparams.get("wbfile_" + pname)).trim()));
                                root.appendChild(child);
                            } else {
                                child = dom.createElement(pname);
                                child.appendChild(dom.createTextNode(value.trim()));
                                root.appendChild(child);
                            }
                            if (hparams.get("wbNoFile_" + pname) != null && hparams.get("wbfile_" + pname) != null) {
                                admResUtils.removeFileFromFS(SWBPortal.getWorkPath() + base.getWorkPath() + "/" + hparams.get("wbfile_" + pname));
                            }
                        } else if (!pname.startsWith("wbfile_") && !pname.startsWith("wbNoFile_") && !pname.startsWith("wbReplacefile_")) {
                            child = dom.createElement(pname);
                            child.appendChild(dom.createTextNode(value.trim()));
                            root.appendChild(child);
                        } else if (pname.startsWith("wbReplacefile_")) { //remover de filesystem archivo anterior
                            String hiddenFile = (String) hparams.get(pname); //archivo anterior
                            String field=pname.substring(14);  //valor de campo de archivo anterior
                            if(afiles.get(field)!=null){
                                admResUtils.removeFileFromFS(SWBPortal.getWorkPath() + base.getWorkPath() + "/" + hiddenFile);
                            }
                        }
                    }
                } else { //utilizar request normal
                    Enumeration en = req.getParameterNames();
                    boolean flag = true;
                    while (en.hasMoreElements()) {
                        String paramN = en.nextElement().toString();
                        String[] paramval = req.getParameterValues(paramN);
                        for (int i = 0; i < paramval.length; i++) {
                            child = dom.createElement(paramN);
                            child.appendChild(dom.createTextNode(paramval[i].trim()));
                            root.appendChild(child);
                        }
                    }
                }
                //strbxmlp.append(com.infotec.appfw.util.AFUtils.getInstance().DomtoXml(dom));
                strbxmlp.append(SWBUtils.XML.domToXml(dom, "ISO-8859-1", true));
            }
        } catch (Exception e) {
            log.error(e);
        }
    }

    /**
     * Gets the xml params.
     * 
     * @return the xml params
     */
    public String getXmlParams() {
        String xmlparams = null;
        if (strbxmlp != null) {
            xmlparams = strbxmlp.toString();
        }
        return xmlparams;
    }

    /**
     * Envia a actualizar a BD
     * send to update a DB.
     * 
     * @param user the user
     * @return the string
     */
    public String update2DB(User user) {
        this.user = user;
        try {
            String xml=getXmlParams();
            if (xml != null && defconn == true)
            {
                if(xml.length()>0)
                {
                    base.setXml(xml);
                }else
                {
                    log.error("Error updating xml of resource:"+base.getId());
                }
            //base.u(user.getId(), "resource width id:"+ base.getId()+",was updated succefully");
            } else { //No es base de datos defecto de recurso
                if (xml != null && defconn == false) {
                    loadXmlRes();
                    if (existRecord) {
                        if (update2NoDefDB()) {
                            log.error("data was saved succefully");
                        }
                    } else { //crea el registro en la bd que no es por defecto.
                        createRecord2NoDefDB();
                    }
                }
            }
        } catch (SWBException e) {
            log.error(e);
        }
        /*
        if(imgapplet!=null) {
        System.out.println("imgapplet:"+imgapplet);
        return strbxmlp.toString() +"\n"+ imgapplet;
        }
        else return strbxmlp.toString() +"\n";
         */
        return imgapplet;
    }

    /**
     * Gets the base.
     * 
     * @return the base
     */
    public Resource getBase() {
        return base;
    }

    /**
     * Coloca atributos de conecci�n a propiedades de la clase
     * insert connection attributes to class properties.
     */
    public void setAttrConn() {
        if (tag != null) {
            NamedNodeMap nnodemap = tag.getAttributes();
            if (nnodemap.getLength() > 0) {
                for (int i = 0; i < nnodemap.getLength(); i++) {
                    String attrName = nnodemap.item(i).getNodeName().trim();
                    String attrValue = nnodemap.item(i).getNodeValue().trim();
                    if (attrValue != null && !attrValue.equals("")) {
                        if (attrName.equalsIgnoreCase("defconn")) {
                            if (Boolean.valueOf(attrValue).booleanValue() && base != null) { //es conecci�n por defecto
                                defconn = true;
                                init();
                                if (formfe != null) {
                                    HiddenFE hiddenfe = new HiddenFE("conname");
                                    hiddenfe.setValue("true");
                                    formfe.add(hiddenfe);
                                }
                            } else { // es otra conexi�n
                                defconn = false;
                            }
                        } else {
                            defconn = false;
                            if (attrName.equalsIgnoreCase("conname") && formfe != null) {
                                this.conname = attrValue;
                                createHiddenTag("conname", attrValue);
                            } else if (attrName.equalsIgnoreCase("tablename")) {
                                this.tablename = attrValue;
                                createHiddenTag("tablename", attrValue);
                            } else if (attrName.equalsIgnoreCase("fieldname")) {
                                this.fieldname = attrValue;
                                createHiddenTag("fieldname", attrValue);
                            } else if (attrName.equalsIgnoreCase("wbi_resID")) {
                                this.resID = attrValue;
                                createHiddenTag("wbi_resID", attrValue);
                            } else if (attrName.equalsIgnoreCase("wbi_resIDTM")) {
                                this.resIDTM = attrValue;
                                createHiddenTag("wbi_resIDTM", attrValue);
                            } else if (attrName.equalsIgnoreCase("params")) {
                                connparams = new TreeMap();
                                StringTokenizer strParams = new StringTokenizer(attrValue, "|");
                                while (strParams.hasMoreElements()) {
                                    String param = strParams.nextToken();
                                    if (param.indexOf(",") > -1) {
                                        String paramName = null;
                                        String paramValue = null;
                                        String pamamType = null;
                                        int cont = 0;
                                        StringTokenizer strvalue = new StringTokenizer(param, ",");
                                        while (strvalue.hasMoreElements()) {
                                            String stoken = strvalue.nextToken();
                                            if (stoken == null) {
                                                continue;
                                            }
                                            cont++;
                                            if (cont == 1) {
                                                paramName = stoken;
                                            } else if (cont == 2) {
                                                paramValue = stoken;
                                            } else if (cont == 3) {
                                                pamamType = stoken;
                                            } else {
                                                break;
                                            }
                                        }
                                        if (paramName != null && paramValue != null && connparams.get(paramName) == null) {
                                            if (pamamType == null) {
                                                pamamType = "String";
                                            }
                                            connparams.put(paramName, paramValue + "|" + pamamType);
                                            createHiddenTag("wbpconn_" + paramName, paramValue + "|" + pamamType);
                                        }
                                    }
                                }
                            } else if (attrName.equalsIgnoreCase("paramsInsert")) {
                                connparamsInsert = new TreeMap();
                                StringTokenizer strParams = new StringTokenizer(attrValue, "|");
                                while (strParams.hasMoreElements()) {
                                    String param = strParams.nextToken();
                                    if (param.indexOf(",") > -1) {
                                        String paramName = null;
                                        String paramValue = null;
                                        String pamamType = null;
                                        int cont = 0;
                                        StringTokenizer strvalue = new StringTokenizer(param, ",");
                                        while (strvalue.hasMoreElements()) {
                                            String stoken = strvalue.nextToken();
                                            if (stoken == null) {
                                                continue;
                                            }
                                            cont++;
                                            if (cont == 1) {
                                                paramName = stoken;
                                            } else if (cont == 2) {
                                                paramValue = stoken;
                                            } else if (cont == 3) {
                                                pamamType = stoken;
                                            } else {
                                                break;
                                            }
                                        }
                                        if (paramName != null && paramValue != null && connparamsInsert.get(paramName) == null) {
                                            if (pamamType == null) {
                                                pamamType = "String";
                                            }
                                            connparamsInsert.put(paramName, paramValue + "|" + pamamType);
                                            createHiddenTag("wbpconnI_" + paramName, paramValue + "|" + pamamType);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (!defconn) {
                    init();
                }
            } else {
                if (base != null) { //si no se puso nada en el tag admdbconnmgr, crea conexi�n por defecto
                    defconn = true;
                    init();
                    if (formfe != null) {
                        HiddenFE hiddenfe = new HiddenFE("conname");
                        hiddenfe.setValue("true");
                        formfe.add(hiddenfe);
                    }
                }
            }
        }
    }

    /**
     * Creates the hidden tag.
     * 
     * @param tagName the tag name
     * @param TagVavue the tag vavue
     */
    private void createHiddenTag(String tagName, String TagVavue) {
        if (formfe != null) {
            HiddenFE hiddenfe = new HiddenFE(tagName);
            hiddenfe.setValue(TagVavue);
            formfe.add(hiddenfe);
        }
    }

    /**
     * Creates the record2 no def db.
     * 
     * @return true, if successful
     */
    private boolean createRecord2NoDefDB() {
        Connection con;
        try {
            long id = 1;
            Timestamp lastupdate = new Timestamp(new java.util.Date().getTime());
            con = SWBUtils.DB.getConnection((String) SWBPlatform.getEnv("wb/db/nameconn"), "AdmDBConnMgr:createRecord2NoDefDB()");

            String query = "SELECT max(" + resID + ") FROM " + tablename + " where " + resIDTM + "=?";

            //Si existen parametros para incluir en el query
            if (connparams != null && connparams.size() > 0) {
                Iterator itParams = connparams.keySet().iterator();
                while (itParams.hasNext()) {
                    String pname = (String) itParams.next();
                    String pvalue = (String) connparams.get(pname);
                    if (pname != null && pvalue != null) {
                        int cont = 0;
                        String svalue = null;
                        String stype = null;
                        StringTokenizer strParams = new StringTokenizer(pvalue, "|");
                        while (strParams.hasMoreElements()) {
                            cont++;
                            if (cont == 1) {
                                svalue = strParams.nextToken();
                            } else if (cont == 2) {
                                stype = strParams.nextToken();
                            } else {
                                break;
                            }
                        }
                        if (svalue != null && stype != null) {
                            query = query + " and " + pname + "=?";
                        }
                    }
                }
            }

            PreparedStatement st = con.prepareStatement(query);

            st.setString(1, base.getWebSiteId());


            //Si existen parametros para incluir en el query
            if (connparams != null && connparams.size() > 0) {
                Iterator itParams = connparams.keySet().iterator();
                int contParam = 1;
                while (itParams.hasNext()) {
                    String pname = (String) itParams.next();
                    String pvalue = (String) connparams.get(pname);
                    if (pname != null && pvalue != null) {
                        int cont = 0;
                        String svalue = null;
                        String stype = null;
                        StringTokenizer strParams = new StringTokenizer(pvalue, "|");
                        while (strParams.hasMoreElements()) {
                            cont++;
                            if (cont == 1) {
                                svalue = strParams.nextToken();
                            } else if (cont == 2) {
                                stype = strParams.nextToken();
                            } else {
                                break;
                            }
                        }
                        contParam++;
                        if (svalue != null && stype != null) {
                            if (stype.toLowerCase().equals("string")) {
                                st.setString(contParam, svalue);
                            } else if (stype.toLowerCase().equals("int")) {
                                st.setInt(contParam, Integer.parseInt(svalue));
                            } else if (stype.toLowerCase().equals("long")) {
                                st.setLong(contParam, Long.valueOf(svalue).longValue());
                            }
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

            query = "insert into " + tablename + "(" + resID + "," + resIDTM + "," + fieldname;

            int contParams = 3;
            //Si existen parametros para incluir en el query
            if (connparamsInsert != null && connparamsInsert.size() > 0) {
                Iterator itParams = connparamsInsert.keySet().iterator();
                while (itParams.hasNext()) {
                    String pname = (String) itParams.next();
                    String pvalue = (String) connparamsInsert.get(pname);
                    if (pname != null && pvalue != null) {
                        int cont = 0;
                        String svalue = null;
                        String stype = null;
                        StringTokenizer strParams = new StringTokenizer(pvalue, "|");
                        while (strParams.hasMoreElements()) {
                            cont++;
                            if (cont == 1) {
                                svalue = strParams.nextToken();
                            } else if (cont == 2) {
                                stype = strParams.nextToken();
                            } else {
                                break;
                            }
                        }
                        if (svalue != null && stype != null) {
                            query = query + "," + pname;
                            contParams++;
                        }
                    }
                }
            }
            query = query + ")";

            query = query + " values (";
            for (int i = 1; i <= contParams; i++) {
                if (i > 1) {
                    query = query + ",";
                }
                query = query + "?";
            }
            query = query + ")";

            st = con.prepareStatement(query);
            st.setString(1, base.getId());
            st.setString(2, base.getWebSiteId());
            st.setString(3, getXmlParams());

            //Si existen parametros para incluir en el query
            if (connparams != null && connparams.size() > 0) {
                Iterator itParams = connparams.keySet().iterator();
                int contParam = 3;
                while (itParams.hasNext()) {
                    String pname = (String) itParams.next();
                    String pvalue = (String) connparams.get(pname);
                    if (pname != null && pvalue != null) {
                        int cont = 0;
                        String svalue = null;
                        String stype = null;
                        StringTokenizer strParams = new StringTokenizer(pvalue, "|");
                        while (strParams.hasMoreElements()) {
                            cont++;
                            if (cont == 1) {
                                svalue = strParams.nextToken();
                            } else if (cont == 2) {
                                stype = strParams.nextToken();
                            } else {
                                break;
                            }
                        }
                        contParam++;
                        if (svalue != null && stype != null) {
                            if (stype.toLowerCase().equals("string")) {
                                st.setString(contParam, svalue);
                            } else if (stype.toLowerCase().equals("int")) {
                                st.setInt(contParam, Integer.parseInt(svalue));
                            } else if (stype.toLowerCase().equals("long")) {
                                st.setLong(contParam, Long.valueOf(svalue).longValue());
                            } else if (stype.toLowerCase().equals("timestamp")) {
                                if (svalue.equals("$LASTUPDATE")) {
                                    st.setTimestamp(contParam, lastupdate);
                                //new Timestamp()
                                //else st.setTimestamp(contParam,);
                                }
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
     * updates a DB that it is not the WebBuilder default (wbresource).
     * 
     * @return true, if successful
     * @throws SWBException the sWB exception
     */
    public boolean update2NoDefDB() throws SWBException {
        boolean regresa = false;
        Connection con = null;
        PreparedStatement st = null;
        try {
            con = SWBUtils.DB.getConnection(conname);
            String query = "update " + tablename + " set " + fieldname + " = ? where " + resID + "=? and " + resIDTM + "=?";

            //Si existen parametros para incluir en el query
            if (connparams != null && connparams.size() > 0) {
                Iterator itParams = connparams.keySet().iterator();
                while (itParams.hasNext()) {
                    String pname = (String) itParams.next();
                    String pvalue = (String) connparams.get(pname);
                    if (pname != null && pvalue != null) {
                        int cont = 0;
                        String svalue = null;
                        String stype = null;
                        StringTokenizer strParams = new StringTokenizer(pvalue, "|");
                        while (strParams.hasMoreElements()) {
                            cont++;
                            if (cont == 1) {
                                svalue = strParams.nextToken();
                            } else if (cont == 2) {
                                stype = strParams.nextToken();
                            } else {
                                break;
                            }
                        }
                        if (svalue != null && stype != null) {
                            query = query + " and " + pname + "=?";
                        }
                    }
                }
            }
            st = con.prepareStatement(query);
            st.setString(1, getXmlParams());
            st.setString(2, base.getId());
            st.setString(3, base.getWebSiteId());

            //Si existen parametros para incluir en el query
            if (connparams != null && connparams.size() > 0) {
                Iterator itParams = connparams.keySet().iterator();
                int contParam = 3;
                while (itParams.hasNext()) {
                    String pname = (String) itParams.next();
                    String pvalue = (String) connparams.get(pname);
                    if (pname != null && pvalue != null) {
                        int cont = 0;
                        String svalue = null;
                        String stype = null;
                        StringTokenizer strParams = new StringTokenizer(pvalue, "|");
                        while (strParams.hasMoreElements()) {
                            cont++;
                            if (cont == 1) {
                                svalue = strParams.nextToken();
                            } else if (cont == 2) {
                                stype = strParams.nextToken();
                            } else {
                                break;
                            }
                        }
                        contParam++;
                        if (svalue != null && stype != null) {
                            if (stype.toLowerCase().equals("string")) {
                                st.setString(contParam, svalue);
                            } else if (stype.toLowerCase().equals("int")) {
                                st.setInt(contParam, Integer.parseInt(svalue));
                            } else if (stype.toLowerCase().equals("long")) {
                                st.setLong(contParam, Long.valueOf(svalue).longValue());
                            }
                        }
                    }
                }
            }


            st.executeUpdate();
            regresa = true;
            st.close();
            con.close();
        } catch (Exception e) {
            log.error(e);
            regresa = false;
        }
        return regresa;
    }

    /**
     * Carga el xml del recurso en las propiedades de la clase
     * loads resource xml in the class properties.
     * 
     * @return the string
     * @throws SWBException the sWB exception
     */
    public String loadXmlRes() throws SWBException {
        Connection con = null;
        //Statement st=null;
        ResultSet rs = null;
        String loadxml = null;
        try {
            con = SWBUtils.DB.getConnection(conname);

            String query = "select " + fieldname + " from " + tablename + " where " + resID + "=? and " + resIDTM + "=?";

            //Si existen parametros para incluir en el query
            if (connparams != null && connparams.size() > 0) {
                Iterator itParams = connparams.keySet().iterator();
                while (itParams.hasNext()) {
                    String pname = (String) itParams.next();
                    String pvalue = (String) connparams.get(pname);
                    if (pname != null && pvalue != null) {
                        int cont = 0;
                        String svalue = null;
                        String stype = null;
                        StringTokenizer strParams = new StringTokenizer(pvalue, "|");
                        while (strParams.hasMoreElements()) {
                            cont++;
                            if (cont == 1) {
                                svalue = strParams.nextToken();
                            } else if (cont == 2) {
                                stype = strParams.nextToken();
                            } else {
                                break;
                            }
                        }
                        if (svalue != null && stype != null) {
                            query = query + " and " + pname + "=?";
                        }
                    }
                }
            }
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, base.getId());
            st.setString(2, base.getWebSiteId());

            //Si existen parametros para incluir en el query
            if (connparams != null && connparams.size() > 0) {
                Iterator itParams = connparams.keySet().iterator();
                int contParam = 2;
                while (itParams.hasNext()) {
                    String pname = (String) itParams.next();
                    String pvalue = (String) connparams.get(pname);
                    if (pname != null && pvalue != null) {
                        int cont = 0;
                        String svalue = null;
                        String stype = null;
                        StringTokenizer strParams = new StringTokenizer(pvalue, "|");
                        while (strParams.hasMoreElements()) {
                            cont++;
                            if (cont == 1) {
                                svalue = strParams.nextToken();
                            } else if (cont == 2) {
                                stype = strParams.nextToken();
                            } else {
                                break;
                            }
                        }
                        contParam++;
                        if (svalue != null && stype != null) {
                            if (stype.toLowerCase().equals("string")) {
                                st.setString(contParam, svalue);
                            } else if (stype.toLowerCase().equals("int")) {
                                st.setInt(contParam, Integer.parseInt(svalue));
                            } else if (stype.toLowerCase().equals("long")) {
                                st.setLong(contParam, Long.valueOf(svalue).longValue());
                            }
                        }
                    }
                }
            }

            rs = st.executeQuery();
            if (rs.next()) {
                existRecord = true;
                loadxml = rs.getString(fieldname);
            }
            rs.close();
            st.close();
            con.close();
        } catch (Exception e) {
            log.error(e);
        }
        return loadxml;
    }

    /**
     * Obtiene el nombre de la conecci�n.
     * 
     * @return the con name
     */
    public String getConName() {
        return conname;
    }

    /**
     * Gets the table name.
     * 
     * @return the table name
     */
    public String getTableName() {
        return tablename;
    }

    /**
     * Gets the field name.
     * 
     * @return the field name
     */
    public String getFieldName() {
        return fieldname;
    }

    /**
     * Gets the res id.
     * 
     * @return the res id
     */
    public String getResID() {
        return resID;
    }

    /**
     * Gets the res idtm.
     * 
     * @return the res idtm
     */
    public String getResIDTM() {
        return resIDTM;
    }

    /**
     * Gets the checks if is def conn.
     * 
     * @return the checks if is def conn
     */
    public boolean getIsDefConn() {
        return defconn;
    }

    /**
     * Gets the connparams.
     * 
     * @return the connparams
     */
    public TreeMap getConnparams() {
        return connparams;
    }

    /**
     * Gets the connparams insert.
     * 
     * @return the connparams insert
     */
    public TreeMap getConnparamsInsert() {
        return connparamsInsert;
    }
}
