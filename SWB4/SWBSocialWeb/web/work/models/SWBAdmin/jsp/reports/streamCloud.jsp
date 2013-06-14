<%-- 
    Document   : cloud
    Created on : 11-jun-2013, 11:52:53
    Author     : jorge.jimenez
--%>
<%@page import="org.semanticwb.social.util.SWBSocialUtil"%>
<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.social.*"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.platform.SemanticProperty"%>
<%@page import="org.semanticwb.portal.api.*"%>
<%@page import="org.semanticwb.*"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="java.util.*"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>

<meta charset="utf-8">
<script src="http://d3js.org/d3.v3.min.js" charset="utf-8"></script>
<script type="text/javascript" charset="utf-8" src="/work/models/SWBAdmin/css/js/d3.layout.cloud.js" ></script>

<body>
    
<%
    //http://stackoverflow.com/questions/516565/how-to-transfer-java-array-to-javascript-array-using-jsp

    //System.out.println("Entra Jsp...StreamCloud-1:"+request.getParameter("suri")+",word:"+request.getParameter("word")); 
    String uri = request.getParameter("suri");
    if(uri==null) return;
    
    //System.out.println("Stream-id/streamCloudJsp:"+uri);

    Stream stream = (Stream)SemanticObject.getSemanticObject(uri).getGenericInstance();    
    
    SWBResourceURL url=paramRequest.getRenderUrl();  
    //url.setParameter("suri", id);
    
    SocialAdmin socialAdminSite=(SocialAdmin)SWBContext.getAdminWebSite();
    
    HashMap<String, Integer> hmWords=new HashMap(); 
    Iterator <PostIn> itPostIns=stream.listPostInStreamInvs();
    while(itPostIns.hasNext()) 
    {
        if(hmWords.size()>=50) break; 
        PostIn postIn=itPostIns.next();
        //System.out.println("postInJ-1:"+postIn); 
        if(postIn.getTags()!=null)
        {
            String[] tags=postIn.getTags().split("\\ ");  //Dividir valores
            //System.out.println("postInJ-tags:"+tags); 
            for(int i=0;i<tags.length;i++)
            {
                String word=tags[i]; 
                if(hmWords.containsKey(word))
                {
                    int wordTimes=hmWords.get(word); 
                    wordTimes=wordTimes+1; 
                    hmWords.remove(word);
                    hmWords.put(word, wordTimes);
                }else{
                    hmWords.put(word, 1);
                }
            }
        }
        if(postIn.getMsg_Text()!=null)
        {
            String msg=postIn.getMsg_Text();
            //System.out.println("postInJ-getMsg_Text-1:"+msg); 
            //TODO:ver si mejor desde que se clasifica el mensaje, se guarda en alguna nueva propiedad
            //el mensaje normalizado, replaceSpecialCharacters y removePuntualSigns.
            msg=SWBSocialUtil.Classifier.normalizer(msg).getNormalizedPhrase();
            msg=SWBSocialUtil.Strings.replaceSpecialCharacters(msg);
            msg=SWBSocialUtil.Strings.removePuntualSigns(msg, socialAdminSite);
        
            //System.out.println("postInJ-getMsg_Text-2:"+msg); 
            String[] mgsWords=msg.split("\\ ");  //Dividir valores
            for(int i=0;i<mgsWords.length;i++)
            {
               String word=mgsWords[i]; 
               if(hmWords.containsKey(word))
               {
                    //System.out.println("postInJ-getMsg_Text-4:"+word); 
                    int wordTimes=hmWords.get(word); 
                    wordTimes=wordTimes+1; 
                    hmWords.remove(word);
                    hmWords.put(word, wordTimes);
               }else{
                    //System.out.println("postInJ-getMsg_TextGeorge-3:"+word); 
                    hmWords.put(word, 1);
               }
            }
        }
    }
%>   
<script>
    var jWord=new Array();
    var jCount = new Array();
<%
    int i=0; 
    Iterator<String> itWords=hmWords.keySet().iterator();
    while(itWords.hasNext())
    {
       String word=itWords.next();
       if(word!=null && word.trim().length()>0)
       {
            int nTimes=hmWords.get(word);
            //out.println("postInJ-itWords:"+word); 
             %>
                 jWord[<%=i%>]="<%=word%>";
                 jCount[<%=i%>]="<%=nTimes%>";
             <%
             i++;
       }
    }
%> 
    
 //alert("jWord:"+jWord);


 var fill = d3.scale.category10(); 
  
  //var jWord = ["abc","def","ghi","jkl"];
  //var jCount = [2, 5, 3, 8];
   
  var maxCount = d3.max(jCount);
  var s = d3.scale.linear().range([10,50]).domain([0, maxCount]); 
  
  //var wordScale=d3.scale.linear().domain([1,100,1000,10000]).range([10,20,40,80]).clamp(true);
  //var wordColor=d3.scale.linear().domain([10,20,40,80]).range(["blue","green","orange","red"]);

  d3.layout.cloud().size([400, 400])
      .words(d3.zip(jWord, jCount).map(function(d) {
            return {text: d[0], size: s(d[1]) };
       }))
      .rotate(function() { return 0; })
      //.rotate(function(d) { return ~~(Math.random() * 5) * 30 - 60; })
      //.rotate(function() { return ~~(Math.random() * 2) * 5; })
      //.rotate(function() { return ~~(Math.random() * 2) * 90; })
      //.padding(1)
      //.font("Impact")
      .fontSize(function(d) { return d.size; })
      .on("end", draw)
      .start();
     

  function draw(words) {
    d3.select("#sca svg").append("svg")
        .attr("width", 400)
        .attr("height", 400)
      .append("g")
        .attr("transform", "translate(150,150)")
      .selectAll("text")
        .data(words)
        .enter().append("a").attr("xlink:href", function(d) { return "<%=url.toString()%>?word=" + d.text+"&suri=<%=stream.getSemanticObject().getEncodedURI()%>"})   
      .append("text")
        .style("font-size", function(d) { return d.size + "px"; })
        .style("font-family", "Impact")
        .style("fill", function(d, i) { return fill(i); })
        .attr("text-anchor", "middle")
        .attr("transform", function(d) {
          return "translate(" + [d.x, d.y] + ")rotate(" + d.rotate + ")";
        })
        .text(function(d) { return d.text; });
  }
</script>


</body>
