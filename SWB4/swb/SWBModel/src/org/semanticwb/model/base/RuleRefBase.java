package org.semanticwb.model.base;


public class RuleRefBase extends org.semanticwb.model.Reference implements org.semanticwb.model.Activeable
{
    public static final org.semanticwb.platform.SemanticClass swb_Rule=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Rule");
    public static final org.semanticwb.platform.SemanticProperty swb_rule=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#rule");
    public static final org.semanticwb.platform.SemanticClass swb_RuleRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#RuleRef");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#RuleRef");

    public RuleRefBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static org.semanticwb.model.RuleRef getRuleRef(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.RuleRef)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static java.util.Iterator<org.semanticwb.model.RuleRef> listRuleRefs(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.RuleRef>(org.semanticwb.model.RuleRef.class, it, true);
    }

    public static java.util.Iterator<org.semanticwb.model.RuleRef> listRuleRefs()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.RuleRef>(org.semanticwb.model.RuleRef.class, it, true);
    }

    public static org.semanticwb.model.RuleRef createRuleRef(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.RuleRef)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static org.semanticwb.model.RuleRef createRuleRef(org.semanticwb.model.SWBModel model)
    {
        long id=org.semanticwb.SWBPlatform.getSemanticMgr().getCounter(model.getSemanticObject().getModel().getName()+"/"+sclass.getName());
        return org.semanticwb.model.RuleRef.createRuleRef(String.valueOf(id), model);
    }

    public static void removeRuleRef(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasRuleRef(String id, org.semanticwb.model.SWBModel model)
    {
        return (getRuleRef(id, model)!=null);
    }

    public void setRule(org.semanticwb.model.Rule rule)
    {
        getSemanticObject().setObjectProperty(swb_rule, rule.getSemanticObject());
    }

    public void removeRule()
    {
        getSemanticObject().removeProperty(swb_rule);
    }

    public org.semanticwb.model.Rule getRule()
    {
         org.semanticwb.model.Rule ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_rule);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Rule)obj.getSemanticClass().newGenericInstance(obj);
         }
         return ret;
    }

    public org.semanticwb.model.WebSite getWebSite()
    {
        return new org.semanticwb.model.WebSite(getSemanticObject().getModel().getModelObject());
    }
}
