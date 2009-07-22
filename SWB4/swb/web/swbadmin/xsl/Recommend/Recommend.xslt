<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" encoding="ISO-8859-1"/>
<xsl:template match="/form">
	<LINK href="{@path}swb-estilo.css" rel="stylesheet" type="text/css" ></LINK>
    <xsl:value-of select="@styleClass" disable-output-escaping="yes" />
	<DIV class="swb-recomendar">
        <h1><xsl:value-of select="msgRecommend" disable-output-escaping="yes"/></h1>
	<xsl:choose>
		<xsl:when test="@email ='1'">
                    <table>
			<TR><TD>
				<xsl:if test="count(headermsg) &gt; 0">
					<BR/><xsl:value-of select="headermsg" disable-output-escaping="yes" /><BR/><BR/>
				</xsl:if><BR/>
				<xsl:value-of select="msgToMessage" disable-output-escaping="yes"/> <I><xsl:value-of select="toname"/></I>, <BR/><BR/>
				<xsl:value-of select="msgFromMessage" disable-output-escaping="yes"/> <I><xsl:value-of select="fromname"/></I> 
				<xsl:value-of select="msgBodyMessage" disable-output-escaping="yes"/><BR/>
				<xsl:text disable-output-escaping="yes">&lt;A href=&quot;</xsl:text>
				<xsl:value-of select="topicurl"/>
				<xsl:text disable-output-escaping="yes">&quot;&gt;</xsl:text>
					<xsl:value-of select="topic"/>
				<xsl:text disable-output-escaping="yes">&lt;/A&gt;</xsl:text>
				<xsl:if test="count(message) &gt; 0">
					<p><fieldset><xsl:value-of select="message" /></fieldset></p>
				</xsl:if>
				<xsl:if test="count(footermsg) &gt; 0">
					<BR/><BR/><BR/><xsl:value-of select="footermsg" disable-output-escaping="yes" />
				</xsl:if>
				<BR/><BR/>-------------------------------------------------------<BR />
				<xsl:value-of select="msgFooterMessage" disable-output-escaping="yes"/><BR/>
				<xsl:text disable-output-escaping="yes">&lt;A href=&quot;</xsl:text>
				<xsl:value-of select="siteurl"/>
				<xsl:text disable-output-escaping="yes">&quot;&gt;</xsl:text>
					<xsl:value-of select="site"/>
				<xsl:text disable-output-escaping="yes">&lt;/A&gt;</xsl:text>
				<BR/><BR/>
			</TD></TR>
                    </table>
		</xsl:when>
		<xsl:otherwise>
                    <FORM name="frmSendEmail" method="POST" action="{@accion}" >
                        <fieldset>
                        <legend><xsl:value-of select="labelSender" /></legend>
                        <table>
			<xsl:for-each select="ftextsender">
                            <TR>
                                <TD><label><xsl:value-of select="@tag" /></label></TD>
                                <TD><INPUT type="text" name="{@inname}" value="{@invalue}" class="swb-recomendar-text"></INPUT></TD>
                            </TR>
			</xsl:for-each>
                        </table>
                        </fieldset>
                        <fieldset>
                        <legend><xsl:value-of select="labelReceiver" /></legend>
                        <table>
			<xsl:for-each select="ftextreceiver">
                            <TR>
                                <TD><label><xsl:value-of select="@tag" /></label></TD>
                                <TD><INPUT type="text" name="{@inname}" value="{@invalue}" class="swb-recomendar-text"></INPUT></TD>
                            </TR>
			</xsl:for-each>
			<xsl:for-each select="ftextarea">
                            <TR>
                                <TD><label><xsl:value-of select="@tag" /></label></TD>
                                <TD><TEXTAREA name="{@inname}" wrap="virtual"></TEXTAREA></TD>
                            </TR>
			</xsl:for-each>
                        </table>
                        </fieldset>
                        <p>
                            <xsl:for-each select="fsubmit">
                                <xsl:if test="@img ='1'">		
                                    <A href="javascript:if(jsValida(document.frmSendEmail)) document.frmSendEmail.submit();" >
                                    <IMG src="{@src}" alt="{@alt}" border="0" ></IMG>
                                    </A>
                                </xsl:if>
                                <xsl:if test="@img ='0'">		
                                    <INPUT type="submit" name="btnEnviar" onClick="if(jsValida(this.form)) return true; else return false;" class="swb-recomendar-boton" value="{@tag}"></INPUT>
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
                                    <INPUT type="reset" name="btnLimpiar" class="swb-recomendar-boton" value="{@tag}"></INPUT>
                                </xsl:if>
                            </xsl:for-each>
                        </p>
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
	</DIV>
	<xsl:value-of select="@styleClassClose" disable-output-escaping="yes" />
</xsl:template>
</xsl:stylesheet>