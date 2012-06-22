/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.listener;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBModel;
import org.semanticwb.social.PostIn;

/**
 *
 * @author jorge.jimenez
 */
public class Classifier {

    private Logger log = SWBUtils.getLogger(Classifier.class);

    PostIn post=null;
    public Classifier (PostIn post)
    {
        this.post=post;
        try{
            ClassifierThread classThread=new ClassifierThread(post);
            System.out.println("THREAD CREADO:"+classThread);
            classThread.start();
            System.out.println("classThread.isAlive():"+classThread);
        }catch(Exception e){
            log.error(e);
        }

    }

}
