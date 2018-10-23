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

import com.bmdb.business.actor.Actor;
import com.bmdb.business.actor.ActorRepository;
import com.bmdb.util.JsonResponse;

@Controller
@RequestMapping("/Actors")
public class ActorController {

	@Autowired
	private ActorRepository actorRepository;

	@GetMapping("/List")
	public @ResponseBody JsonResponse getAllActors() {
		try {
			return JsonResponse.getInstance(actorRepository.findAll());
		} catch (Exception e) {
			return JsonResponse.getErrorInstance("Actor list failure:" + e.getMessage(), e);
		}
	}

	@GetMapping("/Get/{id}")
	public @ResponseBody JsonResponse getActor(@PathVariable int id) {
		try {
			Optional<Actor> actor = actorRepository.findById(id);
			if (actor.isPresent())
				return JsonResponse.getInstance(actor.get());
			else
				return JsonResponse.getErrorInstance("Actor not found for id: " + id, null);
		} catch (Exception e) {
			return JsonResponse.getErrorInstance("Error getting actor:  " + e.getMessage(), null);
		}
	}

//	@PostMapping("/Login")
//	public @ResponseBody JsonResponse authenticate(@RequestBody Actor actor) {
//		try {
//			Actor u = actorRepository.findByActorNameAndPassword(actor.getActorName(), actor.getPassword());
//			return JsonResponse.getInstance(u);
//		} catch (Exception e) {
//			return JsonResponse.getErrorInstance("Error authenticating actor:  " + e.getMessage(), null);
//		}
//
//	}

	@PostMapping("/Add")
	public @ResponseBody JsonResponse addActor(@RequestBody Actor actor) {
		return saveActor(actor);
	}

	@PostMapping("/Change")
	public @ResponseBody JsonResponse updateActor(@RequestBody Actor actor) {
		return saveActor(actor);
	}

	private @ResponseBody JsonResponse saveActor(@RequestBody Actor actor) {
		try {
			actorRepository.save(actor);
			return JsonResponse.getInstance(actor);
		} catch (DataIntegrityViolationException ex) {
			return JsonResponse.getErrorInstance(ex.getRootCause().toString(), ex);
		} catch (Exception ex) {
			return JsonResponse.getErrorInstance(ex.getMessage(), ex);
		}
	}

	@PostMapping("/Remove")
	public @ResponseBody JsonResponse removeActor(@RequestBody Actor actor) {
		try {
			actorRepository.delete(actor);
			return JsonResponse.getInstance(actor);
		} catch (Exception ex) {
			return JsonResponse.getErrorInstance(ex.getMessage(), ex);
		}
	}

}
