<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" version="1.0" encoding="ISO-8859-1" indent="yes"/>
<xsl:param name="ActionURL"/>
<xsl:template match="/">
	<div class="box">
	<form action="{$ActionURL}" method="post" name="frmUserInfo" onsubmit="return validate()">
		<table width="100%" border="0" cellspacing="0" cellpadding="5">
		<xsl:apply-templates/>
		</table>
	</form>
	</div>
	<script language="Javascript">
		<xsl:for-each select="/userForm/*">
			<xsl:if test="./@name='rep'">
				if (document.frmUserInfo.rep==undefined || 
					(document.frmUserInfo.rep.type=="hidden" &amp;&amp; (document.frmUserInfo.rep.value==undefined || document.frmUserInfo.rep.value=='')) ||
					(document.frmUserInfo.rep.type=="select-one" &amp;&amp; (document.frmUserInfo.rep.length==undefined || document.frmUserInfo.rep.length==1))
				) {
		        		document.frmUserInfo.btnSubmit.disabled=true;
		        		alert ("<xsl:value-of select="./@err"/>");
		        	}
		  	</xsl:if>
		</xsl:for-each>
		
		function validate()
		{
			<xsl:for-each select="/userForm/*">
				<xsl:if test="./@required='1'">
					if (document.frmUserInfo.<xsl:value-of select="./@name"/>.value=="") 
					{
						alert ("<xsl:value-of select="./@help"/>");
						document.frmUserInfo.<xsl:value-of select="./@name"/>.focus();
						return false;
					}
			  	</xsl:if>
				<xsl:if test="./@pattern">
					re = <xsl:value-of select="./@pattern"/>;
					if  (!re.test(document.frmUserInfo.<xsl:value-of select="./@name"/>.value))
					{
						alert ("<xsl:value-of select="./@help"/>");
						document.frmUserInfo.<xsl:value-of select="./@name"/>.focus();
						return false;  	
					}
			  	</xsl:if>
				<xsl:if test="local-name()='password'">
					<xsl:if test="./@special">
			  			if (document.frmUserInfo.PASSWORD.value!=document.frmUserInfo.rePASSWORD.value) 
			  			{
			  				alert("<xsl:value-of select="./@help"/>");
			  				document.frmUserInfo.PASSWORD.focus();
			  				return false;
			  			}
			  		</xsl:if>
				</xsl:if>
			</xsl:for-each>
			return true;
		}
	</script>	
</xsl:template>
<xsl:template match="submit">
	<tr><td colspan="2">
		<xsl:text disable-output-escaping="yes">&lt;</xsl:text>hr size="1" noshade<xsl:text disable-output-escaping="yes">/&gt;</xsl:text>
	</td></tr>
	<xsl:text disable-output-escaping="yes">&lt;tr&gt;&lt;td colspan="2" align="right"&gt;</xsl:text>
		<input type="submit" name="{./@name}" value="{./.}" class="boton"></input>
</xsl:template>
<xsl:template match="reset">
		<xsl:text disable-output-escaping="yes">&amp;nbsp;</xsl:text><input type="reset" name="{./@name}" value="{./.}"  class="boton"></input>
	<xsl:text disable-output-escaping="yes">&lt;/td&gt;&lt;/tr&gt;</xsl:text>
</xsl:template>
<xsl:template match="text">
	<tr>
		<td width="150" class="datos">
			<xsl:value-of select="./@caption"/>:
		</td>
		<td class="valores">
			<input type="text" name="{./@name}" size="{./@size}" value="{./.}"></input>
		</td>
	</tr>
</xsl:template>
<xsl:template match="hidden">
	<input type="hidden" name="{./@name}" value="{./.}"></input>
</xsl:template>
<xsl:template match="textarea">
	<tr>
		<td width="150" class="datos">
			<xsl:value-of select="./@caption"/>:
		</td>
		<td class="valores">
			<textarea rows="{./@rows}" cols="{./@size}" name="{./@name}">
				<xsl:value-of select="./."/>
			</textarea>
		</td>
	</tr>
