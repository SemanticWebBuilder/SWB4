package org.semanticwb.process.model.base;


public abstract class LaneSetBase extends org.semanticwb.process.model.BPMNBaseElement implements org.semanticwb.process.model.Laneable,org.semanticwb.model.Descriptiveable,org.semanticwb.process.model.Documentable
{
    public static final org.semanticwb.platform.SemanticClass swp_Lane=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Lane");
    public static final org.semanticwb.platform.SemanticProperty swp_parentLane=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#parentLane");
    public static final org.semanticwb.platform.SemanticClass swp_LaneSet=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#LaneSet");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#LaneSet");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.LaneSet> listLaneSets(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LaneSet>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.LaneSet> listLaneSets()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LaneSet>(it, true);
        }

        public static org.semanticwb.process.model.LaneSet createLaneSet(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.LaneSet.ClassMgr.createLaneSet(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.LaneSet getLaneSet(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.LaneSet)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.LaneSet createLaneSet(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.LaneSet)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeLaneSet(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasLaneSet(String id, org.semanticwb.model.SWBModel model)
        {
            return (getLaneSet(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.LaneSet> listLaneSetByParentLane(org.semanticwb.process.model.Lane value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LaneSet> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parentLane, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.LaneSet> listLaneSetByParentLane(org.semanticwb.process.model.Lane value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LaneSet> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parentLane,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.LaneSet> listLaneSetByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LaneSet> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.LaneSet> listLaneSetByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LaneSet> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.LaneSet> listLaneSetByLane(org.semanticwb.process.model.Lane value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LaneSet> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasLane, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.LaneSet> listLaneSetByLane(org.semanticwb.process.model.Lane value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LaneSet> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasLane,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.LaneSet> listLaneSetByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LaneSet> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.LaneSet> listLaneSetByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LaneSet> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.LaneSet> listLaneSetByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LaneSet> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.LaneSet> listLaneSetByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LaneSet> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public LaneSetBase(org.semanticwb.platform.SemanticObject base)
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

    public void setParentLane(org.semanticwb.process.model.Lane value)
    {
        getSemanticObject().setObjectProperty(swp_parentLane, value.getSemanticObject());
    }

    public void removeParentLane()
    {
        getSemanticObject().removeProperty(swp_parentLane);
    }

    public org.semanticwb.process.model.Lane getParentLane()
    {
         org.semanticwb.process.model.Lane ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_parentLane);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Lane)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Lane> listLanes()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Lane>(getSemanticObject().listObjectProperties(swp_hasLane));
    }

    public boolean hasLane(org.semanticwb.process.model.Lane value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasLane,value.getSemanticObject());
        }
        return ret;
    }

    public void addLane(org.semanticwb.process.model.Lane value)
    {
        getSemanticObject().addObjectProperty(swp_hasLane, value.getSemanticObject());
    }

    public void removeAllLane()
    {
        getSemanticObject().removeProperty(swp_hasLane);
    }

    public void removeLane(org.semanticwb.process.model.Lane value)
    {
        getSemanticObject().removeObjectProperty(swp_hasLane,value.getSemanticObject());
    }

    public org.semanticwb.process.model.Lane getLane()
    {
         org.semanticwb.process.model.Lane ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasLane);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Lane)obj.createGenericInstance();
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
