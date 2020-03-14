TITLE UNIFORM SERVER - Restore MySQL Password
@echo off
cls
rem ##################################################################
rem # Name: Run.bat
rem # Location:  \uniform_server\udrive\plugins\restore_mysql_password
rem # Uses file: \uniform_server\udrive\plugins\restore.sql
rem # Created By: The Uniform Server Development Team
rem # Edited Last By: MPG (ric)
rem # V 1.0 5-1-2009
rem # Restores password back to default (root) and privilages for
rem # user "root" and "pma" back to their defaults 
rem # V 1.1 28-2-2009
rem # Added warning for multi-mysql-servers 
rem ##################################################################
@echo off
COLOR B0
mode con:cols=80 lines=40

rem ## working directory current folder 
pushd %~dp0

echo.
echo  ##############################################################################
echo  #                                                                            #
echo  # Uniform Server: Restores MySQL password back to default                    #
echo  #                                                                            #
echo  #----------------------------------------------------------------------------#
echo  # This script Restores:                                                      #
echo  #                                                                            #
echo  # 1) MySQL password back to the default "root"                               #
echo  #                                                                            #
echo  # 2) Privilages for user "root" and "pma" back to their defaults.            #
echo  #                                                                            #
echo  # Follow the instruction and re-start the servers when prompted              #
echo  #                                                                            #
echo  # NOTE: Close all other MYSQL servers before running this script !!!!        #
echo  #----------------------------------------------------------------------------#
echo.
echo   Are you sure you want to continue?
echo.
set /p check1=  Enter [YES/NO] = 
if NOT "%check1%"=="YES" goto :END

echo.

echo  Checking servers are running ...

if NOT exist ..\..\Install.bat goto :NONSERVICE

rem ============== SERVICE restore =================================================
rem == Stop services regardless
echo  Stopping MySQL service
net stop MySQL 1>> nul 2>&1
echo  Stopping Apache service
net stop Apache2 1>> nul 2>&1

rem == If MySQL was running wait for it to stop 
:RUNNING1
..\..\home\admin\program\unidelay.exe 
..\..\home\admin\program\pskill.exe mysqld-opt.exe
if errorlevel 2 goto :NOTRUNNING1
if not errorlevel 1 goto :RUNNING1

:NOTRUNNING1
echo  Servers not running.
echo  Restoring password.....


rem ## set password file to original
(set /p dummy=root)> ..\..\home\admin\www\mysql_password <nul

rem ## Change working dir to MySQL
CD ..\..\usr\local\mysql\bin

rem ## start mysql server
start mysqld-opt.exe --skip-grant-tables --user=root

rem == Wait for MySQL to start
echo  Starting MySQL server 
:NOTSTARTED
..\..\..\..\home\admin\program\unidelay.exe 
..\..\..\..\home\admin\program\pskill.exe mysqld-opt.exe >nul
if errorlevel 2 goto :NOTSTARTED

echo  Restoring password
rem ## run restore script
mysql < ..\..\..\..\plugins\restore_mysql_password\restore.sql

rem ## Delay and then kill server
..\..\..\..\home\admin\program\unidelay.exe 2
..\..\..\..\home\admin\program\pskill.exe mysqld-opt.exe C
echo  Password restored

rem == Wait for MySQL to stop 
:RUNNING2
..\..\..\..\home\admin\program\unidelay.exe 
..\..\..\..\home\admin\program\pskill.exe mysqld-opt.exe
if errorlevel 2 goto :NOTRUNNING2
if not errorlevel 1 goto :RUNNING2

:NOTRUNNING2

rem == Start services regardless
echo  Starting MySQL service
net start MySQL 1>> nul 2>&1
echo  Starting Apache service
net start Apache2 1>> nul 2>&1

echo.
echo  #----------------------------------------------------------------------------#
echo  # MySQL Password and Privilages restored                                     #
echo  #                                                                            #
echo  # For the new values to be picked up                                         #
echo  #                                                                            #
echo  # Services have been restarted.                                              #
echo  #                                                                            #
echo  ##############################################################################
echo.
pause
popd
EXIT

rem ============== NON SERVICE restore =============================================
:NONSERVICE

rem ## kill mysql regardless
..\..\home\admin\program\pskill.exe mysqld-opt.exe C

rem ## Check servers are running 
if exist ..\..\usr\local\apache2\logs\drive.txt goto :CONTINUE 
echo.
echo  #----------------------------------------------------------------------------#
echo  # Servers are not running!                                                   #
echo  #                                                                            #
echo  # Please start the servers using the batch file.                             #
echo  #                                                                            #
echo  # Then re-run this script.                                                   #
echo  #                                                                            #
echo  ##############################################################################
echo.
pause
exit

:CONTINUE 
echo.
echo  Restoring password.....
echo.

rem ## Get drive letter
SET /P Disk=<..\..\usr\local\apache2\logs\drive.txt


rem ## kill mysql
..\..\home\admin\program\pskill.exe mysqld-opt.exe C

rem ## save current path for stop
set Return=%CD%

rem ## set password file to original
(set /p dummy=root)> ..\..\home\admin\www\mysql_password <nul

%Disk%:
cd \usr\local\mysql\bin

rem ## start mysql server
start mysqld-opt.exe --skip-grant-tables --user=root

\home\admin\program\unidelay.exe 2

rem ## run restore script
mysql < \plugins\restore_mysql_password\restore.sql

\home\admin\program\unidelay.exe 2

\home\admin\program\pskill.exe mysqld-opt.exe C

echo  #----------------------------------------------------------------------------#
echo  # MySQL Password and Privilages restored                                     #
echo  #                                                                            #
echo  # For the new values to be picked up                                         #
echo  #                                                                            #
echo  # Please stop and re-start the servers using the batch files.                #
echo  #                                                                            #
echo  ##############################################################################
echo.

pause

:END
rem restore original working directory
popd
EXIT




