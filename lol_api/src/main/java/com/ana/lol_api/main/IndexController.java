package com.ana.lol_api.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
	/**
	 * LeagueOfLegend API KEY(period day 1)
	 */
	private final static String API_KEY = "RGAPI-b3349c0f-9556-4300-a8fe-a8254b3c11fb";

	
	/**
	 * 상수
	 */
	private final static String	MAGE = "마법사";
	private final static String	SUPPORT = "서포터";
	private final static String	FIGHTER = "전사";
	private final static String	MARKSMAN = "원거리";
	private final static String	TANK = "탱커";
	private final static String	ASSASSIN = "암살자";
	
	@GetMapping("/")
	public ModelAndView index() throws ParseException, IOException {
		ModelAndView mv = new ModelAndView("/main/index");
		
		List<Map<String, Object>> allChamp = getAllChampion();
		List<String> ratateChamp = getRotationChampion();
		List<Map<String, Object>> thisWeekRotateChamp = new ArrayList<>();
		for(int i = 0; i < ratateChamp.size(); i++) {
			for(int j = 0; j < allChamp.size(); j++) {
				if(allChamp.get(j).get("key").equals(ratateChamp.get(i))) {
					thisWeekRotateChamp.add(allChamp.get(j));
				}
			}
		}
		
		mv.addObject("thisWeekRotateChamp", thisWeekRotateChamp);
		
		return mv;
	}
	
	/**
	 * LeagueOfLegend 모든 챔피언
	 */
	public List<Map<String, Object>> getAllChampion() throws IOException {
		List<Map<String, Object>> champList = new ArrayList<>();
		
		try {
			URL url = new URL("https://ddragon.leagueoflegends.com/cdn/14.13.1/data/ko_KR/champion.json");
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("GET");
			urlConnection.setRequestProperty("Content-type", "application/json");
			urlConnection.setDoOutput(true);
			
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));			
			StringBuilder stringBuilder = new StringBuilder();
			while(bufferedReader.ready()) {
				stringBuilder.append(bufferedReader.readLine());
			}
			
			JSONObject champDataList = (JSONObject) new JSONParser().parse(stringBuilder.toString());
			JSONObject champData = (JSONObject) champDataList.get("data");
			List<String> champDataKey = new ArrayList<>(champData.keySet());
			Map<String, Object> champListMap = null;
			ArrayList<String> champPositionArr;
			for(int i = 0; i < champDataKey.size(); i++) {
				JSONObject champObject = (JSONObject) champData.get(champDataKey.get(i));
				champListMap = new HashMap<>();
				champPositionArr = (ArrayList<String>) champObject.get("tags");
				String champPosition = champPositionArr.get(0);
				
				switch(champPosition) {
				case "Mage": champPosition = MAGE; break;
				case "Marksman": champPosition = MARKSMAN; break;
				case "Fighter": champPosition = FIGHTER; break;
				case "Tank": champPosition = TANK; break;
				case "Support": champPosition = SUPPORT; break;
				case "Assassin": champPosition = ASSASSIN; break;
				default: champPosition = "not found"; break;
				}
				
				champListMap.put("version", champObject.get("version"));
				champListMap.put("tags", champPosition);
				champListMap.put("key", champObject.get("key"));
				champListMap.put("name", champObject.get("name"));
				champListMap.put("id", champObject.get("id"));
				
				champList.add(champListMap);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return champList;
	}
	
	/**
	 * LeagueOfLegend 로테이션 챔피언
	 */
	public ArrayList<String> getRotationChampion() throws IOException {
		ArrayList<String> rChampList = new ArrayList<String>();
		
		try {
			URL url = new URL("https://kr.api.riotgames.com/lol/platform/v3/champion-rotations?api_key=" + API_KEY);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("GET");
			urlConnection.setRequestProperty("Content-type", "application/json");
			urlConnection.setDoOutput(true);
			
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));			
			StringBuilder stringBuilder = new StringBuilder();
			while(bufferedReader.ready()) {
				stringBuilder.append(bufferedReader.readLine());
			}
			
			JSONObject rChampDataList = (JSONObject) new JSONParser().parse(stringBuilder.toString());
			JSONArray rChampData = (JSONArray) rChampDataList.get("freeChampionIds");
			
			if(rChampData != null) {
				for(int i = 0; i < rChampData.size(); i++) {
					rChampList.add(rChampData.get(i).toString());
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return rChampList;
	}
}
