package org.semanticwb.model.base;


public class RuleRefBase extends org.semanticwb.model.Reference implements org.semanticwb.model.Activeable
{
    public static final org.semanticwb.platform.SemanticClass swb_Rule=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Rule");
    public static final org.semanticwb.platform.SemanticProperty swb_rule=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#rule");
    public static final org.semanticwb.platform.SemanticClass swb_RuleRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#RuleRef");

    public RuleRefBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
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
