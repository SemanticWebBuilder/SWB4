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
 * The Class VersionInfoBase.
 */
public abstract class VersionInfoBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Traceable
{
    
    /** The Constant swb_versionValue. */
    public static final org.semanticwb.platform.SemanticProperty swb_versionValue=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#versionValue");
    
    /** The Constant swb_VersionInfo. */
    public static final org.semanticwb.platform.SemanticClass swb_VersionInfo=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#VersionInfo");
    
    /** The Constant swb_previousVersion. */
    public static final org.semanticwb.platform.SemanticProperty swb_previousVersion=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#previousVersion");
    
    /** The Constant swb_versionComment. */
    public static final org.semanticwb.platform.SemanticProperty swb_versionComment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#versionComment");
    
    /** The Constant swb_nextVersion. */
    public static final org.semanticwb.platform.SemanticProperty swb_nextVersion=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#nextVersion");
    
    /** The Constant swb_User. */
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    
    /** The Constant swb_versionLockedBy. */
    public static final org.semanticwb.platform.SemanticProperty swb_versionLockedBy=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#versionLockedBy");
    
    /** The Constant swb_versionFile. */
    public static final org.semanticwb.platform.SemanticProperty swb_versionFile=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#versionFile");
    
    /** The Constant swb_versionNumber. */
    public static final org.semanticwb.platform.SemanticProperty swb_versionNumber=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#versionNumber");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#VersionInfo");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {

        /**
         * List version infos.
         * 
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.VersionInfo> listVersionInfos(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.VersionInfo>(it, true);
        }

        /**
         * List version infos.
         * 
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.VersionInfo> listVersionInfos()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.VersionInfo>(it, true);
        }

        /**
         * Creates the version info.
         * 
         * @param model the model
         * @return the org.semanticwb.model. version info
         */
        public static org.semanticwb.model.VersionInfo createVersionInfo(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.model.VersionInfo.ClassMgr.createVersionInfo(String.valueOf(id), model);
        }

