package org.example.portfolioapidevelopment.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.portfolioapidevelopment.dao.Portfolio;
import org.example.portfolioapidevelopment.dao.Skill;
import org.example.portfolioapidevelopment.dto.request.SkillRequestDto;
import org.example.portfolioapidevelopment.dto.response.SkillResponseDto;
import org.example.portfolioapidevelopment.exception.DuplicateEntityException;
import org.example.portfolioapidevelopment.repository.PortfolioRepository;
import org.example.portfolioapidevelopment.repository.SkillRepository;
import org.example.portfolioapidevelopment.security.JwtService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SkillService {
    private final SkillRepository skillRepository;
    private final ModelMapper modelMapper;
    private final PortfolioRepository portfolioRepository;
    private final JwtService jwtService;


    public String addSkill(SkillRequestDto requestDto) {
        log.info("Starting addSkill method for skill: {}", requestDto.getName());

        if (skillRepository.existsByName(requestDto.getName())) {
            log.warn("Skill already exists: {}", requestDto.getName());
            throw new DuplicateEntityException("Skill", "name", requestDto.getName());
        }
        Portfolio portfolio = portfolioRepository.findById(requestDto.getPortfolioId())
                .orElseThrow(() -> {
                    log.error("Portfolio not found with ID: {}", requestDto.getPortfolioId());
                    return new EntityNotFoundException("Portfolio not found!");
                });
        Skill skill = modelMapper.map(requestDto, Skill.class);
        skill.setPortfolio(portfolio);
        skillRepository.save(skill);

        log.info("Skill added successfully: {}", requestDto.getName());
        return "Skill added";
    }

    public List<SkillResponseDto> getAllSkills() {
        log.info("Starting getAllSkills method");

        List<Skill> skills = skillRepository.findAll();
        log.info("Found {} skills in the database", skills.size());

        return skills
                .stream()
                .map(skill -> modelMapper.map(skill, SkillResponseDto.class))
                .toList();
    }

    public String updateSkill(Long skillId, SkillRequestDto requestDto) {
        log.info("Starting updateSkill method for skillId: {}", skillId);

        String currentUsername = jwtService.getCurrentUsername();
        if (!isSkillOwner(skillId, currentUsername)) {
            log.error("Access denied for user: {} while updating skillId: {}", currentUsername, skillId);
            throw new SecurityException("You do not have permission to update this skill");
        }
        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> {
                    log.error("Skill not found with ID: {}", skillId);
                    return new EntityNotFoundException("Skill not found");
                });
        Portfolio portfolio = portfolioRepository.findById(requestDto.getPortfolioId())
                .orElseThrow(() -> {
                    log.error(" Portfolio not found with ID: {}", requestDto.getPortfolioId());
                    return new EntityNotFoundException("Portfolio not found");
                });

        log.info("Updating skill with ID: {}", skillId);
        skill.setPortfolio(portfolio);
        skill.setName(requestDto.getName());
        skill.setLevel(requestDto.getLevel());
        skillRepository.save(skill);

        log.info("Skill with ID: {} updated successfully", skillId);
        return "Skill updated";
    }

    public String deleteSkill(Long skillId) {
        log.info("Starting deleteSkill method for skillId: {}", skillId);

        String currentUsername = jwtService.getCurrentUsername();
        if (!isSkillOwner(skillId, currentUsername)) {
            log.error("Access denied for user: {} while deleting skillId: {}", currentUsername, skillId);
            throw new SecurityException("You do not have permission to delete this skill");
        }
        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> {
                    log.error(" Skill not found with ID: {}", skillId);
                    return new EntityNotFoundException("Skill not found");
                });
        log.info("Deleting skill with ID: {}", skillId);
        skillRepository.delete(skill);

        log.info("Skill with ID: {} deleted successfully", skillId);
        return "Skill deleted";
    }

    public boolean isSkillOwner(Long skillId,String username) {
        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new EntityNotFoundException("Skill not found"));
        Portfolio portfolio = skill.getPortfolio();
        return portfolio.getMyUser().getUsername().equals(username);
    }

}
