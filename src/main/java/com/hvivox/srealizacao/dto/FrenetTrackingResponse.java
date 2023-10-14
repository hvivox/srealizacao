package com.hvivox.srealizacao.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FrenetTrackingResponse {
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonProperty("ExpectedDate")
    private LocalDateTime expectedDate;
    @JsonProperty("TrackingNumber")
    private String trackingNumber;
    @JsonProperty("ServiceDescrition")
    private String serviceDescription;
    @JsonProperty("ErrorMessage")
    private String errorMessage;
    @JsonProperty("TrackingEvents")
    private List<FrenetTrackingEvents> trackingEvents;
    @JsonProperty("Carrier")
    private String carrier;
    @JsonProperty("CarrierCode")
    private String carrierCode;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonProperty("ShippingDate")
    private LocalDateTime shippingDate;
    @JsonProperty("MaxDeliveryTime")
    private String maxDeliveryTime;
    @JsonProperty("EstimatedDate")
    private String estimatedDate;
    @JsonProperty("Attempt")
    private String attempt;
    @JsonProperty("Destination")
    private String destination;
    @JsonProperty("Origin")
    private String origin;
    @JsonProperty("PackageInfo")
    private String packageInfo;
}
