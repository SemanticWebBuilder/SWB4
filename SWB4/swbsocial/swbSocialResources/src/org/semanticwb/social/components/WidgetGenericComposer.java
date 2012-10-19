/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.components;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.zkoss.zk.ui.Component;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.social.components.tree.SWBSTreeComposer;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Iframe;


/**
 *
 * @author jorge.jimenez
 * @date 10/03/2012
 */

/*
 * Clase que maneja la fuente del iframe del widget 1 en la administración de swbsocial
 * 
 */
public final class WidgetGenericComposer extends GenericForwardComposer {

    private static Logger log = SWBUtils.getLogger(SWBSTreeComposer.class);
    private static final long serialVersionUID = -9145887024839938516L;
    private WebSite wsiteAdm=null;
    private SWBParamRequest paramRequest=null;
    private User user=null;
    private Iframe widgetIframe1;
    private Iframe widgetIframe2;
    private Iframe widgetIframe3;
    private Iframe widgetIframe4;

    /*
    * Metodo implementación de la clase padre, este metodo se ejecuta una vez cargado
    * el componente.
    */
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
                    user=paramRequest.getUser();
                    WebPage wpWidget1=wsiteAdm.getWebPage("Widget_1");
                    if(wpWidget1!=null)
                    {
                        widgetIframe1.setSrc(wpWidget1.getUrl(user.getLanguage()));
                    }
                    //De aquí para abajo se validan si son nulos porque se va entrando a esta clase
                    //cuando parsea el archivo zul y como va de uno en uno, los demas hacia adelante
                    //al principio son nulos.
                    WebPage wpWidget2=wsiteAdm.getWebPage("Widget_2");
                    if(widgetIframe2!=null && wpWidget2!=null)
                    {
                        widgetIframe2.setSrc(wpWidget2.getUrl(user.getLanguage()));
                    }
                    WebPage wpWidge3=wsiteAdm.getWebPage("Widget_3");
                    if(widgetIframe3!=null && wpWidge3!=null)
                    {
                        widgetIframe3.setSrc(wpWidge3.getUrl(user.getLanguage()));
                    }
                    WebPage wpWidget4=wsiteAdm.getWebPage("Widget_4");
                    if(widgetIframe4!=null && wpWidget4!=null)
                    {
                        widgetIframe4.setSrc(wpWidget4.getUrl(user.getLanguage()));
                    }
                }
            }
        }catch(Exception e)
        {
            log.error(e);
        }
    }
}
