<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" encoding="ISO-8859-1"/>

<xsl:template name="child">
	<li>
		<a href="{@path}" class="wmn_link" target="{@target}" title="{@name}">
                    <xsl:value-of select="@name"/>
                </a>
		<xsl:if test="child">
   	      <ul>
		   <xsl:for-each select="child">
			<xsl:call-template name="child" />
		   </xsl:for-each>
		</ul>
		</xsl:if>
      </li>
</xsl:template>


<xsl:template match="/menu">
<ul id="MenuBar1" class="MenuBarVertical">
	<xsl:for-each select="brothers">
		<xsl:for-each select="brother">
			<li>
			  <a href="{@path}" class="MenuBarItemSubmenu" target="{@target}" title="{@name}">
                              <xsl:value-of select="@name"/>
                          </a>
                    <xsl:if test="child">
			  <ul>
				<xsl:for-each select="child">
					<xsl:call-template name="child" />
				</xsl:for-each>
			  </ul>
                    </xsl:if>
			</li>
		</xsl:for-each>
	</xsl:for-each>
</ul>
</xsl:template>
</xsl:stylesheet>