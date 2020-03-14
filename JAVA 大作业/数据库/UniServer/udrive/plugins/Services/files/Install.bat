TITLE UNIFORM SERVER - Install and Start service
cls
:######################################################################## 
:# File name: install.bat
:# Created By: The Uniform Server Development Team
:# Edited Last By: Olajide Olaolorun (empirex) 
:# V 1.0 21-11-2008
:# MPG V 1.1 28-2-2009 - Get Apache executable name
:########################################################################
@echo off
COLOR B0
mode con:cols=80 lines=20
echo.

rem ## Save return path
pushd %~dp0

echo.

rem === Get Apache executable file name
FOR /F "tokens=*" %%i in ('dir /B usr\local\apache2\bin\Apache*') do SET Apache_exe=%%i 

echo Installing Apache2 Service ...
rem C:\UniServer\usr\local\apache2\bin\%Apache_exe% -k install -n "Apache2"

C:\UniServer\usr\local\apache2\bin\%Apache_exe% -k install -n "Apache2"
echo.
echo Installing MySQL Service ...
C:\UniServer\usr\local\mysql\bin\mysqld-opt.exe --install MySQL --defaults-file=C:\UniServer\usr\local\mysql\my.cnf

net start Apache2
net start MySQL

echo.
echo Now you can copy important data and use the server!
echo.
pause
start C:\UniServer\home\admin\www\redirect.html

rem ## Return to caller
popd
