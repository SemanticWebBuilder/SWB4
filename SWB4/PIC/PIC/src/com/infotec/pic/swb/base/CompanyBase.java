package com.infotec.pic.swb.base;


public abstract class CompanyBase extends org.semanticwb.model.SWBClass implements com.infotec.pic.swb.AdditionalContactData,org.semanticwb.model.Descriptiveable
{
   /**
   * Sector al que pertenece
   */
    public static final org.semanticwb.platform.SemanticClass pic_Sector=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#Sector");
    public static final org.semanticwb.platform.SemanticProperty pic_sector=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#sector");
    public static final org.semanticwb.platform.SemanticClass pic_Contact=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#Contact");
    public static final org.semanticwb.platform.SemanticProperty pic_hasContact=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#hasContact");
    public static final org.semanticwb.platform.SemanticProperty pic_commercialName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#commercialName");
    public static final org.semanticwb.platform.SemanticClass pic_SalesRange=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#SalesRange");
    public static final org.semanticwb.platform.SemanticProperty pic_salesRange=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#salesRange");
    public static final org.semanticwb.platform.SemanticClass pic_Maturity=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#Maturity");
    public static final org.semanticwb.platform.SemanticProperty pic_maturityLevel=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#maturityLevel");
    public static final org.semanticwb.platform.SemanticClass pic_CP=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#CP");
    public static final org.semanticwb.platform.SemanticProperty pic_cp=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#cp");
    public static final org.semanticwb.platform.SemanticClass pic_Colonia=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#Colonia");
    public static final org.semanticwb.platform.SemanticProperty pic_colonia=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#colonia");
    public static final org.semanticwb.platform.SemanticProperty pic_rfc=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#rfc");
    public static final org.semanticwb.platform.SemanticProperty pic_otherCertificate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#otherCertificate");
    public static final org.semanticwb.platform.SemanticClass pic_Certificate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#Certificate");
    public static final org.semanticwb.platform.SemanticProperty pic_hasCertificate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#hasCertificate");
    public static final org.semanticwb.platform.SemanticProperty pic_hasViewProfile=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#hasViewProfile");
    public static final org.semanticwb.platform.SemanticProperty pic_webSite=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#webSite");
    public static final org.semanticwb.platform.SemanticProperty pic_hasFollow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#hasFollow");
    public static final org.semanticwb.platform.SemanticClass pic_MinoritySupport=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#MinoritySupport");
    public static final org.semanticwb.platform.SemanticProperty pic_minoritySupport=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#minoritySupport");
   /**
   * Giro comercial
   */
    public static final org.semanticwb.platform.SemanticClass pic_BusinessLine=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#BusinessLine");
    public static final org.semanticwb.platform.SemanticProperty pic_bussinesLine=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#bussinesLine");
    public static final org.semanticwb.platform.SemanticProperty pic_innovation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#innovation");
    public static final org.semanticwb.platform.SemanticClass pic_EntidadFederativa=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#EntidadFederativa");
    public static final org.semanticwb.platform.SemanticProperty pic_entidadFederativa=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#entidadFederativa");
    public static final org.semanticwb.platform.SemanticClass pic_Municipio=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#Municipio");
    public static final org.semanticwb.platform.SemanticProperty pic_municipio=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#municipio");
    public static final org.semanticwb.platform.SemanticProperty pic_address=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#address");
    public static final org.semanticwb.platform.SemanticProperty pic_companyLogo=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#companyLogo");
    public static final org.semanticwb.platform.SemanticClass pic_EmployeeNumber=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#EmployeeNumber");
    public static final org.semanticwb.platform.SemanticProperty pic_employeeNumber=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#employeeNumber");
    public static final org.semanticwb.platform.SemanticClass pic_Company=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#Company");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#Company");

    public static class ClassMgr
    {
       /**
       * Returns a list of Company for a model
       * @param model Model to find
       * @return Iterator of com.infotec.pic.swb.Company
       */

        public static java.util.Iterator<com.infotec.pic.swb.Company> listCompanies(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Company>(it, true);
        }
       /**
       * Returns a list of com.infotec.pic.swb.Company for all models
       * @return Iterator of com.infotec.pic.swb.Company
       */

        public static java.util.Iterator<com.infotec.pic.swb.Company> listCompanies()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Company>(it, true);
        }

