TITLE UNIFORM SERVER - Certificate and Key generator
cls
:################################################# 
:# File name: Run.bat
:# Created By: The Uniform Server Development Team
:# Edited Last By: Mike Gleaves (ric) 
:# V 1.0 11-1-2009
:##################################################
@echo off

COLOR B0
mode con:cols=80 lines=48

rem working directory current folder 
pushd %~dp0

rem ## Check for CA control
if exist ..\..\usr\local\apache2\conf\ssl.crt\ca.crt goto :CA

rem ## Check for Server certificate
if NOT exist ..\..\usr\local\apache2\conf\ssl.crt\server.crt goto :CREATE

echo.
echo   Checking for Server certificate ...
echo.
echo   You have already created a server certificate and key!
echo.
echo   Are you sure you want to delete and create new ones?
echo.
set /p check1=  Enter [YES/NO] = 
if NOT "%check1%"=="YES" goto :END
cls

:CREATE
rem ## Not using CA hence create new server cert and key
..\..\usr\bin\perl ssl_gen.pl

goto :END

:CA
echo.
echo  Checking for CA ...
echo
echo  It looks like you are using your own CA to avoid overwriting your
echo  current server certificate and key this script has terminated.
echo.
echo  To create a new server certificate and key use the CA script.
echo.

pause

:END
rem restore original working directory
popd
exit