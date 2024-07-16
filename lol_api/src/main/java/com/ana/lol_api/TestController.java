package com.ana.lol_api;

import static org.hamcrest.CoreMatchers.instanceOf;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController {
	private final static String API_KEY = "RGAPI-5157903c-09b1-408e-93f2-ea9c9b951a52";
	
	@GetMapping("/test")
	public ModelAndView test() throws ParseException {
		ModelAndView mv = new ModelAndView("/test");
		URL url = null;
		HttpURLConnection conn = null;
		JSONObject result = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			url = new URL("https://ddragon.leagueoflegends.com/cdn/14.13.1/data/ko_KR/champion.json");
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "application/json");
			conn.setDoOutput(true);
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			while(br.ready()) {
				sb.append(br.readLine());
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		result = (JSONObject) new JSONParser().parse(sb.toString());
		JSONObject data = (JSONObject) result.get("data");
		List<String> keyList = new ArrayList<>(data.keySet());
		
		JSONObject tmp = null;
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> map1 = null;
		List<String> asdf = new ArrayList<>();
		
		for(int i = 0; i < keyList.size(); i++) {
			map1 = new HashMap<>();
			tmp = (JSONObject) data.get(keyList.get(i));
			map1.put("version", (String) tmp.get("version"));
			map1.put("key", (String) tmp.get("key"));
			map1.put("name", (String) tmp.get("name"));
			map1.put("id", (String) tmp.get("id"));
			
			asdf.add((String) tmp.get("key"));
			
			list.add(map1);
		}
		
		List<Map<String, Object>> rotate = new ArrayList<>();
		
		List<String> qwer = index();
		
		
		for(int i = 0; i < qwer.size(); i++) {
			for(int j = 0; j < list.size(); j++) {
				if(list.get(j).get("key").equals(qwer.get(i))) {
					rotate.add(list.get(j));
				}
			}
		}
		
		
		System.out.println(rotate);
		
		/*
		List<String> oldList = index();
		List<String> newList = asdf;

		List<String> matchList = oldList.stream().filter(o -> newList.stream()
				.anyMatch(Predicate.isEqual(o))).collect(Collectors.toList());

		System.out.println(matchList.toString());
		*/
		
		
		//System.out.println(instanceOf(index().getClass()));
		
		//mv.addObject("version", map1.get("version"));
		mv.addObject("rotate_champ", rotate);
		mv.addObject("lol_champion", list);
		
		return mv;
	}
	
	public ArrayList<String> index() throws ParseException {
		URL url = null;
		HttpURLConnection conn = null;
		JSONObject result;
		StringBuilder sb = new StringBuilder();
		
		try {
			url = new URL("https://kr.api.riotgames.com/lol/platform/v3/champion-rotations?api_key=" + API_KEY);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "application/json");
			conn.setDoOutput(true);
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			while(br.ready()) {
				sb.append(br.readLine());
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		result = (JSONObject) new JSONParser().parse(sb.toString());
		ArrayList<String> listdata = new ArrayList<String>();
		JSONArray array = (JSONArray) result.get("freeChampionIds");
		
		if(array != null) {
			for(int i = 0; i < array.size(); i++) {
				listdata.add(array.get(i).toString());
			}
		}
		
		///System.out.println(listdata);
		
		return listdata;
	}
}
