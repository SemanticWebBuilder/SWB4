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
package org.semanticwb.portal.community.base;


public abstract class ProductResourceBase extends org.semanticwb.portal.community.CommunityResource 
{
    public static final org.semanticwb.platform.SemanticClass swbcomm_ProductElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#ProductElement");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_hasProduct=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#hasProduct");
    public static final org.semanticwb.platform.SemanticClass swbcomm_ProductResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#ProductResource");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#ProductResource");

    public ProductResourceBase()
    {
    }

    public ProductResourceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.ProductElement> listProducts()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.ProductElement>(getSemanticObject().listObjectProperties(swbcomm_hasProduct));
    }

    public boolean hasProduct(org.semanticwb.portal.community.ProductElement productelement)
    {
        boolean ret=false;
        if(productelement!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swbcomm_hasProduct,productelement.getSemanticObject());
        }
        return ret;
    }

    public void addProduct(org.semanticwb.portal.community.ProductElement value)
    {
        getSemanticObject().addObjectProperty(swbcomm_hasProduct, value.getSemanticObject());
    }

    public void removeAllProduct()
    {
        getSemanticObject().removeProperty(swbcomm_hasProduct);
    }

    public void removeProduct(org.semanticwb.portal.community.ProductElement productelement)
    {
        getSemanticObject().removeObjectProperty(swbcomm_hasProduct,productelement.getSemanticObject());
    }

    public org.semanticwb.portal.community.ProductElement getProduct()
    {
         org.semanticwb.portal.community.ProductElement ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbcomm_hasProduct);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.community.ProductElement)obj.createGenericInstance();
         }
         return ret;
    }
}
