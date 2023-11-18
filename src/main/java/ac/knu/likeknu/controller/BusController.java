package ac.knu.likeknu.controller;

import ac.knu.likeknu.controller.dto.base.ResponseDto;
import ac.knu.likeknu.controller.dto.citybus.CityBusesArrivalTimeResponse;
import ac.knu.likeknu.controller.dto.citybus.RouteListResponse;
import ac.knu.likeknu.controller.dto.shuttlebus.ShuttleBusesArrivalTimeResponse;
import ac.knu.likeknu.controller.dto.shuttlebus.ShuttleListResponse;
import ac.knu.likeknu.domain.value.Campus;
import ac.knu.likeknu.service.CityBusService;
import ac.knu.likeknu.service.ShuttleBusService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

@RequestMapping("/api/buses")
@RestController
public class BusController {

    private final CityBusService cityBusService;
    private final ShuttleBusService shuttleBusService;

    public BusController(CityBusService cityBusService, ShuttleBusService shuttleBusService) {
        this.cityBusService = cityBusService;
        this.shuttleBusService = shuttleBusService;
    }

    @GetMapping("/city-bus/routes")
    public ResponseDto<List<RouteListResponse>> eachRouteCityBuses(@RequestParam("campus") Campus campus) {
        List<RouteListResponse> routeList = cityBusService.getRouteList(campus);
        return ResponseDto.of(routeList);
    }

    @GetMapping("/city-bus/{routeId}")
    public ResponseDto<List<CityBusesArrivalTimeResponse>> cityBusesArrivalTime(@PathVariable String routeId) {
        if (LocalTime.now().isAfter(LocalTime.of(23, 30))) {
            return ResponseDto.of(Collections.emptyList());
        }
        List<CityBusesArrivalTimeResponse> cityBusesArrivalTime = cityBusService.getCityBusesArrivalTime(routeId);
        return ResponseDto.of(cityBusesArrivalTime);
    }

    @GetMapping("/shuttle-bus/routes")
    public ResponseDto<List<ShuttleListResponse>> eachRouteShuttleBuses(@RequestParam("campus") Campus campus) {
        List<ShuttleListResponse> routeList = shuttleBusService.getRouteList(campus);
        return ResponseDto.of(routeList);
    }

    @GetMapping("/shuttle-bus/{shuttleId}")
    public ResponseDto<List<ShuttleBusesArrivalTimeResponse>> shuttleBusesArrivalTime(@PathVariable String shuttleId) {
        List<ShuttleBusesArrivalTimeResponse> shuttleBusesArrivalTime = shuttleBusService.getShuttleBusesArrivalTime(shuttleId);
        return ResponseDto.of(shuttleBusesArrivalTime);
    }
}
