/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.WebSite;
import org.semanticwb.office.interfaces.RepositoryInfo;
import org.semanticwb.office.interfaces.SiteInfo;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticIterator;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.repository.office.OfficeCategory;
import org.semanticwb.repository.office.OfficeContent;

/**
 *
 * @author victor.lorenzana
 */
public class SWBOfficeManager implements OfficeManager
{

    private final SWBRepositoryManager manager;
    private static final String DESCRIPTION_BY_DEFAULT = "Sin descripción";
    private static final String LANGUAGE_BY_DEFAULT = "es";
    private static Logger log = SWBUtils.getLogger(SWBOfficeManager.class);
    private final SemanticClass cm_content = OfficeContent.cm_OfficeContent;

    public SWBOfficeManager(SWBRepositoryManager manager)
    {
        log.event("Initializing SWBOfficeManager ...");
        this.manager = manager;
        HashMap<String, String> types = getContentTypes();
        for (String type : types.keySet())
        {
            log.event("Type of office content " + type + " " + types.get(type));
        }
    }

    public HashMap<String, String> getContentTypes()
    {
        HashMap<String, String> types = new HashMap<String, String>();

        String label = DESCRIPTION_BY_DEFAULT + "(" + cm_content.getPrefix() + ":" + cm_content.getName() + ")";
        String literal = cm_content.getDisplayName(LANGUAGE_BY_DEFAULT);
        if (literal != null)
        {
            label = literal;
        }
        types.put(cm_content.getPrefix() + ":" + cm_content.getName(), label);
        Iterator<SemanticClass> childClases = cm_content.listSubClasses();
        while (childClases.hasNext())
        {
            SemanticClass clazz = childClases.next();
            label = DESCRIPTION_BY_DEFAULT + "(" + clazz.getPrefix() + ":" + clazz.getName() + ")";
            literal = clazz.getDisplayName(LANGUAGE_BY_DEFAULT);
            if (literal != null)
            {
                label = literal;
            }
            types.put(clazz.getPrefix() + ":" + clazz.getName(), label);
        }
        return types;
    }

    public String getCategoryType()
    {
        return OfficeCategory.cm_OfficeCategory.getPrefix() + ":" + OfficeCategory.cm_OfficeCategory.getName();
    }

    public String getPropertyTitleType()
    {
        return OfficeCategory.cm_title.getPrefix() + ":" + OfficeCategory.cm_title.getName();
    }

    public String getPropertyDescriptionType()
    {
        return OfficeCategory.cm_description.getPrefix() + ":" + OfficeCategory.cm_description.getName();
    }

    public String getPropertyFileType()
    {
        return OfficeContent.cm_file.getPrefix() + ":" + OfficeContent.cm_file.getName();
    }

    public String getPropertyType()
    {
        return OfficeContent.cm_officetype.getPrefix() + ":" + OfficeContent.cm_officetype.getName();
    }

    public String getUserType()
    {
        return OfficeContent.cm_user.getPrefix() + ":" + OfficeContent.cm_user.getName();
    }

    public Collection<RepositoryInfo> getWorkspaces()
    {
        HashMap<String, RepositoryInfo> workspaces = new HashMap<String, RepositoryInfo>();
        Iterator<WebSite> sites = SWBContext.listWebSites();
        while (sites.hasNext())
        {
            WebSite site = sites.next();
            SemanticIterator<SemanticObject> models = site.listSubModels();
            while (models.hasNext())
            {
                SemanticObject model = models.next();
                if (model.getSemanticClass().equals(Workspace.sclass) || model.getSemanticClass().isSubClass(Workspace.sclass))
                {
                    String name = manager.getName() + "@" + model.getId();
                    RepositoryInfo info = new RepositoryInfo(name);
                    info.exclusive = true;
                    info.siteInfo = new SiteInfo();
                    info.siteInfo.description = site.getDescription();
                    info.siteInfo.title = site.getTitle();
                    info.siteInfo.id = site.getId();
                    workspaces.put(name, info);
                }
            }
        }
        // Shared
        for (String repository : manager.getWorkspaces())
        {
            String name = manager.getName() + "@" + repository;
            if (!workspaces.containsKey(name))
            {
                RepositoryInfo info = new RepositoryInfo();
                info.exclusive = false;
                workspaces.put(name, info);
            }
        }
        return workspaces.values();
    }
}
