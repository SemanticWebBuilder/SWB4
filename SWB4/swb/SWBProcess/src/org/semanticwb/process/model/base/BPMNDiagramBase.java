package org.semanticwb.process.model.base;


public abstract class BPMNDiagramBase extends org.semanticwb.model.base.GenericObjectBase 
{
    public static final org.semanticwb.platform.SemanticClass swp_BPMNBaseElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#BPMNBaseElement");
    public static final org.semanticwb.platform.SemanticProperty swp_contextType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#contextType");
    public static final org.semanticwb.platform.SemanticProperty swb_valid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#valid");
    public static final org.semanticwb.platform.SemanticClass swp_BPMNDiagram=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#BPMNDiagram");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#BPMNDiagram");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.BPMNDiagram> listBPMNDiagrams(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNDiagram>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.BPMNDiagram> listBPMNDiagrams()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNDiagram>(it, true);
        }

        public static org.semanticwb.process.model.BPMNDiagram createBPMNDiagram(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.BPMNDiagram.ClassMgr.createBPMNDiagram(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.BPMNDiagram getBPMNDiagram(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.BPMNDiagram)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.BPMNDiagram createBPMNDiagram(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.BPMNDiagram)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeBPMNDiagram(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasBPMNDiagram(String id, org.semanticwb.model.SWBModel model)
        {
            return (getBPMNDiagram(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.BPMNDiagram> listBPMNDiagramByContextType(org.semanticwb.process.model.BPMNBaseElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNDiagram> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_contextType, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.BPMNDiagram> listBPMNDiagramByContextType(org.semanticwb.process.model.BPMNBaseElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNDiagram> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_contextType,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public BPMNDiagramBase(org.semanticwb.platform.SemanticObject base)
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
        //Override this method in BPMNDiagram object
        return getSemanticObject().getBooleanProperty(swb_valid,false);
    }

    public void setValid(boolean value)
    {
        //Override this method in BPMNDiagram object
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
