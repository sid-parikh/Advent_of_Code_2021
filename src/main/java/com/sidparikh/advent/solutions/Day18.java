package com.sidparikh.advent.solutions;

import com.sidparikh.advent.Solution;

import java.io.IOException;
import java.util.Objects;

public class Day18 extends Solution {
    public Day18() throws IOException {
        super(18);
    }

    @Override
    public String partOne() {
        Pair test = Pair.of("[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]");

        System.out.println(test);

        test = test.reduce(0);

        System.out.println(test);
       // String test = ;

        // reduce(test.toCharArray());

        return null;

        // return test.toString();
    }

//    public char[] reduce(char[] pair) {
//
//        int depth = 0;
//        for (char c : pair) {
//            int m;
//            if (c == '[') {
//                depth++;
//            } else if (c == ']') {
//                depth--;
//            } else if (c == ',') {
//                continue;
//            } else {
//                m = Integer.parseInt(String.valueOf(c));
//                System.out.println(m);
//            }
//
//            if (depth == 4) {
//
//            }
//        }
//
//        return pair;
//
//
//    }

    @Override
    public String partTwo() {
        return null;
    }

    public static class Pair {
        protected Pair xPair;
        protected Pair yPair;
        protected Pair parent = null;

        public Pair(Pair l, Pair r) {
            this.xPair = l;
            this.yPair = r;
            if (l != null) {
                l.parent = this;
            }
            if (r != null) {
                r.parent = this;
            }
        }

        public static Pair of(String input) {
            Pair left;
            Pair right;
            input = input.substring(1, input.length() - 1);
            int i = 0;

            if (input.charAt(i) != '[') {
                left = new Value(Integer.parseInt(input.substring(i, i + 1)));
            } else {
                // Starting with another pair (not value)
                i = i + 1;
                final int start = i;
                int numOpens = 1;
                int numCloses = 0;
                while (numOpens > numCloses) {
                    switch (input.charAt(i)) {
                        case '[' -> numOpens++;
                        case ']' -> numCloses++;
                    }
                    i++;
                }
                left = Pair.of(input.substring(start - 1, i));
            }

            i = input.indexOf(',', i) + 1;

            if (input.charAt(i) != '[') {
                right = new Value(Integer.parseInt(input.substring(i, i + 1)));
            } else {
                // Starting with another pair (not value)
                i = i + 1;
                final int start = i;
                int numOpens = 1;
                int numCloses = 0;
                while (numOpens > numCloses) {
                    switch (input.charAt(i)) {
                        case '[' -> numOpens++;
                        case ']' -> numCloses++;
                    }
                    i++;
                }
                right = Pair.of(input.substring(start - 1, i));
            }

            return new Pair(left, right);
        }

        public Pair reduce(int parents) {
            if (parents < 4) {
                Objects.requireNonNull(xPair);
                Objects.requireNonNull(yPair);
                xPair = xPair.reduce(parents + 1);
                yPair = yPair.reduce(parents + 1);
                return this;
            } else {
                if (yPair instanceof Value) {

                    Pair p = parent;
                    if (p != null && p.yPair == this) {
                        p = parent.parent;
                    }
                    boolean found = false;
                    while (p != null) {
                        p = p.yPair;
                        if (p instanceof Value ) {
                            ((Value) p).val += ((Value) yPair).val;
                            found = true;
                            break;
                        } else {
                            p = p.parent.parent;
                        }
                    }
                    if (!found) {

                    }
                }
                if (xPair instanceof Value) {
                    Pair p = parent;
                    if (p != null && p.xPair == this) {
                        p = parent.parent;
                    }
                    while (p != null) {
                        p = p.xPair;
                        if (p instanceof Value ) {
                            ((Value) p).val += ((Value) xPair).val;
                            break;
                        } else {
                            p = p.parent.parent;
                        }
                    }
                }

                return new Value(0);
            }
        }

        @Override
        public String toString() {
            return String.format("[%s,%s]", xPair, yPair);
        }

        public static Value getValRight(Pair pair) {
            Pair r = pair.yPair;
            do {
                if (r instanceof Value) {
                    return (Value) r;
                } else {
                    r = pair.yPair;
                }
            } while (r != null);
            return null;
        }
    }



    // Certainly a very strange way to do this
    // Not very good OOP of me but let's try it
    public static class Value extends Pair {
        protected int val;

        public Value() {
            super(null, null);
        }

        public Value(int val) {
            this();
            this.val = val;
        }

        @Override
        public Pair reduce(int parents) {
            if (this.val > 10) {
                return new Pair(new Value((int) Math.floor(this.val / 2.0)),
                        new Value((int) Math.ceil(this.val / 2.0)));
            } else {
                return this;
            }
        }

        @Override
        public String toString() {
            return String.valueOf(val);
        }
    }
}
