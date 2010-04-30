package org.semanticwb.process.model.base;


public abstract class ParticipantMultiplicityBase extends org.semanticwb.process.model.BPMNElement 
{
    public static final org.semanticwb.platform.SemanticProperty swp_minimum=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#minimum");
    public static final org.semanticwb.platform.SemanticProperty swp_maximum=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#maximum");
    public static final org.semanticwb.platform.SemanticClass swp_ParticipantMultiplicity=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ParticipantMultiplicity");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ParticipantMultiplicity");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.ParticipantMultiplicity> listParticipantMultiplicities(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParticipantMultiplicity>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.ParticipantMultiplicity> listParticipantMultiplicities()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParticipantMultiplicity>(it, true);
        }

        public static org.semanticwb.process.model.ParticipantMultiplicity createParticipantMultiplicity(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.ParticipantMultiplicity.ClassMgr.createParticipantMultiplicity(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.ParticipantMultiplicity getParticipantMultiplicity(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ParticipantMultiplicity)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.ParticipantMultiplicity createParticipantMultiplicity(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ParticipantMultiplicity)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeParticipantMultiplicity(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasParticipantMultiplicity(String id, org.semanticwb.model.SWBModel model)
        {
            return (getParticipantMultiplicity(id, model)!=null);
        }
    }

    public ParticipantMultiplicityBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public int getMinimum()
    {
        return getSemanticObject().getIntProperty(swp_minimum);
    }

    public void setMinimum(int value)
    {
        getSemanticObject().setIntProperty(swp_minimum, value);
    }

    public int getMaximum()
    {
        return getSemanticObject().getIntProperty(swp_maximum);
    }

    public void setMaximum(int value)
    {
        getSemanticObject().setIntProperty(swp_maximum, value);
    }

    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
