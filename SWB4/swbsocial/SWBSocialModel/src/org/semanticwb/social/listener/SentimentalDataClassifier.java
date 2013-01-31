/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.listener;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.StringTokenizer;
import org.semanticwb.model.SWBModel;
import org.semanticwb.model.WebSite;
import org.semanticwb.social.ExternalPost;
import org.semanticwb.social.MessageIn;
import org.semanticwb.social.PhotoIn;
import org.semanticwb.social.PostIn;
import org.semanticwb.social.Prepositions;
import org.semanticwb.social.SentimentWords;
import org.semanticwb.social.SentimentalLearningPhrase;
import org.semanticwb.social.SocialNetwork;
import org.semanticwb.social.SocialNetworkUser;
import org.semanticwb.social.Stream;
import org.semanticwb.social.VideoIn;
import org.semanticwb.social.util.NormalizerCharDuplicate;
import org.semanticwb.social.util.SWBSocialUtil;
import org.zkoss.zk.ui.event.*;
import org.zkoss.zul.ListModelList;

/**
 *
 * @author jorge.jimenez
 */

/*
 * Clase cuya funcionalidad, es la de clasificar los mensajes que llegan por el listener.
 * Esta clase es llamada por cada thread levantado cuando llegan los mensajes del listener.
 *
 */

public class SentimentalDataClassifier {

    //PostIn post=null;
    String externalString2Clasify=null;
    SWBModel model=null;
    float sentimentalTweetValue=0;
    float IntensiveTweetValue=0;
    int wordsCont=0;
    
    ExternalPost externalPost=null;
    Stream stream=null;
    SocialNetwork socialNetwork=null;

    /*
    public SentimentalDataClassifier(PostIn post, String postData)
    {
        this.postData=postData;
        this.model=WebSite.ClassMgr.getWebSite(post.getSemanticObject().getModel().getName());
        initAnalysis();
    }
    * */
    public SentimentalDataClassifier(ExternalPost externalPost, Stream stream, SocialNetwork socialNetwork)
    {
        this.externalPost=externalPost;
        this.stream=stream;
        this.socialNetwork=socialNetwork;
    
        System.out.println("En SentimentalDataClassifier:"+this.externalPost);
        System.out.println("En stream:"+this.stream);
        System.out.println("En socialNetwork:"+this.socialNetwork);
        
        getExternalPostData();
        this.model=WebSite.ClassMgr.getWebSite(stream.getSemanticObject().getModel().getName());
        initAnalysis();
    }
    
    private void getExternalPostData()
    {
        externalString2Clasify=externalPost.getMessage();
        //System.out.println("SentimentalDataClassifier/getExternalPostData:"+externalString2Clasify);
        //Si la descripción es diferente que nula, se agrega al texto a ser clasificado
        /*
        if(externalPost.getDescription()!=null)
        {
            externalString2Clasify+=" "+externalPost.getDescription();
        }*/
        //System.out.println("SentimentalDataClassifier/getExternalPostData-2:"+externalString2Clasify);
    }
    

