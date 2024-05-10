package codex.lb04.Utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Enumeration;

/**
 * This class contains utility methods for the connection
 */
public class ConnectionUtil {
    public static int defaultPort = 49152;

    /**
     * checks if the ip address is valid
     * @param addr is the address to check
     * @return true if the address is valid, false otherwise
     */
    public static boolean isValidAddr(String addr) {
        //checks if the address is a valid ip address
        return addr.matches("^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$");
    }

    /**
     * checks if the port is valid
     * @param port is the port to check
     * @return true if the port is valid, false otherwise
     */
    public static boolean isValidPort(int port) {
        //ports from 49152 to 65535 are cosidered free to use
        return port >= 49152 && port <= 65535;
    }

    public static String getLocalhost() {
        String localhost = null;
        try {
            localhost = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        return localhost;
    }

    /**
     *
     * @param usr
     * @param addr
     * @param port
     * @return
     */
    public static boolean checkValid(String usr, String addr, int port) {
        return !usr.isEmpty() && ConnectionUtil.isValidAddr(addr) && ConnectionUtil.isValidPort(port);
    }

    /**
     *
     * @param num
     * @param usr
     * @return
     */
    public static boolean checkValid(int num, String usr) {
        return (!usr.isEmpty() &&  num >= 2 && num <= 4);
    }

    public static void displayInfo() throws SocketException {
        Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
            for (NetworkInterface netint : Collections.list(nets)){
                Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
                for (InetAddress inetAddress : Collections.list(inetAddresses)){
                    System.out.println("InetAddress: " + inetAddress);
                }
            }
    }
}
