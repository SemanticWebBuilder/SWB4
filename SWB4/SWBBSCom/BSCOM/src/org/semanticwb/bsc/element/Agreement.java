package org.semanticwb.bsc.element;

import java.util.GregorianCalendar;
import java.util.List;
import org.semanticwb.SWBPortal;
import org.semanticwb.bsc.accessory.Period;
import org.semanticwb.bsc.accessory.State;
import org.semanticwb.bsc.parser.AgreementParser;
import org.semanticwb.bsc.tracing.Meeting;
import org.semanticwb.model.Role;
import org.semanticwb.model.SWBClass;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.UserGroup;
import org.semanticwb.model.WebSite;


   /**
   * Define las características de un Acuerdo. 
   */
public class Agreement extends org.semanticwb.bsc.element.base.AgreementBase {
    
    static {
        SWBPortal.getIndexMgr().getDefaultIndexer().registerParser(Agreement.class, new AgreementParser());
    }
    
    public Agreement(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }
    
    @Override
    public String getPrefix() {
        return getAgreementNumber();
    }
    
    public List<State> listValidStates() {
        return getMeeting().listValidStates();
    }

    /**
     * Obtiene el valor de la propiedad agreementNumber. Si el valor es {@code null}, se genera el valor
     * sugerido con la siguiente estructura: T&iacute;tulo del scorecard + "-" + Tipo de sesi&ooacute;n (RAE o NOA)
     * + n&uacute;mero de sesi&oacute;n a 2 d&iacute;gitos+ "-" + consecutivo del acuerdo por sesi&oacute;n a 2 d&iacute;gitos +
     * "-" + a&ntilde;o actual a 2 d&iacute;gitos
     * El valor sugerido es editable.
     * @return 
     */
    private String getAgreementNumber() {
        String prefix = super.getPrefix();
        
        if (prefix == null) {
            Agreement agreement = (Agreement) getSemanticObject().createGenericInstance();
            Meeting meeting = agreement.getMeeting();
            if (meeting != null) {
                int meetingIndex;
                int agreementId = 0;
                String meetingType = "";
                try {
//                    if (meeting.getId() != null) {
//                        meetingIndex = Integer.parseInt(meeting.getId());
//                    }
                    meetingIndex = meeting.getSerial();
                    if (agreement.getId() != null) {
                        agreementId = Integer.parseInt(agreement.getId());
                    }
                    meetingType = meeting.getMeetingType() != null
                                ? meeting.getMeetingType().toUpperCase() : "";
                } catch (NumberFormatException nfe) {
                    System.err.println("Error al formar numero de acuerdo con id: " +
                            agreement.getId() + ", " + nfe.getCause());
                    meetingIndex = 0;
                }
                java.util.GregorianCalendar systemDate = new java.util.GregorianCalendar();
                int anio = systemDate.get(GregorianCalendar.YEAR) % 100;
                prefix = agreement.getBSC().getTitle() + "-" +
                         meetingType +
                        (meetingIndex > 9 ? "" : "0") + meetingIndex + "-" +
                        (agreementId > 9 ? "" : "0") + agreementId + "-" + anio;
                setPrefix(prefix);
            }
        }
        return prefix;
    }

    @Override
    public void setPrefix(String value) {
        super.setPrefix(value);           
    }
    
    @Override
    public boolean canView() {
        boolean access = Boolean.FALSE;
        SWBClass swbc = getAgreementVisibility();
        final WebSite scorecard = (WebSite) getSemanticObject().getModel().getModelObject().createGenericInstance();
        final User user = SWBContext.getSessionUser(scorecard.getUserRepository().getId());
        if(user!=null && swbc!=null)
        {
            if (swbc instanceof UserGroup) {
                UserGroup ugrp = (UserGroup) swbc;
                if (user.hasUserGroup(ugrp)) {
                    access = true;
                }
            }else if (swbc instanceof Role) {
                Role urole = (Role) swbc;
                if (user.hasRole(urole)) {
                    access = true;
                }
            }else if(swbc instanceof User) {
                User usr = (User)swbc;
                access = user.equals(usr);
            }
        }
        return access;
    }
    
    @Override
    public String getStatusIconClass() {
        StringBuilder iconClass = new StringBuilder();
        try {
            iconClass.append(getAgreementStatus().getIconClass());
        } catch (NullPointerException npe) {
            iconClass.append("swbstrgy-unknown");
        }
        return iconClass.toString();
    }
    
    @Override
    public String getStatusIconClass(Period period) {
        return getStatusIconClass();
    }
}
