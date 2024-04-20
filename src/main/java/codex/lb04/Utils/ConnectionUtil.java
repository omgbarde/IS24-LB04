package codex.lb04.Utils;

public class ConnectionUtil {
    public static String host="localhost";
    public static int defaultPort=49152;

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
}
