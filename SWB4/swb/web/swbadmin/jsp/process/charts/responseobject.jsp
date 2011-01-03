<%@page import="org.semanticwb.portal.api.SWBParamRequest,java.net.URLEncoder,org.semanticwb.process.model.ProcessWebPage"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>Response Stage</title>
	</head>
	<body>
		<%
			String suri = java.net.URLEncoder.encode(request.getParameter("suri"));
		%>
			<iframe id="swbPreviewFrame" dojoType="dijit.layout.ContentPane" src="/es/SWBAdmin/bh_ResponseObject?suri=<%=suri%>" width="100%" height="100%" frameborder="0"></iframe>
	</body>
</html>