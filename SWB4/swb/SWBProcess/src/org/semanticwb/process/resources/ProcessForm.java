package org.semanticwb.process.resources;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.rdf.model.RDFNode;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.security.GeneralSecurityException;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.DisplayProperty;
import org.semanticwb.model.FormElement;
import org.semanticwb.model.Resource;
import org.semanticwb.model.SWBClass;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.*;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.SWBForms;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBParameters;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.util.Base64;
import org.semanticwb.process.forms.SWBFormMgrLayer;
import org.semanticwb.process.model.DataTypes;
import org.semanticwb.process.model.FlowNodeInstance;
import org.semanticwb.process.model.Instance;
import org.semanticwb.process.model.ItemAware;
import org.semanticwb.process.model.ItemAwareReference;
import org.semanticwb.process.model.SWBProcessFormMgr;
import org.semanticwb.process.model.SWBProcessMgr;
import org.semanticwb.process.model.UserTask;
import org.semanticwb.process.model.X509Certificate;
import org.semanticwb.process.model.X509SingInstance;

/**
 * @author javier.solis
 * @modified by Juan Fernández
 * @modified by Hasdai Pacheco {ebenezer.sanchez@infotec.com.mx}
 */
public class ProcessForm extends GenericResource {
    public static final String ATT_PARAMREQUEST = "paramRequest";
    public static final String MODE_EDITPROP = "editProp";
    public static final String MODE_SIGN = "sign";
    public static final String MODE_ACUSE = "acuse";
    public static final String MODE_ADDPROPS = "addProps";
    public static final String PARAM_PROPIDX = "prop";
    public static final String PARAM_PROPMODE = "propMode";
    public static final String PARAM_PROPFE = "propFe";
    public static final String PARAM_PROPLBL = "propLabel";
    public static final String PARAM_DIR = "dir";
    public static final String PARAM_ADMMODE = "adminMode";
    public static final String PARAM_BTNID = "buttonId";
    public static final String PARAM_ROLES = "roles";
    public static final String ADM_MODEADVANCED = "advance";
    public static final String ADM_MODESIMPLE = "simple";
    public static final String ATT_TASK = "task";
    public static final String ATT_RBASE = "rbase";
    public static final String ACT_TOGGLEBUTTON = "toggleBut";
    public static final String ACT_ADDPROPS = "addProps";
    public static final String ACT_REMOVEPROP = "removeProp";
    public static final String ACT_UPDPROP = "updateProp";
    public static final String ACT_UPDBTNLABEL = "updateButtonLabel";
    public static final String ACT_UPDATEXML = "updateXml";
    public static final String ACT_SAVEXML = "saveXml";
    public static final String ACT_PROCESSSIGN = "processSign";
    public static final String ACT_SWAP = "swap";
    public static final String ACT_UPDADMINMODE = "updateMode";
    public static final String ACT_PROCESS = "process";
    public static final String FE_DEFAULT = "generico";
    public static final String FE_MODE_VIEW = "view";
    public static final String FE_MODE_EDIT = "edit";
    public static final Logger log = SWBUtils.getLogger(ProcessForm.class);

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String action = response.getAction();
        Resource base = getResourceBase();
        String suri = request.getParameter("suri");
        User user = response.getUser();
        response.setRenderParameter("suri", suri);
        String lang = "es";
        if (user != null && user.getLanguage() != null) {
            lang = user.getLanguage();
        }
        
