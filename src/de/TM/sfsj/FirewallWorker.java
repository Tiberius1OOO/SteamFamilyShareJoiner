package de.TM.sfsj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FirewallWorker {
    private boolean ruleExist;

    public FirewallWorker() {
        this.ruleExist = false;
    }

    private static boolean doesFirewallRuleExist(String ruleName) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("netsh", "advfirewall", "firewall", "show", "rule", "name=" + ruleName);
            Process process = processBuilder.start();

            // Read the output of the command
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                // Check if the rule name is found in the output
                if (line.contains(ruleName)) {
                    return true;
                }
            }

            // Close the reader and wait for the process to finish
            reader.close();
            int exitCode = process.waitFor();

            // Handle the exit code if needed
            if (exitCode != 0) {
                System.err.println("Error executing command. Exit code: " + exitCode);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        // If we didn't find the rule, return false
        return false;
    }

    public void check(String ruleName) {
        if (doesFirewallRuleExist(ruleName)) {
            System.out.println("The Firewall rule *" + ruleName + "* exists.");
            this.ruleExist = true;

        } else {
            System.out.println("The Firewall rule *" + ruleName + "* doesn't exist yet.");
            this.ruleExist = false;
        }

    }

    public boolean isRuleExist() {
        return ruleExist;
    }

    public void setRuleExist(boolean ruleExist) {
        this.ruleExist = ruleExist;
    }

    @Override
    public String toString() {
        return "FirewallWorker{" +
                "ruleExist=" + ruleExist +
                '}';
    }
}
