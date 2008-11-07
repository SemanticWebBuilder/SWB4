<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" encoding="ISO-8859-1"/>
<xsl:template match="/form">
	<LINK href="{@path}images/Coment.css" rel="stylesheet" type="text/css"></LINK>
	<DIV class="com_box">
	<TABLE width="100%" border="0" align="center" cellSpacing="3" cellPadding="3"> 
	<xsl:choose>
		<xsl:when test="@email ='1'">
			<TR><TD class="com_data">
				<FONT face="Verdana, Arial, Helvetica, sans-serif" size="2"><BR />
				----------------------------------------------------<BR />
				Sitio o portal: <B><xsl:value-of select="site" />.<xsl:value-of select="topic" /></B><BR />
				Solicitud: <B><xsl:value-of select="commenttype" /></B><BR />
				----------------------------------------------------<BR />
				<xsl:if test="count(headermsg) &gt; 0">
					<BR /><xsl:value-of select="headermsg" disable-output-escaping="yes" /><BR/><BR/>
				</xsl:if><BR/>
				<TABLE border="0" class="com_data">
				<TR>
					<TD colspan="2" ><FONT face="Verdana, Arial, Helvetica, sans-serif" size="2">
						<xsl:value-of select="responsable" /><BR /> 
						<xsl:value-of select="area" /><BR /><BR />
					</FONT></TD>
				</TR>
				<TR>
					<TD><FONT face="Verdana, Arial, Helvetica, sans-serif" size="2"><B>
						Nombre:</B>
					</FONT></TD>
					<TD><FONT face="Verdana, Arial, Helvetica, sans-serif" size="2">
						<xsl:value-of select="fromname" />
					</FONT></TD>
				</TR>
				<TR>
					<TD><FONT face="Verdana, Arial, Helvetica, sans-serif" size="2"><B>
						Correo electrónico:</B>
					</FONT></TD>
					<TD><FONT face="Verdana, Arial, Helvetica, sans-serif" size="2">
						<xsl:value-of select="fromemail" />
					</FONT></TD>
				</TR>
				<xsl:if test="count(phone) &gt; 0">
					<TR>
						<TD><FONT face="Verdana, Arial, Helvetica, sans-serif" size="2"><B>
							Teléfono(s):</B>
						</FONT></TD>
						<TD><FONT face="Verdana, Arial, Helvetica, sans-serif" size="2">
							<xsl:value-of select="phone" />
						</FONT></TD>
					</TR>
				</xsl:if>
				<xsl:if test="count(fax) &gt; 0">
					<TR>
						<TD><FONT face="Verdana, Arial, Helvetica, sans-serif" size="2"><B>
							Fax:</B>
						</FONT></TD>
						<TD><FONT face="Verdana, Arial, Helvetica, sans-serif" size="2">
							<xsl:value-of select="fax" />
						</FONT></TD>
					</TR>
				</xsl:if>
				<TR>
					<TD colspan="2"><FONT face="Verdana, Arial, Helvetica, sans-serif" size="2"><BR /><BR />
						Y escribe:<BR /><BR />
						<xsl:value-of select="message" />
					</FONT></TD>
				</TR>
				</TABLE>
				<xsl:if test="count(footermsg) &gt; 0">
					<BR /><BR /><BR />
					<xsl:value-of select="footermsg" disable-output-escaping="yes" />
				</xsl:if>
				<BR /><BR /><BR /></FONT>
			</TD> </TR>
		</xsl:when>
		<xsl:otherwise>
			<FORM name="frmSendEmail" method="POST" action="{@accion}" > 
			<xsl:for-each select="fselect">
				<TR>
					<TD class="com_data"><xsl:value-of select="@tag" /></TD> 
					<TD>
						<select name="{@inname}" class="com_input">
							<xsl:for-each select="foption">
								<option value="{@invalue}"><xsl:value-of select="@invalue" /></option>
							</xsl:for-each>					
						</select>
					</TD>
				</TR> 
			</xsl:for-each>
			<xsl:for-each select="ftext">
				<TR>
					<TD class="com_data"><xsl:value-of select="@tag" /></TD> 
					<TD><INPUT type="text" name="{@inname}" size="30" value="{@invalue}" class="com_input"></INPUT></TD>
				</TR> 
			</xsl:for-each>
			<xsl:for-each select="ftextarea">
				<TR>
					<TD class="com_data"><xsl:value-of select="@tag" /></TD> 
					<TD><TEXTAREA name="{@inname}" rows="6" cols="29" wrap="virtual" class="com_input"></TEXTAREA></TD> 
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
							<INPUT type="submit" name="btnEnviar" onClick="if(jsValida(this.form)) return true; else return false;" class="com_button" value="{@tag}" ></INPUT>
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
							<INPUT type="reset" name="btnLimpiar" class="com_button" value="{@tag}" ></INPUT>
						</xsl:if>
					</xsl:for-each>
				</TD> 
			</TR>
			</FORM>
			<SCRIPT type="text/javascript"><xsl:text disable-output-escaping="yes">
				function jsValida(pForm) 
				{ 
				   if(pForm.txtFromName.value==null || pForm.txtFromName.value=='' || pForm.txtFromName.value==' ') 
				   { 
				       alert('Debe especificar el nombre del remitente.'); 
				       pForm.txtFromName.focus(); 
				       return false; 
				   } 
				   if(!isEmail(pForm.txtFromEmail)) return false; 
				   if(pForm.tarMsg.value==null || pForm.tarMsg.value=='' || pForm.tarMsg.value==' ') 
				   { 
				       alert('Debe capturar su mensaje.'); 
				       pForm.tarMsg.focus(); 
				       return false; 
				   } 	   
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