package com.example.correiossoapclient.services;


import com.example.correiossoapclient.EnderecoERP;
import jakarta.xml.bind.JAXBElement;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ws.client.core.WebServiceTemplate;

import javax.xml.namespace.QName;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CorreiosServiceTest {

    @Test
    void deveRetornarEnderecoMockado(){

        //mock do webservice template
        WebServiceTemplate template = Mockito.mock(WebServiceTemplate.class);

        //mock do objeto de resposta
        EnderecoERP object;
        object = new EnderecoERP();
        object.setCep("12345");
        object.setBairro("Vila mariana");
        object.setCidade("São Paulo");
        object.setEnd("Rua 1");
        object.setUf("SP");
        object.setComplemento2("Complemento 2");

        //empacota o objeto num jaxb element, simulando o que o correios retornaria
        JAXBElement<EnderecoERP> response = new JAXBElement<>(
                new QName("http://cliente.bean.master.sigep.bsb.correios.com.br/", "consultCEPResponse"),
                EnderecoERP.class,
                object
        );

        // quando chamar o soap, retorna o mock
        when(
                template.marshalSendAndReceive(any(Object.class))
        ).thenReturn(response);

        //instancia o service
        CorreiosService service = new CorreiosService(template);

        //executa
        EnderecoERP result = service.getEndereco();

        //verifica
        assertNotNull(result);
        assertEquals("12345", result.getCep());
        assertEquals("Vila mariana", result.getBairro());
        assertEquals("São Paulo", result.getCidade());
        assertEquals("Rua 1", result.getEnd());
        assertEquals("SP", result.getUf());
        assertEquals("Complemento 2", result.getComplemento2());
    }

    @Test
    void deveLancarExcecaoQuandoRespostaForInvalida() {

        //mock do webservice template
        WebServiceTemplate template = Mockito.mock(WebServiceTemplate.class);

        //instancia o service
        CorreiosService service = new CorreiosService(template);

        when(
                template.marshalSendAndReceive(any())
        ).thenReturn("Resposta inválida");

        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                service::getEndereco
        );

        assertEquals("Resposta inesperada", ex.getMessage());
    }
}
