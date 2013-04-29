copy "C:\Users\carlos.ramos\desarrollo\SWB4\swb\SWBBase\dist\SWBBase.jar" "C:\Users\carlos.ramos\desarrollo\SWB4\SWBBSCom\build\web\WEB-INF\lib\SWBBase.jar"
copy "C:\Users\carlos.ramos\desarrollo\SWB4\swb\SWBPlatform\dist\SWBPlatform.jar" "C:\Users\carlos.ramos\desarrollo\SWB4\SWBBSCom\build\web\WEB-INF\lib\SWBPlatform.jar"
copy "C:\Users\carlos.ramos\desarrollo\SWB4\swb\SWBModel\dist\SWBModel.jar" "C:\Users\carlos.ramos\desarrollo\SWB4\SWBBSCom\build\web\WEB-INF\lib\SWBModel.jar"
copy "C:\Users\carlos.ramos\desarrollo\SWB4\swb\SWBAdmin\dist\SWBAdmin.jar" "C:\Users\carlos.ramos\desarrollo\SWB4\SWBBSCom\build\web\WEB-INF\lib\SWBAdmin.jar"
copy "C:\Users\carlos.ramos\desarrollo\SWB4\swb\SWBPortal\dist\SWBPortal.jar" "C:\Users\carlos.ramos\desarrollo\SWB4\SWBBSCom\build\web\WEB-INF\lib\SWBPortal.jar"

copy "C:\Users\carlos.ramos\desarrollo\swbproys\BSCOM_test\dist\BSCOM_test.jar" "C:\Users\carlos.ramos\desarrollo\SWB4\SWBBSCom\build\web\WEB-INF\lib\BSCOM_test.jar"

"C:\jdk1.7.0_09\bin\"java -Xss256k -Xms24m -Xmx512m -Xverify:none -Dfile.encoding=8859_1 -Djava.endorsed.dirs=jetty\lib\endorsed -Dwebapp=../../ -Dorg.xml.sax.parser=org.apache.xerces.parsers.SAXParser -Djetty.port=8088 -Djetty.admin.port=8081 -Dloader.jar.repositories=jetty\lib,jetty\lib\endorsed -Dloader.main.class=org.mortbay.jetty.Server Loader jetty\conf\main.xml jetty\conf\admin.xml