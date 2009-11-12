package org.semanticwb.portal.community.base;


public class CURPModuleBase extends org.semanticwb.portal.community.Organization implements org.semanticwb.model.Rankable,org.semanticwb.portal.community.Contactable,org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable,org.semanticwb.portal.community.Addressable,org.semanticwb.model.Geolocalizable,org.semanticwb.portal.community.Interactiveable
{
       public static final org.semanticwb.platform.SemanticClass swbcomm_CURPModule=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#CURPModule");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#CURPModule");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.portal.community.CURPModule> listCURPModules(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.CURPModule>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.portal.community.CURPModule> listCURPModules()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.CURPModule>(it, true);
       }

       public static org.semanticwb.portal.community.CURPModule createCURPModule(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.portal.community.CURPModule.ClassMgr.createCURPModule(String.valueOf(id), model);
       }

       public static org.semanticwb.portal.community.CURPModule getCURPModule(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.portal.community.CURPModule)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.portal.community.CURPModule createCURPModule(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.portal.community.CURPModule)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeCURPModule(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasCURPModule(String id, org.semanticwb.model.SWBModel model)
       {
           return (getCURPModule(id, model)!=null);
       }
    }

    public CURPModuleBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
