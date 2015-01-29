package dk.telenor.service;

import dk.telenor.storage.Company;

import java.util.Date;
import java.util.Map;

/**
 * @author Vladyslav Povediuk.
 */

public class TokenCheckerThread extends Thread {

    private Map<String, Long> tokens = Company.getTokens();

    @Override
    public void run() {
        System.out.println("TokenChecker started");
        Long currentTime = new Date().getTime();
        for (Map.Entry<String, Long> entry : tokens.entrySet()) {
            if (currentTime - entry.getValue() > 60000) {
                tokens.remove(entry.getKey());
                System.out.println("Token " + entry.getKey() + " removed.");
            }
        }

        System.out.println("TokenChecked finished");

    }
}
