import java.net.*;
import java.io.*;
public class rclient{
public static void main(String args[]) 
{
Socket servsoc=null;
BufferedReader in=null;
BufferedReader key=null;
PrintStream out=null;
String str=null;
String skey=null;
try{
InetAddress address=InetAddress.getLocalHost();
System.out.println(address);
servsoc=new Socket(address,3500);
in=new BufferedReader(new InputStreamReader(servsoc.getInputStream()));
out=new PrintStream(servsoc.getOutputStream());
key=new BufferedReader(new InputStreamReader(System.in));
str=in.readLine();
System.out.println("client from server"+str);
System.out.println("Enter the message <End- to Quit>");
while(true)
{
skey=key.readLine();
out.println(skey);
in=new BufferedReader(new InputStreamReader(servsoc.getInputStream()));
str=in.readLine();
System.out.println("From server  : "+str);
out.println("ok");
String s2="End";
if(skey.equals(s2))
break;
}
}
catch(Exception e){System.out.println("connection is not properly");}
}
}

       

