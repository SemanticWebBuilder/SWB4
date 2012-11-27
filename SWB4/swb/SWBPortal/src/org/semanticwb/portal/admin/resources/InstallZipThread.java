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
package org.semanticwb.portal.admin.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.util.SWBMailSender;
import org.semanticwb.model.ResourceType;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.UserRepository;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.portal.api.SWBResource;
import org.semanticwb.repository.Workspace;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author jorge.jimenez
 */
public class InstallZipThread extends java.lang.Thread {

    /** The log. */
    private static Logger log = SWBUtils.getLogger(InstallZipThread.class);
    File zipFile = null;
    String file2read = null;
    String newWebSiteid = null;
    String newWebSiteTitle = null;
    WebSite createdWebSte = null;
    String wsiteUri = null;
    int istatus = 0;

    public InstallZipThread(File zipFile, String file2read, String newWebSiteid, String newWebSiteTitle) {
        this.zipFile = zipFile;
        this.file2read = file2read;
        this.newWebSiteid = newWebSiteid;
        this.newWebSiteTitle = newWebSiteTitle;
        createdWebSte = null;
        wsiteUri = null;
        istatus = 0;
    }

    @Override
    public void run() {
        try {
            String modelspath = SWBPortal.getWorkPath() + "/models/";
            if (file2read == null) {
                file2read = "siteInfo.xml";
            }
            String siteInfo = SWBUtils.IO.readFileFromZipAsString(zipFile.getAbsolutePath(), file2read);
            String oldIDModel = null, oldNamespace = null, oldTitle = null, oldDescription = null;
            Document dom = SWBUtils.XML.xmlToDom(siteInfo);
            Node nodeModel = dom.getFirstChild();
            if (nodeModel.getNodeName().equals("model")) {
                HashMap smodels = new HashMap();
                NodeList nlChilds = nodeModel.getChildNodes();
                for (int i = 0; i < nlChilds.getLength(); i++) {
                    Node node = nlChilds.item(i);
                    if (node.getNodeName().equals("id")) {
                        oldIDModel = node.getFirstChild().getNodeValue();
                    }
                    if (node.getNodeName().equals("namespace")) {
                        oldNamespace = node.getFirstChild().getNodeValue();
                    }
                    if (node.getNodeName().equals("title")) {
                        oldTitle = node.getFirstChild().getNodeValue();
                    }
                    if (node.getNodeName().equals("description")) {
                        oldDescription = node.getFirstChild().getNodeValue();
                    }
                    if (node.getNodeName().equals("model")) { //Tiene submodelos - un submodelo puede inclusive tener submodelos, esto tiene que ser iterativo
                        iteraModels(node, smodels);
                    }
                }
                
                String newId = newWebSiteid;
                if (newId == null) {
                    newId = oldIDModel;
                }
                String newTitle = newWebSiteTitle;
                if (newTitle == null) {
                    newTitle = oldTitle;
                    newWebSiteTitle = oldTitle;
                }
                java.io.File extractTo = new File(modelspath + newId);
                //Descomprimir zip
                org.semanticwb.SWBUtils.IO.unzip(zipFile, extractTo);
                //Mover directorios de modelos a directorio work leyendo rdfs
                File[] fieldsUnziped = extractTo.listFiles();
                for (int i = 0; i < fieldsUnziped.length; i++) {
                    File file = fieldsUnziped[i];
                    if (file.isDirectory()) { //
                        if (file.getName().equals(oldIDModel)) { //Es la carpeta del modelo principal a cargar
                            SWBUtils.IO.copyStructure(file.getAbsolutePath() + "/", extractTo.getAbsolutePath() + "/");
                            SWBUtils.IO.removeDirectory(file.getAbsolutePath());
                        } else {
                            if (file.getName().endsWith("_usr") || file.getName().endsWith("_rep")) {
                                //las carpetas de los submodelos, predefinidos en wb
                                String wbmodelType = "";
                                if (file.getName().endsWith("_usr")) {
                                    wbmodelType = "_usr";
                                }
                                if (file.getName().endsWith("_rep")) {
                                    wbmodelType = "_rep";
                                }

                                SWBUtils.IO.copyStructure(file.getAbsolutePath(), extractTo.getAbsolutePath() + wbmodelType + "/");
                                SWBUtils.IO.removeDirectory(file.getAbsolutePath());
                            } else { //Puede ser un submodelo tipo sitio
                                //TODO
                            }
                        }
                    } else { //TODO:Archivos rdf(modelos) y xml (siteinfo) y readme, eliminarlos
                        String fileName = file.getName();
                        if (fileName.equalsIgnoreCase(file2read) || fileName.equals("readme.txt")) { //Archivo siteinfo
                            file.delete();
                        }
                    }
                }
                
                //Parseo de nombre de NameSpace anteriores por nuevos
                String newNs = "http://www." + newId + ".swb#";
                File fileModel = new File(modelspath + newId + "/" + oldIDModel + ".nt");
                FileInputStream frdfio = new FileInputStream(fileModel);
                InputStream io=frdfio;
                if(!oldNamespace.equals(newNs))
                {
                    String rdfcontent = SWBUtils.IO.readInputStream(frdfio);
                    fileModel.delete();

                    rdfcontent = SWBUtils.TEXT.replaceAll(rdfcontent, oldNamespace, newNs); //Reemplazar namespace anterior x nuevo
                    rdfcontent = SWBUtils.TEXT.replaceAll(rdfcontent, newNs + oldIDModel, newNs + newId); //Reempplazar namespace y id anterior x nuevos

                    rdfcontent = SWBUtils.TEXT.replaceAll(rdfcontent, "<topicmap id=\\\"" + oldIDModel + "\\\">", "<topicmap id=\\\"" + newId + "\\\">"); // Rempalzar el tag: <topicmap id=\"[oldIDModel]\"> del xml de filtros de recursos
                    //Reemplaza ids de repositorios de usuarios y documentos x nuevos
                    rdfcontent = SWBUtils.TEXT.replaceAll(rdfcontent, oldIDModel + "_usr", newId + "_usr");
                    rdfcontent = SWBUtils.TEXT.replaceAll(rdfcontent, "http://user." + oldIDModel + ".swb#", "http://user." + newId + ".swb#");
                    rdfcontent = SWBUtils.TEXT.replaceAll(rdfcontent, oldIDModel + "_rep", newId + "_rep");
                    rdfcontent = SWBUtils.TEXT.replaceAll(rdfcontent, "http://repository." + oldIDModel + ".swb#", "http://repository." + newId + ".swb#");

                    //rdfcontent = SWBUtils.TEXT.replaceAllIgnoreCase(rdfcontent, oldName, newName); //Reemplazar nombre anterior x nuevo nombre
                    //rdfcontent = parseRdfContent(rdfcontent, oldTitle, newTitle, oldIDModel, newId, newNs);

                    //Mediante inputStream creado generar sitio
                    io = SWBUtils.IO.getStreamFromString(rdfcontent);
                }
                istatus = 30;
                SemanticModel model = SWBPlatform.getSemanticMgr().createDBModelByRDF(newId, newNs, io, "N-TRIPLE");
                //System.out.println("model:"+model+" "+newId+" "+newNs+" "+io+" "+model.getName());
                WebSite website = SWBContext.getWebSite(model.getName());
                //System.out.println("Site:"+website);
                
                wsiteUri = website.getURI();
                website.setTitle(newTitle);
                website.setDescription(oldDescription);
                String xmodelID = null;
                Iterator smodelsKeys = smodels.keySet().iterator();
                while (smodelsKeys.hasNext()) { // Por c/submodelo que exista
                    String key = (String) smodelsKeys.next();
                    HashMap smodelValues = (HashMap) smodels.get(key);
                    Iterator itkVaues = smodelValues.keySet().iterator();
                    while (itkVaues.hasNext()) {
                        String kvalue = (String) itkVaues.next();
                        if (kvalue.equals("id")) {
                            xmodelID = (String) smodelValues.get(kvalue);
                        }
                    }
                    //Buscar rdf del submodelo
                    fileModel = new File(modelspath + newId + "/" + xmodelID + ".nt");
                    if (fileModel != null && fileModel.exists()) {
                        frdfio = new FileInputStream(fileModel);
                        String rdfmodel = SWBUtils.IO.readInputStream(frdfio);
                        if (key.endsWith("_usr")) { //Para los submodelos de usuarios
                            int pos = xmodelID.lastIndexOf("_usr");
                            if (pos > -1) {
                                xmodelID = xmodelID.substring(0, pos);
                                rdfmodel = SWBUtils.TEXT.replaceAll(rdfmodel, xmodelID, newId);
                                io = SWBUtils.IO.getStreamFromString(rdfmodel);
                                SemanticModel usermodel = SWBPlatform.getSemanticMgr().createDBModelByRDF(newId + "_usr", "http://user." + newId + ".swb#", io, "N-TRIPLE");
                                if (usermodel != null) {
                                    UserRepository userRep = SWBContext.getUserRepository(usermodel.getName());
                                    userRep.setTitle("Repositorio de Usuarios (" + newWebSiteTitle + ")", "es");
                                    userRep.setTitle("Users Repository (" + newWebSiteTitle + ")", "en");
                                }
                            }
                        }
                        if (key.endsWith("_rep")) { //Para los submodelos de dosumentos
                            int pos = xmodelID.lastIndexOf("_rep");
                            if (pos > -1) {
                                xmodelID = xmodelID.substring(0, pos);
                                rdfmodel = SWBUtils.TEXT.replaceAll(rdfmodel, xmodelID, newId);
                                io = SWBUtils.IO.getStreamFromString(rdfmodel);
                                SemanticModel repomodel = SWBPlatform.getSemanticMgr().createDBModelByRDF(newId + "_rep", "http://repository." + newId + ".swb#", io, "N-TRIPLE");
                                if (repomodel != null) {
                                    Workspace repo = SWBContext.getWorkspace(repomodel.getName());
                                    repo.setTitle("Repositorio de Documentos (" + newWebSiteTitle + ")", "es");
                                    repo.setTitle("Documents Repository (" + newWebSiteTitle + ")", "en");
                                }
                            }
                        }
                        fileModel.delete();
                    }
                }
                istatus = 80;
                if (website != null) {
                    Iterator<ResourceType> it = website.listResourceTypes();
                    while (it.hasNext()) {
                        ResourceType resourceType = it.next();
                        if (resourceType != null) {
                            try {
                                SWBPortal.getResourceMgr().loadResourceTypeModel(resourceType);
                            } catch (Exception e) {
                                log.error("" + resourceType, e);
                            }
                        }
                    }
                    SWBPlatform.getSemanticMgr().loadBaseVocabulary();
                    it = website.listResourceTypes();
                    while (it.hasNext()) {
                        ResourceType resourceType = it.next();
                        if (resourceType != null) {
                            try {
                                //Runtime.getRuntime().loadLibrary(oldTitle)
                                Class cls = SWBPortal.getResourceMgr().createSWBResourceClass(resourceType.getResourceClassName());
                                if (cls != null) {
                                    SWBResource res = ((SWBResource) SWBPortal.getResourceMgr().convertOldWBResource(cls.newInstance()));
                                    if (res != null) {
                                        res.install(resourceType);
                                    }
                                }
                            } catch (Exception e) {
                                log.error("" + resourceType, e);
                            }
                        }
                    }
                }
                istatus = 100;
                //return website;
                createdWebSte = website;
            }
        } catch (Exception e) {
            log.error("Check if you have the necesary owl files in the owl directory", e);
        }
    }

