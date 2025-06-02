package com.example.analisisleasing.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.analisisleasing.dto.DataLeasingDto;
import com.example.analisisleasing.dto.IRRResult;
import com.example.analisisleasing.dto.NpvResult;
import com.example.analisisleasing.service.LeasingService;

import java.text.DecimalFormat;
import java.util.stream.Collectors;

@Controller
public class LeasingController {
    @Autowired
    private LeasingService leasingService;

    @GetMapping("/")
    public String form(Model model) {
        model.addAttribute("dataLeasingDto", new DataLeasingDto());
        return "form";
    }

    @PostMapping("/hitung")
    public String hitungan(@ModelAttribute DataLeasingDto dataLeasingDto, Model model) {
        System.out.println("===> MASUK KE METODE HITUNG");

        List<NpvResult> daftarNPV = leasingService.hitungNPV(dataLeasingDto);
        IRRResult hasilIRR = leasingService.hitungIRR(dataLeasingDto);

        DecimalFormat df = new DecimalFormat("#,###.##");

        List<NpvResult> daftarNPVFormatted = daftarNPV.stream()
                .map(npv -> new NpvResult(
                        npv.getTingkatDiskonto(),
                        Double.parseDouble(df.format(npv.getNilaiNPV()).replace(",", ""))))
                .collect(Collectors.toList());

        model.addAttribute("npvList", daftarNPVFormatted);
        model.addAttribute("irrFormatted", df.format(hasilIRR.getIrrPercent())); 
        model.addAttribute("irrValueFormatted", df.format(hasilIRR.getNpv())); 
        model.addAttribute("hargaMotorFormatted", df.format(dataLeasingDto.getHargaMotor()));
        model.addAttribute("dpFormatted", df.format(dataLeasingDto.getDp()));
        model.addAttribute("cicilanFormatted", df.format(dataLeasingDto.getCicilan()));
        model.addAttribute("data", dataLeasingDto);

        System.out.println("===> SELESAI HITUNG, AKAN RETURN VIEW");
        return "hasil";
    }

}
