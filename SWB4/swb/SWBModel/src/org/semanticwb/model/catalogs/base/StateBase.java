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


public class StateBase extends org.semanticwb.model.catalogs.LocationEntity implements org.semanticwb.model.TemplateRefable,org.semanticwb.model.Traceable,org.semanticwb.model.Rankable,org.semanticwb.model.UserGroupRefable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.catalogs.GeoTaggable,org.semanticwb.model.PFlowRefable,org.semanticwb.model.Activeable,org.semanticwb.model.Trashable,org.semanticwb.model.Referensable,org.semanticwb.model.Resourceable,org.semanticwb.model.RoleRefable,org.semanticwb.model.Indexable,org.semanticwb.model.Hiddenable,org.semanticwb.model.Undeleteable,org.semanticwb.model.Filterable,org.semanticwb.model.Expirable,org.semanticwb.model.RuleRefable,org.semanticwb.model.CalendarRefable,org.semanticwb.model.Viewable
{
    public static final org.semanticwb.platform.SemanticProperty swbc_stateCode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/swbcatalogs#stateCode");
    public static final org.semanticwb.platform.SemanticClass swbc_County=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/swbcatalogs#County");
    public static final org.semanticwb.platform.SemanticProperty swbc_hasCountyInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/swbcatalogs#hasCountyInv");
    public static final org.semanticwb.platform.SemanticClass swbc_Country=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/swbcatalogs#Country");
    public static final org.semanticwb.platform.SemanticProperty swbc_country=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/swbcatalogs#country");
    public static final org.semanticwb.platform.SemanticClass swbc_State=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/swbcatalogs#State");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/swbcatalogs#State");

    public StateBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.model.catalogs.State> listStates(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.catalogs.State>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.model.catalogs.State> listStates()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.catalogs.State>(it, true);
    }

    public static org.semanticwb.model.catalogs.State getState(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.catalogs.State)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.model.catalogs.State createState(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.catalogs.State)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeState(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasState(String id, org.semanticwb.model.SWBModel model)
    {
        return (getState(id, model)!=null);
    }

    public String getStateCode()
    {
        return getSemanticObject().getProperty(swbc_stateCode);
    }

    public void setStateCode(String stateCode)
    {
        getSemanticObject().setProperty(swbc_stateCode, stateCode);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.catalogs.County> listCountyInvs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.catalogs.County>(getSemanticObject().listObjectProperties(swbc_hasCountyInv));
    }

    public boolean hasCountyInv(org.semanticwb.model.catalogs.County county)
    {
        if(county==null)return false;
        return getSemanticObject().hasObjectProperty(swbc_hasCountyInv,county.getSemanticObject());
    }

    public org.semanticwb.model.catalogs.County getCountyInv()
    {
         org.semanticwb.model.catalogs.County ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbc_hasCountyInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.catalogs.County)obj.createGenericInstance();
         }
         return ret;
    }

    public void setCountry(org.semanticwb.model.catalogs.Country country)
    {
        getSemanticObject().setObjectProperty(swbc_country, country.getSemanticObject());
    }

    public void removeCountry()
    {
        getSemanticObject().removeProperty(swbc_country);
    }

    public org.semanticwb.model.catalogs.Country getCountry()
    {
         org.semanticwb.model.catalogs.Country ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbc_country);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.catalogs.Country)obj.createGenericInstance();
         }
         return ret;
    }
}
