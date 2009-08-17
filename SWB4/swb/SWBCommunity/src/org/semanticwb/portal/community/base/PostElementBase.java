package org.semanticwb.portal.community.base;


public class PostElementBase extends org.semanticwb.portal.community.MicroSiteElement implements org.semanticwb.model.Viewable,org.semanticwb.model.Rankable,org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass swbcomm_Blog=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Blog");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_blogInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#blogInv");
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

    public void setBlog(org.semanticwb.portal.community.Blog value)
    {
        getSemanticObject().setObjectProperty(swbcomm_blogInv, value.getSemanticObject());
    }

    public void removeBlog()
    {
        getSemanticObject().removeProperty(swbcomm_blogInv);
    }

   public static java.util.Iterator<org.semanticwb.portal.community.PostElement> listPostElementByBlog(org.semanticwb.portal.community.Blog bloginv,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.PostElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbcomm_blogInv, bloginv.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.PostElement> listPostElementByBlog(org.semanticwb.portal.community.Blog bloginv)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.PostElement> it=new org.semanticwb.model.GenericIterator(bloginv.getSemanticObject().getModel().listSubjects(swbcomm_blogInv,bloginv.getSemanticObject()));
       return it;
   }

    public org.semanticwb.portal.community.Blog getBlog()
    {
         org.semanticwb.portal.community.Blog ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbcomm_blogInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.community.Blog)obj.createGenericInstance();
         }
         return ret;
    }
}
