package foa.tcg.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("")
@CrossOrigin(origins = "http://localhost:3000")
public class RootController {

	@GetMapping("")
	public ResponseEntity<Map<String, String>> root() {
		return ResponseEntity.ok(Map.of("status", "ok"));
	}

}
