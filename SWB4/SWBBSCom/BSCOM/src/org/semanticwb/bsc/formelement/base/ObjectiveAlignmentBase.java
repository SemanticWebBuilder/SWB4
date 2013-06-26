package org.semanticwb.bsc.formelement.base;


   /**
   * Muestra un select con las instancias pertenecientes al BSC padre, de la clase definida en este elemento 
   */
public abstract class ObjectiveAlignmentBase extends org.semanticwb.model.SelectOne 
{
   /**
   * Define el nombre de la clase para la que se extraerán las instancias del BSC padre
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_classnameToUse=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#classnameToUse");
   /**
   * Muestra un select con las instancias pertenecientes al BSC padre, de la clase definida en este elemento
   */
    public static final org.semanticwb.platform.SemanticClass bsc_ObjectiveAlignment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#ObjectiveAlignment");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#ObjectiveAlignment");

    public static class ClassMgr
    {
       /**
       * Returns a list of ObjectiveAlignment for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.formelement.ObjectiveAlignment
       */

        public static java.util.Iterator<org.semanticwb.bsc.formelement.ObjectiveAlignment> listObjectiveAlignments(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.formelement.ObjectiveAlignment>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.formelement.ObjectiveAlignment for all models
       * @return Iterator of org.semanticwb.bsc.formelement.ObjectiveAlignment
       */

        public static java.util.Iterator<org.semanticwb.bsc.formelement.ObjectiveAlignment> listObjectiveAlignments()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.formelement.ObjectiveAlignment>(it, true);
        }
       /**
       * Gets a org.semanticwb.bsc.formelement.ObjectiveAlignment
       * @param id Identifier for org.semanticwb.bsc.formelement.ObjectiveAlignment
       * @param model Model of the org.semanticwb.bsc.formelement.ObjectiveAlignment
       * @return A org.semanticwb.bsc.formelement.ObjectiveAlignment
       */
        public static org.semanticwb.bsc.formelement.ObjectiveAlignment getObjectiveAlignment(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.formelement.ObjectiveAlignment)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.formelement.ObjectiveAlignment
       * @param id Identifier for org.semanticwb.bsc.formelement.ObjectiveAlignment
       * @param model Model of the org.semanticwb.bsc.formelement.ObjectiveAlignment
       * @return A org.semanticwb.bsc.formelement.ObjectiveAlignment
       */
        public static org.semanticwb.bsc.formelement.ObjectiveAlignment createObjectiveAlignment(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.formelement.ObjectiveAlignment)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.formelement.ObjectiveAlignment
       * @param id Identifier for org.semanticwb.bsc.formelement.ObjectiveAlignment
       * @param model Model of the org.semanticwb.bsc.formelement.ObjectiveAlignment
       */
        public static void removeObjectiveAlignment(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.formelement.ObjectiveAlignment
       * @param id Identifier for org.semanticwb.bsc.formelement.ObjectiveAlignment
       * @param model Model of the org.semanticwb.bsc.formelement.ObjectiveAlignment
       * @return true if the org.semanticwb.bsc.formelement.ObjectiveAlignment exists, false otherwise
       */

        public static boolean hasObjectiveAlignment(String id, org.semanticwb.model.SWBModel model)
        {
            return (getObjectiveAlignment(id, model)!=null);
        }
    }

    public static ObjectiveAlignmentBase.ClassMgr getObjectiveAlignmentClassMgr()
    {
        return new ObjectiveAlignmentBase.ClassMgr();
    }

   /**
   * Constructs a ObjectiveAlignmentBase with a SemanticObject
   * @param base The SemanticObject with the properties for the ObjectiveAlignment
   */
    public ObjectiveAlignmentBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the ClassnameToUse property
* @return String with the ClassnameToUse
*/
    public String getClassnameToUse()
    {
        return getSemanticObject().getProperty(bsc_classnameToUse);
    }

/**
* Sets the ClassnameToUse property
* @param value long with the ClassnameToUse
*/
    public void setClassnameToUse(String value)
    {
        getSemanticObject().setProperty(bsc_classnameToUse, value);
    }
}
