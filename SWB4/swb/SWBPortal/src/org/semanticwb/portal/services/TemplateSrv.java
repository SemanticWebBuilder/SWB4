/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.services;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.SWBInstance;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Template;
import org.semanticwb.model.User;
import org.semanticwb.model.ObjectGroup;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.TemplateRef;
import org.semanticwb.model.VersionInfo;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticIterator;
import org.semanticwb.portal.SWBDBAdmLog;

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
        String title = website.getTitle();
        //java.io.File dir = new File(WBUtils.getInstance().getWorkPath() +"/sites/"+title+ "/templates/" + RECID);
//        dir.mkdir();
//        dir = new File(WBUtils.getInstance().getWorkPath() + "/sites/"+title + "/templates/" + RECID + "/" + version);
//        dir.mkdir();
//        dir = new File(WBUtils.getInstance().getWorkPath() + "/sites/"+title+ "/templates/" + RECID + "/" + version + "/images");
//        dir.mkdir();
//        java.io.OutputStream Os = new FileOutputStream(
//        WBUtils.getInstance().getWorkPath()  + "/sites/"+topicmap + "/title/" + RECID + "/" + version + "/" + filename);
//        Os.write(content.getBytes());
//        Os.flush();
//        Os.close();

        //TODO: Graba attaches
        //Iterator itattaches=attaches.keySet().iterator();
