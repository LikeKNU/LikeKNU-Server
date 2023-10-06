package ac.knu.likeknu.controller;

import ac.knu.likeknu.controller.dto.citybus.CityBusesArrivalTimeResponse;
import ac.knu.likeknu.controller.dto.citybus.RouteListResponse;
import ac.knu.likeknu.controller.dto.base.ResponseDto;
import ac.knu.likeknu.domain.value.Campus;
import ac.knu.likeknu.service.CityBusService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("{routeId}")
    public ResponseDto<List<CityBusesArrivalTimeResponse>> cityBusesArrivalTime(@PathVariable String routeId) {
        List<CityBusesArrivalTimeResponse> cityBusesArrivalTime = cityBusService.getCityBusesArrivalTime(routeId);
        return ResponseDto.of(cityBusesArrivalTime);
    }
}
