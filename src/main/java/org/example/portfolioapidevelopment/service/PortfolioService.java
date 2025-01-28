package org.example.portfolioapidevelopment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.portfolioapidevelopment.dao.Portfolio;
import org.example.portfolioapidevelopment.dao.MyUser;
import org.example.portfolioapidevelopment.dto.request.PortfolioRequestDto;
import org.example.portfolioapidevelopment.dto.response.PortfolioResponseDto;
import org.example.portfolioapidevelopment.exception.DuplicateEntityException;
import org.example.portfolioapidevelopment.repository.PortfolioRepository;
import org.example.portfolioapidevelopment.repository.MyUserRepository;
import org.example.portfolioapidevelopment.security.JwtService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PortfolioService {
    private final PortfolioRepository portfolioRepository;
    private final MyUserRepository myUserRepository;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;


    public String savePortfolio(PortfolioRequestDto portfolioRequestDto) {
        log.info("Starting savePortfolio method for title: {}", portfolioRequestDto.getTitle());

        if (portfolioRepository.existsByTitle(portfolioRequestDto.getTitle())) {
            log.warn("Portfolio already exists with title: {}", portfolioRequestDto.getTitle());
            throw new DuplicateEntityException("Portfolio", "title", portfolioRequestDto.getTitle());
        }
        MyUser myUser = myUserRepository.findById(portfolioRequestDto.getMyUserId()).
                orElseThrow(() -> {
                    log.error("User not found with ID: {}", portfolioRequestDto.getMyUserId());
                    return new RuntimeException("User not found");
                });
        Portfolio portfolio = modelMapper.map(portfolioRequestDto, Portfolio.class);
        portfolio.setMyUser(myUser);
        portfolioRepository.save(portfolio);

        log.info("Portfolio created successfully with title: {}", portfolioRequestDto.getTitle());
        return "Portfolio created";
    }

    public List<PortfolioResponseDto> getAllPortfolios() {
        log.info("Starting getAllPortfolios method");

        List<Portfolio> portfolios = portfolioRepository.findAll();
        log.info("Found {} portfolios in the database", portfolios.size());

        return portfolios
                .stream()
                .map(portfolio -> modelMapper.map(portfolio, PortfolioResponseDto.class))
                .toList();
    }

    public String updatePortfolio(Long portfolioId, PortfolioRequestDto portfolioRequestDto) {
        log.info("Starting updatePortfolio method for portfolioId: {}", portfolioId);

        String currentUserName = jwtService.getCurrentUsername();
        if (!isPortfolioOwner(portfolioId, currentUserName)) {
            log.error("Access denied for user: {} while updating portfolioId: {}", currentUserName, portfolioId);
            throw new SecurityException("You do not have permission to update this portfolio");
        }
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> {
                    log.error("Portfolio not found with ID: {}", portfolioId);
                    return new RuntimeException("Portfolio not found!");
                });
        MyUser myUser = myUserRepository.findById(portfolioRequestDto.getMyUserId())
                .orElseThrow(() -> {
                    log.error(" User not found with ID: {}", portfolioRequestDto.getMyUserId());
                    return new RuntimeException("User not found!");
                });

        log.info("Updating portfolio with ID: {}", portfolioId);
        portfolio.setTitle(portfolioRequestDto.getTitle());
        portfolio.setDescription(portfolioRequestDto.getDescription());
        portfolio.setMyUser(myUser);
        portfolioRepository.save(portfolio);

        log.info("Portfolio with ID: {} updated successfully", portfolioId);
        return "Portfolio updated";
    }

    public String deletePortfolio(Long portfolioId) {
        log.info("Starting deletePortfolio method for portfolioId: {}", portfolioId);

        String currentUserName = jwtService.getCurrentUsername();
        if (!isPortfolioOwner(portfolioId, currentUserName)) {
            log.error("Access denied for user: {} while deleting portfolioId: {}", currentUserName, portfolioId);
            throw new SecurityException("You do not have permission to delete this portfolio");
        }
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> {
                    log.error(" Portfolio not found with ID: {}", portfolioId);
                    return new RuntimeException("Portfolio not found!");
                });
        portfolioRepository.delete(portfolio);

        log.info("Portfolio with ID: {} deleted successfully", portfolioId);
        return "Portfolio deleted";
    }

    public List<PortfolioResponseDto> getPortfoliosByUserId(Long userId) {
        List<Portfolio> portfolios = portfolioRepository.findByMyUserId(userId);
        if (portfolios.isEmpty()) {
            throw new RuntimeException("Portfolio not found!");
        }
        return portfolios
                .stream()
                .map(portfolio -> modelMapper.map(portfolio, PortfolioResponseDto.class))
                .toList();
    }

    public boolean isPortfolioOwner(Long portfolioId, String username) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new RuntimeException("Portfolio not found"));
        return portfolio.getMyUser().getUsername().equals(username);
    }
}
