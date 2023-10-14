package com.hvivox.srealizacao.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FrenetTrackingEvents {
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonProperty("SortDateTime")
    private LocalDateTime sortDateTime;
    
    @JsonProperty("EventDateTime")
    private String eventDateTime;
    @JsonProperty("EventLocation")
    private String eventLocation;
    @JsonProperty("EventDescription")
    private String eventDescription;
    @JsonProperty("EventType")
    private int eventType;
    @JsonProperty("EventStatus")
    private String eventStatus;
    @JsonProperty("CarrierEvent")
    private String carrierEvent;
    @JsonProperty("CarrierStatus")
    private String carrierStatus;
    @JsonProperty("AgencyInfo")
    private String agencyInfo;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonProperty("LimitedDate")
    private LocalDateTime limitedDate;
}
