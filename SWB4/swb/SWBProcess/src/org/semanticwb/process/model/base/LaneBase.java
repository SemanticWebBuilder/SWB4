package org.semanticwb.process.model.base;


public abstract class LaneBase extends org.semanticwb.process.model.BPMNBaseElement implements org.semanticwb.process.model.Artifactable,org.semanticwb.process.model.Documentable,org.semanticwb.model.Descriptiveable,org.semanticwb.process.model.FlowElementsContainerable
{
    public static final org.semanticwb.platform.SemanticClass swp_BPMNBaseElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#BPMNBaseElement");
    public static final org.semanticwb.platform.SemanticProperty swp_partitionElementRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#partitionElementRef");
    public static final org.semanticwb.platform.SemanticProperty swp_partitionElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#partitionElement");
    public static final org.semanticwb.platform.SemanticClass swp_LaneSet=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#LaneSet");
    public static final org.semanticwb.platform.SemanticProperty swp_childLaneSet=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#childLaneSet");
    public static final org.semanticwb.platform.SemanticClass swp_Lane=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Lane");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Lane");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.Lane> listLanes(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Lane>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.Lane> listLanes()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Lane>(it, true);
        }

        public static org.semanticwb.process.model.Lane createLane(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.Lane.ClassMgr.createLane(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.Lane getLane(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.Lane)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.Lane createLane(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.Lane)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeLane(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasLane(String id, org.semanticwb.model.SWBModel model)
        {
            return (getLane(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.Lane> listLaneByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Lane> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Lane> listLaneByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Lane> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Lane> listLaneByPartitionElementRef(org.semanticwb.process.model.BPMNBaseElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Lane> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_partitionElementRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Lane> listLaneByPartitionElementRef(org.semanticwb.process.model.BPMNBaseElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Lane> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_partitionElementRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Lane> listLaneByArtifact(org.semanticwb.process.model.Artifact value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Lane> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasArtifact, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Lane> listLaneByArtifact(org.semanticwb.process.model.Artifact value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Lane> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasArtifact,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Lane> listLaneByPartitionElement(org.semanticwb.process.model.BPMNBaseElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Lane> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_partitionElement, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Lane> listLaneByPartitionElement(org.semanticwb.process.model.BPMNBaseElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Lane> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_partitionElement,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Lane> listLaneByChildLaneSet(org.semanticwb.process.model.LaneSet value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Lane> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_childLaneSet, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Lane> listLaneByChildLaneSet(org.semanticwb.process.model.LaneSet value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Lane> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_childLaneSet,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Lane> listLaneByFlowElement(org.semanticwb.process.model.FlowElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Lane> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowElement, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Lane> listLaneByFlowElement(org.semanticwb.process.model.FlowElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Lane> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowElement,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Lane> listLaneByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Lane> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Lane> listLaneByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Lane> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Lane> listLaneByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Lane> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Lane> listLaneByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Lane> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public LaneBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

    public void setTitle(String value)
    {
        getSemanticObject().setProperty(swb_title, value);
    }

    public String getTitle(String lang)
    {
        return getSemanticObject().getProperty(swb_title, null, lang);
    }

    public String getDisplayTitle(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_title, lang);
    }

    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(swb_title, title, lang);
    }

    public void setPartitionElementRef(org.semanticwb.process.model.BPMNBaseElement value)
    {
        getSemanticObject().setObjectProperty(swp_partitionElementRef, value.getSemanticObject());
    }

    public void removePartitionElementRef()
    {
        getSemanticObject().removeProperty(swp_partitionElementRef);
    }

    public org.semanticwb.process.model.BPMNBaseElement getPartitionElementRef()
    {
         org.semanticwb.process.model.BPMNBaseElement ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_partitionElementRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.BPMNBaseElement)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Artifact> listArtifacts()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Artifact>(getSemanticObject().listObjectProperties(swp_hasArtifact));
    }

    public boolean hasArtifact(org.semanticwb.process.model.Artifact value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasArtifact,value.getSemanticObject());
        }
        return ret;
    }

    public void addArtifact(org.semanticwb.process.model.Artifact value)
    {
        getSemanticObject().addObjectProperty(swp_hasArtifact, value.getSemanticObject());
    }

    public void removeAllArtifact()
    {
        getSemanticObject().removeProperty(swp_hasArtifact);
    }

    public void removeArtifact(org.semanticwb.process.model.Artifact value)
    {
        getSemanticObject().removeObjectProperty(swp_hasArtifact,value.getSemanticObject());
    }

    public org.semanticwb.process.model.Artifact getArtifact()
    {
         org.semanticwb.process.model.Artifact ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasArtifact);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Artifact)obj.createGenericInstance();
         }
         return ret;
    }

    public void setPartitionElement(org.semanticwb.process.model.BPMNBaseElement value)
    {
        getSemanticObject().setObjectProperty(swp_partitionElement, value.getSemanticObject());
    }

    public void removePartitionElement()
    {
        getSemanticObject().removeProperty(swp_partitionElement);
    }

    public org.semanticwb.process.model.BPMNBaseElement getPartitionElement()
    {
         org.semanticwb.process.model.BPMNBaseElement ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_partitionElement);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.BPMNBaseElement)obj.createGenericInstance();
         }
         return ret;
    }

    public void setChildLaneSet(org.semanticwb.process.model.LaneSet value)
    {
        getSemanticObject().setObjectProperty(swp_childLaneSet, value.getSemanticObject());
    }

    public void removeChildLaneSet()
    {
        getSemanticObject().removeProperty(swp_childLaneSet);
    }

    public org.semanticwb.process.model.LaneSet getChildLaneSet()
    {
         org.semanticwb.process.model.LaneSet ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_childLaneSet);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.LaneSet)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowElement> listFlowElements()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowElement>(getSemanticObject().listObjectProperties(swp_hasFlowElement));
    }

    public boolean hasFlowElement(org.semanticwb.process.model.FlowElement value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasFlowElement,value.getSemanticObject());
        }
        return ret;
    }

    public void addFlowElement(org.semanticwb.process.model.FlowElement value)
    {
        getSemanticObject().addObjectProperty(swp_hasFlowElement, value.getSemanticObject());
    }

    public void removeAllFlowElement()
    {
        getSemanticObject().removeProperty(swp_hasFlowElement);
    }

    public void removeFlowElement(org.semanticwb.process.model.FlowElement value)
    {
        getSemanticObject().removeObjectProperty(swp_hasFlowElement,value.getSemanticObject());
    }

    public org.semanticwb.process.model.FlowElement getFlowElement()
    {
         org.semanticwb.process.model.FlowElement ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasFlowElement);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.FlowElement)obj.createGenericInstance();
         }
         return ret;
    }

    public String getDescription()
    {
        return getSemanticObject().getProperty(swb_description);
    }

    public void setDescription(String value)
    {
        getSemanticObject().setProperty(swb_description, value);
    }

    public String getDescription(String lang)
    {
        return getSemanticObject().getProperty(swb_description, null, lang);
    }

    public String getDisplayDescription(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_description, lang);
    }

    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(swb_description, description, lang);
    }
}
