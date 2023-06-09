package com.meditech.members.dto;

import com.meditech.members.entity.EpisodeEntity;
import com.meditech.members.entity.QtableEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor//기본 생성자 자동 생성
@AllArgsConstructor//필드 전체를 매개변수로 하는 생성자 자동 생성
@ToString
public class EpisodeDTO {
    private int id;
    private double s1;
    private double s2;
    private double s3;
    private double s4;
    private double s5;
    private double s6;
    private double epsilon;

    public static EpisodeDTO toEpisodeDTO(EpisodeEntity episodeEntity) {//entity->dto로 변환
        EpisodeDTO episodeDTO = new EpisodeDTO();
        episodeDTO.setId(episodeEntity.getId());
        episodeDTO.setS1(episodeEntity.getS1());
        episodeDTO.setS2(episodeEntity.getS2());
        episodeDTO.setS3(episodeEntity.getS3());
        episodeDTO.setS4(episodeEntity.getS4());
        episodeDTO.setS5(episodeEntity.getS5());
        episodeDTO.setS6(episodeEntity.getS6());
        episodeDTO.setEpsilon(episodeEntity.getEpsilon());
        return episodeDTO;
    }
}
