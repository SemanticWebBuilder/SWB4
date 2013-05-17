/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.util;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.SWBAppObject;
import org.semanticwb.social.SocialFlow.SocialPFlowMgr;

/**
 *
 * @author jorge.jimenez
 */
public class SocialLoader implements SWBAppObject {

    private static Logger log = SWBUtils.getLogger(SocialLoader.class);
    
    /**
     * Is the manager for the publication flows in this portal.
     * <p>Es el administrador de los flujos de publicaci&oacute;n en este portal.</p>
     */
    private static SocialPFlowMgr pflowMgr;
   
    //private static SWBSocialUtil socialUtil;
    
    
    
     /*
     * Metodo constructor que levanta listener de cada uno de los streams de cada sitio de tipo SWBSocial
     */
    public SocialLoader() {
        try {
            System.out.println("Entra a Cargar:SocialLoader");
            pflowMgr = new SocialPFlowMgr();
            pflowMgr.init();
            //socialUtil = new SWBSocialUtil();
            //socialUtil.init();
        } catch (Exception e) {
            log.error(e);
        }
    }
    
    /**
     * Gets a reference to the publication flow manager for this instance of SWB.
     * <p>Obtiene una referencia al administrador de flujos de publicaci&oacute;n
     * de esta instancia de SWB</p>
     * @return a PFlowManager object for this instance of SWB.
     */
    public static SocialPFlowMgr getPFlowManager()
    {
        return pflowMgr;
    }

    @Override
    public void init() {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void destroy() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void refresh() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
