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

import com.bmdb.business.movie.Movie;
import com.bmdb.business.movie.MovieRepository;
import com.bmdb.util.JsonResponse;

@Controller
@RequestMapping("/Movies")
public class MovieController {

	@Autowired
	private MovieRepository movieRepository;

	@GetMapping("/List")
	public @ResponseBody JsonResponse getAllMovies() {
		try {
			return JsonResponse.getInstance(movieRepository.findAll());
		} catch (Exception e) {
			return JsonResponse.getErrorInstance("Movie list failure:" + e.getMessage(), e);
		}
	}

	@GetMapping("/Get/{id}")
	public @ResponseBody JsonResponse getMovie(@PathVariable int id) {
		try {
			Optional<Movie> movie = movieRepository.findById(id);
			if (movie.isPresent())
				return JsonResponse.getInstance(movie.get());
			else
				return JsonResponse.getErrorInstance("Movie not found for id: " + id, null);
		} catch (Exception e) {
			return JsonResponse.getErrorInstance("Error getting movie:  " + e.getMessage(), null);
		}
	}

//	@PostMapping("/Login")
//	public @ResponseBody JsonResponse authenticate(@RequestBody Movie movie) {
//		try {
//			Movie u = movieRepository.findByMovieNameAndPassword(movie.getMovieName(), movie.getPassword());
//			return JsonResponse.getInstance(u);
//		} catch (Exception e) {
//			return JsonResponse.getErrorInstance("Error authenticating movie:  " + e.getMessage(), null);
//		}
//
//	}

	@PostMapping("/Add")
	public @ResponseBody JsonResponse addMovie(@RequestBody Movie movie) {
		return saveMovie(movie);
	}

	@PostMapping("/Change")
	public @ResponseBody JsonResponse updateMovie(@RequestBody Movie movie) {
		return saveMovie(movie);
	}

	private @ResponseBody JsonResponse saveMovie(@RequestBody Movie movie) {
		try {
			movieRepository.save(movie);
			return JsonResponse.getInstance(movie);
		} catch (DataIntegrityViolationException ex) {
			return JsonResponse.getErrorInstance(ex.getRootCause().toString(), ex);
		} catch (Exception ex) {
			return JsonResponse.getErrorInstance(ex.getMessage(), ex);
		}
	}

	@PostMapping("/Remove")
	public @ResponseBody JsonResponse removeMovie(@RequestBody Movie movie) {
		try {
			movieRepository.delete(movie);
			return JsonResponse.getInstance(movie);
		} catch (Exception ex) {
			return JsonResponse.getErrorInstance(ex.getMessage(), ex);
		}
	}

}
