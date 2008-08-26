/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.services;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.Template;
import org.semanticwb.model.User;
import org.semanticwb.model.ObjectGroup;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.TemplateRef;
import org.semanticwb.model.VersionInfo;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;

/**
 *
 * @author jorge.jimenez
 */
public class TemplateSrv {

    private static Logger log = SWBUtils.getLogger(SWBUtils.class);

    public Template createTemplate(WebSite website, String fileName, String content, HashMap attaches, String titulo, String description, ObjectGroup objectgroup, User user) throws SWBException {
        Template template = website.createTemplate();
        template.setTitle(titulo);
        template.setDescription(description);
        template.addGroup(objectgroup);
        template.setStatus(1);
        VersionInfo verInfo = website.createVersionInfo();
        verInfo.setValue("1");
        template.setActualVersion(verInfo);
        template.setLastVersion(verInfo);

        //TODO:Con que metodo le pongo el nombre del archivo al template, lo q antes hacía con:
        //setFilename(filename);
        //Ahora lo voy a poner con setProperty, si es así como creo un SemanticProperty

        //TODO: Grabar el archivo en ruta de fileSystem q se decida
        java.io.File dir = new File(template.getWorkPath());
        dir.mkdir();
        dir = new File(template.getWorkPath() + "/" + verInfo.getValue());
        dir.mkdir();
        dir = new File(template.getWorkPath() + "/" + verInfo.getValue() + "/images");
        dir.mkdir();
        try{
            java.io.OutputStream Os = new FileOutputStream(
            template.getWorkPath() + "/" + verInfo.getValue() + "/" + fileName);
            Os.write(content.getBytes());
            Os.flush();
            Os.close();
        }catch(Exception e){
            log.debug(e);
        }

        //TODO: Graba attaches
        Iterator itattaches=attaches.keySet().iterator();
        while(itattaches.hasNext()) {
            String attach=(String)itattaches.next();
            byte abyte[] = (byte[]) attaches.get(attach);
            try{
                java.io.OutputStream Os = new FileOutputStream(template.getWorkPath() + "/" + verInfo.getValue() + "/images/" + attach);
                Os.write(abyte,0,abyte.length);
                Os.flush();
                Os.close();
            }catch(Exception e){
                log.debug(e);
            }
        }


        SWBPortal.log(user.getURI(), "create", template.getURI(), template.getURI(), "create template", null); 

        return template;
    }

    public Template createTemplate(WebSite website, String id, String fileName, String content, HashMap attaches, String titulo, String description, ObjectGroup objectgroup, User user) throws SWBException {
        Template template = website.createTemplate(id);

        template.setTitle(titulo);
        template.setDescription(description);
        template.addGroup(objectgroup);
        template.setStatus(1);

        SWBPortal.log(user.getURI(), "create", website.getURI(), template.getId(), "create template", null); 

        return template;
    }

    public boolean removeTemplate(WebSite website, String id, User user) throws SWBException {
        boolean deleted = false;
        website.removeTemplate(id);
        deleted = true;

        //Elimina todas las referencias q pueden haber hacía el templete
        Iterator<WebSite> itWebSites = SWBContext.listWebSites();
        while (itWebSites.hasNext()) {
            WebSite webSite = itWebSites.next();
            Iterator<WebPage> itWebPages = webSite.listWebPages();
            while (itWebPages.hasNext()) {
                WebPage webPage = itWebPages.next();
                GenericIterator<TemplateRef> itTemplateRefs = webPage.listTemplateRef();
                while (itTemplateRefs.hasNext()) {
                    TemplateRef tplRef = itTemplateRefs.next();
                    String data = tplRef.getTemplate().getId();
                    if (data != null && data.trim().length() > 0) {
                        if (id.equals(data)) {
                            webPage.removeTemplateRef(tplRef);
                        }
                    }
                }
            }
        }

        //TODO:Elimina el template de FileSystem
        Template tpl=website.getTemplate(id);
        File f = new File(tpl.getWorkPath());
            if (f.exists() && f.isDirectory()) {
                try {
                    boolean flag = SWBUtils.IO.removeDirectory(tpl.getWorkPath());
                    if (flag) {
                        f.delete();
                    }
                } catch (Exception e) {
                    log.debug(e);
                }
            }
        
        SWBPortal.log(user.getURI(), "remove", website.getURI(), id, "remove template", null); 

        return deleted;
    }
    