        if (ACT_UPDBTNLABEL.equals(action)) {
            String btn = request.getParameter(ProcessForm.PARAM_BTNID);
            String label = request.getParameter("btnLabel");
            
            if (label != null && label.trim().length() >0) {
                if ("btnAccept".equals(btn)) {
                    base.setAttribute("btnAcceptLabel", label);
                }
                if ("btnReject".equals(btn)) {
                    base.setAttribute("btnRejectLabel", label);
                }
                if ("btnCancel".equals(btn)) {
                    base.setAttribute("btnCancelLabel", label);
                }
                if ("btnSave".equals(btn)) {
                    base.setAttribute("btnSaveLabel", label);
                }
            }
            response.setMode(SWBActionResponse.Mode_ADMIN);
        } else if (ACT_TOGGLEBUTTON.equals(action)) {
            String toggle = request.getParameter("btns");
            if (base.getAttribute("btnAcceptLabel","").equals("")) {
                base.setAttribute("btnAcceptLabel", "Concluir Tarea");
            }

            if (base.getAttribute("btnRejectLabel","").equals("")) {
                base.setAttribute("btnRejectLabel", "Rechazar Tarea");
            }

            if (base.getAttribute("btnCancelLabel","").equals("")) {
                base.setAttribute("btnCancelLabel", "Regresar");
            }

            if (base.getAttribute("btnSaveLabel","").equals("")) {
                base.setAttribute("btnSaveLabel", "Guardar");
            }
            
            if ("showHeader".equals(toggle)) {
                if(base.getAttribute("showHeader", "").equals("use")) {
                    base.removeAttribute("showHeader");
                } else {
                    base.setAttribute("showHeader", "use");
                }
            }
            if ("accept".equals(toggle)) {
                if(base.getAttribute("btnAccept", "").equals("use")) {
                    base.removeAttribute("btnAccept");
                } else {
                    base.setAttribute("btnAccept", "use");
                }
            }
            if ("reject".equals(toggle)) {
                if(base.getAttribute("btnReject", "").equals("use")) {
                    base.removeAttribute("btnReject");
                } else {
                    base.setAttribute("btnReject", "use");
                }
            }
            if ("cancel".equals(toggle)) {
                if(base.getAttribute("btnCancel", "").equals("use")) {
                    base.removeAttribute("btnCancel");
                } else {
                    base.setAttribute("btnCancel", "use");
                }
            }
            if ("save".equals(toggle)) {
                if(base.getAttribute("btnSave", "").equals("use")) {
                    base.removeAttribute("btnSave");
                } else {
                    base.setAttribute("btnSave", "use");
                }
            }
            if ("sign".equals(toggle)) {
                if(base.getAttribute("useSign", "").equals("use")) {
                    base.removeAttribute("useSign");
                } else {
                    base.setAttribute("useSign", "use");
                }
            }
            
            try {
                base.updateAttributesToDB();
            } catch (Exception e) {
                log.error("FormsBuilderResource - Error al actualizar la configuración de botones.",e);
            }
        } else if (ACT_ADDPROPS.equals(action)) {
            UserTask task = null;
            if (getResourceBase().getResourceable() instanceof UserTask) {
                task = (UserTask) getResourceBase().getResourceable();
            }
            HashMap<String, ItemAware> userDefinedVars = new HashMap<String, ItemAware>();
            
            HashMap<String, SemanticProperty> allprops = new HashMap<String, SemanticProperty>();
            if (task != null) {
                Iterator<ItemAware> it = task.listHerarquicalRelatedItemAwarePlusNullOutputs().iterator();
                while (it.hasNext()) {
                    ItemAware item = it.next();
                    SemanticClass cls = item.getItemSemanticClass();
                    if (cls != null) {
                        Iterator<SemanticProperty> itp = cls.listProperties();
                        while (itp.hasNext()) {
                            SemanticProperty prop = itp.next();
                            String name = item.getName() + "|" + prop.getPropId();
                            if (cls.isSubClass(DataTypes.sclass) && !userDefinedVars.containsKey(name)) {
                                userDefinedVars.put(name, item);
                            }
                            if (!prop.getPropId().equals("swb:valid") && !allprops.containsKey(name)) {
                                allprops.put(name, prop);
                            }
                        }
                    }
                }
                
                //Obtener la lista de propiedades a agregar
                String[] props = request.getParameterValues("properties");
                HashMap<String, String> hmparam = new HashMap<String, String>();
                if (props != null && props.length > 0) {
                    for (int j = 0; j < props.length; j++) {
                        hmparam.put(props[j], props[j]);
                    }
                }

                //Recuperar las propiedades ya configuradas
                HashMap<Integer, String> hmprops = new HashMap();
                int i = 1;
                int j = 1;
                while (!base.getAttribute("prop" + i, "").equals("")) {
                    String val = base.getAttribute("prop" + i);
                    HashMap<String, String> propMap = getPropertiesMap(val);
                    String propKey = propMap.get("varName") +"|"+ propMap.get("propId");
                    
                    if (allprops.containsKey(propKey)) {
                        hmprops.put(new Integer(j++), val);
                        if (hmparam.containsKey(propKey)) {
                            hmparam.remove(propKey);
                        }
                    }
                    
                    base.removeAttribute("prop" + i);
                    i++;
                }

                //Agregar propiedades faltantes
                String defaultFE = FE_DEFAULT;
                String defaultMode = FE_MODE_EDIT;
                String defaultLabel = "";
                String defaultRoles = "";

                // agregando al hmprops las propiedades nuevas
                Iterator<String> itpar = hmparam.keySet().iterator();
                while (itpar.hasNext()) {
                    String strnew = itpar.next();
                    HashMap<String, String> propMap = getPropertiesMap(strnew);
                    SemanticProperty sempro = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticPropertyById(propMap.get("propId"));
                    if (sempro != null) {
                        String name = propMap.get("varName")+"|"+propMap.get("propId");
                        if (userDefinedVars.containsKey(name)) {
                            defaultLabel = userDefinedVars.get(name).getDisplayTitle(lang);
                        } else {
                            defaultLabel = sempro.getDisplayName(response.getUser().getLanguage());
                        }
                        SemanticObject dp = sempro.getDisplayProperty();
                        if (dp != null) {
                            DisplayProperty disprop = new DisplayProperty(dp);
                            SemanticObject semobjFE = disprop.getFormElement();
                            if (semobjFE != null) {
                                defaultFE = semobjFE.getURI();
                            } else {
                                defaultFE = FE_DEFAULT;
                            }
                        }
                    } else {
                        defaultFE = FE_DEFAULT;
                    }

                    String value = strnew + "|" + defaultMode + "|" + defaultFE + "|" + defaultLabel + "|" + defaultRoles;
                    hmprops.put(new Integer(j), value);
                    j++;
                }

                //guardando las propiedades
                Iterator<Integer> itprop = hmprops.keySet().iterator();
                while (itprop.hasNext()) {
                    Integer integer = itprop.next();
                    String thisprop = hmprops.get(integer);
                    base.setAttribute("prop" + integer, thisprop);
                }

                try {
                    base.updateAttributesToDB();
                } catch (Exception e) {
                    log.error("Error al guardar las propiedades de acuerdo al display property.",e);
                }
            }
            response.setMode(SWBActionResponse.Mode_ADMIN);
        } else if (ACT_REMOVEPROP.equals(action)) {
            String prop = request.getParameter(PARAM_PROPIDX);

            ArrayList<String> hmprops = new ArrayList<String>();
            int i = 1;
            while (!base.getAttribute("prop" + i, "").equals("")) {
                if (!("" + i).equals(prop)) {
                    hmprops.add(base.getAttribute("prop" + i));
                }
                base.removeAttribute("prop" + i);
                i++;
            }
            
            i = 1;
            Iterator<String> itprop = hmprops.iterator();
            while (itprop.hasNext()) {
                String thisprop = itprop.next();
                base.setAttribute("prop" + i, thisprop);
                i++;
            }
            
            try {
                base.updateAttributesToDB();
            } catch (Exception ex) {
                log.error("Error al actualizar la propiedad",ex);
            }
        } else if (ACT_UPDPROP.equals(action)) {
            String propId = request.getParameter(PARAM_PROPIDX);
            if (!base.getAttribute("prop"+propId, "").equals("")) {
                String str = base.getAttribute("prop"+propId, "");
                HashMap<String, String> propMap = getPropertiesMap(str);
                
                SemanticProperty sprop = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticPropertyById(propMap.get("propId"));
                String label = request.getParameter(PARAM_PROPLBL);
                String mode = request.getParameter(PARAM_PROPMODE);
                String formElement = request.getParameter(PARAM_PROPFE);
                String [] roles = request.getParameterValues(PARAM_ROLES);
                
                if (sprop != null) {
                    if (label != null) {
                        propMap.put("label", label);
                    }
                    if (mode != null && propMap.get("mode") != null) {
                        propMap.put("mode", mode);
                    }
                    if (formElement != null && propMap.get("fe") != null) {
                        propMap.put("fe", formElement);
                    }
                    if (roles != null && roles.length > 0) {
                        String roleList = "";
                        for (int i = 0; i < roles.length; i++) {
                            String rName = roles[i];
                            if (rName.trim().length() > 0) {
                                roleList += rName;
                            }
                            if (i < roles.length) {
                                roleList += ":";
                            }
                        }
                        propMap.put("roles", roleList);
                    }
                    
                    base.setAttribute("prop"+propId, propMap.get("varName") + "|" + propMap.get("propId") + "|" + propMap.get("mode") + "|" + propMap.get("fe") + "|" + propMap.get("label") + "|" + propMap.get("roles"));
                    try {
                        base.updateAttributesToDB();
                    } catch (Exception ex) {
                        log.error("Error al actualizar la propiedad",ex);
                    }
                    
                    request.getSession(true).setAttribute("reload", true);
                }
            }
            response.setMode(SWBActionResponse.Mode_ADMIN);
        } else if (ACT_SWAP.equals(action)) {
            String dir = request.getParameter(PARAM_DIR);
            String propid = request.getParameter(PARAM_PROPIDX);
            
            if (propid != null && propid.trim().length() > 0 && dir != null) {
                int pos = 0;
                int newPos = 0;
                boolean valid = false;
                
                String temp = base.getAttribute("prop"+propid);
                if (temp != null) {
                    try {
                        pos = Integer.parseInt(propid);
                    } catch (Exception e) {}

                    if (dir.equals("down")) {
                        newPos = pos + 1;
                        if (pos > 0 && newPos > 0 && pos < newPos) {
                            valid = true;
                        }
                    } else if (dir.equals("up")) {
                        newPos = pos - 1;
                        if (pos > 0 && newPos > 0 && pos > newPos) {
                            valid = true;
                        }
                    }
                    
                    String tmp2 = base.getAttribute("prop" + newPos);
                    if (tmp2 != null && valid) {
                        base.setAttribute("prop" + pos, tmp2);
                        base.setAttribute("prop" + newPos, temp);
                    }
                    
                    try {
                        base.updateAttributesToDB();
                    } catch (Exception e) {
                        log.error("Error al reordenar propiedad....", e);
                    }
                }
            }
        } else if (ACT_UPDADMINMODE.equals(action)) {
            base.setAttribute(PARAM_ADMMODE, request.getParameter(PARAM_ADMMODE));
            try {
                base.updateAttributesToDB();
            } catch (Exception e) {
                log.error("Error al actualizar el modo de administración del recurso.",e);
            }
        } else if (ACT_UPDATEXML.equals(action)) {
            String basepath = SWBPortal.getWorkPath() + base.getWorkPath() + "/";
            File xmlFile = new File(basepath);
            if (!xmlFile.exists()) {
                xmlFile.mkdirs();
            }

            if (xmlFile.exists()) {
                try {
                    String value = getFormHTML(request, response);
                    xmlFile = new File(basepath + "code.xml");
                    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(xmlFile)));
                    out.print(value);
                    out.flush();
                } catch (Exception e) {
                    log.error("Error saving file: " + xmlFile.getAbsolutePath(), e);
                }
            }
        } else if (ACT_SAVEXML.equals(action)) {
            String basepath = SWBPortal.getWorkPath() + base.getWorkPath() + "/";
            File xmlFile = new File(basepath);
            if (xmlFile.exists()) {
                try {
                    String value = null;
                    if (request.getParameter("hiddencode") != null && request.getParameter("hiddencode").trim().length() > 0) {
                        value = request.getParameter("hiddencode");
                    } else {
                        value = request.getParameter("resource" + base.getId());
                    }
                    xmlFile = new File(basepath + "code.xml");
                    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(xmlFile)));
                    out.print(value);
                    out.flush();
                } catch (Exception e) {
                    log.error("Error saving file: " + xmlFile.getAbsolutePath(), e);
                }
            }
        } else if (ACT_PROCESSSIGN.equals(action)) {
            FlowNodeInstance foi = (FlowNodeInstance) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(suri);
            if (suri == null || foi.getStatus() == FlowNodeInstance.STATUS_CLOSED) {
                return;
            }
            String cadenaOrig=null;
            String cadenaBase=getStringToSign(foi);
            try {
                cadenaOrig=SWBUtils.CryptoWrapper.comparablePassword(cadenaBase);
            } catch (GeneralSecurityException gse){
                log.error(gse);
            }
            //System.out.println("processSign:"+foi+" "+cadenaOrig);

            String appletHidden = request.getParameter("hiddenSign");
            //System.out.println("appletValue:"+appletHidden);
            try {
                CertificateFactory cf = CertificateFactory.getInstance("X.509");
                if(user.getExternalID()!=null)
                {
                    //System.out.println("user.getExternalID():"+user.getExternalID());
                    Iterator<SemanticObject>it = foi.getProcessSite().getSemanticModel().listSubjects(X509Certificate.swp_X509Serial, user.getExternalID()); //TODO: Modificar Búsqueda
                    if (it.hasNext()){
                        X509Certificate certObj = (X509Certificate)it.next().createGenericInstance();
                        //System.out.println("certObj:"+certObj);
                        FileInputStream fis = new FileInputStream(SWBPortal.getWorkPath()+certObj.getWorkPath()+"/"+certObj.getFile());
                        BufferedInputStream bis = new BufferedInputStream(fis);

                        Certificate cert=null;
                        //System.out.println("datos:"+bis.available());
                        if (bis.available() > 0) {
                            cert = cf.generateCertificate(bis);
                        }
                        //System.out.println("cert:"+cert);
                        bis.close();
                        fis.close();
                        Signature sig = Signature.getInstance("SHA1withRSA");
                        sig.initVerify(cert);
                        byte[] data = Base64.decode(appletHidden);
                        sig.update(cadenaOrig.getBytes());
                        boolean flag = sig.verify(data);
                        //System.out.println("validado:"+flag);
                        if (flag){
                            X509SingInstance x509SingInstance=X509SingInstance.ClassMgr.createX509SingInstance(foi.getProcessSite());
                            x509SingInstance.setCertificate(certObj);
                            x509SingInstance.setFlowNodeInstance(foi);
                            x509SingInstance.setOriginalString(cadenaOrig);
                            x509SingInstance.setSignedString(appletHidden);

                            File file = new File(SWBPortal.getWorkPath()+x509SingInstance.getWorkPath());
                            file.mkdirs();
                            FileOutputStream fileOut = new FileOutputStream(SWBPortal.getWorkPath()+x509SingInstance.getWorkPath()+"/baseData.nt");
                            fileOut.write(cadenaBase.getBytes("utf8"));
                            fileOut.flush();
                            fileOut.close();

                            foi.close(response.getUser(), Instance.ACTION_ACCEPT);
                            response.setMode(MODE_ACUSE);
                            response.setRenderParameter("sg", x509SingInstance.getURI());

                            //response.sendRedirect(foi.getUserTaskInboxUrl());//URL BOTON

                        } else {
                            log.error(new Exception("Error al validar firma..."));
                            //TODO: Fallo de validación de firma.....
                            response.setMode(MODE_SIGN);
                            response.setRenderParameter("err", "invCert");
                        }

                    }else
                    {
                        response.setMode(MODE_SIGN);
                        response.setRenderParameter("err", "noCert");
                    }
                }else
                {
                        response.setMode(MODE_SIGN);
                        response.setRenderParameter("err", "noCert");
                }
            } catch (Exception gse){
                //TODO: Como poner el error...
                log.error("Error al firmar la tarea...", gse);
            }
            // TODO: falta procesar el parámetro del applet de la firma
            // al final se cierra ....
        } else if (ACT_PROCESS.equals(action)) {
            FlowNodeInstance foi = (FlowNodeInstance) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(suri);
            if (suri == null || foi.getStatus() == FlowNodeInstance.STATUS_CLOSED) {
                return;
            }
            SWBProcessFormMgr mgr = new SWBProcessFormMgr(foi);
            mgr.clearProperties();

            HashMap<String, String> hmprops = new HashMap();
            int i = 1;
            while (!base.getAttribute("prop" + i, "").equals("")) {
                String sprop = base.getAttribute("prop" + i);
                HashMap<String, String> propMap = getPropertiesMap(sprop);

                hmprops.put(propMap.get("varName") + "|" + propMap.get("propId"), sprop);
                i++;
            }

            Iterator<ItemAwareReference> it = foi.listHeraquicalItemAwareReference().iterator();
            while (it.hasNext()) {
                ItemAwareReference item = it.next();
                SWBClass obj = item.getProcessObject();
                if (obj != null) {
                    String varName = item.getItemAware().getName();
                    SemanticClass cls = obj.getSemanticObject().getSemanticClass();
                    Iterator<SemanticProperty> itp = cls.listProperties();
                    while (itp.hasNext()) {
                        SemanticProperty prop = itp.next();
                        if (isViewProperty(response, varName, prop, hmprops)) {
                            mgr.addProperty(prop, varName, SWBFormMgr.MODE_VIEW);
                        } else if (isEditProperty(response, varName, prop, hmprops)) {
                            mgr.addProperty(prop, varName, SWBFormMgr.MODE_EDIT);
                        }
                    }
                }
            }
            try {
                mgr.processForm(request);
                String act=null;
                String redirect=null;

                // validar por si requiere firmado
                if (base.getAttribute("useSign", "").equals("use")) {
                    //redireccionamiento a doSign
                    response.setMode(MODE_SIGN);
                    //response.setAction("");
                } else if (request.getParameter("accept") != null && request.getParameter("accept").length()>0) {
                    act=Instance.ACTION_ACCEPT;
                } else if (request.getParameter("reject") != null && request.getParameter("reject").length()>0) {
                    act=Instance.ACTION_REJECT;
                }
                
                if(act!=null)
                {
                    foi.close(response.getUser(), act);
                    if(foi.getFlowNodeType() instanceof UserTask && ((UserTask)foi.getFlowNodeType()).isLinkNextUserTask())
                    {
                        List<FlowNodeInstance> arr=SWBProcessMgr.getActiveUserTaskInstances(foi.getProcessInstance(),response.getUser());                        
                        if(arr.size()>0)
                        {
                            redirect=arr.get(0).getUserTaskUrl();
                        }else
                        {
                            redirect=foi.getUserTaskInboxUrl();
                        }
                    }else
                    {
                        redirect=foi.getUserTaskInboxUrl();
                    }
                }
                if(redirect!=null)
                {
                    response.sendRedirect(redirect);
                }
            } catch (Exception e) {
                log.error("Error al procesar ....",e);
                response.setRenderParameter("err", "invalidForm");
            }
        } else {
            super.processAction(request, response);
        }
    }
    
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String mode = paramRequest.getMode();
        if (MODE_EDITPROP.equals(mode)) {
            doConfig(request, response, paramRequest);
        } else if (MODE_SIGN.equals(mode)) {
            doSign(request, response, paramRequest);
        } else if (MODE_ACUSE.equals(mode)) {
            doAcuse(request, response, paramRequest);
        } else if (MODE_ADDPROPS.equals(mode)) {
            doAddProps(request, response, paramRequest);
        } else {
            super.processRequest(request, response, paramRequest);
        }
    }

    public void doAddProps(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        StringBuilder sb = new StringBuilder();
        PrintWriter out = response.getWriter();
        
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        
        String taskUri = request.getParameter(ATT_TASK);
        SWBResourceURL urladd = paramRequest.getActionUrl().setAction(ProcessForm.ACT_ADDPROPS);
        
        UserTask uTask = (UserTask)SWBPlatform.getSemanticMgr().getOntology().getGenericObject(URLDecoder.decode(taskUri, "utf-8"));
        if (uTask != null) {
            HashMap<String, SemanticProperty> allprops = new HashMap();
            Iterator<ItemAware> it = uTask.listHerarquicalRelatedItemAwarePlusNullOutputs().iterator();
            
            while (it.hasNext()) {
                ItemAware item = it.next();
                SemanticClass cls = item.getItemSemanticClass();
                if (cls != null) {
                    Iterator<SemanticProperty> itp = cls.listProperties();
                    while (itp.hasNext()) {
                        SemanticProperty prop = itp.next();
                        String name = item.getName() + "|" + prop.getPropId();
                        if (!prop.getPropId().equals("swb:valid") && !allprops.containsKey(name)) {
                            allprops.put(name, prop);
                        }
                    }
                }
            }
            
            Resource base = getResourceBase();
            int max = 1;
            while (!base.getAttribute("prop" + max, "").equals("")) {
                String val = base.getAttribute("prop" + max++);
                HashMap<String, String> map = ProcessForm.getPropertiesMap(val);
                String key = map.get("varName") + "|" + map.get("propId");
                if (allprops.containsKey(key)) {
                    allprops.remove(key);
                }
            }
            
            ArrayList<String> list = new ArrayList(allprops.keySet());
            if (!list.isEmpty()) {
                Collections.sort(list);
                Iterator<String> its = list.iterator();
                
                sb.append("<form action=\"").append(urladd)
                    .append("\" class=\"swbform\" dojoType=\"dijit.form.Form\" id=\"")
                    .append(uTask.getId())
                    .append("/addProps\" method=\"post\" onsubmit=\"submitForm('")
                    .append(uTask.getId())
                    .append("/addProps'); return false;\">");
                sb.append("  <fieldset>");
                sb.append("    <select multiple size=\"10\" name=\"properties\" style=\"width:250px;\">");
                while (its.hasNext()) {
                    String str = its.next();
                    String varName = "";
                    StringTokenizer stoken = new StringTokenizer(str, "|");
                    if (stoken.hasMoreTokens()) {
                        varName = stoken.nextToken();
                    }
                    SemanticProperty sp = allprops.get(str);
                    sb.append("<option value=\"").append(str).append("\">").append(varName).append(".").append(sp.getPropertyCodeName()).append("</option>");
                }
                sb.append("    </select>");
                sb.append("  </fieldset>");
                sb.append("  <fieldset>");
                sb.append("    <button dojoType=\"dijit.form.Button\" type=\"submit\">").append(paramRequest.getLocaleString("addSelected")).append("</button>");
                sb.append("    <button dojoType=\"dijit.form.Button\" onclick=\"hideDialog('configDialog');\">").append(paramRequest.getLocaleString("cancel")).append("</button>");
                sb.append("  </fieldset>");
                sb.append("</form>");
            } else {
                sb.append("<span>").append(paramRequest.getLocaleString("msgNoProps")).append("</span>");
            }
        } else {
            sb.append("<span>").append(paramRequest.getLocaleString("msgNoTask")).append("</span>");
        }
        out.print(sb.toString());
    }
    
    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String jsp = "/swbadmin/jsp/process/formsBuilder/admin.jsp";
        Resource base = getResourceBase();
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        
        RequestDispatcher rd = request.getRequestDispatcher(jsp);
        try {
            request.setAttribute(ATT_PARAMREQUEST, paramRequest);
            request.setAttribute(ATT_RBASE, base);
            if (base.getResourceable() instanceof UserTask) {
                request.setAttribute(ATT_TASK, (UserTask)base.getResourceable());
            }
            rd.include(request, response);
        } catch (Exception ex) {
            log.error("FormsBuilderResource - Error including admin.jsp", ex);
        }
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String lang = paramRequest.getUser().getLanguage();
        String suri = request.getParameter("suri");
        StringBuilder ret = new StringBuilder("");
        User user = paramRequest.getUser();
        Resource base = getResourceBase();
        WebSite site = paramRequest.getWebPage().getWebSite();
        HashMap<String, ItemAware> userDefinedVars = new HashMap<String, ItemAware>();

        String adminMode = base.getAttribute("adminMode", ADM_MODESIMPLE);
        String action = paramRequest.getAction();
        
        if (suri == null) {
            out.println("<script type=\"text/javascript\">"
                    + " alert('"+paramRequest.getLocaleString("msgNoInstance")+"');"
                    + "</script>");
            return;
        }
        
        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        FlowNodeInstance foi = (FlowNodeInstance) ont.getGenericObject(suri);
        String viewUrl = foi.getUserTaskInboxUrl();
        
        User asigned = foi.getAssignedto();
        if (asigned != null && !asigned.equals(user)) {
            out.println("<script type=\"text/javascript\">"
                    + " alert('"+paramRequest.getLocaleString("msgAssigned")+"');"
                    + "window.location='" + viewUrl + "';"
                    + "</script>");
            return;
        }

        if (foi.getStatus() == Instance.STATUS_CLOSED || foi.getStatus() == Instance.STATUS_ABORTED || foi.getStatus() == Instance.STATUS_STOPED) {
            out.println("<script type=\"text/javascript\">"
                    + " alert('"+paramRequest.getLocaleString("cancel")+"');"
                    + "window.location='" + viewUrl + "';"
                    + "</script>");
            return;
        }

        if (asigned == null) {
            foi.setAssigned(new Date());
            foi.setAssignedto(user);
        }

        SWBProcessFormMgr mgr = new SWBProcessFormMgr(foi);
        mgr.setAction(paramRequest.getActionUrl().setAction(ACT_PROCESS).toString());
        mgr.clearProperties();

        HashMap<String, SemanticClass> hmclass = new HashMap<String, SemanticClass>();
        HashMap<String, SemanticProperty> hmprops = new HashMap<String, SemanticProperty>();
        Iterator<ItemAwareReference> it = foi.listHeraquicalItemAwareReference().iterator();
        while (it.hasNext()) {
            ItemAwareReference item = it.next();
            SWBClass obj = item.getProcessObject();

            if (obj != null) {
                SemanticClass cls = obj.getSemanticObject().getSemanticClass();

                hmclass.put(item.getItemAware().getName(), cls);
                Iterator<SemanticProperty> itp = cls.listProperties();
                while (itp.hasNext()) {
                    SemanticProperty prop = itp.next();
                    String name = item.getItemAware().getName() + "|" + prop.getPropId();
                    if (cls.isSubClass(DataTypes.sclass) && !userDefinedVars.containsKey(name)) {
                        userDefinedVars.put(name, item.getItemAware());
                    }
                    hmprops.put(name, prop);
                }
            }
        }

        if (ADM_MODESIMPLE.equals(adminMode)) {
            SWBResourceURL urlact = paramRequest.getActionUrl();
            urlact.setAction(ACT_PROCESS);
            out.println(SWBForms.DOJO_REQUIRED);
            
            boolean showHeader = base.getAttribute("showHeader", "").equals("use")?true:false;
            out.println("<script type=\"text/javascript\">function validateForm" + foi.getId() + "(form) {var frm = dijit.byId(form); if (frm.isValid()) {return true;} else {alert('"+paramRequest.getLocaleString("cancel")+"'); return false;}}</script>");
            //--haxdai14032014: Added as workaround for dojo uploader
            out.println("<script type=\"text/javascript\">function sendProcessForm" + foi.getId() + "(_action) {"
                    + "var frm = dojo.byId('"+foi.getId()+"/form'); "
                    + "if (_action && _action !== '') {frm[_action].value=_action;} "
                    + "frm.submit();}"
                    + "</script>");
            //--haxdai14032014
            if (showHeader) {
                out.println("<h3>"+foi.getFlowNodeType().getTitle()+"</h3>");
            }
            out.println("<div class=\"panel panel-default\" id=\"processForm\">");
            out.println("<form name=\""+foi.getId()+"\" id=\"" + foi.getId() + "/form\" dojoType=\"dijit.form.Form\" action=\"" + urlact + "\" method=\"post\" onSubmit=\"return validateForm"+foi.getId()+"('"+foi.getId()+"/form');\">");
            out.println("<input type=\"hidden\" name=\"suri\" value=\"" + suri + "\"/>");
            out.println("<input type=\"hidden\" name=\"smode\" value=\"edit\"/>");
            out.println("<div class=\"panel-body\">");
            boolean printHeaders = false;
            int max = 1;
            if (!base.getAttribute("prop" + max, "").equals("")) {
                while (!base.getAttribute("prop" + max, "").equals("")) {
                    String val = base.getAttribute("prop" + max);
                    HashMap<String, String> propsMap = getPropertiesMap(val);
                    SemanticProperty semprop = hmprops.get(propsMap.get("varName") + "|" + propsMap.get("propId"));

                    String strMode = "";
                    SemanticClass semclass = hmclass.get(propsMap.get("varName"));

                    if (semclass != null && semprop != null) {
//                        boolean canUserView = false;
//                        
//                        String roleList = propsMap.get("roles");
//                        if (roleList == null || roleList.trim().equals("")) {
//                            canUserView = true;
//                        } else {
//                            canUserView = false;
//                            StringTokenizer stk = new StringTokenizer(roleList, ":");
//                            while(stk.hasMoreTokens()) {
//                                String rId = stk.nextToken();
//                                Role role = site.getUserRepository().getRole(rId);
//                                if (user.hasRole(role)) {
//                                    canUserView = true;
//                                    break;
//                                }
//                            }
//                        }
//                        
//                        if (canUserView) {
                            if (FE_MODE_VIEW.equals(propsMap.get("mode"))) {
                                mgr.addProperty(semprop, propsMap.get("varName"), SWBFormMgr.MODE_VIEW);
                                strMode = SWBFormMgr.MODE_VIEW;
                            } else if (FE_MODE_EDIT.equals(propsMap.get("mode"))) {
                                mgr.addProperty(semprop, propsMap.get("varName"), SWBFormMgr.MODE_EDIT);
                                strMode = SWBFormMgr.MODE_VIEW;
                            }

                            SemanticObject sofe = ont.getSemanticObject(propsMap.get("fe"));
                            //String roles = propsMap.get("roles");
                            SWBProcessFormMgr fmgr = new SWBProcessFormMgr(foi);
                            FormElement ele = fmgr.getFormElement(semprop, propsMap.get("varName"));
                            if (!printHeaders) {
                                //out.println("<fieldset>");
        //                        out.println("<legend>Datos Generales</legend>");
                                out.println("<table>");
                                printHeaders = true;
                            }
                            
                            if (propsMap.get("label") == null) {
                                if (userDefinedVars.containsKey(propsMap.get("varName") + "|" + propsMap.get("propId"))) {
                                    propsMap.put("label", userDefinedVars.get(propsMap.get("varName") + "|" + propsMap.get("propId")).getDisplayTitle(lang));
                                } else {
                                    propsMap.put("label", semprop.getDisplayName(lang));
                                }
                            }
                            if (ele == null) {
                                //out.println("<tr><td width=\"200px\" align=\"right\"><label for=\"title\">" + fmgr.renderLabel(request, semprop, propsMap.get("varName"), propsMap.get("mode")) + "</label></td>");
                                out.println("<tr><td width=\"200px\" align=\"right\">" + fmgr.renderLabel(request, semprop, propsMap.get("varName"), propsMap.get("mode")) + "</td>");
                            } else {
                                //ele.setLabel(propsMap.get("label"));
                                //out.println("<tr><td width=\"200px\" align=\"right\"><label for=\"title\">" + ele.renderLabel(request, null, semprop, propsMap.get("varName") + "." + semprop.getName(), SWBFormMgr.TYPE_DOJO, SWBFormMgr.MODE_CREATE, lang, propsMap.get("label")) + "</label></td>");
                                out.println("<tr><td width=\"200px\" align=\"right\">" + ele.renderLabel(request, null, semprop, propsMap.get("varName") + "." + semprop.getName(), SWBFormMgr.TYPE_DOJO, SWBFormMgr.MODE_CREATE, lang, propsMap.get("label")) + "</td>");
                            }
                            out.println("<td>");
                            if (null != sofe) {
                                FormElement frme = (FormElement) sofe.createGenericInstance();
                                out.println(fmgr.renderElement(request, propsMap.get("varName"), semprop, frme, propsMap.get("mode")));
                            } else {
                                out.println(fmgr.renderElement(request, propsMap.get("varName"), semprop, propsMap.get("mode")));
                            }
                            out.println("</td></tr>");
//                        }
                    }
                    max++;
                }

                if (printHeaders) {
                    out.println("    </table>");
                    //out.println("</fieldset>");
                }
            }

            boolean btnSave = base.getAttribute("btnSave", "").equals("use")?true:false;
            boolean btnAccept = base.getAttribute("btnAccept", "").equals("use")?true:false;
            boolean btnReject = base.getAttribute("btnReject", "").equals("use")?true:false;
            boolean btnCancel = base.getAttribute("btnCancel", "").equals("use")?true:false;
            
            out.println("</div>");
            if (btnSave || btnAccept || btnReject || btnCancel) {
                out.println("<div class=\"panel-footer\">");
            }
            //--haxdai14032014: Changed submit to onClick events and actions as hidden parameters as a workaround for dojo uploader
            if (btnSave) {
                out.println("<button dojoType=\"dijit.form.Button\" type=\"submit\" onclick=\"dojo.byId('"+foi.getId()+"/form').submit();\">"+base.getAttribute("btnSaveLabel",paramRequest.getLocaleString("btnSaveTask"))+"</button>");
            }
            if (btnAccept) {
                out.println("<input type=\"hidden\" name=\"accept\" />");
                out.println("<button dojoType=\"dijit.form.Button\" type=\"submit\" onclick=\"sendProcessForm"+foi.getId()+"('accept');\">"+base.getAttribute("btnAcceptLabel",paramRequest.getLocaleString("btnCloseTask"))+"</button>");
            }
            if (btnReject) {
                out.println("<input type=\"hidden\" name=\"reject\" />");
                out.println("<button dojoType=\"dijit.form.Button\" type=\"submit\" onclick=\"sendProcessForm"+foi.getId()+"('reject');\">"+base.getAttribute("btnRejectLabel",paramRequest.getLocaleString("btnRejectTask"))+"</button>");
            }
            if (btnCancel) {
                out.println("<input type=\"hidden\" name=\"cancel\" />");
                out.println("<button dojoType=\"dijit.form.Button\" onclick=\"window.location='" + foi.getUserTaskInboxUrl() + "?suri=" + suri + "'\">"+base.getAttribute("btnCancelLabel",paramRequest.getLocaleString("btnBack"))+"</button>");
            }
            //--haxdai14032014
            if (btnSave || btnAccept || btnReject || btnCancel) {
                out.println("</div>");
            }
            out.println("</form>");
            out.println("</div>");
        } else if (ADM_MODEADVANCED.equals(adminMode)) {
            if (action.equals("add") || action.equals("edit")) {
                String basepath = SWBPortal.getWorkPath() + base.getWorkPath() + "/";
                String xml = SWBUtils.IO.getFileFromPath(basepath + "code.xml");
//                if(null==xml)  xml=base.getXmlConf();
                if (xml != null && xml.trim().length() > 0) {
                    SWBFormMgrLayer swbFormMgrLayer = new SWBFormMgrLayer(xml, paramRequest, request);
                    String html = swbFormMgrLayer.getHtml();
                    ret.append(html);
                }
            }
            out.println(ret.toString());
        }
    }
    
    public void doConfig(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String jsp = "/swbadmin/jsp/process/formsBuilder/config.jsp";
        Resource base = getResourceBase();
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        
        RequestDispatcher rd = request.getRequestDispatcher(jsp);
        try {
            request.setAttribute(ATT_PARAMREQUEST, paramRequest);
            request.setAttribute(ATT_RBASE, base);
            if (base.getResourceable() instanceof UserTask) {
                request.setAttribute(ATT_TASK, (UserTask)base.getResourceable());
            }
            rd.include(request, response);
        } catch (Exception ex) {
            log.error("FormsBuilderResource - Error including config.jsp", ex);
        }
    }
    
    public static String getFESelect(String FEsel, SWBParamRequest paramRequest, SemanticProperty sprop) throws SWBResourceException {
    
        User usr = paramRequest.getUser();
        SemanticOntology ont = SWBPlatform.getSemanticMgr().getSchema();
        SemanticVocabulary sv = SWBPlatform.getSemanticMgr().getVocabulary();
        StringBuilder ret = new StringBuilder();
        ret.append("\n<optgroup label=\"").append(paramRequest.getLocaleString("lblGenericFE")).append("\">");
        ret.append("\n<option value=\"generico\" selected >GenericFormElement</option>");
        ret.append("\n</optgroup>");

        HashMap<String, SemanticClass> hmscfe = new HashMap<String, SemanticClass>();
        HashMap<String, SemanticObject> hmso = null;

        //Property prop=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(SemanticVocabulary.SWB_ANNOT_FORMELEMENTRANGE).getRDFProperty();
        SemanticProperty pro = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(SemanticVocabulary.SWB_ANNOT_FORMELEMENTRANGE);
        //System.out.println("prop:" + pro.getName());


        Iterator<SemanticClass> itsub = sv.getSemanticClass(SemanticVocabulary.SWB_SWBFORMELEMENT).listSubClasses();
        while (itsub.hasNext()) {
            SemanticClass scobj = itsub.next();

            if (pro == null || sprop == null) {
                continue;
            }
            NodeIterator it = scobj.getOntClass().listPropertyValues(pro.getRDFProperty());

            while (it.hasNext()) {
                RDFNode node = it.next();

                //System.out.println("node:" + node+" "+sprop.getRange());            
                if (node != null) {
                    if (sprop.getRange().getURI().equals(node.asResource().getURI())) {
                        hmscfe.put(scobj.getDisplayName(usr.getLanguage()), scobj);
                    } else if (sprop.getRangeClass() != null && node.isResource()) {
                        SemanticClass cls = sv.getSemanticClass(node.asResource().getURI());
                        if (cls != null) {
                            if (sprop.getRangeClass().isSubClass(cls)) {
                                hmscfe.put(scobj.getDisplayName(usr.getLanguage()), scobj);
                            }
                        }
                    }
                }
            }
        }

        ArrayList list = new ArrayList(hmscfe.keySet());
        Collections.sort(list);

        Iterator<String> itsc = list.iterator();
        while (itsc.hasNext()) {

            String key = itsc.next();
            SemanticClass scfe = hmscfe.get(key);

            ret.append("\n<optgroup label=\"");
            ret.append(scfe.getDisplayName(paramRequest.getUser().getLanguage()));
            ret.append("\">");
            hmso = new HashMap<String, SemanticObject>();

            Iterator<SemanticObject> itsco = ont.listInstancesOfClass(scfe);
            while (itsco.hasNext()) {
                SemanticObject semObj = itsco.next();
                hmso.put(semObj.getDisplayName(usr.getLanguage()), semObj);
            }

            list = new ArrayList(hmso.keySet());
            Collections.sort(list);

            Iterator<String> itsoo = list.iterator();
            while (itsoo.hasNext()) {
                key = itsoo.next();
                SemanticObject sofe = hmso.get(key);
                ret.append("\n<option value=\"");
                ret.append(sofe.getURI());
                ret.append("\"");
                String stmp = FEsel + "edit|" + sofe.getURI();
                String stmp2 = FEsel + "view|" + sofe.getURI();
                String data = paramRequest.getResourceBase().getData(paramRequest.getWebPage());
                if (FEsel != null && !FEsel.equals("") && (data != null && (data.indexOf(stmp) > -1 || data.indexOf(stmp2) > -1) || FEsel.equals(sofe.getURI()))) {
                    ret.append(" selected ");
                }
                ret.append(">");
                ret.append(sofe.getDisplayName(paramRequest.getUser().getLanguage()));
                ret.append("\n</option>");
            }
            ret.append("\n</optgroup>");
        }
        return ret.toString();
    }
    
    public static HashMap<String, String> getPropertiesMap(String str) {
        HashMap<String, String> ret = new HashMap<String, String>();
        
        StringTokenizer stoken = new StringTokenizer(str, "|");
        if (stoken.hasMoreTokens()) {
            ret.put("varName", stoken.nextToken());
        }
        if (stoken.hasMoreTokens()) {
            ret.put("propId", stoken.nextToken());
        }
        if (stoken.hasMoreTokens()) {
            ret.put("mode", stoken.nextToken());
        }
        if (stoken.hasMoreTokens()) {
            ret.put("fe", stoken.nextToken());
        }
        if (stoken.hasMoreTokens()) {
            ret.put("label", stoken.nextToken());
        }
        if (stoken.hasMoreTokens()) {
            ret.put("roles", stoken.nextToken());
        }
        return ret;
    }
    
    public String getFormHTML(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        StringBuilder ret = new StringBuilder();
        Resource base = getResourceBase();

        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        UserTask ut = (UserTask) base.getResourceable();

        HashMap<String, SemanticClass> hmclass = new HashMap<String, SemanticClass>();
        HashMap<String, SemanticProperty> hmprops = new HashMap<String, SemanticProperty>();

        Iterator<ItemAware> it = ut.listHerarquicalRelatedItemAwarePlusNullOutputs().iterator();
        while (it.hasNext()) {
            ItemAware item = it.next();

            SemanticClass cls = item.getItemSemanticClass();
            if (cls != null) {
                hmclass.put(item.getName(), cls);

                Iterator<SemanticProperty> itp = cls.listProperties();
                while (itp.hasNext()) {
                    SemanticProperty prop = itp.next();
                    hmprops.put(prop.getPropId(), prop);
                }
            }

            //TODO: Agregar propiedades del item
        }

        ret.append("<div id=\"processForm\">");
        ret.append("\n<form");

        if (request.getParameter("useDojo") != null && request.getParameter("useDojo").equals("dojo")) {
            ret.append(" htmlType=\"dojo\" ");
        }
        ret.append(" class=\"swbform\">");

        ret.append("\n    <fieldset>");
//        ret.append("\n      <legend>Datos Generales</legend>");
        ret.append("\n      <table>");

        int max = 1;
        while (!base.getAttribute("prop" + max, "").equals("")) {

            String val = base.getAttribute("prop" + max);
            HashMap<String, String> props = getPropertiesMap(val);
            String varName = props.get("varName");
            String propid = props.get("propId");
            String modo = props.get("mode");
            String fe = props.get("fe");
            String label = props.get("label");

            SemanticProperty semprop = hmprops.get(propid);
            String strMode = "";
            SemanticClass semclass = hmclass.get(varName);

            if (semclass != null && semprop != null) {
                if (modo.equals(FE_MODE_VIEW)) {
                    strMode = SWBFormMgr.MODE_VIEW;
                } else if (modo.equals(FE_MODE_EDIT)) {
                    strMode = SWBFormMgr.MODE_EDIT;
                }
                SemanticObject sofe = ont.getSemanticObject(fe);
                String strFE = "";

                if (null != sofe) {
                    strFE = "formElement=\"" + sofe.getDisplayName(response.getUser().getLanguage()) + "\"";
                }

                ret.append("\n     <tr>");
                ret.append("\n       <td width=\"200px\" align=\"right\"><label name=\"" + varName + "." + semprop.getName() + "\" /></td>");
                ret.append("\n       <td><property name=\"" + varName + "." + semprop.getName() + "\" " + strFE + " mode=\"" + strMode + "\" /></td>");
                ret.append("\n    </tr>");
            }
            max++;
        }

        ret.append("\n      </table> ");
        ret.append("\n      </fieldset> ");
        ret.append("\n      <fieldset>");

        // validar que botones seleccionaron
        if (base.getAttribute("btnSave") != null) {
            ret.append("\n          <Button type=\"savebtn\"/>");
        }
        if (base.getAttribute("btnAccept") != null) {
            ret.append("\n          <Button type=\"submit\" name=\"accept\" title=\"").append(response.getLocaleString("btnCloseTask")).append("\" isBusyButton=\"true\" />");
        }
        if (base.getAttribute("btnReject") != null) {
            ret.append("\n          <Button isBusyButton=\"true\" name=\"reject\" title=\"").append(response.getLocaleString("btnRejectTask")).append("\" type=\"submit\" />");
        }
        if (base.getAttribute("btnCancel") != null) {
            ret.append("\n          <Button type=\"cancelbtn\"/>");
        }

        ret.append("\n      </fieldset>");
        ret.append("\n</form>");
        ret.append("\n</div>");

        return ret.toString();
    }
    
    public void doSign(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        Resource base = getResourceBase();
        User user = paramRequest.getUser();
        
        String suri = request.getParameter("suri");
        SWBResourceURL viewUrl = paramRequest.getRenderUrl().setMode(SWBParamRequest.Mode_VIEW).setParameter("suri", suri);
        
        if (suri == null) {
            out.println("<script type=\"text/javascript\">"
                    + " alert('"+paramRequest.getLocaleString("msgNoInstance")+"');"
                    + "window.location='" + viewUrl + "';"
                    + "</script>");
            return;
        }
        
        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        FlowNodeInstance foi = (FlowNodeInstance) ont.getGenericObject(suri);
        
        boolean canSign=false;
        if(user.getExternalID()!=null)
        {
            Iterator<SemanticObject>it = foi.getProcessSite().getSemanticModel().listSubjects(X509Certificate.swp_X509Serial, user.getExternalID()); //TODO: Modificar Búsqueda
            if (it.hasNext()){        
                canSign=true;
            }        
        }
        if(!canSign)
        {
            out.println("<script type=\"text/javascript\">"
                    + " alert('"+paramRequest.getLocaleString("msgNoCert")+"');"
                    + "window.location='" + viewUrl + "';"
                    + "</script>");
            return;
        }
        if(request.getParameter("err")!=null)
        {
            out.println("<script type=\"text/javascript\">alert('"+paramRequest.getLocaleString("msgNoSign")+"'); window.location='" + viewUrl + "';</script>");
        }

        String SigCad = getStringToSign(foi); 
        String signTemp=null;
        try {
            signTemp=SWBUtils.CryptoWrapper.comparablePassword(SigCad);
        } catch (GeneralSecurityException gse){
            log.error(gse);
        }
        
        User asigned = foi.getAssignedto();
        if (asigned != null && !asigned.equals(user)) {
            out.println("<script type=\"text/javascript\">"
                    + " alert('"+paramRequest.getLocaleString("msgAssigned")+"');"
                    + "window.location='" + viewUrl + "';"
                    + "</script>");
            return;
        }

        if (foi.getStatus() == Instance.STATUS_CLOSED || foi.getStatus() == Instance.STATUS_ABORTED || foi.getStatus() == Instance.STATUS_STOPED) {
            out.println("<script type=\"text/javascript\">"
                    + " alert('"+paramRequest.getLocaleString("msgClosed")+"');"
                    + "window.location='" + viewUrl + "';"
                    + "</script>");
            return;
        }

        SWBResourceURL urlact = paramRequest.getActionUrl();
        urlact.setAction(ACT_PROCESSSIGN);
       
        out.println(SWBForms.DOJO_REQUIRED);
        
        out.println("<script type=\"text/javascript\">function validateForm" + foi.getId() + "(form) {if (form.validate()) {return true;} else {alert('"+paramRequest.getLocaleString("msgInvalidData")+"'); return false;}}");
        out.println("function setSignature(data){ dataElement = document.getElementById('hiddenSign'); dataElement.value=data; form = document.getElementById('" + foi.getId() + "/form'); alert('"+paramRequest.getLocaleString("msgSignedForm")+"');form.submit();}</script>");
        out.println("<div id=\"processForm\">");
        out.println("<form id=\"" + foi.getId() + "/form\" dojoType=\"dijit.form.Form\" class=\"swbform\" action=\"" + urlact + "\" method=\"post\" onSubmit=\"return validateForm" + foi.getId() + "(this);\">");
        out.println("<input type=\"hidden\" name=\"suri\" value=\"" + suri + "\"/>");
        //out.println("<input type=\"hidden\" name=\"smode\" value=\"edit\"/>");
        out.println("<input type=\"hidden\" id=\"hiddenSign\" name=\"hiddenSign\" value=\"\"/>");
        out.println("<fieldset>");
        out.println("<table>");
        
        out.println("<tr>");
        out.println("<td width=\"200px\" align=\"right\"><label>"+paramRequest.getLocaleString("signForm")+":</label></td>");
        out.println("<td>");
        out.println("<applet code=\"signatureapplet.SignatureApplet.class\" codebase=\"/swbadmin/lib\" archive=\""
                    + SWBPlatform.getContextPath()
                    + "/swbadmin/lib/SWBAplDigitalSignature.jar\" width=\"600\" height=\"250\">");
        out.println("<param name=\"message\" value=\"" + signTemp + "\">");
        out.println("</applet>");
        out.println("</td></tr>");
        out.println("    </table>");
        out.println("</fieldset>");
        out.println("<fieldset><span align=\"center\">");
       // out.println("<button dojoType=\"dijit.form.Button\" name=\"btn_signed\" type=\"submit\">Concluir Tarea</button>");
        if (base.getAttribute("btnCancel", "").equals("use")) {
            out.println("<button dojoType=\"dijit.form.Button\" onclick=\"window.location='" + foi.getUserTaskInboxUrl() + "?suri=" + suri + "'\">"+paramRequest.getLocaleString("cancel")+"</button>");
        }
        out.println("</span></fieldset>");
        out.println("</form>");
        out.println("</div>");
    }
    
    public void doAcuse(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        
        String suri = request.getParameter("suri");
        if (suri == null) {
            out.println(paramRequest.getLocaleString("msgNoInstance"));
            return;
        }
        
        String x509 = request.getParameter("sg");
        if (x509 == null) {
            out.println(paramRequest.getLocaleString("msgNoInstance"));
            return;
        }

        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        FlowNodeInstance foi = (FlowNodeInstance) ont.getGenericObject(suri);
        X509SingInstance x509SingInstance = (X509SingInstance) ont.getGenericObject(x509);
        
        if (null==x509SingInstance){
            out.println(paramRequest.getLocaleString("msgNoCert"));
            return;
        }
        
        out.println(SWBForms.DOJO_REQUIRED);
        
        out.println("<div id=\"processForm\" class=\"swbform\">");
        out.println("<h2>"+paramRequest.getLocaleString("signRecpt")+"</h2>");
        out.println("<fieldset>");
        out.println("<table>");
        
        out.println("<tr>");
        out.println("<td width=\"200px\" align=\"right\"><label>"+paramRequest.getLocaleString("signValue")+":</label></td>");
        out.println("<td><div id=\"code\">");
        out.println(htmlwrap(x509SingInstance.getOriginalString(), 50));
        out.println("</div></td></tr>");
        
        out.println("<td width=\"200px\" align=\"right\"><label>"+paramRequest.getLocaleString("signCode")+":</label></td>");
        out.println("<td><div id=\"code\">");
        out.println(htmlwrap(x509SingInstance.getSignedString(), 50));
        out.println("<div></td></tr>");
        
        out.println("<td width=\"200px\" align=\"right\"><label>"+paramRequest.getLocaleString("signPInstance")+":</label></td>");
        out.println("<td>");
        out.println(x509SingInstance.getFlowNodeInstance().getProcessInstance().getProcessType().getDisplayTitle(paramRequest.getUser().getLanguage()));
        out.println("</td></tr>");
        
        out.println("<td width=\"200px\" align=\"right\"><label>"+paramRequest.getLocaleString("signTInstance")+":</label></td>");
        out.println("<td>");
        out.println(x509SingInstance.getFlowNodeInstance().getFlowNodeType().getDisplayTitle(paramRequest.getUser().getLanguage()));
        out.println("</td></tr>");

        out.println("<td width=\"200px\" align=\"right\"><label>"+paramRequest.getLocaleString("signUser")+":</label></td>");
        out.println("<td>");
        out.println(x509SingInstance.getCertificate().getName());
        out.println("</td></tr>");
        
        out.println("<td width=\"200px\" align=\"right\"><label>"+paramRequest.getLocaleString("signId")+":</label></td>");
        out.println("<td>");
        out.println(x509SingInstance.getCertificate().getSerial());
        out.println("</td></tr>");
        
        out.println("<td width=\"200px\" align=\"right\"><label>"+paramRequest.getLocaleString("signDate")+":</label></td>");
        out.println("<td>");
        out.println(x509SingInstance.getCreated());
        out.println("</td></tr>");
        
        out.println("    </table>");
        out.println("</fieldset>");
        out.println("<fieldset><span align=\"center\">");
       // out.println("<button dojoType=\"dijit.form.Button\" name=\"btn_signed\" type=\"submit\">Concluir Tarea</button>");
        
        out.println("<button dojoType=\"dijit.form.Button\" onclick=\"window.location='" + foi.getUserTaskInboxUrl() + "?suri=" + suri + "'\">"+paramRequest.getLocaleString("signExit")+"</button>");
        
        out.println("</span></fieldset>");
        out.println("</div>");
    }
    
    private String getStringToSign(FlowNodeInstance flowNodeInstance) {

        Iterator<ItemAwareReference> it = flowNodeInstance.listHeraquicalItemAwareReference().iterator();
        ByteArrayOutputStream bout = new ByteArrayOutputStream();

        Model model = ModelFactory.createDefaultModel();
        while (it.hasNext()) {
            ItemAwareReference itemAwareReference = it.next();
            model.add(itemAwareReference.getProcessObject()
                    .getSemanticObject().getRDFResource().listProperties());
                    
        }
        // "RDF/XML", "RDF/XML-ABBREV", "N-TRIPLE" and "N3"
        model.write(bout, "N-TRIPLE");

        String ret = null;
        try {
        ret = bout.toString("UTF8");
        } catch (IOException ioe){
            log.error(ioe);
        }
        return ret;
    }
    
    public String htmlwrap(String orig, final int length){
        StringBuilder ret = new StringBuilder(512);
        ret.append("<pre>");
        int linea = 0;
        while(linea*length < orig.length()){
            int inicial=linea*length;
            int ifinal = ++linea * length;
            if (ifinal>orig.length()){
                ret.append(orig.substring(inicial));
            } else {
                ret.append(orig.substring(inicial,ifinal));
                ret.append("\n");
            }
        }
        ret.append("</pre>");
        return ret.toString();
    }
    
    public boolean isViewProperty(SWBParameters paramRequest, String varName, SemanticProperty prop, HashMap<String, String> hm) {
        boolean ret = false;
        String data = hm.get(varName + "|" + prop.getPropId());
        if (data != null && data.indexOf(varName + "|" + prop.getPropId() + "|view") > -1) {
            ret = true;
        }
        return ret;
    }

    public boolean isEditProperty(SWBParameters paramRequest, String varName, SemanticProperty prop, HashMap<String, String> hm) {
        boolean ret = false;
        String data = hm.get(varName + "|" + prop.getPropId());
        if (data != null && data.indexOf(varName + "|" + prop.getPropId() + "|edit") > -1) {
            ret = true;
        }
        return ret;
    }
}