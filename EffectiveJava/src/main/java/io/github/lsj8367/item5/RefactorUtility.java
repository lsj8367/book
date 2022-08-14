package io.github.lsj8367.item5;

public class RefactorUtility {

    private final Cpu cpu;

    public RefactorUtility(final Cpu cpu) {
        this.cpu = cpu;
    }

    public void spec() {
        System.out.println(cpu);
    }
}
