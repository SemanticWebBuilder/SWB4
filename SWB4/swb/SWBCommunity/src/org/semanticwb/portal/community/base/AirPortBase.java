package org.semanticwb.portal.community.base;


public class AirPortBase extends org.semanticwb.portal.community.Commerce implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Geolocalizable,org.semanticwb.portal.community.FacilitiesEnable,org.semanticwb.portal.community.Contactable,org.semanticwb.model.Traceable,org.semanticwb.portal.community.Addressable
{
    public static final org.semanticwb.platform.SemanticClass swbcomm_AirPort=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#AirPort");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#AirPort");

    public AirPortBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.portal.community.AirPort> listAirPorts(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.AirPort>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.portal.community.AirPort> listAirPorts()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.AirPort>(it, true);
    }

    public static org.semanticwb.portal.community.AirPort createAirPort(org.semanticwb.model.SWBModel model)
    {
        long id=model.getSemanticObject().getModel().getCounter(sclass);
        return org.semanticwb.portal.community.AirPort.createAirPort(String.valueOf(id), model);
    }

    public static org.semanticwb.portal.community.AirPort getAirPort(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.community.AirPort)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.portal.community.AirPort createAirPort(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.community.AirPort)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeAirPort(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasAirPort(String id, org.semanticwb.model.SWBModel model)
    {
        return (getAirPort(id, model)!=null);
    }
}
