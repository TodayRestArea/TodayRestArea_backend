package com.todayrestarea.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;


public class MusicInfoApi {

    /**
     * 20220115 yts
     * @param title : 노래 제목
     * @param artist : 가수 이름
     * @param limit : 검색 개수
     * @return 현재는 검색 결과중 유사도가 가장 높은게 맨 위에 있을거라 생각해 맨 처음 객체 리턴
     * --> TODO 여러개 검색한뒤 필터링해서 줄건지 , 그냥 맨위 줄건지 논의 필요
     */
    public Optional<MusicResponse> getMusicInfo(String title,String artist,Integer limit){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        List<MusicResponse> resultList=new ArrayList<>();
        try {
            ResponseEntity<String> s = restTemplate
                    .exchange(
                            "https://ws.audioscrobbler.com/2.0/"
                                    + "?limit="+limit.toString()
                                    + "&method=album.search"
                                    + "&artist="+artist
                                    + "&album="+title
                                    + "&api_key=92bb2a282b598563622ecc43dc4d14ac" //TODO env에 key 저장
                                    + "&format=json"
                            , HttpMethod.GET, entity, String.class);
            System.out.println("++++++++res\n");
            //convert response json to HASH MAP<string,obj>
            String res = s.getBody().toString();
            ObjectMapper om = new ObjectMapper();
            Map<String,HashMap<String,HashMap<String,List<HashMap<String,Object>>>>> obj = om.readValue(res, HashMap.class);
            List<HashMap<String,Object>> resBody=obj.get("results").get("albummatches").get("album");
            //
            System.out.println("#yts# res success  = " + resBody.toString());

            //parsing hashMap
            for(HashMap<String,Object> item:resBody){
                List<Image> temp=(List<Image>)item.get("image");
                String nm=item.get("name").toString();
                String atst=item.get("artist").toString();
                String url=item.get("url").toString();
                MusicResponse cur=new MusicResponse(nm,atst,url,temp);
                resultList.add(cur);
            }
        } catch (Exception e) {
            System.out.println("At MusicInfoApi.getMusicInfo"+" \nreq ERROR message: "+e.getMessage());
        }finally {
            return resultList.stream().findFirst();
        }

    }

    public static void main(String[] args) {
        MusicInfoApi m=new MusicInfoApi();
        Optional<MusicResponse> musicInfo = m.getMusicInfo("snowman", "sia", 30);
        System.out.println("#########\n#######\nmusicInfo = " + musicInfo.toString());
    }
}
