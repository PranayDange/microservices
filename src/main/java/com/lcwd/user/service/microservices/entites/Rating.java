package com.lcwd.user.service.microservices.entites;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rating {

    private String ratingId;
    private String userId;
    private String hotelId;
    private int rating;
    private String remark;
}
