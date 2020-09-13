package ru.family.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RewardMailDTO {

    private String name;
    private String surname;
    private String mailAddress;
    private String rewardTitle;
    private String rewardDescription;
    private Long rewardPrice;
}
