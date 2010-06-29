<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" encoding="ISO-8859-1"/>
<xsl:template match="/form">
	<LINK href="{@path}images/Recomendar.css" rel="stylesheet" type="text/css" ></LINK>
	<DIV class="rec_box">
	<TABLE width="100%" border="0" align="center" cellSpacing="3" cellPadding="3" > 
	<xsl:choose>
		<xsl:when test="@email ='1'">
			<TR><TD class="rec_data" >
				<BR/><FONT face="Verdana, Arial, Helvetica, sans-serif" size="2">
				----------------------------------------------------------------------<BR/>
				Recomendación especial<BR/>
				----------------------------------------------------------------------<BR/>
				<xsl:if test="count(headermsg) &gt; 0">
					<BR/><xsl:value-of select="headermsg" disable-output-escaping="yes" /><BR/><BR/>
				</xsl:if><BR/>
				Hola <I><xsl:value-of select="toname"/></I>, <BR/><BR/>
				Tu amigo(a) <I><xsl:value-of select="fromname"/></I>
				te recomienda que visites la siguiente dirección:<BR/>
				<xsl:text disable-output-escaping="yes">&lt;A href=&quot;</xsl:text>
				<xsl:value-of select="topicurl"/>
				<xsl:text disable-output-escaping="yes">&quot;&gt;</xsl:text>
					<xsl:value-of select="topic"/>
				<xsl:text disable-output-escaping="yes">&lt;/A&gt;</xsl:text>
				<xsl:if test="count(message) &gt; 0">
					<BR /><BR /><xsl:value-of select="message" />
				</xsl:if>
				<xsl:if test="count(footermsg) &gt; 0">
					<BR/><BR/><BR/><xsl:value-of select="footermsg" disable-output-escaping="yes" />
				</xsl:if>
				<BR/><BR/>----------------------------------------------------------------------<BR />
				Te invitamos a que visites nuestro portal en la siguiente dirección:<BR/>
				<xsl:text disable-output-escaping="yes">&lt;A href=&quot;</xsl:text>
				<xsl:value-of select="siteurl"/>
				<xsl:text disable-output-escaping="yes">&quot;&gt;</xsl:text>
					<xsl:value-of select="site"/>
				<xsl:text disable-output-escaping="yes">&lt;/A&gt;</xsl:text>
				</FONT><BR/><BR/>
			</TD></TR> 			
		</xsl:when>
		<xsl:otherwise>
			<FORM name="frmSendEmail" method="POST" action="{@accion}" > 
			<xsl:for-each select="ftext">	
				<TR>
					<TD class="rec_data" ><xsl:value-of select="@tag" /></TD> 
					<TD ><INPUT type="text" name="{@inname}" size="30" value="{@invalue}" class="rec_input" ></INPUT></TD>
				</TR> 
			</xsl:for-each>
			<xsl:for-each select="ftextarea">
				<TR>
					<TD class="rec_data" ><xsl:value-of select="@tag" /></TD> 
					<TD ><TEXTAREA name="{@inname}" rows="7" cols="29" wrap="virtual" class="rec_input"></TEXTAREA></TD> 
				</TR> 
			</xsl:for-each>
			<TR> 
				<TD align="center" colspan="2" >
					<xsl:for-each select="fsubmit">
						<xsl:if test="@img ='1'">		
							<A href="javascript:if(jsValida(document.frmSendEmail)) document.frmSendEmail.submit();" >
								<IMG src="{@src}" alt="{@alt}" border="0" ></IMG>
							</A>
						</xsl:if>
						<xsl:if test="@img ='0'">		
							<INPUT type="submit" name="btnEnviar" onClick="if(jsValida(this.form)) return true; else return false;" class="rec_button" value="{@tag}" ></INPUT>
						</xsl:if>
					</xsl:for-each>
					<xsl:for-each select="freset">
						<xsl:if test="@img ='1'">		
							<A href="javascript:document.frmSendEmail.reset();" >
								<IMG src="{@src}" alt="{@alt}" border="0" ></IMG>
							</A>
						</xsl:if>
						<xsl:if test="@img ='0'">		
							<xsl:text disable-output-escaping="yes">&amp;nbsp;</xsl:text>
							<INPUT type="reset" name="btnLimpiar" class="rec_button" value="{@tag}" ></INPUT>
						</xsl:if>
					</xsl:for-each>
				</TD> 
			</TR>
			</FORM>
			<SCRIPT type="text/javascript" ><xsl:text disable-output-escaping="yes">
				function jsValida(pForm) 
				{ 
				   if(pForm.txtFromName.value==null || pForm.txtFromName.value=='' || pForm.txtFromName.value==' ') 
				   { 
				       alert('Debe especificar el nombre del remitente.'); 
				       pForm.txtFromName.focus(); 
				       return false; 
				   } 
				   if(!isEmail(pForm.txtFromEmail)) return false; 
				   if(pForm.txtToName.value==null || pForm.txtToName.value=='' || pForm.txtToName.value==' ') 
				   { 
				       alert('Debe especificar el nombre del destinatario.'); 
				       pForm.txtToName.focus(); 
				       return false; 
				   } 
				   if(!isEmail(pForm.txtToEmail)) return false; 
				   return true; 		   
				} 
				function isEmail(pEmail)
				{ 
				   var swOK=2; 
				   pCaracter=pEmail.value.replace(" ","\0");
				   for (var i = 0; i &lt; pCaracter.length; i++) 
				   { 
				      var sByte = pCaracter.substring(i, i + 1); 
				      if (sByte=="@" || sByte==".") { swOK = swOK - 1; } 
				   } 
				   if (swOK &gt; 0 || pCaracter.length &lt; 5 || pCaracter.charAt(0) == '@' || pCaracter.charAt(0) == '.' || pCaracter.charAt(pCaracter.length-1)=='@' || pCaracter.charAt(pCaracter.length-1)=='.' || pCaracter.charAt(pCaracter.indexOf("@")+1)=='.') 
				   { 
				      pEmail.focus(); 
				      alert('Este campo debe ser una dirección de correo electrónico y es obligatorio.');  
				      return false; 
				   } 
				   return true;
				} 
			</xsl:text></SCRIPT>			
		</xsl:otherwise>
	</xsl:choose>
	</TABLE>
	</DIV>
</xsl:template>
</xsl:stylesheet>