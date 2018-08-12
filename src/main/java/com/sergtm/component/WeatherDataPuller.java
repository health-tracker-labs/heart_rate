package com.sergtm.component;

import com.sergtm.model.ServiceName;
import com.sergtm.model.pressureModel.List;
import com.sergtm.model.pressureModel.RestPostsModel;
import com.sergtm.model.weatherModel.WeatherModel;
import com.sergtm.service.IPressureService;
import com.sergtm.service.IStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

@Component
public class WeatherDataPuller {
    private static final double MM_HG_TRANSLATION = 1.33322387415;

    @Autowired
    private IStatusService statusService;

    @Autowired
    private IPressureService pressureService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${openWeatherMapUrl}")
    private String openWhetherMapUrl;

    @Value("${currentWeatherUrl}")
    private String currentWeatherUrl;

    @Scheduled(cron = "${whetherDataPullerDelay}")
    public synchronized void pullFiveDaysWeatherData() {
        System.out.println("Entered");
        Map<LocalDate, Double> map = new TreeMap<>();
        ResponseEntity<RestPostsModel> response = exchange(openWhetherMapUrl, RestPostsModel.class);
        map = groupPage(response.getBody());
        pressureService.addAll(map);

        statusService.updateAndSave(ServiceName.PressureService);
    }

    public WeatherModel pullTodayWeatherData() {
        ResponseEntity<WeatherModel> response = exchange(currentWeatherUrl, WeatherModel.class);
        return response.getBody();
    }

    private <T> ResponseEntity<T> exchange(String url, Class<T> clazz) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                clazz
        );
    }

    private Map<LocalDate, Double> groupPage(RestPostsModel rpm) {
        Map<LocalDate, java.util.List<Double>> map = new TreeMap<>();
        for (List jsonList : rpm.getList()) {
            LocalDate date = LocalDate.parse(jsonList.getDt_txt().substring(0, 10));
            if (map.containsKey(date)) {
                map.get(date).add(jsonList.getMain().getPressure());
            } else {
                java.util.List<Double> tmp = new ArrayList<>();
                tmp.add(jsonList.getMain().getPressure());
                map.put(date, tmp);
            }
        }
        return findAverage(map);
    }

    private Map<LocalDate, Double> findAverage(Map<LocalDate, java.util.List<Double>> map) {
        Map<LocalDate, Double> res = new TreeMap<>();
        for (Map.Entry<LocalDate, java.util.List<Double>> entry : map.entrySet()) {
            final LocalDate ld = entry.getKey();
            double avg = entry.getValue().stream().reduce(0d, Double::sum);
            //System.out.println(avg/entry.getValue().size());
            res.put(ld, (avg / entry.getValue().size()) / MM_HG_TRANSLATION);
        }
        return res;
    }
}
