package com.demo.invoice.models;

import java.util.List;

public class Result {

    private List<InvoiceDTO> invoices;

    public List<InvoiceDTO> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<InvoiceDTO> invoices) {
        this.invoices = invoices;
    }
}
