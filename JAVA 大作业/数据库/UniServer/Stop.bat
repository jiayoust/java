TITLE UNIFORM SERVER - Stop
COLOR B0
mode con:cols=60 lines=20
@echo off
cls
rem ###################################################
rem # Name: Server_Stop.bat
rem # Location: uniform_server
rem # Created By: The Uniform Server Development Team
rem # Edited Last By: MPG (ric)
rem # V 1.0 19-1-2009
rem # V 1.1 28-2-2009 - Get Apache executable name
rem ##################################################
echo.

rem working directory current folder 
pushd %~dp0

rem ## Bail out if UniControler running
udrive\home\admin\program\pskill.exe UniController.exe >NUL
if not errorlevel 1 goto :UNICON

rem ## Delete drive letter file.
if exist udrive\usr\local\apache2\logs\drive.txt del udrive\usr\local\apache2\logs\drive.txt

rem === Get Apache executable file name
FOR /F "tokens=*" %%i in ('dir /B udrive\usr\local\apache2\bin\Apache*') do SET Apache_exe=%%i 

rem Stop Apache indirectly stops MySQL
udrive\home\admin\program\pskill.exe %Apache_exe% C

rem ### Check for system error jump to Pause allows error message to be displayed.
if errorlevel 2 goto :PAUSE
goto :END

:UNICON
echo.
echo  You are currently running UniController!
echo.
echo  Stop UniController if you wish to use batch file control.

:PAUSE
echo.
pause

:END

rem restore original working directory
popd