package com.library.rest;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

/**
 * JAX-RS Application Configuration
 */
@ApplicationPath("/api")
public class ApplicationConfig extends Application {
    // No additional configuration needed
    // All REST resources will be automatically discovered
}

