<%-- 
    Document   : tagCloud
    Created on : 14/06/2013, 09:53:02 AM
    Author     : francisco.jimenez
--%>
<%@page import="java.util.Random"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Collections"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="java.util.Comparator"%>
<%@page import="org.semanticwb.social.SocialAdmin"%>
<%@page import="org.semanticwb.model.SWBContext"%>
<%@page import="java.util.TreeMap"%>
<%@page import="java.util.Map"%>
<%@page import="org.semanticwb.social.util.SWBSocialUtil"%>
<%@page import="java.util.Arrays"%>
<%@page import="org.semanticwb.social.PostIn"%>
<%@page import="org.semanticwb.model.SWBComparator"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.social.Stream"%>
<%@page contentType="text/html" pageEncoding="x-iso-8859-11"%>

<%!

    public static boolean isInteger(String s) {
        try { 
            Integer.parseInt(s); 
        } catch(NumberFormatException e) { 
            return false; 
        }
        return true;
    }
    
    public static <String, Integer extends Comparable<Integer>> Map<String, Integer> sortByValues(final Map<String, Integer> map) {
        Comparator<String> valueComparator =  new Comparator<String>() {
            public int compare(String k1, String k2) {
                int compare = map.get(k2).compareTo(map.get(k1));
                if (compare == 0)
                    return 1;
                else
                    return compare;
            }
        };
        
        Map<String, Integer> sortedByValues = new TreeMap<String, Integer>(valueComparator);
        sortedByValues.putAll(map);
        return sortedByValues;
    }
    
    public static int randomId(){
        Random rand = new Random();;
        int min=1 , max=Integer.MAX_VALUE;
        return rand.nextInt(max - min + 1) + min;
    }
%>

<%
    System.out.println("suri in tagsCloud:" + request.getParameter("suri"));
    String suri = request.getParameter("suri");
    String noTopic = request.getParameter("noTopic");
    Stream stream=null;
    
    if(suri != null) {
         stream = (Stream)SemanticObject.getSemanticObject(suri).getGenericInstance();   
    }else{
        return;
    }
    
    SWBResourceURL renderURL = paramRequest.getRenderUrl().setMode(SWBResourceURL.Mode_EDIT);
    renderURL.setParameter("sID", stream.getId());
    renderURL.setParameter("wsite", stream.getSemanticObject().getModel().getName());
    renderURL.setParameter("dialog", "close");
    renderURL.setParameter("suri", suri);
    renderURL.setParameter("reloadTap", "true");

    Map<String, Integer> cloudTags = new TreeMap<String,Integer>();//Tree to save the words

    SocialAdmin socialAdminSite=(SocialAdmin)SWBContext.getAdminWebSite();    
    
    Iterator <PostIn> itPostIns=stream.listPostInStreamInvs();//The posts
    itPostIns = SWBComparator.sortByCreatedSet(itPostIns, false).iterator();
    
    long noOfPosts = SWBUtils.Collections.sizeOf(stream.listPostInStreamInvs());//Number of recovered posts
        
    int posts = 0;
    int randomId = randomId();
    long startTime = System.currentTimeMillis();

    int percent = (int)(noOfPosts*0.6);//Only process 60% of stream
    //System.out.println("Size of sample: " + percent);
    while(itPostIns.hasNext()){                
        if(cloudTags.size() >= 4000){
            System.out.println("Too large Word List");
            break;        
        }else if( posts > percent){
            System.out.println("Has reached the %");
            break;
        }
        PostIn postIn=itPostIns.next();
        
        //Condition to show only post without Social Topics
        if(noTopic != null){
            if(postIn.getSocialTopic() != null)//Don't process posts with Social topics
                continue;
        }
        
        if(postIn.getTags() != null){//If post has tags
            String[] tags=postIn.getTags().split("\\s+");  //Dividir valores
            System.out.println("postInJ-tags:"+tags); 
            for(int i=0;i<tags.length;i++)
            {
               String word=tags[i]; 
               if(!Arrays.asList(SWBSocialUtil.Classifier.getSpanishStopWords()).contains(word))
               {
                    if(!isInteger(word))
                    {
                        Integer frequency = cloudTags.get(word);
                        if(frequency == null){//New word
                            cloudTags.put(word, 1);
                        }else if(frequency > 0){//Repeated word
                            cloudTags.put(word, ++frequency);
                        }
                    }
               }
            }
        }
        
        if(postIn.getMsg_Text()!=null){//If post has message
            String msg=postIn.getMsg_Text();
            //TODO:ver si mejor desde que se clasifica el mensaje, se guarda en alguna nueva propiedad
            //el mensaje normalizado, replaceSpecialCharacters y removePuntualSigns.
            //msg=SWBSocialUtil.Classifier.normalizer(msg).getNormalizedPhrase();
            msg=SWBSocialUtil.Strings.replaceSpecialCharacters(msg);
            msg=SWBSocialUtil.Strings.removePuntualSigns(msg, socialAdminSite);
            
            String[] mgsWords=msg.split("\\s+");  //Dividir valores
            for(int i=0;i<mgsWords.length;i++)
            {
               String word=mgsWords[i];
               if(!Arrays.asList(SWBSocialUtil.Classifier.getSpanishStopWords()).contains(word))
               {
                    if(!isInteger(word))
                    {
                        Integer frequency = cloudTags.get(word);
                        if(frequency == null){//New word
                            cloudTags.put(word, 1);
                        }else if(frequency > 0){//Repeated word
                            cloudTags.put(word, ++frequency);
                        }
                    }
               }
            }
        }
        posts++;
    }
    
    //System.out.println("POSTS READ:"+ posts);
