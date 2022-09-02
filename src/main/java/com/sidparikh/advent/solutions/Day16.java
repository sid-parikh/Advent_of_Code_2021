/* Sid Parikh. Created on December 16, 2021 for Advent of Code */
package com.sidparikh.advent.solutions;

import com.sidparikh.advent.Solution;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Objects;

public class Day16 extends Solution {
    String binPacket;

    public Day16() throws IOException {
        super(16);
        setup();
    }

    /**
     * Part One Logic
     *
     * @param packet parent packet
     * @return sum of all version numbers of packets within this packet
     */
    public static int versionSum(String packet) {
        if (packet.isEmpty() || new BigInteger(packet, 2).equals(BigInteger.valueOf(0))) {
            return 0;
        }

        int version = Integer.parseInt(packet, 0, 3, 2);

        int id = Integer.parseInt(packet, 3, 6, 2);
        int i = 6;

        if (id == 4) {
            // Jump to the end
            while (packet.charAt(i) == '1') {
                i = i + 5;
            }
            return version + versionSum(packet.substring(i + 5));
        }

        int opType = Integer.parseInt(packet.substring(i, i + 1));

        if (opType == 0) {
            i += 16;
        } else if (opType == 1) {
            i += 12;
        }

        return version + versionSum(packet.substring(i));
    }

    /**
     * Part two logic
     *
     * @param processString starting string
     * @return the first complete packet in the string
     */
    public static Packet getNextSubPacket(String processString) {

        if (processString.length() < 6 || new BigInteger(processString, 2).equals(BigInteger.valueOf(0))) {
            return null;
        }

        int id = Integer.parseInt(processString, 3, 6, 2);
        int i = 6;

        if (id == 4) {
            if (processString.substring(i).length() < 5) {
                return null;
            }

            // Literal Value
            // i is now the index of the first 1
            StringBuilder literalBuilder = new StringBuilder();
            while (processString.charAt(i) == '1') {
                // Get 4 characters after the 1
                literalBuilder.append(processString, i + 1, i + 5);
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

        if (i >= processString.length()) {
            return null;
        }

        ArrayList<Packet> subPackets = new ArrayList<>();
        int len = i;
        int count = 0;
        Packet p;
        while (count < maxSubPackets && (len - i) < maxSubLength) {
            p = getNextSubPacket(processString.substring(len));
            if (p == null) {
                break;
            }
            len += p.length();
            count++;
            subPackets.add(p);
        }

        if (subPackets.size() == 0) {
            return null;
        }

        long val = Long.MIN_VALUE;

        switch (id) {
            case 0 ->
                // Sum Packet
                    val = subPackets.stream().mapToLong(Packet::value).sum();
            case 1 ->
                // Product Packet
                    val = subPackets.stream().mapToLong(Packet::value).reduce((a, b) -> a * b).orElse(1);
            case 2 ->
                // Minimum Packet
                    val = subPackets.stream().mapToLong(Packet::value).min().orElseThrow();
            case 3 ->
                // Maximum Packet
                    val = subPackets.stream().mapToLong(Packet::value).max().orElseThrow();
            case 5 ->
                // Greater Than Packet
                    val = subPackets.get(0).value() > subPackets.get(1).value() ? 1 : 0;
            case 6 ->
                // Less Than Packet
                    val = subPackets.get(0).value() < subPackets.get(1).value() ? 1 : 0;
            case 7 ->
                // Equal To Packet
                    val = subPackets.get(0).value() == subPackets.get(1).value() ? 1 : 0;
        }

        if (val != Long.MIN_VALUE) {
            return new Packet(val, len);
        }

        throw new IllegalArgumentException();
    }

    public void setup() {
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
        if (pad != 0) {
            pad = 4 - pad;
        }
        while (pad > 0) {
            binPacketBuilder.insert(0, "0");
            pad--;
        }
        binPacket = binPacketBuilder.toString();
    }

    @Override
    public String partOne() {
        return String.valueOf(versionSum(binPacket));
    }

    @Override
    public String partTwo() {
        return String.valueOf(
                Objects.requireNonNull(getNextSubPacket(binPacket), "Something very bad has happened.").value());
    }

    public record Packet(long value, int length) {}
}
