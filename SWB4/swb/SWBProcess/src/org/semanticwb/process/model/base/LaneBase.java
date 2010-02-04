package org.semanticwb.process.model.base;


public abstract class LaneBase extends org.semanticwb.process.model.Swimlane implements org.semanticwb.model.Roleable,org.semanticwb.process.model.Laneable,org.semanticwb.model.Ruleable,org.semanticwb.model.UserGroupable,org.semanticwb.model.Descriptiveable
{
       public static final org.semanticwb.platform.SemanticClass swp_Lane=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Lane");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Lane");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.Lane> listLanes(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Lane>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.Lane> listLanes()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Lane>(it, true);
       }

       public static org.semanticwb.process.model.Lane getLane(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.Lane)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.Lane createLane(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.Lane)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeLane(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasLane(String id, org.semanticwb.model.SWBModel model)
       {
           return (getLane(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.Lane> listLaneByLane(org.semanticwb.process.model.Lane haslane,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Lane> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasLane, haslane.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.Lane> listLaneByLane(org.semanticwb.process.model.Lane haslane)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Lane> it=new org.semanticwb.model.GenericIterator(haslane.getSemanticObject().getModel().listSubjects(swp_hasLane,haslane.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.Lane> listLaneByRule(org.semanticwb.model.Rule hasrule,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Lane> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasRule, hasrule.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.Lane> listLaneByRule(org.semanticwb.model.Rule hasrule)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Lane> it=new org.semanticwb.model.GenericIterator(hasrule.getSemanticObject().getModel().listSubjects(swb_hasRule,hasrule.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.Lane> listLaneByUserGroup(org.semanticwb.model.UserGroup hasusergroup,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Lane> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasUserGroup, hasusergroup.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.Lane> listLaneByUserGroup(org.semanticwb.model.UserGroup hasusergroup)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Lane> it=new org.semanticwb.model.GenericIterator(hasusergroup.getSemanticObject().getModel().listSubjects(swb_hasUserGroup,hasusergroup.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.Lane> listLaneByRole(org.semanticwb.model.Role hasrole,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Lane> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasRole, hasrole.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.Lane> listLaneByRole(org.semanticwb.model.Role hasrole)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Lane> it=new org.semanticwb.model.GenericIterator(hasrole.getSemanticObject().getModel().listSubjects(swb_hasRole,hasrole.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.Lane> listLaneByCategory(org.semanticwb.process.model.Category hascategory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Lane> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasCategory, hascategory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.Lane> listLaneByCategory(org.semanticwb.process.model.Category hascategory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Lane> it=new org.semanticwb.model.GenericIterator(hascategory.getSemanticObject().getModel().listSubjects(swp_hasCategory,hascategory.getSemanticObject()));
       return it;
   }
    }

    public LaneBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Lane> listLanes()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Lane>(getSemanticObject().listObjectProperties(swp_hasLane));
    }

    public boolean hasLane(org.semanticwb.process.model.Lane lane)
    {
        if(lane==null)return false;
        return getSemanticObject().hasObjectProperty(swp_hasLane,lane.getSemanticObject());
    }

    public void addLane(org.semanticwb.process.model.Lane value)
    {
        getSemanticObject().addObjectProperty(swp_hasLane, value.getSemanticObject());
    }

    public void removeAllLane()
    {
        getSemanticObject().removeProperty(swp_hasLane);
    }

    public void removeLane(org.semanticwb.process.model.Lane lane)
    {
        getSemanticObject().removeObjectProperty(swp_hasLane,lane.getSemanticObject());
    }


    public org.semanticwb.process.model.Lane getLane()
    {
         org.semanticwb.process.model.Lane ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasLane);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Lane)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.Rule> listRules()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Rule>(getSemanticObject().listObjectProperties(swb_hasRule));
    }

    public boolean hasRule(org.semanticwb.model.Rule rule)
    {
        if(rule==null)return false;
        return getSemanticObject().hasObjectProperty(swb_hasRule,rule.getSemanticObject());
    }

    public void addRule(org.semanticwb.model.Rule value)
    {
        getSemanticObject().addObjectProperty(swb_hasRule, value.getSemanticObject());
    }

    public void removeAllRule()
    {
        getSemanticObject().removeProperty(swb_hasRule);
    }

    public void removeRule(org.semanticwb.model.Rule rule)
    {
        getSemanticObject().removeObjectProperty(swb_hasRule,rule.getSemanticObject());
    }


    public org.semanticwb.model.Rule getRule()
    {
         org.semanticwb.model.Rule ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasRule);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Rule)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroup> listUserGroups()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroup>(getSemanticObject().listObjectProperties(swb_hasUserGroup));
    }

    public boolean hasUserGroup(org.semanticwb.model.UserGroup usergroup)
    {
        if(usergroup==null)return false;
        return getSemanticObject().hasObjectProperty(swb_hasUserGroup,usergroup.getSemanticObject());
    }

    public void addUserGroup(org.semanticwb.model.UserGroup value)
    {
        getSemanticObject().addObjectProperty(swb_hasUserGroup, value.getSemanticObject());
    }

    public void removeAllUserGroup()
    {
        getSemanticObject().removeProperty(swb_hasUserGroup);
    }

    public void removeUserGroup(org.semanticwb.model.UserGroup usergroup)
    {
        getSemanticObject().removeObjectProperty(swb_hasUserGroup,usergroup.getSemanticObject());
    }


    public org.semanticwb.model.UserGroup getUserGroup()
    {
         org.semanticwb.model.UserGroup ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasUserGroup);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.UserGroup)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.Role> listRoles()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Role>(getSemanticObject().listObjectProperties(swb_hasRole));
    }

    public boolean hasRole(org.semanticwb.model.Role role)
    {
        if(role==null)return false;
        return getSemanticObject().hasObjectProperty(swb_hasRole,role.getSemanticObject());
    }

    public void addRole(org.semanticwb.model.Role value)
    {
        getSemanticObject().addObjectProperty(swb_hasRole, value.getSemanticObject());
    }

    public void removeAllRole()
    {
        getSemanticObject().removeProperty(swb_hasRole);
    }

    public void removeRole(org.semanticwb.model.Role role)
    {
        getSemanticObject().removeObjectProperty(swb_hasRole,role.getSemanticObject());
    }


    public org.semanticwb.model.Role getRole()
    {
         org.semanticwb.model.Role ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasRole);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Role)obj.createGenericInstance();
         }
         return ret;
    }
}
