package org.semanticwb.model.base;


public abstract class OntologyBase extends org.semanticwb.model.SWBModel implements org.semanticwb.model.FilterableClass,org.semanticwb.model.Descriptiveable,org.semanticwb.model.FilterableNode,org.semanticwb.model.Traceable,org.semanticwb.model.Undeleteable,org.semanticwb.model.Filterable
{
    public static final org.semanticwb.platform.SemanticClass swb_SparqlQuery=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#SparqlQuery");
    public static final org.semanticwb.platform.SemanticClass swb_InfRule=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#InfRule");
    public static final org.semanticwb.platform.SemanticClass swb_Ontology=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Ontology");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Ontology");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.model.Ontology> listOntologies(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Ontology>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.model.Ontology> listOntologies()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Ontology>(it, true);
        }

        public static org.semanticwb.model.Ontology getOntology(String id)
        {
            org.semanticwb.platform.SemanticMgr mgr=org.semanticwb.SWBPlatform.getSemanticMgr();
            org.semanticwb.model.Ontology ret=null;
            org.semanticwb.platform.SemanticModel model=mgr.getModel(id);
            if(model!=null)
            {
                org.semanticwb.platform.SemanticObject obj=model.getSemanticObject(model.getObjectUri(id,sclass));
                if(obj!=null)
                {
                    ret=(org.semanticwb.model.Ontology)obj.createGenericInstance();
                }
            }
            return ret;
        }

        public static org.semanticwb.model.Ontology createOntology(String id, String namespace)
        {
            org.semanticwb.platform.SemanticMgr mgr=org.semanticwb.SWBPlatform.getSemanticMgr();
            org.semanticwb.platform.SemanticModel model=mgr.createModel(id, namespace);
            return (org.semanticwb.model.Ontology)model.createGenericObject(model.getObjectUri(id, sclass), sclass);
        }

        public static void removeOntology(String id)
        {
            org.semanticwb.model.Ontology obj=getOntology(id);
            if(obj!=null)
            {
                obj.remove();
            }
        }

        public static boolean hasOntology(String id)
        {
            return (getOntology(id)!=null);
        }

        public static java.util.Iterator<org.semanticwb.model.Ontology> listOntologyByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Ontology> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.Ontology> listOntologyByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Ontology> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.Ontology> listOntologyByParentWebSite(org.semanticwb.model.WebSite value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Ontology> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_parentWebSite, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.Ontology> listOntologyByParentWebSite(org.semanticwb.model.WebSite value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Ontology> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_parentWebSite,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.Ontology> listOntologyByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Ontology> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.Ontology> listOntologyByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Ontology> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public OntologyBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(swb_created);
    }

    public void setCreated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_created, value);
    }

    public void setModifiedBy(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(swb_modifiedBy, value.getSemanticObject());
    }

    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(swb_modifiedBy);
    }

    public org.semanticwb.model.User getModifiedBy()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_modifiedBy);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

    public void setTitle(String value)
    {
        getSemanticObject().setProperty(swb_title, value);
    }

    public String getTitle(String lang)
    {
        return getSemanticObject().getProperty(swb_title, null, lang);
    }

    public String getDisplayTitle(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_title, lang);
    }

    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(swb_title, title, lang);
    }

    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(swb_updated);
    }

    public void setUpdated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_updated, value);
    }

    public void setCreator(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(swb_creator, value.getSemanticObject());
    }

    public void removeCreator()
    {
        getSemanticObject().removeProperty(swb_creator);
    }

    public org.semanticwb.model.User getCreator()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_creator);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

    public String getDescription()
    {
        return getSemanticObject().getProperty(swb_description);
    }

    public void setDescription(String value)
    {
        getSemanticObject().setProperty(swb_description, value);
    }

    public String getDescription(String lang)
    {
        return getSemanticObject().getProperty(swb_description, null, lang);
    }

    public String getDisplayDescription(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_description, lang);
    }

    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(swb_description, description, lang);
    }

    public boolean isUndeleteable()
    {
        return getSemanticObject().getBooleanProperty(swb_undeleteable);
    }

    public void setUndeleteable(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_undeleteable, value);
    }

    public org.semanticwb.model.SparqlQuery getSparqlQuery(String id)
    {
        return org.semanticwb.model.SparqlQuery.ClassMgr.getSparqlQuery(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.SparqlQuery> listSparqlQueries()
    {
        return org.semanticwb.model.SparqlQuery.ClassMgr.listSparqlQueries(this);
    }

    public org.semanticwb.model.SparqlQuery createSparqlQuery(String id)
    {
        return org.semanticwb.model.SparqlQuery.ClassMgr.createSparqlQuery(id,this);
    }

    public org.semanticwb.model.SparqlQuery createSparqlQuery()
    {
        long id=getSemanticObject().getModel().getCounter(swb_SparqlQuery);
        return org.semanticwb.model.SparqlQuery.ClassMgr.createSparqlQuery(String.valueOf(id),this);
    } 

    public void removeSparqlQuery(String id)
    {
        org.semanticwb.model.SparqlQuery.ClassMgr.removeSparqlQuery(id, this);
    }
    public boolean hasSparqlQuery(String id)
    {
        return org.semanticwb.model.SparqlQuery.ClassMgr.hasSparqlQuery(id, this);
    }

    public org.semanticwb.model.InfRule getInfRule(String id)
    {
        return org.semanticwb.model.InfRule.ClassMgr.getInfRule(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.InfRule> listInfRules()
    {
        return org.semanticwb.model.InfRule.ClassMgr.listInfRules(this);
    }

    public org.semanticwb.model.InfRule createInfRule(String id)
    {
        return org.semanticwb.model.InfRule.ClassMgr.createInfRule(id,this);
    }

    public org.semanticwb.model.InfRule createInfRule()
    {
        long id=getSemanticObject().getModel().getCounter(swb_InfRule);
        return org.semanticwb.model.InfRule.ClassMgr.createInfRule(String.valueOf(id),this);
    } 

    public void removeInfRule(String id)
    {
        org.semanticwb.model.InfRule.ClassMgr.removeInfRule(id, this);
    }
    public boolean hasInfRule(String id)
    {
        return org.semanticwb.model.InfRule.ClassMgr.hasInfRule(id, this);
    }
}
