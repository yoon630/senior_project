package com.meditech.members.entity;

import com.meditech.members.dto.EpisodeDTO;
import com.meditech.members.dto.QtableDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Setter
@Getter
@Table(name = "episode_table")
public class EpisodeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//auto increment
    private int id;
    @Column
    private double s1;
    @Column
    private double s2;
    @Column
    private double s3;
    @Column
    private double s4;
    @Column
    private double s5;
    @Column
    private double s6;

    public static EpisodeEntity toEpisodeEntity(EpisodeDTO episodeDTO) {//dto객체->entity객체로 변환
        EpisodeEntity episodeEntity = new EpisodeEntity();
        episodeEntity.setId(episodeDTO.getId());
        episodeEntity.setS1(episodeDTO.getS1());
        episodeEntity.setS2(episodeDTO.getS2());
        episodeEntity.setS3(episodeDTO.getS3());
        episodeEntity.setS4(episodeDTO.getS4());
        episodeEntity.setS5(episodeDTO.getS5());
        episodeEntity.setS6(episodeDTO.getS6());
        return episodeEntity;
    }
}