%>
<div id="<%=suri%>/<%=randomId%>/CanvasContainer" align="center">
	<canvas width="500" height="500" id="<%=suri%>/<%=randomId%>/Canvas">
	</canvas>
</div>

<div hidden="true">
    <ul id="tags<%=randomId%>">
<%
try{
        Map<String, Integer> cloudTagsSorted = sortByValues(cloudTags);//Descending order
        Iterator itSorted = cloudTagsSorted.entrySet().iterator();
        int i=1;
        int mappingValue=100; //The most frequent word will have 100 as its weight
        Integer lastValue = 0;
        
        //System.out.println("Size of sorted:" + cloudTagsSorted.size());
        while(itSorted.hasNext()){            
            Map.Entry tag = (Map.Entry)itSorted.next();//entry
            //System.out.println(i + " : " + tag);

            if(i==1){//The word at the top is the most frequent, so it must have 100
                //submitUrlreportContainer
                //out.println("<li><a href=\"" +renderURL.setParameter("word", (String)tag.getKey()) + "\" title=\"" + mappingValue + "\" data-weight=\"" + mappingValue +"\">" + tag.getKey() + "</a></li>");
                //out.println("<li><a href=\"#\" title=\"" + mappingValue + "\" data-weight=\"" + mappingValue +"\" onclick=\"postHtml('" + renderURL.setParameter("search", (String)tag.getKey()) + "','" + suri + "/reportContainer'); return false;\">" + tag.getKey() + "</a></li>");
                //out.println("<form id=\"id" + i + "\" name=\"id" + i + "\" method=\"post\" action=\"" + renderURL.setParameter("search", (String)tag.getKey()) + "\" onsubmit=\"submitForm('id" + i + "'); return false;\"></form>");
                //out.println("<li><a href=\"#\" title=\"" + mappingValue + "\" data-weight=\"" + mappingValue +"\" onclick=\"submitUrl('" + renderURL.setParameter("search", (String)tag.getKey()) + "',this); return false;\">" + tag.getKey() + "</a></li>");
                
                //out.println("<li><a href=\"#\" title=\"" + mappingValue + "\" data-weight=\"" + mappingValue +"\" onclick=\"document.getElementById('" +  suri +"_searchwp').value='chica'; document.getElementById('" +suri + "/fsearchwp').submit(); return false;\">" + tag.getKey() + "</a></li>");
                
                out.println("<li><a href=\"#\" title=\"" + mappingValue + "\" data-weight=\"" + mappingValue +"\" onclick=\"submitUrl('" + renderURL.setParameter("search", (String)tag.getKey()) + "',this); return false;\">" + tag.getKey() + "</a></li>");
            }else{
                if(lastValue.equals(tag.getValue())){//If several words have the same frequency, use the same mappingValue
                    //out.println("<li><a href=\"" +renderURL.setParameter("word", (String)tag.getKey()) + "\" title=\"" + mappingValue + "\" data-weight=\"" + mappingValue +"\">" + tag.getKey() + "</a></li>");
                    //out.println("<li><a href=\"#\" title=\"" + mappingValue + "\" data-weight=\"" + mappingValue +"\" onclick=\"submitUrl('" + renderURL.setParameter("search", (String)tag.getKey()) + "',this); return false;\">" + tag.getKey() + "</a></li>");
                    out.println("<li><a href=\"#\" title=\"" + mappingValue + "\" data-weight=\"" + mappingValue +"\" onclick=\"submitUrl('" + renderURL.setParameter("search", (String)tag.getKey()) + "',this); return false;\">" + tag.getKey() + "</a></li>");
                }else{//If current word is different from the word before, use a lower value
                    mappingValue--;
                    //out.println("<li><a href=\"" +renderURL.setParameter("word", (String)tag.getKey()) + "\" title=\"" +  mappingValue + "\" data-weight=\"" + mappingValue +"\">" + tag.getKey() + "</a></li>");
                    //out.println("<li><a href=\"#\" title=\"" + mappingValue + "\" data-weight=\"" + mappingValue +"\" onclick=\"submitUrl('" + renderURL.setParameter("search", (String)tag.getKey()) + "',this); return false;\">" + tag.getKey() + "</a></li>");
                    out.println("<li><a href=\"#\" title=\"" + mappingValue + "\" data-weight=\"" + mappingValue +"\" onclick=\"submitUrl('" + renderURL.setParameter("search", (String)tag.getKey()) + "',this); return false;\">" + tag.getKey() + "</a></li>");
                }
            }
            lastValue = (Integer)tag.getValue();
            
            if(i++==50){//show only the 50 most frequent words
                break;
            }
        }
    }catch(Exception e){
        System.out.println("Error normalizing the frequencies :" + e);
    }
    
    long estimatedTime = System.currentTimeMillis() - startTime;    
    System.out.println("Elapsed time of processing:"  +  estimatedTime);
