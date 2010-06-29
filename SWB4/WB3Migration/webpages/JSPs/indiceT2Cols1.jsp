<jsp:useBean id="paramRequest" scope="request" type="com.infotec.wb.lib.WBParamRequest"/>
<%
	com.infotec.topicmaps.Topic topic=paramRequest.getTopic();
	com.infotec.topicmaps.Topic tpl=topic.getMap().getTopicLang(paramRequest.getUser().getLanguage());
	java.util.Iterator it=topic.getSortChild(tpl);
%>

<table width="500" border="0" cellspacing="0" cellpadding="0"><%
	while(it.hasNext())
	{
		com.infotec.topicmaps.Topic tp=(com.infotec.topicmaps.Topic)it.next();
%>
  <tr>
    <td width="250" valign="top"><a href="<%=tp.getUrl()%>" class="tematico"><%=tp.getDisplayName(tpl)%></a></td><%
		if(it.hasNext())
		{
			tp=(com.infotec.topicmaps.Topic)it.next();
%>
    <td width="250" valign="top"><a href="<%=tp.getUrl()%>" class="tematico"><%=tp.getDisplayName(tpl)%></a></td><%
		}else
		{
%>
    <td width="250" valign="top"></td><%
		}
%>
  </tr><%		
	}
%>
</table>
