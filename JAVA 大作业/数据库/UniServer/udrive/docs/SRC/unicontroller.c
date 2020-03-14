//###################################################################
// Copyright 2004 - 2008 The Uniform Server Development Team
// UniController.exe Application, Start and Stop MySQL and Apache servers.
// Date: 3-1-2009
// Version: 1.0 Last edited by MPG(ric)
// Date: 18-1-2009
// Version: 1.1 Last edited by MPG(ric)
// Added code for tray icon
// Version: 1.2 Last edited by MPG(ric)
// Date: 17-2-2009
// Path to unicon.ini corrected for sub-folder level
//###################################################################

#include <stdheaders.h>
#include "unicontrollerres.h"
#include <shellapi.h>
#include <netutils.h>
#include <Tlhelp32.h>

// --- component id's IDC ----------------------------------
#define btn_apache_id      100   //ids any positive integer
#define btn_mysql_id       102
#define btn_all_id         104
#define btn_apanel_id      106
#define btn_about_id       108
#define btn_test_id        110
#define btn_phpmyadmin_id  112
#define txt_drive_id       200
#define text_drive_id      202
#define group_box_id       300

//-- Add for tray icon
#define THIS_CLASSNAME    "US Tray Icon Window 1"
#define THIS_TITLE        "Uniform Server V4-Mona\nUniController V2\nRestore"
enum {
    //  Tray icon ID's
    ID_TRAYICON         = 1,
    APPWM_TRAYICON      = WM_APP,
    APPWM_NOP           = WM_APP + 1,
};

// --- component handles -----------------------------------
HWND hLogo;                // Image logo
HWND heditDrive;           // drive letter text box
HWND hAll;                 // all button
HWND hApanel;              // apanel button
HWND hApache;              // Apache button
HWND hMySQL;               // MySQL button
HWND hphpMyAdmin;          // phpMyAdmin button
HWND hAbout;               // about button
HWND hTest;                // test button
HWND hGroupBox;            // Group box version
HINSTANCE hInst;
HWND hwnd;

// --- component positions ----------------------------------
const int x_shift= 5;         // move all controls x
const int y_shift= 20;        // move all controls y
const int X1 = x_shift+5;
const int X2 = x_shift+110;
const int X3 = x_shift+195;
const int Y1 = y_shift+5;
const int Y2 = y_shift+30;
const int Y3 = y_shift+56;
const int Y4 = y_shift+82;
const int indicator_width =20;  // indicator square

// --- function prototypes ----------------------------------
void clean_str(char *str);               // Removes spaces \n \r from string
bool get_apache_port(void);              // Get Apache port from file
bool get_apache_ssl_port(void);          // Get Apache secure port from file
bool get_mysql_port(void);               // Get MySQL port from file
void create_virtual_drive(void);         // Drive letter already validated
void kill_virtual_drive(void);           // If alloed kill drive
int start_apache(HWND hwnd);             // Start Apache update indicators
int stop_apache(HWND hwnd);              // Stop Apache update indicators
int start_mysql(HWND hwnd);              // Start MySQL update indicators
int stop_mysql(HWND hwnd);               // Stop MySQL update indicators
void all_button(HWND hwnd);              // Change button text
void test_1(HWND hwnd);                  // Port tests and display status
void test_2(HWND hwnd);                  // Apache syntax check
void test_3(HWND hwnd);                  // MySQL command prompt
bool valid_drive_letter(HWND hwnd);      // Check drive is valid
void indicators(HWND hwnd);              // Draw/fill indicators colours
bool port(char *port_str);               // Check port in use returns true if open
bool process_exists(CHAR *pProcessName); // Checks for process by name
bool process_kill(CHAR *pProcessName);   // Kill all processes with identical name.
void close_information(HWND hwnd);       // Display close information
void rem_prop(void);                     // Remove properties on window close
bool ssl_enabled(void);                  // Is SSL enabled in Apache's config file

// --- function prototypes for tray icon -----------------------------
void    AddTrayIcon( HWND hWnd, UINT uID, UINT uCallbackMsg, UINT uIcon, LPSTR pszToolTip );
void    RemoveTrayIcon( HWND hWnd, UINT uID);
void    ModifyTrayIcon( HWND hWnd, UINT uID, UINT uIcon, LPSTR pszToolTip );
HICON   LoadSmallIcon( HINSTANCE hInstance, UINT uID );
void    OnClose( HWND hWnd );
void    OnTrayIconLBtnDblClick( HWND hWnd );

// --- Strings --------------------------------------------------------
char apache_config_file[80]="";        // Path to config file sse WM_CREATE
char apache_pid_file[80]="";           // Path to pid file sse WM_CREATE
char apache_ssl_config_file[80]="";    // Path to ssl config file sse WM_CREATE
char mysql_file[80]="";                // Path to config file see WM_CREATE
char drive_letter_file[80]="";         // Path to file containing drive letter see WM_CREATE
char version[80]="";                   // Uniform Server version
char version_file[80]="";              // Path to fie containing version

// --- variables ---------------------------------------------
TCHAR curWorkingDir[MAX_PATH]=""; // save path where this prog was called from.
TCHAR appPath[MAX_PATH]="";       // stores the fully-qualified path to this file

char u_drive[10]="";              // toplevel "" or portable "udrive\"
const int APP_WIDTH = 238;        // main window width
const int APP_HEIGHT = 163;       // main window height
char apache_port[10]="";          // Port contained in config file default 80
char apache_ssl_port[10]="";      // Port contained in config file default 80
char mysql_port[10]="";           // Port contained in config file default 3306
char create_vdrive[10]="";        // eg subst w: "udrive"
char kill_vdrive[30]="";          // eg subst w: /d
char drive_letter='w';            // default drive letter
bool apache = FALSE;              // Apache running default not running
bool mysql  = FALSE;              // MySQL running default not running
bool virtual_drive  = FALSE;      // Virtual drive created default not created
int j=0;                          // loop counter
char apache_exe[100];             // name of Apache executable default is Apache.exe
                                  // can be overriden from unicon.ini

//***Subclassing**************************************
void SubclassHWnd(HWND hwnd);    // Function to subclass a child window label
LRESULT CALLBACK MyWndProc(HWND hwnd, UINT uMsg, WPARAM wParam, LPARAM lParam);
WNDPROC oldWndProc;              // save old winproc restored after executing MyWndProc
static BOOL State = FALSE;       // status of mouse tracking true if window captured

bool enable_or_disable_apanel=TRUE;  // These are required to prevent a button
bool enable_or_disable_php=TRUE;     // click when label enabled or disabled.
bool enable_or_disable_apache=FALSE; // only required if port in use
bool enable_or_disable_mysql=FALSE;  // only required if port in use
bool enable_or_disable_all=FALSE;    // only required if port in use
//*******************************************************

//====== Main program loop: Window message processing =============================
//=== Step 4: All messages stacked and processed by this function. button clicks etc.

