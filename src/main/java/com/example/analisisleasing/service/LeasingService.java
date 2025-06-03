package com.example.analisisleasing.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.analisisleasing.dto.DataLeasingDto;
import com.example.analisisleasing.dto.IRRResult;
import com.example.analisisleasing.dto.NpvResult;

@Service
public class LeasingService {
    public List<NpvResult> hitungNPV(DataLeasingDto dataLeasing) {
        List<NpvResult> daftarHasilNPV = new ArrayList<>();

        double hargaMotor = dataLeasing.getHargaMotor();
        double uangMuka = dataLeasing.getDp();
        double cicilanBulanan = dataLeasing.getCicilan();
        int jumlahBulan = dataLeasing.getJumlahBulan();

        double totalPembayaranAwal = hargaMotor - uangMuka;

        for (int tingkat = 10; tingkat <= 50; tingkat += 5) {
            double tebakanAwal = tingkat / 100.0;
            double tingkatDiskontoBulanan = tebakanAwal / jumlahBulan;

            double npv = -totalPembayaranAwal;

            for (int bulan = 1; bulan <= jumlahBulan; bulan++) {
                double pv = cicilanBulanan / Math.pow(1 + tingkatDiskontoBulanan, bulan);
                npv += pv;
            }

            daftarHasilNPV.add(new NpvResult(tingkat, npv));
        }

        return daftarHasilNPV;
    }

public IRRResult hitungIRR(DataLeasingDto dataLeasing) {
    double hargaMotor = dataLeasing.getHargaMotor();
    double uangMuka = dataLeasing.getDp();
    double cicilanBulanan = dataLeasing.getCicilan();
    int jumlahBulan = dataLeasing.getJumlahBulan();

    double totalPembayaranAwal = hargaMotor - uangMuka;

    double tebakanAwal = 0.10; 
    double tingkatDiskontoBulanan = tebakanAwal / jumlahBulan;

    double totalPV = 0.0;

    for (int bulan = 1; bulan <= jumlahBulan; bulan++) {
        double pv = cicilanBulanan / Math.pow(1 + tingkatDiskontoBulanan, bulan);
        totalPV += pv;
    }

    double irrNominal = totalPV - totalPembayaranAwal;
    double irrPersentase = (irrNominal / totalPembayaranAwal) * 100;

    return new IRRResult(irrPersentase, irrNominal);
}
}
