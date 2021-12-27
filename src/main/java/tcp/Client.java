package tcp;

import java.io.*;
        import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
public class Client
{
    public static void main(String[] args)
    {
        byte[] bKbdInput = new byte[256];

        Socket s;
        InputStream is;
        OutputStream os;
        try
        {
            System.out.println("Socket Client Application" + "\nEnter degrees or" + " 'quit' to exit...");
        }
        catch(Exception ioe)
        {
            System.out.println(ioe);
        }
        try
        {
            s = new Socket("localhost",9999);
            is = s.getInputStream();
            os = s.getOutputStream();
            byte[] buf = new byte[512];
            int length;
            String str;
            while(true)
            {
                length = System.in.read(bKbdInput);
                if(length != 1)
                {
                    str = new String(bKbdInput, 0, bKbdInput.length, StandardCharsets.UTF_8);
                    StringTokenizer st;
                    st   = new StringTokenizer(
                            str, "\r\n");
                    str = (String) st.nextElement();
                    System.out.println(">  " + str);
                    os.write(bKbdInput, 0, length);
                    os.flush();
                    length = is.read(buf);
                    if(length == -1)
                        break;
                    str = new String(buf, 0, buf.length, StandardCharsets.UTF_8);
                    st = new StringTokenizer(
                            str, "\r\n");
                    str = (String) st.nextElement();
                    System.out.println(">> " + str);



                    if(str.equals("quit"))
                        break;
                }
            }
            is.close();
            os.close();
            s.close();
        }
        catch(Exception ioe)
        {
            System.out.println(ioe);
        }
        try
        {
            System.out.println("Press <Enter> to " + "terminate application...");
            System.in.read(bKbdInput);
        }
        catch(Exception ioe)
        {
            System.out.println(ioe);
        }
    }


}