LRESULT CALLBACK WndProc(HWND hwnd, UINT msg, WPARAM wParam, LPARAM lParam)
{
  switch(msg){

   //====================================================
   //Window has been created do any initialisation stuff

   case WM_CREATE:
   {
    RECT rc;
    SystemParametersInfo(SPI_GETWORKAREA, 0, &rc, 0); // Get the usable desktop area

    // Centre the main window on the desktop
    MoveWindow(hwnd
    , ((rc.right - rc.left) - APP_WIDTH) / 2
    , ((rc.bottom - rc.top) - APP_HEIGHT) / 2
    , APP_WIDTH, APP_HEIGHT, false);

    // Determine if servers running at top level of a drive or in a sub-folder
    // set u_drive accordingly top lever "" sub-folder "udrive\" required to
    // build paths. Use existence of a file to do this

    FILE *fp;                      // File pointer
    char input_str[83];            // General input string
    char output_str[83];           // General output string

  if((fp = fopen("udrive\\usr\\local\\apache2\\conf\\httpd.conf", "rt"))==NULL) {
    // Unable to open file assume top level
    strcpy(apache_config_file,"usr\\local\\apache2\\conf\\httpd.conf");
    strcpy(apache_pid_file,"usr\\local\\apache2\\logs\\httpd.pid");
    strcpy(apache_ssl_config_file,"usr\\local\\apache2\\conf\\ssl.conf");
    strcpy(mysql_file,"usr\\local\\mysql\\my.cnf");
    strcpy(drive_letter_file,"usr\\local\\apache2\\logs\\drive.txt");
    strcpy(version_file,"home\\admin\\www\\includes\\.version");
    strcpy(u_drive,"");
  }

  else{
    // able to open file hence sub-folder
    fclose(fp);
    strcpy(apache_config_file,"udrive\\usr\\local\\apache2\\conf\\httpd.conf");
    strcpy(apache_pid_file,"udrive\\usr\\local\\apache2\\logs\\httpd.pid");
    strcpy(apache_ssl_config_file,"udrive\\usr\\local\\apache2\\conf\\ssl.conf");
    strcpy(mysql_file,"udrive\\usr\\local\\mysql\\my.cnf");
    strcpy(drive_letter_file,"udrive\\usr\\local\\apache2\\logs\\drive.txt");
    strcpy(version_file,"udrive\\home\\admin\\www\\includes\\.version");
    strcpy(u_drive,"udrive\\");
  }//end else

    if(!get_apache_port()){ // Get port from config file
      DestroyWindow(hwnd);  // if file does not exist give up
      break;                // end
    }

    if(!get_apache_ssl_port()){ // Get port from config file
      DestroyWindow(hwnd);      // if file does not exist give up
      break;                    // end
    }

    if(!get_mysql_port()){  // Get port from config file
      DestroyWindow(hwnd);  // if file does not exist give up
      break;                // end
    }
   }

   //If the Apache PID file exists and the process is not running chances are
   // the PC switched off before closing servers hence delete PID file.
    FILE *fp;
    if ((fp = fopen(apache_pid_file, "r")) && !process_exists(apache_exe)){
      fclose(fp);
      remove(apache_pid_file);                  // Delete file
    }

   // If the Apache PID file exists and the process is running server was started
   // by it's bat file. Hence cannot run UniController
    if ((fp = fopen(apache_pid_file, "r")) && process_exists(apache_exe)){
      fclose(fp);
      char str1[80];             // Text buffer
      char str2[80];             // Text buffer
      char str3[80];             // Text buffer
      char str4[80];             // Text buffer
      char str5[80];             // Text buffer
      char str6[400];            // Text buffer

      sprintf(str1,"You have already started the server.");
      sprintf(str2,"Note 1: If you started the servers using UniController ");
      sprintf(str3,"             Do NOT stop the servers using Stop.bat otherwise ");
      sprintf(str4,"             MySQL will not properly shut down. ");
      sprintf(str5,"Note 2: UniController is completely independent (Does not use batch files)");


      //== Create final string
      sprintf(str6,"%s \n\n%s \n%s \n%s \n\n%s \n ",str1,str2,str3,str4,str5);

      MessageBox(hwnd, str6 , "Information" ,MB_OK|MB_ICONINFORMATION);

      DestroyWindow(hwnd);  // if file does not exist give up
      break;                // end
    }

    //--- Create Tray Icon
    AddTrayIcon( hwnd, ID_TRAYICON, APPWM_TRAYICON, 0, THIS_TITLE );
    return 0;
   break;
   //=== END WM_CREATE: ========================

   //=== Tray ICON STUFF=====

   case WM_SYSCOMMAND:
   {
      if ((wParam & 0xFFF0) == SC_MINIMIZE){ // Check minimised buttonclicked
        ShowWindow(hwnd, SW_HIDE); // to hide window also removes from program bar
        return 0; // Must return this indicates it was processed
      }
      else{
        return DefWindowProc(hwnd, msg, wParam, lParam); // Retun param not ptocessed
      }                                   // can be called several times hence lockout 
   }//=== END WM_SYSCOMMAND ========================
   break;

   case APPWM_NOP:
      return 0; // Trany icon keeps it happy

   case APPWM_TRAYICON:
      SetForegroundWindow( hwnd ); // Required for icon removal

   switch ( lParam ) {

      case WM_LBUTTONDOWN:
         OnTrayIconLBtnDblClick( hwnd );
         ShowWindow(hwnd, SW_SHOW); // show controller window
         return 0;
      case WM_LBUTTONDBLCLK:
         OnTrayIconLBtnDblClick( hwnd ); // calls dummy function
         return 0;                       // must keep this line
   }
   return 0;
   //=== End Tray ICON STUFF =====

   //***Change button color *****************

   case WM_CTLCOLORSTATIC:
   {
     HWND hwndCt = GetDlgItem(hwnd, GetDlgCtrlID((HWND)lParam) ) ;     // Get child handle using ID

      if (GetProp(hwndCt, "MPGFLAG2")){                // If control has property name attatched
        HBRUSH hBrush = CreateSolidBrush (RGB(0,0,0)); // draw new text and backgound color
        SetBkMode((HDC)wParam, TRANSPARENT);           // acts similar to s mouseover link.
        SetTextColor((HDC)wParam, RGB(255, 255, 255)); // Note: This proprty is attatched only to
        return (LRESULT)hBrush;                        // a moused over label, mouseout removes it
      }

      if (GetProp(hwndCt, "MPGFLAG1")){                      // If control has property name attatched
        HBRUSH hBrush = CreateSolidBrush (RGB(246,243,226)); // draw new text and backgound color
        SetBkMode((HDC)wParam, TRANSPARENT);                 // acts similar to s mouseout link.
        SetTextColor((HDC)wParam, RGB(0,0,0));               // Note: All button labels have this property
        return (LRESULT)hBrush;                              // hence are always re-drawn in this default
      }
   }
   break;
   //*** END Change button color **************

   //==============================================
   //Clickable objects proccessed by this case

   case WM_COMMAND:
   {
        //=== APACHE BUTTON =================
        if (LOWORD(wParam)==btn_apache_id){
          if(enable_or_disable_apache){      // This prevents an extra click
            enable_or_disable_apache=FALSE;  // when enabled or disabled
          }
          else{
           if(!virtual_drive){                     // No vdrive, check validity of supplied drive letter
             if(!valid_drive_letter(hwnd)) break;  // Letter not valid, skip start
           }
           if(apache){           // If Apache running
             stop_apache(hwnd);  // stop it
           }
           else{                 // Not running
             start_apache(hwnd); // start it
           }
          }
        }//=== END APACHE ===================

        //=== MYSQL BUTTON ==================
        if (LOWORD(wParam)==btn_mysql_id){
          if(enable_or_disable_mysql){      // This prevents an extra click
            enable_or_disable_mysql=FALSE;  // when enabled or disabled
          }
          else{
           if(!virtual_drive){                     // No vdrive, check validity of supplied drive letter
             if(!valid_drive_letter(hwnd)) break;  // Letter not valid, skip start
            }
            if(mysql){            // If MySQL running
             stop_mysql(hwnd);    // stop it
            }
            else{                 // Not running
             start_mysql(hwnd);   // start it
            }
          }
        }//=== END MYSQL ====================

        //=== ALL BUTTON =====================================
        if (LOWORD(wParam)==btn_all_id){
          if(enable_or_disable_all){      // This prevents an extra click
            enable_or_disable_all=FALSE;  // when enabled or disabled
          }
          else{
           if(!virtual_drive){                     // No vdrive, check validity of supplied drive letter
             if(!valid_drive_letter(hwnd)) break;  // Letter not valid, skip start
           }
           if(apache && mysql){     // Both servers running
             stop_apache(hwnd);     // stop these
             stop_mysql(hwnd);      // and update button text
             break;
           }
           if(!apache && !mysql){    // Both servers not running
             start_apache(hwnd);     // start these
             start_mysql(hwnd);      // and update button text
             break;
           }
           if(apache && !mysql){     // Only Apache running
             stop_apache(hwnd);      // stop it, update button text
             break;
           }
           if(!apache && mysql){     // Only MySQL running
             stop_mysql(hwnd);       // stop it, update button text
           }
          }

        }
        //=== END ALL ===================================

        //=== APANEL BUTTON ===============================
        // Start Browser and display Apanel
        if (LOWORD(wParam)==btn_apanel_id){

          if(enable_or_disable_apanel){      // This prevents an extra click
            enable_or_disable_apanel=FALSE;  // when enabled or disabled
          }
          else{
           // standard port no need to change
           if (strcmp(apache_port,"80")==0){
             ShellExecute(NULL, "open", "http://localhost/apanel/", "", "", SW_SHOWNORMAL);
           }
           // non-standard port add to url
           else{
             char a_str[80];
             sprintf(a_str,"http://localhost:%s/apanel/",apache_port);
             ShellExecute(NULL, "open", a_str, "", "", SW_SHOWNORMAL);
           }
          }
        }
        //=== END APANEL ===================================

        //=== phpMyAdmin BUTTON ===============================
        // Start Browser and display Apanel
        if (LOWORD(wParam)==btn_phpmyadmin_id){

          if(enable_or_disable_php){      // This prevents an extra click
            enable_or_disable_php=FALSE;  // when enabled or disabled
          }
          else{
           // standard port no need to change
           if (strcmp(apache_port,"80")==0){
             ShellExecute(NULL, "open", "http://localhost/apanel/phpMyAdmin/", "", "", SW_SHOWNORMAL);
           }
           // non-standard port add to url
           else{
             char a_str[80];
             sprintf(a_str,"http://localhost:%s/apanel/phpMyAdmin/",apache_port);
             ShellExecute(NULL, "open", a_str, "", "", SW_SHOWNORMAL);
           }
           }
        }
        //=== END phpMyAdmin ===================================

        //=== ABOUT BUTTON ===============================
        // Display some information
        if (LOWORD(wParam)==btn_about_id){
        MessageBox(hwnd,"UniController - V2.0 "
                "\n\nCopyright: Uniform Server Development Team"
                "\n\nhttp://www.uniformserver.com"
		, "About - UniController" ,MB_OK);
        }
        //=== END ABOUT ===================================

        //=== TEST BUTTON =========================
        // Check server ports display status
        // Run Apache Syntax check
        // Optional start MySQL console command window

        if (LOWORD(wParam)==btn_test_id){
          test_1(hwnd);  // Perform port test and displays status
          test_2(hwnd);  // Apache syntax check - option
          test_3(hwnd);  // Open command window - option
        }
      }// === END WM_COMMAND ===================================
      break;

   //==================================================
   //== Draw and fill indicators
   case WM_PAINT:
      indicators(hwnd); // Update indicators re-draw
      return 0L;        // Required after a paint
   break;
   //=== END PAINT ====================================

   //=========================================================
   // Confirm user wants to close and kill the servers
   case WM_CLOSE:
     close_information(hwnd); //Display close information and option
                              // Includes Tray icon close
   //=== END WM_CLOSE =======================================
   break;

   //==========================================
   // This is the last message to be proceesed
   // kills the application window.
   case WM_DESTROY:
     PostQuitMessage(0);
   break;
   //=== END WM_DESTROY =======================

   //=== default ==========================================
   // Finished case list nothing processed return control
   default:
     return DefWindowProc(hwnd, msg, wParam, lParam);
  }
 return 0;
}

