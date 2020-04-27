package rzd.rrd.rails.cardemo;

import org.springframework.web.bind.annotation.RestController;

import rzd.rrd.rails.cardemo.service.ClientService;
import rzd.rrd.rails.cardemo.service.EnrollService;
import javax.annotation.PostConstruct;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class CarController {

    private final EnrollService enrollService;
    private final ClientService clientService;

    @GetMapping(value = "/queryAllCars")
    public String queryAllCars() throws Exception {
        return clientService.evaluateTransaction("queryAllCars", "");
    }

    @PostConstruct
    public void enrollUsers() throws Exception {
        enrollService.enrollAdmin();
    }

    public CarController(EnrollService enrollService, ClientService clientService) {
        this.enrollService = enrollService;
        this.clientService = clientService;
    }
}