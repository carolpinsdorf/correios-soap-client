package com.example.correiossoapclient.services;


import com.example.correiossoapclient.ConsultaCEP;
import com.example.correiossoapclient.EnderecoERP;
import com.example.correiossoapclient.ObjectFactory;
import jakarta.xml.bind.JAXBElement;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

@Service
public class CorreiosService {

    //variables
    private final WebServiceTemplate webServiceTemplate;

    //constructors
    public CorreiosService(WebServiceTemplate webServiceTemplate) {
        this.webServiceTemplate = webServiceTemplate;
    }

    //functions/methods
    public EnderecoERP getEndereco() {
        //cria o obejto
        ConsultaCEP request = new ConsultaCEP();
        request.setCep("11111111");
        request.setUsuario("usuario");
        request.setSenha("senha");

        // usa object factory para gerar o objeto
        ObjectFactory factory = new ObjectFactory();
        JAXBElement<ConsultaCEP> reqObject = factory.createConsultaCEP(request);

        Object response = webServiceTemplate.marshalSendAndReceive(reqObject);
        if (response instanceof JAXBElement<?> element) {

            if (element.getValue() instanceof EnderecoERP endereco) {
                return endereco;
            }
        }

        throw  new IllegalStateException("Resposta inesperada");
    }
}
