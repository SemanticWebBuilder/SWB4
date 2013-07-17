package com.infotec.pic.swb.base;


public abstract class ProductBase extends org.semanticwb.model.SWBClass implements com.infotec.pic.swb.AdditionalImages,org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass pic_Certificate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#Certificate");
    public static final org.semanticwb.platform.SemanticProperty pic_hasProductCertificate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#hasProductCertificate");
    public static final org.semanticwb.platform.SemanticProperty pic_sku=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#sku");
    public static final org.semanticwb.platform.SemanticClass pic_Company=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#Company");
    public static final org.semanticwb.platform.SemanticProperty pic_company=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#company");
    public static final org.semanticwb.platform.SemanticProperty pic_weight=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#weight");
    public static final org.semanticwb.platform.SemanticProperty pic_productionCapacity=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#productionCapacity");
    public static final org.semanticwb.platform.SemanticClass pic_Tariff=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#Tariff");
    public static final org.semanticwb.platform.SemanticProperty pic_tariff=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#tariff");
    public static final org.semanticwb.platform.SemanticProperty pic_dimensions=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#dimensions");
    public static final org.semanticwb.platform.SemanticProperty pic_packing=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#packing");
   /**
   * Si es producto es true; de lo contrario se consederaría como servicio.
   */
    public static final org.semanticwb.platform.SemanticProperty pic_product=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#product");
    public static final org.semanticwb.platform.SemanticClass pic_DimensionUnits=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#DimensionUnits");
    public static final org.semanticwb.platform.SemanticProperty pic_dimensionUnit=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#dimensionUnit");
    public static final org.semanticwb.platform.SemanticClass pic_WeightUnits=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#WeightUnits");
    public static final org.semanticwb.platform.SemanticProperty pic_weightUnits=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#weightUnits");
    public static final org.semanticwb.platform.SemanticClass pic_Product=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#Product");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#Product");

    public static class ClassMgr
    {
       /**
       * Returns a list of Product for a model
       * @param model Model to find
       * @return Iterator of com.infotec.pic.swb.Product
       */

        public static java.util.Iterator<com.infotec.pic.swb.Product> listProducts(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Product>(it, true);
        }
       /**
       * Returns a list of com.infotec.pic.swb.Product for all models
       * @return Iterator of com.infotec.pic.swb.Product
       */

        public static java.util.Iterator<com.infotec.pic.swb.Product> listProducts()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Product>(it, true);
        }

        public static com.infotec.pic.swb.Product createProduct(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return com.infotec.pic.swb.Product.ClassMgr.createProduct(String.valueOf(id), model);
        }
       /**
       * Gets a com.infotec.pic.swb.Product
       * @param id Identifier for com.infotec.pic.swb.Product
       * @param model Model of the com.infotec.pic.swb.Product
       * @return A com.infotec.pic.swb.Product
       */
        public static com.infotec.pic.swb.Product getProduct(String id, org.semanticwb.model.SWBModel model)
        {
            return (com.infotec.pic.swb.Product)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a com.infotec.pic.swb.Product
       * @param id Identifier for com.infotec.pic.swb.Product
       * @param model Model of the com.infotec.pic.swb.Product
       * @return A com.infotec.pic.swb.Product
       */
        public static com.infotec.pic.swb.Product createProduct(String id, org.semanticwb.model.SWBModel model)
        {
            return (com.infotec.pic.swb.Product)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a com.infotec.pic.swb.Product
       * @param id Identifier for com.infotec.pic.swb.Product
       * @param model Model of the com.infotec.pic.swb.Product
       */
        public static void removeProduct(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a com.infotec.pic.swb.Product
       * @param id Identifier for com.infotec.pic.swb.Product
       * @param model Model of the com.infotec.pic.swb.Product
       * @return true if the com.infotec.pic.swb.Product exists, false otherwise
       */

        public static boolean hasProduct(String id, org.semanticwb.model.SWBModel model)
        {
            return (getProduct(id, model)!=null);
        }
       /**
       * Gets all com.infotec.pic.swb.Product with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the com.infotec.pic.swb.Product
       * @return Iterator with all the com.infotec.pic.swb.Product
       */

        public static java.util.Iterator<com.infotec.pic.swb.Product> listProductByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Product> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Product with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the com.infotec.pic.swb.Product
       */

        public static java.util.Iterator<com.infotec.pic.swb.Product> listProductByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Product> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Product with a determined Certificate
       * @param value Certificate of the type com.infotec.pic.swb.Certificate
       * @param model Model of the com.infotec.pic.swb.Product
       * @return Iterator with all the com.infotec.pic.swb.Product
       */

        public static java.util.Iterator<com.infotec.pic.swb.Product> listProductByCertificate(com.infotec.pic.swb.Certificate value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Product> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(pic_hasProductCertificate, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Product with a determined Certificate
       * @param value Certificate of the type com.infotec.pic.swb.Certificate
       * @return Iterator with all the com.infotec.pic.swb.Product
       */

        public static java.util.Iterator<com.infotec.pic.swb.Product> listProductByCertificate(com.infotec.pic.swb.Certificate value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Product> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(pic_hasProductCertificate,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Product with a determined Company
       * @param value Company of the type com.infotec.pic.swb.Company
       * @param model Model of the com.infotec.pic.swb.Product
       * @return Iterator with all the com.infotec.pic.swb.Product
       */

        public static java.util.Iterator<com.infotec.pic.swb.Product> listProductByCompany(com.infotec.pic.swb.Company value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Product> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(pic_company, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Product with a determined Company
       * @param value Company of the type com.infotec.pic.swb.Company
       * @return Iterator with all the com.infotec.pic.swb.Product
       */

        public static java.util.Iterator<com.infotec.pic.swb.Product> listProductByCompany(com.infotec.pic.swb.Company value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Product> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(pic_company,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Product with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the com.infotec.pic.swb.Product
       * @return Iterator with all the com.infotec.pic.swb.Product
       */

        public static java.util.Iterator<com.infotec.pic.swb.Product> listProductByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Product> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Product with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the com.infotec.pic.swb.Product
       */

        public static java.util.Iterator<com.infotec.pic.swb.Product> listProductByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Product> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Product with a determined Tariff
       * @param value Tariff of the type com.infotec.pic.swb.Tariff
       * @param model Model of the com.infotec.pic.swb.Product
       * @return Iterator with all the com.infotec.pic.swb.Product
       */

        public static java.util.Iterator<com.infotec.pic.swb.Product> listProductByTariff(com.infotec.pic.swb.Tariff value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Product> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(pic_tariff, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Product with a determined Tariff
       * @param value Tariff of the type com.infotec.pic.swb.Tariff
       * @return Iterator with all the com.infotec.pic.swb.Product
       */

        public static java.util.Iterator<com.infotec.pic.swb.Product> listProductByTariff(com.infotec.pic.swb.Tariff value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Product> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(pic_tariff,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Product with a determined DimensionUnit
       * @param value DimensionUnit of the type com.infotec.pic.swb.DimensionUnits
       * @param model Model of the com.infotec.pic.swb.Product
       * @return Iterator with all the com.infotec.pic.swb.Product
       */

        public static java.util.Iterator<com.infotec.pic.swb.Product> listProductByDimensionUnit(com.infotec.pic.swb.DimensionUnits value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Product> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(pic_dimensionUnit, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Product with a determined DimensionUnit
       * @param value DimensionUnit of the type com.infotec.pic.swb.DimensionUnits
       * @return Iterator with all the com.infotec.pic.swb.Product
       */

        public static java.util.Iterator<com.infotec.pic.swb.Product> listProductByDimensionUnit(com.infotec.pic.swb.DimensionUnits value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Product> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(pic_dimensionUnit,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Product with a determined WeightUnits
       * @param value WeightUnits of the type com.infotec.pic.swb.WeightUnits
       * @param model Model of the com.infotec.pic.swb.Product
       * @return Iterator with all the com.infotec.pic.swb.Product
       */

        public static java.util.Iterator<com.infotec.pic.swb.Product> listProductByWeightUnits(com.infotec.pic.swb.WeightUnits value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Product> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(pic_weightUnits, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Product with a determined WeightUnits
       * @param value WeightUnits of the type com.infotec.pic.swb.WeightUnits
       * @return Iterator with all the com.infotec.pic.swb.Product
       */

        public static java.util.Iterator<com.infotec.pic.swb.Product> listProductByWeightUnits(com.infotec.pic.swb.WeightUnits value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Product> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(pic_weightUnits,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static ProductBase.ClassMgr getProductClassMgr()
    {
        return new ProductBase.ClassMgr();
    }

   /**
   * Constructs a ProductBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Product
   */
    public ProductBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property ModifiedBy
   * @param value ModifiedBy to set
   */

    public void setModifiedBy(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_modifiedBy, value.getSemanticObject());
        }else
        {
            removeModifiedBy();
        }
    }
   /**
   * Remove the value for ModifiedBy property
   */

    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(swb_modifiedBy);
    }

   /**
   * Gets the ModifiedBy
   * @return a org.semanticwb.model.User
   */
    public org.semanticwb.model.User getModifiedBy()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_modifiedBy);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Updated property
* @return java.util.Date with the Updated
*/
    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(swb_updated);
    }

/**
* Sets the Updated property
* @param value long with the Updated
*/
    public void setUpdated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_updated, value);
    }

/**
* Gets the Created property
* @return java.util.Date with the Created
*/
    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(swb_created);
    }

/**
* Sets the Created property
* @param value long with the Created
*/
    public void setCreated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_created, value);
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
   * Gets all the com.infotec.pic.swb.Certificate
   * @return A GenericIterator with all the com.infotec.pic.swb.Certificate
   */

    public org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Certificate> listCertificates()
    {
        return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Certificate>(getSemanticObject().listObjectProperties(pic_hasProductCertificate));
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
           ret=getSemanticObject().hasObjectProperty(pic_hasProductCertificate,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a Certificate
   * @param value com.infotec.pic.swb.Certificate to add
   */

    public void addCertificate(com.infotec.pic.swb.Certificate value)
    {
        getSemanticObject().addObjectProperty(pic_hasProductCertificate, value.getSemanticObject());
    }
   /**
   * Removes all the Certificate
   */

    public void removeAllCertificate()
    {
        getSemanticObject().removeProperty(pic_hasProductCertificate);
    }
   /**
   * Removes a Certificate
   * @param value com.infotec.pic.swb.Certificate to remove
   */

    public void removeCertificate(com.infotec.pic.swb.Certificate value)
    {
        getSemanticObject().removeObjectProperty(pic_hasProductCertificate,value.getSemanticObject());
    }

   /**
   * Gets the Certificate
   * @return a com.infotec.pic.swb.Certificate
   */
    public com.infotec.pic.swb.Certificate getCertificate()
    {
         com.infotec.pic.swb.Certificate ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(pic_hasProductCertificate);
         if(obj!=null)
         {
             ret=(com.infotec.pic.swb.Certificate)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Sku property
* @return String with the Sku
*/
    public String getSku()
    {
        return getSemanticObject().getProperty(pic_sku);
    }

/**
* Sets the Sku property
* @param value long with the Sku
*/
    public void setSku(String value)
    {
        getSemanticObject().setProperty(pic_sku, value);
    }
   /**
   * Sets the value for the property Company
   * @param value Company to set
   */

    public void setCompany(com.infotec.pic.swb.Company value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(pic_company, value.getSemanticObject());
        }else
        {
            removeCompany();
        }
    }
   /**
   * Remove the value for Company property
   */

    public void removeCompany()
    {
        getSemanticObject().removeProperty(pic_company);
    }

   /**
   * Gets the Company
   * @return a com.infotec.pic.swb.Company
   */
    public com.infotec.pic.swb.Company getCompany()
    {
         com.infotec.pic.swb.Company ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(pic_company);
         if(obj!=null)
         {
             ret=(com.infotec.pic.swb.Company)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Weight property
* @return String with the Weight
*/
    public String getWeight()
    {
        return getSemanticObject().getProperty(pic_weight);
    }

/**
* Sets the Weight property
* @param value long with the Weight
*/
    public void setWeight(String value)
    {
        getSemanticObject().setProperty(pic_weight, value);
    }
   /**
   * Sets the value for the property Creator
   * @param value Creator to set
   */

    public void setCreator(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_creator, value.getSemanticObject());
        }else
        {
            removeCreator();
        }
    }
   /**
   * Remove the value for Creator property
   */

    public void removeCreator()
    {
        getSemanticObject().removeProperty(swb_creator);
    }

   /**
   * Gets the Creator
   * @return a org.semanticwb.model.User
   */
    public org.semanticwb.model.User getCreator()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_creator);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the ProductionCapacity property
* @return String with the ProductionCapacity
*/
    public String getProductionCapacity()
    {
        return getSemanticObject().getProperty(pic_productionCapacity);
    }

/**
* Sets the ProductionCapacity property
* @param value long with the ProductionCapacity
*/
    public void setProductionCapacity(String value)
    {
        getSemanticObject().setProperty(pic_productionCapacity, value);
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
   * Sets the value for the property Tariff
   * @param value Tariff to set
   */

    public void setTariff(com.infotec.pic.swb.Tariff value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(pic_tariff, value.getSemanticObject());
        }else
        {
            removeTariff();
        }
    }
   /**
   * Remove the value for Tariff property
   */

    public void removeTariff()
    {
        getSemanticObject().removeProperty(pic_tariff);
    }

   /**
   * Gets the Tariff
   * @return a com.infotec.pic.swb.Tariff
   */
    public com.infotec.pic.swb.Tariff getTariff()
    {
         com.infotec.pic.swb.Tariff ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(pic_tariff);
         if(obj!=null)
         {
             ret=(com.infotec.pic.swb.Tariff)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Dimensions property
* @return String with the Dimensions
*/
    public String getDimensions()
    {
        return getSemanticObject().getProperty(pic_dimensions);
    }

/**
* Sets the Dimensions property
* @param value long with the Dimensions
*/
    public void setDimensions(String value)
    {
        getSemanticObject().setProperty(pic_dimensions, value);
    }

/**
* Gets the Packing property
* @return String with the Packing
*/
    public String getPacking()
    {
        return getSemanticObject().getProperty(pic_packing);
    }

/**
* Sets the Packing property
* @param value long with the Packing
*/
    public void setPacking(String value)
    {
        getSemanticObject().setProperty(pic_packing, value);
    }

    public java.util.Iterator<String> listImages()
    {
        java.util.ArrayList<String> values=new java.util.ArrayList<String>();
        java.util.Iterator<org.semanticwb.platform.SemanticLiteral> it=getSemanticObject().listLiteralProperties(pic_hasImage);
        while(it.hasNext())
        {
                org.semanticwb.platform.SemanticLiteral literal=it.next();
                values.add(literal.getString());
        }
        return values.iterator();
    }

    public void addImage(String value)
    {
        getSemanticObject().addLiteralProperty(pic_hasImage, new org.semanticwb.platform.SemanticLiteral(value));
    }

    public void removeAllImage()
    {
        getSemanticObject().removeProperty(pic_hasImage);
    }

    public void removeImage(String value)
    {
        getSemanticObject().removeLiteralProperty(pic_hasImage,new org.semanticwb.platform.SemanticLiteral(value));
    }

/**
* Gets the Product property
* @return boolean with the Product
*/
    public boolean isProduct()
    {
        return getSemanticObject().getBooleanProperty(pic_product);
    }

/**
* Sets the Product property
* @param value long with the Product
*/
    public void setProduct(boolean value)
    {
        getSemanticObject().setBooleanProperty(pic_product, value);
    }
   /**
   * Sets the value for the property DimensionUnit
   * @param value DimensionUnit to set
   */

    public void setDimensionUnit(com.infotec.pic.swb.DimensionUnits value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(pic_dimensionUnit, value.getSemanticObject());
        }else
        {
            removeDimensionUnit();
        }
    }
   /**
   * Remove the value for DimensionUnit property
   */

    public void removeDimensionUnit()
    {
        getSemanticObject().removeProperty(pic_dimensionUnit);
    }

   /**
   * Gets the DimensionUnit
   * @return a com.infotec.pic.swb.DimensionUnits
   */
    public com.infotec.pic.swb.DimensionUnits getDimensionUnit()
    {
         com.infotec.pic.swb.DimensionUnits ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(pic_dimensionUnit);
         if(obj!=null)
         {
             ret=(com.infotec.pic.swb.DimensionUnits)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property WeightUnits
   * @param value WeightUnits to set
   */

    public void setWeightUnits(com.infotec.pic.swb.WeightUnits value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(pic_weightUnits, value.getSemanticObject());
        }else
        {
            removeWeightUnits();
        }
    }
   /**
   * Remove the value for WeightUnits property
   */

    public void removeWeightUnits()
    {
        getSemanticObject().removeProperty(pic_weightUnits);
    }

   /**
   * Gets the WeightUnits
   * @return a com.infotec.pic.swb.WeightUnits
   */
    public com.infotec.pic.swb.WeightUnits getWeightUnits()
    {
         com.infotec.pic.swb.WeightUnits ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(pic_weightUnits);
         if(obj!=null)
         {
             ret=(com.infotec.pic.swb.WeightUnits)obj.createGenericInstance();
         }
         return ret;
    }
}
