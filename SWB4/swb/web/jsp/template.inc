<%@page import="com.infotec.appfw.util.*"%>
<%@page import="com.infotec.wb.lib.*"%>
<%@page import="com.infotec.topicmaps.*"%>
<%@page import="com.infotec.wb.core.*"%>
<%@page import="java.util.*"%>
<jsp:useBean id="topic" scope="request" class="com.infotec.topicmaps.Topic" />
<jsp:useBean id="user" scope="request" class="com.infotec.wb.core.WBUser" />
<jsp:useBean id="params" scope="request" class="java.util.HashMap" />