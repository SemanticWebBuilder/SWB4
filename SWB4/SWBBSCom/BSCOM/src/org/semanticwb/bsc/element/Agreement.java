package org.semanticwb.bsc.element;

import java.util.GregorianCalendar;
import org.semanticwb.bsc.tracing.Meeting;


   /**
   * Define las características de un Acuerdo. 
   */
public class Agreement extends org.semanticwb.bsc.element.base.AgreementBase {
    
    
    public Agreement(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }

    /**
     * Obtiene el valor de la propiedad agreementNumber. Si el valor es {@code null}, se genera el valor
     * sugerido con la siguiente estructura: T&iacute;tulo del scorecard + "-" + Tipo de sesi&ooacute;n (RAE o NOA)
     * + n&uacute;mero de sesi&oacute;n a 2 d&iacute;gitos+ "-" + consecutivo del acuerdo por sesi&oacute;n a 2 d&iacute;gitos +
     * "-" + a&ntilde;o actual a 2 d&iacute;gitos
     * El valor sugerido es editable.
     * @return 
     */
    @Override
    public String getAgreementNumber() {
        String agreementNumber = super.getAgreementNumber();
        
        if (agreementNumber == null) {
            Agreement agreement = (Agreement) getSemanticObject().createGenericInstance();
            Meeting meeting = agreement.getMeeting();
            if (meeting != null) {
                int meetingId = 0;
                int agreementId = 0;
                String meetingType = "";
                try {
                    if (meeting.getId() != null) {
                        meetingId = Integer.parseInt(meeting.getId());
                    }
                    if (agreement.getId() != null) {
                        agreementId = Integer.parseInt(agreement.getId());
                    }
                    meetingType = meeting.getMeetingType() != null
                                ? meeting.getMeetingType().toUpperCase() : "";
                } catch (NumberFormatException nfe) {
                    System.err.println("Error al formar numero de acuerdo con id: " +
                            agreement.getId() + ", " + nfe.getCause());
                }
                java.util.GregorianCalendar systemDate = new java.util.GregorianCalendar();
                int anio = systemDate.get(GregorianCalendar.YEAR) % 100;
                agreementNumber = agreement.getBSC().getTitle() + "-" +
                         meetingType +
                        (meetingId > 9 ? "" : "0") + meetingId + "-" +
                        (agreementId > 9 ? "" : "0") + agreementId + "-" + anio;
                setAgreementNumber(agreementNumber);
            }
        }
        return agreementNumber;
    }

    /**
     * Almacena el valor recibido como valor de la propiedad agreementNumber.
     * @param value valor a almacenar
     */
    @Override
    public void setAgreementNumber(String value) {
        super.setAgreementNumber(value);
    }
    
}
