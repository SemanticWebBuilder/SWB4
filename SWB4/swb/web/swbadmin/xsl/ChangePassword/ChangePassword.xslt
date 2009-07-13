<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" encoding="ISO-8859-1"/> 
<xsl:param name="actionUrl"/>
  <xsl:template match="/">
	<xsl:if test="count(USER/userattr) > 0">
		<script language="JavaScript" >
			function jsValidate(form) {
				if(form.curPassword!=undefined)
					if(!jsRequired(form.curPassword, 'Por favor capture su contrase\u00F1a actual'))
						return false;
					if(!jsRequired(form.newPassword, 'Por favor capture su nueva contrase\u00F1a'))
						return false;
					if(!jsRequired(form.rePassword,  'Por favor capture la confirmaci\u00F3n de su nueva contrase\u00F1a'))
						return false;
					if(form.newPassword.value!=form.rePassword.value) {
						 alert('Por favor verificar su nueva contrase\u00F1a y la confirmaci\u00F3n');
						 form.newPassword.value='';
						 form.rePassword.value='';
						 form.newPassword.focus();
						 return false;
					}
				return true;
			}
			function jsRequired(obj, msg) {
				if(obj.value==null || obj.value=='' || obj.value==' ') {
					alert(msg);
					obj.focus();
					return false;
				}
				return true;
			}
		</script>
		<xsl:text disable-output-escaping="yes">&lt;form method="post" </xsl:text>action="<xsl:value-of select="$actionUrl"/>" <xsl:text disable-output-escaping="yes">&gt;</xsl:text>
			<table >
				<xsl:for-each select="/USER/userattr">
					<tr>
						<td colspan="2" >
							<xsl:value-of select="@name"/>
							<xsl:text>
							</xsl:text>
							<xsl:value-of select="@middleName"/>
							<xsl:text>
							</xsl:text>
						<!--
							<xsl:value-of select="@lastName"/>
							<xsl:text>
							</xsl:text>
						
							<xsl:value-of select="@firstName"/>
							<xsl:text>
							</xsl:text>
						-->
							<xsl:value-of select="@email"/>
							<xsl:text>
							</xsl:text>
							<xsl:value-of select="@login"/>
						</td>
						<td >
						</td>
					</tr>
					<tr>
						<td >
							<xsl:value-of select="@msgCurrentPassword"/>
						</td>
						<td >
							<input type="password" name="curPassword" size="25" />
						</td>
					</tr>
					<tr>
						<td >
							<xsl:value-of select="@msgNewPassword"/>
						</td>
						<td >
							<input type="password" name="newPassword" size="25" />
						</td>
					</tr>
					<tr>
						<td >
							<xsl:value-of select="@msgConfirmPassword"/>
						</td>
						<td >
							<input type="password" name="rePassword" size="25" />
						</td>
					</tr>
				</xsl:for-each>
				<tr>
					<td colspan="2" align="center" >
						<input type="submit" value="Enviar" onClick="if(jsValidate(this.form)) return true; else return false;" />
						<input type="reset" value="Cancelar" />
					</td>
					<td >
					</td>
				</tr>	
			</table>
		<xsl:text disable-output-escaping="yes">&lt;/form&gt;</xsl:text>
	</xsl:if>
	<xsl:if test="count(USER/errorLogin) > 0">
		<table >
				<xsl:for-each select="/USER/errorLogin">
					<tr>
						<td colspan="2" >
							<xsl:value-of select="@msgErrLogin"/>
						</td>
						<td >
						</td>
					</tr>
				</xsl:for-each>
			</table>
	</xsl:if>
   </xsl:template>
</xsl:stylesheet>