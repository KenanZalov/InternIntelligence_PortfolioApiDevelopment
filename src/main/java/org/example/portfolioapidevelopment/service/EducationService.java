package org.example.portfolioapidevelopment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.portfolioapidevelopment.dao.Education;
import org.example.portfolioapidevelopment.dao.Portfolio;
import org.example.portfolioapidevelopment.dto.request.EducationRequestDto;
import org.example.portfolioapidevelopment.dto.response.EducationResponseDto;
import org.example.portfolioapidevelopment.exception.UnauthorizedAccessException;
import org.example.portfolioapidevelopment.repository.EducationRepository;
import org.example.portfolioapidevelopment.repository.PortfolioRepository;
import org.example.portfolioapidevelopment.security.JwtService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EducationService {
    private final EducationRepository educationRepository;
    private final PortfolioRepository portfolioRepository;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;


    public String addEducation(EducationRequestDto educationRequestDto) {
        log.info("Starting addEducation method for portfolioId: {}", educationRequestDto.getPortfolioId());

        Portfolio portfolio = portfolioRepository.findById(educationRequestDto.getPortfolioId())
                .orElseThrow(() -> {
                    log.error("Portfolio not found with ID: {}", educationRequestDto.getPortfolioId());
                    return new RuntimeException("Portfolio not found");
                });
        Education education = modelMapper.map(educationRequestDto, Education.class);
        education.setPortfolio(portfolio);
        educationRepository.save(education);

        log.info("Education added successfully for portfolioId: {}", educationRequestDto.getPortfolioId());
        return "Education added";
    }

    public List<EducationResponseDto> getAllEducations() {
        List<Education> educations = educationRepository.findAll();
        return educations
                .stream()
                .map(education -> modelMapper.map(education, EducationResponseDto.class))
                .toList();
    }

    public String updateEducation(Long educationId,EducationRequestDto educationRequestDto) {
        log.info("Starting updateEducation method for educationId: {}", educationId);

        String currentUserName = jwtService.getCurrentUsername();
        if (!isEducationOwner(educationId, currentUserName)) {
            log.error("Access denied for user: {} while updating educationId: {}", currentUserName, educationId);
            throw new UnauthorizedAccessException("You do not have permission to update this education");
        }
        Education education = educationRepository.findById(educationId)
                .orElseThrow(() -> {
                    log.error("Education not found with ID: {}", educationId);
                    return new RuntimeException("Education not found");
                });
        Portfolio portfolio = portfolioRepository.findById(educationRequestDto.getPortfolioId())
                .orElseThrow(() -> {
                    log.error(" Portfolio not found with ID: {}", educationRequestDto.getPortfolioId());
                    return new RuntimeException("Portfolio not found");
                });
        education.setPortfolio(portfolio);
        education.setSchoolName(educationRequestDto.getSchoolName());
        education.setFieldOfStudy(educationRequestDto.getFieldOfStudy());
        education.setEducationDegree(educationRequestDto.getEducationDegree());
        education.setStartDate(educationRequestDto.getStartDate());
        education.setEndDate(educationRequestDto.getEndDate());
        educationRepository.save(education);

        log.info("Education with ID: {} updated successfully", educationId);
        return "Education updated";
    }

    public String deleteEducation(Long educationId) {
        log.info("Starting deleteEducation method for educationId: {}", educationId);

        String currentUserName = jwtService.getCurrentUsername();
        if (!isEducationOwner(educationId, currentUserName)) {
            log.error("Access denied for user: {} while deleting educationId: {}", currentUserName, educationId);
            throw new UnauthorizedAccessException("You do not have permission to delete this education");
        }
        Education education = educationRepository.findById(educationId)
                .orElseThrow(() -> {
                    log.error(" Education not found with ID: {}", educationId);
                    return new RuntimeException("Education not found");
                });
        educationRepository.delete(education);

        log.info("Education with ID: {} deleted successfully", educationId);
        return "Education deleted";
    }

    public boolean isEducationOwner(Long educationId,String username) {
        Education education = educationRepository.findById(educationId)
                .orElseThrow(() -> new RuntimeException("Education not found"));
        Portfolio portfolio = education.getPortfolio();
        return portfolio.getMyUser().getUsername().equals(username);
    }
}
