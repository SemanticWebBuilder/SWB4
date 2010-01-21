package org.semanticwb.jcr283.repository.model.base;


public abstract class AddressBase extends org.semanticwb.jcr283.repository.model.Base 
{
       public static final org.semanticwb.platform.SemanticClass xsd_String=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.w3.org/2001/XMLSchema#string");
       public static final org.semanticwb.platform.SemanticProperty jcr_protocol=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#protocol");
       public static final org.semanticwb.platform.SemanticProperty jcr_port=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#port");
       public static final org.semanticwb.platform.SemanticProperty jcr_host=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#host");
       public static final org.semanticwb.platform.SemanticProperty jcr_repository=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#repository");
       public static final org.semanticwb.platform.SemanticProperty jcr_workspace=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#workspace");
       public static final org.semanticwb.platform.SemanticClass nt_Address=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#address");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#address");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.jcr283.repository.model.Address> listAddresses(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.Address>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.jcr283.repository.model.Address> listAddresses()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.Address>(it, true);
       }

       public static org.semanticwb.jcr283.repository.model.Address getAddress(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.jcr283.repository.model.Address)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.jcr283.repository.model.Address createAddress(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.jcr283.repository.model.Address)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeAddress(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasAddress(String id, org.semanticwb.model.SWBModel model)
       {
           return (getAddress(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.jcr283.repository.model.Address> listAddressByParentNode(org.semanticwb.jcr283.repository.model.Base parentnode,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.Address> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbrep_parentNode, parentnode.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.jcr283.repository.model.Address> listAddressByParentNode(org.semanticwb.jcr283.repository.model.Base parentnode)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.Address> it=new org.semanticwb.model.GenericIterator(parentnode.getSemanticObject().getModel().listSubjects(swbrep_parentNode,parentnode.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.jcr283.repository.model.Address> listAddressByNode(org.semanticwb.jcr283.repository.model.Base hasnode,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.Address> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbrep_hasNode, hasnode.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.jcr283.repository.model.Address> listAddressByNode(org.semanticwb.jcr283.repository.model.Base hasnode)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.Address> it=new org.semanticwb.model.GenericIterator(hasnode.getSemanticObject().getModel().listSubjects(swbrep_hasNode,hasnode.getSemanticObject()));
       return it;
   }
    }

    public AddressBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public String getProtocol()
    {
        return getSemanticObject().getProperty(jcr_protocol);
    }

    public void setProtocol(String value)
    {
        getSemanticObject().setProperty(jcr_protocol, value);
    }

    public String getPort()
    {
        return getSemanticObject().getProperty(jcr_port);
    }

    public void setPort(String value)
    {
        getSemanticObject().setProperty(jcr_port, value);
    }

    public String getHost()
    {
        return getSemanticObject().getProperty(jcr_host);
    }

    public void setHost(String value)
    {
        getSemanticObject().setProperty(jcr_host, value);
    }

    public String getRepository()
    {
        return getSemanticObject().getProperty(jcr_repository);
    }

    public void setRepository(String value)
    {
        getSemanticObject().setProperty(jcr_repository, value);
    }

    public String getWorkspaceAsString()
    {
        return getSemanticObject().getProperty(jcr_workspace);
    }

    public void setWorkspaceAsString(String value)
    {
        getSemanticObject().setProperty(jcr_workspace, value);
    }
}
