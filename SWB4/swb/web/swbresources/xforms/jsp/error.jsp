<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<!--Copyright 2001-2007 ChibaXForms GmbH -->
<head>
	<title>Meldung</title>
    <link rel="stylesheet" type="text/css" href="styles/chiba-styles.css"/>

<%@ page import="java.io.PrintWriter"%>
<%@ page session="true" %>


</head>
<body bgcolor="#FAFAF0" text="black" link="blue" vlink="purple" alink="orange">

<center>
<table bgcolor="#fcf6d3" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="right" valign="top">
			<br>
		</td></tr>

		<tr>
			<td>
			<table cellspacing="3">
				<tr>
					<td>&nbsp;</td>
					<td>
						<font face="sans-serif">
						<b>OOPS - An error occurred.</b><br><br>
						<%
                            Exception e=(Exception)session.getAttribute("chiba.exception");
                            session.removeAttribute("chiba.exception");
                            String message=e.getMessage();
							if (message!=null && message.length()>0) {
								%>
								<font face="helv" size="+1">
									Message:<br>
									<ul>
										<font color="darkred"><%=message%></font>
									</ul>
								</font>
								<ul>
									<font face="sans-serif">
										<form>
                                            <input type="button" value="Back" onClick="history.back()">
										</form>
									</font>
								</ul>
								<br>
							<%
							}
							%>

							<br><br>
							<b>Stack Trace:</b><br>
                                <pre><% e.printStackTrace(new PrintWriter(out)); %></pre>
						</font>	
					</td>
				</tr>			
			</table>
			</td>
		</tr>
</table>



</center>
</body>
</html>
