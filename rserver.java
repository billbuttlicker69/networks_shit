import java.net.*;
import java.io.*;

public class rserver
{
ServerSocket server=null;
Socket clientserver=null;
Thread ServerThread;
PrintStream out;
BufferedReader in;
String strs;
public  rserver()
{
try{
server=new ServerSocket(3500);
clientserver=server.accept();
System.out.println("server-client connnection is established");
out=new PrintStream(clientserver.getOutputStream());
out.println(" welcome");
out.println("Type <Quit> to end");
while(true)
{
in=new BufferedReader(new InputStreamReader(clientserver.getInputStream()));
strs=in.readLine();
System.out.println("server,from client  : "+strs);
out.println("information is received");
String s2="Quit";
if(strs.equals(s2))
break;
}
}
catch (Exception e)
{
System.out.println(e);
}
}
public static void main(String args[])
{ 
new rserver();
}
}

