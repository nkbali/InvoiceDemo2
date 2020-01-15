package com.demo.invoice;

import com.demo.invoice.data.entities.Invoice;
import com.demo.invoice.data.entities.LineItem;
import com.demo.invoice.models.InvoiceDTO;
import com.demo.invoice.models.LineItemDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@SpringBootApplication
public class InvoiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InvoiceApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        TypeMap<Invoice, InvoiceDTO> invoiceTypeMap =
                modelMapper.createTypeMap(Invoice.class, InvoiceDTO.class);

        invoiceTypeMap.addMapping(invoice -> invoice.getTotal(), InvoiceDTO::setTotal);
        invoiceTypeMap.addMapping(invoice -> invoice.getSubTotal(), InvoiceDTO::setSubTotal);

        TypeMap<LineItem, LineItemDTO> lineItemTypeMap =
                modelMapper.createTypeMap(LineItem.class, LineItemDTO.class);

        lineItemTypeMap.addMapping(lineItem -> lineItem.getLineItemTotal(), LineItemDTO::setLineItemTotal);

        return modelMapper;
    }

    @Bean
    @Scope("prototype")
    Logger logger(InjectionPoint injectionPoint) {
        if (injectionPoint != null && injectionPoint.getMember() != null) {
            return LoggerFactory.getILoggerFactory()
                    .getLogger(injectionPoint.getMember().getDeclaringClass().getName());
        }

        return LoggerFactory.getLogger(this.getClass().getSimpleName());
    }

}
