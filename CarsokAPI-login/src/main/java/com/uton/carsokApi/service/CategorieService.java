package com.uton.carsokApi.service;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.uton.carsokApi.controller.response.CarModelsResponse;
import com.uton.carsokApi.dao.TCarBrandMapper;
import com.uton.carsokApi.dao.TCarModelMapper;
import com.uton.carsokApi.dao.TCarSeriesMapper;
import com.uton.carsokApi.model.TCarBrand;
import com.uton.carsokApi.model.TCarModel;
import com.uton.carsokApi.model.TCarSeries;

import com.uton.carsokApi.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.controller.request.CategorieRequest;
import com.uton.carsokApi.controller.response.CategorieResponse;
import com.uton.carsokApi.dao.CategorieMapper;
import com.uton.carsokApi.model.Categorie;

@Service
public class CategorieService {
	@Autowired
	CategorieMapper categorieMapper;
	@Autowired
	TCarBrandMapper tCarBrandMapper;
	@Autowired
	TCarSeriesMapper tCarSeriesMapper;
	@Autowired
	TCarModelMapper tCarModelMapper;
	
	/**
	 * 车辆品牌
	 * @return
	 */
/*	public BaseResult carBrand(){

		Map<String,List<CategorieResponse>> brandMap = new TreeMap<String, List<CategorieResponse>>();
		//获取所有品牌首字母
		List<String> initial_list = categorieMapper.searchAllInitial();

		//获取所有车型品牌
		List<Categorie> categroid_list = categorieMapper.searchCarBrand();
		if(initial_list.size()>0 && categroid_list.size()>0){
			for(int i = 0;i<initial_list.size();i++){
				String initial = initial_list.get(i);
				List<CategorieResponse> cates = new ArrayList<>();
				//Iterator迭代categroid_list
				Iterator<Categorie> it = categroid_list.iterator();
				while(it.hasNext()){
					Categorie categorie = it.next();
					if (categorie.getInitial().equals(initial)) {
						CategorieResponse result = new CategorieResponse();
						result.setId(categorie.getId());
						result.setInitial(categorie.getInitial());
						result.setLogo(categorie.getLogo());
						result.setName(categorie.getName());
						cates.add(result);
						it.remove(); //移除该对象
					}
				}
				brandMap.put(initial,cates);
			}
		}
		return BaseResult.success(brandMap);
	}
	*/


	public BaseResult carBrand(){
		
		Map<String,List<CategorieResponse>> brandMap = new TreeMap<String, List<CategorieResponse>>();
		//获取所有品牌首字母
		List<String> initial_list = tCarBrandMapper.searchAllInitial();
		
		//获取所有车型品牌
		List<TCarBrand> tCarBrandList = tCarBrandMapper.searchAllBrand();
		if(initial_list.size()>0 && tCarBrandList.size()>0){
			for(int i = 0;i<initial_list.size();i++){
				String initial = initial_list.get(i);
				List<CategorieResponse> cates = new ArrayList<>();
				//Iterator迭代categroid_list
				Iterator<TCarBrand> it = tCarBrandList.iterator();
				while(it.hasNext()){
					TCarBrand tCarBrand = it.next();
			        if (tCarBrand.getInitial().equals(initial)) {
			        	CategorieResponse result = new CategorieResponse();
			        	result.setId(tCarBrand.getBrandId());
			        	result.setInitial(tCarBrand.getInitial());
			        	result.setLogo(tCarBrand.getLogo());
			        	result.setName(tCarBrand.getBrandName());
			        	cates.add(result);
			            it.remove(); //移除该对象
			        }
			    }
				brandMap.put(initial,cates);
			}
		}
		return BaseResult.success(brandMap);
	}
	
/*	*//**
	 * 车系
	 * @param
	 * @return
	 *//*
	public BaseResult carModel(CategorieRequest request){
		Map<String,List<Categorie>> brandMap = new TreeMap<String, List<Categorie>>();
		List<Categorie> categroid_list = categorieMapper.searchCarmodel(request.getId());
		if(categroid_list!=null && categroid_list.size()>0){
			for(Categorie cate:categroid_list){
				List<Categorie> categroid_list_2 = categorieMapper.searchCarmodel(cate.getId());
				brandMap.put(cate.getName(), categroid_list_2);
			}
		}
		return BaseResult.success(brandMap);
	}*/

