package com.example.soal_teknis_gaji_arif.service;

import com.example.soal_teknis_gaji_arif.country.Indonesia;
import com.example.soal_teknis_gaji_arif.country.Vietnam;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PajakService {
    final Indonesia indonesia = new Indonesia();
    final Vietnam vietnam = new Vietnam();

    public int hitungPajak(Map<String, Object> body) {
        var country = (String) ((Map<String, Object>) body.get("employee")).get("country");
        var martialStatus = ((Map<String, Object>) body.get("employee")).get("marital status");
        var child = ((Map<String, Object>) body.get("employee")).get("childs");
        var salaryComponents = (List<Map<String, Object>>) body.get("komponengaji");

        var sumSalaryPerMonth = salaryComponents
                .stream()
                .filter(e -> e.get("type").equals("earning"))
                .mapToInt(e -> (int) e.get("amount"))
                .sum();
        var sumDeductionPerMonth = salaryComponents
                .stream()
                .filter(e -> e.get("type").equals("deduction"))
                .mapToInt(e -> (int) e.get("amount"))
                .sum();

        var nettSalary = sumSalaryPerMonth - sumDeductionPerMonth;


        return switch (country) {
            case "indonesia" -> indonesia.hitungTarifPajak(nettSalary, (String) martialStatus, (int) child);
            case "vietnam" -> vietnam.hitungTarifPajak(nettSalary, (String) martialStatus, (int) child);
            default -> 0;
        };
    }
}
