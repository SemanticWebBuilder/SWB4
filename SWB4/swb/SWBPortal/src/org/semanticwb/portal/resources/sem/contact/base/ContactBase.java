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
package org.semanticwb.portal.resources.sem.contact.base;


   /**
   * Contact 
   */
public abstract class ContactBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Traceable,org.semanticwb.model.WebPageable,org.semanticwb.model.Iconable,org.semanticwb.model.Sortable
{
   /**
   * Treatment
   */
    public static final org.semanticwb.platform.SemanticProperty contact_treatment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/Contact#treatment");
   /**
   * Teléfono
   */
    public static final org.semanticwb.platform.SemanticClass contact_Phone=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/Contact#Phone");
   /**
   * Teléfono particular
   */
    public static final org.semanticwb.platform.SemanticProperty contact_homePhone=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/Contact#homePhone");
   /**
   * Dirección
   */
    public static final org.semanticwb.platform.SemanticClass contact_Address=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/Contact#Address");
   /**
   * Dirección particular
   */
    public static final org.semanticwb.platform.SemanticProperty contact_homeAddress=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/Contact#homeAddress");
   /**
   * Primer apelldio
   */
    public static final org.semanticwb.platform.SemanticProperty contact_lastName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/Contact#lastName");
   /**
   * Segundo apellido
   */
    public static final org.semanticwb.platform.SemanticProperty contact_secondLastName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/Contact#secondLastName");
   /**
   * Teléfono de trabajo secundario
   */
    public static final org.semanticwb.platform.SemanticProperty contact_workPhone2=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/Contact#workPhone2");
   /**
   * Teléfono de recados
   */
    public static final org.semanticwb.platform.SemanticProperty contact_eveningPhone=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/Contact#eveningPhone");
   /**
   * Category
   */
    public static final org.semanticwb.platform.SemanticClass contact_Category=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/Contact#Category");
    public static final org.semanticwb.platform.SemanticProperty contact_category=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/Contact#category");
   /**
   * Teléfono celular
   */
    public static final org.semanticwb.platform.SemanticProperty contact_mobilePhone=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/Contact#mobilePhone");
   /**
   * Página web
   */
    public static final org.semanticwb.platform.SemanticProperty contact_webpage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/Contact#webpage");
   /**
   * Correo electrónico
   */
    public static final org.semanticwb.platform.SemanticProperty contact_email=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/Contact#email");
   /**
   * Dirección alterna
   */
    public static final org.semanticwb.platform.SemanticProperty contact_otherAddress=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/Contact#otherAddress");
   /**
   * Cargo
   */
    public static final org.semanticwb.platform.SemanticProperty contact_position=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/Contact#position");
   /**
   * Organización
   */
    public static final org.semanticwb.platform.SemanticProperty contact_organization=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/Contact#organization");
   /**
   * Work Phone
   */
    public static final org.semanticwb.platform.SemanticProperty contact_workPhone=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/Contact#workPhone");
   /**
   * Nombre
   */
    public static final org.semanticwb.platform.SemanticProperty contact_firstName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/Contact#firstName");
   /**
   * Foto
   */
    public static final org.semanticwb.platform.SemanticProperty contact_photo=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/Contact#photo");
   /**
   * Dirección de trabajo
   */
    public static final org.semanticwb.platform.SemanticProperty contact_workAddress=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/Contact#workAddress");
   /**
   * Second Email
   */
    public static final org.semanticwb.platform.SemanticProperty contact_email2=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/Contact#email2");
   /**
   * Third Email
   */
    public static final org.semanticwb.platform.SemanticProperty contact_email3=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/Contact#email3");
   /**
   * Teléfono de fax
   */
    public static final org.semanticwb.platform.SemanticProperty contact_faxPhone=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/Contact#faxPhone");
   /**
   * Contact
   */
    public static final org.semanticwb.platform.SemanticClass contact_Contact=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/Contact#Contact");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/Contact#Contact");

