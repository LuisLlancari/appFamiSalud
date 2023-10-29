package com.famisalud.famisalud.Model;

public class MetodoPagoClass {

   String cci;
   String numeroCuenta;
   String URLImage;
   String metodo;

   public String getURLImage() {
      return URLImage;
   }

   public void setCci(String cci) {
      this.cci = cci;
   }

   public void setNumeroCuenta(String numeroCuenta) {
      this.numeroCuenta = numeroCuenta;
   }


   public void setMetodo(String metodo) {
      this.metodo = metodo;
   }


   public String getCci() {
      return cci;
   }

   public String getNumeroCuenta() {
      return numeroCuenta;
   }


   public String getMetodo() {
      return metodo;
   }
}
