package com.vedruna.serverProject.services.technology;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vedruna.serverProject.persistance.repository.technology.TechnologyRepository;

@Service
public class TechnologyServiceImpl implements TechnologyServiceI{

	@Autowired
	private TechnologyRepository technologyRepository;
}