    /**
     * Metodo sobrado en este momento, pero servira para cuando un submodelo (sitio), tenga mas submodelos (sitios,repositorios).
     * 
     * @param node the node
     * @param smodels the smodels
     */
    private static void iteraModels(Node node, HashMap smodels) {
        HashMap submodel = new HashMap();
        NodeList nlChildModels = node.getChildNodes();
        for (int j = 0; j < nlChildModels.getLength(); j++) {
            Node nodeSModel = nlChildModels.item(j);
            if (nodeSModel.getNodeName().equals("type")) { //Revisar si existe el submodel en la instancia a importar
                NodeList nlSite = node.getChildNodes();
                for (int k = 0; k < nlSite.getLength(); k++) {
                    if (nlSite.item(k).getNodeName().equals("id")) {
                        smodels.put(nlSite.item(k).getFirstChild().getNodeValue(), submodel);
                    }
                }
            } else if (nodeSModel.getNodeName().equals("id")) {
                submodel.put("id", nodeSModel.getFirstChild().getNodeValue());
            } else if (nodeSModel.getNodeName().equals("namespace")) {
                submodel.put("namespace", nodeSModel.getFirstChild().getNodeValue());
            } else if (nodeSModel.getNodeName().equals("title")) {
                submodel.put("title", nodeSModel.getFirstChild().getNodeValue());
            } else if (nodeSModel.getNodeName().equals("description")) {
                submodel.put("description", nodeSModel.getFirstChild().getNodeValue());
            }
        }
    }

    public String getWebSiteUri() {
        return wsiteUri;
    }

    public WebSite getcreatedWebSite() {
        return createdWebSte;
    }

    public int getStatus() {
        return istatus;
    }
}
