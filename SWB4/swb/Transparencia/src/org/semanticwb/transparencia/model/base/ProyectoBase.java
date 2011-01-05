package org.semanticwb.transparencia.model.base;


public abstract class ProyectoBase extends org.semanticwb.model.SWBClass 
{
   /**
   * Un usuario es una persona que tiene relación con el portal a través de un método de acceso.
   */
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    public static final org.semanticwb.platform.SemanticProperty trans_assignedTo=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.transparencia.org/ontology#assignedTo");
    public static final org.semanticwb.platform.SemanticClass trans_Proyecto=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.transparencia.org/ontology#Proyecto");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.transparencia.org/ontology#Proyecto");

    public static class ClassMgr
    {
       /**
       * Returns a list of Proyecto for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.transparencia.model.Proyecto
       */

        public static java.util.Iterator<org.semanticwb.transparencia.model.Proyecto> listProyectos(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.transparencia.model.Proyecto>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.transparencia.model.Proyecto for all models
       * @return Iterator of org.semanticwb.transparencia.model.Proyecto
       */

        public static java.util.Iterator<org.semanticwb.transparencia.model.Proyecto> listProyectos()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.transparencia.model.Proyecto>(it, true);
        }
       /**
       * Gets a org.semanticwb.transparencia.model.Proyecto
       * @param id Identifier for org.semanticwb.transparencia.model.Proyecto
       * @param model Model of the org.semanticwb.transparencia.model.Proyecto
       * @return A org.semanticwb.transparencia.model.Proyecto
       */
        public static org.semanticwb.transparencia.model.Proyecto getProyecto(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.transparencia.model.Proyecto)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.transparencia.model.Proyecto
       * @param id Identifier for org.semanticwb.transparencia.model.Proyecto
       * @param model Model of the org.semanticwb.transparencia.model.Proyecto
       * @return A org.semanticwb.transparencia.model.Proyecto
       */
        public static org.semanticwb.transparencia.model.Proyecto createProyecto(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.transparencia.model.Proyecto)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.transparencia.model.Proyecto
       * @param id Identifier for org.semanticwb.transparencia.model.Proyecto
       * @param model Model of the org.semanticwb.transparencia.model.Proyecto
       */
        public static void removeProyecto(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.transparencia.model.Proyecto
       * @param id Identifier for org.semanticwb.transparencia.model.Proyecto
       * @param model Model of the org.semanticwb.transparencia.model.Proyecto
       * @return true if the org.semanticwb.transparencia.model.Proyecto exists, false otherwise
       */

        public static boolean hasProyecto(String id, org.semanticwb.model.SWBModel model)
        {
            return (getProyecto(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.transparencia.model.Proyecto with a determined AssignedTo
       * @param value AssignedTo of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.transparencia.model.Proyecto
       * @return Iterator with all the org.semanticwb.transparencia.model.Proyecto
       */

        public static java.util.Iterator<org.semanticwb.transparencia.model.Proyecto> listProyectoByAssignedTo(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.transparencia.model.Proyecto> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(trans_assignedTo, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.transparencia.model.Proyecto with a determined AssignedTo
       * @param value AssignedTo of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.transparencia.model.Proyecto
       */

        public static java.util.Iterator<org.semanticwb.transparencia.model.Proyecto> listProyectoByAssignedTo(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.transparencia.model.Proyecto> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(trans_assignedTo,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
   * Constructs a ProyectoBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Proyecto
   */
    public ProyectoBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property AssignedTo
   * @param value AssignedTo to set
   */

    public void setAssignedTo(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(trans_assignedTo, value.getSemanticObject());
        }else
        {
            removeAssignedTo();
        }
    }
   /**
   * Remove the value for AssignedTo property
   */

    public void removeAssignedTo()
    {
        getSemanticObject().removeProperty(trans_assignedTo);
    }

   /**
   * Gets the AssignedTo
   * @return a org.semanticwb.model.User
   */
    public org.semanticwb.model.User getAssignedTo()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(trans_assignedTo);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }
}
