package tcp;

import javax.swing.*;
import java.awt.*;
import java.io.*;
        import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
public class Server
{
    public static void main(String[] args)
    {
        byte[] bKbdInput = new byte[256];
        ServerSocket ss;
        Socket s;
        InputStream is;
        OutputStream os;
        try
        {
            System.out.println("Socket Server Application");
        }
        catch(Exception ioe)
        {
            System.out.println(ioe);
        }
        try
        {
            ss = new ServerSocket(9999);
            s = ss.accept();
            is = s.getInputStream();
            os = s.getOutputStream();
            byte[] buf = new byte[512];
            int length;
            while(true)
            {
                length = is.read(buf);
                if(length == -1)
                    break;
                String str = new String(buf, 0, buf.length, StandardCharsets.UTF_8);
                StringTokenizer st;
                st   = new StringTokenizer(
                        str, "\r\n");
                str = (String) st.nextElement();
                System.out.println(">  " + str);

                String finalStr = str;
                new JFrame() {
                        {
                            setBounds(960, 540, 400, 400);
                            setVisible(true);
                            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        }

                        public void paint(Graphics g) {
                            super.paint(g);
                            Server.paint(g, finalStr);
                        }
                };

                os.write(buf, 0, length);
                os.flush();
            }
            is.close();
            os.close();
            s.close();
            ss.close();
        }
        catch(Exception ioe)
        {
            System.out.println(ioe);
        }
        try
        {
            System.out.println("Press <Enter> to terminate application...");
            System.in.read(bKbdInput);
        }
        catch(Exception ioe)
        {
            System.out.println(ioe);
        }
    }

    public static void paint(Graphics g, String str)
    {
        int angleFromChart;
        int prevAngle = 0;
        int rColor, gColor, bColor;

        g.setColor(Color.white);
        g.fillRect(0, 0, 400,400);
        g.setColor(Color.black);
        g.drawRect(0, 0, 400,400);

        StringTokenizer st = new StringTokenizer(str, " ,\r\n");

        while(st.hasMoreElements())
        {
            rColor = (int)(255 * Math.random());
            gColor = (int)(255 * Math.random());
            bColor = (int)(255 * Math.random());

            g.setColor(new Color(rColor, gColor, bColor));

            angleFromChart = Integer.parseInt((String)st.nextElement());

            g.fillArc(100, 100, 200, 200, prevAngle, angleFromChart);

            prevAngle += angleFromChart;
        }
    }
}