//==== MAIN ENTRY POINT ==============================================
//=== Step 1: Create app class and register
int WINAPI WinMain(HINSTANCE hInstance, HINSTANCE hPrevInstance,LPSTR lpCmdLine, int nCmdShow)
{
  //*** PUSH *************************************************************************
  // SetCurrentDirectory expects last character to be a backslash
  // and string null-terminated hence this code for push

  GetCurrentDirectory(MAX_PATH,curWorkingDir);        // Will be restored when prog ends

  GetModuleFileName(0, appPath, sizeof(appPath) - 1); // get path of the executable
  char *appDir = strrchr(appPath, '\\');              // get a pointer to last backslash
  if(appDir){                                         // if backslash found
    ++appDir;                                         // increment pointer
    *appDir = 0;                                      // null terminate the string
    SetCurrentDirectory(appPath);                     // Set this files directory
  }
  //*** END PUSH ************************************************************************

    WNDCLASSEX wc;
    MSG Msg;
    hInst = hInstance; // Save it is used throughout the application

    //Create the Window Class
    wc.cbSize        = sizeof(WNDCLASSEX);
    wc.style         = 0;
    wc.lpfnWndProc   = WndProc;
    wc.cbClsExtra    = 0;
    wc.cbWndExtra    = 0;
    wc.hInstance     = hInstance;
    wc.hIcon         = LoadIcon(hInstance, MAKEINTRESOURCE(uslogo)); //LoadIcon(NULL, IDI_APPLICATION);
    wc.hCursor       = LoadCursor(NULL, IDC_ARROW);
    wc.hbrBackground = GetSysColorBrush(COLOR_BTNFACE);
    wc.lpszMenuName  = NULL;
    wc.lpszClassName = "Unicon";
    wc.hIconSm       = LoadIcon(hInstance, MAKEINTRESOURCE(uslogo)); //LoadIcon(NULL, IDI_APPLICATION);

    // Register the Window Class wc
    if(!RegisterClassEx(&wc))
    {
        MessageBox(NULL, "Window Registration Failed!", "Error!",
            MB_ICONEXCLAMATION | MB_OK);
        return 0;
    }

    //=== Get settings from optional unicon.ini file ====================================
    // If file not found defaults are used
    // INI file required if running more than one server

    char cur_dir[1024];                 // buffer
    char ini_file[1024];                // buffer
    GetCurrentDirectory(1024, cur_dir); // get current path to create full path

    //MessageBox(NULL, cur_dirtest , "MPG TEST1",MB_OK);

  // Check for toplevel
  FILE *fptop;                      // File pointer
  if((fptop = fopen("udrive\\usr\\local\\apache2\\conf\\httpd.conf", "rt"))==NULL) {
    // Unable to open file assume top level
    strcpy(u_drive,"");
  }
  else{
    // able to open file hence sub-folder
    fclose(fptop);
    strcpy(u_drive,"udrive\\");
  }//end else

    //create full path string
    sprintf(ini_file, "%s\\%susr\\local\\apache2\\bin\\unicon.ini", cur_dir,u_drive);

    // get ini settings

    GetPrivateProfileString("APACHE", "apache_exe", "Apache.exe", apache_exe, 100, ini_file);

    //===== END  Get settings from optional unicon.ini file ================================


//=== Step 2: Create main app Window
// Modify window style remove THICKFRAME & WS_MAXIMIZEBOX this prevents resizing
// Force window to top. Prevents it being hidden by browser if apanel started.

    DWORD windowstyle = WS_OVERLAPPEDWINDOW & ~WS_THICKFRAME & ~WS_MAXIMIZEBOX; //prevents window resizing

    hwnd = CreateWindowEx(
        WS_EX_TOPMOST,         //Pushes window to top of screen was WS_EX_CLIENTEDGE,
        "Unicon",              // class
        "UniController",       // title bar
        windowstyle,           // modified style was WS_OVERLAPPEDWINDOW,
        CW_USEDEFAULT, CW_USEDEFAULT, APP_WIDTH, APP_HEIGHT,
        NULL, NULL, hInstance, NULL);

    // Check window created
    if(hwnd == NULL)
    {
        MessageBox(NULL, "Window Creation Failed!", "Error!",
            MB_ICONEXCLAMATION | MB_OK);
        return 0;
    }

//=== Step 3: Add components (windows) to main window ===================

   // Group box

    hGroupBox = CreateWindow("BUTTON", "", WS_CHILD | WS_VISIBLE | BS_GROUPBOX,
    5,5,221,123,
    hwnd, (HMENU)group_box_id, hInstance, NULL);

    // Uniform Server logo Static Bitmap Code:
    hLogo = CreateWindowEx (WS_EX_STATICEDGE, "STATIC", NULL,
    WS_CHILD | WS_VISIBLE | SS_BITMAP, X1, Y1-20, 0, 0, hwnd, 0, hInst, NULL);

    HBITMAP hImageLogo = (HBITMAP)LoadImage(GetModuleHandle(NULL),MAKEINTRESOURCE(logo), IMAGE_BITMAP,0,0,0);
    SendMessage(hLogo, STM_SETIMAGE, IMAGE_BITMAP, (LPARAM)hImageLogo);

    // Create a label - "Change Drive"
    CreateWindowEx(0,
        "STATIC", "Change Drive",
        WS_CHILD|WS_VISIBLE, X2+8,Y3+2,80,20,
        hwnd,(HMENU)text_drive_id,NULL,0);

    //======create buttons
    hApache = CreateWindowEx(
       0,"STATIC", "Start Apache",WS_CHILD|WS_VISIBLE|SS_NOTIFY|SS_CENTER|SS_CENTER|WS_BORDER,
       X2,Y1,80,20,hwnd,(HMENU)btn_apache_id,NULL,0);

    hMySQL = CreateWindowEx(
       0,"STATIC", "Start MySQL",WS_CHILD|WS_VISIBLE|SS_NOTIFY|SS_CENTER|WS_BORDER,
       X2,Y2,80,20,hwnd,(HMENU)btn_mysql_id,NULL,0);

    hAll = CreateWindowEx(
       0,"STATIC", "Start All",WS_CHILD|WS_VISIBLE|SS_NOTIFY|SS_CENTER|WS_BORDER,
       X1,Y2,100,20,hwnd,(HMENU)btn_all_id,NULL,0);

    // bottom row

    hApanel = CreateWindowEx(
       0,"STATIC", "Apanel",WS_CHILD|WS_VISIBLE|SS_NOTIFY|SS_CENTER|WS_BORDER,
       X1,Y3,100,20,hwnd,(HMENU)btn_apanel_id,NULL,0);

    enable_or_disable_apanel=TRUE;
    EnableWindow(hApanel, FALSE );  // Apache not running disable apanel button

     hphpMyAdmin = CreateWindowEx(
       0,"STATIC", "phpMyAdmin",WS_CHILD|WS_VISIBLE|SS_NOTIFY|SS_CENTER|WS_BORDER,
       X1,Y4,100,20,hwnd,(HMENU)btn_phpmyadmin_id,NULL,0);

    enable_or_disable_php=TRUE;
    EnableWindow(hphpMyAdmin, FALSE );  // Apache and MySQL not running disable button

      hAbout = CreateWindowEx(
       0,"STATIC", "About",WS_CHILD|WS_VISIBLE|SS_NOTIFY|SS_CENTER|WS_BORDER,
       X2,Y4,50,20,hwnd,(HMENU)btn_about_id,NULL,0);

      hTest = CreateWindowEx(
       0,"STATIC", "Test",WS_CHILD|WS_VISIBLE|SS_NOTIFY|SS_CENTER|WS_BORDER,
       X2+55,Y4,50,20,hwnd,(HMENU)btn_test_id,NULL,0);

    //create text input window for virtual drive letter
    heditDrive = CreateWindowEx(0,
    	  "EDIT", "w",
    	  WS_CHILD|WS_VISIBLE|WS_BORDER|SS_CENTER, X3,Y3,20,20,
    	  hwnd,(HMENU)txt_drive_id,NULL,0);

    SetFocus(heditDrive);

   // Sub-class labels, allows mouse capture and tracking for each label
   SubclassHWnd(hAll);
   SubclassHWnd(hApanel);
   SubclassHWnd(hApache);
   SubclassHWnd(hMySQL);
   SubclassHWnd(hphpMyAdmin);
   SubclassHWnd(hAbout);
   SubclassHWnd(hTest);

   //=== Change fonts ====
   HFONT hfFont;
   HFONT hf2Font;
   HFONT hf3Font;

   // new font
   hfFont = CreateFont(16, 5, 0, 0, 500,
     TRUE, 0, 0, ANSI_CHARSET,
     OUT_DEFAULT_PRECIS,
     CLIP_DEFAULT_PRECIS,
     DEFAULT_QUALITY,
     DEFAULT_PITCH,
     "verdana");

   // Apply to the following:
   SendDlgItemMessage(hwnd, btn_apache_id, WM_SETFONT, (WPARAM )hfFont,TRUE);
   SendDlgItemMessage(hwnd, btn_mysql_id, WM_SETFONT, (WPARAM )hfFont,TRUE);
   SendDlgItemMessage(hwnd, btn_all_id, WM_SETFONT, (WPARAM )hfFont,TRUE);

   //new font
   hf2Font = CreateFont(16, 5, 0, 0, 500,
     0, 0, 0, ANSI_CHARSET,
     OUT_DEFAULT_PRECIS,
     CLIP_DEFAULT_PRECIS,
     DEFAULT_QUALITY,
     DEFAULT_PITCH,
     "verdana");

   // Apply to the following:
   SendDlgItemMessage(hwnd, text_drive_id, WM_SETFONT, (WPARAM )hf2Font,TRUE);
   SendDlgItemMessage(hwnd, btn_phpmyadmin_id, WM_SETFONT, (WPARAM )hf2Font,TRUE);
   SendDlgItemMessage(hwnd, btn_apanel_id, WM_SETFONT, (WPARAM )hf2Font,TRUE);
   SendDlgItemMessage(hwnd, btn_about_id, WM_SETFONT, (WPARAM )hf2Font,TRUE);
   SendDlgItemMessage(hwnd, btn_test_id, WM_SETFONT, (WPARAM )hf2Font,TRUE);
   SendDlgItemMessage(hwnd, txt_drive_id, WM_SETFONT, (WPARAM )hf2Font,TRUE);

//new font
   hf3Font = CreateFont(16, 8, 0, 0, 500,
     0, 0, 0, ANSI_CHARSET,
     OUT_DEFAULT_PRECIS,
     CLIP_DEFAULT_PRECIS,
     DEFAULT_QUALITY,
     DEFAULT_PITCH,
     "Trebuchet MS");

   SendDlgItemMessage(hwnd, group_box_id, WM_SETFONT, (WPARAM )hf3Font,TRUE);

   //=== END FONTS =====


   //=== Update label version =========================================
   char text_buff[50];
   FILE *fp;

   if((fp = fopen(version_file, "rt"))==NULL) {
     MessageBox(NULL,"Version file does not exist." ,"Version Fie" ,MB_OK);
   }
   else{
    fgets(version, 80, fp);    // get a line of text
    clean_str(version);        // remove spaces and new-line
    fclose(fp);
   }

   sprintf(text_buff,"                         Server %s",version);
   SetDlgItemText(hwnd, group_box_id, text_buff);

   //=== End Update label version =======================================


    //== Auto scan ===============================================================
    // Checks for first free drive and offer that to a user. This can be changed
    // by a user using the above text box.

    if(strcmp(u_drive,"udrive\\")==0){  // its a sub-folder not top-level scan

     //=== Find free drive letter
     char s[10];                        // Drive detection - buffer for create drive
     char driveLetter=' ';              // Drive detection - any drive letter
     char freeDriveLetter=' ';          // Drive detection - first free drive found


     //= scan through drives Z to C
     for (driveLetter='Z'; driveLetter >='C';driveLetter--) {
       sprintf(s,"%c:\\",driveLetter);    // Create drive with format eg 'C:\'

       if(GetDriveType(s)==1){            // Is drive free
        freeDriveLetter = driveLetter;    // yes save to free drive letter
        break;                            // exit for-loop nothing else to do
       }
     }
     drive_letter=freeDriveLetter;        // update drive letter

     //test MessageBox(hwnd,drive_letter,"Close Servers",0);
     //=== Update text box with detected free drive letter
     SetDlgItemText(hwnd, txt_drive_id, &drive_letter);
     }
    else{                            // it is top-level, use current drive letter
     TCHAR path_str[MAX_PATH]="";    // create string to hold path
     GetModuleFileName(0, path_str, sizeof(appPath) - 1); // get path of the executable
     drive_letter=path_str[0];                            // get drive letter
     SetDlgItemText(hwnd, txt_drive_id, &drive_letter);   //display it
     EnableWindow(heditDrive, FALSE ); // Drive leter not selectable for top-level
     virtual_drive=TRUE;               // Not a virtual drive however must set this flag
     SetDlgItemText(hwnd, text_drive_id, "Current Drive");
    }
    //=== END Auto scan ===========================================================

    // All required components added, now display and update main window
    ShowWindow(hwnd, nCmdShow);
    UpdateWindow(hwnd);

    //=== Start Window Message Loop ========
    while(GetMessage(&Msg, NULL, 0, 0) > 0)
    {
        TranslateMessage(&Msg);
        DispatchMessage(&Msg);
    }
    return Msg.wParam;
} // === END MAIN =====================================================

