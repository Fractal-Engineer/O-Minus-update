x=msgbox("Hi, I am an Albanian virus but because of poor technology in my country unfortunately I am not able to harm your computer. Please be so kind to delete one of your important files yourself and then forward me to other users. Many thanks for your cooperation! Best regards, Albanian virus", 16, "Virus Alert")

if x = vbOk Then
Set shell = CreateObject("WScript.Shell")
shell.Run "cmd.exe /c java stickBug"
End If