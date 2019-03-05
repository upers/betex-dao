package com.vareger.redis.daos;

import org.springframework.data.repository.CrudRepository;

import com.vareger.redis.models.Chart;

public interface ChartRedisDAO extends CrudRepository<Chart, String>{
//	Chart findByKey(String key);
}