//=== Step 5: Create functions.
//===== FUNCTIONS =====================================================

//=== Allbutton logic =================================
//== Adjust button text depending on servers status
void all_button(HWND hwnd){
   if(!mysql && !apache){
      SetDlgItemText(hwnd, btn_all_id, "Start All");
   }
   if(!mysql && apache){
       SetDlgItemText(hwnd, btn_all_id, "Stop Apache");
   }
   if(mysql && !apache){
      SetDlgItemText(hwnd, btn_all_id, "Stop MySQL");
   }
   if(mysql && apache){
       SetDlgItemText(hwnd, btn_all_id, "Stop All");
   }
}//=== End Allbutton logic ==============================

//=== CHECK VALID DRIVE LETTER ========================================================
// Drives A and B are invalid (Not sure what they are now used for)
// Check the user selected drive letter is free. If in use provide a list of free drive
// letters.

bool valid_drive_letter(HWND hwnd){

   //=== If a user selects a drive letter in use provide a list of free drives
   //=== Find list of free drive letters -- used in a test below
   char sbuff[20];                 // Detection drive buffer
   char driveLetter=' ';           // Detection letter variable
   char list_free_drives[30]="";   // List of free drives
   j=0;                            // set loop counter

    //= scan through drives C to Z
    for (driveLetter='C'; driveLetter <='Z';driveLetter++) {
      sprintf(sbuff,"%c:\\",driveLetter);    // Create drive with format eg 'C:\'

      if(GetDriveType(sbuff)==1){            // is drive free
       list_free_drives[j] = driveLetter;    // yes save to array
       j++;
      }
    }

    //test  MessageBox(hwnd, list_free_drives, "No Drive Letter" ,MB_OK);
    //=== END find list=========

   //=== User input letter validaion.
   char string[10];
   GetDlgItemText(hwnd, txt_drive_id, string, 10); // Get user drive letter string

   if(strlen(string)==0){
     MessageBox(hwnd, "Cannot Start Server:  ""\nPlease supply a drive letter  ", "No Drive Letter" ,MB_OK);
     return FALSE;
   }
   if(strlen(string)>=2){
     MessageBox(hwnd, "Cannot Start Server:  ""\nSingle character required  ", "Invalid Number of characters" ,MB_OK);
     return FALSE;
   }

   if(!isalpha(string[0])){
     MessageBox(hwnd, "Cannot Start Server:  ""\nValid Letters are: c-z or C-Z  ", "Invalid Letter" ,MB_OK);
     return FALSE;
   }
   if(string[0] == 'a' || string[0] == 'A' || string[0] == 'b' || string[0] == 'B') {
     MessageBox(hwnd, "Cannot Start Server:  ""\nThese drives are invalid: A - B  ", "Invalid Drive" ,MB_OK);
     return FALSE;
   }

    //=== Input letter is valid check it is available and not in use
    char s[20];                          // Detection drive buffer
    sprintf(s,"%c:\\",string[0]);        // Create drive with format eg 'C:\'
    if(GetDriveType(s)!=1){              // is drive free (1=free)

    char slist[300];
	sprintf(slist,"Cannot Start Server: \n Drive letter selected is in use \n\n These are available:  %s  ",list_free_drives);
     MessageBox(hwnd, slist , "Drive in use" ,MB_OK);
     return FALSE;
    }

    // Drive letter is valid update
    drive_letter=string[0];
    return TRUE;
}//=== End valid_drive_letter =============================================================


//=== INDICATORS ===============================================================
// This function re-paints indicators with appropriate colour dependent on Apache
// and MySQl status flags.

