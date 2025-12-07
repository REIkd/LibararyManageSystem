package com.library.rest;

import com.library.service.AuthService;
import com.library.util.GsonUtil;
import com.google.gson.Gson;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Map;

/**
 * REST API endpoints for authentication
 */
@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {
    
    private final AuthService authService;
    private final Gson gson;
    
    public AuthResource() {
        this.authService = new AuthService();
        this.gson = GsonUtil.getGson();
    }
    
    /**
     * POST /api/auth/register - Register new user
     */
    @POST
    @Path("/register")
    public Response register(String jsonPayload) {
        try {
            @SuppressWarnings("unchecked")
            Map<String, String> data = gson.fromJson(jsonPayload, Map.class);
            
            String username = data.get("username");
            String email = data.get("email");
            String password = data.get("password");
            String firstName = data.get("firstName");
            String lastName = data.get("lastName");
            String role = data.get("role");
            
            Map<String, Object> result = authService.register(username, email, password, firstName, lastName, role);
            
            if ((Boolean) result.get("success")) {
                return Response.ok(gson.toJson(result)).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(result)).build();
            }
        } catch (Exception e) {
            Map<String, Object> error = Map.of(
                "success", false,
                "message", "Error processing request: " + e.getMessage()
            );
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(error)).build();
        }
    }
    
    /**
     * POST /api/auth/login - User login
     */
    @POST
    @Path("/login")
    public Response login(String jsonPayload) {
        try {
            @SuppressWarnings("unchecked")
            Map<String, String> data = gson.fromJson(jsonPayload, Map.class);
            
            String username = data.get("username");
            String password = data.get("password");
            
            Map<String, Object> result = authService.login(username, password);
            
            if ((Boolean) result.get("success")) {
                return Response.ok(gson.toJson(result)).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).entity(gson.toJson(result)).build();
            }
        } catch (Exception e) {
            Map<String, Object> error = Map.of(
                "success", false,
                "message", "Error processing request: " + e.getMessage()
            );
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(error)).build();
        }
    }
    
    /**
     * GET /api/auth/me - Get current user info
     */
    @GET
    @Path("/me")
    public Response getCurrentUser(@HeaderParam("Authorization") String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                Map<String, Object> error = Map.of(
                    "success", false,
                    "message", "Missing or invalid authorization header"
                );
                return Response.status(Response.Status.UNAUTHORIZED).entity(gson.toJson(error)).build();
            }
            
            String token = authHeader.substring(7);
            Map<String, Object> result = authService.validateToken(token);
            
            if ((Boolean) result.get("success")) {
                return Response.ok(gson.toJson(result)).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).entity(gson.toJson(result)).build();
            }
        } catch (Exception e) {
            Map<String, Object> error = Map.of(
                "success", false,
                "message", "Error processing request: " + e.getMessage()
            );
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(error)).build();
        }
    }
    
    /**
     * POST /api/auth/change-password - Change password
     */
    @POST
    @Path("/change-password")
    public Response changePassword(@HeaderParam("Authorization") String authHeader, String jsonPayload) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                Map<String, Object> error = Map.of(
                    "success", false,
                    "message", "Missing or invalid authorization header"
                );
                return Response.status(Response.Status.UNAUTHORIZED).entity(gson.toJson(error)).build();
            }
            
            String token = authHeader.substring(7);
            Map<String, Object> validation = authService.validateToken(token);
            
            if (!(Boolean) validation.get("success")) {
                return Response.status(Response.Status.UNAUTHORIZED).entity(gson.toJson(validation)).build();
            }
            
            @SuppressWarnings("unchecked")
            Map<String, Object> userInfo = (Map<String, Object>) validation.get("user");
            Long userId = ((Number) userInfo.get("userId")).longValue();
            
            @SuppressWarnings("unchecked")
            Map<String, String> data = gson.fromJson(jsonPayload, Map.class);
            String oldPassword = data.get("oldPassword");
            String newPassword = data.get("newPassword");
            
            Map<String, Object> result = authService.changePassword(userId, oldPassword, newPassword);
            
            if ((Boolean) result.get("success")) {
                return Response.ok(gson.toJson(result)).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(result)).build();
            }
        } catch (Exception e) {
            Map<String, Object> error = Map.of(
                "success", false,
                "message", "Error processing request: " + e.getMessage()
            );
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(error)).build();
        }
    }
}

