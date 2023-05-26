package com.example.soal_teknis_gaji_arif.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PajakService {
    public Object hitungPajak(Map<String, Object> body) {
        var country = (String) ((Map<String, Object>) body.get("employee")).get("country");
        var martialStatus = ((Map<String, Object>) body.get("employee")).get("marital status");
        var child = ((Map<String, Object>) body.get("employee")).get("childs");
        var salaryComponents = (List<Map<String, Object>>) body.get("komponengaji");

        var sumSalaryPerMonth = salaryComponents.stream().filter(e -> e.get("type").equals("earning")).mapToInt(e -> (int) e.get("amount")).sum();
        var sumDeductionPerMonth = salaryComponents.stream().filter(e -> e.get("type").equals("deduction")).mapToInt(e -> (int) e.get("amount")).sum();

        var nettSalary = sumSalaryPerMonth - sumDeductionPerMonth;


        return switch (country) {
            case "indonesia" -> tarifPajakIndo(nettSalary, (String) martialStatus, (int) child);
            case "vietnam" -> tarifPajakVietnam(nettSalary, (String) martialStatus);
            default -> "0";
        };
    }

    public int perkawinanIndo(String martialStatus, int child) {
        if (!martialStatus.equals("married")) {
            return 25;
        }

        if (child == 1) {
            return 50;
        }

        return 75;
    }

    public int perkawinanVietnam(String martialStatus) {
        if (!martialStatus.equals("married")) {
            return 15;
        }

        return 30;
    }

    public int tarifPajakIndo(long sumSalary, String martialStatus, int child) {
        double taxPerYear = 0.0;
        long netto = (sumSalary * 12) - (perkawinanIndo(martialStatus, child) * 1000000L);

        if (netto >= 50_000000) {
            taxPerYear += 50_000000 * 0.05;
        }

        if (netto >= 250_000000) {
            taxPerYear += (netto - 50_000000) * 0.10;
        }

        var taxPerMonth = taxPerYear / 12;
        return (int) Math.ceil(taxPerMonth / 1_000) * 1_000;
    }

    public int tarifPajakVietnam(long sumSalary, String martialStatus) {
        double taxPerYear = 0.0;
        long netto = (sumSalary * 12) - (1_000000 * 12) - (perkawinanVietnam(martialStatus) * 1_000000L);

        if (netto >= 50_000000) {
            taxPerYear += 50_000000 * 0.025;
        }

        if (netto >= 250_000000) {
            taxPerYear += (netto - 50_000000) * 0.075;
        }

        var taxPerMonth = taxPerYear / 12;
        return (int) Math.ceil(taxPerMonth / 1_000) * 1_000;
    }
}
