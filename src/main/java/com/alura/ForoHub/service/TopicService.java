package com.alura.ForoHub.service;

import com.alura.ForoHub.entity.Topic;
import com.alura.ForoHub.repository.TopicRepository;
import com.alura.ForoHub.dto.TopicDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TopicService {

    private final TopicRepository topicRepository;

    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    public Topic getTopicById(Long topicId) {
        return topicRepository.findById(topicId)
                .orElseThrow(() -> new TopicNotFoundException("Tópico no encontrado"));
    }

    public Topic createTopic(TopicDTO topicDTO) {
        Topic topic = new Topic(topicDTO.getTitulo(), topicDTO.getMensaje(), topicDTO.getStatus());
        return topicRepository.save(topic);
    }

    public Topic updateTopic(Long topicId, TopicDTO topicDTO) {
        Topic topicUpdated = topicRepository.findById(topicId)
                .orElseThrow(() -> new TopicNotFoundException("Tópico no encontrado"));
        topicUpdated.setTitulo(topicDTO.getTitulo());
        topicUpdated.setMensaje(topicDTO.getMensaje());
        topicUpdated.setStatus(topicDTO.getStatus());
        return topicRepository.save(topicUpdated);
    }

    public String deleteTopic(Long topicId) {
        Topic topicDeleted = topicRepository.findById(topicId)
                .orElseThrow(() -> new TopicNotFoundException("Tópico no encontrado"));
        topicRepository.delete(topicDeleted);
        return "Tópico eliminado exitosamente";
    }
}
