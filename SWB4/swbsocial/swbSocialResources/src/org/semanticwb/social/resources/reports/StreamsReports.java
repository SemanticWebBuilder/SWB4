/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.resources.reports;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.social.*;
import org.zkoss.exporter.excel.ExcelExporter;
import org.zkoss.exporter.pdf.PdfExporter;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;

/**
 *
 * @author carlos.alvarez
 */
public class StreamsReports extends GenericForwardComposer<Component> {

    private Label lblStream;
    private SemanticObject semObject = null;
    private String objUri;
    private Listbox lbStreams;
    private Toolbarbutton btnImprimir;
    private Toolbarbutton btnImprimirXls;
    private Listheader lh1;
    private Listheader lh2;
    private Listheader lh3;
    private Listheader lh4;
    private Listheader lh5;
    private Listheader lh6;
    private Listheader lh7;
    private Listheader lh8;
    private Listheader lh9;
    private Listheader lh10;
    private Listheader lh11;
    private Listheader lh12;
    private Listheader lhCuenta;
    private Listheader lhTipo;
    private WebSite wsite;
    private Button btnEnviar;
    SWBParamRequest paramRequest = null;
    User user = null;
    Locale locale = new Locale("es", "mx");
    NumberFormat nf = NumberFormat.getIntegerInstance(locale);
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yy HH:mm", locale);
    DecimalFormat df = new DecimalFormat("#.#");
    int i = 0;
    private static Logger log = SWBUtils.getLogger(StreamsReports.class);

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        paramRequest = (SWBParamRequest) requestScope.get("paramRequest");
        user = paramRequest.getUser();
        String sLocale = "org.semanticwb.social.resources.reports.SocialReport";
        Locale localeP = new Locale(user.getLanguage());
        lh1.setLabel(SWBUtils.TEXT.getLocaleString(sLocale, "num", localeP));
        lh2.setLabel(SWBUtils.TEXT.getLocaleString(sLocale, "ori", localeP));
        lh3.setLabel(SWBUtils.TEXT.getLocaleString(sLocale, "fecha", localeP));
        lh4.setLabel(SWBUtils.TEXT.getLocaleString(sLocale, "mensaje", localeP));
        lhCuenta.setLabel(SWBUtils.TEXT.getLocaleString(sLocale, "cuenta", localeP));
        lhTipo.setLabel(SWBUtils.TEXT.getLocaleString(sLocale, "tipo", localeP));
        lh5.setLabel(SWBUtils.TEXT.getLocaleString(sLocale, "sentimiento", localeP));
        lh6.setLabel(SWBUtils.TEXT.getLocaleString(sLocale, "icono", localeP));
        lh7.setLabel(SWBUtils.TEXT.getLocaleString(sLocale, "intensidad", localeP));
        lh8.setLabel(SWBUtils.TEXT.getLocaleString(sLocale, "replica", localeP));
        lh9.setLabel(SWBUtils.TEXT.getLocaleString(sLocale, "usuario", localeP));
        lh10.setLabel(SWBUtils.TEXT.getLocaleString(sLocale, "seguidores", localeP));
        lh11.setLabel(SWBUtils.TEXT.getLocaleString(sLocale, "amigos", localeP));
        lh12.setLabel(SWBUtils.TEXT.getLocaleString(sLocale, "lugar", localeP));
        btnImprimir.setLabel(SWBUtils.TEXT.getLocaleString(sLocale, "pdf", localeP));
        btnImprimir.setTooltiptext(SWBUtils.TEXT.getLocaleString(sLocale, "pdf", localeP));
        btnImprimirXls.setLabel(SWBUtils.TEXT.getLocaleString(sLocale, "xls", localeP));
        btnImprimirXls.setTooltiptext(SWBUtils.TEXT.getLocaleString(sLocale, "xls", localeP));
        wsite = (WebSite) requestScope.get("wsite");
        objUri = (String) requestScope.get("objUri");
        semObject = SemanticObject.createSemanticObject(objUri);
        Stream stream = (Stream) semObject.createGenericInstance();
        Iterator<MessageIn> lista = MessageIn.ClassMgr.listMessageInByPostInStream(stream, wsite);
        while (lista.hasNext()) {
            MessageIn msg = (MessageIn) lista.next();
            Listitem li = new Listitem();
            i++;

            li.appendChild(new Listcell(i + ""));
            try {
                li.appendChild(new Listcell(msg.getPostSource()));
            } catch (Exception e) {
                li.appendChild(new Listcell("--"));
            }
            try {
                li.appendChild(new Listcell(sdf.format(msg.getCreated())));
            } catch (Exception e) {
                li.appendChild(new Listcell("--"));
            }
            li.appendChild(new Listcell(msg.getMsg_Text()));
            li.appendChild(new Listcell(msg.getPostInSocialNetwork().getTitle()));
            li.appendChild(new Listcell(msg.getPostInSocialNetwork().getSemanticObject().getSemanticClass().getName()));
            Listcell lcImgFe = new Listcell();
            Image imgFe = new Image();
            A revaluar = new A();
            if (msg.getPostSentimentalType() == 2) {
                imgFe.setSrc("/swbadmin/images/feelneg.png");
                lcImgFe.appendChild(imgFe);
            } else if (msg.getPostSentimentalType() == 2) {
                imgFe.setSrc("/swbadmin/images/feelpos.png");
                lcImgFe.appendChild(imgFe);
            } else {
                revaluar.setLabel("Revalaur");
                revaluar.setAttribute("idMsg", msg.getId());
                lcImgFe.appendChild(revaluar);
                revaluar.addEventListener("onClick", new muestraRevaluar());
            }
            li.appendChild(lcImgFe);

            Listcell lcEmotion = new Listcell();
            Image imgEmotion = new Image();
            Label lbl = new Label();
            if (msg.getPostSentimentalEmoticonType() == 2) {
                imgEmotion.setSrc("/swbadmin/images/emoneg.png");
                lcEmotion.appendChild(imgEmotion);
            } else if (msg.getPostSentimentalEmoticonType() == 1) {
                imgEmotion.setSrc("/swbadmin/images/emopos.png");
                lcEmotion.appendChild(imgEmotion);
            } else {
                lbl.setValue("--");
                lcEmotion.appendChild(lbl);
            }
            li.appendChild(lcEmotion);

            li.appendChild(new Listcell(df.format(msg.getPostIntensityValue())));
            try {
                li.appendChild(new Listcell(msg.getPostRetweets() + ""));
            } catch (Exception e) {
                li.appendChild(new Listcell("0"));
            }
            try {
                li.appendChild(new Listcell(msg.getPostInSocialNetworkUser().getSnu_name()));
                li.appendChild(new Listcell(msg.getPostInSocialNetworkUser().getFollowers() + ""));
                li.appendChild(new Listcell(msg.getPostInSocialNetworkUser().getFriends() + ""));
            } catch (Exception e) {
                li.appendChild(new Listcell("--"));
                li.appendChild(new Listcell("0"));
                li.appendChild(new Listcell("0"));
            }
            if (msg.getPostPlace() == null) {
                li.appendChild(new Listcell("--"));
            } else {
                li.appendChild(new Listcell(msg.getPostPlace()));
            }
            lbStreams.appendChild(li);
        }
        String total = SWBUtils.TEXT.getLocaleString(sLocale, "total", localeP);
        lblStream.setValue(total + " " + stream.getTitle() + ": " + i);
        lblStream.setTooltiptext(total + " " + stream.getTitle() + ": " + i);
        if (i > 0) {
            btnImprimir.setDisabled(false);
            btnImprimirXls.setDisabled(false);
        }
    }
    Popup pp;

    class muestraRevaluar implements EventListener<Event> {

        public void onEvent(Event event) throws Exception {
            Map arg = new HashMap();
            arg.put("idMsg", event.getTarget().getAttribute("idMsg").toString());
            arg.put("wsite", wsite);
            Window window = (Window) Executions.createComponents("/swbadmin/zul/revalida.zul", self, arg);
            window.setClosable(true);
            window.doModal();
        }
    }

    public void onClick$btnImprimir(Event event) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfExporter exporter = new PdfExporter();
        exporter.export(lbStreams, out);
        AMedia amedia = new AMedia(wsite.getTitle() + ".pdf", "pdf", "application/pdf", out.toByteArray());
        Filedownload.save(amedia);
        out.close();
    }

    public void onClick$btnImprimirXls(Event event) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ExcelExporter exporter = new ExcelExporter();
        exporter.export(lbStreams, out);
        AMedia amedia = new AMedia(wsite.getTitle() + ".xlsx", "xls", "application/file", out.toByteArray());
        Filedownload.save(amedia);
        out.close();
    }
}