package cz.zemeplocha.mestskahlidka.repository;

import cz.zemeplocha.mestskahlidka.entity.Podezrely;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PodezrelyRepository extends JpaRepository<Podezrely, Long> {
}