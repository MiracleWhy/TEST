package com.uton.carsokApi.dao;

import java.util.List;

import com.uton.carsokApi.model.Categorie;

public interface CategorieMapper {
	List<String> searchAllInitial();
	
	List<Categorie> searchCarBrand();
	
	List<Categorie> searchCarmodel(int id);
	
	List<String> searchYeartype(int id);

	String selectByCarBrand(String parameter);
}