//        while(itattaches.hasNext()) {
//            String attach=(String)itattaches.next();
//            byte abyte[] = (byte[]) attaches.get(attach);
//            Os = new FileOutputStream(WBUtils.getInstance().getWorkPath() + "/sites/"+title + "/templates/" + RECID + "/" + version + "/images/" + attach);
//            Os.write(abyte,0,abyte.length);
//            Os.flush();
//            Os.close();
//        }


        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "create", template.getName(), template.getURI(), "create Template", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error creating Template", e);
        }

        return template;
    }

    public Template createTemplate(WebSite website, String id, String fileName, String content, HashMap attaches, String titulo, String description, ObjectGroup objectgroup, User user) throws SWBException {
        Template template = website.createTemplate(id);

        template.setTitle(titulo);
        template.setDescription(description);
        template.addGroup(objectgroup);
        template.setStatus(1);

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "create", template.getName(), template.getURI(), "create Template", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error creating Template", e);
        }

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
                SemanticIterator<TemplateRef> itTemplateRefs = webPage.listTemplateRef();
                while (itTemplateRefs.hasNext()) {
                    TemplateRef tplRef = itTemplateRefs.next();
                    //TODO:Ver si me puede dar el puro id del template en un metodo para no comparar contra toda la Uri
                    String data = tplRef.getTemplate().getURI();
                    if (data != null && data.trim().length() > 0) {
                        if (id.equals(data)) {
                            webPage.removeTemplateRef(tplRef);
                        }
                    }
                }
            }
        }

        //TODO:Elimina el template de FileSystem
        String title = website.getTitle();
        String rutawork = (String) SWBInstance.getWorkPath();
        File f = new File(rutawork + "/sites/" + title + "/templates/");
        if (f.exists() && f.isDirectory()) {
            f = new File(rutawork + "/sites/" + title + "/templates/" + id);
            if (f.exists() && f.isDirectory()) {
                try {
                    String deldirectory = rutawork + "/sites/" + title + "/templates/" + id;
                    boolean flag = SWBUtils.IO.removeDirectory(deldirectory);
                    if (flag) {
                        f.delete();
                    }
                } catch (Exception e) {
                }
            }
        }


        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "remove", id, id, "remove Template", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error removing Template", e);
        }

        return deleted;
    }
    
    public boolean updateTemplate(Template template, String title, String description, String fileName, String content, HashMap attaches, User user)
    {
        VersionInfo verInfo=template.getLastVersion();
        int version=Integer.parseInt(verInfo.getValue())+1;
        verInfo.setValue(""+version);
        template.setStatus(1);
        template.setUserModified(user);
        template.setLastVersion(verInfo);
        template.setActualVersion(verInfo);
        template.setTitle(title);
        template.setDescription(description);
        //TODO: Revisar como poner el fileName
        
        //Ver si existira un metodo que me regrese el id del template, no todo el uri, esto para formar la ruta en filesystem
        //String id = template.getUri();
        //TODO: Grabar el archivo en ruta de fileSystem q se decida
        //String title = website.getTitle();
        //java.io.File dir = new File(WBUtils.getInstance().getWorkPath() +"/sites/"+title+ "/templates/" + RECID);
//        dir.mkdir();
//        dir = new File(WBUtils.getInstance().getWorkPath() + "/sites/"+title + "/templates/" + RECID + "/" + version);
//        dir.mkdir();
//        dir = new File(WBUtils.getInstance().getWorkPath() + "/sites/"+title+ "/templates/" + RECID + "/" + version + "/images");
//        dir.mkdir();
//        java.io.OutputStream Os = new FileOutputStream(
//        WBUtils.getInstance().getWorkPath()  + "/sites/"+topicmap + "/title/" + RECID + "/" + version + "/" + filename);
//        Os.write(content.getBytes());
//        Os.flush();
//        Os.close();

        //TODO: Graba attaches
        //Iterator itattaches=attaches.keySet().iterator();
//        while(itattaches.hasNext()) {
//            String attach=(String)itattaches.next();
//            byte abyte[] = (byte[]) attaches.get(attach);
//            Os = new FileOutputStream(WBUtils.getInstance().getWorkPath() + "/sites/"+title + "/templates/" + RECID + "/" + version + "/images/" + attach);
//            Os.write(abyte,0,abyte.length);
//            Os.flush();
//            Os.close();
//        }
       return true;
    }
    

    public static boolean resetTemplates(WebSite website, Template template, User user) {
        try {
            String rutawork = (String) SWBInstance.getWorkPath();
            File dir = new File(rutawork + "/sites/" + website.getName() + "/templates/" + template.getURI());
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
                    File ActualVDir = new File(rutawork + "/sites/" + "topicmap" + "/templates/" + "templateid" + "/" + lastV);
                    File f = new File(rutawork + "/sites/" + "topicmap" + "/templates/" + "templateid" + "/1");
                    f.mkdir();
                    String sSource = "sites/" + "topicmap" + "/templates/" + "templateid" + "/" + lastV;
                    String sTarget = "sites/" + "topicmap" + "/templates/" + "templateid" + "/1";
                    b = SWBUtils.IO.copyStructure(ActualVDir.getPath() + "/", f.getPath() + "/", true, sSource, sTarget);
                }
                if (b) {
                    VersionInfo verInfo = website.createVersionInfo();
                    verInfo.setValue("1");
                    template.setActualVersion(verInfo);
                    template.setLastVersion(verInfo);

                    //logeo
                    SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "remove", template.getURI(), template.getURI(), "remove Template versions", null);
                    try {
                        swbAdmLog.create();
                    } catch (Exception e) {
                        throw new SWBException("Error removing Template versions", e);
                    }

                    return true;
                }
            }
        } catch (Exception e) {
            log.error("Error while removing template version width id:" + "templateid" + "-TemplateSrv:RemoveTemplateVersion", e);
        }
        return false;
    }

    public boolean removeTemplateGroup(WebSite webSite, String id, User user) throws SWBException {
        boolean doAction = false;
        webSite.removeObjectGroup(id);
        Iterator<Template> itTemplates = webSite.listTemplates();
        while (itTemplates.hasNext()) {
            Template template = itTemplates.next();
            //TODO:Revisar que id me van a pasar aqui para ver si tengo q concatenarlo con algo para formar el uri
            if (template.getGroup().getURI().equals(id)) {
                //Elimina el template
                removeTemplate(webSite, template.getURI(), user);
            }
        }
        doAction = true;
        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "remove", id, id, "remove Template Group", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error removing Template Group", e);
        }
        return doAction;
    }
}
