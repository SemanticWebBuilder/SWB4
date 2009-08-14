package org.semanticwb.portal.community.base;


public class PostElementBase extends org.semanticwb.portal.community.MicroSiteElement implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Viewable,org.semanticwb.model.Traceable,org.semanticwb.model.Rankable
{
    public static final org.semanticwb.platform.SemanticClass swbcomm_PostElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#PostElement");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#PostElement");

    public PostElementBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.portal.community.PostElement> listPostElements(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.PostElement>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.portal.community.PostElement> listPostElements()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.PostElement>(it, true);
    }

    public static org.semanticwb.portal.community.PostElement createPostElement(org.semanticwb.model.SWBModel model)
    {
        long id=model.getSemanticObject().getModel().getCounter(sclass);
        return org.semanticwb.portal.community.PostElement.createPostElement(String.valueOf(id), model);
    }

    public static org.semanticwb.portal.community.PostElement getPostElement(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.community.PostElement)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.portal.community.PostElement createPostElement(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.community.PostElement)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removePostElement(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasPostElement(String id, org.semanticwb.model.SWBModel model)
    {
        return (getPostElement(id, model)!=null);
    }
}