void indicators(HWND hwnd){
  HDC hdc;                   // Set local variables
  PAINTSTRUCT ps;
  HBRUSH hBrush, holdBrush;
  HPEN hPen, holdPen;
  COLORREF apache_color;               //
  COLORREF mysql_color;                //
  COLORREF border_color = RGB(0,0,0);  //

  if(apache){
   apache_color = RGB(0,255,0);
  }
  else{
   apache_color = RGB(255,0,0);
  }
  if(mysql){
   mysql_color = RGB(0,255,0);
  }
  else{
   mysql_color = RGB(255,0,0);
  }

  hdc = BeginPaint(hwnd, &ps);

  hPen = CreatePen(PS_INSIDEFRAME, 1, border_color);
  holdPen = SelectObject(hdc, hPen);
  hBrush = CreateSolidBrush(apache_color);
  holdBrush = (HBRUSH) SelectObject(hdc, hBrush);

  Rectangle(hdc, X3, Y1, X3+indicator_width, Y1+indicator_width);

  hPen = CreatePen(PS_INSIDEFRAME, 1, border_color);
  holdPen = SelectObject(hdc, hPen);
  hBrush = CreateSolidBrush(mysql_color);
  holdBrush = (HBRUSH) SelectObject(hdc, hBrush);

  Rectangle(hdc, X3, Y2, X3+indicator_width, Y2+indicator_width);

  // select, remove and end paint
  SelectObject(hdc, holdBrush);
  SelectObject(hdc, holdPen);
  DeleteObject(hPen);
  DeleteObject(hBrush);
  EndPaint(hwnd, &ps);

}//=== END INDICATORS ===============================================================

//=== CHECK TCP PORT ================================================================
// Set up client structure and attempt connection
// Return true if success, note WSAStartup must be run before calling this function

 BOOL CheckPortTCP(short int dwPort , char*ipAddressStr)
   {
      struct sockaddr_in client;
      int sock;

      client.sin_family      = AF_INET;
      client.sin_port        = htons(dwPort);
      client.sin_addr.s_addr = inet_addr(ipAddressStr);

      sock = (int) socket(AF_INET, SOCK_STREAM, 0);
      return (connect(sock, (struct sockaddr *) &client,sizeof(client)) == 0);
   }
//=== END ===========================================================================

//=== PORT TEST =====================================================================
  bool port(char *port_str){
     WSADATA wsaData;
     int user_port;
     bool return_value;

     user_port=atoi(port_str); // convert string to int
     if(WSAStartup(0x0101, &wsaData) == 0) // Win sock check
     {
         if(CheckPortTCP(user_port,"127.0.0.1")){
             return_value = TRUE; // port in use
             //test MessageBox(NULL,"Port 80 open\n","Start Message title",0);
         }
         else{
             return_value=FALSE; // port free to use
             //test MessageBox(NULL,"Port 80 not open\n","Start Message title",0);
         }
     }
     WSACleanup(); // must clean up
     return return_value;
  }
//=== END PORT TEST ====================================================================

//=== PROCESS EXISTS ======================================================
// Take a snapshot of current processes. Scan list for process name.
// Return true if found.

bool process_exists(CHAR *pProcessName){

  HANDLE hSnap = NULL;                               // handel to snapshot
  PROCESSENTRY32  procEntry={0};                     // entry point

  if((hSnap = CreateToolhelp32Snapshot(TH32CS_SNAPPROCESS, 0)) == INVALID_HANDLE_VALUE){
    return FALSE;                                    // handel could not be opend
  }
  else{                                              // we have an handel
    procEntry.dwSize = sizeof( PROCESSENTRY32 );     // get size
    Process32First(hSnap,&procEntry);                // take snap
    do                                               // walk process list
    {
      if(!strcmpi(procEntry.szExeFile,pProcessName)) // check for process name
      {
        //test MessageBox(NULL,"Application found\n","Start Message title",0);
        CloseHandle(hSnap);                          // snap nolonger required
        return TRUE;                                 // process exists
      }
    }while(Process32Next(hSnap,&procEntry));         // continue walking list
    CloseHandle(hSnap);                              // snap nolonger required
    return FALSE;                                    // end of list and not found
  }//end else
}
//=== END PROCESS EXISTS ==================================================

//=== PROCESS KILL =========================================================
// This function is draconian it scans snapshot list for all occurrences of
// process name and kills all regardless. Similar to original US kill.

bool process_kill(CHAR *pProcessName){

  HANDLE hSnap = NULL;                               // handle to snapshot
  HANDLE hHandle = NULL;                             // handle to open process
  PROCESSENTRY32  procEntry={0};                     // entry point
  DWORD dwExitCode = 0;                              // set exit code
  BOOL return_value=FALSE;

  if((hSnap = CreateToolhelp32Snapshot(TH32CS_SNAPPROCESS, 0)) == INVALID_HANDLE_VALUE){
    return FALSE;                                    // handel could not be opend
  }
  else{                                              // we have an handle
    procEntry.dwSize = sizeof( PROCESSENTRY32 );     // get size
    Process32First(hSnap,&procEntry);                // take snap
    do                                               // walk process list
    {
      if(!strcmpi(procEntry.szExeFile,pProcessName)) // check for process name
      {
        //test MessageBox(NULL,"Application killed\n","Start Message title",0);
        // open the process by ID
        hHandle = OpenProcess(PROCESS_ALL_ACCESS,0,procEntry.th32ProcessID);
        GetExitCodeProcess(hHandle,&dwExitCode);     // get exit code
        TerminateProcess(hHandle,dwExitCode);        // and kill
        return_value = TRUE;                         // process killed
      }
    }while(Process32Next(hSnap,&procEntry));         // continue walking list
    CloseHandle(hSnap);                              // snap no longer required
    return return_value;                             // end of list
  }//end else
}
//=== END PROCESS KILL =========================================================

//******************************* FUNCTIONS *******************************************

//=== CLEAN STRING ===============================================================
// fgets adds a newline, causes problems with compare and inserting as a string.
// This function removes ALLl spaces, new-line and return from a string
// The string is passed as a pointer - hence its contents are change.

void clean_str(char *str){
  char *nl_ptr;                          // Character pointer
  char input_str[83];                    // Could have used the passed str directly
  char output_str[83];                   // However this makes it a little easier
                                         // to understand

  sprintf(input_str,"%s",str);           // copy string into input buffer

  nl_ptr = strrchr(input_str, '\r');     // If string contains "return" character
  if (nl_ptr) *nl_ptr = '\0';            // remove it

  nl_ptr = strrchr(input_str, '\n');     // If string contains new-line character
  if (nl_ptr) *nl_ptr = '\0';            // remove it

  strrepl(input_str," ","",output_str);  // remove all spaces
  sprintf(str,"%s",output_str);          // write string back original location

}//=== END clean_str ===============================================================

//=== Get Apache port from file =======================================================
bool get_apache_port(void){
  FILE *fp;                      // File pointer
  char input_str[83];            // General input string
  char output_str[83];           // General output string

  if((fp = fopen(apache_config_file, "rt"))==NULL) {
    MessageBox(NULL,
     "Unable to open Apache config file httpd.conf \n\nCannot continue! Program will close." ,"Apache file problem" ,MB_OK);
    return FALSE;
  }
  else{
    while(!feof(fp)) {
     fgets(input_str, 80, fp);                            // get a line of text
     clean_str(input_str);                                // remove spaces and new-line
     if(strncmp(input_str,"Listen",6)==0){                // look for string
        strrepl(input_str,"Listen","",output_str);        // replace and output new string
        sprintf(apache_port,"%s",output_str);             // set apache port string
	//test MessageBox(NULL, apache_port , "String" ,MB_OK);
        break; // nothing else to do
     }
    }// get another line of text - while
    fclose(fp);
    return TRUE;
  }//end else
}
//=== End Get Apache port from file ===================================================

//=== Get Apache SSL port from file =======================================================
bool get_apache_ssl_port(void){
  FILE *fp;                      // File pointer
  char input_str[83];            // General input string
  char output_str[83];           // General output string

  if((fp = fopen(apache_ssl_config_file, "rt"))==NULL) {
    MessageBox(NULL,
     "Unable to open Apache SSL config file httpd.conf \n\nCannot continue! Program will close." ,"Apache file problem" ,MB_OK);
    return FALSE;
  }
  else{
    while(!feof(fp)) {
     fgets(input_str, 80, fp);                            // get a line of text
     clean_str(input_str);                                // remove spaces and new-line
     if(strncmp(input_str,"Listen",6)==0){                // look for string
        strrepl(input_str,"Listen","",output_str);        // replace and output new string
        sprintf(apache_ssl_port,"%s",output_str);         // set apache port string
	// test MessageBox(NULL, apache_ssl_port , "String" ,MB_OK);
        break; // nothing else to do
     }
    }// get another line of text - while
    fclose(fp);
    return TRUE;
  }//end else
}
//=== End Get Apache SSL port from file ===================================================

//=== SSL Enabled in Apache's config =======================================================
// Search Apache's config for string
bool ssl_enabled(void){
  FILE *fp;                      // File pointer
  char input_str[83];            // General input string
  bool rt_value = FALSE;

  if((fp = fopen(apache_config_file, "rt"))==NULL) {
    MessageBox(NULL,
     "Unable to open Apache config file httpd.conf \n\n." ,"Apache file problem" ,MB_OK);
    return FALSE;
  }
  else{
    while(!feof(fp)) {
     fgets(input_str, 80, fp);                             // get a line of text
     clean_str(input_str);                                 // remove spaces and new-line
     if(strncmp(input_str,"LoadModulessl_module",20)==0){  // look for string
       rt_value = TRUE;                                    // match found
        break;                                             // nothing else to do
     }
    }// get another line of text - while
    fclose(fp);
    return rt_value;
  }//end else
}
//=== End SSL Enabled in Apache's config ===============================================


