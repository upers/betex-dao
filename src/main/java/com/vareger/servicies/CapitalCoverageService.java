package com.vareger.servicies;

import java.util.List;

import com.vareger.models.CapitalCoverage;

public interface CapitalCoverageService extends Service<Integer, CapitalCoverage>{
	List<CapitalCoverage> findAll(int limit);
}
