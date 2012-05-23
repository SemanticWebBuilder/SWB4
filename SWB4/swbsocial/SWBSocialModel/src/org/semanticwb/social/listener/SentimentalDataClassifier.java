/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.listener;

import java.util.Iterator;
import org.semanticwb.model.SWBModel;
import org.semanticwb.model.WebSite;
import org.semanticwb.social.MessageIn;
import org.semanticwb.social.Post;
import org.semanticwb.social.Prepositions;
import org.semanticwb.social.PunctuationSign;

/**
 *
 * @author jorge.jimenez
 */
public class SentimentalDataClassifier {

    Post post=null;
    String postData=null;
    SWBModel model=null;
    
    public SentimentalDataClassifier(Post post, String postData)
    {
        this.post=post;
        this.postData=postData;
        this.model=WebSite.ClassMgr.getWebSite(post.getSemanticObject().getModel().getName());
        initAnalysis();
    }

    private void initAnalysis()
    {
        removePuntualSigns();
        removeprepositions();
        System.out.println("postData a checar super sentimientos:"+postData);
        /*
        String[] words=postData.split("\\ ");
        for(int i=0;i<words.length;i++)
        {
            String word=words[i];
            System.out.println("word Final a checar sentimientos:"+word);
        }
         * */

    }

    private void removePuntualSigns()
    {
        Iterator <PunctuationSign> itPunctuationSigns=PunctuationSign.ClassMgr.listPunctuationSigns(model);
        while(itPunctuationSigns.hasNext())
        {
            PunctuationSign puncSign=itPunctuationSigns.next();
            //System.out.println("puncSign:"+puncSign.getPuntuationSign());
            postData=postData.replaceAll(puncSign.getPuntuationSign(), "");
        }
    }

    private void removeprepositions()
    {
        Iterator <Prepositions> itPrepositions=Prepositions.ClassMgr.listPrepositionses(model);
        while(itPrepositions.hasNext())
        {
            Prepositions preposition=itPrepositions.next();
            postData=postData.replaceAll(preposition.getPreposition(), "");
        }
    }

    public static void main(String args[]){
        MessageIn msgIn=null;
        //MessageIn msgIn=MessageIn.ClassMgr.createMessageIn("1234567890", WebSite.ClassMgr.getWebSite("Infotc"));
        //msgIn.setMsg_Text("#Peleaporelsegundo lugar en la persona más PENDEJA entre Peña Nieto y Ninel Conde, dicen que Peña Nieto tiene la victoria asegurada.");
        //new SentimentalDataClassifier(msgIn, msgIn.getMsg_Text());
        new SentimentalDataClassifier(msgIn, "#Peleaporelsegundo lugar en la persona más PENDEJA entre Peña Nieto y Ninel Conde, dicen que Peña Nieto tiene la victoria asegurada.");
    }

}
