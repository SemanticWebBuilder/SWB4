package org.semanticwb.process.model;

import org.semanticwb.model.CalendarRef;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.Resource;
import org.semanticwb.model.RoleRef;
import org.semanticwb.model.RuleRef;
import org.semanticwb.model.TemplateRef;
import org.semanticwb.model.UserGroupRef;


public class WrapperProcessWebPage extends org.semanticwb.process.model.base.WrapperProcessWebPageBase 
{
    public WrapperProcessWebPage(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    @Override
    public GenericIterator<Resource> listResources() {
        Process ut=getProcess();
        if(ut!=null)
        {
            return ut.listResources();
        }
        return super.listResources();
    }

    @Override
    public boolean hasResource(Resource value) {
        Process ut=getProcess();
        if(ut!=null)
        {
            return ut.hasResource(value);
        }
        return super.hasResource(value);
    }

    @Override
    public Resource getResource() {
        Process ut=getProcess();
        if(ut!=null)
        {
            return ut.getResource();
        }
        return super.getResource();
    }


    @Override
    public boolean isValid() {
        Process ut=getProcess();
        if(ut!=null)
        {
            return ut.isValid();
        }
        return super.isValid();
    }

    @Override
    public boolean isActive() {
        Process ut=getProcess();
        if(ut!=null)
        {
            return ut.isActive();
        }
        return super.isActive();
    }
    
    @Override
    public GenericIterator<RoleRef> listRoleRefs() {
        Process ut=getProcess();
        if(ut!=null)
        {
            return ut.listRoleRefs();
        }
        return super.listRoleRefs();
    }

    @Override
    public GenericIterator<RoleRef> listInheritRoleRefs() {
        Process ut=getProcess();
        if(ut!=null)
        {
            return ut.listInheritRoleRefs();
        }
        return super.listInheritRoleRefs();
    }


    @Override
    public boolean hasRoleRef(RoleRef value) {
        Process ut=getProcess();
        if(ut!=null)
        {
            return ut.hasRoleRef(value);
        }
        return super.hasRoleRef(value);
    }

    @Override
    public RoleRef getRoleRef() {
        Process ut=getProcess();
        if(ut!=null)
        {
            return ut.getRoleRef();
        }
        return super.getRoleRef();
    }

    @Override
    public GenericIterator<CalendarRef> listCalendarRefs() {
        Process ut=getProcess();
        if(ut!=null)
        {
            return ut.listCalendarRefs();
        }
        return super.listCalendarRefs();
    }

    @Override
    public boolean hasCalendarRef(CalendarRef value) {
        Process ut=getProcess();
        if(ut!=null)
        {
            return ut.hasCalendarRef(value);
        }
        return super.hasCalendarRef(value);
    }

    @Override
    public CalendarRef getCalendarRef() {
        Process ut=getProcess();
        if(ut!=null)
        {
            return ut.getCalendarRef();
        }
        return super.getCalendarRef();
    }

    
    @Override
    public GenericIterator<RuleRef> listRuleRefs() {
        Process ut=getProcess();
        if(ut!=null)
        {
            return ut.listRuleRefs();
        }
        return super.listRuleRefs();
    }

    @Override
    public GenericIterator<RuleRef> listInheritRuleRefs() {
        Process ut=getProcess();
        if(ut!=null)
        {
            return ut.listInheritRuleRefs();
        }
        return super.listInheritRuleRefs();
    }

    @Override
    public boolean hasRuleRef(RuleRef value) {
        Process ut=getProcess();
        if(ut!=null)
        {
            return ut.hasRuleRef(value);
        }
        return super.hasRuleRef(value);
    }

    @Override
    public RuleRef getRuleRef() {
        Process ut=getProcess();
        if(ut!=null)
        {
            return ut.getRuleRef();
        }
        return super.getRuleRef();
    }


    @Override
    public GenericIterator<UserGroupRef> listUserGroupRefs() {
        Process ut=getProcess();
        if(ut!=null)
        {
            return ut.listUserGroupRefs();
        }
        return super.listUserGroupRefs();
    }

    @Override
    public GenericIterator<UserGroupRef> listInheritUserGroupRefs() {
        Process ut=getProcess();
        if(ut!=null)
        {
            return ut.listInheritUserGroupRefs();
        }
        return super.listInheritUserGroupRefs();
    }



    @Override
    public boolean hasUserGroupRef(UserGroupRef value) {
        Process ut=getProcess();
        if(ut!=null)
        {
            return ut.hasUserGroupRef(value);
        }
        return super.hasUserGroupRef(value);
    }

    @Override
    public UserGroupRef getUserGroupRef() {
        Process ut=getProcess();
        if(ut!=null)
        {
            return ut.getUserGroupRef();
        }
        return super.getUserGroupRef();
    }


    @Override
    public GenericIterator<TemplateRef> listTemplateRefs() {
        Process ut=getProcess();
        if(ut!=null)
        {
            return ut.listTemplateRefs();
        }
        return super.listTemplateRefs();
    }

    @Override
    public GenericIterator<TemplateRef> listInheritTemplateRefs() {
        Process ut=getProcess();
        if(ut!=null)
        {
            return ut.listInheritTemplateRefs();
        }
        return super.listInheritTemplateRefs();
    }

    @Override
    public boolean hasTemplateRef(TemplateRef value) {
        Process ut=getProcess();
        if(ut!=null)
        {
            return ut.hasTemplateRef(value);
        }
        return super.hasTemplateRef(value);
    }

    @Override
    public TemplateRef getTemplateRef() {
        Process ut=getProcess();
        if(ut!=null)
        {
            return ut.getTemplateRef();
        }
        return super.getTemplateRef();
    }

    @Override
    public String getDisplayName() {
        Process ut=getProcess();
        if(ut!=null)
        {
            return ut.getDisplayTitle(null);
        }
        return super.getDisplayName();
    }

    @Override
    public String getDisplayName(String lang)
    {
        Process ut=getProcess();
        if(ut!=null)
        {
            return ut.getDisplayTitle(lang);
        }
        return super.getDisplayName(lang);
    }    
    
}
