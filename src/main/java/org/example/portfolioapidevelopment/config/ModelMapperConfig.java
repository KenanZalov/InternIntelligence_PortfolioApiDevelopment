package org.example.portfolioapidevelopment.config;

import org.example.portfolioapidevelopment.dao.*;
import org.example.portfolioapidevelopment.dto.request.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        mapper.createTypeMap(PortfolioRequestDto.class, Portfolio.class)
                .addMapping(src -> null, Portfolio::setId)
                .addMapping(PortfolioRequestDto::getMyUserId, (dest, value) -> {
                    MyUser myUser = new MyUser();
                    myUser.setId((Long) value);
                    dest.setMyUser(myUser);
                });

        mapper.createTypeMap(SkillRequestDto.class, Skill.class)
                .addMapping(src -> null, Skill::setId)
                .addMapping(SkillRequestDto::getPortfolioId, (dest, value) -> {
                    Portfolio portfolio = new Portfolio();
                    portfolio.setId((Long) value);
                    dest.setPortfolio(portfolio);
                });

        mapper.createTypeMap(ProjectRequestDto.class, Project.class)
                .addMapping(src -> null, Project::setId)
                .addMapping(ProjectRequestDto::getPortfolioId, (dest, value) -> {
                    Portfolio portfolio = new Portfolio();
                    portfolio.setId((Long) value);
                    dest.setPortfolio(portfolio);
                });

        mapper.createTypeMap(ExperienceRequestDto.class, Experience.class)
                .addMapping(src -> null, Experience::setId)
                .addMapping(ExperienceRequestDto::getPortfolioId, (dest, value) -> {
                    Portfolio portfolio = new Portfolio();
                    portfolio.setId((Long) value);
                    dest.setPortfolio(portfolio);
                });

        mapper.createTypeMap(EducationRequestDto.class, Education.class)
                .addMapping(src -> null, Education::setId)
                .addMapping(EducationRequestDto::getPortfolioId, (dest, value) -> {
                    Portfolio portfolio = new Portfolio();
                    portfolio.setId((Long) value);
                    dest.setPortfolio(portfolio);
                });

        return mapper;
    }


}
