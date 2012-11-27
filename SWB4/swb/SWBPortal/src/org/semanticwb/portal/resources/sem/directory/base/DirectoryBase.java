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
package org.semanticwb.portal.resources.sem.directory.base;


// TODO: Auto-generated Javadoc
/**
 * The Class DirectoryBase.
 */
public class DirectoryBase extends org.semanticwb.portal.api.GenericSemResource 
{
    
    /** The Constant swb_Class. */
    public static final org.semanticwb.platform.SemanticClass swb_Class=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Class");
    
    /** The Constant dir_classBase. */
    public static final org.semanticwb.platform.SemanticProperty dir_classBase=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/Directory#classBase");
    
    /** The Constant dir_OrderField. */
    public static final org.semanticwb.platform.SemanticProperty dir_OrderField=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/Directory#OrderField");
    
    /** The Constant dir_scope. */
    public static final org.semanticwb.platform.SemanticProperty dir_scope=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/Directory#scope");
    
    /** The Constant dir_properties2Display. */
    public static final org.semanticwb.platform.SemanticProperty dir_properties2Display=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/Directory#properties2Display");
    
    /** The Constant dir_Directory. */
    public static final org.semanticwb.platform.SemanticClass dir_Directory=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/Directory#Directory");

    /**
     * Instantiates a new directory base.
     */
    public DirectoryBase()
    {
    }

    /**
     * Instantiates a new directory base.
     * 
     * @param base the base
     */
    public DirectoryBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/Directory#Directory");

    /**
     * Sets the class base.
     * 
     * @param semanticobject the new class base
     */
    public void setClassBase(org.semanticwb.platform.SemanticObject semanticobject)
    {
        getSemanticObject().setObjectProperty(dir_classBase, semanticobject);
    }

    /**
     * Removes the class base.
     */
    public void removeClassBase()
    {
        getSemanticObject().removeProperty(dir_classBase);
    }

    /**
     * Gets the class base.
     * 
     * @return the class base
     */
    public org.semanticwb.platform.SemanticObject getClassBase()
    {
         org.semanticwb.platform.SemanticObject ret=null;
         ret=getSemanticObject().getObjectProperty(dir_classBase);
         return ret;
    }

    /**
     * Gets the order field.
     * 
     * @return the order field
     */
    public String getOrderField()
    {
        return getSemanticObject().getProperty(dir_OrderField);
    }

    /**
     * Sets the order field.
     * 
     * @param OrderField the new order field
     */
    public void setOrderField(String OrderField)
    {
        getSemanticObject().setProperty(dir_OrderField, OrderField);
    }

    /**
     * Gets the scope.
     * 
     * @return the scope
     */
    public String getScope()
    {
        return getSemanticObject().getProperty(dir_scope);
    }

    /**
     * Sets the scope.
     * 
     * @param scope the new scope
     */
    public void setScope(String scope)
    {
        getSemanticObject().setProperty(dir_scope, scope);
    }

    /**
     * Gets the properties2 display.
     * 
     * @return the properties2 display
     */
    public String getProperties2Display()
    {
        return getSemanticObject().getProperty(dir_properties2Display);
    }

    /**
     * Sets the properties2 display.
     * 
     * @param properties2Display the new properties2 display
     */
    public void setProperties2Display(String properties2Display)
    {
        getSemanticObject().setProperty(dir_properties2Display, properties2Display);
    }
}
