package org.semanticwb.bsc.element;

import java.util.GregorianCalendar;
import org.semanticwb.bsc.accessory.Period;
import org.semanticwb.bsc.tracing.Meeting;


   /**
   * Define las características de un Acuerdo. 
   */
public class Agreement extends org.semanticwb.bsc.element.base.AgreementBase {
    
    
    public Agreement(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }
    
    @Override
    public String getPrefix() {
        return getAgreementNumber();
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
    public String getIconClass() {
        return null;//getStatus.getIconClass;
    }
    
    @Override
    public String getIconClass(Period period) {
        return null;//getStatus.getIconClass;
    }
}
