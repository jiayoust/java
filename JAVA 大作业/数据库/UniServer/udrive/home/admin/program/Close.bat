@echo off
rem ##################################################
rem # Name: Close_Stop.bat
rem # Location: uniform_server
rem # Created By: The Uniform Server Development Team
rem # Edited Last By: MPG (ric)
rem # V 1.0 21-11-2008
rem # V 1.1 28-2-2008 - Target using MySQL port
rem ##################################################

@echo off
:Define current working directory
pushd %~dp0

set Disk=%1
IF "%Disk%"=="" set Disk=l

if exist ..\..\..\usr\local\apache2\logs\httpd.pid del ..\..\..\usr\local\apache2\logs\httpd.pid
rem ## Delete drive letter file.
if exist ..\..\..\usr\local\apache2\logs\drive.txt del ..\..\..\usr\local\apache2\logs\drive.txt

rem Is MySQL running
%Disk%:\home\admin\program\pskill.exe mysqld-opt.exe
IF ERRORLEVEL 1 goto :NOMYSQL

rem MySQL Is running need to target this one

rem GET MySQL password 
SET /P pass=<..\www\mysql_password

rem Get MySQL port
..\..\..\usr\bin\perl  get_port.pl mysql
set mysqlp=%ERRORLEVEL%

rem Shutdown MySQL
..\..\..\usr\local\mysql\bin\mysqladmin.exe --port=%mysqlp% --user=root --password=%pass% shutdown

:NOMYSQL

:Kill off virtual drive
subst %Disk%: /D
:pause
:Restore original location
popd
exit
