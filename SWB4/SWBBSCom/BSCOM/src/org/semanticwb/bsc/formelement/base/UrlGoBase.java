package org.semanticwb.bsc.formelement.base;


public abstract class UrlGoBase extends org.semanticwb.model.base.FormElementBase 
{
    public static final org.semanticwb.platform.SemanticClass bsc_UrlGo=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#UrlGo");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#UrlGo");

    public static class ClassMgr
    {
       /**
       * Returns a list of UrlGo for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.formelement.UrlGo
       */

        public static java.util.Iterator<org.semanticwb.bsc.formelement.UrlGo> listUrlGos(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.formelement.UrlGo>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.formelement.UrlGo for all models
       * @return Iterator of org.semanticwb.bsc.formelement.UrlGo
       */

        public static java.util.Iterator<org.semanticwb.bsc.formelement.UrlGo> listUrlGos()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.formelement.UrlGo>(it, true);
        }
       /**
       * Gets a org.semanticwb.bsc.formelement.UrlGo
       * @param id Identifier for org.semanticwb.bsc.formelement.UrlGo
       * @param model Model of the org.semanticwb.bsc.formelement.UrlGo
       * @return A org.semanticwb.bsc.formelement.UrlGo
       */
        public static org.semanticwb.bsc.formelement.UrlGo getUrlGo(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.formelement.UrlGo)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.formelement.UrlGo
       * @param id Identifier for org.semanticwb.bsc.formelement.UrlGo
       * @param model Model of the org.semanticwb.bsc.formelement.UrlGo
       * @return A org.semanticwb.bsc.formelement.UrlGo
       */
        public static org.semanticwb.bsc.formelement.UrlGo createUrlGo(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.formelement.UrlGo)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.formelement.UrlGo
       * @param id Identifier for org.semanticwb.bsc.formelement.UrlGo
       * @param model Model of the org.semanticwb.bsc.formelement.UrlGo
       */
        public static void removeUrlGo(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.formelement.UrlGo
       * @param id Identifier for org.semanticwb.bsc.formelement.UrlGo
       * @param model Model of the org.semanticwb.bsc.formelement.UrlGo
       * @return true if the org.semanticwb.bsc.formelement.UrlGo exists, false otherwise
       */

        public static boolean hasUrlGo(String id, org.semanticwb.model.SWBModel model)
        {
            return (getUrlGo(id, model)!=null);
        }
    }

   /**
   * Constructs a UrlGoBase with a SemanticObject
   * @param base The SemanticObject with the properties for the UrlGo
   */
    public UrlGoBase(org.semanticwb.platform.SemanticObject base)
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
