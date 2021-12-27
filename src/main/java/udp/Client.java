package udp;

import javax.swing.*;
import java.awt.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;
import java.util.StringTokenizer;

public class Client {
    public static int clientPort = 999;
    public static int buffer_size = 512;
    public static DatagramSocket ds;
    public static byte[] buffer = new byte[buffer_size];

    public static void TheClient() throws Exception {
        while (true) {
            DatagramPacket p = new DatagramPacket(buffer, buffer.length);
            ds.receive(p);
            int length = p.getLength();
            buffer = p.getData();
            if(length == -1)
                break;
            String str = new String(buffer, 0, buffer.length, StandardCharsets.UTF_8);
            StringTokenizer st;
            st = new StringTokenizer(
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
                    Client.paint(g, finalStr);
                }
            };

        }
    }
    public static void main(String[] args) throws Exception {
        ds = new DatagramSocket(clientPort);
        TheClient();
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