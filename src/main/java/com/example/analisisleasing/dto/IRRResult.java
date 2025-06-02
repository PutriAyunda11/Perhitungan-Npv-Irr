package com.example.analisisleasing.dto;

public class IRRResult {
     private double irrPercent;
        private double npv;
        
        public IRRResult(double irrPercent, double npv) {
            this.irrPercent = irrPercent;
            this.npv = npv;
        }

        public double getIrrPercent() {
            return irrPercent;
        }

        public double getNpv() {
            return npv;
        }
}