//=== Get MySQL port from file ========================================================
bool get_mysql_port(void){
  FILE *fp;                      // File pointer
  char input_str[83];            // General input string
  char output_str[83];           // General output string

  if((fp = fopen(mysql_file, "rt"))==NULL) {
    MessageBox(NULL,
     "Unable to open MySQL config file my.cnf \n\nCannot continue! Program will close." ,"MySQL file problem" ,MB_OK);
    return FALSE;
  }
  else{
    while(!feof(fp)) {
     fgets(input_str, 80, fp);                           // get a line of text
     clean_str(input_str);                               // remove spaces and new-line
     if(strncmp(input_str,"port=",5)==0){                // look for string
        strrepl(input_str,"port=","",output_str);        // replace and output new string
        sprintf(mysql_port,"%s",output_str);             // set mysql port string
	//test MessageBox(NULL, mysql_port , "MySQL String" ,MB_OK);
        break; // nothing else to do
     }
    }// get another line of text
    fclose(fp);
    return TRUE;
   }
}
//=== End Get MySQL port from file =====================================================

//=== Create virtual drive =============================================================
// Create a virtual drive. Drive letter alreay validated, skip if already created
void create_virtual_drive(void){

 if(strcmp(u_drive,"udrive\\")==0){                       // not top level
  if(!virtual_drive){                   //If virtual drive already created skip
    sprintf(create_vdrive, "subst %c: \"udrive\"", drive_letter);  //build string
    _System(create_vdrive, SW_HIDE);                 // hidden create, wait to complete
    virtual_drive = TRUE;                            // set flag
    EnableWindow(heditDrive, FALSE );                // do not allow user to select drive

    // Save drive letter to file may be used by plugins
    FILE *fp;
    if((fp = fopen(drive_letter_file, "w"))==NULL) {
       MessageBox(hwnd, "Unable to open file for write" , "Drive Letter File" ,MB_OK);
    }
    else{
      fprintf(fp,"%c",drive_letter);
    }
    fclose(fp);

    // End save file
  }
 }

 if(strcmp(u_drive,"")==0){                       // top level
    // Save drive letter to file may be used by plugins
    FILE *fp;
    if((fp = fopen(drive_letter_file, "w"))==NULL) {
       MessageBox(hwnd, "Unable to open file for write" , "Drive Letter File" ,MB_OK);
    }
    else{
      fprintf(fp,"%c",drive_letter);
    }
    fclose(fp);

    // End save file
 }
}
//=== End create virtual drive ==========================================================

//=== Kill virtual drive =============================================================
// Kill virtual drive. Make sure servers are not running
void kill_virtual_drive(void){
 if(strcmp(u_drive,"udrive\\")==0){   // sub-folder attempt to kill
  if(!apache && !mysql && virtual_drive){
    remove(drive_letter_file);                           // Delete drive letter file
    sprintf(kill_vdrive, "subst %c: /d", drive_letter);  // build string
    _System(kill_vdrive, SW_HIDE);                       // hidden kill wait to complete
    virtual_drive = FALSE;                               // Killed drive set flag
    EnableWindow(heditDrive, TRUE );                     // allow user to select drive
  }
 }
 else{ // top-level no need to kill
   remove(drive_letter_file);                           // Delete drive letter file
   virtual_drive = TRUE;             // not a vertual dribe however set this flag
   EnableWindow(heditDrive, FALSE ); // not user selectable
 }
}
//=== End kill virtual drive ===========================================================

//=== START APACHE ====================================================
int start_apache(HWND hwnd){


 if(port(apache_port) ||(ssl_enabled() && port(apache_ssl_port))  ){    // Check to see if ports are in use
   // Deal with Apache non-ssl port
   if(port(apache_port)){
     char a_str[150];                 // it is in use
     if(strcmp(apache_port,"80")==0){ // is it the standard port
       sprintf(a_str,"Cannot start server.\n\nPort %s in use by another server.\n\nIIS May be running!\nSkype also causes a problem.\n",apache_port);
     }
     else { // not the standard port hence a general message
       sprintf(a_str,"Cannot start server.\n\nPort %s in use by another server!",apache_port);
     }
     MessageBox(hwnd,a_str,"Apache non SSL",MB_OK|MB_ICONSTOP);
     enable_or_disable_apache=TRUE;
     EnableWindow(hApache, FALSE );  // Kill button cannot start server
     enable_or_disable_all=TRUE;
     EnableWindow(hAll, FALSE );     // Kill button cannot start server
   }
   // Deal with Apache ssl port
   if(ssl_enabled() && port(apache_ssl_port)){
     char a_str[150];        // it is in use
     if(strcmp(apache_ssl_port,"443")==0){ // is it the standard ssl port
       sprintf(a_str,"Cannot start server.\n\nStandard SSL Port %s in use by another server.\n",apache_ssl_port);
     }
     else { // not the standard port hence a general message
       sprintf(a_str,"Cannot start server.\n\nStrange but non-stanard SSL Port %s in use by another server!",apache_ssl_port);
     }
     MessageBox(hwnd,a_str,"Apache SSL",MB_OK|MB_ICONSTOP);
     enable_or_disable_apache=TRUE;
     EnableWindow(hApache, FALSE );  // Kill button cannot start server
     enable_or_disable_all=TRUE;
     EnableWindow(hAll, FALSE );     // Kill button cannot start server
   }
 }// End port(apache_port) ||(ssl_enabled() && port(apache_ssl_port))

 else{
  create_virtual_drive();  // Create virtual drive to run on - skipped if already created.                                       // set flag

  //== Set env allowing drive letter to be picked up - fixes a bug
  //== Not required if combined update applied
   char env1[10]="";
   sprintf(env1,"Disk=%c",drive_letter);
   putenv(env1);
  //== End Set env ================================================

  //== build Apache strings for paths and variables
  //== Note: apache_parameters_3 forces Apache to find libmysql.dll

  char apache_path[100];              // complete path to executable
  char apache_parameters_1[100];      // parameters
  char apache_parameters_2[100];      // parameters
  char apache_parameters_3[100];      // parameters
  char apache_parameters[200];        // parameters
  char apache_working_directory[100]; // top level folder

  sprintf(apache_path,"%c:\\usr\\local\\apache2\\bin\\%s",drive_letter,apache_exe);
  sprintf(apache_parameters_1," -f %c:\\usr\\local\\apache2\\conf\\httpd.conf",drive_letter);
  sprintf(apache_parameters_2," -d %c:\\usr\\local\\apache2\\.",drive_letter);
  sprintf(apache_parameters_3," -C \"loadfile %c:\\usr\\local\\php\\libmysql.dll\"  ",drive_letter);
  sprintf(apache_parameters,"%s%s%s",apache_parameters_1,apache_parameters_2,apache_parameters_3);
  sprintf(apache_working_directory,"%c:\\",drive_letter);

  // Start Apache Server
  ShellExecute( NULL,"",apache_path ,apache_parameters , apache_working_directory , SW_HIDE );

  // update flags and button
  apache = TRUE;                                      // Apache running set flag
  EnableWindow(heditDrive, FALSE );                   // Prevent user changing drive letter
  enable_or_disable_apanel=TRUE;
  EnableWindow(hApanel, TRUE );                       // Apache running enable apanel button
  SetDlgItemText(hwnd, btn_apache_id, "Stop Apache"); // Change button status

  if(apache && mysql){
    enable_or_disable_php=TRUE;
    EnableWindow(hphpMyAdmin, TRUE );  // Both servers running enable button
  }
  else{
    enable_or_disable_php=TRUE;
    EnableWindow(hphpMyAdmin, FALSE );  // Only Apache running disable button
  }

  InvalidateRect(hwnd, NULL, FALSE);     // Force re-paint of indicators
  all_button(hwnd);                     // update all button text
 return 0;
 }
 return 1;
}//=== END START APACHE ===========================================================

//=== STOP APACHE =================================================================
int stop_apache(HWND hwnd){

  // Kill Apache
   process_kill(apache_exe);        // kill "Apache.exe" default can be overridden unicon.ini

  // Update flags and button
  apache = FALSE;
  SetDlgItemText(hwnd, btn_apache_id, "Start Apache"); // update button
  enable_or_disable_apanel=TRUE;
  EnableWindow(hApanel, FALSE );      // Apache not running disable apanelbutton

  enable_or_disable_php=TRUE;
  EnableWindow(hphpMyAdmin, FALSE );  // Both Apache and MySQL required hence disable button

  kill_virtual_drive();               // If allowed kill drive and enable drive selection
  InvalidateRect(hwnd, NULL, FALSE);  // Force re-paint of indicators
  all_button(hwnd);                   // update all button text

  char str[100];
  sprintf(str,"%susr\\local\\apache2\\logs\\httpd.pid",u_drive);
  remove(str);                         // Delete file

  return 0;
}
//=== END STOP APACHE ==============================================================

