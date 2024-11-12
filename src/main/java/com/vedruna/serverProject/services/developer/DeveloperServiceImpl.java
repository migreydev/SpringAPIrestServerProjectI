package com.vedruna.serverProject.services.developer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vedruna.serverProject.persistance.repository.developer.DeveloperRepository;

@Service
public class DeveloperServiceImpl implements DeveloperServiceI{
	
	@Autowired
	private DeveloperRepository developerRepository;

}
