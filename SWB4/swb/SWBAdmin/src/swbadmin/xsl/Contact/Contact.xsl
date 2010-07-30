<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" omit-xml-declaration="yes" indent="yes" encoding="ISO-8859-1"/>

    <xsl:template match="/contact">
        <xsl:apply-templates select="apologies"/>
        <div class="swb-contact">
        <form method="post" action="{@url}">
            <h3><xsl:value-of select="@title"/></h3>            
            <xsl:apply-templates select="instructions"/>
            <hr/>
            <xsl:apply-templates select="name"/>
            <xsl:apply-templates select="email"/>
            <xsl:apply-templates select="subject"/>
            <xsl:apply-templates select="message"/>
            <xsl:apply-templates select="command"/>
        </form>
        </div>
    </xsl:template>

    <xsl:template match="apologies">
        <p class="swb-contact-fail">
            <xsl:value-of select="apologies"/>
        </p>
    </xsl:template>

    <xsl:template match="instructions">
        <p class="swb-contact-inst">
        <xsl:for-each select="instruction">
            <xsl:apply-templates/>
        </xsl:for-each>
        </p>
    </xsl:template>


    <xsl:template match="name">
        <p>
            <label for="{@id}">
                <xsl:value-of select="@label"/>: 
            </label>
            <input name="{@id}" id="{@id}" value="{.}" class="swb-contact-field" />
        </p>
    </xsl:template>
    <xsl:template match="email">
        <p>
            <label for="{@id}">
                <xsl:value-of select="@label"/>:
            </label>
            <input name="{@id}" id="{@id}" value="{.}" class="swb-contact-field" />
        </p>
    </xsl:template>
    <xsl:template match="subject">
        <p>
            <label for="{@id}">
                <xsl:value-of select="@label"/>:
            </label>
            <input name="{@id}" id="{@id}" value="{.}" class="swb-contact-field" />
        </p>
    </xsl:template>
    <xsl:template match="message">
        <p>
            <label for="{@id}">
                <xsl:value-of select="@label"/>:
            </label>
            <textarea name="{@id}" id="{@id}" cols="40" rows="5">
                <xsl:apply-templates/>
            </textarea>
        </p>
    </xsl:template>
    <xsl:template match="command">
        <p class="swb-contact-cmd">
            <input name="submit" id="contactoEnviar" type="submit" value="{@submit}" onclick="{@action}" class="swb-contact-button" />
            <input name="reset" id="contactoRestablecer" type="reset" value="{@reset}" class="swb-contact-button" />
        </p>
    </xsl:template>
</xsl:stylesheet>
