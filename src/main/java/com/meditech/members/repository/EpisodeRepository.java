package com.meditech.members.repository;

import com.meditech.members.entity.EpisodeEntity;
import com.meditech.members.entity.QtableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface EpisodeRepository extends JpaRepository<EpisodeEntity, Integer> {
    List<EpisodeEntity> findAll();
    @Query(value = "SELECT e.epsilon FROM EpisodeEntity e")
    List<Double> findAllEpsilons();

    @Query(value = "SELECT e.id FROM EpisodeEntity e")
    List<Integer> findAllId();
    @Query(value = "SELECT e.s1 FROM EpisodeEntity e")
    List<Double> findAllS1();
    @Query(value = "SELECT e.s2 FROM EpisodeEntity e")
    List<Double> findAllS2();
    @Query(value = "SELECT e.s3 FROM EpisodeEntity e")
    List<Double> findAllS3();
    @Query(value = "SELECT e.s4 FROM EpisodeEntity e")
    List<Double> findAllS4();
    @Query(value = "SELECT e.s5 FROM EpisodeEntity e")
    List<Double> findAllS5();
    @Query(value = "SELECT e.s6 FROM EpisodeEntity e")
    List<Double> findAllS6();
    @Query(value = "SELECT e.epsilon FROM EpisodeEntity e WHERE e.id = (SELECT MAX(ee.id) FROM EpisodeEntity ee)")
    double findLatestEpsilon();

    @Query("SELECT MAX(e.id) FROM EpisodeEntity e")
    int findMaxEpisodeId();
    @Modifying
    @Transactional
    @Query("UPDATE EpisodeEntity e SET e.s1 = :s1, e.s2 = :s2, e.s3 = :s3, e.s4 = :s4, e.s5 = :s5, e.s6 = :s6 WHERE e.id = :id")
    void updateEpisode(@Param("s1") double s1, @Param("s2") double s2, @Param("s3") double s3, @Param("s4") double s4, @Param("s5") double s5, @Param("s6") double s6, @Param("id") int id);

}