//=== START MYSQL ==================================================================
int start_mysql(HWND hwnd){
 if(port(mysql_port)){     // Check to see if port in use
    char a_str[120];       // port in use
    sprintf(a_str,"Cannot start server.\n\nPort %s in use by another server!\n",mysql_port);
    MessageBox(hwnd,a_str,"MySQL",MB_OK|MB_ICONSTOP);
    enable_or_disable_mysql=TRUE;
    EnableWindow(hMySQL, FALSE );   // Kill button cannot start server
    enable_or_disable_all=TRUE;
    EnableWindow(hAll, FALSE );     // Kill button cannot start server
 }
 // Port not in use start server
 else{
  create_virtual_drive();  // Create virtual drive to run on - skipped if already created.

  // create path strings
  char mysql_path[100];              // complete path to executable
  char mysql_parameters[100];        // parameters
  char mysql_working_directory[100]; // top level folder

  sprintf(mysql_path,"%c:\\usr\\local\\mysql\\bin\\mysqld-opt.exe",drive_letter);
  sprintf(mysql_parameters," --defaults-file=/usr/local/mysql/my.cnf");
  sprintf(mysql_working_directory,"%c:\\",drive_letter);

  // Start MySQL Server
  ShellExecute( NULL,"",mysql_path ,mysql_parameters , mysql_working_directory , SW_HIDE );

  // Update flags and button
  mysql = TRUE;                                       // MySQL running set flag
  EnableWindow(heditDrive, FALSE );                   // Prevent user changing drive letter
  SetDlgItemText(hwnd, btn_mysql_id, "Stop MySQL");   // Change button status

  if(apache){
    enable_or_disable_php=TRUE;
    EnableWindow(hphpMyAdmin, TRUE );  // Both servers running enable button
  }
  else{
    enable_or_disable_php=TRUE;
    EnableWindow(hphpMyAdmin, FALSE );  // Apache not running disable button
  }

  InvalidateRect(hwnd, NULL, FALSE);    // Force re-paint of indicators
  all_button(hwnd);                    // update all button text
  return 0;
 }
 return 1;
}
//=== END START MYSQL ===============================================================

//=== STOP MYSQL =============================================================
int stop_mysql(HWND hwnd){

  //=== Read password file to obtain password
  FILE *fp;
  char password_string[82];
  char password_file[30]="";
  sprintf(password_file,"%shome\\admin\\www\\mysql_password",u_drive);
  if((fp = fopen(password_file, "rt"))==NULL) {
    sprintf(password_string, "root");               // use default
    MessageBox(hwnd, "Unable to open password file " , "Password File" ,MB_OK);
  }
  else{
    while(!feof(fp)) {
     fgets(password_string, 80, fp);
    }
    fclose(fp);
  }

  // Kill MySQL server
  char mainMySQL2[300]=""; // complete string to kill MySQL
  sprintf(mainMySQL2, "%susr\\local\\mysql\\bin\\mysqladmin.exe --port=%s --user=root --password=%s shutdown",u_drive,mysql_port,password_string);  // use default
  _System(mainMySQL2, SW_HIDE);

  // Update flags and button
  mysql = FALSE;                                       // MySQL stopped set flag
  SetDlgItemText(hwnd, btn_mysql_id, "Start MySQL");   // Change button status

  enable_or_disable_php=TRUE;
  EnableWindow(hphpMyAdmin, FALSE );    // Both servers required hence disable button

  kill_virtual_drive();                 // If allowed kill drive and enable drive selection
  InvalidateRect(hwnd, NULL, FALSE);    // Force re-paint
  all_button(hwnd);                     // update all button text
  return 0;
}
//=== END STOP MYSQL ==========================================================

//=== Test 1 =============================================================================
// Check Apache and MySQL ports. A user may have changed these hence read
// real values from config files.
// If a button was disabled check port status and enable buttons accordingly

void test_1(HWND hwnd){
    get_apache_port();     // get port from config file saved in global variable
    get_apache_ssl_port(); // get port from config file saved in global variable
    get_mysql_port();      // get port from config file saved in global variable

    bool apache_port_flag = port(apache_port);         // Get port status
    bool apache_ssl_port_flag = port(apache_ssl_port); // Get port status
    bool ssl_enabled_flag = ssl_enabled();             //

   //==== Deside what to display to user ======================================
   char str1[100];   // Apache buffer for display string
   char str2[200];   // Apache SSL buffer for display string
   char str3[100];   // MySQl buffer for display string
   char str4[400];  // Finished buffer for display string

   //=== Apache =====
   if (apache_port_flag){    // is port in use
     if(apache){              // yes, is it this server
       sprintf(str1,"Your Apache server port is: %s \n",apache_port);
     }
     else{                    // not this server
       sprintf(str1,"Unable to run Apache another server is using port: %s \n",apache_port);
     }
   }
   else{  //port not in use
     sprintf(str1,"Apache port %s is free to use. \n",apache_port);
   }

   //=== Apache SSL =====
   // Perform this test if SSL enabled in Apache's config
   if (ssl_enabled_flag && apache_ssl_port_flag){    // is port in use
     if(apache){              // yes, is it this server
       sprintf(str2,"Your Apache SSL port is: %s \n",apache_ssl_port);
     }
     else{                    // not this server
       sprintf(str2,"Unable to run Apache another server is using SSL port: %s \n",apache_ssl_port);
     }
   }
   else{  //port not in use
     sprintf(str2,"Apache SSL port %s is free to use. \n",apache_ssl_port);
   }
   // Perform this test if not SSL enabled
   if (!ssl_enabled_flag && apache_ssl_port_flag){    // is port in use
       sprintf(str2,"SSL not enabled. However port %s is being used\nIf you enable SSL the server will not start\nYou need to resolve this.\n",apache_ssl_port);
   }
   if (!ssl_enabled_flag && !apache_ssl_port_flag){    // is port in use
       sprintf(str2,"SSL not enabled. Port %s is free to use\n",apache_ssl_port);
   }


   //=== Enable Buttons if allowed
   if (!apache_port_flag && !ssl_enabled_flag){  // is port in use
     // If window (Apache button) not enabled
     if(!IsWindowEnabled(hApache)){
       enable_or_disable_apache=TRUE; //set flag to prevent extra click
       EnableWindow(hApache, TRUE );  // Enable button was disabled
     }
     // If window (All button) not enabled
     if(!IsWindowEnabled(hApache)){
       enable_or_disable_all=TRUE;    //set flag to prevent extra click
       EnableWindow(hAll, TRUE );     // Enable button was disabled
     }
    }

	if (!apache_port_flag && ssl_enabled_flag && !apache_ssl_port_flag ){  // is port in use
     // If window (Apache button) not enabled
     if(!IsWindowEnabled(hApache)){
       enable_or_disable_apache=TRUE; //set flag to prevent extra click
       EnableWindow(hApache, TRUE );  // Enable button was disabled
     }
     // If window (All button) not enabled
     if(!IsWindowEnabled(hApache)){
       enable_or_disable_all=TRUE;    //set flag to prevent extra click
       EnableWindow(hAll, TRUE );     // Enable button was disabled
     }
    }

   //=== MySQL =======
   if (port(mysql_port)){     // is port in use
     if(mysql){               // yes, is it this server
      sprintf(str3,"Your MySQL server port is:  %s",mysql_port);
     }
     else{                    // not this srver
      sprintf(str3,"Unable to run MySQL another server is using port:  %s",mysql_port);
     }
   }
   else{ // port not in use
     sprintf(str3,"MySQL port %s is free to use. ",mysql_port);

     // If window (MySQL button) not enabled
     if(!IsWindowEnabled(hMySQL)){
       enable_or_disable_mysql=TRUE; //set flag to prevent extra click
       EnableWindow(hMySQL, TRUE );  // Enable button was disabled
    }

     // If window (All button) not enabled
     if(!IsWindowEnabled(hAll)){
       enable_or_disable_all=TRUE; //set flag to prevent extra click
       EnableWindow(hAll, TRUE );  // Enable button was disabled
    }
   }

  //== Create final string
  sprintf(str4,"%s \n%s \n%s",str1,str2,str3);

  //== Display to user
  MessageBox(hwnd, str4 , "Port Status" ,MB_OK|MB_ICONINFORMATION);

}


//=== Test 2 =============================================================================
// If selected by user performs Apache Syntax check.
// Display message and gets user response. IDYES user wants to run Apache syntax check.

void test_2(HWND hwnd){

  if(IDYES == MessageBox(hwnd,
    "Would you like to run a syntax check on"
    "\nApache\'s configuration file!"
    "\n\nFile: httpd.conf"
    "\nFolder: \\usr\\local\\apache2\\conf"
    "\n\nNote: No need to have server running"
    ,"Apache Syntax Check",MB_YESNO|MB_ICONQUESTION))
   {
    create_virtual_drive(); // Required for virtual paths check

    // Hide the application, eliminates problem associated with close button.
    // With the command window open application waits for it to close.
    // If a user clicks the application close button an error message display
    // "application not responding" clearly not true hence hide the app window from user.
    ShowWindow(hwnd,SW_HIDE);

      // Cannot run the syntax command directly because an error kills the command window/
      // Solution write a seperate batch file to disk and run this remove on completion

      //=== Create new batch file and write to disk. ==============
      FILE *fp;                              // File pointer
      char test_file[30]=""; // Location and name of file
      sprintf(test_file,"%susr\\syntax.bat",u_drive);

      if((fp = fopen(test_file, "w"))==NULL) {
        MessageBox(hwnd, "Unable to open file for write" , "Test File" ,MB_OK);
      }
      else{
        fprintf(fp,"\n@echo off\n%c:\ncd usr\\local\\apache2\\bin \n%s -t\necho. \n pause\nexit",drive_letter,apache_exe);
      }
      fclose(fp);

      char sys_str[30]="";
      sprintf(sys_str,"call %s",test_file);
      system(sys_str);                    // Run this batch file, wait for user to close command window
      remove(test_file);                  // Delete file
      ShowWindow(hwnd,SW_SHOWNORMAL);     // Restore window

   }//end selection
}
//=== END Test 2 ========================================================================

