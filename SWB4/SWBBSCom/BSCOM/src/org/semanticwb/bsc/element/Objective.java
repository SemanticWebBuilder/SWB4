package org.semanticwb.bsc.element;

import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.Role;
import org.semanticwb.model.RuleRef;
import org.semanticwb.model.UserGroup;


public class Objective extends org.semanticwb.bsc.element.base.ObjectiveBase 
{
    public Objective(org.semanticwb.platform.SemanticObject base)
            
    {
        super(base);
    }
    
    /**
     * Obtiene el n&uacute;mero consecutivo utilizado en la asignaci&oacute;n del prefijo de un 
     * indicador.
     * @return {@code Int} Retorna un n&uacute;mero consecutivo, obtenido del objetivo que 
     * contiene los indicadores
     */
    @Override
    public synchronized int getSerial() {
        setSerial(super.getSerial()+1);
        return super.getSerial();
    }
    
    /**
     * Actualiza el n&uacute;mero consecutivo a utilizar en el prefijo de un indicador.
     * @param value N&uacute;mero entero actualizado.
     */
    @Override
    public synchronized void setSerial(int value) {
        super.setSerial(value);
    }

    /**
     * Obtiene el prefijo que identifica un objetivo. El prefijo esta definido por el prefijo de la 
     * perspectiva a la que esta asociado el objetivo, seguido de un n&uacute;mero consecutivo. 
     * @return {@code String} Regresa un string con el prefijo asociado a un objetivo.
     * <p>
     * Ejemplo:
     * </p> 
     * <p>
     * El prefijo de la perspectiva es F y el n&uacute;mero consecutivo asignado para el objetivo es 
     * 1, se obtendr&iacute;a el siguiente prefijo: 
     *  {@literal  F1 }
     * </p> 
     */
    @Override
    public String getPrefix() {
        String prefix = super.getPrefix();
        if(prefix==null) {
            try {
                prefix = getTheme().getPerspective().getPrefix().trim() + getTheme().getPerspective().getSerial();
            }catch(Exception e) {
                prefix = getTheme().getPerspective().getTitle().substring(0, 1).toUpperCase() + getTheme().getPerspective().getSerial();
            }
            setPrefix(prefix);
        }
        return prefix;
    }
    
    /**
     * Asigna el prefijo de un objetivo.
     */
    @Override
    public void setPrefix(String value) {
        super.setPrefix(value);
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
}
