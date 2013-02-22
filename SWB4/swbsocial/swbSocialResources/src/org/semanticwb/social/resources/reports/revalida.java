/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.resources.reports;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.WebSite;
import org.semanticwb.social.MessageIn;
import org.semanticwb.social.SentimentalLearningPhrase;
import org.semanticwb.social.util.SWBSocialUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;

/**
 *
 * @author carlos.alvarez
 */
public class revalida extends GenericForwardComposer<Component> {

    private Button btnEnvia;
    private Button btnCancela;
    private Window window;
    private Listbox lbTipo;
    private Listbox lbIntensidad;
    private Textbox txtFrase;
    Include content;
    MessageIn message = null;
    WebSite wsite = null;
    private static Logger log = SWBUtils.getLogger(revalida.class);

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        wsite = (WebSite) arg.get("wsite");
        btnEnvia.addEventListener("onClick", new guardaRevaluar());
        btnCancela.addEventListener("onClick", new cancelar());
        btnCancela.addEventListener("onOK", new cancelar());
        message = MessageIn.ClassMgr.getMessageIn(arg.get("idMsg").toString(), wsite);
        try {
            window.setTitle(message.getPostInSocialNetworkUser().getSnu_name());
        } catch (Exception e) {
            window.setTitle("Sin título");
        }
    }

    class guardaRevaluar implements EventListener<Event> {

        public void onEvent(Event event) throws Exception {
            String error = "";
            String mensaje = "Por favor ingresa la información solicitada";
            if (txtFrase.getValue().trim().equals("")) {
                error += "\nFrase";
            }
            if (error.equals("")) {
                String[] phrases = txtFrase.getValue().trim().split(";");
                SentimentalLearningPhrase slp;
                for (String phrase : phrases) {
                    System.out.println("phrase: " + phrase);
                    phrase = phrase.toLowerCase().trim();
                    slp = SentimentalLearningPhrase.getSentimentalLearningPhrasebyPhrase(phrase, wsite);
                    if (slp == null) {
                        phrase = SWBSocialUtil.Classifier.normalizer(phrase).getNormalizedPhrase();
                        phrase = SWBSocialUtil.Classifier.getRootWord(phrase);
                        phrase = SWBSocialUtil.Classifier.phonematize(phrase);
                        slp = SentimentalLearningPhrase.ClassMgr.createSentimentalLearningPhrase(wsite);
                        slp.setPhrase(phrase);
                        slp.setSentimentType(Integer.parseInt(lbTipo.getSelectedItem().getValue().toString()));
                        slp.setIntensityType(Integer.parseInt(lbIntensidad.getSelectedItem().getValue().toString()));
                    } else {
                        slp.setSentimentType(Integer.parseInt(lbTipo.getSelectedItem().getValue().toString()));
                        slp.setIntensityType(Integer.parseInt(lbIntensidad.getSelectedItem().getValue().toString()));
                    }
                }
                Clients.showNotification("Frases nuevas: " + txtFrase.getValue().trim(), "info", content, "middle_center", 3500);
                window.detach();
            } else {
                Messagebox.show(error, mensaje, Messagebox.OK, Messagebox.ERROR);
            }
        }
    }

    class cancelar implements EventListener<Event> {

        public void onEvent(Event event) throws Exception {
            window.detach();
        }
    }
}
