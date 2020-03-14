@echo off
rem ###################################################
rem # Name: apanel_stop_servers.bat
rem # Location: udrive\home\admin\program
rem # Created By: The Uniform Server Development Team
rem # Edited Last By: MPG (ric)
rem # V 1.0 19-1-2009
rem # Kills Apache, UniController and Virtual drive
rem # Perl can only kill one before itself is killed
rem # V 1.1 21-1-2009
rem # Kill by name added mySQL clean shut down
rem # V 1.2 28-2-2008 - Target using MySQL port
rem ##################################################

pushd %~dp0

rem ### Get UniController executable name
SET /P Unicon_exe=<unicon.txt

rem === Get Apache executable file name
FOR /F "tokens=*" %%i in ('dir /B \usr\local\apache2\bin\Apache*') do SET Apache_exe=%%i

echo %Apache_exe%>fred.txt
dir > fred2.txt

rem ## Is UniControler running if yes jmp to UNICON
rem    If not assume started by batch files and continue
 
pskill.exe %Unicon_exe% >NUL
if not errorlevel 1 goto :UNICON

rem ## UniController not running Kill Apache, Indirectely kills MySQL
pskill %Apache_exe% c
goto :VIRTUAL

:UNICON
rem ### Unicontroller is running need to kill Unicontroller, Apache and MySQL

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
pskill %Unicon_exe% c
pskill %Apache_exe% c
if exist ..\..\..\usr\local\apache2\logs\httpd.pid del ..\..\..\usr\local\apache2\logs\httpd.pid
rem ## Delete drive letter file.
if exist ..\..\..\usr\local\apache2\logs\drive.txt del ..\..\..\usr\local\apache2\logs\drive.txt


:VIRTUAL
rem ### Check if drive is virtual if it is kill it 

subst | FIND /I "%1\:" >NUL
IF ERRORLEVEL 1 goto :NOTVIRTUAL
IF ERRORLEVEL 0 goto :VIRTUAL

:NOTVIRTUAL
goto :END

rem ## Is virtual drive hence kill it off
:VIRTUAL
subst %1 /d

:END

pushd %~dp0
Exit

