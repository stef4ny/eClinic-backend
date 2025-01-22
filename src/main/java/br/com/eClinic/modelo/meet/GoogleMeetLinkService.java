package br.com.eClinic.modelo.meet;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.eClinic.modelo.agendamento.Agendamento;



@Service
public class GoogleMeetLinkService {
  

    @Autowired
    private GoogleMeetLinkRepository googleMeetLinkRepository;

    public GoogleMeetLink gerarLinkJitsiMeet(Agendamento agendamento) {

        String roomName = UUID.randomUUID().toString();
        String link = "https://meet.jit.si/" + roomName; 

   
        GoogleMeetLink googleMeetLink = GoogleMeetLink.builder()
                .agendamento(agendamento)
                .linkMeet(link)
                .build();

        // Salvar no repositório e retornar
        return googleMeetLinkRepository.save(googleMeetLink);
    }
}


