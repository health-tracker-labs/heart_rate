package com.sergtm.component;

import com.sergtm.model.List;
import com.sergtm.model.RestPostsModel;
import com.sergtm.service.IPressureService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private IPressureService pressureService;

    @Autowired
    private RestTemplate restTemplate;

    private static final String EXTERNAL_REST_URL = "http://api.openweathermap.org/data/2.5/forecast?id=703448&appid=41a379bb03ac781ec6c814b80f49c0b4";

    @Scheduled(fixedRate = 1000*60*60*24)
    public void pull() {
        Map<LocalDate, Double> map = new TreeMap<>();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<RestPostsModel> response = restTemplate.exchange(
                EXTERNAL_REST_URL,
                HttpMethod.GET,
                entity,
                RestPostsModel.class
        );
        map = groupPage(response.getBody());
        pressureService.addAll(map);
    }

    private Map<LocalDate, Double> groupPage(RestPostsModel rpm){
        Map<LocalDate, java.util.List<Double>> map = new TreeMap<>();
        for (List jsonList : rpm.getList()) {
            LocalDate date = LocalDate.parse(jsonList.getDt_txt().substring(0,10));
            if (map.containsKey(date)){
                map.get(date).add(jsonList.getMain().getPressure());
            } else {
                java.util.List<Double> tmp = new ArrayList<>();
                tmp.add(jsonList.getMain().getPressure());
                map.put(date, tmp);
            }
        }
        return findAverage(map);
    }

    private Map<LocalDate, Double> findAverage(Map<LocalDate, java.util.List<Double>> map){
        Map<LocalDate, Double> res = new TreeMap<>();
        for (Map.Entry<LocalDate, java.util.List<Double>> entry : map.entrySet()){
            final LocalDate ld = entry.getKey();
            double avg = entry.getValue().stream().reduce(0d, Double::sum);
            //System.out.println(avg/entry.getValue().size());
            res.put(ld, (avg/entry.getValue().size())/MM_HG_TRANSLATION);
        }
        return res;
    }
}
