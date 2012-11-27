/*
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
 */
package org.semanticwb.repository.base;


// TODO: Auto-generated Javadoc
/**
 * The Class ResourceBase.
 */
public abstract class ResourceBase extends org.semanticwb.repository.BaseNode implements org.semanticwb.repository.Referenceable
{
    
    /** The Constant jcr_mimeType. */
    public static final org.semanticwb.platform.SemanticProperty jcr_mimeType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#mimeType");
    
    /** The Constant jcr_encoding. */
    public static final org.semanticwb.platform.SemanticProperty jcr_encoding=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#encoding");
    
    /** The Constant jcr_lastModified. */
    public static final org.semanticwb.platform.SemanticProperty jcr_lastModified=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#lastModified");
    
    /** The Constant jcr_data. */
    public static final org.semanticwb.platform.SemanticProperty jcr_data=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#data");
    
    /** The Constant nt_Resource. */
    public static final org.semanticwb.platform.SemanticClass nt_Resource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#resource");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#resource");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {

        /**
         * List resources.
         * 
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.repository.Resource> listResources(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.repository.Resource>(it, true);
        }

        /**
         * List resources.
         * 
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.repository.Resource> listResources()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.repository.Resource>(it, true);
        }

        /**
         * Gets the resource.
         * 
         * @param id the id
         * @param model the model
         * @return the resource
         */
        public static org.semanticwb.repository.Resource getResource(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.repository.Resource)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        /**
         * Creates the resource.
         * 
         * @param id the id
         * @param model the model
         * @return the org.semanticwb.repository. resource
         */
        public static org.semanticwb.repository.Resource createResource(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.repository.Resource)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        /**
         * Removes the resource.
         * 
         * @param id the id
         * @param model the model
         */
        public static void removeResource(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        /**
         * Checks for resource.
         * 
         * @param id the id
         * @param model the model
         * @return true, if successful
         */
        public static boolean hasResource(String id, org.semanticwb.model.SWBModel model)
        {
            return (getResource(id, model)!=null);
        }

        /**
         * List resource by parent.
         * 
         * @param parentnode the parentnode
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.repository.Resource> listResourceByParent(org.semanticwb.repository.BaseNode parentnode,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.Resource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbrep_parentNode, parentnode.getSemanticObject()));
            return it;
        }

        /**
         * List resource by parent.
         * 
         * @param parentnode the parentnode
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.repository.Resource> listResourceByParent(org.semanticwb.repository.BaseNode parentnode)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.Resource> it=new org.semanticwb.model.GenericIterator(parentnode.getSemanticObject().getModel().listSubjects(swbrep_parentNode,parentnode.getSemanticObject()));
            return it;
        }

        /**
         * List resource by node.
         * 
         * @param hasnodes the hasnodes
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.repository.Resource> listResourceByNode(org.semanticwb.repository.BaseNode hasnodes,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.Resource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbrep_hasNodes, hasnodes.getSemanticObject()));
            return it;
        }

        /**
         * List resource by node.
         * 
         * @param hasnodes the hasnodes
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.repository.Resource> listResourceByNode(org.semanticwb.repository.BaseNode hasnodes)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.Resource> it=new org.semanticwb.model.GenericIterator(hasnodes.getSemanticObject().getModel().listSubjects(swbrep_hasNodes,hasnodes.getSemanticObject()));
            return it;
        }
    }

    /**
     * Instantiates a new resource base.
     * 
     * @param base the base
     */
    public ResourceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /**
     * Gets the mime type.
     * 
     * @return the mime type
     */
    public String getMimeType()
    {
        return getSemanticObject().getProperty(jcr_mimeType);
    }

    /**
     * Sets the mime type.
     * 
     * @param value the new mime type
     */
    public void setMimeType(String value)
    {
        getSemanticObject().setProperty(jcr_mimeType, value);
    }

    /**
     * Gets the encoding.
     * 
     * @return the encoding
     */
    public String getEncoding()
    {
        return getSemanticObject().getProperty(jcr_encoding);
    }

    /**
     * Sets the encoding.
     * 
     * @param value the new encoding
     */
    public void setEncoding(String value)
    {
        getSemanticObject().setProperty(jcr_encoding, value);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.repository.base.ReferenceableBase#getUuid()
     */
    public String getUuid()
    {
        return getSemanticObject().getProperty(jcr_uuid);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.repository.base.ReferenceableBase#setUuid(java.lang.String)
     */
    public void setUuid(String value)
    {
        getSemanticObject().setProperty(jcr_uuid, value);
    }

    /**
     * Gets the last modified.
     * 
     * @return the last modified
     */
    public java.util.Date getLastModified()
    {
        return getSemanticObject().getDateProperty(jcr_lastModified);
    }

    /**
     * Sets the last modified.
     * 
     * @param value the new last modified
     */
    public void setLastModified(java.util.Date value)
    {
        getSemanticObject().setDateProperty(jcr_lastModified, value);
    }

    /**
     * Gets the data.
     * 
     * @return the data
     * @throws Exception the exception
     */
    public java.io.InputStream getData() throws Exception
    {
        return getSemanticObject().getInputStreamProperty(jcr_data);
    }

    /**
     * Sets the data.
     * 
     * @param value the value
     * @param name the name
     * @throws Exception the exception
     */
    public void setData(java.io.InputStream value,String name) throws Exception
    {
        getSemanticObject().setInputStreamProperty(jcr_data, value,name);
    }
}
