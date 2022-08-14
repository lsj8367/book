package io.github.lsj8367.item5;

public class Utility {

    private static final Intel INTEL = new Intel("intel");
    private static final Ryzen RYZEN = new Ryzen("ryzen");

    private Utility() {
        throw new UnsupportedOperationException();
    }

    public static void intelSpec() {
        System.out.println(INTEL);
    }

    public static void ryzenSpec() {
        System.out.println(RYZEN);
    }

}
