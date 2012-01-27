<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" omit-xml-declaration="yes" indent="yes" encoding="ISO-8859-1"/>

    <xsl:template match="/menu">
        <div class="swb-menu">
            <table>
                <xsl:for-each select="parent">
                    <tr>
                        <td>
                            <a href="{@path}" class="swb-menu-parent" target="{@target}" title="{@name}">
                                <xsl:value-of select="@name"/>
                            </a>
                        </td>
                    </tr>
                </xsl:for-each>

                <xsl:for-each select="brothers">
                    <xsl:for-each select="brother">
                        <tr>
                            <td class="wmn_data">
                                <img src="/wbadmin/icons/trans.gif" alt="">
                                    <xsl:attribute name="width"><xsl:value-of select="@caracter"/></xsl:attribute>
                                    <xsl:attribute name="height">1</xsl:attribute>
                                </img>
                                <xsl:if test="@current = '0'">
                                    <a href="{@path}" class="swb-menu-active" target="{@target}" title="{@name}">
                                        <xsl:value-of select="@name"/>
                                    </a>
                                </xsl:if>
                                <xsl:if test="@current = '1'">
                                    <a href="#" class="swb-menu-current" target="{@target}" title="{@name}">
                                        <xsl:value-of select="@name"/>
                                    </a>
                                </xsl:if>
                            </td>
                        </tr>
                        <xsl:for-each select="child">
                            <xsl:call-template name="rest" />
                        </xsl:for-each>
                    </xsl:for-each>
                </xsl:for-each>
            </table>
        </div>
    </xsl:template>

    <xsl:template name="rest">
        <tr>
            <td class="wmn_data" >
                <img src="/wbadmin/icons/trans.gif" alt="">
                    <xsl:attribute name="width"><xsl:value-of select="@caracter"/></xsl:attribute>
                    <xsl:attribute name="height">1</xsl:attribute>
                </img>
                <xsl:if test="@current='0'">
                    <a href="{@path}" class="swb-menu-active" target="{@target}" title="{@name}">
                        <xsl:value-of select="@name"/>
                    </a>
                </xsl:if>
                <xsl:if test="@current='1'">
                    <a href="#" class="swb-menu-current" target="{@target}" title="{@name}">
                        <xsl:value-of select="@name"/>
                    </a>
                </xsl:if>
            </td>
        </tr>
        <xsl:for-each select="child">
            <xsl:call-template name="rest" />
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>