package org.semanticwb.bsc.element;

import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.Role;
import org.semanticwb.model.RuleRef;
import org.semanticwb.model.UserGroup;


public class Perspective extends org.semanticwb.bsc.element.base.PerspectiveBase 
{
    public Perspective(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    @Override
    public synchronized int getSerial() {
        setSerial(super.getSerial()+1);
        return super.getSerial();
    }

    @Override
    public synchronized void setSerial(int value) {
        super.setSerial(value);
    }
    
    @Override
    public String getPrefix() {
        String prefix = super.getPrefix();
        if(prefix==null) {
            try {
                prefix = getTitle().trim().substring(0, 1).toUpperCase();
            }catch(Exception e) {
                prefix = "Untitled";
            }
            setPrefix(prefix);
        }
        return prefix;
    }

    @Override
    public void setPrefix(String value) {
        super.setPrefix(value);           
    }

    @Override
    public GenericIterator<UserGroup> listUserGroups() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean hasUserGroup(UserGroup value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addUserGroup(UserGroup value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeAllUserGroup() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeUserGroup(UserGroup value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public UserGroup getUserGroup() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public GenericIterator<RuleRef> listRuleRefs() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean hasRuleRef(RuleRef value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public GenericIterator<RuleRef> listInheritRuleRefs() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addRuleRef(RuleRef value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeAllRuleRef() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeRuleRef(RuleRef value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public RuleRef getRuleRef() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public GenericIterator<Role> listRoles() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean hasRole(Role value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addRole(Role value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeAllRole() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeRole(Role value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Role getRole() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
