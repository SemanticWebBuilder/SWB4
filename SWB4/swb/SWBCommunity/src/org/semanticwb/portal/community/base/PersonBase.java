package org.semanticwb.portal.community.base;

import org.semanticwb.portal.community.Location;


public class PersonBase extends org.semanticwb.portal.community.DirectoryObject implements org.semanticwb.model.Mapable,org.semanticwb.model.Descriptiveable,org.semanticwb.portal.community.Location,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticClass swbcomm_Person=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Person");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Person");

    public PersonBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.portal.community.Person> listPersons(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Person>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.portal.community.Person> listPersons()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Person>(it, true);
    }

    public static org.semanticwb.portal.community.Person createPerson(org.semanticwb.model.SWBModel model)
    {
        long id=model.getSemanticObject().getModel().getCounter(sclass);
        return org.semanticwb.portal.community.Person.createPerson(String.valueOf(id), model);
    }

    public static org.semanticwb.portal.community.Person getPerson(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.community.Person)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.portal.community.Person createPerson(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.community.Person)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removePerson(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasPerson(String id, org.semanticwb.model.SWBModel model)
    {
        return (getPerson(id, model)!=null);
    }

    public double getLatitude()
    {
        return getSemanticObject().getDoubleProperty(Location.swb_latitude);
    }

    public void setLatitude(double value)
    {
        getSemanticObject().setDoubleProperty(Location.swb_latitude, value);
    }

    public double getLongitude()
    {
        return getSemanticObject().getDoubleProperty(Location.swb_longitude);
    }

    public void setLongitude(double value)
    {
        getSemanticObject().setDoubleProperty(Location.swb_longitude, value);
    }
}
