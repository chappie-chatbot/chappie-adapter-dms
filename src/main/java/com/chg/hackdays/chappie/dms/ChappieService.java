package com.chg.hackdays.chappie.dms;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChappieService {

    public static final String SAVED_DOCUMENTS = "saved_documents";
    public static final String DOCUMENT = "document";
    long sleep = 5000; // 1 second

    private final RestTemplate restTemplate;

    public void polling(){
        log.info("polling...");
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleWithFixedDelay(new PollingService(), 0, sleep, TimeUnit.MILLISECONDS);
    }

    private void saveDocument(Message message) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        message.setReplyTo(message.getId());
        message.setTopic(SAVED_DOCUMENTS);
        message.setId(null);
        message.setTimestamp(null);
        Messages messages = new Messages();
        messages.getItems().add(message);
        HttpEntity<Messages> request = new HttpEntity<>(messages, headers);

        restTemplate.exchange("https://chappie.etherous.net/message?topic=" + SAVED_DOCUMENTS,
                HttpMethod.POST, request, Messages.class);
    }

    private List<Message> getDocuments() {
        return getMessages(DOCUMENT);
    }

    private List<Message> getSavedDocuments() {
        return getMessages(SAVED_DOCUMENTS);
    }

    private List<Message> getMessages(String topic){
        ResponseEntity<Messages> result = restTemplate.exchange("https://chappie.etherous.net/message?topic=" + topic,
                HttpMethod.GET, null, Messages.class);
        return result.getBody().getItems();
    }

    class PollingService implements Runnable {
        private int count = 0;
        public void run() {
            List<Message> documents = getDocuments();
            List<Message> savedDocuments = getSavedDocuments();

            List<String> documentIds = documents.stream().map(Message::getId).collect(Collectors.toList());
            List<String> savedDocumentIds = savedDocuments.stream().map(Message::getReplyTo).collect(Collectors.toList());

            documentIds.removeAll(savedDocumentIds);

            for (Message message : documents){
                if(documentIds.contains(message.getId())){
                    saveDocument(message);
                }
            }
            log.info("iteration :" + (count++));
        }
    }

}
