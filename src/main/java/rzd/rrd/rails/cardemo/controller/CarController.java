package rzd.rrd.rails.cardemo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rzd.rrd.rails.cardemo.service.ClientService;
import rzd.rrd.rails.cardemo.service.EnrollService;
// import javax.annotation.PostConstruct;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class CarController {

    private final EnrollService enrollService;
    private final ClientService clientService;

    private Logger log = LoggerFactory.getLogger(CarController.class);

    @GetMapping(value = "/queryAllCars")
    public String queryAllCars() throws Exception {
        return clientService.evaluateTransaction("queryAllCars", "");
    }

    @GetMapping(value = "/enrollUser")
    public String enrollUser(@RequestParam(name = "forced", required = false) boolean forced) throws Exception {
        log.info("Request: enrollUser. forced: {}", forced);
        return enrollService.enrollUser(forced);
    }

    public CarController(EnrollService enrollService, ClientService clientService) {
        this.enrollService = enrollService;
        this.clientService = clientService;
    }
}