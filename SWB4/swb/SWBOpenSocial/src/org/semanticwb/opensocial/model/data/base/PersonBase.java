/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 */
package org.semanticwb.opensocial.model.data.base;


public abstract class PersonBase extends org.semanticwb.model.base.GenericObjectBase 
{
    public static final org.semanticwb.platform.SemanticProperty data_profileUrl=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/opensocial/socialdata#profileUrl");
    public static final org.semanticwb.platform.SemanticProperty data_gender=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/opensocial/socialdata#gender");
    public static final org.semanticwb.platform.SemanticClass data_AppData=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/opensocial/socialdata#AppData");
    public static final org.semanticwb.platform.SemanticProperty data_hasAppData=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/opensocial/socialdata#hasAppData");
   /**
   * Indica si el elemento es válido
   */
    public static final org.semanticwb.platform.SemanticProperty swb_valid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#valid");
    public static final org.semanticwb.platform.SemanticProperty data_Age=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/opensocial/socialdata#Age");
    public static final org.semanticwb.platform.SemanticProperty data_hasActivity=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/opensocial/socialdata#hasActivity");
    public static final org.semanticwb.platform.SemanticProperty data_hasCar=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/opensocial/socialdata#hasCar");
    public static final org.semanticwb.platform.SemanticProperty data_hasEmail=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/opensocial/socialdata#hasEmail");
    public static final org.semanticwb.platform.SemanticProperty data_hasBook=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/opensocial/socialdata#hasBook");
    public static final org.semanticwb.platform.SemanticProperty data_aboutMe=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/opensocial/socialdata#aboutMe");
    public static final org.semanticwb.platform.SemanticClass data_Group=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/opensocial/socialdata#Group");
    public static final org.semanticwb.platform.SemanticProperty data_hasGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/opensocial/socialdata#hasGroup");
    public static final org.semanticwb.platform.SemanticClass data_Name=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/opensocial/socialdata#Name");
    public static final org.semanticwb.platform.SemanticProperty data_name=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/opensocial/socialdata#name");
    public static final org.semanticwb.platform.SemanticProperty data_birthday=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/opensocial/socialdata#birthday");
    public static final org.semanticwb.platform.SemanticProperty data_thumbnailUrl=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/opensocial/socialdata#thumbnailUrl");
    public static final org.semanticwb.platform.SemanticProperty data_anniversary=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/opensocial/socialdata#anniversary");
    public static final org.semanticwb.platform.SemanticClass data_Album=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/opensocial/socialdata#Album");
    public static final org.semanticwb.platform.SemanticProperty data_hasAlbumns=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/opensocial/socialdata#hasAlbumns");
    public static final org.semanticwb.platform.SemanticClass data_Address=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/opensocial/socialdata#Address");
    public static final org.semanticwb.platform.SemanticProperty data_hasAddress=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/opensocial/socialdata#hasAddress");
    public static final org.semanticwb.platform.SemanticProperty data_children=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/opensocial/socialdata#children");
    public static final org.semanticwb.platform.SemanticClass data_Person=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/opensocial/socialdata#Person");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/opensocial/socialdata#Person");

    public static class ClassMgr
    {
       /**
       * Returns a list of Person for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.opensocial.model.data.Person
       */

