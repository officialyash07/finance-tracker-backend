package com.pathik.financetracker.controller;

import com.pathik.financetracker.dto.dashboard.CategorySummaryResponse;
import com.pathik.financetracker.dto.dashboard.DashboardSummaryResponse;
import com.pathik.financetracker.dto.dashboard.MonthlySummaryResponse;
import com.pathik.financetracker.dto.transaction.TransactionResponse;
import com.pathik.financetracker.security.SecurityUtils;
import com.pathik.financetracker.service.DashboardService;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/dashboard")
public class DashboardController {
    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/summary")
    public DashboardSummaryResponse getSummary(){
        UUID userId= SecurityUtils.getCurrentUserId();

        return dashboardService.getSummary(userId);
    }

    @GetMapping("/monthly")
    public List<MonthlySummaryResponse> getMonthlySummary(@RequestParam int year){
        UUID userId=SecurityUtils.getCurrentUserId();

        return  dashboardService.getMonthlySummary(userId,year);
    }

    @GetMapping("/categories")
    public List<CategorySummaryResponse> getCategorySummary(@RequestParam int year, @RequestParam int month){
        UUID userId=SecurityUtils.getCurrentUserId();

        return dashboardService.getCategorySummary(userId, year, month);
    }

    @GetMapping("/recent-transactions")
    public List<TransactionResponse> getRecentTransactions(@RequestParam(defaultValue = "5") int limit){
        UUID userId=SecurityUtils.getCurrentUserId();

        return dashboardService.getRecentTransactions(userId, limit);
    }
}
