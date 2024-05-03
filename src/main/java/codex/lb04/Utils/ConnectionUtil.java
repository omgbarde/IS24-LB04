package codex.lb04.Utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ConnectionUtil {
    public static int defaultPort = 49153;

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

    public static String getLocalHost() {
        String localhost = null;
        try {
            localhost = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        return localhost;
    }
}
