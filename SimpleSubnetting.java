import java.util.*;

public class SimpleSubnetting {

    public static String toBinary(int num) {
        String bin = Integer.toBinaryString(num);
        while (bin.length() < 8) bin = "0" + bin;
        return bin;
    }

    public static String ipToBinary(String ip) {
        String[] parts = ip.split("\\.");
        return toBinary(Integer.parseInt(parts[0])) + "." +
               toBinary(Integer.parseInt(parts[1])) + "." +
               toBinary(Integer.parseInt(parts[2])) + "." +
               toBinary(Integer.parseInt(parts[3]));
    }

    public static String subnetMaskBinary(int cidr) {
        StringBuilder mask = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            if (i < cidr) {
                mask.append("1");
            } else {
                mask.append("0");
            }
            if ((i + 1) % 8 == 0 && i != 31) {
                mask.append(".");
            }
        }
        return mask.toString();
    }

    public static long ipToLong(String ip) {
        String[] parts = ip.split("\\.");
        long res = 0;
        for (int i = 0; i < 4; i++) {
            res = res * 256 + Integer.parseInt(parts[i]);
        }
        return res;
    }

    public static String longToIp(long ip) {
        return (ip >> 24 & 0xFF) + "." +
               (ip >> 16 & 0xFF) + "." +
               (ip >> 8 & 0xFF) + "." +
               (ip & 0xFF);
    }

    public static String longToBinaryIp(long ip) {
        return toBinary((int)(ip >> 24 & 0xFF)) + "." +
               toBinary((int)(ip >> 16 & 0xFF)) + "." +
               toBinary((int)(ip >> 8 & 0xFF)) + "." +
               toBinary((int)(ip & 0xFF));
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Step 1: Input IP
        System.out.print("Enter IP address: ");
        String ip = sc.nextLine();
        System.out.println("Binary IP: " + ipToBinary(ip));

        // Step 2: Input Network Mask
        System.out.print("Enter Network Mask: ");
        int cidr = sc.nextInt();
        System.out.println("Binary Network Mask: " + subnetMaskBinary(cidr));

        // Step 3: Input number of subnets
        System.out.print("Enter number of subnets required: ");
        int numSubnets = sc.nextInt();

        long baseIp = ipToLong(ip);
        int extraBits = (int) Math.ceil(Math.log(numSubnets) / Math.log(2));
        int newCidr = cidr + extraBits;
        int hostsPerSubnet = (int) Math.pow(2, 32 - newCidr);

        for (int i = 0; i < numSubnets; i++) {
            long startIp = baseIp + (long) i * hostsPerSubnet;
            long endIp = startIp + hostsPerSubnet - 1;

            System.out.println("\nThe group " + (i + 1) + " IP address ranges are:");
            System.out.println(longToBinaryIp(startIp) + "\t" + longToIp(startIp));
            System.out.println("to");
            System.out.println(longToBinaryIp(endIp) + "\t" + longToIp(endIp));
        }

        sc.close();
    }
}
