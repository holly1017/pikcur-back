package com.pikcurchu.pikcur.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResPaymentAddressDto {
    private Integer payPrice;
    private String receiver;
    private String phone;
    private String address;
    private String addressDetail;
}
