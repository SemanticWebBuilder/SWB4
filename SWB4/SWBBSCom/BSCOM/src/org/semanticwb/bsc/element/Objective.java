package org.semanticwb.bsc.element;

import java.util.Iterator;
import java.util.List;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.util.GenericFilterRule;
import org.semanticwb.bsc.accessory.Period;
import org.semanticwb.bsc.accessory.State;
import org.semanticwb.bsc.tracing.EvaluationRule;
import org.semanticwb.bsc.tracing.PeriodStatus;
import org.semanticwb.bsc.tracing.Series;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.Role;
import org.semanticwb.model.RuleRef;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.SWBModel;
import org.semanticwb.model.User;
import org.semanticwb.model.UserGroup;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticObserver;
import org.semanticwb.platform.SemanticProperty;


public class Objective extends org.semanticwb.bsc.element.base.ObjectiveBase 
{
    static
    {
        bsc_hasIndicator.registerObserver(new SemanticObserver() {
            @Override
            public void notify(SemanticObject obj, Object prop, String lang, String action)
            {
                if("ADD".equalsIgnoreCase(action)) {
                    SWBModel model = (SWBModel)obj.getModel().getModelObject().createGenericInstance();
                    Objective objective = (Objective)obj.createGenericInstance();
                    List<State> states = objective.listValidStates();
                    // Funcionan exactamente igual objective.getIndicator() y objective.getLastIndicator()
                    Indicator indicator = objective.getIndicator();
                    if(!states.isEmpty() && indicator!=null)
                    {
                        indicator.addAllStates(states);
                        
                        String names[] = {"Actual","Meta","Actual Acumulado","Meta Acumulada"};
                        Series series;
                        for(int i=0; i<names.length; i++)
                        {
                            series = Series.ClassMgr.createSeries(model);
                            series.setTitle(names[i]);
                            series.setTitle(names[i], lang);
                            series.setDescription("Serie "+names[i]);
                            series.setDescription("Serie "+names[i], lang);
                            series.setIndex(i);
                            indicator.addSeries(series);
//                            for(State state:states)
//                            {
//                                EvaluationRule rule = EvaluationRule.ClassMgr.createEvaluationRule(indicator.getBSC());
//                                rule.setTitle("Regla para "+state.getTitle());
//                                rule.setTitle("Regla para "+state.getTitle(lang), lang);
//                                rule.setDescription("Regla para evaluar serie "+names[i]);
//                                rule.setDescription("Regla para evaluar serie "+names[i], lang);
//                                rule.setAppraisal(state);
//                                series.addEvaluationRule(rule);
//                            }
                        }
                    }
                }
            }
        });
    }
    
    
    public Objective(org.semanticwb.platform.SemanticObject base) {
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
    
    public Indicator getLastIndicator() {
        return getIndicator();
    }

    @Override
    public Indicator getIndicator() {
        Iterator<Indicator> it = SWBComparator.sortByCreated(listIndicators(), false);
        return it.hasNext()?it.next():null;
    }
    
    public List<State> listValidStates() {
        List<State> validStates = SWBUtils.Collections.filterIterator(listStates(), new GenericFilterRule<State>() {
                                                                        @Override
                                                                        public boolean filter(State s) {
                                                                            User user = SWBContext.getSessionUser();
                                                                            return !s.isValid() || !user.haveAccess(s);
                                                                        }            
                                                                    });
        return validStates;
    }

    @Override
    public void removeState(State value) {
        // Eliminar en cascada el estado de los indicadores del objetivo
        Iterator<Indicator> indicators = listIndicators();
        while(indicators.hasNext()) {
            indicators.next().removeState(value);
        }
        super.removeState(value);
    }
    
    
    
    @Override
    public void removeAllState()
    {
        // Eliminar en cascada los estados asignados a los indicadores del objetivo
        Iterator<Indicator> indicators = listIndicators();
        while(indicators.hasNext()) {
            indicators.next().removeAllState();
        }
        super.removeAllState();
    }
    
  
    /**
     * Genera un {@code String} que representa el valor de despliegue de la propiedad {@code Objective.hasIndicator}
     * @param lang un {@code String} que indica el idioma en que se espera el valor devuelto
     * @return un {@code String} que representa el valor de despliegue de la propiedad {@code Objective.hasIndicator}
     */
    private String renderHasIndicatorValue(String lang) {
        
        StringBuilder value = new StringBuilder(128);
        
        if (this.listIndicators().hasNext()) {
            GenericIterator<Indicator> indicatorIterator = this.listIndicators();
            int count = 0;
            while (indicatorIterator.hasNext()) {
                Indicator indicator = indicatorIterator.next();
                count++;
                if (count > 1) {
                    value.append("; ");
                }
                value.append(indicator.renderIndicatorName(lang));
            }
        }
        return value.toString();
    }
    
    /**
     * Genera un {@code String} que representa el valor de despliegue de la propiedad {@code Objective.hasInitiative}
     * @param lang un {@code String} que indica el idioma en que se espera el valor devuelto
     * @return un {@code String} que representa el valor de despliegue de la propiedad {@code Objective.hasInitiative}
     */
    private String renderHasInitiativeValue(String lang) {
        
        StringBuilder value = new StringBuilder(128);
        
        if (this.listInitiatives().hasNext()) {
            GenericIterator<Initiative> initiativeIterator = this.listInitiatives();
            int count = 0;
            while (initiativeIterator.hasNext()) {
                Initiative initiative = initiativeIterator.next();
                count++;
                if (count > 1) {
                    value.append("; ");
                }
                value.append(initiative.renderInitiativeName(lang));
            }
        }
        return value.toString();
    }
    
    /**
     * Genera un {@code String} que representa el valor de despliegue de la propiedad {@code Objective.hasPeriod}
     * @param lang un {@code String} que indica el idioma en que se espera el valor devuelto
     * @return un {@code String} que representa el valor de despliegue de la propiedad {@code Objective.hasPeriod}
     */
    private String renderHasPeriodValue(String lang) {
        
        StringBuilder value = new StringBuilder(128);
        
        if (this.listPeriods().hasNext()) {
            GenericIterator<Period> periodIterator = this.listPeriods();
            int count = 0;
            while (periodIterator.hasNext()) {
                Period period = periodIterator.next();
                count++;
                if (count > 1) {
                    value.append("; ");
                }
                value.append(period.getDisplayTitle(lang));
            }
        }
        return value.toString();
    }
    
    /**
     * Genera un {@code String} que representa el valor de despliegue de la propiedad {@code Objective.hasPeriodStatus}.
     * Si el periodo indicado es {@code null}, se devuelve un {@code String} vac&iacute;o.
     * @param lang un {@code String} que indica el idioma en que se espera el valor devuelto
     * @param thisPeriod indica el periodo para el cual se busca el estatus. Si es {@code null}, se devuelve un {@code String} vac&iacute;o.
     * @return un {@code String} que representa el valor de despliegue de la propiedad {@code Objective.hasPeriodStatus}.
     * Si el periodo indicado es {@code null}, se devuelve un {@code String} vac&iacute;o.
     */
    private String renderHasPeriodStatusValue(String lang, Period thisPeriod) {
        
        StringBuilder value = new StringBuilder(128);
        
        if (this.listPeriodStatuses().hasNext() && thisPeriod != null) {
            GenericIterator<PeriodStatus> periodStatusIterator = this.listPeriodStatuses();
            while (periodStatusIterator.hasNext()) {
                PeriodStatus periodStatus = periodStatusIterator.next();
                if (periodStatus.getPeriod().equals(thisPeriod)) {
                    value.append(periodStatus.getStatus().getDisplayTitle(lang));
                    break;
                }
            }
        }
        return value.toString();
    }
    
    /**
     * Genera un {@code String} que representa el valor de despliegue de la propiedad {@code Objective.hasState}
     * @param lang un {@code String} que indica el idioma en que se espera el valor devuelto
     * @return un {@code String} que representa el valor de despliegue de la propiedad {@code Objective.hasState}
     */
    private String renderHasStateValue(String lang) {
        
        StringBuilder value = new StringBuilder(128);
        
        if (this.listStates().hasNext()) {
            GenericIterator<State> stateIterator = this.listStates();
            int count = 0;
            while (stateIterator.hasNext()) {
                State state = stateIterator.next();
                count++;
                if (count > 1) {
                    value.append("; ");
                }
                value.append(state.getDisplayTitle(lang));
            }
        }
        return value.toString();
    }
    
   
    
    /**
     * Genera un {@code String} que representa el valor de despliegue de la propiedad {@code Objective.RuleRef}
     * @param lang un {@code String} que indica el idioma en que se espera el valor devuelto
     * @return un {@code String} que representa el valor de despliegue de la propiedad {@code Objective.RuleRef}
     */
    private String renderHasRuleRefValue(String lang) {
        
        StringBuilder value = new StringBuilder(128);
        
        if (this.listRuleRefs().hasNext()) {
            GenericIterator<RuleRef> ruleRefIterator = this.listRuleRefs();
            int count = 0;
            while (ruleRefIterator.hasNext()) {
                RuleRef ruleRef = ruleRefIterator.next();
                count++;
                if (count > 1) {
                    value.append("; ");
                }
                value.append(ruleRef.getRule().getDisplayTitle(lang));
            }
        }
        return value.toString();
    }
    
    /**
     * Genera un {@code String} que representa el valor de despliegue de la propiedad {@code Objective.hasUserGroup}
     * @param lang un {@code String} que indica el idioma en que se espera el valor devuelto
     * @return un {@code String} que representa el valor de despliegue de la propiedad {@code Objective.hasUserGroup}
     */
    private String renderHasUserGroupValue(String lang) {
        
        StringBuilder value = new StringBuilder(128);
        
        if (this.listUserGroups().hasNext()) {
            GenericIterator<UserGroup> userGroupIterator = this.listUserGroups();
            int count = 0;
            while (userGroupIterator.hasNext()) {
                UserGroup userGroup = userGroupIterator.next();
                count++;
                if (count > 1) {
                    value.append("; ");
                }
                value.append(userGroup.getDisplayTitle(lang));
            }
        }
        return value.toString();
    }
    
    /**
     * Genera un {@code String} que representa el valor de despliegue de la propiedad {@code Objective.help}
     * @param lang un {@code String} que indica el idioma en que se espera el valor devuelto
     * @return un {@code String} que representa el valor de despliegue de la propiedad {@code Objective.help}
     */
    private String renderHelpValue() {
        
        StringBuilder value = new StringBuilder(128);
        
        if (this.getHelp() != null) {
            value.append(this.getHelp());
        }
        return value.toString();
    }
    
    /**
     * Genera un {@code String} que representa el valor de despliegue de la propiedad {@code Objective.parentObjective}
     * @param lang un {@code String} que indica el idioma en que se espera el valor devuelto
     * @return un {@code String} que representa el valor de despliegue de la propiedad {@code Objective.parentObjective}
     */
    private String renderParentObjectiveValue(String lang) {
        
        StringBuilder value = new StringBuilder(128);
        
        if (this.getParentObjective() != null && this.getBSC() != null) {
            value.append(this.getParentObjective().renderObjectiveName(lang));
        }
        return value.toString();
    }
    
    /**
     * Genera un {@code String} que representa el valor de despliegue de la propiedad {@code Objective.periodicity}
     * @param lang un {@code String} que indica el idioma en que se espera el valor devuelto
     * @return un {@code String} que representa el valor de despliegue de la propiedad {@code Objective.periodicity}
     */
    private String renderPeriodicityValue(String lang) {
        
        StringBuilder value = new StringBuilder(64);
        
        if (this.getPeriodicity() != null) {
            value.append(this.getPeriodicity().getDisplayTitle(lang));
        }
        return value.toString();
    }
    
    /**
     * Genera un {@code String} que representa el valor de despliegue de la propiedad {@code Objective.sponsor}
     * @return un {@code String} que representa el valor de despliegue de la propiedad {@code Objective.sponsor}
     */
    private String renderSponsorValue() {
        
        StringBuilder value = new StringBuilder(64);
        
        if (this.getSponsor() != null) {
            value.append(this.getSponsor().getFullName());
        }
        return value.toString();
    }
    
    /**
     * Genera un {@code String} que representa el valor de despliegue de la propiedad {@code Objective.creator}
     * @return un {@code String} que representa el valor de despliegue de la propiedad {@code Objective.creator}
     */
    private String renderCreatorValue() {
        
        StringBuilder value = new StringBuilder(64);
        
        if (this.getCreator() != null) {
            value.append(this.getCreator().getFullName());
        }
        return value.toString();
    }
    
    /**
     * Genera un {@code String} que representa el valor de despliegue de la propiedad {@code Objective.modifiedBy}
     * @return un {@code String} que representa el valor de despliegue de la propiedad {@code Objective.modifiedBy}
     */
    private String renderModifiedByValue() {
        
        StringBuilder value = new StringBuilder(64);
        
        if (this.getModifiedBy() != null) {
            value.append(this.getModifiedBy().getFullName());
        }
        return value.toString();
    }
    
    /**
     * Genera un {@code String} que representa el valor de despliegue de la propiedad {@code Objective.themeInv}
     * @param lang un {@code String} que indica el idioma en que se espera el valor devuelto
     * @return un {@code String} que representa el valor de despliegue de la propiedad {@code Objective.themeInv}
     */
    private String renderThemeInvValue(String lang) {
        
        StringBuilder value = new StringBuilder(64);
        
        if (this.getTheme() != null) {
            value.append(this.getTheme().getDisplayTitle(lang));
        }
        return value.toString();
    }
    
    /**
     * Genera un {@code String} que representa el nombre del objetivo antecedido por su prefijo
     * @param lang un {@code String} que indica el idioma en que se espera el valor devuelto
     * @return un {@code String} que representa el nombre del objetivo antecedido por su prefijo
     */
    public String renderObjectiveName(String lang) {
        
        StringBuilder value = new StringBuilder(64);
        
        if (this.getPrefix() != null) {
            value.append(this.getPrefix());
            value.append(" ");
        }
        value.append(this.getDisplayTitle(lang));
        return value.toString();
    }
    
}
