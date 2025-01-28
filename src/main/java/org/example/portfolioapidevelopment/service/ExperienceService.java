package org.example.portfolioapidevelopment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.portfolioapidevelopment.dao.Experience;
import org.example.portfolioapidevelopment.dao.Portfolio;
import org.example.portfolioapidevelopment.dto.request.ExperienceRequestDto;
import org.example.portfolioapidevelopment.dto.response.ExperienceResponseDto;
import org.example.portfolioapidevelopment.exception.UnauthorizedAccessException;
import org.example.portfolioapidevelopment.repository.ExperienceRepository;
import org.example.portfolioapidevelopment.repository.PortfolioRepository;
import org.example.portfolioapidevelopment.security.JwtService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExperienceService {
    private final ExperienceRepository experienceRepository;
    private final PortfolioRepository portfolioRepository;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;


    public String addExperience(ExperienceRequestDto experienceRequestDto) {
        log.info("Starting addExperience method for portfolioId: {}", experienceRequestDto.getPortfolioId());

        Portfolio portfolio = portfolioRepository.findById(experienceRequestDto.getPortfolioId())
                .orElseThrow(() -> {
                    log.error("Portfolio not found with ID: {}", experienceRequestDto.getPortfolioId());
                    return new RuntimeException("Portfolio not found");
                });
        Experience experience = modelMapper.map(experienceRequestDto, Experience.class);
        experience.setPortfolio(portfolio);
        experienceRepository.save(experience);

        log.info("Experience added successfully for portfolioId: {}", experienceRequestDto.getPortfolioId());
        return "Experience added";
    }

    public List<ExperienceResponseDto> getAllExperiences() {
        List<Experience> experiences = experienceRepository.findAll();
        return experiences
                .stream()
                .map(experience -> modelMapper.map(experience, ExperienceResponseDto.class))
                .toList();
    }

    public String updateExperience(Long experienceId, ExperienceRequestDto experienceRequestDto) {
        log.info("Starting updateExperience method for experienceId: {}", experienceId);

        String currentUserName = jwtService.getCurrentUsername();
        if (!isExperienceOwner(experienceId, currentUserName)) {
            log.error("Access denied for user: {} while updating experienceId: {}", currentUserName, experienceId);
            throw new UnauthorizedAccessException("You do not have permission to update this experience");
        }
        Experience experience = experienceRepository.findById(experienceId)
                .orElseThrow(() -> {
                    log.error("Experience not found with ID: {}", experienceId);
                    return new RuntimeException("Experience not found");
                });
        Portfolio portfolio = portfolioRepository.findById(experienceRequestDto.getPortfolioId())
                .orElseThrow(() -> {
                    log.error(" Portfolio not found with ID: {}", experienceRequestDto.getPortfolioId());
                    return new RuntimeException("Portfolio not found");
                });

        log.info("Updating experience with ID: {}", experienceId);
        experience.setPortfolio(portfolio);
        experience.setTitle(experienceRequestDto.getTitle());
        experience.setDescription(experienceRequestDto.getDescription());
        experience.setCompanyName(experienceRequestDto.getCompanyName());
        experience.setStartDate(experienceRequestDto.getStartDate());
        experience.setEndDate(experienceRequestDto.getEndDate());
        experience.setEmploymentType(experienceRequestDto.getEmploymentType());
        experienceRepository.save(experience);

        log.info("Experience with ID: {} updated successfully", experienceId);
        return "Experience updated";
    }

    public String deleteExperience(Long experienceId) {
        log.info("Starting deleteExperience method for experienceId: {}", experienceId);

        String currentUserName = jwtService.getCurrentUsername();
        if (!isExperienceOwner(experienceId, currentUserName)) {
            log.error("Access denied for user: {} while deleting experienceId: {}", currentUserName, experienceId);
            throw new UnauthorizedAccessException("You do not have permission to delete this experience");
        }
        Experience experience = experienceRepository.findById(experienceId)
                .orElseThrow(() -> {
                    log.error(" Experience not found with ID: {}", experienceId);
                    return new RuntimeException("Experience not found");
                });
        experienceRepository.delete(experience);

        log.info("Experience with ID: {} deleted successfully", experienceId);
        return "Experience deleted";
    }

    public boolean isExperienceOwner(Long experienceId,String username) {
        Experience experience = experienceRepository.findById(experienceId)
                .orElseThrow(() -> new RuntimeException("Experience not found"));
        Portfolio portfolio = experience.getPortfolio();
        return portfolio.getMyUser().getUsername().equals(username);
    }
}
