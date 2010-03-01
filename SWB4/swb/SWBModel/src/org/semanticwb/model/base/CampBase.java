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
 * The Class CampBase.
 */
public abstract class CampBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Roleable,org.semanticwb.model.CalendarRefable,org.semanticwb.model.Traceable,org.semanticwb.model.Referensable,org.semanticwb.model.Ruleable,org.semanticwb.model.Activeable,org.semanticwb.model.Trashable,org.semanticwb.model.Descriptiveable
{
    
    /** The Constant swb_Camp. */
    public static final org.semanticwb.platform.SemanticClass swb_Camp=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Camp");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Camp");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {

        /**
         * List camps.
         * 
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Camp> listCamps(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Camp>(it, true);
        }

        /**
         * List camps.
         * 
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Camp> listCamps()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Camp>(it, true);
        }

        /**
         * Creates the camp.
         * 
         * @param model the model
         * @return the org.semanticwb.model. camp
         */
        public static org.semanticwb.model.Camp createCamp(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.model.Camp.ClassMgr.createCamp(String.valueOf(id), model);
        }

        /**
         * Gets the camp.
         * 
         * @param id the id
         * @param model the model
         * @return the camp
         */
        public static org.semanticwb.model.Camp getCamp(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.Camp)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        /**
         * Creates the camp.
         * 
         * @param id the id
         * @param model the model
         * @return the org.semanticwb.model. camp
         */
        public static org.semanticwb.model.Camp createCamp(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.Camp)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        /**
         * Removes the camp.
         * 
         * @param id the id
         * @param model the model
         */
        public static void removeCamp(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        /**
         * Checks for camp.
         * 
         * @param id the id
         * @param model the model
         * @return true, if successful
         */
        public static boolean hasCamp(String id, org.semanticwb.model.SWBModel model)
        {
            return (getCamp(id, model)!=null);
        }

        /**
         * List camp by modified by.
         * 
         * @param modifiedby the modifiedby
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Camp> listCampByModifiedBy(org.semanticwb.model.User modifiedby,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Camp> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_modifiedBy, modifiedby.getSemanticObject()));
            return it;
        }

        /**
         * List camp by modified by.
         * 
         * @param modifiedby the modifiedby
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Camp> listCampByModifiedBy(org.semanticwb.model.User modifiedby)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Camp> it=new org.semanticwb.model.GenericIterator(modifiedby.getSemanticObject().getModel().listSubjects(swb_modifiedBy,modifiedby.getSemanticObject()));
            return it;
        }

        /**
         * List camp by rule.
         * 
         * @param hasrule the hasrule
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Camp> listCampByRule(org.semanticwb.model.Rule hasrule,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Camp> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasRule, hasrule.getSemanticObject()));
            return it;
        }

        /**
         * List camp by rule.
         * 
         * @param hasrule the hasrule
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Camp> listCampByRule(org.semanticwb.model.Rule hasrule)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Camp> it=new org.semanticwb.model.GenericIterator(hasrule.getSemanticObject().getModel().listSubjects(swb_hasRule,hasrule.getSemanticObject()));
            return it;
        }

        /**
         * List camp by creator.
         * 
         * @param creator the creator
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Camp> listCampByCreator(org.semanticwb.model.User creator,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Camp> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_creator, creator.getSemanticObject()));
            return it;
        }

        /**
         * List camp by creator.
         * 
         * @param creator the creator
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Camp> listCampByCreator(org.semanticwb.model.User creator)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Camp> it=new org.semanticwb.model.GenericIterator(creator.getSemanticObject().getModel().listSubjects(swb_creator,creator.getSemanticObject()));
            return it;
        }

        /**
         * List camp by calendar ref.
         * 
         * @param hascalendarref the hascalendarref
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Camp> listCampByCalendarRef(org.semanticwb.model.CalendarRef hascalendarref,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Camp> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasCalendarRef, hascalendarref.getSemanticObject()));
            return it;
        }

        /**
         * List camp by calendar ref.
         * 
         * @param hascalendarref the hascalendarref
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Camp> listCampByCalendarRef(org.semanticwb.model.CalendarRef hascalendarref)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Camp> it=new org.semanticwb.model.GenericIterator(hascalendarref.getSemanticObject().getModel().listSubjects(swb_hasCalendarRef,hascalendarref.getSemanticObject()));
            return it;
        }

        /**
         * List camp by role.
         * 
         * @param hasrole the hasrole
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Camp> listCampByRole(org.semanticwb.model.Role hasrole,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Camp> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasRole, hasrole.getSemanticObject()));
            return it;
        }

        /**
         * List camp by role.
         * 
         * @param hasrole the hasrole
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Camp> listCampByRole(org.semanticwb.model.Role hasrole)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Camp> it=new org.semanticwb.model.GenericIterator(hasrole.getSemanticObject().getModel().listSubjects(swb_hasRole,hasrole.getSemanticObject()));
            return it;
        }
    }

    /**
     * Instantiates a new camp base.
     * 
     * @param base the base
     */
    public CampBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
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
     * @see org.semanticwb.model.base.RuleableBase#listRules()
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.Rule> listRules()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Rule>(getSemanticObject().listObjectProperties(swb_hasRule));
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.RuleableBase#hasRule(org.semanticwb.model.Rule)
     */
    public boolean hasRule(org.semanticwb.model.Rule rule)
    {
        boolean ret=false;
        if(rule!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasRule,rule.getSemanticObject());
        }
        return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.RuleableBase#addRule(org.semanticwb.model.Rule)
     */
    public void addRule(org.semanticwb.model.Rule value)
    {
        getSemanticObject().addObjectProperty(swb_hasRule, value.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.RuleableBase#removeAllRule()
     */
    public void removeAllRule()
    {
        getSemanticObject().removeProperty(swb_hasRule);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.RuleableBase#removeRule(org.semanticwb.model.Rule)
     */
    public void removeRule(org.semanticwb.model.Rule rule)
    {
        getSemanticObject().removeObjectProperty(swb_hasRule,rule.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.RuleableBase#getRule()
     */
    public org.semanticwb.model.Rule getRule()
    {
         org.semanticwb.model.Rule ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasRule);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Rule)obj.createGenericInstance();
         }
         return ret;
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
     * @see org.semanticwb.model.base.RoleableBase#listRoles()
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.Role> listRoles()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Role>(getSemanticObject().listObjectProperties(swb_hasRole));
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.RoleableBase#hasRole(org.semanticwb.model.Role)
     */
    public boolean hasRole(org.semanticwb.model.Role role)
    {
        boolean ret=false;
        if(role!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasRole,role.getSemanticObject());
        }
        return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.RoleableBase#addRole(org.semanticwb.model.Role)
     */
    public void addRole(org.semanticwb.model.Role value)
    {
        getSemanticObject().addObjectProperty(swb_hasRole, value.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.RoleableBase#removeAllRole()
     */
    public void removeAllRole()
    {
        getSemanticObject().removeProperty(swb_hasRole);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.RoleableBase#removeRole(org.semanticwb.model.Role)
     */
    public void removeRole(org.semanticwb.model.Role role)
    {
        getSemanticObject().removeObjectProperty(swb_hasRole,role.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.RoleableBase#getRole()
     */
    public org.semanticwb.model.Role getRole()
    {
         org.semanticwb.model.Role ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasRole);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Role)obj.createGenericInstance();
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
