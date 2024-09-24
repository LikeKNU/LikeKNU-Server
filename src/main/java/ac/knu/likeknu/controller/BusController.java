package ac.knu.likeknu.controller;

import ac.knu.likeknu.controller.dto.base.ResponseDto;
import ac.knu.likeknu.controller.dto.citybus.CityBusRoutesResponse;
import ac.knu.likeknu.controller.dto.citybus.CityBusesResponse;
import ac.knu.likeknu.controller.dto.shuttlebus.ShuttleBusesArrivalTimeResponse;
import ac.knu.likeknu.controller.dto.shuttlebus.ShuttleListResponse;
import ac.knu.likeknu.domain.constants.Campus;
import ac.knu.likeknu.domain.constants.RouteType;
import ac.knu.likeknu.service.CityBusService;
import ac.knu.likeknu.service.ShuttleBusService;
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
    private final ShuttleBusService shuttleBusService;

    public BusController(CityBusService cityBusService, ShuttleBusService shuttleBusService) {
        this.cityBusService = cityBusService;
        this.shuttleBusService = shuttleBusService;
    }

    @GetMapping("/city-bus/{type}")
    public ResponseDto<List<CityBusesResponse>> cityBusesArrivalTime(
            @RequestParam("campus") Campus campus, @PathVariable("type") String type,
            @RequestParam(value = "isRefresh", required = false) boolean isRefresh
    ) {
        RouteType routeType = RouteType.of(type);
        List<CityBusesResponse> cityBusesArrivalTime = cityBusService.getCityBusesArrivalTime(campus, routeType);
        return ResponseDto.of(cityBusesArrivalTime);
    }

    @GetMapping("/city-bus/routes")
    public ResponseDto<List<CityBusRoutesResponse>> eachRouteCityBuses(@RequestParam("campus") Campus campus) {
        List<CityBusRoutesResponse> routeList = cityBusService.getRouteList(campus);
        return ResponseDto.of(routeList);
    }

    @GetMapping("/city-bus/{routeId}/arrival-time")
    public ResponseDto<CityBusesResponse> cityBusesArrivalTime(
            @PathVariable("routeId") String routeId
    ) {
        CityBusesResponse cityBusesArrivalTime = cityBusService.getCityBusesArrivalTime(routeId);
        return ResponseDto.of(cityBusesArrivalTime);
    }

    @GetMapping("/shuttle-bus/routes")
    public ResponseDto<List<ShuttleListResponse>> eachRouteShuttleBuses(@RequestParam("campus") Campus campus) {
        List<ShuttleListResponse> routeList = shuttleBusService.getRouteList(campus);
        return ResponseDto.of(routeList);
    }

    @GetMapping("/shuttle-bus/{shuttleId}")
    public ResponseDto<List<ShuttleBusesArrivalTimeResponse>> shuttleBusesArrivalTime(
            @PathVariable("shuttleId") String shuttleId
    ) {
        List<ShuttleBusesArrivalTimeResponse> shuttleBusesArrivalTime = shuttleBusService.getShuttleBusesArrivalTime(shuttleId);
        return ResponseDto.of(shuttleBusesArrivalTime);
    }
}
