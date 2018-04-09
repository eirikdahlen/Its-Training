package tdt4140.gr1812.app.backend.server;

import org.json.JSONArray;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;


@RestController

public class ServerController {
	// Maps to signup-endpoint
	@RequestMapping("/hello")
    public String signup(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) throws Exception{

		JSONObject obj = new JSONObject();
		obj.put("status", "success");
		obj.put("name", name);
		return obj.toString() ;

    }

	@GetMapping("/hello")
	@ResponseBody
	public String sayHello(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) {
		return "yo" + name;
	}


}