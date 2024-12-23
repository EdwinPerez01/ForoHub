package com.alura.ForoHub.repository;

import com.alura.ForoHub.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

    // Método de consulta personalizado para encontrar un tema por su título
    Optional<Topic> findByTitle(String title);
}
