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
package org.semanticwb.repository.office.base;


// TODO: Auto-generated Javadoc
/**
 * The Class OfficeContentBase.
 */
public abstract class OfficeContentBase extends org.semanticwb.repository.File implements org.semanticwb.repository.Referenceable,org.semanticwb.repository.office.Descriptiveable,org.semanticwb.repository.Traceable,org.semanticwb.repository.Lockable
{
    
    /** The Constant swboffice_officetype. */
    public static final org.semanticwb.platform.SemanticProperty swboffice_officetype=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/office#officetype");
    
    /** The Constant swboffice_lastModified. */
    public static final org.semanticwb.platform.SemanticProperty swboffice_lastModified=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/office#lastModified");
    
    /** The Constant swboffice_OfficeContent. */
    public static final org.semanticwb.platform.SemanticClass swboffice_OfficeContent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/office#OfficeContent");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/office#OfficeContent");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {

        /**
         * List office contents.
         * 
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.repository.office.OfficeContent> listOfficeContents(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.repository.office.OfficeContent>(it, true);
        }

        /**
         * List office contents.
         * 
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.repository.office.OfficeContent> listOfficeContents()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.repository.office.OfficeContent>(it, true);
        }

        /**
         * Gets the office content.
         * 
         * @param id the id
         * @param model the model
         * @return the office content
         */
        public static org.semanticwb.repository.office.OfficeContent getOfficeContent(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.repository.office.OfficeContent)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        /**
         * Creates the office content.
         * 
         * @param id the id
         * @param model the model
         * @return the org.semanticwb.repository.office. office content
         */
        public static org.semanticwb.repository.office.OfficeContent createOfficeContent(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.repository.office.OfficeContent)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        /**
         * Removes the office content.
         * 
         * @param id the id
         * @param model the model
         */
        public static void removeOfficeContent(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        /**
         * Checks for office content.
         * 
         * @param id the id
         * @param model the model
         * @return true, if successful
         */
        public static boolean hasOfficeContent(String id, org.semanticwb.model.SWBModel model)
        {
            return (getOfficeContent(id, model)!=null);
        }

        /**
         * List office content by node.
         * 
         * @param value the value
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.repository.office.OfficeContent> listOfficeContentByNode(org.semanticwb.repository.BaseNode value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.office.OfficeContent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swbrep_hasNodes, value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List office content by node.
         * 
         * @param value the value
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.repository.office.OfficeContent> listOfficeContentByNode(org.semanticwb.repository.BaseNode value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.office.OfficeContent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swbrep_hasNodes,value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List office content by parent.
         * 
         * @param value the value
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.repository.office.OfficeContent> listOfficeContentByParent(org.semanticwb.repository.BaseNode value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.office.OfficeContent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swbrep_parentNode, value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List office content by parent.
         * 
         * @param value the value
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.repository.office.OfficeContent> listOfficeContentByParent(org.semanticwb.repository.BaseNode value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.office.OfficeContent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swbrep_parentNode,value.getSemanticObject(),sclass));
            return it;
        }
    }

    /**
     * Instantiates a new office content base.
     * 
     * @param base the base
     */
    public OfficeContentBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /**
     * Gets the uuid.
     * 
     * @return the uuid
     */
    public String getUuid()
    {
        return getSemanticObject().getProperty(jcr_uuid);
    }

    /**
     * Sets the uuid.
     * 
     * @param value the new uuid
     */
    public void setUuid(String value)
    {
        getSemanticObject().setProperty(jcr_uuid, value);
    }

    /**
     * Gets the officetype.
     * 
     * @return the officetype
     */
    public String getOfficetype()
    {
        return getSemanticObject().getProperty(swboffice_officetype);
    }

    /**
     * Sets the officetype.
     * 
     * @param value the new officetype
     */
    public void setOfficetype(String value)
    {
        getSemanticObject().setProperty(swboffice_officetype, value);
    }

    /**
     * Checks if is lock is deep.
     * 
     * @return true, if is lock is deep
     */
    public boolean isLockIsDeep()
    {
        return getSemanticObject().getBooleanProperty(jcr_lockIsDeep);
    }

    /**
     * Sets the lock is deep.
     * 
     * @param value the new lock is deep
     */
    public void setLockIsDeep(boolean value)
    {
        getSemanticObject().setBooleanProperty(jcr_lockIsDeep, value);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.repository.office.Descriptiveable#getTitle()
     */
    public String getTitle()
    {
        return getSemanticObject().getProperty(swboffice_title);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.repository.office.Descriptiveable#setTitle(java.lang.String)
     */
    public void setTitle(String value)
    {
        getSemanticObject().setProperty(swboffice_title, value);
    }

    /**
     * Gets the last modified.
     * 
     * @return the last modified
     */
    public java.util.Date getLastModified()
    {
        return getSemanticObject().getDateProperty(swboffice_lastModified);
    }

    /**
     * Sets the last modified.
     * 
     * @param value the new last modified
     */
    public void setLastModified(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swboffice_lastModified, value);
    }

    /**
     * Gets the lock owner.
     * 
     * @return the lock owner
     */
    public String getLockOwner()
    {
        return getSemanticObject().getProperty(jcr_lockOwner);
    }

    /**
     * Sets the lock owner.
     * 
     * @param value the new lock owner
     */
    public void setLockOwner(String value)
    {
        getSemanticObject().setProperty(jcr_lockOwner, value);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.repository.office.Descriptiveable#getDescription()
     */
    public String getDescription()
    {
        return getSemanticObject().getProperty(swboffice_description);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.repository.office.Descriptiveable#setDescription(java.lang.String)
     */
    public void setDescription(String value)
    {
        getSemanticObject().setProperty(swboffice_description, value);
    }
}
