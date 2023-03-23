package com.tempalych.fcrdle.server.repository;

import com.tempalych.fcrdle.server.model.IpAddress;
import org.springframework.data.repository.CrudRepository;

public interface IpAddressRepository extends CrudRepository<IpAddress, Long> {
    IpAddress findByIp(String ip);
}
