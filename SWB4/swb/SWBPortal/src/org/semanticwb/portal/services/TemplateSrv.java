/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.services;

import java.util.HashMap;
import org.semanticwb.SWBException;
import org.semanticwb.SWBInstance;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.Template;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.model.ObjectGroup;
import org.semanticwb.portal.SWBDBAdmLog;

/**
 *
 * @author jorge.jimenez
 */
public class TemplateSrv {

    public Template createTemplate(SemanticModel model, String fileName, String content, HashMap attaches, String titulo, String description, ObjectGroup objectgroup, User user) throws SWBException {
        Template template = SWBContext.createTemplate(model);
        template.setTitle(titulo);
        template.setDescription(description);
        template.addGroup(objectgroup);
        template.setStatus(1);

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getName(), "create", template.getURI(), template.getURI(), "create Template", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error creating Template", e);
        }

        return template;
    }
    
    public Template createTemplate(SemanticModel model, String templateUri, String fileName, String content, HashMap attaches, String titulo, String description, ObjectGroup objectgroup, User user) throws SWBException {
        Template template = SWBContext.createTemplate(model, templateUri);
        template.setTitle(titulo);
        template.setDescription(description);
        template.addGroup(objectgroup);
        template.setStatus(1);

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getName(), "create", template.getURI(), template.getURI(), "create Template", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error creating Template", e);
        }

        return template;
    }

    public boolean removeTemplate(Template template,User user) throws SWBException {
        boolean deleted = false;
        SWBContext.removeObject(template.getURI());
        deleted = true;

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getName(), "remove", template.getURI(), template.getURI(), "remove Template", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error removing Template", e);
        }

        return deleted;
    }
}
