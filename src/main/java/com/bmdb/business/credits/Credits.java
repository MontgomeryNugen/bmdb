package com.bmdb.business.credits;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.bmdb.business.actor.Actor;
import com.bmdb.business.movie.Movie;

@Entity
public class Credits {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name = "movieID")
	private Movie movie;
	@ManyToOne
	@JoinColumn(name = "actorID")
	private Actor actor;
	private String characterName;

	public Credits() {
		super();
	}

	public Credits(int id, Movie movie, Actor actor, String characterName) {
		super();
		this.id = id;
		this.movie = movie;
		this.actor = actor;
		this.characterName = characterName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public Actor getActor() {
		return actor;
	}

	public void setActor(Actor actor) {
		this.actor = actor;
	}

	public String getCharacterName() {
		return characterName;
	}

	public void setCharacterName(String characterName) {
		this.characterName = characterName;
	}

	@Override
	public String toString() {
		return "Credits [id=" + id + ", movie=" + movie + ", actor=" + actor + ", characterName=" + characterName + "]";
	}

}
