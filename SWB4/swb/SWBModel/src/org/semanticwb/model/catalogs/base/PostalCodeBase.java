/**  
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
**/ 
 
package org.semanticwb.model.catalogs.base;


public class PostalCodeBase extends org.semanticwb.model.SWBClass 
{
    public static final org.semanticwb.platform.SemanticClass swbc_City=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/swbcatalogs#City");
    public static final org.semanticwb.platform.SemanticProperty swbc_pchasCityInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/swbcatalogs#pchasCityInv");
    public static final org.semanticwb.platform.SemanticProperty swbc_zip=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/swbcatalogs#zip");
    public static final org.semanticwb.platform.SemanticClass swbc_PostalCode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/swbcatalogs#PostalCode");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/swbcatalogs#PostalCode");

    public PostalCodeBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.model.catalogs.PostalCode> listPostalCodes(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.catalogs.PostalCode>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.model.catalogs.PostalCode> listPostalCodes()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.catalogs.PostalCode>(it, true);
    }

    public static org.semanticwb.model.catalogs.PostalCode getPostalCode(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.catalogs.PostalCode)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.model.catalogs.PostalCode createPostalCode(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.catalogs.PostalCode)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removePostalCode(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasPostalCode(String id, org.semanticwb.model.SWBModel model)
    {
        return (getPostalCode(id, model)!=null);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.catalogs.City> listCitys()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.catalogs.City>(getSemanticObject().listObjectProperties(swbc_pchasCityInv));
    }

    public boolean hasCity(org.semanticwb.model.catalogs.City city)
    {
        if(city==null)return false;
        return getSemanticObject().hasObjectProperty(swbc_pchasCityInv,city.getSemanticObject());
    }

    public org.semanticwb.model.catalogs.City getCity()
    {
         org.semanticwb.model.catalogs.City ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbc_pchasCityInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.catalogs.City)obj.createGenericInstance();
         }
         return ret;
    }

    public String getZip()
    {
        return getSemanticObject().getProperty(swbc_zip);
    }

    public void setZip(String zip)
    {
        getSemanticObject().setProperty(swbc_zip, zip);
    }
}
