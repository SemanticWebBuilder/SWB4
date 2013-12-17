/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.listener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import org.json.JSONArray;
import org.json.JSONObject;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.SWBModel;
import org.semanticwb.model.WebSite;
import org.semanticwb.social.Action;
import org.semanticwb.social.Country;
import org.semanticwb.social.CountryState;
import org.semanticwb.social.ExternalPost;
import org.semanticwb.social.Kloutable;
import org.semanticwb.social.LifeStage;
import org.semanticwb.social.Listenerable;
import org.semanticwb.social.MarkMsgAsPrioritary;
import org.semanticwb.social.MessageIn;
import org.semanticwb.social.PhotoIn;
import org.semanticwb.social.PostIn;
import org.semanticwb.social.SendEmail;
import org.semanticwb.social.SendPost;
import org.semanticwb.social.SocialNetwork;
import org.semanticwb.social.SocialNetworkUser;
import org.semanticwb.social.SocialRule;
import org.semanticwb.social.SocialRuleRef;
import org.semanticwb.social.Stream;
import org.semanticwb.social.VideoIn;
import org.semanticwb.social.YouTubeCategory;
import org.semanticwb.social.Youtube;
import org.semanticwb.social.util.SWBSocialRuleMgr;
import org.semanticwb.social.util.SWBSocialUtil;

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
    
    private static Logger log = SWBUtils.getLogger(SentimentalDataClassifier.class);
    
    //PostIn post=null;
    String externalString2Clasify = null;
    //String externalString2Clasify_TMP;
    SWBModel model = null;
    float sentimentalTweetValue = 0;
    float IntensiveTweetValue = 0;
    int wordsCont = 0;
    WebSite wbAdmSocialSite = null;
    ExternalPost externalPost = null;
    Stream stream = null;
    SocialNetwork socialNetwork = null;
    boolean classifyGeoLocation = false;
    org.semanticwb.social.SocialSite wsite = null;
    //boolean checkKlout=false;

    /*
     public SentimentalDataClassifier(PostIn post, String postData)
     {
     this.postData=postData;
     this.model=WebSite.ClassMgr.getWebSite(post.getSemanticObject().getModel().getName());
     initAnalysis();
     }
     * */
    public SentimentalDataClassifier(ExternalPost externalPost, Stream stream, SocialNetwork socialNetwork, boolean classifyGeoLocation) {
        if (!stream.isValid()) {
            return;
        }

        this.externalPost = externalPost;
        this.stream = stream;
        this.socialNetwork = socialNetwork;
        this.classifyGeoLocation = classifyGeoLocation;
        this.wsite = org.semanticwb.social.SocialSite.ClassMgr.getSocialSite(stream.getSocialSite().getId());
        System.out.println("wsite en SentimentalDataClassifier:" + wsite);
        //this.checkKlout=checkKlout;


        //System.out.println("En SentimentalDataClassifier:"+this.externalPost);
        //System.out.println("En stream:"+this.stream);
        ////System.out.println("En socialNetwork:"+this.socialNetwork);
        //System.out.println("Creator id:"+externalPost.getCreatorId());
        //System.out.println("Creator name:"+externalPost.getCreatorName());

        getExternalPostData();
        this.model = WebSite.ClassMgr.getWebSite(stream.getSemanticObject().getModel().getName());
        initAnalysis();
    }

    private void getExternalPostData() {
        externalString2Clasify = externalPost.getMessage();

        System.out.println("SentimentalDataClassifier/getExternalPostData:" + externalString2Clasify);
        //Si la descripción es diferente que nula, se agrega al texto a ser clasificado
        /*
         if(externalPost.getDescription()!=null)
         {
         externalString2Clasify+=" "+externalPost.getDescription();
         }*/
        //System.out.println("SentimentalDataClassifier/getExternalPostData-2:"+externalString2Clasify);
    }

    /*Metodo cuya función es la de analizar la información de cada mensaje y determinar el sentimiento del mismo,
     * así como la intensidad, eso en este momento, talvez se requiera realizar mas clasificaciones posteriormente.
     */
    private void initAnalysis() {
        long tini = System.currentTimeMillis();
        //Normalizo
        String externalString2Clasify_TMP = externalString2Clasify;
        /*
         externalString2Clasify=SWBSocialUtil.Classifier.normalizer(externalString2Clasify).getNormalizedPhrase();
        
         //Se cambia toda la frase a su modo raiz
         externalString2Clasify=SWBSocialUtil.Classifier.getRootWord(externalString2Clasify);
        
         //Fonetizo
         externalString2Clasify=SWBSocialUtil.Classifier.phonematize(externalString2Clasify);
        
         //Busco frases en objeto de aprendizaje (SentimentalLearningPhrase)
         findInLearnigPhrases();
        
         //Elimino Caracteres especiales (acentuados)
         externalString2Clasify=SWBSocialUtil.Strings.replaceSpecialCharacters(externalString2Clasify);

         SocialAdmin socialAdminSite=(SocialAdmin)SWBContext.getAdminWebSite();
        
         externalString2Clasify=SWBSocialUtil.Strings.removePuntualSigns(externalString2Clasify, socialAdminSite);
        
         ArrayList<String> aListWords=new ArrayList();
         StringTokenizer st = new StringTokenizer(externalString2Clasify);
         while (st.hasMoreTokens())
         {
         String word2Find=st.nextToken();
         if(Prepositions.ClassMgr.getPrepositions(word2Find, socialAdminSite)!=null) //Elimino preposiciones
         {
         continue;
         }

         String word2FindTmp=word2Find;
         NormalizerCharDuplicate normalizerCharDuplicate=SWBSocialUtil.Classifier.normalizer(word2Find);
         word2Find=normalizerCharDuplicate.getNormalizedPhrase();
         aListWords.add(word2Find);
            
         SentimentWords sentimentalWordObj=SentimentWords.ClassMgr.getSentimentWords(word2Find, socialAdminSite);
         if(sentimentalWordObj!=null) //La palabra en cuestion ha sido encontrada en la BD
         {
         wordsCont++;
         IntensiveTweetValue+=sentimentalWordObj.getIntensityValue();
         //Veo si la palabra cuenta con mas de dos caracteres(Normalmente el inicial de la palabra y talvez otro que
         //hayan escrito por equivocación) en mayusculas
         //De ser así, se incrementaría el valor para la intensidad
         if(SWBSocialUtil.Strings.isIntensiveWordByUpperCase(word2FindTmp, 3))
         {
         IntensiveTweetValue+=1;
         }
         //Veo si en la palabra se repiten mas de 2 caracteres para los que se pueden repetir hasta 2 veces (Arrar Doubles)
         // y mas de 1 cuando no estan dichos caracteres en docho array, si es así entonces se incrementa la intensidad
         if(normalizerCharDuplicate.isCharDuplicate()){
         IntensiveTweetValue+=1;
         }
         sentimentalTweetValue+=sentimentalWordObj.getSentimentalValue();
         }
         }
        
         //Empieza manejo de filtros
        
         //Filtros por sentimiento
        
         float promSentimentalValue=0; 
         int sentimentalTweetValueType=0;    //Por defecto sería neutro
         if(sentimentalTweetValue>0) //Se revisa de acuerdo al promedio de sentimentalTweetValue/wordsCont, que valor sentimental posee el tweet
         {
         promSentimentalValue=sentimentalTweetValue/wordsCont;
         if(promSentimentalValue>=4.5) //Si el promedio es mayor de 4.5 (Segun Octavio) es un tweet positivo
         {
         sentimentalTweetValueType=1;
         }else if(promSentimentalValue<4.5)
         {
         sentimentalTweetValueType=2;
         }
         }
       
         int intensityTweetValueType=0;    //Por defecto sería un tweet con intensidad baja.
         float promIntensityValue=0; 
         if(IntensiveTweetValue>0)
         {
         promIntensityValue=IntensiveTweetValue/wordsCont;
         if(promIntensityValue>=5.44) //Si el promedio es mayor de 5.44 sería un tweet con intesidad alta, ya que la maxima en intensidad es de 8.16
         {
         intensityTweetValueType=2;
         }else if(promIntensityValue<5.44 && promIntensityValue>=2.72) //tweet con intensidad media
         {
         intensityTweetValueType=1;
         }
         }
         */

        //System.out.println("externalString2Clasify:"+externalString2Clasify);


        if (externalString2Clasify == null) {
            return;
        }

        //Para el caso de Streaming Api que no se le puede enviar un bounding box, ni una latitud, longitud y radio para que solo me traiga tweets de una región
        if (stream.getGeoCenterLatitude() != 0 && stream.getGeoCenterLongitude() != 0 && classifyGeoLocation) {
            //System.out.println("Geo-1");
            if (externalPost.getLatitude() != 0 && externalPost.getLongitude() != 0) {
                double eart_Radio = SWBSocialUtil.EART_RADIUS_KM; //Por defecto se mide en Kilometros
                if (stream.getGeoDistanceUnit().equals("MI")) {
                    eart_Radio = SWBSocialUtil.EART_RADIUS_MI;
                }
                double distance = 50;   //distancia en Radio por defecto 50
                if (stream.getGeoRadio() > 0) {
                    distance = stream.getGeoRadio();
                }
                //System.out.println("Geo-2");
                org.semanticwb.social.util.GeoLocation myLocation = org.semanticwb.social.util.GeoLocation.fromDegrees(stream.getGeoCenterLatitude(), stream.getGeoCenterLongitude());
                org.semanticwb.social.util.GeoLocation[] boundingCoordinates = myLocation.boundingCoordinates(distance, eart_Radio);
                if (!SWBSocialUtil.Util.isPointInsideCoodinates(externalPost.getLatitude(), externalPost.getLongitude(), boundingCoordinates)) {
                    //System.out.println("Geo-3");
                    externalPost = null;  //Destruyo el objeto ExternalPost, para que no consuma memoria.
                    return; //Regresa sin hacer nada.
                }
            } else {  //Si en el stream se indica que es mandatorio que se tomen los tweets de una cierta región y el tweet que llega no tiene localización (latitud y longitud) pues No se hace nada con ese externalPost;
                //System.out.println("Geo-4");
                externalPost = null;  //Destruyo el objeto ExternalPost, para que no consuma memoria.
                return; //Regresa sin hacer nada.
            }
        }
        //Pasó filtrado por región.
        //System.out.println("Geo-Fin");

        HashMap hmapValues = SWBSocialUtil.Classifier.classifyText(externalString2Clasify);
        float promSentimentalValue = ((Float) hmapValues.get("promSentimentalValue")).floatValue();
        int sentimentalTweetValueType = ((Integer) hmapValues.get("sentimentalTweetValueType")).intValue();
        float promIntensityValue = ((Float) hmapValues.get("promIntensityValue")).floatValue();
        int intensityTweetValueType = ((Integer) hmapValues.get("intensityTweetValueType")).intValue();

        //long tfinTMP1=System.currentTimeMillis() - tini;
        //System.out.println("\n<!--Total Time to Classify--TMP1: " + tfinTMP1 + "ms - SWBSocial--> ");
        /*
         System.out.println("SentimentalData../promSentimentalValue:"+promSentimentalValue);
         System.out.println("SentimentalData../sentimentalTweetValueType:"+sentimentalTweetValueType);
         System.out.println("SentimentalData../promIntensityValue:"+promIntensityValue);
         System.out.println("SentimentalData../intensityTweetValueType:"+intensityTweetValueType);
         */

        //////////////////////////////////ESTO (PARA ABAJO) SI FUNCIONA BIEN ------10 - Julio - 2013//////////////////////////////////
        boolean filterPositives = stream.isFilterSentimentalPositive();
        boolean filterNegatives = stream.isFilterSentimentalNegative();
        boolean filterNeutrals = stream.isFilterSentimentalNeutral();

        boolean createPostInbySentiment = false;

        //Si el stream no tiene filtros de sentimientos, es decir, que todos sean positivos o negativos, entonces los mensajes siempre pasarían este filtro
        if ((filterPositives && filterNegatives && filterNeutrals) || (!filterPositives && !filterNegatives && !filterNeutrals)) {
            createPostInbySentiment = true;
        } else {
            if (sentimentalTweetValueType == 1) {
                if (filterPositives) {
                    createPostInbySentiment = true;
                }
            } else if (sentimentalTweetValueType == 2) {
                if (filterNegatives) {
                    createPostInbySentiment = true;
                }
            } else if (sentimentalTweetValueType == 0) {
                if (filterNeutrals) {
                    createPostInbySentiment = true;
                }
            }
        }

        //Filtros por intensidad
        boolean filterIntensityHigh = stream.isFilterIntensityHigh();
        boolean filterIntensityMedium = stream.isFilterIntensityMedium();
        boolean filtrarIntensityLow = stream.isFilterIntensityLow();


        boolean createPostInbyIntensity = false;
        //Si el stream no tiene filtros de intensidad, es decir, que todos sean positivos o negativos, entonces los mensajes siempre pasarían este filtro
        if ((filterIntensityHigh && filterIntensityMedium && filtrarIntensityLow) || (!filterIntensityHigh && !filterIntensityMedium && !filtrarIntensityLow)) {
            createPostInbyIntensity = true;
        } else {
            if (intensityTweetValueType == 2) {
                if (filterIntensityHigh) {
                    createPostInbyIntensity = true;
                }
            } else if (intensityTweetValueType == 1) {
                if (filterIntensityMedium) {
                    createPostInbyIntensity = true;
                }
            } else if (intensityTweetValueType == 0) {
                if (filtrarIntensityLow) {
                    createPostInbyIntensity = true;
                }
            }
        }
        //long tfinTMP2=System.currentTimeMillis() - tini;
        //System.out.println("\n<!--Total Time to Classify--TMP2: " + tfinTMP2 + "ms - SWBSocial--> ");
        System.out.println("CLASIFICADOR-STEP-1");
        if (createPostInbySentiment && createPostInbyIntensity) //Si pasa los filtros, entonces se crea el mensaje, de lo contrario el mensaje de la red social nunca se persiste.
        {
            System.out.println("PASA FILTRO DE SENTIMIENTOS E INTENSIDAD");
            //Si pasó filtro por sentimiento e intensidad, entonces revisa filtro por klout
            SocialNetworkUser socialNetUser = null;
            String creatorId = externalPost.getCreatorId();
            socialNetUser = SocialNetworkUser.getSocialNetworkUserbyIDAndSocialNet("" + creatorId, socialNetwork, model);
            boolean createPostbyKlout = false;
            boolean upDateSocialUserNetworkData = false;
            int days2RefreshUserData = 5; //Minumun, if this number is less, the performance could be affected
            if ((wsite).getNumDaysToRefreshUserData() > 5) {
                days2RefreshUserData = wsite.getNumDaysToRefreshUserData();
            }
            int days = 0;
            if (socialNetUser != null) {
                days = SWBSocialUtil.Util.Datediff(socialNetUser.getUpdated(), Calendar.getInstance().getTime());
                if (days >= days2RefreshUserData) {
                    upDateSocialUserNetworkData = true;
                }
            }

            System.out.println("checkKlout en Clasificador..:" + stream.getStream_KloutValue());
            int userKloutScore = 0;
            if (stream.getStream_KloutValue() > 0 && socialNetwork.getSemanticObject().getSemanticClass().isSubClass(Kloutable.social_Kloutable)) {
                System.out.println("creatorId en Sentimental:" + creatorId);
                HashMap userKloutDat = SWBSocialUtil.Classifier.classifybyKlout(socialNetwork, stream, socialNetUser, creatorId, upDateSocialUserNetworkData);
                createPostbyKlout = ((Boolean) userKloutDat.get("createPostbyKlout")).booleanValue();
                userKloutScore = ((Integer) userKloutDat.get("userKloutScore")).intValue();
                //Filtro de Klout
                //stream.setStream_KloutValue(10);
                //Si se requiere filtrar por Klout, esto es porque exista un valo de klout para el stream>0
                //System.out.println("creatorId:"+creatorId+"socialNetUser:"+socialNetUser+",Klout-0, stream k:"+stream.getStream_KloutValue()+",boolean:"+socialNetwork.getSemanticObject().getSemanticClass().isSubClass(Kloutable.social_Kloutable));
                /*
                 if(socialNetwork.getSemanticObject().getSemanticClass().isSubClass(Kloutable.social_Kloutable))
                 {
                 //System.out.println("Klout-1");
                 if(creatorId!=null) //Siempre debería externalPost.getCreatorId() traer un valor, por lo tanto debería entrar a este if
                 {
                 //System.out.println("Klout-2:"+creatorId);
                 //Revisar si existe el usuario en nuestra BD y si ya paso mas de 5 días en mi cache.
                 {
                 if(socialNetUser!=null)
                 {
                 userKloutScore=socialNetUser.getSnu_klout();
                 //System.out.println("Usuario:"+socialNetUser+", SI existe, su Klout es:"+userKloutScore);
                 //System.out.println("userKloutScore:"+userKloutScore);

                 //                                    String patron = "yyyy/MM/dd:hh:mm:ss:SSS:a";
                 //                                    SimpleDateFormat formato = new SimpleDateFormat(patron);
                 //                                    // formateo
                 //                                    System.out.println("Fecha Klout Registrada de usuario:"+formato.format(socialNetUser.getUpdated()));
                 //                                    System.out.println("days Dif:"+days);

                 int numDaysToCheckKlout=5;
                 if(SWBSocialUtil.Util.getModelPropertyValue(SWBContext.getAdminWebSite(), "numDaysToCheckKlout")!=null)
                 {
                 try
                 {
                 numDaysToCheckKlout=Integer.parseInt(SWBSocialUtil.Util.getModelPropertyValue(SWBContext.getAdminWebSite(), "numDaysToCheckKlout"));
                 }catch(NumberFormatException ignored){
                 numDaysToCheckKlout=5;
                 }
                 }
                 //System.out.println("days:"+days+",numDaysToCheckKlout:"+numDaysToCheckKlout);
                 if(days<numDaysToCheckKlout) //Si aun no pasan 5 días de la ultima actualización de los datos del usuario (incluyendo su klout), entonces toma el valor de klout que tiene guardado en BD
                 {
                 if(userKloutScore>=stream.getStream_KloutValue())
                 {
                 createPostbyKlout=true;
                 }
                 }else{  //Si ya pasaron 5 o mas días de que se actualizó la info del usuario, entonces busca su score en Klout
                 Kloutable socialNetKloutAble=(Kloutable) socialNetwork;
                 userKloutScore=Double.valueOf(socialNetKloutAble.getUserKlout(creatorId)).intValue(); 
                 //System.out.println("NO Existe usuario en BD, userKloutScore K TRAJO-1:"+userKloutScore);
                 if(userKloutScore>=stream.getStream_KloutValue())
                 {
                 createPostbyKlout=true;
                 upDateSocialUserNetworkData=true;
                 }
                 }
                 }else { //No existe en la BD, debo revisar su klout
                 Kloutable socialNetKloutAble=(Kloutable) socialNetwork;
                 userKloutScore=Double.valueOf(socialNetKloutAble.getUserKlout(creatorId)).intValue(); 
                 //System.out.println("No existe usuario, userKloutScore K TRAJO-2:"+userKloutScore);
                 if(userKloutScore>=stream.getStream_KloutValue())
                 {
                 createPostbyKlout=true;
                 }
                 //System.out.println("createPostbyKlout:"+createPostbyKlout);
                 }
                 }
                 //Termina de revisar si existe el usuario en nuestra BD y si ya paso mas de 5 días en mi cache.
                 }
                 }else{
                 createPostbyKlout=true;
                 }*/
            } else {
                createPostbyKlout = true;
            }

            //long tfinTMP3=System.currentTimeMillis() - tini;
            //System.out.println("\n<!--Total Time to Classify--TMP3: " + tfinTMP3 + "ms - SWBSocial--> ");
            System.out.println("Klout de usuario del mensaje, lo crea o no??:" + createPostbyKlout);
            if (createPostbyKlout) //Si pasa el filtro de Klout del usuario, entonces ya persite el mensaje en BD
            {
                System.out.println("Paso filtro de sentimientos, intensidad y klout---vamos a persistir el msg...");
                PostIn post = createPostInObj(socialNetUser, userKloutScore, upDateSocialUserNetworkData, days);

                //Guarda valores sentimentales en el PostIn (mensaje de entrada)
                post.setPostSentimentalValue(promSentimentalValue);
                post.setPostSentimentalType(sentimentalTweetValueType);

                //Guarda valores sentimentales en el PostIn (mensaje de entrada)
                post.setPostIntensityValue(promIntensityValue);
                post.setPostIntesityType(intensityTweetValueType);

                //System.out.println("Valor sentimental puesto al final:"+post.getPostSentimentalValue());
                //System.out.println("Valor de intensidad puesto al final:"+post.getPostIntensityValue());

                //Revisa si encuentra emoticones en el mensaje
                //System.out.println("SentimentalData..text/externalString2Clasify:"+externalString2Clasify);
                findEmoticones(post, externalString2Clasify_TMP);
                //MessageIn messageIn=(MessageIn)post;
                //System.out.println("messageIn final:"+messageIn.getMsg_Text());

                //Clasificación por palabras relacionadas a un tema
                SWBSocialUtil.Classifier.clasifyMsgbySocialTopic(post, externalString2Clasify_TMP, true);

                boolean firstTime = true;
                boolean rulesClassifierValue = false;
                boolean rulesClassifierValueTmp = false;
                ArrayList<SocialRule> streamRules = new ArrayList();
                //Momento de revisar las reglas del stream
                Iterator<SocialRuleRef> itsocialRuleRefs = stream.listSocialRuleRefs();
                System.out.println("itsocialRuleRefs jorge:" + itsocialRuleRefs.hasNext());
                while (itsocialRuleRefs.hasNext()) {
                    SocialRuleRef socialRuleRef = itsocialRuleRefs.next();
                    if (socialRuleRef.isActive() && socialRuleRef.getSocialRule() != null) {
                        System.out.println("ReglaRef k:" + socialRuleRef);
                        SWBSocialRuleMgr socialRuleMgr = new SWBSocialRuleMgr();
                        SocialRule socialRule = socialRuleRef.getSocialRule();
                        rulesClassifierValueTmp = socialRuleMgr.eval(post, socialRule);
                        System.out.println("rulesClassifierValueTmp-1:" + rulesClassifierValue);
                        if (firstTime) {
                            rulesClassifierValue = rulesClassifierValueTmp;
                            firstTime = false;
                        } else {
                            rulesClassifierValue = rulesClassifierValue && rulesClassifierValueTmp;
                            //System.out.println("rulesClassifierValue-2:"+rulesClassifierValue);
                        }
                        streamRules.add(socialRule);
                    }
                }
                //System.out.println("rulesClassifierValue-Final:"+rulesClassifierValue);
                //Si el mensaje cumple con las reglas que tiene el stream por el cual provinó, entonces se ejecutan las acciones asignadas a dichas reglas.
                if (rulesClassifierValue) {
                    Iterator<SocialRule> itRules = streamRules.iterator();
                    while (itRules.hasNext()) {
                        SocialRule socialRule = itRules.next();
                        System.out.println("Entra CR/J1");
                        Iterator<Action> itActions = socialRule.listActions();
                        while (itActions.hasNext()) {
                            Action action = itActions.next();
                            System.out.println("Entra CR/J2:" + action);
                            if (action instanceof SendEmail) {
                                System.out.println("Entra CR/J3:" + action);
                                SendEmail.sendEmail(action, post, stream, socialNetwork);
                            } else if (action instanceof SendPost) {
                                if (post.getSocialTopic() != null) {
                                    SendPost.sendPost(action, post);
                                }
                            } else if (action instanceof MarkMsgAsPrioritary) {
                                MarkMsgAsPrioritary.markMsgAsPrioritary(action, post);
                            }
                        }
                    }
                }
            }
        }
        long tfin = System.currentTimeMillis() - tini;
        System.out.println("\n<!--Total Time to Classify: " + tfin + "ms - SWBSocial--> ");
    }

    /*
     * Metodo que revisa el mensaje del post de entrada, si concuerda con algunas de las palabras clave
     * encontradas en algún SocialTopic (Tema), entonces al PostIn le asocia dicho Tema.
     */
    /*
     public void clasifyMsgbySocialTopic(PostIn post, String text)
     {
     SocialSite socialSite=SocialSite.ClassMgr.getSocialSite(post.getSemanticObject().getModel().getName());
     //System.out.println("Asocialcion de socialTopic-23-1");
     //Elimino Caracteres especiales (acentuados)
     String externalMsgTMP=SWBSocialUtil.Strings.replaceSpecialCharacters(text);

     SocialAdmin socialAdminSite=(SocialAdmin)SWBContext.getAdminWebSite();

     externalMsgTMP=SWBSocialUtil.Strings.removePuntualSigns(externalMsgTMP, socialAdminSite);
        
     ArrayList<String> amsgWords=new ArrayList();
     String[] msgWords=externalMsgTMP.split(" ");
     for(int i=0;i<msgWords.length;i++)
     {
     String msgWord=msgWords[i];
     if(msgWord!=null && msgWord.length()>0)
     {
     amsgWords.add(msgWord.toLowerCase());
     }
     }
                
     //System.out.println("Asocialcion de socialTopic-23");
                
     Iterator <SocialTopic> itSocialTopics=SocialTopic.ClassMgr.listSocialTopics(socialSite);
     while(itSocialTopics.hasNext())
     {
     SocialTopic socialTopic=itSocialTopics.next();
     if(socialTopic.isActive() && !socialTopic.isDeleted())  //Si el SocialTopic esta activo y no borrado
     {
     String sTags=socialTopic.getTags();
     boolean existWord=false;
     if(sTags!=null && sTags.length()>0)
     {
     String[] tags=sTags.split("\\,");  //Dividir valores
     for(int i=0;i<tags.length;i++)
     {
     String tag=tags[i];
     //System.out.println("tag:"+tag);

     //Elimino Caracteres especiales (acentuados)
     tag=SWBSocialUtil.Strings.replaceSpecialCharacters(tag);

     tag=SWBSocialUtil.Strings.removePuntualSigns(tag, socialAdminSite);

     //System.out.println("Tag2_Final:"+tag);
     //

     //Si una de las palabras clave de un tema esta en el mensaje de entrada, entonces se agrega al postIn ese tema 
     //y ya no se continua iterando en los temas
     if(amsgWords.contains(tag.toLowerCase()))
     {
     //System.out.println("tag SI esta contenido en las palabras:"+tag);
     //Hice que un msg de entrada solo se pudiera asignar a un tema debido a que si fuera a mas, entonces sería revisado el mismo msg por 
     //varios usuarios en varios flujos, es mejor que se vaya solo a un flujo, asignando bien las palabras clave a cada tema (que no se repitan) 
     // y si se clasificó a un tema que no debia de ser (por no colocar correctamente las palabras clave), las personas en un flujo podrían
     //reclasificar en cualquier momento el mensaje, para que se vaya a otro tema y por consiguiente a otro flujo.
     //System.out.println("Al post se le asocial SocialTopic:"+socialTopic.getURI());
     post.setSocialTopic(socialTopic);    

     //Envío de correo a los usuarios de los grupos que se encuentre asignados al socialtopic, para avisarles
     //del nuevo mensaje que ha llegado a su bandeja
     Iterator<User> itSocialTopicUsers=SWBSocialUtil.SocialTopic.getUsersbySocialTopic(socialTopic).iterator();
     while(itSocialTopicUsers.hasNext()) 
     {
     User user=itSocialTopicUsers.next();
     if(user.getEmail()!=null && SWBUtils.EMAIL.isValidEmailAddress(user.getEmail()))
     {
     String sBody="Hola "+user.getFullName()+"<br>";
     sBody+="Le comunicamos que existe un nuevo mensaje en la bandeja de entrada del tema:"+socialTopic.getTitle()+", al cual usted se encuentra subscripo<br><br><br>";
     sBody+="El mensaje cuenta con el siguiente texto:<br><br><br>";
     sBody+=post.getMsg_Text()+"<br><br><br>";
     if(post.getPostInSocialNetworkUser()!=null)
     {
     sBody+="Usuario:"+post.getPostInSocialNetworkUser().getSnu_name()+"<br><br><br>";
     if(post.getPostInSocialNetworkUser().getSnu_klout()>0)
     {
     sBody+="Klout:"+post.getPostInSocialNetworkUser().getSnu_klout();
     }
     }
     try
     {
     SWBUtils.EMAIL.sendBGEmail(user.getEmail(), "Nuevo Mensaje de Entra en Tema:"+socialTopic.getTitle(), sBody);
     }catch(SocketException so)
     {
     log.error(so);
     }
     }
     }

     existWord=true;
     break;
     }
     }
     if(existWord) {
     break;
     }    //Ahora se saldría del while.
     }
     }
     }
     }
     */
    /**
     * Crea objeto PostIn, de acuerdo a los datos que contenga el objeto
     *
     * @return
     */
    private PostIn createPostInObj(SocialNetworkUser socialNetUser, int userKloutScore, boolean upDateSocialUserNetworkData, int days) {
        PostIn postIn = null;
        try {
            //Persistencia del mensaje
            //System.out.println("externalPost.getPostType():"+externalPost.getPostType());
            if (externalPost.getPostType().equals(SWBSocialUtil.MESSAGE)) {
                postIn = MessageIn.ClassMgr.createMessageIn(model);
                postIn.setPi_type(SWBSocialUtil.POST_TYPE_MESSAGE);   //Para fines de indexado para ordenamientos con Sparql
            } else if (externalPost.getPostType().equals(SWBSocialUtil.PHOTO) && externalPost.lisPictures().hasNext()) {
                postIn = PhotoIn.ClassMgr.createPhotoIn(model);
                PhotoIn photoIn = (PhotoIn) postIn;
                photoIn.setPi_type(SWBSocialUtil.POST_TYPE_PHOTO);    //Para fines de indexado para ordenamientos con Sparql
                if (externalPost.lisPictures().hasNext()) {
                    String sphoto = (String) externalPost.lisPictures().next();
                    if (sphoto != null && sphoto.trim().length() > 0) {
                        photoIn.addPhoto(sphoto);
                    }
                }
            } else if (externalPost.getPostType().equals(SWBSocialUtil.VIDEO) && externalPost.getVideo() != null) {
                postIn = VideoIn.ClassMgr.createVideoIn(model);
                VideoIn videoIn = (VideoIn) postIn;
                videoIn.setVideo(externalPost.getVideo());
                videoIn.setPi_type(SWBSocialUtil.POST_TYPE_VIDEO);    //Para fines de indexado para ordenamientos con Sparql
                if (externalPost.getCategory() != null) {
                    //Para categorias de youtube, si despues se manejan mas categorias para otras redes sociales de video, sería un bloque similar al siguiente
                    //Si despues se manejan mas categorias en otras redes sociales que no sean de tipo Video, tendría que quitar la propiedad category que se encuentra
                    //en este momento en la interfaz PostVideoable y ponerselas a las de mas redes sociales, talvez a la clase SocialNetwork directamenre
                    if (externalPost.getSocialNetwork() instanceof Youtube) {
                        YouTubeCategory youTubeCate = YouTubeCategory.ClassMgr.getYouTubeCategory(externalPost.getCategory(), SWBContext.getAdminWebSite());
                        //System.out.println("youTubeCate-1:"+youTubeCate);
                        if (youTubeCate != null) {
                            //System.out.println("youTubeCate-2:"+youTubeCate);
                            videoIn.setCategory(youTubeCate.getURI());
                        }
                    }
                }
            }

            if (externalPost.getMessage() != null) {
                //System.out.println("postIn fACE:"+postIn+", externalPost:"+externalPost);
                postIn.setMsg_Text(externalPost.getMessage());
            }
            postIn.setSocialNetMsgId(externalPost.getPostId());

            Calendar calendario = Calendar.getInstance();
            System.out.println("FECHA AL POSTIN:" + calendario.getTime());
            postIn.setPi_created(calendario.getTime());   //Para fines de indexado para ordenamientos con Sparql

            /*
             if(externalPost.getDescription()!=null)
             {
             postIn.setDescription(externalPost.getDescription());
             }*/
            //System.out.println("CREÓ EL MENSAJE CON TEXTO:"+postIn.getMsg_Text());
            postIn.setPostInSocialNetwork(socialNetwork);
            postIn.setPostInStream(stream);

            //System.out.println("Ya en Msg ReTweets:"+message.getPostRetweets());
            if (externalPost.getDevice() != null) //Dispositivo utilizado
            {
                String source = null;
                int pos = externalPost.getDevice().indexOf(">");
                if (pos > -1) {
                    int pos1 = externalPost.getDevice().indexOf("<", pos);
                    if (pos1 > -1) {
                        source = externalPost.getDevice().substring(pos + 1, pos1);
                    }
                } else {
                    source = externalPost.getDevice();
                }
                postIn.setPostSource(source);
            }
            //System.out.println("Ya en Msg source:"+source);

            if (externalPost.getCreatorId() != null) {
                if (externalPost.getPlace() != null) {
                    postIn.setPostPlace(externalPost.getPlace());
                }/*else if(externalPost.getLocation()!=null)
                 {
                 postIn.setPostPlace(externalPost.getLocation());
                 }*/

                //Sets the latitude, longitud and possibly the state the message is comming from
                //System.out.println("Checa latitude y longitud del mensaje recibido-Latitude:"+externalPost.getLatitude()+",longitude:"+externalPost.getLongitude());
                if (externalPost.getLatitude() != 0 && externalPost.getLongitude() != 0) {
                    postIn.setLatitude(Float.parseFloat("" + externalPost.getLatitude()));
                    postIn.setLongitude(Float.parseFloat("" + externalPost.getLongitude()));

                    //The next code calculates if latitude and longitud belongs to a country and next to state
                    Country country = null;
                    if (externalPost.getCountryCode() != null) {
                        country = Country.ClassMgr.getCountry(externalPost.getCountryCode().toUpperCase(), SWBContext.getAdminWebSite());
                    } else {
                        country = SWBSocialUtil.Util.getMessageMapCountry(postIn.getLatitude(), postIn.getLongitude());
                    }
                    //System.out.println("country en el que encuentra el mensaje:"+country);
                    if (country != null) {
                        //Busca en que estado del país encontrado se encuentra el mensaje.
                        CountryState countryState = SWBSocialUtil.Util.getMessageMapState(country, postIn.getLatitude(), postIn.getLongitude());
                        //System.out.println("estado en el que encuentra el mensaje:"+countryState);
                        if (countryState != null) {
                            postIn.setGeoStateMap(countryState);
                        }
                    }
                }

                if (externalPost.getPostShared() > 0) {
                    postIn.setPostShared(externalPost.getPostShared());
                }


                if (socialNetUser == null) //Si el usuario no existe en la red social, Twitter, Faebook, etc, entonces crealo
                {
                    //Si no existe el id del usuario para esa red social, lo crea.

                    socialNetUser = SocialNetworkUser.ClassMgr.createSocialNetworkUser(model);
                    socialNetUser.setSnu_id(externalPost.getCreatorId());
                    socialNetUser.setSnu_name(externalPost.getCreatorName());
                    if (externalPost.getCreatorPhotoUrl() != null) {
                        socialNetUser.setSnu_photoUrl(externalPost.getCreatorPhotoUrl());
                    }
                    socialNetUser.setSnu_SocialNetworkObj(socialNetwork.getSemanticObject().getSemanticClass().getSemanticObject());
                    socialNetUser.setCreated(externalPost.getUsercreation());
                    socialNetUser.setSnu_klout(userKloutScore);

                    if (externalPost.getFollowers() > 0 && externalPost.getFriendsNumber() > 0) {
                        //System.out.println("SocialNet em Sentimental JAJS-1:"+socialNetwork);
                        socialNetUser.setFollowers(externalPost.getFollowers());
                        socialNetUser.setFriends(externalPost.getFriendsNumber());
                    } else {
                        getUserData(socialNetUser, days, true);
                    }
                } else if (upDateSocialUserNetworkData) {
                    socialNetUser.setSnu_SocialNetworkObj(socialNetwork.getSemanticObject().getSemanticClass().getSemanticObject());
                    socialNetUser.setSnu_klout(userKloutScore);
                    if (externalPost.getCreatorPhotoUrl() != null) {
                        socialNetUser.setSnu_photoUrl(externalPost.getCreatorPhotoUrl());
                    }

                    if (externalPost.getFollowers() > 0 && externalPost.getFriendsNumber() > 0) {
                        //System.out.println("SocialNet em Sentimental JAJS-1:"+socialNetwork);
                        socialNetUser.setFollowers(externalPost.getFollowers());
                        socialNetUser.setFriends(externalPost.getFriendsNumber());
                    } else {
                        getUserData(socialNetUser, days, false);
                    }

                    socialNetUser.setUpdated(new Date());
                }

                //System.out.println("Poner en UserGe-0");
                if (socialNetUser != null) {
                    postIn.setPostInSocialNetworkUser(socialNetUser);
                    //System.out.println("Poner en UserGe-1");
                    if (externalPost.getUserGeoLocation() != null) {
                        //System.out.println("Poner en UserGeo:"+externalPost.getUserGeoLocation());
                        socialNetUser.setSnu_profileGeoLocation(externalPost.getUserGeoLocation());
                    }
                }
            }

            /*Código test para barrer las instamcias de la clase SocialNetworkUser
             Iterator<SocialNetworkUser> itsnusers=SocialNetworkUser.ClassMgr.listSocialNetworkUsers(model);
             while(itsnusers.hasNext())
             {
             SocialNetworkUser socialNetUserTmp=itsnusers.next();
             System.out.println("socialNetUserTmp ID:"+socialNetUserTmp.getId());
             System.out.println("socialNetUserTmp Name:"+socialNetUserTmp.getSnu_name());
             System.out.println("socialNetUserTmp Followers:"+socialNetUserTmp.getFollowers());
             System.out.println("socialNetUserTmp Friends:"+socialNetUserTmp.getFriends());
             System.out.println("socialNetUserTmp Created:"+socialNetUserTmp.getCreated());
             System.out.println("socialNetUserTmp Updated:"+socialNetUserTmp.getCreated());
             }
             **/

            //Línea para persistir por año y mes el mensaje recibido por el listener, esto para realizar busquedas
            //eficientes de todos los mensajes recibidos por las redes sociales.
            //Por el momento, lo comento, para que no se llene tanto la BD, sin embargo, debo descomentarlo para producción.
            //socialNetwork.addReceivedPost(message, String.valueOf(externalPost.getPostId()), socialNetwork);
        } catch (Exception e) {
            log.error(e);
        }

        return postIn;
    }

    /*
     * Method which review the user information in a specific SocialNetWork and update his(her)
     * information (data) if it has passed more than 5 days since last time that user was updated
     * @param socialNetUser User that will be reviewed in a specific SocialNetWork
     * @param days Days that have passed since the last days the user was updated in the system
     * 
     */
    private void getUserData(SocialNetworkUser socialNetUser, int days, boolean isNewUser) {
        try {
            //Este 5 si lo pongo estatico, normalmente un usuario no aumenta tan rapido sus amigos o seguidores
            //Despues ver si lo hacemos dinamico, como para revisión de Klout
            //Si es usuario nuevo o si No es usuario nuevo pero han pasado + de 5 días de su ultima actualización, entonces se envía a buscar los datos del usuario a la red social 
            if (isNewUser || days > 5) {
                Listenerable listenerAble = (Listenerable) socialNetwork;
                JSONObject userData = listenerAble.getUserInfobyId(socialNetUser.getSnu_id());
                if (userData != null && !userData.toString().equals("{}")) {
                    //Followers
                    if (!userData.isNull("followers")) {
                        long followers = userData.getLong("followers") > 0 ? userData.getLong("followers") : 0;
                        socialNetUser.setFollowers(((Long) followers).intValue());
                    }
                    //Friends
                    if (!userData.isNull("friends")) {
                        long friends = userData.getLong("friends") > 0 ? userData.getLong("friends") : 0;
                        socialNetUser.setFriends(((Long) friends).intValue());
                    }
                    //User profile geoLocation
                    if (!userData.isNull("place_name")) {
                        String profileGeoLoc = userData.getString("place_name") != null ? userData.getString("place_name") : null;
                        if (profileGeoLoc != null && profileGeoLoc.trim().length() > 0) {
                            socialNetUser.setSnu_profileGeoLocation(profileGeoLoc);
                        }
                    }
                    //User email
                    if (!userData.isNull("email")) {
                        String userEmail = userData.getString("email") != null ? userData.getString("email") : null;
                        if (userEmail != null && userEmail.trim().length() > 0) {
                            socialNetUser.setSnu_email(userEmail);
                        }
                    }
                    //User birthday
                    if (!userData.isNull("birthday")) {
                        String userBirthDay = userData.getString("birthday") != null ? userData.getString("birthday") : null;
                        if (userBirthDay != null && userBirthDay.trim().length() > 0) {
                            System.out.println("userBirthDay:" + userBirthDay);
                            //Date date=new SimpleDateFormat("MM/dd/yyyy").parse(userBirthDay);
                            final DateFormat date = new SimpleDateFormat("MM/dd/yyyy");
                            final Calendar c = Calendar.getInstance();
                            c.setTime(date.parse(userBirthDay));
                            if (c.get(Calendar.YEAR) > 1900) {
                                int userYears = SWBSocialUtil.Util.calculateAge(c.getTime());
                                System.out.println("userYears:" + userYears);
                                if (userYears > 0) {
                                    Iterator<LifeStage> itLifeStage = LifeStage.ClassMgr.listLifeStages(SWBContext.getAdminWebSite());
                                    while (itLifeStage.hasNext()) {
                                        LifeStage lifeStage = itLifeStage.next();
                                        if (userYears >= lifeStage.getLs_minAge() && userYears <= lifeStage.getLs_maxAge()) {
                                            socialNetUser.setSnu_LifeStage(lifeStage);
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    //Gender
                    System.out.println("userData.getString(\"gender\")" + userData.getString("gender"));
                    if (!userData.isNull("gender")) {
                        String userGender = userData.getString("gender") != null ? userData.getString("gender") : null;
                        if (userGender != null && userGender.trim().length() > 0) {
                            System.out.println("userGender George:" + userGender);
                            if (userGender.equals("male")) {
                                socialNetUser.setSnu_gender(SocialNetworkUser.USER_GENDER_MALE);
                            } else if (userGender.equals("female")) {
                                socialNetUser.setSnu_gender(SocialNetworkUser.USER_GENDER_FEMALE);
                            } else {
                                socialNetUser.setSnu_gender(SocialNetworkUser.USER_GENDER_UNDEFINED);
                            }
                        } else {
                            socialNetUser.setSnu_gender(SocialNetworkUser.USER_GENDER_UNDEFINED);
                        }
                    } else {
                        socialNetUser.setSnu_gender(SocialNetworkUser.USER_GENDER_UNDEFINED);
                    }
                    //RelationShio Status
                    if (!userData.isNull("relationship_status")) {
                        String userRelationShip = userData.getString("relationship_status") != null ? userData.getString("relationship_status") : null;
                        if (userRelationShip != null && userRelationShip.trim().length() > 0) {
                            System.out.println("relationship_status George:" + userRelationShip);
                            if (userRelationShip.equals("Single")) {
                                socialNetUser.setSnu_relationShipStatus(SocialNetworkUser.USER_RELATION_SINGLE);
                            } else if (userRelationShip.equals("Married")) {
                                socialNetUser.setSnu_relationShipStatus(SocialNetworkUser.USER_RELATION_MARRIED);
                            } else if (userRelationShip.equals("Divorced")) {
                                socialNetUser.setSnu_relationShipStatus(SocialNetworkUser.USER_RELATION_DIVORCED);
                            } else if (userRelationShip.equals("Widowed")) {
                                socialNetUser.setSnu_relationShipStatus(SocialNetworkUser.USER_RELATION_WIDOWED);
                            } else {
                                socialNetUser.setSnu_relationShipStatus(SocialNetworkUser.USER_RELATION_UNDEFINED); //Could be: In a relationship, Engaged, It's complicated, In an open relationship,Separated,  In a civil union, In a domestic partnership, etc
                            }
                        } else {
                            socialNetUser.setSnu_relationShipStatus(SocialNetworkUser.USER_RELATION_UNDEFINED); //Could be: In a relationship, Engaged, It's complicated, In an open relationship,Separated,  In a civil union, In a domestic partnership, etc
                        }
                    } else {
                        socialNetUser.setSnu_relationShipStatus(SocialNetworkUser.USER_RELATION_UNDEFINED); //Could be: In a relationship, Engaged, It's complicated, In an open relationship,Separated,  In a civil union, In a domestic partnership, etc
                    }                    //User Education
                    String school = "";
                    if (!userData.isNull("education")) {
                        JSONArray jArrayEduacation = userData.getJSONArray("education");
                        for (int i = 0; i < jArrayEduacation.length(); i++) {
                            JSONObject jsonObj = jArrayEduacation.getJSONObject(i);
                            if (jsonObj.getString("type") != null) {
                                String schoolType = jsonObj.getString("type");
                                if (school == null && schoolType.equals("High School")) //Secundaria
                                {
                                    school = schoolType;
                                } else if (schoolType.equals("College")) {    //Universidad
                                    school = schoolType;
                                } else if (schoolType.equals("Graduate School")) {    //PostGrado
                                    school = schoolType;
                                }
                            }
                        }
                        if (school.equals("High School")) {
                            socialNetUser.setSnu_education(SocialNetworkUser.USER_EDUCATION_HIGHSCHOOL);
                        } else if (school.equals("College")) {
                            socialNetUser.setSnu_education(SocialNetworkUser.USER_EDUCATION_COLLEGE);
                        } else if (school.equals("Graduate School")) {
                            socialNetUser.setSnu_education(SocialNetworkUser.USER_EDUCATION_GRADUATE);
                        } else {
                            socialNetUser.setSnu_education(SocialNetworkUser.USER_EDUCATION_UNDEFINED);
                        }
                    } else {
                        socialNetUser.setSnu_education(SocialNetworkUser.USER_EDUCATION_UNDEFINED);
                    }
                    System.out.println("User Followers:" + socialNetUser.getFollowers());
                    System.out.println("User Friends:" + socialNetUser.getFriends());
                    System.out.println("User ProfileGeo:" + socialNetUser.getSnu_profileGeoLocation());
                    System.out.println("User Email:" + socialNetUser.getSnu_email());
                    /*
                     if(socialNetUser.getSnu_LifeStage()!=null)
                     {
                     System.out.println("User LifeStage:"+socialNetUser.getSnu_LifeStage().getId());
                     }*/
                    System.out.println("User Gender:" + socialNetUser.getSnu_gender());
                    System.out.println("User RelationShip:" + socialNetUser.getSnu_relationShipStatus());
                    System.out.println("User Education:" + socialNetUser.getSnu_education());

                } else {
                    socialNetUser.setSnu_gender(SocialNetworkUser.USER_GENDER_UNDEFINED);
                    socialNetUser.setSnu_relationShipStatus(SocialNetworkUser.USER_RELATION_UNDEFINED); //Could be: In a relationship, Engaged, It's complicated, In an open relationship,Separated,  In a civil union, In a domestic partnership, etc
                    socialNetUser.setSnu_education(SocialNetworkUser.USER_EDUCATION_UNDEFINED);
                }
            }
        } catch (Exception e) {
            log.error(e);
        }
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
    /*
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
     //System.out.println("sntLPhrase:"+sntLPhrase.getPhrase()+",contOcurrJorge:"+contOcurr);
     if(contOcurr>0)
     {
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
     * */

    /*
     * Función que encuentra el número de ocurrencias en una frase
     */
    /*
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
     */
    /*
     * Encuentra emoticons en el mensaje
     * TODO:Hacer que los emoticons esten almacenados en un objeto.
     */
    private void findEmoticones(PostIn post, String text) {
        //System.out.println("Emoti-1:"+externalString2Clasify_TMP);
        int contPositiveEmoticon = 0;
        int contNegativeEmoticon = 0;
        StringTokenizer st = new StringTokenizer(text);
        while (st.hasMoreTokens()) {
            String word2Find = st.nextToken();
            if (word2Find.indexOf("=)") > -1 || word2Find.indexOf(":)") > -1 || word2Find.indexOf(":))") > -1 || word2Find.indexOf("(:") > -1 || word2Find.indexOf(":-)") > -1 || word2Find.indexOf("(-:") > -1 || word2Find.indexOf("<3") > -1) //Sentimiento Positivo
            {
                //System.out.println("Emoti-2:"+word2Find);
                contPositiveEmoticon++;
            } else if (word2Find.indexOf("=(") > -1 || word2Find.indexOf(":(") > -1 || word2Find.indexOf(":((") > -1 || word2Find.indexOf("):") > -1 || word2Find.indexOf(":-(") > -1 || word2Find.indexOf(")-:") > -1) //Sentimineto negativo
            {
                //System.out.println("Emoti-3:"+word2Find);
                contNegativeEmoticon++;
            }
        }
        if (contPositiveEmoticon > contNegativeEmoticon) {
            //System.out.println("Emoti-4:se puso-Positivo");
            post.setPostSentimentalEmoticonType(1);
        } else if (contPositiveEmoticon < contNegativeEmoticon) {
            //System.out.println("Emoti-4:se puso-Negativo");
            post.setPostSentimentalEmoticonType(2);
        }
    }

    //**************** METODOS PARA PROBAR EL CLASIFICADOR **********************
    public static void main(String args[]) {
        MessageIn msgIn = null;
        //MessageIn msgIn=MessageIn.ClassMgr.createMessageIn("1234567890", WebSite.ClassMgr.getWebSite("Infotc"));
        //msgIn.setMsg_Text("#Peleaporelsegundo lugar en la persona más PENDEJA entre Peña Nieto y Ninel Conde, dicen que Peña Nieto tiene la victoria asegurada.");
        //new SentimentalDataClassifier(msgIn, msgIn.getMsg_Text());
        //new SentimentalDataClassifier(msgIn, "#Peleaporelsegundo! lugar... en la persona! más PENDEJA? entre Peña Nieto y Ninel Conde!, dicen que Peña Nieto tiene la victoria asegurada!!.");
    }

    private void removePuntualSigns1() {
        ArrayList alist = new ArrayList();
        alist.add("!");
        alist.add("\\?");
        alist.add("\\.");
        alist.add(",");
        Iterator<String> itSigns = alist.iterator();
        while (itSigns.hasNext()) {
            String sign = itSigns.next();
            externalString2Clasify = externalString2Clasify.replaceAll(sign, "");
        }
    }

    private ArrayList removeprepositions1(ArrayList aPostDataWords) {
        ArrayList alist = new ArrayList();
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

        Iterator<String> itPreps = alist.iterator();
        while (itPreps.hasNext()) {
            String sprep = itPreps.next();
            //removeWordFromArray(aPostDataWords, sprep);
        }
        return aPostDataWords;
    }
    ////////////////////////////TEST/////////////
    /*
     * Metodo cuya función es la de analizar la información de cada mensaje y determinar el sentimiento del mismo,
     * así como la intensidad, eso en este momento, talvez se requiera realizar mas clasificaciones posteriormente.
     * Funciona bien al 15/06/2012
     */
    /*
     private void initAnalysiss()
     {
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
        
     MessageIn post=(MessageIn)createPostInObj(SocialNetworkUser.ClassMgr.createSocialNetworkUser(model));
        
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
     * */
}
