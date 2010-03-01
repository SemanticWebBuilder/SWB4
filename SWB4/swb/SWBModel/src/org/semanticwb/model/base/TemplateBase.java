/**  
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
 **/
package org.semanticwb.model.base;


// TODO: Auto-generated Javadoc
/**
 * The Class TemplateBase.
 */
public abstract class TemplateBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.FilterableClass,org.semanticwb.model.Deviceable,org.semanticwb.model.Filterable,org.semanticwb.model.Activeable,org.semanticwb.model.UserGroupRefable,org.semanticwb.model.RuleRefable,org.semanticwb.model.Versionable,org.semanticwb.model.Expirable,org.semanticwb.model.CalendarRefable,org.semanticwb.model.Traceable,org.semanticwb.model.Referensable,org.semanticwb.model.RoleRefable,org.semanticwb.model.Trashable,org.semanticwb.model.Localeable,org.semanticwb.model.Descriptiveable
{
    
    /** The Constant swb_TemplateGroup. */
    public static final org.semanticwb.platform.SemanticClass swb_TemplateGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#TemplateGroup");
    
    /** The Constant swb_templateGroup. */
    public static final org.semanticwb.platform.SemanticProperty swb_templateGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#templateGroup");
    
    /** The Constant swb_TemplateRef. */
    public static final org.semanticwb.platform.SemanticClass swb_TemplateRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#TemplateRef");
    
    /** The Constant swb_hasTemplateRefInv. */
    public static final org.semanticwb.platform.SemanticProperty swb_hasTemplateRefInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasTemplateRefInv");
    
