<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" encoding="ISO-8859-1"/>
<xsl:template match="/reltopics">
<div class="swb-relTopic">
<fieldset>
	 <legend>Temas Relacionados</legend>
<xsl:for-each select="padre">
	<xsl:if test="count(hijo) &gt; 0">
            <xsl:if test="position()  mod 2 = 1"><xsl:text disable-output-escaping="yes">
	 &lt;div class="swb-relTopic-col1"&gt;</xsl:text>
             </xsl:if>
             <xsl:if test="position()  mod 2 = 0"><xsl:text disable-output-escaping="yes">
                 &lt;div class="swb-relTopic-col2"&gt;</xsl:text>
             </xsl:if>
 		    <ul>
 			 <li><xsl:value-of select="@nombre"/>
 			   <ul>
		<xsl:for-each select="hijo">		
			<xsl:choose>
				<xsl:when test="@target ='1'">
                                        <li><a href="{@url}" target="_newrtp"><xsl:value-of select="@nombre" /></a></li>
				</xsl:when>
				<xsl:otherwise>
					<li><a href="{@url}"><xsl:value-of select="@nombre" /></a></li>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:for-each>
                        </ul>
                    </li>
                    </ul>
  		<xsl:text disable-output-escaping="yes">&lt;/div&gt;</xsl:text>
	</xsl:if>
</xsl:for-each>		
</fieldset>
</div>
</xsl:template>
</xsl:stylesheet>