package org.semanticwb.repository.base;


public class ResourceBase extends org.semanticwb.repository.BaseNode implements org.semanticwb.repository.Referenceable
{
    public static final org.semanticwb.platform.SemanticProperty jcr_mimeType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#mimeType");
    public static final org.semanticwb.platform.SemanticProperty jcr_encoding=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#encoding");
    public static final org.semanticwb.platform.SemanticProperty jcr_uuid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#uuid");
    public static final org.semanticwb.platform.SemanticProperty jcr_lastModified=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#lastModified");
    public static final org.semanticwb.platform.SemanticProperty jcr_data=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#data");
    public static final org.semanticwb.platform.SemanticClass nt_Resource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#resource");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#resource");

    public ResourceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static org.semanticwb.repository.Resource getResource(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.repository.Resource)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static java.util.Iterator<org.semanticwb.repository.Resource> listResources(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.repository.Resource>(org.semanticwb.repository.Resource.class, it, true);
    }

    public static java.util.Iterator<org.semanticwb.repository.Resource> listResources()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.repository.Resource>(org.semanticwb.repository.Resource.class, it, true);
    }

    public static org.semanticwb.repository.Resource createResource(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.repository.Resource)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeResource(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasResource(String id, org.semanticwb.model.SWBModel model)
    {
        return (getResource(id, model)!=null);
    }

    public String getMimeType()
    {
        return getSemanticObject().getProperty(jcr_mimeType);
    }

    public void setMimeType(String mimeType)
    {
        getSemanticObject().setProperty(jcr_mimeType, mimeType);
    }

    public String getEncoding()
    {
        return getSemanticObject().getProperty(jcr_encoding);
    }

    public void setEncoding(String encoding)
    {
        getSemanticObject().setProperty(jcr_encoding, encoding);
    }

    public String getUuid()
    {
        return getSemanticObject().getProperty(jcr_uuid);
    }

    public void setUuid(String uuid)
    {
        getSemanticObject().setProperty(jcr_uuid, uuid);
    }

    public java.util.Date getLastModified()
    {
        return getSemanticObject().getDateProperty(jcr_lastModified);
    }

    public void setLastModified(java.util.Date lastModified)
    {
        getSemanticObject().setDateProperty(jcr_lastModified, lastModified);
    }

    public java.io.InputStream getData() throws Exception
    {
        return getSemanticObject().getInputStreamProperty(jcr_data);
    }

    public void setData(java.io.InputStream data,String name) throws Exception
    {
        getSemanticObject().setInputStreamProperty(jcr_data, data,name);
    }
}
