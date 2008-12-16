<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" encoding="ISO-8859-1"/>
<xsl:template match="/form">
	<LINK href="{@path}swb-estilo.css" rel="stylesheet" type="text/css"></LINK>
	<DIV id="swb-comentar">
	<xsl:choose>
		<xsl:when test="@email ='1'">
                    <h1><xsl:value-of select="site" /></h1>
                    <h2><xsl:value-of select="msgTo" disable-output-escaping="yes" /> <xsl:value-of select="responsable" />, <xsl:value-of select="area" /> </h2>
		    <xsl:if test="count(headermsg) &gt; 0">
			<p><xsl:value-of select="headermsg" disable-output-escaping="yes" /></p>
		    </xsl:if>
                    <TABLE>
			<TR>
				<TD><xsl:value-of select="msgName" disable-output-escaping="yes" /></TD>
				<TD><xsl:value-of select="fromname" /></TD>
			</TR>
			<TR>
				<TD><xsl:value-of select="msgViewEmail" disable-output-escaping="yes" /></TD>
				<TD><xsl:value-of select="fromemail" /></TD>
			</TR>
			<xsl:if test="count(phone) &gt; 0">
			<TR>
				<TD><xsl:value-of select="msgPhone" disable-output-escaping="yes" /></TD>
				<TD><xsl:value-of select="phone" /></TD>
			</TR>
			</xsl:if>
			<xsl:if test="count(fax) &gt; 0">
			<TR>
				<TD><xsl:value-of select="msgFax" disable-output-escaping="yes" /></TD>
				<TD><xsl:value-of select="fax" /></TD>
			</TR>
			</xsl:if>
		    </TABLE>
                    <fieldset>
                    	<legend><xsl:value-of select="msgMessage" disable-output-escaping="yes" /></legend>
                        <p><xsl:value-of select="message" /></p>
                    </fieldset>
                    <xsl:if test="count(footermsg) &gt; 0">
                        <p>
                        <xsl:value-of select="footermsg" disable-output-escaping="yes" />
                        </p>
                    </xsl:if>
		</xsl:when>
		<xsl:otherwise>
                    <h1><xsl:value-of select="msgComments" disable-output-escaping="yes" /></h1>
		    <FORM name="frmSendEmail" method="POST" action="{@accion}" > 
        	    <TABLE align="center">
			<xsl:for-each select="fselect">
				<TR>
					<TD><label><xsl:value-of select="@tag" /></label></TD> 
					<TD>
						<select name="{@inname}">
							<xsl:for-each select="foption">
								<option value="{@invalue}"><xsl:value-of select="@invalue" /></option>
							</xsl:for-each>					
						</select>
					</TD>
				</TR> 
			</xsl:for-each>
			<xsl:for-each select="ftext">
				<TR>
					<TD><label><xsl:value-of select="@tag" /></label></TD> 
					<TD><INPUT type="text" name="{@inname}" value="{@invalue}" class="swb-comentar-text"></INPUT></TD>
				</TR> 
			</xsl:for-each>
			<xsl:for-each select="ftextarea">
				<TR>
					<TD><label><xsl:value-of select="@tag" /></label></TD>
					<TD><TEXTAREA name="{@inname}" wrap="virtual"></TEXTAREA></TD>
				</TR>
			</xsl:for-each>
        	        </TABLE>
			<p>
					<xsl:for-each select="fsubmit">
						<xsl:if test="@img ='1'">		
							<A href="javascript:if(jsValida(document.frmSendEmail)) document.frmSendEmail.submit();" >
								<IMG src="{@src}" alt="{@alt}" border="0" ></IMG>
							</A>
						</xsl:if>
						<xsl:if test="@img ='0'">		
							<INPUT type="submit" name="btnEnviar" onClick="if(jsValida(this.form)) return true; else return false;" class="swb-recomendar-boton" value="{@tag}" ></INPUT>
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
							<INPUT type="reset" name="btnLimpiar" class="swb-recomendar-boton" value="{@tag}" ></INPUT>
						</xsl:if>
					</xsl:for-each>
			</p>
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
	</DIV>
</xsl:template>
</xsl:stylesheet>