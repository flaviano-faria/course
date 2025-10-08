package com.ead.course.dtos;

import java.util.UUID;

public record UserEventRecordDto(
        UUID userId,
        String userName,
        String email,
        String fullName,
        String userStatus,
        String userType,
        String phoneNumber,
        String imageUrl,
        String actionType) {
}
