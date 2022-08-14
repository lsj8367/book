package io.github.lsj8367.item5;

public class Ryzen extends Cpu {

    private String name;

    public Ryzen(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Ryzen{" +
            "name='" + name + '\'' +
            '}';
    }

}
