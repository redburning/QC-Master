set "LI_HOME=%~dp0%..\"
set "BFC_RUNNRER=%LI_HOME%lib\QC-Master-0.0.1-SNAPSHOT.jar"
set "LB_CONF_PATH=%LI_HOME%conf\application.properties"
set "JNA_TMP_DIR=%LI_HOME%tmp\"
set "MEM=500m"
java -Xms%MEM% -Xmx%MEM% -Djna.tmpdir=%JNA_TMP_DIR% -DQC_Master.home=%LI_HOME% -jar %BFC_RUNNRER% 