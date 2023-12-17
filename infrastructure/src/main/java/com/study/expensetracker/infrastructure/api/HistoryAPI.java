package com.study.expensetracker.infrastructure.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "histories")
@Tag(name = "Histories")
public interface HistoryAPI {
    @PutMapping()
    @Operation(summary = "Update the history")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update successfully"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    void update();
}
