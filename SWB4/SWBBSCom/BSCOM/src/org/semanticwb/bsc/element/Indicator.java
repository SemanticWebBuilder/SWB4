package org.semanticwb.bsc.element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.semanticwb.SWBUtils;
import org.semanticwb.bsc.accessory.Period;
import org.semanticwb.bsc.accessory.State;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.Role;
import org.semanticwb.model.RuleRef;
import org.semanticwb.model.UserGroup;

/*
 * Clase que persiste informaci&oacute;n de un indicador. Un indicador permite informar sobre el avance para alcanzar un objetivo.
 */
public class Indicator extends org.semanticwb.bsc.element.base.IndicatorBase 
{
   
    public Indicator(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    /**
     * Obtiene el prefijo para identificar un indicador. 
     * En caso de que el prefijo sea nulo, asigna un prefijo en autom&aacute;tico. 
     * @return {@code String} Regresa un string con el prefijo asociado al indicador, 
     * si el prefijo es asignado en autom&aacute;tico utiliza el prefijo del objetivo
     * seguido de un n&uacute;mero consecutivo asignado por un motor.
     * <p>
     * Ejemplo:
     * </p> 
     * <p>
     * El motor asigna un n&uacute;mero 1 para el Objetivo F1, se obtendr&iacute;a el siguiente prefijo: 
     *  {@literal  F1.1 }
     * </p>
     */
    @Override
    public String getPrefix() {
        String prefix = super.getPrefix();
        if(prefix==null) {
            try {
                prefix = getObjective().getPrefix().trim() + "." + getObjective().getSerial();
            }catch(Exception e) {
                prefix = getObjective().getTitle().substring(0, 1).toUpperCase() + "." + getObjective().getSerial();
            }
            setPrefix(prefix);
        }
        return prefix;
    }

    /**
     * Asigna un prefijo al indicador
     * @param value 
     */
    @Override
    public void setPrefix(String value) {
        super.setPrefix(value);
    }
    
    private List<Period> sortPeriods() {
        List<Period> periods = SWBUtils.Collections.copyIterator(listPeriods());
        Collections.sort(periods);
        return periods;
    }
    
    private List<Period> sortPeriods(boolean ascendent) {
        if(ascendent) {
            return sortPeriods();
        }else {
            List<Period> periods = SWBUtils.Collections.copyIterator(listPeriods());
            Collections.sort(periods, Collections.reverseOrder());
            return periods;
        }
    }
    
    private List<State> sortStates() {        
        List<State> states = SWBUtils.Collections.copyIterator(listStates());
        Collections.sort(states);
        return states;
    }
    
    private List<State> sortStates(boolean ascendent) {
        if(ascendent) {
            return sortStates();
        }else {
            List<State> states = SWBUtils.Collections.copyIterator(listStates());
            Collections.sort(states, Collections.reverseOrder());
            return states;
        }
    }
    
    /**
     * Devuelve todos los períodos de medición ordenados ascendentemente
     * @return A GenericIterator with all the org.semanticwb.bsc.accessory.Period sorted
     */
    public Iterator<Period> listMeasurablesPeriods() {
        int f = getPeriodicity().getNumberOfPeriods();
        List<Period> periods = sortPeriods();
        if(f==1) {
            return periods.iterator();
        }else {
            List<Period> lst = new ArrayList<Period>();
            for(int i=f-1; i<periods.size(); i=i+f) {
                lst.add(periods.get(i));
            }
            return lst.iterator();
        }
    }
    
    /**
     * Devuelve todos los períodos de medición ordenados ascendentemente
     * @param ascendent - ordenar ascendente o descendentemente 
     * @return A GenericIterator with all the org.semanticwb.bsc.accessory.Period sorted
     */
    public Iterator<Period> listMeasurablesPeriods(boolean ascendent) {
        if(ascendent) {
            return listMeasurablesPeriods();
        }else {
            int f = getPeriodicity().getNumberOfPeriods();
            List<Period> periods = sortPeriods(false);
            if(f==1) {
                return periods.iterator();
            }else {
                List<Period> lst = new ArrayList<Period>();
                for(int i=f-1; i<periods.size(); i=i+f) {
                    lst.add(periods.get(i));
                }
                return lst.iterator();
            }
        }
    }
    
    /**
     * Recupera el primer período de la lista de períodos asignados al indicador.
     * @return El período más anterior
     */
    public Period getFirstPeriod()
    {
        List<Period> periods = sortPeriods();
        try {
            return periods.get(0);
        }catch(IndexOutOfBoundsException e) {            
        }
        return null;
    }
    
    /**
     * Recupera el último período de la lista de períodos asignados al indicador.
     * @return El período más posterior
     */
    public Period getLastPeriod()
    {
        List<Period> periods = sortPeriods();
        try {
            return periods.get(periods.size()-1);
        }catch(IndexOutOfBoundsException e) {            
        }
        return null;
    }
    
    public State getMinimumState() {
        List<State> states = sortStates();
        try {
            return states.get(0);
        }catch(IndexOutOfBoundsException e) {
        
        }
        return null;
    }
    
    public State getMaximumState() {
        List<State> states = sortStates();
        try {
            return states.get(states.size()-1);
        }catch(IndexOutOfBoundsException e) {
        
        }
        return null;
    }
    
    /**
     * Recupera el período más próximo de medición en base a la fecha actual. 
     * La diferencia con nextMeasurementPeriod() es que si actualmente es tiempo de medición,
     * nextMeasurementPeriod() devolverá no este período sino el siguiente.
     * Siempre devuelve el siguiente.
     * @return El período más próximo de medición en base a la fecha actual.
     */
    public Period currentMeasurementPeriod()
    {
        return null;
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
    
    /**
     * Genera un String que representa el nombre del indicador antecedido por su prefijo
     * @param lang un String que indica el idoma en que se espera el valor devuelto
     * @return un String que representa el nombre del indicador antecedido por su prefijo
     */
    public String renderIndicatorName(String lang) {
        
        StringBuilder value = new StringBuilder(64);
        
        if (this.getPrefix() != null) {
            value.append(this.getPrefix());
            value.append(" ");
        }
        value.append(this.getDisplayTitle(lang));
        return value.toString();
    }
}
