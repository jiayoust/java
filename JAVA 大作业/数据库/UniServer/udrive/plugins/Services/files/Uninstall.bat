TITLE UNIFORM SERVER - Stop and Uninstall service
cls
:######################################################################## 
:# File name: uninstall.bat
:# Created By: The Uniform Server Development Team
:# Edited Last By: Mike Gleaves (ric) 
:# V 1.0 22-12-2008
:# MPG V 1.1 28-2-2009 - Get Apache executable name
:########################################################################
@echo off
COLOR B0
mode con:cols=80 lines=20
echo.

rem ## Save return path
pushd %~dp0

echo.
net stop MySQL
net stop Apache2

rem === Get Apache executable file name
FOR /F "tokens=*" %%i in ('dir /B usr\local\apache2\bin\Apache*') do SET Apache_exe=%%i 

usr\local\apache2\bin\%Apache_exe% -k uninstall -n "Apache2"
echo.
echo Uninstalling MySQL Service ...
usr\local\mysql\bin\mysqld-opt.exe --remove MySQL
echo.
echo Now you can copy important data and delete the server root directory
echo.
pause

rem ## Return to caller
popd

