package ac.knu.likeknu.controller;

import ac.knu.likeknu.controller.dto.base.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Slf4j
@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {
    @GetMapping
    public ResponseDto getSchedule(
            @RequestParam(name = "year", defaultValue = "year") Integer year,
            @RequestParam(name = "month", defaultValue = "month") Integer month
    ) {
        return ResponseDto.of(null);
    }

    @InitBinder
    private void InitBinder(WebDataBinder binder) throws Exception {

        CustomNumberEditor customNumberEditor = new CustomNumberEditor(Integer.class, true) {

            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                if ("year".equals(text)) {
                    setValue(LocalDate.now().getYear());
                } else if("month".equals(text)) {
                    setValue(LocalDate.now().getMonthValue());
                } else {
                    super.setAsText(text);
                }
            }
        };

        binder.registerCustomEditor(Integer.class, customNumberEditor);
    }

}
