package org.tmdrk.toturial.service;

import org.tmdrk.toturial.entity.UserDTO;
import org.tmdrk.toturial.entity.UserVO;

public interface UserService {
	UserDTO findNameSex(UserVO vo);
}
