package org.example.portfolioapidevelopment.controller;

import lombok.RequiredArgsConstructor;
import org.example.portfolioapidevelopment.dto.request.PortfolioRequestDto;
import org.example.portfolioapidevelopment.dto.response.PortfolioResponseDto;
import org.example.portfolioapidevelopment.service.PortfolioService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio")
public class PortfolioController {
    private final PortfolioService portfolioService;

    @PostMapping("/savePortfolio")
    public String savePortfolio(@RequestBody PortfolioRequestDto portfolioRequestDto) {
        return portfolioService.savePortfolio(portfolioRequestDto);
    }

    @GetMapping("/getAllPortfolios")
    @PreAuthorize("hasRole('ADMIN')")
    public List<PortfolioResponseDto> getAllPortfolios() {
        return portfolioService.getAllPortfolios();
    }

    @PutMapping("/updatePortfolio")
    public String updatePortfolio(@RequestParam Long portfolioId,
            @RequestBody PortfolioRequestDto portfolioRequestDto) {
        return portfolioService.updatePortfolio(portfolioId, portfolioRequestDto);
    }

    @DeleteMapping("/deletePortfolio")
    public String deletePortfolio(@RequestParam Long portfolioId) {
        return portfolioService.deletePortfolio(portfolioId);
    }

    @GetMapping("/getPortfoliosByUserId")
    public List<PortfolioResponseDto> getPortfoliosByUserId(@RequestParam Long userId) {
        return portfolioService.getPortfoliosByUserId(userId);
    }

}
