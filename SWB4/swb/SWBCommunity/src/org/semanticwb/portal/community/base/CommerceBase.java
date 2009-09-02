package org.semanticwb.portal.community.base;


public class CommerceBase extends org.semanticwb.portal.community.Organization implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Geolocalizable,org.semanticwb.portal.community.FacilitiesEnable,org.semanticwb.portal.community.Contactable,org.semanticwb.model.Traceable,org.semanticwb.portal.community.Addressable
{
    public static final org.semanticwb.platform.SemanticClass swbcomm_Commerce=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Commerce");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Commerce");

    public CommerceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.portal.community.Commerce> listCommerces(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Commerce>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.portal.community.Commerce> listCommerces()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Commerce>(it, true);
    }

    public static org.semanticwb.portal.community.Commerce createCommerce(org.semanticwb.model.SWBModel model)
    {
        long id=model.getSemanticObject().getModel().getCounter(sclass);
        return org.semanticwb.portal.community.Commerce.createCommerce(String.valueOf(id), model);
    }

    public static org.semanticwb.portal.community.Commerce getCommerce(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.community.Commerce)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.portal.community.Commerce createCommerce(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.community.Commerce)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeCommerce(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasCommerce(String id, org.semanticwb.model.SWBModel model)
    {
        return (getCommerce(id, model)!=null);
    }
}
