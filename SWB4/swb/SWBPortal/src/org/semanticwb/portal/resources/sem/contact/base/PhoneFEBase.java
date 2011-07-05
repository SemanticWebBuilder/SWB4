package org.semanticwb.portal.resources.sem.contact.base;


   /**
   * Elemento que muestra un componente grafico para capturar los datos telefónicos 
   */
public abstract class PhoneFEBase extends org.semanticwb.model.base.FormElementBase 
{
   /**
   * Elemento que muestra un componente grafico para capturar los datos telefónicos
   */
    public static final org.semanticwb.platform.SemanticClass contact_PhoneFE=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/Contact#PhoneFE");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/Contact#PhoneFE");

    public static class ClassMgr
    {
       /**
       * Returns a list of PhoneFE for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.portal.resources.sem.contact.PhoneFE
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.contact.PhoneFE> listPhoneFEs(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.contact.PhoneFE>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.portal.resources.sem.contact.PhoneFE for all models
       * @return Iterator of org.semanticwb.portal.resources.sem.contact.PhoneFE
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.contact.PhoneFE> listPhoneFEs()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.contact.PhoneFE>(it, true);
        }
       /**
       * Gets a org.semanticwb.portal.resources.sem.contact.PhoneFE
       * @param id Identifier for org.semanticwb.portal.resources.sem.contact.PhoneFE
       * @param model Model of the org.semanticwb.portal.resources.sem.contact.PhoneFE
       * @return A org.semanticwb.portal.resources.sem.contact.PhoneFE
       */
        public static org.semanticwb.portal.resources.sem.contact.PhoneFE getPhoneFE(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.contact.PhoneFE)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.portal.resources.sem.contact.PhoneFE
       * @param id Identifier for org.semanticwb.portal.resources.sem.contact.PhoneFE
       * @param model Model of the org.semanticwb.portal.resources.sem.contact.PhoneFE
       * @return A org.semanticwb.portal.resources.sem.contact.PhoneFE
       */
        public static org.semanticwb.portal.resources.sem.contact.PhoneFE createPhoneFE(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.contact.PhoneFE)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.portal.resources.sem.contact.PhoneFE
       * @param id Identifier for org.semanticwb.portal.resources.sem.contact.PhoneFE
       * @param model Model of the org.semanticwb.portal.resources.sem.contact.PhoneFE
       */
        public static void removePhoneFE(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.portal.resources.sem.contact.PhoneFE
       * @param id Identifier for org.semanticwb.portal.resources.sem.contact.PhoneFE
       * @param model Model of the org.semanticwb.portal.resources.sem.contact.PhoneFE
       * @return true if the org.semanticwb.portal.resources.sem.contact.PhoneFE exists, false otherwise
       */

        public static boolean hasPhoneFE(String id, org.semanticwb.model.SWBModel model)
        {
            return (getPhoneFE(id, model)!=null);
        }
    }

   /**
   * Constructs a PhoneFEBase with a SemanticObject
   * @param base The SemanticObject with the properties for the PhoneFE
   */
    public PhoneFEBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
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
