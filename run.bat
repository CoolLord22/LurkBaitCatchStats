@ECHO OFF

SET LURKBAIT_DIRECTORY="%userprofile%\\AppData\\LocalLow\\BLAMCAM Interactive\\LurkBait Twitch Fishing\\"
SET FILE_NAME="LurkBaitCatchStats-1.2-SNAPSHOT.jar"

if exist %LURKBAIT_DIRECTORY%\\%FILE_NAME% (
    java -jar %LURKBAIT_DIRECTORY%\\%FILE_NAME%
) else (
	java -jar %FILE_NAME%
)
