package com.example.analisisleasing.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.analisisleasing.dto.DataLeasingDto;
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

        // Hitung NPV untuk tingkat diskonto dari 10% sampai 50%
        for (int tingkat = 10; tingkat <= 50; tingkat += 5) {
            double tingkatDiskonto = tingkat / 100.0;
            double npv = -totalPembayaranAwal;

            for (int bulan = 1; bulan <= jumlahBulan; bulan++) {
                npv += cicilanBulanan / Math.pow(1 + tingkatDiskonto, bulan);
            }

            daftarHasilNPV.add(new NpvResult(tingkat, npv));
        }

        return daftarHasilNPV;
    }

    // Hitung IRR 
    public double hitungIRR(DataLeasingDto dataLeasing) {
        double hargaMotor = dataLeasing.getHargaMotor();
        double uangMuka = dataLeasing.getDp();
        double cicilanBulanan = dataLeasing.getCicilan();
        int jumlahBulan = dataLeasing.getJumlahBulan();

        double totalPembayaranAwal = hargaMotor - uangMuka;

        double tebakanAwal = 0.1;
        double toleransi = 0.000001;
        double selisih;

        do {
            double npv = -totalPembayaranAwal;
            double turunanNPV = 0;

            for (int bulan = 1; bulan <= jumlahBulan; bulan++) {
                double diskonto = Math.pow(1 + tebakanAwal, bulan);
                npv += cicilanBulanan / diskonto;
                turunanNPV -= (bulan * cicilanBulanan) / Math.pow(1 + tebakanAwal, bulan + 1);
            }

            selisih = npv / turunanNPV;
            tebakanAwal -= selisih;
        } while (Math.abs(selisih) > toleransi);

        return tebakanAwal * 100; // Hasil IRR dalam persen
    }
}
