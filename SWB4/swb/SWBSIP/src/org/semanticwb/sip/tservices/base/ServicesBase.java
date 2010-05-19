package org.semanticwb.sip.tservices.base;


public abstract class ServicesBase extends org.semanticwb.portal.api.GenericSemResource 
{
    public static final org.semanticwb.platform.SemanticClass serv_Service=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://org.semanticwb.sip/Services#Service");
    public static final org.semanticwb.platform.SemanticProperty serv_hasService=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://org.semanticwb.sip/Services#hasService");
    public static final org.semanticwb.platform.SemanticProperty serv_maxServView=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://org.semanticwb.sip/Services#maxServView");
    public static final org.semanticwb.platform.SemanticClass serv_Services=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://org.semanticwb.sip/Services#Services");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://org.semanticwb.sip/Services#Services");

    public ServicesBase()
    {
    }

    public ServicesBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.sip.tservices.Service> listServices()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.sip.tservices.Service>(getSemanticObject().listObjectProperties(serv_hasService));
    }

    public boolean hasService(org.semanticwb.sip.tservices.Service value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(serv_hasService,value.getSemanticObject());
        }
        return ret;
    }

    public void addService(org.semanticwb.sip.tservices.Service value)
    {
        getSemanticObject().addObjectProperty(serv_hasService, value.getSemanticObject());
    }

    public void removeAllService()
    {
        getSemanticObject().removeProperty(serv_hasService);
    }

    public void removeService(org.semanticwb.sip.tservices.Service value)
    {
        getSemanticObject().removeObjectProperty(serv_hasService,value.getSemanticObject());
    }

    public org.semanticwb.sip.tservices.Service getService()
    {
         org.semanticwb.sip.tservices.Service ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(serv_hasService);
         if(obj!=null)
         {
             ret=(org.semanticwb.sip.tservices.Service)obj.createGenericInstance();
         }
         return ret;
    }

    public int getMaxServView()
    {
        return getSemanticObject().getIntProperty(serv_maxServView);
    }

    public void setMaxServView(int value)
    {
        getSemanticObject().setIntProperty(serv_maxServView, value);
    }
}
