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


public abstract class ContactDirectoryBase extends org.semanticwb.portal.api.GenericSemResource 
{
    public static final org.semanticwb.platform.SemanticClass contact_Contact=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/Contact#Contact");
    public static final org.semanticwb.platform.SemanticProperty contact_hasContact=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/Contact#hasContact");
    public static final org.semanticwb.platform.SemanticClass swb_Role=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Role");
    public static final org.semanticwb.platform.SemanticProperty contact_editRole=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/Contact#editRole");
    public static final org.semanticwb.platform.SemanticClass swb_Resource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Resource");
    public static final org.semanticwb.platform.SemanticProperty swb_semanticResourceInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#semanticResourceInv");
    public static final org.semanticwb.platform.SemanticClass contact_ContactDirectory=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/Contact#ContactDirectory");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/Contact#ContactDirectory");

    public ContactDirectoryBase()
    {
    }

   /**
   * Constructs a ContactDirectoryBase with a SemanticObject
   * @param base The SemanticObject with the properties for the ContactDirectory
   */
    public ContactDirectoryBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /*
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() 
    {
        return getSemanticObject().hashCode();
    }

    /*
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) 
    {
        if(obj==null)return false;
        return hashCode()==obj.hashCode();
    }
   /**
   * Gets all the org.semanticwb.portal.resources.sem.contact.Contact
   * @return A GenericIterator with all the org.semanticwb.portal.resources.sem.contact.Contact
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.contact.Contact> listContacts()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.contact.Contact>(getSemanticObject().listObjectProperties(contact_hasContact));
    }

   /**
   * Gets true if has a Contact
   * @param value org.semanticwb.portal.resources.sem.contact.Contact to verify
   * @return true if the org.semanticwb.portal.resources.sem.contact.Contact exists, false otherwise
   */
    public boolean hasContact(org.semanticwb.portal.resources.sem.contact.Contact value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(contact_hasContact,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a Contact
   * @param value org.semanticwb.portal.resources.sem.contact.Contact to add
   */

    public void addContact(org.semanticwb.portal.resources.sem.contact.Contact value)
    {
        getSemanticObject().addObjectProperty(contact_hasContact, value.getSemanticObject());
    }
   /**
   * Removes all the Contact
   */

    public void removeAllContact()
    {
        getSemanticObject().removeProperty(contact_hasContact);
    }
   /**
   * Removes a Contact
   * @param value org.semanticwb.portal.resources.sem.contact.Contact to remove
   */

    public void removeContact(org.semanticwb.portal.resources.sem.contact.Contact value)
    {
        getSemanticObject().removeObjectProperty(contact_hasContact,value.getSemanticObject());
    }

   /**
   * Gets the Contact
   * @return a org.semanticwb.portal.resources.sem.contact.Contact
   */
    public org.semanticwb.portal.resources.sem.contact.Contact getContact()
    {
         org.semanticwb.portal.resources.sem.contact.Contact ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(contact_hasContact);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.contact.Contact)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property EditRole
   * @param value EditRole to set
   */

    public void setEditRole(org.semanticwb.model.Role value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(contact_editRole, value.getSemanticObject());
        }else
        {
            removeEditRole();
        }
    }
   /**
   * Remove the value for EditRole property
   */

    public void removeEditRole()
    {
        getSemanticObject().removeProperty(contact_editRole);
    }

   /**
   * Gets the EditRole
   * @return a org.semanticwb.model.Role
   */
    public org.semanticwb.model.Role getEditRole()
    {
         org.semanticwb.model.Role ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(contact_editRole);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Role)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property Resource
   * @param value Resource to set
   */

    public void setResource(org.semanticwb.model.Resource value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_semanticResourceInv, value.getSemanticObject());
        }else
        {
            removeResource();
        }
    }
   /**
   * Remove the value for Resource property
   */

    public void removeResource()
    {
        getSemanticObject().removeProperty(swb_semanticResourceInv);
    }

   /**
   * Gets the Resource
   * @return a org.semanticwb.model.Resource
   */
    public org.semanticwb.model.Resource getResource()
    {
         org.semanticwb.model.Resource ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_semanticResourceInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Resource)obj.createGenericInstance();
         }
         return ret;
    }
}
