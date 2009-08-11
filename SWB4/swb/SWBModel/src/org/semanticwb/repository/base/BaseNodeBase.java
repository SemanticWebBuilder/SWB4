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
 
package org.semanticwb.repository.base;


public class BaseNodeBase extends org.semanticwb.model.base.GenericObjectBase 
{
    public static final org.semanticwb.platform.SemanticProperty jcr_primaryType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#primaryType");
    public static final org.semanticwb.platform.SemanticClass nt_BaseNode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#base");
    public static final org.semanticwb.platform.SemanticProperty swbrep_parentNode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/repository#parentNode");
    public static final org.semanticwb.platform.SemanticProperty swbrep_path=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/repository#path");
    public static final org.semanticwb.platform.SemanticProperty swbrep_name=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/repository#name");
    public static final org.semanticwb.platform.SemanticProperty swbrep_hasNodes=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/repository#hasNodes");
    public static final org.semanticwb.platform.SemanticProperty jcr_mixinTypes=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#mixinTypes");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#base");

    public BaseNodeBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.repository.BaseNode> listBaseNodes(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.repository.BaseNode>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.repository.BaseNode> listBaseNodes()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.repository.BaseNode>(it, true);
    }

    public static org.semanticwb.repository.BaseNode getBaseNode(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.repository.BaseNode)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.repository.BaseNode createBaseNode(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.repository.BaseNode)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeBaseNode(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasBaseNode(String id, org.semanticwb.model.SWBModel model)
    {
        return (getBaseNode(id, model)!=null);
    }

    public String getPrimaryType()
    {
        return getSemanticObject().getProperty(jcr_primaryType);
    }

    public void setPrimaryType(String primaryType)
    {
        getSemanticObject().setProperty(jcr_primaryType, primaryType);
    }

    public void setParent(org.semanticwb.repository.BaseNode basenode)
    {
        getSemanticObject().setObjectProperty(swbrep_parentNode, basenode.getSemanticObject());
    }

    public void removeParent()
    {
        getSemanticObject().removeProperty(swbrep_parentNode);
    }

    public org.semanticwb.repository.BaseNode getParent()
    {
         org.semanticwb.repository.BaseNode ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbrep_parentNode);
         if(obj!=null)
         {
             ret=(org.semanticwb.repository.BaseNode)obj.createGenericInstance();
         }
         return ret;
    }

    public String getPath()
    {
        return getSemanticObject().getProperty(swbrep_path);
    }

    public void setPath(String path)
    {
        getSemanticObject().setProperty(swbrep_path, path);
    }

    public String getName()
    {
        return getSemanticObject().getProperty(swbrep_name);
    }

    public void setName(String name)
    {
        getSemanticObject().setProperty(swbrep_name, name);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.repository.BaseNode> listNodes()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.repository.BaseNode>(getSemanticObject().listObjectProperties(swbrep_hasNodes));
    }

    public boolean hasNode(org.semanticwb.repository.BaseNode basenode)
    {
        if(basenode==null)return false;        return getSemanticObject().hasObjectProperty(swbrep_hasNodes,basenode.getSemanticObject());
    }

    public org.semanticwb.repository.BaseNode getNode()
    {
         org.semanticwb.repository.BaseNode ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbrep_hasNodes);
         if(obj!=null)
         {
             ret=(org.semanticwb.repository.BaseNode)obj.createGenericInstance();
         }
         return ret;
    }

    public String getMixinTypes()
    {
        return getSemanticObject().getProperty(jcr_mixinTypes);
    }

    public void setMixinTypes(String mixinTypes)
    {
        getSemanticObject().setProperty(jcr_mixinTypes, mixinTypes);
    }

    public void remove()
    {
        getSemanticObject().remove();
    }

    public java.util.Iterator<org.semanticwb.model.GenericObject> listRelatedObjects()
    {
        return new org.semanticwb.model.GenericIterator(getSemanticObject().listRelatedObjects(),true);
    }

    public org.semanticwb.repository.Workspace getWorkspace()
    {
        return (org.semanticwb.repository.Workspace)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
