/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.admin.components.widgets;

import org.zkoss.zk.ui.Component;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.social.admin.tree.SWBSTreeComposer;
import org.zkoss.zul.Iframe;
import org.zkoss.zk.ui.util.GenericForwardComposer;

/**
 *
 * @author jorge.jimenez
 */
public class Widget2Composer extends GenericForwardComposer {

    private static Logger log = SWBUtils.getLogger(SWBSTreeComposer.class);
    private static final long serialVersionUID = -9145887024839938516L;
    private WebSite wsiteAdm=null;
    private SWBParamRequest paramRequest=null;
    private User user=null;
    private Iframe widgetIframe2;



    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        try
        {
            paramRequest=(SWBParamRequest)requestScope.get("paramRequest");
            if(paramRequest!=null)
            {
                wsiteAdm=paramRequest.getWebPage().getWebSite();
                if(wsiteAdm!=null)
                {
                    WebPage wpWidget=wsiteAdm.getWebPage("Widget_2");
                    if(wpWidget!=null)
                    {
                        user=paramRequest.getUser();
                        widgetIframe2.setSrc(wpWidget.getUrl(user.getLanguage()));
                    }
                }
            }
        }catch(Exception e)
        {
            log.error(e);
        }
    }
}
