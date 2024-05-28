package codex.lb04.Utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

/**
 * This class contains utility methods for the connection
 */
public class ConnectionUtil {
    //default port is set to the first free port
    public static int defaultPort = 49152;

    /**
     * checks if the ip address is valid
     *
     * @param addr is the address to check
     * @return true if the address is valid, false otherwise
     */
    public static boolean isValidAddr(String addr) {
        //checks if the address is a valid ip address
        return addr.matches("^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$");
    }

    /**
     * checks if the port is valid
     *
     * @param port is the port to check
     * @return true if the port is valid, false otherwise
     */
    public static boolean isValidPort(int port) {
        //ports from 49152 to 65535 are cosidered free to use
        return port >= 49152 && port <= 65535;
    }

    /**
     * gets the localhost ip address
     *
     * @return the localhost ip address
     */
    public static String getLocalhost() {
        String localhost;
        try {
            localhost = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        return localhost;
    }

    /**
     * checks if the parameters are valid
     *
     * @param usr  is the username to check
     * @param addr is the address to check
     * @param port is the port to check
     * @return true if the parameters are all valid, false otherwise
     */
    public static boolean checkValid(String usr, String addr, int port) {
        return !usr.isEmpty() && ConnectionUtil.isValidAddr(addr) && ConnectionUtil.isValidPort(port);
    }

    /**
     * checks if the parameters are valid
     *
     * @param num is the number of players
     * @param usr is the username
     * @return true if the parameters are all valid, false otherwise
     */
    public static boolean checkValid(int num, String usr) {
        return (!usr.isEmpty() && num >= 2 && num <= 4);
    }

    /**
     * displays the network interfaces to the console to help the user connect to the server
     *
     * @throws SocketException if an error occurs
     */
    public static void displayInfo() throws SocketException {
        StreamSupport.stream(Spliterators.spliteratorUnknownSize(NetworkInterface.getNetworkInterfaces().asIterator(), Spliterator.ORDERED), false)
                .flatMap(networkInterface -> {
                    return StreamSupport.stream(
                            Spliterators.spliteratorUnknownSize(networkInterface.getInetAddresses().asIterator(), Spliterator.ORDERED), false);
                })
                .filter(inetAddress -> inetAddress instanceof java.net.Inet4Address)
                .forEach(inetAddress -> System.out.println("interface: " + inetAddress));
    }
}
