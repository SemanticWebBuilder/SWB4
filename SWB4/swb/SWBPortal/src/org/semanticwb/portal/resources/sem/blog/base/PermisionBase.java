package org.semanticwb.portal.resources.sem.blog.base;


public abstract class PermisionBase extends org.semanticwb.model.SWBClass 
{
    public static final org.semanticwb.platform.SemanticProperty blog_level=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/wb2.0/blog#level");
    public static final org.semanticwb.platform.SemanticProperty blog_isRol=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/wb2.0/blog#isRol");
    public static final org.semanticwb.platform.SemanticProperty blog_securityId=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/wb2.0/blog#securityId");
    public static final org.semanticwb.platform.SemanticClass blog_Permision=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/wb2.0/blog#Permision");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/wb2.0/blog#Permision");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.blog.Permision> listPermisions(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.blog.Permision>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.blog.Permision> listPermisions()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.blog.Permision>(it, true);
        }

        public static org.semanticwb.portal.resources.sem.blog.Permision createPermision(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.portal.resources.sem.blog.Permision.ClassMgr.createPermision(String.valueOf(id), model);
        }

        public static org.semanticwb.portal.resources.sem.blog.Permision getPermision(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.blog.Permision)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.portal.resources.sem.blog.Permision createPermision(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.blog.Permision)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removePermision(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasPermision(String id, org.semanticwb.model.SWBModel model)
        {
            return (getPermision(id, model)!=null);
        }
    }

    public PermisionBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public int getLevel()
    {
        return getSemanticObject().getIntProperty(blog_level);
    }

    public void setLevel(int value)
    {
        getSemanticObject().setIntProperty(blog_level, value);
    }

    public boolean isIsRol()
    {
        return getSemanticObject().getBooleanProperty(blog_isRol);
    }

    public void setIsRol(boolean value)
    {
        getSemanticObject().setBooleanProperty(blog_isRol, value);
    }

    public String getSecurityId()
    {
        return getSemanticObject().getProperty(blog_securityId);
    }

    public void setSecurityId(String value)
    {
        getSemanticObject().setProperty(blog_securityId, value);
    }
}
