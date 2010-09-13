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
 * The Class OfficeDocumentBase.
 */
public abstract class OfficeDocumentBase extends org.semanticwb.repository.Resource implements org.semanticwb.repository.Referenceable,org.semanticwb.repository.office.Traceable
{
    
    /** The Constant swboffice_file. */
    public static final org.semanticwb.platform.SemanticProperty swboffice_file=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/office#file");
    
    /** The Constant swboffice_OfficeDocument. */
    public static final org.semanticwb.platform.SemanticClass swboffice_OfficeDocument=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/office#OfficeDocument");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/office#OfficeDocument");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {

        /**
         * List office documents.
         * 
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.repository.office.OfficeDocument> listOfficeDocuments(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.repository.office.OfficeDocument>(it, true);
        }

        /**
         * List office documents.
         * 
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.repository.office.OfficeDocument> listOfficeDocuments()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.repository.office.OfficeDocument>(it, true);
        }

        /**
         * Gets the office document.
         * 
         * @param id the id
         * @param model the model
         * @return the office document
         */
        public static org.semanticwb.repository.office.OfficeDocument getOfficeDocument(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.repository.office.OfficeDocument)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        /**
         * Creates the office document.
         * 
         * @param id the id
         * @param model the model
         * @return the org.semanticwb.repository.office. office document
         */
        public static org.semanticwb.repository.office.OfficeDocument createOfficeDocument(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.repository.office.OfficeDocument)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        /**
         * Removes the office document.
         * 
         * @param id the id
         * @param model the model
         */
        public static void removeOfficeDocument(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        /**
         * Checks for office document.
         * 
         * @param id the id
         * @param model the model
         * @return true, if successful
         */
        public static boolean hasOfficeDocument(String id, org.semanticwb.model.SWBModel model)
        {
            return (getOfficeDocument(id, model)!=null);
        }

        /**
         * List office document by parent.
         * 
         * @param value the value
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.repository.office.OfficeDocument> listOfficeDocumentByParent(org.semanticwb.repository.BaseNode value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.office.OfficeDocument> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swbrep_parentNode, value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List office document by parent.
         * 
         * @param value the value
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.repository.office.OfficeDocument> listOfficeDocumentByParent(org.semanticwb.repository.BaseNode value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.office.OfficeDocument> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swbrep_parentNode,value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List office document by node.
         * 
         * @param value the value
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.repository.office.OfficeDocument> listOfficeDocumentByNode(org.semanticwb.repository.BaseNode value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.office.OfficeDocument> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swbrep_hasNodes, value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List office document by node.
         * 
         * @param value the value
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.repository.office.OfficeDocument> listOfficeDocumentByNode(org.semanticwb.repository.BaseNode value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.office.OfficeDocument> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swbrep_hasNodes,value.getSemanticObject(),sclass));
            return it;
        }
    }

    /**
     * Instantiates a new office document base.
     * 
     * @param base the base
     */
    public OfficeDocumentBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /**
     * Gets the file.
     * 
     * @return the file
     */
    public String getFile()
    {
        return getSemanticObject().getProperty(swboffice_file);
    }

    /**
     * Sets the file.
     * 
     * @param value the new file
     */
    public void setFile(String value)
    {
        getSemanticObject().setProperty(swboffice_file, value);
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
}