    public static class ClassMgr
    {
       /**
       * Returns a list of Contact for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.portal.resources.sem.contact.Contact
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.contact.Contact> listContacts(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.contact.Contact>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.portal.resources.sem.contact.Contact for all models
       * @return Iterator of org.semanticwb.portal.resources.sem.contact.Contact
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.contact.Contact> listContacts()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.contact.Contact>(it, true);
        }

        public static org.semanticwb.portal.resources.sem.contact.Contact createContact(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.portal.resources.sem.contact.Contact.ClassMgr.createContact(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.portal.resources.sem.contact.Contact
       * @param id Identifier for org.semanticwb.portal.resources.sem.contact.Contact
       * @param model Model of the org.semanticwb.portal.resources.sem.contact.Contact
       * @return A org.semanticwb.portal.resources.sem.contact.Contact
       */
        public static org.semanticwb.portal.resources.sem.contact.Contact getContact(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.contact.Contact)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.portal.resources.sem.contact.Contact
       * @param id Identifier for org.semanticwb.portal.resources.sem.contact.Contact
       * @param model Model of the org.semanticwb.portal.resources.sem.contact.Contact
       * @return A org.semanticwb.portal.resources.sem.contact.Contact
       */
        public static org.semanticwb.portal.resources.sem.contact.Contact createContact(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.contact.Contact)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.portal.resources.sem.contact.Contact
       * @param id Identifier for org.semanticwb.portal.resources.sem.contact.Contact
       * @param model Model of the org.semanticwb.portal.resources.sem.contact.Contact
       */
        public static void removeContact(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.portal.resources.sem.contact.Contact
       * @param id Identifier for org.semanticwb.portal.resources.sem.contact.Contact
       * @param model Model of the org.semanticwb.portal.resources.sem.contact.Contact
       * @return true if the org.semanticwb.portal.resources.sem.contact.Contact exists, false otherwise
       */

        public static boolean hasContact(String id, org.semanticwb.model.SWBModel model)
        {
            return (getContact(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.contact.Contact with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.portal.resources.sem.contact.Contact
       * @return Iterator with all the org.semanticwb.portal.resources.sem.contact.Contact
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.contact.Contact> listContactByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.contact.Contact> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.contact.Contact with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.portal.resources.sem.contact.Contact
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.contact.Contact> listContactByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.contact.Contact> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.contact.Contact with a determined HomePhone
       * @param value HomePhone of the type org.semanticwb.portal.resources.sem.contact.Phone
       * @param model Model of the org.semanticwb.portal.resources.sem.contact.Contact
       * @return Iterator with all the org.semanticwb.portal.resources.sem.contact.Contact
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.contact.Contact> listContactByHomePhone(org.semanticwb.portal.resources.sem.contact.Phone value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.contact.Contact> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(contact_homePhone, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.contact.Contact with a determined HomePhone
       * @param value HomePhone of the type org.semanticwb.portal.resources.sem.contact.Phone
       * @return Iterator with all the org.semanticwb.portal.resources.sem.contact.Contact
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.contact.Contact> listContactByHomePhone(org.semanticwb.portal.resources.sem.contact.Phone value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.contact.Contact> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(contact_homePhone,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.contact.Contact with a determined WebPage
       * @param value WebPage of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.portal.resources.sem.contact.Contact
       * @return Iterator with all the org.semanticwb.portal.resources.sem.contact.Contact
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.contact.Contact> listContactByWebPage(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.contact.Contact> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_webPage, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.contact.Contact with a determined WebPage
       * @param value WebPage of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.portal.resources.sem.contact.Contact
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.contact.Contact> listContactByWebPage(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.contact.Contact> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_webPage,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.contact.Contact with a determined HomeAddress
       * @param value HomeAddress of the type org.semanticwb.portal.resources.sem.contact.Address
       * @param model Model of the org.semanticwb.portal.resources.sem.contact.Contact
       * @return Iterator with all the org.semanticwb.portal.resources.sem.contact.Contact
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.contact.Contact> listContactByHomeAddress(org.semanticwb.portal.resources.sem.contact.Address value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.contact.Contact> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(contact_homeAddress, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.contact.Contact with a determined HomeAddress
       * @param value HomeAddress of the type org.semanticwb.portal.resources.sem.contact.Address
       * @return Iterator with all the org.semanticwb.portal.resources.sem.contact.Contact
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.contact.Contact> listContactByHomeAddress(org.semanticwb.portal.resources.sem.contact.Address value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.contact.Contact> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(contact_homeAddress,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.contact.Contact with a determined WorkPhone2
       * @param value WorkPhone2 of the type org.semanticwb.portal.resources.sem.contact.Phone
       * @param model Model of the org.semanticwb.portal.resources.sem.contact.Contact
       * @return Iterator with all the org.semanticwb.portal.resources.sem.contact.Contact
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.contact.Contact> listContactByWorkPhone2(org.semanticwb.portal.resources.sem.contact.Phone value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.contact.Contact> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(contact_workPhone2, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.contact.Contact with a determined WorkPhone2
       * @param value WorkPhone2 of the type org.semanticwb.portal.resources.sem.contact.Phone
       * @return Iterator with all the org.semanticwb.portal.resources.sem.contact.Contact
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.contact.Contact> listContactByWorkPhone2(org.semanticwb.portal.resources.sem.contact.Phone value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.contact.Contact> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(contact_workPhone2,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.contact.Contact with a determined EveningPhone
       * @param value EveningPhone of the type org.semanticwb.portal.resources.sem.contact.Phone
       * @param model Model of the org.semanticwb.portal.resources.sem.contact.Contact
       * @return Iterator with all the org.semanticwb.portal.resources.sem.contact.Contact
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.contact.Contact> listContactByEveningPhone(org.semanticwb.portal.resources.sem.contact.Phone value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.contact.Contact> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(contact_eveningPhone, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.contact.Contact with a determined EveningPhone
       * @param value EveningPhone of the type org.semanticwb.portal.resources.sem.contact.Phone
       * @return Iterator with all the org.semanticwb.portal.resources.sem.contact.Contact
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.contact.Contact> listContactByEveningPhone(org.semanticwb.portal.resources.sem.contact.Phone value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.contact.Contact> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(contact_eveningPhone,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.contact.Contact with a determined Category
       * @param value Category of the type org.semanticwb.portal.resources.sem.contact.Category
       * @param model Model of the org.semanticwb.portal.resources.sem.contact.Contact
       * @return Iterator with all the org.semanticwb.portal.resources.sem.contact.Contact
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.contact.Contact> listContactByCategory(org.semanticwb.portal.resources.sem.contact.Category value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.contact.Contact> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(contact_category, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.contact.Contact with a determined Category
       * @param value Category of the type org.semanticwb.portal.resources.sem.contact.Category
       * @return Iterator with all the org.semanticwb.portal.resources.sem.contact.Contact
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.contact.Contact> listContactByCategory(org.semanticwb.portal.resources.sem.contact.Category value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.contact.Contact> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(contact_category,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.contact.Contact with a determined MobilePhone
       * @param value MobilePhone of the type org.semanticwb.portal.resources.sem.contact.Phone
       * @param model Model of the org.semanticwb.portal.resources.sem.contact.Contact
       * @return Iterator with all the org.semanticwb.portal.resources.sem.contact.Contact
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.contact.Contact> listContactByMobilePhone(org.semanticwb.portal.resources.sem.contact.Phone value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.contact.Contact> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(contact_mobilePhone, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.contact.Contact with a determined MobilePhone
       * @param value MobilePhone of the type org.semanticwb.portal.resources.sem.contact.Phone
       * @return Iterator with all the org.semanticwb.portal.resources.sem.contact.Contact
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.contact.Contact> listContactByMobilePhone(org.semanticwb.portal.resources.sem.contact.Phone value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.contact.Contact> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(contact_mobilePhone,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.contact.Contact with a determined OtherAddress
       * @param value OtherAddress of the type org.semanticwb.portal.resources.sem.contact.Address
       * @param model Model of the org.semanticwb.portal.resources.sem.contact.Contact
       * @return Iterator with all the org.semanticwb.portal.resources.sem.contact.Contact
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.contact.Contact> listContactByOtherAddress(org.semanticwb.portal.resources.sem.contact.Address value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.contact.Contact> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(contact_otherAddress, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.contact.Contact with a determined OtherAddress
       * @param value OtherAddress of the type org.semanticwb.portal.resources.sem.contact.Address
       * @return Iterator with all the org.semanticwb.portal.resources.sem.contact.Contact
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.contact.Contact> listContactByOtherAddress(org.semanticwb.portal.resources.sem.contact.Address value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.contact.Contact> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(contact_otherAddress,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.contact.Contact with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.portal.resources.sem.contact.Contact
       * @return Iterator with all the org.semanticwb.portal.resources.sem.contact.Contact
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.contact.Contact> listContactByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.contact.Contact> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.contact.Contact with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.portal.resources.sem.contact.Contact
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.contact.Contact> listContactByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.contact.Contact> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.contact.Contact with a determined WorkPhone
       * @param value WorkPhone of the type org.semanticwb.portal.resources.sem.contact.Phone
       * @param model Model of the org.semanticwb.portal.resources.sem.contact.Contact
       * @return Iterator with all the org.semanticwb.portal.resources.sem.contact.Contact
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.contact.Contact> listContactByWorkPhone(org.semanticwb.portal.resources.sem.contact.Phone value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.contact.Contact> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(contact_workPhone, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.contact.Contact with a determined WorkPhone
       * @param value WorkPhone of the type org.semanticwb.portal.resources.sem.contact.Phone
       * @return Iterator with all the org.semanticwb.portal.resources.sem.contact.Contact
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.contact.Contact> listContactByWorkPhone(org.semanticwb.portal.resources.sem.contact.Phone value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.contact.Contact> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(contact_workPhone,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.contact.Contact with a determined WorkAddress
       * @param value WorkAddress of the type org.semanticwb.portal.resources.sem.contact.Address
       * @param model Model of the org.semanticwb.portal.resources.sem.contact.Contact
       * @return Iterator with all the org.semanticwb.portal.resources.sem.contact.Contact
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.contact.Contact> listContactByWorkAddress(org.semanticwb.portal.resources.sem.contact.Address value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.contact.Contact> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(contact_workAddress, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.contact.Contact with a determined WorkAddress
       * @param value WorkAddress of the type org.semanticwb.portal.resources.sem.contact.Address
       * @return Iterator with all the org.semanticwb.portal.resources.sem.contact.Contact
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.contact.Contact> listContactByWorkAddress(org.semanticwb.portal.resources.sem.contact.Address value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.contact.Contact> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(contact_workAddress,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.contact.Contact with a determined FaxPhone
       * @param value FaxPhone of the type org.semanticwb.portal.resources.sem.contact.Phone
       * @param model Model of the org.semanticwb.portal.resources.sem.contact.Contact
       * @return Iterator with all the org.semanticwb.portal.resources.sem.contact.Contact
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.contact.Contact> listContactByFaxPhone(org.semanticwb.portal.resources.sem.contact.Phone value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.contact.Contact> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(contact_faxPhone, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.contact.Contact with a determined FaxPhone
       * @param value FaxPhone of the type org.semanticwb.portal.resources.sem.contact.Phone
       * @return Iterator with all the org.semanticwb.portal.resources.sem.contact.Contact
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.contact.Contact> listContactByFaxPhone(org.semanticwb.portal.resources.sem.contact.Phone value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.contact.Contact> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(contact_faxPhone,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
   * Constructs a ContactBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Contact
   */
    public ContactBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Treatment property
* @return String with the Treatment
*/
    public String getTreatment()
    {
        return getSemanticObject().getProperty(contact_treatment);
    }

/**
* Sets the Treatment property
* @param value long with the Treatment
*/
    public void setTreatment(String value)
    {
        getSemanticObject().setProperty(contact_treatment, value);
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
   * Sets the value for the property HomePhone
   * @param value HomePhone to set
   */

    public void setHomePhone(org.semanticwb.portal.resources.sem.contact.Phone value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(contact_homePhone, value.getSemanticObject());
        }else
        {
            removeHomePhone();
        }
    }
   /**
   * Remove the value for HomePhone property
   */

    public void removeHomePhone()
    {
        getSemanticObject().removeProperty(contact_homePhone);
    }

   /**
   * Gets the HomePhone
   * @return a org.semanticwb.portal.resources.sem.contact.Phone
   */
    public org.semanticwb.portal.resources.sem.contact.Phone getHomePhone()
    {
         org.semanticwb.portal.resources.sem.contact.Phone ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(contact_homePhone);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.contact.Phone)obj.createGenericInstance();
         }
         return ret;
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
   * Sets the value for the property WebPage
   * @param value WebPage to set
   */

    public void setWebPage(org.semanticwb.model.WebPage value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_webPage, value.getSemanticObject());
        }else
        {
            removeWebPage();
        }
    }
   /**
   * Remove the value for WebPage property
   */

    public void removeWebPage()
    {
        getSemanticObject().removeProperty(swb_webPage);
    }

   /**
   * Gets the WebPage
   * @return a org.semanticwb.model.WebPage
   */
    public org.semanticwb.model.WebPage getWebPage()
    {
         org.semanticwb.model.WebPage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_webPage);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.WebPage)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property HomeAddress
   * @param value HomeAddress to set
   */

    public void setHomeAddress(org.semanticwb.portal.resources.sem.contact.Address value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(contact_homeAddress, value.getSemanticObject());
        }else
        {
            removeHomeAddress();
        }
    }
   /**
   * Remove the value for HomeAddress property
   */

    public void removeHomeAddress()
    {
        getSemanticObject().removeProperty(contact_homeAddress);
    }

   /**
   * Gets the HomeAddress
   * @return a org.semanticwb.portal.resources.sem.contact.Address
   */
    public org.semanticwb.portal.resources.sem.contact.Address getHomeAddress()
    {
         org.semanticwb.portal.resources.sem.contact.Address ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(contact_homeAddress);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.contact.Address)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the LastName property
* @return String with the LastName
*/
    public String getLastName()
    {
        return getSemanticObject().getProperty(contact_lastName);
    }

/**
* Sets the LastName property
* @param value long with the LastName
*/
    public void setLastName(String value)
    {
        getSemanticObject().setProperty(contact_lastName, value);
    }

/**
* Gets the Index property
* @return int with the Index
*/
    public int getIndex()
    {
        return getSemanticObject().getIntProperty(swb_index);
    }

/**
* Sets the Index property
* @param value long with the Index
*/
    public void setIndex(int value)
    {
        getSemanticObject().setIntProperty(swb_index, value);
    }

/**
* Gets the SecondLastName property
* @return String with the SecondLastName
*/
    public String getSecondLastName()
    {
        return getSemanticObject().getProperty(contact_secondLastName);
    }

/**
* Sets the SecondLastName property
* @param value long with the SecondLastName
*/
    public void setSecondLastName(String value)
    {
        getSemanticObject().setProperty(contact_secondLastName, value);
    }
   /**
   * Sets the value for the property WorkPhone2
   * @param value WorkPhone2 to set
   */

    public void setWorkPhone2(org.semanticwb.portal.resources.sem.contact.Phone value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(contact_workPhone2, value.getSemanticObject());
        }else
        {
            removeWorkPhone2();
        }
    }
   /**
   * Remove the value for WorkPhone2 property
   */

    public void removeWorkPhone2()
    {
        getSemanticObject().removeProperty(contact_workPhone2);
    }

   /**
   * Gets the WorkPhone2
   * @return a org.semanticwb.portal.resources.sem.contact.Phone
   */
    public org.semanticwb.portal.resources.sem.contact.Phone getWorkPhone2()
    {
         org.semanticwb.portal.resources.sem.contact.Phone ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(contact_workPhone2);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.contact.Phone)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property EveningPhone
   * @param value EveningPhone to set
   */

    public void setEveningPhone(org.semanticwb.portal.resources.sem.contact.Phone value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(contact_eveningPhone, value.getSemanticObject());
        }else
        {
            removeEveningPhone();
        }
    }
   /**
   * Remove the value for EveningPhone property
   */

    public void removeEveningPhone()
    {
        getSemanticObject().removeProperty(contact_eveningPhone);
    }

   /**
   * Gets the EveningPhone
   * @return a org.semanticwb.portal.resources.sem.contact.Phone
   */
    public org.semanticwb.portal.resources.sem.contact.Phone getEveningPhone()
    {
         org.semanticwb.portal.resources.sem.contact.Phone ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(contact_eveningPhone);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.contact.Phone)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property Category
   * @param value Category to set
   */

    public void setCategory(org.semanticwb.portal.resources.sem.contact.Category value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(contact_category, value.getSemanticObject());
        }else
        {
            removeCategory();
        }
    }
   /**
   * Remove the value for Category property
   */

    public void removeCategory()
    {
        getSemanticObject().removeProperty(contact_category);
    }

   /**
   * Gets the Category
   * @return a org.semanticwb.portal.resources.sem.contact.Category
   */
    public org.semanticwb.portal.resources.sem.contact.Category getCategory()
    {
         org.semanticwb.portal.resources.sem.contact.Category ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(contact_category);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.contact.Category)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property MobilePhone
   * @param value MobilePhone to set
   */

    public void setMobilePhone(org.semanticwb.portal.resources.sem.contact.Phone value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(contact_mobilePhone, value.getSemanticObject());
        }else
        {
            removeMobilePhone();
        }
    }
   /**
   * Remove the value for MobilePhone property
   */

    public void removeMobilePhone()
    {
        getSemanticObject().removeProperty(contact_mobilePhone);
    }

   /**
   * Gets the MobilePhone
   * @return a org.semanticwb.portal.resources.sem.contact.Phone
   */
    public org.semanticwb.portal.resources.sem.contact.Phone getMobilePhone()
    {
         org.semanticwb.portal.resources.sem.contact.Phone ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(contact_mobilePhone);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.contact.Phone)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Webpage property
* @return String with the Webpage
*/
    public String getWebpage()
    {
        return getSemanticObject().getProperty(contact_webpage);
    }

/**
* Sets the Webpage property
* @param value long with the Webpage
*/
    public void setWebpage(String value)
    {
        getSemanticObject().setProperty(contact_webpage, value);
    }

/**
* Gets the Email property
* @return String with the Email
*/
    public String getEmail()
    {
        return getSemanticObject().getProperty(contact_email);
    }

/**
* Sets the Email property
* @param value long with the Email
*/
    public void setEmail(String value)
    {
        getSemanticObject().setProperty(contact_email, value);
    }
   /**
   * Sets the value for the property OtherAddress
   * @param value OtherAddress to set
   */

    public void setOtherAddress(org.semanticwb.portal.resources.sem.contact.Address value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(contact_otherAddress, value.getSemanticObject());
        }else
        {
            removeOtherAddress();
        }
    }
   /**
   * Remove the value for OtherAddress property
   */

    public void removeOtherAddress()
    {
        getSemanticObject().removeProperty(contact_otherAddress);
    }

   /**
   * Gets the OtherAddress
   * @return a org.semanticwb.portal.resources.sem.contact.Address
   */
    public org.semanticwb.portal.resources.sem.contact.Address getOtherAddress()
    {
         org.semanticwb.portal.resources.sem.contact.Address ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(contact_otherAddress);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.contact.Address)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Position property
* @return String with the Position
*/
    public String getPosition()
    {
        return getSemanticObject().getProperty(contact_position);
    }

/**
* Sets the Position property
* @param value long with the Position
*/
    public void setPosition(String value)
    {
        getSemanticObject().setProperty(contact_position, value);
    }

/**
* Gets the IconClass property
* @return String with the IconClass
*/
    public String getIconClass()
    {
        return getSemanticObject().getProperty(swb_iconClass);
    }

/**
* Sets the IconClass property
* @param value long with the IconClass
*/
    public void setIconClass(String value)
    {
        getSemanticObject().setProperty(swb_iconClass, value);
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
* Gets the Organization property
* @return String with the Organization
*/
    public String getOrganization()
    {
        return getSemanticObject().getProperty(contact_organization);
    }

/**
* Sets the Organization property
* @param value long with the Organization
*/
    public void setOrganization(String value)
    {
        getSemanticObject().setProperty(contact_organization, value);
    }
   /**
   * Sets the value for the property WorkPhone
   * @param value WorkPhone to set
   */

    public void setWorkPhone(org.semanticwb.portal.resources.sem.contact.Phone value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(contact_workPhone, value.getSemanticObject());
        }else
        {
            removeWorkPhone();
        }
    }
   /**
   * Remove the value for WorkPhone property
   */

    public void removeWorkPhone()
    {
        getSemanticObject().removeProperty(contact_workPhone);
    }

   /**
   * Gets the WorkPhone
   * @return a org.semanticwb.portal.resources.sem.contact.Phone
   */
    public org.semanticwb.portal.resources.sem.contact.Phone getWorkPhone()
    {
         org.semanticwb.portal.resources.sem.contact.Phone ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(contact_workPhone);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.contact.Phone)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the FirstName property
* @return String with the FirstName
*/
    public String getFirstName()
    {
        return getSemanticObject().getProperty(contact_firstName);
    }

/**
* Sets the FirstName property
* @param value long with the FirstName
*/
    public void setFirstName(String value)
    {
        getSemanticObject().setProperty(contact_firstName, value);
    }

/**
* Gets the Photo property
* @return String with the Photo
*/
    public String getPhoto()
    {
        return getSemanticObject().getProperty(contact_photo);
    }

/**
* Sets the Photo property
* @param value long with the Photo
*/
    public void setPhoto(String value)
    {
        getSemanticObject().setProperty(contact_photo, value);
    }
   /**
   * Sets the value for the property WorkAddress
   * @param value WorkAddress to set
   */

    public void setWorkAddress(org.semanticwb.portal.resources.sem.contact.Address value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(contact_workAddress, value.getSemanticObject());
        }else
        {
            removeWorkAddress();
        }
    }
   /**
   * Remove the value for WorkAddress property
   */

    public void removeWorkAddress()
    {
        getSemanticObject().removeProperty(contact_workAddress);
    }

   /**
   * Gets the WorkAddress
   * @return a org.semanticwb.portal.resources.sem.contact.Address
   */
    public org.semanticwb.portal.resources.sem.contact.Address getWorkAddress()
    {
         org.semanticwb.portal.resources.sem.contact.Address ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(contact_workAddress);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.contact.Address)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Email2 property
* @return String with the Email2
*/
    public String getEmail2()
    {
        return getSemanticObject().getProperty(contact_email2);
    }

/**
* Sets the Email2 property
* @param value long with the Email2
*/
    public void setEmail2(String value)
    {
        getSemanticObject().setProperty(contact_email2, value);
    }

/**
* Gets the Email3 property
* @return String with the Email3
*/
    public String getEmail3()
    {
        return getSemanticObject().getProperty(contact_email3);
    }

/**
* Sets the Email3 property
* @param value long with the Email3
*/
    public void setEmail3(String value)
    {
        getSemanticObject().setProperty(contact_email3, value);
    }
   /**
   * Sets the value for the property FaxPhone
   * @param value FaxPhone to set
   */

    public void setFaxPhone(org.semanticwb.portal.resources.sem.contact.Phone value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(contact_faxPhone, value.getSemanticObject());
        }else
        {
            removeFaxPhone();
        }
    }
   /**
   * Remove the value for FaxPhone property
   */

    public void removeFaxPhone()
    {
        getSemanticObject().removeProperty(contact_faxPhone);
    }

   /**
   * Gets the FaxPhone
   * @return a org.semanticwb.portal.resources.sem.contact.Phone
   */
    public org.semanticwb.portal.resources.sem.contact.Phone getFaxPhone()
    {
         org.semanticwb.portal.resources.sem.contact.Phone ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(contact_faxPhone);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.contact.Phone)obj.createGenericInstance();
         }
         return ret;
    }
}
