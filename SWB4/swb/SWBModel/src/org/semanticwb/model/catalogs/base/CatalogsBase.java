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


public class CatalogsBase extends org.semanticwb.model.SWBModel 
{
    public static final org.semanticwb.platform.SemanticClass swbc_Catalogs=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/swbcatalogs#Catalogs");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/swbcatalogs#Catalogs");

    public CatalogsBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.model.catalogs.Catalogs> listCatalogss(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.catalogs.Catalogs>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.model.catalogs.Catalogs> listCatalogss()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.catalogs.Catalogs>(it, true);
    }

    public static org.semanticwb.model.catalogs.Catalogs getCatalogs(String id)
    {
       org.semanticwb.platform.SemanticMgr mgr=org.semanticwb.SWBPlatform.getSemanticMgr();
       org.semanticwb.model.catalogs.Catalogs ret=null;
       org.semanticwb.platform.SemanticModel model=mgr.getModel(id);
       if(model!=null)
       {
           org.semanticwb.platform.SemanticObject obj=model.getSemanticObject(model.getObjectUri(id,sclass));
           if(obj!=null)
           {
               ret=(org.semanticwb.model.catalogs.Catalogs)obj.createGenericInstance();
           }
       }
       return ret;
    }

    public static org.semanticwb.model.catalogs.Catalogs createCatalogs(String id, String namespace)
    {
        org.semanticwb.platform.SemanticMgr mgr=org.semanticwb.SWBPlatform.getSemanticMgr();
        org.semanticwb.platform.SemanticModel model=mgr.createModel(id, namespace);
        return (org.semanticwb.model.catalogs.Catalogs)model.createGenericObject(model.getObjectUri(id, sclass), sclass);
    }

    public static void removeCatalogs(String id)
    {
       org.semanticwb.model.catalogs.Catalogs obj=getCatalogs(id);
       if(obj!=null)
       {
           obj.remove();
       }
    }

    public static boolean hasCatalogs(String id)
    {
        return (getCatalogs(id)!=null);
    }
}
