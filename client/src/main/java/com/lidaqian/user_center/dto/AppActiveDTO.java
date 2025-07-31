package com.lidaqian.user_center.dto;

public record AppActiveDTO(
    // request
    String name,
    String description,
    String version,
    String indexUrl,
    // response
    String appId,
    String secret,
    AppStatus status
) {

    public void validate() {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank.");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be null or blank.");
        }
        if (version == null || version.isBlank()) {
            throw new IllegalArgumentException("Version cannot be null or blank.");
        }
        if (indexUrl == null || indexUrl.isBlank()) {
            throw new IllegalArgumentException("Index URL cannot be null or blank.");
        }
    }
}
