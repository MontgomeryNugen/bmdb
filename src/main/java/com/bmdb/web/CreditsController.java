package com.bmdb.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bmdb.business.credits.Credits;
import com.bmdb.business.credits.CreditsRepository;
import com.bmdb.util.JsonResponse;

@Controller
@RequestMapping("/Credits")
public class CreditsController {

	@Autowired
	private CreditsRepository creditsRepository;

	@GetMapping("/List")
	public @ResponseBody JsonResponse getAllCredits() {
		try {
			return JsonResponse.getInstance(creditsRepository.findAll());
		} catch (Exception e) {
			return JsonResponse.getErrorInstance("Credits list failure:" + e.getMessage(), e);
		}
	}

	@GetMapping("/Get/{id}")
	public @ResponseBody JsonResponse getCredits(@PathVariable int id) {
		try {
			Optional<Credits> credits = creditsRepository.findById(id);
			if (credits.isPresent())
				return JsonResponse.getInstance(credits.get());
			else
				return JsonResponse.getErrorInstance("Credits not found for id: " + id, null);
		} catch (Exception e) {
			return JsonResponse.getErrorInstance("Error getting credits:  " + e.getMessage(), null);
		}
	}

//	@PostMapping("/Login")
//	public @ResponseBody JsonResponse authenticate(@RequestBody Credits credits) {
//		try {
//			Credits u = creditsRepository.findByCredistNameAndPassword(credits.getCreditsName(), credits.getPassword());
//			return JsonResponse.getInstance(u);
//		} catch (Exception e) {
//			return JsonResponse.getErrorInstance("Error authenticating credits:  " + e.getMessage(), null);
//		}
//
//	}

	@PostMapping("/Add")
	public @ResponseBody JsonResponse addCredits(@RequestBody Credits credits) {
		return saveCredits(credits);
	}

	@PostMapping("/Change")
	public @ResponseBody JsonResponse updateCredits(@RequestBody Credits credits) {
		return saveCredits(credits);
	}

	private @ResponseBody JsonResponse saveCredits(@RequestBody Credits credits) {
		try {
			creditsRepository.save(credits);
			return JsonResponse.getInstance(credits);
		} catch (DataIntegrityViolationException ex) {
			return JsonResponse.getErrorInstance(ex.getRootCause().toString(), ex);
		} catch (Exception ex) {
			return JsonResponse.getErrorInstance(ex.getMessage(), ex);
		}
	}

	@PostMapping("/Remove")
	public @ResponseBody JsonResponse removeCredits(@RequestBody Credits credits) {
		try {
			creditsRepository.delete(credits);
			return JsonResponse.getInstance(credits);
		} catch (Exception ex) {
			return JsonResponse.getErrorInstance(ex.getMessage(), ex);
		}
	}

}