    /*
     * Metodo cuya función es la de analizar la información de cada mensaje y determinar el sentimiento del mismo,
     * así como la intensidad, eso en este momento, talvez se requiera realizar mas clasificaciones posteriormente.
     * Funciona bien al 15/06/2012
     */
    private void initAnalysis()
    {
        System.out.println("initAnalysis-J1");
        //Convierto todo el mensaje en minusculas
        externalString2Clasify=externalString2Clasify.toLowerCase();

        //Busco frases en objeto de aprendizaje (SentimentalLearningPhrase)
        //System.out.println("externalString2Clasify a revisar:"+externalString2Clasify);
        findInLearnigPhrases();
        //System.out.println("externalString2Clasify a revisado:"+externalString2Clasify+", sentimentalTweetValue:"+sentimentalTweetValue+", IntensiveTweetValue:+"+IntensiveTweetValue+", wordsCont:"+wordsCont);

        //Elimino Caracteres especiales (acentuados)
        externalString2Clasify=SWBSocialUtil.Strings.replaceSpecialCharacters(externalString2Clasify);

        //removePuntualSigns1();
        externalString2Clasify=SWBSocialUtil.Strings.removePuntualSigns(externalString2Clasify, model);

        StringTokenizer st = new StringTokenizer(externalString2Clasify);
        while (st.hasMoreTokens())
        {
            String word2Find=st.nextToken();
            if(Prepositions.ClassMgr.getPrepositions(word2Find, model)!=null) //Elimino preposiciones
            {
                continue;
            }
            
            String word2FindTmp=word2Find;
            //System.out.println("word2Find:"+word2Find);
            NormalizerCharDuplicate normalizerCharDuplicate=SWBSocialUtil.Classifier.normalizer(word2Find);
            word2Find=normalizerCharDuplicate.getNormalizedPhrase();
            //System.out.println("word Normalizada:"+word2Find);
            //Aplicar snowball a la palabra
            word2Find=SWBSocialUtil.Classifier.getRootWord(word2Find);
            //Se fonematiza la palabra
            word2Find=SWBSocialUtil.Classifier.phonematize(word2Find);
            //System.out.println("word Fonematizada:"+word2Find);
            //SentimentWords sentimentalWordObj=SentimentWords.getSentimentalWordByWord(model, word2Find);
            SentimentWords sentimentalWordObj=SentimentWords.ClassMgr.getSentimentWords(word2Find, model);
            if(sentimentalWordObj!=null) //La palabra en cuestion ha sido encontrada en la BD
            {
                //System.out.println("word2Find-1:"+word2Find);
                wordsCont++;
                IntensiveTweetValue+=sentimentalWordObj.getIntensityValue();
                //Veo si la palabra cuenta con mas de dos caracteres(Normalmente el inicial de la palabra y talvez otro que
                //hayan escrito por equivocación) en mayusculas
                //De ser así, se incrementaría el valor para la intensidad
                if(SWBSocialUtil.Strings.isIntensiveWordByUpperCase(word2FindTmp, 3))
                {
                    //System.out.println("VENIA PALABRA CON MAYUSCULAS:"+word2Find);
                    IntensiveTweetValue+=1;
                }
                //Veo si en la palabra se repiten mas de 2 caracteres para los que se pueden repetir hasta 2 veces (Arrar Doubles) 
                // y mas de 1 cuando no estan dichos caracteres en docho array, si es así entonces se incrementa la intensidad
                if(normalizerCharDuplicate.isCharDuplicate()){
                    //System.out.println("VENIA PALABRA CON CARACTERES REPETIDOS:"+word2Find);
                    IntensiveTweetValue+=1;
                }
                sentimentalTweetValue+=sentimentalWordObj.getSentimentalValue();
            }
        }
        //System.out.println("sentimentalTweetValue Final:"+sentimentalTweetValue+", wordsCont:"+wordsCont);
        
        
        //Una vez que se tiene clasificado el mensaje por sentimientos y por intensidad, falta ver si cumple el filtro(s) del stream
        {
            //TODO:FILTRAR
            //SI NO PASA EL O LOS FILTROS, HASTA AQUÍ LLEGA EL THREAD DE ESTE MENSAJE QUE ENTRÓ POR EL LISTENER.
        }
        
        //Si cumple el o los filtros, crea un objeto messageIn.
        
        MessageIn post=(MessageIn)createPostInObj();
        
        if(sentimentalTweetValue>0)
        {
            float prom=sentimentalTweetValue/wordsCont;
            post.setPostSentimentalValue(prom);
            //System.out.println("prom final:"+prom);
            if(prom>=4.5) //Si el promedio es mayor de 4.5 (Segun Octavio) es un tweet positivo
            {
                //System.out.println("Se guarda Post Positivo:"+post.getId()+", valor promedio:"+prom);
                post.setPostSentimentalType(1); //Tweet Postivivo, valor de 1 (Esto yo lo determiné)
            }else if(prom<4.5)
            {
                //System.out.println("Se guarda Post Negativo:"+post.getId()+", valor promedio:"+prom);
                post.setPostSentimentalType(2); //Tweet Negativo, valor de 1 (Esto yo lo determiné)
            }else{
                //System.out.println("Se guarda Post Neutro:"+post.getId()+", valor promedio:"+prom);
                post.setPostSentimentalType(0); //Tweet Neutro, valor de 0 (Esto yo lo determiné)
            }
        }else {
            //System.out.println("Se guarda Post Neutro POR DEFAULT:"+post.getId()+", valor promedio--4.5");
            //Si no encontro ninguna palabra de las que vienen en el post en la BD, entonces es como si no tuviera
            //valor sentimental, por lo cual lo pone con valor de 4.5 (Neutro-según Octavio)
            post.setPostSentimentalValue(Float.parseFloat("0"));
            post.setPostSentimentalType(0); //Tweet Neutro, valor de 0 (Esto yo lo determiné)
        }
        if(IntensiveTweetValue>0)
        {
            float prom=IntensiveTweetValue/wordsCont;
            //System.out.println("IntensiveTweetValue Final:"+IntensiveTweetValue+", valor promedio:"+prom);
            post.setPostIntensityValue(prom);
        }
        
        //Revisa si encuentra emoticones en el mensaje
        findEmoticones(post);
    }

