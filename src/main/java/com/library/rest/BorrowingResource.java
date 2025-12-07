package com.library.rest;

import com.library.service.BorrowingService;
import com.library.util.GsonUtil;
import com.google.gson.Gson;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Map;

/**
 * REST API endpoints for borrowing operations
 */
@Path("/borrowing")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BorrowingResource {
    
    private final BorrowingService borrowingService;
    private final Gson gson;
    
    public BorrowingResource() {
        this.borrowingService = new BorrowingService();
        this.gson = GsonUtil.getGson();
    }
    
    /**
     * POST /api/borrowing/borrow - Borrow a book
     */
    @POST
    @Path("/borrow")
    public Response borrowBook(String jsonPayload) {
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> data = gson.fromJson(jsonPayload, Map.class);
            
            Long userId = ((Number) data.get("userId")).longValue();
            Long bookId = ((Number) data.get("bookId")).longValue();
            Long issuedById = data.containsKey("issuedById") ? 
                ((Number) data.get("issuedById")).longValue() : null;
            int days = data.containsKey("days") ? 
                ((Number) data.get("days")).intValue() : 14;
            
            Map<String, Object> result = borrowingService.borrowBook(userId, bookId, issuedById, days);
            
            if ((Boolean) result.get("success")) {
                return Response.ok(gson.toJson(result)).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(result)).build();
            }
        } catch (Exception e) {
            Map<String, Object> error = Map.of(
                "success", false,
                "message", "Error borrowing book: " + e.getMessage()
            );
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(error)).build();
        }
    }
    
    /**
     * POST /api/borrowing/return/{recordId} - Return a book
     */
    @POST
    @Path("/return/{recordId}")
    public Response returnBook(@PathParam("recordId") Long recordId, String jsonPayload) {
        try {
            Long returnedToId = null;
            if (jsonPayload != null && !jsonPayload.isEmpty()) {
                @SuppressWarnings("unchecked")
                Map<String, Object> data = gson.fromJson(jsonPayload, Map.class);
                if (data.containsKey("returnedToId")) {
                    returnedToId = ((Number) data.get("returnedToId")).longValue();
                }
            }
            
            Map<String, Object> result = borrowingService.returnBook(recordId, returnedToId);
            
            if ((Boolean) result.get("success")) {
                return Response.ok(gson.toJson(result)).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(result)).build();
            }
        } catch (Exception e) {
            Map<String, Object> error = Map.of(
                "success", false,
                "message", "Error returning book: " + e.getMessage()
            );
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(error)).build();
        }
    }
    
    /**
     * GET /api/borrowing/user/{userId} - Get user's borrowing history
     */
    @GET
    @Path("/user/{userId}")
    public Response getUserBorrowingHistory(@PathParam("userId") Long userId) {
        try {
            List<Map<String, Object>> records = borrowingService.getUserBorrowingHistory(userId);
            Map<String, Object> response = Map.of(
                "success", true,
                "data", records
            );
            return Response.ok(gson.toJson(response)).build();
        } catch (Exception e) {
            Map<String, Object> error = Map.of(
                "success", false,
                "message", "Error fetching borrowing history: " + e.getMessage()
            );
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(error)).build();
        }
    }
    
    /**
     * GET /api/borrowing/user/{userId}/active - Get user's active borrowings
     */
    @GET
    @Path("/user/{userId}/active")
    public Response getUserActiveBorrowings(@PathParam("userId") Long userId) {
        try {
            List<Map<String, Object>> records = borrowingService.getUserActiveBorrowings(userId);
            Map<String, Object> response = Map.of(
                "success", true,
                "data", records
            );
            return Response.ok(gson.toJson(response)).build();
        } catch (Exception e) {
            Map<String, Object> error = Map.of(
                "success", false,
                "message", "Error fetching active borrowings: " + e.getMessage()
            );
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(error)).build();
        }
    }
    
    /**
     * GET /api/borrowing/overdue - Get all overdue borrowings
     */
    @GET
    @Path("/overdue")
    public Response getOverdueBorrowings() {
        try {
            List<Map<String, Object>> records = borrowingService.getOverdueBorrowings();
            Map<String, Object> response = Map.of(
                "success", true,
                "data", records
            );
            return Response.ok(gson.toJson(response)).build();
        } catch (Exception e) {
            Map<String, Object> error = Map.of(
                "success", false,
                "message", "Error fetching overdue borrowings: " + e.getMessage()
            );
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(error)).build();
        }
    }
    
    /**
     * GET /api/borrowing/all - Get all borrowing records
     */
    @GET
    @Path("/all")
    public Response getAllBorrowings() {
        try {
            List<Map<String, Object>> records = borrowingService.getAllBorrowings();
            Map<String, Object> response = Map.of(
                "success", true,
                "data", records
            );
            return Response.ok(gson.toJson(response)).build();
        } catch (Exception e) {
            Map<String, Object> error = Map.of(
                "success", false,
                "message", "Error fetching all borrowings: " + e.getMessage()
            );
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(error)).build();
        }
    }
    
    /**
     * GET /api/borrowing/active - Get all active borrowings
     */
    @GET
    @Path("/active")
    public Response getActiveBorrowings() {
        try {
            List<Map<String, Object>> records = borrowingService.getActiveBorrowings();
            Map<String, Object> response = Map.of(
                "success", true,
                "data", records
            );
            return Response.ok(gson.toJson(response)).build();
        } catch (Exception e) {
            Map<String, Object> error = Map.of(
                "success", false,
                "message", "Error fetching active borrowings: " + e.getMessage()
            );
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(error)).build();
        }
    }
}

