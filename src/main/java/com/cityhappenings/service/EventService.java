package com.cityhappenings.service;

import com.cityhappenings.model.Event;
import com.cityhappenings.repository.EventRepository;
import jakarta.persistence.Cacheable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC = "events-topic";

//    @Cacheable("events")
//    public List<Event> getAllEvents(){
//        System.out.println("Fetching events from DB...");
//        return eventRepository.findAll();
//    }

    public Event createEvent(Event event){
        Event savedEvent = eventRepository.save(event);

        //Send Kafka message
        String message = "New event created: " + savedEvent.getName();
        kafkaTemplate.send(TOPIC, message);

        return savedEvent;
    }
}
