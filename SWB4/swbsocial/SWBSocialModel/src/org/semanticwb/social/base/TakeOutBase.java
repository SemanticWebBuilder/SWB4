package org.semanticwb.social.base;


   /**
   * Clase que englobara elementos a ser quitados en los mensajes de las diferentes redes sociales, de tal manera que se puedan comparar cada uno de las palabras de los mensajes con las palabras de valor sentimental que se tengan almacenadas en la clase SentimentalWords. 
   */
public abstract class TakeOutBase extends org.semanticwb.model.SWBClass 
{
   /**
   * Clase que englobara elementos a ser quitados en los mensajes de las diferentes redes sociales, de tal manera que se puedan comparar cada uno de las palabras de los mensajes con las palabras de valor sentimental que se tengan almacenadas en la clase SentimentalWords.
   */
    public static final org.semanticwb.platform.SemanticClass social_TakeOut=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#TakeOut");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#TakeOut");

    public static class ClassMgr
    {
       /**
       * Returns a list of TakeOut for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.TakeOut
       */

        public static java.util.Iterator<org.semanticwb.social.TakeOut> listTakeOuts(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.TakeOut>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.TakeOut for all models
       * @return Iterator of org.semanticwb.social.TakeOut
       */

        public static java.util.Iterator<org.semanticwb.social.TakeOut> listTakeOuts()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.TakeOut>(it, true);
        }
       /**
       * Gets a org.semanticwb.social.TakeOut
       * @param id Identifier for org.semanticwb.social.TakeOut
       * @param model Model of the org.semanticwb.social.TakeOut
       * @return A org.semanticwb.social.TakeOut
       */
        public static org.semanticwb.social.TakeOut getTakeOut(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.TakeOut)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.TakeOut
       * @param id Identifier for org.semanticwb.social.TakeOut
       * @param model Model of the org.semanticwb.social.TakeOut
       * @return A org.semanticwb.social.TakeOut
       */
        public static org.semanticwb.social.TakeOut createTakeOut(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.TakeOut)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.TakeOut
       * @param id Identifier for org.semanticwb.social.TakeOut
       * @param model Model of the org.semanticwb.social.TakeOut
       */
        public static void removeTakeOut(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.TakeOut
       * @param id Identifier for org.semanticwb.social.TakeOut
       * @param model Model of the org.semanticwb.social.TakeOut
       * @return true if the org.semanticwb.social.TakeOut exists, false otherwise
       */

        public static boolean hasTakeOut(String id, org.semanticwb.model.SWBModel model)
        {
            return (getTakeOut(id, model)!=null);
        }
    }

    public static TakeOutBase.ClassMgr getTakeOutClassMgr()
    {
        return new TakeOutBase.ClassMgr();
    }

   /**
   * Constructs a TakeOutBase with a SemanticObject
   * @param base The SemanticObject with the properties for the TakeOut
   */
    public TakeOutBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
