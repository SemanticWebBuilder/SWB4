package org.semanticwb.process.model.base;


public abstract class CorrelationPropertyBase extends org.semanticwb.process.model.BPMNBaseElement implements org.semanticwb.process.model.Documentable
{
    public static final org.semanticwb.platform.SemanticClass swp_CorrelationPropertyRetrievalExpression=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#CorrelationPropertyRetrievalExpression");
    public static final org.semanticwb.platform.SemanticProperty swp_hasCorrelationPropertyRetrievalExpression=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasCorrelationPropertyRetrievalExpression");
    public static final org.semanticwb.platform.SemanticClass swp_CorrelationProperty=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#CorrelationProperty");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#CorrelationProperty");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.CorrelationProperty> listCorrelationProperties(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationProperty>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.CorrelationProperty> listCorrelationProperties()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationProperty>(it, true);
        }

        public static org.semanticwb.process.model.CorrelationProperty createCorrelationProperty(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.CorrelationProperty.ClassMgr.createCorrelationProperty(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.CorrelationProperty getCorrelationProperty(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.CorrelationProperty)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.CorrelationProperty createCorrelationProperty(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.CorrelationProperty)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeCorrelationProperty(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasCorrelationProperty(String id, org.semanticwb.model.SWBModel model)
        {
            return (getCorrelationProperty(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.CorrelationProperty> listCorrelationPropertyByCorrelationPropertyRetrievalExpression(org.semanticwb.process.model.CorrelationPropertyRetrievalExpression value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationProperty> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasCorrelationPropertyRetrievalExpression, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CorrelationProperty> listCorrelationPropertyByCorrelationPropertyRetrievalExpression(org.semanticwb.process.model.CorrelationPropertyRetrievalExpression value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationProperty> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasCorrelationPropertyRetrievalExpression,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CorrelationProperty> listCorrelationPropertyByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationProperty> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CorrelationProperty> listCorrelationPropertyByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationProperty> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CorrelationProperty> listCorrelationPropertyByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationProperty> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CorrelationProperty> listCorrelationPropertyByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationProperty> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CorrelationProperty> listCorrelationPropertyByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationProperty> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CorrelationProperty> listCorrelationPropertyByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationProperty> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public CorrelationPropertyBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationPropertyRetrievalExpression> listCorrelationPropertyRetrievalExpressions()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationPropertyRetrievalExpression>(getSemanticObject().listObjectProperties(swp_hasCorrelationPropertyRetrievalExpression));
    }

    public boolean hasCorrelationPropertyRetrievalExpression(org.semanticwb.process.model.CorrelationPropertyRetrievalExpression value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasCorrelationPropertyRetrievalExpression,value.getSemanticObject());
        }
        return ret;
    }

    public void addCorrelationPropertyRetrievalExpression(org.semanticwb.process.model.CorrelationPropertyRetrievalExpression value)
    {
        getSemanticObject().addObjectProperty(swp_hasCorrelationPropertyRetrievalExpression, value.getSemanticObject());
    }

    public void removeAllCorrelationPropertyRetrievalExpression()
    {
        getSemanticObject().removeProperty(swp_hasCorrelationPropertyRetrievalExpression);
    }

    public void removeCorrelationPropertyRetrievalExpression(org.semanticwb.process.model.CorrelationPropertyRetrievalExpression value)
    {
        getSemanticObject().removeObjectProperty(swp_hasCorrelationPropertyRetrievalExpression,value.getSemanticObject());
    }

    public org.semanticwb.process.model.CorrelationPropertyRetrievalExpression getCorrelationPropertyRetrievalExpression()
    {
         org.semanticwb.process.model.CorrelationPropertyRetrievalExpression ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasCorrelationPropertyRetrievalExpression);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.CorrelationPropertyRetrievalExpression)obj.createGenericInstance();
         }
         return ret;
    }
}
