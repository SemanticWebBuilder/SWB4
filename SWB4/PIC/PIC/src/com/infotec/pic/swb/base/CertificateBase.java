package com.infotec.pic.swb.base;


public abstract class CertificateBase extends com.infotec.pic.swb.Catalog implements org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass pic_Certificate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#Certificate");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#Certificate");

    public static class ClassMgr
    {
       /**
       * Returns a list of Certificate for a model
       * @param model Model to find
       * @return Iterator of com.infotec.pic.swb.Certificate
       */

        public static java.util.Iterator<com.infotec.pic.swb.Certificate> listCertificates(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Certificate>(it, true);
        }
       /**
       * Returns a list of com.infotec.pic.swb.Certificate for all models
       * @return Iterator of com.infotec.pic.swb.Certificate
       */

        public static java.util.Iterator<com.infotec.pic.swb.Certificate> listCertificates()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Certificate>(it, true);
        }

        public static com.infotec.pic.swb.Certificate createCertificate(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return com.infotec.pic.swb.Certificate.ClassMgr.createCertificate(String.valueOf(id), model);
        }
       /**
       * Gets a com.infotec.pic.swb.Certificate
       * @param id Identifier for com.infotec.pic.swb.Certificate
       * @param model Model of the com.infotec.pic.swb.Certificate
       * @return A com.infotec.pic.swb.Certificate
       */
        public static com.infotec.pic.swb.Certificate getCertificate(String id, org.semanticwb.model.SWBModel model)
        {
            return (com.infotec.pic.swb.Certificate)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a com.infotec.pic.swb.Certificate
       * @param id Identifier for com.infotec.pic.swb.Certificate
       * @param model Model of the com.infotec.pic.swb.Certificate
       * @return A com.infotec.pic.swb.Certificate
       */
        public static com.infotec.pic.swb.Certificate createCertificate(String id, org.semanticwb.model.SWBModel model)
        {
            return (com.infotec.pic.swb.Certificate)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a com.infotec.pic.swb.Certificate
       * @param id Identifier for com.infotec.pic.swb.Certificate
       * @param model Model of the com.infotec.pic.swb.Certificate
       */
        public static void removeCertificate(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a com.infotec.pic.swb.Certificate
       * @param id Identifier for com.infotec.pic.swb.Certificate
       * @param model Model of the com.infotec.pic.swb.Certificate
       * @return true if the com.infotec.pic.swb.Certificate exists, false otherwise
       */

        public static boolean hasCertificate(String id, org.semanticwb.model.SWBModel model)
        {
            return (getCertificate(id, model)!=null);
        }
       /**
       * Gets all com.infotec.pic.swb.Certificate with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the com.infotec.pic.swb.Certificate
       * @return Iterator with all the com.infotec.pic.swb.Certificate
       */

        public static java.util.Iterator<com.infotec.pic.swb.Certificate> listCertificateByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Certificate> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Certificate with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the com.infotec.pic.swb.Certificate
       */

        public static java.util.Iterator<com.infotec.pic.swb.Certificate> listCertificateByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Certificate> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Certificate with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the com.infotec.pic.swb.Certificate
       * @return Iterator with all the com.infotec.pic.swb.Certificate
       */

        public static java.util.Iterator<com.infotec.pic.swb.Certificate> listCertificateByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Certificate> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Certificate with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the com.infotec.pic.swb.Certificate
       */

        public static java.util.Iterator<com.infotec.pic.swb.Certificate> listCertificateByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Certificate> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static CertificateBase.ClassMgr getCertificateClassMgr()
    {
        return new CertificateBase.ClassMgr();
    }

   /**
   * Constructs a CertificateBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Certificate
   */
    public CertificateBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
