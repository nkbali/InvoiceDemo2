package com.demo.invoice;

import com.demo.invoice.controllers.InvoiceController;
import com.demo.invoice.data.entities.LineItem;
import com.demo.invoice.models.InvoiceDTO;
import com.demo.invoice.models.LineItemDTO;
import com.demo.invoice.models.Response;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class InvoiceApplicationTests {

    @Autowired
    private InvoiceController invoiceController;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;


    @Test
    @Order(1)
    void contextLoads() {
        assertThat(invoiceController).isNotNull();
    }

    @Test
    @Order(2)
    void createInvoice(){
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setClient("Client 1");
        invoiceDTO.setVatRate(new BigDecimal(15));
        invoiceDTO.setInvoiceDate(new Date());

        LineItemDTO lineItemDTO1 = new LineItemDTO();
        lineItemDTO1.setDescription("Line Item 1");
        lineItemDTO1.setQuantity(2L);
        lineItemDTO1.setUnitPrice(new BigDecimal(20));

        LineItemDTO lineItemDTO2 = new LineItemDTO();
        lineItemDTO2.setDescription("Line Item 2");
        lineItemDTO2.setQuantity(5L);
        lineItemDTO2.setUnitPrice(new BigDecimal(35));

        List<LineItemDTO> lineItemDTOS = new ArrayList<>();
        lineItemDTOS.add(lineItemDTO1);
        lineItemDTOS.add(lineItemDTO2);

        invoiceDTO.setLineItems(lineItemDTOS);

        ResponseEntity<Response> responseEntity = this.testRestTemplate.postForEntity("http://localhost:" + port + "/invoices", invoiceDTO, Response.class);
        assertThat(responseEntity.getBody().getResponseCode()).isEqualTo(201);
        assertThat(responseEntity.getBody().getResult().getInvoices().get(0).getId()).isGreaterThan(0);
    }

    @Test
    @Order(3)
    void getInvoices(){
        ResponseEntity<Response> responseEntity = this.testRestTemplate.getForEntity("http://localhost:" + port + "/invoices", Response.class);
        assertThat(responseEntity.getBody().getResponseCode()).isEqualTo(200);
        assertThat(responseEntity.getBody().getResult().getInvoices().size()).isGreaterThan(0);
    }

    @Test
    @Order(4)
    void getInvoiceById(){
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setClient("Client 1");
        invoiceDTO.setVatRate(new BigDecimal(15));
        invoiceDTO.setInvoiceDate(new Date());

        LineItemDTO lineItemDTO1 = new LineItemDTO();
        lineItemDTO1.setDescription("Line Item 1");
        lineItemDTO1.setQuantity(2L);
        lineItemDTO1.setUnitPrice(new BigDecimal(20));

        LineItemDTO lineItemDTO2 = new LineItemDTO();
        lineItemDTO2.setDescription("Line Item 2");
        lineItemDTO2.setQuantity(5L);
        lineItemDTO2.setUnitPrice(new BigDecimal(35));

        List<LineItemDTO> lineItemDTOS = new ArrayList<>();
        lineItemDTOS.add(lineItemDTO1);
        lineItemDTOS.add(lineItemDTO2);

        invoiceDTO.setLineItems(lineItemDTOS);

        ResponseEntity<Response> responseEntity = this.testRestTemplate.postForEntity("http://localhost:" + port + "/invoices", invoiceDTO, Response.class);
        long invoiceId = responseEntity.getBody().getResult().getInvoices().get(0).getId();


        responseEntity = this.testRestTemplate.getForEntity("http://localhost:" + port + "/invoices/" + invoiceId, Response.class);
        assertThat(responseEntity.getBody().getResponseCode()).isEqualTo(200);
        assertThat(responseEntity.getBody().getResult().getInvoices().size()).isEqualTo(1);
    }

}