    public boolean updateTemplate(Template template, String title, String description, String fileName, String content, HashMap attaches, User user)
    {
        VersionInfo verInfo=template.getLastVersion();
        int version=Integer.parseInt(verInfo.getValue())+1;
        verInfo.setValue(""+version);
        template.setStatus(1);
        template.setModifiedBy(user);
        template.setLastVersion(verInfo);
        template.setActualVersion(verInfo);
        template.setTitle(title);
        template.setDescription(description);
        //TODO: Revisar como poner el fileName
        
        String id = template.getId();
        String wsId = template.getWebSite().getId();
        java.io.File dir = new File(SWBPlatform.getWorkPath() +"/sites/"+wsId+ "/templates/" + id);
        dir.mkdir();
        dir = new File(SWBPlatform.getWorkPath() +"/sites/"+wsId+ "/templates/" + id+ "/" + version);
        dir.mkdir();
        dir = new File(SWBPlatform.getWorkPath() +"/sites/"+wsId+ "/templates/" + id+ "/" + version + "/images");
        dir.mkdir();
        try{
            java.io.OutputStream Os = new FileOutputStream(
            SWBPlatform.getWorkPath() +"/sites/"+wsId+ "/templates/" + id+ "/" + version + "/" + fileName);
            Os.write(content.getBytes());
            Os.flush();
            Os.close();
        }catch(Exception e){
            log.error(e);
        }

        //TODO: Graba attaches
        Iterator itattaches=attaches.keySet().iterator();
        while(itattaches.hasNext()) {
            String attach=(String)itattaches.next();
            byte abyte[] = (byte[]) attaches.get(attach);
            try{
                java.io.OutputStream Os = new FileOutputStream(SWBPlatform.getWorkPath() +"/sites/"+wsId+ "/templates/" + id + "/" + version + "/images/" + attach);
                Os.write(abyte,0,abyte.length);
                Os.flush();
                Os.close();
            }catch(Exception e){
                log.error(e);
            }
        }
       return true;
    }
    

    public static boolean resetTemplates(WebSite website, Template template, User user) {
        try {
            String rutawork = (String) SWBPlatform.getWorkPath();
            File dir = new File(rutawork + "/sites/" + website.getId() + "/templates/" + template.getId());
            if (dir != null && dir.exists() && dir.isDirectory()) {
                File listado[] = dir.listFiles();
                for (int i = 0; i < listado.length; i++) {
                    if (listado[i].isDirectory() && !listado[i].getName().equals(String.valueOf(template.getActualVersion().getValue()))) {
                        boolean flag = SWBUtils.IO.removeDirectory(listado[i].getPath());
                        if (flag) {
                            listado[i].delete();
                        }
                    }
                }
                boolean b = true;
                String lastV = template.getActualVersion().getValue();
                if (!lastV.equals("1")) {
                    File ActualVDir = new File(rutawork + "/sites/" + website.getId()+ "/templates/" + template.getId() + "/" + lastV);
                    File f = new File(rutawork + "/sites/" + website.getId() + "/templates/" + template.getId() + "/1");
                    f.mkdir();
                    String sSource = "sites/" + website.getId() + "/templates/" + template.getId() + "/" + lastV;
                    String sTarget = "sites/" + website.getId() + "/templates/" + template.getId() + "/1";
                    b = SWBUtils.IO.copyStructure(ActualVDir.getPath() + "/", f.getPath() + "/", true, sSource, sTarget);
                }
                if (b) {
                    VersionInfo verInfo = website.createVersionInfo();
                    verInfo.setValue("1");
                    template.setActualVersion(verInfo);
                    template.setLastVersion(verInfo);

                   SWBPortal.log(user.getURI(), "update", website.getURI(), template.getId(), "update template version", null); 

                    return true;
                }
            }
        } catch (Exception e) {
            log.error("Error while removing template version width id:" + template.getId() + "-TemplateSrv:RemoveTemplateVersion", e);
        }
        return false;
    }

    public boolean removeTemplateGroup(WebSite webSite, String id, User user) throws SWBException {
        boolean doAction = false;
        ObjectGroup obj=webSite.getObjectGroup(id);
        iteraRemoveGroup(obj,user);
        doAction = true;
        
        obj.removeAllGroup();
        webSite.removeObjectGroup(obj.getId());
        
        SWBPortal.log(user.getURI(), "remove", webSite.getURI(), id, "remove template group", null); 
        
        return doAction;
    }
    
    private void iteraRemoveGroup(ObjectGroup objGroup, User user) throws SWBException
    {
        Iterator<Template> itTemplates = objGroup.getWebSite().listTemplates();
        while (itTemplates.hasNext()) {
            Template template = itTemplates.next();
            if (template.getGroup().getId().equals(objGroup.getId())) {
                //Elimina el template
                removeTemplate(objGroup.getWebSite(), template.getId(), user);
            }
        }
        Iterator<ObjectGroup> itGroups=objGroup.listGroup();
        while(itGroups.hasNext()){
            ObjectGroup objGroup2=itGroups.next();
            iteraRemoveGroup(objGroup2, user);
        }
    }
    
}
