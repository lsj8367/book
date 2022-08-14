package io.github.lsj8367.item5;

public class Main {

    public static void main(String[] args) {
        Utility.intelSpec();
        Utility.ryzenSpec();

        RefactorUtility utility = new RefactorUtility(new Intel("intel"));
        utility.spec();
        utility = new RefactorUtility(new Ryzen("ryzen"));
        utility.spec();
    }

}
