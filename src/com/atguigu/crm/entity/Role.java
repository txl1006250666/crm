package com.atguigu.crm.entity;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import com.atguigu.crm.entity.Authority;

public class Role extends IdEntity {

	// 角色名称
	private String name;
	// 角色描述
	private String description;
	// 角色状态: 角色是否可用
	private boolean enabled;
	// 角色拥有的权限
	private List<Authority> authorities = new ArrayList<>();
	//该角色分配给了哪些用户
	private Set<User> users = new HashSet<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public List<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}
	
	
	public void setAuthorities2(List<String> authorities){
		this.authorities.clear();
		
		if (authorities == null) {
			return;
		}
		
		for(String authorityId: authorities){
			this.authorities.add(new Authority(Long.parseLong(authorityId)));
		}
	}
	
	@Transient
	public List<String> getAuthorities2(){
		List<String> authorites = new ArrayList<>();
		for(Authority authority:this.authorities){
			authorites.add("" + authority.getId());
		}
		
		return authorites;
	}
	
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
}