    /*Metodo cuya función es la de analizar la información de cada mensaje y determinar el sentimiento del mismo,
     * así como la intensidad, eso en este momento, talvez se requiera realizar mas clasificaciones posteriormente.
     * Metodo Prueba
     */
    
    private void initAnalysiss()
    {
        System.out.println("initAnalysis-1");
        //Normalizo
        externalString2Clasify=SWBSocialUtil.Classifier.normalizer(externalString2Clasify).getNormalizedPhrase();

        //System.out.println("externalString2Clasify-1:"+externalString2Clasify);

        //Se cambia toda la frase a su modo raiz
        externalString2Clasify=SWBSocialUtil.Classifier.getRootWord(externalString2Clasify);

        //System.out.println("externalString2Clasify-2:"+externalString2Clasify);

        //Fonetizo
        externalString2Clasify=SWBSocialUtil.Classifier.phonematize(externalString2Clasify);

        //Busco frases en objeto de aprendizaje (SentimentalLearningPhrase)
        //System.out.println("externalString2Clasify a revisar:"+externalString2Clasify);
        findInLearnigPhrases();
        //System.out.println("externalString2Clasify a revisado:"+externalString2Clasify+", sentimentalTweetValue:"+sentimentalTweetValue+", IntensiveTweetValue:+"+IntensiveTweetValue+", wordsCont:"+wordsCont);

        //Elimino Caracteres especiales (acentuados)
        //externalString2Clasify=SWBSocialUtil.Strings.replaceSpecialCharacters(externalString2Clasify);

        //removePuntualSigns1();
        //externalString2Clasify=SWBSocialUtil.Strings.removePuntualSigns(externalString2Clasify, model);
        System.out.println("initAnalysis-2");
        StringTokenizer st = new StringTokenizer(externalString2Clasify);
        while (st.hasMoreTokens())
        {
            String word2Find=st.nextToken();
            //System.out.println("Palabra:"+word2Find);
            /*
            if(Prepositions.ClassMgr.getPrepositions(word2Find, model)!=null) //Elimino preposiciones
            {
                continue;
            }*/

            //String word2FindTmp=word2Find;
            //System.out.println("word2Find:"+word2Find);
            //NormalizerCharDuplicate normalizerCharDuplicate=SWBSocialUtil.Classifier.normalizer(word2Find);
            //word2Find=normalizerCharDuplicate.getNormalizedWord();
            //System.out.println("word Normalizada:"+word2Find);
            //Aplicar snowball a la palabra
            //word2Find=SWBSocialUtil.Classifier.getRootWord(word2Find);
            //Se fonematiza la palabra
            //word2Find=SWBSocialUtil.Classifier.phonematize(word2Find);
            //System.out.println("word Fonematizada:"+word2Find);
            //SentimentWords sentimentalWordObj=SentimentWords.getSentimentalWordByWord(model, word2Find);
            SentimentWords sentimentalWordObj=SentimentWords.ClassMgr.getSentimentWords(word2Find, model);
            if(sentimentalWordObj!=null) //La palabra en cuestion ha sido encontrada en la BD
            {
                //System.out.println("Palabra Encontrada:"+word2Find);
                wordsCont++;
                IntensiveTweetValue+=sentimentalWordObj.getIntensityValue();
                //Veo si la palabra cuenta con mas de dos caracteres(Normalmente el inicial de la palabra y talvez otro que
                //hayan escrito por equivocación) en mayusculas
                //De ser así, se incrementaría el valor para la intensidad
                /*
                if(SWBSocialUtil.Strings.isIntensiveWordByUpperCase(word2FindTmp, 3))
                {
                    //System.out.println("VENIA PALABRA CON MAYUSCULAS:"+word2Find);
                    IntensiveTweetValue+=1;
                }*/
                //Veo si en la palabra se repiten mas de 2 caracteres para los que se pueden repetir hasta 2 veces (Arrar Doubles)
                // y mas de 1 cuando no estan dichos caracteres en docho array, si es así entonces se incrementa la intensidad
                /*
                if(normalizerCharDuplicate.isCharDuplicate()){
                    //System.out.println("VENIA PALABRA CON CARACTERES REPETIDOS:"+word2Find);
                    IntensiveTweetValue+=1;
                }*/
                sentimentalTweetValue+=sentimentalWordObj.getSentimentalValue();
            }
        }
        System.out.println("initAnalysis-3");
        //System.out.println("sentimentalTweetValue Final:"+sentimentalTweetValue+", wordsCont:"+wordsCont);
        
        //Una vez que se tiene clasificado el mensaje por sentimientos y por intensidad, falta ver si cumple el filtro(s) del stream
        {
            //TODO:FILTRAR
            //SI NO PASA EL O LOS FILTROS, HASTA AQUÍ LLEGA EL THREAD DE ESTE MENSAJE QUE ENTRÓ POR EL LISTENER.
        }
        
        //Si cumple el o los filtros, crea un objeto messageIn.
        
        PostIn post=createPostInObj();
        
        System.out.println("initAnalysis-4");
        if(sentimentalTweetValue>0)
        {
            float prom=sentimentalTweetValue/wordsCont;
            post.setPostSentimentalValue(prom);
            //System.out.println("prom final:"+prom);
            if(prom>=4.5) //Si el promedio es mayor de 4.5 (Segun Octavio) es un tweet positivo
            {
                //System.out.println("Se guarda Post Positivo:"+post.getId()+", valor promedio:"+prom);
                post.setPostSentimentalType(1); //Tweet Postivivo, valor de 1 (Esto yo lo determiné)
            }else if(prom<4.5)
            {
                //System.out.println("Se guarda Post Negativo:"+post.getId()+", valor promedio:"+prom);
                post.setPostSentimentalType(2); //Tweet Negativo, valor de 1 (Esto yo lo determiné)
            }else{
                //System.out.println("Se guarda Post Neutro:"+post.getId()+", valor promedio:"+prom);
                post.setPostSentimentalType(0); //Tweet Neutro, valor de 0 (Esto yo lo determiné)
            }
        }else {
            //System.out.println("Se guarda Post Neutro POR DEFAULT:"+post.getId()+", valor promedio--4.5");
            //Si no encontro ninguna palabra de las que vienen en el post en la BD, entonces es como si no tuviera
            //valor sentimental, por lo cual lo pone con valor de 4.5 (Neutro-según Octavio)
            post.setPostSentimentalValue(Float.parseFloat("0"));
            post.setPostSentimentalType(0); //Tweet Neutro, valor de 0 (Esto yo lo determiné)
        }
        System.out.println("initAnalysis-5");
        if(IntensiveTweetValue>0)
        {
            float prom=IntensiveTweetValue/wordsCont;
            //System.out.println("IntensiveTweetValue Final:"+IntensiveTweetValue+", valor promedio:"+prom);
            post.setPostIntensityValue(prom);
        }
        System.out.println("initAnalysis-6");
        //Revisa si encuentra emoticones en el mensaje
        findEmoticones(post);
        System.out.println("initAnalysis-7");
    }
    
    
    /**
     * Crea objeto PostIn, de acuerdo a los datos que contenga el objeto
     * @return 
     */
    private PostIn createPostInObj()
    {
           //Persistencia del mensaje
                MessageIn message=MessageIn.ClassMgr.createMessageIn(String.valueOf(externalPost.getPostId()), model);
                message.setMsg_Text(externalPost.getMessage());
                message.setPostInSocialNetwork(socialNetwork);
                message.setPostInStream(stream);
                //System.out.println("Fuente:"+status.getSource());
                message.setPostRetweets(Integer.parseInt(""+externalPost.getRetweets()));
                //System.out.println("Ya en Msg ReTweets:"+message.getPostRetweets());
                if(externalPost.getDevice()!=null)    //Dispositivo utilizado
                {
                    String source=null;
                    int pos=externalPost.getDevice().indexOf(">");
                    if(pos>-1)
                    {
                        int pos1=externalPost.getDevice().indexOf("<",pos);
                        if(pos1>-1)
                        {
                            source=externalPost.getDevice().substring(pos+1, pos1);
                        }
                    }else{
                        source=externalPost.getDevice();
                    }
                   message.setPostSource(source);
                }
                //System.out.println("Ya en Msg source:"+source);

                if(externalPost.getCreatorId()!=null)
                {
                    if(externalPost.getPlace()!=null)
                    {
                        message.setPostPlace(externalPost.getPlace());
                    }else if(externalPost.getLocation()!=null)
                    {
                        message.setPostPlace(externalPost.getLocation());
                    }
                    
                    SocialNetworkUser socialNetUser=SocialNetworkUser.getSocialNetworkUserbyIDAndSocialNet(externalPost.getCreatorId(), socialNetwork, model);
                    if(socialNetUser==null)//
                    {
                        //Si no existe el id del usuario para esa red social, lo crea.
                        socialNetUser=SocialNetworkUser.ClassMgr.createSocialNetworkUser(model);
                        socialNetUser.setSnu_id(externalPost.getCreatorId());
                        socialNetUser.setSnu_name(externalPost.getCreatorName());
                        socialNetUser.setSnu_SocialNetwork(socialNetwork);
                        socialNetUser.setCreated(externalPost.getUsercreation());
                        //System.out.println("SocialNetworkUser Creado:"+socialNetUser.getSnu_id());
                    }else{
                        //System.out.println("SocialNetworkUser Actualizado:"+socialNetUser.getSnu_id());
                        socialNetUser.setUpdated(new Date());
                    }
                    socialNetUser.setFollowers(externalPost.getFollowers());
                    socialNetUser.setFriends(externalPost.getFriendsNumber());
                    //int listedCount=status.getUser().getListedCount();
                    //System.out.println("userId:"+userId+", created:"+socialNetUser.getCreated()+", followers:"+socialNetUser.getFollowers()+", friends:"+socialNetUser.getFriends()+", name:"+socialNetUser.getSnu_name());
                    if(socialNetUser!=null)
                    {
                        message.setPostInSocialNetworkUser(socialNetUser);
                    }
                }
                socialNetwork.addReceivedPost(message, String.valueOf(externalPost.getPostId()), socialNetwork);
                
                return message;
    }
    
    
    
    