        public static java.util.Iterator<org.semanticwb.opensocial.model.data.Person> listPersons(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.opensocial.model.data.Person>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.opensocial.model.data.Person for all models
       * @return Iterator of org.semanticwb.opensocial.model.data.Person
       */

        public static java.util.Iterator<org.semanticwb.opensocial.model.data.Person> listPersons()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.opensocial.model.data.Person>(it, true);
        }
       /**
       * Gets a org.semanticwb.opensocial.model.data.Person
       * @param id Identifier for org.semanticwb.opensocial.model.data.Person
       * @param model Model of the org.semanticwb.opensocial.model.data.Person
       * @return A org.semanticwb.opensocial.model.data.Person
       */
        public static org.semanticwb.opensocial.model.data.Person getPerson(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.opensocial.model.data.Person)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.opensocial.model.data.Person
       * @param id Identifier for org.semanticwb.opensocial.model.data.Person
       * @param model Model of the org.semanticwb.opensocial.model.data.Person
       * @return A org.semanticwb.opensocial.model.data.Person
       */
        public static org.semanticwb.opensocial.model.data.Person createPerson(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.opensocial.model.data.Person)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.opensocial.model.data.Person
       * @param id Identifier for org.semanticwb.opensocial.model.data.Person
       * @param model Model of the org.semanticwb.opensocial.model.data.Person
       */
        public static void removePerson(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.opensocial.model.data.Person
       * @param id Identifier for org.semanticwb.opensocial.model.data.Person
       * @param model Model of the org.semanticwb.opensocial.model.data.Person
       * @return true if the org.semanticwb.opensocial.model.data.Person exists, false otherwise
       */

        public static boolean hasPerson(String id, org.semanticwb.model.SWBModel model)
        {
            return (getPerson(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.opensocial.model.data.Person with a determined AppData
       * @param value AppData of the type org.semanticwb.opensocial.model.data.AppData
       * @param model Model of the org.semanticwb.opensocial.model.data.Person
       * @return Iterator with all the org.semanticwb.opensocial.model.data.Person
       */

        public static java.util.Iterator<org.semanticwb.opensocial.model.data.Person> listPersonByAppData(org.semanticwb.opensocial.model.data.AppData value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.opensocial.model.data.Person> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(data_hasAppData, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.opensocial.model.data.Person with a determined AppData
       * @param value AppData of the type org.semanticwb.opensocial.model.data.AppData
       * @return Iterator with all the org.semanticwb.opensocial.model.data.Person
       */

        public static java.util.Iterator<org.semanticwb.opensocial.model.data.Person> listPersonByAppData(org.semanticwb.opensocial.model.data.AppData value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.opensocial.model.data.Person> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(data_hasAppData,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.opensocial.model.data.Person with a determined Group
       * @param value Group of the type org.semanticwb.opensocial.model.data.Group
       * @param model Model of the org.semanticwb.opensocial.model.data.Person
       * @return Iterator with all the org.semanticwb.opensocial.model.data.Person
       */

        public static java.util.Iterator<org.semanticwb.opensocial.model.data.Person> listPersonByGroup(org.semanticwb.opensocial.model.data.Group value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.opensocial.model.data.Person> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(data_hasGroup, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.opensocial.model.data.Person with a determined Group
       * @param value Group of the type org.semanticwb.opensocial.model.data.Group
       * @return Iterator with all the org.semanticwb.opensocial.model.data.Person
       */

        public static java.util.Iterator<org.semanticwb.opensocial.model.data.Person> listPersonByGroup(org.semanticwb.opensocial.model.data.Group value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.opensocial.model.data.Person> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(data_hasGroup,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.opensocial.model.data.Person with a determined Name
       * @param value Name of the type org.semanticwb.opensocial.model.data.Name
       * @param model Model of the org.semanticwb.opensocial.model.data.Person
       * @return Iterator with all the org.semanticwb.opensocial.model.data.Person
       */

        public static java.util.Iterator<org.semanticwb.opensocial.model.data.Person> listPersonByName(org.semanticwb.opensocial.model.data.Name value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.opensocial.model.data.Person> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(data_name, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.opensocial.model.data.Person with a determined Name
       * @param value Name of the type org.semanticwb.opensocial.model.data.Name
       * @return Iterator with all the org.semanticwb.opensocial.model.data.Person
       */

        public static java.util.Iterator<org.semanticwb.opensocial.model.data.Person> listPersonByName(org.semanticwb.opensocial.model.data.Name value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.opensocial.model.data.Person> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(data_name,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.opensocial.model.data.Person with a determined Albumns
       * @param value Albumns of the type org.semanticwb.opensocial.model.data.Album
       * @param model Model of the org.semanticwb.opensocial.model.data.Person
       * @return Iterator with all the org.semanticwb.opensocial.model.data.Person
       */

        public static java.util.Iterator<org.semanticwb.opensocial.model.data.Person> listPersonByAlbumns(org.semanticwb.opensocial.model.data.Album value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.opensocial.model.data.Person> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(data_hasAlbumns, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.opensocial.model.data.Person with a determined Albumns
       * @param value Albumns of the type org.semanticwb.opensocial.model.data.Album
       * @return Iterator with all the org.semanticwb.opensocial.model.data.Person
       */

        public static java.util.Iterator<org.semanticwb.opensocial.model.data.Person> listPersonByAlbumns(org.semanticwb.opensocial.model.data.Album value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.opensocial.model.data.Person> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(data_hasAlbumns,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.opensocial.model.data.Person with a determined Address
       * @param value Address of the type org.semanticwb.opensocial.model.data.Address
       * @param model Model of the org.semanticwb.opensocial.model.data.Person
       * @return Iterator with all the org.semanticwb.opensocial.model.data.Person
       */

        public static java.util.Iterator<org.semanticwb.opensocial.model.data.Person> listPersonByAddress(org.semanticwb.opensocial.model.data.Address value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.opensocial.model.data.Person> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(data_hasAddress, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.opensocial.model.data.Person with a determined Address
       * @param value Address of the type org.semanticwb.opensocial.model.data.Address
       * @return Iterator with all the org.semanticwb.opensocial.model.data.Person
       */

        public static java.util.Iterator<org.semanticwb.opensocial.model.data.Person> listPersonByAddress(org.semanticwb.opensocial.model.data.Address value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.opensocial.model.data.Person> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(data_hasAddress,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
   * Constructs a PersonBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Person
   */
    public PersonBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the ProfileUrl property
* @return String with the ProfileUrl
*/
    public String getProfileUrl()
    {
        return getSemanticObject().getProperty(data_profileUrl);
    }

/**
* Sets the ProfileUrl property
* @param value long with the ProfileUrl
*/
    public void setProfileUrl(String value)
    {
        getSemanticObject().setProperty(data_profileUrl, value);
    }

/**
* Gets the Gender property
* @return String with the Gender
*/
    public String getGender()
    {
        return getSemanticObject().getProperty(data_gender);
    }

/**
* Sets the Gender property
* @param value long with the Gender
*/
    public void setGender(String value)
    {
        getSemanticObject().setProperty(data_gender, value);
    }
   /**
   * Gets all the org.semanticwb.opensocial.model.data.AppData
   * @return A GenericIterator with all the org.semanticwb.opensocial.model.data.AppData
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.opensocial.model.data.AppData> listAppDatas()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.opensocial.model.data.AppData>(getSemanticObject().listObjectProperties(data_hasAppData));
    }

   /**
   * Gets true if has a AppData
   * @param value org.semanticwb.opensocial.model.data.AppData to verify
   * @return true if the org.semanticwb.opensocial.model.data.AppData exists, false otherwise
   */
    public boolean hasAppData(org.semanticwb.opensocial.model.data.AppData value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(data_hasAppData,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a AppData
   * @param value org.semanticwb.opensocial.model.data.AppData to add
   */

    public void addAppData(org.semanticwb.opensocial.model.data.AppData value)
    {
        getSemanticObject().addObjectProperty(data_hasAppData, value.getSemanticObject());
    }
   /**
   * Removes all the AppData
   */

    public void removeAllAppData()
    {
        getSemanticObject().removeProperty(data_hasAppData);
    }
   /**
   * Removes a AppData
   * @param value org.semanticwb.opensocial.model.data.AppData to remove
   */

    public void removeAppData(org.semanticwb.opensocial.model.data.AppData value)
    {
        getSemanticObject().removeObjectProperty(data_hasAppData,value.getSemanticObject());
    }

   /**
   * Gets the AppData
   * @return a org.semanticwb.opensocial.model.data.AppData
   */
    public org.semanticwb.opensocial.model.data.AppData getAppData()
    {
         org.semanticwb.opensocial.model.data.AppData ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(data_hasAppData);
         if(obj!=null)
         {
             ret=(org.semanticwb.opensocial.model.data.AppData)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Valid property
* @return boolean with the Valid
*/
    public boolean isValid()
    {
        //Override this method in Person object
        return getSemanticObject().getBooleanProperty(swb_valid,false);
    }

/**
* Sets the Valid property
* @param value long with the Valid
*/
    public void setValid(boolean value)
    {
        //Override this method in Person object
        getSemanticObject().setBooleanProperty(swb_valid, value,false);
    }

/**
* Gets the Age property
* @return int with the Age
*/
    public int getAge()
    {
        return getSemanticObject().getIntProperty(data_Age);
    }

/**
* Sets the Age property
* @param value long with the Age
*/
    public void setAge(int value)
    {
        getSemanticObject().setIntProperty(data_Age, value);
    }

    public java.util.Iterator<String> listActivities()
    {
        java.util.ArrayList<String> values=new java.util.ArrayList<String>();
        java.util.Iterator<org.semanticwb.platform.SemanticLiteral> it=getSemanticObject().listLiteralProperties(data_hasActivity);
        while(it.hasNext())
        {
                org.semanticwb.platform.SemanticLiteral literal=it.next();
                values.add(literal.getString());
        }
        return values.iterator();
    }

    public void addActivity(String value)
    {
        getSemanticObject().addLiteralProperty(data_hasActivity, new org.semanticwb.platform.SemanticLiteral(value));
    }

    public void removeAllActivity()
    {
        getSemanticObject().removeProperty(data_hasActivity);
    }

    public void removeActivity(String value)
    {
        getSemanticObject().removeLiteralProperty(data_hasActivity,new org.semanticwb.platform.SemanticLiteral(value));
    }

    public java.util.Iterator<String> listCars()
    {
        java.util.ArrayList<String> values=new java.util.ArrayList<String>();
        java.util.Iterator<org.semanticwb.platform.SemanticLiteral> it=getSemanticObject().listLiteralProperties(data_hasCar);
        while(it.hasNext())
        {
                org.semanticwb.platform.SemanticLiteral literal=it.next();
                values.add(literal.getString());
        }
        return values.iterator();
    }

    public void addCar(String value)
    {
        getSemanticObject().addLiteralProperty(data_hasCar, new org.semanticwb.platform.SemanticLiteral(value));
    }

    public void removeAllCar()
    {
        getSemanticObject().removeProperty(data_hasCar);
    }

    public void removeCar(String value)
    {
        getSemanticObject().removeLiteralProperty(data_hasCar,new org.semanticwb.platform.SemanticLiteral(value));
    }

    public java.util.Iterator<String> listEmails()
    {
        java.util.ArrayList<String> values=new java.util.ArrayList<String>();
        java.util.Iterator<org.semanticwb.platform.SemanticLiteral> it=getSemanticObject().listLiteralProperties(data_hasEmail);
        while(it.hasNext())
        {
                org.semanticwb.platform.SemanticLiteral literal=it.next();
                values.add(literal.getString());
        }
        return values.iterator();
    }

    public void addEmail(String value)
    {
        getSemanticObject().addLiteralProperty(data_hasEmail, new org.semanticwb.platform.SemanticLiteral(value));
    }

    public void removeAllEmail()
    {
        getSemanticObject().removeProperty(data_hasEmail);
    }

    public void removeEmail(String value)
    {
        getSemanticObject().removeLiteralProperty(data_hasEmail,new org.semanticwb.platform.SemanticLiteral(value));
    }

    public java.util.Iterator<String> listBooks()
    {
        java.util.ArrayList<String> values=new java.util.ArrayList<String>();
        java.util.Iterator<org.semanticwb.platform.SemanticLiteral> it=getSemanticObject().listLiteralProperties(data_hasBook);
        while(it.hasNext())
        {
                org.semanticwb.platform.SemanticLiteral literal=it.next();
                values.add(literal.getString());
        }
        return values.iterator();
    }

    public void addBook(String value)
    {
        getSemanticObject().addLiteralProperty(data_hasBook, new org.semanticwb.platform.SemanticLiteral(value));
    }

    public void removeAllBook()
    {
        getSemanticObject().removeProperty(data_hasBook);
    }

    public void removeBook(String value)
    {
        getSemanticObject().removeLiteralProperty(data_hasBook,new org.semanticwb.platform.SemanticLiteral(value));
    }

/**
* Gets the AboutMe property
* @return String with the AboutMe
*/
    public String getAboutMe()
    {
        return getSemanticObject().getProperty(data_aboutMe);
    }

/**
* Sets the AboutMe property
* @param value long with the AboutMe
*/
    public void setAboutMe(String value)
    {
        getSemanticObject().setProperty(data_aboutMe, value);
    }
   /**
   * Gets all the org.semanticwb.opensocial.model.data.Group
   * @return A GenericIterator with all the org.semanticwb.opensocial.model.data.Group
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.opensocial.model.data.Group> listGroups()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.opensocial.model.data.Group>(getSemanticObject().listObjectProperties(data_hasGroup));
    }

   /**
   * Gets true if has a Group
   * @param value org.semanticwb.opensocial.model.data.Group to verify
   * @return true if the org.semanticwb.opensocial.model.data.Group exists, false otherwise
   */
    public boolean hasGroup(org.semanticwb.opensocial.model.data.Group value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(data_hasGroup,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a Group
   * @param value org.semanticwb.opensocial.model.data.Group to add
   */

    public void addGroup(org.semanticwb.opensocial.model.data.Group value)
    {
        getSemanticObject().addObjectProperty(data_hasGroup, value.getSemanticObject());
    }
   /**
   * Removes all the Group
   */

    public void removeAllGroup()
    {
        getSemanticObject().removeProperty(data_hasGroup);
    }
   /**
   * Removes a Group
   * @param value org.semanticwb.opensocial.model.data.Group to remove
   */

    public void removeGroup(org.semanticwb.opensocial.model.data.Group value)
    {
        getSemanticObject().removeObjectProperty(data_hasGroup,value.getSemanticObject());
    }

   /**
   * Gets the Group
   * @return a org.semanticwb.opensocial.model.data.Group
   */
    public org.semanticwb.opensocial.model.data.Group getGroup()
    {
         org.semanticwb.opensocial.model.data.Group ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(data_hasGroup);
         if(obj!=null)
         {
             ret=(org.semanticwb.opensocial.model.data.Group)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property Name
   * @param value Name to set
   */

    public void setName(org.semanticwb.opensocial.model.data.Name value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(data_name, value.getSemanticObject());
        }else
        {
            removeName();
        }
    }
   /**
   * Remove the value for Name property
   */

    public void removeName()
    {
        getSemanticObject().removeProperty(data_name);
    }

   /**
   * Gets the Name
   * @return a org.semanticwb.opensocial.model.data.Name
   */
    public org.semanticwb.opensocial.model.data.Name getName()
    {
         org.semanticwb.opensocial.model.data.Name ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(data_name);
         if(obj!=null)
         {
             ret=(org.semanticwb.opensocial.model.data.Name)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Birthday property
* @return java.util.Date with the Birthday
*/
    public java.util.Date getBirthday()
    {
        return getSemanticObject().getDateProperty(data_birthday);
    }

/**
* Sets the Birthday property
* @param value long with the Birthday
*/
    public void setBirthday(java.util.Date value)
    {
        getSemanticObject().setDateProperty(data_birthday, value);
    }

/**
* Gets the ThumbnailUrl property
* @return String with the ThumbnailUrl
*/
    public String getThumbnailUrl()
    {
        return getSemanticObject().getProperty(data_thumbnailUrl);
    }

/**
* Sets the ThumbnailUrl property
* @param value long with the ThumbnailUrl
*/
    public void setThumbnailUrl(String value)
    {
        getSemanticObject().setProperty(data_thumbnailUrl, value);
    }

/**
* Gets the Anniversary property
* @return java.util.Date with the Anniversary
*/
    public java.util.Date getAnniversary()
    {
        return getSemanticObject().getDateProperty(data_anniversary);
    }

/**
* Sets the Anniversary property
* @param value long with the Anniversary
*/
    public void setAnniversary(java.util.Date value)
    {
        getSemanticObject().setDateProperty(data_anniversary, value);
    }
   /**
   * Gets all the org.semanticwb.opensocial.model.data.Album
   * @return A GenericIterator with all the org.semanticwb.opensocial.model.data.Album
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.opensocial.model.data.Album> listAlbumnses()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.opensocial.model.data.Album>(getSemanticObject().listObjectProperties(data_hasAlbumns));
    }

   /**
   * Gets true if has a Albumns
   * @param value org.semanticwb.opensocial.model.data.Album to verify
   * @return true if the org.semanticwb.opensocial.model.data.Album exists, false otherwise
   */
    public boolean hasAlbumns(org.semanticwb.opensocial.model.data.Album value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(data_hasAlbumns,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a Albumns
   * @param value org.semanticwb.opensocial.model.data.Album to add
   */

    public void addAlbumns(org.semanticwb.opensocial.model.data.Album value)
    {
        getSemanticObject().addObjectProperty(data_hasAlbumns, value.getSemanticObject());
    }
   /**
   * Removes all the Albumns
   */

    public void removeAllAlbumns()
    {
        getSemanticObject().removeProperty(data_hasAlbumns);
    }
   /**
   * Removes a Albumns
   * @param value org.semanticwb.opensocial.model.data.Album to remove
   */

    public void removeAlbumns(org.semanticwb.opensocial.model.data.Album value)
    {
        getSemanticObject().removeObjectProperty(data_hasAlbumns,value.getSemanticObject());
    }

   /**
   * Gets the Albumns
   * @return a org.semanticwb.opensocial.model.data.Album
   */
    public org.semanticwb.opensocial.model.data.Album getAlbumns()
    {
         org.semanticwb.opensocial.model.data.Album ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(data_hasAlbumns);
         if(obj!=null)
         {
             ret=(org.semanticwb.opensocial.model.data.Album)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the org.semanticwb.opensocial.model.data.Address
   * @return A GenericIterator with all the org.semanticwb.opensocial.model.data.Address
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.opensocial.model.data.Address> listAddresses()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.opensocial.model.data.Address>(getSemanticObject().listObjectProperties(data_hasAddress));
    }

   /**
   * Gets true if has a Address
   * @param value org.semanticwb.opensocial.model.data.Address to verify
   * @return true if the org.semanticwb.opensocial.model.data.Address exists, false otherwise
   */
    public boolean hasAddress(org.semanticwb.opensocial.model.data.Address value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(data_hasAddress,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a Address
   * @param value org.semanticwb.opensocial.model.data.Address to add
   */

    public void addAddress(org.semanticwb.opensocial.model.data.Address value)
    {
        getSemanticObject().addObjectProperty(data_hasAddress, value.getSemanticObject());
    }
   /**
   * Removes all the Address
   */

    public void removeAllAddress()
    {
        getSemanticObject().removeProperty(data_hasAddress);
    }
   /**
   * Removes a Address
   * @param value org.semanticwb.opensocial.model.data.Address to remove
   */

    public void removeAddress(org.semanticwb.opensocial.model.data.Address value)
    {
        getSemanticObject().removeObjectProperty(data_hasAddress,value.getSemanticObject());
    }

   /**
   * Gets the Address
   * @return a org.semanticwb.opensocial.model.data.Address
   */
    public org.semanticwb.opensocial.model.data.Address getAddress()
    {
         org.semanticwb.opensocial.model.data.Address ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(data_hasAddress);
         if(obj!=null)
         {
             ret=(org.semanticwb.opensocial.model.data.Address)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Children property
* @return String with the Children
*/
    public String getChildren()
    {
        return getSemanticObject().getProperty(data_children);
    }

/**
* Sets the Children property
* @param value long with the Children
*/
    public void setChildren(String value)
    {
        getSemanticObject().setProperty(data_children, value);
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
