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
package org.semanticwb.portal.resources.sem.catalog.base;


public abstract class SWBCatalogResourceBase extends org.semanticwb.portal.api.GenericSemResource 
{
    public static final org.semanticwb.platform.SemanticProperty catalog_hasCatalogDetailProperties=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/sem/SWBCatalogResource#hasCatalogDetailProperties");
    public static final org.semanticwb.platform.SemanticProperty catalog_hasCatalogSearchProperties=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/sem/SWBCatalogResource#hasCatalogSearchProperties");
    public static final org.semanticwb.platform.SemanticClass swb_Class=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Class");
    public static final org.semanticwb.platform.SemanticProperty catalog_catalogClass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/sem/SWBCatalogResource#catalogClass");
    public static final org.semanticwb.platform.SemanticProperty catalog_hasCatalogListProperties=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/sem/SWBCatalogResource#hasCatalogListProperties");
    public static final org.semanticwb.platform.SemanticClass swb_Resource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Resource");
    public static final org.semanticwb.platform.SemanticProperty swb_semanticResourceInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#semanticResourceInv");
    public static final org.semanticwb.platform.SemanticClass swb_SWBModel=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#SWBModel");
    public static final org.semanticwb.platform.SemanticProperty catalog_catalogModel=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/sem/SWBCatalogResource#catalogModel");
    public static final org.semanticwb.platform.SemanticClass catalog_SWBCatalogResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/sem/SWBCatalogResource#SWBCatalogResource");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/sem/SWBCatalogResource#SWBCatalogResource");

    public SWBCatalogResourceBase()
    {
    }

   /**
   * Constructs a SWBCatalogResourceBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SWBCatalogResource
   */
    public SWBCatalogResourceBase(org.semanticwb.platform.SemanticObject base)
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

    public java.util.Iterator<String> listDetailPropertieses()
    {
        java.util.ArrayList<String> values=new java.util.ArrayList<String>();
        java.util.Iterator<org.semanticwb.platform.SemanticLiteral> it=getSemanticObject().listLiteralProperties(catalog_hasCatalogDetailProperties);
        while(it.hasNext())
        {
                org.semanticwb.platform.SemanticLiteral literal=it.next();
                values.add(literal.getString());
        }
        return values.iterator();
    }

    public void addDetailProperties(String value)
    {
        getSemanticObject().addLiteralProperty(catalog_hasCatalogDetailProperties, new org.semanticwb.platform.SemanticLiteral(value));
    }

    public void removeAllDetailProperties()
    {
        getSemanticObject().removeProperty(catalog_hasCatalogDetailProperties);
    }

    public void removeDetailProperties(String value)
    {
        getSemanticObject().removeLiteralProperty(catalog_hasCatalogDetailProperties,new org.semanticwb.platform.SemanticLiteral(value));
    }

    public java.util.Iterator<String> listSearchPropertieses()
    {
        java.util.ArrayList<String> values=new java.util.ArrayList<String>();
        java.util.Iterator<org.semanticwb.platform.SemanticLiteral> it=getSemanticObject().listLiteralProperties(catalog_hasCatalogSearchProperties);
        while(it.hasNext())
        {
                org.semanticwb.platform.SemanticLiteral literal=it.next();
                values.add(literal.getString());
        }
        return values.iterator();
    }

    public void addSearchProperties(String value)
    {
        getSemanticObject().addLiteralProperty(catalog_hasCatalogSearchProperties, new org.semanticwb.platform.SemanticLiteral(value));
    }

    public void removeAllSearchProperties()
    {
        getSemanticObject().removeProperty(catalog_hasCatalogSearchProperties);
    }

    public void removeSearchProperties(String value)
    {
        getSemanticObject().removeLiteralProperty(catalog_hasCatalogSearchProperties,new org.semanticwb.platform.SemanticLiteral(value));
    }

    public void setCatalogClass(org.semanticwb.platform.SemanticObject value)
    {
        getSemanticObject().setObjectProperty(catalog_catalogClass, value);
    }

    public void removeCatalogClass()
    {
        getSemanticObject().removeProperty(catalog_catalogClass);
    }

/**
* Gets the CatalogClass property
* @return the value for the property as org.semanticwb.platform.SemanticObject
*/
    public org.semanticwb.platform.SemanticObject getCatalogClass()
    {
         org.semanticwb.platform.SemanticObject ret=null;
         ret=getSemanticObject().getObjectProperty(catalog_catalogClass);
         return ret;
    }

    public java.util.Iterator<String> listListPropertieses()
    {
        java.util.ArrayList<String> values=new java.util.ArrayList<String>();
        java.util.Iterator<org.semanticwb.platform.SemanticLiteral> it=getSemanticObject().listLiteralProperties(catalog_hasCatalogListProperties);
        while(it.hasNext())
        {
                org.semanticwb.platform.SemanticLiteral literal=it.next();
                values.add(literal.getString());
        }
        return values.iterator();
    }

    public void addListProperties(String value)
    {
        getSemanticObject().addLiteralProperty(catalog_hasCatalogListProperties, new org.semanticwb.platform.SemanticLiteral(value));
    }

    public void removeAllListProperties()
    {
        getSemanticObject().removeProperty(catalog_hasCatalogListProperties);
    }

    public void removeListProperties(String value)
    {
        getSemanticObject().removeLiteralProperty(catalog_hasCatalogListProperties,new org.semanticwb.platform.SemanticLiteral(value));
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
   /**
   * Sets the value for the property CatalogModel
   * @param value CatalogModel to set
   */

    public void setCatalogModel(org.semanticwb.model.SWBModel value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(catalog_catalogModel, value.getSemanticObject());
        }else
        {
            removeCatalogModel();
        }
    }
   /**
   * Remove the value for CatalogModel property
   */

    public void removeCatalogModel()
    {
        getSemanticObject().removeProperty(catalog_catalogModel);
    }

   /**
   * Gets the CatalogModel
   * @return a org.semanticwb.model.SWBModel
   */
    public org.semanticwb.model.SWBModel getCatalogModel()
    {
         org.semanticwb.model.SWBModel ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(catalog_catalogModel);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.SWBModel)obj.createGenericInstance();
         }
         return ret;
    }
}
