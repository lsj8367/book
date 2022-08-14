package io.github.lsj8367.item5;

public class Intel extends Cpu {

    private String name;

    public Intel(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Intel{" +
            "name='" + name + '\'' +
            '}';
    }

}
