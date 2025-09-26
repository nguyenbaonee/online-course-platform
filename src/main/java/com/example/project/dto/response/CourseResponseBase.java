package com.example.project.dto.response;

import java.math.BigDecimal;

public interface CourseResponseBase {
    String getId();
    String getTitle();
    String getDescription();
    BigDecimal getPrice();
    String getThumbnailUrl();
}
