package org.semanticwb.process.model.base;


public abstract class BPMNStyleDefinitionBase extends org.semanticwb.process.model.BPMNElement 
{
    public static final org.semanticwb.platform.SemanticProperty swp_width=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#width");
    public static final org.semanticwb.platform.SemanticProperty swp_height=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#height");
    public static final org.semanticwb.platform.SemanticProperty swp_y=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#y");
    public static final org.semanticwb.platform.SemanticProperty swp_x=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#x");
    public static final org.semanticwb.platform.SemanticClass swp_BPMNStyleDefinition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#BPMNStyleDefinition");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#BPMNStyleDefinition");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.BPMNStyleDefinition> listBPMNStyleDefinitions(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNStyleDefinition>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.BPMNStyleDefinition> listBPMNStyleDefinitions()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNStyleDefinition>(it, true);
        }

        public static org.semanticwb.process.model.BPMNStyleDefinition createBPMNStyleDefinition(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.BPMNStyleDefinition.ClassMgr.createBPMNStyleDefinition(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.BPMNStyleDefinition getBPMNStyleDefinition(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.BPMNStyleDefinition)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.BPMNStyleDefinition createBPMNStyleDefinition(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.BPMNStyleDefinition)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeBPMNStyleDefinition(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasBPMNStyleDefinition(String id, org.semanticwb.model.SWBModel model)
        {
            return (getBPMNStyleDefinition(id, model)!=null);
        }
    }

    public BPMNStyleDefinitionBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public int getWidth()
    {
        return getSemanticObject().getIntProperty(swp_width);
    }

    public void setWidth(int value)
    {
        getSemanticObject().setIntProperty(swp_width, value);
    }

    public int getHeight()
    {
        return getSemanticObject().getIntProperty(swp_height);
    }

    public void setHeight(int value)
    {
        getSemanticObject().setIntProperty(swp_height, value);
    }

    public int getY()
    {
        return getSemanticObject().getIntProperty(swp_y);
    }

    public void setY(int value)
    {
        getSemanticObject().setIntProperty(swp_y, value);
    }

    public int getX()
    {
        return getSemanticObject().getIntProperty(swp_x);
    }

    public void setX(int value)
    {
        getSemanticObject().setIntProperty(swp_x, value);
    }

    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
