TITLE UNIFORM SERVER - Start
COLOR B0
mode con:cols=60 lines=20
@echo off
cls
rem ###################################################
rem # Name: usb_server_start.bat
rem # Location: uniform_sServer
rem # Created By: The Uniform Server Development Team
rem # Edited Last By: MPG (ric)
rem # V 1.0 21-1-2009
rem # V 1.1 28-2-2009 - Get Apache executable name
rem ##################################################
echo.

rem working directory current folder 
pushd %~dp0

rem set current drive letter
set Disk=%~d0
set Disk=%Disk:~0,1%

rem  Command line parameters:
rem  Server_Start.bat %1 %2 %3
rem  %1 = Drive letter to use - Use a dummy drive letter
rem  %2 = mysql (run MySql server) or nomysql (do not run MySql server)
rem  %3 = console (Open a command prompt at folder MySQL Bin)
rem Note: If using %2 you must set a value for %1
rem Note: If using %3 you must set values for %1 and %2
rem Note: Default, uses drive currentdrive, runs the MySQL server and does not run a command prompt.

rem ## Bail out if UniControler running
home\admin\program\pskill.exe UniController.exe >NUL
if not errorlevel 1 goto :UNICON

rem ## Has this Apache been started
if NOT exist %Disk%:\usr\local\apache2\logs\httpd.pid goto :NOTSTARTED

rem ## It was srarted is it running
SET /P pid=<%Disk%:\usr\local\apache2\logs\httpd.pid
netstat -anop tcp | FIND /I " %pid%" >NUL
IF ERRORLEVEL 1 goto :NOTRUNNING
IF ERRORLEVEL 0 goto :RUNNING

:NOTRUNNING
rem ## Not shutdown using server_stop.bat hence delete files
del %Disk%:\usr\local\apache2\logs\httpd.pid
if exist %Disk%:\usr\local\apache2\logs\drive.txt del %Disk%:\usr\local\apache2\logs\drive.txt

:NOTSTARTED
rem ## Get ports from config files
usr\bin\perl  home\admin\program\get_port.pl apache
set apachep=%ERRORLEVEL%

usr\bin\perl  home\admin\program\get_port.pl ssl
set sslp=%ERRORLEVEL%

usr\bin\perl  home\admin\program\get_port.pl mysql
set mysqlp=%ERRORLEVEL%

rem ## Set initial dummy values for port check
set pass1=found
set pass2=found
set pass3=found

rem ## If ssl module not enabled skip port test
FIND "#LoadModule ssl_module" \usr\local\apache2\conf\httpd.conf >NUL
IF NOT ERRORLEVEL 1  goto :SKIP

rem ## Check for another server on this Secure Apache port
netstat -anp tcp | FIND /I "0.0.0.0:%sslp% " >NUL
IF ERRORLEVEL 1 set pass2=notfound
goto :NOSKIP
:SKIP
echo skiped
set pass2=notfound
:NOSKIP

rem ## Check for another server on this Apache port
netstat -anp tcp | FIND /I "0.0.0.0:%apachep% " >NUL
IF ERRORLEVEL 1 set pass1=notfound

rem ## Check for another server on this MySQL port
netstat -anp tcp | FIND /I "0.0.0.0:%mysqlp% " >NUL
IF ERRORLEVEL 1 set pass3=notfound


if %pass1%==notfound if %pass2%==notfound if %pass3%==notfound goto NOTFOUND
echo.
echo  All three ports must be free to run the servers.
echo.
if %pass1%==notfound echo  Port %apachep%   is free - OK to run Apache server
if %pass1%==found echo  Port %apachep%   in use  - FAIL Cannot run Apache server
if %pass2%==notfound echo  Port %sslp%  is free - OK to run Secure Apache server
if %pass2%==found echo  Port %sslp%  in use  - FAIL Cannot run Secure Apache server
if %pass3%==notfound echo  Port %mysqlp% is free - OK to run MySQL server
if %pass3%==found echo  Port %mysqlp% in use  - FAIL Cannot run MySQL server
echo.
pause
goto END

:NOTFOUND
echo  Port %apachep%   is free - OK to run Apache server
echo  Port %sslp%  is free - OK to run Secure Apache server
echo  Port %mysqlp% is free - OK to run MySQL server


rem ## Save drive letter to file for use by plugins.
(set /p dummy=%Disk%) >usr\local\apache2\logs\drive.txt <nul

rem === Get Apache executable file name
FOR /F "tokens=*" %%i in ('dir /B usr\local\apache2\bin\Apache*') do SET Apache_exe=%%i

rem ### Apache not running. Create variables for paths and programs 

set apachepath=\usr\local\apache2\
set apacheit=%Disk%:%apachepath%bin\%Apache_exe% -f %apachepath%conf\httpd.conf -d %apachepath%.
set programit=%Disk%:\home\admin\program\
set closeit=%programit%close.bat %Disk%

rem ### Provide feedback to user
echo.
echo  The server will use disk %Disk%
echo  To run Apanel use http/127.0.0.1/apanel/
echo                OR
echo  To run Apanel use http/localhost/apanel/
echo.

rem ### Start Apache server
start %programit%uniserv.exe "%apacheit%" "%closeit%"

rem ### Before starting Apanel wait for Apache to start 
rem ### Safety loop counter cnt give up after 60 seconds
echo  Starting Apache ...............

echo.
set /a cnt=0
:next1
set /a cnt+=1
if "%cnt%"=="60" goto :TIMEDOUT
%Disk%:\home\admin\program\unidelay.exe
%Disk%:\home\admin\program\pskill.exe %Apache_exe% > nul
IF ERRORLEVEL 1 goto :next1

%Disk%:\home\admin\program\unidelay.exe 2
rem ### Start Apanel
start %Disk%:\home\admin\www\redirect.html

rem ### Start MySQL server - skip if requested not to start (%2)
if "%2"=="nomysql" goto :NOMYSQL
echo  Starting MySQL ...............

start %Disk%:\usr\local\mysql\bin\mysqld-opt.exe --defaults-file=/usr/local/mysql/my.cnf


:NOMYSQL

rem ### Does user want to run a command prompt, if not END 
if "%3"=="console" goto :CONSOLE
goto :END

:CONSOLE
rem ### User wants to run a command prompt. Opens in MySQL bin
rem ### Allows command clients to be used (musql.exe and mysqladmin.exe ) 

cd %Disk%:\usr\local\mysql\bin
start "UNIFORM SERVER MySQL Command prompt" cmd.exe 
goto :END

:TIMEDOUT
rem ### Apache failed to start. Kill virtual drive. Inform user
subst %Disk%: /D
echo.
echo Unable to start Apache Server
goto :PAUSE

:PROBLEM
echo.
echo The disk %Disk% could not be created!
goto :PAUSE

:UNICON
echo.
echo  You are currently running UniController!
echo.
echo  Stop UniController if you wish to use batch file control.
goto :PAUSE

:RUNNING
echo.
echo  This Apache server is already running.
echo  You can stop the server using Stop.bat

:PAUSE
echo.
pause

:END

rem restore original working directory
popd
EXIT
