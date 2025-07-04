package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Complaint {
    String cId;
    String name;
    String description;
    String userId;
    String complainDate;
    String status;
    String resolvedDate;
    String resolvedTime;
}
