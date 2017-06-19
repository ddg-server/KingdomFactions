package nl.dusdavidgames.kingdomfactions.modules.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Randomizer<E> {

	public Randomizer(List<E> list2) {
		setList(list2);
	}
	public Randomizer(E[] list) {
		setList(list);
	}
	
	public Randomizer() {
		this.list = new ArrayList<E>();
	}
	
	public Randomizer<E> setList(List<E> list) {
		this.list = list;
		return this;
	}
	public Randomizer<E> setList(E[] list) {
		this.list = (ArrayList<E>) Arrays.asList(list);
		return this;
	}
	
	private List<E> list;
	
	public E result() {
		return this.list.get(new Random().nextInt(this.list.size()));
	}
	
	public boolean hasResult() {
		return result() != null;
	}
}