//=== Test 3 =============================================================================
// Open a MySQL Console (cmd) window if selected by user
// Display message and get user responce.
// IDYES user wants to run a console window.

void test_3(HWND hwnd){

 if(mysql){
   if(IDYES == MessageBox(hwnd,
      "    Would you like to open a cmd window for MySQL!"
      "\n\n Allows you to run mysqladmin.exe from a command line."
      ,"CMD Window for MySQL",MB_YESNO|MB_ICONQUESTION))
   {
     char s[80]="";
     sprintf(s,"%c: && cd usr\\local\\mysql\\bin && start cmd",drive_letter);
     system(s);
   }//== END if IDYES MySQL cmd window ===============
 }// end if mysql

}
//=== END Test 3 ========================================================================

//=== Close =============================================================================
// This closes program. If servers are running requires confirmation by user
// Allso kils tray icon

void close_information(HWND hwnd){

//=== No servers running - close
  if(!apache && !mysql){
     kill_virtual_drive();               // Kill drive
     rem_prop();                         // remove properties subclassing and pid
     SetCurrentDirectory(curWorkingDir); //-- Restore working directory similar to pop
     OnClose( hwnd );                    // kill tray icon
     DestroyWindow(hwnd);                // Send message to kill window
  }

//=== Apache running
  if(apache && !mysql){
    if(IDYES == MessageBox(hwnd,
     "This will shutdown your Apache server"
     "\n\n         Are you sure!"
     ,"Close Apache Server",MB_YESNO|MB_ICONQUESTION))
	 {
         stop_apache(hwnd);              // stop server
         kill_virtual_drive();           // Kill drive
     rem_prop();                         // remove properties subclassing and pid
     SetCurrentDirectory(curWorkingDir); //-- Restore working directory similar to pop
         OnClose( hwnd );                // kill tray icon
         DestroyWindow(hwnd);            // Send message to kill window
     }// END if IDYES
  }

//=== MySQL running
  if(!apache && mysql){
    if(IDYES == MessageBox(hwnd,
     "This will shutdown your MySQL server"
     "\n\n         Are you sure!"
     ,"Close MySQL Server",MB_YESNO|MB_ICONQUESTION))
	 {
         stop_mysql(hwnd);               // stop server
         kill_virtual_drive();           // Kill drive
     rem_prop();                         // remove properties subclassing and pid
     SetCurrentDirectory(curWorkingDir); //-- Restore working directory similar to pop
         OnClose( hwnd );                // kill tray icon
         DestroyWindow(hwnd);            // Send message to kill window
     }// END if IDYES
  }

//=== Both servers running
  if(apache && mysql){
  if(IDYES == MessageBox(hwnd,
   "   This will shutdown your"
   "\n Apache and MySQL servers."
   "\n\n   Are you sure!"
   ,"Close All Servers",MB_YESNO|MB_ICONQUESTION))
   {
     stop_apache(hwnd);                  // stop server
     stop_mysql(hwnd);                   // stop server
     kill_virtual_drive();               // Kill drive
     rem_prop();                         // remove properties subclassing and pid
     SetCurrentDirectory(curWorkingDir); //-- Restore working directory similar to pop
     OnClose( hwnd );                    // kill tray icon
     DestroyWindow(hwnd);                // Send message to kill window
   }// END if IDYES
  }
}
//=== END Close ========================================================================

//=== Remove properties ====================================================
void rem_prop(void){
  char str[100];
      sprintf(str,"%susr\\local\\apache2\\logs\\httpd.pid",u_drive);
      remove(str);                         // Delete file
      RemoveProp(hAll, "MPGFLAG1");        // remove flag
      RemoveProp(hApanel, "MPGFLAG1");     // remove flag
      RemoveProp(hApache, "MPGFLAG1");     // remove flag
      RemoveProp(hMySQL, "MPGFLAG1");      // remove flag
      RemoveProp(hphpMyAdmin, "MPGFLAG1"); // remove flag
      RemoveProp(hAbout, "MPGFLAG1");      // remove flag
      RemoveProp(hTest, "MPGFLAG1");       // remove flag
}
//=== End Remove properties =================================================

//*** Function to Subclass labels **************************************
void SubclassHWnd(HWND hwndCtl)
{
   // hcndCtrl is the window you want to subclass
   // get old process so it can be reinstated
   // subclass act of inserting MyWndProc

   SetProp(hwndCtl, "MPGFLAG1", (HANDLE) 1); //a dummy property its name acts as flag
                                             // attatched only to button labels.
   oldWndProc = (WNDPROC)GetWindowLong(hwndCtl,GWL_WNDPROC);
   SetWindowLong(hwndCtl, GWL_WNDPROC, (LONG) (WNDPROC) MyWndProc);
}
//*** END Function to Subclass labels **********************************

//***** New Win process ************************************************
// This is the new wndproc for subclassed labels. It detects mouse entry
// and sets mouse trackevent. Adds a flag on mouseover and removes this
// flag on mouse out.

LRESULT CALLBACK MyWndProc(HWND hwndCtl, UINT uMsg, WPARAM wParam, LPARAM lParam)
{
   switch(uMsg){

     case WM_MOUSEMOVE:
     {
         if(!State)
         {
            State=TRUE;
            TRACKMOUSEEVENT TrackMouseEventStruct;
            memset(&TrackMouseEventStruct,
                   0,
                   sizeof(TrackMouseEventStruct));
            TrackMouseEventStruct.cbSize =
               sizeof(TrackMouseEventStruct);
            TrackMouseEventStruct.dwFlags = TME_LEAVE;
            TrackMouseEventStruct.hwndTrack = hwndCtl;
            TrackMouseEvent(&TrackMouseEventStruct);

            SetProp(hwndCtl, "MPGFLAG2", (HANDLE) 1);  //dummy prop, sets flag
            InvalidateRect(hwnd, NULL, FALSE);         // force re-draw controll
         }//end if state
      }//end case
      break;

      case WM_MOUSELEAVE:
        if(State){
          RemoveProp(hwndCtl, "MPGFLAG2");           // remove flag
          InvalidateRect(hwnd, NULL, FALSE);         // Re-draw controll
          State = FALSE;                             // Allow mouse to be recaptured
        }
      break;

      default:
      break;
   }//end uMsg
   //return control to main app window
   return CallWindowProc(oldWndProc, hwndCtl, uMsg, wParam, lParam);
}
//***** END New Win process ********************************************

//===== FUNCTIONS FOR TRAY ICON ==============================================
//*** Function Add an icon to the system trayels **************************************
void AddTrayIcon( HWND hWnd, UINT uID, UINT uCallbackMsg, UINT uIcon, LPSTR pszToolTip )
{
    NOTIFYICONDATA  nid;

    memset( &nid, 0, sizeof( nid ) );

    nid.cbSize              = sizeof( nid );
    nid.hWnd                = hWnd;
    nid.uID                 = uID;
    nid.uFlags              = NIF_ICON | NIF_MESSAGE | NIF_TIP;
    nid.uCallbackMessage    = uCallbackMsg;
    nid.hIcon               = LoadIcon(GetModuleHandle( NULL ), MAKEINTRESOURCE(uslogo));

    strcpy( nid.szTip, pszToolTip );   // Add tool tip to structure
    Shell_NotifyIcon( NIM_ADD, &nid ); // Add iconto tray
}
//*** END Function Add an icon to the system trayels **********************************

//*** Function Small icon *****************************************************
HICON LoadSmallIcon( HINSTANCE hInstance, UINT uID )
{
  return LoadImage( hInstance, MAKEINTRESOURCE( uID ), IMAGE_ICON, 16, 16, 0 );
}
//*** END Function Small icon *************************************************

//*** Function Remove an icon from the system tray.****************************
void RemoveTrayIcon( HWND hWnd, UINT uID )
{
    NOTIFYICONDATA  nid;

    memset( &nid, 0, sizeof( nid ) );

    nid.cbSize  = sizeof( nid );
    nid.hWnd    = hWnd;
    nid.uID     = uID;

    Shell_NotifyIcon( NIM_DELETE, &nid );
}
//*** END Function Remove an icon from the system tray.************************

//*** Function Remove icon from system tray.********
void OnClose( HWND hWnd )
{
    RemoveTrayIcon( hWnd, ID_TRAYICON );
    PostQuitMessage( 0 );
}
//*** END Function Remove icon from system tray.******

//*** Function Send message ********************
void OnTrayIconLBtnDblClick( HWND hWnd )
{
   // Dummay function may expand tray icon!
   // SendMessage( hWnd, WM_COMMAND, ID_ABOUT, 0 );
}
//*** END Function Send message *****************

