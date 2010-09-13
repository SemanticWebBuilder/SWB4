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
 * The Class OfficeCategoryBase.
 */
public abstract class OfficeCategoryBase extends org.semanticwb.repository.Folder implements org.semanticwb.repository.Referenceable,org.semanticwb.repository.office.Descriptiveable,org.semanticwb.repository.Traceable,org.semanticwb.repository.office.Traceable
{
    
    /** The Constant swboffice_OfficeCategory. */
    public static final org.semanticwb.platform.SemanticClass swboffice_OfficeCategory=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/office#OfficeCategory");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/office#OfficeCategory");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {

        /**
         * List office categories.
         * 
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.repository.office.OfficeCategory> listOfficeCategories(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.repository.office.OfficeCategory>(it, true);
        }

        /**
         * List office categories.
         * 
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.repository.office.OfficeCategory> listOfficeCategories()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.repository.office.OfficeCategory>(it, true);
        }

        /**
         * Gets the office category.
         * 
         * @param id the id
         * @param model the model
         * @return the office category
         */
        public static org.semanticwb.repository.office.OfficeCategory getOfficeCategory(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.repository.office.OfficeCategory)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        /**
         * Creates the office category.
         * 
         * @param id the id
         * @param model the model
         * @return the org.semanticwb.repository.office. office category
         */
        public static org.semanticwb.repository.office.OfficeCategory createOfficeCategory(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.repository.office.OfficeCategory)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        /**
         * Removes the office category.
         * 
         * @param id the id
         * @param model the model
         */
        public static void removeOfficeCategory(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        /**
         * Checks for office category.
         * 
         * @param id the id
         * @param model the model
         * @return true, if successful
         */
        public static boolean hasOfficeCategory(String id, org.semanticwb.model.SWBModel model)
        {
            return (getOfficeCategory(id, model)!=null);
        }

        /**
         * List office category by parent.
         * 
         * @param value the value
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.repository.office.OfficeCategory> listOfficeCategoryByParent(org.semanticwb.repository.BaseNode value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.office.OfficeCategory> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swbrep_parentNode, value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List office category by parent.
         * 
         * @param value the value
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.repository.office.OfficeCategory> listOfficeCategoryByParent(org.semanticwb.repository.BaseNode value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.office.OfficeCategory> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swbrep_parentNode,value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List office category by node.
         * 
         * @param value the value
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.repository.office.OfficeCategory> listOfficeCategoryByNode(org.semanticwb.repository.BaseNode value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.office.OfficeCategory> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swbrep_hasNodes, value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List office category by node.
         * 
         * @param value the value
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.repository.office.OfficeCategory> listOfficeCategoryByNode(org.semanticwb.repository.BaseNode value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.office.OfficeCategory> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swbrep_hasNodes,value.getSemanticObject(),sclass));
            return it;
        }
    }

    /**
     * Instantiates a new office category base.
     * 
     * @param base the base
     */
    public OfficeCategoryBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
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

    /* (non-Javadoc)
     * @see org.semanticwb.repository.office.Traceable#getUser()
     */
    public String getUser()
    {
        return getSemanticObject().getProperty(swboffice_user);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.repository.office.Traceable#setUser(java.lang.String)
     */
    public void setUser(String value)
    {
        getSemanticObject().setProperty(swboffice_user, value);
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
