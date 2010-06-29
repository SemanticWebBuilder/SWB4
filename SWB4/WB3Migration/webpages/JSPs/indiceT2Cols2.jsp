<jsp:useBean id="paramRequest" scope="request" type="com.infotec.wb.lib.WBParamRequest"/>
<%
	com.infotec.topicmaps.Topic topic=paramRequest.getTopic();
	com.infotec.topicmaps.Topic tpl=topic.getMap().getTopicLang(paramRequest.getUser().getLanguage());
	java.util.Iterator it=topic.getSortChild(tpl);
	int i=0;	
	int j=0;
	java.util.ArrayList ard=new java.util.ArrayList();
	java.util.ArrayList ars=new java.util.ArrayList();
	//separar los que tienen y no tienen descripcion
	while(it.hasNext())
	{
	  	com.infotec.topicmaps.Topic tp=(com.infotec.topicmaps.Topic)it.next();
		i++;
                String desc=tp.getDescription(tpl);
		if(!(desc==null || desc.trim().length()==0))
                {
                    ard.add(tp);
                    j++;
                }else
		{
		    ars.add(tp);
		}
	}
%>

              <table width="100%" border="0" cellspacing="0" cellpadding="0" >
<%
	it=ard.iterator();
	while(it.hasNext())
	{
	  	com.infotec.topicmaps.Topic tp=(com.infotec.topicmaps.Topic)it.next();
                String desc=tp.getDescription(tpl);
%>
                <tr >
                  <td width="245" align="left" valign="top" ><a href="<%=tp.getUrl()%>" class="Tit_cuadrantes"><%=tp.getDisplayName(tpl)%></a><br /><span class="Text_cuadrantes"><%=desc%></span></td>
                  <td width="8" ></td>
<%
		if(it.hasNext())
		{
		    tp=(com.infotec.topicmaps.Topic)it.next();
                    desc=tp.getDescription(tpl);
%>
                  <td width="245" align="left" valign="top" ><a href="<%=tp.getUrl()%>" class="Tit_cuadrantes"><%=tp.getDisplayName(tpl)%></a><br /><span class="Text_cuadrantes"><%=desc%></span></td>
<%
		}else
		{
%>
                  <td width="245" align="left" valign="top" > </td>
<%
		}
%>
                </tr>
<%
		if(it.hasNext())
		{
%>
                <tr >
                  <td height="10px" ></td>
                  <td ></td>
                  <td ></td>
                </tr>
<%
		}
	}
%>
              </table>
<%
	if(i==j)return;
%>
              <br>
              <img src="/JSPs/images/mas_info_apartado.gif" border="0" alt="Más información de este apartado..." width="497" height="22">

<table width="500" border="0" cellspacing="0" cellpadding="0" >
<%
	it=ars.iterator();
	while(it.hasNext())
	{
		com.infotec.topicmaps.Topic tp=(com.infotec.topicmaps.Topic)it.next();
%>
  <tr>
    <td width="250" valign="top"><a href="<%=tp.getUrl()%>" class="tematico"><%=tp.getDisplayName(tpl)%></a></td>
<%
		if(it.hasNext())
		{
			tp=(com.infotec.topicmaps.Topic)it.next();
%>
    <td width="250" valign="top"><a href="<%=tp.getUrl()%>" class="tematico"><%=tp.getDisplayName(tpl)%></a></td><%
		}else
		{
%>
    <td width="250" valign="top"></td>
<%
		}
%>
  </tr>
<%		
	}
%>
</table>
