' Name: disk_start.vbs
' Location: Uniform Server
' Created By: The Uniform Server Development Team
' Edited Last By: Olajide Olaolorun (empirex)
' Comment: Re-Arranged everything to look nicely. 
' To Developers: Added the MySQL start option.
' MPG: Added auto drive detect and command prompt select 
' MPG: Added UniController detection 
' MPG: Added disk-root support

Dim WSHShell, dir, fso, letter, str1, str2, str3, str4, vFound, runFile
Set fso = CreateObject("Scripting.FileSystemObject") 
Set WSHShell = WScript.CreateObject("WScript.Shell") 

' Check for UniController
' Get process list
Set WshShell = WScript.CreateObject ("WScript.Shell")
Set colProcessList = GetObject("Winmgmts:").ExecQuery ("Select * from Win32_Process")

' Run through list
For Each objProcess in colProcessList
If objProcess.name = "UniController.exe" then
vFound = True
End if
Next

' If UniController running bail out
If vFound then
c=MsgBox("UniController is running!" & vbCR & vbCR & "To run this script close UniController.", vbOKOnly, "UniController Detection") 
WScript.Quit
End if

' Does a start file exist
strFile1= "Server_Start.bat"
strFile2= "usb_server_start.bat"
set objFSO = createobject("Scripting.FileSystemObject")

if objFSO.FileExists(strFile1) Or  objFSO.FileExists(strFile2) then
    if objFSO.FileExists(strFile1) then
     'Locate next available drive letter -- Added MPG
     letter=Asc("z")
     while fso.DriveExists(Chr(letter)+":")
       letter=letter-1
     wend

    ' Build string to display
    str1 = "Use the free drive found click OK or" & vbCR 
    str2 = "Specify a Disk Drive letter then click OK" & vbCR & vbCR 
    str3 = "Note: To abort click Cancel"
    str4 = str1 & str2 & str3
    s=InputBox( str4 ,"Server Disk",UCASE(Chr(letter))) 

    If s = "" Then 
    WScript.Quit 
    End If 
    s=mid(s,1,1) 
    runFile= "Server_Start.bat "

    else
       ' No need to display drive letter option
       Set objFSO = CreateObject("Scripting.FileSystemObject")
       Set objFile = objFSO.GetFile("usb_server_start.bat")
       s = Left(objFSO.GetAbsolutePathName(objFile),1)
       c=MsgBox("Will run servers from root-drive " & s, vbOKOnly, "Server Disk") 
       runFile= "usb_server_start.bat "
    end if
else
 c=MsgBox("Unable to find either of these files:" & vbCR & vbCR & "Server_Start.bat" & vbCR &"Or" & vbCR & "usb_server_start.bat", vbOKOnly, "Start file Detection") 
 WScript.Quit
end if

t=MsgBox("Start the MySQL Database Server?", vbYesNo + vbQuestion, "Database Support") 
If t = vbNo Then 
m=" nomysql" 
Else 
m=" mysql" 
End If 

c=MsgBox("Start a Command Prompt for MySQL commands?", vbYesNo + vbQuestion, "MySQL Command-line Support") 
If c = vbNo Then 
cl="" 
Else 
cl=" console" 
End If 

WSHShell.run runFile&s&m&cl,1,0 

