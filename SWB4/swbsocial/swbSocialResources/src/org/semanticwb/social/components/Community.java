/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.components;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.SWBParamRequest;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Include;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabs;

/**
 *
 * @author carlos.alvarez
 */
public class Community extends GenericForwardComposer<Component> {

    private static Logger log = SWBUtils.getLogger(Community.class);
    private Tabs tabs_gen;
    WebSite wsiteAdm = null;
    User user = null;
    SWBParamRequest paramRequest = null;
    String ImgAdminPathBase = null;
    Iframe iframe_genRedirect;
    Include content;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        System.out.println("entra community");
        try {
            paramRequest = (SWBParamRequest) requestScope.get("paramRequest");
            if (paramRequest != null) {
                wsiteAdm = paramRequest.getWebPage().getWebSite();
                if (wsiteAdm != null) {
                    ImgAdminPathBase = "/work/models/" + wsiteAdm.getId() + "/admin/img/";
                    user = paramRequest.getUser();
                    tabs_gen.getChildren().clear();
                    Tab tbInitial = new Tab();
                    tbInitial.setId("idCommunity" + wsiteAdm.getWebPage("Community").getId());
                    tbInitial.setLabel(wsiteAdm.getWebPage("Community").getDisplayTitle(user.getLanguage()));
                    tbInitial.setTooltiptext(wsiteAdm.getWebPage("Community").getDisplayTitle(user.getLanguage()));
                    tabs_gen.appendChild(tbInitial);

                    content.setSrc(null);
                    content.setSrc("/work/models/swbsocial/admin/zul/cnf_GenericRedirect.zul");
                    content.setDynamicProperty("wsiteAdm", wsiteAdm);
                    content.setDynamicProperty("action", "community");
                    tbInitial.addEventListener(Events.ON_SELECT, new EventListener<Event>() {

                        @Override
                        public void onEvent(Event event) throws Exception {
                            content.setSrc(null);
                            content.setSrc("/work/models/swbsocial/admin/zul/cnf_GenericRedirect.zul");
                            content.setDynamicProperty("wsiteAdm", wsiteAdm);
                            content.setDynamicProperty("action", "community");
                        }
                    });
                }
            }
        } catch (Exception e) {
        }
    }
}
