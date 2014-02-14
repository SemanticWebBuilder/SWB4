package org.semanticwb.bsc.formelement.base;


public abstract class MulSelWithoutCurrentObjectBase extends org.semanticwb.model.SelectMultiple 
{
    public static final org.semanticwb.platform.SemanticClass bsc_MulSelWithoutCurrentObject=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#MulSelWithoutCurrentObject");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#MulSelWithoutCurrentObject");

    public static class ClassMgr
    {
       /**
       * Returns a list of MulSelWithoutCurrentObject for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.formelement.MulSelWithoutCurrentObject
       */

        public static java.util.Iterator<org.semanticwb.bsc.formelement.MulSelWithoutCurrentObject> listMulSelWithoutCurrentObjects(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.formelement.MulSelWithoutCurrentObject>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.formelement.MulSelWithoutCurrentObject for all models
       * @return Iterator of org.semanticwb.bsc.formelement.MulSelWithoutCurrentObject
       */

        public static java.util.Iterator<org.semanticwb.bsc.formelement.MulSelWithoutCurrentObject> listMulSelWithoutCurrentObjects()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.formelement.MulSelWithoutCurrentObject>(it, true);
        }
       /**
       * Gets a org.semanticwb.bsc.formelement.MulSelWithoutCurrentObject
       * @param id Identifier for org.semanticwb.bsc.formelement.MulSelWithoutCurrentObject
       * @param model Model of the org.semanticwb.bsc.formelement.MulSelWithoutCurrentObject
       * @return A org.semanticwb.bsc.formelement.MulSelWithoutCurrentObject
       */
        public static org.semanticwb.bsc.formelement.MulSelWithoutCurrentObject getMulSelWithoutCurrentObject(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.formelement.MulSelWithoutCurrentObject)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.formelement.MulSelWithoutCurrentObject
       * @param id Identifier for org.semanticwb.bsc.formelement.MulSelWithoutCurrentObject
       * @param model Model of the org.semanticwb.bsc.formelement.MulSelWithoutCurrentObject
       * @return A org.semanticwb.bsc.formelement.MulSelWithoutCurrentObject
       */
        public static org.semanticwb.bsc.formelement.MulSelWithoutCurrentObject createMulSelWithoutCurrentObject(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.formelement.MulSelWithoutCurrentObject)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.formelement.MulSelWithoutCurrentObject
       * @param id Identifier for org.semanticwb.bsc.formelement.MulSelWithoutCurrentObject
       * @param model Model of the org.semanticwb.bsc.formelement.MulSelWithoutCurrentObject
       */
        public static void removeMulSelWithoutCurrentObject(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.formelement.MulSelWithoutCurrentObject
       * @param id Identifier for org.semanticwb.bsc.formelement.MulSelWithoutCurrentObject
       * @param model Model of the org.semanticwb.bsc.formelement.MulSelWithoutCurrentObject
       * @return true if the org.semanticwb.bsc.formelement.MulSelWithoutCurrentObject exists, false otherwise
       */

        public static boolean hasMulSelWithoutCurrentObject(String id, org.semanticwb.model.SWBModel model)
        {
            return (getMulSelWithoutCurrentObject(id, model)!=null);
        }
    }

    public static MulSelWithoutCurrentObjectBase.ClassMgr getMulSelWithoutCurrentObjectClassMgr()
    {
        return new MulSelWithoutCurrentObjectBase.ClassMgr();
    }

   /**
   * Constructs a MulSelWithoutCurrentObjectBase with a SemanticObject
   * @param base The SemanticObject with the properties for the MulSelWithoutCurrentObject
   */
    public MulSelWithoutCurrentObjectBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
