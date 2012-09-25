package org.semanticwb.social.base;


   /**
   * Signos de puntuación que seran eliminados de los mensajes de los post dentro del listener. Es decir, estas no deben ser evaluadas para analisis sentimental. 
   */
public abstract class PunctuationSignBase extends org.semanticwb.social.TakeOut 
{
   /**
   * Signo de Puntuación
   */
    public static final org.semanticwb.platform.SemanticProperty social_puntuationSign=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#puntuationSign");
   /**
   * Signos de puntuación que seran eliminados de los mensajes de los post dentro del listener. Es decir, estas no deben ser evaluadas para analisis sentimental.
   */
    public static final org.semanticwb.platform.SemanticClass social_PunctuationSign=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PunctuationSign");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PunctuationSign");

    public static class ClassMgr
    {
       /**
       * Returns a list of PunctuationSign for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.PunctuationSign
       */

        public static java.util.Iterator<org.semanticwb.social.PunctuationSign> listPunctuationSigns(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PunctuationSign>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.PunctuationSign for all models
       * @return Iterator of org.semanticwb.social.PunctuationSign
       */

        public static java.util.Iterator<org.semanticwb.social.PunctuationSign> listPunctuationSigns()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PunctuationSign>(it, true);
        }

        public static org.semanticwb.social.PunctuationSign createPunctuationSign(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.social.PunctuationSign.ClassMgr.createPunctuationSign(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.social.PunctuationSign
       * @param id Identifier for org.semanticwb.social.PunctuationSign
       * @param model Model of the org.semanticwb.social.PunctuationSign
       * @return A org.semanticwb.social.PunctuationSign
       */
        public static org.semanticwb.social.PunctuationSign getPunctuationSign(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.PunctuationSign)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.PunctuationSign
       * @param id Identifier for org.semanticwb.social.PunctuationSign
       * @param model Model of the org.semanticwb.social.PunctuationSign
       * @return A org.semanticwb.social.PunctuationSign
       */
        public static org.semanticwb.social.PunctuationSign createPunctuationSign(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.PunctuationSign)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.PunctuationSign
       * @param id Identifier for org.semanticwb.social.PunctuationSign
       * @param model Model of the org.semanticwb.social.PunctuationSign
       */
        public static void removePunctuationSign(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.PunctuationSign
       * @param id Identifier for org.semanticwb.social.PunctuationSign
       * @param model Model of the org.semanticwb.social.PunctuationSign
       * @return true if the org.semanticwb.social.PunctuationSign exists, false otherwise
       */

        public static boolean hasPunctuationSign(String id, org.semanticwb.model.SWBModel model)
        {
            return (getPunctuationSign(id, model)!=null);
        }
    }

    public static PunctuationSignBase.ClassMgr getPunctuationSignClassMgr()
    {
        return new PunctuationSignBase.ClassMgr();
    }

   /**
   * Constructs a PunctuationSignBase with a SemanticObject
   * @param base The SemanticObject with the properties for the PunctuationSign
   */
    public PunctuationSignBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the PuntuationSign property
* @return String with the PuntuationSign
*/
    public String getPuntuationSign()
    {
        return getSemanticObject().getProperty(social_puntuationSign);
    }

/**
* Sets the PuntuationSign property
* @param value long with the PuntuationSign
*/
    public void setPuntuationSign(String value)
    {
        getSemanticObject().setProperty(social_puntuationSign, value);
    }
}
