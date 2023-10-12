package ac.knu.likeknu.controller;

import ac.knu.likeknu.controller.dto.base.ResponseDto;
import ac.knu.likeknu.controller.dto.citybus.CityBusesArrivalTimeResponse;
import ac.knu.likeknu.controller.dto.citybus.RouteListResponse;
import ac.knu.likeknu.domain.value.Campus;
import ac.knu.likeknu.domain.value.ShuttleType;
import ac.knu.likeknu.service.CityBusService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/buses")
@RestController
public class BusController {

    private final CityBusService cityBusService;

    public BusController(CityBusService cityBusService) {
        this.cityBusService = cityBusService;
    }

    @GetMapping("/city-bus/routes")
    public ResponseDto<List<RouteListResponse>> eachRouteCityBuses(@RequestParam("campus") Campus campus) {
        List<RouteListResponse> routeList = cityBusService.getRouteList(campus);
        return ResponseDto.of(routeList);
    }

    @GetMapping("/city-buses/{routeId}")
    public ResponseDto<List<CityBusesArrivalTimeResponse>> cityBusesArrivalTime(@PathVariable String routeId) {
        List<CityBusesArrivalTimeResponse> cityBusesArrivalTime = cityBusService.getCityBusesArrivalTime(routeId);
        return ResponseDto.of(cityBusesArrivalTime);
    }

    @GetMapping("/{shuttleType}/routes")
    public ResponseDto<List<RouteListResponse>> eachRouteShuttleBuses(
            @PathVariable String shuttleType, @RequestParam("campus") Campus campus
    ) {
        List<RouteListResponse> routeList = cityBusService.getRouteList(campus, ShuttleType.of(shuttleType));
        return ResponseDto.of(routeList);
    }
}
