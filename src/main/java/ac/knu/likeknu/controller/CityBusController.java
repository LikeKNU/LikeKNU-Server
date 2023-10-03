package ac.knu.likeknu.controller;

import ac.knu.likeknu.controller.dto.response.ResponseDto;
import ac.knu.likeknu.controller.dto.response.RouteListResponse;
import ac.knu.likeknu.domain.value.Campus;
import ac.knu.likeknu.service.CityBusService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/city-buses")
@RestController
public class CityBusController {

    private final CityBusService cityBusService;

    public CityBusController(CityBusService cityBusService) {
        this.cityBusService = cityBusService;
    }

    @GetMapping("/routes")
    public ResponseDto<List<RouteListResponse>> eachRouteCityBuses(@RequestParam("campus") Campus campus) {
        List<RouteListResponse> routeList = cityBusService.getRouteList(campus);
        return ResponseDto.of(routeList);
    }
}
