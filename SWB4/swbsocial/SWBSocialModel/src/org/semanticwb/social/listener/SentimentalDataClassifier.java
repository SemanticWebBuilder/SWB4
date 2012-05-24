/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.listener;

import java.util.ArrayList;
import java.util.Iterator;
import org.semanticwb.model.SWBModel;
import org.semanticwb.model.WebSite;
import org.semanticwb.social.MessageIn;
import org.semanticwb.social.Post;
import org.semanticwb.social.Prepositions;
import org.semanticwb.social.PunctuationSign;
import org.semanticwb.social.util.SWBSocialUtil;

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
        //removePuntualSigns1();
        removePuntualSigns();
        //Arreglo con las palabras totales que existe en la variable PostData
        ArrayList aPostDataWords=SWBSocialUtil.Strings.stripWordsByLine(postData);
        
        //Elimino preposiciones
        aPostDataWords=removeprepositions(aPostDataWords);
        Iterator itaPostDataWords=aPostDataWords.iterator();
        while(itaPostDataWords.hasNext())
        {
            System.out.println("Palabra2:"+itaPostDataWords.next());
        }
        //aPostDataWords esta preparado para ver si existen estas palabras en las que tengo en la BD,
        //las cuales cuentan con una valor sentimental y de intensidad
        //TODO:Revisar Palabras de aPostDataWords en mi objeto de BD, para ver si existen y así saber cuales
               //es el valor sentimental de c/palabra y así poder determinar el sentimiento del msg en general.
        
    }

    private void removePuntualSigns()
    {
        Iterator <PunctuationSign> itPunctuationSigns=PunctuationSign.ClassMgr.listPunctuationSigns(model);
        while(itPunctuationSigns.hasNext())
        {
            PunctuationSign puncSign=itPunctuationSigns.next();
            postData=postData.replaceAll(puncSign.getPuntuationSign(), "");
        }
    }

    private ArrayList removeprepositions(ArrayList aPostDataWords)
    {
        Iterator <Prepositions> itPrepositions=Prepositions.ClassMgr.listPrepositionses(model);
        while(itPrepositions.hasNext())
        {
            Prepositions preposition=itPrepositions.next();
            removeWordFromArray(aPostDataWords, preposition.getPreposition());
        }
        return aPostDataWords;
    }

     /*
     * Remueve una cierta palabra de un arreglo, si la palabra existe varias veces en dicho arreglo,
     * las elimina todas, esto de manera iterativa
     */
    private void removeWordFromArray(ArrayList aPostDataWords, String sprep)
    {
        if(aPostDataWords.contains(sprep))
        {
            aPostDataWords.remove(sprep);
            removeWordFromArray(aPostDataWords, sprep);
        }
    }

    //**************** METODOS PARA PROBAR EL CLASIFICADOR **********************

    public static void main(String args[]){
        MessageIn msgIn=null;
        //MessageIn msgIn=MessageIn.ClassMgr.createMessageIn("1234567890", WebSite.ClassMgr.getWebSite("Infotc"));
        //msgIn.setMsg_Text("#Peleaporelsegundo lugar en la persona más PENDEJA entre Peña Nieto y Ninel Conde, dicen que Peña Nieto tiene la victoria asegurada.");
        //new SentimentalDataClassifier(msgIn, msgIn.getMsg_Text());
        new SentimentalDataClassifier(msgIn, "#Peleaporelsegundo! lugar... en la persona! más PENDEJA? entre Peña Nieto y Ninel Conde!, dicen que Peña Nieto tiene la victoria asegurada!!.");
    }

    private void removePuntualSigns1()
    {
        ArrayList alist=new ArrayList();
        alist.add("!");
        alist.add("\\?");
        alist.add("\\.");
        alist.add(",");
        Iterator<String> itSigns=alist.iterator();
        while(itSigns.hasNext())
        {
            String sign=itSigns.next();
            postData=postData.replaceAll(sign, "");
        }
    }

    private ArrayList removeprepositions1(ArrayList aPostDataWords)
    {
        ArrayList alist=new ArrayList();
        alist.add("a");
        alist.add("ante");
        alist.add("bajo");
        alist.add("cabe");
        alist.add("con");
        alist.add("contra");
        alist.add("de");
        alist.add("desde");
        alist.add("durante");
        alist.add("en");
        alist.add("entre");
        alist.add("excepto");
        alist.add("hacia");
        alist.add("hasta");
        alist.add("mediante");
        alist.add("para");
        alist.add("por");
        alist.add("pro");
        alist.add("salvo");
        alist.add("segun");
        alist.add("sin");
        alist.add("so");
        alist.add("sobre");
        alist.add("tras");
        alist.add("via");
        alist.add("para");
        alist.add("contra");
        alist.add("que");
        alist.add("la");
        alist.add("los");
        alist.add("los");
        alist.add("el");
        alist.add("ellos");
        alist.add("ellas");
        alist.add("esa");
        alist.add("ese");
        alist.add("esos");
        alist.add("esas");
        alist.add("las");
        alist.add("despues");
        alist.add("acerca");
        Iterator<String> itPreps=alist.iterator();
        while(itPreps.hasNext())
        {
            String sprep=itPreps.next();
            removeWordFromArray(aPostDataWords, sprep);
        }
        return aPostDataWords;
    }

}
