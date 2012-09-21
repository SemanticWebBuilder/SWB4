/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.admin.components;

import org.zkoss.zk.ui.Component;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.social.SocialNetwork;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Iframe;

/**
 *
 * @author jorge.jimenez
 */
public class GenericIframeRedirect extends GenericForwardComposer
{
    private WebSite wsite=null;
    String action=null;
    String objUri=null;
    String optionWepPage;
    SemanticObject semObject;
    SocialNetwork socialNet;
    Iframe genIframeRed;


    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        wsite=(WebSite)requestScope.get("wsite");
        action=(String)requestScope.get("action");
        objUri=(String)requestScope.get("objUri");
        optionWepPage=(String)requestScope.get("optionWepPage");
        //Manejo dinamico de la propiedad "src" del iframe.
        String wsiteId=null;
        if(wsite!=null)
        {
            wsiteId=wsite.getId();
        }
        genIframeRed.setSrc(optionWepPage+"?wsite="+wsiteId+"&action="+action+"&objUri="+objUri);
    }


}
