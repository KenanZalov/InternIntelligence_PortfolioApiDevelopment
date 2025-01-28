package org.example.portfolioapidevelopment.controller;

import lombok.RequiredArgsConstructor;
import org.example.portfolioapidevelopment.dto.request.EducationRequestDto;
import org.example.portfolioapidevelopment.dto.response.EducationResponseDto;
import org.example.portfolioapidevelopment.service.EducationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/education")
public class EducationController {
    private final EducationService educationService;

    @PostMapping("/addEducation")
    public String addEducation(@RequestBody EducationRequestDto educationRequestDto) {
        return educationService.addEducation(educationRequestDto);
    }

    @GetMapping("/getAllEducations")
    public List<EducationResponseDto> getAllEducations() {
        return educationService.getAllEducations();
    }

    @PutMapping("/updateEducation")
    public String updateEducation(@RequestParam Long educationId,
                                  @RequestBody EducationRequestDto educationRequestDto) {
        return educationService.updateEducation(educationId, educationRequestDto);
    }

    @DeleteMapping("/deleteEducation")
    public String deleteEducation(@RequestParam Long educationId) {
        return educationService.deleteEducation(educationId);
    }

}