        public static com.infotec.pic.swb.Company createCompany(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return com.infotec.pic.swb.Company.ClassMgr.createCompany(String.valueOf(id), model);
        }
       /**
       * Gets a com.infotec.pic.swb.Company
       * @param id Identifier for com.infotec.pic.swb.Company
       * @param model Model of the com.infotec.pic.swb.Company
       * @return A com.infotec.pic.swb.Company
       */
        public static com.infotec.pic.swb.Company getCompany(String id, org.semanticwb.model.SWBModel model)
        {
            return (com.infotec.pic.swb.Company)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a com.infotec.pic.swb.Company
       * @param id Identifier for com.infotec.pic.swb.Company
       * @param model Model of the com.infotec.pic.swb.Company
       * @return A com.infotec.pic.swb.Company
       */
        public static com.infotec.pic.swb.Company createCompany(String id, org.semanticwb.model.SWBModel model)
        {
            return (com.infotec.pic.swb.Company)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a com.infotec.pic.swb.Company
       * @param id Identifier for com.infotec.pic.swb.Company
       * @param model Model of the com.infotec.pic.swb.Company
       */
        public static void removeCompany(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a com.infotec.pic.swb.Company
       * @param id Identifier for com.infotec.pic.swb.Company
       * @param model Model of the com.infotec.pic.swb.Company
       * @return true if the com.infotec.pic.swb.Company exists, false otherwise
       */

        public static boolean hasCompany(String id, org.semanticwb.model.SWBModel model)
        {
            return (getCompany(id, model)!=null);
        }
       /**
       * Gets all com.infotec.pic.swb.Company with a determined Sector
       * @param value Sector of the type com.infotec.pic.swb.Sector
       * @param model Model of the com.infotec.pic.swb.Company
       * @return Iterator with all the com.infotec.pic.swb.Company
       */

        public static java.util.Iterator<com.infotec.pic.swb.Company> listCompanyBySector(com.infotec.pic.swb.Sector value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Company> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(pic_sector, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Company with a determined Sector
       * @param value Sector of the type com.infotec.pic.swb.Sector
       * @return Iterator with all the com.infotec.pic.swb.Company
       */

        public static java.util.Iterator<com.infotec.pic.swb.Company> listCompanyBySector(com.infotec.pic.swb.Sector value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Company> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(pic_sector,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Company with a determined Contact
       * @param value Contact of the type com.infotec.pic.swb.Contact
       * @param model Model of the com.infotec.pic.swb.Company
       * @return Iterator with all the com.infotec.pic.swb.Company
       */

        public static java.util.Iterator<com.infotec.pic.swb.Company> listCompanyByContact(com.infotec.pic.swb.Contact value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Company> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(pic_hasContact, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Company with a determined Contact
       * @param value Contact of the type com.infotec.pic.swb.Contact
       * @return Iterator with all the com.infotec.pic.swb.Company
       */

        public static java.util.Iterator<com.infotec.pic.swb.Company> listCompanyByContact(com.infotec.pic.swb.Contact value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Company> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(pic_hasContact,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Company with a determined Phone
       * @param value Phone of the type com.infotec.pic.swb.Phone
       * @param model Model of the com.infotec.pic.swb.Company
       * @return Iterator with all the com.infotec.pic.swb.Company
       */

        public static java.util.Iterator<com.infotec.pic.swb.Company> listCompanyByPhone(com.infotec.pic.swb.Phone value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Company> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(pic_hasPhone, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Company with a determined Phone
       * @param value Phone of the type com.infotec.pic.swb.Phone
       * @return Iterator with all the com.infotec.pic.swb.Company
       */

        public static java.util.Iterator<com.infotec.pic.swb.Company> listCompanyByPhone(com.infotec.pic.swb.Phone value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Company> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(pic_hasPhone,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Company with a determined SalesRange
       * @param value SalesRange of the type com.infotec.pic.swb.SalesRange
       * @param model Model of the com.infotec.pic.swb.Company
       * @return Iterator with all the com.infotec.pic.swb.Company
       */

        public static java.util.Iterator<com.infotec.pic.swb.Company> listCompanyBySalesRange(com.infotec.pic.swb.SalesRange value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Company> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(pic_salesRange, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Company with a determined SalesRange
       * @param value SalesRange of the type com.infotec.pic.swb.SalesRange
       * @return Iterator with all the com.infotec.pic.swb.Company
       */

        public static java.util.Iterator<com.infotec.pic.swb.Company> listCompanyBySalesRange(com.infotec.pic.swb.SalesRange value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Company> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(pic_salesRange,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Company with a determined MaturityLevel
       * @param value MaturityLevel of the type com.infotec.pic.swb.Maturity
       * @param model Model of the com.infotec.pic.swb.Company
       * @return Iterator with all the com.infotec.pic.swb.Company
       */

        public static java.util.Iterator<com.infotec.pic.swb.Company> listCompanyByMaturityLevel(com.infotec.pic.swb.Maturity value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Company> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(pic_maturityLevel, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Company with a determined MaturityLevel
       * @param value MaturityLevel of the type com.infotec.pic.swb.Maturity
       * @return Iterator with all the com.infotec.pic.swb.Company
       */

        public static java.util.Iterator<com.infotec.pic.swb.Company> listCompanyByMaturityLevel(com.infotec.pic.swb.Maturity value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Company> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(pic_maturityLevel,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Company with a determined Cp
       * @param value Cp of the type com.infotec.pic.swb.CP
       * @param model Model of the com.infotec.pic.swb.Company
       * @return Iterator with all the com.infotec.pic.swb.Company
       */

        public static java.util.Iterator<com.infotec.pic.swb.Company> listCompanyByCp(com.infotec.pic.swb.CP value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Company> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(pic_cp, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Company with a determined Cp
       * @param value Cp of the type com.infotec.pic.swb.CP
       * @return Iterator with all the com.infotec.pic.swb.Company
       */

        public static java.util.Iterator<com.infotec.pic.swb.Company> listCompanyByCp(com.infotec.pic.swb.CP value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Company> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(pic_cp,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Company with a determined Colonia
       * @param value Colonia of the type com.infotec.pic.swb.Colonia
       * @param model Model of the com.infotec.pic.swb.Company
       * @return Iterator with all the com.infotec.pic.swb.Company
       */

        public static java.util.Iterator<com.infotec.pic.swb.Company> listCompanyByColonia(com.infotec.pic.swb.Colonia value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Company> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(pic_colonia, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Company with a determined Colonia
       * @param value Colonia of the type com.infotec.pic.swb.Colonia
       * @return Iterator with all the com.infotec.pic.swb.Company
       */

        public static java.util.Iterator<com.infotec.pic.swb.Company> listCompanyByColonia(com.infotec.pic.swb.Colonia value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Company> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(pic_colonia,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Company with a determined Certificate
       * @param value Certificate of the type com.infotec.pic.swb.Certificate
       * @param model Model of the com.infotec.pic.swb.Company
       * @return Iterator with all the com.infotec.pic.swb.Company
       */

        public static java.util.Iterator<com.infotec.pic.swb.Company> listCompanyByCertificate(com.infotec.pic.swb.Certificate value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Company> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(pic_hasCertificate, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Company with a determined Certificate
       * @param value Certificate of the type com.infotec.pic.swb.Certificate
       * @return Iterator with all the com.infotec.pic.swb.Company
       */

        public static java.util.Iterator<com.infotec.pic.swb.Company> listCompanyByCertificate(com.infotec.pic.swb.Certificate value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Company> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(pic_hasCertificate,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Company with a determined ViewProfile
       * @param value ViewProfile of the type com.infotec.pic.swb.Contact
       * @param model Model of the com.infotec.pic.swb.Company
       * @return Iterator with all the com.infotec.pic.swb.Company
       */

        public static java.util.Iterator<com.infotec.pic.swb.Company> listCompanyByViewProfile(com.infotec.pic.swb.Contact value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Company> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(pic_hasViewProfile, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Company with a determined ViewProfile
       * @param value ViewProfile of the type com.infotec.pic.swb.Contact
       * @return Iterator with all the com.infotec.pic.swb.Company
       */

        public static java.util.Iterator<com.infotec.pic.swb.Company> listCompanyByViewProfile(com.infotec.pic.swb.Contact value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Company> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(pic_hasViewProfile,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Company with a determined Follow
       * @param value Follow of the type com.infotec.pic.swb.Contact
       * @param model Model of the com.infotec.pic.swb.Company
       * @return Iterator with all the com.infotec.pic.swb.Company
       */

        public static java.util.Iterator<com.infotec.pic.swb.Company> listCompanyByFollow(com.infotec.pic.swb.Contact value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Company> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(pic_hasFollow, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Company with a determined Follow
       * @param value Follow of the type com.infotec.pic.swb.Contact
       * @return Iterator with all the com.infotec.pic.swb.Company
       */

        public static java.util.Iterator<com.infotec.pic.swb.Company> listCompanyByFollow(com.infotec.pic.swb.Contact value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Company> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(pic_hasFollow,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Company with a determined MinoritySupport
       * @param value MinoritySupport of the type com.infotec.pic.swb.MinoritySupport
       * @param model Model of the com.infotec.pic.swb.Company
       * @return Iterator with all the com.infotec.pic.swb.Company
       */

        public static java.util.Iterator<com.infotec.pic.swb.Company> listCompanyByMinoritySupport(com.infotec.pic.swb.MinoritySupport value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Company> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(pic_minoritySupport, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Company with a determined MinoritySupport
       * @param value MinoritySupport of the type com.infotec.pic.swb.MinoritySupport
       * @return Iterator with all the com.infotec.pic.swb.Company
       */

        public static java.util.Iterator<com.infotec.pic.swb.Company> listCompanyByMinoritySupport(com.infotec.pic.swb.MinoritySupport value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Company> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(pic_minoritySupport,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Company with a determined BussinesLine
       * @param value BussinesLine of the type com.infotec.pic.swb.BusinessLine
       * @param model Model of the com.infotec.pic.swb.Company
       * @return Iterator with all the com.infotec.pic.swb.Company
       */

        public static java.util.Iterator<com.infotec.pic.swb.Company> listCompanyByBussinesLine(com.infotec.pic.swb.BusinessLine value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Company> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(pic_bussinesLine, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Company with a determined BussinesLine
       * @param value BussinesLine of the type com.infotec.pic.swb.BusinessLine
       * @return Iterator with all the com.infotec.pic.swb.Company
       */

        public static java.util.Iterator<com.infotec.pic.swb.Company> listCompanyByBussinesLine(com.infotec.pic.swb.BusinessLine value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Company> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(pic_bussinesLine,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Company with a determined Social
       * @param value Social of the type com.infotec.pic.swb.SocialNetwork
       * @param model Model of the com.infotec.pic.swb.Company
       * @return Iterator with all the com.infotec.pic.swb.Company
       */

        public static java.util.Iterator<com.infotec.pic.swb.Company> listCompanyBySocial(com.infotec.pic.swb.SocialNetwork value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Company> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(pic_hasSocial, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Company with a determined Social
       * @param value Social of the type com.infotec.pic.swb.SocialNetwork
       * @return Iterator with all the com.infotec.pic.swb.Company
       */

        public static java.util.Iterator<com.infotec.pic.swb.Company> listCompanyBySocial(com.infotec.pic.swb.SocialNetwork value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Company> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(pic_hasSocial,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Company with a determined EntidadFederativa
       * @param value EntidadFederativa of the type com.infotec.pic.swb.EntidadFederativa
       * @param model Model of the com.infotec.pic.swb.Company
       * @return Iterator with all the com.infotec.pic.swb.Company
       */

        public static java.util.Iterator<com.infotec.pic.swb.Company> listCompanyByEntidadFederativa(com.infotec.pic.swb.EntidadFederativa value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Company> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(pic_entidadFederativa, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Company with a determined EntidadFederativa
       * @param value EntidadFederativa of the type com.infotec.pic.swb.EntidadFederativa
       * @return Iterator with all the com.infotec.pic.swb.Company
       */

        public static java.util.Iterator<com.infotec.pic.swb.Company> listCompanyByEntidadFederativa(com.infotec.pic.swb.EntidadFederativa value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Company> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(pic_entidadFederativa,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Company with a determined Municipio
       * @param value Municipio of the type com.infotec.pic.swb.Municipio
       * @param model Model of the com.infotec.pic.swb.Company
       * @return Iterator with all the com.infotec.pic.swb.Company
       */

        public static java.util.Iterator<com.infotec.pic.swb.Company> listCompanyByMunicipio(com.infotec.pic.swb.Municipio value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Company> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(pic_municipio, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Company with a determined Municipio
       * @param value Municipio of the type com.infotec.pic.swb.Municipio
       * @return Iterator with all the com.infotec.pic.swb.Company
       */

        public static java.util.Iterator<com.infotec.pic.swb.Company> listCompanyByMunicipio(com.infotec.pic.swb.Municipio value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Company> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(pic_municipio,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static CompanyBase.ClassMgr getCompanyClassMgr()
    {
        return new CompanyBase.ClassMgr();
    }

   /**
   * Constructs a CompanyBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Company
   */
    public CompanyBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property Sector
   * @param value Sector to set
   */

    public void setSector(com.infotec.pic.swb.Sector value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(pic_sector, value.getSemanticObject());
        }else
        {
            removeSector();
        }
    }
   /**
   * Remove the value for Sector property
   */

    public void removeSector()
    {
        getSemanticObject().removeProperty(pic_sector);
    }

   /**
   * Gets the Sector
   * @return a com.infotec.pic.swb.Sector
   */
    public com.infotec.pic.swb.Sector getSector()
    {
         com.infotec.pic.swb.Sector ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(pic_sector);
         if(obj!=null)
         {
             ret=(com.infotec.pic.swb.Sector)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the com.infotec.pic.swb.Contact
   * @return A GenericIterator with all the com.infotec.pic.swb.Contact
   */

    public org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Contact> listContacts()
    {
        return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Contact>(getSemanticObject().listObjectProperties(pic_hasContact));
    }

   /**
   * Gets true if has a Contact
   * @param value com.infotec.pic.swb.Contact to verify
   * @return true if the com.infotec.pic.swb.Contact exists, false otherwise
   */
    public boolean hasContact(com.infotec.pic.swb.Contact value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(pic_hasContact,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a Contact
   * @param value com.infotec.pic.swb.Contact to add
   */

    public void addContact(com.infotec.pic.swb.Contact value)
    {
        getSemanticObject().addObjectProperty(pic_hasContact, value.getSemanticObject());
    }
   /**
   * Removes all the Contact
   */

    public void removeAllContact()
    {
        getSemanticObject().removeProperty(pic_hasContact);
    }
   /**
   * Removes a Contact
   * @param value com.infotec.pic.swb.Contact to remove
   */

    public void removeContact(com.infotec.pic.swb.Contact value)
    {
        getSemanticObject().removeObjectProperty(pic_hasContact,value.getSemanticObject());
    }

   /**
   * Gets the Contact
   * @return a com.infotec.pic.swb.Contact
   */
    public com.infotec.pic.swb.Contact getContact()
    {
         com.infotec.pic.swb.Contact ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(pic_hasContact);
         if(obj!=null)
         {
             ret=(com.infotec.pic.swb.Contact)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the com.infotec.pic.swb.Phone
   * @return A GenericIterator with all the com.infotec.pic.swb.Phone
   */

    public org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Phone> listPhones()
    {
        return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Phone>(getSemanticObject().listObjectProperties(pic_hasPhone));
    }

   /**
   * Gets true if has a Phone
   * @param value com.infotec.pic.swb.Phone to verify
   * @return true if the com.infotec.pic.swb.Phone exists, false otherwise
   */
    public boolean hasPhone(com.infotec.pic.swb.Phone value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(pic_hasPhone,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a Phone
   * @param value com.infotec.pic.swb.Phone to add
   */

    public void addPhone(com.infotec.pic.swb.Phone value)
    {
        getSemanticObject().addObjectProperty(pic_hasPhone, value.getSemanticObject());
    }
   /**
   * Removes all the Phone
   */

    public void removeAllPhone()
    {
        getSemanticObject().removeProperty(pic_hasPhone);
    }
   /**
   * Removes a Phone
   * @param value com.infotec.pic.swb.Phone to remove
   */

    public void removePhone(com.infotec.pic.swb.Phone value)
    {
        getSemanticObject().removeObjectProperty(pic_hasPhone,value.getSemanticObject());
    }

   /**
   * Gets the Phone
   * @return a com.infotec.pic.swb.Phone
   */
    public com.infotec.pic.swb.Phone getPhone()
    {
         com.infotec.pic.swb.Phone ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(pic_hasPhone);
         if(obj!=null)
         {
             ret=(com.infotec.pic.swb.Phone)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the CommercialName property
* @return String with the CommercialName
*/
    public String getCommercialName()
    {
        return getSemanticObject().getProperty(pic_commercialName);
    }

/**
* Sets the CommercialName property
* @param value long with the CommercialName
*/
    public void setCommercialName(String value)
    {
        getSemanticObject().setProperty(pic_commercialName, value);
    }

/**
* Gets the Description property
* @return String with the Description
*/
    public String getDescription()
    {
        return getSemanticObject().getProperty(swb_description);
    }

/**
* Sets the Description property
* @param value long with the Description
*/
    public void setDescription(String value)
    {
        getSemanticObject().setProperty(swb_description, value);
    }

    public String getDescription(String lang)
    {
        return getSemanticObject().getProperty(swb_description, null, lang);
    }

    public String getDisplayDescription(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_description, lang);
    }

    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(swb_description, description, lang);
    }
   /**
   * Sets the value for the property SalesRange
   * @param value SalesRange to set
   */

    public void setSalesRange(com.infotec.pic.swb.SalesRange value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(pic_salesRange, value.getSemanticObject());
        }else
        {
            removeSalesRange();
        }
    }
   /**
   * Remove the value for SalesRange property
   */

    public void removeSalesRange()
    {
        getSemanticObject().removeProperty(pic_salesRange);
    }

   /**
   * Gets the SalesRange
   * @return a com.infotec.pic.swb.SalesRange
   */
    public com.infotec.pic.swb.SalesRange getSalesRange()
    {
         com.infotec.pic.swb.SalesRange ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(pic_salesRange);
         if(obj!=null)
         {
             ret=(com.infotec.pic.swb.SalesRange)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property MaturityLevel
   * @param value MaturityLevel to set
   */

    public void setMaturityLevel(com.infotec.pic.swb.Maturity value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(pic_maturityLevel, value.getSemanticObject());
        }else
        {
            removeMaturityLevel();
        }
    }
   /**
   * Remove the value for MaturityLevel property
   */

    public void removeMaturityLevel()
    {
        getSemanticObject().removeProperty(pic_maturityLevel);
    }

   /**
   * Gets the MaturityLevel
   * @return a com.infotec.pic.swb.Maturity
   */
    public com.infotec.pic.swb.Maturity getMaturityLevel()
    {
         com.infotec.pic.swb.Maturity ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(pic_maturityLevel);
         if(obj!=null)
         {
             ret=(com.infotec.pic.swb.Maturity)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property Cp
   * @param value Cp to set
   */

    public void setCp(com.infotec.pic.swb.CP value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(pic_cp, value.getSemanticObject());
        }else
        {
            removeCp();
        }
    }
   /**
   * Remove the value for Cp property
   */

    public void removeCp()
    {
        getSemanticObject().removeProperty(pic_cp);
    }

   /**
   * Gets the Cp
   * @return a com.infotec.pic.swb.CP
   */
    public com.infotec.pic.swb.CP getCp()
    {
         com.infotec.pic.swb.CP ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(pic_cp);
         if(obj!=null)
         {
             ret=(com.infotec.pic.swb.CP)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property Colonia
   * @param value Colonia to set
   */

    public void setColonia(com.infotec.pic.swb.Colonia value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(pic_colonia, value.getSemanticObject());
        }else
        {
            removeColonia();
        }
    }
   /**
   * Remove the value for Colonia property
   */

    public void removeColonia()
    {
        getSemanticObject().removeProperty(pic_colonia);
    }

   /**
   * Gets the Colonia
   * @return a com.infotec.pic.swb.Colonia
   */
    public com.infotec.pic.swb.Colonia getColonia()
    {
         com.infotec.pic.swb.Colonia ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(pic_colonia);
         if(obj!=null)
         {
             ret=(com.infotec.pic.swb.Colonia)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Rfc property
* @return String with the Rfc
*/
    public String getRfc()
    {
        return getSemanticObject().getProperty(pic_rfc);
    }

/**
* Sets the Rfc property
* @param value long with the Rfc
*/
    public void setRfc(String value)
    {
        getSemanticObject().setProperty(pic_rfc, value);
    }

/**
* Gets the OtherCertificate property
* @return String with the OtherCertificate
*/
    public String getOtherCertificate()
    {
        return getSemanticObject().getProperty(pic_otherCertificate);
    }

/**
* Sets the OtherCertificate property
* @param value long with the OtherCertificate
*/
    public void setOtherCertificate(String value)
    {
        getSemanticObject().setProperty(pic_otherCertificate, value);
    }
   /**
   * Gets all the com.infotec.pic.swb.Certificate
   * @return A GenericIterator with all the com.infotec.pic.swb.Certificate
   */

    public org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Certificate> listCertificates()
    {
        return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Certificate>(getSemanticObject().listObjectProperties(pic_hasCertificate));
    }

   /**
   * Gets true if has a Certificate
   * @param value com.infotec.pic.swb.Certificate to verify
   * @return true if the com.infotec.pic.swb.Certificate exists, false otherwise
   */
    public boolean hasCertificate(com.infotec.pic.swb.Certificate value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(pic_hasCertificate,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a Certificate
   * @param value com.infotec.pic.swb.Certificate to add
   */

    public void addCertificate(com.infotec.pic.swb.Certificate value)
    {
        getSemanticObject().addObjectProperty(pic_hasCertificate, value.getSemanticObject());
    }
   /**
   * Removes all the Certificate
   */

    public void removeAllCertificate()
    {
        getSemanticObject().removeProperty(pic_hasCertificate);
    }
   /**
   * Removes a Certificate
   * @param value com.infotec.pic.swb.Certificate to remove
   */

    public void removeCertificate(com.infotec.pic.swb.Certificate value)
    {
        getSemanticObject().removeObjectProperty(pic_hasCertificate,value.getSemanticObject());
    }

   /**
   * Gets the Certificate
   * @return a com.infotec.pic.swb.Certificate
   */
    public com.infotec.pic.swb.Certificate getCertificate()
    {
         com.infotec.pic.swb.Certificate ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(pic_hasCertificate);
         if(obj!=null)
         {
             ret=(com.infotec.pic.swb.Certificate)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the com.infotec.pic.swb.Contact
   * @return A GenericIterator with all the com.infotec.pic.swb.Contact
   */

    public org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Contact> listViewProfiles()
    {
        return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Contact>(getSemanticObject().listObjectProperties(pic_hasViewProfile));
    }

   /**
   * Gets true if has a ViewProfile
   * @param value com.infotec.pic.swb.Contact to verify
   * @return true if the com.infotec.pic.swb.Contact exists, false otherwise
   */
    public boolean hasViewProfile(com.infotec.pic.swb.Contact value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(pic_hasViewProfile,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a ViewProfile
   * @param value com.infotec.pic.swb.Contact to add
   */

    public void addViewProfile(com.infotec.pic.swb.Contact value)
    {
        getSemanticObject().addObjectProperty(pic_hasViewProfile, value.getSemanticObject());
    }
   /**
   * Removes all the ViewProfile
   */

    public void removeAllViewProfile()
    {
        getSemanticObject().removeProperty(pic_hasViewProfile);
    }
   /**
   * Removes a ViewProfile
   * @param value com.infotec.pic.swb.Contact to remove
   */

    public void removeViewProfile(com.infotec.pic.swb.Contact value)
    {
        getSemanticObject().removeObjectProperty(pic_hasViewProfile,value.getSemanticObject());
    }

   /**
   * Gets the ViewProfile
   * @return a com.infotec.pic.swb.Contact
   */
    public com.infotec.pic.swb.Contact getViewProfile()
    {
         com.infotec.pic.swb.Contact ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(pic_hasViewProfile);
         if(obj!=null)
         {
             ret=(com.infotec.pic.swb.Contact)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Title property
* @return String with the Title
*/
    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

/**
* Sets the Title property
* @param value long with the Title
*/
    public void setTitle(String value)
    {
        getSemanticObject().setProperty(swb_title, value);
    }

    public String getTitle(String lang)
    {
        return getSemanticObject().getProperty(swb_title, null, lang);
    }

    public String getDisplayTitle(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_title, lang);
    }

    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(swb_title, title, lang);
    }

/**
* Gets the WebSite property
* @return String with the WebSite
*/
    public String getWebSite()
    {
        return getSemanticObject().getProperty(pic_webSite);
    }

/**
* Sets the WebSite property
* @param value long with the WebSite
*/
    public void setWebSite(String value)
    {
        getSemanticObject().setProperty(pic_webSite, value);
    }
   /**
   * Gets all the com.infotec.pic.swb.Contact
   * @return A GenericIterator with all the com.infotec.pic.swb.Contact
   */

    public org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Contact> listFollows()
    {
        return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Contact>(getSemanticObject().listObjectProperties(pic_hasFollow));
    }

   /**
   * Gets true if has a Follow
   * @param value com.infotec.pic.swb.Contact to verify
   * @return true if the com.infotec.pic.swb.Contact exists, false otherwise
   */
    public boolean hasFollow(com.infotec.pic.swb.Contact value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(pic_hasFollow,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a Follow
   * @param value com.infotec.pic.swb.Contact to add
   */

    public void addFollow(com.infotec.pic.swb.Contact value)
    {
        getSemanticObject().addObjectProperty(pic_hasFollow, value.getSemanticObject());
    }
   /**
   * Removes all the Follow
   */

    public void removeAllFollow()
    {
        getSemanticObject().removeProperty(pic_hasFollow);
    }
   /**
   * Removes a Follow
   * @param value com.infotec.pic.swb.Contact to remove
   */

    public void removeFollow(com.infotec.pic.swb.Contact value)
    {
        getSemanticObject().removeObjectProperty(pic_hasFollow,value.getSemanticObject());
    }

   /**
   * Gets the Follow
   * @return a com.infotec.pic.swb.Contact
   */
    public com.infotec.pic.swb.Contact getFollow()
    {
         com.infotec.pic.swb.Contact ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(pic_hasFollow);
         if(obj!=null)
         {
             ret=(com.infotec.pic.swb.Contact)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property MinoritySupport
   * @param value MinoritySupport to set
   */

    public void setMinoritySupport(com.infotec.pic.swb.MinoritySupport value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(pic_minoritySupport, value.getSemanticObject());
        }else
        {
            removeMinoritySupport();
        }
    }
   /**
   * Remove the value for MinoritySupport property
   */

    public void removeMinoritySupport()
    {
        getSemanticObject().removeProperty(pic_minoritySupport);
    }

   /**
   * Gets the MinoritySupport
   * @return a com.infotec.pic.swb.MinoritySupport
   */
    public com.infotec.pic.swb.MinoritySupport getMinoritySupport()
    {
         com.infotec.pic.swb.MinoritySupport ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(pic_minoritySupport);
         if(obj!=null)
         {
             ret=(com.infotec.pic.swb.MinoritySupport)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property BussinesLine
   * @param value BussinesLine to set
   */

    public void setBussinesLine(com.infotec.pic.swb.BusinessLine value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(pic_bussinesLine, value.getSemanticObject());
        }else
        {
            removeBussinesLine();
        }
    }
   /**
   * Remove the value for BussinesLine property
   */

    public void removeBussinesLine()
    {
        getSemanticObject().removeProperty(pic_bussinesLine);
    }

   /**
   * Gets the BussinesLine
   * @return a com.infotec.pic.swb.BusinessLine
   */
    public com.infotec.pic.swb.BusinessLine getBussinesLine()
    {
         com.infotec.pic.swb.BusinessLine ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(pic_bussinesLine);
         if(obj!=null)
         {
             ret=(com.infotec.pic.swb.BusinessLine)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Innovation property
* @return boolean with the Innovation
*/
    public boolean isInnovation()
    {
        return getSemanticObject().getBooleanProperty(pic_innovation);
    }

/**
* Sets the Innovation property
* @param value long with the Innovation
*/
    public void setInnovation(boolean value)
    {
        getSemanticObject().setBooleanProperty(pic_innovation, value);
    }
   /**
   * Gets all the com.infotec.pic.swb.SocialNetwork
   * @return A GenericIterator with all the com.infotec.pic.swb.SocialNetwork
   */

    public org.semanticwb.model.GenericIterator<com.infotec.pic.swb.SocialNetwork> listSocials()
    {
        return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.SocialNetwork>(getSemanticObject().listObjectProperties(pic_hasSocial));
    }

   /**
   * Gets true if has a Social
   * @param value com.infotec.pic.swb.SocialNetwork to verify
   * @return true if the com.infotec.pic.swb.SocialNetwork exists, false otherwise
   */
    public boolean hasSocial(com.infotec.pic.swb.SocialNetwork value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(pic_hasSocial,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a Social
   * @param value com.infotec.pic.swb.SocialNetwork to add
   */

    public void addSocial(com.infotec.pic.swb.SocialNetwork value)
    {
        getSemanticObject().addObjectProperty(pic_hasSocial, value.getSemanticObject());
    }
   /**
   * Removes all the Social
   */

    public void removeAllSocial()
    {
        getSemanticObject().removeProperty(pic_hasSocial);
    }
   /**
   * Removes a Social
   * @param value com.infotec.pic.swb.SocialNetwork to remove
   */

    public void removeSocial(com.infotec.pic.swb.SocialNetwork value)
    {
        getSemanticObject().removeObjectProperty(pic_hasSocial,value.getSemanticObject());
    }

   /**
   * Gets the Social
   * @return a com.infotec.pic.swb.SocialNetwork
   */
    public com.infotec.pic.swb.SocialNetwork getSocial()
    {
         com.infotec.pic.swb.SocialNetwork ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(pic_hasSocial);
         if(obj!=null)
         {
             ret=(com.infotec.pic.swb.SocialNetwork)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property EntidadFederativa
   * @param value EntidadFederativa to set
   */

    public void setEntidadFederativa(com.infotec.pic.swb.EntidadFederativa value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(pic_entidadFederativa, value.getSemanticObject());
        }else
        {
            removeEntidadFederativa();
        }
    }
   /**
   * Remove the value for EntidadFederativa property
   */

    public void removeEntidadFederativa()
    {
        getSemanticObject().removeProperty(pic_entidadFederativa);
    }

   /**
   * Gets the EntidadFederativa
   * @return a com.infotec.pic.swb.EntidadFederativa
   */
    public com.infotec.pic.swb.EntidadFederativa getEntidadFederativa()
    {
         com.infotec.pic.swb.EntidadFederativa ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(pic_entidadFederativa);
         if(obj!=null)
         {
             ret=(com.infotec.pic.swb.EntidadFederativa)obj.createGenericInstance();
         }
         return ret;
    }

    public java.util.Iterator<String> listEmails()
    {
        java.util.ArrayList<String> values=new java.util.ArrayList<String>();
        java.util.Iterator<org.semanticwb.platform.SemanticLiteral> it=getSemanticObject().listLiteralProperties(pic_hasEmail);
        while(it.hasNext())
        {
                org.semanticwb.platform.SemanticLiteral literal=it.next();
                values.add(literal.getString());
        }
        return values.iterator();
    }

    public void addEmail(String value)
    {
        getSemanticObject().addLiteralProperty(pic_hasEmail, new org.semanticwb.platform.SemanticLiteral(value));
    }

    public void removeAllEmail()
    {
        getSemanticObject().removeProperty(pic_hasEmail);
    }

    public void removeEmail(String value)
    {
        getSemanticObject().removeLiteralProperty(pic_hasEmail,new org.semanticwb.platform.SemanticLiteral(value));
    }
   /**
   * Sets the value for the property Municipio
   * @param value Municipio to set
   */

    public void setMunicipio(com.infotec.pic.swb.Municipio value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(pic_municipio, value.getSemanticObject());
        }else
        {
            removeMunicipio();
        }
    }
   /**
   * Remove the value for Municipio property
   */

    public void removeMunicipio()
    {
        getSemanticObject().removeProperty(pic_municipio);
    }

   /**
   * Gets the Municipio
   * @return a com.infotec.pic.swb.Municipio
   */
    public com.infotec.pic.swb.Municipio getMunicipio()
    {
         com.infotec.pic.swb.Municipio ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(pic_municipio);
         if(obj!=null)
         {
             ret=(com.infotec.pic.swb.Municipio)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Address property
* @return String with the Address
*/
    public String getAddress()
    {
        return getSemanticObject().getProperty(pic_address);
    }

/**
* Sets the Address property
* @param value long with the Address
*/
    public void setAddress(String value)
    {
        getSemanticObject().setProperty(pic_address, value);
    }

/**
* Gets the CompanyLogo property
* @return String with the CompanyLogo
*/
    public String getCompanyLogo()
    {
        return getSemanticObject().getProperty(pic_companyLogo);
    }

/**
* Sets the CompanyLogo property
* @param value long with the CompanyLogo
*/
    public void setCompanyLogo(String value)
    {
        getSemanticObject().setProperty(pic_companyLogo, value);
    }

    public void setEmployeeNumber(org.semanticwb.platform.SemanticObject value)
    {
        getSemanticObject().setObjectProperty(pic_employeeNumber, value);
    }

    public void removeEmployeeNumber()
    {
        getSemanticObject().removeProperty(pic_employeeNumber);
    }

/**
* Gets the EmployeeNumber property
* @return the value for the property as org.semanticwb.platform.SemanticObject
*/
    public org.semanticwb.platform.SemanticObject getEmployeeNumber()
    {
         org.semanticwb.platform.SemanticObject ret=null;
         ret=getSemanticObject().getObjectProperty(pic_employeeNumber);
         return ret;
    }
}