        /**
         * Gets the version info.
         * 
         * @param id the id
         * @param model the model
         * @return the version info
         */
        public static org.semanticwb.model.VersionInfo getVersionInfo(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.VersionInfo)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        /**
         * Creates the version info.
         * 
         * @param id the id
         * @param model the model
         * @return the org.semanticwb.model. version info
         */
        public static org.semanticwb.model.VersionInfo createVersionInfo(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.VersionInfo)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        /**
         * Removes the version info.
         * 
         * @param id the id
         * @param model the model
         */
        public static void removeVersionInfo(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        /**
         * Checks for version info.
         * 
         * @param id the id
         * @param model the model
         * @return true, if successful
         */
        public static boolean hasVersionInfo(String id, org.semanticwb.model.SWBModel model)
        {
            return (getVersionInfo(id, model)!=null);
        }

        /**
         * List version info by modified by.
         * 
         * @param modifiedby the modifiedby
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.VersionInfo> listVersionInfoByModifiedBy(org.semanticwb.model.User modifiedby,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.VersionInfo> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_modifiedBy, modifiedby.getSemanticObject()));
            return it;
        }

        /**
         * List version info by modified by.
         * 
         * @param modifiedby the modifiedby
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.VersionInfo> listVersionInfoByModifiedBy(org.semanticwb.model.User modifiedby)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.VersionInfo> it=new org.semanticwb.model.GenericIterator(modifiedby.getSemanticObject().getModel().listSubjects(swb_modifiedBy,modifiedby.getSemanticObject()));
            return it;
        }

        /**
         * List version info by previous version.
         * 
         * @param previousversion the previousversion
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.VersionInfo> listVersionInfoByPreviousVersion(org.semanticwb.model.VersionInfo previousversion,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.VersionInfo> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_previousVersion, previousversion.getSemanticObject()));
            return it;
        }

        /**
         * List version info by previous version.
         * 
         * @param previousversion the previousversion
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.VersionInfo> listVersionInfoByPreviousVersion(org.semanticwb.model.VersionInfo previousversion)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.VersionInfo> it=new org.semanticwb.model.GenericIterator(previousversion.getSemanticObject().getModel().listSubjects(swb_previousVersion,previousversion.getSemanticObject()));
            return it;
        }

        /**
         * List version info by next version.
         * 
         * @param nextversion the nextversion
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.VersionInfo> listVersionInfoByNextVersion(org.semanticwb.model.VersionInfo nextversion,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.VersionInfo> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_nextVersion, nextversion.getSemanticObject()));
            return it;
        }

        /**
         * List version info by next version.
         * 
         * @param nextversion the nextversion
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.VersionInfo> listVersionInfoByNextVersion(org.semanticwb.model.VersionInfo nextversion)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.VersionInfo> it=new org.semanticwb.model.GenericIterator(nextversion.getSemanticObject().getModel().listSubjects(swb_nextVersion,nextversion.getSemanticObject()));
            return it;
        }

        /**
         * List version info by version locked by.
         * 
         * @param versionlockedby the versionlockedby
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.VersionInfo> listVersionInfoByVersionLockedBy(org.semanticwb.model.User versionlockedby,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.VersionInfo> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_versionLockedBy, versionlockedby.getSemanticObject()));
            return it;
        }

        /**
         * List version info by version locked by.
         * 
         * @param versionlockedby the versionlockedby
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.VersionInfo> listVersionInfoByVersionLockedBy(org.semanticwb.model.User versionlockedby)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.VersionInfo> it=new org.semanticwb.model.GenericIterator(versionlockedby.getSemanticObject().getModel().listSubjects(swb_versionLockedBy,versionlockedby.getSemanticObject()));
            return it;
        }

        /**
         * List version info by creator.
         * 
         * @param creator the creator
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.VersionInfo> listVersionInfoByCreator(org.semanticwb.model.User creator,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.VersionInfo> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_creator, creator.getSemanticObject()));
            return it;
        }

        /**
         * List version info by creator.
         * 
         * @param creator the creator
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.VersionInfo> listVersionInfoByCreator(org.semanticwb.model.User creator)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.VersionInfo> it=new org.semanticwb.model.GenericIterator(creator.getSemanticObject().getModel().listSubjects(swb_creator,creator.getSemanticObject()));
            return it;
        }
    }

    /**
     * Instantiates a new version info base.
     * 
     * @param base the base
     */
    public VersionInfoBase(org.semanticwb.platform.SemanticObject base)
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

    /**
     * Gets the version value.
     * 
     * @return the version value
     */
    public String getVersionValue()
    {
        return getSemanticObject().getProperty(swb_versionValue);
    }

    /**
     * Sets the version value.
     * 
     * @param value the new version value
     */
    public void setVersionValue(String value)
    {
        getSemanticObject().setProperty(swb_versionValue, value);
    }

    /**
     * Sets the previous version.
     * 
     * @param value the new previous version
     */
    public void setPreviousVersion(org.semanticwb.model.VersionInfo value)
    {
        getSemanticObject().setObjectProperty(swb_previousVersion, value.getSemanticObject());
    }

    /**
     * Removes the previous version.
     */
    public void removePreviousVersion()
    {
        getSemanticObject().removeProperty(swb_previousVersion);
    }

    /**
     * Gets the previous version.
     * 
     * @return the previous version
     */
    public org.semanticwb.model.VersionInfo getPreviousVersion()
    {
         org.semanticwb.model.VersionInfo ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_previousVersion);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.VersionInfo)obj.createGenericInstance();
         }
         return ret;
    }

    /**
     * Gets the version comment.
     * 
     * @return the version comment
     */
    public String getVersionComment()
    {
        return getSemanticObject().getProperty(swb_versionComment);
    }

    /**
     * Sets the version comment.
     * 
     * @param value the new version comment
     */
    public void setVersionComment(String value)
    {
        getSemanticObject().setProperty(swb_versionComment, value);
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

    /**
     * Sets the next version.
     * 
     * @param value the new next version
     */
    public void setNextVersion(org.semanticwb.model.VersionInfo value)
    {
        getSemanticObject().setObjectProperty(swb_nextVersion, value.getSemanticObject());
    }

    /**
     * Removes the next version.
     */
    public void removeNextVersion()
    {
        getSemanticObject().removeProperty(swb_nextVersion);
    }

    /**
     * Gets the next version.
     * 
     * @return the next version
     */
    public org.semanticwb.model.VersionInfo getNextVersion()
    {
         org.semanticwb.model.VersionInfo ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_nextVersion);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.VersionInfo)obj.createGenericInstance();
         }
         return ret;
    }

    /**
     * Sets the version locked by.
     * 
     * @param value the new version locked by
     */
    public void setVersionLockedBy(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(swb_versionLockedBy, value.getSemanticObject());
    }

    /**
     * Removes the version locked by.
     */
    public void removeVersionLockedBy()
    {
        getSemanticObject().removeProperty(swb_versionLockedBy);
    }

    /**
     * Gets the version locked by.
     * 
     * @return the version locked by
     */
    public org.semanticwb.model.User getVersionLockedBy()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_versionLockedBy);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

    /**
     * Gets the version file.
     * 
     * @return the version file
     */
    public String getVersionFile()
    {
        return getSemanticObject().getProperty(swb_versionFile);
    }

    /**
     * Sets the version file.
     * 
     * @param value the new version file
     */
    public void setVersionFile(String value)
    {
        getSemanticObject().setProperty(swb_versionFile, value);
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

    /**
     * Gets the version number.
     * 
     * @return the version number
     */
    public int getVersionNumber()
    {
        return getSemanticObject().getIntProperty(swb_versionNumber);
    }

    /**
     * Sets the version number.
     * 
     * @param value the new version number
     */
    public void setVersionNumber(int value)
    {
        getSemanticObject().setIntProperty(swb_versionNumber, value);
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
