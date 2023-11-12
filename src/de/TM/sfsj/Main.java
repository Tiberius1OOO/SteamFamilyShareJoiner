package de.TM.sfsj;

public class Main {

    public static void main(String[] args) {
    FirewallWorker worker = new FirewallWorker();

    worker.check("Steam");
        System.out.println(worker.toString());

    }
}