</xsl:template>
<xsl:template match="check">
	<tr>
		<td width="150" class="datos">
			<xsl:value-of select="./@caption"/>:
		</td>
		<td class="valores">
			<xsl:variable name="name" select="./@name"/>
			<xsl:for-each select="./option">
				<xsl:variable name="selected">
					<xsl:choose>
						<xsl:when test="./@checked">
							<xsl:text> checked</xsl:text>
						</xsl:when>
						<xsl:otherwise>
							<xsl:text/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				<xsl:text disable-output-escaping="yes">&lt;input type="checkbox" name="</xsl:text>
				<xsl:value-of select="$name"/>
				<xsl:text disable-output-escaping="yes">" value="</xsl:text>
				<xsl:value-of select="./@value"/>
				<xsl:text disable-output-escaping="yes">" </xsl:text>
				<xsl:value-of select="$selected"/>
				<xsl:text disable-output-escaping="yes"> / &gt;</xsl:text>
				<xsl:value-of select="./@value"/>
			</xsl:for-each>
		</td>
	</tr>
</xsl:template>
<xsl:template match="radio">
	<tr>
		<td width="150" class="datos">
			<xsl:value-of select="./@caption"/>:
		</td>
		<td class="valores">
			<xsl:variable name="name" select="./@name"/>
			<xsl:for-each select="./option">
				<xsl:variable name="selected">
					<xsl:choose>
						<xsl:when test="./@checked">
							<xsl:text> checked</xsl:text>
						</xsl:when>
						<xsl:otherwise>
							<xsl:text/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				<xsl:text disable-output-escaping="yes">&lt;input type="radio" name="</xsl:text>
				<xsl:value-of select="$name"/>
				<xsl:text disable-output-escaping="yes">" value="</xsl:text>
				<xsl:value-of select="./@value"/>
				<xsl:text disable-output-escaping="yes">" </xsl:text>
				<xsl:value-of select="$selected"/>
				<xsl:text disable-output-escaping="yes"> /&gt;</xsl:text>
				<xsl:value-of select="./@value"/>
			</xsl:for-each>
		</td>
	</tr>
</xsl:template>
<xsl:template match="select">
	<tr>
		<td width="150" class="datos">
			<xsl:value-of select="./@caption"/>:
		</td>
		<td class="valores">
			<xsl:text disable-output-escaping="yes">&lt;select name="</xsl:text>
			<xsl:value-of select="./@name"/>
			<xsl:text disable-output-escaping="yes">" size="</xsl:text>
			<xsl:value-of select="./@size"/>
			<xsl:text disable-output-escaping="yes">" </xsl:text>
			<xsl:if test="./@special"> multiple</xsl:if>
			<xsl:text disable-output-escaping="yes">&gt;</xsl:text>
			<xsl:for-each select="./option">
				<xsl:variable name="selected">
					<xsl:choose>
						<xsl:when test="./@selected">
							<xsl:text> selected</xsl:text>
						</xsl:when>
						<xsl:otherwise>
							<xsl:text/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				<xsl:text disable-output-escaping="yes">&lt;option value="</xsl:text>
				<xsl:value-of select="./@value"/>
				<xsl:text disable-output-escaping="yes">" </xsl:text>
				<xsl:value-of select="$selected"/>
				<xsl:text disable-output-escaping="yes">&gt;</xsl:text>
				<xsl:value-of select="./@caption"/>
				<xsl:text disable-output-escaping="yes">&lt;/option&gt;</xsl:text>
			</xsl:for-each>
			<xsl:text disable-output-escaping="yes">&lt;/select&gt;</xsl:text>
		</td>
	</tr>
</xsl:template>
<xsl:template match="password">
	<tr>
		<td width="150" class="datos">
			<xsl:value-of select="./@caption"/>:
		</td>
		<td class="valores">
			<input type="password" name="{./@name}" size="{./@size}" value="{./.}"></input>
		</td>
	</tr>
	<xsl:if test="./@special">
	<tr>
		<td width="150" class="datos">
			<xsl:value-of select="./@special"/>:
		</td>
		<td class="valores">
			<input type="password" name="re{./@name}" size="{./@size}" value="{./.}"></input>
		</td>
	</tr>
	</xsl:if>
</xsl:template>
</xsl:stylesheet>