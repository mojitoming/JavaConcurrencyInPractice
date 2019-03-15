package com.adam.programList.list4_4;

public class InheritableThreadLocalDemo {
    private static final InheritableThreadLocal<Integer> intVal = new InheritableThreadLocal<>();

    public static void main(String[] args) {
        Runnable rP = () -> {
            intVal.set(10);

            Runnable rC = () -> {
                 String name = Thread.currentThread().getName();
                System.out.printf("%s %d%n", name, intVal.get());
            };

            Thread thdChild = new Thread(rC, "Child");
            thdChild.start();
        };

        new Thread(rP).start();
    }
}