	public BaseResult carModel(CategorieRequest request){
		Map<String,List<Categorie>> modelMap = new TreeMap<String, List<Categorie>>();

		//获取所有品牌首字母
		List<String> modelYearList = tCarModelMapper.searchAllModelYear(request.getId());

		//获取所有车型品牌
		List<TCarModel> tCarModelList = tCarModelMapper.searchAllModel(request.getId());
		if(modelYearList.size()>0 && modelYearList.size()>0){
			for(int i = 0;i<modelYearList.size();i++){
				String modelYear = modelYearList.get(i);
				List<Categorie> cates = new ArrayList<>();
				//Iterator迭代categroid_list
				Iterator<TCarModel> it = tCarModelList.iterator();
				while(it.hasNext()){
					TCarModel tCarModel = it.next();
					if (tCarModel.getModelYear().toString().equals(modelYear)) {
						Categorie result = new Categorie();
						result.setFullname(tCarModel.getModelName());
						result.setName(tCarModel.getModelName());
						result.setId(Integer.parseInt(tCarModel.getModelId()));
						result.setPrice(tCarModel.getModelPrice().toString());
						result.setYeartype(tCarModel.getModelYear().toString());
						result.setParentid(Integer.parseInt(tCarModel.getSeriesId()));

						cates.add(result);
						it.remove(); //移除该对象
					}
				}
				modelMap.put(modelYear,cates);
			}
		}
		return BaseResult.success(modelMap);
	}
	
/*	public BaseResult carType(CategorieRequest request){
		Map<String,List<Categorie>> brandMap = new TreeMap<String, List<Categorie>>(
				new Comparator<String>() {
					public int compare(String obj1, String obj2) {
                        // 降序排序
                        return obj2.compareTo(obj1);
                    }
				}
				);
		//获取所选车型的所有年份值
		List<String> yearTypelist = categorieMapper.searchYeartype(request.getId());
		List<Categorie> categroid_list = categorieMapper.searchCarmodel(request.getId());
		if(categroid_list!=null && categroid_list.size()>0 && yearTypelist!=null && yearTypelist.size()>0){
			for(int i=0;i<yearTypelist.size();i++){
				String yeartype = yearTypelist.get(i);
				List<Categorie> cates = new ArrayList<>();
				Iterator<Categorie> it = categroid_list.iterator();
				while(it.hasNext()){
					Categorie categorie = it.next(); 
			        if (categorie.getYeartype().equals(yeartype)) {
			        	cates.add(categorie);
			            it.remove(); //移除该对象
			        }
			    }
				brandMap.put(yeartype,cates);
			}
		}
		return BaseResult.success(brandMap);
	}*/
	public BaseResult carType(CategorieRequest request){
/*		Map<String,List<Categorie>> brandMap = new TreeMap<String, List<Categorie>>(
				new Comparator<String>() {
					public int compare(String obj1, String obj2) {
						// 降序排序
						return obj2.compareTo(obj1);
					}
				}
		);
		//获取所选车型的所有年份值
		List<String> yearTypelist = categorieMapper.searchYeartype(request.getId());
		List<Categorie> categroid_list = categorieMapper.searchCarmodel(request.getId());
		if(categroid_list!=null && categroid_list.size()>0 && yearTypelist!=null && yearTypelist.size()>0){
			for(int i=0;i<yearTypelist.size();i++){
				String yeartype = yearTypelist.get(i);
				List<Categorie> cates = new ArrayList<>();
				Iterator<Categorie> it = categroid_list.iterator();
				while(it.hasNext()){
					Categorie categorie = it.next();
					if (categorie.getYeartype().equals(yeartype)) {
						cates.add(categorie);
						it.remove(); //移除该对象
					}
				}
				brandMap.put(yeartype,cates);
			}
		}*/
		Map<String,List<Categorie>> seriesMap = new TreeMap<String, List<Categorie>>();

		//获取所有品牌首字母
		List<String> seriesGroupList = tCarSeriesMapper.searchAllGroup(request.getId());

		//获取所有车型品牌
		List<TCarSeries> tCarBrandList = tCarSeriesMapper.searchAllSeries(request.getId());
		if(seriesGroupList.size()>0 && seriesGroupList.size()>0){
			for(int i = 0;i<seriesGroupList.size();i++){
				String seriesGroup = seriesGroupList.get(i);
				List<Categorie> cates = new ArrayList<>();
				//Iterator迭代categroid_list
				Iterator<TCarSeries> it = tCarBrandList.iterator();
				while(it.hasNext()){
					TCarSeries tCarSeries = it.next();
					if (tCarSeries.getSeriesGroupName().equals(seriesGroup)) {
						Categorie result = new Categorie();
						result.setName(tCarSeries.getSeriesName());
						result.setFullname(tCarSeries.getSeriesName());
						result.setId(Integer.parseInt(tCarSeries.getSeriesId()));
						result.setParentid(Integer.parseInt(tCarSeries.getBrandId()));

						cates.add(result);
						it.remove(); //移除该对象
					}
				}
				seriesMap.put(seriesGroup,cates);
			}
		}

		return BaseResult.success(seriesMap);
	}


	public List<CarModelsResponse> searchCarModels(String keyword, Integer pageNum, Integer pageSize)
	{
		if(!StringUtil.isEmpty(keyword))
		{
			keyword=keyword.replaceAll(" ","%");
		}
		int startNum = pageNum * pageSize;
		int size = pageSize;
		return tCarModelMapper.searchCarModelsByKey(keyword, startNum, size);
	}

	public String carLogo(String parameter) {
		String logo=categorieMapper.selectByCarBrand(parameter);
		if(StringUtils.isEmpty(logo)){
			return tCarBrandMapper.selectByCarBrand(parameter);
		}
		return logo;
	}
}
