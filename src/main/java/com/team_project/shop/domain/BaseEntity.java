package com.team_project.shop.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @CreatedDate
    private LocalDateTime created;

    @LastModifiedDate
    private LocalDateTime modified;

    public String getFormattiedCreateDate() {
        return getFormattedDate(created, "yyyy.MM.dd HH:mm:ss");
    }

    public String getFormattiedModifyDate() { return getFormattedDate(modified, "yyyy.MM.dd HH:mm:ss"); }

    public String getFormattedDate(LocalDateTime dataTime, String format) {
        if (dataTime == null) {
            return "";
        }
        return dataTime.format(DateTimeFormatter.ofPattern(format));
    }

}