    /*
     * Envía a publicar el mensaje para que lo tomen los timelines que esten escuchando
     */

 

    /*
     *Función que barre todas las frases y las busca en el mensaje (PostData)
     *Esto talvez pueda NO pueda ser lo mas optimo.
     *TODO:Ver si encuentra otra forma más optima de hacer esto.
     *
     */
    private void findInLearnigPhrases()
    {
        int contPositive=0;
        int contNegative=0;
        int result=0;   //Mi inicio sera 0(Neutro) y de ahi se va ha tender a la derecha (positivos) o a la izquierda (Negativos)
        int positiveintensityveResult=0;
        int negativeintensityveResult=0;
        //HashMap sntPhrasesMap=new HashMap();
        Iterator<SentimentalLearningPhrase> itSntPhases=SentimentalLearningPhrase.ClassMgr.listSentimentalLearningPhrases(model);
        while(itSntPhases.hasNext())
        {
            SentimentalLearningPhrase sntLPhrase=itSntPhases.next();
            //System.out.println("Frase Learn:"+sntLPhrase.getPhrase());
            int contOcurr=findOccurrencesNumber(sntLPhrase.getPhrase(), 0);
            if(contOcurr>0)
            {
                //System.out.println("sntLPhrase:"+sntLPhrase.getPhrase()+",contOcurrJorge:"+contOcurr);
                if(sntLPhrase.getSentimentType()==1) //la frase es positiva
                {
                    contPositive+=contOcurr;
                    result+=contOcurr;
                    if(sntLPhrase.getIntensityType()==2) //Es intensidad Alta
                    {
                        positiveintensityveResult+=8;   //Yo internamente le doy un 3 como valor (Este yo se lo pongo)
                    }else if(sntLPhrase.getIntensityType()==1) //Es intensidad Media
                    {
                        positiveintensityveResult+=5;   //Yo internamente le doy un 2 como valor (Este yo se lo pongo)
                    }else if(sntLPhrase.getIntensityType()==0) //Es intensidad Baja
                    {
                        positiveintensityveResult+=2;   //Yo internamente le doy un 1 como valor (Este yo se lo pongo)
                    }
                }else if(sntLPhrase.getSentimentType()==2){ //la frase es Negativa
                    contNegative+=contOcurr;
                    result-=contOcurr;
                    if(sntLPhrase.getIntensityType()==2) //Es intensidad Alta
                    {
                        negativeintensityveResult+=8;   //Yo internamente le doy un 3 como valor (Este yo se lo pongo)
                    }else if(sntLPhrase.getIntensityType()==1) //Es intensidad Media
                    {
                        negativeintensityveResult+=5;   //Yo internamente le doy un 2 como valor (Este yo se lo pongo)
                    }else if(sntLPhrase.getIntensityType()==0) //Es intensidad Baja
                    {
                        negativeintensityveResult+=2;   //Yo internamente le doy un 1 como valor (Este yo se lo pongo)
                    }
                }
            }
        }
        //System.out.println("result k:"+result);
        //Reglas
        if(result>0)    //Es positivo
        {
            sentimentalTweetValue=7*contPositive;   //El 7 yo lo propongo dado que la númeración va de 0-9 considero que un 7 es la media para los positivos
            IntensiveTweetValue=positiveintensityveResult/contPositive;
            wordsCont=contPositive;
        }else if(result<0){ //Es Negativo
            sentimentalTweetValue=3*contNegative; //El 3 yo lo propongo dado que la númeración va de 0-9 considero que un 3 es la media para los Negativos
            IntensiveTweetValue=negativeintensityveResult/contNegative;
            wordsCont=contNegative;
        }else{ //Es Neutro
            sentimentalTweetValue=0;
            IntensiveTweetValue=0;
            wordsCont=0;
        }
    }

