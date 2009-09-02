package org.semanticwb.portal.community.base;


public class ClasifiedCourseBase extends org.semanticwb.portal.community.Clasified implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable,org.semanticwb.portal.community.Contactable
{
    public static final org.semanticwb.platform.SemanticClass swbcomm_ClasifiedCourse=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#ClasifiedCourse");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#ClasifiedCourse");

    public ClasifiedCourseBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.portal.community.ClasifiedCourse> listClasifiedCourses(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.ClasifiedCourse>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.portal.community.ClasifiedCourse> listClasifiedCourses()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.ClasifiedCourse>(it, true);
    }

    public static org.semanticwb.portal.community.ClasifiedCourse createClasifiedCourse(org.semanticwb.model.SWBModel model)
    {
        long id=model.getSemanticObject().getModel().getCounter(sclass);
        return org.semanticwb.portal.community.ClasifiedCourse.createClasifiedCourse(String.valueOf(id), model);
    }

    public static org.semanticwb.portal.community.ClasifiedCourse getClasifiedCourse(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.community.ClasifiedCourse)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.portal.community.ClasifiedCourse createClasifiedCourse(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.community.ClasifiedCourse)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeClasifiedCourse(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasClasifiedCourse(String id, org.semanticwb.model.SWBModel model)
    {
        return (getClasifiedCourse(id, model)!=null);
    }
}
