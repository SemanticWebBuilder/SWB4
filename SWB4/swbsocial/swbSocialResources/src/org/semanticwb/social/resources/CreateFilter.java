/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.resources;

import java.util.Locale;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.DisplayObject;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.social.Stream;
import org.semanticwb.social.util.SWBSocialUtil;
import org.semanticwb.social.utils.SWBSocialResourceUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.event.ScrollEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;

/**
 *
 * @author carlos.alvarez
 */
public class CreateFilter extends GenericForwardComposer<Component> {

    private static Logger log = SWBUtils.getLogger(CreateFilter.class);
    private SemanticObject semObject = null;
    private SWBParamRequest paramRequest = null;
    private User user = null;
    private WebSite wsite = null;
    private String objUri = null;
    private Stream stream = null;
    private Label lblSentimiento;
    private Checkbox chNeutro;
    private Checkbox chPositivo;
    private Checkbox chNegativo;
    private Label lblIntensidad;
    private Checkbox chMedia;
    private Checkbox chAlta;
    private Checkbox chBaja;
    private Button btnCrear;
    private Intbox inbKlout;
    private Slider slKlout;
    Locale localeP = null;
    String sLocale = null;
    private WebSite modelAdmin = null;
    private String ImgAdminPathBase = null;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        paramRequest = (SWBParamRequest) requestScope.get("paramRequest");
        user = paramRequest.getUser();
        objUri = (String) requestScope.get("objUri");
        wsite = (WebSite) requestScope.get("wsite");
        semObject = SemanticObject.createSemanticObject(objUri);
        stream = (Stream) semObject.createGenericInstance();

        DisplayObject displayObj = (DisplayObject) stream.getSemanticObject().getSemanticClass().getDisplayObject().createGenericInstance();
        String icon = displayObj.getIconClass();

        modelAdmin = paramRequest.getWebPage().getWebSite();
        ImgAdminPathBase = "/work/models/" + modelAdmin.getId() + "/admin/img/";
        chNeutro.setImage(ImgAdminPathBase + icon);

        sLocale = "org.semanticwb.social.resources.SocialMessages";
        localeP = new Locale(user.getLanguage());

        lblSentimiento.setValue((SWBUtils.TEXT.getLocaleString(sLocale, "sentimiento", localeP)) + ":");
        chNeutro.setLabel(SWBUtils.TEXT.getLocaleString(sLocale, "neutro", localeP));
        chNeutro.setTooltiptext(SWBUtils.TEXT.getLocaleString(sLocale, "neutro", localeP));
        chPositivo.setLabel(SWBUtils.TEXT.getLocaleString(sLocale, "positivo", localeP));
        chPositivo.setTooltiptext(SWBUtils.TEXT.getLocaleString(sLocale, "positivo", localeP));
        chNegativo.setLabel(SWBUtils.TEXT.getLocaleString(sLocale, "negativo", localeP));
        chNegativo.setTooltiptext(SWBUtils.TEXT.getLocaleString(sLocale, "negativo", localeP));

        lblIntensidad.setValue((SWBUtils.TEXT.getLocaleString(sLocale, "intensidad", localeP)) + ":");
        chMedia.setLabel(SWBUtils.TEXT.getLocaleString(sLocale, "media", localeP));
        chMedia.setTooltiptext(SWBUtils.TEXT.getLocaleString(sLocale, "media", localeP));
        chAlta.setLabel(SWBUtils.TEXT.getLocaleString(sLocale, "alta", localeP));
        chAlta.setTooltiptext(SWBUtils.TEXT.getLocaleString(sLocale, "alta", localeP));
        chBaja.setLabel(SWBUtils.TEXT.getLocaleString(sLocale, "baja", localeP));
        chBaja.setTooltiptext(SWBUtils.TEXT.getLocaleString(sLocale, "baja", localeP));
        if (stream.isFilterSentimentalNeutral()) {
            chNeutro.setChecked(true);
        }
        if (stream.isFilterSentimentalPositive()) {
            chPositivo.setChecked(true);
        }
        if (stream.isFilterSentimentalNegative()) {
            chNegativo.setChecked(true);
        }
        if (stream.isFilterIntensityMedium()) {
            chMedia.setChecked(true);
        }
        if (stream.isFilterIntensityHigh()) {
            chAlta.setChecked(true);
        }
        if (stream.isFilterIntensityLow()) {
            chBaja.setChecked(true);
        }
        slKlout.setCurpos(stream.getStream_KloutValue());
        inbKlout.setValue(stream.getStream_KloutValue());
        btnCrear.setLabel(SWBUtils.TEXT.getLocaleString(sLocale, "guardar", localeP));
        btnCrear.setTooltiptext(SWBUtils.TEXT.getLocaleString(sLocale, "guardar", localeP));
        btnCrear.addEventListener("onClick", new crearElFiltro());
    }

    class crearElFiltro implements EventListener<Event> {

        public void onEvent(Event event) throws Exception {
            if (chNeutro.isChecked()) {
                stream.setFilterSentimentalNeutral(true);
            } else {
                stream.setFilterSentimentalNeutral(false);
            }
            if (chPositivo.isChecked()) {
                stream.setFilterSentimentalPositive(true);
            } else {
                stream.setFilterSentimentalPositive(false);
            }
            if (chNegativo.isChecked()) {
                stream.setFilterSentimentalNegative(true);
            } else {
                stream.setFilterSentimentalNegative(false);
            }
            if (chMedia.isChecked()) {
                stream.setFilterIntensityMedium(true);
            } else {
                stream.setFilterIntensityMedium(false);
            }
            if (chAlta.isChecked()) {
                stream.setFilterIntensityHigh(true);
            } else {
                stream.setFilterIntensityHigh(false);
            }
            if (chBaja.isChecked()) {
                stream.setFilterIntensityLow(true);
            } else {
                stream.setFilterIntensityLow(false);
            }
            stream.setStream_KloutValue(slKlout.getCurpos());
            SWBSocialResourceUtils.Zkoss.setStatusMessage("Filtro actualizado");
            btnCrear.setDisabled(true);
            btnCrear.setImage("/swbadmin/images/off_ok.png");
        }
    }

    public void onChanging$inbKlout(InputEvent event) throws Exception {
        try {
            slKlout.setCurpos(Integer.parseInt(event.getValue().toString()));
            btnCrear.setDisabled(false);
            btnCrear.setImage("/swbadmin/images/okk.png");
        } catch (Exception e) {
        }
    }

    public void onBlur$inbKlout(Event event) throws Exception {
        try {
            if (inbKlout.intValue() > 100) {
                slKlout.setCurpos(100);
            }
        } catch (Exception e) {
        }
    }

    public void onScrolling$slKlout(ScrollEvent event) throws Exception {
        try {
            inbKlout.setValue(event.getPos());
            btnCrear.setDisabled(false);
            btnCrear.setImage("/swbadmin/images/okk.png");
        } catch (Exception e) {
        }
    }

    public void onScroll$slKlout(ScrollEvent event) throws Exception {
        try {
            inbKlout.setValue(event.getPos());
            btnCrear.setDisabled(false);
            btnCrear.setImage("/swbadmin/images/okk.png");
        } catch (Exception e) {
        }
    }
}