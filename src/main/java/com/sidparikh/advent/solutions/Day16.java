package com.sidparikh.advent.solutions;

import com.sidparikh.advent.Solution;

import java.math.BigInteger;
import java.util.ArrayList;

public class Day16 extends Solution {
    public Day16() {
        super(16);
    }

    public static int versionSum(String packet) {
        System.out.printf("packet %s\n", packet);

        if (packet.isEmpty() || new BigInteger(packet, 2).equals(BigInteger.valueOf(0))) {
            return 0;
        }

        int version = Integer.parseInt(packet, 0, 3, 2);

        int id = Integer.parseInt(packet, 3, 6, 2);
        int i = 6;

        if (id == 4) {
            // Literal
            // i is now the index of the first 1
            StringBuilder literalBuilder = new StringBuilder();
            while (packet.charAt(i) == '1') {
                // Get 4 characters after the 1
                literalBuilder.append(packet, i + 1, i + 5);
                System.out.println(packet.substring(i + 1, i + 5));
                // Now start after those chars
                i = i + 5;
            }
            // i is now 0, denoting the start of the last group of 4 chars
            literalBuilder.append(packet, i + 1, i + 5);
            System.out.println(packet.substring(i + 1, i + 5));
            System.out.println(literalBuilder);
            //int literalVal = Integer.parseInt(literalBuilder.toString(), 2);
            //System.out.println(literalVal);
            return version + versionSum(packet.substring(i + 5));
        }

        int opType = Integer.parseInt(packet.substring(i, i + 1));
        i++;
        if (opType == 0) {
            // int totalLength = Integer.parseInt(packet, i, i + 15, 2);

            return version + versionSum(packet.substring(i + 15));
        } else if (opType == 1) {
            // int numPackets = Integer.parseInt(packet, i, i + 11, 2);

            return version + versionSum(packet.substring(i + 11));
        }
        return -1;
    }

    public static Packet getNextSubPacket(String processString) {

        if (processString.length() < 6 || new BigInteger(processString, 2).equals(BigInteger.valueOf(0))) {
            return null;
        }

        int id = Integer.parseInt(processString, 3, 6, 2);
        int i = 6;

        System.out.printf("id %d process %s\n", id, processString);

        if (id == 4) {
            if (processString.substring(i).length() < 5) return null;

            // Literal Value
            // i is now the index of the first 1
            StringBuilder literalBuilder = new StringBuilder();
            while (processString.charAt(i) == '1') {
                // Get 4 characters after the 1
                literalBuilder.append(processString, i + 1, i + 5);
                System.out.println(processString.substring(i + 1, i + 5));
                // Now start after those chars
                i = i + 5;
            }
            // i is now 0, denoting the start of the last group of 4 chars
            literalBuilder.append(processString, i + 1, i + 5);
            i = i + 5;
            long literalVal = Long.valueOf(literalBuilder.toString(), 2);
            return new Packet(literalVal, i);
        }

        int opType = Integer.parseInt(processString.substring(i, i + 1));
        i++;
        int maxSubLength = Integer.MAX_VALUE;
        int maxSubPackets = Integer.MAX_VALUE;
        if (opType == 0) {
            maxSubLength = Integer.parseInt(processString, i, i + 15, 2);
            i += 15;
        } else if (opType == 1) {
            maxSubPackets = Integer.parseInt(processString, i, i + 11, 2);
            i += 11;
        }

        if (i >= processString.length()) return null;

        ArrayList<Packet> subPackets = new ArrayList<>();
        int len = i;
        int count = 0;
        Packet p;
        while (count < maxSubPackets && (len - i) < maxSubLength) {
            p = getNextSubPacket(processString.substring(len));
            if (p == null) break;
            len += p.length();
            count++;
            subPackets.add(p);
        }

        if (subPackets.size() == 0) return null;

        if (id == 0) {
            // Sum Packet
            long sum = subPackets.stream().mapToLong(Packet::value).sum();

            return new Packet(sum, len);
        } else if (id == 1) {
            // Product Packet
            long product = 1;
            for (Packet sub : subPackets) {
                product *= sub.value();
            }

            return new Packet(product, len);
        } else if (id == 2) {
            // Minimum Packet
            long min = subPackets.stream().mapToLong(Packet::value).min().orElseThrow();

            return new Packet(min, len);
        } else if (id == 3) {
            // Maximum Packet
            long max = subPackets.stream().mapToLong(Packet::value).max().orElseThrow();

            return new Packet(max, len);
        } else if (id == 5) {
            // Greater Than Packet
            long val = subPackets.get(0).value() > subPackets.get(1).value() ? 1 : 0;

            return new Packet(val, len);
        } else if (id == 6) {
            // Less Than Packet
            long val = subPackets.get(0).value() < subPackets.get(1).value() ? 1 : 0;

            return new Packet(val, len);
        } else if (id == 7) {
            // Equal To Packet
            long val = subPackets.get(0).value() == subPackets.get(1).value() ? 1 : 0;

            return new Packet(val, len);
        }

        throw new IllegalArgumentException();
    }

    @Override
    public String partOne() {
        return null;
//        String hexPacket = input.get(0);
//
//        StringBuilder binPacketBuilder = new StringBuilder(new BigInteger(hexPacket, 16).toString(2));
//
//        int pad = binPacketBuilder.length() % 4;
//        if (pad != 0) pad = 4 - pad;
//        while (pad > 0) {
//            binPacketBuilder.insert(0, "0");
//            pad--;
//        }
//        String binPacket = binPacketBuilder.toString();
//
//
//        return String.valueOf(versionSum(binPacket));
    }

    @Override
    public String partTwo() {
        String hexPacket = input.get(0);

        int i = 0;
        StringBuilder binPacketBuilder = new StringBuilder();

        while (hexPacket.charAt(i) == '0') {
            binPacketBuilder.append("0000");
            i++;
        }

        hexPacket = hexPacket.substring(i);


        String a = new BigInteger(hexPacket, 16).toString(2);
        binPacketBuilder.append(a);

        int pad = binPacketBuilder.length() % 4;
        if (pad != 0) pad = 4 - pad;
        while (pad > 0) {
            binPacketBuilder.insert(0, "0");
            pad--;
        }
        String binPacket = binPacketBuilder.toString();


        return String.valueOf(getNextSubPacket(binPacket).value());
    }

//    public static int processSubPackets(String subPackets) {
//
//    }

    public record Packet(long value, int length) {
    }
}