    /** The Constant swb_Template. */
    public static final org.semanticwb.platform.SemanticClass swb_Template=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Template");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Template");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {

        /**
         * List templates.
         * 
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Template> listTemplates(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Template>(it, true);
        }

        /**
         * List templates.
         * 
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Template> listTemplates()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Template>(it, true);
        }

        /**
         * Creates the template.
         * 
         * @param model the model
         * @return the org.semanticwb.model. template
         */
        public static org.semanticwb.model.Template createTemplate(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.model.Template.ClassMgr.createTemplate(String.valueOf(id), model);
        }

        /**
         * Gets the template.
         * 
         * @param id the id
         * @param model the model
         * @return the template
         */
        public static org.semanticwb.model.Template getTemplate(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.Template)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        /**
         * Creates the template.
         * 
         * @param id the id
         * @param model the model
         * @return the org.semanticwb.model. template
         */
        public static org.semanticwb.model.Template createTemplate(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.Template)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        /**
         * Removes the template.
         * 
         * @param id the id
         * @param model the model
         */
        public static void removeTemplate(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        /**
         * Checks for template.
         * 
         * @param id the id
         * @param model the model
         * @return true, if successful
         */
        public static boolean hasTemplate(String id, org.semanticwb.model.SWBModel model)
        {
            return (getTemplate(id, model)!=null);
        }

        /**
         * List template by user group ref.
         * 
         * @param hasusergroupref the hasusergroupref
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Template> listTemplateByUserGroupRef(org.semanticwb.model.UserGroupRef hasusergroupref,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Template> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasUserGroupRef, hasusergroupref.getSemanticObject()));
            return it;
        }

        /**
         * List template by user group ref.
         * 
         * @param hasusergroupref the hasusergroupref
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Template> listTemplateByUserGroupRef(org.semanticwb.model.UserGroupRef hasusergroupref)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Template> it=new org.semanticwb.model.GenericIterator(hasusergroupref.getSemanticObject().getModel().listSubjects(swb_hasUserGroupRef,hasusergroupref.getSemanticObject()));
            return it;
        }

        /**
         * List template by group.
         * 
         * @param templategroup the templategroup
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Template> listTemplateByGroup(org.semanticwb.model.TemplateGroup templategroup,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Template> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_templateGroup, templategroup.getSemanticObject()));
            return it;
        }

        /**
         * List template by group.
         * 
         * @param templategroup the templategroup
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Template> listTemplateByGroup(org.semanticwb.model.TemplateGroup templategroup)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Template> it=new org.semanticwb.model.GenericIterator(templategroup.getSemanticObject().getModel().listSubjects(swb_templateGroup,templategroup.getSemanticObject()));
            return it;
        }

        /**
         * List template by last version.
         * 
         * @param lastversion the lastversion
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Template> listTemplateByLastVersion(org.semanticwb.model.VersionInfo lastversion,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Template> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_lastVersion, lastversion.getSemanticObject()));
            return it;
        }

        /**
         * List template by last version.
         * 
         * @param lastversion the lastversion
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Template> listTemplateByLastVersion(org.semanticwb.model.VersionInfo lastversion)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Template> it=new org.semanticwb.model.GenericIterator(lastversion.getSemanticObject().getModel().listSubjects(swb_lastVersion,lastversion.getSemanticObject()));
            return it;
        }

        /**
         * List template by language.
         * 
         * @param language the language
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Template> listTemplateByLanguage(org.semanticwb.model.Language language,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Template> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_language, language.getSemanticObject()));
            return it;
        }

        /**
         * List template by language.
         * 
         * @param language the language
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Template> listTemplateByLanguage(org.semanticwb.model.Language language)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Template> it=new org.semanticwb.model.GenericIterator(language.getSemanticObject().getModel().listSubjects(swb_language,language.getSemanticObject()));
            return it;
        }

        /**
         * List template by calendar ref.
         * 
         * @param hascalendarref the hascalendarref
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Template> listTemplateByCalendarRef(org.semanticwb.model.CalendarRef hascalendarref,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Template> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasCalendarRef, hascalendarref.getSemanticObject()));
            return it;
        }

        /**
         * List template by calendar ref.
         * 
         * @param hascalendarref the hascalendarref
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Template> listTemplateByCalendarRef(org.semanticwb.model.CalendarRef hascalendarref)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Template> it=new org.semanticwb.model.GenericIterator(hascalendarref.getSemanticObject().getModel().listSubjects(swb_hasCalendarRef,hascalendarref.getSemanticObject()));
            return it;
        }

        /**
         * List template by modified by.
         * 
         * @param modifiedby the modifiedby
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Template> listTemplateByModifiedBy(org.semanticwb.model.User modifiedby,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Template> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_modifiedBy, modifiedby.getSemanticObject()));
            return it;
        }

        /**
         * List template by modified by.
         * 
         * @param modifiedby the modifiedby
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Template> listTemplateByModifiedBy(org.semanticwb.model.User modifiedby)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Template> it=new org.semanticwb.model.GenericIterator(modifiedby.getSemanticObject().getModel().listSubjects(swb_modifiedBy,modifiedby.getSemanticObject()));
            return it;
        }

        /**
         * List template by actual version.
         * 
         * @param actualversion the actualversion
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Template> listTemplateByActualVersion(org.semanticwb.model.VersionInfo actualversion,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Template> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_actualVersion, actualversion.getSemanticObject()));
            return it;
        }

        /**
         * List template by actual version.
         * 
         * @param actualversion the actualversion
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Template> listTemplateByActualVersion(org.semanticwb.model.VersionInfo actualversion)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Template> it=new org.semanticwb.model.GenericIterator(actualversion.getSemanticObject().getModel().listSubjects(swb_actualVersion,actualversion.getSemanticObject()));
            return it;
        }

        /**
         * List template by role ref.
         * 
         * @param hasroleref the hasroleref
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Template> listTemplateByRoleRef(org.semanticwb.model.RoleRef hasroleref,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Template> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasRoleRef, hasroleref.getSemanticObject()));
            return it;
        }

        /**
         * List template by role ref.
         * 
         * @param hasroleref the hasroleref
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Template> listTemplateByRoleRef(org.semanticwb.model.RoleRef hasroleref)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Template> it=new org.semanticwb.model.GenericIterator(hasroleref.getSemanticObject().getModel().listSubjects(swb_hasRoleRef,hasroleref.getSemanticObject()));
            return it;
        }

        /**
         * List template by device.
         * 
         * @param device the device
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Template> listTemplateByDevice(org.semanticwb.model.Device device,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Template> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_device, device.getSemanticObject()));
            return it;
        }

        /**
         * List template by device.
         * 
         * @param device the device
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Template> listTemplateByDevice(org.semanticwb.model.Device device)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Template> it=new org.semanticwb.model.GenericIterator(device.getSemanticObject().getModel().listSubjects(swb_device,device.getSemanticObject()));
            return it;
        }

        /**
         * List template by template ref inv.
         * 
         * @param hastemplaterefinv the hastemplaterefinv
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Template> listTemplateByTemplateRefInv(org.semanticwb.model.TemplateRef hastemplaterefinv,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Template> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasTemplateRefInv, hastemplaterefinv.getSemanticObject()));
            return it;
        }

        /**
         * List template by template ref inv.
         * 
         * @param hastemplaterefinv the hastemplaterefinv
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Template> listTemplateByTemplateRefInv(org.semanticwb.model.TemplateRef hastemplaterefinv)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Template> it=new org.semanticwb.model.GenericIterator(hastemplaterefinv.getSemanticObject().getModel().listSubjects(swb_hasTemplateRefInv,hastemplaterefinv.getSemanticObject()));
            return it;
        }

        /**
         * List template by creator.
         * 
         * @param creator the creator
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Template> listTemplateByCreator(org.semanticwb.model.User creator,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Template> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_creator, creator.getSemanticObject()));
            return it;
        }

        /**
         * List template by creator.
         * 
         * @param creator the creator
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Template> listTemplateByCreator(org.semanticwb.model.User creator)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Template> it=new org.semanticwb.model.GenericIterator(creator.getSemanticObject().getModel().listSubjects(swb_creator,creator.getSemanticObject()));
            return it;
        }

        /**
         * List template by rule ref.
         * 
         * @param hasruleref the hasruleref
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Template> listTemplateByRuleRef(org.semanticwb.model.RuleRef hasruleref,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Template> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasRuleRef, hasruleref.getSemanticObject()));
            return it;
        }

        /**
         * List template by rule ref.
         * 
         * @param hasruleref the hasruleref
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Template> listTemplateByRuleRef(org.semanticwb.model.RuleRef hasruleref)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Template> it=new org.semanticwb.model.GenericIterator(hasruleref.getSemanticObject().getModel().listSubjects(swb_hasRuleRef,hasruleref.getSemanticObject()));
            return it;
        }
    }

    /**
     * Instantiates a new template base.
     * 
     * @param base the base
     */
    public TemplateBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.UserGroupRefableBase#listUserGroupRefs()
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroupRef> listUserGroupRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroupRef>(getSemanticObject().listObjectProperties(swb_hasUserGroupRef));
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.UserGroupRefableBase#hasUserGroupRef(org.semanticwb.model.UserGroupRef)
     */
    public boolean hasUserGroupRef(org.semanticwb.model.UserGroupRef usergroupref)
    {
        boolean ret=false;
        if(usergroupref!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasUserGroupRef,usergroupref.getSemanticObject());
        }
        return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.UserGroupRefableBase#listInheritUserGroupRefs()
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroupRef> listInheritUserGroupRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroupRef>(getSemanticObject().listInheritProperties(swb_hasUserGroupRef));
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.UserGroupRefableBase#addUserGroupRef(org.semanticwb.model.UserGroupRef)
     */
    public void addUserGroupRef(org.semanticwb.model.UserGroupRef value)
    {
        getSemanticObject().addObjectProperty(swb_hasUserGroupRef, value.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.UserGroupRefableBase#removeAllUserGroupRef()
     */
    public void removeAllUserGroupRef()
    {
        getSemanticObject().removeProperty(swb_hasUserGroupRef);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.UserGroupRefableBase#removeUserGroupRef(org.semanticwb.model.UserGroupRef)
     */
    public void removeUserGroupRef(org.semanticwb.model.UserGroupRef usergroupref)
    {
        getSemanticObject().removeObjectProperty(swb_hasUserGroupRef,usergroupref.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.UserGroupRefableBase#getUserGroupRef()
     */
    public org.semanticwb.model.UserGroupRef getUserGroupRef()
    {
         org.semanticwb.model.UserGroupRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasUserGroupRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.UserGroupRef)obj.createGenericInstance();
         }
         return ret;
    }

    /**
     * Sets the group.
     * 
     * @param value the new group
     */
    public void setGroup(org.semanticwb.model.TemplateGroup value)
    {
        getSemanticObject().setObjectProperty(swb_templateGroup, value.getSemanticObject());
    }

    /**
     * Removes the group.
     */
    public void removeGroup()
    {
        getSemanticObject().removeProperty(swb_templateGroup);
    }

    /**
     * Gets the group.
     * 
     * @return the group
     */
    public org.semanticwb.model.TemplateGroup getGroup()
    {
         org.semanticwb.model.TemplateGroup ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_templateGroup);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.TemplateGroup)obj.createGenericInstance();
         }
         return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.VersionableBase#setLastVersion(org.semanticwb.model.VersionInfo)
     */
    public void setLastVersion(org.semanticwb.model.VersionInfo value)
    {
        getSemanticObject().setObjectProperty(swb_lastVersion, value.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.VersionableBase#removeLastVersion()
     */
    public void removeLastVersion()
    {
        getSemanticObject().removeProperty(swb_lastVersion);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.VersionableBase#getLastVersion()
     */
    public org.semanticwb.model.VersionInfo getLastVersion()
    {
         org.semanticwb.model.VersionInfo ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_lastVersion);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.VersionInfo)obj.createGenericInstance();
         }
         return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TrashableBase#isDeleted()
     */
    public boolean isDeleted()
    {
        return getSemanticObject().getBooleanProperty(swb_deleted);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TrashableBase#setDeleted(boolean)
     */
    public void setDeleted(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_deleted, value);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.ActiveableBase#isActive()
     */
    public boolean isActive()
    {
        return getSemanticObject().getBooleanProperty(swb_active);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.ActiveableBase#setActive(boolean)
     */
    public void setActive(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_active, value);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.LocaleableBase#setLanguage(org.semanticwb.model.Language)
     */
    public void setLanguage(org.semanticwb.model.Language value)
    {
        getSemanticObject().setObjectProperty(swb_language, value.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.LocaleableBase#removeLanguage()
     */
    public void removeLanguage()
    {
        getSemanticObject().removeProperty(swb_language);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.LocaleableBase#getLanguage()
     */
    public org.semanticwb.model.Language getLanguage()
    {
         org.semanticwb.model.Language ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_language);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Language)obj.createGenericInstance();
         }
         return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.CalendarRefableBase#listCalendarRefs()
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.CalendarRef> listCalendarRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.CalendarRef>(getSemanticObject().listObjectProperties(swb_hasCalendarRef));
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.CalendarRefableBase#hasCalendarRef(org.semanticwb.model.CalendarRef)
     */
    public boolean hasCalendarRef(org.semanticwb.model.CalendarRef calendarref)
    {
        boolean ret=false;
        if(calendarref!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasCalendarRef,calendarref.getSemanticObject());
        }
        return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.CalendarRefableBase#addCalendarRef(org.semanticwb.model.CalendarRef)
     */
    public void addCalendarRef(org.semanticwb.model.CalendarRef value)
    {
        getSemanticObject().addObjectProperty(swb_hasCalendarRef, value.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.CalendarRefableBase#removeAllCalendarRef()
     */
    public void removeAllCalendarRef()
    {
        getSemanticObject().removeProperty(swb_hasCalendarRef);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.CalendarRefableBase#removeCalendarRef(org.semanticwb.model.CalendarRef)
     */
    public void removeCalendarRef(org.semanticwb.model.CalendarRef calendarref)
    {
        getSemanticObject().removeObjectProperty(swb_hasCalendarRef,calendarref.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.CalendarRefableBase#getCalendarRef()
     */
    public org.semanticwb.model.CalendarRef getCalendarRef()
    {
         org.semanticwb.model.CalendarRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasCalendarRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.CalendarRef)obj.createGenericInstance();
         }
         return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#getCreated()
     */
    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(swb_created);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#setCreated(java.util.Date)
     */
    public void setCreated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_created, value);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#setModifiedBy(org.semanticwb.model.User)
     */
    public void setModifiedBy(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(swb_modifiedBy, value.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#removeModifiedBy()
     */
    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(swb_modifiedBy);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#getModifiedBy()
     */
    public org.semanticwb.model.User getModifiedBy()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_modifiedBy);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.ExpirableBase#getExpiration()
     */
    public java.util.Date getExpiration()
    {
        return getSemanticObject().getDateProperty(swb_expiration);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.ExpirableBase#setExpiration(java.util.Date)
     */
    public void setExpiration(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_expiration, value);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#getTitle()
     */
    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#setTitle(java.lang.String)
     */
    public void setTitle(String value)
    {
        getSemanticObject().setProperty(swb_title, value);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#getTitle(java.lang.String)
     */
    public String getTitle(String lang)
    {
        return getSemanticObject().getProperty(swb_title, null, lang);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#getDisplayTitle(java.lang.String)
     */
    public String getDisplayTitle(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_title, lang);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#setTitle(java.lang.String, java.lang.String)
     */
    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(swb_title, title, lang);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.VersionableBase#setActualVersion(org.semanticwb.model.VersionInfo)
     */
    public void setActualVersion(org.semanticwb.model.VersionInfo value)
    {
        getSemanticObject().setObjectProperty(swb_actualVersion, value.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.VersionableBase#removeActualVersion()
     */
    public void removeActualVersion()
    {
        getSemanticObject().removeProperty(swb_actualVersion);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.VersionableBase#getActualVersion()
     */
    public org.semanticwb.model.VersionInfo getActualVersion()
    {
         org.semanticwb.model.VersionInfo ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_actualVersion);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.VersionInfo)obj.createGenericInstance();
         }
         return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#getUpdated()
     */
    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(swb_updated);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#setUpdated(java.util.Date)
     */
    public void setUpdated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_updated, value);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.RoleRefableBase#listRoleRefs()
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.RoleRef> listRoleRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.RoleRef>(getSemanticObject().listObjectProperties(swb_hasRoleRef));
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.RoleRefableBase#hasRoleRef(org.semanticwb.model.RoleRef)
     */
    public boolean hasRoleRef(org.semanticwb.model.RoleRef roleref)
    {
        boolean ret=false;
        if(roleref!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasRoleRef,roleref.getSemanticObject());
        }
        return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.RoleRefableBase#listInheritRoleRefs()
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.RoleRef> listInheritRoleRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.RoleRef>(getSemanticObject().listInheritProperties(swb_hasRoleRef));
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.RoleRefableBase#addRoleRef(org.semanticwb.model.RoleRef)
     */
    public void addRoleRef(org.semanticwb.model.RoleRef value)
    {
        getSemanticObject().addObjectProperty(swb_hasRoleRef, value.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.RoleRefableBase#removeAllRoleRef()
     */
    public void removeAllRoleRef()
    {
        getSemanticObject().removeProperty(swb_hasRoleRef);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.RoleRefableBase#removeRoleRef(org.semanticwb.model.RoleRef)
     */
    public void removeRoleRef(org.semanticwb.model.RoleRef roleref)
    {
        getSemanticObject().removeObjectProperty(swb_hasRoleRef,roleref.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.RoleRefableBase#getRoleRef()
     */
    public org.semanticwb.model.RoleRef getRoleRef()
    {
         org.semanticwb.model.RoleRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasRoleRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.RoleRef)obj.createGenericInstance();
         }
         return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DeviceableBase#setDevice(org.semanticwb.model.Device)
     */
    public void setDevice(org.semanticwb.model.Device value)
    {
        getSemanticObject().setObjectProperty(swb_device, value.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DeviceableBase#removeDevice()
     */
    public void removeDevice()
    {
        getSemanticObject().removeProperty(swb_device);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DeviceableBase#getDevice()
     */
    public org.semanticwb.model.Device getDevice()
    {
         org.semanticwb.model.Device ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_device);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Device)obj.createGenericInstance();
         }
         return ret;
    }

    /**
     * List template ref invs.
     * 
     * @return the org.semanticwb.model. generic iterator
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.TemplateRef> listTemplateRefInvs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.TemplateRef>(getSemanticObject().listObjectProperties(swb_hasTemplateRefInv));
    }

    /**
     * Checks for template ref inv.
     * 
     * @param templateref the templateref
     * @return true, if successful
     */
    public boolean hasTemplateRefInv(org.semanticwb.model.TemplateRef templateref)
    {
        boolean ret=false;
        if(templateref!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasTemplateRefInv,templateref.getSemanticObject());
        }
        return ret;
    }

    /**
     * Gets the template ref inv.
     * 
     * @return the template ref inv
     */
    public org.semanticwb.model.TemplateRef getTemplateRefInv()
    {
         org.semanticwb.model.TemplateRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasTemplateRefInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.TemplateRef)obj.createGenericInstance();
         }
         return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#setCreator(org.semanticwb.model.User)
     */
    public void setCreator(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(swb_creator, value.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#removeCreator()
     */
    public void removeCreator()
    {
        getSemanticObject().removeProperty(swb_creator);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#getCreator()
     */
    public org.semanticwb.model.User getCreator()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_creator);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.RuleRefableBase#listRuleRefs()
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.RuleRef> listRuleRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.RuleRef>(getSemanticObject().listObjectProperties(swb_hasRuleRef));
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.RuleRefableBase#hasRuleRef(org.semanticwb.model.RuleRef)
     */
    public boolean hasRuleRef(org.semanticwb.model.RuleRef ruleref)
    {
        boolean ret=false;
        if(ruleref!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasRuleRef,ruleref.getSemanticObject());
        }
        return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.RuleRefableBase#listInheritRuleRefs()
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.RuleRef> listInheritRuleRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.RuleRef>(getSemanticObject().listInheritProperties(swb_hasRuleRef));
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.RuleRefableBase#addRuleRef(org.semanticwb.model.RuleRef)
     */
    public void addRuleRef(org.semanticwb.model.RuleRef value)
    {
        getSemanticObject().addObjectProperty(swb_hasRuleRef, value.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.RuleRefableBase#removeAllRuleRef()
     */
    public void removeAllRuleRef()
    {
        getSemanticObject().removeProperty(swb_hasRuleRef);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.RuleRefableBase#removeRuleRef(org.semanticwb.model.RuleRef)
     */
    public void removeRuleRef(org.semanticwb.model.RuleRef ruleref)
    {
        getSemanticObject().removeObjectProperty(swb_hasRuleRef,ruleref.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.RuleRefableBase#getRuleRef()
     */
    public org.semanticwb.model.RuleRef getRuleRef()
    {
         org.semanticwb.model.RuleRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasRuleRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.RuleRef)obj.createGenericInstance();
         }
         return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#getDescription()
     */
    public String getDescription()
    {
        return getSemanticObject().getProperty(swb_description);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#setDescription(java.lang.String)
     */
    public void setDescription(String value)
    {
        getSemanticObject().setProperty(swb_description, value);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#getDescription(java.lang.String)
     */
    public String getDescription(String lang)
    {
        return getSemanticObject().getProperty(swb_description, null, lang);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#getDisplayDescription(java.lang.String)
     */
    public String getDisplayDescription(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_description, lang);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#setDescription(java.lang.String, java.lang.String)
     */
    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(swb_description, description, lang);
    }

    /**
     * Gets the web site.
     * 
     * @return the web site
     */
    public org.semanticwb.model.WebSite getWebSite()
    {
        return (org.semanticwb.model.WebSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
