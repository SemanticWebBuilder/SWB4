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


public class IPRangeBase extends org.semanticwb.model.SWBClass 
{
    public static final org.semanticwb.platform.SemanticClass swbc_LocationEntity=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/swbcatalogs#LocationEntity");
    public static final org.semanticwb.platform.SemanticProperty swbc_location=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/swbcatalogs#location");
    public static final org.semanticwb.platform.SemanticProperty swbc_finalIP=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/swbcatalogs#finalIP");
    public static final org.semanticwb.platform.SemanticProperty swbc_initialIP=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/swbcatalogs#initialIP");
    public static final org.semanticwb.platform.SemanticClass swbc_IPRange=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/swbcatalogs#IPRange");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/swbcatalogs#IPRange");

    public IPRangeBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.model.catalogs.IPRange> listIPRanges(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.catalogs.IPRange>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.model.catalogs.IPRange> listIPRanges()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.catalogs.IPRange>(it, true);
    }

    public static org.semanticwb.model.catalogs.IPRange getIPRange(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.catalogs.IPRange)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.model.catalogs.IPRange createIPRange(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.catalogs.IPRange)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeIPRange(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasIPRange(String id, org.semanticwb.model.SWBModel model)
    {
        return (getIPRange(id, model)!=null);
    }

    public void setLocation(org.semanticwb.model.catalogs.LocationEntity locationentity)
    {
        getSemanticObject().setObjectProperty(swbc_location, locationentity.getSemanticObject());
    }

    public void removeLocation()
    {
        getSemanticObject().removeProperty(swbc_location);
    }

    public org.semanticwb.model.catalogs.LocationEntity getLocation()
    {
         org.semanticwb.model.catalogs.LocationEntity ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbc_location);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.catalogs.LocationEntity)obj.createGenericInstance();
         }
         return ret;
    }

    public long getFinalIP()
    {
        return getSemanticObject().getLongProperty(swbc_finalIP);
    }

    public void setFinalIP(long finalIP)
    {
        getSemanticObject().setLongProperty(swbc_finalIP, finalIP);
    }

    public long getInitialIP()
    {
        return getSemanticObject().getLongProperty(swbc_initialIP);
    }

    public void setInitialIP(long initialIP)
    {
        getSemanticObject().setLongProperty(swbc_initialIP, initialIP);
    }
}
