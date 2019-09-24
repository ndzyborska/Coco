package com.gluonapplication.views;

class Ingredients {

    private final String food;
    private final String measurement;
    private  String amount;



    Ingredients(String food, String measurement, String amount) {
        this.food=food;
        this.measurement = measurement;
        this.amount = amount;

    }
    String getFood() {return this.food; }
    String getMeasurement() {return this.measurement; }
    String getAmount() {return this.amount; }
    void setAmount(String amount) {this.amount = amount;}

}