    /*
     * Función que encuentra el número de ocurrencias en una frase
     */
    private int findOccurrencesNumber(String phrase, int contOcurrences)
    {
        int iocurrence=externalString2Clasify.indexOf(phrase);
        if(iocurrence>-1)
        {
            contOcurrences++;
            externalString2Clasify=externalString2Clasify.substring(0, iocurrence)+externalString2Clasify.substring(iocurrence+phrase.length());
            //System.out.println("phrase:"+phrase+",Ocurrencia:"+contOcurrences+",externalString2Clasify:"+externalString2Clasify);
            contOcurrences=findOccurrencesNumber(phrase, contOcurrences);
        }
        return contOcurrences;
    }

  
    /*
     * Encuentra emoticons en el mensaje
     * TODO:Hacer que los emoticons esten almacenados en un objeto.
     */
    
    private void findEmoticones(PostIn post)
    {
         int contPositiveEmoticon=0;
         int contNegativeEmoticon=0;
         StringTokenizer st = new StringTokenizer(externalString2Clasify);
         while (st.hasMoreTokens())
         {
             String word2Find=st.nextToken();
            if(word2Find.indexOf("=)")>-1 || word2Find.indexOf(":)")>-1 || word2Find.indexOf(":))")>-1 || word2Find.indexOf("(:")>-1 || word2Find.indexOf(":-)")>-1 || word2Find.indexOf("(-:")>-1 || word2Find.indexOf("<3")>-1) //Sentimiento Positivo
             {
                contPositiveEmoticon++;
             }else if(word2Find.indexOf("=(")>-1 || word2Find.indexOf(":(")>-1 || word2Find.indexOf(":((")>-1 || word2Find.indexOf("):")>-1 || word2Find.indexOf(":-(")>-1 || word2Find.indexOf(")-:")>-1) //Sentimineto negativo
             {
                contNegativeEmoticon++;
             }
        }
        if(contPositiveEmoticon>contNegativeEmoticon)
        {
            post.setPostSentimentalEmoticonType(1);
        }else if(contPositiveEmoticon<contNegativeEmoticon)
        {
            post.setPostSentimentalEmoticonType(2);
        }
    }




