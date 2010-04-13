package org.semanticwb.process.model.base;


public abstract class ParticipantMultiplicityInstanceBase extends org.semanticwb.process.model.BPMNInstance 
{
    public static final org.semanticwb.platform.SemanticProperty swp_numParticipants=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#numParticipants");
    public static final org.semanticwb.platform.SemanticClass swp_ParticipantMultiplicityInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ParticipantMultiplicityInstance");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ParticipantMultiplicityInstance");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.ParticipantMultiplicityInstance> listParticipantMultiplicityInstances(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParticipantMultiplicityInstance>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.ParticipantMultiplicityInstance> listParticipantMultiplicityInstances()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParticipantMultiplicityInstance>(it, true);
        }

        public static org.semanticwb.process.model.ParticipantMultiplicityInstance getParticipantMultiplicityInstance(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ParticipantMultiplicityInstance)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.ParticipantMultiplicityInstance createParticipantMultiplicityInstance(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ParticipantMultiplicityInstance)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeParticipantMultiplicityInstance(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasParticipantMultiplicityInstance(String id, org.semanticwb.model.SWBModel model)
        {
            return (getParticipantMultiplicityInstance(id, model)!=null);
        }
    }

    public ParticipantMultiplicityInstanceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public int getNumParticipants()
    {
        return getSemanticObject().getIntProperty(swp_numParticipants);
    }

    public void setNumParticipants(int value)
    {
        getSemanticObject().setIntProperty(swp_numParticipants, value);
    }
}
