package com.atguigu.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.crm.entity.Authority;
import com.atguigu.crm.mapper.AuthorityMapper;

@Service
public class AuthorityService {

	@Autowired
	private AuthorityMapper authorityMapper;


	public List<Authority> getParentAllAuth() {
		return authorityMapper.getParentAllAuth();
	}
	
}
