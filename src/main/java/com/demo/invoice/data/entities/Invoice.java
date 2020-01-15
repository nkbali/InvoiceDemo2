package com.demo.invoice.data.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Invoice {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String client;

    private Long vatRate;

    private Date invoiceDate;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "invoiceId")
    private List<LineItem> lineItems = new ArrayList<>();

    public Invoice(){

    }

    public Invoice(String client, Long vatRate, Date invoiceDate) {
        this.client = client;
        this.vatRate = vatRate;
        this.invoiceDate = invoiceDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public BigDecimal getVatRate() {
        return new BigDecimal(vatRate).setScale(2, RoundingMode.HALF_UP);
    }

    public void setVatRate(Long vatRate) {
        this.vatRate = vatRate;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    public void addLineItem(LineItem lineItem){
        lineItems.add(lineItem);
    }

    public void removeLineItem(LineItem lineItem){
        lineItems.remove(lineItem);
    }

    public BigDecimal getSubTotal(){
        double result = 0;
        for (LineItem lineItem: lineItems) {
            result += lineItem.getLineItemTotal().doubleValue();
        }

        BigDecimal subTotal = new BigDecimal(result);
        return subTotal.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getTotal(){
        BigDecimal subTotal = getSubTotal();
        BigDecimal vatAmount = subTotal.multiply(getVatRate().divide(new BigDecimal(100)));
        BigDecimal total = subTotal.add(vatAmount);
        return total.setScale(2, RoundingMode.HALF_UP);
    }
}
