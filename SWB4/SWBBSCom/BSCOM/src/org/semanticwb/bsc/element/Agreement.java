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

    @Override
    public String getAgreementNumber() {
        System.out.println("Entra al metodo \n");
        String agreementNumber = super.getAgreementNumber();
        
        if (agreementNumber == null) {
            Agreement agreement = (Agreement) getSemanticObject().createGenericInstance();
            Meeting meeting = agreement.getMeeting();
            if (meeting != null) {
                /*
                int meetingId = Integer.getInteger(meeting.getId()).intValue();
                int agreementId = Integer.getInteger(this.getId()).intValue();
                java.util.GregorianCalendar systemDate = new java.util.GregorianCalendar();
                int anio = systemDate.get(GregorianCalendar.YEAR) % 100;
                agreementNumber = agreement.getBSC().getTitle() + "-" +
                        meeting.getMeetingType() != null ? meeting.getMeetingType().toUpperCase() : "" +
                        (meetingId > 9 ? "" : "0") + meetingId + "-" +
                        (agreementId > 9 ? "" : "0") + agreementId + anio;
                        */
                agreementNumber = "InfotecPEMPP-001-NOE-01-14";
                this.setAgreementNumber(agreementNumber);
            } else {
                System.out.println("Meeting es nula");
            }
        }
        System.out.println("AgreementNumber: " + agreementNumber);
        try {
            throw new Exception("Por el numero de acuerdo");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return agreementNumber;
    }

    @Override
    public void setAgreementNumber(String value) {
        super.setAgreementNumber(value);
    }
    
}
