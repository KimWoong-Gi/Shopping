package com.green.biz.address;

import java.util.List;

public interface AddressService {
	List<AddressVO> selectAddressByDong(AddressVO aVo);
}
