package com.example.soal_teknis_gaji_arif.country;

public class Indonesia implements CountryInterface {
    @Override
    public int hitungTarifPajak(long sumSalary, String martialStatus, int child) {
        double taxPerYear = 0.0;
        long netto = (sumSalary * 12) - (ptkp(martialStatus, child) * 1000000L);

        if (netto >= 50_000000) {
            taxPerYear += 50_000000 * 0.05;
        }

        if (netto >= 250_000000) {
            taxPerYear += (netto - 50_000000) * 0.10;
        }

        var taxPerMonth = taxPerYear / 12;
        return (int) Math.ceil(taxPerMonth / 1_000) * 1_000;
    }

    @Override
    public int ptkp(String martialStatus, int child) {
        if (!martialStatus.equals("married")) {
            return 25;
        }

        if (child == 1) {
            return 50;
        }

        return 75;
    }
}