    //**************** METODOS PARA PROBAR EL CLASIFICADOR **********************

    public static void main(String args[]){
        MessageIn msgIn=null;
        //MessageIn msgIn=MessageIn.ClassMgr.createMessageIn("1234567890", WebSite.ClassMgr.getWebSite("Infotc"));
        //msgIn.setMsg_Text("#Peleaporelsegundo lugar en la persona más PENDEJA entre Peña Nieto y Ninel Conde, dicen que Peña Nieto tiene la victoria asegurada.");
        //new SentimentalDataClassifier(msgIn, msgIn.getMsg_Text());
        //new SentimentalDataClassifier(msgIn, "#Peleaporelsegundo! lugar... en la persona! más PENDEJA? entre Peña Nieto y Ninel Conde!, dicen que Peña Nieto tiene la victoria asegurada!!.");
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
            externalString2Clasify=externalString2Clasify.replaceAll(sign, "");
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
        alist.add("y");
        alist.add("yo");
        alist.add("tu");
        alist.add("dicen");
        alist.add("todos");
        alist.add("tampoco");
        alist.add("pero");
        alist.add("siempre");
        alist.add("tener");
        alist.add("si");
        alist.add("no");
        alist.add("es");
        alist.add("solo");
        alist.add("aun");
        alist.add("todo");
        alist.add("toda");
        alist.add("su");
        alist.add("mio");
        alist.add("mia");

        Iterator<String> itPreps=alist.iterator();
        while(itPreps.hasNext())
        {
            String sprep=itPreps.next();
            //removeWordFromArray(aPostDataWords, sprep);
        }
        return aPostDataWords;
    }

}
