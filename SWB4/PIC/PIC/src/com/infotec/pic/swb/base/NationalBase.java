package com.infotec.pic.swb.base;


public abstract class NationalBase extends com.infotec.pic.swb.Company implements com.infotec.pic.swb.AdditionalContactData,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass pic_National=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#National");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#National");

    public static class ClassMgr
    {
       /**
       * Returns a list of National for a model
       * @param model Model to find
       * @return Iterator of com.infotec.pic.swb.National
       */

        public static java.util.Iterator<com.infotec.pic.swb.National> listNationals(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.National>(it, true);
        }
       /**
       * Returns a list of com.infotec.pic.swb.National for all models
       * @return Iterator of com.infotec.pic.swb.National
       */

        public static java.util.Iterator<com.infotec.pic.swb.National> listNationals()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.National>(it, true);
        }
       /**
       * Gets a com.infotec.pic.swb.National
       * @param id Identifier for com.infotec.pic.swb.National
       * @param model Model of the com.infotec.pic.swb.National
       * @return A com.infotec.pic.swb.National
       */
        public static com.infotec.pic.swb.National getNational(String id, org.semanticwb.model.SWBModel model)
        {
            return (com.infotec.pic.swb.National)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a com.infotec.pic.swb.National
       * @param id Identifier for com.infotec.pic.swb.National
       * @param model Model of the com.infotec.pic.swb.National
       * @return A com.infotec.pic.swb.National
       */
        public static com.infotec.pic.swb.National createNational(String id, org.semanticwb.model.SWBModel model)
        {
            return (com.infotec.pic.swb.National)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a com.infotec.pic.swb.National
       * @param id Identifier for com.infotec.pic.swb.National
       * @param model Model of the com.infotec.pic.swb.National
       */
        public static void removeNational(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a com.infotec.pic.swb.National
       * @param id Identifier for com.infotec.pic.swb.National
       * @param model Model of the com.infotec.pic.swb.National
       * @return true if the com.infotec.pic.swb.National exists, false otherwise
       */

        public static boolean hasNational(String id, org.semanticwb.model.SWBModel model)
        {
            return (getNational(id, model)!=null);
        }
       /**
       * Gets all com.infotec.pic.swb.National with a determined Sector
       * @param value Sector of the type com.infotec.pic.swb.Sector
       * @param model Model of the com.infotec.pic.swb.National
       * @return Iterator with all the com.infotec.pic.swb.National
       */

        public static java.util.Iterator<com.infotec.pic.swb.National> listNationalBySector(com.infotec.pic.swb.Sector value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.National> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(pic_sector, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.National with a determined Sector
       * @param value Sector of the type com.infotec.pic.swb.Sector
       * @return Iterator with all the com.infotec.pic.swb.National
       */

        public static java.util.Iterator<com.infotec.pic.swb.National> listNationalBySector(com.infotec.pic.swb.Sector value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.National> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(pic_sector,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.National with a determined Contact
       * @param value Contact of the type com.infotec.pic.swb.Contact
       * @param model Model of the com.infotec.pic.swb.National
       * @return Iterator with all the com.infotec.pic.swb.National
       */

        public static java.util.Iterator<com.infotec.pic.swb.National> listNationalByContact(com.infotec.pic.swb.Contact value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.National> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(pic_hasContact, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.National with a determined Contact
       * @param value Contact of the type com.infotec.pic.swb.Contact
       * @return Iterator with all the com.infotec.pic.swb.National
       */

        public static java.util.Iterator<com.infotec.pic.swb.National> listNationalByContact(com.infotec.pic.swb.Contact value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.National> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(pic_hasContact,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.National with a determined Phone
       * @param value Phone of the type com.infotec.pic.swb.Phone
       * @param model Model of the com.infotec.pic.swb.National
       * @return Iterator with all the com.infotec.pic.swb.National
       */

        public static java.util.Iterator<com.infotec.pic.swb.National> listNationalByPhone(com.infotec.pic.swb.Phone value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.National> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(pic_hasPhone, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.National with a determined Phone
       * @param value Phone of the type com.infotec.pic.swb.Phone
       * @return Iterator with all the com.infotec.pic.swb.National
       */

        public static java.util.Iterator<com.infotec.pic.swb.National> listNationalByPhone(com.infotec.pic.swb.Phone value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.National> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(pic_hasPhone,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.National with a determined SalesRange
       * @param value SalesRange of the type com.infotec.pic.swb.SalesRange
       * @param model Model of the com.infotec.pic.swb.National
       * @return Iterator with all the com.infotec.pic.swb.National
       */

        public static java.util.Iterator<com.infotec.pic.swb.National> listNationalBySalesRange(com.infotec.pic.swb.SalesRange value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.National> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(pic_salesRange, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.National with a determined SalesRange
       * @param value SalesRange of the type com.infotec.pic.swb.SalesRange
       * @return Iterator with all the com.infotec.pic.swb.National
       */

        public static java.util.Iterator<com.infotec.pic.swb.National> listNationalBySalesRange(com.infotec.pic.swb.SalesRange value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.National> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(pic_salesRange,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.National with a determined MaturityLevel
       * @param value MaturityLevel of the type com.infotec.pic.swb.Maturity
       * @param model Model of the com.infotec.pic.swb.National
       * @return Iterator with all the com.infotec.pic.swb.National
       */

        public static java.util.Iterator<com.infotec.pic.swb.National> listNationalByMaturityLevel(com.infotec.pic.swb.Maturity value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.National> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(pic_maturityLevel, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.National with a determined MaturityLevel
       * @param value MaturityLevel of the type com.infotec.pic.swb.Maturity
       * @return Iterator with all the com.infotec.pic.swb.National
       */

        public static java.util.Iterator<com.infotec.pic.swb.National> listNationalByMaturityLevel(com.infotec.pic.swb.Maturity value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.National> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(pic_maturityLevel,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.National with a determined Cp
       * @param value Cp of the type com.infotec.pic.swb.CP
       * @param model Model of the com.infotec.pic.swb.National
       * @return Iterator with all the com.infotec.pic.swb.National
       */

        public static java.util.Iterator<com.infotec.pic.swb.National> listNationalByCp(com.infotec.pic.swb.CP value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.National> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(pic_cp, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.National with a determined Cp
       * @param value Cp of the type com.infotec.pic.swb.CP
       * @return Iterator with all the com.infotec.pic.swb.National
       */

        public static java.util.Iterator<com.infotec.pic.swb.National> listNationalByCp(com.infotec.pic.swb.CP value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.National> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(pic_cp,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.National with a determined Colonia
       * @param value Colonia of the type com.infotec.pic.swb.Colonia
       * @param model Model of the com.infotec.pic.swb.National
       * @return Iterator with all the com.infotec.pic.swb.National
       */

        public static java.util.Iterator<com.infotec.pic.swb.National> listNationalByColonia(com.infotec.pic.swb.Colonia value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.National> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(pic_colonia, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.National with a determined Colonia
       * @param value Colonia of the type com.infotec.pic.swb.Colonia
       * @return Iterator with all the com.infotec.pic.swb.National
       */

        public static java.util.Iterator<com.infotec.pic.swb.National> listNationalByColonia(com.infotec.pic.swb.Colonia value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.National> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(pic_colonia,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.National with a determined Certificate
       * @param value Certificate of the type com.infotec.pic.swb.Certificate
       * @param model Model of the com.infotec.pic.swb.National
       * @return Iterator with all the com.infotec.pic.swb.National
       */

        public static java.util.Iterator<com.infotec.pic.swb.National> listNationalByCertificate(com.infotec.pic.swb.Certificate value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.National> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(pic_hasCertificate, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.National with a determined Certificate
       * @param value Certificate of the type com.infotec.pic.swb.Certificate
       * @return Iterator with all the com.infotec.pic.swb.National
       */

        public static java.util.Iterator<com.infotec.pic.swb.National> listNationalByCertificate(com.infotec.pic.swb.Certificate value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.National> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(pic_hasCertificate,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.National with a determined ViewProfile
       * @param value ViewProfile of the type com.infotec.pic.swb.Contact
       * @param model Model of the com.infotec.pic.swb.National
       * @return Iterator with all the com.infotec.pic.swb.National
       */

        public static java.util.Iterator<com.infotec.pic.swb.National> listNationalByViewProfile(com.infotec.pic.swb.Contact value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.National> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(pic_hasViewProfile, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.National with a determined ViewProfile
       * @param value ViewProfile of the type com.infotec.pic.swb.Contact
       * @return Iterator with all the com.infotec.pic.swb.National
       */

        public static java.util.Iterator<com.infotec.pic.swb.National> listNationalByViewProfile(com.infotec.pic.swb.Contact value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.National> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(pic_hasViewProfile,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.National with a determined Follow
       * @param value Follow of the type com.infotec.pic.swb.Contact
       * @param model Model of the com.infotec.pic.swb.National
       * @return Iterator with all the com.infotec.pic.swb.National
       */

        public static java.util.Iterator<com.infotec.pic.swb.National> listNationalByFollow(com.infotec.pic.swb.Contact value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.National> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(pic_hasFollow, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.National with a determined Follow
       * @param value Follow of the type com.infotec.pic.swb.Contact
       * @return Iterator with all the com.infotec.pic.swb.National
       */

        public static java.util.Iterator<com.infotec.pic.swb.National> listNationalByFollow(com.infotec.pic.swb.Contact value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.National> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(pic_hasFollow,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.National with a determined MinoritySupport
       * @param value MinoritySupport of the type com.infotec.pic.swb.MinoritySupport
       * @param model Model of the com.infotec.pic.swb.National
       * @return Iterator with all the com.infotec.pic.swb.National
       */

        public static java.util.Iterator<com.infotec.pic.swb.National> listNationalByMinoritySupport(com.infotec.pic.swb.MinoritySupport value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.National> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(pic_minoritySupport, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.National with a determined MinoritySupport
       * @param value MinoritySupport of the type com.infotec.pic.swb.MinoritySupport
       * @return Iterator with all the com.infotec.pic.swb.National
       */

        public static java.util.Iterator<com.infotec.pic.swb.National> listNationalByMinoritySupport(com.infotec.pic.swb.MinoritySupport value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.National> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(pic_minoritySupport,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.National with a determined BussinesLine
       * @param value BussinesLine of the type com.infotec.pic.swb.BusinessLine
       * @param model Model of the com.infotec.pic.swb.National
       * @return Iterator with all the com.infotec.pic.swb.National
       */

        public static java.util.Iterator<com.infotec.pic.swb.National> listNationalByBussinesLine(com.infotec.pic.swb.BusinessLine value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.National> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(pic_bussinesLine, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.National with a determined BussinesLine
       * @param value BussinesLine of the type com.infotec.pic.swb.BusinessLine
       * @return Iterator with all the com.infotec.pic.swb.National
       */

        public static java.util.Iterator<com.infotec.pic.swb.National> listNationalByBussinesLine(com.infotec.pic.swb.BusinessLine value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.National> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(pic_bussinesLine,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.National with a determined Social
       * @param value Social of the type com.infotec.pic.swb.SocialNetwork
       * @param model Model of the com.infotec.pic.swb.National
       * @return Iterator with all the com.infotec.pic.swb.National
       */

        public static java.util.Iterator<com.infotec.pic.swb.National> listNationalBySocial(com.infotec.pic.swb.SocialNetwork value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.National> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(pic_hasSocial, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.National with a determined Social
       * @param value Social of the type com.infotec.pic.swb.SocialNetwork
       * @return Iterator with all the com.infotec.pic.swb.National
       */

        public static java.util.Iterator<com.infotec.pic.swb.National> listNationalBySocial(com.infotec.pic.swb.SocialNetwork value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.National> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(pic_hasSocial,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.National with a determined EntidadFederativa
       * @param value EntidadFederativa of the type com.infotec.pic.swb.EntidadFederativa
       * @param model Model of the com.infotec.pic.swb.National
       * @return Iterator with all the com.infotec.pic.swb.National
       */

        public static java.util.Iterator<com.infotec.pic.swb.National> listNationalByEntidadFederativa(com.infotec.pic.swb.EntidadFederativa value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.National> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(pic_entidadFederativa, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.National with a determined EntidadFederativa
       * @param value EntidadFederativa of the type com.infotec.pic.swb.EntidadFederativa
       * @return Iterator with all the com.infotec.pic.swb.National
       */

        public static java.util.Iterator<com.infotec.pic.swb.National> listNationalByEntidadFederativa(com.infotec.pic.swb.EntidadFederativa value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.National> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(pic_entidadFederativa,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.National with a determined Municipio
       * @param value Municipio of the type com.infotec.pic.swb.Municipio
       * @param model Model of the com.infotec.pic.swb.National
       * @return Iterator with all the com.infotec.pic.swb.National
       */

        public static java.util.Iterator<com.infotec.pic.swb.National> listNationalByMunicipio(com.infotec.pic.swb.Municipio value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.National> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(pic_municipio, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.National with a determined Municipio
       * @param value Municipio of the type com.infotec.pic.swb.Municipio
       * @return Iterator with all the com.infotec.pic.swb.National
       */

        public static java.util.Iterator<com.infotec.pic.swb.National> listNationalByMunicipio(com.infotec.pic.swb.Municipio value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.National> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(pic_municipio,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static NationalBase.ClassMgr getNationalClassMgr()
    {
        return new NationalBase.ClassMgr();
    }

   /**
   * Constructs a NationalBase with a SemanticObject
   * @param base The SemanticObject with the properties for the National
   */
    public NationalBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
