package org.semanticwb.process.model.base;


public abstract class BPMNNodeBase extends org.semanticwb.model.base.GenericObjectBase 
{
    public static final org.semanticwb.platform.SemanticClass swp_BPMNBaseElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#BPMNBaseElement");
    public static final org.semanticwb.platform.SemanticProperty swp_contextType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#contextType");
    public static final org.semanticwb.platform.SemanticProperty swb_valid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#valid");
    public static final org.semanticwb.platform.SemanticClass swp_BPMNNode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#BPMNNode");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#BPMNNode");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.BPMNNode> listBPMNNodes(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNNode>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.BPMNNode> listBPMNNodes()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNNode>(it, true);
        }

        public static org.semanticwb.process.model.BPMNNode createBPMNNode(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.BPMNNode.ClassMgr.createBPMNNode(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.BPMNNode getBPMNNode(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.BPMNNode)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.BPMNNode createBPMNNode(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.BPMNNode)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeBPMNNode(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasBPMNNode(String id, org.semanticwb.model.SWBModel model)
        {
            return (getBPMNNode(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.BPMNNode> listBPMNNodeByContextType(org.semanticwb.process.model.BPMNBaseElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNNode> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_contextType, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.BPMNNode> listBPMNNodeByContextType(org.semanticwb.process.model.BPMNBaseElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNNode> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_contextType,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public BPMNNodeBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setContextType(org.semanticwb.process.model.BPMNBaseElement value)
    {
        getSemanticObject().setObjectProperty(swp_contextType, value.getSemanticObject());
    }

    public void removeContextType()
    {
        getSemanticObject().removeProperty(swp_contextType);
    }

    public org.semanticwb.process.model.BPMNBaseElement getContextType()
    {
         org.semanticwb.process.model.BPMNBaseElement ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_contextType);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.BPMNBaseElement)obj.createGenericInstance();
         }
         return ret;
    }

    public boolean isValid()
    {
        //Override this method in BPMNNode object
        return getSemanticObject().getBooleanProperty(swb_valid,false);
    }

    public void setValid(boolean value)
    {
        //Override this method in BPMNNode object
        getSemanticObject().setBooleanProperty(swb_valid, value,false);
    }

    public void remove()
    {
        getSemanticObject().remove();
    }

    public java.util.Iterator<org.semanticwb.model.GenericObject> listRelatedObjects()
    {
        return new org.semanticwb.model.GenericIterator(getSemanticObject().listRelatedObjects(),true);
    }
}
