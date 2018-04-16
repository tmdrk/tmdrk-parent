package org.tmdrk.toturial.service.impl;

import org.tmdrk.toturial.entity.UserDTO;
import org.tmdrk.toturial.entity.UserPO;
import org.tmdrk.toturial.entity.UserVO;
import org.tmdrk.toturial.service.UserService;

public class UserServiceImpl implements UserService{

	@Override
	public UserDTO findNameSex(UserVO vo) {
		UserDTO dto= new UserDTO();
		if(vo.getUserType1().contains("11")){
			dto.setUserType(vo.getUserType1()+" | 男");
			return dto;
		}
		return dto;
	}
	public static void main(String[] args) {
		UserPO userPO = new UserPO();
		userPO.setUserId(1);
		userPO.setUserName("sdkf");
		userPO.setType("2");
		System.out.println(userPO.getUserId()+"|"+userPO.getUserName()+userPO.getUserType());
	}
}
