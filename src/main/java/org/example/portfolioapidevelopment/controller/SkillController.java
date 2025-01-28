package org.example.portfolioapidevelopment.controller;

import lombok.RequiredArgsConstructor;
import org.example.portfolioapidevelopment.dto.request.SkillRequestDto;
import org.example.portfolioapidevelopment.dto.response.SkillResponseDto;
import org.example.portfolioapidevelopment.service.SkillService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/skill")
public class SkillController {
    private final SkillService skillService;

    @PostMapping("/addSkill")
    public String addSkill(@RequestBody SkillRequestDto requestDto) {
        return skillService.addSkill(requestDto);
    }

    @GetMapping("/getAllSkills")
    public List<SkillResponseDto> getAllSkills() {
        return skillService.getAllSkills();
    }

    @PutMapping("/updateSkill")
    public String updateSkill(@RequestParam Long skillId,
            @RequestBody SkillRequestDto requestDto) {
        return skillService.updateSkill(skillId, requestDto);
    }

    @DeleteMapping("/deleteSkill")
    public String deleteSkill(@RequestParam Long skillId) {
        return skillService.deleteSkill(skillId);
    }
}
