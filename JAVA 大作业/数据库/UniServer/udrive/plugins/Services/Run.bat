TITLE UNIFORM SERVER - Install and Run as Service
cls
:######################################################################## 
:# File name: Run.bat
:# Created By: The Uniform Server Development Team
:# Edited Last By: Mike Gleaves (ric) 
:# V 1.0 23-1-2009
:# V 1.1 28-2-2009
:# Check this instance is running
:########################################################################
@echo off
COLOR B0
mode con:cols=100 lines=40
echo.

rem working directory current folder 
pushd %~dp0

if exist ..\..\usr\local\apache2\logs\httpd.pid goto :RUNNING

..\..\usr\bin\perl w2c1.pl
goto to END

:RUNNING
echo.
echo  To successfully install servers as a service
echo  both Apache and MySQL servers must be stopped.
echo  Using either Stop.bat or UniController.exe
echo.
echo  Please close running servers and rerun this script.
echo. 

pause
:END

rem restore original working directory
popd