%>
    </ul>
</div>

<!--
<div><input type="button" value="Pausar" onclick="TagCanvas.Pause('<%//=suri%>/myCanvas');"/>  
     <input type="button" value="Continuar" onclick="TagCanvas.Resume('<%//=suri%>/myCanvas');"/>
     <input type="button" value="Reload" onclick="TagCanvas.Resume('<%//=suri%>/myCanvas');"/>
</div>
-->
<div dojoType="dojox.layout.ContentPane">
    <script type="dojo/method">
        
        try{
            TagCanvas.interval = 20;
            TagCanvas.textFont = 'Impact,Arial Black,sans-serif';
            //TagCanvas.textColour = '#00f';
            //TagCanvas.textColour = null;
            TagCanvas.textHeight = 30;
            TagCanvas.weightSizeMin=15;
            TagCanvas.weightSizeMax=40;
            TagCanvas.outlineColour = '#f00';
            TagCanvas.outlineThickness = 5;
            TagCanvas.maxSpeed = 0.04;
            TagCanvas.minBrightness = 0.1;
            TagCanvas.depth = 0.92;
            TagCanvas.pulsateTo = 0.2;
            TagCanvas.pulsateTime = 0.75;
            TagCanvas.initial = [0.1,-0.1];
            TagCanvas.decel = 0.98;
            TagCanvas.reverse = true;
            TagCanvas.hideTags = false;
            TagCanvas.shadow = '#ccf';
            TagCanvas.shadowBlur = 1;
            TagCanvas.weight = true;
            TagCanvas.weightFrom = 'data-weight';
            TagCanvas.tooltip ='native';
            var gradient = { 0: '#FF0000', // red 
                             0.25: '#FF00FF', // fucsia
                             0.50: '#FFA500', //  orange
                             0.75: '#FFFF00', // yellow
                             1: '#0000FF' // blue
   			   };
            TagCanvas.weightGradient = gradient;
            
            TagCanvas.Start('<%=suri%>/<%=randomId%>/Canvas', 'tags<%=randomId%>', {weightMode:'both'});
	}catch(e){
            document.getElementById('<%=suri%>/<%=randomId%>/CanvasContainer').style.display ='none';
	}
   </script>
</div>