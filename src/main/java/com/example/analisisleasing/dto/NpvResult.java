package com.example.analisisleasing.dto;

public class NpvResult {
    private double tingkatDiskonto;
    private double nilaiNPV;

    public NpvResult(double tingkatDiskonto, double nilaiNPV) {
        this.tingkatDiskonto = tingkatDiskonto;
        this.nilaiNPV = nilaiNPV;
    }

    public double getTingkatDiskonto() {
        return tingkatDiskonto;
    }

    public double getNilaiNPV() {
        return nilaiNPV;
    }
}
