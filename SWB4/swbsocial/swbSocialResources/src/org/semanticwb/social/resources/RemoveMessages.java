/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.resources;

import java.util.Iterator;
import java.util.Locale;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.social.MessageIn;
import org.semanticwb.social.Stream;
import org.semanticwb.social.resources.reports.StreamReport;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;

/**
 *
 * @author carlos.alvarez
 */
public class RemoveMessages extends GenericForwardComposer<Component> {

    private Label lblMessages;
    private Label lblTotal;
    private Label lblStream;
    private Button btnDelete;
    private SemanticObject semObject = null;
    private SWBParamRequest paramRequest = null;
    private User user = null;
    private WebSite wsite = null;
    String objUri = null;
    Stream stream = null;
//    Include content;
    String eliminar = null;
    String confirmar = null;
    String total = null;
    private static Logger log = SWBUtils.getLogger(RemoveMessages.class);

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        paramRequest = (SWBParamRequest) requestScope.get("paramRequest");
        user = paramRequest.getUser();
        objUri = (String) requestScope.get("objUri");
        wsite = (WebSite) requestScope.get("wsite");
        semObject = SemanticObject.createSemanticObject(objUri);
        stream = (Stream) semObject.createGenericInstance();
        String sLocale = "org.semanticwb.social.resources.SocialMessages";
        Locale localeP = new Locale(user.getLanguage());
        eliminar = SWBUtils.TEXT.getLocaleString(sLocale, "eliminar", localeP);
        confirmar = SWBUtils.TEXT.getLocaleString(sLocale, "confirmar", localeP);
        lblMessages.setValue(eliminar);
        int i = 0;
        Iterator<MessageIn> lista = MessageIn.ClassMgr.listMessageInByPostInStream(stream, wsite);
        while (lista.hasNext()) {
            MessageIn msg = (MessageIn) lista.next();
            i++;
        }
        total = SWBUtils.TEXT.getLocaleString(sLocale, "total", localeP);
        
        lblTotal.setValue(total + ": " + i);
        lblStream.setValue(stream.getTitle() + "?");
        if(i==0){
            btnDelete.setDisabled(true);
        }
        
        
        btnDelete.setLabel(SWBUtils.TEXT.getLocaleString(sLocale, "aceptar", localeP));
        btnDelete.setTooltiptext(SWBUtils.TEXT.getLocaleString(sLocale, "aceptar", localeP));
        btnDelete.addEventListener("onClick", new sendRemove());
    }

    class sendRemove implements EventListener<Event> {

        public void onEvent(Event event) throws Exception {
            Messagebox.show(eliminar + ": " + stream.getTitle() + "?", confirmar, Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {

                public void onEvent(Event event) throws InterruptedException {
                    if (event.getName().equals("onOK")) {
                        try {
                            Iterator<MessageIn> lista = MessageIn.ClassMgr.listMessageInByPostInStream(stream, wsite);
                            while (lista.hasNext()) {
                                MessageIn msg = (MessageIn) lista.next();
                                msg.remove();
                            }
                            muestraDatos();
                        } catch (Exception e) {
                            log.error(e.getMessage());
                        }
                    }
                }
            });
        }
    }

    public void muestraDatos() {
        int i = 0;
        Iterator<MessageIn> lista = MessageIn.ClassMgr.listMessageInByPostInStream(stream, wsite);
        while (lista.hasNext()) {
            MessageIn msg = (MessageIn) lista.next();
            i++;
        }
        lblTotal.setValue(total + ": " + i);
        lblStream.setValue(stream.getTitle() + "?");
        if (i < 1) {
            btnDelete.setDisabled(true);
        } else {
            btnDelete.setDisabled(false);
        }
    }
}
