TITLE UNIFORM SERVER - Move Server
cls
:################################################# 
:# File name: Run.bat
:# Created By: The Uniform Server Development Team
:# Edited Last By: Mike Gleaves (ric) 
:# V 1.0 10-3-2009
:##################################################
@echo off

COLOR B0
mode con:cols=80 lines=41

rem working directory current folder 
pushd %~dp0

rem ## Has this Apache been started
if NOT exist ..\..\usr\local\apache2\logs\httpd.pid goto :NOTSTARTED

echo.
echo  The server is running hence cannot run the script!
echo.
echo  Please stop the servers and rerun this batch file
echo.
pause
goto :END

:NOTSTARTED

rem ## Run move server Perl script
..\..\usr\bin\perl move_server.pl

pause
:END
rem restore original working directory
popd
exit