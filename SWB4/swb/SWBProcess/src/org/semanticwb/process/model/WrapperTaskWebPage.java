package org.semanticwb.process.model;

import org.semanticwb.model.CalendarRef;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.Resource;
import org.semanticwb.model.RoleRef;
import org.semanticwb.model.RuleRef;
import org.semanticwb.model.TemplateRef;
import org.semanticwb.model.UserGroupRef;


public class WrapperTaskWebPage extends org.semanticwb.process.model.base.WrapperTaskWebPageBase 
{
    public WrapperTaskWebPage(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public GenericIterator<Resource> listResources() {
        UserTask ut=getUserTask();
        if(ut!=null)
        {
            return ut.listResources();
        }
        return super.listResources();
    }

    @Override
    public boolean hasResource(Resource value) {
        UserTask ut=getUserTask();
        if(ut!=null)
        {
            return ut.hasResource(value);
        }
        return super.hasResource(value);
    }

    @Override
    public Resource getResource() {
        UserTask ut=getUserTask();
        if(ut!=null)
        {
            return ut.getResource();
        }
        return super.getResource();
    }


    @Override
    public boolean isValid() {
        UserTask ut=getUserTask();
        if(ut!=null)
        {
            return ut.isValid();
        }
        return super.isValid();
    }

    @Override
    public GenericIterator<RoleRef> listRoleRefs() {
        UserTask ut=getUserTask();
        if(ut!=null)
        {
            ResourceAssignment res=ut.getResourceAssignment();
            if(res!=null)return res.listRoleRefs();
        }
        return super.listRoleRefs();
    }

    @Override
    public GenericIterator<RoleRef> listInheritRoleRefs() {
        UserTask ut=getUserTask();
        if(ut!=null)
        {
            ResourceAssignment res=ut.getResourceAssignment();
            if(res!=null)return res.listInheritRoleRefs();
        }
        return super.listInheritRoleRefs();
    }


    @Override
    public boolean hasRoleRef(RoleRef value) {
        UserTask ut=getUserTask();
        if(ut!=null)
        {
            ResourceAssignment res=ut.getResourceAssignment();
            if(res!=null)return res.hasRoleRef(value);
        }
        return super.hasRoleRef(value);
    }

    @Override
    public RoleRef getRoleRef() {
        UserTask ut=getUserTask();
        if(ut!=null)
        {
            ResourceAssignment res=ut.getResourceAssignment();
            if(res!=null)return res.getRoleRef();
        }
        return super.getRoleRef();
    }

    @Override
    public GenericIterator<CalendarRef> listCalendarRefs() {
        UserTask ut=getUserTask();
        if(ut!=null)
        {
            return ut.listCalendarRefs();
        }
        return super.listCalendarRefs();
    }

    @Override
    public boolean hasCalendarRef(CalendarRef value) {
        UserTask ut=getUserTask();
        if(ut!=null)
        {
            return ut.hasCalendarRef(value);
        }
        return super.hasCalendarRef(value);
    }

    @Override
    public CalendarRef getCalendarRef() {
        UserTask ut=getUserTask();
        if(ut!=null)
        {
            return ut.getCalendarRef();
        }
        return super.getCalendarRef();
    }

    
    @Override
    public GenericIterator<RuleRef> listRuleRefs() {
        UserTask ut=getUserTask();
        if(ut!=null)
        {
            ResourceAssignment res=ut.getResourceAssignment();
            if(res!=null)return res.listRuleRefs();
        }
        return super.listRuleRefs();
    }

    @Override
    public GenericIterator<RuleRef> listInheritRuleRefs() {
        UserTask ut=getUserTask();
        if(ut!=null)
        {
            ResourceAssignment res=ut.getResourceAssignment();
            if(res!=null)return res.listInheritRuleRefs();
        }
        return super.listInheritRuleRefs();
    }

    @Override
    public boolean hasRuleRef(RuleRef value) {
        UserTask ut=getUserTask();
        if(ut!=null)
        {
            ResourceAssignment res=ut.getResourceAssignment();
            if(res!=null)return res.hasRuleRef(value);
        }
        return super.hasRuleRef(value);
    }

    @Override
    public RuleRef getRuleRef() {
        UserTask ut=getUserTask();
        if(ut!=null)
        {
            ResourceAssignment res=ut.getResourceAssignment();
            if(res!=null)return res.getRuleRef();
        }
        return super.getRuleRef();
    }


    @Override
    public GenericIterator<UserGroupRef> listUserGroupRefs() {
        UserTask ut=getUserTask();
        if(ut!=null)
        {
            ResourceAssignment res=ut.getResourceAssignment();
            if(res!=null)return res.listUserGroupRefs();
        }
        return super.listUserGroupRefs();
    }

    @Override
    public GenericIterator<UserGroupRef> listInheritUserGroupRefs() {
        UserTask ut=getUserTask();
        if(ut!=null)
        {
            ResourceAssignment res=ut.getResourceAssignment();
            if(res!=null)return res.listInheritUserGroupRefs();
        }
        return super.listInheritUserGroupRefs();
    }



    @Override
    public boolean hasUserGroupRef(UserGroupRef value) {
        UserTask ut=getUserTask();
        if(ut!=null)
        {
            ResourceAssignment res=ut.getResourceAssignment();
            if(res!=null)return res.hasUserGroupRef(value);
        }
        return super.hasUserGroupRef(value);
    }

    @Override
    public UserGroupRef getUserGroupRef() {
        UserTask ut=getUserTask();
        if(ut!=null)
        {
            ResourceAssignment res=ut.getResourceAssignment();
            if(res!=null)return res.getUserGroupRef();
        }
        return super.getUserGroupRef();
    }


    @Override
    public GenericIterator<TemplateRef> listTemplateRefs() {
        UserTask ut=getUserTask();
        if(ut!=null)
        {
            return ut.listTemplateRefs();
        }
        return super.listTemplateRefs();
    }

    @Override
    public GenericIterator<TemplateRef> listInheritTemplateRefs() {
        UserTask ut=getUserTask();
        if(ut!=null)
        {
            return ut.listInheritTemplateRefs();
        }
        return super.listInheritTemplateRefs();
    }

    @Override
    public boolean hasTemplateRef(TemplateRef value) {
        UserTask ut=getUserTask();
        if(ut!=null)
        {
            return ut.hasTemplateRef(value);
        }
        return super.hasTemplateRef(value);
    }

    @Override
    public TemplateRef getTemplateRef() {
        UserTask ut=getUserTask();
        if(ut!=null)
        {
            return ut.getTemplateRef();
        }
        return super.getTemplateRef();
    }

    @Override
    public String getDisplayName() {
        UserTask ut=getUserTask();
        if(ut!=null)
        {
            return ut.getDisplayTitle(null);
        }
        return super.getDisplayName();
    }

    @Override
    public String getDisplayName(String lang)
    {
        UserTask ut=getUserTask();
        if(ut!=null)
        {
            return ut.getDisplayTitle(lang);
        }
        return super.getDisplayName(lang);
    }

}
